package pe.gob.osinergmin.sicoes.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gob.osinergmin.siged.remote.rest.ro.base.BaseOutRO;
import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pe.gob.osinergmin.sicoes.consumer.PidoConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SneApiConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.Contrato;
import pe.gob.osinergmin.sicoes.model.DictamenEvaluacion;
import pe.gob.osinergmin.sicoes.model.Documento;
import pe.gob.osinergmin.sicoes.model.Estudio;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.model.PerfilAprobador;
import pe.gob.osinergmin.sicoes.model.Persona;
import pe.gob.osinergmin.sicoes.model.Representante;
import pe.gob.osinergmin.sicoes.model.ReqInicioServicio;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SupervisoraDictamen;
import pe.gob.osinergmin.sicoes.model.SupervisoraPerfil;
import pe.gob.osinergmin.sicoes.model.SupervisoraRepresentante;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.ContratoDao;
import pe.gob.osinergmin.sicoes.repository.DocumentoDao;
import pe.gob.osinergmin.sicoes.repository.EstudioDao;
import pe.gob.osinergmin.sicoes.repository.OtroRequisitoDao;
import pe.gob.osinergmin.sicoes.repository.PerfilAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.PersonaDao;
import pe.gob.osinergmin.sicoes.repository.RepresentanteDao;
import pe.gob.osinergmin.sicoes.repository.ReqInicioServicioDao;
import pe.gob.osinergmin.sicoes.repository.SolicitudDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.AsignacionService;
import pe.gob.osinergmin.sicoes.service.DictamenEvaluacionService;
import pe.gob.osinergmin.sicoes.service.DocumentoService;
import pe.gob.osinergmin.sicoes.service.EmpresasSancionadaService;
import pe.gob.osinergmin.sicoes.service.EstudioService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.OtroRequisitoService;
import pe.gob.osinergmin.sicoes.service.PersonaService;
import pe.gob.osinergmin.sicoes.service.RepresentanteService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.service.SupervisoraDictamenService;
import pe.gob.osinergmin.sicoes.service.SupervisoraPerfilService;
import pe.gob.osinergmin.sicoes.service.SupervisoraRepresentanteService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.*;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionVvoBeanDTO;
import pe.gob.osinergmin.sicoes.util.bean.sne.AfiliacionVvoOutRO;
import pe.gob.osinergmin.sicoes.util.bean.sne.NotificacionBeanDTO;

@Service
public class SolicitudServiceImpl implements SolicitudService {

	Logger logger = LogManager.getLogger(SolicitudServiceImpl.class);

	@Autowired
	private SolicitudDao solicitudDao;

	@Autowired
	private DocumentoDao documentoDao;

	@Autowired
	private RepresentanteDao representanteDao;

	@Autowired
	private ArchivoDao archivoDao;

	@Autowired
	private OtroRequisitoDao otroRequisitoDao;

	@Autowired
	private PersonaDao personaDao;

	@Autowired
	private EstudioDao estudioDao;
	
	@Autowired
	private PerfilAprobadorDao perfilAprobadorDao;

	@Autowired
	private RepresentanteService representanteService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private ListadoDetalleService listadoDetalleService;

	@Autowired
	private OtroRequisitoService otroRequisitoService;

	@Autowired
	private EstudioService estudioService;

	@Autowired
	private DocumentoService documentoService;

	@Value("${siged.old.proyecto}")
	private String SIGLA_PROYECTO;

	@Value("${siged.titulo.incripcion.registro}")
	private String TITULO_REGISTRO;

	@Autowired
	private ArchivoService archivoService;

	@Autowired
	private PidoConsumer pidoConsumer;

	@Autowired
	private SigedApiConsumer sigedApiConsumer;

	@Autowired
	NotificacionService notificacionService;

	@Autowired
	AsignacionService asignacionService;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	SupervisoraService supervisoraService;

	@Autowired
	SupervisoraRepresentanteService supervisoraRepresentanteService;

	@Autowired
	SupervisoraPerfilService supervisoraPerfilService;
	
	@Autowired
	private DictamenEvaluacionService dictamenEvaluacionService;

	@Autowired
	SneApiConsumer sneApiConsumer;

	@Value("${path.temporal}")
	private String pathTemporal;

	@Value("${path.jasper}")
	private String pathJasper;

	@Autowired
	private Environment env;

	@Autowired
	EmpresasSancionadaService empresasSancionadaService;
	@Value("${timer.alfresco}")
	private Long segundos;
	
	@Autowired
	private SupervisoraDictamenService supervisoraDictamenService;
	
	@Autowired
	private ReqInicioServicioDao reqInicioServicioDao;
	
	@Autowired
	private ContratoDao contratoDao;

	public boolean validarJuridicoPostor(String solicitudUuid) {
		ListadoDetalle tipoPersona = obtenerTipoPersona(solicitudUuid);

		boolean isJuridica = Constantes.LISTADO.TIPO_PERSONA.JURIDICA.equals(tipoPersona.getCodigo());
		boolean isPerNatPostor = Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR.equals(tipoPersona.getCodigo());
		boolean isExtrajero = Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO.equals(tipoPersona.getCodigo());

		return isJuridica || isPerNatPostor || isExtrajero;
	}

	public boolean validarPN(String solicitudUuid) {
		ListadoDetalle tipoPersona = obtenerTipoPersona(solicitudUuid);

		boolean isPN = Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO.equals(tipoPersona.getCodigo());

		return isPN;
	}

	Long getDate() {
		return new Date().getTime();
	}
	@Transactional(rollbackFor = { Exception.class, ValidacionException.class })
	public Solicitud enviar(Solicitud solicitud, Contexto contexto) {	
		Solicitud solicitudBD = obtener(solicitud.getSolicitudUuid(), contexto);
		List<File> archivosAlfresco=null;
		if (solicitudBD.getOrigenRegistro() != null && solicitudBD.getOrigenRegistro().getCodigo().equals(Constantes.LISTADO.ORIGEN_REGISTRO.MODIFICACION) && (solicitudBD.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.NATURAL) || solicitudBD.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO))) {
			return enviarModificacion(solicitud, contexto);
		} else if (solicitudBD.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)) {
			try {
			archivosAlfresco = archivoService.obtenerArchivoContenido(solicitudBD.getIdSolicitud(),Constantes.LISTADO.TIPO_ARCHIVO.FORMATO, contexto);
			return enviar(solicitud,solicitudBD,archivosAlfresco,contexto);
			} catch (ValidacionException e) {
				archivarExpediente(solicitudBD.getNumeroExpediente());
				logger.error("ERROR 2 {} ", e.getMessage(), e);
				throw e;
			} catch (Exception e) {
				archivarExpediente(solicitudBD.getNumeroExpediente());
				logger.error("ERROR {} ", e.getMessage(), e);
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE,solicitudBD.getNumeroExpediente(),e.getMessage());
			}
		} else if (solicitudBD.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_SOLICITUD.OBSERVADO)) {
			archivosAlfresco = archivoService.obtenerArchivoContenido(solicitudBD.getIdSolicitud(),Constantes.LISTADO.TIPO_ARCHIVO.SUBSANACION, contexto);
			return enviar(solicitud,solicitudBD,archivosAlfresco,contexto);
		}
		return null;
	}
	
	
//	@Transactional(rollbackFor = { Exception.class, ValidacionException.class })
	private Solicitud enviar(Solicitud solicitud,Solicitud solicitudBD,List<File> archivosAlfresco, Contexto contexto) {
		Long inicioTotal=getDate();
		Long inicioParcial=getDate();
		if (solicitud == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENVIADO);
		}
		if (solicitud.getPersona() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.PERSONA_NO_ENVIADO);
		}
		if (solicitud.getPersona().getTipoDocumento() == null
				&& solicitud.getPersona().getTipoDocumento().getIdListadoDetalle() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPODOC_NO_ENVIADO);
		}
		if (StringUtils.isBlank(solicitud.getPersona().getNumeroDocumento())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOC_NO_ENVIADO);
		}
		if (StringUtils.isBlank(solicitud.getPersona().getCorreo())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.CORREO_NO_ENVIADO);
		}
		
		if(!Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO.equals(solicitudBD.getPersona().getTipoPersona().getCodigo())) {
			if (StringUtils.isBlank(solicitud.getPersona().getCodigoDepartamento())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.CODIGO_DEPARTAMENTO_NOENVIADO);
			}
			if (StringUtils.isBlank(solicitud.getPersona().getCodigoDistrito())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.CODIGO_DISTRITO_NOENVIADO);
			}
			if (StringUtils.isBlank(solicitud.getPersona().getCodigoProvincia())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.CODIGO_PROVINCIA_NOENVIADO);
			}
			if (StringUtils.isBlank(solicitud.getPersona().getNombreProvincia())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROVINCIA_NO_ENVIADO);
			}
			if (StringUtils.isBlank(solicitud.getPersona().getNombreDepartamento())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.DEPARTAMENTO_NO_ENVIADO);
			}
			if (StringUtils.isBlank(solicitud.getPersona().getNombreDistrito())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.DISTRITO_NO_ENVIADO);
			}
		}

		if (!StringUtils.isBlank(solicitud.getPersona().getTelefono1())&&solicitud.getPersona().getTelefono1().trim().length()<9) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.INGRESE_TELEFONO1);
		}
		if (!StringUtils.isBlank(solicitud.getPersona().getTelefono2())&&solicitud.getPersona().getTelefono2().trim().length()<9) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.INGRESE_TELEFONO2);
		}
		if (!StringUtils.isBlank(solicitud.getPersona().getTelefono3())&&solicitud.getPersona().getTelefono3().trim().length()<9) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.INGRESE_TELEFONO3);
		}
		if (validarJuridicoPostor(solicitud.getSolicitudUuid())) {
			if (StringUtils.isBlank(solicitud.getPersona().getNombreRazonSocial())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.RAZONSOCIAL_NO_ENVIADO);
			}
			if (StringUtils.isBlank(solicitud.getPersona().getCodigoPartidaRegistral())
					&& solicitud.getPersona().isPesonaJuridica()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.PARTIDA_REGISTRAL_NO_ENVIADO);
			}
			if (solicitud.getRepresentante() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.REPRESENTANTE_NO_ENVIADO);
			}
			if (solicitud.getRepresentante().getTipoDocumento() == null
					&& solicitud.getRepresentante().getTipoDocumento().getIdListadoDetalle() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.R_TIPO_DOC_NO_ENVIADO);
			}
			if (StringUtils.isBlank(solicitud.getRepresentante().getNumeroDocumento())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.R_NUM_DOC_NO_ENVIADO);
			}
			if (StringUtils.isBlank(solicitud.getRepresentante().getNombres())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.R_NOMBRE_NO_ENVIADO);
			}
			if (!solicitud.getRepresentante().isPesonaJuridica()) {
				if (StringUtils.isBlank(solicitud.getRepresentante().getApellidoPaterno())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.R_APATERNO_NO_ENVIADO);
				}
				if (StringUtils.isBlank(solicitud.getRepresentante().getApellidoMaterno())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.R_AMATERNO_NO_ENVIADO);
				}
			}
		} else if (validarPN(solicitud.getSolicitudUuid())) {
			if (StringUtils.isBlank(solicitud.getPersona().getNombres())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.NOMBRE_PERSONA_NO_ENVIADO);
			}
		}
		logger.info("fin de validaciones :"+(getDate()-inicioParcial));
		
		inicioParcial=getDate();
		logger.info("obtener Solicitud :"+(getDate()-inicioParcial));
		
		inicioParcial=getDate();
		validarPresentacion(solicitudBD, contexto);
		logger.info("validarPresentacion :"+(getDate()-inicioParcial));
		
		if (solicitudBD.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO)) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_PRESENTADA);
		}
		if (solicitudBD.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)) {
			inicioParcial=getDate();
			ListadoDetalle estadoSolicitud = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO);
			ListadoDetalle estadoRevision = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR);
			solicitudBD.setEstado(estadoSolicitud);
			solicitudBD.setEstadoRevision(estadoRevision);
			solicitudBD.setEstadoEvaluacionAdministrativa(estadoRevision);
			solicitudBD.setEstadoEvaluacionTecnica(estadoRevision);
			solicitudBD.setFechaPresentacion(new Date());
			solicitudBD.setFlagActivo(Constantes.FLAG.ACTIVO);

			ListadoDetalle plazoRespuesta = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.RESPUESTA_SOLICITUD);
			solicitudBD.setNumeroPlazoResp(Long.parseLong(plazoRespuesta.getValor()));
			solicitudBD.setFechaPlazoResp(
					calcularFechaFin(solicitudBD.getFechaPresentacion(), solicitudBD.getNumeroPlazoResp()));

			ListadoDetalle plazoAsignar = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO,Constantes.LISTADO.PLAZOS.ASIGNAR_EVALUADORES);
			solicitudBD.setNumeroPlazoAsig(Long.parseLong(plazoAsignar.getValor()));
			solicitudBD.setFechaPlazoAsig(calcularFechaFin(solicitudBD.getFechaPresentacion(), solicitudBD.getNumeroPlazoAsig()));

			ListadoDetalle plazoTecnico = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO,Constantes.LISTADO.PLAZOS.CONCLUIR_EVALUACION_TECNICA);
			solicitudBD.setNumeroPlazoTecnico(Long.parseLong(plazoTecnico.getValor()));
			solicitudBD.setFechaPlazoTecnico(calcularFechaFin(solicitudBD.getFechaPresentacion(), solicitudBD.getNumeroPlazoTecnico()));
			ExpedienteInRO expedienteInRO=null;
			inicioParcial=getDate();
			try {
				
				expedienteInRO = crearExpedientePresentacion(solicitudBD, contexto);
	
			} catch (Exception e) {				
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE);
			}
			Archivo formato04 = null;


			String codigoExpediente=null;
			logger.info("obtenerArchivoContenido :"+(getDate()-inicioParcial));
			try {
				inicioParcial=getDate();
				logger.info("SIGED RESULT: {}", expedienteInRO);
				ExpedienteOutRO expedienteOutRO = sigedApiConsumer.crearExpediente(expedienteInRO, archivosAlfresco);
				logger.info("SIGED RESULT: {}", expedienteOutRO.getMessage());
				logger.info("SIGED consulta :"+(getDate()-inicioParcial));
				inicioParcial=getDate();
				if (expedienteOutRO.getResultCode() != 1) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE,
							expedienteOutRO.getMessage());
				}
				if(expedienteOutRO.getResultCode()==1) {
					AuditoriaUtil.setAuditoriaRegistro(solicitudBD, contexto);
					solicitudDao.save(solicitudBD);
					logger.info("guardar solicitud :"+(getDate()-inicioParcial));
					codigoExpediente = expedienteOutRO.getCodigoExpediente();
					solicitudBD.setNumeroExpediente(codigoExpediente);
					solicitudBD.getPersona()
							.setCodigoCliente(expedienteOutRO.getClientes().getCliente().get(0).getCodigoCliente() * 1L);
					personaService.guardar(solicitudBD.getPersona(), contexto);
					AuditoriaUtil.setAuditoriaRegistro(solicitudBD, contexto);
					solicitudDao.save(solicitudBD);
					actualizarEstadoFlagEnviado(solicitudBD, contexto);
					expedienteInRO = crearExpedientePresentacion(solicitudBD, contexto);
	
					formato04 = archivoService.obtenerTipoArchivo(solicitudBD.getIdSolicitud(),
							Constantes.LISTADO.TIPO_ARCHIVO.FORMATO);
					if (formato04 == null) {
						formato04 = generarReporte(solicitudBD.getIdSolicitud(), contexto);
					}
					archivosAlfresco = new ArrayList<File>();
					File file = null;
					try {
						File dir = new File(pathTemporal+File.separator+"temporales" +File.separator+ solicitudBD.getIdSolicitud());
						if (!dir.exists()) {
							dir.mkdirs();
						}
						file = new File(
								pathTemporal+File.separator+"temporales" +File.separator+  solicitudBD.getIdSolicitud() + File.separator + formato04.getNombre());
						FileUtils.writeByteArrayToFile(file, formato04.getContenido());
						formato04.setContenido(Files.readAllBytes(file.toPath()));
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO,codigoExpediente);
					}
					logger.info("archivos :"+(getDate()-inicioParcial));
					inicioParcial=getDate();
					Thread.sleep(segundos * 1000);
					logger.info("Timer :"+(getDate()-inicioParcial));
					inicioParcial=getDate();
					archivoService.guardar(formato04, contexto);
					archivosAlfresco.add(file);
					logger.info("actualizacion sicoes :"+(getDate()-inicioParcial));
					inicioParcial=getDate();
					DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
					logger.info("SIGED RESULT: {}", expedienteOutRO.getMessage());
					logger.info("agregarDocumento :"+(getDate()-inicioParcial));
					inicioParcial=getDate();
					if (documentoOutRO.getResultCode() != 1) {
						throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_AGREGAR_DOCUMENTOS,
								codigoExpediente,expedienteOutRO.getMessage());
					}
					actualizarEstadoFlagEnviado(solicitudBD, contexto);
					logger.info("actualizarEstadoFlagEnviado :"+(getDate()-inicioParcial));
					inicioParcial=getDate();
					String token = sneApiConsumer.obtenerTokenAcceso();
					AfiliacionVvoBeanDTO afiliacionVvoBeanDTO = new AfiliacionVvoBeanDTO();
					afiliacionVvoBeanDTO.setAppInvoker("VVO");
					afiliacionVvoBeanDTO.setLoginNuevoUsuario(solicitudBD.getPersona().getNumeroDocumento());
					afiliacionVvoBeanDTO.setEmailConsentimiento(solicitudBD.getPersona().getNumeroDocumento() + "@gmail.com");
					afiliacionVvoBeanDTO.setIdClienteSiged(solicitudBD.getPersona().getCodigoCliente().intValue());
					afiliacionVvoBeanDTO.setEstadoConsentimiento("A");
					afiliacionVvoBeanDTO.setTipoPersonaClienteSiged(solicitudBD.getPersona().isPesonaJuridica() ? "J" : "N");
					afiliacionVvoBeanDTO.setTipoConsentimiento("G");
					afiliacionVvoBeanDTO.setTipoAfiliacion("AO");
					logger.info("obtenerTokenAcceso :"+(getDate()-inicioParcial));
					inicioParcial=getDate();
					AfiliacionVvoOutRO afiliacionVvoOutRO = sneApiConsumer.registrarAfiliacion(afiliacionVvoBeanDTO, token);
					logger.info("registrarAfiliacion :"+(getDate()-inicioParcial));
					inicioParcial=getDate();
					//SICOES-614 11/01/2024 ECELIZ
					/*if (afiliacionVvoOutRO.getResultCode().equals("1")) {
						throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_AFILIAR_SNE,
								codigoExpediente,afiliacionVvoOutRO.getMessage());
					}*/
					solicitudBD.setCodigoConsentimiento(afiliacionVvoBeanDTO.getIdConsentimiento());
					AuditoriaUtil.setAuditoriaRegistro(solicitudBD, contexto);
					solicitudDao.save(solicitudBD);
					logger.info("solicitudDao.save :"+(getDate()-inicioParcial));
					inicioParcial=getDate();
				}else {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE,
							expedienteOutRO.getMessage());					
				}
			} catch (ValidacionException e) {
				archivarExpediente(codigoExpediente);
				logger.error("ERROR 2 {} ", e.getMessage(), e);
				throw e;
			} catch (Exception e) {
				archivarExpediente(codigoExpediente);
				logger.error("ERROR {} ", e.getMessage(), e);
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE,codigoExpediente,e.getMessage());
			}

			solicitudBD.setArchivos(new ArrayList<Archivo>());
			solicitudBD.getArchivos().add(formato04);
			notificacionService.enviarMensajeSolicitudInscripcion01(solicitudBD, contexto);
			if (solicitudBD.getPersona().getTipoPersona().getCodigo()
					.equals(Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO)) {
				notificacionService.enviarMensajeSolicitudInscripcionExtranjera15(solicitudBD, contexto);
			}
			logger.info("enviarMensajeSolicitudInscripcion01 :"+(getDate()-inicioParcial));
			logger.info("guardado total :"+(getDate()-inicioTotal));
			// ASIGNAR USUARIOS A SOLICITUD PERSONAS NATUALES
			List<OtroRequisito> listaPerfiles = new ArrayList<OtroRequisito>();
			listaPerfiles = otroRequisitoService.listarOtroRequisitosPerfil(solicitudBD.getIdSolicitud());
			for (OtroRequisito perfil : listaPerfiles)
			{
				if (perfil.getTipo().getIdListadoDetalle() == 91 &&
						(solicitudBD.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.NATURAL) ||
						solicitudBD.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO))) {
					
					List<PerfilAprobador> perfilesAprobador = new ArrayList<PerfilAprobador>();

					perfilesAprobador = perfilAprobadorDao.obtenerPerfilAprobadorPorIdPerfil(perfil.getPerfil().getIdListadoDetalle());
					
					//Verificar si el perfil cuenta con más de un evaluador
					List<Long> idEvaluadores = new ArrayList<Long>();
					
					if (perfilesAprobador.get(0).getEvaluador() != null) {
						idEvaluadores.add(perfilesAprobador.get(0).getEvaluador().getIdUsuario());
					
						if (perfilesAprobador.get(0).getEvaluador2() != null) {
							idEvaluadores.add(perfilesAprobador.get(0).getEvaluador2().getIdUsuario());
						
							if (perfilesAprobador.get(0).getEvaluador3() != null) {
								idEvaluadores.add(perfilesAprobador.get(0).getEvaluador3().getIdUsuario());
						
								if (perfilesAprobador.get(0).getEvaluador4() != null) {
									idEvaluadores.add(perfilesAprobador.get(0).getEvaluador4().getIdUsuario());
								
									if (perfilesAprobador.get(0).getEvaluador5() != null) {
										idEvaluadores.add(perfilesAprobador.get(0).getEvaluador5().getIdUsuario());
									
										if (perfilesAprobador.get(0).getEvaluador6() != null) {
											idEvaluadores.add(perfilesAprobador.get(0).getEvaluador6().getIdUsuario());
										
											if (perfilesAprobador.get(0).getEvaluador7() != null) {
												idEvaluadores.add(perfilesAprobador.get(0).getEvaluador7().getIdUsuario());
											
												if (perfilesAprobador.get(0).getEvaluador8() != null)
													idEvaluadores.add(perfilesAprobador.get(0).getEvaluador8().getIdUsuario());
											}
										}
									}
								}
							}
						}
					}
					
					//Comparar el número de evaluaciones pendientes de cada evaluador
					Long numeroEvaluacionesPendientes = 0L;
					Long idEvaluadorAsignado = 0L;
					
					//Primer evaluador
					Integer ordenEvaluador = 0;
					Long numeroEvaluacionesEvaluadorAsignado = new Long(otroRequisitoDao.listarPerfilesPendientesDeEvaluacionPorIdUsuario(idEvaluadores.get(0)).size());
					
					for (int i = 0; i < idEvaluadores.size(); i++) {
						
						numeroEvaluacionesPendientes  = new Long(otroRequisitoDao.listarPerfilesPendientesDeEvaluacionPorIdUsuario(idEvaluadores.get(i)).size());
						
						if (numeroEvaluacionesEvaluadorAsignado > numeroEvaluacionesPendientes) {
							numeroEvaluacionesEvaluadorAsignado = numeroEvaluacionesPendientes;
							ordenEvaluador = i;
						}				
					}
					
					switch (ordenEvaluador) {
						case 0:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador().getIdUsuario().toString());
							break;
						case 1:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador2().getIdUsuario().toString());
							break;
						case 2:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador3().getIdUsuario().toString());
							break;
						case 3:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador4().getIdUsuario().toString());
							break;
						case 4:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador5().getIdUsuario().toString());
							break;
						case 5:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador6().getIdUsuario().toString());
							break;
						case 6:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador7().getIdUsuario().toString());
							break;
						case 7:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador8().getIdUsuario().toString());
							break;
					}
					
					List<Asignacion> asignaciones = new ArrayList<Asignacion>();
					
					Asignacion asignacionActual = new Asignacion();
					asignacionActual.setSolicitud(solicitudBD);
					
					ListadoDetalle tipoActual = new ListadoDetalle();
					tipoActual.setIdListadoDetalle(Long.parseLong("102"));
					tipoActual.setCodigo("TECNICO");
					asignacionActual.setTipo(tipoActual);
					
					Usuario usuarioActual = new Usuario();
					usuarioActual.setIdUsuario(idEvaluadorAsignado);					
					asignacionActual.setUsuario(usuarioActual);
					asignaciones.add(asignacionActual);
					otroRequisitoService.asignarEvaluadorPerfil(perfil.getIdOtroRequisito(), asignaciones, contexto);
				}
			}

			// ASIGNAR USUARIOS A SOLICITUD PERSONAS JURÍDICAS
			List<Documento> listaDocumentos = new ArrayList<>();
			listaDocumentos = documentoService.buscar(solicitudBD.getIdSolicitud(), contexto);
			
			for(Documento doc: listaDocumentos) {
				if (doc.getActividadArea() != null && (solicitudBD.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.JURIDICA) ||
						solicitudBD.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR))){
					List<PerfilAprobador> perfilesAprobador = new ArrayList<PerfilAprobador>();
					// OBTENER PERFIL APROBADOR POR EL ID DE ACTIVIDAD AREA
					perfilesAprobador = perfilAprobadorDao.obtenerPerfilAprobadorPorIdPerfil(doc.getActividadArea().getIdListadoDetalle());

					if (solicitudBD.getOrigenRegistro() != null && doc.getEvaluacion().getCodigo().equals(Constantes.LISTADO.RESULTADO_EVALUACION.CUMPLE)
							&& Constantes.LISTADO.ORIGEN_REGISTRO.MODIFICACION.equals(solicitudBD.getOrigenRegistro().getCodigo())) {
						continue;
					}
					
					//Verificar si el perfil cuenta con más de un evaluador
					List<Long> idEvaluadores = new ArrayList<Long>();

					if (perfilesAprobador.get(0).getEvaluador() != null) {
						idEvaluadores.add(perfilesAprobador.get(0).getEvaluador().getIdUsuario());
					
						if (perfilesAprobador.get(0).getEvaluador2() != null) {
							idEvaluadores.add(perfilesAprobador.get(0).getEvaluador2().getIdUsuario());
						
							if (perfilesAprobador.get(0).getEvaluador3() != null) {
								idEvaluadores.add(perfilesAprobador.get(0).getEvaluador3().getIdUsuario());
						
								if (perfilesAprobador.get(0).getEvaluador4() != null) {
									idEvaluadores.add(perfilesAprobador.get(0).getEvaluador4().getIdUsuario());
								
									if (perfilesAprobador.get(0).getEvaluador5() != null) {
										idEvaluadores.add(perfilesAprobador.get(0).getEvaluador5().getIdUsuario());
									
										if (perfilesAprobador.get(0).getEvaluador6() != null) {
											idEvaluadores.add(perfilesAprobador.get(0).getEvaluador6().getIdUsuario());
										
											if (perfilesAprobador.get(0).getEvaluador7() != null) {
												idEvaluadores.add(perfilesAprobador.get(0).getEvaluador7().getIdUsuario());
											
												if (perfilesAprobador.get(0).getEvaluador8() != null)
													idEvaluadores.add(perfilesAprobador.get(0).getEvaluador8().getIdUsuario());
											}
										}
									}
								}
							}
						}
					}
					
					//Comparar el número de evaluaciones pendientes de cada evaluador
					Long numeroEvaluacionesPendientes = 0L;
					Long idEvaluadorAsignado = 0L;
					
					//Primer evaluador
					Integer ordenEvaluador = 0;
					Long numeroEvaluacionesEvaluadorAsignado = new Long(otroRequisitoDao.listarPerfilesPendientesDeEvaluacionPorIdUsuario(idEvaluadores.get(0)).size());
					
					for (int i = 0; i < idEvaluadores.size(); i++) {
						
						numeroEvaluacionesPendientes  = new Long(otroRequisitoDao.listarPerfilesPendientesDeEvaluacionPorIdUsuario(idEvaluadores.get(i)).size());
						
						if (numeroEvaluacionesEvaluadorAsignado > numeroEvaluacionesPendientes) {
							numeroEvaluacionesEvaluadorAsignado = numeroEvaluacionesPendientes;
							ordenEvaluador = i;
						}				
					}
					
					switch (ordenEvaluador) {
						case 0:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador().getIdUsuario().toString());
							break;
						case 1:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador2().getIdUsuario().toString());
							break;
						case 2:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador3().getIdUsuario().toString());
							break;
						case 3:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador4().getIdUsuario().toString());
							break;
						case 4:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador5().getIdUsuario().toString());
							break;
						case 5:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador6().getIdUsuario().toString());
							break;
						case 6:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador7().getIdUsuario().toString());
							break;
						case 7:
							idEvaluadorAsignado = Long.parseLong(perfilesAprobador.get(0).getEvaluador8().getIdUsuario().toString());
							break;
					}
					
					List<Asignacion> asignaciones = new ArrayList<Asignacion>();
					
					Asignacion asignacionActual = new Asignacion();
					asignacionActual.setSolicitud(solicitudBD);
					
					ListadoDetalle tipoActual = new ListadoDetalle();
					tipoActual.setIdListadoDetalle(Long.parseLong("102"));
					tipoActual.setCodigo("TECNICO");
					asignacionActual.setTipo(tipoActual);
					
					Usuario usuarioActual = new Usuario();
					usuarioActual.setIdUsuario(idEvaluadorAsignado);					
					asignacionActual.setUsuario(usuarioActual);
					asignaciones.add(asignacionActual);
					documentoService.asignarEvaluadorPerfil(doc, asignaciones, contexto);
				}
			}
			
			return solicitudBD;
		} else if (solicitudBD.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_SOLICITUD.OBSERVADO)) {
			inicioParcial=getDate();
			ListadoDetalle estadoSolicitud = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO);
			ListadoDetalle estadoRevision = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR);
			solicitudBD.setEstado(estadoSolicitud);
			
			/* --- 04-01-2024 --- INI ---*/
			List<OtroRequisito> listarOtroRequisitosPerfilObservado = otroRequisitoService.listarOtroRequisitosPerfilObservado(solicitudBD.getIdSolicitudPadre());
			//evaluacion administrativa
			if(solicitudBD.getResultadoAdministrativo().getCodigo().equals(Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO) && solicitudBD.getEstadoRevision().getCodigo().equals(Constantes.LISTADO.ESTADO_REVISION.OBSERVADO) && listarOtroRequisitosPerfilObservado.isEmpty()) {
				logger.info("evaluacion administrativa");
				ListadoDetalle resultadoRevision = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.ASIGNADO);
				ListadoDetalle estadoEvaluacionAdministrativa = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.ASIGNADO);
				ListadoDetalle estadoEvaluacionTecnica = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.CONCLUIDO);
				solicitudBD.setEstadoRevision(resultadoRevision);
				solicitudBD.setEstadoEvaluacionAdministrativa(estadoEvaluacionAdministrativa);
				solicitudBD.setEstadoEvaluacionTecnica(estadoEvaluacionTecnica);
				//evaluacion tecnica
			}else if(solicitudBD.getResultadoAdministrativo().getCodigo().equals(Constantes.LISTADO.RESULTADO_EVALUACION_ADM.CALIFICA) && solicitudBD.getEstadoRevision().getCodigo().equals(Constantes.LISTADO.ESTADO_REVISION.OBSERVADO) && !listarOtroRequisitosPerfilObservado.isEmpty()) {
				logger.info("evaluacion tecnica");
				ListadoDetalle resultadoRevision = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR);
				ListadoDetalle estadoEvaluacionTecnica = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR);
				ListadoDetalle estadoEvaluacionAdministrativa = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.CONCLUIDO);
				solicitudBD.setEstadoRevision(resultadoRevision);
				solicitudBD.setEstadoEvaluacionAdministrativa(estadoEvaluacionAdministrativa);
				solicitudBD.setEstadoEvaluacionTecnica(estadoEvaluacionTecnica);
				//evaluacion administrativa y tecnica
			}else if(solicitudBD.getResultadoAdministrativo().getCodigo().equals(Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO) && solicitudBD.getEstadoRevision().getCodigo().equals(Constantes.LISTADO.ESTADO_REVISION.OBSERVADO) && !listarOtroRequisitosPerfilObservado.isEmpty()){
			//}else{
				logger.info("evaluacion administrativa y tecnica");
				ListadoDetalle resultadoRevision = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR);
				ListadoDetalle estadoEvaluacionTecnica = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR);
				ListadoDetalle estadoEvaluacionAdministrativa = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.ASIGNADO);
				solicitudBD.setEstadoRevision(resultadoRevision);
				solicitudBD.setEstadoEvaluacionAdministrativa(estadoEvaluacionAdministrativa);
				solicitudBD.setEstadoEvaluacionTecnica(estadoEvaluacionTecnica);
			}
			/* --- 04-01-2024 --- FIN ---*/
			solicitudBD.setFechaPresentacion(new Date());
			solicitudBD.setFlagActivo(Constantes.FLAG.ACTIVO);
			ListadoDetalle plazoRespuesta = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.RESPUESTA_SUBSANACION_OBSERVACIONES);
			solicitudBD.setNumeroPlazoResp(Long.parseLong(plazoRespuesta.getValor()));
			solicitudBD.setFechaPlazoResp(
					calcularFechaFin(solicitudBD.getFechaPresentacion(), solicitudBD.getNumeroPlazoResp()));

			ListadoDetalle plazoAsignar = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO,
					Constantes.LISTADO.PLAZOS.ASIGNAR_EVALUADORES);
			solicitudBD.setNumeroPlazoAsig(Long.parseLong(plazoAsignar.getValor()));
			solicitudBD.setFechaPlazoAsig(
					calcularFechaFin(solicitudBD.getFechaPresentacion(), solicitudBD.getNumeroPlazoAsig()));

			ListadoDetalle tipoSolicitud = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.TIPO_SOLICITUD.CODIGO, Constantes.LISTADO.TIPO_SOLICITUD.SUBSANACION);
			solicitudBD.setTipoSolicitud(tipoSolicitud);
			
			ListadoDetalle plazoTecnico = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO,Constantes.LISTADO.PLAZOS.CONCLUIR_EVALUACION_TECNICA);
			solicitudBD.setNumeroPlazoTecnico(Long.parseLong(plazoTecnico.getValor()));
			solicitudBD.setFechaPlazoTecnico(calcularFechaFin(solicitudBD.getFechaPresentacion(), solicitudBD.getNumeroPlazoTecnico()));
			AuditoriaUtil.setAuditoriaRegistro(solicitudBD, contexto);
			solicitudDao.save(solicitudBD);

			Solicitud solicitudPadre = solicitudDao.obtener(solicitudBD.getIdSolicitudPadre());
			solicitudPadre.setFlagActivo(Constantes.FLAG.ACTIVO);
			AuditoriaUtil.setAuditoriaRegistro(solicitudPadre, contexto);
			solicitudDao.save(solicitudPadre);
			logger.info("guardar solicitud subsanacion :"+(getDate()-inicioParcial));
			
			ExpedienteInRO expedienteInRO = crearExpedienteAgregarDocumentos(solicitudBD, contexto);
			Archivo formato04 = null;
			try {
				formato04 = archivoService.obtenerTipoArchivo(solicitudBD.getIdSolicitud(),
						Constantes.LISTADO.TIPO_ARCHIVO.SUBSANACION);
				if (formato04 == null) {
					formato04 = generarReporteSubsanacion(solicitudBD.getIdSolicitud(), contexto);
					archivoService.guardar(formato04, contexto);
				}
			} catch (Exception e) {
				logger.error("ERROR S-----", e.getMessage(), e);
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_SUBSANAR);
			}
			archivosAlfresco = archivoService.obtenerArchivoContenido(solicitudBD.getIdSolicitud(),Constantes.LISTADO.TIPO_ARCHIVO.SUBSANACION, contexto);
			try {				
				DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
				if (documentoOutRO.getResultCode() != 1) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE,
							documentoOutRO.getMessage());
				}
				personaService.guardar(solicitudBD.getPersona(), contexto);
				AuditoriaUtil.setAuditoriaRegistro(solicitudBD, contexto);
				solicitudDao.save(solicitudBD);
				actualizarEstadoRequisito(solicitudBD, contexto);
			} catch (ValidacionException e) {
				throw e;
			} catch (Exception e) {
				logger.error("ERROR {} ", e.getMessage(), e);
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE);
			}

			solicitudBD.setArchivos(new ArrayList<Archivo>());
			solicitudBD.getArchivos().add(formato04);
			notificacionService.enviarMensajeSubsanacionIObservaciones05(solicitudBD, contexto);
			if (solicitudBD.getPersona().getTipoPersona().getCodigo()
					.equals(Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO)) {
				notificacionService.enviarMensajeSolicitudSubsanacionExtranjera16(solicitudBD, contexto);
			}
			logger.info("guardado total2 :"+(getDate()-inicioTotal));
			return solicitudBD;
		}
		
		return null;
	}

	private Solicitud enviarModificacion(Solicitud solicitudBD, Contexto contexto) {
		Solicitud solicitudPadre = solicitudDao.obtener(solicitudBD.getIdSolicitudPadre());
		Solicitud solicitudRes = null;

		if (solicitudPadre != null) {
			solicitudBD.setResultadoAdministrativo(solicitudPadre.getResultadoAdministrativo());
			solicitudBD.setEstadoEvaluacionTecnica(solicitudPadre.getEstadoEvaluacionTecnica());
			solicitudBD.setEstadoEvaluacionAdministrativa(solicitudPadre.getEstadoEvaluacionAdministrativa());
			solicitudBD.setObservacionAdmnistrativa(solicitudPadre.getObservacionAdmnistrativa());
		}

		ListadoDetalle estadoConcluido = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO);

		ListadoDetalle estadoRevision = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.CONCLUIDO);

		solicitudBD.setEstado(estadoConcluido);
		solicitudBD.setEstadoRevision(estadoRevision);
		solicitudBD.setFechaPresentacion(new Date());
		solicitudBD.setFlagActivo(Constantes.FLAG.ACTIVO);
		ExpedienteInRO expedienteInRO = null;
		List<File> archivosAlfresco = null;
		try {
			expedienteInRO = crearExpedientePresentacion(solicitudBD, contexto);
			archivosAlfresco = archivoService.obtenerArchivoModificacion(solicitudBD.getIdSolicitud(), contexto);
			DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
			if (documentoOutRO.getResultCode() != 1) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE,
						documentoOutRO.getMessage());
			}
			
			AuditoriaUtil.setAuditoriaActualizacion(solicitudBD, contexto);
			solicitudRes = solicitudDao.save(solicitudBD);
		} catch (Exception e) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE);
		}

		return solicitudRes;
	}

	private void archivarExpediente(String codigoExpediente) {
		sigedApiConsumer.archivarExpediente(codigoExpediente, "SICOES-Error en transaccion");		
	}

	public boolean esTerminoCondiciones(OtroRequisito otroRequisito) {
		return (otroRequisito.getTipoRequisito()!=null&&(Constantes.LISTADO.OTROS_DOCUMENTOS_PN.TERMINOS_CONDICIONES.equals(otroRequisito.getTipoRequisito().getCodigo())
		|| Constantes.LISTADO.OTROS_DOCUMENTOS_PN_POSTOR.TERMINOS_CONDICIONES.equals(otroRequisito.getTipoRequisito().getCodigo())
		||Constantes.LISTADO.OTROS_DOCUMENTOS_PJ.TERMINOS_CONDICIONES.equals(otroRequisito.getTipoRequisito().getCodigo())
		||Constantes.LISTADO.OTROS_DOCUMENTOS_EXTRANJERO.TERMINOS_CONDICIONES.equals(otroRequisito.getTipoRequisito().getCodigo())
		));
	}

	private void validarPresentacion(Solicitud solicitudBD, Contexto contexto) {
		List<OtroRequisito> otrosRequisito = otroRequisitoService.listarOtroRequisito(
				Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, solicitudBD.getIdSolicitud());
		List<OtroRequisito> perfil = otroRequisitoService
				.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, solicitudBD.getIdSolicitud());
		List<Estudio> estudiosAcademicos = estudioService.buscar(Constantes.LISTADO.TIPO_ESTUDIO.ACADEMICOS,
				solicitudBD.getIdSolicitud(), contexto);
		List<Estudio> estudiosCapacitacion = estudioService.buscar(Constantes.LISTADO.TIPO_ESTUDIO.CAPACITACION,
				solicitudBD.getIdSolicitud(), contexto);
		List<Documento> documentos = documentoService.buscar(solicitudBD.getIdSolicitud(), contexto);
		String[] codigosOtroRequisitoRequerido = null;

		ListadoDetalle tipoPersona = obtenerTipoPersona(solicitudBD.getSolicitudUuid());

		if (Constantes.LISTADO.TIPO_PERSONA.JURIDICA.equals(tipoPersona.getCodigo())) {
			codigosOtroRequisitoRequerido = new String[] { Constantes.LISTADO.OTROS_DOCUMENTOS_PJ.DOC_ACREDITE,
					Constantes.LISTADO.OTROS_DOCUMENTOS_PJ.RUC, Constantes.LISTADO.OTROS_DOCUMENTOS_PJ.DJ };
		} else if (Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR.equals(tipoPersona.getCodigo())) {
			codigosOtroRequisitoRequerido = new String[] { Constantes.LISTADO.OTROS_DOCUMENTOS_PJ.RUC,
					Constantes.LISTADO.OTROS_DOCUMENTOS_PJ.DJ };
		} else if (Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO.equals(tipoPersona.getCodigo())) {
			codigosOtroRequisitoRequerido = new String[] { Constantes.LISTADO.OTROS_DOCUMENTOS_EXTRANJERO.DOC_ACREDITE,
					Constantes.LISTADO.OTROS_DOCUMENTOS_EXTRANJERO.INSTRUMENTO };
		} else {
			codigosOtroRequisitoRequerido = new String[] { Constantes.LISTADO.OTROS_DOCUMENTOS_PN.DJ,
					Constantes.LISTADO.OTROS_DOCUMENTOS_PN.DNI, Constantes.LISTADO.OTROS_DOCUMENTOS_PN.RUC };
		}

		if (validarJuridicoPostor(solicitudBD.getSolicitudUuid())) {
			if (perfil.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.INGRESE_SECTORES);
			}
			if (documentos.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.INGRESE_DOCUMENTOS_EXPERIENCIA);
			}

		} else {
			if (perfil.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.INGRESE_PERFILES);
			}
			if (estudiosAcademicos.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.INGRESE_GRADOS_ACADEMICOS);
			}
			if (estudiosCapacitacion.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.INGRESE_CAPACITACIONES);
			}
			if (documentos.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.INGRESE_DOCUMENTOS_EXPERIENCIA);
			}
			for (Estudio estudio : estudiosAcademicos) {
				Estudio estudioBD = estudioService.obtener(estudio.getIdEstudio(), contexto);
				if (estudioBD.getArchivos() == null || estudioBD.getArchivos().isEmpty()) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTUDIO_SIN_ARCHIVO);
				}

			}
		}

		
		
		
		boolean esPefilS4 = esPerfilS4(perfil);
		OtroRequisito requisitoEliminar = null;
		for (OtroRequisito requisito : otrosRequisito) {
			if (!esTerminoCondiciones(requisito)) {
				boolean requisitoOtroRQValidar = !requisito.getTipoRequisito().getCodigo()
						.equals(Constantes.LISTADO.OTROS_DOCUMENTOS_PN.CONSTANCIA_APROBACION);
				boolean requisitoCA = requisito.getTipoRequisito().getCodigo()
						.equals(Constantes.LISTADO.OTROS_DOCUMENTOS_PN.CONSTANCIA_APROBACION);
				if (requisitoOtroRQValidar || (requisitoCA && esPefilS4)) {
					if (!Constantes.FLAG.ACTIVO.toString().equals(requisito.getFlagActivo())) {
						throw new ValidacionException(Constantes.CODIGO_MENSAJE.OTRO_REQUISITO_REQUERIDOS);
					}
					if (Constantes.LISTADO.OTROS_DOCUMENTOS_PN.DNI.equals(requisito.getTipoRequisito().getCodigo())) {
						if (requisito.getFlagElectronico() == null || requisito.getFlagFirmaDigital() == null) {
							throw new ValidacionException(Constantes.CODIGO_MENSAJE.OTRO_REQUISITO_REQUERIDOS);
						}
					}
					List<Archivo> listArchivo = archivoService.buscar(null, null, requisito.getIdOtroRequisito(),
							contexto);
					if (listArchivo == null || listArchivo.isEmpty()) {
						throw new ValidacionException(Constantes.CODIGO_MENSAJE.OTRO_REQUISITO_REQUERIDOS);
					}
				}
				if (requisitoCA && !esPefilS4) {
					requisitoEliminar = requisito;
				}
			}
		}
		for (String codigo : codigosOtroRequisitoRequerido) {
			boolean encontrado = false;
			for (OtroRequisito requisito : otrosRequisito) {
				if (codigo.equals(requisito.getTipoRequisito().getCodigo())) {
					encontrado = true;
					break;
				}
			}
			if (!encontrado) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.OTRO_REQUISITO_REQUERIDOS);
			}
		}
		/*if (solicitudBD.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_SOLICITUD.OBSERVADO)) {
			List<Documento> listDocumento = documentoService.buscar(solicitudBD.getIdSolicitud(), contexto);
			List<OtroRequisito> listPerfiles = otroRequisitoService.listarOtroRequisito(
					Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, solicitudBD.getIdSolicitud());
			List<OtroRequisito> listOtroRequisito = otroRequisitoService.listarOtroRequisito(
					Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, solicitudBD.getIdSolicitud());
			List<Estudio> listCapacitacion = estudioService.buscar(Constantes.LISTADO.TIPO_ESTUDIO.CAPACITACION,
					solicitudBD.getIdSolicitud(), contexto);
			List<Estudio> listAcademicos = estudioService.buscar(Constantes.LISTADO.TIPO_ESTUDIO.ACADEMICOS,
					solicitudBD.getIdSolicitud(), contexto);

			for (Documento documento : listDocumento) {
				if (Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO.equals(documento.getEvaluacion().getCodigo())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.RESPONDA_EVALUACIONES_OBSERVADA);
				}
			}
			for (OtroRequisito otroRequisito : listPerfiles) {
				if (Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO
						.equals(otroRequisito.getEvaluacion().getCodigo())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.RESPONDA_EVALUACIONES_OBSERVADA);
				}
			}
			for (OtroRequisito otroRequisito : listOtroRequisito) {
				if (!esTerminoCondiciones(otroRequisito)) {
					if (Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO
							.equals(otroRequisito.getEvaluacion().getCodigo())) {
						throw new ValidacionException(Constantes.CODIGO_MENSAJE.RESPONDA_EVALUACIONES_OBSERVADA);
					}
				}
			}

			for (Estudio estudio : listCapacitacion) {
				if (Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO.equals(estudio.getEvaluacion().getCodigo())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.RESPONDA_EVALUACIONES_OBSERVADA);
				}
			}
			for (Estudio estudio : listAcademicos) {
				if (Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO.equals(estudio.getEvaluacion().getCodigo())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.RESPONDA_EVALUACIONES_OBSERVADA);
				}
			}

		}*/
		if (requisitoEliminar != null) {
			otroRequisitoService.eliminar(requisitoEliminar.getIdOtroRequisito(), contexto);
		}
	}

	private boolean esPerfilS4(List<OtroRequisito> perfil) {
		for (OtroRequisito otroRequisito : perfil) {
			if (otroRequisito.getPerfil() != null
					&& otroRequisito.getPerfil().getNombre().toUpperCase().contains("S4")) {
				return true;
			}
		}

		return false;
	}

	private void actualizarEstadoFlagEnviado(Solicitud solicitudBD, Contexto contexto) {
	    List<OtroRequisito> otrosRequisito = otroRequisitoService.listarOtroRequisito(solicitudBD.getIdSolicitud());
	    for (OtroRequisito requisito : otrosRequisito) {
	        AuditoriaUtil.setAuditoriaRegistro(requisito, contexto);
	        requisito.setFlagSiged(Constantes.FLAG.ACTIVO);
	    }
	    otroRequisitoDao.saveAll(otrosRequisito);

	    List<Estudio> estudios = estudioService.buscar(solicitudBD.getIdSolicitud(), contexto);
	    for (Estudio estudio : estudios) {
	        AuditoriaUtil.setAuditoriaRegistro(estudio, contexto);
	        estudio.setFlagSiged(Constantes.FLAG.ACTIVO);
	    }
	    estudioDao.saveAll(estudios);

	    List<Documento> documentos = documentoService.buscar(solicitudBD.getIdSolicitud(), contexto);
	    for (Documento documento : documentos) {
	        AuditoriaUtil.setAuditoriaRegistro(documento, contexto);
	        documento.setFlagSiged(Constantes.FLAG.ACTIVO);
	    }
	    documentoDao.saveAll(documentos);
	}


	private void actualizarEstadoRequisito(Solicitud solicitudBD, Contexto contexto) {
		List<OtroRequisito> otrosRequisito = otroRequisitoService.listarOtroRequisito(solicitudBD.getIdSolicitud());
		ListadoDetalle porEvaluar = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO, Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR);
		for (OtroRequisito requisito : otrosRequisito) {
			if (requisito.getEvaluacion() != null && Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO.equals(requisito.getEvaluacion().getCodigo())) {
				AuditoriaUtil.setAuditoriaRegistro(requisito, contexto);
				requisito.setEvaluacion(porEvaluar);
				otroRequisitoDao.save(requisito);
			}
		}

		List<Estudio> estudios = estudioService.buscar(solicitudBD.getIdSolicitud(), contexto);
		for (Estudio estudio : estudios) {
			if (Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO.equals(estudio.getEvaluacion().getCodigo())) {
				AuditoriaUtil.setAuditoriaRegistro(estudio, contexto);
				estudio.setEvaluacion(porEvaluar);
				estudioDao.save(estudio);
			}
		}

		List<Documento> documentos = documentoService.buscar(solicitudBD.getIdSolicitud(), contexto);
		for (Documento documento : documentos) {
			if (Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO.equals(documento.getEvaluacion().getCodigo())) {
				AuditoriaUtil.setAuditoriaRegistro(documento, contexto);
				documento.setEvaluacion(porEvaluar);
				documentoDao.save(documento);
			}
		}

	}

	private Date calcularFechaFin(Date fechaPresentacion, Long dia) {
		return sigedApiConsumer.calcularFechaFin(fechaPresentacion, dia, Constantes.DIAS_HABILES);
	}

	private ListadoDetalle setTipoPersona(Solicitud solicitud, Solicitud solicitudBD) {
		ListadoDetalle tipoPersona = null;
		if (solicitudBD == null) {
			tipoPersona = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_PERSONA.CODIGO,
					solicitud.getPersona().getTipoPersona().getCodigo());
		} else {
			tipoPersona = solicitudBD.getPersona().getTipoPersona();
		}
		solicitud.getPersona().setTipoPersona(tipoPersona);
		return tipoPersona;
	}

	@Transactional(rollbackFor = Exception.class)
	public Solicitud guardar(Solicitud solicitud, Contexto contexto) {
		empresasSancionadaService.validarEmpresaSancionada(contexto.getUsuario().getCodigoRuc());
		boolean esNuevo = solicitud.getSolicitudUuid() == null;
		Solicitud solicitudBD = null;
		if(!esNuevo) {
			solicitudBD = this.obtener(solicitud.getSolicitudUuid(), contexto);
			solicitud.setIdSolicitud(solicitudBD.getIdSolicitud());
		}
		ListadoDetalle tipoPersona = setTipoPersona(solicitud, solicitudBD);
		String modo=env.getProperty("modo.produccion");
		if("1".equals(modo)) {	
			if(esNuevo) {
				Integer cantidad = solicitudDao.contarPendientes(contexto.getUsuario().getIdUsuario(),tipoPersona.getCodigo());
				if(cantidad>0) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_EN_TRAMITE);
				}
			}
		}
		
		if (solicitudBD == null
				|| solicitudBD.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)) {			

			AuditoriaUtil.setAuditoriaRegistro(solicitud,contexto);
			Representante representanteBD = new Representante();
			if (solicitud.getRepresentante() != null) {
				//Se busca el Estado para el Nuevo Representante
				ListadoDetalle estadoNuevoRepr = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_REPRESENTANTE.CODIGO, Constantes.LISTADO.ESTADO_REPRESENTANTE.ACTIVO);

				Representante representanteNuevo = solicitud.getRepresentante();
				representanteNuevo.setEstado(estadoNuevoRepr);
				solicitud.setRepresentante(representanteNuevo);

				representanteBD = representanteService.guardar(solicitud.getRepresentante(), contexto);
				solicitud.setRepresentante(representanteBD);
			}

			if (!solicitud.getPersona().isPesonaJuridica()) {
				solicitud.getPersona().setPais(contexto.getUsuario().getPais());
			}
			Persona personaBD = personaService.guardar(solicitud.getPersona(), contexto);
			solicitud.setPersona(personaBD);
			ListadoDetalle tipoSolicitud = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.TIPO_SOLICITUD.CODIGO, Constantes.LISTADO.TIPO_SOLICITUD.INSCRIPCION);
			ListadoDetalle estadoSolicitud = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR);
			ListadoDetalle estadoRevision = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR);

			if (solicitud.getTipoSolicitud() != null && solicitud.getTipoSolicitud().getCodigo().equals(Constantes.LISTADO.TIPO_SOLICITUD.MODIFICACION)) {
				tipoSolicitud = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.TIPO_SOLICITUD.CODIGO, Constantes.LISTADO.TIPO_SOLICITUD.MODIFICACION);
				solicitud.setTipoSolicitud(tipoSolicitud);
			} else {
				solicitud.setTipoSolicitud(tipoSolicitud);
			}

			solicitud.setEstadoRevision(estadoRevision);
			solicitud.setFechaRegistro(new Date());
			solicitud.setUsuario(contexto.getUsuario());
			solicitud.setEstado(estadoSolicitud);
			solicitud.setFlagActivo(Constantes.FLAG.ACTIVO);
			if (solicitudBD != null) {
				solicitud.setIdSolicitudPadre(solicitudBD.getIdSolicitudPadre());
				solicitud.setSolicitudUuidPadre(solicitudBD.getSolicitudUuidPadre());
				solicitud.setOrigenRegistro(solicitudBD.getOrigenRegistro());
			}

			AuditoriaUtil.setAuditoriaRegistro(solicitud, contexto);
			solicitudBD = solicitudDao.save(solicitud);

			//Actualizar el idSolicitud en el Representante
			if (solicitud.getRepresentante() != null) {
				representanteBD.setIdSolicitud(solicitudBD.getIdSolicitud());
				representanteService.guardar(representanteBD, contexto);
			}

			if (esNuevo) {
				solicitud.setSolicitudUuid(UUID.randomUUID().toString());
				List<ListadoDetalle> otroRequisitos = null;
				if (Constantes.LISTADO.TIPO_PERSONA.JURIDICA.equals(tipoPersona.getCodigo())) {
					otroRequisitos = listadoDetalleService.buscar(Constantes.LISTADO.OTROS_DOCUMENTOS_PJ.CODIGO,
							contexto);
				} else if (Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR.equals(tipoPersona.getCodigo())) {
					otroRequisitos = listadoDetalleService.buscar(Constantes.LISTADO.OTROS_DOCUMENTOS_PN_POSTOR.CODIGO,
							contexto);
				} else if (Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO.equals(tipoPersona.getCodigo())) {
					otroRequisitos = listadoDetalleService.buscar(Constantes.LISTADO.OTROS_DOCUMENTOS_PN.CODIGO,
							contexto);
				} else if (Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO.equals(tipoPersona.getCodigo())) {
					otroRequisitos = listadoDetalleService.buscar(Constantes.LISTADO.OTROS_DOCUMENTOS_EXTRANJERO.CODIGO,
							contexto);
				}
				ListadoDetalle tipoOtroRequisito = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.CODIGO,
						Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO);
				for (ListadoDetalle listadoDetalle : otroRequisitos) {
					OtroRequisito otroRequisitoAux = new OtroRequisito();
					otroRequisitoAux.setTipo(tipoOtroRequisito);
					otroRequisitoAux.setTipoRequisito(listadoDetalle);
					otroRequisitoAux.setSolicitud(solicitudBD);
					AuditoriaUtil.setAuditoriaRegistro(otroRequisitoAux, contexto);
					otroRequisitoAux = otroRequisitoService.guardar(otroRequisitoAux, contexto);
				}
			} else {
				List<OtroRequisito> otroRequisitos = solicitud.getOtrosRequisitos();
				if (otroRequisitos != null) {
					List<OtroRequisito> otroRequisitosBD = otroRequisitoService.listarOtroRequisito(
							Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, solicitudBD.getIdSolicitud());
					boolean encontrado = false;
					for (OtroRequisito otroRequisitoBD : otroRequisitosBD) {
						encontrado = false;
						for (OtroRequisito otroRequisito : otroRequisitos) {
							if (otroRequisitoBD.getIdOtroRequisito().equals(otroRequisito.getIdOtroRequisito())) {
								encontrado = true;
								break;
							}
						}
						if (!encontrado) {
							otroRequisitoService.eliminar(otroRequisitoBD.getIdOtroRequisito(), contexto);
						}
					}
					for (OtroRequisito otroRequisito : otroRequisitos) {
						otroRequisito.setSolicitud(solicitudBD);
						otroRequisito = otroRequisitoService.guardar(otroRequisito, contexto);
					}
				}
			}
		} else {
			List<OtroRequisito> otroRequisitos = solicitud.getOtrosRequisitos();
			List<OtroRequisito> otroRequisitosActualizados = new ArrayList<>();
			if (otroRequisitos != null) {
				List<OtroRequisito> otroRequisitosBD = otroRequisitoService.listarOtroRequisito(
						Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, solicitudBD.getIdSolicitud());
				boolean encontrado = false;
				for (OtroRequisito otroRequisitoBD : otroRequisitosBD) {
					encontrado = false;
					for (OtroRequisito otroRequisito : otroRequisitos) {
						if (otroRequisitoBD.getIdOtroRequisito().equals(otroRequisito.getIdOtroRequisito())) {
							encontrado = true;
							if (!Objects.equals(otroRequisitoBD.getFlagActivo(), otroRequisito.getFlagActivo())) {
								logger.info("Cambio en flagActivo: BD={} | Nuevo={}", otroRequisitoBD.getFlagActivo(), otroRequisito.getFlagActivo());
								otroRequisitosActualizados.add(otroRequisito);
							}
							if (!Objects.equals(otroRequisitoBD.getFlagFirmaDigital(), otroRequisito.getFlagFirmaDigital())) {
								logger.info("Cambio en flagFirmaDigital: BD={} | Nuevo={}", otroRequisitoBD.getFlagFirmaDigital(), otroRequisito.getFlagFirmaDigital());
								otroRequisitosActualizados.add(otroRequisito);
							}
							if (!Objects.equals(otroRequisitoBD.getFlagElectronico(), otroRequisito.getFlagElectronico())) {
								logger.info("Cambio en flagElectronico: BD={} | Nuevo={}", otroRequisitoBD.getFlagElectronico(), otroRequisito.getFlagElectronico());
								otroRequisitosActualizados.add(otroRequisito);
							}
							if (!Objects.equals(otroRequisitoBD.getArchivo(),otroRequisito.getArchivo())) {
								logger.info("Cambio en Archivo: BD={} | Nuevo={}", otroRequisitoBD.getArchivo(), otroRequisito.getArchivo());
								otroRequisitosActualizados.add(otroRequisito);
							}
							List<Archivo> archivoOtroRQ = archivoService.buscar(null, null, otroRequisito.getIdOtroRequisito(), contexto);
							for (Archivo archivoBD : archivoOtroRQ) {
								if (otroRequisito.getArchivo() != null && 
									!Objects.equals(archivoBD.getIdArchivo(), otroRequisito.getArchivo().getIdArchivo())) {
									otroRequisitosActualizados.add(otroRequisito);
								}
							}
							break;
						}
					}
					if (!encontrado) {
						otroRequisitoService.eliminar(otroRequisitoBD.getIdOtroRequisito(), contexto);
					}
				}
				for (OtroRequisito otroRequisito : otroRequisitosActualizados) {
					otroRequisito.setSolicitud(solicitudBD);
					otroRequisito = otroRequisitoService.guardar(otroRequisito, contexto);
				}
			}
		}

		return solicitudBD;
	}

	@Transactional(rollbackFor = Exception.class)
	public Solicitud actualizar(Solicitud solicitud, Contexto contexto) {
		Solicitud solicitudBD = this.obtener(solicitud.getSolicitudUuid(), contexto);
		if(solicitudBD.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO)
				&& (solicitudBD.getTipoSolicitud().getCodigo().equals(Constantes.LISTADO.TIPO_SOLICITUD.INSCRIPCION)
				|| solicitudBD.getTipoSolicitud().getCodigo().equals(Constantes.LISTADO.TIPO_SOLICITUD.SUBSANACION))) {
			Persona persona = solicitudBD.getPersona();
			ListadoDetalle tipoPersona = persona.getTipoPersona();
			if(tipoPersona.getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.JURIDICA)
				|| tipoPersona.getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO)) {
				if (solicitud.getRepresentante() != null) {
					//validar que sea diferente el Representante
					if(!solicitudBD.getRepresentante().getNumeroDocumento().equals(solicitud.getRepresentante().getNumeroDocumento())) {
						//Actualizar el Representante actual a Inactivo
						ListadoDetalle estadoRepr = listadoDetalleService.obtenerListadoDetalle(
								Constantes.LISTADO.ESTADO_REPRESENTANTE.CODIGO, Constantes.LISTADO.ESTADO_REPRESENTANTE.INACTIVO);

						AuditoriaUtil.setAuditoriaRegistro(solicitudBD, contexto);
						representanteDao.updateEstadoRepresentanteSolicitud(solicitudBD.getIdSolicitud(),
								estadoRepr.getIdListadoDetalle(),
								solicitudBD.getUsuActualizacion(),
								solicitudBD.getIpActualizacion());

						//Se busca el Estado para el Nuevo Representante
						ListadoDetalle estadoNuevoRepr = listadoDetalleService.obtenerListadoDetalle(
								Constantes.LISTADO.ESTADO_REPRESENTANTE.CODIGO, Constantes.LISTADO.ESTADO_REPRESENTANTE.ACTIVO);

						Representante representanteNuevo = solicitud.getRepresentante();
						representanteNuevo.setEstado(estadoNuevoRepr);
						representanteNuevo.setIdSolicitud(solicitudBD.getIdSolicitud());
						AuditoriaUtil.setAuditoriaRegistro(representanteNuevo, contexto);
						Representante representanteBD = representanteService.guardar(representanteNuevo, contexto);

						solicitudBD.setRepresentante(representanteBD);
						//Obtener Representantes
						List<Representante> lista = representanteDao.obtenerRepresentantesSolicitud(solicitudBD.getIdSolicitud(),
								Constantes.LISTADO.ESTADO_REPRESENTANTE.INACTIVO);
						solicitudBD.setHistorialRepresentante(lista);
					}
				}

			}
			//Agregar campos de Persona
			if(Optional.ofNullable(solicitud.getPersona()).isPresent()) {
				Persona personaRequest = solicitud.getPersona();
				if(tipoPersona.getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.NATURAL)
						|| tipoPersona.getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR)
						|| tipoPersona.getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO)) {
					persona.setDireccion(this.validarCampo(personaRequest.getDireccion()));
					persona.setCodigoDepartamento(this.validarCampo(personaRequest.getCodigoDepartamento()));
					persona.setCodigoProvincia(this.validarCampo(personaRequest.getCodigoProvincia()));
					persona.setCodigoDistrito(this.validarCampo(personaRequest.getCodigoDistrito()));
				}
				persona.setTelefono1(this.validarCampo(personaRequest.getTelefono1()));
				persona.setTelefono2(this.validarCampo(personaRequest.getTelefono2()));
				persona.setTelefono3(this.validarCampo(personaRequest.getTelefono3()));
				persona.setCorreo(this.validarCampo(personaRequest.getCorreo()));
				solicitudBD.setPersona(persona);
			}

			// Actualizar DJ
			List<OtroRequisito> otrosReqs = solicitud.getOtrosRequisitos();
			String procedimiento = "Actualizacion";
			if (otrosReqs != null) {
				OtroRequisito dj = null;
				ListadoDetalle tipoPersonaSolicitud = obtenerTipoPersona(solicitud.getSolicitudUuid());
				if (tipoPersonaSolicitud != null &&
						(tipoPersonaSolicitud.getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.JURIDICA)
						|| tipoPersonaSolicitud.getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO)
						|| tipoPersonaSolicitud.getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR))) {
					dj = otrosReqs.stream().filter(or -> or.getTipoRequisito().getCodigo().equals(Constantes.LISTADO.OTROS_DOCUMENTOS_PJ.DJ))
							.findFirst().orElse(null);
				}

				if (tipoPersonaSolicitud != null &&
						(tipoPersonaSolicitud.getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.NATURAL)
						|| tipoPersonaSolicitud.getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO))) {
					dj = otrosReqs.stream().filter(or -> or.getTipoRequisito().getCodigo().equals(Constantes.LISTADO.OTROS_DOCUMENTOS_PN.DJ))
							.findFirst().orElse(null);
				}

				if (dj == null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.DJ_AUSENTE);
				}

				if (dj.getArchivo() == null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.DJ_AUSENTE);
				}

				List<Archivo> archivoOtroReq = archivoService.buscar(null, null, dj.getIdOtroRequisito(), contexto);
				for (Archivo archivoBD : archivoOtroReq) {
					if (dj.getArchivo() != null &&
							!Objects.equals(archivoBD.getIdArchivo(), dj.getArchivo().getIdArchivo())) {
						dj.setSolicitud(solicitudBD);
						ExpedienteInRO expedienteInRO = null;
						try {
							expedienteInRO = crearExpedientePresentacion(solicitudBD, contexto);
							List<File> files;
							files = archivoService.obtenerArchivoDj(dj.getIdOtroRequisito(), procedimiento, contexto);
							DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, files);
							if (documentoOutRO.getResultCode() != 1) {
								throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE,
										documentoOutRO.getMessage());
							}
							otroRequisitoService.guardar(dj, contexto);
						} catch (Exception e) {
							logger.error("ERROR {}", e.getMessage(), e);
							throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE);
						}

					}
				}
			}

			AuditoriaUtil.setAuditoriaRegistro(solicitudBD, contexto);
			return solicitudDao.save(solicitudBD);
		} else if (solicitudBD.getOrigenRegistro() != null && Constantes.LISTADO.ORIGEN_REGISTRO.MODIFICACION.equals(solicitudBD.getOrigenRegistro().getCodigo())) {
			// Actualizar DJ
			List<OtroRequisito> otrosReqs = solicitud.getOtrosRequisitos();
			if (otrosReqs != null) {
				OtroRequisito dj = otrosReqs.stream().filter(or -> or.getTipoRequisito().getCodigo().equals(Constantes.LISTADO.OTROS_DOCUMENTOS_PJ.DJ))
						.findFirst().orElse(null);

				List<Archivo> archivoOtroReq = archivoService.buscar(null, null, dj.getIdOtroRequisito(), contexto);
				for (Archivo archivoBD : archivoOtroReq) {
					if (dj.getArchivo() != null &&
							!Objects.equals(archivoBD.getIdArchivo(), dj.getArchivo().getIdArchivo())) {
						dj.setSolicitud(solicitudBD);
						otroRequisitoService.guardar(dj, contexto);
					}
				}
			}

			AuditoriaUtil.setAuditoriaRegistro(solicitudBD, contexto);
			return solicitudDao.save(solicitudBD);
		} else {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_TIPO_INCORRECTO);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Solicitud modificar(String solicitudUuid, Contexto contexto) {
		Long idSolicitud = solicitudDao.obtenerId(solicitudUuid);
		Solicitud solicitudBD = obtener(idSolicitud, contexto);
		if(solicitudBD.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO)
				&& (solicitudBD.getTipoSolicitud().getCodigo().equals(Constantes.LISTADO.TIPO_SOLICITUD.INSCRIPCION)
				|| solicitudBD.getTipoSolicitud().getCodigo().equals(Constantes.LISTADO.TIPO_SOLICITUD.SUBSANACION))) {
			// Clonar Solicitud
			Solicitud solicitudNueva = this.clonarSolicitud(solicitudBD, contexto);

			// Obtener Asignacion
			asignacionService.clonarAsignacion(solicitudBD, solicitudNueva, contexto);
			Pageable pageable = PageRequest.of(0, Integer.parseInt(env.getProperty("maximo.paginas")));
			solicitudNueva.setAsignados(asignacionService.buscar(solicitudNueva.getIdSolicitud(), null, pageable, contexto).getContent());

			// Obtener estado "Por Asignar"
			ListadoDetalle estadoRevision = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR);

			// Actualizar Solicitud con campos faltantes
			ListadoDetalle estadoPreliminar = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR);
			solicitudNueva.setEstado(estadoPreliminar);
			solicitudNueva.setFlagArchivamiento(solicitudBD.getFlagArchivamiento());
			solicitudNueva.setFlagRespuesta(solicitudBD.getFlagRespuesta());
			solicitudNueva.setObservacionAdmnistrativa(solicitudBD.getObservacionAdmnistrativa());
			solicitudNueva.setObservacionTecnica(solicitudBD.getObservacionTecnica());
			solicitudNueva.setEstadoRevision(estadoRevision);

			// Actualizar Solicitud y secciones
			Solicitud solicitudNuevaActualizada = actualizarSolicitudModificada(solicitudNueva, contexto);

            // Obtener el origen de registro
            ListadoDetalle origenRegistro = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ORIGEN_REGISTRO.CODIGO, Constantes.LISTADO.ORIGEN_REGISTRO.MODIFICACION);
            solicitudNuevaActualizada.setOrigenRegistro(origenRegistro);

			// Asignar numero de expediente de solicitud padre para solicitudes de PN
			if (
					solicitudNuevaActualizada.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.NATURAL)
					|| solicitudNuevaActualizada.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO)
			) {
				solicitudNuevaActualizada.setNumeroExpediente(solicitudBD.getNumeroExpediente());
			}

			solicitudDao.save(solicitudNuevaActualizada);

			//Actualizar idSolicitud en Representantes
			List<Representante> representantes = representanteDao.obtenerRepresentantesSolicitud(solicitudBD.getIdSolicitud(),
				Constantes.LISTADO.ESTADO_REPRESENTANTE.INACTIVO);
			if(!representantes.isEmpty()) {
				representantes = representantes.stream()
						.map(repre -> {
							Representante representante = CloneUtil.clonarRepresentante(repre, contexto);
							AuditoriaUtil.setAuditoriaRegistro(representante, contexto);
							representante.setIdSolicitud(solicitudNuevaActualizada.getIdSolicitud());
							return representante;
						})
						.collect(Collectors.toList());
				representanteDao.saveAll(representantes);
				solicitudNuevaActualizada.setHistorialRepresentante(representantes);
			}
			return solicitudNuevaActualizada;
		}
		throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_TIPO_INCORRECTO);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Solicitud actualizarRequisito(Solicitud solicitud, Contexto contexto) {
		Long idSolicitud = solicitudDao.obtenerId(solicitud.getSolicitudUuid());
		Solicitud solicitudBD = obtener(idSolicitud, contexto);
		List<OtroRequisito> otroRequisitos = solicitud.getOtrosRequisitos();
		if (otroRequisitos != null) {
			List<OtroRequisito> otroRequisitosBD = otroRequisitoService.listarOtroRequisito(
					Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, solicitudBD.getIdSolicitud());
			boolean encontrado = false;
			for (OtroRequisito otroRequisitoBD : otroRequisitosBD) {
				encontrado = false;
				for (OtroRequisito otroRequisito : otroRequisitos) {
					if (otroRequisitoBD.getIdOtroRequisito().equals(otroRequisito.getIdOtroRequisito())) {
						encontrado = true;
						break;
					}
				}
				if (!encontrado) {
					otroRequisitoService.eliminar(otroRequisitoBD.getIdOtroRequisito(), contexto);
				}
			}
			for (OtroRequisito otroRequisito : otroRequisitos) {
				otroRequisito.setSolicitud(solicitudBD);
				otroRequisitoService.guardar(otroRequisito, contexto);
			}
		}
		return solicitudBD;
	}

	private String validarCampo(String campo) {
		return campo.trim().isEmpty() ? null : campo.trim();
	}

	public ExpedienteInRO crearExpedientePresentacion(Solicitud solicitud, Contexto contexto) {
		return crearExpediente(solicitud,
				Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear")),null, contexto);
	}

	public ExpedienteInRO crearExpedienteAgregarDocumentos(Solicitud solicitud, Contexto contexto) {
		return crearExpediente(solicitud,
				Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.adjuntar")),null, contexto);
	}
	
	public ExpedienteInRO crearExpedienteAgregarDocumentosTecnicos(Solicitud solicitud,Integer codigoUsuario, Contexto contexto) {
		return crearExpediente(solicitud,
				Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.tecnico")), codigoUsuario,contexto);
	}

	public ExpedienteInRO crearExpediente(Solicitud solicitud, Integer codigoTipoDocumento,Integer codigoUsuario, Contexto contexto) {
		ExpedienteInRO expediente = new ExpedienteInRO();
		DocumentoInRO documento = new DocumentoInRO();
		ClienteListInRO clientes = new ClienteListInRO();
		ClienteInRO cs = new ClienteInRO();
		List<ClienteInRO> cliente = new ArrayList<>();
		DireccionxClienteListInRO direcciones = new DireccionxClienteListInRO();
		DireccionxClienteInRO d = new DireccionxClienteInRO();
		List<DireccionxClienteInRO> direccion = new ArrayList<>();
		expediente.setProceso(Integer.parseInt(env.getProperty("crear.expediente.parametros.proceso")));
		expediente.setDocumento(documento);
		if (solicitud.getNumeroExpediente() != null) {
			expediente.setNroExpediente(solicitud.getNumeroExpediente());
		}
		if (solicitud.getPersona() != null) {
			documento.setAsunto(String.format(TITULO_REGISTRO, solicitud.getPersona().getNumeroDocumento()));
		}

		documento.setAppNameInvokes(SIGLA_PROYECTO);
		ListadoDetalle tipoDocumento = listadoDetalleService
				.obtener(solicitud.getPersona().getTipoDocumento().getIdListadoDetalle(), contexto);
		cs.setCodigoTipoIdentificacion(Integer.parseInt(tipoDocumento.getValor()));
		if (cs.getCodigoTipoIdentificacion() == 1) {
			cs.setNombre(solicitud.getPersona().getNombreRazonSocial());
			cs.setApellidoPaterno("-");
			cs.setApellidoMaterno("-");
		} else if(cs.getCodigoTipoIdentificacion() == 3){
			cs.setNombre(solicitud.getPersona().getNombres());
			cs.setApellidoPaterno(solicitud.getPersona().getApellidoPaterno());
			cs.setApellidoMaterno(solicitud.getPersona().getApellidoMaterno());
		}else{
			cs.setNombre(solicitud.getPersona().getNombres());
			cs.setApellidoPaterno(solicitud.getPersona().getApellidoPaterno());
			cs.setApellidoMaterno(solicitud.getPersona().getApellidoMaterno());
		}
		if (solicitud.getRepresentante() != null) {
			cs.setRepresentanteLegal(
					solicitud.getRepresentante().getNombres() + " " + solicitud.getRepresentante().getApellidoPaterno()
							+ " " + solicitud.getRepresentante().getApellidoMaterno());
		}
		cs.setRazonSocial(solicitud.getPersona().getNombreRazonSocial());
		cs.setNroIdentificacion(solicitud.getPersona().getNumeroDocumento());
		cs.setTipoCliente(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
		cliente.add(cs);
		d.setDireccion(solicitud.getPersona().getDireccion());
		d.setDireccionPrincipal(true);
		d.setEstado(env.getProperty("crear.expediente.parametros.direccion.estado").charAt(0));
		d.setTelefono(solicitud.getPersona().getTelefono1());
		if (solicitud.getPersona().getCodigoDistrito() != null) {
			d.setUbigeo(Integer.parseInt(solicitud.getPersona().getCodigoDistrito()));
		}
		direccion.add(d);
		direcciones.setDireccion(direccion);
		cs.setDirecciones(direcciones);
		clientes.setCliente(cliente);
		documento.setClientes(clientes);
		documento.setCodTipoDocumento(codigoTipoDocumento);
		documento.setEnumerado(env.getProperty("crear.expediente.parametros.enumerado").charAt(0));
		documento.setEstaEnFlujo(env.getProperty("crear.expediente.parametros.esta.en.flujo").charAt(0));
		documento.setFirmado(env.getProperty("crear.expediente.parametros.firmado").charAt(0));
		documento.setCreaExpediente(env.getProperty("crear.expediente.parametros.crea.expediente").charAt(0));
		documento.setPublico(env.getProperty("crear.expediente.parametros.crea.publico").charAt(0));
		documento.setNroFolios(Integer.parseInt(env.getProperty("crear.expediente.parametros.crea.folio")));
		/*if(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.tecnico"))==codigoTipoDocumento) {
			if(codigoUsuario!=null) {
				documento.setUsuarioCreador(codigoUsuario);
				documento.setFirmante(codigoUsuario);
			}	
		}else{
			documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
		}*/
		String stecnico=env.getProperty("crear.expediente.parametros.tipo.documento.informe.tecnico");
		int vtenico= Integer.parseInt(stecnico);
		
		if(vtenico==codigoTipoDocumento) {
			if(codigoUsuario!=null) {
				documento.setUsuarioCreador(codigoUsuario);
				documento.setFirmante(codigoUsuario);
			}	
		}
                if(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.respuesta.solicitud.pn"))==codigoTipoDocumento) {
			
				documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
				documento.setFirmante(Integer.parseInt(env.getProperty("siged.firmante.informe.respuesta.id.usuario")));
				
		}else{
			documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
		}
		logger.info("DOC_EXPEDIENTE--- :"+documento);
		logger.info("EXPEDIENTE_INFORME_TEC :"+expediente);
		return expediente;
	}
	
	//AFC
	
		public ExpedienteInRO crearExpedienteAgregarDocumentosAdminstrativos(Solicitud solicitud,Integer codigoUsuario, Contexto contexto) {
			return crearExpedienteAdminstrativos(solicitud,
					Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.administrativo")), codigoUsuario,contexto);
		}

		public ExpedienteInRO crearExpedienteAdminstrativos(Solicitud solicitud, Integer codigoTipoDocumento,Integer codigoUsuario, Contexto contexto) {
			ExpedienteInRO expediente = new ExpedienteInRO();
			DocumentoInRO documento = new DocumentoInRO();
			ClienteListInRO clientes = new ClienteListInRO();
			ClienteInRO cs = new ClienteInRO();
			List<ClienteInRO> cliente = new ArrayList<>();
			DireccionxClienteListInRO direcciones = new DireccionxClienteListInRO();
			DireccionxClienteInRO d = new DireccionxClienteInRO();
			List<DireccionxClienteInRO> direccion = new ArrayList<>();
			expediente.setProceso(Integer.parseInt(env.getProperty("crear.expediente.parametros.proceso")));
			expediente.setDocumento(documento);
			if (solicitud.getNumeroExpediente() != null) {
				expediente.setNroExpediente(solicitud.getNumeroExpediente());
			}
			if (solicitud.getPersona() != null) {
				documento.setAsunto(String.format(TITULO_REGISTRO, solicitud.getPersona().getNumeroDocumento()));
			}

			documento.setAppNameInvokes(SIGLA_PROYECTO);
			ListadoDetalle tipoDocumento = listadoDetalleService
					.obtener(solicitud.getPersona().getTipoDocumento().getIdListadoDetalle(), contexto);
			cs.setCodigoTipoIdentificacion(Integer.parseInt(tipoDocumento.getValor()));
			if (cs.getCodigoTipoIdentificacion() == 1) {
				cs.setNombre(solicitud.getPersona().getNombreRazonSocial());
				cs.setApellidoPaterno("-");
				cs.setApellidoMaterno("-");
			} else if(cs.getCodigoTipoIdentificacion() == 3){
				cs.setNombre(solicitud.getPersona().getNombres());
				cs.setApellidoPaterno(solicitud.getPersona().getApellidoPaterno());
				cs.setApellidoMaterno(solicitud.getPersona().getApellidoMaterno());
			}else{
				cs.setNombre(solicitud.getPersona().getNombres());
				cs.setApellidoPaterno(solicitud.getPersona().getApellidoPaterno());
				cs.setApellidoMaterno(solicitud.getPersona().getApellidoMaterno());
			}
			if (solicitud.getRepresentante() != null) {
				cs.setRepresentanteLegal(
						solicitud.getRepresentante().getNombres() + " " + solicitud.getRepresentante().getApellidoPaterno()
								+ " " + solicitud.getRepresentante().getApellidoMaterno());
			}
			cs.setRazonSocial(solicitud.getPersona().getNombreRazonSocial());
			cs.setNroIdentificacion(solicitud.getPersona().getNumeroDocumento());
			cs.setTipoCliente(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
			cliente.add(cs);
			d.setDireccion(solicitud.getPersona().getDireccion());
			d.setDireccionPrincipal(true);
			d.setEstado(env.getProperty("crear.expediente.parametros.direccion.estado").charAt(0));
			d.setTelefono(solicitud.getPersona().getTelefono1());
			if (solicitud.getPersona().getCodigoDistrito() != null) {
				d.setUbigeo(Integer.parseInt(solicitud.getPersona().getCodigoDistrito()));
			}
			direccion.add(d);
			direcciones.setDireccion(direccion);
			cs.setDirecciones(direcciones);
			clientes.setCliente(cliente);
			documento.setClientes(clientes);
			documento.setCodTipoDocumento(codigoTipoDocumento);
			documento.setEnumerado(env.getProperty("crear.expediente.parametros.enumerado").charAt(0));
			documento.setEstaEnFlujo(env.getProperty("crear.expediente.parametros.esta.en.flujo").charAt(0));
			documento.setFirmado(env.getProperty("crear.expediente.parametros.firmado").charAt(0));
			documento.setCreaExpediente(env.getProperty("crear.expediente.parametros.crea.expediente").charAt(0));
			documento.setPublico(env.getProperty("crear.expediente.parametros.crea.publico").charAt(0));
			documento.setNroFolios(Integer.parseInt(env.getProperty("crear.expediente.parametros.crea.folio")));
			String stecnico=env.getProperty("crear.expediente.parametros.tipo.documento.informe.administrativo");
			int vtenico= Integer.parseInt(stecnico);
			
			if(vtenico==codigoTipoDocumento) {
				if(codigoUsuario!=null) {
					documento.setUsuarioCreador(codigoUsuario);
					documento.setFirmante(codigoUsuario);
				}	
			}
	                if(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.respuesta.solicitud.pn"))==codigoTipoDocumento) {
				
					documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
					documento.setFirmante(Integer.parseInt(env.getProperty("siged.firmante.informe.respuesta.id.usuario")));
					
			}else{
				documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
			}
			logger.info("DOC_EXPEDIENTE--- :"+documento);
			logger.info("EXPEDIENTE_INFORME_ADM :"+expediente);
			return expediente;
		}
	
	Integer getInteger(Long date){
		try {
			return Integer.parseInt(date.toString());
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	@Override
	public Solicitud obtener(String solicitudUuid, Contexto contexto) {
		Long idSolicitud = solicitudDao.obtenerId(solicitudUuid);
		Solicitud solicitud = obtener(idSolicitud, contexto);
		Long idUsuario=contexto.getUsuario().getIdUsuario();
		// Buscar Historial Representantes
		List<Representante> lista = representanteDao.obtenerRepresentantesSolicitud(idSolicitud, Constantes.LISTADO.ESTADO_REPRESENTANTE.INACTIVO);
		solicitud.setHistorialRepresentante(lista);
		if (contexto.getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO) && solicitud.getUsuario().getIdUsuario().equals(idUsuario)) {
			return solicitud;
		}
		
		if(contexto.getUsuario().isRol(Constantes.ROLES.RESPONSABLE_ADMINISTRATIVO)||contexto.getUsuario().isRol(Constantes.ROLES.RESPONSABLE_TECNICO)) {
			Pageable pageable = PageRequest.of(0, Integer.parseInt(env.getProperty("maximo.paginas")));
			Page<Solicitud> page=buscarResponsable(null, null, solicitud.getNumeroExpediente(), null, null, null, null, pageable, contexto);
			if(page!=null&&!page.isEmpty()) {
				return solicitud;
			}
		}
		
		if(contexto.getUsuario().isRol(Constantes.ROLES.EVALUADOR_ADMINISTRATIVO)||contexto.getUsuario().isRol(Constantes.ROLES.EVALUADOR_TECNICO)) {
			Pageable pageable = PageRequest.of(0, Integer.parseInt(env.getProperty("maximo.paginas")));
			Page<Solicitud> page=buscarEvaluador(null, null, solicitud.getNumeroExpediente(), null, null, null, null,null,null, pageable, contexto);
			if(page!=null&&!page.isEmpty()) {
				return solicitud;
			}
		}
		if(contexto.getUsuario().isRol(Constantes.ROLES.APROBADOR_TECNICO)||contexto.getUsuario().isRol(Constantes.ROLES.APROBADOR_ADMINISTRATIVO)) {
			Pageable pageable = PageRequest.of(0, Integer.parseInt(env.getProperty("maximo.paginas")));
			Page<Solicitud> page=buscarAprobador(solicitud.getNumeroExpediente(),null,null,null,null,null,pageable, contexto);
			if(page!=null&&!page.isEmpty()) {
				return solicitud;
			}
		}
		throw new ValidacionException(Constantes.CODIGO_MENSAJE.ACCESO_NO_AUTORIZADO);
	}

	@Override
	public Solicitud obtener(Long idSolicitud, Contexto contexto) {
		Solicitud solicitud = solicitudDao.obtener(idSolicitud);
		List<OtroRequisito> otrosRequisitos = otroRequisitoService
				.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, idSolicitud);
		solicitud.setOtrosRequisitos(otrosRequisitos);
		solicitud.setArchivos(new ArrayList<Archivo>());
		Archivo formato04 = archivoService.obtenerTipoArchivo(solicitud.getIdSolicitud(),
				Constantes.LISTADO.TIPO_ARCHIVO.FORMATO);
		Archivo formato21 = archivoService.obtenerTipoArchivo(solicitud.getIdSolicitudPadre(),
				Constantes.LISTADO.TIPO_ARCHIVO.RESULTADO);
		Archivo formato05 = archivoService.obtenerTipoArchivo(solicitud.getIdSolicitud(),
				Constantes.LISTADO.TIPO_ARCHIVO.SUBSANACION);
		solicitud.getArchivos().add(formato04);
		solicitud.getArchivos().add(formato21);
		solicitud.getArchivos().add(formato05);
		Pageable pageable = PageRequest.of(0, Integer.parseInt(env.getProperty("maximo.paginas")));
		solicitud.setAsignados(asignacionService.buscar(idSolicitud, null, pageable, contexto).getContent());
		return solicitud;
	}

	@Override
	public Solicitud obtenerSinAsignados(Long idSolicitud, Contexto contexto) {
		Solicitud solicitud = solicitudDao.obtener(idSolicitud);
		List<DictamenEvaluacion> montos = dictamenEvaluacionService.listar(idSolicitud, contexto);
		solicitud.setMontosFacturados(montos);
		return solicitud;
	}
	@Override
	public void eliminar(Long idSolicitud, Contexto contexto) {
		solicitudDao.deleteById(idSolicitud);
	}

	public void eliminar(Solicitud solicitud, Contexto contexto) {
		Long idSolicitud = solicitud.getIdSolicitud();
		List<OtroRequisito> otrosRequisito = otroRequisitoService.listarOtroRequisito(idSolicitud);

		for (OtroRequisito requisito : otrosRequisito) {
			otroRequisitoService.eliminar(requisito.getIdOtroRequisito(), contexto);
		}

		List<Estudio> estudios = estudioService.buscar(idSolicitud, contexto);
		for (Estudio estudio : estudios) {
			estudioService.eliminar(estudio.getIdEstudio(), contexto);
		}
		
		List<DictamenEvaluacion> dictamenMontos = dictamenEvaluacionService.listar(idSolicitud, contexto);
		for (DictamenEvaluacion dictamen : dictamenMontos) {
			dictamenEvaluacionService.eliminar(dictamen.getIdDictamenEvaluacion(), contexto);
		}

		List<Documento> documentos = documentoService.buscar(idSolicitud, contexto);

		for (Documento documento : documentos) {
			documentoService.eliminar(documento.getIdDocumento(), contexto);
		}
		archivoService.eliminarXIDSolicitud(idSolicitud);

		solicitudDao.deleteById(idSolicitud);

		if (solicitud.getPersona() != null) {
			personaDao.deleteById(solicitud.getPersona().getIdPersona());
		}
		if (solicitud.getRepresentante() != null) {
			representanteDao.deleteById(solicitud.getRepresentante().getIdRepresentante());
		}

	}

	@Override
	public Page<Solicitud> buscarResponsable(String sFechaDesde, String sSechaHasta, String nroExpediente,
			Long idTipoSolicitud, Long idEstadoSolicitud, String solicitante, Long idEstadoRevision, Pageable pageable,
			Contexto contexto) {
		Date fechaDesde = DateUtil.getEndDay(sFechaDesde);
		Date fechaHasta = DateUtil.getEndDay(sSechaHasta);
		if (StringUtils.isBlank(nroExpediente)) {
			nroExpediente = null;
		} else {
			nroExpediente = "%" + nroExpediente + "%";
		}
		if (StringUtils.isBlank(solicitante)) {
			solicitante = null;
		} else {
			solicitante = "%" + solicitante.toUpperCase() + "%";
		}
		Long idUsuario = null;
		Long idUsuarioResponsable = contexto.getUsuario().getIdUsuario();
		String codigoEstadoBorrador = Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR;
		return solicitudDao.buscarResponsable(fechaDesde, fechaHasta, idTipoSolicitud, idEstadoSolicitud, nroExpediente,
				solicitante, idEstadoRevision, idUsuario, idUsuarioResponsable, codigoEstadoBorrador, pageable);
	}

	@Override
	public Page<Solicitud> buscarEvaluador(String sFechaDesde, String sSechaHasta, String nroExpediente,
			Long idTipoSolicitud, Long idEstadoSolicitud, String solicitante, Long idEstadoRevision,Long idEstadoEvalTecnica, Long idEstadoEvalAdministrativa, Pageable pageable,
			Contexto contexto) {
		Date fechaDesde = DateUtil.getEndDay(sFechaDesde);
		Date fechaHasta = DateUtil.getEndDay(sSechaHasta);
		String codigoEstadoBorrador = Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR;
		Long idUsuario = contexto.getUsuario().getIdUsuario();
		if (StringUtils.isBlank(nroExpediente)) {
			nroExpediente = null;
		} else {
			nroExpediente = "%" + nroExpediente + "%";
		}
		if (StringUtils.isBlank(solicitante)) {
			solicitante = null;
		} else {
			solicitante = "%" + solicitante.toUpperCase() + "%";
		}
		return solicitudDao.buscarEvaluadores(fechaDesde, fechaHasta, idTipoSolicitud, idEstadoSolicitud, nroExpediente,
				solicitante, idEstadoRevision,idEstadoEvalTecnica,idEstadoEvalAdministrativa, idUsuario, codigoEstadoBorrador, pageable);
	}

	@Override
	public Page<Solicitud> buscar(String sFechaDesde, String sSechaHasta, String nroExpediente, Long idTipoSolicitud,
			Long idEstadoSolicitud, String solicitante, Long idEstadoRevision, Pageable pageable, Contexto contexto) {
		Date fechaDesde = DateUtil.getEndDay(sFechaDesde);
		Date fechaHasta = DateUtil.getEndDay(sSechaHasta);
		if (StringUtils.isBlank(nroExpediente)) {
			nroExpediente = null;
		} else {
			nroExpediente = "%" + nroExpediente + "%";
		}
		Long idUsuario = -1L;
		String codigoEstadoBorrador = null;
		if (contexto.getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
			idUsuario = contexto.getUsuario().getIdUsuario();
		} else if (contexto.getUsuario().isRol(Constantes.ROLES.RESPONSABLE_ADMINISTRATIVO)
				|| contexto.getUsuario().isRol(Constantes.ROLES.RESPONSABLE_TECNICO)) {

		} else if (contexto.getUsuario().isRol(Constantes.ROLES.EVALUADOR_ADMINISTRATIVO)
				|| contexto.getUsuario().isRol(Constantes.ROLES.EVALUADOR_TECNICO)) {
			codigoEstadoBorrador = Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR;
			idUsuario = contexto.getUsuario().getIdUsuario();
			return solicitudDao.buscarEvaluadores(fechaDesde, fechaHasta, idTipoSolicitud, idEstadoSolicitud,
					nroExpediente, solicitante, idEstadoRevision,null,null, idUsuario, codigoEstadoBorrador, pageable);
		} else {
			codigoEstadoBorrador = Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR;
			idUsuario = null;
		}

		Page<Solicitud> page = solicitudDao.buscar(fechaDesde, fechaHasta, idTipoSolicitud, idEstadoSolicitud,
				nroExpediente, solicitante, idEstadoRevision, idUsuario, codigoEstadoBorrador, pageable);
		for (Solicitud solicitud : page.getContent()) {
			solicitud.setArchivos(new ArrayList<Archivo>());
			Archivo archivo01 = null;
			Archivo archivo02 = null;
			Archivo archivo03 = null;
			Archivo archivo04 = null;
			if (solicitud.getIdSolicitudPadre() == null || solicitud.getOrigenRegistro() != null) {
				archivo01 = archivoService.obtenerTipoArchivo(solicitud.getIdSolicitud(),
						Constantes.LISTADO.TIPO_ARCHIVO.FORMATO);
				archivo02 = archivoService.obtenerTipoArchivo(solicitud.getIdSolicitud(),
						Constantes.LISTADO.TIPO_ARCHIVO.RESULTADO);
			} else {
				archivo01 = archivoService.obtenerTipoArchivo(solicitud.getIdSolicitudPadre(),
						Constantes.LISTADO.TIPO_ARCHIVO.FORMATO);
				archivo02 = archivoService.obtenerTipoArchivo(solicitud.getIdSolicitudPadre(),
						Constantes.LISTADO.TIPO_ARCHIVO.RESULTADO);
				archivo03 = archivoService.obtenerTipoArchivo(solicitud.getIdSolicitud(),
						Constantes.LISTADO.TIPO_ARCHIVO.SUBSANACION);
				archivo04 = archivoService.obtenerTipoArchivo(solicitud.getIdSolicitud(),
						Constantes.LISTADO.TIPO_ARCHIVO.RESULTADO_SUBSANACION);
			}
			solicitud.getArchivos().add(archivo01);
			solicitud.getArchivos().add(archivo02);
			solicitud.getArchivos().add(archivo03);
			solicitud.getArchivos().add(archivo04);

		}

		return page;
	}

	@Override
	public Page<Solicitud> buscarAprobador(String nroExpediente, String solicitante, Long idTipoSolicitud,
			Long idEstadoRevision, Long idEstadoEvaluacionTecnica, Long idEstadoEvaluacionAdministrativa,
			Pageable pageable, Contexto contexto) {
		String codigoEstadoBorrador = Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR;
		Long idUsuario = contexto.getUsuario().getIdUsuario();
		if (StringUtils.isBlank(nroExpediente)) {
			nroExpediente = null;
		} else {
			nroExpediente = "%" + nroExpediente + "%";
		}
		if (StringUtils.isBlank(solicitante)) {
			solicitante = null;
		} else {
			solicitante = "%" + solicitante.toUpperCase() + "%";
		}
		Page<Solicitud> page = solicitudDao.buscarAprobador(nroExpediente, solicitante, idTipoSolicitud,
				idEstadoRevision, idEstadoEvaluacionTecnica, idEstadoEvaluacionAdministrativa, idUsuario,
				codigoEstadoBorrador, pageable);
		for (Solicitud solicitud : page.getContent()) {
			List<Asignacion> asignados = asignacionService.obtener(solicitud.getIdSolicitud(), idUsuario);
			solicitud.setAsignados(asignados);
		}

		return page;
	}
	
	@Override
	public Page<Contrato> buscarAprobadorContratos(String nroExpediente, String contratista, String idTipoContrato,
			String idTipoAprobacion, Pageable pageable, Contexto contexto) {
		String codigoEstadoBorrador = Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR;
		Long idUsuario = contexto.getUsuario().getIdUsuario();
		if (StringUtils.isBlank(nroExpediente)) {
			nroExpediente = null;
		} else {
			nroExpediente = "%" + nroExpediente + "%";
		}
		if (StringUtils.isBlank(contratista)) {
			contratista = null;
		} else {
			contratista = "%" + contratista.toUpperCase() + "%";
		}
		Page<Contrato> page = contratoDao.obtenerContratosAprobados(nroExpediente, contratista, idTipoContrato,
				idTipoAprobacion, pageable);
		return page;
	}

	private Archivo generarSubsanacionPN(Solicitud solicitud, Contexto contexto) throws Exception {
		Long idSolicitud = solicitud.getIdSolicitud();
		Archivo archivo = new Archivo();
		archivo.setNombre("Subsanacion_PN_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
		archivo.setNombreReal("Subsanacion_PN_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
		archivo.setTipo("application/pdf");
		archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
				Constantes.LISTADO.TIPO_ARCHIVO.SUBSANACION));
		archivo.setIdSolicitud(idSolicitud);		
		
		List<Documento> documentos = documentoService.buscarPresentacion(idSolicitud,  contexto);
		List<OtroRequisito> otrosRequisitos = otroRequisitoService.listarOtroRequisitoArchivoPresentacion(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, idSolicitud, contexto);
		List<Estudio> capacitaciones = estudioService.buscarPresentacion(Constantes.LISTADO.TIPO_ESTUDIO.CAPACITACION, idSolicitud,contexto);
		List<Estudio> academicos = estudioService.buscarPresentacion(Constantes.LISTADO.TIPO_ESTUDIO.ACADEMICOS, idSolicitud,contexto);
		List<OtroRequisito> perfiles = otroRequisitoService.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, idSolicitud);
		
		List<Archivo> archivos = archivoService.buscarPresentacion(idSolicitud,Constantes.LISTADO.TIPO_ARCHIVO.SUBSANACION, contexto);
		removerTerminosCondiciones(otrosRequisitos);
		archivos.add(archivo);

		solicitud.setArchivos(archivos);
		solicitud.setCapacitaciones(capacitaciones);
		solicitud.setPerfiles(perfiles);
		solicitud.setAcademicos(academicos);
		solicitud.setDocumentos(documentos);
		solicitud.setOtrosRequisitos(otrosRequisitos);

		File jrxml = new File(pathJasper + "Formato_06.jrxml");
		File jrxml2 = new File(pathJasper + "Formato_04_06_Academico.jrxml");
		File jrxml3 = new File(pathJasper + "Formato_04_06_Capacitacion.jrxml");
		File jrxml4 = new File(pathJasper + "Formato_04_06_Documento.jrxml");
		File jrxml5 = new File(pathJasper + "Formato_04_06_OtroDocumento.jrxml");
		File jrxml6 = new File(pathJasper + "Formato_04_06_Perfil.jrxml");
		File jrxml7 = new File(pathJasper + "Formato_04_06_Presentados.jrxml");

		JasperReport jasperReport2 = getJasperCompilado(jrxml2);
		JasperReport jasperReport3 = getJasperCompilado(jrxml3);
		JasperReport jasperReport4 = getJasperCompilado(jrxml4);
		JasperReport jasperReport5 = getJasperCompilado(jrxml5);
		JasperReport jasperReport6 = getJasperCompilado(jrxml6);
		JasperReport jasperReport7 = getJasperCompilado(jrxml7);

		Map<String, Object> parameters = new HashMap<String, Object>();
		logger.info("SUBREPORT_DIR: {}", pathJasper);
		parameters.put("SUBREPORT_DIR", pathJasper);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JasperPrint print = null;
		InputStream appLogo = null;
		InputStream osinermingLogo = null;
		try {
			appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
			osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
			parameters.put("P_LOGO_APP", appLogo);
			parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
			List<Solicitud> solicitudes = new ArrayList<Solicitud>();
			solicitudes.add(solicitud);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
			JasperReport jasperReport = getJasperCompilado(jrxml);
			print = JasperFillManager.fillReport(jasperReport, parameters, ds);
			output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, output);

		} catch (Exception e) {
			throw e;

		} finally {
			close(appLogo);
			close(osinermingLogo);
		}

		byte[] bytesSalida = output.toByteArray();
		archivo.setPeso(bytesSalida.length * 1L);
		archivo.setNroFolio(1L);
		archivo.setContenido(bytesSalida);
		return archivo;
	}

	private Archivo generarSubsanacionPJ(Solicitud solicitud, Contexto contexto) throws Exception {
		Long idSolicitud = solicitud.getIdSolicitud();
		Archivo archivo = new Archivo();
		archivo.setNombre("Subsanacion_PJ_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
		archivo.setNombreReal("Subsanacion_PJ_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
		archivo.setTipo("application/pdf");
		archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
				Constantes.LISTADO.TIPO_ARCHIVO.SUBSANACION));
		archivo.setIdSolicitud(idSolicitud);
		
		List<Documento> documentos = documentoService.buscarPresentacion(idSolicitud, contexto);
		List<OtroRequisito> otrosRequisitos = otroRequisitoService.listarOtroRequisitoArchivoPresentacion(
				Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, idSolicitud, contexto);
		
		List<Archivo> archivos = archivoService.buscarPresentacion(idSolicitud,Constantes.LISTADO.TIPO_ARCHIVO.SUBSANACION, contexto);

		solicitud.setArchivos(archivos);
		solicitud.setDocumentos(documentos);
		solicitud.setOtrosRequisitos(otrosRequisitos);
		removerTerminosCondiciones(otrosRequisitos);
		archivos.add(archivo);
		File jrxml = new File(pathJasper + "Formato_05.jrxml");
		File jrxml2 = new File(pathJasper + "Formato_02_05_Documento.jrxml");
		File jrxml3 = new File(pathJasper + "Formato_02_05_OtroRequisito.jrxml");
		File jrxml4 = new File(pathJasper + "Formato_02_05_Presentados.jrxml");

		JasperReport jasperReport2 = getJasperCompilado(jrxml2);
		JasperReport jasperReport3 = getJasperCompilado(jrxml3);
		JasperReport jasperReport4 = getJasperCompilado(jrxml4);

		Map<String, Object> parameters = new HashMap<String, Object>();
		logger.info("SUBREPORT_DIR: {}", pathJasper);
		parameters.put("SUBREPORT_DIR", pathJasper);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JasperPrint print = null;
		InputStream appLogo = null;
		InputStream osinermingLogo = null;
		try {
			appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
			osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
			parameters.put("P_LOGO_APP", appLogo);
			parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
			List<Solicitud> solicitudes = new ArrayList<Solicitud>();
			solicitudes.add(solicitud);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
			JasperReport jasperReport = getJasperCompilado(jrxml);
			print = JasperFillManager.fillReport(jasperReport, parameters, ds);
			output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, output);

		} catch (Exception e) {
			throw e;

		} finally {
			close(appLogo);
			close(osinermingLogo);
		}

		byte[] bytesSalida = output.toByteArray();
		archivo.setPeso(bytesSalida.length * 1L);
		archivo.setNroFolio(1L);
		archivo.setContenido(bytesSalida);
		return archivo;
	}

	private Archivo generarResultadoPJ(Solicitud solicitud, String tipo, Contexto contexto) throws Exception {
		Long idSolicitud = solicitud.getIdSolicitud();
		Archivo archivo = new Archivo();
		if (Constantes.LISTADO.TIPO_ARCHIVO.RESULTADO.equals(tipo)) {
			archivo.setNombre("Respuesta_Solicitud_PJ_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
			archivo.setNombreReal("Respuesta_Solicitud_PJ_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
		} else {
			archivo.setNombre(
					"Respuesta_Solicitud_Subsanacion_PJ_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
			archivo.setNombreReal(
					"Respuesta_Solicitud_Subsanacion_PJ_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
		}
		archivo.setTipo("application/pdf");
		archivo.setTipoArchivo(
				listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO, tipo));
		archivo.setIdSolicitud(idSolicitud);
		List<OtroRequisito> perfiles = otroRequisitoService
				.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, idSolicitud);
		solicitud.setPerfiles(perfiles);

		File jrxml = new File(pathJasper + "Formato_21_PJ.jrxml");
		File jrxml2 = new File(pathJasper + "Formato_21_Perfil_PJ.jrxml");

		JasperReport jasperReport2 = getJasperCompilado(jrxml2);

		Map<String, Object> parameters = new HashMap<String, Object>();
		logger.info("SUBREPORT_DIR: {}", pathJasper);
		parameters.put("SUBREPORT_DIR", pathJasper);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JasperPrint print = null;
		InputStream appLogo = null;
		InputStream osinermingLogo = null;
		try {
			appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
			osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
			parameters.put("P_LOGO_APP", appLogo);
			parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
			List<Solicitud> solicitudes = new ArrayList<Solicitud>();
			solicitudes.add(solicitud);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
			JasperReport jasperReport = getJasperCompilado(jrxml);
			print = JasperFillManager.fillReport(jasperReport, parameters, ds);
			output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, output);

		} catch (Exception e) {
			throw e;

		} finally {
			close(appLogo);
			close(osinermingLogo);
		}

		byte[] bytesSalida = output.toByteArray();
		archivo.setPeso(bytesSalida.length * 1L);
		archivo.setNroFolio(1L);
		archivo.setContenido(bytesSalida);
		return archivo;
	}

	private Archivo generarResultadoPN(Solicitud solicitud, String tipo, Contexto contexto) throws Exception {
		Long idSolicitud = solicitud.getIdSolicitud();
		Archivo archivo = new Archivo();
		if (Constantes.LISTADO.TIPO_ARCHIVO.RESULTADO.equals(tipo)) {
			archivo.setNombre("Respuesta_Solicitud_PN_" + solicitud.getPersona().getNumeroDocumento() + (new Date().getTime()) + ".pdf");
			archivo.setNombreReal("Respuesta_Solicitud_PN_" + solicitud.getPersona().getNumeroDocumento() + (new Date().getTime()) + ".pdf");
		} else {
			archivo.setNombre(
					"Respuesta_Solicitud_Subsanacion_PN_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
			archivo.setNombreReal(
					"Respuesta_Solicitud_Subsanacion_PN_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
		}
		archivo.setTipo("application/pdf");
		archivo.setTipoArchivo(
				listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO, tipo));
		archivo.setIdSolicitud(idSolicitud);
		
		/* --- 04-01-2024 --- INI ---*/
		if (tipo.equals(Constantes.LISTADO.TIPO_ARCHIVO.RESULTADO_SUBSANACION)) {
			List<OtroRequisito> perfiles = otroRequisitoService
					.listarOtroRequisitosPerfilCalifica(idSolicitud);
			solicitud.setPerfiles(perfiles);
		}else {
		List<OtroRequisito> perfiles = otroRequisitoService
				.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, idSolicitud);
		solicitud.setPerfiles(perfiles);
		}
		/* --- 04-01-2024 --- FIN ---*/
		
		File jrxml = new File(pathJasper + "Formato_21_PN.jrxml");
		File jrxml2 = new File(pathJasper + "Formato_21_Perfil_PN.jrxml");

		JasperReport jasperReport2 = getJasperCompilado(jrxml2);

		Map<String, Object> parameters = new HashMap<String, Object>();
		logger.info("SUBREPORT_DIR: {}", pathJasper);
		parameters.put("SUBREPORT_DIR", pathJasper);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JasperPrint print = null;
		InputStream appLogo = null;
		InputStream osinermingLogo = null;
		try {
			appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
			osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
			parameters.put("P_LOGO_APP", appLogo);
			parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
			List<Solicitud> solicitudes = new ArrayList<Solicitud>();
			solicitudes.add(solicitud);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
			JasperReport jasperReport = getJasperCompilado(jrxml);
			print = JasperFillManager.fillReport(jasperReport, parameters, ds);
			output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, output);

		} catch (Exception e) {
			throw e;

		} finally {
			close(appLogo);
			close(osinermingLogo);
		}

		byte[] bytesSalida = output.toByteArray();
		archivo.setPeso(bytesSalida.length * 1L);
		archivo.setNroFolio(1L);
		archivo.setContenido(bytesSalida);
		return archivo;
	}

	@Override
	public Archivo generarReporteSubsanacion(Long idSolicitud, Contexto contexto) throws Exception {
		Solicitud solicitud = obtener(idSolicitud, contexto);
		if (validarJuridicoPostor(solicitud.getSolicitudUuid())) {
			return generarSubsanacionPJ(solicitud, contexto);
		} else {
			return generarSubsanacionPN(solicitud, contexto);
		}
	}

	@Override
	public Archivo generarReporteResultado(Solicitud solicitud, String tipo, Contexto contexto) throws Exception {
		if (validarJuridicoPostor(solicitud.getSolicitudUuid())) {
			return generarResultadoPJ(solicitud, tipo, contexto);
		} else {
			return generarResultadoPN(solicitud, tipo, contexto);
		}
	}

	@Override
	public Archivo generarReporte(Long idSolicitud, Contexto contexto) {
		Solicitud solicitud = obtener(idSolicitud, contexto);
		if (validarJuridicoPostor(solicitud.getSolicitudUuid())) {
			return generarPJ(solicitud, contexto);
		} else {
			return generarPN(solicitud, contexto);
		}
	}

	private void removerTerminosCondiciones(List<OtroRequisito> otrosRequisitos) {
		Iterator<OtroRequisito> itOtroRequisito = otrosRequisitos.iterator();
		while (itOtroRequisito.hasNext()) {
			OtroRequisito otroRequisito = itOtroRequisito.next();

			if (esTerminoCondiciones(otroRequisito)) {
				itOtroRequisito.remove();
			}
		}

	}

	private Archivo generarPJ(Solicitud solicitud, Contexto contexto) {
		Long idSolicitud = solicitud.getIdSolicitud();
		Archivo archivo = new Archivo();
		archivo.setNombre("Solicitud_PJ_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
		archivo.setNombreReal("Solicitud_PJ_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
		archivo.setTipo("application/pdf");
		archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
				Constantes.LISTADO.TIPO_ARCHIVO.FORMATO));

		archivo.setIdSolicitud(idSolicitud);

		List<Documento> documentos = documentoService.buscar(idSolicitud, contexto);
		List<OtroRequisito> otrosRequisitos = otroRequisitoService.listarOtroRequisitoArchivo(
				Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, idSolicitud, contexto);
		List<Estudio> capacitaciones = estudioService.buscar(Constantes.LISTADO.TIPO_ESTUDIO.CAPACITACION, idSolicitud,
				contexto);
		List<Estudio> academicos = estudioService.buscar(Constantes.LISTADO.TIPO_ESTUDIO.ACADEMICOS, idSolicitud,
				contexto);
		List<OtroRequisito> perfiles = otroRequisitoService
				.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, idSolicitud);
		List<Archivo> archivos = archivoService.buscar(idSolicitud, contexto);
		removerTerminosCondiciones(otrosRequisitos);
		archivos.add(archivo);

		solicitud.setArchivos(archivos);
		solicitud.setCapacitaciones(capacitaciones);
		solicitud.setPerfiles(perfiles);
		solicitud.setAcademicos(academicos);
		solicitud.setDocumentos(documentos);
		solicitud.setOtrosRequisitos(otrosRequisitos);
		JasperPrint print = null;
		InputStream appLogo = null;
		InputStream osinermingLogo = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {

			File jrxml = new File(pathJasper + "Formato_02.jrxml");
			File jrxml2 = new File(pathJasper + "Formato_02_05_Presentados.jrxml");
			File jrxml3 = new File(pathJasper + "Formato_02_05_OtroRequisito.jrxml");
			File jrxml4 = new File(pathJasper + "Formato_02_05_Documento.jrxml");

			JasperReport jasperReport2 = getJasperCompilado(jrxml2);
			JasperReport jasperReport3 = getJasperCompilado(jrxml3);
			JasperReport jasperReport4 = getJasperCompilado(jrxml4);

			Map<String, Object> parameters = new HashMap<String, Object>();
			logger.info("SUBREPORT_DIR: {}", pathJasper);
			parameters.put("SUBREPORT_DIR", pathJasper);
			appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
			osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
			parameters.put("P_LOGO_APP", appLogo);
			parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
			List<Solicitud> solicitudes = new ArrayList<Solicitud>();
			solicitudes.add(solicitud);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
			JasperReport jasperReport = getJasperCompilado(jrxml);
			print = JasperFillManager.fillReport(jasperReport, parameters, ds);
			output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, output);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_04, e);

		} finally {
			close(appLogo);
			close(osinermingLogo);
		}

		byte[] bytesSalida = output.toByteArray();
		archivo.setContenido(bytesSalida);
		archivo.setPeso(bytesSalida.length * 1L);
		archivo.setNroFolio(1L);
		return archivo;
	}

	private Archivo generarPN(Solicitud solicitud, Contexto contexto) {
		Long idSolicitud = solicitud.getIdSolicitud();
		Archivo archivo = new Archivo();
		archivo.setNombre("Solicitud_PN_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
		archivo.setNombreReal("Solicitud_PN_" + solicitud.getPersona().getNumeroDocumento() + ".pdf");
		archivo.setTipo("application/pdf");
		archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
				Constantes.LISTADO.TIPO_ARCHIVO.FORMATO));
		archivo.setIdSolicitud(idSolicitud);

		List<Documento> documentos = documentoService.buscar(idSolicitud, contexto);
		List<OtroRequisito> otrosRequisitos = otroRequisitoService.listarOtroRequisitoArchivo(
				Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, idSolicitud, contexto);
		List<Estudio> capacitaciones = estudioService.buscar(Constantes.LISTADO.TIPO_ESTUDIO.CAPACITACION, idSolicitud,
				contexto);
		List<Estudio> academicos = estudioService.buscar(Constantes.LISTADO.TIPO_ESTUDIO.ACADEMICOS, idSolicitud,
				contexto);
		List<OtroRequisito> perfiles = otroRequisitoService
				.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, idSolicitud);
		List<Archivo> archivos = archivoService.buscar(idSolicitud, contexto);
		removerTerminosCondiciones(otrosRequisitos);
		archivos.add(archivo);

		solicitud.setArchivos(archivos);
		solicitud.setCapacitaciones(capacitaciones);
		solicitud.setPerfiles(perfiles);
		solicitud.setAcademicos(academicos);
		solicitud.setDocumentos(documentos);
		solicitud.setOtrosRequisitos(otrosRequisitos);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JasperPrint print = null;
		InputStream appLogo = null;
		InputStream osinermingLogo = null;
		try {

			File jrxml = new File(pathJasper + "Formato_04.jrxml");
			File jrxml2 = new File(pathJasper + "Formato_04_06_Academico.jrxml");
			File jrxml3 = new File(pathJasper + "Formato_04_06_Capacitacion.jrxml");
			File jrxml4 = new File(pathJasper + "Formato_04_06_Documento.jrxml");
			File jrxml5 = new File(pathJasper + "Formato_04_06_OtroDocumento.jrxml");
			File jrxml6 = new File(pathJasper + "Formato_04_06_Perfil.jrxml");
			File jrxml7 = new File(pathJasper + "Formato_04_06_Presentados.jrxml");
			JasperReport jasperReport2 = getJasperCompilado(jrxml2);
			JasperReport jasperReport3 = getJasperCompilado(jrxml3);
			JasperReport jasperReport4 = getJasperCompilado(jrxml4);
			JasperReport jasperReport5 = getJasperCompilado(jrxml5);
			JasperReport jasperReport6 = getJasperCompilado(jrxml6);
			JasperReport jasperReport7 = getJasperCompilado(jrxml7);

			Map<String, Object> parameters = new HashMap<String, Object>();
			logger.info("SUBREPORT_DIR: {}", pathJasper);
			parameters.put("SUBREPORT_DIR", pathJasper);

			appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
			osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
			parameters.put("P_LOGO_APP", appLogo);
			parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
			List<Solicitud> solicitudes = new ArrayList<Solicitud>();
			solicitudes.add(solicitud);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
			JasperReport jasperReport = getJasperCompilado(jrxml);
			print = JasperFillManager.fillReport(jasperReport, parameters, ds);
			output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, output);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_04, e);
		} finally {
			close(appLogo);
			close(osinermingLogo);
		}

		byte[] bytesSalida = output.toByteArray();
		archivo.setPeso(bytesSalida.length * 1L);
		archivo.setNroFolio(1L);
		archivo.setContenido(bytesSalida);
		return archivo;
	}

	public JasperReport getJasperCompilado(File path) throws JRException, FileNotFoundException {
		FileInputStream employeeReportStream = new FileInputStream(path);
		return JasperCompileManager.compileReport(employeeReportStream);
	}

	private void close(InputStream file) {
		try {
			if (file != null)
				file.close();
		} catch (Exception e) {
			logger.error("Error al cerrar el stream del logo", e);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public Solicitud guardarObservacionAdm(Solicitud solicitud, Contexto contexto) {
		Solicitud solicitudBD = this.obtener(solicitud.getSolicitudUuid(), contexto);
		if (contexto.getUsuario().isRol(Constantes.ROLES.EVALUADOR_ADMINISTRATIVO)) {
			solicitudBD.setObservacionAdmnistrativa(solicitud.getObservacionAdmnistrativa());
		}
		AuditoriaUtil.setAuditoriaRegistro(solicitudBD, contexto);
		return solicitudDao.save(solicitudBD);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Solicitud guardarObservacionTec(Solicitud solicitud, Contexto contexto) {
		Solicitud solicitudBD = this.obtener(solicitud.getSolicitudUuid(), contexto);
		if (contexto.getUsuario().isRol(Constantes.ROLES.EVALUADOR_TECNICO)) {
			solicitudBD.setObservacionTecnica(solicitud.getObservacionTecnica());
		}
		AuditoriaUtil.setAuditoriaRegistro(solicitudBD, contexto);
		return solicitudDao.save(solicitudBD);
	}

	@Transactional(rollbackFor = Exception.class)
	public Solicitud guardarResultado(Solicitud solicitud, Contexto contexto) {
		Solicitud solicitudBD = this.obtener(solicitud.getSolicitudUuid(), contexto);
		solicitudBD.setResultadoAdministrativo(solicitud.getResultadoAdministrativo());
		AuditoriaUtil.setAuditoriaRegistro(solicitudBD, contexto);
		return solicitudDao.save(solicitudBD);
	}

	@Override
	public List<pe.gob.osinergmin.sicoes.util.bean.siged.DocumentoOutRO> obtenerExpediente(String solicitudUuid,
			Contexto contexto) {
		Long idSolicitud = this.obtenerId(solicitudUuid);
		Solicitud solicitudBD = solicitudDao.obtener(idSolicitud);
		try {
			return pidoConsumer.listarExpediente(solicitudBD.getNumeroExpediente());
		} catch (Exception e) {
			throw new ValidacionException(e);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public Solicitud finalizarAdministrativo(Solicitud solicitud, Contexto contexto) {
		Solicitud solicitudBD = this.obtener(solicitud.getSolicitudUuid(), contexto);
		validarEvaluacionesAdministrativa(solicitudBD, contexto);
		ListadoDetalle estadoEvaluacion = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.RESULTADO_EVALUACION_TEC_ADM.CODIGO,
				Constantes.LISTADO.RESULTADO_EVALUACION_TEC_ADM.EN_APROBACION);
		solicitudBD.setEstadoEvaluacionAdministrativa(estadoEvaluacion);
		AuditoriaUtil.setAuditoriaRegistro(solicitudBD,contexto);
		solicitudDao.save(solicitudBD);
		List<ListadoDetalle> usuariosAdministrativos = listadoDetalleService
				.buscar(Constantes.LISTADO.APROBADORES_ADMINISTRATIVOS.CODIGO, contexto);
		for (ListadoDetalle usuarioLD : usuariosAdministrativos) {
			ListadoDetalle grupo = listadoDetalleService.buscar(Constantes.LISTADO.GRUPOS.CODIGO, contexto).get(0);
			ListadoDetalle tipo = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_EVALUADOR.CODIGO,
					Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO);
			Usuario usuario = usuarioService.obtener(usuarioLD.getValor());
			Asignacion asignacion = new Asignacion();
			asignacion.setUsuario(usuario);
			asignacion.setSolicitud(solicitudBD);
			asignacion.setTipo(tipo);
			asignacion.setFechaRegistro(new Date());
			asignacion.setGrupo(grupo);
			asignacion.setFlagActivo(Constantes.FLAG.ACTIVO);
			AuditoriaUtil.setAuditoriaRegistro(asignacion,contexto);
			ListadoDetalle plazoConcluir = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO,
					Constantes.LISTADO.PLAZOS.CONCLUIR_EVALUACION_TECNICA);
			asignacion.setNumeroPlazoResp(Long.parseLong(plazoConcluir.getValor()));
			asignacion.setFechaPlazoResp(
					calcularFechaFin(asignacion.getFechaRegistro(), asignacion.getNumeroPlazoResp()));
			ListadoDetalle evaluacion = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO);
			asignacion.setEvaluacion(evaluacion);
			asignacionService.guardar(asignacion);
			notificacionService.enviarMensajeAsignacionAprobacion14(asignacion, contexto);
		}
		String type = "ADMINISTRATIVO";
		notificacionService.enviarMensajeEvaluacionConcluida03(solicitudBD, type, contexto);
		return solicitudBD;
	}

	private void validarEvaluacionesAdministrativa(Solicitud solicitudBD, Contexto contexto) {
		List<OtroRequisito> listOtroRequisito = otroRequisitoService.listarOtroRequisito(
				Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, solicitudBD.getIdSolicitud());
		for (OtroRequisito otroRequisito : listOtroRequisito) {
			if (!esTerminoCondiciones(otroRequisito)) {
				if (Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR
						.equals(otroRequisito.getEvaluacion().getCodigo())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUE_TODO_REQUISITOS);
				}
			}

		}

		if (solicitudBD.getResultadoAdministrativo() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUE_TODO_REQUISITOS);
		}

	}

	@Transactional(rollbackFor = Exception.class)
	public Solicitud finalizarTecnico(Solicitud solicitud, Contexto contexto) {
		Solicitud solicitudBD = this.obtener(solicitud.getSolicitudUuid(), contexto);
		validarEvaluacionesTecnicas(solicitudBD, contexto);
		ListadoDetalle estadoEvaluacion = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.RESULTADO_EVALUACION_TEC_ADM.CODIGO,
				Constantes.LISTADO.RESULTADO_EVALUACION_TEC_ADM.EN_APROBACION);
		solicitudBD.setEstadoEvaluacionTecnica(estadoEvaluacion);
		AuditoriaUtil.setAuditoriaRegistro(solicitudBD,contexto);
		solicitudDao.save(solicitudBD);
		asignacionService.actualizarAsignados(solicitudBD.getIdSolicitud(),
				Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO, 1L, contexto);
		String type = "TECNICO";
		notificacionService.enviarMensajeEvaluacionConcluida03(solicitudBD, type, contexto);
		return solicitudBD;
	}

	private void actualizarRequisitosNulos(String resultado,Solicitud solicitudBD, Contexto contexto) {
		List<Documento> listDocumento = documentoService.buscar(solicitudBD.getIdSolicitud(), contexto);		
		List<Estudio> listCapacitacion = estudioService.buscar(Constantes.LISTADO.TIPO_ESTUDIO.CAPACITACION,solicitudBD.getIdSolicitud(), contexto);
		List<Estudio> listAcademicos = estudioService.buscar(Constantes.LISTADO.TIPO_ESTUDIO.ACADEMICOS,solicitudBD.getIdSolicitud(), contexto);
		ListadoDetalle cumple=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO, Constantes.LISTADO.RESULTADO_EVALUACION.CUMPLE);
		ListadoDetalle observado=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO, Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO);
		ListadoDetalle noCumple=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO, Constantes.LISTADO.RESULTADO_EVALUACION.NO_CUMPLE);
		ListadoDetalle resultadoFinal=null;
		if(Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO.equals(resultado)) {
			resultadoFinal=observado;
		}else if(Constantes.LISTADO.RESULTADO_EVALUACION_ADM.CALIFICA.equals(resultado)) {
			resultadoFinal=cumple;
		}else if(Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO.equals(resultado)) {
			resultadoFinal=noCumple;
		}
		
		for (Documento documento : listDocumento) {
			documento.setEvaluacion(resultadoFinal);
			AuditoriaUtil.setAuditoriaRegistro(documento, contexto);
			documentoDao.save(documento);
		}
		for (Estudio estudio : listCapacitacion) {
			estudio.setEvaluacion(resultadoFinal);
			AuditoriaUtil.setAuditoriaRegistro(estudio, contexto);
			estudioDao.save(estudio);
		}
		for (Estudio estudio : listAcademicos) {
			estudio.setEvaluacion(resultadoFinal);
			AuditoriaUtil.setAuditoriaRegistro(estudio, contexto);
			estudioDao.save(estudio);
		}
		
	}
	private void validarEvaluacionesTecnicas(Solicitud solicitudBD, Contexto contexto) {
		List<OtroRequisito> listOtroRequisito = otroRequisitoService
				.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, solicitudBD.getIdSolicitud());
		for (OtroRequisito otroRequisito : listOtroRequisito) {
			if (otroRequisito.getActividad() != null && otroRequisito.getEvaluacion() != null &&
					Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR.equals(otroRequisito.getEvaluacion().getCodigo())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUE_TODO_REQUISITOS);
			}
			if (otroRequisito.getActividad() != null  && otroRequisito.getFinalizado() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FINALICE_TODO_REQUISITOS);
			}
		}
		/*List<Documento> listDocumento = documentoService.buscar(solicitudBD.getIdSolicitud(), contexto);		
		List<Estudio> listCapacitacion = estudioService.buscar(Constantes.LISTADO.TIPO_ESTUDIO.CAPACITACION,
				solicitudBD.getIdSolicitud(), contexto);
		List<Estudio> listAcademicos = estudioService.buscar(Constantes.LISTADO.TIPO_ESTUDIO.ACADEMICOS,
				solicitudBD.getIdSolicitud(), contexto);

		for (Documento documento : listDocumento) {
			if (documento.getEvaluacion()!=null&&Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR.equals(documento.getEvaluacion().getCodigo())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUE_TODO_REQUISITOS);
			}
		}
		for (Estudio estudio : listCapacitacion) {
			if (estudio.getEvaluacion()!=null&&Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR.equals(estudio.getEvaluacion().getCodigo())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUE_TODO_REQUISITOS);
			}
		}
		for (Estudio estudio : listAcademicos) {
			if (estudio.getEvaluacion()!=null&&Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR.equals(estudio.getEvaluacion().getCodigo())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUE_TODO_REQUISITOS);
			}
		}*/

		Pageable pageable = PageRequest.of(0, Integer.parseInt(env.getProperty("maximo.paginas")));
		Page<Asignacion> aprobadores = asignacionService.buscar(solicitudBD.getIdSolicitud(),
				Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO, pageable, contexto);
		if (aprobadores == null || aprobadores.getContent() == null || aprobadores.getContent().isEmpty()) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.INGRESE_APROBADORES_TECNICOS);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void actualizarAsignado(Asignacion asignacion, Contexto contexto) {
		Solicitud solicitudBD = solicitudDao.obtener(asignacion.getSolicitud().getIdSolicitud());
		
		ListadoDetalle estadoRevisionAsignado = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.ASIGNADO);
		ListadoDetalle estadoRevisionProceso = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.EN_PROCESO);

		if (Constantes.LISTADO.TIPO_EVALUADOR.TECNICO.equals(asignacion.getTipo().getCodigo())) {

//			if (!Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR.equals(solicitudBD.getEstadoRevision().getCodigo())) {
//				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ASIGNACION_YA_ASIGNADA_TECNICO);
//			}
			solicitudBD.setEstadoEvaluacionTecnica(estadoRevisionAsignado);
		} else if (Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO.equals(asignacion.getTipo().getCodigo())) {
			if (!Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR.equals(solicitudBD.getEstadoRevision().getCodigo())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ASIGNACION_YA_ASIGNADA_ADMINISTRATIVO);
			}
			solicitudBD.setEstadoEvaluacionAdministrativa(estadoRevisionAsignado);
		} /*
			 * else
			 * if(Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO.equals(asignacion.
			 * getTipo().getCodigo())) { ListadoDetalle
			 * estadoRevisionEnAprobacion=listadoDetalleService.obtenerListadoDetalle(
			 * Constantes.LISTADO.ESTADO_REVISION.CODIGO,
			 * Constantes.LISTADO.ESTADO_REVISION.EN_APROBACION);
			 * solicitudBD.setEstadoEvaluacionTecnica(estadoRevisionEnAprobacion); } else
			 * if(Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO.equals(
			 * asignacion.getTipo().getCodigo())) { ListadoDetalle
			 * estadoRevisionEnAprobacion=listadoDetalleService.obtenerListadoDetalle(
			 * Constantes.LISTADO.ESTADO_REVISION.CODIGO,
			 * Constantes.LISTADO.ESTADO_REVISION.EN_APROBACION);
			 * solicitudBD.setEstadoEvaluacionAdministrativa(estadoRevisionEnAprobacion); }
			 */
		if (Constantes.LISTADO.TIPO_EVALUADOR.TECNICO.equals(asignacion.getTipo().getCodigo())
				|| Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO.equals(asignacion.getTipo().getCodigo())) {
			if (validarAsignadoProceso(solicitudBD.getEstadoEvaluacionTecnica())
					&& validarAsignadoProceso(solicitudBD.getEstadoEvaluacionAdministrativa())) {

				if (validarEnProceso(solicitudBD.getEstadoEvaluacionTecnica())
						|| validarEnProceso(solicitudBD.getEstadoEvaluacionAdministrativa())) {
					solicitudBD.setEstadoRevision(estadoRevisionProceso);
				} else {
					solicitudBD.setEstadoRevision(estadoRevisionAsignado);
				}
			}
		}

		AuditoriaUtil.setAuditoriaRegistro(solicitudBD, contexto);
		solicitudDao.save(solicitudBD);
	}

	public boolean validarEnProceso(ListadoDetalle estadoEvaluacion) {
		return (!Constantes.LISTADO.ESTADO_REVISION.ASIGNADO.equals(estadoEvaluacion.getCodigo()));
	}

	public boolean validarAsignadoProceso(ListadoDetalle estadoEvaluacion) {
		return !(Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR.equals(estadoEvaluacion.getCodigo()));
	}

	@Transactional(rollbackFor = Exception.class)
	public void actualizarProceso(Solicitud solicitud, String codigoTipo, Contexto contexto) {
		Solicitud solicitudBD = this.obtener(solicitud.getSolicitudUuid(), contexto);
		ListadoDetalle estadoRevision = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.EN_PROCESO);
		if (Constantes.LISTADO.TIPO_EVALUADOR.TECNICO.equals(codigoTipo)) {
			if (Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR
					.equals(solicitudBD.getEstadoEvaluacionTecnica().getCodigo())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ASIGNADA_EVALUACION_TECNICA);
			}
			solicitudBD.setEstadoEvaluacionTecnica(estadoRevision);
			AuditoriaUtil.setAuditoriaRegistro(solicitudBD,contexto);
			solicitudDao.save(solicitudBD);
		} else if (Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO.equals(codigoTipo)) {
			if (Constantes.LISTADO.ESTADO_REVISION.POR_ASIGNAR
					.equals(solicitudBD.getEstadoEvaluacionAdministrativa().getCodigo())) {
				throw new ValidacionException(
						Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ASIGNADA_EVALUACION_ADMINISTRATIVA);
			}
			solicitudBD.setEstadoEvaluacionAdministrativa(estadoRevision);
			AuditoriaUtil.setAuditoriaRegistro(solicitudBD,contexto);
			solicitudDao.save(solicitudBD);
		}
		if (Constantes.LISTADO.ESTADO_REVISION.ASIGNADO.equals(solicitudBD.getEstadoRevision().getCodigo())) {
			solicitudBD.setEstadoRevision(estadoRevision);
			AuditoriaUtil.setAuditoriaRegistro(solicitudBD,contexto);
			solicitudDao.save(solicitudBD);
		}
	}

	public Solicitud guardar(Solicitud solicitud) {
		AuditoriaUtil.setAuditoriaRegistro(solicitud,AuditoriaUtil.getContextoJob());
		return solicitudDao.save(solicitud);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void finalizarRevision(Long idSolicitud, String codigoTipo, Contexto contexto) {
		ListadoDetalle resultadoAdmTec = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.RESULTADO_EVALUACION_TEC_ADM.CODIGO,
				Constantes.LISTADO.RESULTADO_EVALUACION_TEC_ADM.CONCLUIDO);
		ListadoDetalle resultadoRevision = null;
		ListadoDetalle estadoSolicitud = null;
		Archivo resultado = null;
		Solicitud solicitudBD = solicitudDao.obtener(idSolicitud);
		if (Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO.equals(codigoTipo)) {
			solicitudBD.setEstadoEvaluacionAdministrativa(resultadoAdmTec);
		} else if (Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO.equals(codigoTipo)) {
			solicitudBD.setEstadoEvaluacionTecnica(resultadoAdmTec);
		}
		if (Constantes.LISTADO.RESULTADO_EVALUACION_TEC_ADM.CONCLUIDO.equals(solicitudBD.getEstadoEvaluacionTecnica().getCodigo())
				&& Constantes.LISTADO.RESULTADO_EVALUACION_TEC_ADM.CONCLUIDO.equals(solicitudBD.getEstadoEvaluacionAdministrativa().getCodigo())) {
			
			String resultadoSol=actualizarObservada(solicitudBD,contexto);
			actualizarRequisitosNulos(resultadoSol,solicitudBD,contexto);
			boolean solicitudObservada = Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO.equals(resultadoSol);
			
			copiarSupervisora(solicitudBD, contexto);
			solicitudBD.setFlagArchivamiento(Constantes.FLAG.INACTIVO);
			solicitudBD.setFlagRespuesta(Constantes.FLAG.INACTIVO);
			if (solicitudObservada) {

				resultadoRevision = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.OBSERVADO);
				estadoSolicitud = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.OBSERVADO);
				solicitudBD.setFlagActivo(Constantes.FLAG.INACTIVO);
				solicitudBD.setEstado(estadoSolicitud);
				solicitudBD.setEstadoRevision(resultadoRevision);
				Solicitud solNueva = clonarSolicitud(solicitudBD, contexto);
				

				/* --- 04-01-2024 --- INI ---*/ 
				Asignacion asiNueva = asignacionService.clonarAsignacion(solicitudBD,solNueva, contexto);
				logger.info("DATOS: {}", solNueva,asiNueva);
				/* --- 04-01-2024 --- FIN ---*/
				
			} else {
				resultadoRevision = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.CONCLUIDO);
				estadoSolicitud = listadoDetalleService.obtenerListadoDetalle(
						Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO);
				solicitudBD.setEstado(estadoSolicitud);
				solicitudBD.setEstadoRevision(resultadoRevision);
			}

			String tipo = Constantes.LISTADO.TIPO_ARCHIVO.RESULTADO_SUBSANACION;
			
			if (solicitudBD.getIdSolicitudPadre() == null) {
				tipo = Constantes.LISTADO.TIPO_ARCHIVO.RESULTADO;
			}

			try {
				resultado = archivoService.obtenerTipoArchivo(solicitudBD.getIdSolicitud(), tipo);
				if (resultado == null) {
					resultado = generarReporteResultado(solicitudBD, tipo, contexto);
					archivoService.guardar(resultado, contexto);
				}
			} catch (Exception e) {
				logger.error("ERROR S-----", e.getMessage(), e);
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO);
			}
			ExpedienteInRO expedienteInRO = crearExpedienteAgregarDocumentos(solicitudBD, contexto);
			List<File> archivosAlfresco = new ArrayList<File>();
			File file = null;
			try {
				File dir = new File(pathTemporal + File.separator +"temporales"+ File.separator+ idSolicitud);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				file = new File(pathTemporal + File.separator +"temporales"+ File.separator+ idSolicitud + File.separator + resultado.getNombre());
				FileUtils.writeByteArrayToFile(file, resultado.getContenido());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO);
			}
			archivosAlfresco.add(file);
			try {
				DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
				if(documentoOutRO.getResultCode()!=1) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO,documentoOutRO.getMessage());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				if (e instanceof ValidacionException) {
					throw (ValidacionException) e;
				}
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO);
			}
			AuditoriaUtil.setAuditoriaRegistro(solicitudBD,contexto);
			Solicitud sol = solicitudDao.save(solicitudBD);
			if (solicitudBD.getPersona().getTipoPersona().getCodigo()
					.equals(Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO)) {
				if (solicitudBD.getIdSolicitudPadre() == null) {
					notificacionService.enviarMensajeSolicitudResultadoExtranjera17(solicitudBD, resultado, contexto);
				} else {
					notificacionService.enviarMensajeSolicitudSubsanacionResultadoExtranjera18(solicitudBD, resultado,
							contexto);
				}
			}
		} else {
			AuditoriaUtil.setAuditoriaRegistro(solicitudBD,contexto);
			Solicitud sol = solicitudDao.save(solicitudBD);
		}

		logger.info(solicitudBD);

	}

	public void copiar(Long idSolicitud, Contexto contexto) {
		Solicitud solicitud = obtener(idSolicitud, contexto);
		copiarSupervisora(solicitud, contexto);
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void copiarSupervisora(Solicitud solicitudBD, Contexto contexto) {

		ListadoDetalle tipoPersona = solicitudBD.getPersona().getTipoPersona();

		Supervisora supervisora = supervisoraService.obtenerXCodigo(solicitudBD.getPersona().getCodigoCliente(),
				contexto);
		SupervisoraRepresentante representante = null;
		if (supervisora == null) {
			supervisora = CloneUtil.clonarSupervisora(solicitudBD.getPersona(), contexto);
			if (solicitudBD.getRepresentante() != null) {
				representante = CloneUtil.clonarSupervisoraRepresentante(solicitudBD.getRepresentante(), contexto);
			}
		} else {
			supervisora = CloneUtil.actualizarSupervisora(solicitudBD.getPersona(), supervisora, contexto);
			representante = supervisoraRepresentanteService.obtenerXIdSupervisora(supervisora.getIdSupervisora(),
					contexto);
			if (solicitudBD.getRepresentante() != null) {
				representante = CloneUtil.clonarSupervisoraRepresentante(solicitudBD.getRepresentante(), representante,
						contexto);
			}
		}
		List<OtroRequisito> perfilesBD = otroRequisitoService
				.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, solicitudBD.getIdSolicitud());
		boolean hayPerfiles = false;
		for (OtroRequisito otroRequisito : perfilesBD) {
			if (Constantes.LISTADO.RESULTADO_EVALUACION_ADM.CALIFICA.equals(otroRequisito.getResultado().getCodigo())) {
				hayPerfiles = true;
				break;
			}
		}
		if (!hayPerfiles) {
			return;
		}
		supervisora.setFechaIngreso(new Date());
		supervisora.setTipoPersona(tipoPersona);
		supervisora.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUPERVISORA.CODIGO,
				Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE));
		supervisora.setNumeroExpediente(solicitudBD.getNumeroExpediente());
		supervisora = supervisoraService.guardar(supervisora, contexto);
		if (representante != null) {
			representante.setIdSupervisora(supervisora.getIdSupervisora());
			supervisoraRepresentanteService.guardar(representante, contexto);
		}

		List<SupervisoraPerfil> supervisoraPerfiles = supervisoraPerfilService.buscar(supervisora.getIdSupervisora(),
				contexto);

		for (OtroRequisito otroRequisito : perfilesBD) {
			if (Constantes.LISTADO.RESULTADO_EVALUACION_ADM.CALIFICA.equals(otroRequisito.getResultado().getCodigo())) {
				SupervisoraPerfil supervisoraPerfilBD = null;
				for (SupervisoraPerfil supervisoraPerfil : supervisoraPerfiles) {

					if (validarJuridicoPostor(solicitudBD.getSolicitudUuid())){
						if (otroRequisito.getSector().getIdListadoDetalle() == supervisoraPerfil.getSector()
								.getIdListadoDetalle()) {
							if (otroRequisito.getSubsector().getIdListadoDetalle() == supervisoraPerfil.getSubsector()
									.getIdListadoDetalle()) {
								supervisoraPerfilBD = supervisoraPerfil;
								break;
							}
						}
					} else {
						if (otroRequisito.getSector().getIdListadoDetalle() == supervisoraPerfil.getSector()
								.getIdListadoDetalle()) {
							if (otroRequisito.getSubsector().getIdListadoDetalle() == supervisoraPerfil.getSubsector()
									.getIdListadoDetalle()) {
								if (otroRequisito.getActividad().getIdListadoDetalle() == supervisoraPerfil
										.getActividad().getIdListadoDetalle()) {
									if (otroRequisito.getUnidad().getIdListadoDetalle() == supervisoraPerfil.getUnidad()
											.getIdListadoDetalle()) {
										if (otroRequisito.getSubCategoria().getIdListadoDetalle() == supervisoraPerfil
												.getSubCategoria().getIdListadoDetalle()) {
											if (otroRequisito.getPerfil().getIdListadoDetalle() == supervisoraPerfil
													.getPerfil().getIdListadoDetalle()) {
												supervisoraPerfilBD = supervisoraPerfil;
												break;
											}
										}
									}
								}
							}
						}
					}
				}

				if (supervisoraPerfilBD == null) {
					ListadoDetalle estadoActivo =listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.ACTIVO);
					supervisoraPerfilBD = CloneUtil.clonarSupervisoraPerfil(otroRequisito, supervisora,estadoActivo, contexto);
				}
				supervisoraPerfilBD.setFechaIngreso(new Date());
				supervisoraPerfilBD.setNumeroExpediente(solicitudBD.getNumeroExpediente());
				supervisoraPerfilService.guardar(supervisoraPerfilBD, contexto);
				
				if(documentoService.validarJuridicoPostor(solicitudBD.getPersona().getTipoPersona())) {
					
					DictamenEvaluacion dictamenEvaluacion = dictamenEvaluacionService.obtenerXSol(solicitudBD.getIdSolicitud(), supervisoraPerfilBD.getSector().getIdListadoDetalle(), contexto);
					SupervisoraDictamen supervisoraDictamenBD=supervisoraDictamenService.obtenerXSupervisora(supervisora.getIdSupervisora(), supervisoraPerfilBD.getSector().getIdListadoDetalle(), contexto);
					if(supervisoraDictamenBD==null) {
						SupervisoraDictamen supervisoraDictamen=new SupervisoraDictamen();
						supervisoraDictamen.setSector(dictamenEvaluacion.getSector());
						supervisoraDictamen.setMontoFacturado(dictamenEvaluacion.getMontoFacturado());
						supervisoraDictamen.setSupervisora(supervisoraPerfilBD.getSupervisora());
						supervisoraDictamenService.guardar(supervisoraDictamen, contexto);
					}else {
						supervisoraDictamenBD.setMontoFacturado(dictamenEvaluacion.getMontoFacturado());
						supervisoraDictamenService.guardar(supervisoraDictamenBD, contexto);
					}
				}
			}

		}

	}

	@Override
	public Solicitud clonarSolicitud(Long id, Contexto contexto) {
		Solicitud solicitudBD = solicitudDao.obtener(id);
		return clonarSolicitud(solicitudBD, contexto);
	}

	private Solicitud clonarSolicitud(Solicitud solicitudBD, Contexto contexto) {
		Solicitud solicitudNueva = new Solicitud();
		solicitudNueva.setNumeroExpediente(solicitudBD.getNumeroExpediente());

		if (solicitudBD.getRepresentante() != null) {
			Representante representante = CloneUtil.clonarRepresentante(solicitudBD.getRepresentante(), contexto);
			AuditoriaUtil.setAuditoriaRegistro(representante, contexto);
			Representante representanteNuevo = representanteDao.save(representante);
			solicitudNueva.setRepresentante(representanteNuevo);
		}
		if (solicitudBD.getPersona() != null) {
			Persona persona = CloneUtil.clonarPersona(solicitudBD.getPersona(), contexto);
			AuditoriaUtil.setAuditoriaRegistro(persona, contexto);
			Persona personaNueva = personaDao.save(persona);
			solicitudNueva.setPersona(personaNueva);
		}
		if (solicitudBD.getUsuario() != null) {
			Usuario usuario = CloneUtil.clonarUsuario(solicitudBD.getUsuario(), contexto);
			solicitudNueva.setUsuario(usuario);
		}
		solicitudNueva.setUsuario(solicitudBD.getUsuario());
		/* --- 04-01-2024 --- INI ---*/
		solicitudNueva.setResultadoAdministrativo(solicitudBD.getResultadoAdministrativo());
//		if (!Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO.equals(solicitudBD.getResultadoAdministrativo().getCodigo())) {
//			
//			solicitudNueva.setResultadoAdministrativo(solicitudBD.getResultadoAdministrativo());
//		}
		/* --- 04-01-2024 --- FIN ---*/
		solicitudNueva.setEstadoRevision(solicitudBD.getEstadoRevision());
		solicitudNueva.setEstadoEvaluacionAdministrativa(solicitudBD.getEstadoEvaluacionAdministrativa());
		solicitudNueva.setEstadoEvaluacionTecnica(solicitudBD.getEstadoEvaluacionTecnica());
		solicitudNueva.setResultadoTecnico(solicitudBD.getResultadoTecnico());
		solicitudNueva.setTipoSolicitud(solicitudBD.getTipoSolicitud());
		solicitudNueva.setEstado(solicitudBD.getEstado());
		solicitudNueva.setNumeroPlazoResp(solicitudBD.getNumeroPlazoResp());
		solicitudNueva.setNumeroPlazoAsig(solicitudBD.getNumeroPlazoAsig());
		solicitudNueva.setNumeroPlazoSub(solicitudBD.getNumeroPlazoSub());
//		solicitudNueva.setObservacionAdmnistrativa(solicitudBD.getObservacionAdmnistrativa());
//		solicitudNueva.setObservacionTecnica(solicitudBD.getObservacionTecnica());
		solicitudNueva.setObservacionNoCalifica(solicitudBD.getObservacionNoCalifica());
		solicitudNueva.setCodigoConsentimiento(solicitudBD.getCodigoConsentimiento());
		solicitudNueva.setFechaRegistro(solicitudBD.getFechaRegistro());
		solicitudNueva.setFechaPresentacion(solicitudBD.getFechaPresentacion());
		solicitudNueva.setFechaPlazoResp(solicitudBD.getFechaPlazoResp());
		solicitudNueva.setFechaPlazoAsig(solicitudBD.getFechaPlazoAsig());
//		solicitudNueva.setFechaPlazoSub(solicitudBD.getFechaPlazoSub());
		solicitudNueva.setFechaArchivamiento(solicitudBD.getFechaArchivamiento());
		solicitudNueva.setFlagActivo(Constantes.FLAG.ACTIVO);
		solicitudNueva.setSolicitudUuid(UUID.randomUUID().toString());
		solicitudNueva.setIdSolicitudPadre(solicitudBD.getIdSolicitud());
		solicitudNueva.setSolicitudUuidPadre(solicitudBD.getSolicitudUuid());
		solicitudNueva.setDivision(solicitudBD.getDivision());//afc1
		solicitudNueva.setProfesion(solicitudBD.getProfesion());//afc1
		AuditoriaUtil.setAuditoriaRegistro(solicitudNueva, contexto);
		Solicitud sol = solicitudDao.save(solicitudNueva);

		//Actualizar el idSolicitud en el Representante
		if (sol.getRepresentante() != null) {
			Representante representante = sol.getRepresentante();
			representante.setIdSolicitud(sol.getIdSolicitud());
			representanteService.guardar(representante, contexto);
		}

		ListadoDetalle estadoPorEvaluar = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO,Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR);

		List<OtroRequisito> otrosRequisito = otroRequisitoService.listarOtroRequisito(solicitudBD.getIdSolicitud());
		HashMap<Long, OtroRequisito> mapTodosSectores=new HashMap<Long, OtroRequisito>();
		HashMap<Long, OtroRequisito> mapSectorCumple=new HashMap<Long, OtroRequisito>();
		
		for (OtroRequisito requisito : otrosRequisito) {
			List<Archivo> archivosEstudio = archivoService.buscar(null, null, requisito.getIdOtroRequisito(), contexto);
			OtroRequisito otroRequisitoNuevo = CloneUtil.clonarOtroRequisito(requisito, sol, estadoPorEvaluar, contexto);
			if(esTerminoCondiciones(requisito)) {
				otroRequisitoNuevo.setFlagActivo(Constantes.FLAG.INACTIVO.toString());
			}
			Date fechaHoy = new Date();
			otroRequisitoNuevo.setFecActualizacion(fechaHoy);//afc1
			AuditoriaUtil.setAuditoriaRegistro(otroRequisitoNuevo, contexto);
			OtroRequisito otroRequisitoBD = otroRequisitoDao.save(otroRequisitoNuevo);
			if(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL.equals(requisito.getTipo().getCodigo())) {
				mapTodosSectores.put(requisito.getSector().getIdListadoDetalle(), requisito);
			}
			if(Constantes.LISTADO.RESULTADO_EVALUACION_ADM.CALIFICA.equals(requisito.getEvaluacion().getCodigo())) {
				if(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL.equals(requisito.getTipo().getCodigo())) {
					mapSectorCumple.put(requisito.getSector().getIdListadoDetalle(), requisito);
				}
			}
			for (Archivo archivo : archivosEstudio) {
				Archivo archivoNuevo = CloneUtil.clonarArchivo(archivo, sol, contexto);
				archivoNuevo.setIdOtroRequisito(otroRequisitoBD.getIdOtroRequisito());
				AuditoriaUtil.setAuditoriaRegistro(archivoNuevo, contexto);
				archivoDao.save(archivoNuevo);
			}
		}
		
		if(validarJuridicoPostor(solicitudBD.getSolicitudUuid())) {
			Iterator<Long> keyIT=mapTodosSectores.keySet().iterator();
			while(keyIT.hasNext()) {
				Long key=keyIT.next();
				DictamenEvaluacion dictamen = dictamenEvaluacionService.obtenerXSolicitud(solicitudBD.getIdSolicitud(), key);
				DictamenEvaluacion dictamenNuevo = CloneUtil.clonarDictamen(dictamen, sol);
				if(mapSectorCumple.get(key) == null) {
					dictamenNuevo.setMontoFacturado(null);
				}
				dictamenEvaluacionService.guardarDictamen(dictamenNuevo, contexto);
			}
			
		}
		List<Estudio> estudios = estudioService.buscar(solicitudBD.getIdSolicitud(), contexto);
		for (Estudio estudio : estudios) {
			List<Archivo> archivosEstudio = archivoService.buscar(estudio.getIdEstudio(), null, null, contexto);
			Estudio estudioNuevo = CloneUtil.clonarEstudio(estudio, sol, contexto);
			AuditoriaUtil.setAuditoriaRegistro(estudioNuevo, contexto);
			Estudio estudioBD = estudioDao.save(estudioNuevo);
			for (Archivo archivo : archivosEstudio) {
				Archivo archivoNuevo = CloneUtil.clonarArchivo(archivo, sol, contexto);
				archivoNuevo.setIdEstudio(estudioBD.getIdEstudio());
				AuditoriaUtil.setAuditoriaRegistro(archivoNuevo, contexto);
				archivoDao.save(archivoNuevo);
			}
		}

		List<Documento> documentos = documentoService.buscar(solicitudBD.getIdSolicitud(), contexto);
		for (Documento documento : documentos) {
			List<Archivo> archivosDoc = archivoService.buscar(null, documento.getIdDocumento(), null, contexto);
			Documento documentoNuevo = CloneUtil.clonarDocumento(documento, sol, contexto);
			AuditoriaUtil.setAuditoriaRegistro(documentoNuevo, contexto);
			Documento documentoBD = documentoDao.save(documentoNuevo);
			for (Archivo archivo : archivosDoc) {
				Archivo archivoNuevo = CloneUtil.clonarArchivo(archivo, sol, contexto);
				archivoNuevo.setIdDocumento(documentoBD.getIdDocumento());
				AuditoriaUtil.setAuditoriaRegistro(archivoNuevo, contexto);
				archivoDao.save(archivoNuevo);
			}
		}

		List<Archivo> listEvidencia = archivoService.listarEvidencias(solicitudBD.getIdSolicitud());
		for (Archivo archivo : listEvidencia) {

			Archivo archivoNuevo = CloneUtil.clonarArchivo(archivo, sol, contexto);
			AuditoriaUtil.setAuditoriaRegistro(archivoNuevo, contexto);
			archivoDao.save(archivoNuevo);
		}

		return sol;
	}

	private String actualizarObservada(Solicitud solicitud,Contexto contexto) {
		String observado = null;
		String noCalifica = null;
		List<OtroRequisito> otroRequisitos = otroRequisitoService
				.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, solicitud.getIdSolicitud());
		if (Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO
				.equals(solicitud.getResultadoAdministrativo().getCodigo())) {
			for (OtroRequisito otroRequisito : otroRequisitos) {
				if (otroRequisito.getEvaluacion() != null && Constantes.LISTADO.RESULTADO_EVALUACION_ADM.NO_CALIFICA
						.equals(otroRequisito.getEvaluacion().getCodigo())) {
					otroRequisito.setResultado(listadoDetalleService.obtenerListadoDetalle(
							Constantes.LISTADO.RESULTADO_EVALUACION_ADM.CODIGO,
							Constantes.LISTADO.RESULTADO_EVALUACION_ADM.NO_CALIFICA));
				} else {
					otroRequisito.setResultado(listadoDetalleService.obtenerListadoDetalle(
							Constantes.LISTADO.RESULTADO_EVALUACION_ADM.CODIGO,
							Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO));
					observado = Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO;
				}
				AuditoriaUtil.setAuditoriaRegistro(otroRequisito, contexto);
				otroRequisitoDao.save(otroRequisito);
			}
			if(observado!=null) {
				return observado;
			}else {
				return Constantes.LISTADO.RESULTADO_EVALUACION_ADM.NO_CALIFICA;
			}

		} else if (Constantes.LISTADO.RESULTADO_EVALUACION_ADM.NO_CALIFICA
				.equals(solicitud.getResultadoAdministrativo().getCodigo())) {
			for (OtroRequisito otroRequisito : otroRequisitos) {
				otroRequisito.setResultado(
						listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION_ADM.CODIGO,
								Constantes.LISTADO.RESULTADO_EVALUACION_ADM.NO_CALIFICA));
				AuditoriaUtil.setAuditoriaRegistro(otroRequisito, contexto);
				otroRequisitoDao.save(otroRequisito);
			}
			return Constantes.LISTADO.RESULTADO_EVALUACION_ADM.NO_CALIFICA;
		} else {
			for (OtroRequisito otroRequisito : otroRequisitos) {
				otroRequisito.setResultado(otroRequisito.getEvaluacion());
				if (otroRequisito.getEvaluacion() != null && Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO
						.equals(otroRequisito.getEvaluacion().getCodigo())) {
					observado = Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO;
				}else if (otroRequisito.getEvaluacion() != null && Constantes.LISTADO.RESULTADO_EVALUACION_ADM.NO_CALIFICA
						.equals(otroRequisito.getEvaluacion().getCodigo())) {
					noCalifica = Constantes.LISTADO.RESULTADO_EVALUACION_ADM.NO_CALIFICA;
				}
				AuditoriaUtil.setAuditoriaRegistro(otroRequisito, contexto);
				otroRequisitoDao.save(otroRequisito);
			}
		}
		
		if(observado!=null) return observado;
		if(noCalifica!=null) return noCalifica;
		return Constantes.LISTADO.RESULTADO_EVALUACION_ADM.CALIFICA;
	}

	@Override
	public void regresarProceso(Solicitud solicitud, String codigoTipo, Contexto contexto) {
		Solicitud solicitudBD = solicitudDao.obtener(solicitud.getIdSolicitud());
		ListadoDetalle estadoRevision = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.EN_PROCESO);
		if (Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO.equals(codigoTipo)) {
			solicitudBD.setEstadoEvaluacionTecnica(estadoRevision);
			AuditoriaUtil.setAuditoriaRegistro(solicitudBD,contexto);
			solicitudDao.save(solicitudBD);
			if(solicitudBD.getIdSolicitudPadre() == null) {
				List<OtroRequisito> otroRequisitos = otroRequisitoService.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, solicitud.getIdSolicitud());
				for(OtroRequisito otroRequisito:otroRequisitos) {
					otroRequisito.setFinalizado(null);
					AuditoriaUtil.setAuditoriaRegistro(otroRequisito,contexto);
					otroRequisitoDao.save(otroRequisito);
				}	
			}else {
				List<OtroRequisito> otroRequisitosPadreObs = otroRequisitoService.listarOtroRequisitoObservado(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, solicitud.getIdSolicitud());
				for(OtroRequisito otroRequisito:otroRequisitosPadreObs) {
					otroRequisito.setEvaluacion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO,Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR));
					otroRequisito.setFinalizado(null);
					AuditoriaUtil.setAuditoriaRegistro(otroRequisito,contexto);
					otroRequisitoDao.save(otroRequisito);
				}		
			}
			List<DictamenEvaluacion> dictamenMontos = dictamenEvaluacionService.listar(solicitud.getIdSolicitud(), contexto);
			for(DictamenEvaluacion dictamen:dictamenMontos) {
				dictamen.setMontoFacturado(null);
				dictamenEvaluacionService.guardarDictamen(dictamen, contexto);
			}	
		} else if (Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO.equals(codigoTipo)) {
			solicitudBD.setEstadoEvaluacionAdministrativa(estadoRevision);
			if(solicitudBD.getIdSolicitudPadre() != null) {
				Solicitud solicitudBDPadre = solicitudDao.obtenerPadre(solicitudBD.getIdSolicitudPadre());
				if (Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO.equals(solicitudBDPadre.getResultadoAdministrativo().getCodigo())) {
					solicitudBD.setResultadoAdministrativo(null);
				}
			}
			AuditoriaUtil.setAuditoriaRegistro(solicitudBD,contexto);
			solicitudDao.save(solicitudBD);
		}
		
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void marcarSolicitudRespuesta(Long idSolicitud,Date fecha, Contexto contexto) {
		Solicitud solicitudBD = solicitudDao.obtener(idSolicitud);
		solicitudBD.setFlagRespuesta(Constantes.FLAG.ACTIVO);
		AuditoriaUtil.setAuditoriaRegistro(solicitudBD,contexto);
		
		ListadoDetalle plazoSubsanar = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.SUBSANAR_OBSERVACIONES);
		solicitudBD.setNumeroPlazoSub(Long.parseLong(plazoSubsanar.getValor()));
		solicitudBD.setFechaPlazoSub(calcularFechaFin(fecha, solicitudBD.getNumeroPlazoSub()));
		solicitudDao.save(solicitudBD);
		
		if(Constantes.LISTADO.ESTADO_SOLICITUD.OBSERVADO.equals(solicitudBD.getEstado().getCodigo()) ) {
			Solicitud solHija=solicitudDao.obtenerIdSolicitudPadre(idSolicitud);
			if(solHija!=null) {
				solHija.setNumeroPlazoSub(solicitudBD.getNumeroPlazoSub());
				solHija.setFechaPlazoSub(solicitudBD.getFechaPlazoSub());
				AuditoriaUtil.setAuditoriaRegistro(solHija,contexto);
				solicitudDao.save(solHija);
				
			}
		}

		
		if (Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO.equals(solicitudBD.getEstado().getCodigo())) {
			BaseOutRO baseOutRO = sigedApiConsumer.archivarExpediente(solicitudBD.getNumeroExpediente(),
					env.getProperty("mensaje.archivamiento"));
			if (baseOutRO.getErrorCode() != 1) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_ARCHIVAR_EXPEDIENTE,
						baseOutRO.getMessage());
			}
		}
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void marcarSolicitudArchivado(Long idSolicitud, Contexto contexto) {
		Solicitud solicitudBD = solicitudDao.obtener(idSolicitud);
		solicitudBD.setFlagArchivamiento(Constantes.FLAG.ACTIVO);
		AuditoriaUtil.setAuditoriaRegistro(solicitudBD,contexto);
		solicitudDao.save(solicitudBD);
		BaseOutRO baseOutRO = sigedApiConsumer.archivarExpediente(solicitudBD.getNumeroExpediente(),
				env.getProperty("mensaje.archivamiento"));
		if (baseOutRO.getErrorCode() != 1) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_ARCHIVAR_EXPEDIENTE,
					baseOutRO.getMessage());
		}

	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void archivarSolicitud() {
		logger.info("archivarSolicitud inicio");
		List<Solicitud> listSolicitud = solicitudDao.listarSolicitudesXArchivar();
		Iterator<Solicitud> itSolicitud = listSolicitud.iterator();
		while (itSolicitud.hasNext()) {
			Solicitud solicitud = itSolicitud.next();
			Solicitud solicitudPadre = solicitudDao.obtener(solicitud.getIdSolicitudPadre());
			solicitudPadre.setFlagActivo(Constantes.FLAG.ACTIVO);
			solicitudPadre.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.ARCHIVADO));
			solicitudPadre.setEstadoRevision(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.ARCHIVADO));
			solicitudPadre.setFechaArchivamiento(new Date());
			AuditoriaUtil.setAuditoriaRegistro(solicitudPadre,AuditoriaUtil.getContextoJob());
			solicitudDao.save(solicitudPadre);
			
			Solicitud solicitudHija = solicitudDao.obtener(solicitud.getIdSolicitud());
			solicitudHija.setFlagActivo(Constantes.FLAG.INACTIVO);
			solicitudHija.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.ARCHIVADO));
			solicitudHija.setEstadoRevision(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.ARCHIVADO));
			solicitudHija.setFechaArchivamiento(new Date());
			AuditoriaUtil.setAuditoriaRegistro(solicitudHija,AuditoriaUtil.getContextoJob());
			solicitudDao.save(solicitudHija);
//			eliminar(solicitud, AuditoriaUtil.getContextoJob());
		}
		logger.info("archivarSolicitud fin");
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void registrarNotificacionSolicitud(Contexto contexto) throws Exception {
		
		logger.info("registrarNotificacionSolicitud inicio");
		List<Solicitud> listaSolicitud = solicitudDao.listarSolicitudesXNotificar();
		Iterator<Solicitud> itSolicitud = listaSolicitud.iterator();

		while (itSolicitud.hasNext()) {
			Solicitud solicitud = itSolicitud.next();
			String token = sneApiConsumer.obtenerTokenAcceso2();
			
            try {
				NotificacionBeanDTO notificacionBeanDTO = new NotificacionBeanDTO();
				notificacionBeanDTO.setIdClienteSiged(null);
				notificacionBeanDTO.setIdUnidadOperativa(null);
				notificacionBeanDTO.setExpedienteSigedNotificacion(solicitud.getNumeroExpediente());
				notificacionBeanDTO.setIdDocumentoSigedDocumentoNotificacion(null);
				Date fechaValidacion = sneApiConsumer.obtenerFechaNotificacion(notificacionBeanDTO, token);
			
				marcarSolicitudRespuesta(solicitud.getIdSolicitud(), fechaValidacion, contexto);
            }
            catch (Exception e) {
				logger.info("registrarNotificacionSolicitud: No se obtuvo la fecha de notificación del número expediente " + solicitud.getNumeroExpediente());
			}
		}
		
		logger.info("registrarNotificacionSolicitud fin");
	}
	

	public ListadoDetalle obtenerTipoPersona(String solicitudUuid) {
		Solicitud solicitud = solicitudDao.obtenerTipoPersona(solicitudUuid);
		return solicitud.getPersona().getTipoPersona();
	}

	@Override
	public Long obtenerId(String solicitudUuid) {
		if(solicitudUuid==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		return solicitudDao.obtenerId(solicitudUuid);
	}
	
	@Override
	public List<PerfilAprobador> buscarAprobadoresPorSolicitud(Long idSolicitud) {
	    return solicitudDao.buscarAprobadoresPorSolicitud(idSolicitud);
	}
	
	@Override
	public Solicitud obtenerSolicitudAprobada(String codigoRuc) {
		return solicitudDao.obtenerAprobadaPorRuc(codigoRuc);
	}
	
	
	private String reemplazarCaracteres(String originalFilename) {
		if (originalFilename == null) {
			throw new NullPointerException("originalFilename es nulo");
		}
		return originalFilename.replaceAll("[^\\p{ASCII}]", "");
	}
	
	private Archivo generarInformeTecnicoPJ(Solicitud solicitud, Asignacion asignacion, Contexto contexto) throws Exception {
		Long idSolicitud = solicitud.getIdSolicitud();
		Long idUsuario = asignacion.getUsuario().getIdUsuario();
		Archivo archivo = new Archivo();
		SimpleDateFormat sdf2=new SimpleDateFormat("ddMMyyyyhhmm");
		String fechaHoy = sdf2.format(new Date());
		archivo.setNombre("INFORME_VERIFICACION_TECNICA_"+reemplazarCaracteres(asignacion.getUsuario().getUsuario().toUpperCase())+"_"+fechaHoy+".pdf");
		archivo.setNombreReal("INFORME_VERIFICACION_TECNICA_"+reemplazarCaracteres(asignacion.getUsuario().getUsuario().toUpperCase())+"_"+fechaHoy+".pdf");
		archivo.setTipo("application/pdf");
		archivo.setIdAsignacion(asignacion.getIdAsignacion());
		archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
				Constantes.LISTADO.TIPO_ARCHIVO.INFORME_TECNICO));
		archivo.setIdSolicitud(idSolicitud);		
		
		List<OtroRequisito> otrosRequisitos = new ArrayList<OtroRequisito>();
		List<Asignacion> aprobadores = null;

		if(solicitud.getIdSolicitudPadre() != null) {
			List<OtroRequisito> otrosRequisitosAnteriores = otroRequisitoService.listarOtroRequisitoXSolicitudObservadosPJ( solicitud.getIdSolicitudPadre() ,idUsuario, contexto);
			
			List<OtroRequisito> otrosRequisitosActuales = otroRequisitoService.listarOtroRequisitoXSolicitudPJ( idSolicitud ,idUsuario, contexto);

			Map<String, Object> requisitosMap = new HashMap<>();
			for(OtroRequisito requisito:otrosRequisitosAnteriores) {
				requisitosMap.put(requisito.getSubsector().getCodigo(), requisito);
			}
			
			for(OtroRequisito requisito:otrosRequisitosActuales) {
				OtroRequisito requisitoObservado = (OtroRequisito) requisitosMap.get(requisito.getSubsector().getCodigo());
				if(requisitoObservado!= null) {
					otrosRequisitos.add(requisito);
				}
			}
		}else {
			otrosRequisitos = otroRequisitoService.listarOtroRequisitoXSolicitudPJ(idSolicitud ,idUsuario, contexto);
		}
		if(!Constantes.LISTADO.GRUPOS.G1.equals(asignacion.getGrupo().getCodigo())) {
			aprobadores = asignacionService.buscarAprobadoresPJ(idSolicitud,idUsuario);
		}
		solicitud.setAsignados(aprobadores);
		removerTerminosCondiciones(otrosRequisitos);
		solicitud.setOtrosRequisitos(otrosRequisitos);

		File jrxml = new File(pathJasper + "Formato_Informe_PJ.jrxml");
		File jrxml2 = new File(pathJasper + "Informe_PJ_Aprobadores.jrxml");
		File jrxml3 = new File(pathJasper + "Informe_PJ_Resultado.jrxml");

		JasperReport jasperReport2 = getJasperCompilado(jrxml2);
		JasperReport jasperReport3 = getJasperCompilado(jrxml3);

		Map<String, Object> parameters = new HashMap<String, Object>();
		logger.info("SUBREPORT_DIR: {}", pathJasper);
		parameters.put("SUBREPORT_DIR", pathJasper);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JasperPrint print = null;
		InputStream appLogo = null;
		InputStream osinermingLogo = null;
		try {
			appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
			osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
			parameters.put("P_LOGO_APP", appLogo);
			parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
			List<Solicitud> solicitudes = new ArrayList<Solicitud>();
			solicitudes.add(solicitud);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
			JasperReport jasperReport = getJasperCompilado(jrxml);
			print = JasperFillManager.fillReport(jasperReport, parameters, ds);
			output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, output);

		} catch (Exception e) {
			throw e;

		} finally {
			close(appLogo);
			close(osinermingLogo);
		}

		byte[] bytesSalida = output.toByteArray();
		archivo.setPeso(bytesSalida.length * 1L);
		archivo.setNroFolio(1L);
		archivo.setContenido(bytesSalida);
		return archivo;
	}
	
	//AFC
		private Archivo generarInformeAdministrativoPN(Solicitud solicitud, Asignacion asignacion, Contexto contexto) throws Exception {
			Long idSolicitud = solicitud.getIdSolicitud();
			Long idUsuario = asignacion.getUsuario().getIdUsuario();
			Archivo archivo = new Archivo();
			SimpleDateFormat sdf2=new SimpleDateFormat("ddMMyyyyhhmm");
			String fechaHoy = sdf2.format(new Date());
			archivo.setNombre("INFORME_RESULTADO_ADMINISTRATIVO_"+reemplazarCaracteres(asignacion.getUsuario().getUsuario().toUpperCase())+"_"+fechaHoy+".pdf");
			archivo.setNombreReal("INFORME_RESULTADO_ADMINISTRATIVO_"+reemplazarCaracteres(asignacion.getUsuario().getUsuario().toUpperCase())+"_"+fechaHoy+".pdf");
			archivo.setIdAsignacion(asignacion.getIdAsignacion());
			archivo.setTipo("application/pdf");
			archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
					Constantes.LISTADO.TIPO_ARCHIVO.INFORME_ADMINISTRATIVO)); //CAMBIO
			archivo.setIdSolicitud(idSolicitud);		
			
			List<OtroRequisito> otrosRequisitos = new ArrayList<OtroRequisito>();
			List<OtroRequisito> otrosRequisitosAdm = new ArrayList<OtroRequisito>();

			List<Asignacion> aprobadores = null;
			StringBuilder observacionesConcatenadas = new StringBuilder();

				otrosRequisitos = otroRequisitoService.listarOtroRequisitoXSolicitudAdmin( idSolicitud );
				otrosRequisitosAdm = otroRequisitoService.listarOtroRequisitoXSolicitudAdminUser( idSolicitud );
				
				
			String usuario = otrosRequisitosAdm.get(0).getUsuActualizacion();
			Date fechaAct = asignacion.getFechaRegistro();
			boolean observacionAgregada = false;

			for (OtroRequisito requisito : otrosRequisitosAdm) {
				if (!(requisito.getObservacion()== null)) { 
					if (requisito.getObservacion().length() > 0) {
						observacionesConcatenadas.append(requisito.getObservacion() + "\n"); 
						observacionAgregada = true;
			        }
			    }
				
				if(usuario == null) {
					if (requisito.getUsuActualizacion()!= null) {
						usuario = requisito.getUsuActualizacion();
						}
				    }
				if(fechaAct ==null) {
					if (requisito.getFecActualizacion()!= null) {
						fechaAct = requisito.getFecActualizacion();
						}
				    }
				}
		    if (!observacionAgregada) { 
			      observacionesConcatenadas.append("Cumple con todos los requisitos solicitados."); 
			      observacionAgregada = true;
			   }
			
			for (OtroRequisito requisito : otrosRequisitos) {
			//if ((requisito.getFecActualizacion()== null)) { 
			//	requisito.setFecActualizacion(fechaAct);
			//    }else {
			     requisito.setFecActualizacion(fechaAct);
			//    }
			
			}
			
			removerTerminosCondiciones(otrosRequisitos);
			
			solicitud.setObservacionAdmnistrativa(observacionesConcatenadas.substring(0));
			solicitud.setOtrosRequisitos(otrosRequisitos);
			aprobadores = asignacionService.buscarAprobadoresAdministrativos(idSolicitud,Long.parseLong(usuario));
			solicitud.setAsignados(aprobadores);
			File jrxml = new File(pathJasper + "Formato_informe_PN_admin.jrxml");
			

			Map<String, Object> parameters = new HashMap<String, Object>();
			logger.info("SUBREPORT_DIR: {}", pathJasper);
			parameters.put("SUBREPORT_DIR", pathJasper);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperPrint print = null;
			InputStream appLogo = null;
			InputStream osinermingLogo = null;
			try {
				appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
				osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
				parameters.put("P_LOGO_APP", appLogo);
				parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
				List<Solicitud> solicitudes = new ArrayList<Solicitud>();
				solicitudes.add(solicitud);
				JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
				JasperReport jasperReport = getJasperCompilado(jrxml);
				print = JasperFillManager.fillReport(jasperReport, parameters, ds);
				output = new ByteArrayOutputStream();
				JasperExportManager.exportReportToPdfStream(print, output);

			} catch (Exception e) {
				throw e;

			} finally {
				close(appLogo);
				close(osinermingLogo);
			}

			byte[] bytesSalida = output.toByteArray();
			archivo.setPeso(bytesSalida.length * 1L);
			archivo.setNroFolio(1L);
			archivo.setContenido(bytesSalida);
			return archivo;
		}
	
		private Archivo generarInformeAdministrativoPJ(Solicitud solicitud, Asignacion asignacion, Contexto contexto) throws Exception {
			Long idSolicitud = solicitud.getIdSolicitud();
			Long idUsuario = asignacion.getUsuario().getIdUsuario();
			Archivo archivo = new Archivo();
			SimpleDateFormat sdf2=new SimpleDateFormat("ddMMyyyyhhmm");
			String fechaHoy = sdf2.format(new Date());
			archivo.setNombre("INFORME_RESULTADO_ADMINISTRATIVO_"+reemplazarCaracteres(asignacion.getUsuario().getUsuario().toUpperCase())+"_"+fechaHoy+".pdf");
			archivo.setNombreReal("INFORME_RESULTADO_ADMINISTRATIVO_"+reemplazarCaracteres(asignacion.getUsuario().getUsuario().toUpperCase())+"_"+fechaHoy+".pdf");
			archivo.setTipo("application/pdf");
			archivo.setIdAsignacion(asignacion.getIdAsignacion());
			archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
					Constantes.LISTADO.TIPO_ARCHIVO.INFORME_ADMINISTRATIVO));
			archivo.setIdSolicitud(idSolicitud);		
			
			List<OtroRequisito> otrosRequisitos = new ArrayList<OtroRequisito>();
			List<OtroRequisito> otrosRequisitosAdm = new ArrayList<OtroRequisito>();

			List<Asignacion> aprobadores = null;

				otrosRequisitos = otroRequisitoService.listarOtroRequisitoXSolicitudAdmin(idSolicitud);
				otrosRequisitosAdm = otroRequisitoService.listarOtroRequisitoXSolicitudAdminUser( idSolicitud );

			StringBuilder observacionesConcatenadas = new StringBuilder();
			String usuario = otrosRequisitosAdm.get(0).getUsuActualizacion();
			Date fechaAct = asignacion.getFechaRegistro();		
			boolean observacionAgregada = false;

			for (OtroRequisito requisito : otrosRequisitosAdm) {
				if (!(requisito.getObservacion()== null)) { 
					if (requisito.getObservacion().length() > 0) {
						observacionesConcatenadas.append(requisito.getObservacion() + "\n"); 
						observacionAgregada = true;
			        }
					
			    }
				
				if(usuario == null) {
					if (requisito.getUsuActualizacion()!= null) {
						usuario = requisito.getUsuActualizacion();
						}
				    }
				if(fechaAct ==null) {
					if (requisito.getFecActualizacion()!= null) {
						fechaAct = requisito.getFecActualizacion();
						}
				    }
				}
		    if (!observacionAgregada) { 
			      observacionesConcatenadas.append("Cumple con todos los requisitos solicitados."); 
			      observacionAgregada = true;
			   }
			
			for (OtroRequisito requisito : otrosRequisitos) {
			//if ((requisito.getFecActualizacion()== null)) { 
			//	requisito.setFecActualizacion(fechaAct);
			//    }else {
			     requisito.setFecActualizacion(fechaAct);
			//    }
			
			}
			
			
			removerTerminosCondiciones(otrosRequisitos);
			
			solicitud.setObservacionAdmnistrativa(observacionesConcatenadas.substring(0));
			solicitud.setOtrosRequisitos(otrosRequisitos);
			aprobadores = asignacionService.buscarAprobadoresAdministrativos(idSolicitud,Long.parseLong(usuario));
			solicitud.setAsignados(aprobadores);

			File jrxml = new File(pathJasper + "Formato_Informe_PJ_admin.jrxml");


			Map<String, Object> parameters = new HashMap<String, Object>();
			logger.info("SUBREPORT_DIR: {}", pathJasper);
			parameters.put("SUBREPORT_DIR", pathJasper);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperPrint print = null;
			InputStream appLogo = null;
			InputStream osinermingLogo = null;
			try {
				appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
				osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
				parameters.put("P_LOGO_APP", appLogo);
				parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
				List<Solicitud> solicitudes = new ArrayList<Solicitud>();
				solicitudes.add(solicitud);
				JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
				JasperReport jasperReport = getJasperCompilado(jrxml);
				print = JasperFillManager.fillReport(jasperReport, parameters, ds);
				output = new ByteArrayOutputStream();
				JasperExportManager.exportReportToPdfStream(print, output);

			} catch (Exception e) {
				throw e;

			} finally {
				close(appLogo);
				close(osinermingLogo);
			}

			byte[] bytesSalida = output.toByteArray();
			archivo.setPeso(bytesSalida.length * 1L);
			archivo.setNroFolio(1L);
			archivo.setContenido(bytesSalida);
			return archivo;
		}
		//AFC
		
	
	private Archivo generarInformeTecnicoPN(Solicitud solicitud, Asignacion asignacion, Contexto contexto) throws Exception {
		Long idSolicitud = solicitud.getIdSolicitud();
		Long idUsuario = asignacion.getUsuario().getIdUsuario();
		Archivo archivo = new Archivo();
		SimpleDateFormat sdf2=new SimpleDateFormat("ddMMyyyyhhmm");
		String fechaHoy = sdf2.format(new Date());
		archivo.setNombre("INFORME_VERIFICACION_TECNICA_"+reemplazarCaracteres(asignacion.getUsuario().getUsuario().toUpperCase())+"_"+fechaHoy+".pdf");
		archivo.setNombreReal("INFORME_VERIFICACION_TECNICA_"+reemplazarCaracteres(asignacion.getUsuario().getUsuario().toUpperCase())+"_"+fechaHoy+".pdf");
		archivo.setIdAsignacion(asignacion.getIdAsignacion());
		archivo.setTipo("application/pdf");
		archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
				Constantes.LISTADO.TIPO_ARCHIVO.INFORME_TECNICO));
		archivo.setIdSolicitud(idSolicitud);		
		
		List<OtroRequisito> otrosRequisitos = new ArrayList<OtroRequisito>();
		List<Asignacion> aprobadores = null;
		
		if(solicitud.getIdSolicitudPadre() != null) {
			List<OtroRequisito> otrosRequisitosAnteriores = otroRequisitoService.listarOtroRequisitoXSolicitudObservados(solicitud.getIdSolicitudPadre() ,idUsuario, contexto);
					
			List<OtroRequisito> otrosRequisitosActuales = otroRequisitoService.listarOtroRequisitoXSolicitud(idSolicitud ,idUsuario, contexto);

			Map<String, Object> requisitosMap = new HashMap<>();
			for(OtroRequisito requisito:otrosRequisitosAnteriores) {
				requisitosMap.put(requisito.getPerfil().getCodigo(), requisito);
			}
			
			for(OtroRequisito requisito:otrosRequisitosActuales) {
				OtroRequisito requisitoObservado = (OtroRequisito) requisitosMap.get(requisito.getPerfil().getCodigo());
				if(requisitoObservado!= null) {
					otrosRequisitos.add(requisito);
				}
			}
			
		}else {
			otrosRequisitos = otroRequisitoService.listarOtroRequisitoXSolicitud( idSolicitud ,idUsuario, contexto);
		}
		
		if(!Constantes.LISTADO.GRUPOS.G1.equals(asignacion.getGrupo().getCodigo())) {
			aprobadores = asignacionService.buscarAprobadores(idSolicitud,idUsuario);
		}
		solicitud.setAsignados(aprobadores);
		removerTerminosCondiciones(otrosRequisitos);
		solicitud.setOtrosRequisitos(otrosRequisitos);
		
		File jrxml = new File(pathJasper + "Formato_Informe_PN.jrxml");
		File jrxml2 = new File(pathJasper + "Informe_PN_Aprobadores.jrxml");
		File jrxml3 = new File(pathJasper + "Informe_PN_Resultado.jrxml");

		JasperReport jasperReport2 = getJasperCompilado(jrxml2);
		JasperReport jasperReport3 = getJasperCompilado(jrxml3);

		Map<String, Object> parameters = new HashMap<String, Object>();
		logger.info("SUBREPORT_DIR: {}", pathJasper);
		parameters.put("SUBREPORT_DIR", pathJasper);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JasperPrint print = null;
		InputStream appLogo = null;
		InputStream osinermingLogo = null;
		try {
			appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
			osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
			parameters.put("P_LOGO_APP", appLogo);
			parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
			List<Solicitud> solicitudes = new ArrayList<Solicitud>();
			solicitudes.add(solicitud);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
			JasperReport jasperReport = getJasperCompilado(jrxml);
			print = JasperFillManager.fillReport(jasperReport, parameters, ds);
			output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, output);

		} catch (Exception e) {
			throw e;

		} finally {
			close(appLogo);
			close(osinermingLogo);
		}

		byte[] bytesSalida = output.toByteArray();
		archivo.setPeso(bytesSalida.length * 1L);
		archivo.setNroFolio(1L);
		archivo.setContenido(bytesSalida);
		return archivo;
	}

	@Override
	public Archivo generarInformeTecnico(Long idSolicitud, Long idAsignacion, Contexto contexto) throws Exception {
		Solicitud solicitud = obtenerSinAsignados(idSolicitud, contexto);
		Asignacion asignacion = asignacionService.obtener(idAsignacion, contexto);
		if (validarJuridicoPostor(solicitud.getSolicitudUuid())) {
			return generarInformeTecnicoPJ(solicitud,asignacion,contexto);
		} else {
			return generarInformeTecnicoPN(solicitud,asignacion,contexto);
		}
	}
	
	//AFC
		@Override
		public Archivo generarInformeAdministrativo(Long idSolicitud, Long idAsignacion, Contexto contexto) throws Exception {
			Solicitud solicitud = obtenerSinAsignados(idSolicitud, contexto);
			Asignacion asignacion = asignacionService.obtener(idAsignacion, contexto);
			if (validarJuridicoPostor(solicitud.getSolicitudUuid())) {
				return generarInformeAdministrativoPJ(solicitud,asignacion,contexto);
			} else {
				return generarInformeAdministrativoPN(solicitud,asignacion,contexto);
			}
		}
		//AFC

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void subirDocumentoTecnicos(Contexto contexto) {
		logger.info("subirDocumentoTecnicos Inicio");
		List<Archivo> archivos=archivoService.obtenerDocumentoTecnicosPendientes(contexto);
		for(Archivo archivoAux:archivos) {
			subirDocumentoTecnicos(archivoAux, contexto);
		}
		logger.info("subirDocumentoTecnicos Fin");
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void subirDocumentoTecnicos(Archivo archivoAux,Contexto contexto) {
		logger.info("subirDocumentoTecnicos Inicio");
		Archivo archivo = archivoService.obtenerContenido(archivoAux.getIdArchivo(), contexto);
		List<File> archivosAlfresco=new ArrayList<File>();
		try {
			File file = null;
			try {
				File dir = new File(pathTemporal + File.separator +"temporales"+ File.separator+ archivo.getIdSolicitud());
				if (!dir.exists()) {
					dir.mkdirs();
				}
				file = new File(pathTemporal + File.separator +"temporales"+ File.separator+ archivo.getIdSolicitud() + File.separator + archivo.getNombreReal());
				FileUtils.writeByteArrayToFile(file, archivo.getContenido());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);				
			}
			archivosAlfresco.add(file);				
			Asignacion asignacion=asignacionService.obtener(archivo.getIdAsignacion(), contexto);
			Solicitud solicitud=obtener(archivo.getIdSolicitud(), contexto);
			ExpedienteInRO expedienteInRO = crearExpedienteAgregarDocumentosTecnicos(solicitud,getInteger(asignacion.getUsuario().getCodigoUsuarioInterno()),contexto);
			DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
			logger.info("SIGED RESULT: {}", documentoOutRO.getMessage());
		} catch (Exception e) {
			logger.error("ERROR 3 {} ", e.getMessage(), e);
			throw new ValidacionException(e);								
		}
		logger.info("subirDocumentoTecnicos Fin");
	}
	
	//AFC
		@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
		public void subirDocumentoAdministrativos(Contexto contexto) {
			logger.info("subirDocumentoAdministrativos Inicio");
			List<Archivo> archivos=archivoService.obtenerDocumentoTecnicosPendientes(contexto);
			for(Archivo archivoAux:archivos) {
				subirDocumentoAdministrativos(archivoAux, contexto);
			}
			logger.info("subirDocumentoAdministrativos Fin");
		}
		
		@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
		public void subirDocumentoAdministrativos(Archivo archivoAux,Contexto contexto) {
			logger.info("subirDocumentoAdministrativos Inicio");
			Archivo archivo = archivoService.obtenerContenido(archivoAux.getIdArchivo(), contexto);
			List<File> archivosAlfresco=new ArrayList<File>();
			try {
				File file = null;
				try {
					File dir = new File(pathTemporal + File.separator +"temporales"+ File.separator+ archivo.getIdSolicitud());
					if (!dir.exists()) {
						dir.mkdirs();
					}
					file = new File(pathTemporal + File.separator +"temporales"+ File.separator+ archivo.getIdSolicitud() + File.separator + archivo.getNombreReal());
					FileUtils.writeByteArrayToFile(file, archivo.getContenido());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);				
				}
				archivosAlfresco.add(file);				
				Asignacion asignacion=asignacionService.obtener(archivo.getIdAsignacion(), contexto);
				Solicitud solicitud=obtener(archivo.getIdSolicitud(), contexto);
				ExpedienteInRO expedienteInRO = crearExpedienteAgregarDocumentosAdminstrativos(solicitud,getInteger(asignacion.getUsuario().getCodigoUsuarioInterno()),contexto);
				DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
				logger.info("SIGED RESULT: {}", documentoOutRO.getMessage());
			} catch (Exception e) {
				logger.error("ERROR 3 {} ", e.getMessage(), e);
				throw new ValidacionException(e);								
			}
			logger.info("subirDocumentoAdministrativos Fin");
		}
	//AFC
	
	@Override
	public Solicitud obtenerUltimaSolicitudPresentadaPorUsuario(Contexto contexto) {
		Long idUsuario = -1L;
		Solicitud solicitud = null;
		
		if (contexto.getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
			idUsuario = contexto.getUsuario().getIdUsuario();
		}
		
		List<Solicitud> solicitudes = solicitudDao.listarSolicitudesPresentadasPorIdUsuario(idUsuario);
		
		if (solicitudes != null && solicitudes.size() > 0) {
			solicitud = solicitudes.get(0);
		}
		
		return solicitud;
	}
	
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void anularSolicitud(Long idSolicitud, Contexto contexto) {
		
		Solicitud currentSolicitud = null;
		currentSolicitud = solicitudDao.obtener(idSolicitud);
		List<OtroRequisito> perfiles = otroRequisitoService.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, idSolicitud);
		
		//ID_ESTADO_LD
		ListadoDetalle estado = listadoDetalleService.obtener(Long.parseLong("72"), contexto);		
		currentSolicitud.setEstado(estado);
		
		//ID_ESTADO_REVISION_LD
		ListadoDetalle estadoRevision = listadoDetalleService.obtener(Long.parseLong("77"), contexto);		
		currentSolicitud.setEstadoRevision(estadoRevision);
		
		//NULL DE PERFILES
		for(OtroRequisito perfil : perfiles){
			perfil.setPerfil(null);
		}
		currentSolicitud.setPerfiles(perfiles);

		//FL_ACTIVO
		currentSolicitud.setFlagActivo(Long.parseLong("0"));
		
		//US_ACTUALIZACION
		currentSolicitud.setUsuActualizacion("user");
		
		//FE_ACTUALIZACION
		currentSolicitud.setFecActualizacion(new Date());
		
		//IP_ACTUALIZACION
		currentSolicitud.setIpActualizacion(contexto.getIp());
		
		solicitudDao.save(currentSolicitud);
	}
	//Fin cambio

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void cancelarSolicitud(String solicitudUuid, Contexto contexto) {
		
		Long idSolicitud = solicitudDao.obtenerId(solicitudUuid);

		Solicitud currentSolicitud = null;
		currentSolicitud = solicitudDao.obtener(idSolicitud);
		List<OtroRequisito> perfiles = otroRequisitoService
				.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, idSolicitud);

		// ID_ESTADO_LD
		ListadoDetalle estado = listadoDetalleService.obtener(Long.parseLong("72"), contexto);
		currentSolicitud.setEstado(estado);

		// ID_ESTADO_REVISION_LD
		ListadoDetalle estadoRevision = listadoDetalleService.obtener(Long.parseLong("77"), contexto);
		currentSolicitud.setEstadoRevision(estadoRevision);

		// FL_ACTIVO
		currentSolicitud.setFlagActivo(Long.parseLong("0"));

		// NULL DE PERFILES
		for (OtroRequisito perfil : perfiles) {
			perfil.setPerfil(null);
		}
		currentSolicitud.setPerfiles(perfiles);

		// US_ACTUALIZACION
		currentSolicitud.setUsuActualizacion("user");

		// FE_ACTUALIZACION
		currentSolicitud.setFecActualizacion(new Date());

		// IP_ACTUALIZACION
		currentSolicitud.setIpActualizacion(contexto.getIp());

		solicitudDao.save(currentSolicitud);
	}

	@Override
	public List<Long> obtenerSubsectoresXUsuarioSolicitud(String uuid, Long idUsuario) {
		return solicitudDao.obtenerSubsectoresXUsuarioSolicitud(uuid, idUsuario);
	}

	private Solicitud actualizarSolicitudModificada(Solicitud solicitudNueva, Contexto contexto) {
		// Setear algunos campos en null
		solicitudNueva.resetCamposModificables();

		// Asignar Solicitud de Modificacion a Personas Naturales
		if (solicitudNueva.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.NATURAL)
		|| solicitudNueva.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO)) {
			ListadoDetalle tipoModificacion = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.TIPO_SOLICITUD.CODIGO, Constantes.LISTADO.TIPO_SOLICITUD.MODIFICACION);
			solicitudNueva.setTipoSolicitud(tipoModificacion);
		}

		// Actualizar estado Otro Requisito
		List<OtroRequisito> otrosRequisito = otroRequisitoService.listarOtroRequisito(solicitudNueva.getIdSolicitud());
		if(!otrosRequisito.isEmpty()) {
			ListadoDetalle estadoOtroRequisito = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_OTRO_REQUISITO.CODIGO, Constantes.LISTADO.ESTADO_OTRO_REQUISITO.ORIGINAL);
			otrosRequisito = otrosRequisito.stream()
					.peek(req -> req.setEstado(estadoOtroRequisito))
					.collect(Collectors.toList());
			otroRequisitoDao.saveAll(otrosRequisito);
		}

		//Actualizar estado Documento
		List<Documento> documentos = documentoService.buscar(solicitudNueva.getIdSolicitud(), contexto);
		if(!documentos.isEmpty()) {
			ListadoDetalle estadoDocumento = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_DOCUMENTO.CODIGO, Constantes.LISTADO.ESTADO_DOCUMENTO.ORIGINAL);
			if (solicitudNueva.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.NATURAL)
					|| solicitudNueva.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO)) {
				documentos = documentos.stream()
						.peek(doc -> {
							doc.setEstado(estadoDocumento);
						})
						.collect(Collectors.toList());
			} else {
				documentos = documentos.stream()
						.peek(doc -> {
							doc.setEstado(estadoDocumento);
							doc.setFlagSiged(0L);
						})
						.collect(Collectors.toList());
			}

			documentoDao.saveAll(documentos);
		}

		//Actualizar estado Estudios
		List<Estudio> estudios = estudioService.buscar(solicitudNueva.getIdSolicitud(), contexto);
		if(!estudios.isEmpty()) {
			ListadoDetalle estadoEstudio = listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.ESTADO_ESTUDIO.CODIGO, Constantes.LISTADO.ESTADO_ESTUDIO.ORIGINAL);
			estudios = estudios.stream()
					.peek(estudio -> estudio.setEstado(estadoEstudio))
					.collect(Collectors.toList());
			estudioDao.saveAll(estudios);
		}

		return solicitudNueva;
	}

	@Override
	public boolean validarCambios(Solicitud solicitud, Contexto contexto) {

		Long solicitudId = obtenerId(solicitud.getSolicitudUuid());
		Solicitud solicitudDB = obtener(solicitudId, contexto);

		List<Documento> documentosPadre = documentoService.buscar(solicitudDB.getIdSolicitudPadre(), contexto);
		List<Documento> documentosHijo = documentoService.buscar(solicitudDB.getIdSolicitud(), contexto);

		List<Estudio> estudiosPadre = estudioService.buscar(solicitudDB.getIdSolicitudPadre(), contexto);
		List<Estudio> estudiosHijo = estudioService.buscar(solicitudDB.getIdSolicitud(), contexto);

		if (solicitudDB.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.NATURAL)
				|| solicitudDB.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO)) {
			// Si las listas tienen diferente tamaño, hay cambios
			if (documentosPadre.size() != documentosHijo.size() || estudiosPadre.size() != estudiosHijo.size()) {
				return true;
			}
		} else {
			// Si las listas tienen diferente tamaño, hay cambios
			if (documentosPadre.size() != documentosHijo.size()) {
				return true;
			}

			// Verificar si algún documento en la lista hijo coincide con algún documento en la lista padre
			for (Documento docHijo : documentosHijo) {
				boolean encontroCoincidencia = false;

				for (Documento docPadre : documentosPadre) {
					boolean mismoTipoDocumento = ComparisonUtil.sonIguales(docHijo.getTipoDocumento(), docPadre.getTipoDocumento());
					boolean mismoTipoCambio = ComparisonUtil.sonIguales(docHijo.getTipoCambio(), docPadre.getTipoCambio());
					boolean mismoCodigoContrato = ComparisonUtil.sonIguales(docHijo.getCodigoContrato(), docPadre.getCodigoContrato());
					boolean mismaFechaInicio = ComparisonUtil.sonIguales(docHijo.getFechaInicio(), docPadre.getFechaInicio());
					boolean mismaFechaFin = ComparisonUtil.sonIguales(docHijo.getFechaFin(), docPadre.getFechaFin());
					boolean mismoFlagVigente = ComparisonUtil.sonIguales(docHijo.getFlagVigente(), docPadre.getFlagVigente());
					boolean mismoMontoContrato = ComparisonUtil.sonIguales(docHijo.getMontoContrato(), docPadre.getMontoContrato());
					boolean mismoMontoContratoSol = ComparisonUtil.sonIguales(docHijo.getMontoContratoSol(), docPadre.getMontoContratoSol());

					// Si todos los campos coinciden, marcar como encontrado
					if (mismoTipoDocumento && mismoTipoCambio && mismoCodigoContrato && mismaFechaInicio &&
							mismaFechaFin && mismoFlagVigente && mismoMontoContrato && mismoMontoContratoSol) {
						encontroCoincidencia = true;
					} else {
						encontroCoincidencia = false;
						break;
					}
				}

				if (!encontroCoincidencia) {
					return true;
				}
			}
		}
		return false;
	}

    @Transactional
    public void guardarLote(List<ReqInicioServicio> registros) {
        for (ReqInicioServicio body : registros) {
            Optional<ReqInicioServicio> existente = reqInicioServicioDao.findBySolicitudPerfilIdAndTipoDocumento(
                body.getSolicitudPerfilId(), body.getTipoDocumento()
            );
            ReqInicioServicio e = existente.orElse(new ReqInicioServicio());
            e.setSolicitudPerfilId(body.getSolicitudPerfilId());
            e.setSupervisoraId(body.getSupervisoraId());
            e.setTipoDocumento(body.getTipoDocumento());
            e.setArchivoId(body.getArchivoId());
            e.setFechaInicio(body.getFechaInicio());
            e.setFechaFin(body.getFechaFin());
            e.setEstadoEvaluacion(null); // OBLIGATORIAMENTE en null
            e.setUsuarioId(null);
            e.setFechaEvaluacion(null);
            e.setObservacion(null);
            reqInicioServicioDao.save(e);
        }
    }

}
