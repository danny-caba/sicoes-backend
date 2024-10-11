package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.Estudio;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.service.EstudioService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.Estudios;
import pe.gob.osinergmin.sicoes.util.OtrosRequisitos;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/estudios")
public class EstudioRestController extends BaseRestController {

	@Autowired
	EstudioService estudioService;
	
	@Autowired
	SolicitudService solicitudService;
	
	private Logger logger = LogManager.getLogger(EstudioRestController.class);
	
	@GetMapping("/{tipo}")
	@Raml("estudio.listar.properties")
	public Page<Estudio> buscar(@PathVariable String  tipo,@RequestParam String solicitudUuid,Pageable pageable){
		Long idSolicitud = solicitudService.obtenerId(solicitudUuid);
		Page<Estudio> estudios= estudioService.buscar(Estudios.getCodigoByPath(tipo),idSolicitud,pageable,getContexto());
		return estudios;
	}
	
	@GetMapping("/sunedu")
	@Raml("estudio.listar.properties")
	public void calcularSunedu(@RequestParam String  solicitudUuid,Pageable pageable){
		Long idSolicitud = solicitudService.obtenerId(solicitudUuid);
		estudioService.calcularEstudioSunedu(idSolicitud,pageable,getContexto());		
	}
	
	@PostMapping("/{tipo}")
	@Raml("estudio.obtener.properties")
	public Estudio registrar(@PathVariable String  tipo,@RequestBody Estudio estudio) {
		logger.info("registrar {} ",estudio);
		estudio.setTipoEstudio(new ListadoDetalle());
		estudio.getTipoEstudio().setCodigo(Estudios.getCodigoByPath(tipo));
		Estudio estudioBD=  estudioService.guardar(estudio,getContexto());
		return estudioBD;
	}
	
	@PutMapping("/{tipo}/{id}")
	@Raml("estudio.obtener.properties")
	public Estudio modificar(@PathVariable String  tipo, @PathVariable Long  id, @RequestBody Estudio estudio) {
		logger.info("modificar {} {}",id,estudio);
		estudio.setIdEstudio(id);
		logger.info("TIPO================ {} {}",tipo);
		estudio.setTipoEstudio(new ListadoDetalle());
		estudio.getTipoEstudio().setCodigo(Estudios.getCodigoByPath(tipo));
		return estudioService.guardar(estudio,getContexto());
		
	}
	
	@DeleteMapping("/{tipo}/{id}")
	public void eliminar(@PathVariable Long  id){
		logger.info("eliminar {} ",id);
		estudioService.eliminar(id,getContexto());
	}
	
	@GetMapping("/{tipo}/{id}")
	@Raml("estudio.obtener.properties")
	public Estudio obtener(@PathVariable String  tipo, @PathVariable Long  id){
		logger.info("obtener {} ",id);
		return estudioService.obtener(id,getContexto());
	}
	
	
	@PutMapping("/{tipo}/{id}/evaluar")
	@Raml("estudio.obtener.properties")
	public Estudio evaluar(@PathVariable String  tipo,@PathVariable Long  id,@RequestBody Estudio estudio) {
		logger.info("modificar {} {}",id,estudio);
		estudio.setIdEstudio(id);
		estudio.setTipo(new ListadoDetalle());
		estudio.getTipo().setCodigo(OtrosRequisitos.getCodigoByPath(tipo));
		return estudioService.evalular(estudio,getContexto());
		
	}
	
}
