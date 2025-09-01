package pe.gob.osinergmin.sicoes.job;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.web.multipart.MultipartFile;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ExcelUtils;

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

	@Autowired
	private ProcesoEtapaService procesoEtapaService;

	@Autowired
	private ProcesoConsultaService procesoConsultaService;

	@Autowired
	private ProcesoDocumentoService procesoDocumentoService;

	@Autowired
	private ListadoDetalleService listadoDetalleService;

	@Autowired
	private SicoesSolicitudService sicoesSolicitudService;

	@Autowired
	private RequerimientoInvitacionService requerimientoInvitacionService;


	@Value("${path.temporal}")
	private String path;
	
	@Scheduled(fixedRate = 5*60*1000)
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

	@Scheduled(cron = "0 0 4 * * ?")
	public void tareaDiariaConsolidadoConsultas() throws Exception {
		logger.info("Inicio de la tarea diaria de consolidado de consultas");
		ListadoDetalle etapaFormulacion = listadoDetalleService.obtenerListadoDetalleOrden(Constantes.LISTADO.ETAPA_PROCESO.CODIGO,
				Constantes.LISTADO.ETAPA_PROCESO.ETAPA_FORMULACION_ORDEN);
		List<Object[]> lstEtapaProceso = procesoEtapaService.listarEtapasFormulacionConsultas(etapaFormulacion.getIdListadoDetalle());
		logger.info("Se encontraron {} procesos en etapa de formulación", lstEtapaProceso.size());

		for(Object[] etapaProceso : lstEtapaProceso) {
			try {
				Long idProceso = Long.parseLong(etapaProceso[0].toString());
				Long idEtapa = Long.parseLong(etapaProceso[1].toString());
				logger.info("Procesando la etapa del proceso: " + etapaProceso[0] + ", " + etapaProceso[1]);
				InputStream is = procesoConsultaService.generarExport(idProceso);
				MultipartFile xls = ExcelUtils.crearArchivoXls(is, "consultasFormuladas");

				procesoDocumentoService.registrarXls(xls, idEtapa, idProceso, "Consultas_Formuladas", getContextoAnonimo());
			} catch (Exception e) {
				logger.error("Error procesando la etapa del proceso: " + etapaProceso[0] + ", " + etapaProceso[1], e);
			}
		}
		logger.info("Fin de la tarea diaria de consolidado de consultas");
	}

	@Scheduled(cron = "0 0 3,19 * * *")
	public void actualizarSolicitudesPerfContObservadasNotificadas() throws Exception {
		logger.info("Inicio actualizarSolicitudesPerfContObservadasNotificadas");
		sicoesSolicitudService.registrarNotificacionSolicitudPerfCont(getContextoAnonimo());
		logger.info("Fin actualizarSolicitudesPerfContObservadasNotificadas");
	}

	@Scheduled(cron = "0 1 0 * * ?")
	public void archivarSolicitudesPerfContNoPresentadas() throws Exception {
		logger.info("Inicio archivarSolicitudesPerfContNoPresentadas");
		sicoesSolicitudService.archivarSolicitudesPerfContNoPresentadas(getContextoAnonimo());
		logger.info("Fin archivarSolicitudesPerfContNoPresentadas");
	}

	//TODO: definir el timepo de ejecucion @Scheduled(fixedRate = 5*60*1000)  @Scheduled(cron = "0 3 18 * * ?")
	public void cancelarInvitacionesCaducada()throws Exception{
		logger.info("Ini cancelarInvitacionesCaducada");
		int i=requerimientoInvitacionService.cancelarCaducados(new Date(),getContextoAnonimo());
		logger.info("Fin cancelarInvitacionesCaducada {}",i);
	}
}
