package pe.gob.osinergmin.sicoes.util.vo;

import java.io.Serializable;
import java.util.List;

public class ModuloDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String modulo;
	private List<OpcionDTO> opciones;
	
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public List<OpcionDTO> getOpciones() {
		return opciones;
	}
	public void setOpciones(List<OpcionDTO> opciones) {
		this.opciones = opciones;
	}
	
}
