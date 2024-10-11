package pe.gob.osinergmin.sicoes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Profesion;
import pe.gob.osinergmin.sicoes.model.dto.ProfesionDTO;
import pe.gob.osinergmin.sicoes.repository.ProfesionDao;
import pe.gob.osinergmin.sicoes.service.ProfesionService;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class ProfesionServiceImpl implements ProfesionService {
	
	Logger logger = LogManager.getLogger(ProfesionServiceImpl.class);
	
	@Autowired
	private ProfesionDao profesionDao;

	@Override
	public Profesion guardar(Profesion model, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profesion obtener(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<ProfesionDTO> listarProfesionesTodas() {
		List<Profesion> listaProfesiones = profesionDao.listarTodos();
		List<ProfesionDTO> listaProfesionesDto = new ArrayList<ProfesionDTO>();
		ProfesionDTO dto = null;
		
		for (Profesion profesion : listaProfesiones) {
			dto = new ProfesionDTO();
			dto.setIdProfesion(profesion.getIdProfesion());
			dto.setDeProfesion(profesion.getDeProfesion());
			listaProfesionesDto.add(dto);
		}
				
		return listaProfesionesDto;
	}

}
