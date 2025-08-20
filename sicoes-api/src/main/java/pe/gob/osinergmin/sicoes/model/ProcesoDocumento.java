package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name="SICOES_TR_PROCESO_DOCUMENTO")
public class ProcesoDocumento extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_SICOES_SEQ_PROCESO_DOCUMENTO")
	@SequenceGenerator(name="GEN_SICOES_SEQ_PROCESO_DOCUMENTO", sequenceName = "SICOES_SEQ_PROCESO_DOCUMENTO", allocationSize = 1)
	@Column(name = "ID_PROCESO_DOCUMENTO")	
	private Long idProcesoDocumento;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ETAPA_LD")	
	private ListadoDetalle etapa;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ARCHIVO")
	private Archivo archivo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PROCESO")
	private Proceso proceso;
	
	@Column(name = "DOCUMENT_NAME")
	private String documentName;
	
	@Column(name = "FEC_PUBLICACION")
	private LocalDateTime fechaPublicacion;
			
	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Long getIdProcesoDocumento() {
		return idProcesoDocumento;
	}

	public void setIdProcesoDocumento(Long idProcesoDocumento) {
		this.idProcesoDocumento = idProcesoDocumento;
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

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	@Override
	public String toString() {
		return "ProcesoDocumento [idProcesoDocumento=" + idProcesoDocumento + ", etapa=" + etapa + ", archivo="
				+ archivo + ", proceso=" + proceso + ", documentName=" + documentName + "]";
	}
	
	
}