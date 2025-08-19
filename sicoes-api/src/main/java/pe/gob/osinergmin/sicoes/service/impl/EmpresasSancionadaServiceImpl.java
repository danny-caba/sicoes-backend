package pe.gob.osinergmin.sicoes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pe.gob.osinergmin.sicoes.repository.EmpresasSancionadaDao;
import pe.gob.osinergmin.sicoes.service.EmpresasSancionadaService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPElement;
import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.ZoneOffset;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

@Service
public class EmpresasSancionadaServiceImpl implements EmpresasSancionadaService {

	@Value("${osce.ws.url}")
	String PIDO_BUS_SERVER;
	
	@Value("${osce.bus.server.usuario}")
	String pidoBusServerUsuario;
	
	@Value("${osce.bus.server.password}")
	String pidoBusServerPassword;
	
	@Value("${osce.ws.path.sancion.vigente}")
	String OSCE_SANCION_VIGENTE;
	
	@Value("${siged.xml.listar.expediente.usuario}")
	String expedienteUser;
	
	@Value("${siged.xml.listar.expediente.ip}")
	String expedienteIp;
	
	@Value("${osce.ws.path.sancion.vigente.PN}")
	String urlSancionVigentePN;
	
	
	static String PIDO_BUS_SERVER_USUARIO;
	static String PIDO_BUS_SERVER_PASSWORD;
	@PostConstruct
	public void init() {
	    PIDO_BUS_SERVER_USUARIO = pidoBusServerUsuario;
	    PIDO_BUS_SERVER_PASSWORD = pidoBusServerPassword;
	}
	
	@Autowired
	EmpresasSancionadaDao empresasSancionadaDao;

	@Override
	public String validarEmpresaSancionada(String ruc) {
		String resultado=empresasSancionadaDao.validarEmpresaSancionada(ruc);
//		if(!"NO".equals(resultado)) {
//			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ERROR_EMPRESA_SANCIONADA);
//		}
		return resultado;
//		return "NO";
	}

	@Override
	public String validadSancion(String codigoRuc) {
		String resultadoValue = "";
		
		String urlBase = PIDO_BUS_SERVER;
		String urlService = OSCE_SANCION_VIGENTE;
		try {
			
			// Crear una conexión SOAP
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // URL del servicio SOAP
            String url = urlBase+urlService;
            System.out.println("Valor de url: " + url);
            // Crear la solicitud SOAP con encabezado
            SOAPMessage soapMessage = createSOAPRequestWithHeader(codigoRuc);
            
            // Enviar la solicitud SOAP y obtener la respuesta
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.setHeader("Content-Type", "application/soap+xml");

            SOAPMessage soapResponse = soapConnection.call(soapMessage, url);
            SOAPBody soapBody = soapResponse.getSOAPBody();

            // Buscar el elemento <ns2:resultado>
            NodeList resultadoNodes = soapBody.getElementsByTagNameNS("http://soa.osinergmin.gob.pe/service/osce/proveedoresinhabilitados/obtenerSancionVigente/v1.0", "resultado");
            if (resultadoNodes.getLength() > 0) {
                Node resultadoNode = resultadoNodes.item(0);
                resultadoValue = resultadoNode.getTextContent();
                System.out.println("Valor de <ns2:resultado>: " + resultadoValue);
            } else {
                System.out.println("Elemento <ns2:resultado> no encontrado.");
            }

            // Cerrar la conexión
            soapConnection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
		return resultadoValue;
	}

	@Override
	public List<String[]> validadSancionV2(String codigoRuc) {
		List<String[]> resultados = new ArrayList<>();

		String urlBase = PIDO_BUS_SERVER;
		String urlService = OSCE_SANCION_VIGENTE;
		try {

			// Crear una conexión SOAP
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// URL del servicio SOAP
			String url = urlBase+urlService;
			System.out.println("Valor de url: " + url);
			// Crear la solicitud SOAP con encabezado
			SOAPMessage soapMessage = createSOAPRequestWithHeader(codigoRuc);

			// Enviar la solicitud SOAP y obtener la respuesta
			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.setHeader("Content-Type", "application/soap+xml");

			SOAPMessage soapResponse = soapConnection.call(soapMessage, url);
			SOAPBody soapBody = soapResponse.getSOAPBody();

			String namespace = "http://soa.osinergmin.gob.pe/service/osce/proveedoresinhabilitados/obtenerSancionVigente/v1.0";

			// Buscar el elemento <ns2:resultado>
			NodeList resultadoNodes = soapBody.getElementsByTagNameNS(namespace, "resultado");
			if (resultadoNodes.getLength() > 0) {
				Node resultadoNode = resultadoNodes.item(0);
				String resValue = resultadoNode.getTextContent();
				String resName = resultadoNode.getNodeName();

				resultados.add(new String[]{resName, resValue});

				if (resultadoNode.getTextContent().equals("1")) {
					NodeList sancionNodes = soapBody.getElementsByTagNameNS(namespace, "sancion");
					for (int i = 0; i < sancionNodes.getLength(); i++) {
						Node sancionNode = sancionNodes.item(i);
						NodeList subNodes = sancionNode.getChildNodes();
						for (int j = 0; j < subNodes.getLength(); j++) {
							Node subNode = subNodes.item(j);
							if (subNode.getNodeType() == Node.ELEMENT_NODE) {
								resultados.add(new String[]{subNode.getNodeName(), subNode.getTextContent().trim()});
							}
						}
					}
				}

//				logger.info("Valor de <ns2:resultado>: " + resultadoNode.getTextContent());
			} else {
//				logger.info("Elemento <ns2:resultado> no encontrado.");
			}

			// Cerrar la conexión
			soapConnection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultados;
	}

	private static SOAPMessage createSOAPRequestWithHeader(String codigoRuc) throws Exception {
		 	ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

	        // Formatear la fecha y hora al formato deseado
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
	        String formattedDate = now.format(formatter);   
	        //Variables desde el properties
	        String User = PIDO_BUS_SERVER_USUARIO;
	        String Pass = PIDO_BUS_SERVER_PASSWORD;
	        System.out.println("Valor de User: " + User);
	        System.out.println("Valor de Pass: " + Pass);
	        // Crear el mensaje SOAP
	        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL); // Usar SOAP 1.2
	        SOAPMessage soapMessage = messageFactory.createMessage();

	        // Crear la parte del mensaje SOAP
	        SOAPPart soapPart = soapMessage.getSOAPPart();

	        // Crear el envelope SOAP
	        SOAPEnvelope envelope = soapPart.getEnvelope();
	        envelope.addNamespaceDeclaration("v1", "http://soa.osinergmin.gob.pe/service/osce/proveedoresinhabilitados/obtenerSancionVigente/v1.0");
	        envelope.addNamespaceDeclaration("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
	        envelope.addNamespaceDeclaration("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
	        envelope.addNamespaceDeclaration("con", "http://namespace-for-consumidor-element"); // Añadir el namespace correcto

	        // Crear el encabezado SOAP
	        SOAPHeader soapHeader = envelope.getHeader();

	        // Añadir el elemento Security al encabezado
	        SOAPElement security = soapHeader.addChildElement("Security", "wsse");
	        SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
	        usernameToken.addAttribute(new QName("wsu:Id"), "Id-0001427873415141-0000000076fdd541-1");

	        SOAPElement username = usernameToken.addChildElement("Username", "wsse");
	        username.addTextNode(User);

	        SOAPElement password = usernameToken.addChildElement("Password", "wsse");
	        password.addTextNode(Pass);

	        SOAPElement created = usernameToken.addChildElement("Created", "wsu");
	        created.addTextNode(formattedDate);


	        // Crear el cuerpo del mensaje SOAP
	        SOAPBody soapBody = envelope.getBody();
	        SOAPElement obtenerSancionVigente = soapBody.addChildElement("obtenerSancionVigente", "v1");
	        SOAPElement input = obtenerSancionVigente.addChildElement("input", "v1");
	        SOAPElement ruc = input.addChildElement("ruc", "v1");
	        ruc.addTextNode(codigoRuc);

	        // Guardar los cambios
	        soapMessage.saveChanges();


	        return soapMessage;
	    }

	@Override
	public String validadSancionPersonNatural(String documento) {
		String valor = "2";
		String fechaCeseStr = null;
		 try {
	            // Define la URL base y los parámetros
			 String dni = documento.substring(2, 10);
	            String baseUrl = urlSancionVigentePN;
	            String document = dni;
	            String codigoAplicativo = "SICOES";
	            String codigoConsulta = "A50";
	            
	            // Construir la URL con parámetros
	            String urlWithParams = String.format(
	                "%s?documento=%s&codigoAplicativo=%s&codigoConsulta=%s",
	                baseUrl,
	                URLEncoder.encode(document, StandardCharsets.UTF_8.toString()),
	                URLEncoder.encode(codigoAplicativo, StandardCharsets.UTF_8.toString()),
	                URLEncoder.encode(codigoConsulta, StandardCharsets.UTF_8.toString())
	            );
	            System.out.println("Valor de urlWithParams: " + urlWithParams);
	            // Crear una URL y abrir una conexión
	            URL url = new URL(urlWithParams);
	            HttpURLConnection con = (HttpURLConnection) url.openConnection();
	            con.setRequestMethod("GET");

	            // Leer la respuesta
	            int responseCode = con.getResponseCode();
	            System.out.println("Response Code: " + responseCode);
	            if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
	                System.out.println("Error 404: Recurso no encontrado.");
	                // Aquí puedes manejar el caso cuando la respuesta es 404
	                fechaCeseStr = "2"; // O cualquier lógica que desees aplicar en caso de un 404
	            } else if (responseCode == HttpURLConnection.HTTP_OK) {
		            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		            String inputLine;
		            StringBuilder response = new StringBuilder();
	
		            while ((inputLine = in.readLine()) != null) {
		                response.append(inputLine);
		            }
		            in.close();
	
	
		            // Convertir la respuesta a JSON y extraer el campo `status`
		            JSONObject jsonResponse = new JSONObject(response.toString());
		            String status = jsonResponse.getString("status");
		            
		            if(status.equalsIgnoreCase("success")) {
		            	JSONObject result = jsonResponse.getJSONObject("result");
		            	String descripcionPuesto = result.optString("descripcionPuesto", null);

		            	if (descripcionPuesto != null && !descripcionPuesto.isEmpty()) {
		                    if (descripcionPuesto.toUpperCase().contains("PRACTICANTE") || descripcionPuesto.toUpperCase().contains("CONSEJO DIRECTIVO") ) {
		                        fechaCeseStr = "2"; // Si la descripción contiene 'PRACTICANTE'
		                    } else {
		                        fechaCeseStr = result.optString("fechaCese", null); // Extraer fecha de cese si no es practicante
		                    }
		                } else {
		                    fechaCeseStr = result.optString("fechaCese", null); // Extraer fecha de cese si no hay puesto
		                }
	
		            }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
		return fechaCeseStr;
    }

	@Override
	public String validadSancionPersonNaturalFec(String documento) {
		String valor = "2";
		 try {
	            // Define la URL base y los parámetros
			 String dni = documento.substring(2, 10);
	            String baseUrl = urlSancionVigentePN;
	            String document = dni;
	            String codigoAplicativo = "SICOES";
	            String codigoConsulta = "A50";

	            // Construir la URL con parámetros
	            String urlWithParams = String.format(
	                "%s?documento=%s&codigoAplicativo=%s&codigoConsulta=%s",
	                baseUrl,
	                URLEncoder.encode(document, StandardCharsets.UTF_8.toString()),
	                URLEncoder.encode(codigoAplicativo, StandardCharsets.UTF_8.toString()),
	                URLEncoder.encode(codigoConsulta, StandardCharsets.UTF_8.toString())
	            );
	            System.out.println("Valor de urlWithParams: " + urlWithParams);
	            // Crear una URL y abrir una conexión
	            URL url = new URL(urlWithParams);
	            HttpURLConnection con = (HttpURLConnection) url.openConnection();
	            con.setRequestMethod("GET");

	            // Leer la respuesta
	            int responseCode = con.getResponseCode();
	            System.out.println("Response Code: " + responseCode);
	            if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
	                System.out.println("Error 404: Recurso no encontrado.");
	                // Aquí puedes manejar el caso cuando la respuesta es 404
	                valor = "1"; // O cualquier lógica que desees aplicar en caso de un 404
	            } else if (responseCode == HttpURLConnection.HTTP_OK) {
		            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		            String inputLine;
		            StringBuilder response = new StringBuilder();
	
		            while ((inputLine = in.readLine()) != null) {
		                response.append(inputLine);
		            }
		            in.close();
	
	
		            // Convertir la respuesta a JSON y extraer el campo `status`
		            JSONObject jsonResponse = new JSONObject(response.toString());
		            String status = jsonResponse.getString("status");
		            
		            if(status.equalsIgnoreCase("success")) {
		            	JSONObject result = jsonResponse.getJSONObject("result");
		                String fechaCeseStr = result.optString("fechaCese", null);

		                if (fechaCeseStr != null) {
		                	// Parsear la fechaCese
		                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		                    LocalDate fechaCese = LocalDate.parse(fechaCeseStr, formatter);

		                    // Calcular la fecha actual menos 12 meses
		                    LocalDate fechaLimite = LocalDate.now().minusMonths(12);
		                 // Comparar las fechas
		                    if (fechaCese.isAfter(fechaLimite)) {
		                        valor = "1"; // Si fechaCese es menor a 12 meses desde la fecha actual
		                    }
		                }else {
		                    // Si fechaCese es null
		                    valor = "1";
		                }
		            }
		            else {
		                // En caso de que el status no sea "success"
		                valor = "2";
		            }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
		return valor;
	}

	@Override
	public Map<String, String> validadSancionPersonNaturalV2(String documento) {
		Map<String, String> resultados = new HashMap<>();
		String valor = "2";
		String fechaCeseStr = null;
		String areaOperativa = null;
		String fechaIngreso = null;
		String descripcionPuesto = null;
		try {
			// Define la URL base y los parámetros
			String dni = documento.substring(2, 10);
			String baseUrl = urlSancionVigentePN;
			String document = dni;
			String codigoAplicativo = "SICOES";
			String codigoConsulta = "A50";

			// Construir la URL con parámetros
			String urlWithParams = String.format(
					"%s?documento=%s&codigoAplicativo=%s&codigoConsulta=%s",
					baseUrl,
					URLEncoder.encode(document, StandardCharsets.UTF_8.toString()),
					URLEncoder.encode(codigoAplicativo, StandardCharsets.UTF_8.toString()),
					URLEncoder.encode(codigoConsulta, StandardCharsets.UTF_8.toString())
			);
			System.out.println("Valor de urlWithParams: " + urlWithParams);
			// Crear una URL y abrir una conexión
			URL url = new URL(urlWithParams);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			// Leer la respuesta
			int responseCode = con.getResponseCode();
			System.out.println("Response Code: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
				System.out.println("Error 404: Recurso no encontrado.");
				// Aquí puedes manejar el caso cuando la respuesta es 404
				fechaCeseStr = "2"; // O cualquier lógica que desees aplicar en caso de un 404
			} else if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();


				// Convertir la respuesta a JSON y extraer el campo `status`
				JSONObject jsonResponse = new JSONObject(response.toString());
				String status = jsonResponse.getString("status");

				if(status.equalsIgnoreCase("success")) {
					JSONObject result = jsonResponse.getJSONObject("result");
					descripcionPuesto = result.optString("descripcionPuesto", null);
					areaOperativa = result.optString("areaOperativa", null);
					fechaIngreso = result.optString("fechaIngreso", null);

					if (descripcionPuesto != null && !descripcionPuesto.isEmpty()) {
						if (descripcionPuesto.toUpperCase().contains("PRACTICANTE") || descripcionPuesto.toUpperCase().contains("CONSEJO DIRECTIVO") ) {
							fechaCeseStr = "2"; // Si la descripción contiene 'PRACTICANTE'
						} else {
							fechaCeseStr = result.optString("fechaCese", null); // Extraer fecha de cese si no es practicante
						}
					} else {
						fechaCeseStr = result.optString("fechaCese", null); // Extraer fecha de cese si no hay puesto
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		resultados.put("fechaCeseStr", fechaCeseStr);
		resultados.put("areaOperativa", areaOperativa);
		resultados.put("fechaIngreso", fechaIngreso);
		resultados.put("descripcionPuesto", descripcionPuesto);

		return resultados;
	}
}

