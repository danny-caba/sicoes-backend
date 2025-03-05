package pe.gob.osinergmin.sicoes.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetallePerfilDTO;
import pe.gob.osinergmin.sicoes.repository.ListadoDetallePerfilDao;

@Repository
public class ListadoDetallePerfilDaoImpl implements ListadoDetallePerfilDao{

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public List<ListadoDetallePerfilDTO> buscarListadoDetallePerfiles(Long idSubSector) {
		
		Query queryRes = null;
		//String querySector ="AND SS.ID_LISTADO_DETALLE="+idSubSector;
		
		if(idSubSector!=null) {
			Query query = entityManager.createNativeQuery(
					"SELECT                                                             "+
		    				"SE.ID_LISTADO_DETALLE AS idSector,                                             "+
		    				"SE.CO_LISTADO_DETALLE AS codigoSector,                                             "+
		    				"SE.NO_LISTADO_DETALLE AS nombreSector,                                             "+
		    				"SS.ID_LISTADO_DETALLE AS idSubSector,                                             "+
		    				"SS.CO_LISTADO_DETALLE AS codigoSubSector,                                             "+
		    				"SS.NO_LISTADO_DETALLE AS nombreSubSector,                                             "+
		    				"AC.ID_LISTADO_DETALLE AS idActividad,                                             "+
		    				"AC.CO_LISTADO_DETALLE AS codigoActividad,                                             "+
		    				"AC.NO_LISTADO_DETALLE AS nombreActividad,                                             "+
		    				"UN.ID_LISTADO_DETALLE AS idUnidad,                                             "+
		    				"UN.CO_LISTADO_DETALLE AS codigoUnidad,                                             "+
		    				"UN.NO_LISTADO_DETALLE AS nombreUnidad,                                             "+
		    				"SC.ID_LISTADO_DETALLE AS idSubCategoria,                                             "+
		    				"SC.CO_LISTADO_DETALLE AS codigoSubCategoria,                                             "+
		    				"SC.NO_LISTADO_DETALLE AS nombreSubCategoria,                                             "+
		    				"PE.ID_LISTADO_DETALLE AS idPerfil,                                             "+
		    				"PE.CO_LISTADO_DETALLE AS codigoPerfil,                                              "+
		    				"PE.NO_LISTADO_DETALLE AS nombrePerfil                                              "+
		    				"FROM                                                               "+
		    				"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=9)SE,   "+
		    				"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=10)SS,  "+
		    				"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=11)AC,  "+
		    				"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=12)UN,  "+
		    				"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=35)SC,  "+
		    				"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=36)PE   "+
		    				"WHERE                                                              "+
		    				"SE.ID_LISTADO_DETALLE=SS.ID_SUPERIOR_LD                            "+
		    				"AND SS.ID_LISTADO_DETALLE=AC.ID_SUPERIOR_LD                        "+
		    				"AND AC.ID_LISTADO_DETALLE=UN.ID_SUPERIOR_LD                        "+
		    				"AND UN.ID_LISTADO_DETALLE=SC.ID_SUPERIOR_LD                        "+
		    				"AND SC.ID_LISTADO_DETALLE=PE.ID_SUPERIOR_LD                       "+
		    				"AND SS.ID_LISTADO_DETALLE="+idSubSector
	        		);
			
			queryRes=query;
		}else {
			Query query = entityManager.createNativeQuery(
					"SELECT                                                             "+
		    				"SE.ID_LISTADO_DETALLE AS idSector,                                             "+
		    				"SE.CO_LISTADO_DETALLE AS codigoSector,                                             "+
		    				"SE.NO_LISTADO_DETALLE AS nombreSector,                                             "+
		    				"SS.ID_LISTADO_DETALLE AS idSubSector,                                             "+
		    				"SS.CO_LISTADO_DETALLE AS codigoSubSector,                                             "+
		    				"SS.NO_LISTADO_DETALLE AS nombreSubSector,                                             "+
		    				"AC.ID_LISTADO_DETALLE AS idActividad,                                             "+
		    				"AC.CO_LISTADO_DETALLE AS codigoActividad,                                             "+
		    				"AC.NO_LISTADO_DETALLE AS nombreActividad,                                             "+
		    				"UN.ID_LISTADO_DETALLE AS idUnidad,                                             "+
		    				"UN.CO_LISTADO_DETALLE AS codigoUnidad,                                             "+
		    				"UN.NO_LISTADO_DETALLE AS nombreUnidad,                                             "+
		    				"SC.ID_LISTADO_DETALLE AS idSubCategoria,                                             "+
		    				"SC.CO_LISTADO_DETALLE AS codigoSubCategoria,                                             "+
		    				"SC.NO_LISTADO_DETALLE AS nombreSubCategoria,                                             "+
		    				"PE.ID_LISTADO_DETALLE AS idPerfil,                                             "+
		    				"PE.CO_LISTADO_DETALLE AS codigoPerfil,                                              "+
		    				"PE.NO_LISTADO_DETALLE AS nombrePerfil                                              "+
		    				"FROM                                                               "+
		    				"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=9)SE,   "+
		    				"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=10)SS,  "+
		    				"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=11)AC,  "+
		    				"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=12)UN,  "+
		    				"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=35)SC,  "+
		    				"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=36)PE   "+
		    				"WHERE                                                              "+
		    				"SE.ID_LISTADO_DETALLE=SS.ID_SUPERIOR_LD                            "+
		    				"AND SS.ID_LISTADO_DETALLE=AC.ID_SUPERIOR_LD                        "+
		    				"AND AC.ID_LISTADO_DETALLE=UN.ID_SUPERIOR_LD                        "+
		    				"AND UN.ID_LISTADO_DETALLE=SC.ID_SUPERIOR_LD                        "+
		    				"AND SC.ID_LISTADO_DETALLE=PE.ID_SUPERIOR_LD                       "
		    				
	        		);
			
			queryRes=query;
		}
		
		
		
        
        List<Object[]> lista = queryRes.getResultList();
        
        List<ListadoDetallePerfilDTO> perfiles = new ArrayList<>();
        
        for (Object[] dto: lista) {
        	ListadoDetallePerfilDTO obj = new ListadoDetallePerfilDTO();
        	
        	ListadoDetalleDTO sector = new ListadoDetalleDTO();
        	sector.setIdListadoDetalle(obtenerLong(dto[0]));
        	sector.setCodigo(obtenerString(dto[1]));
        	sector.setNombre(obtenerString(dto[2]));
        	
        	ListadoDetalleDTO subSector = new ListadoDetalleDTO();
        	subSector.setIdListadoDetalle(obtenerLong(dto[3]));
        	subSector.setCodigo(obtenerString(dto[4]));
        	subSector.setNombre(obtenerString(dto[5]));
        	
        	ListadoDetalleDTO actividad = new ListadoDetalleDTO();
        	actividad.setIdListadoDetalle(obtenerLong(dto[6]));
        	actividad.setCodigo(obtenerString(dto[7]));
        	actividad.setNombre(obtenerString(dto[8]));
        	
        	ListadoDetalleDTO unidad = new ListadoDetalleDTO();
        	unidad.setIdListadoDetalle(obtenerLong(dto[9]));
        	unidad.setCodigo(obtenerString(dto[10]));
        	unidad.setNombre(obtenerString(dto[11]));
        		
        	ListadoDetalleDTO subCategoria = new ListadoDetalleDTO();
        	subCategoria.setIdListadoDetalle(obtenerLong(dto[12]));
        	subCategoria.setCodigo(obtenerString(dto[13]));
        	subCategoria.setNombre(obtenerString(dto[14]));
        	
        	ListadoDetalleDTO perfil = new ListadoDetalleDTO();
        	perfil.setIdListadoDetalle(obtenerLong(dto[15]));
        	perfil.setCodigo(obtenerString(dto[16]));
        	perfil.setNombre(obtenerString(dto[17]));
        	
        	
        	obj.setSector(sector);
        	obj.setSubsector(subSector);
        	obj.setActividad(actividad);
        	obj.setUnidad(unidad);
        	obj.setSubCategoria(subCategoria);
        	obj.setPerfil(perfil);
        	
        	perfiles.add(obj); 
        }
        
		return perfiles;
	}
	
	private String obtenerString(Object o) {
		if(o == null) {
			return null;
		}
		return o + "";
	}
	
	private Long obtenerLong(Object o) {
		if(o == null) {
			return null;
		}
		return Long.valueOf(o.toString());
	}
	
	@Override
	public List<ListadoDetallePerfilDTO> listarPerfilesDistintosPorIdProfesionIdDivision(Long idProfesion, Long idDivision, Long idUsuario) {
		Query queryRes = null;
		
		Query query = entityManager.createNativeQuery(
				"SELECT	DISTINCT															"+
						"SE.ID_LISTADO_DETALLE AS idSector,									"+
						"SE.CO_LISTADO_DETALLE AS codigoSector,								"+
						"SE.NO_LISTADO_DETALLE AS nombreSector,								"+
						"SS.ID_LISTADO_DETALLE AS idSubSector,								"+
						"SS.CO_LISTADO_DETALLE AS codigoSubSector,							"+
						"SS.NO_LISTADO_DETALLE AS nombreSubSector,							"+
						"AC.ID_LISTADO_DETALLE AS idActividad,								"+
						"AC.CO_LISTADO_DETALLE AS codigoActividad,							"+
						"AC.NO_LISTADO_DETALLE AS nombreActividad,							"+
						"UN.ID_LISTADO_DETALLE AS idUnidad,									"+
						"UN.CO_LISTADO_DETALLE AS codigoUnidad,								"+
						"UN.NO_LISTADO_DETALLE AS nombreUnidad,								"+
						"SC.ID_LISTADO_DETALLE AS idSubCategoria,							"+
						"SC.CO_LISTADO_DETALLE AS codigoSubCategoria,						"+
						"SC.NO_LISTADO_DETALLE AS nombreSubCategoria,						"+
						"PE.ID_LISTADO_DETALLE AS idPerfil,									"+
						"PE.CO_LISTADO_DETALLE AS codigoPerfil,								"+
						"PE.NO_LISTADO_DETALLE AS nombrePerfil								"+
					"FROM																	"+
						"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=9) SE,	"+
						"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=10) SS,	"+
						"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=11) AC,	"+
						"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=12) UN,	"+
						"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=35) SC,	"+
						"(SELECT * FROM SICOES_TM_LISTADO_DETALLE  WHERE ID_LISTADO=36) PE,	"+
						"SICOES_TX_PERFIL_PROFESION PR,										"+
						"SICOES_TX_PERFIL_DIVISION DI										"+
					"WHERE																	"+
						"SE.ID_LISTADO_DETALLE = SS.ID_SUPERIOR_LD							"+
						"AND SS.ID_LISTADO_DETALLE = AC.ID_SUPERIOR_LD						"+
						"AND AC.ID_LISTADO_DETALLE = UN.ID_SUPERIOR_LD						"+
						"AND UN.ID_LISTADO_DETALLE = SC.ID_SUPERIOR_LD						"+
						"AND SC.ID_LISTADO_DETALLE = PE.ID_SUPERIOR_LD						"+
						"AND PR.ID_PERFIL = PE.ID_LISTADO_DETALLE							"+
						"AND DI.ID_PERFIL = PE.ID_LISTADO_DETALLE							"+
						"AND PR.ID_PROFESION = " + idProfesion							+ "	"+
						"AND DI.ID_DIVISION = " + idDivision							+ " "+
						"AND NOT EXISTS (													"+
						"		SELECT 1													"+
						"		FROM														"+
						"			SICOES_TR_SOLICITUD SO,									"+
						"			SICOES_TR_OTRO_REQUISITO RO								"+
						"		WHERE														"+
						"			SO.ID_USUARIO = " + idUsuario						+ " "+
						"			AND SO.ID_ESTADO_LD = 72								"+
						//"			AND (SO.ID_ESTADO_LD = 1 OR SO.ID_ESTADO_LD = 2 OR SO.ID_ESTADO_LD = 3)	"+
						"           AND (RO.ID_EVALUACION_LD=141 AND SO.ID_RESUL_ADMIN=141)"+
						"			AND RO.ID_SOLICITUD = SO.ID_SOLICITUD					"+
						"			AND RO.ID_PERFIL_LD IS NOT NULL							"+
						"			AND PE.ID_LISTADO_DETALLE = RO.ID_PERFIL_LD				"+
						"		)"+
						"AND NOT EXISTS (													"+
						"		SELECT 1													"+
						"		FROM														"+
						"			SICOES_TR_SOLICITUD SO,									"+
						"			SICOES_TR_OTRO_REQUISITO RO								"+
						"		WHERE														"+
						"			SO.ID_USUARIO = " + idUsuario						+ " "+
						"			AND SO.ID_ESTADO_LD NOT IN (72, 657, 71)					"+
						"			AND RO.ID_SOLICITUD = SO.ID_SOLICITUD					"+
						"			AND RO.ID_PERFIL_LD IS NOT NULL							"+
						"			AND PE.ID_LISTADO_DETALLE = RO.ID_PERFIL_LD				"+
						"		)"
	        		);
		
		queryRes = query;
		
		List<Object[]> lista = queryRes.getResultList();
		List<ListadoDetallePerfilDTO> perfiles = new ArrayList<>();
		
		for (Object[] dto: lista) {
			ListadoDetallePerfilDTO obj = new ListadoDetallePerfilDTO();
			
			ListadoDetalleDTO sector = new ListadoDetalleDTO();
			sector.setIdListadoDetalle(obtenerLong(dto[0]));
			sector.setCodigo(obtenerString(dto[1]));
			sector.setNombre(obtenerString(dto[2]));
			
			ListadoDetalleDTO subSector = new ListadoDetalleDTO();
			subSector.setIdListadoDetalle(obtenerLong(dto[3]));
			subSector.setCodigo(obtenerString(dto[4]));
			subSector.setNombre(obtenerString(dto[5]));
			
			ListadoDetalleDTO actividad = new ListadoDetalleDTO();
			actividad.setIdListadoDetalle(obtenerLong(dto[6]));
			actividad.setCodigo(obtenerString(dto[7]));
			actividad.setNombre(obtenerString(dto[8]));
			
			ListadoDetalleDTO unidad = new ListadoDetalleDTO();
			unidad.setIdListadoDetalle(obtenerLong(dto[9]));
			unidad.setCodigo(obtenerString(dto[10]));
			unidad.setNombre(obtenerString(dto[11]));
			
			ListadoDetalleDTO subCategoria = new ListadoDetalleDTO();
			subCategoria.setIdListadoDetalle(obtenerLong(dto[12]));
			subCategoria.setCodigo(obtenerString(dto[13]));
			subCategoria.setNombre(obtenerString(dto[14]));
			
			ListadoDetalleDTO perfil = new ListadoDetalleDTO();
			perfil.setIdListadoDetalle(obtenerLong(dto[15]));
			perfil.setCodigo(obtenerString(dto[16]));
			perfil.setNombre(obtenerString(dto[17]));
			
			obj.setSector(sector);
			obj.setSubsector(subSector);
			obj.setActividad(actividad);
			obj.setUnidad(unidad);
			obj.setSubCategoria(subCategoria);
			obj.setPerfil(perfil);
			
			perfiles.add(obj); 
		}
		
		
		return perfiles;
	}

}
