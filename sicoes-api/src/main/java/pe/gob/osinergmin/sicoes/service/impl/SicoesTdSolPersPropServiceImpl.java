package pe.gob.osinergmin.sicoes.service.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.consumer.SissegApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SneApiConsumer;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.DetalleVacacionesDTO;
import pe.gob.osinergmin.sicoes.model.dto.EvaluacionPendienteDTO;
import pe.gob.osinergmin.sicoes.repository.AsignacionDao;
import pe.gob.osinergmin.sicoes.repository.AsignacionPerfilDivisionDao;
import pe.gob.osinergmin.sicoes.repository.EvaluacionPendienteDao;
import pe.gob.osinergmin.sicoes.repository.HistorialAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.HistorialVacacionesDao;
import pe.gob.osinergmin.sicoes.repository.PerfilAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.PerfilDivisionDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.repository.SicoesTdSoliPersPropDao;
import pe.gob.osinergmin.sicoes.repository.SolicitudDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.bean.siged.AccessRequestInFirmaDigital;


@Service
public class SicoesTdSolPersPropServiceImpl implements SicoesTdSolPersPropService{

	Logger logger = LogManager.getLogger(SicoesTdSolPersPropServiceImpl.class);

	@Autowired
	private SicoesTdSoliPersPropDao sicoesTdSoliPersPropDao;

	@Autowired
	private PropuestaProfesionalService propuestaProfesionalService;

	@Autowired
	private SicoesTdSolPerConSecService sicoesTdSolPerConSecService;

	@Override
	public SicoesTdSoliPersProp guardar(SicoesTdSoliPersProp sicoesTdSoliPersProp, Contexto contexto) {
		return sicoesTdSoliPersPropDao.save(sicoesTdSoliPersProp);
	}

	@Override
	public SicoesTdSoliPersProp obtener(Long id, Contexto contexto) {
		return sicoesTdSoliPersPropDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		// TODO Auto-generated method stub

	}



	@Override
	public SicoesTdSoliPersProp update(SicoesTdSoliPersProp sicoesTdSoliPersProp, Contexto contexto) {
		Optional<SicoesTdSoliPersProp> mdDt = sicoesTdSoliPersPropDao.findById(sicoesTdSoliPersProp.getIdSoliPersProp());
		SicoesTdSoliPersProp sicoesTdSoliPersPropFinal=mdDt.orElseThrow(() -> new RuntimeException("Seccion no encontrado"));
		sicoesTdSoliPersPropFinal.setFecActualizacion(new Date());

		 if (sicoesTdSoliPersProp.getSupervisora() != null) {
			 sicoesTdSoliPersPropFinal.setSupervisora(sicoesTdSoliPersProp.getSupervisora());
          }
		 if (sicoesTdSoliPersProp.getSicoesTdSolPerConSec() != null) {
			 sicoesTdSoliPersPropFinal.setSicoesTdSolPerConSec(sicoesTdSoliPersProp.getSicoesTdSolPerConSec());
          }
		 if (sicoesTdSoliPersProp.getDePerfilProfesional() != null) {
			 sicoesTdSoliPersPropFinal.setDePerfilProfesional(sicoesTdSoliPersProp.getDePerfilProfesional()) ;
          }
		 if (sicoesTdSoliPersProp.getTiSolicitud() != null) {
			 sicoesTdSoliPersPropFinal.setTiSolicitud(sicoesTdSoliPersProp.getTiSolicitud());
          }
		 if (sicoesTdSoliPersProp.getEsAdjuntoSolicitud() != null) {
			 sicoesTdSoliPersPropFinal.setEsAdjuntoSolicitud(sicoesTdSoliPersProp.getEsAdjuntoSolicitud());
          }
		 sicoesTdSoliPersPropFinal.setIpActualizacion(contexto.getIp());
		 sicoesTdSoliPersPropFinal.setUsuActualizacion(contexto.getUsuario().getUsuario());
		return  sicoesTdSoliPersPropDao.save(sicoesTdSoliPersPropFinal) ;
	 
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<SicoesTdSoliPersProp> registrarProfesionales(SicoesSolicitud resSolicitud, List<SicoesTdSolPerConSec> secciones, Contexto contexto) {
		List<SicoesTdSoliPersProp> response = new ArrayList<>();
		List<PropuestaProfesional> invitaciones = propuestaProfesionalService.listar(resSolicitud.getPropuesta().getPropuestaUuid(), contexto);

		List<PropuestaProfesional> propuestaProfesionales = invitaciones.stream()
				.filter(propuestaProfesional -> propuestaProfesional.getEstado().getCodigo().equals(Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO))
				.collect(Collectors.toList());

		List<SicoesTdSolPerConSec> lstSicoesTdSolPerConSec = secciones.stream().filter(seccion -> seccion.getFlConPersonal().equals(Constantes.FLAG_PERSONAL_PERF_CONTRATO.SI)).collect(Collectors.toList());

		for (SicoesTdSolPerConSec sicoesTdSolPerConSec: lstSicoesTdSolPerConSec) {
			for (PropuestaProfesional propuestaProfesional : propuestaProfesionales) {
				SicoesTdSoliPersProp sicoesTdSoliPersProp = new SicoesTdSoliPersProp();
				sicoesTdSoliPersProp.setSupervisora(propuestaProfesional.getSupervisora());
				sicoesTdSoliPersProp.setSicoesTdSolPerConSec(sicoesTdSolPerConSec);
				sicoesTdSoliPersProp.setDePerfilProfesional(propuestaProfesional.getPerfil().getDescripcion());
				sicoesTdSoliPersProp.setTiSolicitud(Constantes.TIPO_SOLICITUD_PERF_CONTRATO.INSCRIPCION);
				sicoesTdSoliPersProp.setEsAdjuntoSolicitud(Constantes.ESTADO.ACTIVO);
				AuditoriaUtil.setAuditoriaRegistro(sicoesTdSoliPersProp, contexto);
				response.add(sicoesTdSoliPersProp);
				sicoesTdSoliPersPropDao.save(sicoesTdSoliPersProp);
			}
		}
		return response;
	}

	@Override
	public List<SicoesTdSoliPersProp> obtenerProfesionalesPorSeccion(Long idSeccion) {
		return sicoesTdSoliPersPropDao.obtenerProfesionalesPorSeccion(idSeccion);
	}

	@Override
	public Page<SicoesTdSoliPersProp> personasPorSeccion(Long idSeccionPerConSec, Pageable pageable, Contexto contexto) {
		return sicoesTdSoliPersPropDao.personasPorSeccion(idSeccionPerConSec, pageable);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<SicoesTdSoliPersProp> registrarProfesionalesSubsanacion(List<SicoesTdSolPerConSec> seccionesSubsanacion, Contexto contexto) {

		List<SicoesTdSoliPersProp> response = new ArrayList<>();

		try {
			for (SicoesTdSolPerConSec sicoesTdSolPerConSec : seccionesSubsanacion) {
				if ("1".equals(sicoesTdSolPerConSec.getFlConPersonal())) {
					List<SicoesTdSoliPersProp> listaPersonal = obtenerProfesionalesPorSeccion(sicoesTdSolPerConSec.getIdSeccion());

					for (SicoesTdSoliPersProp sicoesTdSoliPersProp : listaPersonal) {
						SicoesTdSoliPersProp sicoesTdSoliPersPropNew = new SicoesTdSoliPersProp();
						BeanUtils.copyProperties(sicoesTdSoliPersProp, sicoesTdSoliPersPropNew, "idSoliPersProp", "fecCreacion", "usuCreacion", "ipCreacion", "fecActualizacion", "usuActualizacion", "ipActualizacion");

						AuditoriaUtil.setAuditoriaRegistro(sicoesTdSoliPersPropNew, contexto);
						response.add(sicoesTdSoliPersPropNew);
					}
				}
			}

			sicoesTdSoliPersPropDao.saveAll(response);
		} catch (Exception e) {
			logger.error("Error al guardar solicitud", e);
		}

		return response;
	}


}
