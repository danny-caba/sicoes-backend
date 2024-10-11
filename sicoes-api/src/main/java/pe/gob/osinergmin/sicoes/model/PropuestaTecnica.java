package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
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
import javax.persistence.Transient;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TR_PRO_TECNICA")
public class PropuestaTecnica extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PRO_TECNICA")
	@SequenceGenerator(name="GEN_SICOES_SEQ_PRO_TECNICA", sequenceName = "SICOES_SEQ_PRO_TECNICA", allocationSize = 1)
	@Column(name = "ID_PRO_TECNICA")	
	private Long idPropuestaTecnica;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONSORCIO_LD")
	private ListadoDetalle consorcio;
	
	@Transient
	private String propuestaUuid;
	
	
	@Transient
	private List<Archivo> archivos;

	public Long getIdPropuestaTecnica() {
		return idPropuestaTecnica;
	}

	public ListadoDetalle getConsorcio() {
		return consorcio;
	}

	public List<Archivo> getArchivos() {
		return archivos;
	}

	public void setIdPropuestaTecnica(Long idPropuestaTecnica) {
		this.idPropuestaTecnica = idPropuestaTecnica;
	}



	public void setConsorcio(ListadoDetalle consorcio) {
		this.consorcio = consorcio;
	}

	public void setArchivos(List<Archivo> archivos) {
		this.archivos = archivos;
	}

	@Override
	public String toString() {
		return "PropuestaTecnica [idPropuestaTecnica=" + idPropuestaTecnica + ", consorcio=" + consorcio + ", archivos="
				+ archivos + "]";
	}

	public String getPropuestaUuid() {
		return propuestaUuid;
	}

	public void setPropuestaUuid(String propuestaUuid) {
		this.propuestaUuid = propuestaUuid;
	}

	
	
}