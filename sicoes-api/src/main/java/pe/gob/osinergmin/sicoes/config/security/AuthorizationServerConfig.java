package pe.gob.osinergmin.sicoes.config.security;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.client.RestTemplate;

import pe.gob.osinergmin.sicoes.controller.ErrorRestUtil;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private Environment env;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private InfoAdicionalToken infoAditionalToken;
	
	@Autowired
	ErrorRestUtil errorRestUtil;
	
	@Autowired
	UsuarioService usuarioService; 
	
	@Autowired
	DataSource dataSource;
		
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAditionalToken, accessTokenConverter()));
		
		

		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore()).reuseRefreshTokens(false)
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain)
		.userDetailsService(usuarioService)
		.exceptionTranslator(webResponseExceptionTranslator());
	}
	
	@Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {

            @Override
            public ResponseEntity translate(Exception e) throws Exception {
            	if(e instanceof InvalidTokenException) {
            		e = new ValidacionException(Constantes.CODIGO_MENSAJE.AUTENTICACION_REFRESH_TOKEN_EXP);
            	}
               return errorRestUtil.handleException(e);
            }
        };
    }
	
	
	@Bean
	public TokenStore  tokenStore() {
		JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
		/*
		jdbcTokenStore.setInsertAccessTokenSql("insert into "+getEsquema()+".oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)");
		jdbcTokenStore.setSelectAccessTokenSql("select token_id, token from "+getEsquema()+".oauth_access_token where token_id = ?");
		jdbcTokenStore.setSelectAccessTokenAuthenticationSql("select token_id, authentication from "+getEsquema()+".oauth_access_token where token_id = ?");
		jdbcTokenStore.setSelectAccessTokenFromAuthenticationSql("select token_id, token from "+getEsquema()+".oauth_access_token where authentication_id = ?");
		jdbcTokenStore.setSelectAccessTokensFromUserNameAndClientIdSql("select token_id, token from "+getEsquema()+".oauth_access_token where user_name = ? and client_id = ?");
		jdbcTokenStore.setSelectAccessTokensFromUserNameSql("select token_id, token from "+getEsquema()+".oauth_access_token where user_name = ?");
		jdbcTokenStore.setSelectAccessTokensFromClientIdSql("select token_id, token from "+getEsquema()+".oauth_access_token where client_id = ?");
		jdbcTokenStore.setDeleteAccessTokenSql("delete from "+getEsquema()+".oauth_access_token where token_id = ?");
		jdbcTokenStore.setInsertRefreshTokenSql("insert into "+getEsquema()+".oauth_refresh_token (token_id, token, authentication) values (?, ?, ?)");
		jdbcTokenStore.setSelectRefreshTokenSql("select token_id, token from "+getEsquema()+".oauth_refresh_token where token_id = ?");
		jdbcTokenStore.setSelectRefreshTokenAuthenticationSql("select token_id, authentication from "+getEsquema()+".oauth_refresh_token where token_id = ?");
		jdbcTokenStore.setDeleteRefreshTokenSql("delete from "+getEsquema()+".oauth_refresh_token where token_id = ?");
		jdbcTokenStore.setDeleteAccessTokenFromRefreshTokenSql("delete from "+getEsquema()+".oauth_access_token where refresh_token = ?");
		*/
		return jdbcTokenStore;
//		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(env.getProperty("config.security.oauth.jwt.key"));
		return tokenConverter;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("permitAll()");
	}
	
	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
	    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	    defaultTokenServices.setTokenStore(tokenStore());
	    defaultTokenServices.setSupportRefreshToken(true);
	    return defaultTokenServices;
	}

/*	private static final String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, "
			+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
			+ "refresh_token_validity, additional_information, autoapprove";

	private  String CLIENT_FIELDS = "client_secret, " + CLIENT_FIELDS_FOR_UPDATE;*/

	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		
//		String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS
//				+ " from "+getEsquema()+".oauth_client_details";
		
		JdbcClientDetailsService clientDetailsService= new JdbcClientDetailsService(dataSource); 
		/*	clientDetailsService.setDeleteClientDetailsSql("delete from "+getEsquema()+".oauth_client_details where client_id = ?");
		clientDetailsService.setFindClientDetailsSql(BASE_FIND_STATEMENT + " order by client_id"); 
		clientDetailsService.setInsertClientDetailsSql("insert into "+getEsquema()+".oauth_client_details (" + CLIENT_FIELDS
				+ ", client_id) values (?,?,?,?,?,?,?,?,?,?,?)"); 
		clientDetailsService.setSelectClientDetailsSql(BASE_FIND_STATEMENT + " where client_id = ?"); 
		clientDetailsService.setUpdateClientDetailsSql("update "+getEsquema()+".oauth_client_details " + "set "
				+ CLIENT_FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=? where client_id = ?"); 
		clientDetailsService.setUpdateClientSecretSql("update "+getEsquema()+".oauth_client_details "
				+ "set client_secret = ? where client_id = ?"); 
		*/
//		Field[] campos= clientDetailsService.getClass().getDeclaredFields();
//		for(Field campo:campos) {
//			if(campo.getDeclaringClass().equals("".getClass())) {
//				
//			}
//			System.out.println(campo.get(clientDetailsService));
//		}
		
		configurer.withClientDetails(clientDetailsService);
		/*
		.withClient(env.getProperty("config.security.oauth.client.id"))
		.secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))
		.scopes("read", "write")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(36000)
		.refreshTokenValiditySeconds(36000);*/
		
		/*clients.inMemory().withClient(env.getProperty("config.security.oauth.client.id"))
		.secret(passwordEncoder.encode(env.getProperty("config.security.oauth.client.secret")))
		.scopes("read", "write")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(36000)
		.refreshTokenValiditySeconds(36000)
		;*/
	}
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate= new RestTemplate();
	    
	    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
	    mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
	    MediaType.TEXT_HTML,
	    MediaType.TEXT_PLAIN));
	    restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
	    return restTemplate;

	}
}
