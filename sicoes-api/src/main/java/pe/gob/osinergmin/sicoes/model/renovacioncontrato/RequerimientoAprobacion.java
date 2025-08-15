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
import pe.gob.osinergmin.sicoes.model.Notificacion;

/**
 * Entidad para la tabla SICOES_TC_REQ_APROBACION
 * Representa los requerimientos de aprobación de renovación de contratos
 */
@Entity
@Table(name = "SICOES_TC_REQ_APROBACION")
public class RequerimientoAprobacion extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQ_APROBACION")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_REQ_APROBACION", sequenceName = "SICOES_SEQ_REQ_APROBACION", allocationSize = 1)
    @Column(name = "ID_REQ_APROBACION")
    private Long idReqAprobacion;

    @Column(name = "ID_REQUERIMIENTO", precision = 38, nullable = false)
    private Long idRequerimiento;

    @Column(name = "ID_REQ_INFORME", precision = 38)
    private Long idReqInforme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INFORME_RENOVACION", nullable = false,insertable = false, updatable = false)
    @JsonIgnore
    private InformeRenovacionContrato informeRenovacionContrato;


    @Column(name = "ID_REQ_DOCUMENTO", precision = 38)
    private Long idReqDocumento;

    @Column(name = "ID_TIPO_LD", precision = 38)
    private Long idTipoLd;

    @Column(name = "ID_GRUPO_LD", precision = 38)
    private Long idGrupoLd;

    @Column(name = "ID_USUARIO", precision = 38)
    private Long idUsuario;

    @Column(name = "ID_ESTADO_LD", precision = 38)
    private Long idEstadoLd;

    @Column(name = "ID_FIRMADO_LD", precision = 38)
    private Long idFirmadoLd;

    @Column(name = "DE_OBSERVACION", length = 500)
    private String deObservacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_APROBACION")
    private Date feAprobacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_RECHAZO")
    private Date feRechazo;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_FIRMA")
    private Date feFirma;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INFORME_RENOVACION", insertable = false, updatable = false)
    private InformeRenovacion informeRenovacion;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICACION", insertable = false, updatable = false)
    private Notificacion notificacion;



    @Temporal(TemporalType.DATE)
    @Column(name = "FE_ASIGNACION")
    private Date feAsignacion;

    @Column(name = "ID_TIPO_APROBADOR_LD", precision = 38)
    private Long idTipoAprobadorLd;

    @Column(name = "ID_GRUPO_APROBADOR_LD", precision = 38)
    private Long idGrupoAprobadorLd;

    // Constructores
    public RequerimientoAprobacion() {
    }

    public RequerimientoAprobacion(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    // Getters y Setters
    public Long getIdReqAprobacion() {
        return idReqAprobacion;
    }

    public void setIdReqAprobacion(Long idReqAprobacion) {
        this.idReqAprobacion = idReqAprobacion;
    }

    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public Long getIdReqInforme() {
        return idReqInforme;
    }

    public void setIdReqInforme(Long idReqInforme) {
        this.idReqInforme = idReqInforme;
    }

    public Long getIdReqDocumento() {
        return idReqDocumento;
    }

    public void setIdReqDocumento(Long idReqDocumento) {
        this.idReqDocumento = idReqDocumento;
    }

    public Long getIdTipoLd() {
        return idTipoLd;
    }

    public void setIdTipoLd(Long idTipoLd) {
        this.idTipoLd = idTipoLd;
    }

    public Long getIdGrupoLd() {
        return idGrupoLd;
    }

    public void setIdGrupoLd(Long idGrupoLd) {
        this.idGrupoLd = idGrupoLd;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdEstadoLd() {
        return idEstadoLd;
    }

    public void setIdEstadoLd(Long idEstadoLd) {
        this.idEstadoLd = idEstadoLd;
    }

    public Long getIdFirmadoLd() {
        return idFirmadoLd;
    }

    public void setIdFirmadoLd(Long idFirmadoLd) {
        this.idFirmadoLd = idFirmadoLd;
    }

    public String getDeObservacion() {
        return deObservacion;
    }

    public void setDeObservacion(String deObservacion) {
        this.deObservacion = deObservacion;
    }

    public Date getFeAprobacion() {
        return feAprobacion;
    }

    public void setFeAprobacion(Date feAprobacion) {
        this.feAprobacion = feAprobacion;
    }

    public Date getFeRechazo() {
        return feRechazo;
    }

    public void setFeRechazo(Date feRechazo) {
        this.feRechazo = feRechazo;
    }

    public Date getFeFirma() {
        return feFirma;
    }

    public void setFeFirma(Date feFirma) {
        this.feFirma = feFirma;
    }

    public InformeRenovacion getInformeRenovacion() {
        return informeRenovacion;
    }

    public void setInformeRenovacion(InformeRenovacion informeRenovacion) {
        this.informeRenovacion = informeRenovacion;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public Date getFeAsignacion() {
        return feAsignacion;
    }

    public void setFeAsignacion(Date feAsignacion) {
        this.feAsignacion = feAsignacion;
    }

    public Long getIdTipoAprobadorLd() {
        return idTipoAprobadorLd;
    }

    public void setIdTipoAprobadorLd(Long idTipoAprobadorLd) {
        this.idTipoAprobadorLd = idTipoAprobadorLd;
    }

    public Long getIdGrupoAprobadorLd() {
        return idGrupoAprobadorLd;
    }

    public void setIdGrupoAprobadorLd(Long idGrupoAprobadorLd) {
        this.idGrupoAprobadorLd = idGrupoAprobadorLd;
    }

    @Override
    public String toString() {
        return "RequerimientoAprobacion [idReqAprobacion=" + idReqAprobacion 
                + ", idRequerimiento=" + idRequerimiento 
                + ", idReqInforme=" + idReqInforme 
                + ", idReqDocumento=" + idReqDocumento 
                + ", idTipoLd=" + idTipoLd 
                + ", idGrupoLd=" + idGrupoLd 
                + ", idUsuario=" + idUsuario 
                + ", idEstadoLd=" + idEstadoLd 
                + ", idFirmadoLd=" + idFirmadoLd 
                + ", deObservacion=" + deObservacion 
                + ", feAprobacion=" + feAprobacion 
                + ", feRechazo=" + feRechazo 
                + ", feFirma=" + feFirma 
                + ", feAsignacion=" + feAsignacion 
                + ", idTipoAprobadorLd=" + idTipoAprobadorLd 
                + ", idGrupoAprobadorLd=" + idGrupoAprobadorLd + "]";
    }
}
