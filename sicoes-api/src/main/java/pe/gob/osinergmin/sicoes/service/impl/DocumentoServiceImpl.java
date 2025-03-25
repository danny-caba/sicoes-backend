package pe.gob.osinergmin.sicoes.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.env.Environment;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.Documento;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.model.PerfilAprobador;
import pe.gob.osinergmin.sicoes.model.PerfilDivision;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.dto.InfoSectorActividadDTO;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.AsignacionDao;
import pe.gob.osinergmin.sicoes.repository.DocumentoDao;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.OtroRequisitoDao;
import pe.gob.osinergmin.sicoes.repository.PerfilAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.PerfilDivisionDao;
import pe.gob.osinergmin.sicoes.repository.SolicitudDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.AsignacionService;
import pe.gob.osinergmin.sicoes.service.DocumentoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.OtroRequisitoService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.PageUtilImpl;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.ValidacionUtil;

@Service
public class DocumentoServiceImpl implements DocumentoService {

	Logger logger = LogManager.getLogger(DocumentoServiceImpl.class);

	@Autowired
	private DocumentoDao documentoDao;

	@Autowired
	private AsignacionDao asignacionDao;

	@Autowired
	private PerfilDivisionDao perfilDivisionDao;

	@Autowired
	private ListadoDetalleDao listadoDetalleDao;

	@Autowired
	private PerfilAprobadorDao perfilAprobadorDao;

	@Autowired
	private ArchivoService archivoService;

	@Autowired
	private ArchivoDao archivoDao;

	@Autowired
	private OtroRequisitoDao otroRequisitoDao;

	@Autowired
	private SolicitudDao solicitudDao;

	@Autowired
	private ListadoDetalleService listadoDetalleService;

	@Autowired
	private SolicitudService solicitudService;

	@Autowired
	private AsignacionService asignacionService;

	@Autowired
	private OtroRequisitoService otroRequisitoService;

	@Autowired
	private Environment env;

	private final EntityManager entityManager;

	public DocumentoServiceImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Documento obtener(Long idDocumento, Contexto contexto) {
		Documento doc = documentoDao.obtener(idDocumento);
		List<Archivo> list = archivoService.buscar(null, idDocumento, null, contexto);
		if (list != null && !list.isEmpty()) {
			doc.setArchivo(list.get(0));
		}
		return doc;
	}

	@Override
	public Page<Documento> buscar(Long idSolicitud, Pageable pageable, Contexto contexto) {
		Solicitud solicitud = solicitudDao.obtener(idSolicitud);
		List<Asignacion> asignacionesUsuarioSolicitud = asignacionDao.obtenerAsignacionTecnico(idSolicitud,
				contexto.getUsuario().getIdUsuario());
		Pageable pageableMaximo = PageRequest.of(0, Integer.parseInt(env.getProperty("maximo.paginas")));
		Page<Documento> documentoTotal = null;
		Page<Documento> documentos = null;
		if (solicitud.getPersona().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.JURIDICA)
				&& !asignacionesUsuarioSolicitud.isEmpty()
				&& asignacionesUsuarioSolicitud.stream().anyMatch(asignacion -> (Constantes.LISTADO.TIPO_EVALUADOR.TECNICO).equals(asignacion.getTipo().getCodigo()))) {
			Log.info("ES PERSONA JURÍDICA ");
			List<Long> subsectores = solicitudDao.obtenerSubsectoresXUsuarioSolicitud(solicitud.getSolicitudUuid(),
					contexto.getUsuario().getIdUsuario());
			documentoTotal = documentoDao.buscarSubSectorAsignado(idSolicitud, subsectores, pageableMaximo);
			documentos = documentoDao.buscarSubSectorAsignado(idSolicitud, subsectores, pageable);

		} else {
			Log.info("NO ES PERSONA JURÍDICA ");
			List<Long> subsectores = solicitudDao.obtenerSubsectoresXUsuarioSolicitud(solicitud.getSolicitudUuid(),
					contexto.getUsuario().getIdUsuario());
			documentoTotal = documentoDao.buscar(idSolicitud, pageableMaximo);
			documentos = documentoDao.buscar(idSolicitud, pageable);
		}

		PageUtilImpl<Documento> page = new PageUtilImpl<Documento>(documentos.getContent(), documentos.getPageable(),
				documentos.getTotalElements());
		page.setTotalMonto(documentoDao.sumarMontoTotal(idSolicitud));
		if (page.getTotalMonto() == null) {
			calcularExperienciaTotal(page, documentoTotal.getContent());
		}
		for (Documento documento : documentos) {
			List<Archivo> list = archivoService.buscar(null, documento.getIdDocumento(), null, contexto);
			if (list != null && !list.isEmpty()) {
				documento.setArchivo(list.get(0));
			}
		}
		return page;
	}

	private static void calcularExperienciaTotal(PageUtilImpl page, List<Documento> documentos) {
		Date fechaMinima = null;
		Date fechaMaxima = null;
		Date fechaEvaluar = null;
		int dias = 0;
		if (documentos == null || documentos.isEmpty())
			return;
		for (Documento documento : documentos) {
			Date fechaInicioAux = documento.getFechaInicio();
			Date fechaFinAux = null;
			if (documento.getFechaFin() != null) {
				fechaFinAux = documento.getFechaFin();
			} else {
				fechaFinAux = new Date();
			}
			if (fechaMinima == null || DateUtil.esMenorIgual(fechaInicioAux, fechaMinima)) {
				fechaMinima = fechaInicioAux;
			}
			if (fechaMaxima == null || DateUtil.esMayorIgual(fechaFinAux, fechaMaxima)) {
				fechaMaxima = fechaFinAux;
			}
		}
		fechaEvaluar = fechaMinima;
		while (DateUtil.esMenorIgual(fechaEvaluar, fechaMaxima)) {
			for (Documento documento : documentos) {
				Date fechaInicioAux = documento.getFechaInicio();
				Date fechaFinAux = null;
				if (documento.getFechaFin() != null) {
					fechaFinAux = documento.getFechaFin();
				} else {
					fechaFinAux = new Date();
				}
				if (DateUtil.esMayorIgual(fechaEvaluar, fechaInicioAux)
						&& DateUtil.esMenorIgual(fechaEvaluar, fechaFinAux)) {
					dias++;
					break;
				}
			}

			fechaEvaluar = DateUtil.sumarDia(fechaEvaluar, 1L);
		}
		int anio = dias / 365;
		int mes = (dias - (anio * 365)) / 30;
		int dia = (dias - (anio * 365) - (mes * 30));
		page.setAnio(anio);
		page.setMes(mes);
		page.setDia(dia);
	}

	public boolean validarJuridicoPostor(ListadoDetalle tipoPersona) {

		boolean isJuridica = Constantes.LISTADO.TIPO_PERSONA.JURIDICA.equals(tipoPersona.getCodigo());
		boolean isPerNatPostor = Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR.equals(tipoPersona.getCodigo());
		boolean isExtrajero = Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO.equals(tipoPersona.getCodigo());

		return isJuridica || isPerNatPostor || isExtrajero;
	}

	@Transactional(rollbackFor = Exception.class)
	public Documento guardar(Documento documento, Contexto contexto) {
		Solicitud sol = solicitudService.obtener(documento.getSolicitud().getSolicitudUuid(), contexto);
		if (!validarJuridicoPostor(sol.getPersona().getTipoPersona())) {
			if (StringUtils.isBlank(documento.getNombreEntidad())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ENTIDAD_NO_ENVIADO);
			}
		} else {
			if (documento.getPais() == null || documento.getPais().getIdListadoDetalle() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.PAIS_NO_ENVIADO);
			}
			if (StringUtils.isBlank(documento.getCodigoContrato())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.CODIGO_NO_ENVIADO);
			}
			if (documento.getCuentaConformidad() == null
					|| documento.getCuentaConformidad().getIdListadoDetalle() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.CONFORMIDAD_NO_ENVIADO);
			}
			if (documento.getMontoContrato() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_NO_ENVIADO);
			}
			if (documento.getMontoContratoSol() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_SOL_NO_ENVIADO);
			}
			/*
			 * if(documento.getMontoContrato()<documento.getMontoContratoSol()) { throw new
			 * ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_FACTURADO_MAYOR); }
			 */
			if (documento.getTipoCambio() == null || documento.getTipoCambio().getIdListadoDetalle() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_CAMBIO_NO_ENVIADO);
			}

			if (documento.getSubSectorDoc() == null || documento.getSubSectorDoc().getIdListadoDetalle() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_CAMBIO_NO_ENVIADO);
			}

			if (documento.getActividadArea() == null || documento.getActividadArea().getIdListadoDetalle() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_CAMBIO_NO_ENVIADO);
			}
			//
			// if (tipoCambio.getCodigo().equals(Constantes.LISTADO.TIPO_CAMBIO.DOLARES) &&
			// documento.getMontoTipoCambio()==null) {
			// throw new
			// ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_CAMBIO_NO_ENVIADO);
			// }
			Double montoContratado = null;
			ListadoDetalle tipoCambio = listadoDetalleService.obtener(documento.getTipoCambio().getIdListadoDetalle(),
					contexto);
			logger.info("DOLAR12" + tipoCambio.getCodigo());
			if (Constantes.LISTADO.TIPO_CAMBIO.DOLARES.equals(tipoCambio.getCodigo())) {
				if (documento.getMontoTipoCambio() == null || documento.getMontoTipoCambio() <= 0) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_CAMBIO_NO_ENVIADO);
				}
				montoContratado = documento.getMontoContrato() * documento.getMontoTipoCambio();

			} else {
				montoContratado = documento.getMontoContrato();
			}

			if (montoContratado < documento.getMontoContratoSol()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_FACTURADO_MAYOR);
			}
		}
		if (documento.getSolicitud() == null || documento.getSolicitud().getSolicitudUuid() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		if (documento.getTipoDocumento() == null || documento.getTipoDocumento().getIdListadoDetalle() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPODOC_NO_ENVIADO);
		}
		if (StringUtils.isBlank(documento.getDescripcionContrato())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.DESCRIPCION_NO_ENVIADO);
		}

		if (StringUtils.isBlank(documento.getNumeroDocumento())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.D_DOC_NO_ENVIADO);
		}
		if (documento.getFechaInicio() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_INICIO_NO_ENVIADO);
		}
		if ("0".equals(documento.getFlagVigente())) {
			if (documento.getFechaFin() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_FIN_NO_ENVIADO);
			}
			if (DateUtil.esMayorIgual(documento.getFechaInicio(), documento.getFechaFin())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_INICIO_MAYOR);
			}
			if (StringUtils.isBlank(documento.getDuracion())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.DURACION_NO_ENVIADO);
			}
		}
		ValidacionUtil.validarPresentacion(contexto, sol);
		Documento documentoBD = documentoDao.obtener(documento.getIdDocumento());
		if (documentoBD != null) {
			documentoBD.setTipoDocumento(
					listadoDetalleService.obtener(documento.getTipoDocumento().getIdListadoDetalle(), contexto));
			documentoBD.setTipoCambio(documento.getTipoCambio());
			documentoBD.setCuentaConformidad(documento.getCuentaConformidad());
			documentoBD.setTipoIdentificacion(documento.getTipoIdentificacion());
			documentoBD.setPais(documento.getPais());
			documentoBD.setActividadArea(documento.getActividadArea());
			documentoBD.setSubSectorDoc(documento.getSubSectorDoc());
			documentoBD.setNumeroDocumento(documento.getNumeroDocumento());
			documentoBD.setNombreEntidad(documento.getNombreEntidad());
			documentoBD.setCodigoContrato(documento.getCodigoContrato());
			documentoBD.setDescripcionContrato(documento.getDescripcionContrato());
			documentoBD.setFechaInicio(documento.getFechaInicio());
			documentoBD.setFechaFin(documento.getFechaFin());
			documentoBD.setDuracion(documento.getDuracion());
			documentoBD.setFlagVigente(documento.getFlagVigente());
			documentoBD.setFechaConformidad(documento.getFechaConformidad());
			documentoBD.setMontoContrato(documento.getMontoContrato());
			documentoBD.setMontoTipoCambio(documento.getMontoTipoCambio());
			documentoBD.setMontoContratoSol(documento.getMontoContratoSol());
			if (Constantes.LISTADO.ESTADO_SOLICITUD.OBSERVADO.equals(sol.getEstado().getCodigo())
					&& Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO
							.equals(documentoBD.getEvaluacion().getCodigo())) {
				documentoBD.setEvaluacion(
						listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO,
								Constantes.LISTADO.RESULTADO_EVALUACION.RESPONDIDO));
			}
			
			if(documentoDao.existeDocumentos(sol.getIdSolicitud(), documento.getTipoDocumento().getIdListadoDetalle(), 
					documento.getCodigoContrato(), documentoBD.getIdDocumento()) > 0) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.NRO_DE_CONTRATO_YA_EXISTE);
			}
			
		} else {
			documento.getSolicitud().setIdSolicitud(sol.getIdSolicitud());
			documento.setEvaluacion(
					listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO,
							Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR));
			if(sol.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)
					&& (sol.getTipoSolicitud().getCodigo().equals(Constantes.LISTADO.TIPO_SOLICITUD.INSCRIPCION)
					|| sol.getTipoSolicitud().getCodigo().equals(Constantes.LISTADO.TIPO_SOLICITUD.SUBSANACION))
					&& Optional.ofNullable(sol.getIdSolicitudPadre()).isPresent()) {
				documento.setEstado(
						listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_DOCUMENTO.CODIGO,
								Constantes.LISTADO.ESTADO_DOCUMENTO.ACTUAL));
			}
			documentoBD = documento;
			
			if(documentoDao.existeDocumento(sol.getIdSolicitud(), documento.getTipoDocumento().getIdListadoDetalle(), 
					documento.getCodigoContrato()) > 0) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.NRO_DE_CONTRATO_YA_EXISTE);
			}
		}
		documentoBD.setFlagSiged(Constantes.FLAG.INACTIVO);
		AuditoriaUtil.setAuditoriaRegistro(documento, contexto);
		documentoBD = documentoDao.save(documentoBD);
		if (documento.getArchivo() == null || documento.getArchivo().getIdArchivo() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ARCHIVO_NO_ENVIADO);
		}
		Archivo archivoBD = archivoDao.obtener(documento.getArchivo().getIdArchivo());
		archivoService.asociarArchivo(documentoBD, archivoBD, contexto);
		actualizarNombreArchivo(documentoBD.getSolicitud().getIdSolicitud(), contexto);

		// ACTUALIZAR OTRO REQUISITO
		if (validarJuridicoPostor(sol.getPersona().getTipoPersona())) {
			String tipoPerfil = Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL;
			List<OtroRequisito> otrosRequisitosPerfil = otroRequisitoDao
					.obtenerOtrosRequisitosPerfil(documentoBD.getSolicitud().getIdSolicitud(), tipoPerfil);
	
			List<OtroRequisito> actividadAreasEncontrados = otrosRequisitosPerfil.stream()
					.filter(dd -> dd.getActividadArea() != null
							&& dd.getActividadArea().getCodigo().equals(documento.getActividadArea().getCodigo()))
					.collect(Collectors.toList());
	
			if (actividadAreasEncontrados.isEmpty()) {
				List<OtroRequisito> otroRequisitoSubSector = otrosRequisitosPerfil.stream().filter(
						dd -> dd.getSubsector().equals(documento.getSubSectorDoc()) && dd.getActividadArea() == null)
						.collect(Collectors.toList());
				if (otroRequisitoSubSector.isEmpty()) {
					ListadoDetalle subSector = listadoDetalleDao
							.obtener(documentoBD.getSubSectorDoc().getIdListadoDetalle());
					OtroRequisito reqClone = otrosRequisitosPerfil.get(0);
					OtroRequisito or = new OtroRequisito();
					Long idSector = listadoDetalleDao.idSuperiod(documentoBD.getSubSectorDoc().getIdListadoDetalle());
					ListadoDetalle sector = listadoDetalleDao.obtener(idSector);
					or.setTipo(reqClone.getTipo());
					or.setTipoRequisito(reqClone.getTipoRequisito());
					or.setSector(sector);
					or.setSubsector(documentoBD.getSubSectorDoc());
					or.setFlagActivo(reqClone.getFlagActivo());
					or.setEvaluacion(reqClone.getEvaluacion());
					or.setSolicitud(reqClone.getSolicitud());
					or.setFlagSiged(reqClone.getFlagSiged());
					or.setActividadArea(documentoBD.getActividadArea());
					AuditoriaUtil.setAuditoriaRegistro(or, contexto);
					otroRequisitoDao.save(or);
				} else {
					OtroRequisito or = otroRequisitoSubSector.get(0);
					or.setActividadArea(documentoBD.getActividadArea());
					otroRequisitoDao.save(or);
				}
	
			}

			actualizarRequisitos(documentoBD.getSolicitud().getIdSolicitud(), contexto, documentoBD.getActividadArea(),
				documentoBD.getSubSectorDoc());
		}
		return documentoBD;

	}

	private List<InfoSectorActividadDTO> obtenerSubSectorActividadUnicos(List<Documento> documentos) {
		return documentos.stream().filter(doc -> doc.getSubSectorDoc() != null && doc.getActividadArea() != null)
				.map(doc -> new InfoSectorActividadDTO(doc.getSubSectorDoc(), doc.getActividadArea())).distinct()
				.collect(Collectors.toList());
	}

	private void actualizarNombreArchivo(Long idSolicitud, Contexto contexto) {
		List<Documento> documentos = buscar(idSolicitud, contexto);
		int indice = 1;
		logger.info("Nombres de Documento");
		for (Documento documento : documentos) {
			List<Archivo> archivosDocumento = archivoService.buscar(null, documento.getIdDocumento(), null, contexto);
			for (Archivo archivoAux : archivosDocumento) {
				archivoAux.setCorrelativo(Long.parseLong(indice + ""));
				if (Constantes.LISTADO.RESULTADO_EVALUACION.RESPONDIDO.equals(documento.getEvaluacion().getCodigo())) {
					archivoAux.setVersion(2L);
				} else {
					archivoAux.setVersion(1L);
				}
				String nombre = archivoAux.getTipoArchivo().getValor() + "_" + documento.getTipoDocumento().getValor()
						+ "_" + StringUtils.leftPad(archivoAux.getCorrelativo() + "", 3, '0') + "_v"
						+ archivoAux.getVersion() + ".pdf";
				archivoAux.setNombre(nombre);
				logger.info(archivoAux.getCorrelativo() + ":" + archivoAux.getNombre());
				archivoService.guardar(archivoAux, contexto);
			}
			indice++;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, Contexto contexto) {
		Documento doc = documentoDao.getOne(id);
		ListadoDetalle actividadArea = doc.getActividadArea();
		ListadoDetalle subSector = doc.getSubSectorDoc();
		Solicitud sol = documentoDao.getOne(id).getSolicitud();
		archivoService.eliminarIdDocumento(id, contexto);
		actualizarRequisitos(sol.getIdSolicitud(), contexto, actividadArea, subSector);
		documentoDao.deleteById(id);

	}

	@Override
	public List<Documento> buscar(Long idSolicitud, Contexto contexto) {
		return documentoDao.buscar(idSolicitud);
	}

	@Override
	public List<Documento> buscarPresentacion(Long idSolicitud, Contexto contexto) {
		return documentoDao.buscarPresentacion(idSolicitud);
	}

	@Override
	public Documento evalular(Documento documento, Contexto contexto) {
		// FIMEX VALIDAR DATOS DE ENTRADA
		Documento documentoBD = documentoDao.obtener(documento.getIdDocumento());
		documentoBD.setEvaluacion(documento.getEvaluacion());
		documentoBD.setObservacion(documento.getObservacion());
		AuditoriaUtil.setAuditoriaRegistro(documentoBD, contexto);
		documentoDao.save(documentoBD);
		solicitudService.actualizarProceso(documentoBD.getSolicitud(), Constantes.LISTADO.TIPO_EVALUADOR.TECNICO,
				contexto);
		return documentoBD;
	}

	public static long diasEntreDosFechas(Date fechaDesde, Date fechaHasta) {
		long startTime = fechaDesde.getTime();
		long endTime = fechaHasta.getTime();
		long diasDesde = (long) Math.floor(startTime / (1000 * 60 * 60 * 24)); // convertimos a dias, para que no
																				// afecten cambios de hora
		long diasHasta = (long) Math.floor(endTime / (1000 * 60 * 60 * 24)); // convertimos a dias, para que no afecten
																				// cambios de hora
		long dias = diasHasta - diasDesde;

		return dias;
	}

	// public static void main(String[] args) {
	// int dias=diasEntreDosFechas(S)
	// }

	public void copiarDocumentosUltimaSolicitud(String solicitudUuidUltima, String solicitudUuid, Contexto contexto) {
		Long idSolicitudUltima = solicitudService.obtenerId(solicitudUuidUltima);
		Long idSolicitud = solicitudService.obtenerId(solicitudUuid);
		List<Documento> listaDocumentosUltimaSolicitud = documentoDao.buscar(idSolicitudUltima);
		List<Documento> listaDocumentosSolicitud = documentoDao.buscar(idSolicitud);

		if (listaDocumentosSolicitud == null || listaDocumentosSolicitud.isEmpty()) {
			if (listaDocumentosUltimaSolicitud != null) {
				Documento documentoNuevo = null;
				for (Documento documento : listaDocumentosUltimaSolicitud) {
					documentoNuevo = new Documento();
					Solicitud solicitud = new Solicitud();
					solicitud.setIdSolicitud(idSolicitud);
					documentoNuevo.setSolicitud(solicitud);
					documentoNuevo.setTipoDocumento(documento.getTipoDocumento());
					documentoNuevo.setNumeroDocumento(documento.getNumeroDocumento());
					documentoNuevo.setDescripcionContrato(documento.getDescripcionContrato());
					documentoNuevo.setFechaInicio(documento.getFechaInicio());
					documentoNuevo.setFechaFin(documento.getFechaFin());
					documentoNuevo.setDuracion(documento.getDuracion());
					documentoNuevo.setFlagVigente(documento.getFlagVigente());
					documentoNuevo.setEvaluacion(documento.getEvaluacion());
					documentoNuevo.setPais(documento.getPais());
					documentoNuevo.setTipoCambio(documento.getTipoCambio());
					documentoNuevo.setNombreEntidad(documento.getNombreEntidad());
					documentoNuevo.setFlagSiged(documento.getFlagSiged());
					documentoNuevo.setUsuCreacion(contexto.getUsuario().getUsuario());
					documentoNuevo.setIpCreacion(contexto.getIp());
					documentoNuevo.setFecCreacion(new Date());
					documentoNuevo = documentoDao.save(documentoNuevo);

					Pageable pageable = null;
					Page<Archivo> listaArchivos = archivoDao.buscarArchivo(Constantes.LISTADO.TIPO_ARCHIVO.EXPERIENCIA,
							idSolicitudUltima, pageable);
					if (listaArchivos != null) {
						Archivo archivoNuevo = null;
						for (Archivo archivo : listaArchivos) {
							archivoNuevo = new Archivo();
							archivoNuevo.setIdDocumento(documentoNuevo.getIdDocumento());
							archivoNuevo.setEstado(archivo.getEstado());
							archivoNuevo.setTipoArchivo(archivo.getTipoArchivo());
							archivoNuevo.setIdSolicitud(idSolicitud);
							archivoNuevo.setNombre(archivo.getNombre());
							archivoNuevo.setNombreReal(archivo.getNombreReal());
							archivoNuevo.setNombreAlFresco(archivo.getNombreAlFresco());
							archivoNuevo.setCodigo(UUID.randomUUID().toString());
							archivoNuevo.setTipo(archivo.getTipo());
							archivoNuevo.setCorrelativo(archivo.getCorrelativo());
							archivoNuevo.setVersion(archivo.getVersion());
							archivoNuevo.setNroFolio(archivo.getNroFolio());
							archivoNuevo.setPeso(archivo.getPeso());
							archivoNuevo.setUsuCreacion(contexto.getUsuario().getUsuario());
							archivoNuevo.setIpCreacion(contexto.getIp());
							archivoNuevo.setFecCreacion(new Date());
							archivoNuevo = archivoDao.save(archivoNuevo);
						}
					}
				}
			}
		}
	}

	@Override
	public Documento asignarEvaluadorPerfil(Documento doc, List<Asignacion> asignaciones, Contexto contexto) {
		ListadoDetalle actArea = doc.getActividadArea();
		// ACTUALIZA OTRO REQUISITO CON EL USUARIO DE LA ASIGNACIÓN Y ACTUALIZA SU FECHA
		List<OtroRequisito> otroRequisitos = otroRequisitoDao.listarOtroRequisitoXSubSectorXArea(
				doc.getSubSectorDoc().getIdListadoDetalle(), doc.getSolicitud().getIdSolicitud(),
				doc.getActividadArea().getIdListadoDetalle());
		if (!otroRequisitos.isEmpty()) {
			for (OtroRequisito otroRequisito : otroRequisitos) {
				otroRequisito.setUsuario(asignaciones.get(0).getUsuario());
				otroRequisito.setFechaAsignacion(new Date());
				OtroRequisito otroRequisitoRpt = otroRequisitoService.guardar(otroRequisito, contexto);

				asignaciones.get(0).setOtroRequisito(otroRequisitoRpt);
			}
		}
		List<Asignacion> listaAsiganaciones = asignacionDao.obtenerAsignaciones(doc.getSolicitud().getIdSolicitud());

		for (Asignacion asignacion : listaAsiganaciones) {
			if (asignacion.getGrupo() != null && asignacion.getGrupo().getIdListado() == null) {
				ListadoDetalle grupo = new ListadoDetalle();
				grupo = listadoDetalleDao.obtener(asignacion.getGrupo().getIdListadoDetalle());
				asignacion.setGrupo(grupo);
			}
		}

		asignacionService.guardar(asignaciones, contexto);

		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudUuid(doc.getSolicitud().getSolicitudUuid());
		PerfilDivision perfilDivision = perfilDivisionDao
				.obtenerPerfilDivisionPorIdPerfil(doc.getActividadArea().getIdListadoDetalle());
		List<PerfilAprobador> listaPerfilesAprobadores = perfilAprobadorDao
				.obtenerPerfilAprobadorPorIdPerfil(doc.getActividadArea().getIdListadoDetalle());

		if (listaPerfilesAprobadores != null && !listaPerfilesAprobadores.isEmpty()) {
			PerfilAprobador perfilAprobadores = listaPerfilesAprobadores.get(0);
			boolean registrarAsignacion = true;
			//boolean registrarG2 = true;

			for (Asignacion asignacion : listaAsiganaciones) {
				/*
				 * if (asignacion.getOtroRequisito() != null) { PerfilDivision
				 * perfilDivisionAprobadores =
				 * perfilDivisionDao.obtenerPerfilDivisionPorIdPerfil(asignacion.
				 * getOtroRequisito().getPerfil().getIdListadoDetalle()); if
				 * (perfilDivision.getDivision().getIdDivision().equals(
				 * perfilDivisionAprobadores.getDivision().getIdDivision())) {
				 * registrarAsignacion = false; } }
				 */

				if (asignacion.getGrupo() != null) {
					if (asignacion.getGrupo().getCodigo().equals(Constantes.LISTADO.GRUPOS.G1) && !asignacion
							.getUsuario().getIdUsuario().equals(perfilAprobadores.getAprobadorG1().getIdUsuario())) {
						registrarAsignacion = true;
						//registrarG2 = true;
						break;
					}

					// 01.

					if (asignacion.getGrupo().getCodigo().equals(Constantes.LISTADO.GRUPOS.G2) && !asignacion
							.getUsuario().getIdUsuario().equals(perfilAprobadores.getAprobadorG2().getIdUsuario())) {
						registrarAsignacion = true;
						//registrarG2 = true;
						break;
					}

					if (asignacion.getGrupo().getCodigo().equals(Constantes.LISTADO.GRUPOS.G1) && asignacion
							.getUsuario().getIdUsuario().equals(perfilAprobadores.getAprobadorG1().getIdUsuario())) {
						registrarAsignacion = false;
						//registrarG2 = false;
						//break;
					}

					if (asignacion.getGrupo().getCodigo().equals(Constantes.LISTADO.GRUPOS.G2) && asignacion
							.getUsuario().getIdUsuario().equals(perfilAprobadores.getAprobadorG2().getIdUsuario())) {

						registrarAsignacion = false;
						//registrarG2 = false;
						//break;
					}
				}
			}

			if (registrarAsignacion) {
				ListadoDetalle tipo = new ListadoDetalle();
				tipo.setCodigo(Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO);
				List<ListadoDetalle> tipos = listadoDetalleService
						.listarListadoDetallePorCoodigo(Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO, contexto);
				tipo.setIdListadoDetalle(tipos.get(0).getIdListadoDetalle());

				// Diferente de GSM
				if (!perfilDivision.getDivision().getIdDivision().equals(Constantes.DIVISION.IDENTIFICADOR.ID_GSM)) {
					// Asigna aprobador g1 automaticamente
					ListadoDetalle grupo1 = new ListadoDetalle();
					List<ListadoDetalle> grupos1 = listadoDetalleService
							.listarListadoDetallePorCoodigo(Constantes.LISTADO.GRUPOS.G1, contexto);
					grupo1.setIdListadoDetalle(grupos1.get(0).getIdListadoDetalle());
					Asignacion asignacion1 = new Asignacion();
					asignacion1.setSolicitud(solicitud);
					asignacion1.setTipo(tipo);
					asignacion1.setUsuario(perfilAprobadores.getAprobadorG1());
					asignacion1.setGrupo(grupo1);
					asignacionService.guardarAprobador(asignacion1, contexto);

					// Asigna aprobador g2 automaticamente
					//if (registrarG2) {
						ListadoDetalle grupo2 = new ListadoDetalle();
						List<ListadoDetalle> grupos2 = listadoDetalleService
								.listarListadoDetallePorCoodigo(Constantes.LISTADO.GRUPOS.G2, contexto);
						grupo2.setIdListadoDetalle(grupos2.get(0).getIdListadoDetalle());
						Asignacion asignacion2 = new Asignacion();
						asignacion2.setSolicitud(solicitud);
						asignacion2.setTipo(tipo);
						asignacion2.setUsuario(perfilAprobadores.getAprobadorG2());
						asignacion2.setGrupo(grupo2);
						asignacionService.guardarAprobador(asignacion2, contexto);
					//}
				}

				// Igual a GSM
				if (perfilDivision.getDivision().getIdDivision().equals(Constantes.DIVISION.IDENTIFICADOR.ID_GSM)) {
					// Asigna aprobador g2 automaticamente
					ListadoDetalle grupo2 = new ListadoDetalle();
					List<ListadoDetalle> grupos2 = listadoDetalleService
							.listarListadoDetallePorCoodigo(Constantes.LISTADO.GRUPOS.G2, contexto);
					grupo2.setIdListadoDetalle(grupos2.get(0).getIdListadoDetalle());

					Asignacion asignacion2 = new Asignacion();
					asignacion2.setSolicitud(solicitud);
					asignacion2.setTipo(tipo);
					asignacion2.setUsuario(perfilAprobadores.getAprobadorG2());
					asignacion2.setGrupo(grupo2);
					asignacionService.guardarAprobador(asignacion2, contexto);
				}
			}
		} else {
			logger.error(
					"No se econtro relacion de aprobadores a pefil : " + doc.getActividadArea().getIdListadoDetalle());
		}

		return doc;
	}

	public void actualizarRequisitos(Long solicitudId, Contexto contexto, ListadoDetalle actividadArea,
			ListadoDetalle subSector) {
		List<Documento> listaDocumentos = documentoDao.buscar(solicitudId);

		// Obtener OtrosRequisitos relacionados con la solicitud
		List<OtroRequisito> otrosRequisitos = entityManager.createQuery(
				"SELECT o FROM OtroRequisito o left join fetch o.tipo t WHERE o.solicitud.idSolicitud = :solicitudId and t.codigo='"
						+ Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL + "' ",
				OtroRequisito.class).setParameter("solicitudId", solicitudId).getResultList();
		List<Documento> documentosByArea = listaDocumentos.stream()
				.filter(dd -> dd.getActividadArea() != null
						&& dd.getActividadArea().getCodigo().equals(actividadArea.getCodigo()))
				.collect(Collectors.toList());

		if (documentosByArea.isEmpty()) {
			List<OtroRequisito> actividadAreasEncontrados = otrosRequisitos.stream()
					.filter(dd -> dd.getActividadArea() != null
							&& dd.getActividadArea().getCodigo().equals(actividadArea.getCodigo()))
					.collect(Collectors.toList());
			List<OtroRequisito> subsectoresEncontrados = otrosRequisitos.stream()
					.filter(dd -> dd.getSubsector() != null
							&& dd.getSubsector().getCodigo().equals(subSector.getCodigo()))
					.collect(Collectors.toList());
			if(subsectoresEncontrados.size() == actividadAreasEncontrados.size()) {
				for (OtroRequisito or : actividadAreasEncontrados) {
					or.setActividadArea(null);
					otroRequisitoDao.save(or);
				}
			} else if (subsectoresEncontrados.size() > actividadAreasEncontrados.size()) {
				for (OtroRequisito or : actividadAreasEncontrados) {
					or.setSolicitud(null);
					otroRequisitoDao.save(or);
				}
			}
			
		}

	}

	private static class SubSectorActividadArea {
		private final ListadoDetalle subsector;
		private final ListadoDetalle actividadArea;

		public SubSectorActividadArea(ListadoDetalle subsector, ListadoDetalle actividadArea) {
			this.subsector = subsector;
			this.actividadArea = actividadArea;
		}

		public ListadoDetalle getSubsector() {
			return subsector;
		}

		public ListadoDetalle getActividadArea() {
			return actividadArea;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			SubSectorActividadArea that = (SubSectorActividadArea) o;
			return (subsector == null ? that.subsector == null : subsector.equals(that.subsector))
					&& (actividadArea == null ? that.actividadArea == null : actividadArea.equals(that.actividadArea));
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + (subsector != null ? subsector.hashCode() : 0);
			result = 31 * result + (actividadArea != null ? actividadArea.hashCode() : 0);
			return result;
		}
	}
}