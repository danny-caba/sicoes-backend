package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.io.Serializable;

/**
 * DTO para la actualización de bandejas (aprobaciones y renovación de contrato)
 */
public class ActualizacionBandejaDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long idRequerimientoAprobacion;
    private Long idRequerimientoRenovacion;
    private Long idInformeRenovacion;
    private Integer nuevoEstado;
    private String observaciones;
    private Long idUsuario;
    
    // Constructor vacío
    public ActualizacionBandejaDTO() {
    }
    
    // Getters y Setters
    public Long getIdRequerimientoAprobacion() {
        return idRequerimientoAprobacion;
    }
    
    public void setIdRequerimientoAprobacion(Long idRequerimientoAprobacion) {
        this.idRequerimientoAprobacion = idRequerimientoAprobacion;
    }
    
    public Long getIdRequerimientoRenovacion() {
        return idRequerimientoRenovacion;
    }
    
    public void setIdRequerimientoRenovacion(Long idRequerimientoRenovacion) {
        this.idRequerimientoRenovacion = idRequerimientoRenovacion;
    }
    
    public Long getIdInformeRenovacion() {
        return idInformeRenovacion;
    }
    
    public void setIdInformeRenovacion(Long idInformeRenovacion) {
        this.idInformeRenovacion = idInformeRenovacion;
    }
    
    public Integer getNuevoEstado() {
        return nuevoEstado;
    }
    
    public void setNuevoEstado(Integer nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public Long getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}