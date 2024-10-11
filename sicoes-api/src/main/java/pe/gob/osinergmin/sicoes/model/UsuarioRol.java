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
@Table(name="SICOES_TM_USUARIO_ROL")
public class UsuarioRol extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_USUARIO_ROL")
	@SequenceGenerator(name="GEN_SICOES_SEQ_USUARIO_ROL", sequenceName = "SICOES_SEQ_USUARIO_ROL", allocationSize = 1)
	@Column(name = "ID_USUARIO_ROL")	
	private Long idUsuarioRol;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ROL")
	private Rol rol;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;
	
	@Column(name="ES_USUARIO_ROL")
	private String estadoUsuarioRol;

	public Long getIdUsuarioRol() {
		return idUsuarioRol;
	}

	public void setIdUsuarioRol(Long idUsuarioRol) {
		this.idUsuarioRol = idUsuarioRol;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getEstadoUsuarioRol() {
		return estadoUsuarioRol;
	}

	public void setEstadoUsuarioRol(String estadoUsuarioRol) {
		this.estadoUsuarioRol = estadoUsuarioRol;
	}
}