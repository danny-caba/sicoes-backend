package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Persona;
import pe.gob.osinergmin.sicoes.repository.PersonaDao;
import pe.gob.osinergmin.sicoes.service.PersonaService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;


@Service
public class PersonaServiceImpl implements PersonaService{
	
	Logger logger = LogManager.getLogger(PersonaServiceImpl.class);

	@Autowired
	private PersonaDao personaDao;
	
	@Override
	public Persona obtener(Long id, Contexto contexto) {
		Persona persona= personaDao.obtener(id);
		return persona;
	}

	@Override
	public Persona guardar(Persona persona, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(persona,contexto);
		Persona personaBD=personaDao.save(persona);
		return personaBD;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		personaDao.deleteById(id);
	}

	@Override
	public Page<Persona> buscar(Pageable pageable, Contexto contexto) {
		Page<Persona> personas =personaDao.buscar(pageable);
		return personas;
	}
}
