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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name="SICOES_TR_PROPUESTA")
public class Propuesta extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PROPUESTA")
	@SequenceGenerator(name="GEN_SICOES_SEQ_PROPUESTA", sequenceName = "SICOES_SEQ_PROPUESTA", allocationSize = 1)
	@Column(name = "ID_PROPUESTA")	
	private Long idPropuesta;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_SUPERVISORA")
	private Supervisora supervisora;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	@Column(name="FE_PRESENTACION")
	private Date fechaPresentacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")	
	private ListadoDetalle estado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROCESO_ITEM")
	private ProcesoItem procesoItem;

	@Column(name="CO_UUID")
	private String propuestaUuid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PRO_TECNICA")	
	private PropuestaTecnica propuestaTecnica;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PRO_ECONOMICA")	
	private PropuestaEconomica propuestaEconomica;
	
	@Column(name="DE_DESCARGA")	
	private String decripcionDescarga;
	
	@Column(name="DE_RUTA_DESCARGA")	
	private String rutaDescarga;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_GANADOR_LD")	
	private ListadoDetalle ganador;
	
	@Transient
	public boolean datoProceso;
	@Transient
	public boolean proTecnica;
	@Transient
	public boolean invitarProfesionales;
	@Transient
	public boolean proEconomica;
	@Transient
	public boolean presentarPro;
	
	public Long getIdPropuesta() {
		return idPropuesta;
	}

	public Supervisora getSupervisora() {
		return supervisora;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public ProcesoItem getProcesoItem() {
		return procesoItem;
	}

	public String getPropuestaUuid() {
		return propuestaUuid;
	}

	public PropuestaTecnica getPropuestaTecnica() {
		return propuestaTecnica;
	}

	public PropuestaEconomica getPropuestaEconomica() {
		return propuestaEconomica;
	}

	public ListadoDetalle getGanador() {
		return ganador;
	}

	public void setIdPropuesta(Long idPropuesta) {
		this.idPropuesta = idPropuesta;
	}

	public void setSupervisora(Supervisora supervisora) {
		this.supervisora = supervisora;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public void setProcesoItem(ProcesoItem procesoItem) {
		this.procesoItem = procesoItem;
	}

	public void setPropuestaUuid(String propuestaUuid) {
		this.propuestaUuid = propuestaUuid;
	}

	public void setPropuestaTecnica(PropuestaTecnica propuestaTecnica) {
		this.propuestaTecnica = propuestaTecnica;
	}

	public void setPropuestaEconomica(PropuestaEconomica propuestaEconomica) {
		this.propuestaEconomica = propuestaEconomica;
	}

	public void setGanador(ListadoDetalle ganador) {
		this.ganador = ganador;
	}

	public String getDecripcionDescarga() {
		return decripcionDescarga;
	}

	public void setDecripcionDescarga(String decripcionDescarga) {
		this.decripcionDescarga = decripcionDescarga;
	}

	public String getRutaDescarga() {
		return rutaDescarga;
	}

	public void setRutaDescarga(String rutaDescarga) {
		this.rutaDescarga = rutaDescarga;
	}

	public boolean isDatoProceso() {
		return datoProceso;
	}

	public boolean isProTecnica() {
		return proTecnica;
	}

	public boolean isInvitarProfesionales() {
		return invitarProfesionales;
	}

	public boolean isProEconomica() {
		return proEconomica;
	}

	public boolean isPresentarPro() {
		return presentarPro;
	}

	public void setDatoProceso(boolean datoProceso) {
		this.datoProceso = datoProceso;
	}

	public void setProTecnica(boolean proTecnica) {
		this.proTecnica = proTecnica;
	}

	public void setInvitarProfesionales(boolean invitarProfesionales) {
		this.invitarProfesionales = invitarProfesionales;
	}

	public void setProEconomica(boolean proEconomica) {
		this.proEconomica = proEconomica;
	}

	public void setPresentarPro(boolean presentarPro) {
		this.presentarPro = presentarPro;
	}

	@Override
	public String toString() {
		return "Propuesta [idPropuesta=" + idPropuesta + ", supervisora=" + supervisora + ", fechaPresentacion="
				+ fechaPresentacion + ", estado=" + estado + ", procesoItem=" + procesoItem + ", propuestaUuid="
				+ propuestaUuid + ", propuestaTecnica=" + propuestaTecnica + ", propuestaEconomica="
				+ propuestaEconomica + ", decripcionDescarga=" + decripcionDescarga + ", rutaDescarga=" + rutaDescarga
				+ ", ganador=" + ganador + ", datoProceso=" + datoProceso + ", proTecnica=" + proTecnica
				+ ", invitarProfesionales=" + invitarProfesionales + ", proEconomica=" + proEconomica
				+ ", presentarPro=" + presentarPro + "]";
	}
	
	
}