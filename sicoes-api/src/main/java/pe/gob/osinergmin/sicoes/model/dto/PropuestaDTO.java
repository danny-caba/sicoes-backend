package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class PropuestaDTO {

	private static final long serialVersionUID = 1L;

	private Long idPropuesta;
	
	private String nombre;

	private SupervisoraDTO supervisora;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private Date fechaPresentacion;

	private ListadoDetalleDTO estado;

	private ProcesoItemDTO procesoItem;

	private String propuestaUuid;

	private ListadoDetalleDTO ganador;

	private String decripcionDescarga;
	
	private String rutaDescarga;

	public PropuestaDTO() {
	}

	public Long getIdPropuesta() {
		return idPropuesta;
	}

	public void setIdPropuesta(Long idPropuesta) {
		this.idPropuesta = idPropuesta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public SupervisoraDTO getSupervisora() {
		return supervisora;
	}

	public void setSupervisora(SupervisoraDTO supervisora) {
		this.supervisora = supervisora;
	}
}
