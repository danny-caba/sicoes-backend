package pe.gob.osinergmin.sicoes.util.bean.siged;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseGetUserDto {

	@JsonProperty("obtenerUsuarioSIGED")
	private UsuarioSiged obtenerUsuarioSIGED;

	public UsuarioSiged getObtenerUsuarioSIGED() {
		return obtenerUsuarioSIGED;
	}

	public void setObtenerUsuarioSIGED(UsuarioSiged obtenerUsuarioSIGED) {
		this.obtenerUsuarioSIGED = obtenerUsuarioSIGED;
	}

	public static class UsuarioSiged {

		@JsonProperty("resultCode")
		private int resultCode;

		@JsonProperty("usuarios")
		private Usuarios usuarios;

		public int getResultCode() {
			return resultCode;
		}

		public void setResultCode(int resultCode) {
			this.resultCode = resultCode;
		}

		public Usuarios getUsuarios() {
			return usuarios;
		}

		public void setUsuarios(Usuarios usuarios) {
			this.usuarios = usuarios;
		}

	}

	public static class Usuarios {

		@JsonProperty("usuario")
		private Usuario usuario;

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}

	}

	public static class Usuario {

		@JsonProperty("nombresUsuario")
		private String nombresUsuario;

		@JsonProperty("apellidosUsuario")
		private String apellidosUsuario;

		@JsonProperty("correoUsuario")
		private String correoUsuario;

		@JsonProperty("estadoUsuario")
		private String estadoUsuario;

		@JsonProperty("idUnidad")
		private Long idUnidad;

		@JsonProperty("idUsuario")
		private Long idUsuario;

		@JsonProperty("roles")
		private Roles roles;

		@JsonProperty("usuario")
		private String usuario;

		@JsonProperty("nombreUnidad")
		private String nombreUnidad;

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

		public String getEstadoUsuario() {
			return estadoUsuario;
		}

		public void setEstadoUsuario(String estadoUsuario) {
			this.estadoUsuario = estadoUsuario;
		}

		public Long getIdUnidad() {
			return idUnidad;
		}

		public void setIdUnidad(Long idUnidad) {
			this.idUnidad = idUnidad;
		}

		public Long getIdUsuario() {
			return idUsuario;
		}

		public void setIdUsuario(Long idUsuario) {
			this.idUsuario = idUsuario;
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public String getNombreUnidad() {
			return nombreUnidad;
		}

		public void setNombreUnidad(String nombreUnidad) {
			this.nombreUnidad = nombreUnidad;
		}

	}

	public static class Roles {

		@JsonProperty("rol")
		private List<Rol> rol;

		public List<Rol> getRol() {
			return rol;
		}

		public void setRol(List<Rol> rol) {
			this.rol = rol;
		}

	}

	public static class Rol {

		@JsonProperty("idRol")
		private Long idRol;

		@JsonProperty("nombreRol")
		private String nombreRol;

		public Long getIdRol() {
			return idRol;
		}

		public void setIdRol(Long idRol) {
			this.idRol = idRol;
		}

		public String getNombreRol() {
			return nombreRol;
		}

		public void setNombreRol(String nombreRol) {
			this.nombreRol = nombreRol;
		}

	}
}
