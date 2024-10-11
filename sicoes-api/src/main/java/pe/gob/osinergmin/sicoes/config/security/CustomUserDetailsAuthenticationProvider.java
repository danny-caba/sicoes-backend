package pe.gob.osinergmin.sicoes.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.ValidacionException;


public class CustomUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    /**
     * The plaintext password used to perform
     * PasswordEncoder#matches(CharSequence, String)}  on when the user is
     * not found to avoid SEC-2056.
     */
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    private PasswordEncoder passwordEncoder;
    private UsuarioService userDetailsService;
    
    /*@PostConstruct
	public void initProvider() {
		ReloadableResourceBundleMessageSource localMessageSource = new ReloadableResourceBundleMessageSource();
		localMessageSource.setBasenames("messages");
		messages = new MessageSourceAccessor(localMessageSource);
	}*/


    /**
     * The password used to perform
     * {@link PasswordEncoder#matches(CharSequence, String)} on when the user is
     * not found to avoid SEC-2056. This is necessary, because some
     * {@link PasswordEncoder} implementations will short circuit if the password is not
     * in a valid format.
     */
    private String userNotFoundEncodedPassword;

    public CustomUserDetailsAuthenticationProvider(PasswordEncoder passwordEncoder, UsuarioService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) 
        throws AuthenticationException {

        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.AUTENTICACION_SIN_CREDENCIALES);
        }

        String presentedPassword = authentication.getCredentials()
            .toString();

        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            logger.debug("Authentication failed: password does not match stored value");
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.AUTENTICACION_CREDENCIALES_INCORRECTAS);
        }
    }

    @Override
    protected void doAfterPropertiesSet() throws Exception {
        this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) 
        throws AuthenticationException {
    	UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        UserDetails loadedUser;

        try {
            loadedUser = this.userDetailsService.loadUserByUsername(auth.getPrincipal().toString(),(String)authentication.getCredentials());
        } catch (UsernameNotFoundException notFound) {
        	
            if (authentication.getCredentials() != null) {
                String presentedPassword = authentication.getCredentials()
                    .toString();
                passwordEncoder.matches(presentedPassword, userNotFoundEncodedPassword);
            }
            throw notFound;
        } 

        if (loadedUser == null) {
        	throw new ValidacionException(Constantes.CODIGO_MENSAJE.AUTENTICACION_USUARIO_NO_EXISTE);
        }
        return loadedUser;
    }

}