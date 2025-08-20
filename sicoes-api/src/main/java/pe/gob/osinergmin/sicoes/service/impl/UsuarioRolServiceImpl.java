package pe.gob.osinergmin.sicoes.service.impl;



import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.UsuarioRol;
import pe.gob.osinergmin.sicoes.model.UsuarioRolConfiguracion;
import pe.gob.osinergmin.sicoes.repository.ConfBandejaDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioEvaluacionDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioRolConfiguracionDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioRolDao;
import pe.gob.osinergmin.sicoes.service.UsuarioRolService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class UsuarioRolServiceImpl extends BaseService implements UsuarioRolService {

	private Logger logger = LogManager.getLogger(UsuarioRolServiceImpl.class);

	@Autowired
	UsuarioRolDao usuarioRolDao;
	
	@Autowired
	UsuarioRolConfiguracionDao usuarioRolConfiguracionDao;
	
	@Autowired
	UsuarioEvaluacionDao usuarioEvaluacionDao;
	
	@Autowired
	ConfBandejaDao confBandejaDao;
	
	@Transactional(rollbackFor = Exception.class)
	public UsuarioRol guardar(UsuarioRol usuarioRol, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(usuarioRol, contexto);
		return usuarioRolDao.save(usuarioRol);
	}

	@Override
	public UsuarioRol obtener(Long id, Contexto contexto) {
		return usuarioRolDao.getOne(id);
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		usuarioRolDao.deleteById(id);		
	}
	
	@Override
	public UsuarioRol registrarUsuarioRol(UsuarioRol usuarioRol,Contexto contexto) {
		 AuditoriaUtil.setAuditoriaRegistro(usuarioRol,contexto);
		 return usuarioRolDao.save(usuarioRol);
	}
	
	@Override
	public void actualizarEstadoUsuarioRol(UsuarioRol usuarioRol,Contexto contexto) {
		 AuditoriaUtil.setAuditoriaRegistro(usuarioRol,contexto);
		 usuarioRolDao.actualizarEstadoUsuarioRol(usuarioRol.getIdUsuarioRol(), usuarioRol.getEstadoUsuarioRol(), usuarioRol.getUsuCreacion(), usuarioRol.getIpCreacion());
		 
		 List<UsuarioRolConfiguracion> lista = usuarioEvaluacionDao.obtenerUsuarioRolConfiguraciones(usuarioRol);
		 
		 for (UsuarioRolConfiguracion usuarioRolConfiguracion : lista) {
			  confBandejaDao.actualizarEstadoConfigBandeja(usuarioRolConfiguracion.getIdConfiguracionBandeja(), Constantes.ESTADO.INACTIVO, usuarioRolConfiguracion.getUsuCreacion(), usuarioRolConfiguracion.getIpCreacion());
			  usuarioRolConfiguracionDao.actualizarEstadoUsuarioRolConf(usuarioRolConfiguracion.getIdUsuarioRolConfig(), Constantes.ESTADO.INACTIVO, usuarioRolConfiguracion.getUsuCreacion(), usuarioRolConfiguracion.getIpCreacion());
		}
	}
	
	@Override
	public UsuarioRolConfiguracion registrarUsuarioRolConfiguracion(UsuarioRolConfiguracion usuarioRolConfiguracion,Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(usuarioRolConfiguracion,contexto);
		return usuarioRolConfiguracionDao.save(usuarioRolConfiguracion);
	}
	
	@Override
	public void actualizarEstadoUsuarioRolConf(UsuarioRolConfiguracion usuarioRolConfiguracion,Contexto contexto) {
		AuditoriaUtil.setAuditoriaActualizacion(usuarioRolConfiguracion, contexto);
		usuarioRolConfiguracionDao.actualizarEstadoUsuarioRolConf(usuarioRolConfiguracion.getIdUsuarioRolConfig(), usuarioRolConfiguracion.getEstadoUsuarioRolConfig(), usuarioRolConfiguracion.getUsuActualizacion(), usuarioRolConfiguracion.getIpActualizacion());
	}

}
