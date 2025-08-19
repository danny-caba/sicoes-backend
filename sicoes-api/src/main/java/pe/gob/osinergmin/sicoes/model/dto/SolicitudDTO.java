package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitudDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String uuid;
	private String descripcionSolicitud;
    private PropuestaDTO propuesta;
    private Long idSolicitud;


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getDescripcionSolicitud() {
        return descripcionSolicitud;
    }

    public void setDescripcionSolicitud(String descripcionSolicitud) {
        this.descripcionSolicitud = descripcionSolicitud;
    }

    public PropuestaDTO getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(PropuestaDTO propuesta) {
        this.propuesta = propuesta;
    }

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
    
    
}
