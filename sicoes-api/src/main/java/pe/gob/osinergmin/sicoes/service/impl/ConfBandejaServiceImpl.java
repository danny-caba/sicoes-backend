package pe.gob.osinergmin.sicoes.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.ConfiguracionBandeja;
import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.UsuarioReasignacion;
import pe.gob.osinergmin.sicoes.model.UsuarioRol;
import pe.gob.osinergmin.sicoes.model.UsuarioRolConfiguracion;
import pe.gob.osinergmin.sicoes.repository.AsignacionDao;
import pe.gob.osinergmin.sicoes.repository.ConfBandejaDao;
import pe.gob.osinergmin.sicoes.repository.DivisionDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioEvaluacionDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioReasignacionDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioRolConfiguracionDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioRolDao;
import pe.gob.osinergmin.sicoes.service.ConfBandejaService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;


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

	@Override
	public ConfiguracionBandeja registrarConfiguracionBandeja(ConfiguracionBandeja configuracionBandeja,Contexto contexto) {
		

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
		configuracionBandeja.setEstadoConfiguracion(Constantes.ESTADO.ACTIVO);
		configuracionBandeja.setTipoConfiguracion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_CONFIGURACION.CODIGO, Constantes.LISTADO.TIPO_CONFIGURACION.EVALUADORES));//AFC
		
		AuditoriaUtil.setAuditoriaRegistro(configuracionBandeja,contexto);
		
		ConfiguracionBandeja configuracion = confBandejaDao.save(configuracionBandeja);
		
		if (configuracion.getIdUsuarioRolC()!=null) {
			UsuarioRolConfiguracion usuarioRolConfiguracion = new UsuarioRolConfiguracion();
			usuarioRolConfiguracion.setIdConfiguracionBandeja(configuracion.getIdConfiguracionBandeja());
			usuarioRolConfiguracion.setIdUsuarioRol(configuracion.getIdUsuarioRolC());
			usuarioRolConfiguracion.setEstadoUsuarioRolConfig(Constantes.ESTADO.ACTIVO);
			AuditoriaUtil.setAuditoriaRegistro(usuarioRolConfiguracion, contexto);
			usuarioRolConfiguracionDao.save(usuarioRolConfiguracion);
		}
		
		return configuracion;
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
	
	@Override
	public List<Division> obtenerDivisiones() {
		return divisionDao.obtener();
		 
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
