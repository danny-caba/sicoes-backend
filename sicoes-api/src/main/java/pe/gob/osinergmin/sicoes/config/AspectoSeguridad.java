package pe.gob.osinergmin.sicoes.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import pe.gob.osinergmin.sicoes.model.BaseModel;

@Aspect
@Component
public class AspectoSeguridad {

	Logger logger = LogManager.getLogger(AspectoSeguridad.class);

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpServletResponse response;
	
	@Autowired
	MessageSource messageSource;

	@Around("execution(* pe.gob.osinergmin.sicoes.service.impl..*.*(..))")
	public Object agregarAuditoria(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.debug("ingreso al aspecto "+joinPoint.getSignature().getName());
		Object obj =null;		
			Object[] parameters = joinPoint.getArgs();
			for (Object object : parameters) {
				if(object instanceof BaseModel) {
					configurarDatosAuditoria((BaseModel)object);
				}
			}
			obj = joinPoint.proceed();		
		logger.debug("termino el aspecto "+joinPoint.getSignature().getName());
		return obj;
	}


	public void configurarDatosAuditoria(BaseModel baseModel) {
		try {
			logger.debug("request.getMethod()"+request.getMethod());
			String metodo=request.getMethod();
			String user = request.getHeader("usuario");
		}catch (Exception e) {
			logger.debug(e.getMessage(),e);
		}
		/*if("POST".equals(metodo)) {
			baseModel.setUsuCreacion("WDURAN");
			baseModel.setFecCreacion(new Date());
			baseModel.setIpCreacion("123.123.4132");
		}else if("PUT".equals(metodo)){
			baseModel.setUsuActualizacion("WDURAN");
			baseModel.setFecActualizacion(new Date());
			baseModel.setIpActualizacion("123.123.4132");
		}*/
		
	}
}
