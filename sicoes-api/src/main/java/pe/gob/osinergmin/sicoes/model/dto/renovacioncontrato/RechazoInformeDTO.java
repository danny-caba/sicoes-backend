package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.io.Serializable;

/**
 * DTO para el rechazo de informe de renovación
 */
public class RechazoInformeDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long idInformeRenovacion;
    private String motivoRechazo;
    private Long idUsuario;
    private String observaciones;
    private Long idGrupoAprobador;
    
    // Constructor vacío
    public RechazoInformeDTO() {
    }
    
    // Getters y Setters
    public Long getIdInformeRenovacion() {
        return idInformeRenovacion;
    }
    
    public void setIdInformeRenovacion(Long idInformeRenovacion) {
        this.idInformeRenovacion = idInformeRenovacion;
    }
    
    public String getMotivoRechazo() {
        return motivoRechazo;
    }
    
    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }
    
    public Long getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public Long getIdGrupoAprobador() {
        return idGrupoAprobador;
    }
    
    public void setIdGrupoAprobador(Long idGrupoAprobador) {
        this.idGrupoAprobador = idGrupoAprobador;
    }
}