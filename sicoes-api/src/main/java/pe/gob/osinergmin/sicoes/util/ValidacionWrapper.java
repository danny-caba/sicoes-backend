package pe.gob.osinergmin.sicoes.util;

public class ValidacionWrapper {

	private String codigoMensaje;
	private Object[] parametros;
	private Exception exception;
	
	public ValidacionWrapper(Exception exception) {
		this.setException(exception);
	}
	
	public ValidacionWrapper(Exception exception,String codigoMensaje,Object ...parametros) {
		this.setException(exception);
		this.codigoMensaje=codigoMensaje;
		this.parametros=parametros;
	}
	
	public ValidacionWrapper(String codigoMensaje,Object ...parametros) {
		this.codigoMensaje=codigoMensaje;
		this.parametros=parametros;
	}
	
	public String getCodigoMensaje() {
		return codigoMensaje;
	}
	public void setCodigoMensaje(String codigoMensaje) {
		this.codigoMensaje = codigoMensaje;
	}
	public Object[] getParametros() {
		return parametros;
	}
	public void setParametros(Object[] parametros) {
		this.parametros = parametros;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	
}
