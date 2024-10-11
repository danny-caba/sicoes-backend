package pe.gob.osinergmin.sicoes.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pe.gob.osinergmin.sicoes.service.UsuarioService;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{


	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AuthenticationSuccessErrorHandler eventPublisher;
	
	@Autowired
	private Environment env;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider()).authenticationEventPublisher(eventPublisher);
		
		/*auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder())
		.and().authenticationEventPublisher(eventPublisher);*/
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	 public AuthenticationProvider authProvider() {
	        CustomUserDetailsAuthenticationProvider provider 
	            = new CustomUserDetailsAuthenticationProvider(passwordEncoder(), usuarioService);
	        return provider;
	    }
	 
	
}
