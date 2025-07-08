package pe.gob.osinergmin.sicoes.consumer.impl;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.out.query.ClienteConsultaOutRO;
import gob.osinergmin.siged.rest.util.ClienteInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import gob.osinergmin.siged.remote.rest.ro.base.BaseOutRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import gob.osinergmin.siged.rest.util.ExpedienteInvoker;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.model.dto.UsuarioDetalleSigedDTO;
import pe.gob.osinergmin.sicoes.model.dto.UsuarioSigedResponseDTO;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.bean.siged.ResponseUserListDto;

@Repository("sigedApiConsumer")
public class SigedApiConsumerImpl implements SigedApiConsumer {
	private static Logger LOG = LogManager.getLogger(SigedApiConsumerImpl.class);
	
	@Value("${siged.old.ws.url}")
	private String SIGED_WS_URL;
	@Value("${siged.old.path.crearExpediente}")
	private String SIGED_PATH_CREAR_EXPEDIENTE;	
	@Value("${siged.old.path.buscarArchivosExpediente}")
	private String SIGED_PATH_ARCHIVO_EXPEDIENTE;
	@Value("${siged.old.path.agregarDocumento}")
	private String SIGED_PATH_AGREGAR_DOCUMENTO;	
	@Value("${siged.ws.path.registrar.archivamiento}")
	private String SIGED_PATH_REGISTRAR_ARCHIVAMIENTO;	
	@Value("${siged.ws.path.calcular.fecha.fin}")
	private String SIGED_PATH_CALCULAR_FECHA_FIN;	
	@Value("${siged.ws.path.listar.usuarios}")
	private String SIGED_PATH_LISTAR_USUARIOS;
	@Value("${siged.ws.path.obtener.usuario}")
	private String SIGED_PATH_OBTENER_USUARIO;
	@Value("${siged.ws.path.buscar.cliente}")
	private String SIGED_PATH_BUSCAR_CLIENTE;
	
	@Override
	public ExpedienteOutRO crearExpediente(ExpedienteInRO expediente, List<File> archivos) throws Exception {
		return ExpedienteInvoker.create(SIGED_WS_URL+SIGED_PATH_CREAR_EXPEDIENTE, expediente, archivos);
	}
	
	@Override
	public DocumentoOutRO agregarDocumento(ExpedienteInRO expediente, List<File> archivos) throws Exception {
		DocumentoOutRO doc =ExpedienteInvoker.addDocument(SIGED_WS_URL+SIGED_PATH_AGREGAR_DOCUMENTO, expediente, archivos, false);
		LOG.info("EXPEDIENTE_INFORME_TEC :"+doc.getResultCode());
		LOG.info("EXPEDIENTE_INFORME_TEC :"+doc.getErrorCode());
		LOG.info("EXPEDIENTE_INFORME_TEC :"+doc.getMessage());
		return doc;
	}
	
	@Override
	public DocumentoOutRO agregarDocumentoVersionar(ExpedienteInRO expediente, List<File> archivos) throws Exception {
		DocumentoOutRO doc =ExpedienteInvoker.addDocument(SIGED_WS_URL+SIGED_PATH_AGREGAR_DOCUMENTO, expediente, archivos, true);
		LOG.info("EXPEDIENTE_INFORME_TEC :"+doc.getResultCode());
		LOG.info("EXPEDIENTE_INFORME_TEC :"+doc.getErrorCode());
		LOG.info("EXPEDIENTE_INFORME_TEC :"+doc.getMessage());
		return doc;
	}
	
	private SOAPMessage sendPidoRequest(String xmlRequest, String URL) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/soap+xml;charset=UTF-8");
		HttpEntity<String> request = new HttpEntity<String>(xmlRequest, headers);	
		String xmlResponse=null;
		try {
			xmlResponse = restTemplate.postForObject(URL, request, String.class);
		} catch (HttpServerErrorException ex) {
			try {
				InputStream is2 = new ByteArrayInputStream(ex.getResponseBodyAsByteArray());
				SOAPMessage soapResponse = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage(null, is2);
				return soapResponse;
			}catch (Exception e) {
				LOG.error("Error al obtener response", e);
				return null;
			}
		}
		try {
			InputStream is = new ByteArrayInputStream(xmlResponse.getBytes(StandardCharsets.UTF_8));
			SOAPMessage soapResponse = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage(null, is);
			return soapResponse;
		} catch (IOException | SOAPException e) {
			LOG.error("Error al obtener response", e);
			return null;
		}
	}
	
	
	@Override
	public Date calcularFechaFin(Date fechaInicio, Long diasPlazo,String tipoPlazo) {
		String url=SIGED_WS_URL+SIGED_PATH_CALCULAR_FECHA_FIN;
		BaseOutRO baseOutRO=new BaseOutRO();
		try {			
			RestTemplate restTemplate = new RestTemplate();
			LOG.debug("url [{}].", url);
			
			Object[] args = {DateUtil.getDate(fechaInicio,"dd/MM/yyyy"),(diasPlazo),tipoPlazo};
			String xmlString = String.format("<fechaFeriado><fechaInicio>%s</fechaInicio><diasPlazo>%s</diasPlazo><tipoPlazo>%s</tipoPlazo></fechaFeriado>", args);
			LOG.info(xmlString);
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_XML);
		    HttpEntity<String> request = new HttpEntity<String>(xmlString, headers);
		    
		    String result = restTemplate.postForObject( url, request, String.class);
		    JSONObject soapDatainJsonObject = XML.toJSONObject(result);
		    
			JSONObject jsonCrearDetallePrincipalAnexo=(soapDatainJsonObject.getJSONObject("fechaFeriadoOutRO"));
			int resultCode=jsonCrearDetallePrincipalAnexo.getInt("resultCode");
			
			baseOutRO.setErrorCode(resultCode);
			if(resultCode!=1) {
				String message=jsonCrearDetallePrincipalAnexo.getString("message");			
				throw new ValidacionException(message);
			}else {
				return DateUtil.getFormat(jsonCrearDetallePrincipalAnexo.getString("fechaFin"), "dd/MM/yyyy");	
			}
			
		} catch (Exception ex) {
			LOG.error("Invoking of web service " + url + " of siged was failed", ex);
            throw ex;
		}
		
	}
	
	
	@Override
	public BaseOutRO archivarExpediente(String numeroExpediente, String observacion) {
		String url=SIGED_WS_URL+SIGED_PATH_REGISTRAR_ARCHIVAMIENTO;
		BaseOutRO baseOutRO=new BaseOutRO();
		try {			
			RestTemplate restTemplate = new RestTemplate();
			LOG.debug("url [{}].", url);
			
			Object[] args = {numeroExpediente,observacion};
			String xmlString = String.format("<expediente><nroExpediente>%s</nroExpediente><observacionArchivar>%s</observacionArchivar></expediente>", args);
			
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_XML);
		    HttpEntity<String> request = new HttpEntity<String>(xmlString, headers);
		    
		    String result = restTemplate.postForObject( url, request, String.class);
		    JSONObject soapDatainJsonObject = XML.toJSONObject(result);
		    
			JSONObject jsonCrearDetallePrincipalAnexo=(soapDatainJsonObject.getJSONObject("expedienteOutRO"));
			int resultCode=jsonCrearDetallePrincipalAnexo.getInt("resultCode");
			
			baseOutRO.setErrorCode(resultCode);
			if(resultCode!=1) {
				String message=jsonCrearDetallePrincipalAnexo.getString("message");			
				baseOutRO.setMessage(message);
			}
			return baseOutRO;
		} catch (Exception ex) {
			LOG.error("Invoking of web service " + url + " of siged was failed", ex);
            throw ex;
		}
		
	}
	
	@Override
	public List<ResponseUserListDto.Usuario> listarUsuariosSiged() throws Exception {
		String url=SIGED_WS_URL+SIGED_PATH_LISTAR_USUARIOS;
		ResponseUserListDto myObject =new ResponseUserListDto();
		try {			
			RestTemplate restTemplate = new RestTemplate();
			LOG.debug("url [{}].", url);
			
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_XML);
		    
		    String result = restTemplate.getForObject(url, String.class);
		    JSONObject soapDatainJsonObject = XML.toJSONObject(result);
		    	
		    System.out.println(soapDatainJsonObject);
		    
		    ObjectMapper objectMapper = new ObjectMapper();

		    myObject = objectMapper.readValue(soapDatainJsonObject.toString(), ResponseUserListDto.class);

		    if (myObject.getUsuarios()==null || myObject.getUsuarios().getResultCode()!=1) {
				return null;
			}
		    
			return myObject.getUsuarios().getUsuario();
		} catch (Exception ex) {
			LOG.error("Invoking of web service " + url + " of siged was failed", ex);
            throw ex;
		}
		
	}
	
	@Override
	public UsuarioDetalleSigedDTO obtenerUsuarioSiged(Long idUsuario) throws Exception {
		String url=SIGED_WS_URL+SIGED_PATH_OBTENER_USUARIO;

		try {			
			RestTemplate restTemplate = new RestTemplate();
			LOG.debug("url [{}].", url);
			
			Object[] parametros = {idUsuario};
			String xmlString = String.format("<usuario><idUsuario>%s</idUsuario></usuario>", parametros);
			
			System.out.println(xmlString);
		
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_XML);
		    restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		    HttpEntity<String> request = new HttpEntity<String>(xmlString, headers);
		    
		    String result = restTemplate.postForObject(url, request, String.class);
			JAXBContext jaxbContext = JAXBContext.newInstance(UsuarioSigedResponseDTO.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			UsuarioSigedResponseDTO responseDTO = (UsuarioSigedResponseDTO) unmarshaller.unmarshal(new StringReader(result));
			UsuarioDetalleSigedDTO usuario = null;
			if (responseDTO.getResultCode() == 1 && responseDTO.getUsuarios() != null) {
				usuario = responseDTO.getUsuarios().getUsuarioList().get(0);
			} else {
				LOG.error("Error al obtener usuario de SIGED");
			}
			return usuario;
		} catch (Exception ex) {
			LOG.error("Invoking of web service " + url + " of siged was failed", ex);
            throw ex;
		}
		
	}

	@Override
	public ClienteConsultaOutRO buscarCliente(Integer tipoIdentificacion, String nroIdentificacion) {
		ClienteInRO clienteInRO = new ClienteInRO();
		clienteInRO.setCodigoTipoIdentificacion(tipoIdentificacion);
		clienteInRO.setNroIdentificacion(nroIdentificacion);
		String url= SIGED_WS_URL + SIGED_PATH_BUSCAR_CLIENTE;
		return ClienteInvoker.find(url, clienteInRO);
	}
	
}
