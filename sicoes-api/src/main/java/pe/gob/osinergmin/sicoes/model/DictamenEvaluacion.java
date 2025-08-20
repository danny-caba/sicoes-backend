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
@Table(name="SICOES_TR_DICTAMEN_EVAL")
public class DictamenEvaluacion extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_DICTAMEN_EVAL")
	@SequenceGenerator(name="GEN_SICOES_SEQ_DICTAMEN_EVAL", sequenceName = "SICOES_SEQ_DICTAMEN_EVAL", allocationSize = 1)
	@Column(name = "ID_DICTAMEN_EVAL")	
	private Long idDictamenEvaluacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SOLICITUD")
	private Solicitud solicitud;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTOR_LD")	
	private ListadoDetalle sector;
	
	@Column(name="MO_FACTURADO")	
	private Double montoFacturado;

	public Long getIdDictamenEvaluacion() {
		return idDictamenEvaluacion;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public ListadoDetalle getSector() {
		return sector;
	}

	public Double getMontoFacturado() {
		return montoFacturado;
	}

	public void setIdDictamenEvaluacion(Long idDictamenEvaluacion) {
		this.idDictamenEvaluacion = idDictamenEvaluacion;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public void setSector(ListadoDetalle sector) {
		this.sector = sector;
	}

	public void setMontoFacturado(Double montoFacturado) {
		this.montoFacturado = montoFacturado;
	}

	@Override
	public String toString() {
		return "DictamenEvaluacion [idDictamenEvaluacion=" + idDictamenEvaluacion + ", solicitud=" + solicitud
				+ ", sector=" + sector + ", montoFacturado=" + montoFacturado + "]";
	}
}