package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Listado;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface ListadoService extends BaseService<Listado, Long>  {

	public Listado obtener(Long idListado,Contexto contexto);
	
	public Page<Listado> buscar(Pageable pageable,Contexto contexto);

	Listado obtenerPorCodigo(String codigo, Contexto contexto);

}