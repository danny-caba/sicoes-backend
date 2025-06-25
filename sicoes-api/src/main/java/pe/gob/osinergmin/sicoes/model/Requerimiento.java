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
@Table(name="SICOES_TC_REQUERIMIENTO")
public class Requerimiento extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQUERIMIENTO")
    @SequenceGenerator(name="GEN_SICOES_SEQ_REQUERIMIENTO", sequenceName = "SICOES_SEQ_REQUERIMIENTO", allocationSize = 1)
    @Column(name = "ID_REQUERIMIENTO")
    private Long idRequerimiento;

    @Column(name="NU_EXPEDIENTE")
    private String numeroExpediente;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_ESTADO_LD")
    private ListadoDetalle estado;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_REGISTRO")
    private Date fechaRegistro;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_DIVISION")
    private Division division;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_PERFIL_LD")
    private ListadoDetalle perfil;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_REGISTRO")
    private Date fechaPlazoCargaDoc;

    @Column(name = "DE_OBSERVACION")
    private String observacion;

    @Column(name = "NU_SIAF")
    private String nuSiaf;

    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    public ListadoDetalle getEstado() {
        return estado;
    }

    public void setEstado(ListadoDetalle estado) {
        this.estado = estado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public ListadoDetalle getPerfil() {
        return perfil;
    }

    public void setPerfil(ListadoDetalle perfil) {
        this.perfil = perfil;
    }

    public Date getFechaPlazoCargaDoc() {
        return fechaPlazoCargaDoc;
    }

    public void setFechaPlazoCargaDoc(Date fechaPlazoCargaDoc) {
        this.fechaPlazoCargaDoc = fechaPlazoCargaDoc;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNuSiaf() {
        return nuSiaf;
    }

    public void setNuSiaf(String nuSiaf) {
        this.nuSiaf = nuSiaf;
    }
}
