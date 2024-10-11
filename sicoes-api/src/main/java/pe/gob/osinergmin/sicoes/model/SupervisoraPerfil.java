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

@Entity
@Table(name="SICOES_TM_SUPER_PERFIL")
public class SupervisoraPerfil extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_SUPERVISORA_PERFIL")
	@SequenceGenerator(name="GEN_SICOES_SEQ_SUPERVISORA_PERFIL", sequenceName = "SICOES_SEQ_SUPERVISORA_PERFIL", allocationSize = 1)
	@Column(name = "ID_SUPER_PERFIL")	
	private Long idSupervisoraPerfil;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUPERVISORA")	
	private Supervisora supervisora;
	
	@Column(name="NU_EXPEDIENTE")	
	private String numeroExpediente;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTOR_LD")	
	private ListadoDetalle sector;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUBSECTOR_LD")	
	private ListadoDetalle subsector;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ACTIVIDAD_LD")	
	private ListadoDetalle actividad;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUBCATEGORIA_LD")	
	private ListadoDetalle subCategoria;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_UNIDAD_LD")	
	private ListadoDetalle unidad;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERFIL_LD")	
	private ListadoDetalle perfil;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_INGRESO")
	private Date fechaIngreso;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")	
	private ListadoDetalle estado;

	public Long getIdSupervisoraPerfil() {
		return idSupervisoraPerfil;
	}

	public Supervisora getSupervisora() {
		return supervisora;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public ListadoDetalle getSector() {
		return sector;
	}

	public ListadoDetalle getSubsector() {
		return subsector;
	}

	public ListadoDetalle getActividad() {
		return actividad;
	}

	public ListadoDetalle getSubCategoria() {
		return subCategoria;
	}

	public ListadoDetalle getUnidad() {
		return unidad;
	}

	public ListadoDetalle getPerfil() {
		return perfil;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

//	public Double getMontoFacturado() {
//		return montoFacturado;
//	}

	public void setIdSupervisoraPerfil(Long idSupervisoraPerfil) {
		this.idSupervisoraPerfil = idSupervisoraPerfil;
	}

	public void setSupervisora(Supervisora supervisora) {
		this.supervisora = supervisora;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public void setSector(ListadoDetalle sector) {
		this.sector = sector;
	}

	public void setSubsector(ListadoDetalle subsector) {
		this.subsector = subsector;
	}

	public void setActividad(ListadoDetalle actividad) {
		this.actividad = actividad;
	}

	public void setSubCategoria(ListadoDetalle subCategoria) {
		this.subCategoria = subCategoria;
	}

	public void setUnidad(ListadoDetalle unidad) {
		this.unidad = unidad;
	}

	public void setPerfil(ListadoDetalle perfil) {
		this.perfil = perfil;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

//	public void setMontoFacturado(Double montoFacturado) {
//		this.montoFacturado = montoFacturado;
//	}
//	
	
}
