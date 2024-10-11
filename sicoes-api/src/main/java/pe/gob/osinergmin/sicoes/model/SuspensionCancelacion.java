package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="SICOES_TR_SUSPEN_CANCELACION")
public class SuspensionCancelacion extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_SUSPEN_CANCELACION")
	@SequenceGenerator(name="GEN_SICOES_SEQ_SUSPEN_CANCELACION", sequenceName = "SICOES_SEQ_SUSPEN_CANCELACION", allocationSize = 1)
	@Column(name = "ID_SUSPEN_CANCELACION")	
	private Long idSuspensionCancelacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUPERVISORA")
	private Supervisora supervisora;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_LD")	
	private ListadoDetalle tipo;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_INICIO")
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_FIN")
	private Date fechaFin;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")	
	private ListadoDetalle estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CAUSAL_LD")	
	private ListadoDetalle causal;
	
	@Column(name="DE_SUSTENTO")	
	private String sustento;
	
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_SUSPEN_CANCELACION")
	private Date fechaSuspensionCancelacion;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_ACTIVACION")
	private Date fechaActivacion;
	

	public Long getIdSuspensionCancelacion() {
		return idSuspensionCancelacion;
	}

	public void setIdSuspensionCancelacion(Long idSuspensionCancelacion) {
		this.idSuspensionCancelacion = idSuspensionCancelacion;
	}

	public Supervisora getSupervisora() {
		return supervisora;
	}

	public void setSupervisora(Supervisora supervisora) {
		this.supervisora = supervisora;
	}

	public ListadoDetalle getTipo() {
		return tipo;
	}

	public void setTipo(ListadoDetalle tipo) {
		this.tipo = tipo;
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

	public ListadoDetalle getEstado() {
		return estado;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public ListadoDetalle getCausal() {
		return causal;
	}

	public void setCausal(ListadoDetalle causal) {
		this.causal = causal;
	}

	public String getSustento() {
		return sustento;
	}

	public void setSustento(String sustento) {
		this.sustento = sustento;
	}

	public Date getFechaSuspensionCancelacion() {
		return fechaSuspensionCancelacion;
	}

	public void setFechaSuspensionCancelacion(Date fechaSuspensionCancelacion) {
		this.fechaSuspensionCancelacion = fechaSuspensionCancelacion;
	}

	public Date getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	

	
}