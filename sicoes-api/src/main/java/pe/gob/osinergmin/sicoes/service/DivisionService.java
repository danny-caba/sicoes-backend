package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import pe.gob.osinergmin.sicoes.model.Division;

public interface DivisionService extends BaseService<Division, Long> {
	
	public List<Division> listarDivisionesPorIdProfesion(Long idProfesion);

}
