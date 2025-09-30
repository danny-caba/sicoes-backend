package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;

@Repository
public interface RequerimientoInvitacionDao extends JpaRepository<RequerimientoInvitacion, Long> {

    @Query("SELECT ri FROM RequerimientoInvitacion ri " +
            "LEFT JOIN FETCH ri.requerimientoRenovacion rr " +
            "WHERE ri.idReqInvitacion = :id")
    Optional<RequerimientoInvitacion> findByIdWithRequerimientoRenovacion(@Param("id") Long id);

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.flActivo = '1' ORDER BY r.idReqInvitacion DESC")
    List<RequerimientoInvitacion> listarActivos();

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.idReqInvitacion = :id AND r.flActivo = '1'")
    RequerimientoInvitacion obtenerPorId(@Param("id") Long id);

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.idRequerimiento = :idRequerimiento AND r.flActivo = '1'")
    List<RequerimientoInvitacion> listarPorRequerimiento(@Param("idRequerimiento") Long idRequerimiento);

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.supervisora.idSupervisora = :idSupervisora AND r.flActivo = '1'")
    List<RequerimientoInvitacion> listarPorSupervisora(@Param("idSupervisora") Long idSupervisora);

    @Query("SELECT r FROM RequerimientoInvitacion r " +
           "LEFT JOIN r.requerimientoRenovacion req " +
           "LEFT JOIN r.estadoInvitacion e " +
           "WHERE r.flActivo = '1' " +
           "AND (:numeroExpediente IS NULL OR UPPER(req.nuExpediente) LIKE UPPER(CONCAT('%', :numeroExpediente, '%'))) " +
           "AND (:nombreItem IS NULL OR UPPER(req.noItem) LIKE UPPER(CONCAT('%', :nombreItem, '%'))) " +
           "AND (:estadoInvitacion IS NULL OR e.idListadoDetalle = :estadoInvitacion) " +
           "ORDER BY r.idReqInvitacion DESC")
    Page<RequerimientoInvitacion> buscarInvitaciones(@Param("numeroExpediente") String numeroExpediente,
                                                     @Param("nombreItem") String nombreItem,
                                                     @Param("estadoInvitacion") Integer estadoInvitacion,
                                                     Pageable pageable);

    @Query("SELECT r FROM RequerimientoInvitacion r " +
           "WHERE r.flActivo = '1' " +
           "AND r.fecCreacion BETWEEN STR_TO_DATE(:fechaDesde, '%d/%m/%Y') AND STR_TO_DATE(:fechaHasta, '%d/%m/%Y') " +
           "ORDER BY r.idReqInvitacion DESC")
    List<RequerimientoInvitacion> buscarPorRangoFechas(@Param("fechaDesde") String fechaDesde,
                                                       @Param("fechaHasta") String fechaHasta);

    Optional<RequerimientoInvitacion> findByIdReqInvitacion(Long idReqInvitacion);


    @Query("SELECT ri FROM RequerimientoInvitacion ri " +
                "WHERE ri.estadoInvitacion.codigo = :estadoInvitado " +
                "AND ri.feCaducidad < :fechaReferencia " +
                "AND ri.flActivo = '1'")
    List<RequerimientoInvitacion> findInvitacionesCaducadas(@Param("fechaReferencia") Date fechaReferencia,
                @Param("estadoInvitado") String estadoInvitado);

    @Query(value="select distinct ri from RequerimientoInvitacion ri "
            + "left join fetch ri.requerimientoRenovacion rr "
            + "left join fetch ri.estadoInvitacion e "
            + "where (:numeroExpediente is null or UPPER(rr.nuExpediente) like UPPER(:numeroExpediente)) "
            + "and (:nombreItem is null or UPPER(rr.noItem) like UPPER(:nombreItem)) "
            + "and (:estadoInvitacion is null or e.idListadoDetalle = :estadoInvitacion) "
            + "and (:fechaInicio is null or trunc(ri.feInvitacion)>=:fechaInicio) "
            + "and (:fechaFin is null or trunc(ri.feInvitacion)<=:fechaFin) "
            + "and (:idSupervisora is null or ri.supervisora.idSupervisora = :idSupervisora) "
            + "order by ri.fecCreacion desc ",
            countQuery ="select count(distinct ri) from RequerimientoInvitacion ri "
                    + "left join ri.requerimientoRenovacion rr "
                    + "left join ri.estadoInvitacion e "
                    + "where (:numeroExpediente is null or UPPER(rr.nuExpediente) like UPPER(:numeroExpediente)) "
                    + "and (:nombreItem is null or UPPER(rr.noItem) like UPPER(:nombreItem)) "
                    + "and (:estadoInvitacion is null or e.idListadoDetalle = :estadoInvitacion) "
                    + "and (:fechaInicio is null or trunc(ri.feInvitacion)>=:fechaInicio) "
                    + "and (:fechaFin is null or trunc(ri.feInvitacion)<=:fechaFin) "
                    + "and (:idSupervisora is null or ri.supervisora.idSupervisora = :idSupervisora) "
                    + "order by ri.fecCreacion desc ")
    Page<RequerimientoInvitacion> listarInvitaciones(
            String numeroExpediente,
            String nombreItem,
            Integer estadoInvitacion,
            Date fechaInicio,
            Date fechaFin,
            Long idSupervisora,
            Pageable pageable
    );

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.coUuid = :coUuid AND r.flActivo = '1'")
    Optional<RequerimientoInvitacion> obtenerPorUuid(String coUuid);

}