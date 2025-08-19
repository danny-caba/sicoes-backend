package pe.gob.osinergmin.sicoes.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.consumer.PidoConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.ProcesoDocumento;
import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetallePerfilDTO;
import pe.gob.osinergmin.sicoes.model.dto.PerfilDetalleDTO;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.ProcesoDocumentoService;
import pe.gob.osinergmin.sicoes.service.ProcesoEtapaService;
import pe.gob.osinergmin.sicoes.service.ProcesoService;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.util.bean.siged.UnidadOutRO;

@RestController
@RequestMapping("/api")
public class ListadoRestController extends BaseRestController {

    private Logger logger = LogManager.getLogger(ListadoRestController.class);

    @Autowired
	PidoConsumer pidoConsumer;
    @Autowired
    private ListadoDetalleService listadoDetalleService;
    @Autowired
    private ProcesoService procesoService;
    @Autowired
    private ProcesoEtapaService procesoEtapaService;
    @Autowired
    private ProcesoDocumentoService procesoDocumentoService;
    @Autowired
    private ArchivoService archivoService;

    @GetMapping("/listado/{codigo}")
    public List<ListadoDetalle> listar(@PathVariable String codigo, HttpServletRequest request) {
        return listadoDetalleService.buscar(codigo, getContexto());
    }

    @GetMapping("/listado/{codigo}/detalles/{idSuperior}")
    public List<ListadoDetalle> listar(@PathVariable String codigo, @PathVariable Long idSuperior,
            HttpServletRequest request) {
        return listadoDetalleService.buscar(codigo, idSuperior, getContexto());
    }

    @GetMapping("/listado-publico/{codigo}/detalles/{idSuperior}")
    public List<ListadoDetalle> listarPublico(@PathVariable String codigo, @PathVariable Long idSuperior,
            HttpServletRequest request) {
        return listadoDetalleService.buscar(codigo, idSuperior, null);
    }

    @GetMapping("/listado/perfiles")
    public List<ListadoDetallePerfilDTO> listar(@RequestParam(required = false) Long idSubSector) {
        return listadoDetalleService.buscarPerfiles(idSubSector);
    }

    @GetMapping("/listado-publico/{codigo}")
    public List<ListadoDetalle> listarPublico(@PathVariable String codigo, HttpServletRequest request) {
        return listadoDetalleService.buscar(codigo, null);
    }

    @GetMapping("/listado-publico/listar-procesos")
    @Raml("proceso.listar.properties")
    public Page<Proceso> buscarProcesosSeleccion(
            @RequestParam(required = false) Long idEstado,
            @RequestParam(required = false) String nombreArea,
            @RequestParam(required = false) String nombreProceso,
            Pageable pageable) {
        logger.info("buscarProcesos");
        // if(getContexto().getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
        // return null;
        // }
        return procesoService.listarProcesosSeleccion(idEstado, nombreArea, nombreProceso, pageable,null);
    }

    @GetMapping("/listado-publico-unidad")
	public List<UnidadOutRO> listarUnidad(){
		logger.info("listarUnidad  ");
		return pidoConsumer.listarUnidad();
	}
    
    @GetMapping("/listado-publico-procesos/{procesoUuid}")
	@Raml("proceso.obtener.properties")
	public Proceso obtener(@PathVariable String procesoUuid) {
		logger.info("obtener {} ", procesoUuid);
		return procesoService.obtener(procesoUuid,null);
	}
    
    @GetMapping("/listado-publico-procesos/listar-etapas")
	@Raml("procesoEtapa.listar.properties")
	public Page<ProcesoEtapa> buscar(@RequestParam(required=true) String procesoUuid,Pageable pageable) {
		logger.info("listarEtapas");			
		return procesoEtapaService.listarEtapas(procesoUuid, pageable, null);
	}
    
    @GetMapping("/listado-publico-procesos/listar-documento-etapas")
	@Raml("procesoDocumento.listar.properties")
	public Page<ProcesoDocumento> buscar(@RequestParam(required=true) Long idProceso,Pageable pageable) {
		logger.info("listarproceso-documentos");			
		return procesoDocumentoService.listarDocumentos(idProceso, pageable, null);
	}
    
	@GetMapping("/listado-publico/archivos/{uuid}/descarga")
	public ResponseEntity<Object> download(@PathVariable String uuid, HttpServletResponse response)
	{
		Archivo archivo= archivoService.obtener(uuid,null);
	    download(archivo,response);
	    return new ResponseEntity<>(HttpStatus.OK);
	}

    @GetMapping("/listado/perfiles/{idProfesion}/{idDivision}")
    public List<ListadoDetallePerfilDTO> listarPerfilesPorIdProfesionIdDivisioon(@PathVariable Long idProfesion,
            @PathVariable Long idDivision, HttpServletRequest request) {
        return listadoDetalleService.listarPerfilesDistintosPorIdProfesionIdDivision(idProfesion, idDivision,
                getContexto());
    }

    @GetMapping("/listado-detalle/{codigo}")
    public List<ListadoDetalle> listarListadoDetallePorCoodigo(@PathVariable String codigo,
            HttpServletRequest request) {
		return listadoDetalleService.listarListadoDetallePorCoodigo(codigo, getContexto());
	}
    
    public void download(Archivo archivo, HttpServletResponse response){
		try {
			response.setContentType(archivo.getTipo());
			response.setHeader("Content-Disposition", "attachment; filename=\""+archivo.getNombre()+"\""); 
		    IOUtils.copy(new ByteArrayInputStream(archivo.getContenido()), response.getOutputStream());
		    response.flushBuffer();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
    @GetMapping("/listado-publico/perfiles/detalle")
    public List<PerfilDetalleDTO> listarPublicoDetalle() {
        return listadoDetalleService.buscarPerfilesDetalle(null);
    }

    @GetMapping("/listado-publico/perfiles/division/{idDivision}/detalle")
    public List<ListadoDetalle> listarPerfilesDetallePorDivision(@PathVariable Long idDivision) {
        return listadoDetalleService.buscarPerfilesDetallePorDivision(idDivision, getContexto());
    }
}
