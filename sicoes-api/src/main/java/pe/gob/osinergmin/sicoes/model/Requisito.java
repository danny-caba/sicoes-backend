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
				'}';
	}
}