package pe.gob.osinergmin.sicoes.consumer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gob.osinergmin.sne.domain.dto.rest.in.AccessRequestInRO;
import gob.osinergmin.sne.domain.dto.rest.in.AfiliacionInRO;
import gob.osinergmin.sne.domain.dto.rest.out.AccessResponseOutRO;
import gob.osinergmin.sne.domain.dto.rest.out.AfiliacionOutROsne;
import pe.gob.osinergmin.sicoes.consumer.SneApiConsumer;
import pe.gob.osinergmin.sicoes.model.dto.DetalleVacacionesDTO;
import pe.gob.osinergmin.sicoes.model.dto.ListadoVacacionesDTO;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionBeanDTO;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionOutRO;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionVvoBeanDTO;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionVvoOutRO;
import pe.gob.osinergmin.sicoes.util.bean.sne.NotificacionBeanDTO;

@Repository("sneApiConsumer")
public class SneApiConsumerImpl implements SneApiConsumer{
	
	private static Logger LOG = LogManager.getLogger(SneApiConsumerImpl.class);

	@Value("${sne.ws.url}")
	private String SNE_privateWS_URL;
	@Value("${sne.ws.path.obtener.afiliacion.vvo}")
	private String SNE_PATH_afiliacion;
	@Value("${sne.ws.path.obtener.afiliacion.sne}")
	private String SNE_PATH_AFILIACION_SNE;
	@Value("${sne.ws.path.login}")
	private String SNE_PATH_LOGIN;	
	@Value("${sne.ws.path.registrar.afiliacion}")
	private String SNE_PATH_REGISTRAR_AFILIACION;	
	@Value("${sne.ws.sigla.proyecto}")
	private String SIGLA_PROYECTO;	
	@Value("${sne.ws.tipo.login.usuario.consulta}")
	private String TIPO_LOGIN_USUARIO_CONSULTA;	
	@Value("${sne.ws.valor.desconocido}")
	private String VALOR_DESCONOCIDO;		
	@Value("${sne.ws.usuario}")
	private String USUARIO;		
	@Value("${sne.ws.password}")
	private String PASSWORD;	
	
	
	@Value("${sne.ws.resultado.ok}")
	private String RESULTADO_SUCCESS;	
	@Value("${sne.ws.token.header}")
	private String TOKEN_HEADER;	
	@Value("${sne.ws.token.type}")
	private String TOKEN_TYPE;
	
	@Value("${sne.two.ws.url}")
	private String SNE_TWO_PRIVATE_WS_URL;
	@Value("${sne.two.ws.usuario}")
	private String USUARIO_2;		
	@Value("${sne.two.ws.password}")
	private String PASSWORD_2;
	@Value("${sne.two.ws.path.obtener.fecha.notificacion}")
	private String SNE_TWO_PATH_NOTIFICACION;
	@Value("${sne.two.ws.sigla.proyecto}")
	private String SIGLA_PROYECTO_2;
	
	@Value("${sne.three.ws.url}")
	private String SNE_THREE_PRIVATE_WS_URL;
	@Value("${sne.three.ws.path.listar.por.dia}")
	private String SNE_PATH_LISTAR_POR_DIA;
	@Value("${sne.three.ws.codigo.aplicativo}")
	private String SNE_THREE_CODIGO_APLICATIVO;
	@Value("${sne.three.ws.codigo.consulta}")
	private String SNE_THREE_CODIGO_CONSULTA;
	
	@Override
    public String obtenerTokenAcceso() throws Exception {
    	String token = null;
        String url = SNE_privateWS_URL+SNE_PATH_LOGIN;
        try{
        	RestTemplate restTemplate = new RestTemplate();
            LOG.debug("url [{}].", url);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            AccessRequestInRO accessRequestInRO = new AccessRequestInRO();
            accessRequestInRO.setAppInvoker(SIGLA_PROYECTO);
            accessRequestInRO.setLoginType(TIPO_LOGIN_USUARIO_CONSULTA);
            accessRequestInRO.setSector(VALOR_DESCONOCIDO);
            accessRequestInRO.setUsername(USUARIO);
            accessRequestInRO.setPassword(PASSWORD);
            
            HttpEntity<AccessRequestInRO> request = new HttpEntity<>(accessRequestInRO, headers);

            
            AccessResponseOutRO accessResponseOutRO = restTemplate.postForObject(url, request, AccessResponseOutRO.class);
            if(accessResponseOutRO == null || !accessResponseOutRO.getResultCode().equals(RESULTADO_SUCCESS)){
            	throw new Exception(accessResponseOutRO.getMessage());
            }
            token = accessResponseOutRO.getToken();
            LOG.info("Invoking of web service " + url + " was successful");
        }catch(Exception ex){
        	LOG.error("Invoking of web service " + url + " was failed");
        	throw ex;
        }
        LOG.info("Ending web service " + url);
        return token;
    }
	
	@Override
	public AfiliacionOutRO obtenerAfiliacion(AfiliacionBeanDTO afiliacionBeanDTO, String token) throws Exception {
		
		String url=SNE_privateWS_URL+SNE_PATH_afiliacion;
		AfiliacionOutRO afiliacionOutRO=null;
		try {			
			RestTemplate restTemplate = new RestTemplate();
			LOG.debug("url [{}].", url);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(TOKEN_HEADER, TOKEN_TYPE +" "+token);
			afiliacionBeanDTO.setAppInvoker(SIGLA_PROYECTO);
			HttpEntity<AfiliacionBeanDTO> entity = new HttpEntity<AfiliacionBeanDTO>(afiliacionBeanDTO,headers);
			afiliacionOutRO = restTemplate.postForObject(url, entity, AfiliacionOutRO.class);
		} catch (HttpClientErrorException ex) {
			LOG.error("Invoking of web service " + url + " of siged was failed", ex);
			throw new ValidacionException(ex.getRawStatusCode()+"",ex.getMessage());
		}catch (Exception ex) {
			LOG.error("Invoking of web service " + url + " of siged was failed", ex);
			throw ex;
		}
		
		return afiliacionOutRO;
	}
	
	@Override
	public AfiliacionOutROsne obtenerAfiliacionSNE(AfiliacionInRO afiliacionBeanDTO, String token) throws Exception {
		
		String url=SNE_privateWS_URL+SNE_PATH_AFILIACION_SNE;
		AfiliacionOutROsne afiliacionOutRO=null;
		try {			
			RestTemplate restTemplate = new RestTemplate();
			LOG.debug("url [{}].", url);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(TOKEN_HEADER, TOKEN_TYPE +" "+token);
			HttpEntity<AfiliacionInRO> entity = new HttpEntity<AfiliacionInRO>(afiliacionBeanDTO,headers);
			afiliacionOutRO = restTemplate.postForObject(url, entity, AfiliacionOutROsne.class);
		} catch (Exception ex) {
			LOG.error("Invoking of web service " + url + " of siged was failed", ex);
            throw ex;
		}
		
		return afiliacionOutRO;
	}
	

	@Override
	public AfiliacionVvoOutRO registrarAfiliacion(AfiliacionVvoBeanDTO afiliacionBeanDTO, String token) throws Exception {
		
		String url=SNE_privateWS_URL+SNE_PATH_REGISTRAR_AFILIACION;
		AfiliacionVvoOutRO afiliacionOutRO=null;
		try {			
			RestTemplate restTemplate = new RestTemplate();
			LOG.debug("url [{}].", url);
			
			HttpHeaders headers = new HttpHeaders();
            headers.add(TOKEN_HEADER, TOKEN_TYPE + " " + token);
            headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<AfiliacionVvoBeanDTO> inputEntity = new HttpEntity<AfiliacionVvoBeanDTO>(afiliacionBeanDTO, headers);
			afiliacionOutRO=restTemplate.postForObject(url, inputEntity, AfiliacionVvoOutRO.class);
		} catch (Exception ex) {
			LOG.error("Invoking of web service " + url + " of siged was failed", ex);
            throw ex;
		}
		
		return afiliacionOutRO;
	}
	
	@Override
    public String obtenerTokenAcceso2() throws Exception {
    	String token = null;
        String url = SNE_TWO_PRIVATE_WS_URL + SNE_PATH_LOGIN;
        try{
        	RestTemplate restTemplate = new RestTemplate();
            LOG.debug("url [{}].", url);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            AccessRequestInRO accessRequestInRO = new AccessRequestInRO();
            accessRequestInRO.setAppInvoker(SIGLA_PROYECTO);
            accessRequestInRO.setLoginType(TIPO_LOGIN_USUARIO_CONSULTA);
            accessRequestInRO.setSector(VALOR_DESCONOCIDO);
            accessRequestInRO.setUsername(USUARIO_2);
            accessRequestInRO.setPassword(PASSWORD_2);
            
            HttpEntity<AccessRequestInRO> request = new HttpEntity<>(accessRequestInRO, headers);

            
            AccessResponseOutRO accessResponseOutRO = restTemplate.postForObject(url, request, AccessResponseOutRO.class);
            if(accessResponseOutRO == null || !accessResponseOutRO.getResultCode().equals(RESULTADO_SUCCESS)){
            	throw new Exception(accessResponseOutRO.getMessage());
            }
            token = accessResponseOutRO.getToken();
            LOG.info("Invoking of web service " + url + " was successful");
        }catch(Exception ex){
        	LOG.error("Invoking of web service " + url + " was failed");
        	throw ex;
        }
        LOG.info("Ending web service " + url);
        return token;
    }
	
	@Override
	public Date obtenerFechaNotificacion(NotificacionBeanDTO notificacionBeanDTO, String token) throws Exception {
		
		String url=SNE_TWO_PRIVATE_WS_URL + SNE_TWO_PATH_NOTIFICACION;
		Date fechaNotificacion = null;
		
		try {			
			RestTemplate restTemplate = new RestTemplate();
			LOG.debug("url [{}].", url);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(TOKEN_HEADER, TOKEN_TYPE + token);
			notificacionBeanDTO.setAppInvoker(SIGLA_PROYECTO_2);

			HttpEntity<NotificacionBeanDTO> entity = new HttpEntity<NotificacionBeanDTO>(notificacionBeanDTO,headers);

			ResponseEntity<String> forEntity = restTemplate.postForEntity(url, entity, String.class);
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			JsonNode jsonNode = objectMapper.readTree(forEntity.getBody());
			
			String fecha = jsonNode.get("notificacion").get(0).get("fechaNotificacionConFormato").toString();
			fecha = fecha.substring(fecha.indexOf(' ')).trim();
			fecha = fecha.substring(0, fecha.indexOf(' ')).trim();
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			fechaNotificacion = formatter.parse(fecha);
			
		} catch (HttpClientErrorException ex) {
			LOG.error("Invoking of web service " + url + " of siged was failed", ex);
			throw new ValidacionException(ex.getRawStatusCode()+"",ex.getMessage());
		}catch (Exception ex) {
			LOG.error("Invoking of web service " + url + " of siged was failed", ex);
			throw ex;
		}
		
		return fechaNotificacion;
	}
	
	@Override
	public List<DetalleVacacionesDTO> obtenerListadoVacaciones() throws Exception {
		
		String url = SNE_THREE_PRIVATE_WS_URL + SNE_PATH_LISTAR_POR_DIA + "?codigoAplicativo=" + SNE_THREE_CODIGO_APLICATIVO + "&codigoConsulta=" + SNE_THREE_CODIGO_CONSULTA;
		
		List<DetalleVacacionesDTO> listaVacaciones = new ArrayList<DetalleVacacionesDTO>();
		
		try {			
			RestTemplate restTemplate = new RestTemplate();
			LOG.debug("url [{}].", url);			
			
			ListadoVacacionesDTO response = restTemplate.getForObject(url, ListadoVacacionesDTO.class);
			
			listaVacaciones = response.getResult();
		}
		catch (HttpClientErrorException ex) {
			LOG.error("Invoking of web service " + url + " of siged was failed", ex);
			throw new ValidacionException(ex.getRawStatusCode()+"",ex.getMessage());
		}
		catch (Exception ex) {
			LOG.error("Invoking of web service " + url + " of siged was failed", ex);
			throw ex;
		}
		
		return listaVacaciones;
	}
}
