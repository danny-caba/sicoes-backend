package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TM_LISTADO")
public class Listado extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_LISTADO")
	@SequenceGenerator(name="GEN_SICOES_SEQ_LISTADO", sequenceName = "SICOES_SEQ_LISTADO", allocationSize = 1)
	@Column(name = "ID_LISTADO")	
	private Long idListado;

	@Column(name="CD_LISTADO")	
	private String codigo;
	
	@Column(name="NO_LISTADO")	
	private String nombre;
	
	@Column(name="DE_LISTADO")	
	private String descripcion;
	
	@Column(name="CO_PADRE")
	private String codigoPadre;


	public Long getIdListado() {
		return idListado;
	}


	public void setIdListado(Long idListado) {
		this.idListado = idListado;
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


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getCodigoPadre() {
		return codigoPadre;
	}


	public void setCodigoPadre(String codigoPadre) {
		this.codigoPadre = codigoPadre;
	}
	
//	@Transient
//	private List detalles;

	
	
	

}