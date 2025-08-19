package pe.gob.osinergmin.sicoes.util.bean;

import java.io.Serializable;

public class WrapperResponseDNI implements Serializable {
	private Datos datos;
	private String errorMsg;
	private String errorCode;
	public Datos getDatos() {
		return datos;
	}
	public void setDatos(Datos datos) {
		this.datos = datos;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
