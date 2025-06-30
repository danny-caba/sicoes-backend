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
@Table(name="SICOES_TD_REQ_DOCUMENTO")
public class RequerimientoDocumentoDetalle extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQ_DET_DOCUMENTO")
    @SequenceGenerator(name="GEN_SICOES_SEQ_REQ_DET_DOCUMENTO", sequenceName = "SICOES_SEQ_REQ_DET_DOCUMENTO", allocationSize = 1)
    @Column(name = "ID_REQ_DET_DOCUMENTO")
    private Long idRequerimientoDocumentoDetalle;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_REQ_DOCUMENTO")
    private RequerimientoDocumento requerimientoDocumento;

    @Column(name="DE_REQUISITO")
    private String descripcionRequisito;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_REQUISITO_LD")
    private ListadoDetalle requisito;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_EVALUACION_LD")
    private ListadoDetalle evaluacion;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="US_EVALUACION")
    private Usuario usuario;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_EVALUACION")
    private Date fechaEvaluacion;

    @Column(name="DE_OBSERVACION")
    private String observacion;

    public Long getIdRequerimientoDocumentoDetalle() {
        return idRequerimientoDocumentoDetalle;
    }

    public void setIdRequerimientoDocumentoDetalle(Long idRequerimientoDocumentoDetalle) {
        this.idRequerimientoDocumentoDetalle = idRequerimientoDocumentoDetalle;
    }

    public RequerimientoDocumento getRequerimientoDocumento() {
        return requerimientoDocumento;
    }

    public void setRequerimientoDocumento(RequerimientoDocumento requerimientoDocumento) {
        this.requerimientoDocumento = requerimientoDocumento;
    }

    public String getDescripcionRequisito() {
        return descripcionRequisito;
    }

    public void setDescripcionRequisito(String descripcionRequisito) {
        this.descripcionRequisito = descripcionRequisito;
    }

    public ListadoDetalle getRequisito() {
        return requisito;
    }

    public void setRequisito(ListadoDetalle requisito) {
        this.requisito = requisito;
    }

    public ListadoDetalle getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(ListadoDetalle evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(Date fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
