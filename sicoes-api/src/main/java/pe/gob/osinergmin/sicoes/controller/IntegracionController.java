package pe.gob.osinergmin.sicoes.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import gob.osinergmin.sne.domain.dto.rest.out.PidoOutRO;
import pe.gob.osinergmin.sicoes.consumer.PidoConsumer;
import pe.gob.osinergmin.sicoes.consumer.SneApiConsumer;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.dto.CaptchaBeanDTO;
import pe.gob.osinergmin.sicoes.service.CaptchaServiceConsumer;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.bean.PidoBeanOutRO;
import pe.gob.osinergmin.sicoes.util.bean.SuneduOutRO;
import pe.gob.osinergmin.sicoes.util.bean.siged.UnidadOutRO;
import pe.gob.osinergmin.sicoes.util.bean.siged.UsuarioOutRO;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionBeanDTO;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionOutRO;

@RestController
@RequestMapping("/api/pido")
public class IntegracionController extends BaseRestController{

	private Logger logger = LogManager.getLogger(IntegracionController.class);

	@Autowired
	SneApiConsumer sneApiConsumer; 
	
	@Autowired
	PidoConsumer pidoConsumer;

	@Autowired
	CaptchaServiceConsumer captchaConsumer;

	@Autowired
	ListadoDetalleService listadoDetalleService;

	@Value("${recaptcha.score}")
	private String RECAPTCHA_SCORE;
	
	@GetMapping("/sunat/{ruc}")
	public PidoBeanOutRO consultaRUC(
			@RequestHeader(value = "recaptcha") String recaptcha,
			@PathVariable String ruc) throws Exception {

		logger.info("consultaRUC {} ",ruc);

		try {
			ListadoDetalle estadoActivo = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RECAPTCHA.CODIGO, Constantes.LISTADO.RECAPTCHA.ACTIVO);
			ListadoDetalle estadoActual = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RECAPTCHA.CODIGO, Constantes.LISTADO.RECAPTCHA.ESTADO_ACTUAL);

			if (estadoActivo.getValor().equals(estadoActual.getValor())) {
				CaptchaBeanDTO captchaBeanDTO = captchaConsumer.processResponse(recaptcha, RECAPTCHA_SCORE);
				if (!captchaBeanDTO.isSuccess()) {
					throw new ValidacionException("Captcha: No se validó el token");
				}
			}

			return pidoConsumer.obtenerContribuyente(ruc);
		} catch (Exception e) {
			logger.error("Error en el servicio de recaptcha: {}", e.getMessage());
			throw new Exception("Error en el servicio de recaptcha");
		}
	}
	
	@GetMapping("/reniec/{DNI}")
	public PidoBeanOutRO consultaDNI(
			@RequestHeader(value = "recaptcha") String recaptcha,
			@PathVariable String DNI) throws Exception {

		logger.info("consultaDNI {} ",DNI);

		try {
			ListadoDetalle estadoActivo = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RECAPTCHA.CODIGO, Constantes.LISTADO.RECAPTCHA.ACTIVO);
			ListadoDetalle estadoActual = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RECAPTCHA.CODIGO, Constantes.LISTADO.RECAPTCHA.ESTADO_ACTUAL);

			if (estadoActivo.getValor().equals(estadoActual.getValor())) {
				CaptchaBeanDTO captchaBeanDTO = captchaConsumer.processResponse(recaptcha, RECAPTCHA_SCORE);
				if (!captchaBeanDTO.isSuccess()) {
					throw new ValidacionException("Captcha: No se validó el token");
				}
			}

			return pidoConsumer.obtenerPidoCiudadanoOrquestado(DNI);
		} catch (Exception e) {
			logger.error("Error en el servicio de recaptcha: {}", e.getMessage());
			throw new Exception("Error en el servicio de recaptcha");
		}
	}
	
	@GetMapping("/migracion/{NRO}")
	//public PidoBeanOutRO consultaCE(@PathVariable String NRO)throws Exception{
	public PidoOutRO consultaCE(@PathVariable String NRO)throws Exception{
		logger.info("consultaCE {} ",NRO);
		//return pidoConsumer.obtenerCiudadnoExtranjero(NRO);
		return pidoConsumer.consultarPidoMigraccionesCiudadanoExtranjero(NRO);
	}
	
	@GetMapping("/sunedu/{NRO}")
	public SuneduOutRO consultaGrados(@PathVariable String NRO)throws Exception{
		logger.info("consultaGrados {} ",NRO);
		return pidoConsumer.obtenerGrados(NRO);
	}
	
	@GetMapping("/SNE")
	public AfiliacionOutRO consultaSNE(@RequestParam String nroDocumento,@RequestParam Integer tipoDocumento)throws Exception{
		logger.info("consultaPartidas {} ",nroDocumento);
		String token =sneApiConsumer.obtenerTokenAcceso();
		AfiliacionBeanDTO afiliacionBeanDTO=new AfiliacionBeanDTO();
		afiliacionBeanDTO.setTipoDocumento(tipoDocumento);
		afiliacionBeanDTO.setNumeroDocumento(nroDocumento);
		afiliacionBeanDTO.setAppInvoker("vvo");
		afiliacionBeanDTO.setVerificarData(true);
		return sneApiConsumer.obtenerAfiliacion(afiliacionBeanDTO, token);
	}
	
	@GetMapping("/listar-unidad")
	public List<UnidadOutRO> listarUnidad(){
		logger.info("listarUnidad  ");
		return pidoConsumer.listarUnidad();
	}
	
	@GetMapping("/listar-usuarios")
	public List<UsuarioOutRO> listarUsuario(){
		logger.info("listarUsuarios ");
		return pidoConsumer.listarUsuarios();
	}

	@GetMapping("/sunat/representante/{ruc}")
	public PidoBeanOutRO consultaRepresentanteRUC(
			@RequestHeader(value = "recaptcha") String recaptcha,
			@PathVariable String ruc) throws Exception {

		logger.info("consultaRepresentanteRUC {} ",ruc);

		try {
			PidoBeanOutRO pidoBeanOutRO = new PidoBeanOutRO();
			ListadoDetalle estadoActivo = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RECAPTCHA.CODIGO, Constantes.LISTADO.RECAPTCHA.ACTIVO);
			ListadoDetalle estadoActual = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RECAPTCHA.CODIGO, Constantes.LISTADO.RECAPTCHA.ESTADO_ACTUAL);

			if (estadoActivo.getValor().equals(estadoActual.getValor())) {
				CaptchaBeanDTO captchaBeanDTO = captchaConsumer.processResponse(recaptcha, RECAPTCHA_SCORE);
				if (!captchaBeanDTO.isSuccess()) {
					throw new ValidacionException("Captcha: No se validó el token");
				}
			}

			PidoBeanOutRO response = pidoConsumer.obtenerContribuyente(ruc);
			if (response != null) {
				pidoBeanOutRO.setNombres(response.getNombres());
				pidoBeanOutRO.setApellidoPaterno(response.getApellidoPaterno());
				pidoBeanOutRO.setApellidoMaterno(response.getApellidoMaterno());
			}
			return pidoBeanOutRO;
		} catch (Exception e) {
			logger.error("Error en el servicio de recaptcha: {}", e.getMessage());
			throw new Exception("Error en el servicio de recaptcha");
		}
	}


	@GetMapping("/reniec/representante/{DNI}")
	public PidoBeanOutRO consultaRepresentanteDNI(
			@RequestHeader(value = "recaptcha") String recaptcha,
			@PathVariable String DNI) throws Exception {

		logger.info("consultaRepresentanteDNI {} ",DNI);

		try {
			PidoBeanOutRO pidoBeanOutRO = new PidoBeanOutRO();
			ListadoDetalle estadoActivo = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RECAPTCHA.CODIGO, Constantes.LISTADO.RECAPTCHA.ACTIVO);
			ListadoDetalle estadoActual = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RECAPTCHA.CODIGO, Constantes.LISTADO.RECAPTCHA.ESTADO_ACTUAL);

			if (estadoActivo.getValor().equals(estadoActual.getValor())) {
				CaptchaBeanDTO captchaBeanDTO = captchaConsumer.processResponse(recaptcha, RECAPTCHA_SCORE);
				if (!captchaBeanDTO.isSuccess()) {
					throw new ValidacionException("Captcha: No se validó el token");
				}
			}

			PidoBeanOutRO response = pidoConsumer.obtenerPidoCiudadanoOrquestado(DNI);
			if (response != null) {
				pidoBeanOutRO.setResultCode(response.getResultCode());
				pidoBeanOutRO.setMessage(response.getDeResultado());
				pidoBeanOutRO.setNombres(response.getNombres());
				pidoBeanOutRO.setApellidoPaterno(response.getApellidoPaterno());
				pidoBeanOutRO.setApellidoMaterno(response.getApellidoMaterno());
			}
			return pidoBeanOutRO;
		} catch (Exception e) {
			logger.error("Error en el servicio de recaptcha: {}", e.getMessage());
			throw new Exception("Error en el servicio de recaptcha");
		}
	}
}
