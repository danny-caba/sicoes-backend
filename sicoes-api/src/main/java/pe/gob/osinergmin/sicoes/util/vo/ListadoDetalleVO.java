package pe.gob.osinergmin.sicoes.util.vo;

import pe.gob.osinergmin.sicoes.model.BaseModel;

public class ListadoDetalleVO extends  BaseModel{


	private Long idListadoDetalle;
	
	private String codigo;

	private String descripcion;

	
	private String nombre;

	private Long orden;

	private String valor;

	private Long idListado;
	
	private Long idListadoSuperior;
	
	private Long idListadoPadre;

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Long getIdListado() {
		return idListado;
	}

	public void setIdListado(Long idListado) {
		this.idListado = idListado;
	}

	public Long getIdListadoSuperior() {
		return idListadoSuperior;
	}

	public void setIdListadoSuperior(Long idListadoSuperior) {
		this.idListadoSuperior = idListadoSuperior;
	}

	public Long getIdListadoPadre() {
		return idListadoPadre;
	}

	public void setIdListadoPadre(Long idListadoPadre) {
		this.idListadoPadre = idListadoPadre;
	}

	
	
	
	
}
