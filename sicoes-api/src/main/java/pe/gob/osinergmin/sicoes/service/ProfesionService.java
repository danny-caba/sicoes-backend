package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import pe.gob.osinergmin.sicoes.model.Profesion;
import pe.gob.osinergmin.sicoes.model.dto.ProfesionDTO;

public interface ProfesionService extends BaseService<Profesion, Long> {
	
	public List<ProfesionDTO> listarProfesionesTodas();

}
