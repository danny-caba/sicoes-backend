package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;

import java.util.Date;
import java.util.Optional;

public interface RequerimientoInvitacionDao extends JpaRepository<RequerimientoInvitacion, Long> {

    @Query(value="select i from RequerimientoInvitacion i " +
            "where i.supervisora.idSupervisora = :idSupervisora " +
            "and (:idEstado is null or i.estado.idListadoDetalle = :idEstado) " +
            "and (:fechaInicio is null or i.fechaInvitacion >= :fechaInicio) " +
            "and (:fechaFin is null or i.fechaInvitacion <= :fechaFin) " +
            "order by i.fechaInvitacion desc ",
            countQuery = "select count(i) from RequerimientoInvitacion i " +
                    "where i.supervisora.idSupervisora = :idSupervisora " +
                    "and (:idEstado is null or i.estado.idListadoDetalle = :idEstado) " +
                    "and (:fechaInicio is null or i.fechaInvitacion >= :fechaInicio) " +
                    "and (:fechaFin is null or i.fechaInvitacion <= :fechaFin) ")
    Page<RequerimientoInvitacion> obtenerInvitaciones(Long idSupervisora,
                                                       Long idEstado,
                                                       Date fechaInicio,
                                                       Date fechaFin,
                                                       Pageable pageable);

    @Query(value="select i from RequerimientoInvitacion i " +
            "where i.requerimientoInvitacionUuid = :uuid " +
            "and i.flagActivo = 1")
    Optional<RequerimientoInvitacion> obtenerPorUuid(@Param("uuid") String uuid);

}
