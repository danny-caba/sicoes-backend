package pe.gob.osinergmin.sicoes.util.vo;

import java.io.Serializable;
import java.util.List;

public class Agrupador implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long idTipo;
	private String nombre;
	private Long total;
	private List<Agrupador> detalles;
	
	
	public Agrupador() {
		
	}
	
	public Agrupador(Long idTipo,String nombre, Long total) {
		super();
		this.idTipo=idTipo;
		this.nombre = nombre;
		this.total = total;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "Agrupador [nombre=" + nombre + ", total=" + total + "]";
	}
	public List<Agrupador> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<Agrupador> detalles) {
		this.detalles = detalles;
	}

	public Long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}
	
	
}
