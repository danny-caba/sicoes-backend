package pe.gob.osinergmin.sicoes.util.bean.siged;

import java.io.Serializable;

//extends BaseOutRO
public class GenericBeanOutRO implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String value;
	private String label;

	public GenericBeanOutRO(){	
	}
	
	public GenericBeanOutRO(String value, String label){
		this.value = value;
		this.label = label;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
