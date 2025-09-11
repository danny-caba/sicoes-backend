package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;


@Repository
public interface InformeRenovacionContratoDao extends JpaRepository<InformeRenovacionContrato, Long> {

    @Query(
            value = "SELECT i FROM InformeRenovacionContrato i " +
                    "JOIN FETCH i.usuario " +
                    "JOIN FETCH i.requerimiento r " +
                    "WHERE (:estadoAprobacion IS NULL OR i.estadoAprobacionInforme.idListadoDetalle = :estadoAprobacion) " +
                    "AND (:numeroExpediente IS NULL OR r.nuExpediente = :numeroExpediente) " +
                    "AND (:idContratista IS NULL OR r.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
                    "AND (:vigente IS NULL OR i.vigente = :vigente) " +
                    "AND EXISTS (SELECT apr FROM i.aprobaciones apr WHERE apr.idUsuario = :idUsuario) " +
                    "ORDER BY i.usuCreacion DESC",
            countQuery = "SELECT COUNT(i) FROM InformeRenovacionContrato i " +
                    "JOIN i.requerimiento r " +
                    "WHERE (:estadoAprobacion IS NULL OR i.estadoAprobacionInforme.idListadoDetalle = :estadoAprobacion) " +
                    "AND (:numeroExpediente IS NULL OR r.nuExpediente = :numeroExpediente) " +
                    "AND (:idContratista IS NULL OR r.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
                    "AND (:vigente IS NULL OR i.vigente = :vigente) " +
                    "AND EXISTS (SELECT apr FROM i.aprobaciones apr WHERE apr.idUsuario = :idUsuario)"
    )
    Page<InformeRenovacionContrato> findByFiltrosWithJoins2(
            @Param("numeroExpediente") String numeroExpediente,
            @Param("vigente") Boolean vigente,
            @Param("estadoAprobacion") Long estadoAprobacion,
            @Param("idContratista") Long idContratista,
            @Param("idUsuario") Long idUsuario,
            Pageable pageable
    );

@Query(
        value = "SELECT i FROM InformeRenovacionContrato i " +
                        "INNER JOIN FETCH i.usuario u " +
                        "INNER JOIN FETCH i.requerimiento r " +
                        "WHERE (:estadoAprobacion IS NULL OR i.estadoAprobacionInforme.idListadoDetalle = :estadoAprobacion) " +
                        "AND (:numeroExpediente IS NULL OR r.nuExpediente = :numeroExpediente) " +
                        "AND (:idContratista IS NULL OR r.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
                        "AND (:vigente IS NULL OR i.vigente = :vigente) " +
                        "ORDER BY i.usuCreacion DESC",
        countQuery = "SELECT COUNT(i) FROM InformeRenovacionContrato i " +
                        "INNER JOIN i.requerimiento r " +
                        "WHERE (:estadoAprobacion IS NULL OR i.estadoAprobacionInforme.idListadoDetalle = :estadoAprobacion) " +
                        "AND (:numeroExpediente IS NULL OR r.nuExpediente = :numeroExpediente) " +
                        "AND (:idContratista IS NULL OR r.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
                        "AND (:vigente IS NULL OR i.vigente = :vigente) "
)
Page<InformeRenovacionContrato> findByFiltrosWithJoins(
        @Param("numeroExpediente") String numeroExpediente,
        @Param("vigente") Boolean vigente,
        @Param("estadoAprobacion") Long estadoAprobacion,
        @Param("idContratista") Long idContratista,
        Pageable pageable
);
    
    
    @Transactional
    @Modifying
    @Query("UPDATE InformeRenovacionContrato informeRC SET " +        
           "informeRC.estadoAprobacionInforme.idListadoDetalle = :estado, " +
           "informeRC.fecActualizacion = :fecha, " +
           "informeRC.ipActualizacion = :ip, " +
           "informeRC.usuActualizacion = :usActualizacion " +
           "WHERE informeRC.idInformeRenovacion = :idInformeRenovacion")
    int actualizarEstadoEvaluacionPropuestPerfilPuestoPorId(
        @Param("idInformeRenovacion") Long idInformeRenovacion,
        @Param("estado") Long estado,
        @Param("fecha") LocalDateTime feActualizacion,
        @Param("ip") String ipActualizacion,
        @Param("usActualizacion") String usActualizacion
    );

    
    @Query("SELECT i FROM InformeRenovacionContrato i " +
    "LEFT JOIN FETCH i.usuario " +
    "LEFT JOIN FETCH i.notificacion " +
    "LEFT JOIN FETCH i.requerimiento " +
    "LEFT JOIN FETCH i.estadoAprobacionInforme " +
    "WHERE i.idInformeRenovacion = :idInformeRenovacion " +
    " ORDER BY i.usuCreacion DESC" )
    Optional<InformeRenovacionContrato>getInformeRenovacion(
            @Param("idInformeRenovacion") Long idInformeRenovacion
    );
}
