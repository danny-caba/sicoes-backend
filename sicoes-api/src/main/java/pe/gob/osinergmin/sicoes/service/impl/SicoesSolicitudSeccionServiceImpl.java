package pe.gob.osinergmin.sicoes.service.impl;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.ArchivoDTO;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequisitoDTO;
import pe.gob.osinergmin.sicoes.model.dto.SicoesSolicitudSeccionDTO;
import pe.gob.osinergmin.sicoes.repository.RequisitoDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudSeccionDao;
import pe.gob.osinergmin.sicoes.repository.SicoesTdSolPerConSecDao;
import pe.gob.osinergmin.sicoes.repository.SicoesTdSoliPersPropDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.stream.Collectors;

@Service
public class SicoesSolicitudSeccionServiceImpl implements SicoesSolicitudSeccionService {

	Logger logger = LogManager.getLogger(SicoesSolicitudSeccionServiceImpl.class);

	@Autowired
	private SicoesSolicitudSeccionDao sicoesSolicitudSeccionDao;

	@Autowired
	private RequisitoDao requisitoDao;

	@Autowired
	private SicoesTdSolPerConSecDao sicoesTdSolPerConSecDao;

	@Autowired
	private SicoesTdSolPersPropService sicoesTdSolPersPropService;

	@Autowired
	private SicoesSolicitudSeccionService sicoesSolicitudSeccionService;

	@Autowired
	private ArchivoService archivoService;

	@Autowired
	private SupervisoraService supervisoraService;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SicoesSolicitudSeccion guardar(SicoesSolicitudSeccion solicitud, Contexto contexto) {
//		AuditoriaUtil.setAuditoriaRegistro(solicitud,contexto);
//		solicitud.setEstado("1");//activo
		return sicoesSolicitudSeccionDao.save(solicitud);
	}

	@Override
	public SicoesSolicitudSeccion obtener(Long id, Contexto contexto) {
		return sicoesSolicitudSeccionDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public SicoesSolicitudSeccion putRequisito(SicoesSolicitudSeccion sicoesSolicitud, Contexto contexto) {

		Usuario usuario = usuarioService.obtener(contexto.getUsuario().getIdUsuario());

		if (sicoesSolicitud.getProcRevision().equals("1")) {
			sicoesSolicitud.setEvaluacion(null);
		}
		sicoesSolicitud.setFechaEvaluacion(new Date());
		sicoesSolicitud.setUsuarioEvaluacion(usuario);
		AuditoriaUtil.setAuditoriaActualizacion(sicoesSolicitud, contexto);
		SicoesSolicitudSeccion sicoesSolicitudDB = sicoesSolicitudSeccionDao.save(sicoesSolicitud);
		return  sicoesSolicitudSeccionDao.save(sicoesSolicitudDB) ;

	}

	@Override
	public SicoesSolicitudSeccion actualizarRequisitoPersonal(SicoesTdSoliPersProp personalPropuesto, Contexto contexto) {
		SicoesSolicitudSeccion response = null;
		boolean esPrimeraEntrada = true;

		try {
			Usuario usuario = usuarioService.obtener(contexto.getUsuario().getIdUsuario());

			for (Archivo archivo : personalPropuesto.getArchivos()) {
				if (archivo.getIdSeccionRequisito() == null) {
					continue;
				}

				SicoesSolicitudSeccion requisito = sicoesSolicitudSeccionDao
						.findById(archivo.getIdSeccionRequisito())
						.orElse(null);

				if (requisito != null) {
					if (personalPropuesto.getProcRevision().equals("1")) {
						requisito.setEvaluacion(null);
					} else {
						requisito.setEvaluacion(personalPropuesto.getEvaluacion());
					}
					requisito.setProcRevision(personalPropuesto.getProcRevision());
					requisito.setUsuarioEvaluacion(usuario);
					requisito.setFechaEvaluacion(new Date());
					AuditoriaUtil.setAuditoriaActualizacion(requisito, contexto);

					SicoesSolicitudSeccion requisitoDB = sicoesSolicitudSeccionDao.save(requisito);

					if (esPrimeraEntrada && requisitoDB != null) {
						response = requisitoDB;
						esPrimeraEntrada = false;
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error al actualizar el requisito personal", e);
			throw new RuntimeException("Error al actualizar el requisito personal", e);
		}

		return response;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void registrarSolicitudSeccion(Iterable<SicoesTdSolPerConSec> secciones, List<SicoesTdSoliPersProp> profesionales, Contexto contexto) {
		List<Requisito> requisitos = requisitoDao.buscarRequisitos();
		for (Requisito requisito : requisitos) {
			SicoesTdSolPerConSec sicoesTdSolPerConSec = null;
			for (SicoesTdSolPerConSec seccion : secciones) {
				if (requisito.getSeccion().getIdSeccion().equals(seccion.getIdSeccion())) {
					sicoesTdSolPerConSec = seccion;
					break;
				}
			}

			if (sicoesTdSolPerConSec == null) {
				continue;
			}

			if (!Constantes.FLAG_PERSONAL_PERF_CONTRATO.SI.equals(sicoesTdSolPerConSec.getFlConPersonal())) {
				SicoesSolicitudSeccion solicitudSeccion = new SicoesSolicitudSeccion();
				solicitudSeccion.setRequisito(requisito);
				solicitudSeccion.setIdPerConSec(sicoesTdSolPerConSec.getIdSolPerConSec());
				solicitudSeccion.setProcSubsanacion(Constantes.FLAG_PROCESO_SUBSANACION.INSCRIPCION);
				solicitudSeccion.setProcRevision(Constantes.FLAG_PROCESO_SUBSANACION.INSCRIPCION);
				solicitudSeccion.setEstado(Constantes.ESTADO.ACTIVO);
				solicitudSeccion.setDeRequisito(requisito.getDeSeccionRequisito());
				AuditoriaUtil.setAuditoriaRegistro(solicitudSeccion, contexto);
				sicoesSolicitudSeccionService.guardar(solicitudSeccion, contexto);
			} else {
				for (SicoesTdSoliPersProp sicoesTdSoliPersProp : profesionales) {
					SicoesSolicitudSeccion solicitudSeccion = new SicoesSolicitudSeccion();
					solicitudSeccion.setRequisito(requisito);
					solicitudSeccion.setIdPerConSec(sicoesTdSoliPersProp.getSicoesTdSolPerConSec().getIdSolPerConSec());
					solicitudSeccion.setPersonalPropuesto(sicoesTdSoliPersProp);
					solicitudSeccion.setProcSubsanacion(Constantes.FLAG_PROCESO_SUBSANACION.INSCRIPCION);
					solicitudSeccion.setProcRevision(Constantes.FLAG_PROCESO_SUBSANACION.INSCRIPCION);
					solicitudSeccion.setEstado(Constantes.ESTADO.ACTIVO);
					solicitudSeccion.setDeRequisito(requisito.getDeSeccionRequisito());
					AuditoriaUtil.setAuditoriaRegistro(solicitudSeccion, contexto);
					sicoesSolicitudSeccionService.guardar(solicitudSeccion, contexto);
				}
			}
		}
	}

	@Override
	public Page<SicoesSolicitudSeccion> obtenerRequisitosPorSeccion(Long idSeccion, Long tipoContrato, Pageable pageable, Contexto contexto) {
		Page<SicoesSolicitudSeccion> listaRequisitos = sicoesSolicitudSeccionDao.obtenerRequisitosPorSeccion(idSeccion, tipoContrato, pageable);
		for (SicoesSolicitudSeccion requisito : listaRequisitos) {
			List<Archivo> list = archivoService.buscarPorPerfContrato(requisito.getIdSolicitudSeccion(), contexto);
			if (list != null && !list.isEmpty()) {
				requisito.setArchivo(list.get(0));
			}
		}
		return listaRequisitos;
	}

	@Override
	public Page<SicoesSolicitudSeccion> obtenerRequisitosPorPersonal(Long idSoliPersProp, Long tipoContrato, Pageable pageable, Contexto contexto) {
		Page<SicoesSolicitudSeccion> listaRequisitos = sicoesSolicitudSeccionDao.obtenerRequisitosPorPersonal(idSoliPersProp, tipoContrato, pageable);
//		List<SicoesSolicitudSeccion> listaRequisitosConvertido = listaRequisitos.stream().map(this::convertirARequisitoDTO).collect(Collectors.toList());

//		for (SicoesSolicitudSeccionDTO requisitoConvertido : listaRequisitosConvertido) {
//			List<Archivo> list = archivoService.buscarPorPerfContrato(requisitoConvertido.getIdSolicitudSeccion(), contexto);
//			if (list != null && !list.isEmpty()) {
//				requisitoConvertido.setArchivo(list.get(0));
//			}
//		}

//		return listaRequisitosConvertido;

		for (SicoesSolicitudSeccion requisito : listaRequisitos) {
			List<Archivo> list = archivoService.buscarPorPerfContrato(requisito.getIdSolicitudSeccion(), contexto);
			if (list != null && !list.isEmpty()) {
				requisito.setArchivo(list.get(0));
			}
		}
		return listaRequisitos;

	}

	@Override
	public Page<SicoesTdSoliPersProp> obtenerRequisitosConFlagActivo(Long idSeccion, Long tipoContrato, Pageable pageable, Contexto contexto) {
		Page<SicoesTdSoliPersProp> profesionales = sicoesTdSolPersPropService.personasPorSeccion(idSeccion, pageable, contexto);
		List<SicoesTdSoliPersProp> profesionalesMutable = new ArrayList<>(profesionales.getContent());

		for (SicoesTdSoliPersProp profesional : new ArrayList<>(profesionalesMutable)) {
			List<SicoesSolicitudSeccion> listaRequisitos = sicoesSolicitudSeccionDao.obtenerRequisitosPorPersonalV2(profesional.getIdSoliPersProp(), tipoContrato);
			List<Archivo> archivos = new ArrayList<>();
			boolean tieneEvaluados = false;
			String evaluacion = null;
			String procRevision = null;
			SicoesSolicitudSeccion solicitudSeccion = null;
			for (SicoesSolicitudSeccion requisito : listaRequisitos) {
				if (requisito.getRequisito().getDeSeccionRequisito().equals("DJ del personal propuesto por la empresa (Nepotismo)")) {
					List<Archivo> list = archivoService.buscarPorPerfContrato(requisito.getIdSolicitudSeccion(), contexto);
					if (list != null && !list.isEmpty()) {
						archivos.add(list.get(0));
					} else {
						Archivo archivo = new Archivo();
						archivo.setNombre("No se adjuntó archivo");
						archivos.add(archivo);
					}
				} else if (requisito.getRequisito().getDeSeccionRequisito().equals("DJ Impedimentos / Incompatib. y prohibiciones")) {
					List<Archivo> list = archivoService.buscarPorPerfContrato(requisito.getIdSolicitudSeccion(), contexto);
					if (list != null && !list.isEmpty()) {
						archivos.add(list.get(0));
					} else {
						Archivo archivo = new Archivo();
						archivo.setNombre("No se adjuntó archivo");
						archivos.add(archivo);
					}
				} else if (requisito.getRequisito().getDeSeccionRequisito().equals("DJ No Vinculo Contractual con Agente Supervisado")) {
					List<Archivo> list = archivoService.buscarPorPerfContrato(requisito.getIdSolicitudSeccion(), contexto);
					if (list != null && !list.isEmpty()) {
						archivos.add(list.get(0));
					} else {
						Archivo archivo = new Archivo();
						archivo.setNombre("No se adjuntó archivo");
						archivos.add(archivo);
					}
				} else if (requisito.getRequisito().getDeSeccionRequisito().equals("Otros documentos")) {
					List<Archivo> list = archivoService.buscarPorPerfContrato(requisito.getIdSolicitudSeccion(), contexto);
					if (list != null && !list.isEmpty()) {
						archivos.add(list.get(0));
					} else {
						Archivo archivo = new Archivo();
						archivo.setNombre("No se adjuntó archivo");
						archivos.add(archivo);
					}
				}

				if (!requisito.getProcRevision().equals("0") && !tieneEvaluados) {
					tieneEvaluados = true;
					solicitudSeccion = requisito;
				}

			}
			profesional.setArchivos(archivos);

			if (!tieneEvaluados) {
				profesional.setEvaluacion("");
				profesional.setProcRevision("0");
			}

			if (tieneEvaluados) {
				profesional.setRequisito(solicitudSeccion);
				profesional.setProcRevision(solicitudSeccion.getProcRevision());
				profesional.setEvaluacion(solicitudSeccion.getEvaluacion());
			}

			if (solicitudSeccion != null) {
				profesional.setRequisito(solicitudSeccion);
			}

			if (profesional.getArchivos() == null || profesional.getArchivos().isEmpty()) {
				profesionalesMutable.remove(profesional);
			} else {
				int i = 0;
				for (Archivo archivo : profesional.getArchivos()) {
					if (archivo.getNombre() == null) {
						continue;
					}

					if (archivo.getNombre().equals("No se adjuntó archivo")) {
						i++;
					}
				}

				if (i == 4) {
					profesionalesMutable.remove(profesional);
				}
			}
		}

		Page<SicoesTdSoliPersProp> resultadoFinal = new PageImpl<>(profesionalesMutable, pageable, profesionalesMutable.size());

		return resultadoFinal;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<SicoesSolicitudSeccion> actualizarSolicitudDetalle(List<SicoesSolicitudSeccion> listaSolicitudSeccion, Contexto contexto) {

		List<SicoesSolicitudSeccion> listaSolicitudSeccionFinal = new ArrayList<>(); // Lista mutable

		listaSolicitudSeccion.forEach(solicitudSeccion -> {
			Optional<SicoesSolicitudSeccion> solicitudSeccionOpt = sicoesSolicitudSeccionDao.findById(solicitudSeccion.getIdSolicitudSeccion());
			SicoesSolicitudSeccion solicitudSeccionFinal = solicitudSeccionOpt.orElseThrow(() ->
					new RuntimeException("No se encontró la solicitudSeccion con id " + solicitudSeccion.getIdSolicitudSeccion())
			);

			solicitudSeccionFinal.setFlagRequisito("1");
			solicitudSeccionFinal.setValor(solicitudSeccion.getValor());
			solicitudSeccionFinal.setTexto(solicitudSeccion.getTexto());

			AuditoriaUtil.setAuditoriaActualizacion(solicitudSeccionFinal, contexto);
			SicoesSolicitudSeccion sicoesSolicitudSeccion = sicoesSolicitudSeccionDao.save(solicitudSeccionFinal);
			listaSolicitudSeccionFinal.add(sicoesSolicitudSeccion);
		});

		return listaSolicitudSeccionFinal;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<SicoesSolicitudSeccion> registrarSolicitudSeccionSubsanacion(List<SicoesTdSolPerConSec> seccionesSubsanacion, Contexto contexto) {

		return null;

	}

	@Override
	public List<SicoesSolicitudSeccion> obtenerRequisitosPorSeccionSub(Long idSeccion) {
		return sicoesSolicitudSeccionDao.obtenerRequisitosPorSeccionSub(idSeccion);
	}

	@Override
	public List<SicoesSolicitudSeccion> obtenerRequisitosPorPersonalSub(Long idSoliPersProp) {
		return sicoesSolicitudSeccionDao.obtenerRequisitosPorPersonalSub(idSoliPersProp);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean modificarFlagRequisito(List<SicoesSolicitudSeccion> lstRequisitos, Contexto contexto) {
		List<SicoesSolicitudSeccion> lstRequisitosDB = new ArrayList<>();
		for (SicoesSolicitudSeccion requisito : lstRequisitos) {
			requisito.setFlagRequisito("1");
			AuditoriaUtil.setAuditoriaActualizacion(requisito, contexto);
			lstRequisitosDB.add(requisito);
		}

		try {
			sicoesSolicitudSeccionDao.saveAll(lstRequisitosDB);
			return true;
		} catch (Exception e) {
			logger.error("Error al modificar el flag de los requisitos", e);
			return false;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean descartarFlagRequisitoPersonal(SicoesTdSoliPersProp persProp, Contexto contexto) {
		List<Archivo> lstArchivos = persProp.getArchivos();

		try {

			List<SicoesSolicitudSeccion> lstRequisitos = sicoesSolicitudSeccionService.obtenerRequisitosPorPersonalSub(persProp.getIdSoliPersProp());
			for (SicoesSolicitudSeccion requisito : lstRequisitos) {
				requisito.setFlagRequisito("0");
				AuditoriaUtil.setAuditoriaActualizacion(requisito, contexto);
				sicoesSolicitudSeccionService.guardar(requisito, contexto);
			}

			if (lstArchivos != null && !lstArchivos.isEmpty()) {
				for (Archivo archivo : lstArchivos) {
					if (archivo.getIdArchivo() == null) {
						continue;
					}
					archivoService.eliminar(archivo.getIdArchivo(), contexto);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("Error al descartar el flag de los requisitos", e);
			return false;
		}
	}

	@Override
	public List<SicoesSolicitudSeccion> obtenerRequisitosPorSeccionFinalizacion(List<Long> seccionIds) {
		return sicoesSolicitudSeccionDao.obtenerRequisitosPorSeccionFinalizacion(seccionIds);
	}

	@Override
	public List<SicoesSolicitudSeccion> obtenerRequisitosPorSeccionConPersonal(Long idSeccion) {
		return sicoesSolicitudSeccionDao.obtenerRequisitosPorSeccionConPersonal(idSeccion);
	}

	@Override
	public List<SicoesSolicitudSeccion> obtenerRequisitosEvaluados(Long idSeccion, String tipoRequisitoEvaluado) {
		return sicoesSolicitudSeccionDao.obtenerRequisitosEvaluados(idSeccion, tipoRequisitoEvaluado);
	}

	@Override
	public List<SicoesSolicitudSeccion> obtenerRequisitosPorPersonalActivo(Long idSoliPersProp) {
		return sicoesSolicitudSeccionDao.obtenerRequisitosPorPersonalActivo(idSoliPersProp);
	}

	@Override
	public List<SicoesSolicitudSeccion> obtenerRequisitosPorSecciones(List<Long> seccionesIds) {
		return sicoesSolicitudSeccionDao.obtenerRequisitosPorSecciones(seccionesIds);
	}

}
