package pe.gob.osinergmin.sicoes.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.PropuestaEconomica;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface PropuestaEconomicaService extends BaseService<PropuestaEconomica, Long> {

	public Page<PropuestaEconomica> listarPropuestasEconomicas(Pageable pageable, Contexto contexto);

	public PropuestaEconomica obtener(Long id, String codigo, Contexto contexto);

	public void eliminar(Long id, String uuidPropuesta, Contexto contexto);
}