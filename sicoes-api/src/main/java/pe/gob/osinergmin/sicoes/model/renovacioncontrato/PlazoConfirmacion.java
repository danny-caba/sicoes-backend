package pe.gob.osinergmin.sicoes.model.renovacioncontrato;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pe.gob.osinergmin.sicoes.model.BaseModel;

/**
 * Entidad para la tabla SICOES_TM_PLAZO_CONFIRMACION
 * Representa los plazos de confirmación en el sistema
 */
@Entity
@Table(name = "SICOES_TM_PLAZO_CONFIRMACION")
public class PlazoConfirmacion extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PLAZO_CONF")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_PLAZO_CONF", sequenceName = "SEQ_SICOES_TM_PLAZO_CONF", allocationSize = 1)
    @Column(name = "ID_PLAZO_CONFIRMACION")
    private Long idPlazoConfirmacion;

    @Column(name = "FE_BASE", length = 38, nullable = false)
    private String feBase;

    @Column(name = "IN_TIPO_DIA", nullable = false)
    private Integer inTipoDia;

    @Column(name = "NU_DIAS", precision = 5, nullable = false)
    private Integer nuDias;

    @Column(name = "ES_REGISTRO", length = 1, nullable = false)
    private String esRegistro;

    // Constructores
    public PlazoConfirmacion() {
    }

    public PlazoConfirmacion(String feBase, Integer inTipoDia, Integer nuDias, String esRegistro) {
        this.feBase = feBase;
        this.inTipoDia = inTipoDia;
        this.nuDias = nuDias;
        this.esRegistro = esRegistro;
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
        return "PlazoConfirmacion [idPlazoConfirmacion=" + idPlazoConfirmacion 
                + ", feBase=" + feBase 
                + ", inTipoDia=" + inTipoDia 
                + ", nuDias=" + nuDias 
                + ", esRegistro=" + esRegistro + "]";
    }
}
