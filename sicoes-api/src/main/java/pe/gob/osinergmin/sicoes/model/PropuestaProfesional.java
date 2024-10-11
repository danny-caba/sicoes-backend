package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.util.Arrays;
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

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the listado database table.
 * 
 */
@Entity
@Table(name="SICOES_TR_PRO_PROFESIONAL")
public class PropuestaProfesional extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PROCESO")
	@SequenceGenerator(name="GEN_SICOES_SEQ_PROCESO", sequenceName = "SICOES_SEQ_PROCESO", allocationSize = 1)
	@Column(name = "ID_PRO_PROFESIONAL")	
	private Long idPropuestaProfesional;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROPUESTA")
	private Propuesta propuesta;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUPERVISORA")
	private Supervisora supervisora;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")	
	private ListadoDetalle estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTOR_LD")	
	private ListadoDetalle sector;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUBSECTOR_LD")	
	private ListadoDetalle subsector;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERFIL_LD")	
	private ListadoDetalle perfil;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="FE_INVITACION")
	private Date fechaInvitacion;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_RESPUESTA")
	private Date fechaRespuesta;

	public Long getIdPropuestaProfesional() {
		return idPropuestaProfesional;
	}

	public void setIdPropuestaProfesional(Long idPropuestaProfesional) {
		this.idPropuestaProfesional = idPropuestaProfesional;
	}

	public Propuesta getPropuesta() {
		return propuesta;
	}

	public void setPropuesta(Propuesta propuesta) {
		this.propuesta = propuesta;
	}

	public Supervisora getSupervisora() {
		return supervisora;
	}

	public void setSupervisora(Supervisora supervisora) {
		this.supervisora = supervisora;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public ListadoDetalle getSector() {
		return sector;
	}

	public void setSector(ListadoDetalle sector) {
		this.sector = sector;
	}

	public ListadoDetalle getSubsector() {
		return subsector;
	}

	public void setSubsector(ListadoDetalle subsector) {
		this.subsector = subsector;
	}

	public ListadoDetalle getPerfil() {
		return perfil;
	}

	public void setPerfil(ListadoDetalle perfil) {
		this.perfil = perfil;
	}

	public Date getFechaInvitacion() {
		return fechaInvitacion;
	}

	public void setFechaInvitacion(Date fechaInvitacion) {
		this.fechaInvitacion = fechaInvitacion;
	}

	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}


	@Override
	public String toString() {
		return "PropuestaProfesional [idPropuestaProfesional=" + idPropuestaProfesional + ", propuesta=" + propuesta
				+ ", supervisora=" + supervisora + ", estado=" + estado + ", sector=" + sector + ", subsector="
				+ subsector + ", perfil=" + perfil + ", fechaInvitacion=" + fechaInvitacion + ", fechaRespuesta="
				+ fechaRespuesta+  "]";
	}
}