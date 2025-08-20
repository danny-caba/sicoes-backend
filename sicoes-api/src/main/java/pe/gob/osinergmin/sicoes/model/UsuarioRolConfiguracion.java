package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SICOES_TX_USUARIO_ROL_CONF")
public class UsuarioRolConfiguracion extends BaseModel  implements Serializable{

private static final long serialVersionUID = -5163874121463770562L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_USUARIO_ROL_CONF")
	@SequenceGenerator(name="GEN_SICOES_SEQ_USUARIO_ROL_CONF", sequenceName = "SICOES_SEQ_USUARIO_ROL_CONF", allocationSize = 1)
	
	@Column(name = "ID_USUARIO_ROL_CONF")		
	private Long idUsuarioRolConfig;	
	
	@Column(name="ID_CONF_BANDEJA")
	private Long idConfiguracionBandeja;
	
	@Column(name="ID_USUARIO_ROL")
	private Long idUsuarioRol;
	
	@Column(name="ES_USUARIO_ROL_CONF")
	private String estadoUsuarioRolConfig;

	public Long getIdUsuarioRolConfig() {
		return idUsuarioRolConfig;
	}

	public void setIdUsuarioRolConfig(Long idUsuarioRolConfig) {
		this.idUsuarioRolConfig = idUsuarioRolConfig;
	}

	public Long getIdConfiguracionBandeja() {
		return idConfiguracionBandeja;
	}

	public void setIdConfiguracionBandeja(Long idConfiguracionBandeja) {
		this.idConfiguracionBandeja = idConfiguracionBandeja;
	}

	public Long getIdUsuarioRol() {
		return idUsuarioRol;
	}

	public void setIdUsuarioRol(Long idUsuarioRol) {
		this.idUsuarioRol = idUsuarioRol;
	}

	public String getEstadoUsuarioRolConfig() {
		return estadoUsuarioRolConfig;
	}

	public void setEstadoUsuarioRolConfig(String estadoUsuarioRolConfig) {
		this.estadoUsuarioRolConfig = estadoUsuarioRolConfig;
	}
}
