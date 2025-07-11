package pe.gob.osinergmin.sicoes.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.io.Serializable;
import java.util.Arrays;

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
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "SICOES_TC_REQ_INICIO_SERVICIO")
public class ReqInicioServicio {

	@Id
	@Column(name = "ID_REQ_INICIO_SERVICIO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_req")
	@SequenceGenerator(name = "seq_req", sequenceName = "seq_req_inicio_servicio", allocationSize = 1)
	private Long id;

	@Column(name = "ID_SOLI_PERF_CONT", nullable = false)
	private Long solicitudPerfilId;

	@Column(name = "ID_SUPERVISORA")
	private Long supervisoraId;

	@Column(name = "DE_TIPO_DOCUMENTO", length = 50)
	private String tipoDocumento;

	@Column(name = "ID_ARCHIVO")
	private Long archivoId;

	@Column(name = "FE_INICIO")
	private LocalDate fechaInicio;

	@Column(name = "FE_FIN")
	private LocalDate fechaFin;

	@Column(name = "DE_EVALUACION", length = 20)
	private String estadoEvaluacion;

	@Column(name = "ID_USUARIO")
	private Long usuarioId;

	@Column(name = "FE_EVALUACION")
	private OffsetDateTime fechaEvaluacion;

	@Column(name = "DE_OBSERVACION", length = 100)
	private String observacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSolicitudPerfilId() {
		return solicitudPerfilId;
	}

	public void setSolicitudPerfilId(Long solicitudPerfilId) {
		this.solicitudPerfilId = solicitudPerfilId;
	}

	public Long getSupervisoraId() {
		return supervisoraId;
	}

	public void setSupervisoraId(Long supervisoraId) {
		this.supervisoraId = supervisoraId;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getArchivoId() {
		return archivoId;
	}

	public void setArchivoId(Long archivoId) {
		this.archivoId = archivoId;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstadoEvaluacion() {
		return estadoEvaluacion;
	}

	public void setEstadoEvaluacion(String estadoEvaluacion) {
		this.estadoEvaluacion = estadoEvaluacion;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public OffsetDateTime getFechaEvaluacion() {
		return fechaEvaluacion;
	}

	public void setFechaEvaluacion(OffsetDateTime fechaEvaluacion) {
		this.fechaEvaluacion = fechaEvaluacion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

}
