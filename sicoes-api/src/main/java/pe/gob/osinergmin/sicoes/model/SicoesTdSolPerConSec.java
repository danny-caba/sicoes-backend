package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import pe.gob.osinergmin.sicoes.model.dto.EvaluacionPerfDTO;


/**
 * The persistent class for the listado database table.
 *
 */
@Entity
@Table(name="SICOES_TD_SOL_PER_CON_SEC")
public class SicoesTdSolPerConSec extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_TD_SOL_PER_CON_SEC")
	@SequenceGenerator(name="GEN_SICOES_SEQ_TD_SOL_PER_CON_SEC", sequenceName = "SICOES_SEQ_TD_SOL_PER_CON_SEC", allocationSize = 1)
	@Column(name = "ID_SOL_PER_CON_SEC")
	private Long idSolPerConSec;
	@Column(name="ID_SOLI_PERF_CONT")
	private Long idSoliPerfCont;

	@Column(name="ID_SECCION")
	private Long idSeccion;

	@Column(name="FL_CON_PERSONAL")
	private String flConPersonal;

	@Column(name="DE_SECCION")
	private String deSeccion;

	public String getDeSeccion() {
		return deSeccion;
	}

	public void setDeSeccion(String deSeccion) {
		this.deSeccion = deSeccion;
	}

	@Column(name="TI_SOLICITUD")
	private String tiSolicitud;

	@Column(name="ES_SOLI_PERF_CONT")
	private String esSoliPerfCont;

	public Long getIdSolPerConSec() {
		return idSolPerConSec;
	}

	public void setIdSolPerConSec(Long idSolPerConSec) {
		this.idSolPerConSec = idSolPerConSec;
	}

	public Long getIdSoliPerfCont() {
		return idSoliPerfCont;
	}

	public void setIdSoliPerfCont(Long idSoliPerfCont) {
		this.idSoliPerfCont = idSoliPerfCont;
	}

	public Long getIdSeccion() {
		return idSeccion;
	}

	public void setIdSeccion(Long idSeccion) {
		this.idSeccion = idSeccion;
	}

	public String getFlConPersonal() {
		return flConPersonal;
	}

	public void setFlConPersonal(String flConPersonal) {
		this.flConPersonal = flConPersonal;
	}

	public String getTiSolicitud() {
		return tiSolicitud;
	}

	public void setTiSolicitud(String tiSolicitud) {
		this.tiSolicitud = tiSolicitud;
	}

	public String getEsSoliPerfCont() {
		return esSoliPerfCont;
	}

	public void setEsSoliPerfCont(String esSoliPerfCont) {
		this.esSoliPerfCont = esSoliPerfCont;
	}

}