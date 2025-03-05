package pe.gob.osinergmin.sicoes.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.model.PerfilDivision;
import pe.gob.osinergmin.sicoes.repository.DivisionAdicionalDao;
import pe.gob.osinergmin.sicoes.repository.DivisionDao;
import pe.gob.osinergmin.sicoes.repository.PerfilDivisionDao;
import pe.gob.osinergmin.sicoes.service.DivisionService;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class DivisionServiceImpl implements DivisionService {
	
	Logger logger = LogManager.getLogger(ProfesionServiceImpl.class);
	
	@Autowired
	private DivisionAdicionalDao divisionAdicionalDao;

	@Autowired
	private DivisionDao divisionDao;

	@Autowired
	private PerfilDivisionDao perfilDivisionDao;

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
	public List<Division> listarDivisiones() {
		return divisionDao.findAll();
	}

	@Override
	public List<Division> listarDivisionesPorIdProfesion(Long idProfesion) {
		return divisionAdicionalDao.listarDivisionesPorIdProfesion(idProfesion);
	}

	@Override
	public List<Division> listarDivisionesPorUsuario(Long idUsuario) {
		List<PerfilDivision> resultados = perfilDivisionDao.obtenerDivisionesPorUsuario(idUsuario);

        return resultados.stream()
				.map(PerfilDivision::getDivision)
				.distinct()
				.collect(Collectors.toList());
	}

}
