package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface RequerimientoAprobacionDao extends JpaRepository<RequerimientoAprobacion, Long> {



    @Query(
           value = "SELECT apr FROM RequerimientoAprobacion apr " +
                         "JOIN FETCH apr.informeRenovacion i " +
                         "JOIN FETCH i.usuario " +
                         "JOIN FETCH i.requerimientoRenovacion r " +
                         "WHERE (:estadoAprobacion IS NULL OR i.estadoAprobacionInforme.idListadoDetalle = :estadoAprobacion) " +
                         "AND (:numeroExpediente IS NULL OR r.nuExpediente = :numeroExpediente) " +
                         "AND (:idContratista IS NULL OR r.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
                         "AND apr.idUsuario = :idUsuario " +
                         "AND (:esVigente IS NULL OR i.esVigente = :esVigente) " +
                         "ORDER BY i.usuCreacion DESC",
           countQuery = "SELECT COUNT(apr) FROM RequerimientoAprobacion apr " +
                         "JOIN apr.informeRenovacion i " +
                         "JOIN i.requerimientoRenovacion r " +
                         "WHERE (:estadoAprobacion IS NULL OR i.estadoAprobacionInforme.idListadoDetalle = :estadoAprobacion) " +
                         "AND (:numeroExpediente IS NULL OR r.nuExpediente = :numeroExpediente) " +
                         "AND (:idContratista IS NULL OR r.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
                         "AND apr.idUsuario = :idUsuario " +
                         "AND (:esVigente IS NULL OR i.esVigente = :esVigente)"
    )
    Page<RequerimientoAprobacion> buscarByIdUsuario(
           @Param("numeroExpediente") String numeroExpediente,
           @Param("estadoAprobacion") Long estadoAprobacion,
           @Param("idContratista") Long idContratista,
           @Param("idUsuario") Long idUsuario,
           @Param("esVigente") Integer esVigente,
           Pageable pageable
    );

    @Query("SELECT r FROM RequerimientoAprobacion r WHERE r.idEstadoLd = '1' ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> listarActivos();

    @Query("SELECT r FROM RequerimientoAprobacion r WHERE r.idReqAprobacion = :id AND r.idEstadoLd = '1'")
    RequerimientoAprobacion obtenerPorId(@Param("id") Long id);

    @Query("SELECT r FROM RequerimientoAprobacion r " +
           "WHERE r.idEstadoLd = '1' " +
           "AND r.informeRenovacion.idInformeRenovacion = :idInforme " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> listarPorInforme(@Param("idInforme") Long idInforme);

    @Query("SELECT r FROM RequerimientoAprobacion r " +
           "WHERE r.idEstadoLd = '1' " +
           "AND r.idEstadoLd = :estadoId " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> listarPorEstado(@Param("estadoId") Long estadoId);

    @Query("SELECT r FROM RequerimientoAprobacion r " +
           "WHERE  r.idEstadoLd = '1' " +
           "AND r.idGrupoAprobadorLd = :idUsuario " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> listarPorAprobador(@Param("idUsuario") Long idUsuario);

    @Query("SELECT r FROM RequerimientoAprobacion r " +
           "WHERE r.idInformeRenovacion = :idInformeRenovacion " +
           "AND r.idGrupoAprobadorLd = :idGrupoAprobadorLd " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> findByIdInformeRenovacionAndIdGrupoAprobadorLd(
        @Param("idInformeRenovacion") Long idInformeRenovacion, 
        @Param("idGrupoAprobadorLd") Long idGrupoAprobadorLd);
}