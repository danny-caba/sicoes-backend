package pe.gob.osinergmin.sicoes.util.bean.siged;

public class UnidadItemBeanDTO {
	private Integer idUnidadIten;
	private Integer Unidad; 	
	private Integer proceso;
	private Integer parametro;
	private String item;
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public UnidadItemBeanDTO(){
		
		
	}
	
	public UnidadItemBeanDTO(Integer unidad, Integer idUnidad, Integer proceso,Integer parametro, String item){
	
		 this.idUnidadIten = idUnidad;
		 this.Unidad = unidad	;
		 this.proceso = proceso;
		 this.parametro = parametro;
		 this.item=item;
		
	}
	public Integer getIdUnidadIten() {
		return idUnidadIten;
	}
	public void setIdUnidadIten(Integer idUnidadIten) {
		this.idUnidadIten = idUnidadIten;
	}
	public Integer getUnidad() {
		return Unidad;
	}
	public void setUnidad(Integer unidad) {
		Unidad = unidad;
	}
	public Integer getProceso() {
		return proceso;
	}
	public void setProceso(Integer proceso) {
		this.proceso = proceso;
	}
	public Integer getParametro() {
		return parametro;
	}
	public void setParametro(Integer parametro) {
		this.parametro = parametro;
	}
	
	
}
