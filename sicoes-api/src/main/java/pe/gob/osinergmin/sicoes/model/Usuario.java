package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "SICOES_TM_USUARIO")
public class Usuario extends BaseModel  implements Serializable{

	private static final long serialVersionUID = -5163874121463770562L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_USUARIO")
	@SequenceGenerator(name="GEN_SICOES_SEQ_USUARIO", sequenceName = "SICOES_SEQ_USUARIO", allocationSize = 1)
	
	@Column(name = "ID_USUARIO")		
	private Long idUsuario;	
	
	@Column(name="CO_USUARIO")
	private Long codigoUsuarioInterno;
	
	@Column(name="DE_RUC")
	private String codigoRuc;
	
	@Column(name="DE_CONTRASENIA")
	private String contrasenia;
	
	@Column(name="DE_RAZON_SOCIAL")
	private String razonSocial;
	
	@Column(name="DE_CORREO")
	private String correo;
	
	@Column(name="NU_TELEFONO")
	private Long telefono;
	
	@Transient
	private String codigoToken;
	
	@Transient
	private String confirmarContrasenia;
	
	@Transient
	private String nombreTipoNegocio;
	
	@Transient
	private String descEstado;
	
	public Long getTelefono() {
		return telefono;
	}

	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}

	public ListadoDetalle getPais() {
		return pais;
	}

	public void setPais(ListadoDetalle pais) {
		this.pais = pais;
	}

	@Column(name="NO_USUARIO")
	private String nombreUsuario;

	@Column(name="DE_USUARIO")
	private String usuario;
	
	@Column(name="ES_USUARIO")
	private String estadoUsuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_PERSONA_LD")	
	private ListadoDetalle tipoPersona;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_DOCUMENTO_LD")	
	private ListadoDetalle tipoDocumento;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PAIS_LD")	
	private ListadoDetalle pais;	
	
	@Transient
	private String numeroDocumento;
	
	@Transient
	private String fechaInicioSesion;
	
	@Transient
	private List<Rol> roles;
	
	@Transient
	private String codigoTipo;
	
	@Transient
	private Map opciones;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getCodigoRuc() {
		return codigoRuc;
	}

	public void setCodigoRuc(String codigoRuc) {
		this.codigoRuc = codigoRuc;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
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

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", ruc=" + codigoRuc + "]";
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

	public ListadoDetalle getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(ListadoDetalle tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	
	public boolean isPesonaJuridica() {
		return (codigoRuc!=null&&codigoRuc.length()==11&&codigoRuc.startsWith("20"));
	}

	public boolean isRol(String codigRol) {
		if(roles!=null) {
			for(Rol rol:roles) {
				if(rol.getCodigo().equals(codigRol)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String getFechaInicioSesion() {
		return fechaInicioSesion;
	}

	public void setFechaInicioSesion(String fechaInicioSesion) {
		this.fechaInicioSesion = fechaInicioSesion;
	}

	public Map getOpciones() {
		return opciones;
	}

	public void setOpciones(Map opciones) {
		this.opciones = opciones;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public String getCodigoTipo() {
		return codigoTipo;
	}

	public void setCodigoTipo(String codigoTipo) {
		this.codigoTipo = codigoTipo;
	}

	public String getCodigoToken() {
		return codigoToken;
	}

	public void setCodigoToken(String codigoToken) {
		this.codigoToken = codigoToken;
	}

	public String getConfirmarContrasenia() {
		return confirmarContrasenia;
	}

	public void setConfirmarContrasenia(String confirmarContrasenia) {
		this.confirmarContrasenia = confirmarContrasenia;
	}

	public String getNombreTipoNegocio() {
		return nombreTipoNegocio;
	}

	public void setNombreTipoNegocio(String nombreTipoNegocio) {
		this.nombreTipoNegocio = nombreTipoNegocio;
	}

	public Long getCodigoUsuarioInterno() {
		return codigoUsuarioInterno;
	}

	public void setCodigoUsuarioInterno(Long codigoUsuarioInterno) {
		this.codigoUsuarioInterno = codigoUsuarioInterno;
	}

	public String getDescEstado() {
		return descEstado;
	}

	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
