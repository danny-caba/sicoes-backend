package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.Arrays;

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

import org.springframework.web.multipart.MultipartFile;

/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name = "SICOES_TR_ARCHIVO")
public class Archivo extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_ARCHIVO")
	@SequenceGenerator(name = "GEN_SICOES_SEQ_ARCHIVO", sequenceName = "SICOES_SEQ_ARCHIVO", allocationSize = 1)
	@Column(name = "ID_ARCHIVO")
	private Long idArchivo;

	@Column(name = "ID_DOCUMENTO")
	private Long idDocumento;

	@Column(name = "ID_OTRO_REQUISITO")
	private Long idOtroRequisito;

	@Column(name = "ID_ESTUDIO")
	private Long idEstudio;

	@Column(name = "ID_PROPUESTA")
	private Long idPropuesta;

	@Column(name = "ID_PRO_TECNICA")
	private Long idPropuestaTecnica;

	@Column(name = "ID_PRO_ECONOMICA")
	private Long idPropuestaEconomica;

	@Column(name = "ID_NOTIFICACION_SOLICITUD")
	private Long idNotificacionSolicitud;

	@Column(name = "ID_NOTIFICACION")
	private Long idNotificacion;

	@Column(name = "ID_PROCESO")
	private Long idProceso;

	@Column(name = "ID_ASIGNACION")
	private Long idAsignacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTADO_LD")
	private ListadoDetalle estado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_ARCHIVO_LD")
	private ListadoDetalle tipoArchivo;

	@Column(name = "ID_SOLICITUD")
	private Long idSolicitud;

	@Column(name = "NO_ARCHIVO")
	private String nombre;

	@Column(name = "NO_REAL")
	private String nombreReal;

	@Column(name = "NO_ALFRESCO")
	private String nombreAlFresco;

	@Column(name = "CO_ARCHIVO")
	private String codigo;

	@Column(name = "CO_TIPO_ARCHIVO")
	private String tipo;

	@Column(name = "DE_ARCHIVO")
	private String descripcion;

	@Column(name = "NU_CORRELATIVO")
	private Long correlativo;

	@Column(name = "NU_VERSION")
	private Long version;

	@Column(name = "NU_FOLIO")
	private Long nroFolio;

	@Column(name = "NU_PESO")
	private Long peso;

	@Column(name = "FL_SIGED")
	private Long flagSiged;

	@Column(name = "ID_DET_SOLI_PERF_CONT")
	private Long idSeccionRequisito;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;
	
	@Column(name = "ID_SOLI_PERF_CONT_LD")
	private Long idSoliPerfCont;

	@Column(name = "ID_DOCUMENTO_REEMPLAZO")
	private Long idDocumentoReem;

	@Column(name = "ID_PERSONAL_REEMPLAZO")
	private Long idReemplazoPersonal;
	
	@Transient
	private MultipartFile file;

	@Transient
	private byte[] contenido;

	@Transient
	private String solicitudUuid;

	@Transient
	private String propuestaUuid;

	public Long getIdArchivo() {
		return idArchivo;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public Long getIdOtroRequisito() {
		return idOtroRequisito;
	}

	public Long getIdEstudio() {
		return idEstudio;
	}

	public Long getIdPropuestaTecnica() {
		return idPropuestaTecnica;
	}

	public Long getIdPropuestaEconomica() {
		return idPropuestaEconomica;
	}

	public Long getIdNotificacionSolicitud() {
		return idNotificacionSolicitud;
	}

	public Long getIdNotificacion() {
		return idNotificacion;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public ListadoDetalle getTipoArchivo() {
		return tipoArchivo;
	}

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNombreReal() {
		return nombreReal;
	}

	public String getNombreAlFresco() {
		return nombreAlFresco;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Long getCorrelativo() {
		return correlativo;
	}

	public Long getVersion() {
		return version;
	}

	public Long getNroFolio() {
		return nroFolio;
	}

	public Long getPeso() {
		return peso;
	}

	public MultipartFile getFile() {
		return file;
	}

	public byte[] getContenido() {
		return contenido;
	}

	public void setIdArchivo(Long idArchivo) {
		this.idArchivo = idArchivo;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public void setIdOtroRequisito(Long idOtroRequisito) {
		this.idOtroRequisito = idOtroRequisito;
	}

	public void setIdEstudio(Long idEstudio) {
		this.idEstudio = idEstudio;
	}

	public void setIdPropuestaTecnica(Long idPropuestaTecnica) {
		this.idPropuestaTecnica = idPropuestaTecnica;
	}

	public void setIdPropuestaEconomica(Long idPropuestaEconomica) {
		this.idPropuestaEconomica = idPropuestaEconomica;
	}

	public void setIdNotificacionSolicitud(Long idNotificacionSolicitud) {
		this.idNotificacionSolicitud = idNotificacionSolicitud;
	}

	public void setIdNotificacion(Long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public void setTipoArchivo(ListadoDetalle tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setNombreReal(String nombreReal) {
		this.nombreReal = nombreReal;
	}

	public void setNombreAlFresco(String nombreAlFresco) {
		this.nombreAlFresco = nombreAlFresco;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setCorrelativo(Long correlativo) {
		this.correlativo = correlativo;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public void setNroFolio(Long nroFolio) {
		this.nroFolio = nroFolio;
	}

	public void setPeso(Long peso) {
		this.peso = peso;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}

	@Override
	public String toString() {
		return "Archivo [idArchivo=" + idArchivo + ", idDocumento=" + idDocumento + ", idOtroRequisito="
				+ idOtroRequisito + ", idEstudio=" + idEstudio + ", idPropuestaTecnica=" + idPropuestaTecnica
				+ ", idPropuestaEconomica=" + idPropuestaEconomica + ", idNotificacionSolicitud="
				+ idNotificacionSolicitud + ", idNotificacion=" + idNotificacion + ", estado=" + estado
				+ ", tipoArchivo=" + tipoArchivo + ", idSolicitud=" + idSolicitud + ", nombre=" + nombre
				+ ", nombreReal=" + nombreReal + ", nombreAlFresco=" + nombreAlFresco + ", codigo=" + codigo + ", tipo="
				+ tipo + ", descripcion=" + descripcion + ", correlativo=" + correlativo + ", version=" + version
				+ ", nroFolio=" + nroFolio + ", peso=" + peso + ", file=" + file + ", contenido="
				+ Arrays.toString(contenido) + "]";
	}

	public String getSolicitudUuid() {
		return solicitudUuid;
	}

	public void setSolicitudUuid(String solicitudUuid) {
		this.solicitudUuid = solicitudUuid;
	}

	public Long getIdPropuesta() {
		return idPropuesta;
	}

	public void setIdPropuesta(Long idPropuesta) {
		this.idPropuesta = idPropuesta;
	}

	public String getPropuestaUuid() {
		return propuestaUuid;
	}

	public void setPropuestaUuid(String propuestaUuid) {
		this.propuestaUuid = propuestaUuid;
	}

	public Long getIdAsignacion() {
		return idAsignacion;
	}

	public void setIdAsignacion(Long idAsignacion) {
		this.idAsignacion = idAsignacion;
	}

	public Long getFlagSiged() {
		return flagSiged;
	}

	public void setFlagSiged(Long flagSiged) {
		this.flagSiged = flagSiged;
	}

	public Long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}

	public Long getIdSeccionRequisito() {
		return idSeccionRequisito;
	}

	public void setIdSeccionRequisito(Long idSeccionRequisito) {
		this.idSeccionRequisito = idSeccionRequisito;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Long getIdSoliPerfCont() {
		return idSoliPerfCont;
	}

	public void setIdSoliPerfCont(Long idSoliPerfCont) {
		this.idSoliPerfCont = idSoliPerfCont;
	}

	public Long getIdDocumentoReem(){
		return idDocumentoReem;
	}

	public void setIdDocumentoReem(Long idDocumentoReem) {
		this.idDocumentoReem = idDocumentoReem;
	}

	public Long getIdReemplazoPersonal() {
		return idReemplazoPersonal;
	}

	public void setIdReemplazoPersonal(Long idReemplazoPersonal) {
		this.idReemplazoPersonal = idReemplazoPersonal;
	}
	
}