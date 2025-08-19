
package pe.gob.osinergmin.sicoes.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PacesUpdateDTO implements Serializable{
	private Long idPaces;
	private int mesConvocatoria;
	private String noConvocatoria;
	private String deItemPaces;
	private String reProgramaAnualSupervision;
	private Double dePresupuesto;	
	private boolean resultado; 
		
	
	public boolean isResultado() {
		return resultado;
	}
	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}
	public Long getIdPaces() {
		return idPaces;
	}
	public void setIdPaces(Long idPaces) {
		this.idPaces = idPaces;
	}
	public int getMesConvocatoria() {
		return mesConvocatoria;
	}
	public void setMesConvocatoria(int mesConvocatoria) {
		this.mesConvocatoria = mesConvocatoria;
	}
	public String getNoConvocatoria() {
		return noConvocatoria;
	}
	public void setNoConvocatoria(String noConvocatoria) {
		this.noConvocatoria = noConvocatoria;
	}
	public String getDeItemPaces() {
		return deItemPaces;
	}
	public void setDeItemPaces(String deItemPaces) {
		this.deItemPaces = deItemPaces;
	}
	public String getReProgramaAnualSupervision() {
		return reProgramaAnualSupervision;
	}
	public void setReProgramaAnualSupervision(String reProgramaAnualSupervision) {
		this.reProgramaAnualSupervision = reProgramaAnualSupervision;
	}
	public Double getDePresupuesto() {
		return dePresupuesto;
	}
	public void setDePresupuesto(Double dePresupuesto) {
		this.dePresupuesto = dePresupuesto;
	}
	      			     
	
	
	
	
	
}
