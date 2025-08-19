package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TD_CONTRATO")
public class Contrato extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_TD_CONTRATO")
	@SequenceGenerator(name="GEN_SICOES_SEQ_TD_CONTRATO", sequenceName = "SICOES_SEQ_TD_CONTRATO", allocationSize = 1)
	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SOLI_PERF_CONT")
	private SicoesSolicitud solicitudPerfCont;
	
	@Column(name="ID_CONTRATO_PADRE")
	private Long idContratoPadre;
	
	@Column(name="NU_CONTRATO_SAP")
	private Long numeroContrato;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_SUSCRIPCION_CONTRATO")
	private Date fechaSuscripcionContrato;

	@Column(name="CO_AREA")
	private String codigoArea;

	@Column(name="NO_AREA")
	private String nombreArea;

	@Column(name="ES_CONTRATO")
	private String estadoContrato;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_INICIO_CONTRATO")
	private Date fechaInicioContrato;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_FINAL_CONTRATO")
	private Date fechaFinalContrato;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")
	private ListadoDetalle estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_EVAL_LOG_LD")
	private ListadoDetalle estadoEvalLog;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_EVAL_GAF_LD")
	private ListadoDetalle estadoEvalGaf;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_EVAL_UNI_LD")
	private ListadoDetalle estadoEvalUni;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_EVAL_APR_LD")
	private ListadoDetalle estadoEvalApr;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_EVAL_LD")
	private ListadoDetalle idEstadoEval;
	
    @Transient
    private List<Long> ids;

    @Transient
    private String accion;

    @Transient
    private String observacion;

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public SicoesSolicitud getSolicitudPerfCont() {
		return solicitudPerfCont;
	}

	public void setSolicitudPerfCont(SicoesSolicitud solicitudPerfCont) {
		this.solicitudPerfCont = solicitudPerfCont;
	}

	public Long getIdContratoPadre() {
		return idContratoPadre;
	}

	public void setIdContratoPadre(Long idContratoPadre) {
		this.idContratoPadre = idContratoPadre;
	}

	public Long getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(Long numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public Date getFechaSuscripcionContrato() {
		return fechaSuscripcionContrato;
	}

	public void setFechaSuscripcionContrato(Date fechaSuscripcionContrato) {
		this.fechaSuscripcionContrato = fechaSuscripcionContrato;
	}

	public String getCodigoArea() {
		return codigoArea;
	}

	public void setCodigoArea(String codigoArea) {
		this.codigoArea = codigoArea;
	}

	public String getNombreArea() {
		return nombreArea;
	}

	public void setNombreArea(String nombreArea) {
		this.nombreArea = nombreArea;
	}

	public String getEstadoContrato() {
		return estadoContrato;
	}

	public void setEstadoContrato(String estadoContrato) {
		this.estadoContrato = estadoContrato;
	}

	public Date getFechaInicioContrato() {
		return fechaInicioContrato;
	}

	public void setFechaInicioContrato(Date fechaInicioContrato) {
		this.fechaInicioContrato = fechaInicioContrato;
	}

	public Date getFechaFinalContrato() {
		return fechaFinalContrato;
	}

	public void setFechaFinalContrato(Date fechaFinalContrato) {
		this.fechaFinalContrato = fechaFinalContrato;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public ListadoDetalle getEstadoEvalLog() {
		return estadoEvalLog;
	}

	public void setEstadoEvalLog(ListadoDetalle estadoEvalLog) {
		this.estadoEvalLog = estadoEvalLog;
	}

	public ListadoDetalle getEstadoEvalGaf() {
		return estadoEvalGaf;
	}

	public void setEstadoEvalGaf(ListadoDetalle estadoEvalGaf) {
		this.estadoEvalGaf = estadoEvalGaf;
	}

	public ListadoDetalle getEstadoEvalUni() {
		return estadoEvalUni;
	}

	public void setEstadoEvalUni(ListadoDetalle estadoEvalUni) {
		this.estadoEvalUni = estadoEvalUni;
	}

	public ListadoDetalle getEstadoEvalApr() {
		return estadoEvalApr;
	}

	public void setEstadoEvalApr(ListadoDetalle estadoEvalApr) {
		this.estadoEvalApr = estadoEvalApr;
	}

	public ListadoDetalle getIdEstadoEval() {
		return idEstadoEval;
	}

	public void setIdEstadoEval(ListadoDetalle idEstadoEval) {
		this.idEstadoEval = idEstadoEval;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

}