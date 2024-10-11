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
@Table(name="SICOES_TM_ROL_OPCION")
public class RolOpcion extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_ROL_OPCION")
	@SequenceGenerator(name="GEN_SICOES_SEQ_ROL_OPCION", sequenceName = "SICOES_SEQ_ROL_OPCION", allocationSize = 1)
	@Column(name = "ID_ROL_OPCION")	
	private Long idRolOpcion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_OPCION")	
	private Opcion opcion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ROL")
	private Rol rol;

	public Long getIdRolOpcion() {
		return idRolOpcion;
	}

	public void setIdRolOpcion(Long idRolOpcion) {
		this.idRolOpcion = idRolOpcion;
	}

	public Opcion getOpcion() {
		return opcion;
	}

	public void setOpcion(Opcion opcion) {
		this.opcion = opcion;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	
	
}