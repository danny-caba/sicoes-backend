package pe.gob.osinergmin.sicoes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.RolOpcion;
import pe.gob.osinergmin.sicoes.repository.RolOpcionDao;
import pe.gob.osinergmin.sicoes.service.RolOpcionService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class RolOpcionServiceImpl implements RolOpcionService{

	@Autowired
	RolOpcionDao rolOpcionDao; 
	
	@Override
	public RolOpcion guardar(RolOpcion rolOpcion, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(rolOpcion, contexto);
		return rolOpcionDao.save(rolOpcion);
	}

	@Override
	public RolOpcion obtener(Long id, Contexto contexto) {
		return rolOpcionDao.getOne(id);
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		rolOpcionDao.deleteById(id);
		
	}

	@Override
	public void eliminar(Long idRol, Long idOpcion) {
		rolOpcionDao.eliminar(idRol, idOpcion);
		
	}
	
}
