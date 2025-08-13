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
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="SICOES_TD_SOLI_PERS_PROP")
public class SicoesTdSoliPersProp extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_TD_SOLI_PERS_PROP")
	@SequenceGenerator(name="GEN_SICOES_SEQ_TD_SOLI_PERS_PROP", sequenceName = "SICOES_SEQ_TD_SOLI_PERS_PROP", allocationSize = 1)
	@Column(name = "ID_SOLI_PERS_PROP")	
	private Long idSoliPersProp;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ID_PERS_SUPER")
	private Supervisora supervisora;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SOL_PER_CON_SEC")
	private SicoesTdSolPerConSec sicoesTdSolPerConSec;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERFIL_LD")
	private ListadoDetalle perfil;

	@Column(name="DE_PERFIL_PROFESIONAL")	
	private String dePerfilProfesional;

	@Column(name="TI_SOLICITUD")
	private String tiSolicitud;
	 
	@Column(name="ES_ADJUNTO_SOLICITUD")
	private String  esAdjuntoSolicitud;

	@Transient
	private Long idRequisito;

	@Transient
	private List<Archivo> archivos;

	@Transient
	private SicoesSolicitudSeccion requisito;

	@Transient
	private String evaluacion;

	@Transient
	private String procRevision;

	
	public Long getIdSoliPersProp() {
		return idSoliPersProp;
	}

	public void setIdSoliPersProp(Long idSoliPersProp) {
		this.idSoliPersProp = idSoliPersProp;
	}

	public SicoesTdSolPerConSec getSicoesTdSolPerConSec() {
		return sicoesTdSolPerConSec;
	}

	public void setSicoesTdSolPerConSec(SicoesTdSolPerConSec sicoesTdSolPerConSec) {
		this.sicoesTdSolPerConSec = sicoesTdSolPerConSec;
	}

	public ListadoDetalle getPerfil(){
		return perfil;
	}

	public void setPerfil(ListadoDetalle perfil){
		this.perfil = perfil;
	}

	public String getDePerfilProfesional() {
		return dePerfilProfesional;
	}


	public void setDePerfilProfesional(String dePerfilProfesional) {
		this.dePerfilProfesional = dePerfilProfesional;
	}


	public String getTiSolicitud() {
		return tiSolicitud;
	}


	public void setTiSolicitud(String tiSolicitud) {
		this.tiSolicitud = tiSolicitud;
	}


	public String getEsAdjuntoSolicitud() {
		return esAdjuntoSolicitud;
	}


	public void setEsAdjuntoSolicitud(String esAdjuntoSolicitud) {
		this.esAdjuntoSolicitud = esAdjuntoSolicitud;
	}

	public Supervisora getSupervisora() {
		return supervisora;
	}

	public void setSupervisora(Supervisora supervisora) {
		this.supervisora = supervisora;
	}

	public List<Archivo> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<Archivo> archivos) {
		this.archivos = archivos;
	}

	public Long getIdRequisito() {
		return idRequisito;
	}

	public void setIdRequisito(Long idRequisito) {
		this.idRequisito = idRequisito;
	}

	public String getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}

	public String getProcRevision() {
		return procRevision;
	}

	public void setProcRevision(String procRevision) {
		this.procRevision = procRevision;
	}

	public SicoesSolicitudSeccion getRequisito() {
		return requisito;
	}

	public void setRequisito(SicoesSolicitudSeccion requisito) {
		this.requisito = requisito;
	}


}