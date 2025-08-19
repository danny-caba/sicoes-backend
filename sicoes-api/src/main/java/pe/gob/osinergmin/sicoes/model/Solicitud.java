package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="SICOES_TR_SOLICITUD")
public class Solicitud extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_SOLICITUD")
	@SequenceGenerator(name="GEN_SICOES_SEQ_SOLICITUD", sequenceName = "SICOES_SEQ_SOLICITUD", allocationSize = 1)
	@Column(name = "ID_SOLICITUD")	
	private Long idSolicitud;
	
	@Column(name = "CO_UUID")	
	private String solicitudUuid;

	@Column(name="NU_EXPEDIENTE")	
	private String numeroExpediente;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_REPRESENTANTE")	
	private Representante representante;
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERSONA")
	private Persona persona;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO")
	private Usuario usuario;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_RESUL_ADMIN")
	private ListadoDetalle resultadoAdministrativo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_RESUL_TECNICO")
	private ListadoDetalle resultadoTecnico;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_SOLICITUD_LD")
	private ListadoDetalle tipoSolicitud;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")
	private ListadoDetalle estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_REVISION_LD")
	private ListadoDetalle estadoRevision;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_EVAL_TECNICA_LD")
	private ListadoDetalle estadoEvaluacionTecnica;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_EVAL_ADMIN_LD")
	private ListadoDetalle estadoEvaluacionAdministrativa;
	
	@Column(name="NU_DIA_PLAZO_TECN")	
	private Long numeroPlazoTecnico;	

	
	@Column(name="DE_OBS_TECNICA")	
	private String observacionAdmnistrativa;
	
	@Column(name="DE_OBS_ADMIN")	
	private String observacionTecnica;
	
	@Column(name="DE_NO_CALIFICA")	
	private String observacionNoCalifica;
		
	@Column(name="CO_CONSENTIMIENTO")	
	private String codigoConsentimiento;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_REGISTRO")
	private Date fechaRegistro;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="FE_PRESENTACION")
	private Date fechaPresentacion;
	
	
	@Column(name="NU_DIA_PLAZO_RESP")	
	private Long numeroPlazoResp;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_PLAZO_RESP")
	private Date fechaPlazoResp;
	
	
	@Column(name="NU_DIA_PLAZO_ASIG")	
	private Long numeroPlazoAsig;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_PLAZO_ASIG")
	private Date fechaPlazoAsig;	
	
	@Column(name="NU_DIA_PLAZO_SUB")	
	private Long numeroPlazoSub;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_PLAZO_SUB")
	private Date fechaPlazoSub;	
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_PLAZO_TECN")	
	private Date fechaPlazoTecnico;
	
	@Column(name = "ID_SOLICITUD_PADRE")	
	private Long idSolicitudPadre;
	
	@Column(name = "CO_UUID_PADRE")	
	private String solicitudUuidPadre;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_ARCHIVAMIENTO")
	private Date fechaArchivamiento;
	
	@Column(name="FL_ACTIVO")	
	private Long flagActivo;
	
	@Column(name="FL_ARCHIVAMIENTO")	
	private Long flagArchivamiento;
	
	@Column(name="FL_RESPUESTA")	
	private Long flagRespuesta;
	
	@Column(name="US_ACTUALIZACION",insertable=false,updatable=true)
	@JsonIgnore
	protected String usuActualizacion;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_ACTUALIZACION",insertable=false,updatable=true)
	@JsonIgnore
	protected Date fecActualizacion;
	
	@Column(name="IP_ACTUALIZACION",insertable=false,updatable=true)
	@JsonIgnore
	protected String ipActualizacion;

		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROFESION")
	private Profesion profesion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DIVISION")
	private Division division;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ORIGEN_REGISTRO_LD")
	private ListadoDetalle origenRegistro;
	
	@Transient
	private List<OtroRequisito> otrosRequisitos;
	
	@Transient
	private List<OtroRequisito> perfiles;
	
	@Transient
	private List<Estudio> academicos;
	
	@Transient
	private List<Estudio> capacitaciones;
	
	@Transient
	private List<Documento> documentos;
	
	@Transient
	private List<Archivo> archivos;
	
	@Transient
	private List<Asignacion> asignados;
	
	@Transient
	private List<DictamenEvaluacion> montosFacturados;

	@Transient
	private List<Representante> historialRepresentante;

	public String getSolicitudUuid() {
		return solicitudUuid;
	}

	public void setSolicitudUuid(String solicitudUuid) {
		this.solicitudUuid = solicitudUuid;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public Representante getRepresentante() {
		return representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ListadoDetalle getResultadoAdministrativo() {
		return resultadoAdministrativo;
	}

	public void setResultadoAdministrativo(ListadoDetalle resultadoAdministrativo) {
		this.resultadoAdministrativo = resultadoAdministrativo;
	}

	public ListadoDetalle getResultadoTecnico() {
		return resultadoTecnico;
	}

	public void setResultadoTecnico(ListadoDetalle resultadoTecnico) {
		this.resultadoTecnico = resultadoTecnico;
	}

	public ListadoDetalle getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(ListadoDetalle tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public ListadoDetalle getEstadoRevision() {
		return estadoRevision;
	}

	public void setEstadoRevision(ListadoDetalle estadoRevision) {
		this.estadoRevision = estadoRevision;
	}

	public ListadoDetalle getEstadoEvaluacionTecnica() {
		return estadoEvaluacionTecnica;
	}

	public void setEstadoEvaluacionTecnica(ListadoDetalle estadoEvaluacionTecnica) {
		this.estadoEvaluacionTecnica = estadoEvaluacionTecnica;
	}

	public ListadoDetalle getEstadoEvaluacionAdministrativa() {
		return estadoEvaluacionAdministrativa;
	}

	public void setEstadoEvaluacionAdministrativa(ListadoDetalle estadoEvaluacionAdministrativa) {
		this.estadoEvaluacionAdministrativa = estadoEvaluacionAdministrativa;
	}

	public Long getNumeroPlazoResp() {
		return numeroPlazoResp;
	}

	public void setNumeroPlazoResp(Long numeroPlazoResp) {
		this.numeroPlazoResp = numeroPlazoResp;
	}

	public Long getNumeroPlazoAsig() {
		return numeroPlazoAsig;
	}

	public void setNumeroPlazoAsig(Long numeroPlazoAsig) {
		this.numeroPlazoAsig = numeroPlazoAsig;
	}

	public Long getNumeroPlazoSub() {
		return numeroPlazoSub;
	}

	public void setNumeroPlazoSub(Long numeroPlazoSub) {
		this.numeroPlazoSub = numeroPlazoSub;
	}

	public String getObservacionAdmnistrativa() {
		return observacionAdmnistrativa;
	}

	public void setObservacionAdmnistrativa(String observacionAdmnistrativa) {
		this.observacionAdmnistrativa = observacionAdmnistrativa;
	}

	public String getObservacionTecnica() {
		return observacionTecnica;
	}

	public void setObservacionTecnica(String observacionTecnica) {
		this.observacionTecnica = observacionTecnica;
	}

	public String getObservacionNoCalifica() {
		return observacionNoCalifica;
	}

	public void setObservacionNoCalifica(String observacionNoCalifica) {
		this.observacionNoCalifica = observacionNoCalifica;
	}

	public String getCodigoConsentimiento() {
		return codigoConsentimiento;
	}

	public void setCodigoConsentimiento(String codigoConsentimiento) {
		this.codigoConsentimiento = codigoConsentimiento;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public Date getFechaPlazoResp() {
		return fechaPlazoResp;
	}

	public void setFechaPlazoResp(Date fechaPlazoResp) {
		this.fechaPlazoResp = fechaPlazoResp;
	}

	public Date getFechaPlazoAsig() {
		return fechaPlazoAsig;
	}

	public void setFechaPlazoAsig(Date fechaPlazoAsig) {
		this.fechaPlazoAsig = fechaPlazoAsig;
	}

	public Long getIdSolicitudPadre() {
		return idSolicitudPadre;
	}

	public void setIdSolicitudPadre(Long idSolicitudPadre) {
		this.idSolicitudPadre = idSolicitudPadre;
	}

	public List<OtroRequisito> getOtrosRequisitos() {
		return otrosRequisitos;
	}

	public void setOtrosRequisitos(List<OtroRequisito> otrosRequisitos) {
		this.otrosRequisitos = otrosRequisitos;
	}

	public List<OtroRequisito> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<OtroRequisito> perfiles) {
		this.perfiles = perfiles;
	}

	public List<Estudio> getAcademicos() {
		return academicos;
	}

	public void setAcademicos(List<Estudio> academicos) {
		this.academicos = academicos;
	}

	public List<Estudio> getCapacitaciones() {
		return capacitaciones;
	}

	public void setCapacitaciones(List<Estudio> capacitaciones) {
		this.capacitaciones = capacitaciones;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public List<Archivo> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<Archivo> archivos) {
		this.archivos = archivos;
	}

	public List<Asignacion> getAsignados() {
		return asignados;
	}

	public void setAsignados(List<Asignacion> asignados) {
		this.asignados = asignados;
	}

	
	public Long getFlagActivo() {
		return flagActivo;
	}

	public void setFlagActivo(Long flagActivo) {
		this.flagActivo = flagActivo;
	}

	public Long getFlagArchivamiento() {
		return flagArchivamiento;
	}

	public void setFlagArchivamiento(Long flagArchivamiento) {
		this.flagArchivamiento = flagArchivamiento;
	}

	public Long getFlagRespuesta() {
		return flagRespuesta;
	}

	public void setFlagRespuesta(Long flagRespuesta) {
		this.flagRespuesta = flagRespuesta;
	}

	public Date getFechaArchivamiento() {
		return fechaArchivamiento;
	}

	public void setFechaArchivamiento(Date fechaArchivamiento) {
		this.fechaArchivamiento = fechaArchivamiento;
	}
	

	public Date getFechaPlazoSub() {
		return fechaPlazoSub;
	}

	public void setFechaPlazoSub(Date fechaPlazoSub) {
		this.fechaPlazoSub = fechaPlazoSub;
	}

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getSolicitudUuidPadre() {
		return solicitudUuidPadre;
	}

	public void setSolicitudUuidPadre(String solicitudUuidPadre) {
		this.solicitudUuidPadre = solicitudUuidPadre;
	}

	public Long getNumeroPlazoTecnico() {
		return numeroPlazoTecnico;
	}

	public void setNumeroPlazoTecnico(Long numeroPlazoTecnico) {
		this.numeroPlazoTecnico = numeroPlazoTecnico;
	}

	public Date getFechaPlazoTecnico() {
		return fechaPlazoTecnico;
	}

	public void setFechaPlazoTecnico(Date fechaPlazoTecnico) {
		this.fechaPlazoTecnico = fechaPlazoTecnico;
	}

	public List<DictamenEvaluacion> getMontosFacturados() {
		return montosFacturados;
	}

	public void setMontosFacturados(List<DictamenEvaluacion> montosFacturados) {
		this.montosFacturados = montosFacturados;
	}

	public List<Representante> getHistorialRepresentante() {
		return historialRepresentante;
	}

	public void setHistorialRepresentante(List<Representante> historialRepresentante) {
		this.historialRepresentante = historialRepresentante;
	}

	public Profesion getProfesion() {
		return profesion;
	}

	public void setProfesion(Profesion profesion) {
		this.profesion = profesion;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public ListadoDetalle getOrigenRegistro() {
		return origenRegistro;
	}
	
	public void setOrigenRegistro(ListadoDetalle origenRegistro) {
		this.origenRegistro = origenRegistro;
	}

	public void resetCamposModificables() {
		this.setNumeroExpediente(null);
		this.setFechaRegistro(null);
		this.setFechaPresentacion(null);
		this.setResultadoAdministrativo(null);
		this.setEstadoEvaluacionTecnica(null);
		this.setEstadoEvaluacionAdministrativa(null);
		this.setNumeroPlazoResp(null);
		this.setFechaPlazoResp(null);
		this.setNumeroPlazoAsig(null);
		this.setFechaPlazoAsig(null);
		this.setObservacionTecnica(null);
		this.setObservacionAdmnistrativa(null);
		this.setCodigoConsentimiento(null);
		this.setNumeroPlazoSub(null);
		this.setFechaPlazoTecnico(null);
		this.setObservacionNoCalifica(null);
		this.setFechaArchivamiento(null);
		this.setFlagArchivamiento(null);
		this.setFlagRespuesta(null);
	}
	
}