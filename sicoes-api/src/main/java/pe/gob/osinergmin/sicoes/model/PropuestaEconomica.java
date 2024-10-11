package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import pe.gob.osinergmin.sicoes.util.AttributeEncryptor;
import java.text.NumberFormat;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TR_PRO_ECONOMICA")
public class PropuestaEconomica extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PRO_ECONOMICA")
	@SequenceGenerator(name="GEN_SICOES_SEQ_PRO_ECONOMICA", sequenceName = "SICOES_SEQ_PRO_ECONOMICA", allocationSize = 1)
	@Column(name = "ID_PRO_ECONOMICA")	
	private Long idPropuestaEconomica;
	
	@Column(name="DE_IMPORTE")
	@Convert(converter = AttributeEncryptor.class)
	private String importe;
	
	@Column(name="NU_FOLIO_INICIO")	
	private Long folioInicio;
	
	@Column(name="NU_FOLIO_FIN")	
	private Long folioFin;
	
	@Transient
	private String propuestaUuid;
	
	@Transient
	private List<Archivo> archivos;

	public Long getIdPropuestaEconomica() {
		return idPropuestaEconomica;
	}

	public void setIdPropuestaEconomica(Long idPropuestaEconomica) {
		this.idPropuestaEconomica = idPropuestaEconomica;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public Long getFolioInicio() {
		return folioInicio;
	}

	public void setFolioInicio(Long folioInicio) {
		this.folioInicio = folioInicio;
	}

	public Long getFolioFin() {
		return folioFin;
	}

	public void setFolioFin(Long folioFin) {
		this.folioFin = folioFin;
	}

	public List<Archivo> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<Archivo> archivos) {
		this.archivos = archivos;
	}

	@Override
	public String toString() {
		return "PropuestaEconomica [idPropuestaEconomica=" + idPropuestaEconomica + ", importe=" + importe
				+ ", folioInicio=" + folioInicio + ", folioFin=" + folioFin + ", archivos=" + archivos + "]";
	}

	public String getPropuestaUuid() {
		return propuestaUuid;
	}

	public void setPropuestaUuid(String propuestaUuid) {
		this.propuestaUuid = propuestaUuid;
	}
	
}