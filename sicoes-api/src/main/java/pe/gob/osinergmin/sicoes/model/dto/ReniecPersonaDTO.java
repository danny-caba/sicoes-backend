package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

public class ReniecPersonaDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String preNombres;
	private String apPrimer;
	private String apSegundo;
	
	public String getPreNombres() {
		return preNombres;
	}
	public void setPreNombres(String preNombres) {
		this.preNombres = preNombres;
	}
	public String getApPrimer() {
		return apPrimer;
	}
	public void setApPrimer(String apPrimer) {
		this.apPrimer = apPrimer;
	}
	public String getApSegundo() {
		return apSegundo;
	}
	public void setApSegundo(String apSegundo) {
		this.apSegundo = apSegundo;
	}
	@Override
	public String toString() {
		return "ReniecPersonaDTO [preNombres=" + preNombres + ", apPrimer=" + apPrimer + ", apSegundo=" + apSegundo
				+ "]";
	}
	
	
}
