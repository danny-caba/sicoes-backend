package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.Transient;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TM_ROL")
public class Rol extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_ROL")
	@SequenceGenerator(name="GEN_SICOES_SEQ_ROL", sequenceName = "SICOES_SEQ_ROL", allocationSize = 1)
	@Column(name = "ID_ROL")	
	private Long idRol;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")	
	private ListadoDetalle estado;
	
	@Column(name="NO_ROL")
	private String nombre;
	
	@Column(name="DE_ROL")
	private String descripcion;
	
	@Column(name="CO_ROL")
	private String codigo;
	
	@Transient
	private List<Opcion> opciones;

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
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

	public List<Opcion> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<Opcion> opciones) {
		this.opciones = opciones;
	}
	
}