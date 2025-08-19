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
import javax.persistence.Transient;

@Entity
@Table(name="SICOES_TR_PRO_CONSORCIO")
public class PropuestaConsorcio extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PRO_CONSORCIO")
	@SequenceGenerator(name="GEN_SICOES_SEQ_PRO_CONSORCIO", sequenceName = "SICOES_SEQ_PRO_CONSORCIO", allocationSize = 1)
	@Column(name = "ID_PRO_CONSORCIO")
	private Long idPropuestaConsorcio;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PRO_TECNICA")	
	private PropuestaTecnica propuestaTecnica;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUPERVISORA")
	private Supervisora supervisora;
	
	@Column(name="PARTICIPACION")
	private Double participacion;
	
	@Transient
	public Double facturacion;

	public Long getIdPropuestaConsorcio() {
		return idPropuestaConsorcio;
	}

	public void setIdPropuestaConsorcio(Long idPropuestaConsorcio) {
		this.idPropuestaConsorcio = idPropuestaConsorcio;
	}

	public PropuestaTecnica getPropuestaTecnica() {
		return propuestaTecnica;
	}

	public void setPropuestaTecnica(PropuestaTecnica propuestaTecnica) {
		this.propuestaTecnica = propuestaTecnica;
	}

	public Supervisora getSupervisora() {
		return supervisora;
	}

	public void setSupervisora(Supervisora supervisora) {
		this.supervisora = supervisora;
	}

	public Double getParticipacion() {
		return participacion;
	}

	public void setParticipacion(Double participacion) {
		this.participacion = participacion;
	}

	public Double getFacturacion() {
		return facturacion;
	}

	public void setFacturacion(Double facturacion) {
		this.facturacion = facturacion;
	}

	@Override
	public String toString() {
		return "PropuestaConsorcio [idPropuestaConsorcio=" + idPropuestaConsorcio + ", propuestaTecnica="
				+ propuestaTecnica + ", supervisora=" + supervisora + ", participacion=" + participacion
				+ ", facturacion=" + facturacion + "]";
	}
}
