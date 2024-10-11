package pe.gob.osinergmin.sicoes.util.bean;

import java.io.Serializable;

public class BaseOutRO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String resultCode;
	private String message;
	private String errorCode;
	private String deResultado;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDeResultado() {
		return deResultado;
	}

	public void setDeResultado(String deResultado) {
		this.deResultado = deResultado;
	}

}
