package pe.gob.osinergmin.sicoes.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.repository.DivisionAdicionalDao;

@Repository
public class DivisionAdicionalDaoImpl implements DivisionAdicionalDao {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public List<Division> listarDivisionesPorIdProfesion(Long idProfesion) {
		Query queryRes = null;
		
		Query query = entityManager.createNativeQuery(
				"SELECT	DISTINCT																			"+
						"D.ID_DIVISION AS idDivision,														"+
						"D.DE_DIVISION AS deDivision														"+
					"FROM SICOES_TM_DIVISION	D															"+
						"INNER JOIN SICOES_TX_PERFIL_DIVISION PD ON (D.ID_DIVISION = PD.ID_DIVISION)		"+
						"INNER JOIN SICOES_TM_LISTADO_DETALLE PE ON (PD.ID_PERFIL = PE.ID_LISTADO_DETALLE)	"+
						"INNER JOIN SICOES_TX_PERFIL_PROFESION PP ON (PE.ID_LISTADO_DETALLE = PP.ID_PERFIL)	"+
					"WHERE																					"+
						"PP.ID_PROFESION = " + idProfesion
	        		);
		
		queryRes = query;
		
		List<Object[]> lista = queryRes.getResultList();
		List<Division> divisiones = new ArrayList<>();
		
		for (Object[] dto: lista) {
			Division division= new Division();
			division.setIdDivision(obtenerLong(dto[0]));
			division.setDeDivision(obtenerString(dto[1]));
			divisiones.add(division); 
		}
		
		
		return divisiones;
	}
	
	private String obtenerString(Object o) {
		if (o == null) {
			return null;
		}
		return o + "";
	}
	
	private Long obtenerLong(Object o) {
		if (o == null) {
			return null;
		}
		return Long.valueOf(o.toString());
	}

}
