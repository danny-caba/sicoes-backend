package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.osinergmin.sicoes.model.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.util.Constantes;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RequerimientoAprobacionDao extends JpaRepository<RequerimientoAprobacion, Long> {

    @Query("SELECT r FROM RequerimientoAprobacion r " +
            "WHERE r.requerimiento.idRequerimiento = :idRequerimiento " +
            "and r.grupo.idListadoDetalle = :idListadoDetalle " +
            "and (:estadoAprobacion IS NULL OR r.estado.codigo = :estadoAprobacion) ")
    Optional<RequerimientoAprobacion> obtenerPorRequerimientoYGrupo(@Param("idRequerimiento") Long idRequerimiento,
                                                                    @Param("idListadoDetalle") Long idListadoDetalle,
                                                                    @Param("estadoAprobacion") String estadoAprobacion);
    @Query(value = "SELECT r FROM RequerimientoAprobacion r " +
            "WHERE r.requerimiento.requerimientoUuid = :uuid " +
            "order by r.grupo.orden",
            countQuery = "SELECT r FROM RequerimientoAprobacion r " +
                    "WHERE r.requerimiento.requerimientoUuid = :uuid ")
    Page<RequerimientoAprobacion> obtenerAprobaciones(@Param("uuid") String uuid,
                                                      Pageable pageable);


    @Query(value = "SELECT ra FROM RequerimientoAprobacion ra " +
            "LEFT JOIN FETCH ra.requerimiento r " +
            "LEFT JOIN FETCH r.division d " +
            "LEFT JOIN FETCH r.perfil p " +
            "LEFT JOIN FETCH r.supervisora s " +
            "LEFT JOIN FETCH r.estado e " +
            "LEFT JOIN FETCH r.estadoRevision er " +
            "LEFT JOIN FETCH ra.usuario u " +
            "LEFT JOIN FETCH ra.tipo t " +
            "LEFT JOIN FETCH ra.estado rae " +
            "WHERE rae.codigo = '" + Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO + "' " +
            "AND (:expediente IS NULL OR r.nuExpediente = :expediente) " +
            "AND (:idDivision IS NULL OR d.idDivision = :idDivision) " +
            "AND (:idPerfil IS NULL OR p.idListadoDetalle = :idPerfil) " +
            "AND (:idSupervisora IS NULL OR s.idSupervisora = :idSupervisora) " +
            "AND (:idEstadoAprobacion IS NULL OR e.idListadoDetalle = :idEstadoAprobacion) " +
            "AND u.idUsuario = :idUsuario " +
            "ORDER BY r.nuExpediente ASC",
            countQuery = "SELECT ra FROM RequerimientoAprobacion ra " +
                    "LEFT JOIN ra.requerimiento r " +
                    "LEFT JOIN r.division d " +
                    "LEFT JOIN r.perfil p " +
                    "LEFT JOIN r.supervisora s " +
                    "LEFT JOIN r.estado e " +
                    "LEFT JOIN r.estadoRevision er " +
                    "LEFT JOIN ra.usuario u " +
                    "LEFT JOIN ra.tipo t " +
                    "LEFT JOIN ra.estado rae " +
                    "WHERE rae.codigo = '" + Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO + "' " +
                    "AND (:expediente IS NULL OR r.nuExpediente = :expediente) " +
                    "AND (:idDivision IS NULL OR d.idDivision = :idDivision) " +
                    "AND (:idPerfil IS NULL OR p.idListadoDetalle = :idPerfil) " +
                    "AND (:idSupervisora IS NULL OR s.idSupervisora = :idSupervisora) " +
                    "AND (:idEstadoAprobacion IS NULL OR e.idListadoDetalle = :idEstadoAprobacion) " +
                    "AND u.idUsuario = :idUsuario ")
    Page<RequerimientoAprobacion> buscar(
            @Param("expediente") String expediente,
            @Param("idDivision") Long idDivision,
            @Param("idPerfil") Long idPerfil,
            @Param("idSupervisora") Long idSupervisora,
            @Param("idEstadoAprobacion") Long idEstadoAprobacion,
            @Param("idUsuario") Long idUsuario,
            Pageable pageable
    );

    @Query(value = "SELECT ra FROM RequerimientoAprobacion ra " +
            "LEFT JOIN FETCH ra.requerimiento r " +
            "LEFT JOIN FETCH ra.estado e " +
            "LEFT JOIN FETCH ra.grupo g " +
            "WHERE r.idRequerimiento IN :idsRequerimientos")
    List<RequerimientoAprobacion> obtenerAprobacionesPorRequerimiento(
            @Param("idsRequerimientos") Set<Long> idsRequerimientos);


}
