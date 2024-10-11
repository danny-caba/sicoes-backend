package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.SupervisoraRepresentante;
import pe.gob.osinergmin.sicoes.repository.SupervisoraRepresentanteDao;
import pe.gob.osinergmin.sicoes.service.SupervisoraRepresentanteService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;


@Service
public class SupervisoraRepresentanteServiceImpl implements SupervisoraRepresentanteService{
	
	Logger logger = LogManager.getLogger(SupervisoraRepresentanteServiceImpl.class);

	@Autowired
	private SupervisoraRepresentanteDao supervisoraRepresentanteDao;
	
	@Override
	public SupervisoraRepresentante obtener(Long idRepresentante, Contexto contexto) {
		SupervisoraRepresentante representante= supervisoraRepresentanteDao.obtener(idRepresentante);
		return representante;
	}
	
	@Override
	public SupervisoraRepresentante obtenerXIdSupervisora(Long idSupervisora, Contexto contexto) {
		SupervisoraRepresentante representante= supervisoraRepresentanteDao.obtenerXIdSupervisora(idSupervisora);
		return representante;
	}
	
	;
	
	@Override
	public Page<SupervisoraRepresentante> buscar(Long idOpcion, Pageable pageable) {
		Page<SupervisoraRepresentante> representantes =supervisoraRepresentanteDao.buscar(idOpcion, pageable);
		return representantes;
	}

	@Transactional(rollbackFor = Exception.class)
	public SupervisoraRepresentante guardar(SupervisoraRepresentante representante, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(representante,contexto);
		return supervisoraRepresentanteDao.save(representante);
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		supervisoraRepresentanteDao.deleteById(id);		
	}
}
