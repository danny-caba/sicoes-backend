package pe.gob.osinergmin.sicoes.controller;

import java.io.ByteArrayInputStream;
import java.util.Date;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.dto.SolicitudDTO;
import pe.gob.osinergmin.sicoes.service.BitacoraService;
import pe.gob.osinergmin.sicoes.service.DocumentoService;
import pe.gob.osinergmin.sicoes.service.EstudioService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.OtroRequisitoService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.bean.siged.DocumentoOutRO;
@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudRestController extends BaseRestController{

	private Logger logger = LogManager.getLogger(SolicitudRestController.class);
	
	@Autowired
	SolicitudService solicitudService; 
	
	@Autowired
	NotificacionService notificacionService;
	
	@Autowired
	private BitacoraService bitacoraService;
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private EstudioService estudioService;
	
	@Autowired
	private OtroRequisitoService otroRequisitoService;

	@PostMapping
	@Raml("solicitud.obtener.properties")
	public Solicitud registrar(@RequestBody Solicitud solicitud) {
		logger.info("registrar {} ",solicitud);
		return solicitudService.guardar(solicitud,getContexto());
	}
	
	@PutMapping("/{solicitudUuid}")
	@Raml("solicitud.obtener.properties")
	public Solicitud modificar(@PathVariable String  solicitudUuid,@RequestBody Solicitud solicitud) {
		logger.info("modificar {} {}",solicitudUuid,solicitud);
		solicitud.setSolicitudUuid(solicitudUuid);
		return solicitudService.guardar(solicitud,getContexto());
		
	}
	
	@PutMapping("/{solicitudUuid}/enviar")
	@Raml("solicitud.obtener.properties")
	public Solicitud enviar(@PathVariable String  solicitudUuid, @RequestBody Solicitud solicitud) {
		logger.info("modificar {} {}",solicitudUuid,solicitud);
		try {
			solicitud.setSolicitudUuid(solicitudUuid);
			if(solicitud.getIdSolicitudPadre()==null) {
				bitacoraService.registrarSolicitudInscripcion(solicitud, getContexto());
			}else {
				bitacoraService.subsanacionSolicitud(solicitud, getContexto());
			}			
			solicitudService.guardar(solicitud,getContexto());
			Solicitud solicitudBD= solicitudService.enviar(solicitud,getContexto());
			if(solicitudBD.getIdSolicitudPadre()==null) {
				bitacoraService.registrarSolicitudInscripcionGenerarExpediente(solicitudBD, getContexto());
			}else {
				bitacoraService.subsanacionSolicitudGenerarExpediente(solicitudBD, getContexto());
			}			
			return solicitudBD;
		}catch (ValidacionException e) {
			/* Se comenta por que generaba tranzacción de envio en la solicitud
			if(solicitud.getIdSolicitudPadre()==null) {
				bitacoraService.registrarSolicitudInscripcionGenerarExpedienteError(solicitud, getContexto());
			}else {
				bitacoraService.subsanacionSolicitudGenerarExpedienteError(solicitud, getContexto());
			}*/			
			throw e;
		}
		
	}
	
	@PutMapping("/{id}/clonar")
	@Raml("solicitud.obtener.properties")
	public Solicitud clonar(@PathVariable Long  id) {
		return solicitudService.clonarSolicitud(id,getContexto());		
	}
	
	@PutMapping("/{id}/observacionesAdm")
	@Raml("solicitud.obtener.properties")
	public Solicitud guardarObservacionAdm(@PathVariable String  id,@RequestBody Solicitud solicitud) {
		logger.info("guardarObservacion {} {}",id,solicitud);
		solicitud.setSolicitudUuid(id);
		return solicitudService.guardarObservacionAdm(solicitud,getContexto());
		
	}
	
	@PutMapping("/{id}/observacionesTec")
	@Raml("solicitud.obtener.properties")
	public Solicitud guardarObservacionTec(@PathVariable String  id,@RequestBody Solicitud solicitud) {
		logger.info("guardarObservacion {} {}",id,solicitud);
		solicitud.setSolicitudUuid(id);
		return solicitudService.guardarObservacionTec(solicitud,getContexto());
		
	}
	
	@PutMapping("/{id}/resultado")
	@Raml("solicitud.obtener.properties")
	public Solicitud guardarResultado(@PathVariable String  id,@RequestBody Solicitud solicitud) {
		logger.info("guardarObservacion {} {}",id,solicitud);
		solicitud.setSolicitudUuid(id);
		return solicitudService.guardarResultado(solicitud,getContexto());
		
	}
	
	@PutMapping("/{id}/finalizar-administrativo")
	@Raml("solicitud.obtener.properties")
	public Solicitud finalizarAdministrativo(@PathVariable String  id,@RequestBody Solicitud solicitud) {
		logger.info("guardarObservacion {} {}",id,solicitud);
		solicitud.setSolicitudUuid(id);
		return solicitudService.finalizarAdministrativo(solicitud,getContexto());
		
	}
	
	@PutMapping("/{id}/finalizar-tecnico")
	@Raml("solicitud.obtener.properties")
	public Solicitud finalizarTecnico(@PathVariable String  id,@RequestBody Solicitud solicitud) {
		logger.info("guardarObservacion {} {}",id,solicitud);
		solicitud.setSolicitudUuid(id);
		return solicitudService.finalizarTecnico(solicitud,getContexto());
		
	}
	
	@DeleteMapping("/{solicitudUuid}")
	public void eliminar(@PathVariable Long  id){
		logger.info("eliminar {} ", id);
		solicitudService.eliminar(id,getContexto());
	}
	
	@GetMapping("/{solicitudUuid}")
	@Raml("solicitud.obtener.properties")
	public Solicitud obtener(@PathVariable String solicitudUuid){
		logger.info("obtener {} ", solicitudUuid);
		return solicitudService.obtener(solicitudUuid, getContexto());
	}
	
	
	@GetMapping("/{solicitudUuid}/expediente-siged")
	public List<DocumentoOutRO> obtenerExpediente(@PathVariable String solicitudUuid){
		logger.info("obtener {} ", solicitudUuid);
		return solicitudService.obtenerExpediente(solicitudUuid, getContexto());
	}
	
	
	
//	@GetMapping("/{id}/PDF")
//	public ResponseEntity<?> generarPDF(@PathVariable Long  id, HttpServletResponse response){
//		logger.info("obtener {} ",id);
//		try {
//			Archivo archivo=solicitudService.generarReporte(id,getContexto());
//			response.setContentType(archivo.getTipo());
//			response.setHeader("Content-Disposition", "attachment; filename=\""+archivo.getNombre()+"\""); 
//		    IOUtils.copy(new ByteArrayInputStream(archivo.getContenido()), response.getOutputStream());
//		    response.flushBuffer();
//		}catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
	
	@GetMapping("/{id}/subsanacion")
	public ResponseEntity<?> generarPDFSubsanacion(@PathVariable Long  id, HttpServletResponse response){
		logger.info("obtener {} ",id);
		try {
			Archivo archivo=solicitudService.generarReporteSubsanacion(id,getContexto());
			response.setContentType(archivo.getTipo());
			response.setHeader("Content-Disposition", "attachment; filename=\""+archivo.getNombre()+"\""); 
		    IOUtils.copy(new ByteArrayInputStream(archivo.getContenido()), response.getOutputStream());
		    response.flushBuffer();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{id}/resultado")
	public ResponseEntity<?> generarPDFResultado(@PathVariable Long  id, HttpServletResponse response){
		logger.info("obtener {} ",id);
		try {
			Solicitud solicitud=solicitudService.obtener(id, getContexto());
			Archivo archivo=solicitudService.generarReporteResultado(solicitud,Constantes.LISTADO.TIPO_ARCHIVO.RESULTADO,getContexto());
			response.setContentType(archivo.getTipo());
			response.setHeader("Content-Disposition", "attachment; filename=\""+archivo.getNombre()+"\""); 
		    IOUtils.copy(new ByteArrayInputStream(archivo.getContenido()), response.getOutputStream());
		    response.flushBuffer();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping
	@Raml("solicitud.listar.properties")
	public Page<Solicitud> buscar(
			@RequestParam(required=false) String fechaDesde,
			@RequestParam(required=false) String fechaHasta,
			@RequestParam(required=false) String nroExpediente,
			@RequestParam(required=false) Long idTipoSolicitud,
			@RequestParam(required=false) Long idEstadoSolicitud,
			@RequestParam(required=false) String solicitante,
			@RequestParam(required=false) Long idEstadoRevision,
			Pageable pageable) {
		logger.info("buscar {} {}");			
		return solicitudService.buscar(fechaDesde,fechaHasta,nroExpediente,idTipoSolicitud,idEstadoSolicitud,solicitante,idEstadoRevision,pageable,getContexto());
	}
	
	
	@GetMapping("/responsable")
	@Raml("solicitud.listar.properties")
	public Page<Solicitud> buscarResponsable(
			@RequestParam(required=false) String fechaDesde,
			@RequestParam(required=false) String fechaHasta,
			@RequestParam(required=false) String nroExpediente,
			@RequestParam(required=false) Long idTipoSolicitud,
			@RequestParam(required=false) Long idEstadoSolicitud,
			@RequestParam(required=false) String solicitante,
			@RequestParam(required=false) Long idEstadoRevision,
			Pageable pageable) {
		return solicitudService.buscarResponsable(fechaDesde,fechaHasta,nroExpediente,idTipoSolicitud,idEstadoSolicitud,solicitante,idEstadoRevision,pageable,getContexto());
	}
	
	@GetMapping("/evaluador")
	@Raml("solicitud.listar.properties")
	public Page<Solicitud> buscarEvaluador(
			@RequestParam(required=false) String fechaDesde,
			@RequestParam(required=false) String fechaHasta,
			@RequestParam(required=false) String nroExpediente,
			@RequestParam(required=false) Long idTipoSolicitud,
			@RequestParam(required=false) Long idEstadoSolicitud,
			@RequestParam(required=false) String solicitante,
			@RequestParam(required=false) Long idEstadoRevision,
			@RequestParam(required=false) Long idEstadoEvalTecnica,
			@RequestParam(required=false) Long idEstadoEvalAdministrativa,
			Pageable pageable) {
		return solicitudService.buscarEvaluador(fechaDesde,fechaHasta,nroExpediente,idTipoSolicitud,idEstadoSolicitud,solicitante,idEstadoRevision,idEstadoEvalTecnica,idEstadoEvalAdministrativa,pageable,getContexto());
	}
	
	
	@GetMapping("/aprobador")
	@Raml("solicitud.listar.properties")
	public Page<Solicitud> buscarAprobador(
			@RequestParam(required=false) String nroExpediente,
			@RequestParam(required=false) String solicitante,
			@RequestParam(required=false) Long idTipoSolicitud,
			@RequestParam(required=false) Long idEstadoRevision,
			@RequestParam(required=false) Long idEstadoEvaluacionTecnica,
			@RequestParam(required=false) Long idEstadoEvaluacionAdministrativa,		
			Pageable pageable) {
		return solicitudService.buscarAprobador(nroExpediente,solicitante,idTipoSolicitud,idEstadoRevision,idEstadoEvaluacionTecnica,idEstadoEvaluacionAdministrativa,pageable,getContexto());
	}
	
	@GetMapping("/copiar")
	public void copiar(@RequestParam(required=false) Long idSolicitud) {
		solicitudService.copiar(idSolicitud, getContexto());
	}
	
	@GetMapping("/notificar")
	public void notificar(@RequestParam(required=false) Long idSolicitud) {
		solicitudService.marcarSolicitudRespuesta(idSolicitud,new Date(), getContexto());
	}
	
	@GetMapping("/{id}/informe")
	public ResponseEntity<?> generarInformeTecnicoPDF(@PathVariable Long  id,@RequestParam(required=false) Long idAsignacion, HttpServletResponse response){
		logger.info("obtener {} ",id);
		try {
			Archivo archivo=solicitudService.generarInformeTecnico(id,idAsignacion,getContexto());
			response.setContentType(archivo.getTipo());
			response.setHeader("Content-Disposition", "attachment; filename=\""+archivo.getNombre()+"\""); 
		    IOUtils.copy(new ByteArrayInputStream(archivo.getContenido()), response.getOutputStream());
		    response.flushBuffer();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/ultimaSolicitudUsuario")
	public SolicitudDTO obtenerUltimaSolicitudPresentadaPorUsuario(HttpServletRequest request) {
		logger.info("obtenerUltimaSolicitud {} ");		
		
		SolicitudDTO dto = new SolicitudDTO();
		Solicitud solicitud = solicitudService.obtenerUltimaSolicitudPresentadaPorUsuario(getContexto());
		if (solicitud != null) {
			dto.setUuid(solicitud.getSolicitudUuid());			
		}
		
		return dto;
	}
	
	@PutMapping("/copiar/{solicitudUuidUltima}/{solicitudUuid}")
	public void copiarDocumentosUltimaSolicitud(@PathVariable String solicitudUuidUltima, @PathVariable String solicitudUuid) {
		logger.info("copiarDocumentosUltimaSolicitud {} {}", solicitudUuidUltima, solicitudUuid);
		documentoService.copiarDocumentosUltimaSolicitud(solicitudUuidUltima, solicitudUuid, getContexto());
		estudioService.copiarCapacitacionesUltimaSolicitud(solicitudUuidUltima, solicitudUuid, getContexto());
		otroRequisitoService.copiarOtrosRequisitosUltimaSolicitud(solicitudUuidUltima, solicitudUuid, getContexto());
	}
	@PutMapping("anular/{idSolicitud}")
	public void anularSolicitud(@PathVariable Long  idSolicitud) {
		logger.info("anular {}",idSolicitud);
		solicitudService.anularSolicitud(idSolicitud, getContexto());
	}
	
	@PutMapping("cancelar/{solicitudUuid}")
	public void cancelarSolicitud(@PathVariable String  solicitudUuid) {
		logger.info("cancelar {}",solicitudUuid);
		solicitudService.cancelarSolicitud(solicitudUuid, getContexto());
	}

	@GetMapping("/{solicitudUuid}/subsector-usuario")
	public List<Long> subsectoresUsuario(@PathVariable String solicitudUuid){
		logger.info("obtener {} ", solicitudUuid);
		return solicitudService.obtenerSubsectoresXUsuarioSolicitud(solicitudUuid, getContexto().getUsuario().getIdUsuario());
	}
	
}
