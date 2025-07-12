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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TM_SECCION_REQUISITO")
public class Requisito extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SQ_SECCION_REQUISITO")
	@SequenceGenerator(name="GEN_SICOES_SQ_SECCION_REQUISITO", sequenceName = "SICOES_SEQ_SECCION_REQUISITO", allocationSize = 1)
	@Column(name = "ID_SECCION_REQUISITO")	
	private Long idSeccionRequisito;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECCION")
	private Seccion seccion;
	
	@Column(name="CO_REQUISITO")	
	private String coRequisito;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_DATO_LD")
	private ListadoDetalle tipoDato;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_DATO_ENTRADA_LD")
	private ListadoDetalle tipoDatoEntrada;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_CONTRATO_LD")
	private ListadoDetalle tipoContrato;
	
	@Column(name="DE_SECCION_REQUISITO")	
	private String deSeccionRequisito;
	
	@Column(name="ES_SECCION_REQUISITO")	
	private String esSeccionRequisito;

	@Column(name="FL_CONFORMA_CONSORCIO")
	private String flagConformaConsorcio;

	@Column(name="FL_REMYPE")
	private String flagRemype;

	@Column(name="FL_VISIBLE_FIEL_CUMPLIMIENTO")
	private String flagVisibleFielCumplimiento;

	@Column(name="FL_VISIBLE_RETENCION")
	private String flagVisibleRetencion;

	@Column(name="FL_VISIBLE_SUPERA_PROPUESTA")
	private String flagVisibleSuperaPropuesta;

	public Long getIdSeccionRequisito() {
		return idSeccionRequisito;
	}

	public void setIdSeccionRequisito(Long idSeccionRequisito) {
		this.idSeccionRequisito = idSeccionRequisito;
	}

	public Seccion getSeccion() {
		return seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}

	public String getCoRequisito() {
		return coRequisito;
	}

	public void setCoRequisito(String coRequisito) {
		this.coRequisito = coRequisito;
	}

	public ListadoDetalle getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(ListadoDetalle tipoDato) {
		this.tipoDato = tipoDato;
	}

	public ListadoDetalle getTipoDatoEntrada() {
		return tipoDatoEntrada;
	}

	public void setTipoDatoEntrada(ListadoDetalle tipoDatoEntrada) {
		this.tipoDatoEntrada = tipoDatoEntrada;
	}

	public ListadoDetalle getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(ListadoDetalle tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public String getDeSeccionRequisito() {
		return deSeccionRequisito;
	}

	public void setDeSeccionRequisito(String deSeccionRequisito) {
		this.deSeccionRequisito = deSeccionRequisito;
	}

	public String getEsSeccionRequisito() {
		return esSeccionRequisito;
	}

	public void setEsSeccionRequisito(String esSeccionRequisito) {
		this.esSeccionRequisito = esSeccionRequisito;
	}

	public String getFlagConformaConsorcio() {
		return flagConformaConsorcio;
	}

	public void setFlagConformaConsorcio(String flagConformaConsorcio) {
		this.flagConformaConsorcio = flagConformaConsorcio;
	}

	public String getFlagRemype() {
		return flagRemype;
	}

	public void setFlagRemype(String flagRemype) {
		this.flagRemype = flagRemype;
	}

	public String getFlagVisibleFielCumplimiento() {
		return flagVisibleFielCumplimiento;
	}

	public void setFlagVisibleFielCumplimiento(String flagVisibleFielCumplimiento) {
		this.flagVisibleFielCumplimiento = flagVisibleFielCumplimiento;
	}

	public String getFlagVisibleRetencion() {
		return flagVisibleRetencion;
	}

	public void setFlagVisibleRetencion(String flagVisibleRetencion) {
		this.flagVisibleRetencion = flagVisibleRetencion;
	}

	public String getFlagVisibleSuperaPropuesta() {
		return flagVisibleSuperaPropuesta;
	}

	public void setFlagVisibleSuperaPropuesta(String flagVisibleSuperaPropuesta) {
		this.flagVisibleSuperaPropuesta = flagVisibleSuperaPropuesta;
	}

	@Override
	public String toString() {
		return "Requisito{" +
				"idSeccionRequisito=" + idSeccionRequisito +
				", seccion=" + seccion +
				", coRequisito='" + coRequisito + '\'' +
				", tipoDato=" + tipoDato +
				", tipoDatoEntrada=" + tipoDatoEntrada +
				", tipoContrato=" + tipoContrato +
				", deSeccionRequisito='" + deSeccionRequisito + '\'' +
				", esSeccionRequisito='" + esSeccionRequisito + '\'' +
				", flagConformaConsorcio='" + flagConformaConsorcio + '\'' +
				", flagRemype='" + flagRemype + '\'' +
				", flagVisibleFielCumplimiento='" + flagVisibleFielCumplimiento + '\'' +
				", flagVisibleRetencion='" + flagVisibleRetencion + '\'' +
				", flagVisibleSuperaPropuesta='" + flagVisibleSuperaPropuesta + '\'' +
				'}';
	}
}