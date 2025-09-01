package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

/**
 * DTO para la notificación de aprobación.
 */
public class NotificacionAprobacionResponseDTO {
    /** Identificador de la notificación */
    private Long idNotificacion;
    /** Mensaje de la notificación */
    private String mensaje;

    // Getters y Setters
    public Long getIdNotificacion() { return idNotificacion; }
    public void setIdNotificacion(Long idNotificacion) { this.idNotificacion = idNotificacion; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
