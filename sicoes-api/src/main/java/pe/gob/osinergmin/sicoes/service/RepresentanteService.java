package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Representante;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface RepresentanteService extends BaseService<Representante, Long>{

	public Representante obtener(Long idRepresentante,Contexto contexto);
	
	public Page<Representante> buscar(Long idOpcion,Pageable pageable);

}