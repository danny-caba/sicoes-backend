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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="SICOES_TC_REQ_DOCUMENTO")
public class RequerimientoDocumento extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQ_DOCUMENTO")
    @SequenceGenerator(name="GEN_SICOES_SEQ_REQ_DOCUMENTO", sequenceName = "SICOES_SEQ_REQ_DOCUMENTO", allocationSize = 1)
    @Column(name = "ID_REQ_DOCUMENTO")
    private Long idRequerimientoDocumento;

    @Column(name = "CO_UUID")
    private String requerimientoDocumentoUuid;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_REQUERIMIENTO")
    private Requerimiento requerimiento;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_ESTADO_LD")
    private ListadoDetalle estado;

    @Column(name="FL_ACTIVO")
    private String flagActivo;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_INGRESO")
    private Date fechaIngreso;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_TIPO_LD")
    private ListadoDetalle tipo;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_PLAZO_ENTREGA")
    private Date fechaplazoEntrega;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_REVISION_LD")
    private ListadoDetalle revision;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_REQ_CONTRATO")
    private RequerimientoContrato contrato;

    @Transient
    private List<RequerimientoDocumentoDetalle> requerimientosDocumentosDetalles;

    @Transient
    private Date fechaInvitacion;

    public Long getIdRequerimientoDocumento() {
        return idRequerimientoDocumento;
    }

    public void setIdRequerimientoDocumento(Long idRequerimientoDocumento) {
        this.idRequerimientoDocumento = idRequerimientoDocumento;
    }

    public String getRequerimientoDocumentoUuid() {
        return requerimientoDocumentoUuid;
    }

    public void setRequerimientoDocumentoUuid(String requerimientoDocumentoUuid) {
        this.requerimientoDocumentoUuid = requerimientoDocumentoUuid;
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

    public String getFlagActivo() {
        return flagActivo;
    }

    public void setFlagActivo(String flagActivo) {
        this.flagActivo = flagActivo;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public ListadoDetalle getTipo() {
        return tipo;
    }

    public void setTipo(ListadoDetalle tipo) {
        this.tipo = tipo;
    }

    public Date getFechaplazoEntrega() {
        return fechaplazoEntrega;
    }

    public void setFechaplazoEntrega(Date fechaplazoEntrega) {
        this.fechaplazoEntrega = fechaplazoEntrega;
    }

    public ListadoDetalle getRevision() {
        return revision;
    }

    public void setRevision(ListadoDetalle revision) {
        this.revision = revision;
    }

    public RequerimientoContrato getContrato() {
        return contrato;
    }

    public void setContrato(RequerimientoContrato contrato) {
        this.contrato = contrato;
    }

    public List<RequerimientoDocumentoDetalle> getRequerimientosDocumentosDetalles() {
        return requerimientosDocumentosDetalles;
    }

    public void setRequerimientosDocumentosDetalles(List<RequerimientoDocumentoDetalle> requerimientosDocumentosDetalles) {
        this.requerimientosDocumentosDetalles = requerimientosDocumentosDetalles;
    }

    public Date getFechaInvitacion() {
        return fechaInvitacion;
    }

    public void setFechaInvitacion(Date fechaInvitacion) {
        this.fechaInvitacion = fechaInvitacion;
    }
}
