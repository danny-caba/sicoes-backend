package pe.gob.osinergmin.sicoes.util;

public class ErrorInfo {
	private String message;
	private String errorCode;
	private int statusCode;
	private String errorMessage;

	private String uri;


	public ErrorInfo() {		
	}

	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


}
