package pe.gob.osinergmin.sicoes.model;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TR_ASIGNACION")
public class Asignacion extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_ASIGNACION")
	@SequenceGenerator(name="GEN_SICOES_SEQ_ASIGNACION", sequenceName = "SICOES_SEQ_ASIGNACION", allocationSize = 1)
	@Column(name = "ID_ASIGNACION")	
	private Long idAsignacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SOLICITUD")
	private Solicitud solicitud;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_LD")	
	private ListadoDetalle tipo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_GRUPO_LD")	
	private ListadoDetalle grupo;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_PLAZO_RESP")
	private Date fechaPlazoResp;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="FE_REGISTRO")
	private Date fechaRegistro;

	@Column(name="NU_DIA_PLAZO_RESP")	
	private Long numeroPlazoResp;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_EVALUACION_LD")	
	private ListadoDetalle evaluacion;
		
	@Column(name="DE_OBSERVACION")
	private String observacion;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="FE_APROBACION")
	private Date fechaAprobacion;
	
	@Column(name="FL_ACTIVO")	
	private Long flagActivo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_OTRO_REQUISITO")
	private OtroRequisito otroRequisito;
	
	@Column(name="ID_USUARIO_ORIGEN")
	private Long idUsuarioOrigen;
	
	@Transient
	private ConfiguracionBandeja configuracionBandeja;

	public Long getIdAsignacion() {
		return idAsignacion;
	}

	public void setIdAsignacion(Long idAsignacion) {
		this.idAsignacion = idAsignacion;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public ListadoDetalle getTipo() {
		return tipo;
	}

	public void setTipo(ListadoDetalle tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ListadoDetalle getGrupo() {
		return grupo;
	}

	public void setGrupo(ListadoDetalle grupo) {
		this.grupo = grupo;
	}

	public Date getFechaPlazoResp() {
		return fechaPlazoResp;
	}

	public void setFechaPlazoResp(Date fechaPlazoResp) {
		this.fechaPlazoResp = fechaPlazoResp;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getNumeroPlazoResp() {
		return numeroPlazoResp;
	}

	public void setNumeroPlazoResp(Long numeroPlazoResp) {
		this.numeroPlazoResp = numeroPlazoResp;
	}

	public ListadoDetalle getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(ListadoDetalle evaluacion) {
		this.evaluacion = evaluacion;
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

	@Override
	public String toString() {
		return "Asignacion [idAsignacion=" + idAsignacion + ", solicitud=" + solicitud + ", tipo=" + tipo + ", usuario="
				+ usuario + ", grupo=" + grupo + ", fechaPlazoResp=" + fechaPlazoResp + ", fechaRegistro="
				+ fechaRegistro + ", numeroPlazoResp=" + numeroPlazoResp + ", evaluacion=" + evaluacion
				+ ", observacion=" + observacion + ", fechaAprobacion=" + fechaAprobacion + "]";
	}

	public Long getFlagActivo() {
		return flagActivo;
	}

	public void setFlagActivo(Long flagActivo) {
		this.flagActivo = flagActivo;
	}

	public OtroRequisito getOtroRequisito() {
		return otroRequisito;
	}

	public void setOtroRequisito(OtroRequisito otroRequisito) {
		this.otroRequisito = otroRequisito;
	}
	
	public Long getIdUsuarioOrigen() {
		return idUsuarioOrigen;
	}

	public void setIdUsuarioOrigen(Long idUsuarioOrigen) {
		this.idUsuarioOrigen = idUsuarioOrigen;
	}

	public ConfiguracionBandeja getConfiguracionBandeja() {
		return configuracionBandeja;
	}

	public void setConfiguracionBandeja(ConfiguracionBandeja configuracionBandeja) {
		this.configuracionBandeja = configuracionBandeja;
	}
}