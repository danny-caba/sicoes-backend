package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.util.vo.OpcionDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsernameDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String ruc;
	private String token;
	
	private String codigoSede;
	private String usuario;
	
	private Long idUsuario;
	private Long idModulo;
	private Long idPerfil;
	private String codigo;
	
	private String nombres;
	private String clave;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombreCompleto;
	
	private Long idTipoDocumento;
	private Long idPais;
	
//	private Sede sede;
	private String cargo;
	private String area;
	
	private List<OpcionDTO> opciones;
	
	
	public UsernameDTO() {
	}
	
	public UsernameDTO(Usuario usuario) {
		idUsuario=usuario.getIdUsuario();		
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getCodigoSede() {
		return codigoSede;
	}
	public void setCodigoSede(String codigoSede) {
		this.codigoSede = codigoSede;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdModulo() {
		return idModulo;
	}
	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
	}
	public Long getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
	
	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public Long getIdPais() {
		return idPais;
	}

	public void setIdPais(Long idPais) {
		this.idPais = idPais;
	}

	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	@Override
	public String toString() {
		return "UsernameDTO [ruc=" + ruc + ", codigoSede=" + codigoSede + ", usuario=" + usuario + ", idUsuario="
				+ idUsuario + ", idModulo=" + idModulo + ", idPerfil=" + idPerfil + ", codigo=" + codigo + "]";
	}
//	public Sede getSede() {
//		return sede;
//	}
//	public void setSede(Sede sede) {
//		this.sede = sede;
//	}

	public String getNombreCompleto() {
		return (nombres==null?"":nombres) 
				+" "+(apellidoPaterno==null?"":apellidoPaterno)
				+" "+(apellidoMaterno==null?"":apellidoMaterno);
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public List<OpcionDTO> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<OpcionDTO> opciones) {
		this.opciones = opciones;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
