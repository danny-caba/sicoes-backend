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

import pe.gob.osinergmin.sicoes.model.SolicitudNotificacion;
import pe.gob.osinergmin.sicoes.service.SolicitudNotificacionService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/solicitud-notificacion")
public class SolicitudNotificacionRestController extends BaseRestController {

	@Autowired
	SolicitudNotificacionService solicitudNotificacionService;
	
	private Logger logger = LogManager.getLogger(SolicitudNotificacionRestController.class);
	
	@GetMapping
	@Raml("solicitud.notificacion.listar.properties")
	public Page<SolicitudNotificacion> buscar(
				@RequestParam(required=false) String codigoTipoNotificacion,Pageable pageable){
		Page<SolicitudNotificacion> solicitudNotificaciones= solicitudNotificacionService.buscar(codigoTipoNotificacion,pageable,getContexto());
		return solicitudNotificaciones;
	}
	
	
	@PostMapping
	@Raml("solicitud.notificacion.obtener.properties")
	public SolicitudNotificacion registrar(@RequestBody SolicitudNotificacion supervisora) {
		logger.info("registrar {} ",supervisora);
		SolicitudNotificacion solicitudNotificacionBD=  solicitudNotificacionService.guardar(supervisora,getContexto());
		return solicitudNotificacionBD;
	}
	
	@PutMapping("/{id}")
	@Raml("solicitud.notificacion.obtener.properties")
	public SolicitudNotificacion modificar(@PathVariable Long  id,@RequestBody SolicitudNotificacion solicitudNotificacion) {
		logger.info("modificar {} {}",id,solicitudNotificacion);
		solicitudNotificacion.setIdSolNotificacion(id);
		return solicitudNotificacionService.guardar(solicitudNotificacion,getContexto());
		
	}
	
	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long  id){
		logger.info("eliminar {} ",id);
		solicitudNotificacionService.eliminar(id,getContexto());
	}
	
	@GetMapping("/{id}")
	@Raml("solicitud.notificacion.obtener.properties")
	public SolicitudNotificacion obtener(@PathVariable Long  id){
		logger.info("obtener {} ",id);
		return solicitudNotificacionService.obtener(id,getContexto());
	}
	
	@GetMapping("/respuesta/{solicitudUuid}")
	@Raml("solicitud.notificacion.obtener.properties")
	public SolicitudNotificacion obtenerRespuesta(@PathVariable String solicitudUuid){
		logger.info("obtener {} ", solicitudUuid);
		return solicitudNotificacionService.obtenerXSolicitud(Constantes.LISTADO.TIPO_NOTIFICACION_SOLICITUD.RESPUESTA, solicitudUuid, getContexto());
	}

	@GetMapping("/archivamiento/{solicitudUuid}")
	@Raml("solicitud.notificacion.obtener.properties")
	public SolicitudNotificacion obtenerArchivamiento(@PathVariable String solicitudUuid){
		logger.info("obtener {} ", solicitudUuid);
		return solicitudNotificacionService.obtenerXSolicitud(Constantes.LISTADO.TIPO_NOTIFICACION_SOLICITUD.ARCHIVAMIENTO, solicitudUuid,getContexto());
	}

	
}
