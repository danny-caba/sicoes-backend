
package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PacesObservarDivisionDTO implements Serializable{
	private Long idPaces;		
	private String observacion;
	private boolean resultado; 
	
	public Long getIdPaces() {
		return idPaces;
	}
	public void setIdPaces(Long idPaces) {
		this.idPaces = idPaces;
	}	
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}		      			     
	public boolean isResultado() {
		return resultado;
	}
	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}
	
}