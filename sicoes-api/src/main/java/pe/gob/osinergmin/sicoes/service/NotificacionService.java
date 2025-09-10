package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface NotificacionService extends BaseService<Notificacion, Long> {

	public Notificacion obtener(Long idNotificacion,Contexto contexto);
	public Page<Notificacion> buscar(Pageable pageable,Contexto contexto);
	void enviarCorreos();
	
	/**
	 * Envía notificación específica para procesos de renovación
	 * @param idTipoNotifica Tipo de notificación (1: Aprobación, 2: Rechazo, 3: Perfeccionamiento)
	 * @param mensaje Mensaje de la notificación
	 * @param contexto Contexto del usuario
	 */
	void enviarNotificacionRenovacion(Integer idTipoNotifica, String mensaje, Contexto contexto);
	public void enviarMensajeSolicitudInscripcion01(Solicitud solicitud,Contexto contexto);
	public void enviarMensajeAsignacionEvaluacion02(Asignacion asignacionBD,Contexto contexto);
	public void enviarMensajeEvaluacionConcluida03(Solicitud solicitud,String type,Contexto contexto);
	public void enviarMensajeResultadoSolicitud04(Solicitud solicitud,Contexto contexto);
	public void enviarMensajeSubsanacionIObservaciones05(Solicitud solicitud, Contexto contexto);
	public void enviarMensajeAsignacionEvaluador06(Asignacion asignacion,String type,Contexto contexto);
	public void enviarMensajePlazoConcluirTecnica07(Solicitud solicitud,Contexto contexto);
	public void enviarMensajeFueraPlazo08(Solicitud solicitud, Contexto contexto);
	public void enviarMensajePlazoRespuesta09(Solicitud solicitud, Contexto contexto);
	public void enviarMensajePlazoRespuestaVencido10(Solicitud solicitud, Contexto contexto);
	public void enviarMensajeSuspension11(Solicitud solicitud,String type, Contexto contexto);
	public void enviarMensajeActualizacion12(Solicitud solicitud, Contexto contexto);
	public void enviarMensajeCorreoValidacion13(Notificacion notificacion, Contexto contexto);
	public void enviarMensajeAsignacionAprobacion14(Asignacion asignacion, Contexto contexto);
	public void enviarMensajeSolicitudInscripcionExtranjera15(Solicitud solicitud,Contexto contexto);
	public void enviarMensajeSolicitudSubsanacionExtranjera16(Solicitud solicitud, Contexto contexto);
	public void enviarMensajeSolicitudResultadoExtranjera17(Solicitud solicitud,Archivo archivo, Contexto contexto);
	public void enviarMensajeSolicitudSubsanacionResultadoExtranjera18(Solicitud solicitud,Archivo archivo, Contexto contexto);
	public void enviarMensajePresentacionPropuesta(Propuesta propuesta, int numeroDocTec, int numeroDocEco, Contexto contexto);
	public void enviarMensajeAsignacionEvaluacion03(Asignacion asignacionBD,Contexto contexto);
	public void enviarMensajeEvaluacionPendiente(String asunto, String correo, String tipo, String table, Contexto contexto);
	public void enviarMensajeAsignacionEvaluacion04(Long idOtroRequisito, Contexto contexto);
	public void enviarMensajeSolicitudRevertirEvaluacion(Long idOtroRequisito, Contexto contexto);
	public void enviarMensajeAprobacionRevertirEvaluacion(Long idOtroRequisito, Contexto contexto);

	public void enviarMensajeRequerimientoInvitacionGPPM(RequerimientoInvitacion requerimientoInvitacion, Contexto contexto);
	public void enviarMensajeRequerimientoInvitacionGSE(RequerimientoInvitacion requerimientoInvitacion, Contexto contexto);
}
