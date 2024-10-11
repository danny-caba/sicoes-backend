package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;

public class ExpedienteDTO extends ExpedienteInRO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String asunto;

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
}
