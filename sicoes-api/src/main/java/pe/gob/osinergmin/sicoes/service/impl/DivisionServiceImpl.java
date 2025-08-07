package pe.gob.osinergmin.sicoes.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.model.PerfilDivision;
import pe.gob.osinergmin.sicoes.model.dto.DivisionDTO;
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
		return divisionDao.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DivisionDTO> listarDivisiones() {
		return divisionDao.findAll().stream()
				.map(division -> {
					DivisionDTO dto = new DivisionDTO();
					dto.setIdDivision(division.getIdDivision());
					dto.setDeDivision(division.getDeDivision());
					return dto;
				})
				.collect(Collectors.toList());
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

	@Override
	public List<DivisionDTO> listarDivisionesCoordinador(Contexto contexto) {
		List<Division> divisiones = divisionDao.findByIdUsuario(contexto.getUsuario().getIdUsuario());
		if (divisiones.isEmpty()) {
			return Collections.emptyList();
		}
		return divisiones.stream()
				.map(division -> {
					DivisionDTO dto = new DivisionDTO();
					dto.setIdDivision(division.getIdDivision());
					dto.setDeDivision(division.getDeDivision());
					return dto;
				})
				.collect(Collectors.toList());
	}

}
