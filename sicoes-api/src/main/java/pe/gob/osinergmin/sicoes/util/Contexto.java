package pe.gob.osinergmin.sicoes.util;

import java.io.Serializable;

import pe.gob.osinergmin.sicoes.model.Usuario;

public class Contexto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private String usuarioApp;
	private String ip;
	
	private String aplicacion;
	private Long idEmpresa;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	@Override
	public String toString() {
		return "Contexo [usuario=" + usuario + ", aplicacion=" + aplicacion + "]";
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getUsuarioApp() {
		return usuarioApp;
	}

	public void setUsuarioApp(String usuarioApp) {
		this.usuarioApp = usuarioApp;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}	
}
