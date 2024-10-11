package pe.gob.osinergmin.sicoes.job;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.service.AsignacionService;
import pe.gob.osinergmin.sicoes.service.ConfBandejaService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.ProcesoService;
import pe.gob.osinergmin.sicoes.service.PropuestaService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.service.SuspensionCancelacionService;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Component
public class ScheduledTasks {

	private static final Logger logger = LogManager.getLogger(ScheduledTasks.class);

	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
	private SolicitudService solicitudService; 
	
	@Autowired
	private SuspensionCancelacionService suspensionCancelacionService; 

	@Autowired
	private ProcesoService procesoService; 
	
	@Autowired
	private PropuestaService propuestaService;
	
	@Autowired
	private AsignacionService asignacionService;
	
	@Autowired
	private ConfBandejaService confBandejaService;
	
	
	@Value("${path.temporal}")
	private String path;
	
	@Scheduled(fixedRate = 5*1000)
	public void reportCurrentTime() throws Exception {
		logger.info("Inicio el Job");
//		solicitudService.subirDocumentoTecnicos(getContextoAnonimo());
		notificacionService.enviarCorreos();		
		solicitudService.archivarSolicitud();
		suspensionCancelacionService.procesarSupencionCancelacion();
		procesoService.actualizarProcesoAdmision(getContextoAnonimo());
		procesoService.actualizarProcesoPresentacion(getContextoAnonimo());
		logger.info("Fin Job");
		
	}
	
	@Scheduled(cron = "0 0 12 1/1 * ? ")
	public void limpiarArchivos() {
		File pathTemporal= new File(path);
		for(File f : pathTemporal.listFiles()){                                       
		    boolean delete = org.springframework.util.FileSystemUtils.deleteRecursively(f);
		     if(delete) logger.info("File deleted "+f.getAbsolutePath()); 
		}
		propuestaService.limpiarDescarga();
	}
	
	public Contexto getContextoAnonimo() {
		Contexto contexo = new Contexto();		
		contexo.setUsuarioApp("SICOES");
		contexo.setIp("127.0.0.1");
		return contexo;
	}
	
	//@Scheduled(fixedRate = 5*60*1000)
	@Scheduled(cron = "0 0 9 * * *")
	public void notificarEvaluacionPerfiles() {
		asignacionService.listarOtroRequisitoPerfilesPendientesDeEvaluacion(getContextoAnonimo());
		asignacionService.listarOtroRequisitoPerfilesPendientesDeAprobacion(getContextoAnonimo());
	}
	
	@Scheduled(cron = "0 0 9 * * ?")
	public void actualizarReasignacionesFinalizadas() {
		logger.info("Inicio del Job de actualizacion de reasignaciones finalizadas");
		
		confBandejaService.obtenerReasignacionesFinalizadas(getContextoAnonimo());
		
		logger.info("Fin del Job de actualizacion de reasignaciones finalizadas");

	}

	@Scheduled(cron = "0 0 3,19 * * *")
	public void actualizarSolicitudesObservadasNotificadas() throws Exception {		
		logger.info("Inicio del registro de la hora de notificación");
		solicitudService.registrarNotificacionSolicitud(getContextoAnonimo());
		logger.info("Fin el registro de la hora de notificación");
	}
	
	@Scheduled(cron = "0 0 7 * * *")
	public void notificarEvaluacionPendiente() {
		logger.info("Inicio de la notificación de evaluaciones pendientes");
		asignacionService.notificarEvaluacionesPendientes(asignacionService.listarPerfilesPendientesDeEvaluacion(), getContextoAnonimo());
		logger.info("Fin de la notificación de evaluaciones pendientes");
	}
	
	@Scheduled(cron = "0 30 7 * * *")
	public void actualizarEvaluacionPendientePorVacaciones() throws Exception {
		logger.info("Inicio de la actualización de evaluaciones pendientes por vacaciones");
		asignacionService.actualizarEvaluacionPendientePorVacaciones(getContextoAnonimo());
		logger.info("Fin de la actualización de evaluaciones pendientes por vacaciones");
	}
}
