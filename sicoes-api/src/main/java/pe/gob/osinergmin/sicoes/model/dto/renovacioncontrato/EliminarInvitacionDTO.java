package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.io.Serializable;

/**
 * DTO para eliminar invitación de renovación de contrato
 */
public class EliminarInvitacionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idReqInvitacion;
    private String motivo;
    private String observaciones;

    public EliminarInvitacionDTO() {
    }

    public EliminarInvitacionDTO(Long idReqInvitacion) {
        this.idReqInvitacion = idReqInvitacion;
    }

    public EliminarInvitacionDTO(Long idReqInvitacion, String motivo) {
        this.idReqInvitacion = idReqInvitacion;
        this.motivo = motivo;
    }

    public Long getIdReqInvitacion() {
        return idReqInvitacion;
    }

    public void setIdReqInvitacion(Long idReqInvitacion) {
        this.idReqInvitacion = idReqInvitacion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "EliminarInvitacionDTO [idReqInvitacion=" + idReqInvitacion 
                + ", motivo=" + motivo 
                + ", observaciones=" + observaciones + "]";
    }
}