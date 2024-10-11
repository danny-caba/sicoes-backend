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
@Table(name="SICOES_TR_PROCESO_ETAPA")
public class ProcesoEtapa extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PROCESO_ETAPA")
	@SequenceGenerator(name="GEN_SICOES_SEQ_PROCESO_ETAPA", sequenceName = "SICOES_SEQ_PROCESO_ETAPA", allocationSize = 1)
	@Column(name = "ID_PROCESO_ETAPA")	
	private Long idProcesoEtapa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ETAPA_LD")	
	private ListadoDetalle etapa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROCESO")
	private Proceso proceso;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_INICIO")
	private Date fechaInicio;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_FIN")
	private Date fechaFin;

	public Long getIdProcesoEtapa() {
		return idProcesoEtapa;
	}

	public void setIdProcesoEtapa(Long idProcesoEtapa) {
		this.idProcesoEtapa = idProcesoEtapa;
	}

	public ListadoDetalle getEtapa() {
		return etapa;
	}

	public void setEtapa(ListadoDetalle etapa) {
		this.etapa = etapa;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public String toString() {
		return "ProcesoEtapa [idProcesoEtapa=" + idProcesoEtapa + ", etapa=" + etapa + ", proceso=" + proceso
				+ ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + "]";
	}

}