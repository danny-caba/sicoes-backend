package pe.gob.osinergmin.sicoes.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.DictamenEvaluacion;
import pe.gob.osinergmin.sicoes.model.SupervisoraDictamen;
import pe.gob.osinergmin.sicoes.model.dto.SupervisoraDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;


public interface SupervisoraDictamenService extends BaseService<SupervisoraDictamen, Long> {

	
	public SupervisoraDictamen obtenerXSupervisora(Long idSupervisora, Long idSector, Contexto contexto) ;

	public List<SupervisoraDictamen> listarXSupervisora(Long idSupervisora, Contexto contexto);
	
	public Page<SupervisoraDTO> buscarXMonto(
		String nroExpediente,
		Long idTipoPersona,
		Long idTipoDocumento,
		String ruc,
		String nombres,
		String perfil,
		String fechaInicio,
		String fechaFin,
		Pageable pageable,
		Contexto contexto
	);

}