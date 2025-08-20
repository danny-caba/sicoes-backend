package pe.gob.osinergmin.sicoes.service.impl;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.DivisionDTO;
import pe.gob.osinergmin.sicoes.repository.*;
import pe.gob.osinergmin.sicoes.service.ConfBandejaService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.ListadoService;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;


@Service
public class ConfBandejaServiceImpl implements ConfBandejaService {

	Logger logger = LogManager.getLogger(ConfBandejaServiceImpl.class);

	@Autowired
	private ConfBandejaDao confBandejaDao;
	
	@Autowired
	private UsuarioEvaluacionDao usuarioEvaluacionDao;
	
	@Autowired
	private UsuarioRolConfiguracionDao usuarioRolConfiguracionDao;
	
	@Autowired
	private DivisionDao divisionDao;
	
	@Autowired
	private AsignacionDao asignacionDao;
	
	@Autowired
	private UsuarioRolDao usuarioRolDao;
	
	@Autowired
	private UsuarioReasignacionDao usuarioReasignacionDao;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Autowired
	private ListadoService listadoService;

	@Autowired
	private PerfilDivisionDao perfilDivisionDao;

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public ConfiguracionBandeja obtener(Long idConfBandeja, Contexto contexto) {
		ConfiguracionBandeja confBandeja = confBandejaDao.obtener(idConfBandeja);
		return confBandeja;
	}

	@Transactional(rollbackFor = Exception.class)
	public ConfiguracionBandeja guardar(ConfiguracionBandeja confBandeja, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(confBandeja, contexto);		
		ConfiguracionBandeja confBandejaBD= confBandejaDao.save(confBandeja);	
		return confBandejaBD;
	}

	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, Contexto contexto) {
		confBandejaDao.deleteById(id);

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Map<String, List<ConfiguracionBandeja>>> registrarConfiguracionBandeja(ConfiguracionBandeja configuracionBandeja,Contexto contexto) {

		List<Map<String, List<ConfiguracionBandeja>>> response = new ArrayList<>();

		if (configuracionBandeja.getDivision().getIdDivision() == null && configuracionBandeja.getPerfil().getIdListadoDetalle() == null) {
			throw new ValidacionException("No se ha seleccionado un perfil o una división");
		}

		List<ListadoDetalle> listaPerfiles = new ArrayList<>();

		if (configuracionBandeja.getPerfil().getIdListadoDetalle() == null) {
			List<PerfilDivision> perfiles = perfilDivisionDao.obtenerPorIdDivision(configuracionBandeja.getDivision().getIdDivision());
			listaPerfiles = perfiles
					.stream()
					.map(PerfilDivision::getPerfil)
					.collect(Collectors.toList());
		} else {
			listaPerfiles.add(configuracionBandeja.getPerfil());
		}

		Map<Boolean, List<ConfiguracionBandeja>> resultado = listaPerfiles.parallelStream()
				.map(perfil -> {

					int cantidadPerfilUsuario = confBandejaDao.contarConfiguracionPorPerfilUsuario(perfil.getIdListadoDetalle(), configuracionBandeja.getUsuario().getIdUsuario());
					logger.info("cantidadPerfilUsuario: " + cantidadPerfilUsuario);

					ConfiguracionBandeja conf = new ConfiguracionBandeja();
					Map<String, ListadoDetalle> detalles = obtenerJerarquiaListadoDetalle(perfil, contexto);
					if (detalles == null) {
						return null;
					}
					Usuario usuario = usuarioService.obtener(configuracionBandeja.getUsuario().getIdUsuario());

					conf.setUsuario(usuario);
					conf.setPerfil(detalles.get("perfil"));
					conf.setSector(detalles.get("sector"));
					conf.setSubsector(detalles.get("subsector"));
					conf.setActividad(detalles.get("actividad"));
					conf.setUnidad(detalles.get("unidad"));
					conf.setSubCategoria(detalles.get("subcategoria"));
					conf.setEstadoConfiguracion(Constantes.ESTADO.ACTIVO);
					conf.setTipoConfiguracion(
							listadoDetalleService.obtenerListadoDetalle(
									Constantes.LISTADO.TIPO_CONFIGURACION.CODIGO,
									Constantes.LISTADO.TIPO_CONFIGURACION.EVALUADORES
							)
					);

					if (cantidadPerfilUsuario > 0) {
						return conf;
					}

					AuditoriaUtil.setAuditoriaRegistro(conf, contexto);
					ConfiguracionBandeja confDB = confBandejaDao.save(conf);

					if (confDB.getIdUsuarioRolC() != null) {
						UsuarioRolConfiguracion usuarioRolConfiguracion = new UsuarioRolConfiguracion();
						usuarioRolConfiguracion.setIdConfiguracionBandeja(confDB.getIdConfiguracionBandeja());
						usuarioRolConfiguracion.setIdUsuarioRol(confDB.getIdUsuarioRolC());
						usuarioRolConfiguracion.setEstadoUsuarioRolConfig(Constantes.ESTADO.ACTIVO);
						AuditoriaUtil.setAuditoriaRegistro(usuarioRolConfiguracion, contexto);
						usuarioRolConfiguracionDao.save(usuarioRolConfiguracion);
					}

					return confDB;
				})
				.filter(Objects::nonNull)
				.collect(Collectors.partitioningBy(configuracion -> configuracion.getIdConfiguracionBandeja() != null));
		List<ConfiguracionBandeja> configuracionesNuevas = resultado.get(true);
		List<ConfiguracionBandeja> configuracionesExistentes = resultado.get(false);

		response.add(new HashMap<String, List<ConfiguracionBandeja>>() {{
			put("nuevas", configuracionesNuevas);
			put("existentes", configuracionesExistentes);
		}});

		return response;

	}

	private Map<String, ListadoDetalle> obtenerJerarquiaListadoDetalle(ListadoDetalle perfil, Contexto contexto) {
		Map<String, ListadoDetalle> detalles = new HashMap<>();

		ListadoDetalle perfilDB = listadoDetalleService.obtener(perfil.getIdListadoDetalle(), contexto);
		Listado listadoPerfil = listadoService.obtenerPorCodigo(Constantes.LISTADO.PERFILES, contexto);

		if (!perfilDB.getIdListado().equals(listadoPerfil.getIdListado())) {
			return null;
		}

		ListadoDetalle subcategoria = listadoDetalleService.obtener(perfilDB.getIdListadoSuperior(), contexto);
		ListadoDetalle unidad = listadoDetalleService.obtener(subcategoria.getIdListadoSuperior(), contexto);
		ListadoDetalle actividad = listadoDetalleService.obtener(unidad.getIdListadoSuperior(), contexto);
		ListadoDetalle subsector = listadoDetalleService.obtener(actividad.getIdListadoSuperior(), contexto);
		ListadoDetalle sector = listadoDetalleService.obtener(subsector.getIdListadoSuperior(), contexto);

		detalles.put("perfil", perfilDB);
		detalles.put("subcategoria", subcategoria);
		detalles.put("unidad", unidad);
		detalles.put("actividad", actividad);
		detalles.put("subsector", subsector);
		detalles.put("sector", sector);

		return detalles;
	}
	
	@Override
	public ConfiguracionBandeja actualizarConfiguracionBandeja(ConfiguracionBandeja configuracionBandeja,Contexto contexto) {
		

		List<ConfiguracionBandeja> listaConfBandeja = confBandejaDao.obtenerConfiguracionPorPerfil(configuracionBandeja.getPerfil().getIdListadoDetalle());
		ConfiguracionBandeja conf = new ConfiguracionBandeja();
		if (listaConfBandeja!=null && !listaConfBandeja.isEmpty()) {
			conf = listaConfBandeja.get(0);
		}
		
		configuracionBandeja.setSector(new ListadoDetalle());
		configuracionBandeja.getSector().setIdListadoDetalle(conf.getSector().getIdListadoDetalle());
		configuracionBandeja.setSubsector(new ListadoDetalle());
		configuracionBandeja.getSubsector().setIdListadoDetalle(conf.getSubsector().getIdListadoDetalle());
		configuracionBandeja.setActividad(new ListadoDetalle());
		configuracionBandeja.getActividad().setIdListadoDetalle(conf.getActividad().getIdListadoDetalle());
		configuracionBandeja.setUnidad(new ListadoDetalle());
		configuracionBandeja.getUnidad().setIdListadoDetalle(conf.getUnidad().getIdListadoDetalle());
		configuracionBandeja.setSubCategoria(new ListadoDetalle());
		configuracionBandeja.getSubCategoria().setIdListadoDetalle(conf.getSubCategoria().getIdListadoDetalle());
		
		

		AuditoriaUtil.setAuditoriaActualizacion(configuracionBandeja,contexto);
		
		
		ConfiguracionBandeja configuracion = confBandejaDao.save(configuracionBandeja);
		
		UsuarioRolConfiguracion usuarioRolConfiguracion = new UsuarioRolConfiguracion();
		usuarioRolConfiguracion.setIdConfiguracionBandeja(configuracion.getIdConfiguracionBandeja());
		usuarioRolConfiguracion.setIdUsuarioRol(configuracion.getIdUsuarioRolC());
		usuarioRolConfiguracion.setEstadoUsuarioRolConfig(Constantes.ESTADO.ACTIVO);
		AuditoriaUtil.setAuditoriaRegistro(usuarioRolConfiguracion, contexto);
		usuarioRolConfiguracionDao.save(usuarioRolConfiguracion);
		
		return configuracion;
	}
	
	@Override
	public void actualizarEstadoConfigBandeja(ConfiguracionBandeja configuracionBandeja,Contexto contexto) {
		 AuditoriaUtil.setAuditoriaRegistro(configuracionBandeja,contexto);
		 confBandejaDao.actualizarEstadoConfigBandeja(configuracionBandeja.getIdConfiguracionBandeja(), configuracionBandeja.getEstadoConfiguracion(), configuracionBandeja.getUsuCreacion(), configuracionBandeja.getIpCreacion());
	}
	
	
	@Override
	public Map<String, Object> listarPerfiles(Long idUsuario, int offset, int pageSize) {
		Map<String, Object> info = new HashMap<>();
		
		info.put("totalRegistros", usuarioEvaluacionDao.obtenerTotalRegistrosPerfiles(idUsuario));
		info.put("lista", usuarioEvaluacionDao.listarPerfiles(idUsuario, offset, pageSize));
		return info;
		 
	}
	
	@Override
	public Map<String, Object> listarConfigBandejaPorIdUsuario(Long idUsuario, Long idPerfil, Long idRol, int offset, int pageSize) {
		Map<String, Object> info = new HashMap<>();
		
		info.put("totalRegistros", usuarioEvaluacionDao.obtenerTotalRegistrosConfiguraciones(idUsuario, idPerfil, idRol));
		info.put("lista", usuarioEvaluacionDao.listarConfiguraciones(idUsuario, idPerfil, idRol, offset, pageSize));
		return info;
		 
	}
	
	@Override
	public List<ConfiguracionBandeja> listarConfiguracionesReasignadas(Long idUsuario) {
		
		List<UsuarioRol> listaUsuarioRol = usuarioRolDao.listarUsuarioRolXIdUsuario(idUsuario);
		boolean aplicaReasignacion = false;
		String codigoRol = "";
		for (UsuarioRol usuarioRol : listaUsuarioRol) {
			if (usuarioRol.getRol().getCodigo().equals("04")) { //Evaluador tecnico
				aplicaReasignacion = true;
				codigoRol = usuarioRol.getRol().getCodigo();
				break;
			}else if(usuarioRol.getRol().getCodigo().equals("05")){ //Aprobador tecnico
				aplicaReasignacion = true;
				codigoRol = usuarioRol.getRol().getCodigo();
				break;
			}
		}
		
		if (!aplicaReasignacion) {
			return null;
		}
		
		List<ConfiguracionBandeja> listaConfiguracionBandeja = usuarioEvaluacionDao.listarConfiguracionesReasignadas(idUsuario);
		for (ConfiguracionBandeja configuracionBandeja : listaConfiguracionBandeja) {
			configuracionBandeja.setCodigoRol(codigoRol);
		}
		
		return listaConfiguracionBandeja;
		 
	}
	
	@Override
	public List<UsuarioReasignacion> listarBandejaHistorialReasignaciones(String nombreUsuario, String fechaInicio, String fechaFin, Long idDivision, int offset, int pageSize) {
		return usuarioEvaluacionDao.listarBandejaHistorialReasignaciones(nombreUsuario,fechaInicio,fechaFin,idDivision,offset,pageSize);
		 
	}
	
	/*@Override
	public List<Division> obtenerDivisiones() {
		return divisionDao.obtener();
		 
	}*/
	
	@Override
	public List<DivisionDTO> obtenerDivisiones() {
	    return divisionDao.obtener().stream()
	        .map(d -> {
	            DivisionDTO dto = new DivisionDTO();
	            dto.setIdDivision(d.getIdDivision());
	            dto.setDeDivision(d.getDeDivision());
	            return dto;
	        })
	        .collect(Collectors.toList());
	}
	
	@Override
	public void obtenerReasignacionesFinalizadas(Contexto contexto) {
		
		List<Asignacion> listaAsignaciones = usuarioEvaluacionDao.obtenerReasignacionesFinalizadas();
		
		UsuarioReasignacion usuarioReasignacion = new UsuarioReasignacion();
		AuditoriaUtil.setAuditoriaActualizacion(usuarioReasignacion,contexto);
		for (Asignacion asignacion : listaAsignaciones) {
			asignacionDao.actualizarUsuario(asignacion.getIdAsignacion(), asignacion.getIdUsuarioOrigen(),null);
			usuarioReasignacionDao.finalizarReasignacion(asignacion.getUsuario().getIdUsuario(), asignacion.getConfiguracionBandeja().getIdConfiguracionBandeja(), usuarioReasignacion.getUsuActualizacion(), usuarioReasignacion.getIpActualizacion());
		}
		
		 
	}

}
