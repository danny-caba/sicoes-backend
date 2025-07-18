package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumento;

import java.util.Date;
import java.util.Optional;

@Repository
public interface RequerimientoDocumentoDao extends JpaRepository<RequerimientoDocumento, Long> {

    @Query(value = "SELECT rd FROM RequerimientoDocumento rd " +
            "LEFT JOIN FETCH rd.estado e " +
            "LEFT JOIN FETCH rd.requerimiento r " +
            "WHERE (:idEstado IS NULL OR e.idListadoDetalle = :idEstado) " +
            "AND (:fechaInicio IS NULL OR r.feRegistro >= :fechaInicio) " +
            "AND (:fechaFin IS NULL OR r.feRegistro <= :fechaFin)" +
            "ORDER BY r.nuExpediente ASC",
            countQuery = "SELECT COUNT(rd) FROM RequerimientoDocumento rd " +
                    "JOIN rd.estado e " +
                    "JOIN rd.requerimiento r " +
                    "WHERE (:idEstado IS NULL OR e.idListadoDetalle = :idEstado) " +
                    "AND (:fechaInicio IS NULL OR r.feRegistro >= :fechaInicio) " +
                    "AND (:fechaFin IS NULL OR r.feRegistro <= :fechaFin)")
    Page<RequerimientoDocumento> listarRequerimientosDocumentos(Long idEstado, Date fechaInicio, Date fechaFin, Pageable pageable);

    @Query("SELECT rd FROM RequerimientoDocumento rd " +
            "WHERE rd.requerimientoDocumentoUuid = :uuid")
    Optional<RequerimientoDocumento> obtenerPorUuid(@Param("uuid") String uuid);
}
