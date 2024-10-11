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
import javax.persistence.Transient;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TM_CONF_BANDEJA")
public class ConfiguracionBandeja extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_CONF_BANDEJA")
	@SequenceGenerator(name="GEN_SICOES_SEQ_CONF_BANDEJA", sequenceName = "SICOES_SEQ_CONF_BANDEJA", allocationSize = 1)
	@Column(name = "ID_CONF_BANDEJA")	
	private Long idConfiguracionBandeja;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_CONF")	
	private ListadoDetalle tipoConfiguracion;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTOR_LD")	
	private ListadoDetalle sector;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUBSECTOR_LD")	
	private ListadoDetalle subsector;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ACTIVIDAD_LD")	
	private ListadoDetalle actividad;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_UNIDAD_LD")	
	private ListadoDetalle unidad;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUBCATEGORIA_LD")	
	private ListadoDetalle subCategoria;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERFIL_LD")	
	private ListadoDetalle perfil;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;
	
	@Column(name="ES_CONF_BANDEJA")
	private String estadoConfiguracion;
	
	@Transient
	private UsuarioReasignacion usuarioReasignacion;
	
	@Transient
	private Usuario usuarioR;
	
	@Transient
	private Long idUsuarioRolC;
	
	@Transient
	private String nombreRol;
	
	@Transient
	private String codigoRol;
	

	public Long getIdConfiguracionBandeja() {
		return idConfiguracionBandeja;
	}

	public ListadoDetalle getTipoConfiguracion() {
		return tipoConfiguracion;
	}

	public ListadoDetalle getSector() {
		return sector;
	}

	public ListadoDetalle getSubsector() {
		return subsector;
	}

	public ListadoDetalle getActividad() {
		return actividad;
	}

	public ListadoDetalle getUnidad() {
		return unidad;
	}

	public ListadoDetalle getSubCategoria() {
		return subCategoria;
	}

	public ListadoDetalle getPerfil() {
		return perfil;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setIdConfiguracionBandeja(Long idConfiguracionBandeja) {
		this.idConfiguracionBandeja = idConfiguracionBandeja;
	}

	public void setTipoConfiguracion(ListadoDetalle tipoConfiguracion) {
		this.tipoConfiguracion = tipoConfiguracion;
	}

	public void setSector(ListadoDetalle sector) {
		this.sector = sector;
	}

	public void setSubsector(ListadoDetalle subsector) {
		this.subsector = subsector;
	}

	public void setActividad(ListadoDetalle actividad) {
		this.actividad = actividad;
	}

	public void setUnidad(ListadoDetalle unidad) {
		this.unidad = unidad;
	}

	public void setSubCategoria(ListadoDetalle subCategoria) {
		this.subCategoria = subCategoria;
	}

	public void setPerfil(ListadoDetalle perfil) {
		this.perfil = perfil;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getEstadoConfiguracion() {
		return estadoConfiguracion;
	}

	public void setEstadoConfiguracion(String estadoConfiguracion) {
		this.estadoConfiguracion = estadoConfiguracion;
	}
	
	public UsuarioReasignacion getUsuarioReasignacion() {
		return usuarioReasignacion;
	}

	public void setUsuarioReasignacion(UsuarioReasignacion usuarioReasignacion) {
		this.usuarioReasignacion = usuarioReasignacion;
	}

	public Usuario getUsuarioR() {
		return usuarioR;
	}

	public void setUsuarioR(Usuario usuarioR) {
		this.usuarioR = usuarioR;
	}
	
	public Long getIdUsuarioRolC() {
		return idUsuarioRolC;
	}

	public void setIdUsuarioRolC(Long idUsuarioRolC) {
		this.idUsuarioRolC = idUsuarioRolC;
	}
	
	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public String getCodigoRol() {
		return codigoRol;
	}

	public void setCodigoRol(String codigoRol) {
		this.codigoRol = codigoRol;
	}


	@Override
	public String toString() {
		return "ConfiguracionBandeja [idConfiguracionBandeja=" + idConfiguracionBandeja + ", tipoConfiguracion="
				+ tipoConfiguracion + ", sector=" + sector + ", subsector=" + subsector + ", actividad=" + actividad
				+ ", unidad=" + unidad + ", subCategoria=" + subCategoria + ", perfil=" + perfil + ", usuario="
				+ usuario + "]";
	}

}