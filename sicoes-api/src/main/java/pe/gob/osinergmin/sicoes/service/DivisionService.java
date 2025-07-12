package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.model.dto.DivisionDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface DivisionService extends BaseService<Division, Long> {

	List<DivisionDTO> listarDivisiones();
	public List<Division> listarDivisionesPorIdProfesion(Long idProfesion);
	List<Division> listarDivisionesPorUsuario(Long idUsuario);
	List<DivisionDTO> listarDivisionesCoordinador(Contexto contexto);
}
