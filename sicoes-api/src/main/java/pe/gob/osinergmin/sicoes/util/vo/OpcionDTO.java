package pe.gob.osinergmin.sicoes.util.vo;

import java.io.Serializable;

public class OpcionDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String modulo;
	private String opcion;
	private String url;
	
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public String getOpcion() {
		return opcion;
	}
	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
