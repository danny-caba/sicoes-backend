package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TR_PERSONA")
public class Persona extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PERSONA")
	@SequenceGenerator(name="GEN_SICOES_SEQ_PERSONA", sequenceName = "SICOES_SEQ_PERSONA", allocationSize = 1)
	@Column(name = "ID_PERSONA")	
	private Long idPersona;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_DOCUMENTO_LD")	
	private ListadoDetalle tipoDocumento;
	
	@Column(name="NU_DOCUMENTO")	
	private String numeroDocumento;
	
	@Column(name="NO_RAZON_SOCIAL")	
	private String nombreRazonSocial;
	
	@Column(name="NO_PERSONA")	
	private String nombres;
	
	@Column(name="AP_PATERNO")	
	private String apellidoPaterno;
	
	@Column(name="AP_MATERNO")	
	private String apellidoMaterno;
	
	@Column(name="CO_RUC")	
	private String codigoRuc;
	
	@Column(name="DE_DIRECCION")	
	private String direccion;
	
	@Column(name="CO_DEPARTAMENTO")	
	private String codigoDepartamento;
	
	@Column(name="CO_PROVINCIA")	
	private String codigoProvincia;
	
	@Column(name="CO_DISTRITO")	
	private String codigoDistrito;
	
	@Column(name="NO_DEPARTAMENTO")	
	private String nombreDepartamento;
	
	@Column(name="NO_PROVINCIA")	
	private String nombreProvincia;
	
	@Column(name="NO_DISTRITO")	
	private String nombreDistrito;	
	
	@Column(name="CO_PARTIDA_REGISTRAL")	
	private String codigoPartidaRegistral;
	
	@Column(name="DE_TELEFONO1")	
	private String telefono1;
	
	@Column(name="DE_TELEFONO2")	
	private String telefono2;
	
	@Column(name="DE_TELEFONO3")	
	private String telefono3;
	
	@Column(name="DE_CORREO")	
	private String correo;
	
	@Column(name="CO_CLIENTE")
	private Long codigoCliente;
	
	@Column(name="CO_TIPO_PN")	
	private String codigoTipoPN;
	
	@Column(name="DE_TIPO_PN")	
	private String tipoPN;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PAIS_LD")
	private ListadoDetalle pais;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_PERSONA_LD")	
	private ListadoDetalle tipoPersona;

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public ListadoDetalle getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(ListadoDetalle tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
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

	public String getCodigoRuc() {
		return codigoRuc;
	}

	public void setCodigoRuc(String codigoRuc) {
		this.codigoRuc = codigoRuc;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public String getCodigoPartidaRegistral() {
		return codigoPartidaRegistral;
	}

	public void setCodigoPartidaRegistral(String codigoPartidaRegistral) {
		this.codigoPartidaRegistral = codigoPartidaRegistral;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getTelefono3() {
		return telefono3;
	}

	public void setTelefono3(String telefono3) {
		this.telefono3 = telefono3;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public boolean isPesonaJuridica() {
		return (numeroDocumento!=null&&numeroDocumento.length()==11&&numeroDocumento.startsWith("20"));
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public String getNombreProvincia() {
		return nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}

	public String getNombreDistrito() {
		return nombreDistrito;
	}

	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}

	public Long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}
	
	public String getSolicitante() {
		if(StringUtils.isNotBlank(nombreRazonSocial)) {
			return this.numeroDocumento + " - " +  this.nombreRazonSocial;
		}
		return this.numeroDocumento + " - " + this.apellidoPaterno + " " +  this.apellidoMaterno + " " +this.nombres;
	}

	public ListadoDetalle getPais() {
		return pais;
	}

	public void setPais(ListadoDetalle pais) {
		this.pais = pais;
	}

	public String getCodigoTipoPN() {
		return codigoTipoPN;
	}

	public void setCodigoTipoPN(String codigoTipoPN) {
		this.codigoTipoPN = codigoTipoPN;
	}

	public String getTipoPN() {
		return tipoPN;
	}

	public void setTipoPN(String tipoPN) {
		this.tipoPN = tipoPN;
	}

	public ListadoDetalle getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(ListadoDetalle tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
}