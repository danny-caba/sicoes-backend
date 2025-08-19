package pe.gob.osinergmin.sicoes.util.vo;

import java.io.Serializable;

public class CeapamDTO implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String razonSocial;
	private String nombre;
	private String codigoEstado;
	private String estado;
	private String aforo;
	private String cantPam;
	private String codigoTipoCeapam;
	private String tipoCeapam;
	private String codigoCondicion;
	private String condicion;
	private String direccion;
	private String codigoUbigeo;
	private String ubigeo;
	private String nombreRepresentante;
	private String codTipoDocRepresentante;
	private String tipoDocumentoRepresentante;
	private String nroDocumentoRepresentante;
	private String correoRepresentante;
	private String telefonoRepresentante;
	private String nombreResponsable;
	private String codTipoDocResponsable;
	private String tipoDocumentoResponsable;
	private String nroDocumentoResponsable;
	private String correoResponsable;
	private String telefonoResponsable;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigoEstado() {
		return codigoEstado;
	}
	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getAforo() {
		return aforo;
	}
	public void setAforo(String aforo) {
		this.aforo = aforo;
	}
	public String getCantPam() {
		return cantPam;
	}
	public void setCantPam(String cantPam) {
		this.cantPam = cantPam;
	}
	public String getCodigoTipoCeapam() {
		return codigoTipoCeapam;
	}
	public void setCodigoTipoCeapam(String codigoTipoCeapam) {
		this.codigoTipoCeapam = codigoTipoCeapam;
	}
	public String getTipoCeapam() {
		return tipoCeapam;
	}
	public void setTipoCeapam(String tipoCeapam) {
		this.tipoCeapam = tipoCeapam;
	}
	public String getCodigoCondicion() {
		return codigoCondicion;
	}
	public void setCodigoCondicion(String codigoCondicion) {
		this.codigoCondicion = codigoCondicion;
	}
	public String getCondicion() {
		return condicion;
	}
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCodigoUbigeo() {
		return codigoUbigeo;
	}
	public void setCodigoUbigeo(String codigoUbigeo) {
		this.codigoUbigeo = codigoUbigeo;
	}
	public String getUbigeo() {
		return ubigeo;
	}
	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}
	public String getNombreRepresentante() {
		return nombreRepresentante;
	}
	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}
	public String getCodTipoDocRepresentante() {
		return codTipoDocRepresentante;
	}
	public void setCodTipoDocRepresentante(String codTipoDocRepresentante) {
		this.codTipoDocRepresentante = codTipoDocRepresentante;
	}
	public String getTipoDocumentoRepresentante() {
		return tipoDocumentoRepresentante;
	}
	public void setTipoDocumentoRepresentante(String tipoDocumentoRepresentante) {
		this.tipoDocumentoRepresentante = tipoDocumentoRepresentante;
	}
	public String getNroDocumentoRepresentante() {
		return nroDocumentoRepresentante;
	}
	public void setNroDocumentoRepresentante(String nroDocumentoRepresentante) {
		this.nroDocumentoRepresentante = nroDocumentoRepresentante;
	}
	public String getCorreoRepresentante() {
		return correoRepresentante;
	}
	public void setCorreoRepresentante(String correoRepresentante) {
		this.correoRepresentante = correoRepresentante;
	}
	public String getTelefonoRepresentante() {
		return telefonoRepresentante;
	}
	public void setTelefonoRepresentante(String telefonoRepresentante) {
		this.telefonoRepresentante = telefonoRepresentante;
	}
	public String getNombreResponsable() {
		return nombreResponsable;
	}
	public void setNombreResponsable(String nombreResponsable) {
		this.nombreResponsable = nombreResponsable;
	}
	public String getCodTipoDocResponsable() {
		return codTipoDocResponsable;
	}
	public void setCodTipoDocResponsable(String codTipoDocResponsable) {
		this.codTipoDocResponsable = codTipoDocResponsable;
	}
	public String getTipoDocumentoResponsable() {
		return tipoDocumentoResponsable;
	}
	public void setTipoDocumentoResponsable(String tipoDocumentoResponsable) {
		this.tipoDocumentoResponsable = tipoDocumentoResponsable;
	}
	public String getNroDocumentoResponsable() {
		return nroDocumentoResponsable;
	}
	public void setNroDocumentoResponsable(String nroDocumentoResponsable) {
		this.nroDocumentoResponsable = nroDocumentoResponsable;
	}
	public String getCorreoResponsable() {
		return correoResponsable;
	}
	public void setCorreoResponsable(String correoResponsable) {
		this.correoResponsable = correoResponsable;
	}
	public String getTelefonoResponsable() {
		return telefonoResponsable;
	}
	public void setTelefonoResponsable(String telefonoResponsable) {
		this.telefonoResponsable = telefonoResponsable;
	}
	
	@Override
	public String toString() {
		return "CeapamDTO [codigo=" + codigo + ", razonSocial=" + razonSocial + ", nombre=" + nombre + ", codigoEstado="
				+ codigoEstado + ", estado=" + estado + ", aforo=" + aforo + ", cantPam=" + cantPam
				+ ", codigoTipoCeapam=" + codigoTipoCeapam + ", tipoCeapam=" + tipoCeapam + ", codigoCondicion="
				+ codigoCondicion + ", condicion=" + condicion + ", direccion=" + direccion + ", codigoUbigeo="
				+ codigoUbigeo + ", ubigeo=" + ubigeo + ", nombreRepresentante=" + nombreRepresentante
				+ ", codTipoDocRepresentante=" + codTipoDocRepresentante + ", tipoDocumentoRepresentante="
				+ tipoDocumentoRepresentante + ", nroDocumentoRepresentante=" + nroDocumentoRepresentante
				+ ", correoRepresentante=" + correoRepresentante + ", telefonoRepresentante=" + telefonoRepresentante
				+ ", nombreResponsable=" + nombreResponsable + ", codTipoDocResponsable=" + codTipoDocResponsable
				+ ", tipoDocumentoResponsable=" + tipoDocumentoResponsable + ", nroDocumentoResponsable="
				+ nroDocumentoResponsable + ", correoResponsable=" + correoResponsable + ", telefonoResponsable="
				+ telefonoResponsable + "]";
	}
	
	
	
	
	
	
}
