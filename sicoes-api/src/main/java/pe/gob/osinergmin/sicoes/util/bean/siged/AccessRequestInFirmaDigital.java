package pe.gob.osinergmin.sicoes.util.bean.siged;

import java.io.Serializable;

public class AccessRequestInFirmaDigital implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String action;
	
	private String loginUsuario;
	
	private String passwordUsuario;
	
	private String archivosFirmar;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public String getPasswordUsuario() {
		return passwordUsuario;
	}

	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}

	public String getArchivosFirmar() {
		return archivosFirmar;
	}

	public void setArchivosFirmar(String archivosFirmar) {
		this.archivosFirmar = archivosFirmar;
	}

	@Override
	public String toString() {
		return "AccessRequestInFirmaDigital [action=" + action + ", loginUsuario=" + loginUsuario + ", passwordUsuario="
				+ passwordUsuario + ", archivosFirmar=" + archivosFirmar + "]";
	}
}
