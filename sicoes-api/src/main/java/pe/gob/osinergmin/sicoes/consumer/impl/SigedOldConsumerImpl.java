package pe.gob.osinergmin.sicoes.consumer.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Ubigeo;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.bean.AlfrescoFileOut;
import pe.gob.osinergmin.sicoes.util.bean.siged.AccessRequestInFirmaDigital;

@Component
public class SigedOldConsumerImpl implements SigedOldConsumer{

	@Value("${siged.old.ws.url}")
	private String SIGED_WS_URL;
	
	@Value("${siged.old.ws.path.subir.archivo}")
	private String SIGED_PATH_SUBIR_ARCHIVO;
	
	@Value("${siged.old.ws.path.descargar.archivo}")
	private String SIGED_PATH_DESCARGAR_ARCHIVO;
	
	@Value("${siged.old.user}")
	private String SIGED_USER;
	
	@Value("${siged.old.base.path}")
	private String SIGED_PATH_BASE;
	
	@Value("${siged.old.ubigeo.departamentos.path}")
	private String PIDO_PATH_DEPARTAMENTO;			

	@Value("${siged.old.ubigeo.provincias.path}")
	private String PIDO_PATH_PROVINCIAS;
	
	@Value("${siged.old.ubigeo.distritos.path}")
	private String PIDO_PATH_DISTRITOS;
	
	@Value("${siged.ws.path.obtener.id.archvivo}")
	private String SIGED_PATH_OBTENER_ID_ARCHIVO;
	
	@Value("${siged.ws.path.firma.digital}")
	private String SIGED_PATH_FIRMA_DIGITAL;
	
	@Value("${siged.ws.usuario.firma.digital}")
	private String SIGED_USER_FIRMA_DIGITAL;
	
	@Value("${siged.ws.password.firma.digital}")
	private String SIGED_PASSWORD_FIRMA_DIGITAL;
	
	Logger logger = LogManager.getLogger(SigedOldConsumerImpl.class);

	public String subirArchivosAlfresco(Long idSolicitud,Long idPropuesta,Long idProceso,Long idSeccionRequisito,Long idContrato,Long idSoliPerfCont,Archivo archivo) {
		
		try {
			
			RestTemplate restTemplate=new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
	        ContentDisposition contentDisposition = ContentDisposition
	                .builder("form-data")
	                .name("file")
	                .filename(archivo.getNombreReal())
	                .build();
	        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
	        HttpEntity<byte[]> fileEntity=null;
	        
	        if(archivo.getFile()!=null) {
	        	fileEntity = new HttpEntity<>(archivo.getFile().getBytes(), fileMap);
	        }else {
	        	fileEntity = new HttpEntity<>(archivo.getContenido(), fileMap);
	        }
	        
	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	        body.add("file", fileEntity);

	        HttpEntity<MultiValueMap<String, Object>> requestEntity =
	                new HttpEntity<>(body, headers);
	        String path="";
	        if(archivo.getIdSolicitud()!=null) {
	        	path=SIGED_WS_URL+SIGED_PATH_SUBIR_ARCHIVO+SIGED_USER+SIGED_PATH_BASE+"/SOLICITUD/"+idSolicitud;	
	        }else if(archivo.getIdPropuesta()!=null) {
	        	path=SIGED_WS_URL+SIGED_PATH_SUBIR_ARCHIVO+SIGED_USER+SIGED_PATH_BASE+"/PROPUESTA/"+idPropuesta;
	        }else if(archivo.getIdProceso()!=null) {
	        	path=SIGED_WS_URL+SIGED_PATH_SUBIR_ARCHIVO+SIGED_USER+SIGED_PATH_BASE+"/PROCESO/"+idProceso;
	        }else if(archivo.getIdContrato()!=null) {
	        	path=SIGED_WS_URL+SIGED_PATH_SUBIR_ARCHIVO+SIGED_USER+SIGED_PATH_BASE+"/CONTRATO/"+idContrato;
	        }else if(archivo.getIdSoliPerfCont()!=null) {
	        	path=SIGED_WS_URL+SIGED_PATH_SUBIR_ARCHIVO+SIGED_USER+SIGED_PATH_BASE+"/PERFECCIONAMIENTO_CONTRATO/"+idSoliPerfCont;
	        }else if(archivo.getIdSeccionRequisito()!=null) {
	        	path=SIGED_WS_URL+SIGED_PATH_SUBIR_ARCHIVO+SIGED_USER+SIGED_PATH_BASE+"/PERFECCIONAMIENTO_REQUISITO/"+idSeccionRequisito;
			}else {
	        	logger.info("Sin path enviar idSolicitud o idPropuesta "+path);
	        }
	        
	        logger.info("Enviado a alfresco : "+path);
	        logger.info("nombre Archivo: "+archivo.getNombreReal());
	        if(archivo.getFile()!=null) {
	        	logger.info("contenido Archivo: "+archivo.getFile().getBytes().length);	
	        }else {
	        	logger.info("contenido Archivo: "+archivo.getContenido().length);
	        }
	            ResponseEntity<String> response = restTemplate.exchange(
	            		path,
	                    HttpMethod.POST,
	                    requestEntity,
	                    String.class);
	         
	            XmlMapper xmlMapper = new XmlMapper();
	            AlfrescoFileOut fileOut=xmlMapper.readValue(response.getBody(), AlfrescoFileOut.class);
	            logger.info("respuesta: "+fileOut);
	            return  fileOut.getFiles().getFullFilePath();
	            
	        } catch (Exception e) {
	        	logger.error(e.getMessage(), e);
	            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_PROBLEMA_SUBIR_ALFRESCO,e);
	        }
	}

	@Override
	public byte[] descargarArchivosAlfresco(Archivo archivo) {
		try {
			logger.info("NOMBREARCHIVO---------------"+SIGED_WS_URL+SIGED_PATH_DESCARGAR_ARCHIVO+SIGED_USER+"/"+archivo.getNombreAlFresco());
			RestTemplate restTemplate=new RestTemplate();
		        byte[] imageBytes = restTemplate.getForObject(SIGED_WS_URL+SIGED_PATH_DESCARGAR_ARCHIVO+SIGED_USER+"/"+archivo.getNombreAlFresco(), byte[].class);
	            return imageBytes;
	            
	        } catch (Exception e) {
	        	logger.error(e.getMessage(), e);
	        	 throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_PROBLEMA_DESCARGAR_ALFRESCO,e);
	        }
	}

	@Override
	public byte[] descargarArchivoPorUuidAlfresco(String uuid) {
		try {
			// Validar UUID
			if (uuid == null || uuid.trim().isEmpty()) {
				throw new IllegalArgumentException("UUID del archivo es requerido");
			}
			
			// Limpiar UUID para prevenir path traversal
			String uuidLimpio = uuid.trim().replaceAll("[^a-zA-Z0-9\\-_]", "");
			
			// Construir URL para descarga usando UUID - formato: /archivos/{uuid}/descarga
			String urlDescarga = SIGED_WS_URL + "/archivos/" + uuidLimpio + "/descarga";
			
			logger.info("Descargando archivo desde Alfresco - UUID: {}, URL: {}", uuidLimpio, urlDescarga);
			
			RestTemplate restTemplate = new RestTemplate();
			
			// Configurar headers si es necesario
			HttpHeaders headers = new HttpHeaders();
			headers.add("User-Agent", "SICOES-API");
			HttpEntity<String> entity = new HttpEntity<>(headers);
			
			// Realizar petición GET para descargar el archivo
			ResponseEntity<byte[]> response = restTemplate.exchange(
				urlDescarga, 
				HttpMethod.GET, 
				entity, 
				byte[].class
			);
			
			byte[] contenidoArchivo = response.getBody();
			
			if (contenidoArchivo == null || contenidoArchivo.length == 0) {
				logger.warn("Archivo no encontrado o vacío para UUID: {}", uuidLimpio);
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENCONTRADO);
			}
			
			logger.info("Archivo descargado exitosamente - UUID: {}, Tamaño: {} bytes", uuidLimpio, contenidoArchivo.length);
			return contenidoArchivo;
			
		} catch (HttpClientErrorException e) {
			logger.error("Error del cliente al descargar archivo - UUID: {}, Status: {}, Response: {}", 
						uuid, e.getStatusCode(), e.getResponseBodyAsString());
			
			if (e.getStatusCode().value() == 404) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENCONTRADO, e);
			} else if (e.getStatusCode().value() == 403) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_SIN_PERMISOS, e);
			} else {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_PROBLEMA_DESCARGAR_ALFRESCO, e);
			}
			
		} catch (HttpServerErrorException e) {
			logger.error("Error del servidor al descargar archivo - UUID: {}, Status: {}, Response: {}", 
						uuid, e.getStatusCode(), e.getResponseBodyAsString());
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_PROBLEMA_DESCARGAR_ALFRESCO, e);
			
		} catch (ValidacionException e) {
			// Re-lanzar excepciones de validación
			throw e;
			
		} catch (Exception e) {
			logger.error("Error inesperado al descargar archivo por UUID: " + uuid, e);
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_PROBLEMA_DESCARGAR_ALFRESCO, e);
		}
	}
	
	public List<Ubigeo> departamentos()  {
		List<Ubigeo> listUbigeo=new ArrayList<Ubigeo>();
		try {
			Document document = sendPidoRequestGet(SIGED_WS_URL+PIDO_PATH_DEPARTAMENTO);
			NodeList idDepartamentos=document.getElementsByTagName("idDepartamento");
			NodeList nombres=document.getElementsByTagName("nombre");
			
			for(int i=0;i<idDepartamentos.getLength();i++) {
				Ubigeo ubigeo=new Ubigeo();
				Node idDepartamento=idDepartamentos.item(i);
				Node nombre=nombres.item(i);
				ubigeo.setCodigo(idDepartamento.getTextContent());
				ubigeo.setNombre(nombre.getTextContent());
				listUbigeo.add(ubigeo);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return listUbigeo;
	}
	
	@Override
	public List<Ubigeo> provincias(String codigoDepartamento) {
		List<Ubigeo> listUbigeo=new ArrayList<Ubigeo>();
		try {
			Document document = sendPidoRequestGet(SIGED_WS_URL+PIDO_PATH_PROVINCIAS+codigoDepartamento);
			NodeList idProvincias=document.getElementsByTagName("idProvincia");
			NodeList nombres=document.getElementsByTagName("nombre");
			
			for(int i=0;i<idProvincias.getLength();i++) {
				Ubigeo ubigeo=new Ubigeo();
				Node idProvincia=idProvincias.item(i);
				Node nombre=nombres.item(i);
				ubigeo.setCodigo(idProvincia.getTextContent());
				ubigeo.setNombre(nombre.getTextContent());
				listUbigeo.add(ubigeo);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return listUbigeo;
	}

	@Override
	public List<Ubigeo> distritos(String codigoProvincia) {
		List<Ubigeo> listUbigeo=new ArrayList<Ubigeo>();
		try {
			Document document = sendPidoRequestGet(SIGED_WS_URL+PIDO_PATH_DISTRITOS+codigoProvincia);
			NodeList idProvincias=document.getElementsByTagName("idDistrito");
			NodeList nombres=document.getElementsByTagName("nombre");
			
			for(int i=0;i<idProvincias.getLength();i++) {
				Ubigeo ubigeo=new Ubigeo();
				Node idProvincia=idProvincias.item(i);
				Node nombre=nombres.item(i);
				ubigeo.setCodigo(idProvincia.getTextContent());
				ubigeo.setNombre(nombre.getTextContent());
				listUbigeo.add(ubigeo);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return listUbigeo;
	}
	
	private Document sendPidoRequestGet(String URL,Object... uriVariables) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/soap+xml");
		restTemplate.getMessageConverters()
        .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		HttpEntity<String> request = new HttpEntity<String>( headers);	
		String xmlResponse=null;
		try {
			xmlResponse = restTemplate.getForObject(URL, String.class,uriVariables);
		} catch (HttpServerErrorException ex) {
			try {
				InputStream is2 = new ByteArrayInputStream(ex.getResponseBodyAsByteArray());
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(is2);
				return doc;
			}catch (Exception e) {
				logger.error("Error al obtener response", e);
				return null;
			}
		}
		try {
			InputStream is = new ByteArrayInputStream(xmlResponse.getBytes(StandardCharsets.UTF_8));
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);
			return  doc;
		} catch (Exception e) {
			logger.error("Error al obtener response", e);
			return null;
		}
	}
	
	public Long obtenerIdArchivos(String numeroExpediente, String nombreUsuario) throws Exception{
		
		logger.info("obtenerIdArchivos inicio...");
		
		String url = SIGED_WS_URL + SIGED_PATH_OBTENER_ID_ARCHIVO + "/" + numeroExpediente + "/1";
		Long idArchivo = 0L;
		Date fechaCreacion = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {			
			RestTemplate restTemplate = new RestTemplate();
			logger.info("url [{}].", url);			
			
			String response = restTemplate.getForObject(url, String.class);
			
			while (true) {
				Integer indexInformeVerificacion = response.indexOf("INFORME_VERIFICACION_TECNICA_" + nombreUsuario);
				Integer indexInformeResultado = response.indexOf("INFORME_RESULTADO_ADMINISTRATIVO_" + nombreUsuario);
				
				if (indexInformeVerificacion > 0 || indexInformeResultado > 0) {
					
					Integer indexInforme = indexInformeVerificacion > 0 ? indexInformeVerificacion : indexInformeResultado;
					
					//Obtener los campos del informe
					String partialResponse = response.substring(0, indexInforme);				
					Integer indexInicioArchivo = partialResponse.lastIndexOf("<archivo>") + "<archivo>".length();				
					partialResponse = response.substring(indexInforme);				
					Integer indexFinalArchivo = partialResponse.indexOf("</archivo>");
					
					//En caso exista más de informe a firmar
					String afterResponse = response.substring(indexInforme + indexFinalArchivo + "</archivo>".length());
					response = response.substring(indexInicioArchivo, indexInforme + indexFinalArchivo).trim();
					
					//Obtener la fecha de creación
					Integer indexFechaCreacionArchivo = response.indexOf("<fechaCreacion>") + "<fechaCreacion>".length();
					String fechaCreacionResponse = response.substring(indexFechaCreacionArchivo);
					fechaCreacionResponse = fechaCreacionResponse.substring(0, fechaCreacionResponse.indexOf("</fechaCreacion>"));
					Date fechaCreacionArchivo = formatter.parse(fechaCreacionResponse.substring(0, fechaCreacionResponse.indexOf("T")) + " " +
							fechaCreacionResponse.substring(fechaCreacionResponse.indexOf("T") + 1, fechaCreacionResponse.indexOf("T") + 9));
					
					//Obtener el idArchivo del informe
					Integer indexIdArchivo = response.indexOf("<idArchivo>") + "<idArchivo>".length();
					response = response.substring(indexIdArchivo);
					response = response.substring(0, response.indexOf("</idArchivo>"));
					Long idArchivoTemp = Long.parseLong(response.trim());
					
					response = afterResponse;
					
					if (fechaCreacion == null) {
						idArchivo = idArchivoTemp;
						fechaCreacion = fechaCreacionArchivo;
					}
					else if (fechaCreacionArchivo.compareTo(fechaCreacion) > 0) {
						idArchivo = idArchivoTemp;
						fechaCreacion = fechaCreacionArchivo;
					}
				}
				else {
					break;
				}
			}
			logger.info("idArchivo: " + idArchivo);
		}
		catch (HttpClientErrorException ex) {
			logger.error("Invoking of web service " + url + " of siged was failed", ex);
			throw new ValidacionException(ex.getRawStatusCode()+"",ex.getMessage());
		}
		catch (Exception ex) {
			logger.error("Invoking of web service " + url + " of siged was failed", ex);
			throw ex;
		}
		
		logger.info("obtenerIdArchivos fin...");
		
		return idArchivo;
	}
	
	public AccessRequestInFirmaDigital obtenerParametrosfirmaDigital() {
		
		AccessRequestInFirmaDigital parametros = new AccessRequestInFirmaDigital();
		
		parametros.setAction(SIGED_PATH_FIRMA_DIGITAL);
		parametros.setLoginUsuario(SIGED_USER_FIRMA_DIGITAL);
		parametros.setPasswordUsuario(SIGED_PASSWORD_FIRMA_DIGITAL);
		
		return parametros;
	}

	/**
	 * Obtiene el identificador de archivos asociados a la renovación de contrato (Requerimiento 350) 
	 * a partir del número de expediente proporcionado.
	 *
	 * @param numeroExpediente El número de expediente para el cual se requiere obtener el ID de archivo.
	 * @return El identificador del archivo relacionado con la renovación de contrato.
	 * @throws Exception Si ocurre un error al invocar el servicio web de SIGED.
	 * 
	 * Requerimiento: 350 - Renovación de contrato.
	 */
	public Long obtenerIdArchivosRenovacionContrato(String numeroExpediente) throws Exception{

		logger.info("obtenerIdArchivos inicio...");

		String url = SIGED_WS_URL + SIGED_PATH_OBTENER_ID_ARCHIVO + "/" + numeroExpediente + "/1";
		Long idArchivo = 0L;

		try {
			RestTemplate restTemplate = new RestTemplate();
			logger.info("url [{}].", url);
			String response = restTemplate.getForObject(url, String.class);
			logger.info("response: " + response);

			logger.info("idArchivo: " + idArchivo);
		}
		catch (HttpClientErrorException ex) {
			logger.error("Invoking of web service " + url + " of siged was failed", ex);
			throw new ValidacionException(ex.getRawStatusCode()+"",ex.getMessage());
		}
		catch (Exception ex) {
			logger.error("Invoking of web service " + url + " of siged was failed", ex);
			throw ex;
		}

		logger.info("obtenerIdArchivos fin...");

		return idArchivo;
	}

}
