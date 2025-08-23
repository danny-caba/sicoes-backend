package pe.gob.osinergmin.sicoes.model.renovacioncontrato;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import pe.gob.osinergmin.sicoes.model.BaseModel;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Notificacion;

/**
 * Entidad para la tabla SICOES_TC_REQ_INVITACION
 * Representa los requerimientos de invitación de renovación de contratos
 */
@Entity
@Table(name = "SICOES_TC_REQ_INVITACION")
public class RequerimientoInvitacion extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQ_INVITACION")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_REQ_INVITACION", sequenceName = "SICOES_SEQ_REQ_INVITACION", allocationSize = 1)
    @Column(name = "ID_REQ_INVITACION")
    private Long idReqInvitacion;

    @Column(name = "ID_REQUERIMIENTO", precision = 38)
    private Long idRequerimiento;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICACION")
    private Notificacion notificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PLAZO_CONFIRMACION")
    private PlazoConfirmacion plazoConfirmacion;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REQ_RENOVACION", insertable = false, updatable = false)
    @JsonIgnore
    private RequerimientoRenovacion requerimientoRenovacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO_LD", insertable = false, updatable = false)
    private ListadoDetalle estadoInvitacion;

    @Column(name = "ID_SUPERVISORA", precision = 38)
    private Long idSupervisora;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_INVITACION")
    private Date feInvitacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_CADUCIDAD")
    private Date feCaducidad;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_ACEPTACION")
    private Date feAceptacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_RECHAZO")
    private Date feRechazo;

    @Column(name = "CA_SALDO_CONTRATO", precision = 38)
    private BigDecimal caSaldoContrato;

    @Column(name = "FL_ACTIVO", length = 1, nullable = false)
    private String flActivo = "1";

    @Column(name = "CO_UUID", length = 50)
    private String coUuid;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_CANCELADO")
    private Date feCancelado;

    // Constructores
    public RequerimientoInvitacion() {
    }

    public RequerimientoInvitacion(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
        this.flActivo = "1";
    }

    public RequerimientoInvitacion(Long idRequerimiento, Long idSupervisora, String flActivo) {
        this.idRequerimiento = idRequerimiento;
        this.idSupervisora = idSupervisora;
        this.flActivo = flActivo != null ? flActivo : "1";
    }

    // Getters y Setters
    public Long getIdReqInvitacion() {
        return idReqInvitacion;
    }

    public void setIdReqInvitacion(Long idReqInvitacion) {
        this.idReqInvitacion = idReqInvitacion;
    }

    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }



    public PlazoConfirmacion getPlazoConfirmacion() {
        return plazoConfirmacion;
    }

    public void setPlazoConfirmacion(PlazoConfirmacion plazoConfirmacion) {
        this.plazoConfirmacion = plazoConfirmacion;
    }

    public RequerimientoRenovacion getRequerimientoRenovacion() {
        return requerimientoRenovacion;
    }

    public void setRequerimientoRenovacion(RequerimientoRenovacion requerimientoRenovacion) {
        this.requerimientoRenovacion = requerimientoRenovacion;
    }



    public Long getIdSupervisora() {
        return idSupervisora;
    }

    public void setIdSupervisora(Long idSupervisora) {
        this.idSupervisora = idSupervisora;
    }

    public Date getFeInvitacion() {
        return feInvitacion;
    }

    public void setFeInvitacion(Date feInvitacion) {
        this.feInvitacion = feInvitacion;
    }

    public Date getFeCaducidad() {
        return feCaducidad;
    }

    public void setFeCaducidad(Date feCaducidad) {
        this.feCaducidad = feCaducidad;
    }

    public Date getFeAceptacion() {
        return feAceptacion;
    }

    public void setFeAceptacion(Date feAceptacion) {
        this.feAceptacion = feAceptacion;
    }

    public Date getFeRechazo() {
        return feRechazo;
    }

    public void setFeRechazo(Date feRechazo) {
        this.feRechazo = feRechazo;
    }

    public BigDecimal getCaSaldoContrato() {
        return caSaldoContrato;
    }

    public void setCaSaldoContrato(BigDecimal caSaldoContrato) {
        this.caSaldoContrato = caSaldoContrato;
    }

    public String getFlActivo() {
        return flActivo;
    }

    public void setFlActivo(String flActivo) {
        this.flActivo = flActivo;
    }

    public String getCoUuid() {
        return coUuid;
    }

    public void setCoUuid(String coUuid) {
        this.coUuid = coUuid;
    }

    public Date getFeCancelado() {
        return feCancelado;
    }

    public void setFeCancelado(Date feCancelado) {
        this.feCancelado = feCancelado;
    }

    public ListadoDetalle getEstadoInvitacion() {
        return estadoInvitacion;
    }

    public void setEstadoInvitacion(ListadoDetalle estadoInvitacion) {
        this.estadoInvitacion = estadoInvitacion;
    }

    @Override
    public String toString() {
        return "RequerimientoInvitacion [idReqInvitacion=" + idReqInvitacion 
                + ", idRequerimiento=" + idRequerimiento

                + ", idSupervisora=" + idSupervisora 
                + ", feInvitacion=" + feInvitacion 
                + ", feCaducidad=" + feCaducidad 
                + ", feAceptacion=" + feAceptacion 
                + ", feRechazo=" + feRechazo 
                + ", caSaldoContrato=" + caSaldoContrato 
                + ", flActivo=" + flActivo 
                + ", coUuid=" + coUuid 
                + ", feCancelado=" + feCancelado + "]";
    }
}
