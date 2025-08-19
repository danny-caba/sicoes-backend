package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.io.Serializable;

/**
 * DTO para el manejo de plazos de confirmación
 */
public class PlazoConfirmacionDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long idPlazoConfirmacion;
    private String feBase;
    private Integer inTipoDia;
    private Integer nuDias;
    private String esRegistro;
    
    // Constructor vacío
    public PlazoConfirmacionDTO() {
    }
    
    // Constructor con parámetros principales
    public PlazoConfirmacionDTO(String feBase, Integer inTipoDia, Integer nuDias) {
        this.feBase = feBase;
        this.inTipoDia = inTipoDia;
        this.nuDias = nuDias;
    }
    
    // Getters y Setters
    public Long getIdPlazoConfirmacion() {
        return idPlazoConfirmacion;
    }
    
    public void setIdPlazoConfirmacion(Long idPlazoConfirmacion) {
        this.idPlazoConfirmacion = idPlazoConfirmacion;
    }
    
    public String getFeBase() {
        return feBase;
    }
    
    public void setFeBase(String feBase) {
        this.feBase = feBase;
    }
    
    public Integer getInTipoDia() {
        return inTipoDia;
    }
    
    public void setInTipoDia(Integer inTipoDia) {
        this.inTipoDia = inTipoDia;
    }
    
    public Integer getNuDias() {
        return nuDias;
    }
    
    public void setNuDias(Integer nuDias) {
        this.nuDias = nuDias;
    }
    
    public String getEsRegistro() {
        return esRegistro;
    }
    
    public void setEsRegistro(String esRegistro) {
        this.esRegistro = esRegistro;
    }
    
    @Override
    public String toString() {
        return "PlazoConfirmacionDTO [idPlazoConfirmacion=" + idPlazoConfirmacion 
                + ", feBase=" + feBase 
                + ", inTipoDia=" + inTipoDia 
                + ", nuDias=" + nuDias 
                + ", esRegistro=" + esRegistro + "]";
    }
}
