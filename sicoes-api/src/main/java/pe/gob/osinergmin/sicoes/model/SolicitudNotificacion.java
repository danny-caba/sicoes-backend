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
@Table(name="SICOES_TR_SOL_NOTIFICACION")
public class SolicitudNotificacion  extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_SOL_NOTIFICACION")
	@SequenceGenerator(name="GEN_SICOES_SEQ_SOL_NOTIFICACION", sequenceName = "SICOES_SEQ_SOL_NOTIFICACION", allocationSize = 1)
	@Column(name = "ID_SOL_NOTIFICACION")	
	private Long idSolNotificacion;
	
	@Column(name="ID_SOLICITUD")	
	private Long idSolicitud;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_LD")	
	private ListadoDetalle tipo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ESTADO_LD")	
	private ListadoDetalle estado;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_NOTIFICACION")
	private Date fechaNotificacion;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="FE_REGISTRO")
	private Date fechaRegistro;
	
	@Column(name="DE_OBSERVACION")
	private String observacion;
	
	
	@Transient
	private Archivo archivo;
	
	@Transient
	private String solicitudUuid;



	public ListadoDetalle getTipo() {
		return tipo;
	}

	public void setTipo(ListadoDetalle tipo) {
		this.tipo = tipo;
	}

	public ListadoDetalle getEstado() {
		return estado;
	}

	public void setEstado(ListadoDetalle estado) {
		this.estado = estado;
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Long getIdSolNotificacion() {
		return idSolNotificacion;
	}

	public void setIdSolNotificacion(Long idSolNotificacion) {
		this.idSolNotificacion = idSolNotificacion;
	}

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	public String getSolicitudUuid() {
		return solicitudUuid;
	}

	public void setSolicitudUuid(String solicitudUuid) {
		this.solicitudUuid = solicitudUuid;
	}
	
}
