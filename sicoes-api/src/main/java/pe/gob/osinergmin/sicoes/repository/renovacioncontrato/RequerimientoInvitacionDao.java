package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

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

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.flActivo = '1' ORDER BY r.fecCreacion DESC")
    List<RequerimientoInvitacion> listarActivos();

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.idReqInvitacion = :id AND r.flActivo = '1'")
    RequerimientoInvitacion obtenerPorId(@Param("id") Long id);

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.idRequerimiento = :idRequerimiento AND r.flActivo = '1'")
    List<RequerimientoInvitacion> listarPorRequerimiento(@Param("idRequerimiento") Long idRequerimiento);

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.idSupervisora = :idSupervisora AND r.flActivo = '1'")
    List<RequerimientoInvitacion> listarPorSupervisora(@Param("idSupervisora") Long idSupervisora);

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.idEstadoLd = :estadoId AND r.flActivo = '1'")
    List<RequerimientoInvitacion> listarPorEstado(@Param("estadoId") Long estadoId);

    @Query("SELECT r FROM RequerimientoInvitacion r " +
           "LEFT JOIN r.requerimientoRenovacion req " +
           "WHERE r.flActivo = '1' " +
           "AND (:numeroExpediente IS NULL OR UPPER(req.nuExpediente) LIKE UPPER(CONCAT('%', :numeroExpediente, '%'))) " +
           "AND (:tipoSector IS NULL OR UPPER(req.tiSector) LIKE UPPER(CONCAT('%', :tipoSector, '%'))) " +
           "AND (:estadoInvitacion IS NULL OR r.idEstadoLd = :estadoInvitacion) " +
           "ORDER BY r.fecCreacion DESC")
    Page<RequerimientoInvitacion> buscarInvitaciones(@Param("numeroExpediente") String numeroExpediente,
                                                     @Param("tipoSector") String tipoSector,
                                                     @Param("estadoInvitacion") Integer estadoInvitacion,
                                                     Pageable pageable);

    @Query("SELECT r FROM RequerimientoInvitacion r " +
           "WHERE r.flActivo = '1' " +
           "AND r.fecCreacion BETWEEN STR_TO_DATE(:fechaDesde, '%d/%m/%Y') AND STR_TO_DATE(:fechaHasta, '%d/%m/%Y') " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoInvitacion> buscarPorRangoFechas(@Param("fechaDesde") String fechaDesde,
                                                       @Param("fechaHasta") String fechaHasta);
}