package pe.gob.osinergmin.sicoes.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.repository.DivisionAdicionalDao;
import pe.gob.osinergmin.sicoes.service.DivisionService;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class DivisionServiceImpl implements DivisionService {
	
	Logger logger = LogManager.getLogger(ProfesionServiceImpl.class);
	
	@Autowired
	private DivisionAdicionalDao divisionAdicionalDao;

	@Override
	public Division guardar(Division model, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Division obtener(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Division> listarDivisionesPorIdProfesion(Long idProfesion) {
		return divisionAdicionalDao.listarDivisionesPorIdProfesion(idProfesion);
	}
	
}
