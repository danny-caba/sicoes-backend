package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.SupervisoraRepresentante;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface SupervisoraRepresentanteService extends BaseService<SupervisoraRepresentante, Long>{

	public SupervisoraRepresentante obtener(Long idSupervisoraRepresentante,Contexto contexto);
	
	public Page<SupervisoraRepresentante> buscar(Long idOpcion,Pageable pageable);
	public SupervisoraRepresentante obtenerXIdSupervisora(Long idSupervisora, Contexto contexto);

}