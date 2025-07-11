package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.osinergmin.sicoes.model.RequerimientoAprobacion;

import java.util.Optional;

public interface RequerimientoAprobacionDao extends JpaRepository<RequerimientoAprobacion, Long> {

    @Query("SELECT r FROM RequerimientoAprobacion r " +
            "WHERE r.requerimiento.idRequerimiento = :idRequerimiento " +
            "and r.grupo.idListadoDetalle = :idListadoDetalle " +
            "and (:estadoAprobacion IS NULL OR r.estado.codigo = :estadoAprobacion) ")
    Optional<RequerimientoAprobacion> obtenerPorRequerimientoYGrupo(@Param("idRequerimiento") Long idRequerimiento,
                                                                    @Param("idListadoDetalle") Long idListadoDetalle,
                                                                    @Param("estadoAprobacion") String estadoAprobacion);
    @Query(value = "SELECT r FROM RequerimientoAprobacion r " +
            "WHERE r.requerimiento.requerimientoUuid = :uuid " +
            "order by r.grupo.orden",
            countQuery = "SELECT r FROM RequerimientoAprobacion r " +
                    "WHERE r.requerimiento.requerimientoUuid = :uuid ")
    Page<RequerimientoAprobacion> obtenerAprobaciones(@Param("uuid") String uuid,
                                                      Pageable pageable);
}
