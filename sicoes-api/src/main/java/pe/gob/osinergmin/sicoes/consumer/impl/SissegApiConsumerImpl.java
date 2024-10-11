package pe.gob.osinergmin.sicoes.consumer.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pe.gob.osinergmin.sicoes.consumer.SissegApiConsumer;
import pe.gob.osinergmin.sicoes.model.Usuario;

@Repository("sissegApiConsumer")
public class SissegApiConsumerImpl implements SissegApiConsumer {
	
	private Logger logger = LogManager.getLogger(PidoConsumerImpl.class);
	
	@Value("${sisseg.ws.url}")
	private String SISSEG_WS_URL;
	
	@Value("${gsm.ws.url}")
	private String GSM_WS_URL;
	
	@Value("${sisseg.ws.path.obtener.token}")
	private String SISSEG_WS_PATH_OBTENER_TOKEN;
	
	@Value("${sisseg.ws.path.obtener.id.usuario}")
	private String SISSEG_WS_PATH_OBTENER_ID_USUARIO;
	
	@Value("${sisseg.ws.path.obtener.usuario}")
	private String SISSEG_WS_PATH_OBTENER_USUARIO;
	
	@Autowired
	private Environment env;

	@Override
	public String obtenerAccessToken(String token) throws Exception {

		String accessToken = "";
		String url = SISSEG_WS_URL + SISSEG_WS_PATH_OBTENER_TOKEN;

		try {
			RestTemplate restTemplate = new RestTemplate();
			logger.debug("url {}", url);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
	        body.add("grant_type", "client_credentials");
	        body.add("client_id", env.getProperty("sisseg.encryption-key") + "::" + env.getProperty("sisseg.application-id") + "::" + token);
	        body.add("client_secret", token);

	        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
	        ObjectMapper objectMapper = new ObjectMapper();			
			JsonNode jsonNode = objectMapper.readTree(response.getBody());
			
			accessToken = jsonNode.get("access_token").toString().replaceAll("\"", "").trim();
		}
		catch(Exception ex) {
			logger.error("Invoking of web service " + url + " of siged was failed", ex);
            throw ex;
		}
		
		return accessToken;
	}
	
	@Override
	public Long obtenerIdUsuario(String username) {
		
		logger.info("obtenerIdUsuario {}", username);
		
		Long idUsuario = 0L;
		String url = GSM_WS_URL + SISSEG_WS_PATH_OBTENER_ID_USUARIO + "/" + username;
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			logger.debug("url {}", url);
	        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

	        idUsuario = Long.parseLong(response.getBody());
		}
		catch(Exception ex) {
			logger.error("Invoking of web service " + url + " of siged was failed", ex);
            throw ex;
		}
		
		return idUsuario;
	}

	@Override
	public Usuario obtenerUsuario(Long idUsuario, String token) throws Exception {
		
		logger.info("obtenerUsuario {} {}", idUsuario, token);
		
		Usuario usuario = new Usuario();
		String url = SISSEG_WS_URL + SISSEG_WS_PATH_OBTENER_USUARIO + "/" + idUsuario;
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			logger.debug("url {}", url);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + token);

			HttpEntity<String> requestEntity = new HttpEntity<>(headers);
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
	        ObjectMapper objectMapper = new ObjectMapper();			
			JsonNode jsonNode = objectMapper.readTree(response.getBody());
			
	        usuario.setUsuario(jsonNode.get("usuario").get(0).get("username").toString().replaceAll("\"", "").trim());
	        usuario.setContrasenia(jsonNode.get("usuario").get(0).get("password").toString().replaceAll("\"", "").trim());
		}
		catch(Exception ex) {
			logger.error("Invoking of web service " + url + " of siged was failed", ex);
            throw ex;
		}
		
		return usuario;
	}	
}
