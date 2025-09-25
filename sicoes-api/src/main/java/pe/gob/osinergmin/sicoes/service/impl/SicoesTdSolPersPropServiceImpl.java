package pe.gob.osinergmin.sicoes.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.repository.SicoesTdSoliPersPropDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;


@Service
public class SicoesTdSolPersPropServiceImpl implements SicoesTdSolPersPropService{

	private static final Logger logger = LogManager.getLogger(SicoesTdSolPersPropServiceImpl.class);

	private final SicoesTdSoliPersPropDao sicoesTdSoliPersPropDao;
	private final PropuestaProfesionalService propuestaProfesionalService;
	private final ListadoDetalleService listadoDetalleService;
	private final SupervisoraMovimientoService supervisoraMovimientoService;

	@Autowired
	public SicoesTdSolPersPropServiceImpl(SicoesTdSoliPersPropDao sicoesTdSoliPersPropDao,
										  PropuestaProfesionalService propuestaProfesionalService,
										  ListadoDetalleService listadoDetalleService,
										  SupervisoraMovimientoService supervisoraMovimientoService) {
		this.sicoesTdSoliPersPropDao = sicoesTdSoliPersPropDao;
		this.propuestaProfesionalService = propuestaProfesionalService;
		this.listadoDetalleService = listadoDetalleService;
		this.supervisoraMovimientoService = supervisoraMovimientoService;
	}


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
		 if (sicoesTdSoliPersProp.getPerfil() != null) {
			 sicoesTdSoliPersPropFinal.setPerfil(sicoesTdSoliPersProp.getPerfil());
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
				sicoesTdSoliPersProp.setPerfil(propuestaProfesional.getPerfil());
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
	public Page<SicoesTdSoliPersProp> personasPorIdSolicitud(Long idSolicitud, Pageable pageable) {
		Page<SicoesTdSoliPersProp> allPage = sicoesTdSoliPersPropDao.personasPorSolicitud(idSolicitud,Pageable.unpaged());
		List<SicoesTdSoliPersProp> todas = allPage.getContent();

		ListadoDetalle listadoDetalle = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO,
				Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO);

		Contexto ctx = AuditoriaUtil.getContextoJob();

		List<SicoesTdSoliPersProp> filtradas = todas.stream()
				.filter(ss -> {
					if (ss.getSupervisora() == null || ss.getSupervisora().getIdSupervisora() == null) return false;
					SupervisoraMovimiento mov = supervisoraMovimientoService
							.ultimoMovimiento(ss.getSupervisora().getIdSupervisora(), ctx);
					return mov != null
							&& mov.getEstado() != null
							&& Objects.equals(
							mov.getEstado().getIdListadoDetalle(),
							listadoDetalle.getIdListadoDetalle()
					);
				})
				.collect(Collectors.toList());

		int start = (int) Math.min(pageable.getOffset(), filtradas.size());
		int end   = Math.min(start + pageable.getPageSize(), filtradas.size());
		List<SicoesTdSoliPersProp> pageContent = filtradas.subList(start, end);
		return new PageImpl<>(pageContent, pageable, filtradas.size());
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
