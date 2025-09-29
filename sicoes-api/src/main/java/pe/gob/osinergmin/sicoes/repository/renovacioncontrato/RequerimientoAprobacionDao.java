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



    @Query("SELECT DISTINCT r FROM RequerimientoAprobacion r " +
           "JOIN r.informeRenovacion i " +
           "WHERE r.idGrupoLd = 542 " +
           "AND r.idGrupoAprobadorLd = 954 " +
           "AND r.idUsuario = :idUsuario " +
           "AND (:estadoAprobacion IS NULL OR i.esAprobacionInforme = :estadoAprobacion) " +
           "AND (:numeroExpediente IS NULL OR i.requerimientoRenovacion.nuExpediente = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR i.requerimientoRenovacion.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(i.requerimientoRenovacion.solicitudPerfil.supervisora.nombreRazonSocial) LIKE UPPER(CONCAT('%', :nombreContratista, '%'))) " +
           "AND (:esVigente IS NULL OR i.esVigente = :esVigente) " +
           "AND r.idEstadoLd IN (958, 960, 1160) " +
           "AND NOT EXISTS (SELECT 1 FROM RequerimientoAprobacion r2 WHERE r2.idInformeRenovacion = r.idInformeRenovacion AND r2.idGrupoAprobadorLd = 954 AND r2.idEstadoLd = 959) " +
           "ORDER BY r.fecCreacion DESC")
    Page<RequerimientoAprobacion> buscarByIdUsuario(
           @Param("numeroExpediente") String numeroExpediente,
           @Param("estadoAprobacion") Long estadoAprobacion,
           @Param("idContratista") Long idContratista,
           @Param("nombreContratista") String nombreContratista,
           @Param("idUsuario") Long idUsuario,
           @Param("esVigente") Integer esVigente,
           Pageable pageable
    );

    @Query("SELECT r FROM RequerimientoAprobacion r WHERE r.idEstadoLd = '1' ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> listarActivos();

    @Query("SELECT r FROM RequerimientoAprobacion r WHERE r.idReqAprobacion = :id  ")
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
    
    @Query("SELECT r FROM RequerimientoAprobacion r " +
           "WHERE r.idInformeRenovacion = :idInformeRenovacion " +
           "AND r.idGrupoLd = :idGrupoLd " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> findByIdInformeRenovacionAndIdGrupoLd(
        @Param("idInformeRenovacion") Long idInformeRenovacion, 
        @Param("idGrupoLd") Long idGrupoLd);
    
    @Query("SELECT r FROM RequerimientoAprobacion r " +
           "WHERE r.idUsuario = :idUsuario " +
           "AND r.idEstadoLd = :idEstadoLd " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> findByIdUsuarioAndIdEstadoLd(
        @Param("idUsuario") Long idUsuario,
        @Param("idEstadoLd") Long idEstadoLd);

    @Query("SELECT r FROM RequerimientoAprobacion r " +
           "WHERE r.idInformeRenovacion = :idInformeRenovacion " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> findByIdInformeRenovacion(
        @Param("idInformeRenovacion") Long idInformeRenovacion);

    @Query("SELECT DISTINCT r FROM RequerimientoAprobacion r " +
           "JOIN r.informeRenovacion i " +
           "WHERE r.idGrupoLd = 542 " +
           "AND r.idGrupoAprobadorLd = 954 " +
           "AND (:estadoAprobacion IS NULL OR i.esAprobacionInforme = :estadoAprobacion) " +
           "AND (:numeroExpediente IS NULL OR i.requerimientoRenovacion.nuExpediente = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR i.requerimientoRenovacion.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(i.requerimientoRenovacion.solicitudPerfil.supervisora.nombreRazonSocial) LIKE UPPER(CONCAT('%', :nombreContratista, '%'))) " +
           "AND (:esVigente IS NULL OR i.esVigente = :esVigente) " +
           "AND r.idEstadoLd IN (958, 960, 1160) " +
           "AND NOT EXISTS (SELECT 1 FROM RequerimientoAprobacion r2 WHERE r2.idInformeRenovacion = r.idInformeRenovacion AND r2.idGrupoAprobadorLd = 954 AND r2.idEstadoLd = 959) " +
           "ORDER BY r.fecCreacion DESC")
    Page<RequerimientoAprobacion> buscarSinFiltroUsuario(
        @Param("numeroExpediente") String numeroExpediente,
        @Param("estadoAprobacion") Long estadoAprobacion,
        @Param("idContratista") Long idContratista,
        @Param("nombreContratista") String nombreContratista,
        @Param("esVigente") Integer esVigente,
        Pageable pageable
    );

    // Query simple para debugging del filtro por estado
    @Query("SELECT r FROM RequerimientoAprobacion r " +
           "LEFT JOIN r.informeRenovacion i " +
           "WHERE (:estadoAprobacion IS NULL OR i.esAprobacionInforme = :estadoAprobacion) " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> debugPorEstadoAprobacion(@Param("estadoAprobacion") Long estadoAprobacion);

    // Query específica para verificar registros G2 existentes
    @Query("SELECT r FROM RequerimientoAprobacion r " +
           "WHERE r.idGrupoLd = 543 " +
           "AND r.idGrupoAprobadorLd = 955 " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> debugRegistrosG2();

    // Nueva consulta para búsqueda por estadoAprobacionInforme sin filtro de estado del requerimiento
    @Query("SELECT DISTINCT r FROM RequerimientoAprobacion r " +
           "JOIN r.informeRenovacion i " +
           "WHERE r.idGrupoLd = 542 " +
           "AND r.idGrupoAprobadorLd = 954 " +
           "AND r.idUsuario = :idUsuario " +
           "AND i.esAprobacionInforme = :estadoAprobacion " +
           "AND (:numeroExpediente IS NULL OR i.requerimientoRenovacion.nuExpediente = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR i.requerimientoRenovacion.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(i.requerimientoRenovacion.solicitudPerfil.supervisora.nombreRazonSocial) LIKE UPPER(CONCAT('%', :nombreContratista, '%'))) " +
           "AND (:esVigente IS NULL OR i.esVigente = :esVigente) " +
           "AND r.idEstadoLd IN (958, 960, 1160) " +
           "AND NOT EXISTS (SELECT 1 FROM RequerimientoAprobacion r2 WHERE r2.idInformeRenovacion = r.idInformeRenovacion AND r2.idGrupoAprobadorLd = 954 AND r2.idEstadoLd = 959) " +
           "ORDER BY r.fecCreacion DESC")
    Page<RequerimientoAprobacion> buscarPorEstadoAprobacionInforme(
           @Param("estadoAprobacion") Long estadoAprobacion,
           @Param("numeroExpediente") String numeroExpediente,
           @Param("idContratista") Long idContratista,
           @Param("nombreContratista") String nombreContratista,
           @Param("idUsuario") Long idUsuario,
           @Param("esVigente") Integer esVigente,
           Pageable pageable
    );

    // Nueva consulta sin filtro de usuario para búsqueda por estadoAprobacionInforme
    @Query("SELECT DISTINCT r FROM RequerimientoAprobacion r " +
           "JOIN r.informeRenovacion i " +
           "WHERE r.idGrupoLd = 542 " +
           "AND r.idGrupoAprobadorLd = 954 " +
           "AND i.esAprobacionInforme = :estadoAprobacion " +
           "AND (:numeroExpediente IS NULL OR i.requerimientoRenovacion.nuExpediente = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR i.requerimientoRenovacion.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(i.requerimientoRenovacion.solicitudPerfil.supervisora.nombreRazonSocial) LIKE UPPER(CONCAT('%', :nombreContratista, '%'))) " +
           "AND (:esVigente IS NULL OR i.esVigente = :esVigente) " +
           "AND r.idEstadoLd IN (958, 960, 1160) " +
           "AND NOT EXISTS (SELECT 1 FROM RequerimientoAprobacion r2 WHERE r2.idInformeRenovacion = r.idInformeRenovacion AND r2.idGrupoAprobadorLd = 954 AND r2.idEstadoLd = 959) " +
           "ORDER BY r.fecCreacion DESC")
    Page<RequerimientoAprobacion> buscarPorEstadoAprobacionInformeSinFiltroUsuario(
        @Param("estadoAprobacion") Long estadoAprobacion,
        @Param("numeroExpediente") String numeroExpediente,
        @Param("idContratista") Long idContratista,
        @Param("nombreContratista") String nombreContratista,
        @Param("esVigente") Integer esVigente,
        Pageable pageable
    );

    // ================= QUERIES PARA G2 (ID_GRUPO_LD = 543 AND ID_GRUPO_APROBADOR_LD = 955) =================
    
    @Query("SELECT DISTINCT r FROM RequerimientoAprobacion r " +
           "JOIN r.informeRenovacion i " +
           "WHERE r.idGrupoLd = 543 " +
           "AND r.idGrupoAprobadorLd = 955 " +
           "AND (:estadoAprobacion IS NULL OR i.esAprobacionInforme = :estadoAprobacion) " +
           "AND (:numeroExpediente IS NULL OR i.requerimientoRenovacion.nuExpediente = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR i.requerimientoRenovacion.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(i.requerimientoRenovacion.solicitudPerfil.supervisora.nombreRazonSocial) LIKE UPPER(CONCAT('%', :nombreContratista, '%'))) " +
           "AND (:esVigente IS NULL OR i.esVigente = :esVigente) " +
           "AND r.idEstadoLd IN (958, 959, 960, 1160) " +
           "ORDER BY r.fecCreacion DESC")
    Page<RequerimientoAprobacion> buscarByIdUsuarioG2(
           @Param("numeroExpediente") String numeroExpediente,
           @Param("estadoAprobacion") Long estadoAprobacion,
           @Param("idContratista") Long idContratista,
           @Param("nombreContratista") String nombreContratista,
           @Param("esVigente") Integer esVigente,
           Pageable pageable
    );

    @Query("SELECT DISTINCT r FROM RequerimientoAprobacion r " +
           "JOIN r.informeRenovacion i " +
           "WHERE r.idGrupoLd = 543 " +
           "AND r.idGrupoAprobadorLd = 955 " +
           "AND (:estadoAprobacion IS NULL OR i.esAprobacionInforme = :estadoAprobacion) " +
           "AND (:numeroExpediente IS NULL OR i.requerimientoRenovacion.nuExpediente = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR i.requerimientoRenovacion.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(i.requerimientoRenovacion.solicitudPerfil.supervisora.nombreRazonSocial) LIKE UPPER(CONCAT('%', :nombreContratista, '%'))) " +
           "AND (:esVigente IS NULL OR i.esVigente = :esVigente) " +
           "AND r.idEstadoLd IN (958, 959, 960, 1160) " +
           "ORDER BY r.fecCreacion DESC")
    Page<RequerimientoAprobacion> buscarSinFiltroUsuarioG2(
        @Param("numeroExpediente") String numeroExpediente,
        @Param("estadoAprobacion") Long estadoAprobacion,
        @Param("idContratista") Long idContratista,
        @Param("nombreContratista") String nombreContratista,
        @Param("esVigente") Integer esVigente,
        Pageable pageable
    );

    @Query("SELECT DISTINCT r FROM RequerimientoAprobacion r " +
           "JOIN r.informeRenovacion i " +
           "WHERE r.idGrupoLd = 543 " +
           "AND r.idGrupoAprobadorLd = 955 " +
           "AND i.esAprobacionInforme = :estadoAprobacion " +
           "AND (:numeroExpediente IS NULL OR i.requerimientoRenovacion.nuExpediente = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR i.requerimientoRenovacion.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(i.requerimientoRenovacion.solicitudPerfil.supervisora.nombreRazonSocial) LIKE UPPER(CONCAT('%', :nombreContratista, '%'))) " +
           "AND (:esVigente IS NULL OR i.esVigente = :esVigente) " +
           "AND r.idEstadoLd IN (958, 959, 960, 1160) " +
           "ORDER BY r.fecCreacion DESC")
    Page<RequerimientoAprobacion> buscarPorEstadoAprobacionInformeG2(
           @Param("estadoAprobacion") Long estadoAprobacion,
           @Param("numeroExpediente") String numeroExpediente,
           @Param("idContratista") Long idContratista,
           @Param("nombreContratista") String nombreContratista,
           @Param("esVigente") Integer esVigente,
           Pageable pageable
    );

    @Query("SELECT DISTINCT r FROM RequerimientoAprobacion r " +
           "JOIN r.informeRenovacion i " +
           "WHERE r.idGrupoLd = 543 " +
           "AND r.idGrupoAprobadorLd = 955 " +
           "AND i.esAprobacionInforme = :estadoAprobacion " +
           "AND (:numeroExpediente IS NULL OR i.requerimientoRenovacion.nuExpediente = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR i.requerimientoRenovacion.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(i.requerimientoRenovacion.solicitudPerfil.supervisora.nombreRazonSocial) LIKE UPPER(CONCAT('%', :nombreContratista, '%'))) " +
           "AND (:esVigente IS NULL OR i.esVigente = :esVigente) " +
           "AND r.idEstadoLd IN (958, 959, 960, 1160) " +
           "ORDER BY r.fecCreacion DESC")
    Page<RequerimientoAprobacion> buscarPorEstadoAprobacionInformeSinFiltroUsuarioG2(
        @Param("estadoAprobacion") Long estadoAprobacion,
        @Param("numeroExpediente") String numeroExpediente,
        @Param("idContratista") Long idContratista,
        @Param("nombreContratista") String nombreContratista,
        @Param("esVigente") Integer esVigente,
        Pageable pageable
    );
}