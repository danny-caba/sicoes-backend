package pe.gob.osinergmin.sicoes.repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.PropuestaProfesional;
import pe.gob.osinergmin.sicoes.model.PropuestaTecnica;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Repository
public interface PropuestaProfesionalDao extends JpaRepository<PropuestaProfesional, Long> {
	
	@Query("select p from PropuestaProfesional p "	
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
		+ "where p.idPropuestaProfesional=:idPropuestaProfesional "
		+ "and pp.propuestaUuid = :uuidPropuesta")	
	public PropuestaProfesional obtener(Long idPropuestaProfesional,String uuidPropuesta);
	
	@Query("select p from PropuestaProfesional p "	
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
		+ "where p.idPropuestaProfesional=:idPropuestaProfesional ")	
	public PropuestaProfesional obtener(Long idPropuestaProfesional);
	
	@Query(value="select p from PropuestaProfesional p "
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
			+ "left join fetch pp.procesoItem pi "
			+ "left join fetch pi.proceso pr "
			+ "where (:fechaDesde is null or trunc(p.fechaInvitacion)>=:fechaDesde) "
			+ "and (:fechaHasta is null or trunc(p.fechaInvitacion)<=:fechaHasta) "
			+ "and (:idSector is null or p.sector.idListadoDetalle = :idSector) "
			+ "and (:idSubSector is null or p.subsector.idListadoDetalle = :idSubSector) "
			+ "and (:nroProceso is null or pr.numeroProceso like :nroProceso) "
			+ "and (:nombreProceso is null or pr.nombreProceso like :nombreProceso) "
			+ "and (:idEstadoItem is null or pi.estado.idListadoDetalle = :idEstadoItem) "
			+ "and (:idEstadoInvitacion is null or p.estado.idListadoDetalle = :idEstadoInvitacion) "
			+ "and (:idSupervisora is null or s.idSupervisora = :idSupervisora) "
			+ "and (:empresa is null or pp.supervisora.nombreRazonSocial like :empresa) "
			+ "and (:item is null or pi.descripcionItem like :item) "
			+ "order by p.fechaInvitacion desc ",
			countQuery ="select count(p) from PropuestaProfesional p "
					+ "left join p.propuesta pp "
					+ "left join p.supervisora s "
					+ "left join p.estado e "
					+ "left join p.sector se "
					+ "left join p.subsector su "
					+ "left join p.perfil pe "
					+ "left join pp.procesoItem pi "
					+ "left join pi.proceso pr "
					+ "where (:fechaDesde is null or trunc(p.fechaInvitacion)>=:fechaDesde) "
					+ "and (:fechaHasta is null or trunc(p.fechaInvitacion)<=:fechaHasta) "
					+ "and (:idSector is null or p.sector.idListadoDetalle = :idSector) "
					+ "and (:idSubSector is null or p.subsector.idListadoDetalle = :idSubSector) "
					+ "and (:nroProceso is null or pr.numeroProceso like :nroProceso) "
					+ "and (:nombreProceso is null or pr.nombreProceso like :nombreProceso) "
					+ "and (:idEstadoItem is null or pi.estado.idListadoDetalle = :idEstadoItem) "
					+ "and (:idEstadoInvitacion is null or e.idListadoDetalle = :idEstadoInvitacion) "
					+ "and (:idSupervisora is null or s.idSupervisora = :idSupervisora) "
					+ "and (:empresa is null or pp.supervisora.nombreRazonSocial like :empresa) "
					+ "and (:item is null or pi.descripcionItem like :item) "
					+ "order by p.fechaInvitacion desc ")		
	public Page<PropuestaProfesional> buscar(Date fechaDesde, Date fechaHasta, Long idSector, Long idSubSector, String nroProceso, String nombreProceso, Long idEstadoItem, Long idEstadoInvitacion,Long idSupervisora,String empresa, String item ,Pageable pageable);
	
	
	@Query(value="select p from PropuestaProfesional p "
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
			+ "left join fetch pp.procesoItem pi "
			+ "left join fetch pi.proceso pr "
			+ "where pp.propuestaUuid = :propuestaUuid "
			+ "order by p.fechaInvitacion desc ",
			countQuery ="select count(p) from PropuestaProfesional p "
					+ "left join p.propuesta pp "
					+ "left join p.supervisora s "
					+ "left join p.estado e "
					+ "left join p.sector se "
					+ "left join p.subsector su "
					+ "left join p.perfil pe "
					+ "left join pp.procesoItem pi "
					+ "left join pi.proceso pr "
					+ "where pp.propuestaUuid = :propuestaUuid "
					+ "order by p.fechaInvitacion desc ")		
	public Page<PropuestaProfesional> buscarXPropuesta(String propuestaUuid,Pageable pageable);
	
	@Modifying
	@Query("delete from PropuestaProfesional p where p.propuesta.propuestaUuid = :uuidPropuesta and p.idPropuestaProfesional=:idPropuestaProfesional ")
	public void eliminar(Long idPropuestaProfesional, String uuidPropuesta);

	
	@Query("select p from PropuestaProfesional p "	
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
		+ "where pp.propuestaUuid = :propuestaUuid")	
	public List<PropuestaProfesional> listarXpropuesta(String propuestaUuid);

	
	@Query("select to_char(pp.fechaPresentacion,'dd/MM/yyyy HH:mm'),(pro.numeroProceso|| ' - '|| pro.nombreProceso),(pi.numeroItem|| ' - '|| pi.descripcionItem),pps.tipoPersona.nombre,pps.pais.nombre,(pps.nombreRazonSocial|| ' : '|| pps.codigoRuc),pip.nroProfesionales ,p.sector.nombre,p.subsector.nombre,p.perfil.nombre,s.codigoRuc,(s.nombres|| ' '|| s.apellidoPaterno|| ' '||s.apellidoMaterno) from PropuestaProfesional p,ProcesoItemPerfil pip "	
			+ "left join p.propuesta pp "
			+ "left join pp.supervisora pps "
			+ "left join pp.procesoItem pi "
			+ "left join pi.proceso pro "
			+ "left join p.supervisora s "
			+ "left join p.estado e "
			+ "left join p.sector se "
			+ "left join p.subsector su "
			+ "left join p.perfil pe "
			+ "left join pip.procesoItem pipp "
			+ "left join pp.estado ep "
			+ "where ep.codigo = '"+Constantes.LISTADO.ESTADO_PRESENTACION.PRESENTADO+"' "
			+ "and pi.idProcesoItem = pipp.idProcesoItem "
			+ "and p.perfil = pip.perfil "
			+ "and pi.procesoItemUuid like :procesoItemUuid "
			+ "and e.codigo='"+Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO+ "'")
	public List<Object[]> listarProfesionalesXPerfil(String procesoItemUuid);
	

	
	@Query("select p from PropuestaProfesional p "	
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe ")
	public List<PropuestaProfesional> listar();

	
	@Query(value="select p from PropuestaProfesional p "
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
			+ "left join fetch pp.procesoItem pi "
			+ "left join fetch pi.proceso pr "
			+ "where pi.procesoItemUuid = :procesoItemUuid ",
			countQuery ="select count(p) from PropuestaProfesional p "
					+ "left join p.propuesta pp "
					+ "left join p.supervisora s "
					+ "left join p.estado e "
					+ "left join p.sector se "
					+ "left join p.subsector su "
					+ "left join p.perfil pe "
					+ "left join pp.procesoItem pi "
					+ "left join pi.proceso pr "
					+ "where pi.procesoItemUuid = :procesoItemUuid ")	
	public Page<PropuestaProfesional> buscarXItem(String procesoItemUuid, Pageable pageable);

	@Query("select p from PropuestaProfesional p "	
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
		+ "where pp.propuestaUuid = :propuestaUuid "
		+ "and pe.idListadoDetalle = :idPerfil "
		+ "and e.codigo!='"+Constantes.LISTADO.ESTADO_INVITACION.CANCELADO+ "' "
		+ "and e.codigo!='"+Constantes.LISTADO.ESTADO_INVITACION.RECHAZADO+ "'")	
	public List<PropuestaProfesional> invitacionesXPerfil(Long idPerfil, String propuestaUuid);

	@Query("select p from PropuestaProfesional p "	
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
		+ "where pp.propuestaUuid = :propuestaUuid "
		+ "and e.codigo='"+Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO+ "'")	
	public List<PropuestaProfesional> listarAceptados(String propuestaUuid);

	@Query("select p from PropuestaProfesional p "	
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
			+ "where e.codigo='"+Constantes.LISTADO.ESTADO_INVITACION.INVITADO+ "'"
			+ "and pp.propuestaUuid=:propuestaUuid ")
	public List<PropuestaProfesional> listarInvitados(String propuestaUuid);
    
	
	@Query(value="select p from PropuestaProfesional p "
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
			+ "left join fetch pp.procesoItem pi "
			+ "left join fetch pi.proceso pr "
			+ "where pp.propuestaUuid = :propuestaUuid "
			+ "and e.codigo='"+Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO+ "'",
			countQuery ="select count(p) from PropuestaProfesional p "
					+ "left join p.propuesta pp "
					+ "left join p.supervisora s "
					+ "left join p.estado e "
					+ "left join p.sector se "
					+ "left join p.subsector su "
					+ "left join p.perfil pe "
					+ "left join pp.procesoItem pi "
					+ "left join pi.proceso pr "
					+ "where pp.propuestaUuid = :propuestaUuid "
					+ "and e.codigo='"+Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO+ "'")			
	public Page<PropuestaProfesional> buscarAceptados(String propuestaUuid, Pageable pageable);

	
	
	@Query("select p from PropuestaProfesional p "	
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
			+ "left join fetch pp.procesoItem pi "
			+ "where pi.procesoItemUuid = :procesoItemUuid "
			+ "and e.codigo='"+Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO+ "'")	
	public List<PropuestaProfesional> listarAceptadosXItem(String procesoItemUuid);
	
	
	@Query("select p from PropuestaProfesional p "	
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
			+ "left join fetch pp.procesoItem pi "
			+ "left join fetch pp.ganador g "
			+ "where pi.procesoItemUuid = :procesoItemUuid "
			+ "and e.codigo='"+Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO+ "' "
			+ "and g.codigo='"+Constantes.LISTADO.SI_NO.NO +"' ")	
	public List<PropuestaProfesional> listarNoGanadoresXItem(String procesoItemUuid);
	
	
	@Query(value = "SELECT su.nu_documento, " +
            "su.no_persona || ' ' || su.ap_paterno || ' ' || su.ap_materno, " +
            "ld.no_listado_detalle " +
            "FROM sicoes_tm_supervisora su " +
            "INNER JOIN SICOES_TR_PRO_PROFESIONAL pro ON su.id_supervisora = pro.id_supervisora " +
            "INNER JOIN SICOES_TC_SOLI_PERF_CONT s ON pro.id_propuesta = s.id_propuesta " +
            "INNER JOIN SICOES_TM_LISTADO_DETALLE ld ON pro.id_perfil_ld = ld.id_listado_detalle " +
            "WHERE s.ID_SOLI_PERF_CONT = :idSoliPerfCont " +
            "AND pro.id_estado_ld = 738",
    nativeQuery = true)
	List<Object[]> findPersonalPropuesto(@Param("idSoliPerfCont") Long idSoliPerfCont);

	@Query("select p from PropuestaProfesional p "
			+ "left join fetch p.propuesta pp "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.estado e "
			+ "left join fetch p.sector se "
			+ "left join fetch p.subsector su "
			+ "left join fetch p.perfil pe "
			+ "left join fetch pp.procesoItem pi "
			+ "left join fetch pp.ganador g "
			+ "left join fetch SicoesSolicitud ss on ss.propuesta = pp "
			+ "where ss.idSolicitud = :idSolicitud "
			+ "and s.idSupervisora = :idSupervisora")
	PropuestaProfesional listarXSolicitud(Long idSolicitud,Long idSupervisora);

}
