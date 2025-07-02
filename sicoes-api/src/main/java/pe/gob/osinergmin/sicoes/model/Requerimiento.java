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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SICOES_TC_REQUERIMIENTO")
public class Requerimiento extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQUERIMIENTO")
    @SequenceGenerator(name = "GEN_SICOES_SEQ_REQUERIMIENTO", sequenceName = "SICOES_SEQ_REQUERIMIENTO", allocationSize = 1)
    @Column(name = "ID_REQUERIMIENTO")
    private Long idRequerimiento;

    @Column(name = "CO_UUID")
    private String requerimientoUuid;

    @Column(name = "NU_EXPEDIENTE")
    private String nuExpediente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ESTADO_LD")
    private ListadoDetalle estado;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_REGISTRO")
    private Date feRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DIVISION")
    private Division division;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERFIL_LD")
    private ListadoDetalle perfil;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "FE_PLAZO_CARGA_DOC")
    private Date fePlazoCargaDoc;

    @Column(name = "DE_OBSERVACION")
    private String deObservacion;

    @Column(name = "NU_SIAF")
    private String nuSiaf;

    @OneToMany(mappedBy = "requerimiento", fetch = FetchType.LAZY)
    private List<RequerimientoInvitacion> reqInvitaciones;

    public Long getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Long idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public String getRequerimientoUuid() {
        return requerimientoUuid;
    }

    public void setRequerimientoUuid(String requerimientoUuid) {
        this.requerimientoUuid = requerimientoUuid;
    }

    public String getNuExpediente() {
        return nuExpediente;
    }

    public void setNuExpediente(String nuExpediente) {
        this.nuExpediente = nuExpediente;
    }

    public ListadoDetalle getEstado() {
        return estado;
    }

    public void setEstado(ListadoDetalle estado) {
        this.estado = estado;
    }

    public Date getFeRegistro() {
        return feRegistro;
    }

    public void setFeRegistro(Date feRegistro) {
        this.feRegistro = feRegistro;
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

    public Date getFePlazoCargaDoc() {
        return fePlazoCargaDoc;
    }

    public void setFePlazoCargaDoc(Date fePlazoCargaDoc) {
        this.fePlazoCargaDoc = fePlazoCargaDoc;
    }

    public String getDeObservacion() {
        return deObservacion;
    }

    public void setDeObservacion(String deObservacion) {
        this.deObservacion = deObservacion;
    }

    public String getNuSiaf() {
        return nuSiaf;
    }

    public void setNuSiaf(String nuSiaf) {
        this.nuSiaf = nuSiaf;
    }

    public List<RequerimientoInvitacion> getReqInvitaciones() {
        return reqInvitaciones;
    }

    public void setReqInvitaciones(List<RequerimientoInvitacion> reqInvitaciones) {
        this.reqInvitaciones = reqInvitaciones;
    }

}
