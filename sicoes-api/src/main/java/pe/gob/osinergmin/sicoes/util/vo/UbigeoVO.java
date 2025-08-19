package pe.gob.osinergmin.sicoes.util.vo;

import java.io.Serializable;

public class UbigeoVO implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String codigo;
	private String nombre;
	
	private String departamento;
	private String provincia;
	private String distrito;
	
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
	@Override
	public String toString() {
		return "UbigeoVO [codigo=" + codigo + ", nombre=" + nombre + "]";
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public String getNombreCompleto() {
		return departamento+" / "+provincia+" / "+distrito;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
