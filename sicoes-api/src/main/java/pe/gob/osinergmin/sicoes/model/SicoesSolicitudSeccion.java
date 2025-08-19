package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="SICOES_TD_SOLI_PERF_CONT")
public class SicoesSolicitudSeccion extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_SOLICITUD_SECCION")
	@SequenceGenerator(name="GEN_SICOES_SEQ_SOLICITUD_SECCION", sequenceName = "SICOES_SEQ_SOLICITUD_SECCION", allocationSize = 1)
	@Column(name = "ID_DET_SOLI_PERF_CONT")	
	private Long idSolicitudSeccion;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECCION_REQUISITO")
	private Requisito requisito;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SOLI_PERS_PROP")
	private SicoesTdSoliPersProp personalPropuesto;
	
	@Column(name="ID_SOL_PER_CON_SEC")
	private Long idPerConSec;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO_EVAL")
	private Usuario usuarioEvaluacion;
	
	@Column(name="FL_PROC_SUBSANACION")
	private String procSubsanacion;
	
	@Column(name="NU_VALOR")
	private Double valor;
	
	@Column(name="DE_TEXTO")
	private String texto;
	
	@Column(name="ES_PROC_REVISION")
	private String procRevision;

	@Lob
	@Column(name="DE_EVALUACION")
	private String evaluacion;
	
	@Column(name="ES_DET_SOLI_PERF_CONT")
	private String estado;
	
	@Column(name="DE_REQUISITO")
	private String deRequisito;

	@Column(name="FL_REQUISITO_PERF_CONT")
	private String flagRequisito;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Lima")
	@Column(name="FE_EVALUACION")
	private Date fechaEvaluacion;

	@Transient
	private Archivo archivo;

	@Transient
	private String nombreArchivo;

	@Transient
	private Long nroFolio;

	@Transient
	private Long peso;

	public Long getIdSolicitudSeccion() {
		return idSolicitudSeccion;
	}

	public void setIdSolicitudSeccion(Long idSolicitudSeccion) {
		this.idSolicitudSeccion = idSolicitudSeccion;
	}

	public Requisito getRequisito() {
		return requisito;
	}

	public void setRequisito(Requisito requisito) {
		this.requisito = requisito;
	}

	public String getDeRequisito() {
		return deRequisito;
	}

	public void setDeRequisito(String deRequisito) {
		this.deRequisito = deRequisito;
	}

	public SicoesTdSoliPersProp getPersonalPropuesto() {
		return personalPropuesto;
	}

	public void setPersonalPropuesto(SicoesTdSoliPersProp personalPropuesto) {
		this.personalPropuesto = personalPropuesto;
	}

	public Long getIdPerConSec() {
		return idPerConSec;
	}

	public void setIdPerConSec(Long idPerConSec) {
		this.idPerConSec = idPerConSec;
	}

	public Usuario getUsuarioEvaluacion() {
		return usuarioEvaluacion;
	}

	public void setUsuarioEvaluacion(Usuario usuarioEvaluacion) {
		this.usuarioEvaluacion = usuarioEvaluacion;
	}

	public String getProcSubsanacion() {
		return procSubsanacion;
	}

	public void setProcSubsanacion(String procSubsanacion) {
		this.procSubsanacion = procSubsanacion;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getProcRevision() {
		return procRevision;
	}

	public void setProcRevision(String procRevision) {
		this.procRevision = procRevision;
	}

	public String getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFlagRequisito() {
		return flagRequisito;
	}

	public void setFlagRequisito(String flagRequisito) {
		this.flagRequisito = flagRequisito;
	}

	public Date getFechaEvaluacion() {
		return fechaEvaluacion;
	}

	public void setFechaEvaluacion(Date fechaEvaluacion) {
		this.fechaEvaluacion = fechaEvaluacion;
	}

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Long getNroFolio() {
		return nroFolio;
	}

	public void setNroFolio(Long nroFolio) {
		this.nroFolio = nroFolio;
	}

	public Long getPeso() {
		return peso;
	}

	public void setPeso(Long peso) {
		this.peso = peso;
	}

}
