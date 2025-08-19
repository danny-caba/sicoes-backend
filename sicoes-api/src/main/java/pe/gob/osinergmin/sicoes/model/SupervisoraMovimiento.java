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
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="SICOES_TR_SUPER_MOVIMIENTO")
public class SupervisoraMovimiento extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_SUPER_MOVIMIENTO")
	@SequenceGenerator(name="GEN_SICOES_SEQ_SUPER_MOVIMIENTO", sequenceName = "SICOES_SEQ_SUPER_MOVIMIENTO", allocationSize = 1)
	@Column(name = "ID_SUPER_MOVIMIENTO")	
	private Long idSupervisoraMovimiento;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SECTOR_LD")
	private ListadoDetalle sector;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUBSECTOR_LD")
	private ListadoDetalle subsector;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUPERVISORA")
	private Supervisora supervisora;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")
	private ListadoDetalle estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MOTIVO_LD")
	private ListadoDetalle motivo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_MOTIVO_LD")
	private ListadoDetalle tipoMotivo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ACCION_LD")
	private ListadoDetalle accion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PRO_PROFESIONAL")
	private PropuestaProfesional propuestaProfesional;
	
	@Column(name = "DE_MOTIVO")	
	private String motivoDescripcion;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="FE_REGISTRO")
	private Date fechaRegistro;

	public Long getIdSupervisoraMovimiento() {
		return idSupervisoraMovimiento;
	}

	public ListadoDetalle getSector() {
		return sector;
	}

	public ListadoDetalle getSubsector() {
		return subsector;
	}

	public Supervisora getSupervisora() {
		return supervisora;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public ListadoDetalle getMotivo() {
		return motivo;
	}

	public ListadoDetalle getTipoMotivo() {
		return tipoMotivo;
	}

	public ListadoDetalle getAccion() {
		return accion;
	}

	public PropuestaProfesional getPropuestaProfesional() {
		return propuestaProfesional;
	}

	public String getMotivoDescripcion() {
		return motivoDescripcion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setIdSupervisoraMovimiento(Long idSupervisoraMovimiento) {
		this.idSupervisoraMovimiento = idSupervisoraMovimiento;
	}

	public void setSector(ListadoDetalle sector) {
		this.sector = sector;
	}

	public void setSubsector(ListadoDetalle subsector) {
		this.subsector = subsector;
	}

	public void setSupervisora(Supervisora supervisora) {
		this.supervisora = supervisora;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public void setMotivo(ListadoDetalle motivo) {
		this.motivo = motivo;
	}

	public void setTipoMotivo(ListadoDetalle tipoMotivo) {
		this.tipoMotivo = tipoMotivo;
	}

	public void setAccion(ListadoDetalle accion) {
		this.accion = accion;
	}

	public void setPropuestaProfesional(PropuestaProfesional propuestaProfesional) {
		this.propuestaProfesional = propuestaProfesional;
	}

	public void setMotivoDescripcion(String motivoDescripcion) {
		this.motivoDescripcion = motivoDescripcion;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		return "SupervisoraMovimiento [idSupervisoraMovimiento=" + idSupervisoraMovimiento + ", sector=" + sector
				+ ", subsector=" + subsector + ", supervisora=" + supervisora + ", estado=" + estado + ", motivo="
				+ motivo + ", tipoMotivo=" + tipoMotivo + ", accion=" + accion + ", propuestaProfesional="
				+ propuestaProfesional + ", motivoDescripcion=" + motivoDescripcion + ", fechaRegistro=" + fechaRegistro
				+ "]";
	}

}