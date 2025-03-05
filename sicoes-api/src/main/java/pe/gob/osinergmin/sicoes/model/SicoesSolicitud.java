package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import pe.gob.osinergmin.sicoes.model.dto.EvaluacionPerfDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="SICOES_TC_SOLI_PERF_CONT")
public class SicoesSolicitud extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_TC_SOLI_PERF_CONT")
	@SequenceGenerator(name="GEN_SICOES_SEQ_TC_SOLI_PERF_CONT", sequenceName = "SICOES_SEQ_TC_SOLI_PERF_CONT", allocationSize = 1)
	@Column(name = "ID_SOLI_PERF_CONT")	
	private Long idSolicitud;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROPUESTA")
	private Propuesta propuesta;
	
	@Column(name="ID_SOLI_PERF_CONT_PADRE")
	private Long idSolicitudPadre;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUPERVISORA")
	private Supervisora supervisora;
	
	@Column(name="DE_SOLICITUD")
	private String descripcionSolicitud;
	
	@Column(name="TI_SOLICITUD")
	private String tipoSolicitud;
	
	@Column(name="ES_PROC_SOLICITUD")
	private String estadoProcesoSolicitud;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_PLAZO_INSCRIPCION")
	private Date fechaPlazoInscripcion;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_PLAZO_SUBSANACION")
	private Date fechaPlazoSubsanacion;
	
	@Column(name="ES_SOLI_PERF_CONT")
	private String estado;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_CONTRATACION_LD")
	private ListadoDetalle tipoContratacion;

	@Column(name="NU_EXPEDIENTE")
	private String numeroExpediente;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="FE_PRESENTACION")
	private Date fechaHoraPresentacion;

	@Transient
	private List<SicoesSolicitudSeccion> requisitos;

	@Transient
	private List<EvaluacionPerfDTO> seccionDoc;

	@Transient
	private List<EvaluacionPerfDTO> seccionPer;

	@Transient
	private List<EvaluacionPerfDTO> seccionFiel;

	@Transient
	private List<EvaluacionPerfDTO> seccionMonto;

	@Transient
	private List<SicoesTdSolPerConSec> secciones;

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Propuesta getPropuesta() {
		return propuesta;
	}

	public void setPropuesta(Propuesta propuesta) {
		this.propuesta = propuesta;
	}

	public Long getIdSolicitudPadre() {
		return idSolicitudPadre;
	}

	public void setIdSolicitudPadre(Long idSolicitudPadre) {
		this.idSolicitudPadre = idSolicitudPadre;
	}

	public Supervisora getSupervisora() {
		return supervisora;
	}

	public void setSupervisora(Supervisora supervisora) {
		this.supervisora = supervisora;
	}

	public String getDescripcionSolicitud() {
		return descripcionSolicitud;
	}

	public void setDescripcionSolicitud(String descripcionSolicitud) {
		this.descripcionSolicitud = descripcionSolicitud;
	}

	public String getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public String getEstadoProcesoSolicitud() {
		return estadoProcesoSolicitud;
	}

	public void setEstadoProcesoSolicitud(String estadoProcesoSolicitud) {
		this.estadoProcesoSolicitud = estadoProcesoSolicitud;
	}

	public Date getFechaPlazoSubsanacion() {
		return fechaPlazoSubsanacion;
	}

	public void setFechaPlazoSubsanacion(Date fechaPlazoSubsanacion) {
		this.fechaPlazoSubsanacion = fechaPlazoSubsanacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ListadoDetalle getTipoContratacion() {
		return tipoContratacion;
	}

	public void setTipoContratacion(ListadoDetalle tipoContratacion) {
		this.tipoContratacion = tipoContratacion;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public List<SicoesSolicitudSeccion> getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(List<SicoesSolicitudSeccion> requisitos) {
		this.requisitos = requisitos;
	}

	public Date getFechaHoraPresentacion() {
		return fechaHoraPresentacion;
	}

	public void setFechaHoraPresentacion(Date fechaHoraPresentacion) {
		this.fechaHoraPresentacion = fechaHoraPresentacion;
	}

	public List<EvaluacionPerfDTO> getSeccionDoc() {
		return seccionDoc;
	}

	public void setSeccionDoc(List<EvaluacionPerfDTO> seccionDoc) {
		this.seccionDoc = seccionDoc;
	}

	public List<EvaluacionPerfDTO> getSeccionPer() {
		return seccionPer;
	}

	public void setSeccionPer(List<EvaluacionPerfDTO> seccionPer) {
		this.seccionPer = seccionPer;
	}

	public List<EvaluacionPerfDTO> getSeccionFiel() {
		return seccionFiel;
	}

	public void setSeccionFiel(List<EvaluacionPerfDTO> seccionFiel) {
		this.seccionFiel = seccionFiel;
	}

	public List<EvaluacionPerfDTO> getSeccionMonto() {
		return seccionMonto;
	}

	public void setSeccionMonto(List<EvaluacionPerfDTO> seccionMonto) {
		this.seccionMonto = seccionMonto;
	}

	public List<SicoesTdSolPerConSec> getSecciones() {
		return secciones;
	}

	public void setSecciones(List<SicoesTdSolPerConSec> secciones) {
		this.secciones = secciones;
	}

	public Date getFechaPlazoInscripcion() {
		return fechaPlazoInscripcion;
	}

	public void setFechaPlazoInscripcion(Date fechaPlazoInscripcion) {
		this.fechaPlazoInscripcion = fechaPlazoInscripcion;
	}
}
