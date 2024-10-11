package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ReasignacionDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long idUsuarioReasignacion;
	private Long idConfiguracionBandeja;
	private String estadoUsuarioReasignacion;
	private Long idUsuarioReasignado;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate fechaInicio;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate fechaFin;
	
	private Long idUsuarioOrigen;
	
	public Long getIdUsuarioReasignacion() {
		return idUsuarioReasignacion;
	}
	public void setIdUsuarioReasignacion(Long idUsuarioReasignacion) {
		this.idUsuarioReasignacion = idUsuarioReasignacion;
	}
	public Long getIdConfiguracionBandeja() {
		return idConfiguracionBandeja;
	}
	public void setIdConfiguracionBandeja(Long idConfiguracionBandeja) {
		this.idConfiguracionBandeja = idConfiguracionBandeja;
	}
	public String getEstadoUsuarioReasignacion() {
		return estadoUsuarioReasignacion;
	}
	public void setEstadoUsuarioReasignacion(String estadoUsuarioReasignacion) {
		this.estadoUsuarioReasignacion = estadoUsuarioReasignacion;
	}
	public Long getIdUsuarioReasignado() {
		return idUsuarioReasignado;
	}
	public void setIdUsuarioReasignado(Long idUsuarioReasignado) {
		this.idUsuarioReasignado = idUsuarioReasignado;
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
	public Long getIdUsuarioOrigen() {
		return idUsuarioOrigen;
	}
	public void setIdUsuarioOrigen(Long idUsuarioOrigen) {
		this.idUsuarioOrigen = idUsuarioOrigen;
	}
}
