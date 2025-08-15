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
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.Usuario;

/**
 * Entidad para la tabla SICOES_TC_REQ_RENOVACION
 * Representa los requerimientos de renovación de contrato
 */
@Entity
@Table(name = "SICOES_TC_REQ_RENOVACION")
public class RequerimientoRenovacion extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQ_RENOVACION")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_REQ_RENOVACION", sequenceName = "SEQ_SICOES_TC_REQ_RENOVACION", allocationSize = 1)
    @Column(name = "ID_REQ_RENOVACION")
    private Long idReqRenovacion;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SOLI_PERF_CONT", insertable = false, updatable = false)
    private SicoesSolicitud solicitudPerfil;

    @Column(name = "ID_USUARIO", precision = 10)
    private Long idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
    private Usuario usuario;

    @Column(name = "NU_EXPEDIENTE", length = 50, nullable = false)
    private String nuExpediente;

    @Column(name = "TI_SECTOR", length = 100, nullable = false)
    private String tiSector;

    @Column(name = "TI_SUB_SECTOR", length = 100, nullable = false)
    private String tiSubSector;

    @Column(name = "NO_ITEM", length = 100, nullable = false)
    private String noItem;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_REGISTRO", nullable = false)
    private Date feRegistro;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ES_REQ_RENOVACION", insertable = false, updatable = false)
    private ListadoDetalle estadoReqRenovacion;

    @Column(name = "DE_OBSERVACION", length = 500)
    private String deObservacion;

    @Column(name = "ES_REGISTRO", length = 1, nullable = false)
    private String esRegistro;

    // Constructores
    public RequerimientoRenovacion() {
    }

    public RequerimientoRenovacion(String nuExpediente, String tiSector, String tiSubSector, String noItem, 
                        String esRegistro) {
        this.nuExpediente = nuExpediente;
        this.tiSector = tiSector;
        this.tiSubSector = tiSubSector;
        this.noItem = noItem;
        this.esRegistro = esRegistro;
        this.feRegistro = new Date();
    }

    // Getters y Setters
    public Long getIdReqRenovacion() {
        return idReqRenovacion;
    }

    public void setIdReqRenovacion(Long idReqRenovacion) {
        this.idReqRenovacion = idReqRenovacion;
    }

    public SicoesSolicitud getSolicitudPerfil() {
        return solicitudPerfil;
    }

    public void setSolicitudPerfil(SicoesSolicitud solicitudPerfil) {
        this.solicitudPerfil = solicitudPerfil;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNuExpediente() {
        return nuExpediente;
    }

    public void setNuExpediente(String nuExpediente) {
        this.nuExpediente = nuExpediente;
    }

    public String getTiSector() {
        return tiSector;
    }

    public void setTiSector(String tiSector) {
        this.tiSector = tiSector;
    }

    public String getTiSubSector() {
        return tiSubSector;
    }

    public void setTiSubSector(String tiSubSector) {
        this.tiSubSector = tiSubSector;
    }

    public String getNoItem() {
        return noItem;
    }

    public void setNoItem(String noItem) {
        this.noItem = noItem;
    }

    public Date getFeRegistro() {
        return feRegistro;
    }

    public void setFeRegistro(Date feRegistro) {
        this.feRegistro = feRegistro;
    }

    public ListadoDetalle getEstadoReqRenovacion() {
        return estadoReqRenovacion;
    }

    public void setEstadoReqRenovacion(ListadoDetalle estadoReqRenovacion) {
        this.estadoReqRenovacion = estadoReqRenovacion;
    }

    public String getDeObservacion() {
        return deObservacion;
    }

    public void setDeObservacion(String deObservacion) {
        this.deObservacion = deObservacion;
    }

    public String getEsRegistro() {
        return esRegistro;
    }

    public void setEsRegistro(String esRegistro) {
        this.esRegistro = esRegistro;
    }

    @Override
    public String toString() {
        return "RequerimientoRenovacion [idReqRenovacion=" + idReqRenovacion 
                + ", idUsuario=" + idUsuario 
                + ", nuExpediente=" + nuExpediente 
                + ", tiSector=" + tiSector 
                + ", tiSubSector=" + tiSubSector 
                + ", noItem=" + noItem 
                + ", feRegistro=" + feRegistro 
                + ", deObservacion=" + deObservacion 
                + ", esRegistro=" + esRegistro + "]";
    }
}
