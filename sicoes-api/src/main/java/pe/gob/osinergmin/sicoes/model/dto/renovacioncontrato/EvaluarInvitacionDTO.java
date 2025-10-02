package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.io.Serializable;

public class EvaluarInvitacionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long idReqInvitacion;
    private Long idRequerimientoInvitacion; // Alias para compatibilidad con frontend
    
    // Nota: Solo necesitamos el ID para las operaciones de aceptar/rechazar
    // Toda la lógica de negocio se maneja en el backend
    
    public EvaluarInvitacionDTO() {
    }
    
    public Long getIdReqInvitacion() {
        return idReqInvitacion;
    }
    
    public void setIdReqInvitacion(Long idReqInvitacion) {
        this.idReqInvitacion = idReqInvitacion;
    }
    
    public Long getIdRequerimientoInvitacion() {
        return idRequerimientoInvitacion;
    }
    
    public void setIdRequerimientoInvitacion(Long idRequerimientoInvitacion) {
        this.idRequerimientoInvitacion = idRequerimientoInvitacion;
        // También setear el campo principal cuando se use el alias
        if (this.idReqInvitacion == null) {
            this.idReqInvitacion = idRequerimientoInvitacion;
        }
    }
    
    @Override
    public String toString() {
        return "EvaluarInvitacionDTO [idReqInvitacion=" + idReqInvitacion + 
               ", idRequerimientoInvitacion=" + idRequerimientoInvitacion + "]";
    }
}