package pe.gob.osinergmin.sicoes.service.impl;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import gob.osinergmin.siged.remote.rest.ro.base.BaseOutRO;
import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SneApiConsumer;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.EvaluacionPerfDTO;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.*;
import pe.gob.osinergmin.sicoes.util.bean.sne.NotificacionBeanDTO;

import javax.mail.internet.MimeMessage;

@Service
public class SicoesSolicitudServiceImpl implements SicoesSolicitudService  {

	Logger logger = LogManager.getLogger(SicoesSolicitudServiceImpl.class);

	@Autowired
	private SicoesSolicitudDao sicoesSolicitudDao;

	@Autowired
	private SicoesTdSolPerConSecService sicoesTdSolPerConSecService;

	@Autowired
	private SicoesTdSolPersPropService sicoesTdSolPersPropService;

	@Autowired
	private SicoesSolicitudSeccionService sicoesSolicitudSeccionService;

	@Autowired
	private SupervisoraService supervisoraService;

	@Autowired
	private SigedApiConsumer sigedApiConsumer;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private Environment env;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private SicoesTdSolPerConSecService seccionesService;

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private ArchivoService archivoService;

	@Autowired
	private SupervisoraRepresentanteService supervisoraRepresentanteService;

	@Autowired
	private ListadoDetalleService listadoDetalleService;

	@Autowired
	SneApiConsumer sneApiConsumer;

	@Value("${siged.old.proyecto}")
	private String SIGLA_PROYECTO;

	@Value("${path.jasper}")
	private String pathJasper;

	@Value("${path.temporal}")
	private String pathTemporal;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SicoesSolicitud guardarContratoConsentido(Propuesta propuesta, Contexto contexto) {
		SicoesSolicitud resSolicitud = null;
		ListadoDetalle plazoPresentar = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.PRESENTAR_PERFECCIONAMIENTO);
		Date fechaInscripcion = calcularFechaFin(new Date(), Long.parseLong(plazoPresentar.getValor()));
		ListadoDetalle valorAdjudicacionSimplificada = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.VALORES.CODIGO, Constantes.LISTADO.VALORES.MONTO_SOLES);

		SicoesSolicitud solicitud = new SicoesSolicitud();
		solicitud.setPropuesta(propuesta);
		solicitud.setSupervisora(propuesta.getSupervisora());
		solicitud.setDescripcionSolicitud(Constantes.DESC_PROCESO_PERF_CONTRATO.PRELIMINAR);
		solicitud.setTipoSolicitud(Constantes.TIPO_SOLICITUD_PERF_CONTRATO.INSCRIPCION);
		solicitud.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.PRELIMINAR);
		solicitud.setFechaPlazoInscripcion(fechaInscripcion);
		solicitud.setEstado(Constantes.ESTADO.ACTIVO);
		solicitud.setValorAdjSimplificada(valorAdjudicacionSimplificada.getValor());

		AuditoriaUtil.setAuditoriaRegistro(solicitud, contexto);
		try {
			resSolicitud = sicoesSolicitudDao.save(solicitud);
			if (resSolicitud != null) {
				List<SicoesTdSolPerConSec> secciones = sicoesTdSolPerConSecService.guardarSicoes(resSolicitud, contexto);
				List<SicoesTdSoliPersProp> profesionales = sicoesTdSolPersPropService.registrarProfesionales(resSolicitud, secciones, contexto);
				sicoesSolicitudSeccionService.registrarSolicitudSeccion(secciones, profesionales, contexto);
			} else {
				throw new IllegalStateException("La solicitud no pudo ser guardada.");
			}
		} catch (Exception e) {
			logger.error("Error al guardar solicitud", e);
			throw new ValidacionException("Error al guardar solicitud de perfeccionamiento de contrato");
		}
		return resSolicitud;
	}

	@Override
	public Page<SicoesSolicitud> listarSolicitudesPresentacion(String estado, String nroConcurso, Long item, String convocatoria, String tipoSolicitud, Pageable pageable, Contexto contexto) {
		logger.info("listarSolicitudesObservadas");
		Supervisora supervisora = supervisoraService.obtenerSupervisoraPorRucPostorOrJuridica(contexto.getUsuario().getCodigoRuc());
		return sicoesSolicitudDao.obtenerxTipoEstadoProceso(estado, nroConcurso, item, convocatoria, tipoSolicitud, supervisora.getIdSupervisora(), pageable);
	}

	@Override
	public Page<SicoesSolicitud> listarSolicitudesProceso(String estado, String nroConcurso, Long item, String convocatoria, String tipoSolicitud, Pageable pageable, Contexto contexto) {
		return sicoesSolicitudDao.obtenerxTipoEstadoProcesoInterno(estado, nroConcurso, item, convocatoria, tipoSolicitud, pageable);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SicoesSolicitud finalizarSolicitud(SicoesSolicitud solicitud, List<SicoesSolicitudSeccion> listaSolicitudSeccion, Contexto contexto) {
		AuditoriaUtil.setAuditoriaActualizacion(solicitud, contexto);
		String descripcionSolicitud = null;

//		if (solicitud.getIdSolicitudPadre() != null) {
//			solicitud.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.ARCHIVADO);
//			descripcionSolicitud = Constantes.DESC_PROCESO_PERF_CONTRATO.ARCHIVADO;
//		} else {
//			descripcionSolicitud = Constantes.DESC_PROCESO_PERF_CONTRATO.PRELIMINAR;
			List<SicoesTdSolPerConSec> secciones = seccionesService.obtenerSeccionesXSolicitud(solicitud.getIdSolicitud());
			List<Long> seccionesIds = new ArrayList<>();
			secciones.forEach(seccion -> seccionesIds.add(seccion.getIdSolPerConSec()));
			List<SicoesSolicitudSeccion> requisitosEvaluables = sicoesSolicitudSeccionService.obtenerRequisitosPorSeccionFinalizacion(seccionesIds);

			List<SicoesSolicitudSeccion> lstRequisitosSP = new ArrayList<>();
			List<SicoesSolicitudSeccion> lstRequisitosCP = new ArrayList<>();
			List<SicoesSolicitudSeccion> lstRequisitos = new ArrayList<>();

			lstRequisitosSP = requisitosEvaluables.stream()
					.filter(s -> s.getPersonalPropuesto() == null)
					.collect(Collectors.toList());

			lstRequisitosCP = requisitosEvaluables.stream()
					.filter(s -> s.getPersonalPropuesto() != null)
					.collect(Collectors.toList());

			List<Long> personasIds = new ArrayList<>();
			lstRequisitosCP.forEach(requisito -> personasIds.add(requisito.getPersonalPropuesto().getIdSoliPersProp()));

			List<Long> personasIdsSinDuplicados = new ArrayList<>(new LinkedHashSet<>(personasIds));

			for (Long personaId: personasIdsSinDuplicados) {
				List<SicoesSolicitudSeccion> requisitosPorPersona = sicoesSolicitudSeccionService.obtenerRequisitosPorPersonalActivo(personaId);
				Optional<SicoesSolicitudSeccion> existeEvaluadoCumple = requisitosPorPersona.stream()
				.filter(s -> s.getProcRevision().equals("1"))
				.findFirst();
				if (existeEvaluadoCumple.isPresent()) {
					lstRequisitos.add(existeEvaluadoCumple.get());
				} else {
					lstRequisitos.add(requisitosPorPersona.get(0));
				}
			}

			lstRequisitos.addAll(lstRequisitosSP);

			Optional<SicoesSolicitudSeccion> solicitudNoCumpleOpt = lstRequisitos.stream()
			.filter(s -> !s.getProcRevision().equals("1"))
			.findFirst();

//		}

//		Optional<SicoesSolicitudSeccion> solicitudNoCumpleOpt = lstRequisitosDet.stream()
//				.filter(s -> !s.getProcRevision().equals("1"))
//				.findFirst();

		if (solicitudNoCumpleOpt.isPresent()) {
			if(solicitud.getIdSolicitudPadre() != null) {
				solicitud.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.ARCHIVADO);
				descripcionSolicitud = Constantes.DESC_PROCESO_PERF_CONTRATO.ARCHIVADO;
			} else {
				descripcionSolicitud = Constantes.DESC_PROCESO_PERF_CONTRATO.OBSERVADO;
				SicoesSolicitud resSolicitudSubsanacion = null;
//				Date fechaFutura = this.calcularFechaFin(new Date(), 4L);
				SicoesSolicitud solicitudSubsanacion = new SicoesSolicitud();
				BeanUtils.copyProperties(solicitud, solicitudSubsanacion, "idSolicitud", "fechaHoraPresentacion", "fechaPlazoInscripcion", "fecCreacion", "usuCreacion", "ipCreacion", "fecActualizacion", "usuActualizacion", "ipActualizacion");

				solicitudSubsanacion.setIdSolicitudPadre(solicitud.getIdSolicitud());
				solicitudSubsanacion.setDescripcionSolicitud(Constantes.DESC_PROCESO_PERF_CONTRATO.PRELIMINAR);
				solicitudSubsanacion.setTipoSolicitud(Constantes.TIPO_SOLICITUD_PERF_CONTRATO.SUBSANACION);
				solicitudSubsanacion.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.PRELIMINAR);
//				solicitudSubsanacion.setFechaPlazoSubsanacion(fechaFutura);

				AuditoriaUtil.setAuditoriaRegistro(solicitudSubsanacion, contexto);
				try {
					resSolicitudSubsanacion = sicoesSolicitudDao.save(solicitudSubsanacion);
					if (resSolicitudSubsanacion != null) {
						List<SicoesTdSolPerConSec> seccionesSubsanacion = sicoesTdSolPerConSecService.guardarSeccionesSubsanacion(resSolicitudSubsanacion, contexto);
					} else {
						throw new IllegalStateException("La solicitud no pudo ser guardada.");
					}
				} catch (Exception e) {
					logger.error("Error al guardar solicitud", e);
				}

				solicitud.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.OBSERVADO);
				solicitud.setDescripcionSolicitud(Constantes.DESC_PROCESO_PERF_CONTRATO.OBSERVADO);
			}
		} else {
			solicitud.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.CONCLUIDO);
			descripcionSolicitud = Constantes.DESC_PROCESO_PERF_CONTRATO.CONCLUIDO;
			try {
				Contrato contratoDB = contratoService.registrarNuevoContrato(solicitud, contexto);
			} catch (Exception e) {
				logger.error("Error al registrar contrato", e);
			}
		}

		AuditoriaUtil.setAuditoriaActualizacion(solicitud, contexto);
		solicitud.setDescripcionSolicitud(descripcionSolicitud);
		SicoesSolicitud solicitudDB = sicoesSolicitudDao.save(solicitud);

		if (solicitudDB == null) {
			throw new ValidacionException("No se pudo actualizar la solicitud con id " + solicitud.getIdSolicitud());
		}

		try {
			SicoesSolicitud solicitudJasper = new SicoesSolicitud();
			String numeroExpedienteJasper = null;
			if (solicitud.getIdSolicitudPadre() != null) {
				SicoesSolicitud solicitudPadre = sicoesSolicitudDao.findById(solicitud.getIdSolicitudPadre()).get();
				numeroExpedienteJasper = solicitudPadre.getNumeroExpediente();
				solicitudJasper.setIdSolicitudPadre(solicitud.getIdSolicitudPadre());
			} else {
				numeroExpedienteJasper = solicitud.getNumeroExpediente();
			}
			solicitudJasper.setNumeroExpediente(numeroExpedienteJasper);
			Supervisora supervisoraJasper = initializeAndUnproxy(solicitud.getSupervisora());
			if (supervisoraJasper.getNombreRazonSocial() == null) {
				supervisoraJasper.setNombreRazonSocial(supervisoraJasper.getNombres()+ " " + supervisoraJasper.getApellidoPaterno() + " " + supervisoraJasper.getApellidoMaterno());
			}
			solicitudJasper.setSupervisora(supervisoraJasper);

			List<SicoesTdSolPerConSec> seccionesJasper = seccionesService.obtenerSeccionesXSolicitud(solicitud.getIdSolicitud());

			//				solicitudJasper.setSecciones(seccionesJasper);

			String tipoRequisitoEvaluado = solicitud.getTipoSolicitud()
					.equals(Constantes.TIPO_SOLICITUD_PERF_CONTRATO.INSCRIPCION)
					? Constantes.FLAG_PROCESO_SUBSANACION.INSCRIPCION
					: Constantes.FLAG_PROCESO_SUBSANACION.SUBSANACION;

			List<EvaluacionPerfDTO> resultadosDoc = new ArrayList<>();
			List<EvaluacionPerfDTO> resultadosPer = new ArrayList<>();
			List<EvaluacionPerfDTO> resultadosFiel = new ArrayList<>();
			List<EvaluacionPerfDTO> resultadosMonto = new ArrayList<>();

			seccionesJasper.forEach(seccion -> {
				SicoesTdSolPerConSec seccionJasper = new SicoesTdSolPerConSec();
				List<SicoesSolicitudSeccion> requisitos = sicoesSolicitudSeccionService.obtenerRequisitosEvaluados(seccion.getIdSolPerConSec(), tipoRequisitoEvaluado);
				boolean personalAdded = false;
				if (seccion.getFlConPersonal().equals(Constantes.FLAG_PERSONAL_PERF_CONTRATO.NO)) {
					requisitos.forEach(requisito -> {
						EvaluacionPerfDTO resultado = new EvaluacionPerfDTO();
						if (seccion.getFlConPersonal().equals(Constantes.FLAG_PERSONAL_PERF_CONTRATO.NO)) {
							resultado.setDescripcion(requisito.getDeRequisito());
							resultado.setResultado(obtenerResultadoEvaluacion(requisito.getProcRevision()));
						}

						if (seccion.getDeSeccion().contains("DOCUMENTOS REQUERIDOS PARA SUSCRIBIR EL CONTRATO")) {
							resultadosDoc.add(resultado);
						}

						if (seccion.getDeSeccion().contains("CARTA FIANZA DE FIEL CUMPLIMIENTO")) {
							resultadosFiel.add(resultado);
						}

						if (seccion.getDeSeccion().contains("CARTA FIANZA DEL MONTO DIFERENCIAL")) {
							resultadosMonto.add(resultado);
						}

					});
				} else {
					List<Long> personasEvaluadasIds = new ArrayList<>();
					requisitos.forEach(requisito -> personasEvaluadasIds.add(requisito.getPersonalPropuesto().getIdSoliPersProp()));

					List<Long> personasEvaluadasIdsSinDuplicados = new ArrayList<>(new LinkedHashSet<>(personasEvaluadasIds));

					for (Long personaId: personasEvaluadasIdsSinDuplicados) {
						List<SicoesSolicitudSeccion> requisitosPorPersona = sicoesSolicitudSeccionService.obtenerRequisitosPorPersonalActivo(personaId);
						SicoesTdSoliPersProp personaEvaluada = sicoesTdSolPersPropService.obtener(personaId, contexto);
						SicoesTdSoliPersProp personaEvaluadaUnproxy = initializeAndUnproxy(personaEvaluada);
						Supervisora supervisora = initializeAndUnproxy(personaEvaluadaUnproxy.getSupervisora());
						personaEvaluadaUnproxy.setSupervisora(supervisora);
						Optional<SicoesSolicitudSeccion> existeNoEvaluado = requisitosPorPersona.stream()
								.filter(s -> s.getProcRevision().equals("0"))
								.findFirst();
						Optional<SicoesSolicitudSeccion> existeEvaluadoCumple = requisitosPorPersona.stream()
								.filter(s -> s.getProcRevision().equals("1"))
								.findFirst();
						Optional<SicoesSolicitudSeccion> existeEvaluadoNoCumple = requisitosPorPersona.stream()
								.filter(s -> s.getProcRevision().equals("2"))
								.findFirst();
						Optional<SicoesSolicitudSeccion> existeEvaluadoObservado = requisitosPorPersona.stream()
								.filter(s -> s.getProcRevision().equals("3"))
								.findFirst();

						EvaluacionPerfDTO resultado = new EvaluacionPerfDTO();
						if (existeEvaluadoCumple.isPresent()) {
							resultado.setDescripcion(personaEvaluadaUnproxy.getSupervisora().getNombres() + " " + personaEvaluadaUnproxy.getSupervisora().getApellidoPaterno() + " " + personaEvaluadaUnproxy.getSupervisora().getApellidoMaterno());
							resultado.setResultado(obtenerResultadoEvaluacion(existeEvaluadoCumple.get().getProcRevision()));
						} else {
							resultado.setDescripcion(personaEvaluadaUnproxy.getSupervisora().getNombres() + " " + personaEvaluadaUnproxy.getSupervisora().getApellidoPaterno() + " " + personaEvaluadaUnproxy.getSupervisora().getApellidoMaterno());

							if (existeEvaluadoNoCumple.isPresent()) {
								resultado.setResultado(obtenerResultadoEvaluacion(existeEvaluadoNoCumple.get().getProcRevision()));
							} else if (existeEvaluadoObservado.isPresent()) {
								resultado.setResultado(obtenerResultadoEvaluacion(existeEvaluadoObservado.get().getProcRevision()));
							} else {
								resultado.setResultado(obtenerResultadoEvaluacion(existeNoEvaluado.get().getProcRevision()));
							}
						}

						if (seccion.getDeSeccion().contains("DEL PERSONAL PROPUESTO")) {
							resultadosPer.add(resultado);
						}
					}
				}
			});
			ExpedienteInRO expedienteInRO = null;
			solicitudJasper.setSeccionDoc(resultadosDoc);
			solicitudJasper.setSeccionPer(resultadosPer);
			solicitudJasper.setSeccionFiel(resultadosFiel);
			solicitudJasper.setSeccionMonto(resultadosMonto);
			expedienteInRO = crearExpedientePresentacion(solicitud, null, contexto);
			Archivo formato_23 = generarReporteEvaluacion(solicitudJasper, contexto);

			List<File> archivosAlfresco = new ArrayList<>();
			File file = null;

			try {
				File dir = new File(pathTemporal + File.separator+"temporales" + File.separator + solicitud.getIdSolicitud());
				if (!dir.exists()) {
					dir.mkdirs();
				}
				file = new File(
						pathTemporal + File.separator + "temporales" + File.separator + solicitud.getIdSolicitud() + File.separator + formato_23.getNombre());
				FileUtils.writeByteArrayToFile(file, formato_23.getContenido());
				formato_23.setContenido(Files.readAllBytes(file.toPath()));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO);
			}

			archivosAlfresco.add(file);
			DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
			if (documentoOutRO.getResultCode() != 1) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_AGREGAR_DOCUMENTOS);
			}

		} catch (Exception e) {
			logger.error("Error al guardar solicitud", e);
			throw new ValidacionException("Problemas al subir evaluación a SIGED");
		}

		return solicitudDB;
	}

	@Override
	public boolean enviarCorreoSancion(SicoesSolicitud solicitud, String periodo, String inicio, String fin, Contexto contexto) {
		logger.info("enviarCorreoSancion - Perfeccionamiento de contrato");

		final Context ctx = new Context();
		ctx.setVariable("nombreEmpresa", solicitud.getSupervisora().getNombreRazonSocial());
		ctx.setVariable("periodoInhabilitacion", periodo);
		ctx.setVariable("fechaInicio", inicio);
		ctx.setVariable("fechaFin", fin);

		String htmlContent = templateEngine.process("21-contrato-sancion-vigente.html", ctx);
		try {
			final MimeMessage mimeMessage = emailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart

			message.setSubject("Cancelación de Perfeccionamiento de contrato");
			message.setFrom(env.getProperty("spring.mail.username"));
			String[] correos = {solicitud.getSupervisora().getCorreo(), env.getProperty("spring.mail.username")};
			message.setTo(correos);
			message.setText(htmlContent, true);
			emailSender.send(mimeMessage);
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
		}
		logger.info("enviarCorreos fin");
		return true;
	}

	@Override
	public boolean enviarCorreoSancionPN(Map<String, Object> datos, Supervisora supervisora, Contexto contexto) {

		logger.info("enviarCorreoSancionPN - Perfeccionamiento de contrato");

		final Context ctx = new Context();
		ctx.setVariable("nombres", supervisora.getNombres() + " " + supervisora.getApellidoPaterno() + " " + supervisora.getApellidoMaterno());
		ctx.setVariable("fechaIngreso", datos.get("fechaIngreso"));
		ctx.setVariable("areaOperativa", datos.get("areaOperativa"));
		ctx.setVariable("descripcionPuesto", datos.get("descripcionPuesto"));

		String htmlContent = templateEngine.process("22-contrato-sancion-vigente-pn.html", ctx);
		try {
			final MimeMessage mimeMessage = emailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart

			message.setSubject("Cancelación de Perfeccionamiento de contrato - Persona Natural");
			message.setFrom(env.getProperty("spring.mail.username"));
			String[] correos = {supervisora.getCorreo(), env.getProperty("spring.mail.username")};
			message.setTo(correos);
			message.setText(htmlContent, true);
			emailSender.send(mimeMessage);
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
		}
		logger.info("enviarCorreos fin");
		return true;
	}

	@Override
	public boolean validarRemype(String numeroDocumento, Contexto contexto) {
		Supervisora supervisora = supervisoraService.obtenerSupervisoraPorRucPostorOrJuridica(numeroDocumento);
		Integer cantidad = sicoesSolicitudDao.buscarRegistroRemype(supervisora.getIdSupervisora());

		if (cantidad > 0) {
			return true;
		}

		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void registrarNotificacionSolicitudPerfCont(Contexto contexto) throws Exception {
		logger.info("registrarNotificacionSolicitudPerfCont");
		List<SicoesSolicitud> lstSolicitud = sicoesSolicitudDao.listarSolicitudesPorNotificar();
		Iterator<SicoesSolicitud> itSolicitudPerfCont = lstSolicitud.iterator();

		while (itSolicitudPerfCont.hasNext()) {
			SicoesSolicitud sicoesSolicitud = itSolicitudPerfCont.next();
			String token = sneApiConsumer.obtenerTokenAcceso2();

			try {
				NotificacionBeanDTO notificacionBeanDTO = new NotificacionBeanDTO();
				notificacionBeanDTO.setIdClienteSiged(null);
				notificacionBeanDTO.setIdUnidadOperativa(null);
				notificacionBeanDTO.setExpedienteSigedNotificacion(sicoesSolicitud.getNumeroExpediente());
				notificacionBeanDTO.setIdDocumentoSigedDocumentoNotificacion(null);
				Date fechaValidacion = sneApiConsumer.obtenerFechaNotificacion(notificacionBeanDTO, token);

				marcarSolicitudPerfContRespuesta(sicoesSolicitud.getIdSolicitud(), fechaValidacion, contexto);
			}
			catch (Exception e) {
				logger.info("registrarNotificacionSolicitudPerfCont: No se obtuvo la fecha de notificación del número expediente " + sicoesSolicitud.getNumeroExpediente());
			}
		}

		logger.info("registrarNotificacionSolicitudPerfCont fin");
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void archivarSolicitudesPerfContNoPresentadas(Contexto contexto) {
		archivarSolicitudesInscripcion(contexto);
		archivarSolicitudesSubsanacion(contexto);
	}

	public @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	void archivarSolicitudesInscripcion(Contexto contexto) {
		logger.info("Inicio archivarSolicitudesInscripcion");
		List<SicoesSolicitud> lstSolicitud = sicoesSolicitudDao.listarSolicitudesPorInscripcion();
		for (SicoesSolicitud solicitud: lstSolicitud) {
			solicitud.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.ARCHIVADO);
			solicitud.setDescripcionSolicitud(Constantes.DESC_PROCESO_PERF_CONTRATO.ARCHIVADO);
			AuditoriaUtil.setAuditoriaActualizacion(solicitud, contexto);
			sicoesSolicitudDao.save(solicitud);
		}
		logger.info("Fin archivarSolicitudesInscripcion");
	}

	public @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	void archivarSolicitudesSubsanacion(Contexto contexto) {
		logger.info("Inicio archivarSolicitudesSubsanacion");
		List<SicoesSolicitud> lstSolicitud = sicoesSolicitudDao.listarSolicitudesPorSubsanacion();
		for (SicoesSolicitud solicitud: lstSolicitud) {
			solicitud.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.ARCHIVADO);
			solicitud.setDescripcionSolicitud(Constantes.DESC_PROCESO_PERF_CONTRATO.ARCHIVADO);
			AuditoriaUtil.setAuditoriaActualizacion(solicitud, contexto);
			sicoesSolicitudDao.save(solicitud);
		}
		logger.info("Fin archivarSolicitudesSubsanacion");
	}

	@Override
	public boolean validarFechaPresentacionSubsanacion(Long idSolicitud, Contexto contexto) {
		SicoesSolicitud solicitud = sicoesSolicitudDao.findById(idSolicitud).orElse(null);

		if (solicitud != null) {

			if (solicitud.getTipoSolicitud().equals(Constantes.TIPO_SOLICITUD_PERF_CONTRATO.SUBSANACION)
				&& solicitud.getFechaPlazoSubsanacion() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_PERFECCIONAMIENTO_SIN_FECHA);
			}

			Date fechaActual = new Date();
			Date fechaLimite = solicitud.getTipoSolicitud().equals(Constantes.TIPO_SOLICITUD_PERF_CONTRATO.INSCRIPCION) ? solicitud.getFechaPlazoInscripcion() : solicitud.getFechaPlazoSubsanacion();
			LocalDate localFechaActual = fechaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			fechaActual = Date.from(localFechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant());

			return !fechaActual.after(fechaLimite);

		}

		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String actualizarSolicitud(List<SicoesSolicitudSeccion> listaSolicitudSeccion, SicoesSolicitud solicitud, Contexto contexto) throws Exception {
		if (solicitud.getEstadoProcesoSolicitud().equals(Constantes.ESTADO_PROCESO_PERF_CONTRATO.PRELIMINAR)) {
			solicitud.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.EN_PROCESO);
			solicitud.setFechaHoraPresentacion(new Date());
		}

		String expediente = null;

		List<Archivo> archivosRegistrados = obtenerArchivosRegistrados(listaSolicitudSeccion, solicitud, contexto);
		List<File> archivosAlfresco = null;

		archivosAlfresco = archivoService.obtenerArchivoContenidoPerfCont(archivosRegistrados, solicitud, contexto);
		expediente = enviarArchivos(archivosAlfresco, solicitud, contexto);

		if (expediente == null) {
			throw new ValidacionException("No se pudo enviar los archivos a Alfresco");
		}

		solicitud.setNumeroExpediente(expediente);
		AuditoriaUtil.setAuditoriaActualizacion(solicitud, contexto);
		SicoesSolicitud solicitudDB = sicoesSolicitudDao.save(solicitud);

		if (solicitudDB == null) {
			throw new ValidacionException("No se pudo actualizar la solicitud con id " + solicitud.getIdSolicitud());
		}

		sicoesSolicitudSeccionService.actualizarSolicitudDetalle(listaSolicitudSeccion, contexto);
		sicoesSolicitudSeccionService.actualizarProcesoRevisionPersonal(solicitudDB, contexto);

		return expediente;
	}


//	@Override
//	public Page<SicoesSolicitud> listarSolicitudesPresentacion(String estado, String tipoSolicitud, Contexto contexto, Pageable pageable) {
//		logger.info("listarSolicitudesObservadas");
//		Supervisora supervisora = supervisoraService.obtenerSupervisoraXRUCNoProfesional(contexto.getUsuario().getCodigoRuc());
//		return sicoesSolicitud.obtenerxTipoEstadoProceso(tipoSolicitud, estado, supervisora.getIdSupervisora(), pageable);
//	}


	@Override
	public SicoesSolicitud guardar(SicoesSolicitud model, Contexto contexto) {
		return null;
	}

	@Override
	public SicoesSolicitud obtener(Long aLong, Contexto contexto) {
		return sicoesSolicitudDao.findById(aLong).orElse(null);
	}

	@Override
	public void eliminar(Long aLong, Contexto contexto) {

	}

	private Date calcularFechaFin(Date fechaPresentacion, Long dia) {
		return sigedApiConsumer.calcularFechaFin(fechaPresentacion, dia, Constantes.DIAS_HABILES);
	}

	private List<Archivo> obtenerArchivosRegistrados(List<SicoesSolicitudSeccion> listaSolicitudSeccion, SicoesSolicitud solicitud, Contexto contexto) {
		List<Archivo> archivosRegistrados = new ArrayList<>();
		List<SicoesSolicitudSeccion> listaSolicitudSeccionFinal = new ArrayList<>();

		if (solicitud.getIdSolicitudPadre() != null) {
			listaSolicitudSeccionFinal = listaSolicitudSeccion.stream().filter(s -> !s.getProcRevision().equals("1")).collect(Collectors.toList());
		} else {
			listaSolicitudSeccionFinal.addAll(listaSolicitudSeccion);
		}

		for (SicoesSolicitudSeccion requisito: listaSolicitudSeccionFinal) {
			if (requisito.getArchivo() == null) {
				continue;
			}
			if (solicitud.getIdSolicitudPadre() != null) {
				requisito.getArchivo().setNombreReal("subsanacion_"+requisito.getArchivo().getNombreReal());
			}
			archivosRegistrados.add(requisito.getArchivo());
		}

		List<SicoesTdSolPerConSec> secciones = seccionesService.obtenerSeccionesXSolicitud(solicitud.getIdSolicitud());

		for (SicoesTdSolPerConSec seccion: secciones) {
			if (!"1".equals(seccion.getFlConPersonal())) {
				continue;
			}

			List<Long> seccionesIds = new ArrayList<>();
			seccionesIds.add(seccion.getIdSolPerConSec());

			List<SicoesSolicitudSeccion> requisitosPersonal = sicoesSolicitudSeccionService.obtenerRequisitosPorSeccionFinalizacion(seccionesIds);
			List<SicoesSolicitudSeccion> requisitosPersonalFinal = new ArrayList<>();

			if (solicitud.getIdSolicitudPadre() != null) {
				requisitosPersonalFinal = requisitosPersonal.stream().filter(s -> !s.getProcRevision().equals("1")).collect(Collectors.toList());
			} else {
				requisitosPersonalFinal.addAll(requisitosPersonal);
			}

			List<Long> requisitosIds = new ArrayList<>();
			requisitosPersonalFinal.forEach(requisito -> requisitosIds.add(requisito.getIdSolicitudSeccion()));
			List<Archivo> archivosPersonal = archivoService.obtenerArchivosPorRequisitos(requisitosIds, contexto);

			for (Archivo archivo: archivosPersonal) {
//				if (archivo.getArchivo() == null) {
//					continue;
//				}
				if (solicitud.getIdSolicitudPadre() != null) {
					archivo.setNombreReal("subsanacion_"+archivo.getNombreReal());
				}
				archivosRegistrados.add(archivo);
			}
		}

		return archivosRegistrados;
	}

	private Archivo generarReportePresentacion(SicoesSolicitud solicitud, String codExpediente, Contexto contexto) {
		Archivo archivo = new Archivo();
		if (solicitud.getIdSolicitudPadre() != null) {
			archivo.setNombre("Subsanacion_Perfeccionamiento_" + solicitud.getSupervisora().getNumeroDocumento() + ".pdf");
			archivo.setNombreReal("Subsanacion_Perfeccionamiento_" + solicitud.getSupervisora().getNumeroDocumento() + ".pdf");
		} else {
			archivo.setNombre("Solicitud_Perfeccionamiento_" + solicitud.getSupervisora().getNumeroDocumento() + ".pdf");
			archivo.setNombreReal("Solicitud_Perfeccionamiento_" + solicitud.getSupervisora().getNumeroDocumento() + ".pdf");
		}
		archivo.setTipo("application/pdf");
		archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,Constantes.LISTADO.TIPO_ARCHIVO.SOLICITUD_PERFECCIONAMIENTO));
		//si se desea guardar en bd descomentar y asignar a un nuevo campo
		// archivo.setIdNuevoCampo(solicitud.getIdSolicitud());

		List<Long> seccionesIds = new ArrayList<>();
		List<Long> requisitosIds = new ArrayList<>();
		SicoesSolicitud solicitudDB = sicoesSolicitudDao.findById(solicitud.getIdSolicitud()).orElse(null);
		SicoesSolicitud solicitudJasper = new SicoesSolicitud();

		solicitudJasper.setIdSolicitud(solicitudDB.getIdSolicitud());
		solicitudJasper.setNumeroExpediente(solicitudDB.getNumeroExpediente() == null ? codExpediente : solicitudDB.getNumeroExpediente());
		solicitudJasper.setFechaHoraPresentacion(solicitudDB.getFechaHoraPresentacion());

		// Cargar supervisora
		Supervisora supervisora = initializeAndUnproxy(solicitudDB.getSupervisora());
		if (supervisora.getNombreRazonSocial() == null) {
			supervisora.setNombreRazonSocial(supervisora.getNombres()+ " " + supervisora.getApellidoPaterno() + " " + supervisora.getApellidoMaterno());
		}
		ListadoDetalle pais = initializeAndUnproxy(supervisora.getPais());
		ListadoDetalle tipoDocumento = initializeAndUnproxy(supervisora.getTipoDocumento());
		supervisora.setPais(pais);
		supervisora.setTipoDocumento(tipoDocumento);
		SupervisoraRepresentante representante = supervisoraRepresentanteService.obtenerXIdSupervisora(solicitudDB.getSupervisora().getIdSupervisora(), contexto);
		ListadoDetalle tipoDocumentoRepresentante = initializeAndUnproxy(representante.getTipoDocumento());
		representante.setTipoDocumento(tipoDocumentoRepresentante);
		supervisora.setSupervisoraRepresentante(representante);

		solicitudJasper.setSupervisora(supervisora);

		// Cargar propuesta
		Propuesta propuesta = initializeAndUnproxy(solicitudDB.getPropuesta());
		ProcesoItem procesoItem = initializeAndUnproxy(propuesta.getProcesoItem());
		Proceso proceso = initializeAndUnproxy(procesoItem.getProceso());
		ListadoDetalle sector = initializeAndUnproxy(proceso.getSector());
		ListadoDetalle subsector = initializeAndUnproxy(proceso.getSubsector());
		proceso.setSector(sector);
		proceso.setSector(subsector);
		procesoItem.setProceso(proceso);
		propuesta.setProcesoItem(procesoItem);
		solicitudJasper.setPropuesta(propuesta);

		List<SicoesTdSolPerConSec> secciones = seccionesService.obtenerSeccionesXSolicitud(solicitud.getIdSolicitud());
		secciones.forEach(seccion -> seccionesIds.add(seccion.getIdSolPerConSec()));

		List<SicoesSolicitudSeccion> requisitos = sicoesSolicitudSeccionService.obtenerRequisitosPorSecciones(seccionesIds);
		requisitos.forEach(requisito -> requisitosIds.add(requisito.getIdSolicitudSeccion()));

		List<Archivo> archivos = archivoService.obtenerArchivosPorRequisitos(requisitosIds, contexto);
		requisitos.forEach(requisito -> {
			archivos.forEach(archivoRequisito -> {
				if (requisito.getIdSolicitudSeccion().equals(archivoRequisito.getIdSeccionRequisito())) {
					requisito.setNombreArchivo(archivoRequisito.getNombreReal());
					requisito.setPeso((long) (archivoRequisito.getPeso() / 8.0 / 1024.0));
					requisito.setNroFolio(archivoRequisito.getNroFolio());
				}
			});
		});

		// Filtrar requisitos sin archivos asociados
		requisitos.removeIf(requisito ->
				archivos.stream()
						.noneMatch(archivoRequisito ->
								requisito.getIdSolicitudSeccion().equals(archivoRequisito.getIdSeccionRequisito())
						)
		);

		if (solicitud.getIdSolicitudPadre() != null) {
			requisitos.removeIf(requisito -> requisito.getProcRevision().equals("1"));
		}

		solicitudJasper.setRequisitos(requisitos);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JasperPrint print = null;
		InputStream appLogo = null;
		InputStream osinermingLogo = null;

		try {
			File jrxmlFile = new File(pathJasper + "Formato_22.jrxml");
			File jrxmlFile2 = new File(pathJasper + "Formato_22_Presentados.jrxml");
			JasperReport jasperReport2 = getJasperCompilado(jrxmlFile2);

			Map<String, Object> params = new HashMap<>();
			logger.info("Subreport_DIR: {}", pathJasper);
			params.put("SUBREPORT_DIR", pathJasper);

			appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
			osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
			params.put("P_LOGO_APP", appLogo);
			params.put("P_LOGO_OSINERGMIN", osinermingLogo);
			List<SicoesSolicitud> solicitudes = new ArrayList<>();
			solicitudes.add(solicitudJasper);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
			JasperReport jasperReport = getJasperCompilado(jrxmlFile);
			print = JasperFillManager.fillReport(jasperReport, params, ds);
			output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, output);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ValidacionException("Error al generar reporte de presentación", e);
		} finally {
			close(appLogo);
			close(osinermingLogo);
		}

		byte[] outBytes = output.toByteArray();
		archivo.setPeso(outBytes.length * 1L);
		archivo.setNroFolio(1L);
		archivo.setContenido(outBytes);
		return archivo;

	}

	private Archivo generarReporteEvaluacion(SicoesSolicitud solicitud, Contexto contexto) {
		Archivo archivo = new Archivo();
		if (solicitud.getIdSolicitudPadre() != null) {
			archivo.setNombre("Respuesta_Subsanacion_Perfeccionamiento_" + solicitud.getSupervisora().getNumeroDocumento() + ".pdf");
			archivo.setNombreReal("Respuesta_Subsanacion_Perfeccionamiento_" + solicitud.getSupervisora().getNumeroDocumento () + ".pdf");
		} else {
			archivo.setNombre("Respuesta_Perfeccionamiento_" + solicitud.getSupervisora().getNumeroDocumento() + ".pdf");
			archivo.setNombreReal("Respuesta_Perfeccionamiento_" + solicitud.getSupervisora().getNumeroDocumento () + ".pdf");
		}
		archivo.setTipo("application/pdf");
		archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,Constantes.LISTADO.TIPO_ARCHIVO.SOLICITUD_PERFECCIONAMIENTO));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		JasperPrint print = null;
		InputStream appLogo = null;
		InputStream osinermingLogo = null;

		try {
			System.setProperty("net.sf.jasperreports.debug", "true");

			File jrxmlFile = new File(pathJasper + "Formato_23.jrxml");
			File jrxmlFile2 = new File(pathJasper + "Formato_23_Doc.jrxml");
			File jrxmlFile3 = new File(pathJasper + "Formato_23_Per.jrxml");
			File jrxmlFile4 = new File(pathJasper + "Formato_23_Fiel.jrxml");
			File jrxmlFile5 = new File(pathJasper + "Formato_23_Monto.jrxml");
			JasperReport jasperReport2 = getJasperCompilado(jrxmlFile2);
			JasperReport jasperReport3 = getJasperCompilado(jrxmlFile3);
			JasperReport jasperReport4 = getJasperCompilado(jrxmlFile4);
			JasperReport jasperReport5 = getJasperCompilado(jrxmlFile5);

			Map<String, Object> params = new HashMap<>();
			logger.info("Subreport_DIR: {}", pathJasper);
			params.put("SUBREPORT_DIR", pathJasper);

			appLogo = new FileInputStream(pathJasper + "logo-sicoes.png");
			osinermingLogo = new FileInputStream(pathJasper + "logo-osinerming.png");
			params.put("P_LOGO_APP", appLogo);
			params.put("P_LOGO_OSINERGMIN", osinermingLogo);
			List<SicoesSolicitud> solicitudes = new ArrayList<>();
			solicitudes.add(solicitud);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(solicitudes);
			JasperReport jasperReport = getJasperCompilado(jrxmlFile);
			print = JasperFillManager.fillReport(jasperReport, params, ds);
			output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, output);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ValidacionException("Error al generar reporte de presentación", e);
		} finally {
			close(appLogo);
			close(osinermingLogo);
		}

		byte[] outBytes = output.toByteArray();
		archivo.setPeso(outBytes.length * 1L);
		archivo.setNroFolio(1L);
		archivo.setContenido(outBytes);
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

	private String enviarArchivos(List<File> archivosAlfresco, SicoesSolicitud solicitud, Contexto contexto) throws Exception {
		ExpedienteInRO expedienteInRO = null;
		String codExpediente = null;
		if (solicitud.getIdSolicitudPadre() != null) {
			SicoesSolicitud solicitudPadre = sicoesSolicitudDao.findById(solicitud.getIdSolicitudPadre()).orElse(null);
			codExpediente = solicitudPadre.getNumeroExpediente();
		}
		expedienteInRO = crearExpedientePresentacion(solicitud, codExpediente, contexto);
		ExpedienteOutRO expedienteOutRO = null;
		DocumentoOutRO documentoSubsanacionOutRO = null;
		if (codExpediente != null) {
			documentoSubsanacionOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
		} else {
			expedienteOutRO = sigedApiConsumer.crearExpediente(expedienteInRO, archivosAlfresco);
		}

		if (codExpediente != null) {
			if (1 != documentoSubsanacionOutRO.getResultCode()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NOMBRE_DUPLICADO);
			}
		} else {
			if (1 != expedienteOutRO.getResultCode()) {
				throw new ValidacionException(expedienteOutRO.getMessage());
			}
		}

		codExpediente = codExpediente != null ? codExpediente : expedienteOutRO.getCodigoExpediente();

		if (false) {
			throw new ValidacionException("Error al guardar archivos en SIGED");
		} else {
			expedienteInRO = crearExpedientePresentacion(solicitud, codExpediente, contexto);
			Archivo formato_22 = null;

			formato_22 = generarReportePresentacion(solicitud, codExpediente, contexto);

			archivosAlfresco = new ArrayList<>();
			File file = null;

			File dir = new File(pathTemporal + File.separator+"temporales" + File.separator + solicitud.getIdSolicitud());
			if (!dir.exists()) {
				dir.mkdirs();
			}
			file = new File(
					pathTemporal + File.separator + "temporales" + File.separator + solicitud.getIdSolicitud() + File.separator + formato_22.getNombre());
			FileUtils.writeByteArrayToFile(file, formato_22.getContenido());
			formato_22.setContenido(Files.readAllBytes(file.toPath()));

			archivosAlfresco.add(file);
			DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
			if (documentoOutRO.getResultCode() != 1) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_AGREGAR_DOCUMENTOS,
						codExpediente);
			}
		}
		return codExpediente;
	}

	private ExpedienteInRO crearExpedientePresentacion(SicoesSolicitud solicitud, String codExpediente, Contexto contexto) {
		return crearExpediente(solicitud,
				Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear")), codExpediente, contexto);
	}

	private ExpedienteInRO crearExpediente(SicoesSolicitud solicitud, Integer codigoTipoDocumento, String codExpediente, Contexto contexto) {
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
		if (solicitud.getNumeroExpediente() != null || codExpediente != null) {
			if (codExpediente != null) {
				expediente.setNroExpediente(codExpediente);
			} else {
				expediente.setNroExpediente(solicitud.getNumeroExpediente());
			}
		}
//		if (solicitud.getPersona() != null) {
		documento.setAsunto(String.format("Solicitud de Perfeccionamiento de Contrato - Inscripción"));
//		}

		documento.setAppNameInvokes(SIGLA_PROYECTO);
//		ListadoDetalle tipoDocumento = listadoDetalleService
//				.obtener(solicitud.getPersona().getTipoDocumento().getIdListadoDetalle(), contexto);
		cs.setCodigoTipoIdentificacion(1);
		if (cs.getCodigoTipoIdentificacion() == 1) {
			cs.setNombre(solicitud.getSupervisora().getNombreRazonSocial());
			cs.setApellidoPaterno("-");
			cs.setApellidoMaterno("-");
		}

//		if (solicitud.getRepresentante() != null) {
//			cs.setRepresentanteLegal(
//					solicitud.getRepresentante().getNombres() + " " + solicitud.getRepresentante().getApellidoPaterno()
//							+ " " + solicitud.getRepresentante().getApellidoMaterno());
//		}
		cs.setRazonSocial(solicitud.getSupervisora().getNombreRazonSocial());
		cs.setNroIdentificacion(solicitud.getSupervisora().getNumeroDocumento());
		cs.setTipoCliente(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
		cliente.add(cs);
		d.setDireccion(solicitud.getSupervisora().getDireccion());
		d.setDireccionPrincipal(true);
		d.setEstado(env.getProperty("crear.expediente.parametros.direccion.estado").charAt(0));
		d.setTelefono(solicitud.getSupervisora().getTelefono1());
		if (solicitud.getSupervisora().getCodigoDistrito() != null) {
			d.setUbigeo(Integer.parseInt(solicitud.getSupervisora().getCodigoDistrito()));
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
//		String stecnico=env.getProperty("crear.expediente.parametros.tipo.documento.informe.tecnico");
//		int vtenico= Integer.parseInt(stecnico);
//
//		if(vtenico==codigoTipoDocumento) {
//			if(codigoUsuario!=null) {
//				documento.setUsuarioCreador(codigoUsuario);
//				documento.setFirmante(codigoUsuario);
//			}
//		}
//		if(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.respuesta.solicitud.pn"))==codigoTipoDocumento) {
//
//			documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
//			documento.setFirmante(Integer.parseInt(env.getProperty("siged.firmante.informe.respuesta.id.usuario")));
//
//		}else{
		documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
//		}
		logger.info("DOC_EXPEDIENTE--- :"+documento);
		logger.info("EXPEDIENTE_REGISTRO_PERF :"+expediente);
		return expediente;
	}

	public static <T> T initializeAndUnproxy(T entity) {
		if (entity == null) {
			throw new
					NullPointerException("Entity passed for initialization is null");
		}

		Hibernate.initialize(entity);
		if (entity instanceof HibernateProxy) {
			entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
					.getImplementation();
		}
		return entity;
	}

	private String obtenerResultadoEvaluacion(String condigoEvaluado) {
		String resultado = "";
		switch (condigoEvaluado) {
			case "1":
				resultado = "Cumple";
				break;
			case "2":
				resultado = "No Cumple";
				break;
			case "3":
				resultado = "Observado";
				break;
			default:
				resultado = "No Evaluado";
				break;
		}
		return resultado;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void marcarSolicitudPerfContRespuesta(Long idSolicitud, Date fecha, Contexto contexto) {
		SicoesSolicitud solicitudDB = sicoesSolicitudDao.findById(idSolicitud).orElse(null);
		if (solicitudDB != null) {
			ListadoDetalle plazoSubsanar = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.SUBSANAR_PERFECCIONAMIENTO);
			solicitudDB.setFechaPlazoSubsanacion(calcularFechaFin(fecha, Long.parseLong(plazoSubsanar.getValor())));
			AuditoriaUtil.setAuditoriaActualizacion(solicitudDB, contexto);
			sicoesSolicitudDao.save(solicitudDB);

			if (Constantes.ESTADO_PROCESO_PERF_CONTRATO.OBSERVADO.equals(solicitudDB.getEstadoProcesoSolicitud())) {
				SicoesSolicitud solicitidSubsanacion = sicoesSolicitudDao.obtenerPorIdSolicitudPadre(idSolicitud);
				if (solicitidSubsanacion != null) {
					solicitidSubsanacion.setFechaPlazoSubsanacion(calcularFechaFin(fecha, Long.parseLong(plazoSubsanar.getValor())));
					AuditoriaUtil.setAuditoriaActualizacion(solicitidSubsanacion, contexto);
					sicoesSolicitudDao.save(solicitidSubsanacion);
				}
			}

			if (Constantes.ESTADO_PROCESO_PERF_CONTRATO.CONCLUIDO.equals(solicitudDB.getEstadoProcesoSolicitud())) {
				BaseOutRO baseOutRO = sigedApiConsumer.archivarExpediente(solicitudDB.getNumeroExpediente(),
						env.getProperty("mensaje.archivamiento"));
				if (baseOutRO.getErrorCode() != 1) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_ARCHIVAR_EXPEDIENTE,
							baseOutRO.getMessage());
				}
			}

		}
	}

}
