package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.time.LocalDateTime;

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
                    "LEFT JOIN FETCH i.usuario u " +
                    "LEFT JOIN FETCH i.requerimiento r " +
                    "WHERE (:estadoAprobacion IS NULL OR i.estadoAprobacionInforme.idListadoDetalle = :estadoAprobacion) " +
                    "AND (:numeroExpediente IS NULL OR r.nuExpediente = :numeroExpediente) " +
                    "AND (:idContratista IS NULL OR r.solicitudPerfil.supervisora.idSupervisora = :idContratista) " +
                    "AND (:vigente IS NULL OR i.vigente = :vigente) " +
                    "ORDER BY i.usuCreacion DESC",
            countQuery = "SELECT COUNT(i) FROM InformeRenovacionContrato i " +
                    "LEFT JOIN i.requerimiento r " +
                    "WHERE (:estadoAprobacion IS NULL OR i.estadoAprobacionInforme.idListado = :estadoAprobacion) " +
                    " AND (:numeroExpediente IS NULL OR r.nuExpediente = :numeroExpediente) " +
                    "AND (:vigente IS NULL OR i.vigente = :vigente)"
    )
    Page<InformeRenovacionContrato> findByFiltrosWithJoins(
            @Param("numeroExpediente") String numeroExpediente,
            @Param("vigente") Boolean vigente,
            @Param("estadoAprobacion") Long estadoAprobacion,
            @Param("idContratista") Long idContratista ,
            Pageable pageable
    );
    
    
    @Transactional
    @Modifying
    @Query("UPDATE InformeRenovacionContrato informeRC SET " +        
           "informeRC.estadoAprobacionInforme.idListadoDetalle = :estado, " +
           "informeRC.feActualizacion = :fecha, " +
           "informeRC.ipActualizacion = :ip, " +
           "informeRC.usActualizacion = :usActualizacion " +
           "WHERE informeRC.idInformeRenovacion = :idInformeRenovacion")
    int actualizarEstadoEvaluacionPropuestPerfilPuestoPorId(
        @Param("idInformeRenovacion") Long idInformeRenovacion,
        @Param("estado") Long estado,
        @Param("fecha") LocalDateTime feActualizacion,
        @Param("ip") String ipActualizacion,
        @Param("usActualizacion") String usActualizacion
    );
}
