
package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PacesAprobadorDTO implements Serializable{
	private Long idPace;
	private Long idAprobadorG2;		
	private Long idAprobadorG3;
	private boolean resultado; 
	
	public Long getIdPace() {
		return idPace;
	}
	public void setIdPace(Long idPace) {
		this.idPace = idPace;
	}
	public Long getIdAprobadorG2() {
		return idAprobadorG2;
	}
	public void setIdAprobadorG2(Long idAprobadorG2) {
		this.idAprobadorG2 = idAprobadorG2;
	}	
	
	public Long getIdAprobadorG3() {
		return idAprobadorG3;
	}
	public void setIdAprobadorG3(Long idAprobadorG3) {
		this.idAprobadorG3 = idAprobadorG3;
	}
	
	public boolean isResultado() {
		return resultado;
	}
	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}
	
}