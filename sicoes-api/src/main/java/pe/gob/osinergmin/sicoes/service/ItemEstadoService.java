package pe.gob.osinergmin.sicoes.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.ItemEstado;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface ItemEstadoService extends BaseService<ItemEstado, Long> {

	Page<ItemEstado> listarItemsEstado(String procesoItemUuid, Pageable pageable, Contexto contexto);

	ItemEstado obtener(Long idItemEstado, String procesoItemUuid, Contexto contexto);

	void eliminar(Long id, String procesoItemUuid, Contexto contexto);

}