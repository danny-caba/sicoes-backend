package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.*;

@Repository
public interface RequerimientoDao extends JpaRepository<Requerimiento, Long> {

    @Query(value = "SELECT DISTINCT r FROM Requerimiento r " +
            "LEFT JOIN FETCH r.division d " +
            "LEFT JOIN FETCH r.perfil p " +
            "LEFT JOIN FETCH r.estado e " +
            "LEFT JOIN FETCH r.supervisora s " +
            "WHERE (:idDivision IS NULL OR d.idDivision = :idDivision) " +
            "AND (:idPerfil IS NULL OR p.idListadoDetalle = :idPerfil) " +
            "AND (:idSupervisora IS NULL OR s.idSupervisora = :idSupervisora) " +
            "AND (:idEstado IS NULL OR e.idListadoDetalle = :idEstado) " +
            "AND d.idDivision IN :divisionIds " +
            "AND (:fechaInicio IS NULL OR r.feRegistro >= :fechaInicio) " +
            "AND (:fechaFin IS NULL OR r.feRegistro <= :fechaFin) " +
            "ORDER BY r.nuExpediente ASC",
            countQuery = "SELECT COUNT(DISTINCT r) FROM Requerimiento r " +
                    "LEFT JOIN r.division d " +
                    "LEFT JOIN r.perfil p " +
                    "LEFT JOIN r.estado e " +
                    "LEFT JOIN r.supervisora s " +
                    "WHERE (:idDivision IS NULL OR d.idDivision = :idDivision) " +
                    "AND (:idPerfil IS NULL OR p.idListadoDetalle = :idPerfil) " +
                    "AND (:idSupervisora IS NULL OR s.idSupervisora = :idSupervisora) " +
                    "AND (:idEstado IS NULL OR e.idListadoDetalle = :idEstado) " +
                    "AND d.idDivision IN :divisionIds " +
                    "AND (:fechaInicio IS NULL OR r.feRegistro >= :fechaInicio) " +
                    "AND (:fechaFin IS NULL OR r.feRegistro <= :fechaFin) ")
    Page<Requerimiento> listarRequerimientos(Long idDivision, Long idPerfil, Date fechaInicio, Date fechaFin,
                                             Long idSupervisora,
                                             Long idEstado,
                                             List<Long> divisionIds,
                                             Pageable pageable);

    @Query("SELECT r FROM Requerimiento r WHERE r.idRequerimiento = :id")
    Optional<Requerimiento> obtener(@Param("id") Long id);

    @Query("select r from Requerimiento r where r.idRequerimiento=:idRequerimiento")
    public Requerimiento obtenerRequerimiento(Long idRequerimiento);

    @Query("select r.idRequerimiento from Requerimiento r where r.requerimientoUuid=:requerimientoUuid")
    public Long obtenerId(String requerimientoUuid);

    Requerimiento findByRequerimientoUuid(String requerimientoUuid);
}
