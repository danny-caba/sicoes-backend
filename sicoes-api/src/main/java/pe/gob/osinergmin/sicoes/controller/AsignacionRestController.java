package pe.gob.osinergmin.sicoes.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.dto.HistorialContratoDto;
import pe.gob.osinergmin.sicoes.service.AsignacionService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.bean.siged.AccessRequestInFirmaDigital;
@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionRestController extends BaseRestController{

	private Logger logger = LogManager.getLogger(AsignacionRestController.class);
	
	@Autowired
	AsignacionService asignacionService;
	
	@Autowired
	SolicitudService solicitudService; 
	
	@Autowired
	NotificacionService notificacionService;
	
	@GetMapping
	@Raml("asignacion.listar.properties")
	public Page<Asignacion> buscar(@RequestParam(required=false) String solicitudUuid,@RequestParam(required=false) String codigoTipoAprobador,Pageable pageable) throws InterruptedException {
		logger.info("buscar {} {}",solicitudUuid, codigoTipoAprobador);	
		Long idSolicitud = solicitudService.obtenerId(solicitudUuid);
		return asignacionService.buscar(idSolicitud,codigoTipoAprobador,pageable,getContexto());
	}


	@GetMapping("/{id}")
	@Raml("asignacion.obtener.properties")
	public Asignacion obtener(@PathVariable Long  id){
		logger.info("obtener {} ",id);
		return asignacionService.obtener(id,getContexto());
	}	
	
	@PostMapping
	public List<Asignacion> registrar(@RequestBody List<Asignacion> asignacion) {
		logger.info("registrar {} ",asignacion);
		 return asignacionService.guardar(asignacion,getContexto());
	}
	
	@PostMapping("/aprobadores")
	public Asignacion registrarAprobador(@RequestBody Asignacion asignacion) {
		logger.info("registrarAprobador {} ",asignacion);
		asignacion = asignacionService.validarAprobador(asignacion, getContexto());
		return asignacionService.guardarAprobador(asignacion,getContexto());
	}
	
	@PutMapping("/aprobadores/{id}")
	public boolean modificarAprobador(@PathVariable Long  id,@RequestBody Asignacion asignacion) {
		logger.info("modificarAprobador {} ",asignacion);
		asignacion.setIdAsignacion(id);
		return asignacionService.modificarAprobador(asignacion,getContexto());
	}
	
	@DeleteMapping("/aprobadores/{id}")
	public void eliminarAprobador(@PathVariable Long  id) {
		logger.info("eliminarAprobador {} ",id);
		asignacionService.eliminarAprobador(id,getContexto());		
	}
	
	@PutMapping("/{id}")
	@Raml("asignacion.obtener.properties")
	public Asignacion modificar(@PathVariable Long  id,@RequestBody Asignacion asignacion) {
		logger.info("modificar {} {}",id,asignacion);
		asignacion.setIdAsignacion(id);
		return asignacionService.guardar(asignacion,getContexto());
	}

	//RECHAZO
	@PutMapping("/{idAsignacion}/rechazar-perfil")
	public ResponseEntity<Map<String, Object>> rechazarPerfil(
	    @PathVariable Long idAsignacion,
	    @RequestBody Map<String, Object> request
	) {
	    try {
	        Long idOtroRequisito = Long.parseLong(request.get("idOtroRequisito").toString());
	        String observacion = (String) request.get("observacion");
	        
	        //asignacionService.crearHistorialAsignacion(idAsignacion, "RECHAZO", observacion, getContexto());
	        asignacionService.rechazarPerfil(idAsignacion, idOtroRequisito, observacion, getContexto());
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", true);
	        response.put("message", "Perfil rechazado exitosamente");
	        return ResponseEntity.ok(response);
	        
	    } catch (ValidacionException e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("success", false);
	        errorResponse.put("error", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    } catch (Exception e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("success", false);
	        errorResponse.put("error", "Error interno al rechazar el perfil");
	        logger.error("[{}] Error inesperado: {}", e.getMessage(), e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse); 
	    }
	}
	
	//RECHAZO
	@PostMapping("/historial")
	public ResponseEntity<Map<String, Object>> crearHistorialAsignacionEndpoint(
	        @RequestBody Map<String, Object> request
	) {
	    try {
	        Long idAsignacion = Long.parseLong(request.get("idAsignacion").toString());
	        String accion = (String) request.get("accion");
	        String observacion = (String) request.get("observacion");

	        asignacionService.crearHistorialAsignacion(idAsignacion, accion, observacion, getContexto());

	        Map<String, Object> response = new HashMap<>();
	        response.put("success", true);
	        response.put("message", "Historial de asignación creado exitosamente");
	        return ResponseEntity.ok(response);

	    } catch (ValidacionException e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("success", false);
	        errorResponse.put("error", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    } catch (Exception e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("success", false);
	        errorResponse.put("error", "Error interno al crear el historial de asignación");
	        logger.error("[{}] Error inesperado al crear historial: {}", e.getMessage(), e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	@GetMapping("/aprobador/{idAprobador}/perfiles-asignados")
    public ResponseEntity<List<Integer>> obtenerPerfilesAsignadosAprobador(
            @PathVariable Long idAprobador
    ) {
        try {
            List<Integer> idsPerfiles = asignacionService.obtenerIdsPerfilesAsignadosAprobador(idAprobador);
            return ResponseEntity.ok(idsPerfiles);
        } catch (Exception e) {
            logger.error("[{}] Error al obtener los IDs de perfiles del aprobador {}: {}", e.getMessage(), idAprobador, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

	@GetMapping("/aprobaciones")
	@Raml("asignacion.listar.properties")
	public Page<Asignacion> buscarAprobaciones(@RequestParam(required=false) String solicitudUuid,Pageable pageable) {
		logger.info("buscarAprobaciones {} ",solicitudUuid);			
		return asignacionService.buscarAprobaciones(solicitudUuid,pageable,getContexto());
	}
	
	@GetMapping("/obtenerIdArchivo/{numeroExpediente}")
	public Long obtenerIdArchivo(@PathVariable String numeroExpediente) throws Exception{

		logger.info("obtenerIdArchivo {} ", numeroExpediente);
		
		return asignacionService.obtenerIdArchivo(numeroExpediente, getContexto().getUsuario().getUsuario());
	}
	
	@GetMapping("/obtenerParametros/firmaDigital")
	public AccessRequestInFirmaDigital obtenerParametrosfirmaDigital(@RequestParam(required=false) String usuario) throws Exception{
		
		logger.info("obtenerParametrosfirmaDigital");
		return asignacionService.obtenerParametrosfirmaDigital(usuario, getContexto().getUsuario().getUsuario());
	}
	
	@GetMapping("/historial/{idContrato}")
	public ResponseEntity<List<Map<String,Object>>> getHistorialContrato(
	    @PathVariable Long idContrato) {
	  return ResponseEntity.ok(
	    asignacionService.obtenerHistorialPorContrato(idContrato)
	  );
	}
}
