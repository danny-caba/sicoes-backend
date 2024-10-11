package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the listado_detalle database table.
 * 
 */
@Entity
@Table(name="SICOES_TM_LISTADO_DETALLE")
public class ListadoDetalle extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_LISTADO_DETALLE")
	@SequenceGenerator(name="GEN_SICOES_SEQ_LISTADO_DETALLE", sequenceName = "SICOES_SEQ_LISTADO_DETALLE", allocationSize = 1)
	@Column(name = "ID_LISTADO_DETALLE")		
	private Long idListadoDetalle;
	
	@Column(name="ID_LISTADO")
	private Long idListado;
	
	@Column(name="ID_LISTADO_PADRE")
	private Long idListadoPadre;
	
	@Column(name="ID_SUPERIOR_LD")
	private Long idListadoSuperior;
	
	@Column(name="CO_LISTADO_DETALLE")
	private String codigo;
	
	@Column(name="NU_ORDEN")
	private Long orden;
	
	@Column(name="NO_LISTADO_DETALLE")
	private String nombre;
	
	@Column(name="DE_LISTADO_DETALLE")
	private String descripcion;
	
	@Column(name="DE_VALOR")
	private String valor;
	
	@Transient
	private Listado listado;
	
	public ListadoDetalle() {
	}
	
	public ListadoDetalle(ListadoDetalle ld,Listado l) {
		this.idListadoDetalle 	= ld.getIdListadoDetalle();
		this.codigo 			= ld.getCodigo();
		this.descripcion 		= ld.getDescripcion();
		this.nombre 			= ld.getNombre();
		this.orden 				= ld.getOrden();
		this.valor 				= ld.getValor();
		this.idListado 			= ld.getIdListado();
		this.idListadoPadre 	= ld.getIdListadoPadre();
		this.idListadoSuperior 	= ld.getIdListadoSuperior();
		this.listado=l;
	}
	

	public Long getIdListadoDetalle() {
		return idListadoDetalle;
	}

	public void setIdListadoDetalle(Long idListadoDetalle) {
		this.idListadoDetalle = idListadoDetalle;
	}

	public Long getIdListado() {
		return idListado;
	}

	public void setIdListado(Long idListado) {
		this.idListado = idListado;
	}

	public Long getIdListadoPadre() {
		return idListadoPadre;
	}

	public void setIdListadoPadre(Long idListadoPadre) {
		this.idListadoPadre = idListadoPadre;
	}


	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Long getIdListadoSuperior() {
		return idListadoSuperior;
	}

	public void setIdListadoSuperior(Long idListadoSuperior) {
		this.idListadoSuperior = idListadoSuperior;
	}

	public Listado getListado() {
		return listado;
	}

	public void setListado(Listado listado) {
		this.listado = listado;
	}

	@Override
	public String toString() {
		return "ListadoDetalle [idListadoDetalle=" + idListadoDetalle + ", idListado=" + idListado + ", idListadoPadre="
				+ idListadoPadre + ", idListadoSuperior=" + idListadoSuperior + ", codigo=" + codigo + ", orden="
				+ orden + ", nombre=" + nombre + ", descripcion=" + descripcion + ", valor=" + valor + ", listado="
				+ listado + "]";
	}

	
	
}