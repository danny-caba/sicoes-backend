package pe.gob.osinergmin.sicoes.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.repository.AsignacionPerfilDivisionDao;

@Repository
public class AsignacionPerfilDivisionDaoImpl implements AsignacionPerfilDivisionDao {
	
	@PersistenceContext
	protected EntityManager entityManager;

	public List<Long> listarAsignacionesPendientesAprobacionXRetornar(Long idDivision) {
		
		Query queryRes = null;
		
		Query query = entityManager.createNativeQuery(
				"SELECT " +
						"A.ID_ASIGNACION " +
				"FROM " +
					"ES_SICOES.SICOES_TR_ASIGNACION A " +
					"INNER JOIN ES_SICOES.SICOES_TR_SOLICITUD S ON S.ID_SOLICITUD = A.ID_SOLICITUD " +
				"WHERE " +
					"A.FL_ACTIVO = 1 AND " +
					"A.ID_GRUPO_LD = 543 AND " +
					"(A.ID_EVALUACION_LD IS NULL OR A.ID_EVALUACION_LD = 566) AND " +
					"S.ID_DIVISION = " + idDivision
		);

		queryRes = query;

		List<Object[]> lista = queryRes.getResultList();
		List<Long> listaAsignaciones = new ArrayList<Long>();

		for (int i = 0; i < lista.size(); i++) {
			listaAsignaciones.add(obtenerLong(lista.get(i))); //Id asignación
		}
		
		return listaAsignaciones;
	}
	
	private Long obtenerLong(Object o) {
		if (o == null) {
			return null;
		}
		return Long.valueOf(o.toString());
	}
}
