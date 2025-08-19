package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "SICOES_TX_USUARIO_REASIGNACION")
public class UsuarioReasignacion extends BaseModel  implements Serializable{

	private static final long serialVersionUID = -5163874121463770562L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_USUARIO_REASIG")
	@SequenceGenerator(name="GEN_SICOES_SEQ_USUARIO_REASIG", sequenceName = "SICOES_SEQ_USUARIO_REASIG", allocationSize = 1)
	
	@Column(name = "ID_USUARIO_REASIGNACION")		
	private Long idUsuarioReasignacion;	
	
	@Column(name="ID_CONF_BANDEJA")
	private Long idConfiguracionBandeja;
	
	@Column(name="ID_USUARIO")
	private Long idUsuario;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_INICIO")
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_FIN")
	private Date fechaFin;
	
	@Column(name="ES_USUARIO_REASIGNACION")
	private String estadoUsuarioReasignacion;
	
	@Transient
	private String periodo;
	
	@Transient
	private String fechaInicioS;
	
	@Transient
	private String fechaFinS;
	
	@Transient
	private Usuario usuario;
	
	@Transient
	private ConfiguracionBandeja configuracionBandeja;
	
	@Transient
	private Division division;
	
	@Transient
	private Usuario usuarioReasignacion;

	public Long getIdUsuarioReasignacion() {
		return idUsuarioReasignacion;
	}

	public void setIdUsuarioReasignacion(Long idUsuarioReasignacion) {
		this.idUsuarioReasignacion = idUsuarioReasignacion;
	}

	public Long getIdConfiguracionBandeja() {
		return idConfiguracionBandeja;
	}

	public void setIdConfiguracionBandeja(Long idConfiguracionBandeja) {
		this.idConfiguracionBandeja = idConfiguracionBandeja;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstadoUsuarioReasignacion() {
		return estadoUsuarioReasignacion;
	}

	public void setEstadoUsuarioReasignacion(String estadoUsuarioReasignacion) {
		this.estadoUsuarioReasignacion = estadoUsuarioReasignacion;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioReasignacion() {
		return usuarioReasignacion;
	}

	public void setUsuarioReasignacion(Usuario usuarioReasignacion) {
		this.usuarioReasignacion = usuarioReasignacion;
	}

	public String getFechaInicioS() {
		return fechaInicioS;
	}

	public void setFechaInicioS(String fechaInicioS) {
		this.fechaInicioS = fechaInicioS;
	}

	public String getFechaFinS() {
		return fechaFinS;
	}

	public void setFechaFinS(String fechaFinS) {
		this.fechaFinS = fechaFinS;
	}

	public ConfiguracionBandeja getConfiguracionBandeja() {
		return configuracionBandeja;
	}

	public void setConfiguracionBandeja(ConfiguracionBandeja configuracionBandeja) {
		this.configuracionBandeja = configuracionBandeja;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}
}
