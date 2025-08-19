package pe.gob.osinergmin.sicoes.util.bean.siged;

import java.io.Serializable;

public class TipoDocumentoBeanDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private String value;
    private String label;
    private String extension;
    
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
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public TipoDocumentoBeanDto(String value, String label, String extension) {
		this.value = value;
		this.label = label;
		this.extension = extension;
	}
    
    

}
