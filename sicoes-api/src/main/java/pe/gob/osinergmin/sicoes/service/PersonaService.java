package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Persona;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface PersonaService extends BaseService<Persona, Long>  {

	public Persona obtener(Long idPersona,Contexto contexto);
	
	public Page<Persona> buscar(Pageable pageable,Contexto contexto);

}