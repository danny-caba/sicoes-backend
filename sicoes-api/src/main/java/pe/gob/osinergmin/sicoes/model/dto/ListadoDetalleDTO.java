package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListadoDetalleDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long idListadoDetalle;
	private String codigo;
	private String nombre;
	public Long getIdListadoDetalle() {
		return idListadoDetalle;
	}
	public void setIdListadoDetalle(Long idListadoDetalle) {
		this.idListadoDetalle = idListadoDetalle;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
