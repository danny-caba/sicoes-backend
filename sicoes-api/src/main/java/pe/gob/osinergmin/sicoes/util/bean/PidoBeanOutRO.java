package pe.gob.osinergmin.sicoes.util.bean;


public class PidoBeanOutRO extends BaseOutRO{

	private static final long serialVersionUID = -5844589315582356494L;
	
	private String nombres;
	private String nombreRazonSocial;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String direccion;
	private String ubigeo;
	private String codigoDepartamento;
	private String codigoProvincia;
	private String codigoDistrito;
	private String departamento;
	private String provincia;
	private String distrito;
	private String nombreDepartamento;
	private String nombreProvincia;
	private String nombreDistrito;
	private String celular;
	private int tieneNegocio;
	private String codigoTipoNegocio;
	private String nombreTipoNegocio;
	private boolean esCiudadanoValido;
	
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getUbigeo() {
		return ubigeo;
	}
	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombre) {
		this.nombres = nombre;
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
	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
	public boolean esCiudadanoValido() {
		return esCiudadanoValido;
	}
	public void setEsCiudadanoValido(boolean esCiudadanoValido) {
		this.esCiudadanoValido = esCiudadanoValido;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public boolean isEsCiudadanoValido() {
		return esCiudadanoValido;
	}
	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}
	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}
	public String getCodigoProvincia() {
		return codigoProvincia;
	}
	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}
	public String getCodigoDistrito() {
		return codigoDistrito;
	}
	public void setCodigoDistrito(String codigoDistrito) {
		this.codigoDistrito = codigoDistrito;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	public String getNombreDepartamento() {
		return this.departamento;
	}
	public String getNombreProvincia() {
		return this.provincia;
	}
	public String getNombreDistrito() {
		return this.distrito;
	}
	public String getCodigoTipoNegocio() {
		return codigoTipoNegocio;
	}
	public void setCodigoTipoNegocio(String codigoTipoNegocio) {
		this.codigoTipoNegocio = codigoTipoNegocio;
	}
	public String getNombreTipoNegocio() {
		return nombreTipoNegocio;
	}
	public void setNombreTipoNegocio(String nombreTipoNegocio) {
		this.nombreTipoNegocio = nombreTipoNegocio;
	}
	
}
