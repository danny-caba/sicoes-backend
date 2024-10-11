package pe.gob.osinergmin.sicoes.config.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher{
	
	private Logger log = LogManager.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		log.debug("Success Login: " + authentication.getPrincipal());
	}
	
	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		log.debug("Error en el login " + exception.getMessage());
	}
	
}
