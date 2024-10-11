package pe.gob.osinergmin.sicoes.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.ErrorInfo;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.ValidacionWrapper;

@ControllerAdvice(annotations = { Controller.class, RestController.class })
public class ErrorRestUtil {

	Logger logger = LogManager.getLogger(ErrorRestUtil.class);

	@Autowired
	MessageSource messageSource;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	@ExceptionHandler
	public ResponseEntity<ErrorInfo> handleException(Exception ex) {
		logger.info("executing exception handler (web)");
		ResponseEntity<ErrorInfo> data = resolveException(ex);
		data.getBody().setUri(request.getRequestURI());
		return data;
	}

	private ResponseEntity<ErrorInfo> resolveException(Exception e) {
		ErrorInfo errorInfo = new ErrorInfo();
		ResponseEntity<ErrorInfo> data = new ResponseEntity<ErrorInfo>(HttpStatus.BAD_REQUEST);
		String codigoError = "", mensajeError = "";
		Object[] parametros = new Object[0];
		String mensajeDefecto = "";
		
		if (e instanceof ValidacionException) {
			ValidacionException validacionException = (ValidacionException) e;
			List<ValidacionWrapper> ValidacionWrappers=validacionException.getValidacionWrappers();
			data = new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
			for(ValidacionWrapper validacionWrapper:ValidacionWrappers) {
				codigoError = validacionWrapper.getCodigoMensaje();
				parametros = validacionWrapper.getParametros();				
				mensajeError+=getMessage(codigoError,parametros,mensajeDefecto)+"\n";
			}
		} else if (e instanceof InvalidGrantException) {
			codigoError = "E001001";
			data = new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
		} else {
			data = new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String codigo = sdf.format(new Date());
			parametros = new Object[] { codigo };
			codigoError = "E001002";
			mensajeError=getMessage(codigoError,parametros,mensajeDefecto);
			logger.error("/************* " + codigo + " - INICIO ***********/");
			logger.error(e.getMessage(), e);
			logger.error("/************* " + codigo + " - FIN ***********/");
		}
		
		errorInfo.setErrorCode(codigoError);
		errorInfo.setErrorMessage(mensajeError);
		errorInfo.setStatusCode(data.getStatusCode().value());
		return data;
	}
	
	public String getMessage(String codigoError,Object[] parametros,String mensajeDefecto) {
		String mensajeError = messageSource.getMessage(codigoError, parametros, mensajeDefecto, Locale.getDefault());
		if(mensajeError.equals("")) {
			logger.warn("NO SE TIENE UNA DEFINICION DE MENSAJE PARA EL SIGUIENTE CODIGO DE ERROR: {}",codigoError);

		}
		return mensajeError;
	}


}
