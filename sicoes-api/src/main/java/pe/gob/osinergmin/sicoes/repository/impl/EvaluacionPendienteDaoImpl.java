package pe.gob.osinergmin.sicoes.repository.impl;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.dto.EvaluacionPendienteDTO;
import pe.gob.osinergmin.sicoes.repository.EvaluacionPendienteDao;

@Repository
public class EvaluacionPendienteDaoImpl implements EvaluacionPendienteDao {
	
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public EvaluacionPendienteDTO listarEvaluacionesPendientes(Long IdOtroRequisito) {
		
		Query queryRes = null;
		
		Query query = entityManager.createNativeQuery(
				"SELECT " +
						"S.ID_SOLICITUD,  A.FE_REGISTRO, LD.DE_LISTADO_DETALLE, O.FE_ASIGNACION, S.ID_DIVISION, U.ID_USUARIO, U.NO_USUARIO, U.DE_CORREO, A.ID_TIPO_LD " +
				"FROM " +
					"ES_SICOES.SICOES_TR_OTRO_REQUISITO O " +
					"INNER JOIN ES_SICOES.SICOES_TR_ASIGNACION A  ON O.ID_OTRO_REQUISITO = A.ID_OTRO_REQUISITO " +
					"INNER JOIN ES_SICOES.SICOES_TM_USUARIO U ON U.ID_USUARIO = A.ID_USUARIO " +
					"INNER JOIN ES_SICOES.SICOES_TM_LISTADO_DETALLE LD ON LD.ID_LISTADO_DETALLE = O.ID_PERFIL_LD " +
					"INNER JOIN ES_SICOES.SICOES_TR_SOLICITUD S ON S.ID_SOLICITUD = O.ID_SOLICITUD " +
				"WHERE " +
					"A.ID_OTRO_REQUISITO = " + IdOtroRequisito
		);
		
		queryRes = query;
		
		List<Object[]> lista = queryRes.getResultList();
		EvaluacionPendienteDTO evaluacion = new EvaluacionPendienteDTO();
		
		evaluacion.setIdSolicitud(obtenerLong(lista.get(0)[0])); //Id solicitud
		
		try {
			evaluacion.setFechaIngreso(Date.valueOf(obtenerString(lista.get(0)[1]).substring(0, 10))); //Fecha ingreso
		}
		catch (Exception e) {
			evaluacion.setFechaIngreso(null);
		}

		evaluacion.setPerfil(obtenerString(lista.get(0)[2])); //Perfil
		
		try {
			evaluacion.setFechaAsignacion(Date.valueOf(obtenerString(lista.get(0)[3]).substring(0, 10))); //Fecha asignación
		}
		catch (Exception e) {
			evaluacion.setFechaAsignacion(null);
		}
		
		evaluacion.setIdDivision(obtenerLong(lista.get(0)[4])); //Id división
		
		evaluacion.setIdUsuario(obtenerLong(lista.get(0)[5])); //Id usuario
		
		evaluacion.setNombreUsuario(obtenerString(lista.get(0)[6])); //Nombre del usuario
		
		evaluacion.setCorreoUsuario(obtenerString(lista.get(0)[7])); //Correo del usuario
		
		evaluacion.setIdTipo(obtenerLong(lista.get(0)[8])); //Tipo de evaluación
		
		
		//Número de expediente
		if (evaluacion.getIdSolicitud() != null) {
			query = entityManager.createNativeQuery("SELECT NU_EXPEDIENTE FROM ES_SICOES.SICOES_TR_SOLICITUD WHERE ID_SOLICITUD = " + evaluacion.getIdSolicitud());
			queryRes = query;
			
			List<Object[]> lista1 = queryRes.getResultList();
			
			evaluacion.setNumeroExpediente(obtenerString(lista1.get(0)));
		}
		
		//División / Gerencia
		if (evaluacion.getIdDivision() != null) {
			query = entityManager.createNativeQuery("SELECT DE_DIVISION FROM ES_SICOES.SICOES_TM_DIVISION WHERE ID_DIVISION = " + evaluacion.getIdDivision());
			queryRes = query;
			
			List<Object[]> lista2 = queryRes.getResultList();
			
			evaluacion.setDivisionGerencia(obtenerString(lista2.get(0)));
		}
		else {
			evaluacion.setDivisionGerencia("");
		}
		
		//Tipo
		if (evaluacion.getIdTipo() != null) {
			query = entityManager.createNativeQuery("SELECT DE_LISTADO_DETALLE FROM ES_SICOES.SICOES_TM_LISTADO_DETALLE WHERE ID_LISTADO_DETALLE = " + evaluacion.getIdTipo());
			queryRes = query;
			
			List<Object[]> lista3 = queryRes.getResultList();
			
			evaluacion.setTipo(obtenerString(lista3.get(0)));
		}
		
		return evaluacion;
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