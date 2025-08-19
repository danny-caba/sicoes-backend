package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseUsuarioSigedDTO implements Serializable{

	private Long idUsuario;
	private String nombresUsuario;
	private String apellidosUsuario;
	private String correoUsuario;
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombresUsuario() {
		return nombresUsuario;
	}
	public void setNombresUsuario(String nombresUsuario) {
		this.nombresUsuario = nombresUsuario;
	}
	public String getApellidosUsuario() {
		return apellidosUsuario;
	}
	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuario = apellidosUsuario;
	}
	public String getCorreoUsuario() {
		return correoUsuario;
	}
	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}
	
	
}
