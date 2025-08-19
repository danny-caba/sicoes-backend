package pe.gob.osinergmin.sicoes.consumer.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import gob.osinergmin.sne.domain.dto.rest.out.PidoOutRO;
import pe.gob.osinergmin.sicoes.consumer.PidoConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.Ubigeo;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.bean.GradoTituloRO;
import pe.gob.osinergmin.sicoes.util.bean.PidoBeanOutRO;
import pe.gob.osinergmin.sicoes.util.bean.SuneduOutRO;
import pe.gob.osinergmin.sicoes.util.bean.siged.UnidadOutRO;
import pe.gob.osinergmin.sicoes.util.bean.siged.UsuarioOutRO;
import pe.gob.osinergmin.soa.ConsultarContribuyenteRucServiceLocator;
import pe.gob.osinergmin.soa.DatosBasicosCiudadanoOrquestadoServiceLocator;
import pe.gob.osinergmin.soa.ListarDocumentoExpedienteServiceLocator;
import pe.gob.osinergmin.soa.ListarUnidadServiceLocator;
import pe.gob.osinergmin.soa.ListarUsuariosServiceLocator;
import pe.gob.osinergmin.soa.ObtenerDatosBasicoCiudadanoExtranjeroServiceLocator;
import pe.gob.osinergmin.soa.ObtenerDatosBasicoCiudadanoExtranjeroServiceLocatorException;
import pe.gob.osinergmin.soa.ObtenerGradosTitulosServiceLocator;
import pe.gob.osinergmin.soa.schema.comun.consumidor.sunat.ruc.CanalConsumoTYPE;
import pe.gob.osinergmin.soa.schema.comun.consumidor.sunat.ruc.ConsumidorTYPE;
import pe.gob.osinergmin.soa.schema.comun.consumidor.sunat.ruc.OrganizacionTYPE;
import pe.gob.osinergmin.soa.schema.gradostitulos.gradotituloacademico.obtener._1.GradoAcademicoTYPE;
import pe.gob.osinergmin.soa.schema.gradostitulos.gradotituloacademico.obtener._1.GradoTituloAcademicoObtenerReqParamTYPE;
import pe.gob.osinergmin.soa.schema.gradostitulos.gradotituloacademico.obtener._1.GradoTituloAcademicoObtenerRespParamTYPE;
//import pe.gob.osinergmin.soa.schema.migraciones.ciudadanoextranjero.obtener._1.CiudadanoExtranjeroObtenerReqParamTYPE;
//import pe.gob.osinergmin.soa.schema.migraciones.ciudadanoextranjero.obtener._1.CiudadanoExtranjeroObtenerRespParamTYPE;
import pe.gob.osinergmin.soa.service.expediente.documentos.listardocumentos.v1.AccesoTypeAP4034;
import pe.gob.osinergmin.soa.service.expediente.documentos.listardocumentos.v1.ListarDocumentos;
import pe.gob.osinergmin.soa.service.expediente.documentos.listardocumentos.v1.ListarDocumentosResponse;
import pe.gob.osinergmin.soa.service.expediente.documentos.listardocumentos.v1.ListarDocumentosResponse.Documento;
import pe.gob.osinergmin.soa.service.expediente.documentos.listardocumentos.v1.ListarDocumentosResponse.Documento.Archivos.Archivo;
import pe.gob.osinergmin.soa.service.expediente.documentos.listardocumentos.v1.ParametrosTypeAP4034;
import pe.gob.osinergmin.soa.service.expediente.documentos.listardocumentos.v1.ServiceAP4034;
import pe.gob.osinergmin.soa.service.expediente.documentos.usuariolist.v1.ListarUsuariosResponse;
import pe.gob.osinergmin.soa.service.expediente.documentos.usuariolist.v1.ServiceAP40326;
import pe.gob.osinergmin.soa.service.expediente.unidad.listar.v1.ListarUnidadAP40330;
import pe.gob.osinergmin.soa.service.expediente.unidad.listar.v1.ListarUnidadResponseAP40330;
import pe.gob.osinergmin.soa.service.expediente.unidad.listar.v1.ServiceAP40330;
import pe.gob.osinergmin.soa.service.gradostitulos.gradotituloacademico.obtener._1.GradoTituloAcademicoObtenerFaultMsg;
import pe.gob.osinergmin.soa.service.gradostitulos.gradotituloacademico.obtener._1.GradoTituloAcademicoObtenerPT;
//import pe.gob.osinergmin.soa.service.migraciones.ciudadanoextranjero.obtener._1.CiudadanoExtranjeroObtenerPT;
import pe.org.osinergmin.soa.schema.consultaidentificacion.ciudadano.orquestado.CiudadanoOrquestadoConsultarReqParamTYPE;
import pe.org.osinergmin.soa.schema.consultaidentificacion.ciudadano.orquestado.CiudadanoOrquestadoConsultarRespParamTYPE;
import pe.org.osinergmin.soa.schema.consultarucdatosprincipales.contribuyentes.consultar._1.ContribuyentesConsultarReqParamTYPE;
import pe.org.osinergmin.soa.schema.consultarucdatosprincipales.contribuyentes.consultar._1.ContribuyentesConsultarRespParamTYPE;
import pe.org.osinergmin.soa.schema.consultarucdatosprincipales.contribuyentes.consultar._1.DatosPrincipalesTYPE;
import pe.org.osinergmin.soa.service.consultaidentificacion.ciudadano.orquestado.CiudadanoConsultarPT;
import pe.org.osinergmin.soa.service.consultarucdatosprincipales.contribuyentes.consultar._1.ContribuyentesConsultarPT;
import pe.gob.osinergmin.soa.service.expediente.documentos.usuariolist.v1.Void;
import pe.gob.osinergmin.soa.service.expediente.migraciones.carnetextranjeria.v1.ConsultarCarnetExtranjeria;
import pe.gob.osinergmin.soa.service.expediente.migraciones.carnetextranjeria.v1.ConsultarCarnetExtranjeriaResponse;
import pe.gob.osinergmin.soa.service.expediente.migraciones.carnetextranjeria.v1.ServiceAP40411;

@Component
public class PidoConsumerImpl implements PidoConsumer {

	private Logger logger = LogManager.getLogger(PidoConsumerImpl.class);

	@Value("${pido.ws.url}")
	String PIDO_BUS_SERVER;
	@Value("${siged.bus.server.usuario}")
	String PIDO_BUS_SERVER_USUARIO;
	@Value("${siged.bus.server.password}")
	String PIDO_BUS_SERVER_PASSWORD;

	@Value("${pido.ws.path.contribuyente}")
	String PIDO_BUS_OBTENER_CONTRIBUYENTE;
	@Value("${pido.ws.consumidor.sistema.header}")
	String PIDO_WS_CONSUMIDOR_SISTEMA;
	@Value("${pido.ws.consumidor.modulo.header}")
	String PIDO_WS_CONSUMIDOR_MODULO;
	@Value("${pido.ws.consumidor.usuario.header}")
	String PIDO_WS_CONSUMIDOR_USUARIO;
	@Value("${pido.ws.consumidor.ip.header}")
	String PIDO_WS_CONSUMIDOR_IP;

	@Value("${pido.ws.reniec.ciudadano.orquestado.dniusuario.body}")
	String PIDO_WS_DNI_USUARIO;
	@Value("${pido.ws.reniec.ciudadano.orquestado.password.body}")
	String PIDO_WS_PASSWORD;
	@Value("${pido.ws.reniec.ciudadano.orquestado.dni.usuario}")
	String PIDO_PATH_CIUDADANO_ORQUESTADO_DNIUSUARIORENIEC;
	@Value("${pido.ws.reniec.ciudadano.orquestado.path}")
	String PIDO_PATH_CIUDADANO_ORQUESTADO;

	@Value("${pido.ws.path.ciudadano.extranjero}")
	String PIDO_PATH_CIUDADANO_EXTRANJERO;

	@Value("${pido.ws.path.sunedu}")
	String PIDO_PATH_SUNEDU;

	@Value("${pido.ws.path.listar.expediente}")
	String PIDO_PATH_LISTAR_EXPEDIENTE;

	@Value("${siged.bus.server.id.usuario}")
	String SIGED_XML_LISTAR_EXPEDIENTE_ID_USUARIO;

	@Value("${pido.ws.path.listar.unidad}")
	String PIDO_PATH_LISTAR_UNIDAD;

	@Value("${pido.ws.path.listar.usuarios}")
	String PIDO_PATH_LISTAR_USUARIO;

	@Autowired
	SigedOldConsumer sigedOldConsumer;

	@Autowired
	private Environment env;

	private pe.gob.osinergmin.soa.schema.comun.consumidor.lde.ConsumidorTYPE getConsumidorTYPELDE() {
		pe.gob.osinergmin.soa.schema.comun.consumidor.lde.ConsumidorTYPE consumidorTYPE = new pe.gob.osinergmin.soa.schema.comun.consumidor.lde.ConsumidorTYPE();
		consumidorTYPE.setCanal(pe.gob.osinergmin.soa.schema.comun.consumidor.lde.CanalConsumoTYPE.WEB);
		consumidorTYPE.setOrganizacion(pe.gob.osinergmin.soa.schema.comun.consumidor.lde.OrganizacionTYPE.OSINERGMIN);
		consumidorTYPE.setSistema(PIDO_WS_CONSUMIDOR_SISTEMA);
		consumidorTYPE.setModulo(PIDO_WS_CONSUMIDOR_MODULO);
		consumidorTYPE.setUsuario(PIDO_WS_CONSUMIDOR_USUARIO);
		consumidorTYPE.setIp(PIDO_WS_CONSUMIDOR_IP);
		return consumidorTYPE;
	}

	private ConsumidorTYPE getConsumidorTYPE() {
		ConsumidorTYPE consumidorTYPE = new ConsumidorTYPE();
		consumidorTYPE.setCanal(CanalConsumoTYPE.WEB);
		consumidorTYPE.setOrganizacion(OrganizacionTYPE.OSINERGMIN);
		consumidorTYPE.setSistema(PIDO_WS_CONSUMIDOR_SISTEMA);
		consumidorTYPE.setModulo(PIDO_WS_CONSUMIDOR_MODULO);
		consumidorTYPE.setUsuario(PIDO_WS_CONSUMIDOR_USUARIO);
		consumidorTYPE.setIp(PIDO_WS_CONSUMIDOR_IP);
		return consumidorTYPE;
	}

	private pe.gob.osinergmin.soa.schema.comun.consumidor.gradotituloacademico.ConsumidorTYPE getConsumidorTYPE3() {
		pe.gob.osinergmin.soa.schema.comun.consumidor.gradotituloacademico.ConsumidorTYPE consumidorTYPE = new pe.gob.osinergmin.soa.schema.comun.consumidor.gradotituloacademico.ConsumidorTYPE();
		consumidorTYPE
				.setCanal(pe.gob.osinergmin.soa.schema.comun.consumidor.gradotituloacademico.CanalConsumoTYPE.WEB);
		consumidorTYPE.setOrganizacion(
				pe.gob.osinergmin.soa.schema.comun.consumidor.gradotituloacademico.OrganizacionTYPE.OSINERGMIN);
		consumidorTYPE.setSistema(PIDO_WS_CONSUMIDOR_SISTEMA);
		consumidorTYPE.setModulo(PIDO_WS_CONSUMIDOR_MODULO);
		consumidorTYPE.setUsuario(PIDO_WS_CONSUMIDOR_USUARIO);
		consumidorTYPE.setIp(PIDO_WS_CONSUMIDOR_IP);
		return consumidorTYPE;
	}

	private pe.gob.osinergmin.soa.schema.comun.consumidor.ConsumidorTYPE getConsumidorTYPEComun() {
		pe.gob.osinergmin.soa.schema.comun.consumidor.ConsumidorTYPE consumidorTYPE = new pe.gob.osinergmin.soa.schema.comun.consumidor.ConsumidorTYPE();
		consumidorTYPE.setCanal(pe.gob.osinergmin.soa.schema.comun.consumidor.CanalConsumoTYPE.WEB);
		consumidorTYPE.setOrganizacion(pe.gob.osinergmin.soa.schema.comun.consumidor.OrganizacionTYPE.OSINERGMIN);
		consumidorTYPE.setSistema(PIDO_WS_CONSUMIDOR_SISTEMA);
		consumidorTYPE.setModulo(PIDO_WS_CONSUMIDOR_MODULO);
		consumidorTYPE.setUsuario(PIDO_WS_CONSUMIDOR_USUARIO);
		consumidorTYPE.setIp(PIDO_WS_CONSUMIDOR_IP);
		return consumidorTYPE;
	}

	private pe.gob.osinergmin.soa.schema.comun.consumidor.ce.ConsumidorTYPE getConsumidorTYPECE() {
		pe.gob.osinergmin.soa.schema.comun.consumidor.ce.ConsumidorTYPE consumidorTYPE = new pe.gob.osinergmin.soa.schema.comun.consumidor.ce.ConsumidorTYPE();
		consumidorTYPE.setCanal(pe.gob.osinergmin.soa.schema.comun.consumidor.ce.CanalConsumoTYPE.WEB);
		consumidorTYPE.setOrganizacion(pe.gob.osinergmin.soa.schema.comun.consumidor.ce.OrganizacionTYPE.OSINERGMIN);
		consumidorTYPE.setSistema(PIDO_WS_CONSUMIDOR_SISTEMA);
		consumidorTYPE.setModulo(PIDO_WS_CONSUMIDOR_MODULO);
		consumidorTYPE.setUsuario(PIDO_WS_CONSUMIDOR_USUARIO);
		consumidorTYPE.setIp(PIDO_WS_CONSUMIDOR_IP);
		return consumidorTYPE;
	}

	public PidoBeanOutRO obtenerContribuyente(String ruc) {
		try {
			ContribuyentesConsultarPT service = ConsultarContribuyenteRucServiceLocator.getInstance().getService(
					PIDO_BUS_SERVER + PIDO_BUS_OBTENER_CONTRIBUYENTE, PIDO_BUS_SERVER_USUARIO,
					PIDO_BUS_SERVER_PASSWORD);
			ContribuyentesConsultarReqParamTYPE contribuyentesConsultarReqParamTYPE = new ContribuyentesConsultarReqParamTYPE();
			contribuyentesConsultarReqParamTYPE.setNumRuc(ruc);

			ContribuyentesConsultarRespParamTYPE contribuyentesConsultarRespParamTYPE = service
					.contribuyentesConsultar(contribuyentesConsultarReqParamTYPE, getConsumidorTYPE());
			DatosPrincipalesTYPE datosPrincipalesTYPE = contribuyentesConsultarRespParamTYPE.getDatosPrincipales();
			PidoBeanOutRO pidoOutRO = new PidoBeanOutRO();

			pidoOutRO.setNombres(trim(datosPrincipalesTYPE.getDdpNombre()));
			pidoOutRO.setNombreRazonSocial(trim(datosPrincipalesTYPE.getDdpNombre()));
			pidoOutRO.setUbigeo(datosPrincipalesTYPE.getDdpUbigeo());
			String departamentoNombre = trim(datosPrincipalesTYPE.getDescDep());
			String provinciaNombre = trim(datosPrincipalesTYPE.getDescProv());
			String distritoNombre = trim(datosPrincipalesTYPE.getDescDist());
			pidoOutRO.setDireccion(datosPrincipalesTYPE.getDescTipvia() + " " + datosPrincipalesTYPE.getDdpNomvia()
					+ " " + datosPrincipalesTYPE.getDdpNumer1() + " "
					+ String.format("%s/%s/%s", departamentoNombre, provinciaNombre, distritoNombre));
			pidoOutRO.setCodigoDepartamento(lpad(datosPrincipalesTYPE.getCodDep(), "0", 6));
			pidoOutRO.setCodigoProvincia(lpad(datosPrincipalesTYPE.getCodProv(), "0", 6));
			pidoOutRO.setCodigoDistrito(lpad(datosPrincipalesTYPE.getCodDist() + "", "0", 6));
			pidoOutRO.setDepartamento(departamentoNombre);
			pidoOutRO.setProvincia(provinciaNombre);
			pidoOutRO.setDistrito(distritoNombre.trim());

			pidoOutRO.setCodigoTipoNegocio(datosPrincipalesTYPE.getDdpTpoemp());
			pidoOutRO.setNombreTipoNegocio(trim(datosPrincipalesTYPE.getDescTpoemp()));
			return pidoOutRO;
		} catch (Exception e) {
			logger.error("PATH :" + PIDO_BUS_SERVER + PIDO_BUS_OBTENER_CONTRIBUYENTE);
			logger.error(e.getMessage(), e);
			throw new ValidacionException(e, Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO, "obtenerContribuyente");
		}
	}

	@Override
	public PidoBeanOutRO obtenerPidoCiudadanoOrquestado(String nroDocumento) {
		logger.info("Proceso de consumer PidoApiConsumerImpl - obtenerCiudadanoLocal()");
		PidoBeanOutRO pidoOutRO = new PidoBeanOutRO();
		CiudadanoConsultarPT service;

		try {
			service = DatosBasicosCiudadanoOrquestadoServiceLocator.getInstance().getService(
					PIDO_BUS_SERVER + PIDO_PATH_CIUDADANO_ORQUESTADO, PIDO_BUS_SERVER_USUARIO,
					PIDO_BUS_SERVER_PASSWORD);

			CiudadanoOrquestadoConsultarReqParamTYPE req = new CiudadanoOrquestadoConsultarReqParamTYPE();
			req.setNumDni(nroDocumento);
			req.setDniUsuario(PIDO_WS_DNI_USUARIO);
			req.setPassword(PIDO_WS_PASSWORD);
			req.setDniUsuarioReniec(PIDO_PATH_CIUDADANO_ORQUESTADO_DNIUSUARIORENIEC);
			CiudadanoOrquestadoConsultarRespParamTYPE resp = service.ciudadanoConsultar(req,
					DatosBasicosCiudadanoOrquestadoServiceLocator.buildConsumidor(PIDO_WS_CONSUMIDOR_SISTEMA,
							PIDO_WS_CONSUMIDOR_MODULO, PIDO_WS_CONSUMIDOR_USUARIO, PIDO_WS_CONSUMIDOR_IP));
			pidoOutRO.setResultCode(resp.getCoResultado());
			pidoOutRO.setMessage(resp.getDeResultado());
			pidoOutRO.setNombres(resp.getCiudadano().getNombres());
			pidoOutRO.setApellidoPaterno(resp.getCiudadano().getApellidoPaterno());
			pidoOutRO.setApellidoMaterno(resp.getCiudadano().getApellidoMaterno());
			pidoOutRO.setDireccion(resp.getCiudadano().getDireccion());
			pidoOutRO.setDeResultado(resp.getDeResultado());
			String ubigeo = resp.getCiudadano().getUbigeo();
			if (StringUtils.isNotBlank(ubigeo) && !StringUtils.equals(ubigeo, "//")) {
				pidoOutRO.setUbigeo(resp.getCiudadano().getUbigeo());
				String[] nombreUbigeo = ubigeo.split("/");
				if (nombreUbigeo.length == 3) {
					pidoOutRO.setDepartamento(nombreUbigeo[0]);
					pidoOutRO.setProvincia(nombreUbigeo[1]);
					pidoOutRO.setDistrito(nombreUbigeo[2]);
				} else if (nombreUbigeo.length == 2) {
					pidoOutRO.setProvincia(nombreUbigeo[0]);
					pidoOutRO.setDistrito(nombreUbigeo[1]);
				} else if (nombreUbigeo.length == 4) {
					pidoOutRO.setDepartamento(nombreUbigeo[0]);
					pidoOutRO.setProvincia(nombreUbigeo[1]);
					pidoOutRO.setDistrito(nombreUbigeo[2]);
				} 

				if (pidoOutRO.getDepartamento() == null || "".equals(pidoOutRO.getDepartamento())) {
					pidoOutRO.setDepartamento("CALLAO");
				}

				Ubigeo departamento = obtenerCodigoDepartamento(pidoOutRO.getDepartamento());
				Ubigeo provincia = null;
				Ubigeo distrito = null;
				if (departamento != null) {
					pidoOutRO.setCodigoDepartamento(departamento.getCodigo());
					provincia = obtenerCodigoProvincia(departamento.getCodigo(), pidoOutRO.getProvincia());
				}
				if (provincia != null) {
					pidoOutRO.setCodigoProvincia(provincia.getCodigo());
					distrito = obtenerCodigoDistrito(provincia.getCodigo(), pidoOutRO.getDistrito());
				}
				if (distrito != null) {
					pidoOutRO.setCodigoDistrito(distrito.getCodigo());
				}

			}
			if (ubigeo != null) {
				String direccionCompleta = String.format("%s %s", pidoOutRO.getDireccion(), ubigeo);
				pidoOutRO.setDireccion(direccionCompleta);
			}
			return pidoOutRO;

		} catch (Exception e) {
			logger.error("PATH :" + PIDO_BUS_SERVER + PIDO_PATH_CIUDADANO_ORQUESTADO);
			logger.error(e.getMessage(), e);
			throw new ValidacionException(e, Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO,
					"obtenerPidoCiudadanoOrquestado");
		}
	}

	public static void main(String[] args) {
		String ubigeo = "CALLAO/CALLAO";
		System.out.println(ubigeo.split("/")[0]);
		System.out.println(ubigeo.split("/")[1]);
		System.out.println(ubigeo.split("/")[2]);

	}

	/*public PidoBeanOutRO obtenerCiudadnoExtranjero(String nroDocumento) {
		try {
			CiudadanoExtranjeroObtenerPT service = ObtenerDatosBasicoCiudadanoExtranjeroServiceLocator.getInstance()
					.getService(PIDO_BUS_SERVER + PIDO_PATH_CIUDADANO_EXTRANJERO, PIDO_BUS_SERVER_USUARIO,
							PIDO_BUS_SERVER_PASSWORD);
			CiudadanoExtranjeroObtenerReqParamTYPE ciudadanoExtranjeroObtenerReqParamTYPE = new CiudadanoExtranjeroObtenerReqParamTYPE();
			ciudadanoExtranjeroObtenerReqParamTYPE.setTipoDocumento("CE");
			ciudadanoExtranjeroObtenerReqParamTYPE.setNumeroDocumento(nroDocumento);
			CiudadanoExtranjeroObtenerRespParamTYPE ciudadanoExtranjeroObtenerRespParamTYPE = service
					.ciudadanoExtranjeroObtener(ciudadanoExtranjeroObtenerReqParamTYPE, getConsumidorTYPECE());
			PidoBeanOutRO pidoOutRO = new PidoBeanOutRO();
			String codigoError = ciudadanoExtranjeroObtenerRespParamTYPE.getCodigoError();
			pidoOutRO.setResultCode(codigoError);
			pidoOutRO.setMessage(ciudadanoExtranjeroObtenerRespParamTYPE.getDescripcionError());
			if (!"0001".equals(codigoError) && ciudadanoExtranjeroObtenerRespParamTYPE.getExtranjero() != null) {
				pidoOutRO.setNombres(ciudadanoExtranjeroObtenerRespParamTYPE.getExtranjero().getNombres());
				pidoOutRO.setApellidoPaterno(
						ciudadanoExtranjeroObtenerRespParamTYPE.getExtranjero().getApellidoPaterno());
				pidoOutRO.setApellidoMaterno(
						ciudadanoExtranjeroObtenerRespParamTYPE.getExtranjero().getApellidoMaterno());
			}

			if ("0001".equals(codigoError)) {
				pidoOutRO.setMessage(pidoOutRO.getMessage().replaceFirst("Sin error, no", "No"));
			}
			return pidoOutRO;
		} catch (Exception e) {
			logger.error("PATH :" + PIDO_BUS_SERVER + PIDO_PATH_CIUDADANO_EXTRANJERO);
			logger.error(e.getMessage(), e);
			throw new ValidacionException(e, Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO, "obtenerCiudadnoExtranjero");
		}
	}*/
	
	@Override
	public PidoOutRO consultarPidoMigraccionesCiudadanoExtranjero(String nroDocumento) throws Exception {
		PidoOutRO pidoOutRO = null;
		ServiceAP40411 service;
        try{

    		try {

    			service = ObtenerDatosBasicoCiudadanoExtranjeroServiceLocator.getInstance().getService(PIDO_BUS_SERVER + PIDO_PATH_CIUDADANO_EXTRANJERO, PIDO_BUS_SERVER_USUARIO, PIDO_BUS_SERVER_PASSWORD); 			

    			if (service != null) {
    				ConsultarCarnetExtranjeria req = new ConsultarCarnetExtranjeria();
    				//req.setTipoDocumento("CE");
    				//req.setNumeroDocumento(nroDocumento);
    				req.setDocconsulta(nroDocumento);
    				ConsultarCarnetExtranjeriaResponse resp = service.callServiceListarDocumentos(req,
    						ObtenerDatosBasicoCiudadanoExtranjeroServiceLocator.buildConsumidor("SNE", "MIGRACIONES", PIDO_WS_CONSUMIDOR_USUARIO, PIDO_WS_CONSUMIDOR_IP));
					
					pidoOutRO = new PidoOutRO();
					
					pidoOutRO.setResultCode(resp.getJsonObject().getCodRespuesta().toString());
					pidoOutRO.setMessage(resp.getJsonObject().getDesRespuesta());
					pidoOutRO.setNombres(resp.getJsonObject().getDatosPersonales().getNombres());
					pidoOutRO.setApellidoPaterno(resp.getJsonObject().getDatosPersonales().getApepaterno());
					pidoOutRO.setApellidoMaterno(resp.getJsonObject().getDatosPersonales().getApematerno());
					
    			}
    		}
    		catch (ObtenerDatosBasicoCiudadanoExtranjeroServiceLocatorException | javax.xml.ws.WebServiceException | NullPointerException ex) {
    			logger.error(ex.getMessage());
    		}
				

    		logger.info("Invoking of web service consultarPidoMigraccionesCiudadanoExtranjero");
	    }
        catch(Exception ex){
        	logger.error("PATH :" + PIDO_BUS_SERVER + PIDO_PATH_CIUDADANO_EXTRANJERO);
        	logger.error(ex.getMessage());
        	
        	throw ex;
        }
        
        logger.info("Ending web service consultarPidoMigraccionesCiudadanoExtranjero");
		
		return pidoOutRO;
	}


	public SuneduOutRO obtenerGrados(String nroDocumento) {
		SuneduOutRO suneduOutRO = new SuneduOutRO();
		try {

			GradoTituloAcademicoObtenerPT service = ObtenerGradosTitulosServiceLocator.getInstance()
					.getService(PIDO_BUS_SERVER + PIDO_PATH_SUNEDU, PIDO_BUS_SERVER_USUARIO, PIDO_BUS_SERVER_PASSWORD);
			GradoTituloAcademicoObtenerReqParamTYPE gradoTituloAcademicoObtenerReqParamTYPE = new GradoTituloAcademicoObtenerReqParamTYPE();
			gradoTituloAcademicoObtenerReqParamTYPE.setDni(nroDocumento);
			GradoTituloAcademicoObtenerRespParamTYPE ciudadanoExtranjeroObtenerRespParamTYPE = service
					.gradoTituloAcademicoObtener(gradoTituloAcademicoObtenerReqParamTYPE, getConsumidorTYPE3());
			if (ciudadanoExtranjeroObtenerRespParamTYPE.getGradoAcademico() != null) {
				suneduOutRO.setGradosyTitulos(new ArrayList<GradoTituloRO>());
				for (GradoAcademicoTYPE gradoAcademicoTYPE : ciudadanoExtranjeroObtenerRespParamTYPE
						.getGradoAcademico()) {
					GradoTituloRO gradoTituloRO = new GradoTituloRO();
					gradoTituloRO.setAbreviaturaTitulo(gradoAcademicoTYPE.getAbreviaturaTitulo());
					gradoTituloRO.setNombres(gradoAcademicoTYPE.getNombres());
					gradoTituloRO.setApellidoPaterno(gradoAcademicoTYPE.getApellidoPaterno());
					gradoTituloRO.setApellidoMaterno(gradoAcademicoTYPE.getApellidoMaterno());
					gradoTituloRO.setEspecialidad(gradoAcademicoTYPE.getEspecialidad());
					gradoTituloRO.setTipoDocumento(gradoAcademicoTYPE.getTipoDocumento());
					gradoTituloRO.setNumeroDocumento(gradoAcademicoTYPE.getNumeroDocumento());
					gradoTituloRO.setPais(gradoAcademicoTYPE.getPais());
					gradoTituloRO.setTituloProfesional(gradoAcademicoTYPE.getTituloProfesional());
					gradoTituloRO.setUniversidad(gradoAcademicoTYPE.getUniversidad());
					gradoTituloRO.setTipoInstitucion(gradoAcademicoTYPE.getTipoInstitucion());
					gradoTituloRO.setTipoGestion(gradoAcademicoTYPE.getTipoGestion());
					gradoTituloRO.setFechaEmision(gradoAcademicoTYPE.getFechaEmision());
					gradoTituloRO.setResolucion(gradoAcademicoTYPE.getResolucion());
					gradoTituloRO.setFechaResolucion(gradoAcademicoTYPE.getFechaResolucion());
					suneduOutRO.getGradosyTitulos().add(gradoTituloRO);
				}
			}
		} catch (GradoTituloAcademicoObtenerFaultMsg e) {
			logger.error("obtenerGrados  " + e.getFaultInfo());
			logger.error(e.getMessage(), e);
			throw new ValidacionException(e, Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO, "obtenerGrados");
		} catch (Exception e) {
			logger.error("PATH :" + PIDO_BUS_SERVER + PIDO_PATH_SUNEDU);
			logger.error(e.getMessage(), e);
			throw new ValidacionException(e, Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO, "obtenerGrados");
		}
		return suneduOutRO;
	}

	@Override
	public List<pe.gob.osinergmin.sicoes.util.bean.siged.DocumentoOutRO> listarExpediente(String numeroExpediente) {
		try {
			List<pe.gob.osinergmin.sicoes.util.bean.siged.DocumentoOutRO> listDocumentoOutRO = new ArrayList<pe.gob.osinergmin.sicoes.util.bean.siged.DocumentoOutRO>();
			ServiceAP4034 serviceAP4034 = ListarDocumentoExpedienteServiceLocator.getInstance().getService(
					PIDO_BUS_SERVER + PIDO_PATH_LISTAR_EXPEDIENTE, PIDO_BUS_SERVER_USUARIO, PIDO_BUS_SERVER_PASSWORD);
			ListarDocumentos listarDocumentos = new ListarDocumentos();
			ParametrosTypeAP4034 parametrosTypeAP4034 = new ParametrosTypeAP4034();
			parametrosTypeAP4034.setNumeroExpediente(new BigInteger(numeroExpediente));
			parametrosTypeAP4034.setFiles(BigInteger.ONE);
			AccesoTypeAP4034 accesoTypeAP4034 = new AccesoTypeAP4034();
			accesoTypeAP4034.setNroExpediente(numeroExpediente);
			accesoTypeAP4034.setIdUsuario(new BigInteger(SIGED_XML_LISTAR_EXPEDIENTE_ID_USUARIO));
			accesoTypeAP4034.setIdRol(new BigInteger("5"));
			accesoTypeAP4034.setAccionServicio("expediente/documentos");
			accesoTypeAP4034.setTipoAccion("L");
			parametrosTypeAP4034.setAcceso(accesoTypeAP4034);
			listarDocumentos.setParametros(parametrosTypeAP4034);
			ListarDocumentosResponse listarDocumentosResponse = serviceAP4034
					.callServiceListarDocumentos(listarDocumentos, getConsumidorTYPELDE());

			if (listarDocumentosResponse.getDocumento() != null) {
				for (Documento documento : listarDocumentosResponse.getDocumento()) {
					for (Archivo archivo : documento.getArchivos().getArchivo()) {
						pe.gob.osinergmin.sicoes.util.bean.siged.DocumentoOutRO documentoOutRO = new pe.gob.osinergmin.sicoes.util.bean.siged.DocumentoOutRO();
						documentoOutRO.setNumeroDocumento(documento.getNroExpediente() + "");
						documentoOutRO.setTipoDocumento(documento.getNombreTipoDocumento() + "");
						documentoOutRO.setAutor(documento.getAutor() + "");
						documentoOutRO.setAsunto(documento.getAsunto() + "");
						documentoOutRO.setFechaCreacion(archivo.getFechaCreacion() + "");
						documentoOutRO.setNombre(archivo.getNombre() + "");
						listDocumentoOutRO.add(documentoOutRO);
					}
				}
			}

			return listDocumentoOutRO;
		} catch (Exception e) {
			logger.error("PATH :" + PIDO_BUS_SERVER + PIDO_PATH_LISTAR_EXPEDIENTE);
			logger.error(e.getMessage(), e);
			throw new ValidacionException(e, Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO, "listarExpediente");
		}
	}

	private Ubigeo obtenerCodigoDistrito(String codigo, String distrito) {
		List<Ubigeo> distritos = sigedOldConsumer.distritos(codigo);
		for (Ubigeo ubigeo : distritos) {
			if (distrito.equals(ubigeo.getNombre())) {
				return ubigeo;
			}
		}
		return null;
	}

	private Ubigeo obtenerCodigoProvincia(String codigo, String provincia) {
		List<Ubigeo> provincias = sigedOldConsumer.provincias(codigo);
		for (Ubigeo ubigeo : provincias) {
			if (provincia.equals(ubigeo.getNombre())) {
				return ubigeo;
			}
		}
		return null;
	}

	private Ubigeo obtenerCodigoDepartamento(String departamento) {
		List<Ubigeo> departamentos = sigedOldConsumer.departamentos();
		for (Ubigeo ubigeo : departamentos) {
			if (departamento.equals(ubigeo.getNombre())) {
				return ubigeo;
			}
		}
		return null;
	}

	private pe.gob.osinergmin.soa.schema.comun.consumidor.ConsumidorTYPE getConsumidorTYPEUnidad() {
		pe.gob.osinergmin.soa.schema.comun.consumidor.ConsumidorTYPE consumidorTYPE = new pe.gob.osinergmin.soa.schema.comun.consumidor.ConsumidorTYPE();
		consumidorTYPE.setCanal(pe.gob.osinergmin.soa.schema.comun.consumidor.CanalConsumoTYPE.WEB);
		consumidorTYPE.setOrganizacion(pe.gob.osinergmin.soa.schema.comun.consumidor.OrganizacionTYPE.OSINERGMIN);
		consumidorTYPE.setSistema(PIDO_WS_CONSUMIDOR_SISTEMA);
		consumidorTYPE.setModulo(PIDO_WS_CONSUMIDOR_MODULO);
		consumidorTYPE.setUsuario(PIDO_WS_CONSUMIDOR_USUARIO);
		consumidorTYPE.setIp(PIDO_WS_CONSUMIDOR_IP);
		return consumidorTYPE;
	}

	public List<UnidadOutRO> listarUnidad() {
		String modo = env.getProperty("modo-produccion");
		List<UnidadOutRO> listUnidadOutRO = new ArrayList<UnidadOutRO>();
		if (!"modo-produccion".equals(modo)) {
			try {
				ServiceAP40330 service = ListarUnidadServiceLocator.getInstance().getService(
						PIDO_BUS_SERVER + PIDO_PATH_LISTAR_UNIDAD, PIDO_BUS_SERVER_USUARIO, PIDO_BUS_SERVER_PASSWORD);

				ListarUnidadAP40330 listarUnidadAP40330 = new ListarUnidadAP40330();
				ListarUnidadResponseAP40330 listarUnidadResponseAP40330 = service
						.callServiceListarUnidad(listarUnidadAP40330, getConsumidorTYPEUnidad());

				if (listarUnidadResponseAP40330 != null && listarUnidadResponseAP40330.getUnidad() != null) {
					for (ListarUnidadResponseAP40330.Unidad unidad : listarUnidadResponseAP40330.getUnidad()) {
						UnidadOutRO unidadOutRO = new UnidadOutRO();
						unidadOutRO.setCantidadProcesos(unidad.getCantidadProcesos());
						unidadOutRO.setIdUnidad(unidad.getIdUnidad());
						unidadOutRO.setNombreUnidad(unidad.getNombreUnidad());
						
						listUnidadOutRO.add(unidadOutRO);
					}
				}

				return listUnidadOutRO;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new ValidacionException(e, Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO,
						"obtenerAreas");
			}
		} else {
			UnidadOutRO unidadOutRO = new UnidadOutRO();
			unidadOutRO.setCantidadProcesos(new BigInteger("1000000000"));
			unidadOutRO.setIdUnidad(new BigInteger("1000000000"));
			unidadOutRO.setNombreUnidad("UNIDAD DUMMY");
			listUnidadOutRO.add(unidadOutRO);
			return listUnidadOutRO;
		}
	}

	private pe.gob.osinergmin.soa.schema.comun.consumidor.ConsumidorTYPE getConsumidorTYPEUsuario() {
		pe.gob.osinergmin.soa.schema.comun.consumidor.ConsumidorTYPE consumidorTYPE = new pe.gob.osinergmin.soa.schema.comun.consumidor.ConsumidorTYPE();
		consumidorTYPE.setCanal(pe.gob.osinergmin.soa.schema.comun.consumidor.CanalConsumoTYPE.WEB);
		consumidorTYPE.setOrganizacion(pe.gob.osinergmin.soa.schema.comun.consumidor.OrganizacionTYPE.OSINERGMIN);
		consumidorTYPE.setSistema(PIDO_WS_CONSUMIDOR_SISTEMA);
		consumidorTYPE.setModulo(PIDO_WS_CONSUMIDOR_MODULO);
		consumidorTYPE.setUsuario(PIDO_WS_CONSUMIDOR_USUARIO);
		consumidorTYPE.setIp(PIDO_WS_CONSUMIDOR_IP);
		return consumidorTYPE;
	}

	public List<UsuarioOutRO> listarUsuarios() {
		String modo=env.getProperty("modo-produccion");
		List<UsuarioOutRO> listUsuarioOutRO=new ArrayList<UsuarioOutRO>();
		if(!"1".equals(modo)) {			 			 
			try {
				ServiceAP40326 service=ListarUsuariosServiceLocator.getInstance().getService(PIDO_BUS_SERVER+PIDO_PATH_LISTAR_USUARIO,PIDO_BUS_SERVER_USUARIO,PIDO_BUS_SERVER_PASSWORD);		
				Void voidValue =new Void();					
				ListarUsuariosResponse  listarUsuariosResponse = service.callServiceListarUsuarios(voidValue,getConsumidorTYPEUsuario());				
				if(listarUsuariosResponse!=null&&listarUsuariosResponse.getUsuario()!=null) {				
					for(ListarUsuariosResponse.Usuario usuario:listarUsuariosResponse.getUsuario()) {						
						UsuarioOutRO usuarioOutRO=new UsuarioOutRO();
						usuarioOutRO.setNombres(usuario.getNombres());
						usuarioOutRO.setIdUsuario(usuario.getIdUsuario());						
//						System.out.println(usuario.getIdUsuario()+"|"+usuario.getNombres());
						listUsuarioOutRO.add(usuarioOutRO);
						
					}				
				}
				listUsuarioOutRO.sort((p1, p2) -> { 
						return p1.getNombres().compareTo(p2.getNombres());
						});

				return listUsuarioOutRO;
				
			}catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new ValidacionException(e,Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO,"obtenerUsuarios"); 
			}
		}else {
			return getUsuarios();
		}
	}

	public List<UsuarioOutRO> getUsuarios() {
		List<UsuarioOutRO> listUsuarioOutRO = new ArrayList<UsuarioOutRO>();
		for (int i = 100000000; i < 100000000 + 10; i++) {
			UsuarioOutRO usuarioOutRO1 = new UsuarioOutRO();
			usuarioOutRO1.setNombres("USUARIO " + i);
			usuarioOutRO1.setIdUsuario(new BigInteger(i + ""));
			listUsuarioOutRO.add(usuarioOutRO1);
		}
		return listUsuarioOutRO;
	}

	public String trim(String entrada) {
		if (entrada == null)
			return null;
		return entrada.trim();
	}

	public String lpad(String entrada, String format, Integer cantidad) {
		if (entrada.length() >= cantidad) {
			return entrada;
		}
		StringBuilder sb = new StringBuilder(entrada);
		while (sb.length() < cantidad) {
			sb.append(format);
		}

		return sb.toString();
	}

}
