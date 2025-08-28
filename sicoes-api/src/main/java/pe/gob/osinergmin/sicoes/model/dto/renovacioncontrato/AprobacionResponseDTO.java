package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

/**
 * DTO para cada aprobación realizada.
 */
public class AprobacionResponseDTO {
    /** Notificación asociada a la aprobación */
    private NotificacionAprobacionResponseDTO notificacion;

    // Getters y Setters
    public NotificacionAprobacionResponseDTO getNotificacion() { return notificacion; }
    public void setNotificacion(NotificacionAprobacionResponseDTO notificacion) { this.notificacion = notificacion; }
}
