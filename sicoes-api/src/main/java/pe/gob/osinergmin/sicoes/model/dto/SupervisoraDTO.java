package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SupervisoraDTO {
    private Long idSupervisora;
    private String pais;
    private String numeroExpediente;
    private String tipoPersona;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombreRazonSocial;
    private String estado;
    private Date fechaIngreso;
    private List<DetalleDTO> detalles;
    
	public Long getIdSupervisora() {
		return idSupervisora;
	}
	public void setIdSupervisora(Long idSupervisora) {
		this.idSupervisora = idSupervisora;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getNumeroExpediente() {
		return numeroExpediente;
	}
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Date fechaCreacion) {
		this.fechaIngreso = fechaCreacion;
	}
	public List<DetalleDTO> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetalleDTO> detalles) {
		this.detalles = detalles;
	}
}
