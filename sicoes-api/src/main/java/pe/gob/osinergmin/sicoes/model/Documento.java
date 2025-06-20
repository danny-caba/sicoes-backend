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

/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name = "SICOES_TR_DOCUMENTO")
public class Documento extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_DOCUMENTO")
	@SequenceGenerator(name = "GEN_SICOES_SEQ_DOCUMENTO", sequenceName = "SICOES_SEQ_DOCUMENTO", allocationSize = 1)
	@Column(name = "ID_DOCUMENTO")
	private Long idDocumento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_LD")
	private ListadoDetalle tipo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_DOCUMENTO_LD")
	private ListadoDetalle tipoDocumento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_CAMBIO_LD")
	private ListadoDetalle tipoCambio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONFORMIDAD_LD")
	private ListadoDetalle cuentaConformidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_ID_TRIBURARIO_LD")
	private ListadoDetalle tipoIdentificacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PAIS_LD")
	private ListadoDetalle pais;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SUBSECTOR")
	private ListadoDetalle subSectorDoc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ACTIVIDAD_AREA")
	private ListadoDetalle actividadArea;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SOLICITUD")
	private Solicitud solicitud;

	@Column(name = "ID_DOCUMENTO_PADRE")
	private Long idDocumentoPadre;

	@Column(name = "NU_DOCUMENTO")
	private String numeroDocumento;

	@Column(name = "NO_ENTIDAD")
	private String nombreEntidad;

	@Column(name = "CO_CONTRATO")
	private String codigoContrato;

	@Column(name = "DE_CONTRATO")
	private String descripcionContrato;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "FE_INICIO_CONTRATO")
	private Date fechaInicio;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "FE_FIN_CONTRATO")
	private Date fechaFin;

	@Column(name = "DE_DURACION")
	private String duracion;

	@Column(name = "FL_VIGENTE")
	private String flagVigente;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "FE_CONFORMIDAD")
	private Date fechaConformidad;

	@Column(name = "MO_CONTRATO")
	private Double montoContrato;

	@Column(name = "MO_TIPO_CAMBIO")
	private Double montoTipoCambio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EVALUACION_LD")
	private ListadoDetalle evaluacion;

	@Column(name = "MO_CONTRATO_SOL")
	private Double montoContratoSol;

	@Column(name = "DE_OBSERVACION")
	private String observacion;

	@Column(name = "FL_SIGED")
	private Long flagSiged;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")
	private ListadoDetalle estado;

	@Transient
	private Archivo archivo;
	
	@Transient
	private List<PerfilAprobador> perfilesAprobadores;

	public List<PerfilAprobador> getPerfilesAprobadores() {
		return perfilesAprobadores;
	}

	public void setPerfilesAprobadores(List<PerfilAprobador> perfilesAprobadores) {
		this.perfilesAprobadores = perfilesAprobadores;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public ListadoDetalle getTipo() {
		return tipo;
	}

	public void setTipo(ListadoDetalle tipo) {
		this.tipo = tipo;
	}

	public ListadoDetalle getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(ListadoDetalle tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public ListadoDetalle getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(ListadoDetalle tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public ListadoDetalle getCuentaConformidad() {
		return cuentaConformidad;
	}

	public void setCuentaConformidad(ListadoDetalle cuentaConformidad) {
		this.cuentaConformidad = cuentaConformidad;
	}

	public ListadoDetalle getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(ListadoDetalle tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public ListadoDetalle getPais() {
		return pais;
	}

	public void setPais(ListadoDetalle pais) {
		this.pais = pais;
	}
	public ListadoDetalle getSubSectorDoc() {
		return subSectorDoc;
	}

	public void setSubSectorDoc(ListadoDetalle subSectorDoc) {
		this.subSectorDoc = subSectorDoc;
	}
	
	public ListadoDetalle getActividadArea() {
		return actividadArea;
	}

	public void setActividadArea(ListadoDetalle actividadArea) {
		this.actividadArea = actividadArea;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getCodigoContrato() {
		return codigoContrato;
	}

	public void setCodigoContrato(String codigoContrato) {
		this.codigoContrato = codigoContrato;
	}

	public String getDescripcionContrato() {
		return descripcionContrato;
	}

	public void setDescripcionContrato(String descripcionContrato) {
		this.descripcionContrato = descripcionContrato;
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

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String getFlagVigente() {
		return flagVigente;
	}

	public void setFlagVigente(String flagVigente) {
		this.flagVigente = flagVigente;
	}

	public Date getFechaConformidad() {
		return fechaConformidad;
	}

	public void setFechaConformidad(Date fechaConformidad) {
		this.fechaConformidad = fechaConformidad;
	}

	public Double getMontoContrato() {
		return montoContrato;
	}

	public void setMontoContrato(Double montoContrato) {
		this.montoContrato = montoContrato;
	}

	public Double getMontoTipoCambio() {
		return montoTipoCambio;
	}

	public void setMontoTipoCambio(Double montoTipoCambio) {
		this.montoTipoCambio = montoTipoCambio;
	}

	public ListadoDetalle getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(ListadoDetalle evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Double getMontoContratoSol() {
		return montoContratoSol;
	}

	public void setMontoContratoSol(Double montoContratoSol) {
		this.montoContratoSol = montoContratoSol;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	@Override
	public String toString() {
		return "Documento [idDocumento=" + idDocumento + ", tipo=" + tipo + ", tipoDocumento=" + tipoDocumento
				+ ", tipoCambio=" + tipoCambio + ", cuentaConformidad=" + cuentaConformidad + ", tipoIdentificacion="
				+ tipoIdentificacion + ", pais=" + pais + ", solicitud=" + solicitud
				+ ", numeroDocumento=" + numeroDocumento + ", nombreEntidad=" + nombreEntidad + ", codigoContrato="
				+ codigoContrato + ", descripcionContrato=" + descripcionContrato + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", duracion=" + duracion + ", flagVigente=" + flagVigente
				+ ", fechaConformidad=" + fechaConformidad + ", montoContrato=" + montoContrato + ", montoTipoCambio="
				+ montoTipoCambio + ", evaluacion=" + evaluacion + ", montoContratoSol=" + montoContratoSol
				+ ", observacion=" + observacion + ", archivo=" + archivo + ", subSectorDoc=" + subSectorDoc + "]";
	}

	public Long getIdDocumentoPadre() {
		return idDocumentoPadre;
	}

	public void setIdDocumentoPadre(Long idDocumentoPadre) {
		this.idDocumentoPadre = idDocumentoPadre;
	}

	public Long getFlagSiged() {
		return flagSiged;
	}

	public void setFlagSiged(Long flagSiged) {
		this.flagSiged = flagSiged;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}
	// public Long getFlagUltimo() {
	// return flagUltimo;
	// }
	//
	// public void setFlagUltimo(Long flagUltimo) {
	// this.flagUltimo = flagUltimo;
	// }
}