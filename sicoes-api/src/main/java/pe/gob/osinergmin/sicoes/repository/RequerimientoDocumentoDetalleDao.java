package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumentoDetalle;

import java.util.List;

@Repository
public interface RequerimientoDocumentoDetalleDao extends JpaRepository<RequerimientoDocumentoDetalle, Long> {

    @Query("SELECT rdd FROM RequerimientoDocumentoDetalle rdd " +
            "JOIN rdd.requerimientoDocumento rd " +
            "WHERE rd.requerimientoDocumentoUuid = :uuid")
    List<RequerimientoDocumentoDetalle> listarPorUuid(@Param("uuid") String uuid);

    @Query("SELECT rdd FROM RequerimientoDocumentoDetalle rdd " +
            "WHERE rdd.requerimientoDocumentoDetalleUuid = :uuid")
    RequerimientoDocumentoDetalle buscarPorUuid(String uuid);

    @Query("SELECT rd.requerimientoDocumentoUuid FROM RequerimientoDocumentoDetalle rdd " +
            "JOIN rdd.requerimientoDocumento rd " +
            "WHERE rdd.requerimientoDocumentoDetalleUuid = :detalleUuid")
    String obtenerRequerimientoDocumentoUuidPorDetalleUuid(@Param("detalleUuid") String detalleUuid);
}
