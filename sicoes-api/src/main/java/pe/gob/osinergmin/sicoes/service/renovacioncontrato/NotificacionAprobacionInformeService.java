package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.NotificacionRCDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

/**
 * Servicio para el manejo de notificaciones de aprobación de informes de renovación de contrato.
 */
public interface NotificacionAprobacionInformeService {

    /**
     * Envía notificación cuando un informe está por aprobar y firmar.
     * @param usuario Usuario que recibirá la notificación
     * @param numExpediente Número de expediente del requerimiento
     * @param contexto Contexto de la operación
     * @return ID de la notificación creada
     */
    Long notificacionInformePorAprobaryFirmar(Usuario usuario, String numExpediente, Contexto contexto);

    /**
     * Envía notificación cuando un informe está por revisar.
     * @param usuario Usuario que recibirá la notificación
     * @param numExpediente Número de expediente del requerimiento
     * @param contexto Contexto de la operación
     * @return ID de la notificación creada
     */
    Long notificacionInformePorRevisar(Usuario usuario, String numExpediente, Contexto contexto);

    /**
     * Envía notificación cuando un informe está por evaluar.
     * @param usuario Usuario que recibirá la notificación
     * @param numExpediente Número de expediente del requerimiento
     * @param contexto Contexto de la operación
     * @return ID de la notificación creada
     */
    Long notificacionInformePorEvaluar(Usuario usuario, String numExpediente, Contexto contexto);
}
