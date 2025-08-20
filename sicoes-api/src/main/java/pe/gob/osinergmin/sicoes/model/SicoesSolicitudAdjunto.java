package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SICOES_TD_ADJUNTO_SOLI")
public class SicoesSolicitudAdjunto extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_ADJUNTO_SOLICITUD")
	@SequenceGenerator(name="GEN_SICOES_SEQ_ADJUNTO_SOLICITUD", sequenceName = "SICOES_SEQ_ADJUNTO_SOLICITUD", allocationSize = 1)
	@Column(name = "ID_ADJUNTO_SOLI")	
	private Long idAdjuntoSolicitud;
	
	@Column(name="NO_ARCHIVO")
	private String nombreArchivo;
	
	@Column(name="ES_ADJUNTO_SOLI")
	private String estado;
	
	@Column(name="ID_DET_SOLICITUD")
	private Long idDetalleSolicitud;
	
	@Column(name="NO_ALFRESCO")
	private String nombreAlfresco;
	
	public Long getIdAdjuntoSolicitud() {
		return idAdjuntoSolicitud;
	}

	public void setIdAdjuntoSolicitud(Long idAdjuntoSolicitud) {
		this.idAdjuntoSolicitud = idAdjuntoSolicitud;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getIdDetalleSolicitud() {
		return idDetalleSolicitud;
	}

	public void setIdDetalleSolicitud(Long idDetalleSolicitud) {
		this.idDetalleSolicitud = idDetalleSolicitud;
	}

	public String getNombreAlfresco() {
		return nombreAlfresco;
	}

	public void setNombreAlfresco(String nombreAlfresco) {
		this.nombreAlfresco = nombreAlfresco;
	}

}
