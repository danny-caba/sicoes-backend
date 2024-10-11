package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import pe.gob.osinergmin.sicoes.model.Division;

public interface DivisionAdicionalDao {
	
	public List<Division> listarDivisionesPorIdProfesion(Long idProfesion);

}
