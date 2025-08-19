package pe.gob.osinergmin.sicoes.config.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	TokenStore tokenStore;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**.html").permitAll().antMatchers("/**.jsp").permitAll()
				.antMatchers("/api/oauth/**").permitAll().antMatchers(HttpMethod.GET, "/api/supervisora-perfiles")
				.permitAll().antMatchers(HttpMethod.GET, "/api/supervisora-perfiles/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/formato-publico/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/listado-publico").permitAll()
				.antMatchers(HttpMethod.GET, "/api/listado-publico/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/listado-publico-procesos/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/listado-publico-procesos").permitAll()
				.antMatchers(HttpMethod.GET, "/api//listado-publico-unidad").permitAll()
				.antMatchers(HttpMethod.GET, "/api/supervisoras/suspendidas-cancelado").permitAll()
				.antMatchers(HttpMethod.GET, "/api/supervisoras/suspendidas-cancelado/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/usuarios/publico/enviar-codigo-validacion").permitAll()
				.antMatchers(HttpMethod.POST, "/api/usuarios/publico/validar-codigo-validacion").permitAll()
				.antMatchers(HttpMethod.POST, "/api/usuarios/publico").permitAll()
				.antMatchers(HttpMethod.GET, "/api/usuarios/publico/obtener-correo").permitAll()
				.antMatchers(HttpMethod.POST, "/api/usuarios/publico/recuperar-contrasenia").permitAll()
				.antMatchers(HttpMethod.POST, "/api/usuarios/publico/cambiar-contrasenia").permitAll()

				// .antMatchers(HttpMethod.GET,"/api/archivos/**/descarga").permitAll()
				/*
				 * .antMatchers("/api/consulta-sunat").permitAll()
				 * .antMatchers("/api/consulta-reniec").permitAll() .antMatchers(HttpMethod.GET,
				 * "/api/departamentos").permitAll() .antMatchers(HttpMethod.GET,
				 * "/api/provincias/**").permitAll() .antMatchers(HttpMethod.GET,
				 * "/api/distritos/**").permitAll() .antMatchers(HttpMethod.GET,
				 * "/api/descargar-observaciones-url/**").permitAll()
				 * .antMatchers(HttpMethod.POST, "/api/adjuntos/**").permitAll()
				 * .antMatchers(HttpMethod.GET, "/api/descargar/**").permitAll()
				 * .antMatchers(HttpMethod.GET, "/api/descargarVisitas/**").permitAll()
				 * .antMatchers(HttpMethod.GET, "/api/listado/
				 **//*
					 * detalles").permitAll() .antMatchers(HttpMethod.POST,
					 * "/api/solicitudes-registro-usuario").permitAll()
					 * .antMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
					 * .antMatchers(HttpMethod.POST, "/api/usuarios/cambiarPassword").permitAll()
					 * .antMatchers(HttpMethod.GET,"/api/tipo-documento").permitAll()
					 * .antMatchers(HttpMethod.GET,"/api/reporte/ceapams-busqueda").permitAll()
					 * .antMatchers(HttpMethod.GET,"/api/reporte/ceapams-listado").permitAll()
					 * .antMatchers("/api/usuarios/recuperar").permitAll()
					 * .antMatchers("/api/job-act-cepam").permitAll()
					 */
				.antMatchers(HttpMethod.POST, "/api/usuarios/token").authenticated().anyRequest().authenticated().and()
				.cors().configurationSource(corsConfigurationSource());
	}

	/*
	 * public ClienteSissegConfig clienteSissegConfig() { ClienteSissegConfig
	 * clienteSissegConfig=new ClienteSissegConfig();
	 * clienteSissegConfig.put(ClienteSissegConfigParams.BASE_URL,
	 * env.getProperty("sisseg.base-url"));
	 * clienteSissegConfig.put(ClienteSissegConfigParams.ENCRYPTION_KEY,env.
	 * getProperty("sisseg.application-id") );
	 * clienteSissegConfig.put(ClienteSissegConfigParams.APPLICATION_ID,env.
	 * getProperty("sisseg.encryption-key") ); return clienteSissegConfig; }
	 * 
	 * 
	 * public SissegAuthenticationProvider sissegAuthenticationProvider() { return
	 * new SissegAuthenticationProvider(clienteSissegConfig()); }
	 * 
	 * 
	 * @Bean
	 * 
	 * @Order(Ordered.HIGHEST_PRECEDENCE) public SecurityFilterChain
	 * securityFilterChain(HttpSecurity httpSecurity) throws Exception{
	 * SissegAuthenticationConfigurer<HttpSecurity> configurer=new
	 * SissegAuthenticationConfigurer<HttpSecurity>(sissegAuthenticationProvider());
	 * RequestMatcher requestMatcher=configurer.getRequestMatcher();
	 * httpSecurity.requestMatcher(requestMatcher). authorizeRequests(requests->
	 * requests.anyRequest().authenticated()). csrf().disable(). apply(configurer);
	 * return httpSecurity.build(); }
	 */

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(
				Arrays.asList(CorsConfiguration.ALL, "http://localhost:4200", "http://078d-190-234-127-88.ngrok.io"));
//		config.setAllowedOrigins(Arrays.asList(CorsConfiguration.ALL));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
		config.setAllowCredentials(true);
		List<String> headers = Arrays.asList("Access-Control-Expose-Headers", "Access-Control-Allow-Headers",
				"Access-Control-Allow-Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers",
				"Origin", "Cache-Control", "Content-Type", "Authorization", "Error-Code", "Error-Message",
				"Application", "IdEmpresa", "Recaptcha");
		config.setAllowedHeaders(headers);
		config.setExposedHeaders(headers);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

}
