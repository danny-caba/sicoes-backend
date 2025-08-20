package pe.gob.osinergmin.sicoes.util.bean.siged;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUserListDto {
	
	@JsonProperty("usuarios")
	private Usuarios Usuarios;

	public Usuarios getUsuarios() {
		return Usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		Usuarios = usuarios;
	}

	public static class Usuarios {
		
		@JsonProperty("resultCode")
		private int resultCode;

		@JsonProperty("usuario")
		private List<Usuario> usuario;

		public int getResultCode() {
			return resultCode;
		}

		public void setResultCode(int resultCode) {
			this.resultCode = resultCode;
		}

		public List<Usuario> getUsuario() {
			return usuario;
		}

		public void setUsuario(List<Usuario> usuario) {
			this.usuario = usuario;
		}

	}

	public static class Usuario {

		@JsonProperty("idUsuario")
		private int idUsuario;

		@JsonProperty("nombres")
		private String nombres;

		public int getIdUsuario() {
			return idUsuario;
		}

		public void setIdUsuario(int idUsuario) {
			this.idUsuario = idUsuario;
		}

		public String getNombres() {
			return nombres;
		}

		public void setNombres(String nombres) {
			this.nombres = nombres;
		}
	}

}
