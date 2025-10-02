package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.BandejaAprobacionFullDTO;

@Repository
public interface RequerimientoAprobacionDao extends JpaRepository<RequerimientoAprobacion, Long>, RequerimientoAprobacionDaoCustom {



    @Query(value = "SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, " +
           "r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, " +
           "r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, " +
           "r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, " +
           "r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, " +
           "r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, " +
           "r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, " +
           "r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, " +
           "r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, " +
           "r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, " +
           "i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, " +
           "i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, " +
           "rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, " +
           "s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora " +
           "FROM ES_SICOES.SICOES_TC_REQ_APROBACION r " +
           "JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION " +
           "JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION " +
           "LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT " +
           "LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA " +
           "WHERE r.ID_GRUPO_LD = 542 " +
           "AND r.ID_GRUPO_APROBADOR_LD = 954 " +
           "AND r.ID_USUARIO = :idUsuario " +
           "AND r.ID_ESTADO_LD IN (958, 960, 1160) " +
           "AND NOT EXISTS (SELECT 1 FROM ES_SICOES.SICOES_TC_REQ_APROBACION r2 WHERE r2.ID_INFORME_RENOVACION = r.ID_INFORME_RENOVACION AND r2.ID_GRUPO_APROBADOR_LD = 954 AND r2.ID_ESTADO_LD = 959) " +
           "ORDER BY r.FE_CREACION DESC", nativeQuery = true)
    Page<BandejaAprobacionFullDTO> buscarByIdUsuarioSimple(
           @Param("idUsuario") int idUsuario,
           Pageable pageable
    );

    @Query(value = "SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, " +
           "r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, " +
           "r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, " +
           "r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, " +
           "r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, " +
           "r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, " +
           "r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, " +
           "r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, " +
           "r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, " +
           "r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, " +
           "i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, " +
           "i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, " +
           "rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, " +
           "s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora " +
           "FROM ES_SICOES.SICOES_TC_REQ_APROBACION r " +
           "JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION " +
           "JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION " +
           "LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT " +
           "LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA " +
           "WHERE r.ID_GRUPO_LD = 542 " +
           "AND r.ID_GRUPO_APROBADOR_LD = 954 " +
           "AND r.ID_USUARIO = :idUsuario " +
           "AND (:numeroExpediente IS NULL OR rr.NU_EXPEDIENTE = :numeroExpediente) " +
           "AND (:estadoAprobacionInforme = -1 OR i.ES_APROBACION_INFORME = :estadoAprobacionInforme) " +
           "AND (:idContratista IS NULL OR spc.ID_SUPERVISORA = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(s.NO_RAZON_SOCIAL) LIKE UPPER('%' || :nombreContratista || '%')) " +
           "AND r.ID_ESTADO_LD IN (958, 960, 1160) " +
           "AND NOT EXISTS (SELECT 1 FROM ES_SICOES.SICOES_TC_REQ_APROBACION r2 WHERE r2.ID_INFORME_RENOVACION = r.ID_INFORME_RENOVACION AND r2.ID_GRUPO_APROBADOR_LD = 954 AND r2.ID_ESTADO_LD = 959) " +
           "ORDER BY r.FE_CREACION DESC", nativeQuery = true)
    Page<BandejaAprobacionFullDTO> buscarByIdUsuarioConFiltros(
           @Param("numeroExpediente") String numeroExpediente,
           @Param("estadoAprobacionInforme") Long estadoAprobacionInforme,
           @Param("idContratista") Long idContratista,
           @Param("nombreContratista") String nombreContratista,
           @Param("idUsuario") int idUsuario,
           Pageable pageable
    );


    
    @Query(value = "SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, " +
           "r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, " +
           "r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, " +
           "r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, " +
           "r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, " +
           "r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, " +
           "r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, " +
           "r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, " +
           "r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, " +
           "r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, " +
           "i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, " +
           "i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, " +
           "rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, " +
           "s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora " +
           "FROM ES_SICOES.SICOES_TC_REQ_APROBACION r " +
           "JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION " +
           "JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION " +
           "LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT " +
           "LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA " +
           "WHERE r.ID_GRUPO_LD = 542 " +
           "AND r.ID_GRUPO_APROBADOR_LD = 954 " +
           "AND r.ID_USUARIO = :idUsuario " +
           "AND (:estadoAprobacion IS NULL OR i.ES_APROBACION_INFORME = :estadoAprobacion) " +
           "AND (:numeroExpediente IS NULL OR rr.NU_EXPEDIENTE = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR spc.ID_SUPERVISORA = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(s.NO_RAZON_SOCIAL) LIKE UPPER('%' || :nombreContratista || '%')) " +
           "AND r.ID_ESTADO_LD IN (958, 960, 1160) " +
           "AND NOT EXISTS (SELECT 1 FROM ES_SICOES.SICOES_TC_REQ_APROBACION r2 WHERE r2.ID_INFORME_RENOVACION = r.ID_INFORME_RENOVACION AND r2.ID_GRUPO_APROBADOR_LD = 954 AND r2.ID_ESTADO_LD = 959) " +
           "ORDER BY r.FE_CREACION DESC", nativeQuery = true)
    Page<BandejaAprobacionFullDTO> buscarByIdUsuarioSinFiltroVigente(
           @Param("numeroExpediente") String numeroExpediente,
           @Param("estadoAprobacion") Long estadoAprobacion,
           @Param("idContratista") Long idContratista,
           @Param("nombreContratista") String nombreContratista,
           @Param("idUsuario") Long idUsuario,
           Pageable pageable
    );

    @Query("SELECT r FROM RequerimientoAprobacion r WHERE r.idEstadoLd = '1' ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> listarActivos();
    
    // Consulta simple para debug sin JOINs
    @Query(value = "SELECT * FROM ES_SICOES.SICOES_TC_REQ_APROBACION WHERE ROWNUM <= 5", nativeQuery = true)
    List<RequerimientoAprobacion> debugConsultaSimple();

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
           "AND r.grupo.idListadoDetalle = :idGrupoLd " +
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

    @Query(value = "SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, " +
           "r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, " +
           "r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, " +
           "r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, " +
           "r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, " +
           "r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, " +
           "r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, " +
           "r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, " +
           "r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, " +
           "r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, " +
           "i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, " +
           "i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, " +
           "rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, " +
           "s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora " +
           "FROM ES_SICOES.SICOES_TC_REQ_APROBACION r " +
           "JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION " +
           "JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION " +
           "LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT " +
           "LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA " +
           "WHERE r.ID_GRUPO_LD = 542 " +
           "AND r.ID_GRUPO_APROBADOR_LD = 954 " +
           "AND (:estadoAprobacion IS NULL OR i.ES_APROBACION_INFORME = :estadoAprobacion) " +
           "AND (:numeroExpediente IS NULL OR rr.NU_EXPEDIENTE = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR spc.ID_SUPERVISORA = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(s.NO_RAZON_SOCIAL) LIKE UPPER('%' || :nombreContratista || '%')) " +
           "AND r.ID_ESTADO_LD IN (958, 960, 1160) " +
           "AND NOT EXISTS (SELECT 1 FROM ES_SICOES.SICOES_TC_REQ_APROBACION r2 WHERE r2.ID_INFORME_RENOVACION = r.ID_INFORME_RENOVACION AND r2.ID_GRUPO_APROBADOR_LD = 954 AND r2.ID_ESTADO_LD = 959) " +
           "ORDER BY r.FE_CREACION DESC", nativeQuery = true)
    Page<BandejaAprobacionFullDTO> buscarSinFiltroUsuario(
        @Param("numeroExpediente") String numeroExpediente,
        @Param("estadoAprobacion") Long estadoAprobacion,
        @Param("idContratista") Long idContratista,
        @Param("nombreContratista") String nombreContratista,
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
           "WHERE r.grupo.idListadoDetalle = 543 " +
           "AND r.idGrupoAprobadorLd = 955 " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> debugRegistrosG2();

    // Nueva consulta para búsqueda por estadoAprobacionInforme sin filtro de estado del requerimiento
    @Query(value = "SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, " +
           "r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, " +
           "r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, " +
           "r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, " +
           "r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, " +
           "r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, " +
           "r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, " +
           "r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, " +
           "r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, " +
           "r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, " +
           "i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, " +
           "i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, " +
           "rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, " +
           "s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora " +
           "FROM ES_SICOES.SICOES_TC_REQ_APROBACION r " +
           "JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION " +
           "JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION " +
           "LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT " +
           "LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA " +
           "WHERE r.ID_GRUPO_LD = 542 " +
           "AND r.ID_GRUPO_APROBADOR_LD = 954 " +
           "AND r.ID_USUARIO = :idUsuario " +
           "AND i.ES_APROBACION_INFORME = :estadoAprobacion " +
           "AND (:numeroExpediente IS NULL OR rr.NU_EXPEDIENTE = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR spc.ID_SUPERVISORA = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(s.NO_RAZON_SOCIAL) LIKE UPPER('%' || :nombreContratista || '%')) " +
           "AND r.ID_ESTADO_LD IN (958, 960, 1160) " +
           "AND NOT EXISTS (SELECT 1 FROM ES_SICOES.SICOES_TC_REQ_APROBACION r2 WHERE r2.ID_INFORME_RENOVACION = r.ID_INFORME_RENOVACION AND r2.ID_GRUPO_APROBADOR_LD = 954 AND r2.ID_ESTADO_LD = 959) " +
           "ORDER BY r.FE_CREACION DESC", nativeQuery = true)
    Page<BandejaAprobacionFullDTO> buscarPorEstadoAprobacionInforme(
           @Param("estadoAprobacion") Long estadoAprobacion,
           @Param("numeroExpediente") String numeroExpediente,
           @Param("idContratista") Long idContratista,
           @Param("nombreContratista") String nombreContratista,
           @Param("idUsuario") Long idUsuario,
           Pageable pageable
    );

    // Nueva consulta sin filtro de usuario para búsqueda por estadoAprobacionInforme
    @Query(value = "SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, " +
           "r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, " +
           "r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, " +
           "r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, " +
           "r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, " +
           "r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, " +
           "r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, " +
           "r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, " +
           "r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, " +
           "r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, " +
           "i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, " +
           "i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, " +
           "rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, " +
           "s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora " +
           "FROM ES_SICOES.SICOES_TC_REQ_APROBACION r " +
           "JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION " +
           "JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION " +
           "LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT " +
           "LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA " +
           "WHERE r.ID_GRUPO_LD = 542 " +
           "AND r.ID_GRUPO_APROBADOR_LD = 954 " +
           "AND i.ES_APROBACION_INFORME = :estadoAprobacion " +
           "AND (:numeroExpediente IS NULL OR rr.NU_EXPEDIENTE = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR spc.ID_SUPERVISORA = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(s.NO_RAZON_SOCIAL) LIKE UPPER('%' || :nombreContratista || '%')) " +
           "AND r.ID_ESTADO_LD IN (958, 960, 1160) " +
           "AND NOT EXISTS (SELECT 1 FROM ES_SICOES.SICOES_TC_REQ_APROBACION r2 WHERE r2.ID_INFORME_RENOVACION = r.ID_INFORME_RENOVACION AND r2.ID_GRUPO_APROBADOR_LD = 954 AND r2.ID_ESTADO_LD = 959) " +
           "ORDER BY r.FE_CREACION DESC", nativeQuery = true)
    Page<BandejaAprobacionFullDTO> buscarPorEstadoAprobacionInformeSinFiltroUsuario(
        @Param("estadoAprobacion") Long estadoAprobacion,
        @Param("numeroExpediente") String numeroExpediente,
        @Param("idContratista") Long idContratista,
        @Param("nombreContratista") String nombreContratista,
        Pageable pageable
    );

    // ================= QUERIES PARA G2 (ID_GRUPO_LD = 543 AND ID_GRUPO_APROBADOR_LD = 955) =================
    
    @Query(value = "SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, " +
           "r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, " +
           "r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, " +
           "r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, " +
           "r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, " +
           "r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, " +
           "r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, " +
           "r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, " +
           "r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, " +
           "r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, " +
           "i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, " +
           "i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, " +
           "rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, " +
           "s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora " +
           "FROM ES_SICOES.SICOES_TC_REQ_APROBACION r " +
           "JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION " +
           "JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION " +
           "LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT " +
           "LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA " +
           "WHERE r.ID_GRUPO_LD = 543 " +
           "AND r.ID_GRUPO_APROBADOR_LD = 955 " +
           "AND r.ID_ESTADO_LD IN (958, 959, 960, 1160) " +
           "ORDER BY r.FE_CREACION DESC", nativeQuery = true)
    Page<BandejaAprobacionFullDTO> buscarByIdUsuarioG2Simple(
           Pageable pageable
    );

    @Query(value = "SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, " +
           "r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, " +
           "r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, " +
           "r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, " +
           "r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, " +
           "r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, " +
           "r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, " +
           "r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, " +
           "r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, " +
           "r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, " +
           "i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, " +
           "i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, " +
           "rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, " +
           "s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora " +
           "FROM ES_SICOES.SICOES_TC_REQ_APROBACION r " +
           "JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION " +
           "JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION " +
           "LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT " +
           "LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA " +
           "WHERE r.ID_GRUPO_LD = 543 " +
           "AND r.ID_GRUPO_APROBADOR_LD = 955 " +
           "AND (:numeroExpediente IS NULL OR rr.NU_EXPEDIENTE = :numeroExpediente) " +
           "AND (:estadoAprobacionInforme = -1 OR i.ES_APROBACION_INFORME = :estadoAprobacionInforme) " +
           "AND (:idContratista IS NULL OR spc.ID_SUPERVISORA = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(s.NO_RAZON_SOCIAL) LIKE UPPER('%' || :nombreContratista || '%')) " +
           "AND r.ID_ESTADO_LD IN (958, 959, 960, 1160) " +
           "ORDER BY r.FE_CREACION DESC", nativeQuery = true)
    Page<BandejaAprobacionFullDTO> buscarByIdUsuarioG2ConFiltros(
           @Param("numeroExpediente") String numeroExpediente,
           @Param("estadoAprobacionInforme") Long estadoAprobacionInforme,
           @Param("idContratista") Long idContratista,
           @Param("nombreContratista") String nombreContratista,
           Pageable pageable
    );

    @Query(value = "SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, " +
           "r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, " +
           "r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, " +
           "r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, " +
           "r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, " +
           "r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, " +
           "r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, " +
           "r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, " +
           "r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, " +
           "r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, " +
           "i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, " +
           "i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, " +
           "rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, " +
           "s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora " +
           "FROM ES_SICOES.SICOES_TC_REQ_APROBACION r " +
           "JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION " +
           "JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION " +
           "LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT " +
           "LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA " +
           "WHERE r.ID_GRUPO_LD = 543 " +
           "AND r.ID_GRUPO_APROBADOR_LD = 955 " +
           "AND (:estadoAprobacion IS NULL OR i.ES_APROBACION_INFORME = :estadoAprobacion) " +
           "AND (:numeroExpediente IS NULL OR rr.NU_EXPEDIENTE = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR spc.ID_SUPERVISORA = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(s.NO_RAZON_SOCIAL) LIKE UPPER('%' || :nombreContratista || '%')) " +
           "AND r.ID_ESTADO_LD IN (958, 959, 960, 1160) " +
           "ORDER BY r.FE_CREACION DESC", nativeQuery = true)
    Page<BandejaAprobacionFullDTO> buscarSinFiltroUsuarioG2(
        @Param("numeroExpediente") String numeroExpediente,
        @Param("estadoAprobacion") Long estadoAprobacion,
        @Param("idContratista") Long idContratista,
        @Param("nombreContratista") String nombreContratista,
        Pageable pageable
    );

    @Query(value = "SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, " +
           "r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, " +
           "r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, " +
           "r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, " +
           "r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, " +
           "r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, " +
           "r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, " +
           "r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, " +
           "r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, " +
           "r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, " +
           "i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, " +
           "i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, " +
           "rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, " +
           "s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora " +
           "FROM ES_SICOES.SICOES_TC_REQ_APROBACION r " +
           "JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION " +
           "JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION " +
           "LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT " +
           "LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA " +
           "WHERE r.ID_GRUPO_LD = 543 " +
           "AND r.ID_GRUPO_APROBADOR_LD = 955 " +
           "AND i.ES_APROBACION_INFORME = :estadoAprobacion " +
           "AND (:numeroExpediente IS NULL OR rr.NU_EXPEDIENTE = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR spc.ID_SUPERVISORA = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(s.NO_RAZON_SOCIAL) LIKE UPPER('%' || :nombreContratista || '%')) " +
           "AND r.ID_ESTADO_LD IN (958, 959, 960, 1160) " +
           "ORDER BY r.FE_CREACION DESC", nativeQuery = true)
    Page<BandejaAprobacionFullDTO> buscarPorEstadoAprobacionInformeG2(
           @Param("estadoAprobacion") Long estadoAprobacion,
           @Param("numeroExpediente") String numeroExpediente,
           @Param("idContratista") Long idContratista,
           @Param("nombreContratista") String nombreContratista,
           Pageable pageable
    );

    @Query(value = "SELECT r.ID_REQ_APROBACION as idReqAprobacion, r.ID_REQUERIMIENTO as idRequerimiento, " +
           "r.ID_REQ_INFORME as idReqInforme, r.ID_REQ_DOCUMENTO as idReqDocumento, " +
           "r.ID_TIPO_LD as idTipoLd, r.ID_GRUPO_LD as idGrupoLd, r.ID_USUARIO as idUsuario, " +
           "r.ID_ESTADO_LD as idEstadoLd, r.ID_FIRMADO_LD as idFirmadoLd, r.DE_OBSERVACION as deObservacion, " +
           "r.FE_APROBACION as feAprobacion, r.FE_RECHAZO as feRechazo, r.FE_FIRMA as feFirma, " +
           "r.ID_INFORME_RENOVACION as idInformeRenovacion, r.ID_NOTIFICACION as idNotificacion, " +
           "r.FE_ASIGNACION as feAsignacion, r.ID_TIPO_APROBADOR_LD as idTipoAprobadorLd, " +
           "r.ID_GRUPO_APROBADOR_LD as idGrupoAprobadorLd, " +
           "r.US_CREACION as usCreacion, r.IP_CREACION as ipCreacion, r.FE_CREACION as feCreacion, " +
           "r.US_ACTUALIZACION as usActualizacion, r.IP_ACTUALIZACION as ipActualizacion, r.FE_ACTUALIZACION as feActualizacion, " +
           "i.ES_VIGENTE as esVigente, i.ES_APROBACION_INFORME as esAprobacionInforme, " +
           "i.DE_UUID_INFO_RENOVACION as deUuidInfoRenovacion, i.DE_NOMBRE_ARCHIVO as deNombreArchivo, " +
           "rr.NU_EXPEDIENTE as nuExpediente, rr.TI_SECTOR as tiSector, rr.TI_SUB_SECTOR as tiSubSector, rr.NO_ITEM as noItem, " +
           "s.NO_RAZON_SOCIAL as noRazonSocial, s.ID_SUPERVISORA as idSupervisora " +
           "FROM ES_SICOES.SICOES_TC_REQ_APROBACION r " +
           "JOIN ES_SICOES.SICOES_TD_INFORME_RENOVACION i ON r.ID_INFORME_RENOVACION = i.ID_INFORME_RENOVACION " +
           "JOIN ES_SICOES.SICOES_TC_REQ_RENOVACION rr ON i.ID_REQUERIMIENTO = rr.ID_REQ_RENOVACION " +
           "LEFT JOIN ES_SICOES.SICOES_TC_SOLI_PERF_CONT spc ON rr.ID_SOLI_PERF_CONT = spc.ID_SOLI_PERF_CONT " +
           "LEFT JOIN ES_SICOES.SICOES_TM_SUPERVISORA s ON spc.ID_SUPERVISORA = s.ID_SUPERVISORA " +
           "WHERE r.ID_GRUPO_LD = 543 " +
           "AND r.ID_GRUPO_APROBADOR_LD = 955 " +
           "AND i.ES_APROBACION_INFORME = :estadoAprobacion " +
           "AND (:numeroExpediente IS NULL OR rr.NU_EXPEDIENTE = :numeroExpediente) " +
           "AND (:idContratista IS NULL OR spc.ID_SUPERVISORA = :idContratista) " +
           "AND (:nombreContratista IS NULL OR UPPER(s.NO_RAZON_SOCIAL) LIKE UPPER('%' || :nombreContratista || '%')) " +
           "AND r.ID_ESTADO_LD IN (958, 959, 960, 1160) " +
           "ORDER BY r.FE_CREACION DESC", nativeQuery = true)
    Page<BandejaAprobacionFullDTO> buscarPorEstadoAprobacionInformeSinFiltroUsuarioG2(
        @Param("estadoAprobacion") Long estadoAprobacion,
        @Param("numeroExpediente") String numeroExpediente,
        @Param("idContratista") Long idContratista,
        @Param("nombreContratista") String nombreContratista,
        Pageable pageable
    );

    @Query(value="SELECT r FROM RequerimientoAprobacion r "
            + "LEFT JOIN FETCH r.tipoAprobador t "
            + "LEFT JOIN FETCH r.grupo g "
            + "LEFT JOIN FETCH r.usuario u "
            + "LEFT JOIN FETCH r.estado e "
            + "WHERE r.informeRenovacion.idInformeRenovacion = :idInformeRenovacion "
            + "ORDER BY r.fecCreacion DESC ",
            countQuery ="SELECT COUNT(DISTINCT r) FROM RequerimientoAprobacion r "
            + "WHERE r.informeRenovacion.idInformeRenovacion = :idInformeRenovacion ")
    Page<RequerimientoAprobacion> obtenerPorInformeRenovacion(Long idInformeRenovacion, Pageable pageable);

    // Query para determinar si un usuario es G1 o G2 usando SICOES_TX_PERFIL_APROBADOR
    @Query(value = "SELECT " +
           "CASE " +
           "    WHEN COUNT(CASE WHEN ID_APROBADOR_G1 = :idUsuario THEN 1 END) > 0 " +
           "     AND COUNT(CASE WHEN ID_APROBADOR_G2 = :idUsuario THEN 1 END) > 0 THEN 'G1,G2' " +
           "    WHEN COUNT(CASE WHEN ID_APROBADOR_G1 = :idUsuario THEN 1 END) > 0 THEN 'G1' " +
           "    WHEN COUNT(CASE WHEN ID_APROBADOR_G2 = :idUsuario THEN 1 END) > 0 THEN 'G2' " +
           "    ELSE 'No es G1 ni G2' " +
           "END AS TIPO_APROBADOR " +
           "FROM ( " +
           "    SELECT ID_APROBADOR_G1, ID_APROBADOR_G2 " +
           "    FROM ES_SICOES.SICOES_TX_PERFIL_APROBADOR " +
           "    WHERE (ID_APROBADOR_G1 = :idUsuario OR ID_APROBADOR_G2 = :idUsuario) " +
           "    AND ID_PERFIL IN (282,257,304) " +
           ")", nativeQuery = true)
    String obtenerTipoAprobador(@Param("idUsuario") Long idUsuario);
}