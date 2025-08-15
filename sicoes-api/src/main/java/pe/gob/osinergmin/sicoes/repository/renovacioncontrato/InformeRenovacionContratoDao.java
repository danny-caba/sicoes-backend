package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;


@Repository
public interface InformeRenovacionContratoDao extends JpaRepository<InformeRenovacionContrato, Long> {

   // Page<InformeRenovacionContrato> listaInformes(String numeroExpediente, String estado, String nombreContratista);

    @Query(
            value = "SELECT i FROM InformeRenovacionContrato i " +
                    "LEFT JOIN FETCH i.usuario u " +
                    "LEFT JOIN FETCH i.requerimiento r " +
                    //"WHERE (:estadoAprobacion IS NULL OR i.esAprobacionInforme = :estadoAprobacion) " +
                    "WHERE (:numeroExpediente IS NULL OR r.nuExpediente = :numeroExpediente) " +
                    "AND (:vigente IS NULL OR i.vigente = :vigente) " +
                    "ORDER BY i.usuCreacion DESC",
            countQuery = "SELECT COUNT(i) FROM InformeRenovacionContrato i " +
                    "LEFT JOIN i.requerimiento r " +
                  //  "WHERE (:estadoAprobacion IS NULL OR i.esAprobacionInforme = :estadoAprobacion) " +
                    "WHERE (:numeroExpediente IS NULL OR r.nuExpediente = :numeroExpediente) " +
                    "AND (:vigente IS NULL OR i.vigente = :vigente)"
    )
    Page<InformeRenovacionContrato> findByFiltrosWithJoins(
            @Param("numeroExpediente") String numeroExpediente,
           // @Param("estadoAprobacion") Integer estadoAprobacion,
            @Param("vigente") Boolean vigente,
            Pageable pageable
    );
    
}
