package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

public class ReniecDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String deResultado;
	private String coResultado;
	private ReniecPersonaDTO datosPersona;

	public ReniecPersonaDTO getDatosPersona() {
		return datosPersona;
	}

	public void setDatosPersona(ReniecPersonaDTO datosPersona) {
		this.datosPersona = datosPersona;
	}

	@Override
	public String toString() {
		return "ReniecDTO [datosPersona=" + datosPersona + "]";
	}

	public String getDeResultado() {
		return deResultado;
	}

	public void setDeResultado(String deResultado) {
		this.deResultado = deResultado;
	}

	public String getCoResultado() {
		return coResultado;
	}

	public void setCoResultado(String coResultado) {
		this.coResultado = coResultado;
	}
	
	
}
