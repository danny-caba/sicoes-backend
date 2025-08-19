package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;

public class UsuarioDTO implements Serializable{

	private Long idUsuario;
	private String usuCreacion;
	private String ipCreacion;
	private String usuActualizacion;
	private String ipActualizacion;

	private String codigoRuc;
	private String razonSocial;
	private String correo;
	private Long telefono;
	private String nombreTipoNegocio;
	private String descEstado;
	private String nombreUsuario;
	private String usuario;
	private String estadoUsuario;
	private String numeroDocumento;
	private List<Rol> listaRoles;
	
	public static class Rol{
		private Long idRol;
		private Long idUsuario;
		private String nombre;
		private String descripcion;
		private String codigo;
		public Long getIdRol() {
			return idRol;
		}
		public void setIdRol(Long idRol) {
			this.idRol = idRol;
		}
		public Long getIdUsuario() {
			return idUsuario;
		}
		public void setIdUsuario(Long idUsuario) {
			this.idUsuario = idUsuario;
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
		
		
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsuActualizacion() {
		return usuActualizacion;
	}
	public void setUsuActualizacion(String usuActualizacion) {
		this.usuActualizacion = usuActualizacion;
	}
	public String getIpActualizacion() {
		return ipActualizacion;
	}
	public void setIpActualizacion(String ipActualizacion) {
		this.ipActualizacion = ipActualizacion;
	}
	public String getUsuCreacion() {
		return usuCreacion;
	}
	public void setUsuCreacion(String usuCreacion) {
		this.usuCreacion = usuCreacion;
	}
	public String getIpCreacion() {
		return ipCreacion;
	}
	public void setIpCreacion(String ipCreacion) {
		this.ipCreacion = ipCreacion;
	}
	public String getCodigoRuc() {
		return codigoRuc;
	}
	public void setCodigoRuc(String codigoRuc) {
		this.codigoRuc = codigoRuc;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public Long getTelefono() {
		return telefono;
	}
	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}
	public String getNombreTipoNegocio() {
		return nombreTipoNegocio;
	}
	public void setNombreTipoNegocio(String nombreTipoNegocio) {
		this.nombreTipoNegocio = nombreTipoNegocio;
	}
	public String getDescEstado() {
		return descEstado;
	}
	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getEstadoUsuario() {
		return estadoUsuario;
	}
	public void setEstadoUsuario(String estadoUsuario) {
		this.estadoUsuario = estadoUsuario;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public List<Rol> getListaRoles() {
		return listaRoles;
	}
	public void setListaRoles(List<Rol> listaRoles) {
		this.listaRoles = listaRoles;
	}
	
}
