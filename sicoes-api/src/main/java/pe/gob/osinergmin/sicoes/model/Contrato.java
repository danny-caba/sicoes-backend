package pe.gob.osinergmin.sicoes.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


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
	private String numeroContrato;

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

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
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
}