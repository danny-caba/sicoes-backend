package pe.gob.osinergmin.sicoes.util.bean.sne;

import java.io.Serializable;

public class AfiliacionBeanDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String appInvoker;                      
    private int tipoDocumento;                     
    private String numeroDocumento;
    private boolean verificarData = true;
    
	
	public String getAppInvoker() {
		return appInvoker;
	}
	public void setAppInvoker(String appInvoker) {
		this.appInvoker = appInvoker;
	}
	public int getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(int tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	public boolean isVerificarData() {
		return verificarData;
	}
	public void setVerificarData(boolean verificarData) {
		this.verificarData = verificarData;
	}
	@Override
	public String toString() {
		return "AfiliacionBeanDTO [appInvoker=" + appInvoker + ", tipoDocumento=" + tipoDocumento + ", numeroDocumento="
				+ numeroDocumento + "]";
	}

    
}
