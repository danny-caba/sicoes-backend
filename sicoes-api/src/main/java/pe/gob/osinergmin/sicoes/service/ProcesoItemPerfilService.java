package pe.gob.osinergmin.sicoes.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.ProcesoItemPerfil;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface ProcesoItemPerfilService extends BaseService<ProcesoItemPerfil, Long> {

	Page<ProcesoItemPerfil> listarPerfiles(String procesoItemUuid, Pageable pageable, Contexto contexto);

	void eliminarPerfil(Long idItemPerfil, String procesoItemUuid, Contexto contexto);

	ProcesoItemPerfil obtener(Long idProcesoItemPerfil, String procesoItemUuid, Contexto contexto);

	List<ProcesoItemPerfil> listar(String procesoItemUuid, Contexto contexto);

	List<Object[]> listarProfesionalesXPerfil(String procesoItemUuid,Contexto contexto);

	List<ProcesoItemPerfil> buscar(Long idProceso);

}