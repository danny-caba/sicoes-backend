package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.dto.HistorialContratoDto;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface AsignacionDao extends JpaRepository<Asignacion, Long> {
	
	@Query("select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.grupo g "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.evaluacion e  "
			+ "where a.idAsignacion=:idAsignacion  and a.flagActivo=1 ")
	public Asignacion obtener(Long idAsignacion);

	@Query("select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.grupo g "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.evaluacion e  "
			+ "where s.idSolicitud=:idSolicitud and u.idUsuario=:idUsuario")
	public List<Asignacion> obtener(Long idSolicitud, Long idUsuario);

	@Query("select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.grupo g "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.evaluacion e  "
			+ "where s.idSolicitud=:idSolicitud and u.idUsuario=:idUsuario and td.codigo='"
			+ Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO + "'")
	public List<Asignacion> obtenerAsignacionTecnico(Long idSolicitud, Long idUsuario);

	@Query(value = "select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.grupo g "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.evaluacion e  "
			+ "left join fetch a.otroRequisito r "
			+ "where s.idSolicitud=:idSolicitud and a.flagActivo=1 "
			+ "and (:codigoTipoAprobador is null or td.codigo=:codigoTipoAprobador ) "
			+ "order by td.orden asc, g.orden  asc ", countQuery = "select count(a) from Asignacion a "
					+ "left join  a.solicitud s "
					+ "left join  a.tipo td "
					+ "left join  a.grupo g "
					+ "left join  a.usuario u "
					+ "where s.idSolicitud=:idSolicitud and a.flagActivo=1 "
					+ "and (:codigoTipoAprobador is null or td.codigo=:codigoTipoAprobador )")
	Page<Asignacion> buscar(Long idSolicitud, String codigoTipoAprobador, Pageable pageable);

	@Query(value = "select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.grupo g "
			+ "left join fetch a.evaluacion e  "
			+ "where s.idSolicitud=:idSolicitud and a.flagActivo=1 ")
	public List<Asignacion> obtenerAsignaciones(Long idSolicitud);

	@Query(value = "select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.grupo g  "
			+ "left join fetch a.evaluacion e  "
			+ "where s.idSolicitud=:idSolicitud and a.flagActivo=1 and td.idListadoDetalle = :idTipo order by g.orden asc")
	public List<Asignacion> obtenerAsignaciones(Long idSolicitud, Long idTipo);

	@Query(value = "select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.grupo g  "
			+ "left join fetch a.evaluacion e  "
			+ "where s.idSolicitud=:idSolicitud and a.flagActivo=1 and td.idListadoDetalle = :idTipo and e.idListadoDetalle = :idEstado order by g.orden asc")
	public List<Asignacion> obtenerAsignacionesxEstado(Long idSolicitud, Long idTipo, Long idEstado);

	@Query(value = "select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.grupo g "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.evaluacion ae "
			+ "where s.solicitudUuid=:solicitudUuid "
			+ "and a.flagActivo=1"
			+ "and ( td.codigo='" + Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO + "' or td.codigo='"
			+ Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO + "') "
			+ "order by td.orden asc, a.fechaRegistro desc ", countQuery = "select count(a) from Asignacion a "
					+ "left join  a.solicitud s "
					+ "left join  a.tipo td "
					+ "left join  a.grupo g "
					+ "left join  a.usuario u "
					+ "left join a.evaluacion ae "
					+ "where s.solicitudUuid=:solicitudUuid "
					+ "and a.flagActivo=1"
					+ "and ( td.codigo='" + Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO + "' or td.codigo='"
					+ Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO + "')")
	public Page<Asignacion> buscar(String solicitudUuid, Pageable pageable);

	@Modifying
	@Query(value = "delete from Asignacion a  where a.solicitud.idSolicitud=:idSolicitud")
	public void eliminarXIdSolicitud(Long idSolicitud);

	// @Query(value="select a from Asignacion a "
	// + "left join fetch a.solicitud s "
	// + "left join fetch a.tipo td "
	// + "left join fetch a.usuario u "
	// + "left join fetch a.grupo g "
	// + "left join fetch a.evaluacion e "
	// + "where s.idSolicitud=:idSolicitud and a.flagActivo=1 and td.codigo =
	// :codigoAprobadorTecnico order by g.orden asc")
	// public List<Asignacion> listarAprobadoresTecnicos(Long idSolicitud,Long
	// idAprobador, String codigoAprobadorTecnico);

	@Query(value = "select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.grupo g  "
			+ "left join fetch a.evaluacion e  "
			+ "where s.idSolicitud=:idSolicitud and a.flagActivo=1 "
			+ "and td.codigo = '" + Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO + "' "
			+ "and g.codigo = '" + Constantes.LISTADO.GRUPOS.G1 + "' "
			+ "and a.usuario in "
			+ "(select u from Usuario u "
			+ "where u in "
			+ "(select cb.usuario from ConfiguracionBandeja cb "
			+ "where cb.perfil in "
			+ "(select o.perfil from OtroRequisito o "
			+ "where o.perfil in "
			+ "(select c.perfil from ConfiguracionBandeja c "
			+ "left join c.usuario u "
			+ "where u.idUsuario =:idAprobador ))))")
	public List<Asignacion> listarAprobadoresTecnicos(Long idSolicitud, Long idAprobador);

	@Query(value = "select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.grupo g  "
			+ "left join fetch a.evaluacion e  "
			+ "where s.idSolicitud=:idSolicitud and a.flagActivo=1 "
			+ "and td.codigo = '" + Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO + "' "
			+ "and g.codigo = '" + Constantes.LISTADO.GRUPOS.G1 + "' "
			+ "and a.usuario in "
			+ "(select u from Usuario u "
			+ "where u in "
			+ "(select cb.usuario from ConfiguracionBandeja cb "
			+ "where cb.perfil is null "
			+ "and cb.subsector in "
			+ "(select o.subsector from OtroRequisito o "
			+ "where o.subsector in "
			+ "(select c.subsector from ConfiguracionBandeja c "
			+ "left join c.usuario u "
			+ "where c.perfil is null "
			+ "and u.idUsuario =:idAprobador ))))")
	public List<Asignacion> listarAprobadoresTecnicosPJ(Long idSolicitud, Long idAprobador);

	@Query(value = "select max(g.orden) from Asignacion a "
			+ "left join a.solicitud s "
			+ "left join a.tipo td "
			+ "left join a.usuario u "
			+ "left join a.grupo g  "
			+ "left join a.evaluacion e  "
			+ "where s.idSolicitud=:idSolicitud and a.flagActivo=1 and td.idListadoDetalle = :idTipo order by g.orden asc")
	public Long obtenerMaximoGrupo(Long idSolicitud, Long idTipo);

	@Query("select a from Asignacion a "	
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.grupo g "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.evaluacion e "
			+ "left join fetch a.otroRequisito r "
			+ "where r.idOtroRequisito = :idOtroRequisito and a.flagActivo = 1 ")
	public Asignacion obtenerAsignacionPorIdOtroRequisito(Long idOtroRequisito);
	
	@Query(value="select a from Asignacion a "	
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.grupo g  "
			+ "left join fetch a.evaluacion e  "
			+ "where a.flagActivo = 1 "
			+ "and td.codigo = '" + Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO + "' "
			+ "and a.fechaAprobacion is null "
			+ "and (g.codigo = '" + Constantes.LISTADO.GRUPOS.G1 + "' or g.codigo = '" + Constantes.LISTADO.GRUPOS.G2 + "')"
			)
	public List<Asignacion> listarAsiganacionesPendintesAproobacion();
	
	/* --- 04-01-2024 --- INI ---\*/
	@Query(value="select a from Asignacion a "	
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.grupo g  "
			+ "left join fetch a.evaluacion e  "
			+ "where s.idSolicitud=:idSolicitud "
			+ "and a.flagActivo = 1 "
			+ "and td.codigo = '" + Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO + "' "
			+ "and g.codigo = '"+Constantes.LISTADO.GRUPOS.G1+"' "
			)
	public List<Asignacion> listarAsiganacionesPendientesG1(Long idSolicitud);
	/* --- 04-01-2024 --- FIN ---\*/

	//AFC
		@Query(value="select a from Asignacion a "	
				+ "left join fetch a.solicitud s "
				+ "left join fetch a.tipo td "
				+ "left join fetch a.usuario u "
				+ "left join fetch a.grupo g  "
				+ "left join fetch a.evaluacion e  "
				+ "where s.idSolicitud=:idSolicitud and a.flagActivo=1 "
			+ "and td.codigo = '"+Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO+"' "
				//+ "and g.codigo = '"+Constantes.LISTADO.GRUPOS.G1+"' "
				+ "and a.usuario in "
				+ "(select u from Usuario u "
				+ "where u in "
				+ "(select cb.usuario from ConfiguracionBandeja cb "
				+ "where cb.perfil in "
				+"(select o.perfil from OtroRequisito o "	
				+ "where o.perfil in "
				+ "(select c.perfil from ConfiguracionBandeja c "
				+ "left join c.usuario u "
				+ "where u.idUsuario =:idUsuario ))))")
		public List<Asignacion> listarAprobadoresAdminstrativos(Long idSolicitud, Long idUsuario);
		//AFC

	@Modifying
	@Transactional
	@Query(value="update Asignacion set usuario.idUsuario = :idUsuario, idUsuarioOrigen = :idUsuarioOrigen where idAsignacion=:iAsignacion")
	public void actualizarUsuario(Long iAsignacion, Long idUsuario, Long idUsuarioOrigen);

	@Query(value="select a from Asignacion a "	
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.grupo g  "
			+ "left join fetch a.evaluacion e  "
			+ "where s.idSolicitud=:idSolicitud and a.flagActivo=1 "
			+ "and td.codigo = '"+Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO+"' "
			//+ "and g.codigo = '"+Constantes.LISTADO.GRUPOS.G1+"' "
			+ "and a.usuario in "
			+ "(select u from Usuario u "
			+ "where u in "
			+ "(select cb.usuario from ConfiguracionBandeja cb "
			+ "where cb.perfil is null "
			+ "and cb.subsector in "
			+"(select o.subsector from OtroRequisito o "	
			+ "where o.subsector in "
			+ "(select c.subsector from ConfiguracionBandeja c "
			+ "left join c.usuario u "
			+ "where c.perfil is null "
			+ "and u.idUsuario =:idUsuario ))))")
	public List<Asignacion> listarAprobadoresAdministrativosPJ(Long idSolicitud, Long idUsuario);
	
	@Query(value = "select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.grupo g  "
			+ "left join fetch a.evaluacion e  "
			+ "where u.idUsuario =:idAprobador "
			+ "and a.flagActivo = 1 "
			+ "and (e.codigo = '" + Constantes.LISTADO.RESULTADO_EVALUACION_TEC_ADM.ASIGNADO + "' or e.codigo is null)"
			+ "and g.codigo = '" + Constantes.LISTADO.GRUPOS.G2 + "' ")
	public List<Asignacion> listarAsignacionesPendintesAprobacion(Long idAprobador);
	
	@Query(value = "select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.grupo g  "
			+ "left join fetch a.evaluacion e  "
			+ "where a.flagActivo = 1 "
			+ "and s.idSolicitud = :idSolicitud "
			+ "and (e.codigo = '" + Constantes.LISTADO.RESULTADO_EVALUACION_TEC_ADM.ASIGNADO + "'"
			+ "or e.codigo is null) "
			+ "and td.idListadoDetalle = :idTipo "
			+ "and g.idListadoDetalle = :idGrupo ")
	List<Asignacion> obtenerAsignados(Long idSolicitud, Long idTipo, Long idGrupo);

	@Query("select a from Asignacion a "
			+ "left join fetch a.solicitud s "
			+ "left join fetch a.tipo td "
			+ "left join fetch a.grupo g "
			+ "left join fetch a.usuario u "
			+ "left join fetch a.evaluacion e  "
			+ "where s.idSolicitud = :idSolicitud "
			+ "and a.flagActivo = 1 "
			+ "and td.idListadoDetalle = :idTipo "
            + "and g.idListadoDetalle = :idGrupo "
			+ "and e.idListadoDetalle = :idAprobado")
	public List<Asignacion> obtenerAsignacionesPorGrupoYSolicitud(Long idTipo, Long idGrupo, Long idSolicitud, Long idAprobado);


	@Query("select a from Asignacion a where a.contrato = :contratoId AND a.tipo.idListadoDetalle = :tipoLdId")
	    Asignacion findByContratoAndTipo(Long contratoId, Long tipoLdId);
	
   /* @Query("SELECT a " +
            "FROM Asignacion a " +
            " JOIN FETCH a.tipo td   " +
            " JOIN FETCH a.usuario u  " +
            " JOIN FETCH a.evaluacion ev " +
            "WHERE a.contrato = :contratoId " +
            "  AND a.flagActivo = 1 " +
            "ORDER BY a.fechaRegistro")
     public List<Asignacion> findHistorialByContratao(Long contratoId);
    */


	@Query(value = "SELECT " 
			+ "ld.NO_LISTADO_DETALLE  AS tipoLado, " 
			+ "a.FE_CREACION          AS fechaCreacion, "
			+ "u.no_usuario           AS usuario, " 
			+ "a.FE_APROBACION        AS fechaAprobacion, "
			+ "lde.NO_LISTADO_DETALLE AS tipoEvaluacion, " 
			+ "a.DE_OBSERVACION       AS observacion "
			+ "FROM sicoes_tr_asignacion a "
			+ " INNER JOIN sicoes_tm_listado_detalle ld  ON a.ID_TIPO_LD      = ld.ID_LISTADO_DETALLE "
			+ " INNER JOIN sicoes_tm_usuario          u   ON a.ID_USUARIO      = u.ID_USUARIO "
			+ " INNER JOIN sicoes_tm_listado_detalle lde ON a.ID_EVALUACION_LD = lde.ID_LISTADO_DETALLE "
			+ "WHERE a.id_contrato_ld = :contratoId " 
			+ "AND a.FL_ACTIVO      = 1", nativeQuery = true)
	public List<Object[]> findHistorialByContrato(Long contratoId);

	@Query(value = "SELECT a FROM Asignacion a LEFT JOIN a.tipo t LEFT JOIN a.evaluacion e "
			+ "WHERE t.codigo LIKE '" + Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO + "' "
			+ "AND a.solicitud.idSolicitud = :idSolicitud ")
	List<Asignacion> obtenerAsignacionesEvalAdm(
			@Param("idSolicitud") Long idSolicitud);
	
}

