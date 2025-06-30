package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.Supervisora;

@Repository
public interface RequerimientoDao extends JpaRepository<Requerimiento, Long> {

    @Query(value = "SELECT r FROM Requerimiento r " +
            "LEFT JOIN FETCH r.division " +
            "LEFT JOIN FETCH r.perfil " +
            "LEFT JOIN FETCH r.estado " +
            "LEFT JOIN r.reqInvitaciones i " +
            "WHERE (:division IS NULL OR r.division = :division) " +
            "AND (:perfil IS NULL OR r.perfil = :perfil) " +
            "AND (:supervisora IS NULL OR i.supervisora = :supervisora) " +
            "AND (:fechaInicio IS NULL OR r.feRegistro >= :fechaInicio) " +
            "AND (:fechaFin IS NULL OR r.feRegistro <= :fechaFin) " +
            "AND (:estadoAprobacion IS NULL OR r.estado = :estadoAprobacion) " +
            "ORDER BY r.nuExpediente ASC",
            countQuery = "SELECT COUNT(r) FROM Requerimiento r " +
                    "LEFT JOIN r.reqInvitaciones i " +
                    "WHERE (:division IS NULL OR r.division = :division) " +
                    "AND (:perfil IS NULL OR r.perfil = :perfil) " +
                    "AND (:supervisora IS NULL OR i.supervisora = :supervisora) " +
                    "AND (:fechaInicio IS NULL or r.feRegistro>=:fechaInicio) " +
                    "AND (:fechaFin IS NULL or r.feRegistro<=:fechaFin) " +
                    "AND (:estadoAprobacion IS NULL OR r.estado = :estadoAprobacion)")
    Page<Requerimiento> listarRequerimientos(@Param("division") Division division,
                                             @Param("perfil") ListadoDetalle perfil,
                                             @Param("fechaInicio") Date fechaInicio,
                                             @Param("fechaFin") Date fechaFin,
                                             @Param("supervisora") Supervisora supervisora,
                                             @Param("estadoAprobacion") ListadoDetalle estadoAprobacion,
                                             Pageable pageable);

    @Query("SELECT r FROM Requerimiento r WHERE r.id = :id")
    Optional<Requerimiento> buscarPorId(@Param("id") Long id);

}
