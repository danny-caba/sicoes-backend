package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;

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
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="SICOES_TC_REQ_INVITACION")
public class RequerimientoInvitacion extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQ_INVITACION")
    @SequenceGenerator(name="GEN_SICOES_SEQ_REQ_INVITACION", sequenceName = "SICOES_SEQ_REQ_INVITACION", allocationSize = 1)
    @Column(name = "ID_REQ_INVITACION")
    private Long idRequerimientoInvitacion;

    @Column(name = "CO_UUID")
    private String requerimientoInvitacionUuid;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_REQUERIMIENTO")
    private Requerimiento requerimiento;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_ESTADO_LD")
    private ListadoDetalle estado;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_SUPERVISORA")
    private Supervisora supervisora;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_INVITACION")
    private Date fechaInvitacion;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_CADUCIDAD")
    private Date fechaCaducidad;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_ACEPTACION")
    private Date fechaAceptacion;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_RECHAZO")
    private Date fechaRechazo;

    @Column(name = "CA_SALDO_CONTRATO")
    private Long saldoContrato;

    @Column(name="FL_ACTIVO")
    private String flagActivo;

    public Long getIdRequerimientoInvitacion() {
        return idRequerimientoInvitacion;
    }

    public void setIdRequerimientoInvitacion(Long idRequerimientoInvitacion) {
        this.idRequerimientoInvitacion = idRequerimientoInvitacion;
    }

    public String getRequerimientoInvitacionUuid() {
        return requerimientoInvitacionUuid;
    }

    public void setRequerimientoInvitacionUuid(String requerimientoInvitacionUuid) {
        this.requerimientoInvitacionUuid = requerimientoInvitacionUuid;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public ListadoDetalle getEstado() {
        return estado;
    }

    public void setEstado(ListadoDetalle estado) {
        this.estado = estado;
    }

    public Supervisora getSupervisora() {
        return supervisora;
    }

    public void setSupervisora(Supervisora supervisora) {
        this.supervisora = supervisora;
    }

    public Date getFechaInvitacion() {
        return fechaInvitacion;
    }

    public void setFechaInvitacion(Date fechaInvitacion) {
        this.fechaInvitacion = fechaInvitacion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Date getFechaAceptacion() {
        return fechaAceptacion;
    }

    public void setFechaAceptacion(Date fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    public Date getFechaRechazo() {
        return fechaRechazo;
    }

    public void setFechaRechazo(Date fechaRechazo) {
        this.fechaRechazo = fechaRechazo;
    }

    public Long getSaldoContrato() {
        return saldoContrato;
    }

    public void setSaldoContrato(Long saldoContrato) {
        this.saldoContrato = saldoContrato;
    }

    public String getFlagActivo() {
        return flagActivo;
    }

    public void setFlagActivo(String flagActivo) {
        this.flagActivo = flagActivo;
    }
}
