package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Representante;
import pe.gob.osinergmin.sicoes.repository.RepresentanteDao;
import pe.gob.osinergmin.sicoes.service.RepresentanteService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;


@Service
public class RepresentanteServiceImpl implements RepresentanteService{
	
	Logger logger = LogManager.getLogger(RepresentanteServiceImpl.class);

	@Autowired
	private RepresentanteDao representanteDao;
	
	@Override
	public Representante obtener(Long idRepresentante, Contexto contexto) {
		Representante representante= representanteDao.obtener(idRepresentante);
		return representante;
	}
	
	@Override
	public Page<Representante> buscar(Long idOpcion, Pageable pageable) {
		Page<Representante> representantes =representanteDao.buscar(idOpcion, pageable);
		return representantes;
	}

	@Transactional(rollbackFor = Exception.class)
	public Representante guardar(Representante representante, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(representante,contexto);
		return representanteDao.save(representante);
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		representanteDao.deleteById(id);		
	}
}
