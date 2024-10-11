package pe.gob.osinergmin.sicoes.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidacionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private List<ValidacionWrapper> validacionWrappers;

	public ValidacionException(Exception exception) {
		validacionWrappers = new ArrayList();
		validacionWrappers.add(new ValidacionWrapper(exception));
	}

	public ValidacionException(Exception exception, String codigoMensaje, Object... parametros) {
		validacionWrappers = new ArrayList();
		validacionWrappers.add(new ValidacionWrapper(exception, codigoMensaje, parametros));
	}

	public ValidacionException(List<ValidacionWrapper> validacionWrappers) {
		this.validacionWrappers = validacionWrappers;
	}

	public ValidacionException(String codigoMensaje, Object... parametros) {
		validacionWrappers = new ArrayList();
		validacionWrappers.add(new ValidacionWrapper(codigoMensaje, parametros));
	}

	public List<ValidacionWrapper> getValidacionWrappers() {
		return validacionWrappers;
	}

	public void setValidacionWrappers(List<ValidacionWrapper> validacionWrappers) {
		this.validacionWrappers = validacionWrappers;
	}
	
	

}
