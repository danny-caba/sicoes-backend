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
@Table(name="SICOES_TM_SECCION")
public class Seccion extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SQ_SECCION")
	@SequenceGenerator(name="GEN_SICOES_SQ_SECCION", sequenceName = "SICOES_SEQ_SECCION", allocationSize = 1)
	@Column(name = "ID_SECCION")	
	private Long idSeccion;
	
	@Column(name="DE_SECCION")	
	private String deSeccion;
	
	@Column(name="ES_SECCION")	
	private String esSeccion;
	
	@Column(name="FL_REQ_PERSONAL")	
	private String flReqPersonal;
	
	@Column(name="CO_SECCION")	
	private String coSeccion;
	
	public String getFlReqPersonal() {
		return flReqPersonal;
	}


	public void setFlReqPersonal(String flReqPersonal) {
		this.flReqPersonal = flReqPersonal;
	}


	public String getCoSeccion() {
		return coSeccion;
	}


	public void setCoSeccion(String coSeccion) {
		this.coSeccion = coSeccion;
	}

	
	public Long getIdSeccion() {
		return idSeccion;
	}


	public void setIdSeccion(Long idSeccion) {
		this.idSeccion = idSeccion;
	}


	public String getDeSeccion() {
		return deSeccion;
	}


	public void setDeSeccion(String deSeccion) {
		this.deSeccion = deSeccion;
	}


	public String getEsSeccion() {
		return esSeccion;
	}


	public void setEsSeccion(String esSeccion) {
		this.esSeccion = esSeccion;
	}

 
  
 
	 
}