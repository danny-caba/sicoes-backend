package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Estudio;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.model.ProcesoItemPerfil;
import pe.gob.osinergmin.sicoes.model.SupervisoraPerfil;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface ProcesoItemPerfilDao extends JpaRepository<ProcesoItemPerfil, Long> {
	
	
	
	@Query(value="select i from ProcesoItemPerfil i "
			+ "left join fetch i.procesoItem pi "
			+ "left join fetch i.sector s "
			+ "left join fetch i.subsector ss "
			+ "left join fetch i.perfil p "
			+ "where pi.procesoItemUuid=:procesoItemUuid",
			countQuery ="select count(s) from ProcesoItemPerfil i "
					+ "left join i.procesoItem pi "
					+ "left join i.sector s "
					+ "left join i.subsector ss "
					+ "left join i.perfil p "
					+ "where pi.procesoItemUuid=:procesoItemUuid")		
	public Page<ProcesoItemPerfil> buscar(String procesoItemUuid,Pageable pageable);
	
	@Query("select i from ProcesoItemPerfil i "	
			+ "left join fetch i.procesoItem pi "
			+ "left join fetch i.sector s "
			+ "left join fetch i.subsector ss "
			+ "left join fetch i.perfil p "
			+ "where pi.procesoItemUuid=:procesoItemUuid "
			+ "and i.idProcesoItemPerfil=:idProcesoItemPerfil ")	
	public ProcesoItemPerfil obtener(Long idProcesoItemPerfil ,String procesoItemUuid);
	
	@Query(value="select i from ProcesoItemPerfil i "
			+ "left join fetch i.procesoItem pi "
			+ "left join fetch i.sector s "
			+ "left join fetch i.subsector ss "
			+ "left join fetch i.perfil p "
			+ "where pi.procesoItemUuid=:procesoItemUuid")
	public List<ProcesoItemPerfil> listar(String procesoItemUuid);
	
	@Query(value="select i from ProcesoItemPerfil i "
			+ "left join fetch i.procesoItem pi "
			+ "left join fetch i.sector s "
			+ "left join fetch i.subsector ss "
			+ "left join fetch i.perfil p ")
	public List<ProcesoItemPerfil> listar();
	
	@Query("select i from ProcesoItemPerfil i "	
			+ "left join fetch i.procesoItem pi "
			+ "left join fetch i.sector s "
			+ "left join fetch i.subsector ss "
			+ "left join fetch i.perfil p "
			+ "where i.idProcesoItemPerfil=:idProcesoItemPerfil ")	
	public ProcesoItemPerfil obtener(Long idProcesoItemPerfil);

	
	//@Query("select (pi.numeroItem|| ' - '|| pi.descripcionItem),(pi.numeroItem|| ' - '|| pi.descripcionItem),(pi.numeroItem|| ' - '|| pi.descripcionItem),(s.nombres|| ': '|| s.codigoRuc),(pi.numeroItem|| ' - '|| pi.descripcionItem),(pi.numeroItem|| ' - '|| pi.descripcionItem),(pi.numeroItem|| ' - '|| pi.descripcionItem),(pi.numeroItem|| ' - '|| pi.descripcionItem),(pi.numeroItem|| ' - '|| pi.descripcionItem) from ProcesoItemPerfil i"	
//case when i.nroProfesionales>count(pp) then 'Incompleto' else 'Completo'	
	@Query(
			value= "SELECT A.PROCESO,A.ITEM,A.PERSONA,A.PAIS,A.RAZON,A.PERFIL,A.REQUERIDOS,A.PRESENTADOS, "
			+ "CASE WHEN A.REQUERIDOS > A.PRESENTADOS THEN 'Incompleto' ELSE 'Completo' END AS RESULTADO "
			+ "FROM  ( "          
				+ "SELECT " 
			    + "P.NU_PROCESO||' '||P.NO_PROCESO PROCESO, "
			    + "PI.NU_ITEM||' '||PI.DE_ITEM ITEM, "
			    + "TP.NO_LISTADO_DETALLE PERSONA, "
			    + "PA.NO_LISTADO_DETALLE PAIS, "
			    + "S.NO_RAZON_SOCIAL RAZON, "
			    + "PEF.NO_LISTADO_DETALLE PERFIL, "
			    + "PIP.NRO_PROFESIONALES REQUERIDOS, "
			    + " COUNT(PRP.ID_PRO_PROFESIONAL) PRESENTADOS "
			    + "FROM SICOES_TR_PROCESO_ITEM PI "
			    + "LEFT JOIN SICOES_TR_PROCESO P ON (PI.ID_PROCESO=P.ID_PROCESO) " 
			    + "LEFT JOIN SICOES_TR_PROCESO_ITEM_PERFIL PIP ON (PI.ID_PROCESO_ITEM=PIP.ID_PROCESO_ITEM) "    
			    + "LEFT JOIN SICOES_TR_PROPUESTA PR ON (PI.ID_PROCESO_ITEM= PR.ID_PROCESO_ITEM) "
			    + "LEFT JOIN SICOES_TR_PRO_PROFESIONAL PRP ON (PR.ID_PROPUESTA= PRP.ID_PROPUESTA) "
			    + "LEFT JOIN SICOES_TM_SUPERVISORA S ON (PR.ID_SUPERVISORA=S.ID_SUPERVISORA) " 
			    + "LEFT JOIN SICOES_TM_LISTADO_DETALLE TP ON (TP.ID_LISTADO_DETALLE=S.ID_TIPO_PERSONA_LD) "
			    + "LEFT JOIN SICOES_TM_LISTADO_DETALLE PA ON (PA.ID_LISTADO_DETALLE=S.ID_PAIS_LD) "
			    + "LEFT JOIN SICOES_TM_LISTADO_DETALLE PEF ON (PEF.ID_LISTADO_DETALLE=PIP.ID_PERFIL_LD) "
			    + "LEFT JOIN SICOES_TM_LISTADO_DETALLE EP ON (EP.ID_LISTADO_DETALLE=PRP.ID_ESTADO_LD) "
			    + "LEFT JOIN SICOES_TM_LISTADO_DETALLE EPR ON (EPR.ID_LISTADO_DETALLE=PR.ID_ESTADO_LD) "
			    + "WHERE PI.CO_UUID =:procesoItemUuid "
			    + "AND EPR.CO_LISTADO_DETALLE ='"+Constantes.LISTADO.ESTADO_PRESENTACION.PRESENTADO +"' "
			    + "AND EP.CO_LISTADO_DETALLE = '"+Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO+"' "
			    + "GROUP BY " 
			    + "P.NU_PROCESO||' '||P.NO_PROCESO, "
			    + "PI.NU_ITEM||' '||PI.DE_ITEM, "
			    + "TP.NO_LISTADO_DETALLE, "
			    + "PA.NO_LISTADO_DETALLE, "
			    + "S.NO_RAZON_SOCIAL, "
			    + "PEF.NO_LISTADO_DETALLE, "
			    + "PIP.NRO_PROFESIONALES) A ", 
			    nativeQuery = true)
	public List<Object[]> listarProfesionalesXPerfil(String procesoItemUuid);
	
	@Modifying
	@Query("delete from ProcesoItemPerfil i where i.procesoItem.procesoItemUuid=:procesoItemUuid and i.idProcesoItemPerfil=:idProcesoItemPerfil ")
	public void eliminar(Long idProcesoItemPerfil,String procesoItemUuid);

	
	
	@Query("select sp from SupervisoraPerfil sp "
			+ "left join fetch sp.sector se "
			+ "left join fetch sp.actividad a "
			+ "left join fetch sp.subsector ss "
			+ "left join fetch sp.subCategoria sc "
			+ "left join fetch sp.unidad u "
			+ "left join fetch sp.perfil pe "
			+ "left join fetch sp.supervisora s "
			+ "left join fetch s.tipoDocumento td "
			+ "left join fetch s.pais p "
			+ "left join fetch s.estado spe "
			+ "where s.idSupervisora=:idSupervisora "
			+ "and se.idListadoDetalle=:idSector "
			+ "and ss.idListadoDetalle=:idSubsector "
			+ "and spe.codigo='"+Constantes.LISTADO.ESTADO_SUPERVISORA.VIGENTE+"'")
	public List<SupervisoraPerfil> buscar(Long idSupervisora, Long idSector,Long idSubsector) ;
	
	
	@Query(value="select i from ProcesoItemPerfil i "
			+ "left join fetch i.procesoItem pi "
			+ "left join fetch i.sector s "
			+ "left join fetch i.subsector ss "
			+ "left join fetch i.perfil p "
			+ "left join fetch pi.proceso pip "
			+ "where pip.idProceso=:idProceso ")
	public List<ProcesoItemPerfil> buscarPerfiles(Long idProceso);


	
	
	

}
