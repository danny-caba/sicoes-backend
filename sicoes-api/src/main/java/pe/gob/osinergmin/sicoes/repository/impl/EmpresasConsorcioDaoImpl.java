package pe.gob.osinergmin.sicoes.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.PropuestaConsorcio;
import pe.gob.osinergmin.sicoes.model.PropuestaTecnica;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.repository.EmpresasConsorcioDao;

@Repository
public class EmpresasConsorcioDaoImpl implements EmpresasConsorcioDao {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public List<Supervisora> obtenerEmpresasSupervisoraSector(Long idSector) {
		
		List<Supervisora> listaEmpresas = new ArrayList<Supervisora>();
		
		Query queryRes = null;
		
		Query query = entityManager.createNativeQuery(
				"SELECT " +
					"s.ID_SUPERVISORA, s.NO_RAZON_SOCIAL " +
				"FROM " +
					"ES_SICOES.SICOES_TM_SUPERVISORA s " +
					"INNER JOIN ES_SICOES.SICOES_TM_SUPER_DICTAMEN sd ON sd.ID_SUPERVISORA = s.ID_SUPERVISORA " + 
				"WHERE " +
					"s.ID_TIPO_PERSONA_LD IN (655, 678) " + //Persona Jurídica y PN - Postor
					"AND sd.ID_SECTOR_LD = " + idSector
		);
		
		queryRes = query;

		List<Object[]> lista = queryRes.getResultList();
		
		for (Object[] dto : lista) {
			Supervisora supervisora = new Supervisora();
			
			supervisora.setIdSupervisora(obtenerLong(dto[0]));
			supervisora.setNombreRazonSocial(obtenerString(dto[1]));
			
			listaEmpresas.add(supervisora);
		}
		
		return listaEmpresas;
	}
	
	@Override
	public List<PropuestaConsorcio> obtenerEmpresasConsorcio(Long idPropuestaTecnica, Long idSector) {
		
		List<PropuestaConsorcio> listaEmpresasConsorcio = new ArrayList<PropuestaConsorcio>();
		
		Query queryRes = null;
		
		Query query = entityManager.createNativeQuery(
				
		"SELECT "
				+ "pc.ID_PRO_CONSORCIO, pc.ID_PRO_TECNICA, pc.ID_SUPERVISORA, s.NO_RAZON_SOCIAL, s.CO_RUC, sd.MO_FACTURADO, pc.PARTICIPACION "
		+ "FROM "
			+ "ES_SICOES.SICOES_TR_PRO_CONSORCIO pc "
			+ "INNER JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON s.ID_SUPERVISORA = pc.ID_SUPERVISORA "
			+ "INNER JOIN ES_SICOES.SICOES_TM_SUPER_DICTAMEN sd ON sd.ID_SUPERVISORA = pc.ID_SUPERVISORA "
		+ "WHERE "
			+ "pc.ID_PRO_TECNICA = " + idPropuestaTecnica + " AND sd.ID_SECTOR_LD = " + idSector
		);
		
		queryRes = query;

		List<Object[]> lista = queryRes.getResultList();
		
		for (Object[] dto : lista) {
			PropuestaConsorcio propuesta = new PropuestaConsorcio(); 
			propuesta.setIdPropuestaConsorcio(obtenerLong(dto[0]));
			
			//Propuesta tecnica
			PropuestaTecnica proTec = new PropuestaTecnica();
			proTec.setIdPropuestaTecnica(obtenerLong(dto[1]));
			propuesta.setPropuestaTecnica(proTec);
			
			//Supervisora
			Supervisora supervisora = new Supervisora();
			supervisora.setIdSupervisora(obtenerLong(dto[2]));
			supervisora.setNombreRazonSocial(obtenerString(dto[3]));
			supervisora.setCodigoRuc(obtenerString(dto[4]));
			propuesta.setSupervisora(supervisora);
			
			propuesta.setFacturacion(obtenerDouble(dto[5]));
			propuesta.setParticipacion(obtenerDouble(dto[6]));
			
			listaEmpresasConsorcio.add(propuesta);
		}
		
		return listaEmpresasConsorcio;
	}

	private Long obtenerLong(Object o) {
		if (o == null) {
			return null;
		}
		return Long.valueOf(o.toString());
	}
	
	private String obtenerString(Object o) {
		if (o == null) {
			return null;
		}
		return o + "";
	}
	
	private Double obtenerDouble(Object o) {
		if (o == null) {
			return null;
		}
		return Double.valueOf(o.toString());
	}
}
