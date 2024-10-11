package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;

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


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TM_SUPER_DICTAMEN")
public class SupervisoraDictamen extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_SUPER_DICTAMEN")
	@SequenceGenerator(name="GEN_SICOES_SEQ_SUPER_DICTAMEN", sequenceName = "SICOES_SEQ_SUPER_DICTAMEN", allocationSize = 1)
	@Column(name = "ID_SUPER_DICTAMEN")	
	private Long idSupervisoraDictamen;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUPERVISORA")
	private Supervisora supervisora;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTOR_LD")	
	private ListadoDetalle sector;
	
	@Column(name="MO_FACTURADO")	
	private Double montoFacturado;

	public Long getIdSupervisoraDictamen() {
		return idSupervisoraDictamen;
	}

	public Supervisora getSupervisora() {
		return supervisora;
	}

	public ListadoDetalle getSector() {
		return sector;
	}

	public Double getMontoFacturado() {
		return montoFacturado;
	}

	public void setIdSupervisoraDictamen(Long idSupervisoraDictamen) {
		this.idSupervisoraDictamen = idSupervisoraDictamen;
	}

	public void setSupervisora(Supervisora supervisora) {
		this.supervisora = supervisora;
	}

	public void setSector(ListadoDetalle sector) {
		this.sector = sector;
	}

	public void setMontoFacturado(Double montoFacturado) {
		this.montoFacturado = montoFacturado;
	}

	@Override
	public String toString() {
		return "SupervisoraDictamen [idSupervisoraDictamen=" + idSupervisoraDictamen + ", supervisora=" + supervisora
				+ ", sector=" + sector + ", montoFacturado=" + montoFacturado + "]";
	}

}