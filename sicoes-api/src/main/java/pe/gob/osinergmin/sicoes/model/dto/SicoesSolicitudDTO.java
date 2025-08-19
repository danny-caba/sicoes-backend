package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SicoesSolicitudDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idSolicitud;

	private PropuestaDTO propuesta;

	private Long idSolicitudPadre;

	private SupervisoraDTO supervisora;

	private String descripcionSolicitud;
	
	private String tipoSolicitud;
	
	private String estadoProcesoSolicitud;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaPlazoInscripcion;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fechaPlazoSubsanacion;

	private String estado;

	private ListadoDetalleDTO tipoContratacion;

	private String numeroExpediente;
	
	private String valorAdjSimplificada;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private Date fechaHoraPresentacion;

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public PropuestaDTO getPropuesta() {
		return propuesta;
	}

	public void setPropuesta(PropuestaDTO propuesta) {
		this.propuesta = propuesta;
	}

	public Long getIdSolicitudPadre() {
		return idSolicitudPadre;
	}

	public void setIdSolicitudPadre(Long idSolicitudPadre) {
		this.idSolicitudPadre = idSolicitudPadre;
	}

	public SupervisoraDTO getSupervisora() {
		return supervisora;
	}

	public void setSupervisora(SupervisoraDTO supervisora) {
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

	public Date getFechaPlazoInscripcion() {
		return fechaPlazoInscripcion;
	}

	public void setFechaPlazoInscripcion(Date fechaPlazoInscripcion) {
		this.fechaPlazoInscripcion = fechaPlazoInscripcion;
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

	public ListadoDetalleDTO getTipoContratacion() {
		return tipoContratacion;
	}

	public void setTipoContratacion(ListadoDetalleDTO tipoContratacion) {
		this.tipoContratacion = tipoContratacion;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public String getValorAdjSimplificada() {
		return valorAdjSimplificada;
	}

	public void setValorAdjSimplificada(String valorAdjSimplificada) {
		this.valorAdjSimplificada = valorAdjSimplificada;
	}

	public Date getFechaHoraPresentacion() {
		return fechaHoraPresentacion;
	}

	public void setFechaHoraPresentacion(Date fechaHoraPresentacion) {
		this.fechaHoraPresentacion = fechaHoraPresentacion;
	}
}