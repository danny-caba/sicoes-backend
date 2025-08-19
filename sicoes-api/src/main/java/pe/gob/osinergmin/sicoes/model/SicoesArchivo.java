package pe.gob.osinergmin.sicoes.model;

import java.io.Serializable;

//import javax.persistence.Entity;
//import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

//@Entity
public class SicoesArchivo extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idArchivo;
	
	private String descripcion;
	
	//@Transient
	private MultipartFile file;
	
	//@Transient
	private byte[] contenido;
	
	//NECESARIOS PARA SUBIR A ALFRESCO *******
	private Long idPropuesta;
	private Long idProceso;
	private Long idSolicitud;
	private String nombreAlFresco;
	//****************************************

	public Long getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(Long idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getContenido() {
		return contenido;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Long getIdPropuesta() {
		return idPropuesta;
	}

	public void setIdPropuesta(Long idPropuesta) {
		this.idPropuesta = idPropuesta;
	}

	public Long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getNombreAlFresco() {
		return nombreAlFresco;
	}

	public void setNombreAlFresco(String nombreAlFresco) {
		this.nombreAlFresco = nombreAlFresco;
	}

}
