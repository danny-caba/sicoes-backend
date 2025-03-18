package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@Table(name="SICOES_TR_ESTUDIO")
public class Estudio extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_ESTUDIO")
	@SequenceGenerator(name="GEN_SICOES_SEQ_ESTUDIO", sequenceName = "SICOES_SEQ_ESTUDIO", allocationSize = 1)
	@Column(name = "ID_ESTUDIO")	
	private Long idEstudio;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_ESTUDIO_LD")	
	private ListadoDetalle tipoEstudio;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_LD")	
	private ListadoDetalle tipo;
	
	@Column(name="FL_EGRESADO")	
	private String flagEgresado;
	
	@Column(name="DE_COLEGIATURA")	
	private String colegiatura;
	
	@Column(name="DE_ESPECIALIDAD")	
	private String especialidad;
	
	@Column(name="DE_DETALLE_TIPO")	
	private String detalleTipo;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_VIGENCIA_GRADO")
	private Date fechaVigenciaGrado;
	
	@Column(name="NO_INSTITUCION")	
	private String institucion;
	
	@Column(name="FL_COLEGIATURA")	
	private String flagColegiatura;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_VIGENCIA_COLEGIATURA")
	private Date fechaVigenciaColegiatura;
	
	@Column(name="NO_INSTITUCION_COLEGIATURA")	
	private String institucionColegiatura;
	
	@Column(name="NU_HORA")	
	private Long hora;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_VIGENCIA")
	private Date fechaVigencia;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_INICIO")
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_FIN")
	private Date fechaFin;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_EVALUACION_LD")	
	private ListadoDetalle evaluacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SOLICITUD")
	private Solicitud solicitud;
	
	@Column(name="ID_ESTUDIO_PADRE")
	private Long idEstudioPadre;
	
	@Column(name="DE_OBSERVACION")
	private String observacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_FUENTE_LD")	
	private ListadoDetalle fuente;
	
	@Column(name="FL_SIGED")	
	private Long flagSiged;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")
	private ListadoDetalle estado;

	@Transient
	private List<Archivo> archivos;

	public Long getIdEstudio() {
		return idEstudio;
	}

	public void setIdEstudio(Long idEstudio) {
		this.idEstudio = idEstudio;
	}

	public ListadoDetalle getTipoEstudio() {
		return tipoEstudio;
	}

	public void setTipoEstudio(ListadoDetalle tipoEstudio) {
		this.tipoEstudio = tipoEstudio;
	}

	public ListadoDetalle getTipo() {
		return tipo;
	}

	public void setTipo(ListadoDetalle tipo) {
		this.tipo = tipo;
	}

	public String getFlagEgresado() {
		return flagEgresado;
	}

	public void setFlagEgresado(String flagEgresado) {
		this.flagEgresado = flagEgresado;
	}

	public String getColegiatura() {
		return colegiatura;
	}

	public void setColegiatura(String colegiatura) {
		this.colegiatura = colegiatura;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getDetalleTipo() {
		return detalleTipo;
	}

	public void setDetalleTipo(String detalleTipo) {
		this.detalleTipo = detalleTipo;
	}

	public Date getFechaVigenciaGrado() {
		return fechaVigenciaGrado;
	}

	public void setFechaVigenciaGrado(Date fechaVigenciaGrado) {
		this.fechaVigenciaGrado = fechaVigenciaGrado;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getFlagColegiatura() {
		return flagColegiatura;
	}

	public void setFlagColegiatura(String flagColegiatura) {
		this.flagColegiatura = flagColegiatura;
	}

	public Date getFechaVigenciaColegiatura() {
		return fechaVigenciaColegiatura;
	}

	public void setFechaVigenciaColegiatura(Date fechaVigenciaColegiatura) {
		this.fechaVigenciaColegiatura = fechaVigenciaColegiatura;
	}

	public String getInstitucionColegiatura() {
		return institucionColegiatura;
	}

	public void setInstitucionColegiatura(String institucionColegiatura) {
		this.institucionColegiatura = institucionColegiatura;
	}

	public Long getHora() {
		return hora;
	}

	public void setHora(Long hora) {
		this.hora = hora;
	}

	public Date getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
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

	public ListadoDetalle getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(ListadoDetalle evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public Long getIdEstudioPadre() {
		return idEstudioPadre;
	}

	public void setIdEstudioPadre(Long idEstudioPadre) {
		this.idEstudioPadre = idEstudioPadre;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public ListadoDetalle getFuente() {
		return fuente;
	}

	public void setFuente(ListadoDetalle fuente) {
		this.fuente = fuente;
	}

	public Long getFlagSiged() {
		return flagSiged;
	}

	public void setFlagSiged(Long flagSiged) {
		this.flagSiged = flagSiged;
	}

	public List<Archivo> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<Archivo> archivos) {
		this.archivos = archivos;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}
}