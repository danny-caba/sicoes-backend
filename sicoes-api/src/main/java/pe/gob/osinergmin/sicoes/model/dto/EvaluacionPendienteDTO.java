package pe.gob.osinergmin.sicoes.model.dto;

import java.sql.Date;

public class EvaluacionPendienteDTO {
	
	private Long idSolicitud;
	
	private Date fechaIngreso;//
	
	private String numeroExpediente;//
	
	private String perfil;//
	
	private Date fechaAsignacion;//
	
	private Long idDivision;
	
	private String divisionGerencia;
	
	private Long idUsuario;//
	
	private String nombreUsuario;//
	
	private String correoUsuario;//
	
	private Long diasPendientes;
	
	private Long idTipo;
	
	private String tipo;

	
	public Long getIdSolicitud() {
		return idSolicitud;
	}


	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}


	public Date getFechaIngreso() {
		return fechaIngreso;
	}


	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}


	public String getNumeroExpediente() {
		return numeroExpediente;
	}


	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}


	public String getPerfil() {
		return perfil;
	}


	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}


	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}


	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
	

	public Long getIdDivision() {
		return idDivision;
	}


	public void setIdDivision(Long idDivision) {
		this.idDivision = idDivision;
	}


	public String getDivisionGerencia() {
		return divisionGerencia;
	}


	public void setDivisionGerencia(String divisionGerencia) {
		this.divisionGerencia = divisionGerencia;
	}


	public Long getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}


	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	public String getCorreoUsuario() {
		return correoUsuario;
	}


	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}


	public Long getDiasPendientes() {
		return diasPendientes;
	}


	public void setDiasPendientes(Long diasPendientes) {
		this.diasPendientes = diasPendientes;
	}

	
	public Long getIdTipo() {
		return idTipo;
	}


	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	@Override
	public String toString() {
		return "EvaluacionPendienteDTO [idSolicitud=" + idSolicitud + ", fechaIngreso=" + fechaIngreso
				+ ", numeroExpediente=" + numeroExpediente + ", perfil=" + perfil + ", fechaAsignacion="
				+ fechaAsignacion + ", idDivision=" + idDivision + ", divisionGerencia=" + divisionGerencia
				+ ", idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", correoUsuario=" + correoUsuario
				+ ", diasPendientes=" + diasPendientes + ", idTipo=" + idTipo + ", tipo=" + tipo + "]";
	}
}
