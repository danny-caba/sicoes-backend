package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TM_OPCION")
public class Opcion extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_OPCION")
	@SequenceGenerator(name="GEN_SICOES_SEQ_OPCION", sequenceName = "SICOES_SEQ_OPCION", allocationSize = 1)
	@Column(name = "ID_OPCION")	
	private Long idOpcion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PADRE")	
	private Opcion padre;
	
	@Column(name="NO_OPCION")
	private String nombre;
	
	@Column(name="DE_OPCION")
	private String descripcion;
	
	@Column(name="CO_OPCION")
	private String codigo;
	
	@Column(name="NU_ORDEN")
	private String orden;
	
	@Column(name="FL_VISIBLE")
	private String visible;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")	
	private ListadoDetalle estado;

	public Long getIdOpcion() {
		return idOpcion;
	}

	public void setIdOpcion(Long idOpcion) {
		this.idOpcion = idOpcion;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public Opcion getPadre() {
		return padre;
	}

	public void setPadre(Opcion padre) {
		this.padre = padre;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}
	
}