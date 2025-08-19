package pe.gob.osinergmin.sicoes.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.DictamenEvaluacion;
import pe.gob.osinergmin.sicoes.model.Documento;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.model.PerfilAprobador;
import pe.gob.osinergmin.sicoes.model.PerfilDivision;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.AsignacionDao;
import pe.gob.osinergmin.sicoes.repository.DocumentoDao;
import pe.gob.osinergmin.sicoes.repository.EvaluadorAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.OtroRequisitoDao;
import pe.gob.osinergmin.sicoes.repository.PerfilAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.PerfilDivisionDao;
import pe.gob.osinergmin.sicoes.repository.SolicitudDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.AsignacionService;
import pe.gob.osinergmin.sicoes.service.DictamenEvaluacionService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.OtroRequisitoService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Constantes.LISTADO.TIPO_PERSONA;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.ValidacionUtil;

@Service
public class OtroRequisitoServiceImpl implements OtroRequisitoService {

	Logger logger = LogManager.getLogger(OtroRequisitoServiceImpl.class);

	@Autowired
	private OtroRequisitoDao otroRequisitoDao;

	@Autowired
	private ListadoDetalleService listadoDetalleService;

	@Autowired
	private ArchivoService archivoService;

	@Autowired
	private SolicitudService solicitudService;

	@Autowired
	private SolicitudDao solicitudDao;

	@Autowired
	private DictamenEvaluacionService dictamenEvaluacionService;

	@Autowired
	AsignacionService asignacionService;

	@Autowired
	private PerfilDivisionDao perfilDivisionDao;

	@Autowired
	private ArchivoDao archivoDao;

	@Autowired
	PerfilAprobadorDao perfilAprobadorDao;

	@Autowired
	AsignacionDao asignacionDao;

	@Autowired
	ListadoDetalleDao listadoDetalleDao;

	@Autowired
	DocumentoDao documentoDao;
	
	@Autowired
	private NotificacionService notificacionService; 

	@Override
	public OtroRequisito obtener(Long idOtroRequisito, Contexto contexto) {
		return otroRequisitoDao.getOne(idOtroRequisito);
	}

	@Override
	public Page<OtroRequisito> buscar(String tipo, Pageable pageable, Contexto contexto) {
		Page<OtroRequisito> otroRequisitos = otroRequisitoDao.buscar(pageable);
		return otroRequisitos;
	}

	@Transactional(rollbackFor = Exception.class)
	public OtroRequisito guardar(OtroRequisito otroRequisito, Contexto contexto) {
		Solicitud solicitudBD = null;
		if (otroRequisito.getSolicitud() == null && otroRequisito.getSolicitud().getSolicitudUuid() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		solicitudBD = solicitudService.obtener(otroRequisito.getSolicitud().getSolicitudUuid(), contexto);
		ListadoDetalle tipoPersona = solicitudBD.getPersona().getTipoPersona();
		if (otroRequisito.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL)) {

			boolean isJuridica = Constantes.LISTADO.TIPO_PERSONA.JURIDICA.equals(tipoPersona.getCodigo());
			boolean isPerNatPostor = Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR.equals(tipoPersona.getCodigo());
			boolean isExtrajero = Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO.equals(tipoPersona.getCodigo());

			if (isJuridica || isPerNatPostor || isExtrajero) {
				if (otroRequisito.getSector() == null && otroRequisito.getSector().getIdListadoDetalle() == null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_SECTOR_NO_ENVIADO);
				}
				if (otroRequisito.getSubsector() == null
						&& otroRequisito.getSubsector().getIdListadoDetalle() == null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_SUBSECTOR_NO_ENVIADO);
				}

			} else {
				if (otroRequisito.getSector() == null && otroRequisito.getSector().getIdListadoDetalle() == null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_SECTOR_NO_ENVIADO);
				}
				if (otroRequisito.getSubsector() == null
						&& otroRequisito.getSubsector().getIdListadoDetalle() == null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_SUBSECTOR_NO_ENVIADO);
				}
				if (otroRequisito.getActividad() == null
						&& otroRequisito.getActividad().getIdListadoDetalle() == null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_ACTIVIDAD_NO_ENVIADO);
				}
				if (otroRequisito.getSubCategoria() == null
						&& otroRequisito.getSubCategoria().getIdListadoDetalle() == null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_SUBCATEGORIA_NO_ENVIADO);
				}
				if (otroRequisito.getSubCategoria() == null
						&& otroRequisito.getUnidad().getIdListadoDetalle() == null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_UNIDAD_NO_ENVIADO);
				}
				if (otroRequisito.getPerfil() == null && otroRequisito.getPerfil().getIdListadoDetalle() == null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.P_PERFIL_NO_ENVIADO);
				}
				if (otroRequisitoDao.exiteRequisito(solicitudBD.getIdSolicitud(),
						otroRequisito.getPerfil().getIdListadoDetalle(), otroRequisito.getIdOtroRequisito()) > 0) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.PERFIL_YA_EXISTE);
				}
			}
		}
		ValidacionUtil.validarPresentacion(contexto, solicitudBD);

		OtroRequisito otroRequisitoBD = otroRequisitoDao.obtener(otroRequisito.getIdOtroRequisito());
		if (otroRequisitoBD != null) {
		
			if (otroRequisitoBD.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL)) {
				List<Documento> documentosPerfil = documentoDao.buscarXSolicitudXSubSector(
						otroRequisitoBD.getSolicitud().getIdSolicitud(),
						otroRequisitoBD.getSubsector().getIdListadoDetalle());
				if (!otroRequisitoBD.getSubsector().getIdListadoDetalle()
						.equals(otroRequisito.getSubsector().getIdListadoDetalle()) && !documentosPerfil.isEmpty()) {
					throw new ValidacionException(
							Constantes.CODIGO_MENSAJE.DOCUMENTO_EXISTE_SOLICITUD_SECTOR_SUBSECTOR);
				}
			 
			
			if (!otroRequisitoBD.getSubsector().getIdListadoDetalle()
					.equals(otroRequisito.getSubsector().getIdListadoDetalle()) && !documentosPerfil.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_EXISTE_SOLICITUD_SECTOR_SUBSECTOR);
			}}
			otroRequisitoBD.setTipoRequisito(otroRequisito.getTipoRequisito());
			otroRequisitoBD.setFlagElectronico(otroRequisito.getFlagElectronico());
			otroRequisitoBD.setFlagFirmaDigital(otroRequisito.getFlagFirmaDigital());
			otroRequisitoBD.setFlagActivo(otroRequisito.getFlagActivo());
			otroRequisitoBD.setFechaExpedicion(otroRequisito.getFechaExpedicion());
			otroRequisitoBD.setSector(otroRequisito.getSector());
			otroRequisitoBD.setSubsector(otroRequisito.getSubsector());
			otroRequisitoBD.setActividad(otroRequisito.getActividad());
			otroRequisitoBD.setUnidad(otroRequisito.getUnidad());
			otroRequisitoBD.setSubCategoria(otroRequisito.getSubCategoria());
			otroRequisitoBD.setPerfil(otroRequisito.getPerfil());

			boolean isJuridica = Constantes.LISTADO.TIPO_PERSONA.JURIDICA.equals(tipoPersona.getCodigo());
			boolean isPerNatPostor = Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR.equals(tipoPersona.getCodigo());
			boolean isExtrajero = Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO.equals(tipoPersona.getCodigo());
			boolean isPerNatPropuesto= Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO.equals(tipoPersona.getCodigo());
			
			if (isJuridica || isPerNatPostor || isExtrajero) {

				if (otroRequisito.getSector() != null) {
					DictamenEvaluacion dictamenXSol = dictamenEvaluacionService.obtenerXSol(
							solicitudBD.getIdSolicitud(), otroRequisito.getSector().getIdListadoDetalle(), contexto);
					if (dictamenXSol == null) {
						DictamenEvaluacion dictamen = new DictamenEvaluacion();
						dictamen.setSector(otroRequisito.getSector());
						dictamen.setSolicitud(solicitudBD);
						dictamen.setMontoFacturado((double) 0);
						dictamenEvaluacionService.guardar(dictamen, contexto);
					}
					
					int tipo = 91;
					Long count = otroRequisitoDao.existeSector(solicitudBD.getIdSolicitud(), otroRequisito.getSector().getIdListadoDetalle(), tipo);
					if (count > 0) {
					    throw new ValidacionException(Constantes.CODIGO_MENSAJE.EXISTE_SECTOR_SUBSECTOR);
					}
				}
			} else if (isPerNatPropuesto) {
				if (otroRequisito.getSector() != null) {
					int tipo = 91;
					Long count = otroRequisitoDao.existeSector(solicitudBD.getIdSolicitud(), otroRequisito.getSector().getIdListadoDetalle(), tipo);
					if (count > 0) {
					    throw new ValidacionException(Constantes.CODIGO_MENSAJE.EXISTE_SECTOR_SUBSECTOR);
					}
				}
			}

		} else {

			boolean isJuridica = Constantes.LISTADO.TIPO_PERSONA.JURIDICA.equals(tipoPersona.getCodigo());
			boolean isPerNatPostor = Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR.equals(tipoPersona.getCodigo());
			boolean isExtrajero = Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO.equals(tipoPersona.getCodigo());
			boolean isPerNatPropuesto= Constantes.LISTADO.TIPO_PERSONA.PN_PERS_PROPUESTO.equals(tipoPersona.getCodigo());
			if (isJuridica || isPerNatPostor || isExtrajero) {
				
				if (otroRequisito.getSector() != null) {
					DictamenEvaluacion dictamenXSol = dictamenEvaluacionService.obtenerXSol(
							solicitudBD.getIdSolicitud(), otroRequisito.getSector().getIdListadoDetalle(), contexto);
					if (dictamenXSol == null) {
						DictamenEvaluacion dictamen = new DictamenEvaluacion();
						dictamen.setSector(otroRequisito.getSector());
						dictamen.setSolicitud(solicitudBD);
						dictamen.setMontoFacturado((double) 0);
						dictamenEvaluacionService.guardar(dictamen, contexto);
					}
					
					if (otroRequisitoDao.exiteRequisito(solicitudBD.getIdSolicitud(), otroRequisito.getSector().getIdListadoDetalle(), 
							otroRequisito.getSubsector().getIdListadoDetalle(), otroRequisito.getIdOtroRequisito()) > 0) {
						throw new ValidacionException(Constantes.CODIGO_MENSAJE.SECTOR_YA_EXISTE);
					}
					
					int tipo = 91;
					Long count = otroRequisitoDao.existeSector(solicitudBD.getIdSolicitud(), otroRequisito.getSector().getIdListadoDetalle(), tipo);
					if (count > 0) {
					    throw new ValidacionException(Constantes.CODIGO_MENSAJE.EXISTE_SECTOR_SUBSECTOR);
					}
				}
			} else if (isPerNatPropuesto) {
				if (otroRequisito.getSector() != null) {
					int tipo = 91;
					Long count = otroRequisitoDao.existeSector(solicitudBD.getIdSolicitud(), otroRequisito.getSector().getIdListadoDetalle(), tipo);
					if (count > 0) {
					    throw new ValidacionException(Constantes.CODIGO_MENSAJE.EXISTE_SECTOR_SUBSECTOR);
					}
				}
			}
			otroRequisito.setTipo(listadoDetalleService.obtenerListadoDetalle(
					Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.CODIGO, otroRequisito.getTipo().getCodigo()));
			otroRequisito.setEvaluacion(
					listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO,
							Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR));
			otroRequisitoBD = otroRequisito;
		}
		otroRequisitoBD.setFlagSiged(Constantes.FLAG.INACTIVO);
		AuditoriaUtil.setAuditoriaRegistro(otroRequisitoBD, contexto);
		if (Constantes.LISTADO.ESTADO_SOLICITUD.OBSERVADO.equals(solicitudBD.getEstado().getCodigo())
				&& Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO
						.equals(otroRequisito.getEvaluacion().getCodigo())) {
			otroRequisitoBD.setEvaluacion(
					listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO,
							Constantes.LISTADO.RESULTADO_EVALUACION.RESPONDIDO));
		}
		otroRequisitoBD.getSolicitud().setIdSolicitud(solicitudBD.getIdSolicitud());
		otroRequisitoBD = otroRequisitoDao.save(otroRequisitoBD);
		if (otroRequisito.getArchivo() != null) {
			archivoService.asociarArchivo(otroRequisitoBD, otroRequisito.getArchivo(), contexto);
		} else {
			archivoService.eliminarIdOtroRequisito(otroRequisitoBD.getIdOtroRequisito(), contexto);
		}
		actualizarNombreArchivo(solicitudBD, contexto);

		boolean isJuridica = Constantes.LISTADO.TIPO_PERSONA.JURIDICA.equals(tipoPersona.getCodigo());
		boolean isPerNatPostor = Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR.equals(tipoPersona.getCodigo());
		boolean isExtrajero = Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO.equals(tipoPersona.getCodigo());
		if (isJuridica || isPerNatPostor || isExtrajero) {
			List<OtroRequisito> listEnergetico = otroRequisitoDao.listarOtroRequisitoXSector(
					Constantes.LISTADO.SECTOR.SECTOR_ENERGETICO, solicitudBD.getIdSolicitud());
			List<OtroRequisito> listMinero = otroRequisitoDao
					.listarOtroRequisitoXSector(Constantes.LISTADO.SECTOR.SECTOR_MINERO, solicitudBD.getIdSolicitud());

			if (listEnergetico.isEmpty()) {
				DictamenEvaluacion dictamenEnergetico = dictamenEvaluacionService.obtenerXCodigoSector(
						solicitudBD.getIdSolicitud(), Constantes.LISTADO.SECTOR.SECTOR_ENERGETICO, contexto);
				if (dictamenEnergetico != null) {
					dictamenEvaluacionService.eliminarXCodigoSector(Constantes.LISTADO.SECTOR.SECTOR_ENERGETICO,
							solicitudBD.getIdSolicitud(), contexto);
				}
			}
			if (listMinero.isEmpty()) {
				DictamenEvaluacion dictamenMinero = dictamenEvaluacionService.obtenerXCodigoSector(
						solicitudBD.getIdSolicitud(), Constantes.LISTADO.SECTOR.SECTOR_MINERO, contexto);
				if (dictamenMinero != null) {
					dictamenEvaluacionService.eliminarXCodigoSector(Constantes.LISTADO.SECTOR.SECTOR_MINERO,
							solicitudBD.getIdSolicitud(), contexto);
				}
			}

		}
		return otroRequisitoBD;
	}

	private void actualizarNombreArchivo(Solicitud solicitud, Contexto contexto) {

		List<OtroRequisito> otrosRequisitos = listarOtroRequisito(
				Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, solicitud.getIdSolicitud());
		int indice = 1;
		logger.info("Nombres de Otro requisito");
		for (OtroRequisito otroRequisito : otrosRequisitos) {
			List<Archivo> archivosOtros = archivoService.buscar(null, null, otroRequisito.getIdOtroRequisito(),
					contexto);
			for (Archivo archivoAux : archivosOtros) {
				archivoAux.setCorrelativo(Long.parseLong(indice + ""));
				if (Constantes.LISTADO.RESULTADO_EVALUACION.RESPONDIDO
						.equals(otroRequisito.getEvaluacion().getCodigo())) {
					archivoAux.setVersion(2L);
				} else {
					archivoAux.setVersion(1L);
				}
				String nombre = "";
				if (Constantes.LISTADO.TIPO_ARCHIVO.DNI.equals(archivoAux.getTipoArchivo().getCodigo())) {
					nombre = archivoAux.getTipoArchivo().getValor() + "_" + solicitud.getPersona().getNumeroDocumento()
							+ "_v" + archivoAux.getVersion() + ".pdf";
				} else if (Constantes.LISTADO.TIPO_ARCHIVO.RUC.equals(archivoAux.getTipoArchivo().getCodigo())) {
					nombre = archivoAux.getTipoArchivo().getValor() + "_" + solicitud.getPersona().getCodigoRuc() + "_v"
							+ archivoAux.getVersion() + ".pdf";
				} else if (Constantes.LISTADO.TIPO_ARCHIVO.JURADA.equals(archivoAux.getTipoArchivo().getCodigo())) {
					if (solicitud.getUsuario().isPesonaJuridica()) {
						nombre = archivoAux.getTipoArchivo().getValor() + "_PJ_" + solicitud.getPersona().getCodigoRuc()
								+ "_v" + archivoAux.getVersion() + ".pdf";
					} else {
						nombre = archivoAux.getTipoArchivo().getValor() + "_PN_"
								+ solicitud.getPersona().getNumeroDocumento() + "_v" + archivoAux.getVersion() + ".pdf";
					}
				} else if (Constantes.LISTADO.TIPO_ARCHIVO.REGISTRAL.equals(archivoAux.getTipoArchivo().getCodigo())) {
					nombre = archivoAux.getTipoArchivo().getValor() + "_PJ_" + solicitud.getPersona().getCodigoRuc()
							+ "_v" + archivoAux.getVersion() + ".pdf";
				} else if (Constantes.LISTADO.TIPO_ARCHIVO.ACREDITACION
						.equals(archivoAux.getTipoArchivo().getCodigo())) {
					nombre = archivoAux.getTipoArchivo().getValor() + "_PJ_" + solicitud.getPersona().getCodigoRuc()
							+ "_v" + archivoAux.getVersion() + ".pdf";
				} else if (Constantes.LISTADO.TIPO_ARCHIVO.CONSTANCIA_APROBACION
						.equals(archivoAux.getTipoArchivo().getCodigo())) {
					nombre = archivoAux.getTipoArchivo().getValor() + "_v" + archivoAux.getVersion() + ".pdf";
				} else if (Constantes.LISTADO.TIPO_ARCHIVO.CONSTITUCION_EMPRESA
						.equals(archivoAux.getTipoArchivo().getCodigo())) {
					nombre = archivoAux.getTipoArchivo().getValor() + "_v" + archivoAux.getVersion() + ".pdf";
				} else {
					System.out.println("IdArchivo  :" + archivoAux.getIdArchivo());
				}

				archivoAux.setNombre(nombre);
				logger.info(archivoAux.getCorrelativo() + ":" + archivoAux.getNombre());
				archivoService.guardar(archivoAux, contexto);
			}
			indice++;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, Contexto contexto) {

		OtroRequisito requisito = obtener(id, contexto);
		if (Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL.equals(requisito.getTipo().getCodigo())) {
			Solicitud solicitud = solicitudService.obtenerSinAsignados(requisito.getSolicitud().getIdSolicitud(),
					contexto);
			List<Documento> documentosPerfil = documentoDao.buscarXSolicitudXSubSector(
					requisito.getSolicitud().getIdSolicitud(), requisito.getSubsector().getIdListadoDetalle());
			if (!documentosPerfil.isEmpty()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_EXISTE_SOLICITUD_SECTOR_SUBSECTOR);
			}
			boolean isJuridica = Constantes.LISTADO.TIPO_PERSONA.JURIDICA
					.equals(solicitud.getPersona().getTipoPersona().getCodigo());
			boolean isPerNatPostor = Constantes.LISTADO.TIPO_PERSONA.PN_POSTOR
					.equals(solicitud.getPersona().getTipoPersona().getCodigo());
			boolean isExtrajero = Constantes.LISTADO.TIPO_PERSONA.EXTRANJERO
					.equals(solicitud.getPersona().getTipoPersona().getCodigo());
			if (isJuridica || isPerNatPostor || isExtrajero) {

				if (Constantes.LISTADO.SECTOR.SECTOR_MINERO.equals(requisito.getSector().getCodigo())) {
					dictamenEvaluacionService.eliminarXSector(requisito, contexto);
				} else {
					List<OtroRequisito> list = otroRequisitoDao.listarOtroRequisitoXSector(
							requisito.getSector().getIdListadoDetalle(), solicitud.getIdSolicitud());
					if (list.size() == 1) {
						dictamenEvaluacionService.eliminarXSector(requisito, contexto);
					}
				}
			}
		}

		archivoService.eliminarIdOtroRequisito(id, contexto);
		otroRequisitoDao.deleteById(id);
	}

	@Override
	public Page<OtroRequisito> listarOtroRequisito(String tipo, String solicitudUuid, Pageable pageable,
	        Contexto contexto) {
	    Long idSolicitud = solicitudDao.obtenerId(solicitudUuid);
	    Page<OtroRequisito> otroRequisitos = otroRequisitoDao.listarOtroRequisito(idSolicitud, tipo, pageable);

	    if (contexto.getUsuario().getTipoPersona() != null) {
	        boolean isJuridica = contexto.getUsuario().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.JURIDICA);
	        boolean isNatural = contexto.getUsuario().getTipoPersona().getCodigo().equals(Constantes.LISTADO.TIPO_PERSONA.NATURAL);

	        if ((isJuridica || isNatural) && tipo.equals(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL)) {
	            // Verificar si todos los OtroRequisito tienen actividad no nula
	            boolean allHaveActivity = otroRequisitos.getContent().stream()
	                                    .allMatch(otroRequisito -> otroRequisito.getUnidad() == null);

	            if (allHaveActivity) {
	                otroRequisitos = agruparYObtenerMaximos(otroRequisitos);
	            }
	        }
	    } else if (tipo.equals(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL)) {
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            Date fechaCreacion = sdf.parse("2024-10-16");
	            otroRequisitos = otroRequisitoDao.listarOtroRequisitoEvaluardor(idSolicitud, tipo, fechaCreacion, pageable);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	    }

	    for (OtroRequisito otroRequisito : otroRequisitos) {
	        List<Archivo> list = archivoService.buscar(null, null, otroRequisito.getIdOtroRequisito(), contexto);
	        if (list != null && !list.isEmpty()) {
	            otroRequisito.setArchivo(list.get(0));
	        }
	    }
	    return otroRequisitos;
	}



	@Override
	public List<OtroRequisito> listarOtroRequisitoArchivo(String tipo, Long idSolicitud, Contexto contexto) {
		List<OtroRequisito> otroRequisitos = otroRequisitoDao.listarOtroRequisito(tipo, idSolicitud);
		for (OtroRequisito otroRequisito : otroRequisitos) {
			List<Archivo> list = archivoService.buscar(null, null, otroRequisito.getIdOtroRequisito(), contexto);
			if (list != null && !list.isEmpty()) {
				otroRequisito.setArchivo(list.get(0));
			}
		}
		return otroRequisitos;
	}

	@Override
	public List<OtroRequisito> listarOtroRequisitoArchivoPresentacion(String tipo, Long idSolicitud,
			Contexto contexto) {
		List<OtroRequisito> otroRequisitos = otroRequisitoDao.listarOtroRequisitoEstado(tipo, idSolicitud);
		for (OtroRequisito otroRequisito : otroRequisitos) {
			List<Archivo> list = archivoService.buscar(null, null, otroRequisito.getIdOtroRequisito(), contexto);
			if (list != null && !list.isEmpty()) {
				otroRequisito.setArchivo(list.get(0));
			}
		}
		return otroRequisitos;
	}

	@Override
	public List<OtroRequisito> listarOtroRequisito(String tipo, Long idSolicitud) {
		List<OtroRequisito> otroRequisitos = otroRequisitoDao.listarOtroRequisito(tipo, idSolicitud);

		return otroRequisitos;
	}

	@Override
	public List<OtroRequisito> listarOtroRequisitoObservado(String tipo, Long idSolicitud) {
		return otroRequisitoDao.listarOtroRequisitoObservado(tipo, idSolicitud);
	}

	@Override
	public List<OtroRequisito> listarOtroRequisito(Long idSolicitud) {
		List<OtroRequisito> otroRequisitos = otroRequisitoDao.listarOtroRequisito(idSolicitud);

		return otroRequisitos;
	}

	@Override
	public List<OtroRequisito> listarOtroRequisitosPerfil(Long idSolicitud) {
		List<OtroRequisito> otroRequisitos = otroRequisitoDao.listarOtroRequisitosPerfil(idSolicitud);

		return otroRequisitos;
	}

	@Override
	public OtroRequisito obtener(String tipo, Long idOtroRequisito, Contexto contexto) {
		OtroRequisito otroRequisito = otroRequisitoDao.obtener(idOtroRequisito);
		return otroRequisito;
	}

	@Override
	public OtroRequisito evalular(OtroRequisito otroRequisito, Contexto contexto) {
		if (otroRequisito.getSolicitud() == null && otroRequisito.getSolicitud().getSolicitudUuid() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		if (otroRequisito.getTipo() == null && otroRequisito.getTipo().getIdListadoDetalle() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_NO_ENVIADO);
		}
		if (otroRequisito.getEvaluacion() == null && otroRequisito.getEvaluacion().getIdListadoDetalle() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_EVALUACION_NO_ENVIADO);
		}
		if (Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO.equals(otroRequisito.getTipo().getCodigo())) {
			if (otroRequisito.getIdOtroRequisito() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_REQUISITO_NO_ENVIADO);
			}
			if (otroRequisito.getTipoRequisito() == null
					&& otroRequisito.getTipoRequisito().getIdListadoDetalle() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_REQUISITO_NO_ENVIADO);
			}
		}
		OtroRequisito otroRequisitoBD = otroRequisitoDao.obtener(otroRequisito.getIdOtroRequisito());
		if (otroRequisitoBD.getMontoFacturadoSector() == null ) {
			otroRequisitoBD.setMontoFacturadoSector(otroRequisito.getMontoFacturadoSector());
		}
		otroRequisitoBD.setEvaluacion(otroRequisito.getEvaluacion());
		otroRequisitoBD.setObservacion(otroRequisito.getObservacion());
		AuditoriaUtil.setAuditoriaRegistro(otroRequisitoBD, contexto);
		otroRequisitoDao.save(otroRequisitoBD);
		String codigoTipo = "";
		if (Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO.equals(otroRequisitoBD.getTipo().getCodigo())) {
			codigoTipo = Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO;
		} else {
			codigoTipo = Constantes.LISTADO.TIPO_EVALUADOR.TECNICO;
		}
		solicitudService.actualizarProceso(otroRequisitoBD.getSolicitud(), codigoTipo, contexto);
		return otroRequisitoBD;
	}

	@Override
	public OtroRequisito finalizar(OtroRequisito otroRequisito, Contexto contexto) {
		if (otroRequisito.getSolicitud() == null && otroRequisito.getSolicitud().getSolicitudUuid() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		if (otroRequisito.getTipo() == null && otroRequisito.getTipo().getIdListadoDetalle() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_NO_ENVIADO);
		}
		if (Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO.equals(otroRequisito.getTipo().getCodigo())) {
			if (otroRequisito.getIdOtroRequisito() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_REQUISITO_NO_ENVIADO);
			}
		}
		OtroRequisito otroRequisitoBD = otroRequisitoDao.obtener(otroRequisito.getIdOtroRequisito());
		if (otroRequisitoBD.getEvaluacion() != null && Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR
				.equals(otroRequisito.getEvaluacion().getCodigo())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUE_TODO_REQUISITOS);
		}

		ListadoDetalle finalizado = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.SI_NO.CODIGO,
				Constantes.LISTADO.SI_NO.SI);
		otroRequisitoBD.setFinalizado(finalizado);
		otroRequisitoBD.setUsuario(contexto.getUsuario());
		otroRequisitoBD.setFechaFinalizador(new Date());
		AuditoriaUtil.setAuditoriaRegistro(otroRequisitoBD, contexto);
		otroRequisitoDao.save(otroRequisitoBD);
		return otroRequisitoBD;
	}

	@Override
	public List<OtroRequisito> listarOtroRequisitoXSolicitud(Long idSolicitud, Long idUsuario, Contexto contexto) {
		return otroRequisitoDao.listarOtroRequisitoXSolicitud(idSolicitud, idUsuario);
	}

	@Override
	public List<OtroRequisito> listarOtroRequisitoXSolicitudObservados(Long idSolicitud, Long idUsuario,
			Contexto contexto) {
		return otroRequisitoDao.listarOtroRequisitoXSolicitudObservados(idSolicitud, idUsuario);
	}

	@Override
	public List<OtroRequisito> listarOtroRequisitoXSolicitudPJ(Long idSolicitud, Long idUsuario, Contexto contexto) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaLimite = null;
		try {
			fechaLimite = dateFormat.parse("2024-10-16");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return otroRequisitoDao.listarOtroRequisitoXSolicitudPJ(idSolicitud, idUsuario, fechaLimite);
	}

	@Override
	public List<OtroRequisito> listarOtroRequisitoXSolicitudObservadosPJ(Long idSolicitud, Long idUsuario,
			Contexto contexto) {
		return otroRequisitoDao.listarOtroRequisitoXSolicitudObservadosPJ(idSolicitud, idUsuario);
	}

	@Override
	public List<OtroRequisito> listarOtroRequisitoPerfilesPendientesDeEvaluacion() {
		return otroRequisitoDao.listarOtroRequisitoPerfilesPendientesDeEvaluacion();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OtroRequisito asignarEvaluadorPerfil(Long idOtroRequisito, List<Asignacion> asignaciones,
			Contexto contexto) {
		OtroRequisito otroRequisito = otroRequisitoDao.obtener(idOtroRequisito);
		otroRequisito.setUsuario(asignaciones.get(0).getUsuario());
		otroRequisito.setFechaAsignacion(new Date());
		OtroRequisito otroRequisitoRpt = guardar(otroRequisito, contexto);
		asignaciones.get(0).setOtroRequisito(otroRequisitoRpt);
		List<Asignacion> listaAsiganaciones = asignacionDao
				.obtenerAsignaciones(otroRequisito.getSolicitud().getIdSolicitud());

		for (Asignacion asignacion : listaAsiganaciones) {
			if (asignacion.getGrupo() != null && asignacion.getGrupo().getIdListado() == null) {
				ListadoDetalle grupo = new ListadoDetalle();
				grupo = listadoDetalleDao.obtener(asignacion.getGrupo().getIdListadoDetalle());
				asignacion.setGrupo(grupo);
			}
		}

		asignacionService.guardar(asignaciones, contexto);

		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudUuid(otroRequisito.getSolicitud().getSolicitudUuid());
		PerfilDivision perfilDivision = perfilDivisionDao
				.obtenerPerfilDivisionPorIdPerfil(otroRequisito.getPerfil().getIdListadoDetalle());
		List<PerfilAprobador> listaPerfilesAprobadores = perfilAprobadorDao
				.obtenerPerfilAprobadorPorIdPerfil(otroRequisito.getPerfil().getIdListadoDetalle());

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
			logger.error("No se econtro relacion de aprobadores a pefil : "
					+ otroRequisito.getPerfil().getIdListadoDetalle());
		}

		return otroRequisitoRpt;
	}

	public void copiarOtrosRequisitosUltimaSolicitud(String solicitudUuidUltima, String solicitudUuid,
			Contexto contexto) {
		Long idSolicitudUltima = solicitudService.obtenerId(solicitudUuidUltima);
		Long idSolicitud = solicitudService.obtenerId(solicitudUuid);
		List<OtroRequisito> listaOtrosRequisitosUltimaSolicitud = otroRequisitoDao
				.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, idSolicitudUltima);
		List<OtroRequisito> listaOtrosRequisitosSolicitud = otroRequisitoDao
				.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO, idSolicitud);

		if (!listaOtrosRequisitosSolicitud.isEmpty()) {
			for (OtroRequisito otroRequisito : listaOtrosRequisitosSolicitud) {
				try {
					archivoService.eliminarIdOtroRequisito(otroRequisito.getIdOtroRequisito(), contexto);
				} catch (Exception e) {
				}
				otroRequisitoDao.delete(otroRequisito);
			}
		}
		if (listaOtrosRequisitosUltimaSolicitud != null) {
			OtroRequisito otroRequisitoNuevo = null;
			for (OtroRequisito otroRequisito : listaOtrosRequisitosUltimaSolicitud) {
				otroRequisitoNuevo = new OtroRequisito();
				Solicitud solicitud = new Solicitud();
				solicitud.setIdSolicitud(idSolicitud);
				otroRequisitoNuevo.setSolicitud(solicitud);
				otroRequisitoNuevo.setTipo(otroRequisito.getTipo());
				otroRequisitoNuevo.setTipoRequisito(otroRequisito.getTipoRequisito());
				otroRequisitoNuevo.setFlagElectronico(otroRequisito.getFlagElectronico());
				otroRequisitoNuevo.setFlagFirmaDigital(otroRequisito.getFlagFirmaDigital());
				otroRequisitoNuevo.setFlagActivo(otroRequisito.getFlagActivo());
				ListadoDetalle evaluacion = listadoDetalleDao
						.listarListadoDetallePorCoodigo(Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR).get(0);
				otroRequisitoNuevo.setEvaluacion(evaluacion);
				otroRequisitoNuevo.setFlagSiged(otroRequisito.getFlagSiged());
				otroRequisitoNuevo.setUsuCreacion(contexto.getUsuario().getUsuario());
				otroRequisitoNuevo.setIpCreacion(contexto.getIp());
				otroRequisitoNuevo.setFecCreacion(new Date());
				otroRequisitoNuevo = otroRequisitoDao.save(otroRequisitoNuevo);

				List<Archivo> listaArchivos = archivoService.buscar(null, null, otroRequisito.getIdOtroRequisito(),
						contexto);
				if (listaArchivos != null) {
					Archivo archivoNuevo = null;
					for (Archivo archivo : listaArchivos) {
						archivoNuevo = new Archivo();
						archivoNuevo.setIdOtroRequisito(otroRequisitoNuevo.getIdOtroRequisito());
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

	@Override
	public OtroRequisito actualizarEstadoSolicitudRevertirEvaluacion(OtroRequisito otroRequisito, Contexto contexto) {
		Solicitud solicitud = solicitudDao.obtener(otroRequisito.getSolicitud().getIdSolicitud());

		if (otroRequisito.getSolicitud() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		if (otroRequisito.getTipo() == null && otroRequisito.getTipo().getIdListadoDetalle() == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_NO_ENVIADO);
		}
		if (Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.OTRO_REQUISITO.equals(otroRequisito.getTipo().getCodigo())) {
			if (otroRequisito.getIdOtroRequisito() == null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_REQUISITO_NO_ENVIADO);
			}
		}
		if (!solicitud.getFlagActivo().equals(1L)
				|| !solicitud.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO)) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_NO_EN_PROCESO);
		}

		OtroRequisito otroRequisitoBD = otroRequisitoDao.obtener(otroRequisito.getIdOtroRequisito());
		otroRequisitoBD.setEstadoReversion(otroRequisito.getEstadoReversion());
		otroRequisitoBD.setFinalizado(otroRequisito.getFinalizado());
		otroRequisitoBD.setFechaFinalizador(otroRequisito.getFechaFinalizador());
		otroRequisitoBD.setObservacion(otroRequisito.getObservacion());
		otroRequisitoBD.setEvaluacion(otroRequisito.getEvaluacion());
		AuditoriaUtil.setAuditoriaRegistro(otroRequisitoBD, contexto);
		otroRequisitoDao.save(otroRequisitoBD);
		if (otroRequisitoBD.getEstadoReversion().getCodigo().equals(Constantes.LISTADO.ESTADO_REVERSION.REV_SOLICITADO)) {
			notificacionService.enviarMensajeSolicitudRevertirEvaluacion(otroRequisito.getIdOtroRequisito(), contexto);
		}else if (otroRequisitoBD.getEstadoReversion().getCodigo().equals(Constantes.LISTADO.ESTADO_REVERSION.REV_APROBADO)) {
			notificacionService.enviarMensajeAprobacionRevertirEvaluacion(otroRequisito.getIdOtroRequisito(), contexto);
		}
		

		return otroRequisitoBD;
	}

	@Override
	public List<OtroRequisito> listarOtroRequisitosPerfilObservado(Long idSolicitud) {
		List<OtroRequisito> otroRequisitos = otroRequisitoDao.listarOtroRequisitosPerfilObservado(idSolicitud);

		return otroRequisitos;
	}

	@Override
	public List<OtroRequisito> listarOtroRequisitosPerfilCalifica(Long idSolicitud) {
		List<OtroRequisito> otroRequisitos = otroRequisitoDao.listarOtroRequisitosPerfilCalifica(idSolicitud);

		return otroRequisitos;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public OtroRequisito modificarEvaluadorPerfil(Long idOtroRequisito, List<Asignacion> asignaciones,
			Contexto contexto) {
		OtroRequisito otroRequisito = otroRequisitoDao.obtener(idOtroRequisito);
		if (otroRequisito.getFinalizado() == null) {
			otroRequisito.setUsuario(asignaciones.get(0).getUsuario());
			otroRequisito.setFechaAsignacion(new Date());
			OtroRequisito otroRequisitoRpt = guardar(otroRequisito, contexto);
			asignaciones.get(0).setOtroRequisito(otroRequisitoRpt);
			List<Asignacion> listaAsiganaciones = asignacionDao
					.obtenerAsignaciones(otroRequisito.getSolicitud().getIdSolicitud());
			for (Asignacion asignacion : listaAsiganaciones) {
				if (asignacion.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO) && asignacion
						.getOtroRequisito().getIdOtroRequisito().equals(otroRequisitoRpt.getIdOtroRequisito())) {
					asignacion.setUsuario(otroRequisitoRpt.getUsuario());
					solicitudService.actualizarAsignado(asignacion, contexto);
					break;
				}
			}
			return otroRequisitoRpt;
		}
		return null;
	}

	@Override
	public List<OtroRequisito> listarPerfilesPendientesDeEvaluacion() {
		return otroRequisitoDao.listarPerfilesPendientesDeEvaluacion();
	}

	private Page<OtroRequisito> agruparYObtenerMaximos(Page<OtroRequisito> otrosRequisitos) {
		// Convertir Page a List
		List<OtroRequisito> otrosRequisitosList = otrosRequisitos.getContent();

		// Agrupar por sector y subsector, y obtener el máximo de idOtroRequisito
		Map<ListadoDetalle, Map<ListadoDetalle, OtroRequisito>> agrupados = otrosRequisitosList.stream()
				.collect(Collectors.groupingBy(OtroRequisito::getSector,
						Collectors.groupingBy(OtroRequisito::getSubsector,
								Collectors.collectingAndThen(
										Collectors.maxBy(Comparator.comparing(OtroRequisito::getIdOtroRequisito)),
										Optional::get))));

		// Convertir los grupos a una lista de OtroRequisito
		List<OtroRequisito> maximosPorGrupo = agrupados.values().stream().flatMap(map -> map.values().stream())
				.collect(Collectors.toList());

		// Obtener el Pageable del Page original
		Pageable pageable = PageRequest.of(otrosRequisitos.getNumber(), otrosRequisitos.getSize());

		// Convertir la lista a un Page y devolver
		return new PageImpl<>(maximosPorGrupo, pageable, maximosPorGrupo.size());
	}
	
	//AFC
	@Override
	public List<OtroRequisito> listarOtroRequisitoXSolicitudAdmin(Long idSolicitud) {
		return otroRequisitoDao.listarOtroRequisitoXSolicitudAdmin(idSolicitud);
	}
	
	@Override
	public List<OtroRequisito> listarOtroRequisitoXSolicitudAdminUser(Long idSolicitud) {
		return otroRequisitoDao.listarOtroRequisitoXSolicitudAdminUser(idSolicitud);
	}
	
	
	//AFC
}
