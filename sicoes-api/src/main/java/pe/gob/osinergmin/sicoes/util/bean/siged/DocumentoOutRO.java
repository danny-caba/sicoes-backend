package pe.gob.osinergmin.sicoes.util.bean.siged;

import java.io.Serializable;

import gob.osinergmin.siged.remote.rest.ro.base.BaseOutRO;

public class DocumentoOutRO extends BaseOutRO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String numeroDocumento;
	private String tipoDocumento;
	private String autor;
	private String asunto;
	private String fechaCreacion;
	private String nombre;
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "DocumentoOutRO [numeroDocumento=" + numeroDocumento + ", tipoDocumento=" + tipoDocumento + ", autor="
				+ autor + ", asunto=" + asunto + ", fechaCreacion=" + fechaCreacion + ", nombre=" + nombre + "]";
	}
	
	
	
}
