package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListadoDetallePerfilDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ListadoDetalleDTO sector; 
	private ListadoDetalleDTO subsector; 
	private ListadoDetalleDTO actividad; 
	private ListadoDetalleDTO unidad; 
	private ListadoDetalleDTO subCategoria; 
	private ListadoDetalleDTO perfil;
	
	private String detalle;
	
	public String getDetalle() {
		return sector.getNombre() + " / " + subsector.getNombre() + " / " + actividad.getNombre() + " / " + unidad.getNombre() + " / " + subCategoria.getNombre() + " / " + perfil.getNombre();
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public ListadoDetalleDTO getSector() {
		return sector;
	}
	public void setSector(ListadoDetalleDTO sector) {
		this.sector = sector;
	}
	public ListadoDetalleDTO getSubsector() {
		return subsector;
	}
	public void setSubsector(ListadoDetalleDTO subsector) {
		this.subsector = subsector;
	}
	public ListadoDetalleDTO getActividad() {
		return actividad;
	}
	public void setActividad(ListadoDetalleDTO actividad) {
		this.actividad = actividad;
	}
	public ListadoDetalleDTO getUnidad() {
		return unidad;
	}
	public void setUnidad(ListadoDetalleDTO unidad) {
		this.unidad = unidad;
	}
	public ListadoDetalleDTO getSubCategoria() {
		return subCategoria;
	}
	public void setSubCategoria(ListadoDetalleDTO subCategoria) {
		this.subCategoria = subCategoria;
	}
	public ListadoDetalleDTO getPerfil() {
		return perfil;
	}
	public void setPerfil(ListadoDetalleDTO perfil) {
		this.perfil = perfil;
	} 
	
}
