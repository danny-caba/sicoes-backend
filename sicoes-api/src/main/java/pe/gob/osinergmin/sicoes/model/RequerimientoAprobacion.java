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
@Table(name="SICOES_TC_REQ_APROBACION")
public class RequerimientoAprobacion extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_REQ_APROBACION")
    @SequenceGenerator(name="GEN_SICOES_SEQ_REQ_APROBACION", sequenceName = "SICOES_SEQ_REQ_APROBACION", allocationSize = 1)
    @Column(name = "ID_REQ_APROBACION")
    private Long idRequerimientoAprobacion;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_REQUERIMIENTO")
    private Requerimiento requerimiento;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_TIPO_LD")
    private ListadoDetalle tipo;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_GRUPO_LD")
    private ListadoDetalle grupo;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ID_USUARIO")
    private Usuario usuario;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_ESTADO_LD")
    private ListadoDetalle estado;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="FL_FIRMADO_LD")
    private ListadoDetalle flagFirmado;

    @Column(name = "DE_OBSERVACION")
    private String observacion;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    @Column(name="FE_APROBACION")
    private Date fechaAprobacion;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_RECHAZO")
    private Date fechaRechazo;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="FE_FIRMA")
    private Date fechaFirma;

    public Long getIdRequerimientoAprobacion() {
        return idRequerimientoAprobacion;
    }

    public void setIdRequerimientoAprobacion(Long idRequerimientoAprobacion) {
        this.idRequerimientoAprobacion = idRequerimientoAprobacion;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public ListadoDetalle getTipo() {
        return tipo;
    }

    public void setTipo(ListadoDetalle tipo) {
        this.tipo = tipo;
    }

    public ListadoDetalle getGrupo() {
        return grupo;
    }

    public void setGrupo(ListadoDetalle grupo) {
        this.grupo = grupo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ListadoDetalle getEstado() {
        return estado;
    }

    public void setEstado(ListadoDetalle estado) {
        this.estado = estado;
    }

    public ListadoDetalle getFlagFirmado() {
        return flagFirmado;
    }

    public void setFlagFirmado(ListadoDetalle flagFirmado) {
        this.flagFirmado = flagFirmado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public Date getFechaRechazo() {
        return fechaRechazo;
    }

    public void setFechaRechazo(Date fechaRechazo) {
        this.fechaRechazo = fechaRechazo;
    }

    public Date getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(Date fechaFirma) {
        this.fechaFirma = fechaFirma;
    }
}
