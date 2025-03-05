package pe.gob.osinergmin.sicoes.service.impl;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.StreamSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.repository.SeccionDao;
import pe.gob.osinergmin.sicoes.repository.SicoesTdSolPerConSecDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;

@Service
public class SicoesTdSolPerConSecServiceImpl  implements SicoesTdSolPerConSecService{
	Logger logger = LogManager.getLogger(SicoesTdSolPerConSecServiceImpl.class);
	
	@Autowired
	private SicoesTdSolPerConSecDao sicoesTdSolPerConSecDao;

	@Autowired
	private SeccionDao seccionDao;

	@Autowired
	private SicoesTdSolPersPropService sicoesTdSolPersPropService;

	@Autowired
	private SicoesSolicitudSeccionService sicoesSolicitudSeccionService;

	@Autowired
	private ArchivoService archivoService;
 
	@Override
	public void eliminar(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public
	Iterable <SicoesTdSolPerConSec> guardarSicoes(SicoesSolicitud sicoesSolicitud, Contexto contexto) {
		 try	{
			 List<Map<String, Object>> response = new ArrayList<>();
			 
			 Map<String, Object> items =  new HashMap<>();
			 items.put("message", "Se inserto correctamente");
			 response.add(items);
			 Pageable pageable = Pageable.unpaged();
			 List<SicoesTdSolPerConSec> lista = new ArrayList<>();
			 seccionDao.getListaSeccion(pageable).getContent().stream().
			 forEach(x-> {
				 logger.info("Seccion: {}", x);
				 SicoesTdSolPerConSec modelPerso = new SicoesTdSolPerConSec();
				 modelPerso.setEsSoliPerfCont(x.get("ESSECCION").toString());
				 modelPerso.setTiSolicitud(sicoesSolicitud.getTipoSolicitud());
				 modelPerso.setFlConPersonal(x.get("FLREQPERSONAL").toString());
				 modelPerso.setIdSoliPerfCont(sicoesSolicitud.getIdSolicitud());
				 modelPerso.setIdSeccion(Long.valueOf(x.get("IDSECCION").toString()));
				 modelPerso.setDeSeccion(x.get("DESECCION").toString());
				 AuditoriaUtil.setAuditoriaRegistro(modelPerso, contexto);
				 lista.add(modelPerso);

			 });
			 Iterable<SicoesTdSolPerConSec> savedUsers = sicoesTdSolPerConSecDao.saveAll(lista);
			    // Use Stream API to process the saved users
			    StreamSupport.stream(savedUsers.spliterator(), false)
			                 .forEach(x -> System.out.println("Saved solicitud:   " + x.getIdSeccion()));

			 return	savedUsers;
		  }catch (Exception ex) {
			 logger.error("Ocurrio un error al guardar la solicitud: {}, Contexto: {}, Excepción: {}", sicoesSolicitud, contexto, ex.getMessage(), ex);
			 throw new RuntimeException("Error al guardar la solicitud", ex);
		  }
	}

	@Override
	public List<SicoesTdSolPerConSec> obtenerSeccionPorPersonal() {
		return sicoesTdSolPerConSecDao.obtenerSeccionPorPersonal();
	}

	@Override
	public List<SicoesTdSolPerConSec> obtenerSeccionesXSolicitud(Long idSolicitud) {
		return sicoesTdSolPerConSecDao.obtenerSeccionesXSolicitud(idSolicitud);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<SicoesTdSolPerConSec> guardarSeccionesSubsanacion(SicoesSolicitud sicoesSolicitud, Contexto contexto) {

		List<SicoesTdSolPerConSec> response = new ArrayList<>();

		try {
			List<SicoesTdSolPerConSec> listaSeccionesPadre = sicoesTdSolPerConSecDao.buscarPorIdSolicitudPadre(sicoesSolicitud.getIdSolicitudPadre());

			for (SicoesTdSolPerConSec seccionPadre : listaSeccionesPadre) {

				SicoesTdSolPerConSec seccionSubsanacion = new SicoesTdSolPerConSec();
				BeanUtils.copyProperties(seccionPadre, seccionSubsanacion, "idSolPerConSec", "fecCreacion", "usuCreacion", "ipCreacion", "fecActualizacion", "usuActualizacion", "ipActualizacion");
				seccionSubsanacion.setIdSoliPerfCont(sicoesSolicitud.getIdSolicitud());
				AuditoriaUtil.setAuditoriaRegistro(seccionSubsanacion, contexto);

				SicoesTdSolPerConSec sicoesTdSolPerConSecNew = sicoesTdSolPerConSecDao.save(seccionSubsanacion);

				response.add(seccionSubsanacion);

				if ("1".equals(seccionPadre.getFlConPersonal())) {
					List<SicoesTdSoliPersProp> listaPersonal = sicoesTdSolPersPropService.obtenerProfesionalesPorSeccion(seccionPadre.getIdSolPerConSec());

					for (SicoesTdSoliPersProp profesionalPadre : listaPersonal) {
						SicoesTdSoliPersProp profesionalSubsanacion = new SicoesTdSoliPersProp();
						BeanUtils.copyProperties(profesionalPadre, profesionalSubsanacion, "idSoliPersProp", "sicoesTdSolPerConSec", "fecCreacion", "usuCreacion", "ipCreacion", "fecActualizacion", "usuActualizacion", "ipActualizacion");
						profesionalSubsanacion.setSicoesTdSolPerConSec(sicoesTdSolPerConSecNew);

						AuditoriaUtil.setAuditoriaRegistro(profesionalSubsanacion, contexto);
						SicoesTdSoliPersProp profesionalSubsanacionDB = sicoesTdSolPersPropService.guardar(profesionalSubsanacion, contexto);

						List<SicoesSolicitudSeccion> listaRequisitosPorPersonal = sicoesSolicitudSeccionService.obtenerRequisitosPorPersonalSub(profesionalPadre.getIdSoliPersProp());

						for (SicoesSolicitudSeccion requisitoPresentacion : listaRequisitosPorPersonal) {
							SicoesSolicitudSeccion requisitoSubsanacion = new SicoesSolicitudSeccion();
							BeanUtils.copyProperties(requisitoPresentacion, requisitoSubsanacion, "idSolicitudSeccion", "idPerConSec", "idSoliPersProp", "procSubsanacion", "procRevision", "estado", "fecCreacion", "usuCreacion", "ipCreacion", "fecActualizacion", "usuActualizacion", "ipActualizacion");
							requisitoSubsanacion.setIdPerConSec(sicoesTdSolPerConSecNew.getIdSolPerConSec());
							requisitoSubsanacion.setPersonalPropuesto(profesionalSubsanacionDB);
							if (requisitoPresentacion.getProcRevision().equals("1")) {
								requisitoSubsanacion.setProcSubsanacion("0");
								requisitoSubsanacion.setProcRevision("1");
							} else {
								requisitoSubsanacion.setProcSubsanacion("1");
								requisitoSubsanacion.setProcRevision("0");
								requisitoSubsanacion.setFechaEvaluacion(null);
								requisitoSubsanacion.setUsuarioEvaluacion(null);
								requisitoSubsanacion.setEvaluacion(null);
							}
							requisitoSubsanacion.setEstado("1");

							AuditoriaUtil.setAuditoriaRegistro(requisitoSubsanacion, contexto);
							SicoesSolicitudSeccion requisitoSubsanacionDB = sicoesSolicitudSeccionService.guardar(requisitoSubsanacion, contexto);
							List<Archivo> archivo = archivoService.buscarPorPerfContrato(requisitoPresentacion.getIdSolicitudSeccion(), contexto);

							if (archivo != null && !archivo.isEmpty()) {
								Archivo archivoNew = new Archivo();
								BeanUtils.copyProperties(archivo.get(0), archivoNew, "idArchivo", "idPerfContrato", "fecCreacion", "usuCreacion", "ipCreacion", "fecActualizacion", "usuActualizacion", "ipActualizacion");
								archivoNew.setIdSeccionRequisito(requisitoSubsanacionDB.getIdSolicitudSeccion());
								AuditoriaUtil.setAuditoriaRegistro(archivoNew, contexto);
								archivoService.guardarArchivoSubsanacionContrato(archivoNew, contexto);
							}
						}
					}
				} else {
					List<SicoesSolicitudSeccion> listaRequisitosPorSeccion = sicoesSolicitudSeccionService.obtenerRequisitosPorSeccionSub(seccionPadre.getIdSolPerConSec());
					for (SicoesSolicitudSeccion requisitoPresentacion : listaRequisitosPorSeccion) {
						SicoesSolicitudSeccion requisitoSubsanacion = new SicoesSolicitudSeccion();
						BeanUtils.copyProperties(requisitoPresentacion, requisitoSubsanacion, "idSolicitudSeccion", "idPerConSec", "idSoliPersProp", "procSubsanacion", "procRevision", "estado", "fecCreacion", "usuCreacion", "ipCreacion", "fecActualizacion", "usuActualizacion", "ipActualizacion");
						requisitoSubsanacion.setIdPerConSec(sicoesTdSolPerConSecNew.getIdSolPerConSec());
						if (requisitoPresentacion.getProcRevision().equals("1")) {
							requisitoSubsanacion.setProcSubsanacion("0");
							requisitoSubsanacion.setProcRevision("1");
						} else {
							requisitoSubsanacion.setProcSubsanacion("1");
							requisitoSubsanacion.setProcRevision("0");
							requisitoSubsanacion.setFechaEvaluacion(null);
							requisitoSubsanacion.setUsuarioEvaluacion(null);
							requisitoSubsanacion.setEvaluacion(null);
						}
						requisitoSubsanacion.setEstado("1");

						AuditoriaUtil.setAuditoriaRegistro(requisitoSubsanacion, contexto);
						SicoesSolicitudSeccion requisitoSubsanacionDB = sicoesSolicitudSeccionService.guardar(requisitoSubsanacion, contexto);
						List<Archivo> archivo = archivoService.buscarPorPerfContrato(requisitoPresentacion.getIdSolicitudSeccion(), contexto);

						if (archivo != null && !archivo.isEmpty()) {
							Archivo archivoNew = new Archivo();
							BeanUtils.copyProperties(archivo.get(0), archivoNew, "idArchivo", "idSeccionRequisito", "fecCreacion", "usuCreacion", "ipCreacion", "fecActualizacion", "usuActualizacion", "ipActualizacion");
							archivoNew.setIdSeccionRequisito(requisitoSubsanacionDB.getIdSolicitudSeccion());
							AuditoriaUtil.setAuditoriaRegistro(archivoNew, contexto);
							archivoService.guardarArchivoSubsanacionContrato(archivoNew, contexto);
						}
					}
				}

			}

		} catch (Exception e) {
			logger.error("Error al guardar secciones de subsanación", e);
		}

		return response;
	}

	@Override
	public SicoesTdSolPerConSec obtener(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SicoesTdSolPerConSec guardar(SicoesTdSolPerConSec model, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

}
