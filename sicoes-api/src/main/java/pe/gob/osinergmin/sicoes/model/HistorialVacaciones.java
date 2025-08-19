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

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TR_HISTORIAL_VACACIONES")
public class HistorialVacaciones extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_HISTORIAL_VACACIONES")
	@SequenceGenerator(name = "GEN_SICOES_SEQ_HISTORIAL_VACACIONES", sequenceName = "SICOES_SEQ_HIST_VACACIONES", allocationSize = 1)
	@Column(name = "ID_HISTORIAL_VACACIONES")
	private Long idHistorialVacaciones;
	
	@Column(name="ID")	
	private Long id;
	
	@Column(name="DNI_EMPLEADO")
	private String dniEmpleado;
	
	@Column(name="USUARIO_SALIENTE")
	private String usuarioSaliente;
	
	@Column(name="APELLIDOS")
	private String apellidos;
	
	@Column(name="NOMBRES")
	private String nombres;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_INICIO_AUSENCIA")
	private Date fechaInicioAusencia;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_FIN_AUSENCIA")
	private Date fechaFinAusencia;
	
	@Column(name="MOTIVO_AUSENCIA")
	private String motivoAusencia;
	
	@Column(name="CODIGO_USUARIO_REEMPLAZO")	
	private Long codigoUsuarioReemplazo;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_INICIO_REEMPLAZO")
	private Date fechaInicioReemplazo;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_FIN_REEMPLAZO")
	private Date fechaFinReemplazo;
	
	@Column(name="DOCUMENTO_REEMPLAZO")
	private String documentoReemplazo;
	
	@Column(name="USUARIO_REEMPLAZO")
	private String usuarioReemplazo;
	
	@Column(name="APELLIDOS_REEMPLAZO")
	private String apellidosReemplazo;
	
	@Column(name="NOMBRES_REEMPLAZO")
	private String nombresReemplazo;
	
	@Column(name="NOMBRE_COMPLETO_REEMPLAZO")
	private String nombreCompletoReemplazo;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_CONSULTA")
	private Date fechaConsulta;
	
	@Column(name="CODIGO_CONSULTA")	
	private Integer codigoConsulta;
	
	@Column(name = "ID_GRUPO_APROBADOR_REEMPLAZO")
	private Long idGrupoAprobadorReemplazo;
	
	@Column(name = "ID_DIVISION_APROBADOR_SALIENTE")
	private Long idDivisionAprobadorSaliente;
	
	@Column(name="FL_ACTIVO")	
	private Long flagActivo;

	public Long getIdHistorialVacaciones() {
		return idHistorialVacaciones;
	}

	public void setIdHistorialVacaciones(Long idHistorialVacaciones) {
		this.idHistorialVacaciones = idHistorialVacaciones;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDniEmpleado() {
		return dniEmpleado;
	}

	public void setDniEmpleado(String dniEmpleado) {
		this.dniEmpleado = dniEmpleado;
	}

	public String getUsuarioSaliente() {
		return usuarioSaliente;
	}

	public void setUsuarioSaliente(String usuarioSaliente) {
		this.usuarioSaliente = usuarioSaliente;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Date getFechaInicioAusencia() {
		return fechaInicioAusencia;
	}

	public void setFechaInicioAusencia(Date fechaInicioAusencia) {
		this.fechaInicioAusencia = fechaInicioAusencia;
	}

	public Date getFechaFinAusencia() {
		return fechaFinAusencia;
	}

	public void setFechaFinAusencia(Date fechaFinAusencia) {
		this.fechaFinAusencia = fechaFinAusencia;
	}

	public String getMotivoAusencia() {
		return motivoAusencia;
	}

	public void setMotivoAusencia(String motivoAusencia) {
		this.motivoAusencia = motivoAusencia;
	}

	public Long getCodigoUsuarioReemplazo() {
		return codigoUsuarioReemplazo;
	}

	public void setCodigoUsuarioReemplazo(Long codigoUsuarioReemplazo) {
		this.codigoUsuarioReemplazo = codigoUsuarioReemplazo;
	}

	public Date getFechaInicioReemplazo() {
		return fechaInicioReemplazo;
	}

	public void setFechaInicioReemplazo(Date fechaInicioReemplazo) {
		this.fechaInicioReemplazo = fechaInicioReemplazo;
	}

	public Date getFechaFinReemplazo() {
		return fechaFinReemplazo;
	}

	public void setFechaFinReemplazo(Date fechaFinReemplazo) {
		this.fechaFinReemplazo = fechaFinReemplazo;
	}

	public String getDocumentoReemplazo() {
		return documentoReemplazo;
	}

	public void setDocumentoReemplazo(String documentoReemplazo) {
		this.documentoReemplazo = documentoReemplazo;
	}

	public String getUsuarioReemplazo() {
		return usuarioReemplazo;
	}

	public void setUsuarioReemplazo(String usuarioReemplazo) {
		this.usuarioReemplazo = usuarioReemplazo;
	}

	public String getApellidosReemplazo() {
		return apellidosReemplazo;
	}

	public void setApellidosReemplazo(String apellidosReemplazo) {
		this.apellidosReemplazo = apellidosReemplazo;
	}

	public String getNombresReemplazo() {
		return nombresReemplazo;
	}

	public void setNombresReemplazo(String nombresReemplazo) {
		this.nombresReemplazo = nombresReemplazo;
	}

	public String getNombreCompletoReemplazo() {
		return nombreCompletoReemplazo;
	}

	public void setNombreCompletoReemplazo(String nombreCompletoReemplazo) {
		this.nombreCompletoReemplazo = nombreCompletoReemplazo;
	}

	public Date getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	public Integer getCodigoConsulta() {
		return codigoConsulta;
	}

	public void setCodigoConsulta(Integer codigoConsulta) {
		this.codigoConsulta = codigoConsulta;
	}

	public Long getIdGrupoAprobadorReemplazo() {
		return idGrupoAprobadorReemplazo;
	}

	public void setIdGrupoAprobadorReemplazo(Long idGrupoAprobadorReemplazo) {
		this.idGrupoAprobadorReemplazo = idGrupoAprobadorReemplazo;
	}

	public Long getIdDivisionAprobadorSaliente() {
		return idDivisionAprobadorSaliente;
	}

	public void setIdDivisionAprobadorSaliente(Long idDivisionAprobadorSaliente) {
		this.idDivisionAprobadorSaliente = idDivisionAprobadorSaliente;
	}

	public Long getFlagActivo() {
		return flagActivo;
	}

	public void setFlagActivo(Long flagActivo) {
		this.flagActivo = flagActivo;
	}

	@Override
	public String toString() {
		return "HistorialVacaciones [idHistorialVacaciones=" + idHistorialVacaciones + ", id=" + id + ", dniEmpleado="
				+ dniEmpleado + ", usuarioSaliente=" + usuarioSaliente + ", apellidos=" + apellidos + ", nombres="
				+ nombres + ", fechaInicioAusencia=" + fechaInicioAusencia + ", fechaFinAusencia=" + fechaFinAusencia
				+ ", motivoAusencia=" + motivoAusencia + ", codigoUsuarioReemplazo=" + codigoUsuarioReemplazo
				+ ", fechaInicioReemplazo=" + fechaInicioReemplazo + ", fechaFinReemplazo=" + fechaFinReemplazo
				+ ", documentoReemplazo=" + documentoReemplazo + ", usuarioReemplazo=" + usuarioReemplazo
				+ ", apellidosReemplazo=" + apellidosReemplazo + ", nombresReemplazo=" + nombresReemplazo
				+ ", nombreCompletoReemplazo=" + nombreCompletoReemplazo + ", fechaConsulta=" + fechaConsulta
				+ ", codigoConsulta=" + codigoConsulta + ", idGrupoAprobadorReemplazo=" + idGrupoAprobadorReemplazo
				+ ", idDivisionAprobadorSaliente=" + idDivisionAprobadorSaliente + ", flagActivo=" + flagActivo + "]";
	}
}
