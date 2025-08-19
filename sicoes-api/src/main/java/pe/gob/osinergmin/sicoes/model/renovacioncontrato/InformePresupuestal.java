package pe.gob.osinergmin.sicoes.model.renovacioncontrato;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pe.gob.osinergmin.sicoes.model.BaseModel;

/**
 * Entidad para la tabla SICOES_TZ_INF_PRESUPUESTAL
 * Representa los informes presupuestales
 */
@Entity
@Table(name = "SICOES_TZ_INF_PRESUPUESTAL")
public class InformePresupuestal extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_INF_PRESUP")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_INF_PRESUP", sequenceName = "SEQ_SICOES_INF_PRESUP", allocationSize = 1)
    @Column(name = "ID_INF_PRESUPUESTAL")
    private Long idInfPresupuestal;

 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REQ_APROBACION", insertable = false, updatable = false)
    private RequerimientoAprobacion requerimientoAprobacion;


    @Column(name = "DE_UUID_ARCHIVO", length = 36, nullable = false)
    private String deUuidArchivo;

    @Column(name = "DE_NOMBRE_ARCHIVO", length = 100, nullable = false)
    private String deNombreArchivo;

    @Column(name = "DE_RUTA_ARCHIVO", length = 100, nullable = false)
    private String deRutaArchivo;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_CARGA", nullable = false)
    private Date feCarga;

    @Column(name = "ES_REGISTRO", length = 1, nullable = false)
    private String esRegistro;

    // Constructores
    public InformePresupuestal() {
    }

    public InformePresupuestal(String deUuidArchivo, String deNombreArchivo, 
                          String deRutaArchivo, String esRegistro) {
        this.deUuidArchivo = deUuidArchivo;
        this.deNombreArchivo = deNombreArchivo;
        this.deRutaArchivo = deRutaArchivo;
        this.esRegistro = esRegistro;
        this.feCarga = new Date();
    }

    // Getters y Setters
    public Long getIdInfPresupuestal() {
        return idInfPresupuestal;
    }

    public void setIdInfPresupuestal(Long idInfPresupuestal) {
        this.idInfPresupuestal = idInfPresupuestal;
    }

    public RequerimientoAprobacion getRequerimientoAprobacion() {
        return requerimientoAprobacion;
    }

    public void setRequerimientoAprobacion(RequerimientoAprobacion requerimientoAprobacion) {
        this.requerimientoAprobacion = requerimientoAprobacion;
    }

    public String getDeUuidArchivo() {
        return deUuidArchivo;
    }

    public void setDeUuidArchivo(String deUuidArchivo) {
        this.deUuidArchivo = deUuidArchivo;
    }

    public String getDeNombreArchivo() {
        return deNombreArchivo;
    }

    public void setDeNombreArchivo(String deNombreArchivo) {
        this.deNombreArchivo = deNombreArchivo;
    }

    public String getDeRutaArchivo() {
        return deRutaArchivo;
    }

    public void setDeRutaArchivo(String deRutaArchivo) {
        this.deRutaArchivo = deRutaArchivo;
    }

    public Date getFeCarga() {
        return feCarga;
    }

    public void setFeCarga(Date feCarga) {
        this.feCarga = feCarga;
    }

    public String getEsRegistro() {
        return esRegistro;
    }

    public void setEsRegistro(String esRegistro) {
        this.esRegistro = esRegistro;
    }

    @Override
    public String toString() {
        return "InformePresupuestal [idInfPresupuestal=" + idInfPresupuestal 
                + ", deUuidArchivo=" + deUuidArchivo 
                + ", deNombreArchivo=" + deNombreArchivo 
                + ", deRutaArchivo=" + deRutaArchivo 
                + ", feCarga=" + feCarga 
                + ", esRegistro=" + esRegistro + "]";
    }
}
