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
@Table(name="SICOES_TC_REQ_CONTRATO")
public class RequerimientoContrato extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQ_CONTRATO")
    @SequenceGenerator(name="GEN_SICOES_SEQ_REQ_CONTRATO", sequenceName = "SICOES_SEQ_REQ_CONTRATO", allocationSize = 1)
    @Column(name = "ID_REQUERIMIENTO")
    private Long idRequerimiento;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_REQUERIMIENTO")
    private Requerimiento requerimiento;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_REQ_DOCUMENTO")
    private RequerimientoDocumento requerimientoDocumento;

    @Column(name="NU_CONTRATO")
    private String numeroContrato;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_SUSCRIPCION")
    private Date fechaSuscripcion;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_INICIO")
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_FIN")
    private Date fechaFin;

    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public RequerimientoDocumento getRequerimientoDocumento() {
        return requerimientoDocumento;
    }

    public void setRequerimientoDocumento(RequerimientoDocumento requerimientoDocumento) {
        this.requerimientoDocumento = requerimientoDocumento;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public Date getFechaSuscripcion() {
        return fechaSuscripcion;
    }

    public void setFechaSuscripcion(Date fechaSuscripcion) {
        this.fechaSuscripcion = fechaSuscripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
