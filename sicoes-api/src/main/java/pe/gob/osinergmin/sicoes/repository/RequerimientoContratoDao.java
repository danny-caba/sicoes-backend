package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.osinergmin.sicoes.model.RequerimientoContrato;

import java.util.Optional;

public interface RequerimientoContratoDao extends JpaRepository<RequerimientoContrato, Long> {
    @Query(value = "SELECT DISTINCT c FROM RequerimientoContrato c " +
            "LEFT JOIN FETCH c.requerimiento r " +
            "LEFT JOIN FETCH r.division d " +
            "LEFT JOIN FETCH r.perfil p " +
            "LEFT JOIN FETCH r.supervisora s " +
            "WHERE (:idDivision IS NULL OR d.idDivision = :idDivision) " +
            "AND (:idPerfil IS NULL OR p.idListadoDetalle = :idPerfil) " +
            "AND (:idSupervisora IS NULL OR s.idSupervisora = :idSupervisora) " +
            "AND (:numeroContrato IS NULL OR c.numeroContrato = :numeroContrato) " +
            "ORDER BY r.nuExpediente ASC",
            countQuery = "SELECT COUNT(DISTINCT c) FROM RequerimientoContrato c " +
                    "LEFT JOIN c.requerimiento r " +
                    "LEFT JOIN r.division d " +
                    "LEFT JOIN r.perfil p " +
                    "LEFT JOIN r.supervisora s " +
                    "WHERE (:idDivision IS NULL OR d.idDivision = :idDivision) " +
                    "AND (:idPerfil IS NULL OR p.idListadoDetalle = :idPerfil) " +
                    "AND (:idSupervisora IS NULL OR s.idSupervisora = :idSupervisora) " +
                    "AND (:numeroContrato IS NULL OR c.numeroContrato = :numeroContrato) ")
    Page<RequerimientoContrato> listarRequerimientoContrato(@Param("idDivision") Long idDivision,
                                                            @Param("idPerfil") Long idPerfil,
                                                            @Param("idSupervisora") Long idSupervisora,
                                                            @Param("numeroContrato") String numeroContrato,
                                                            Pageable pageable);

    @Query("SELECT c FROM RequerimientoContrato c WHERE c.requerimientoContratoUuid = :uuid")
    Optional<RequerimientoContrato> obtenerPorUuid(@Param("uuid") String uuid);
}
