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

import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.Supervisora;

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
    Page<Requerimiento> listarRequerimientos(
            @Param("idDivision") Long idDivision,
            @Param("idPerfil") Long idPerfil,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin,
            @Param("idSupervisora") Long idSupervisora,
            @Param("idEstado") Long idEstado,
            @Param("divisionIds") List<Long> divisionIds,
            @Param("pageable") Pageable pageable);

    @Query("SELECT r FROM Requerimiento r WHERE r.idRequerimiento = :id")
    Optional<Requerimiento> obtener(@Param("id") Long id);

    @Query("select r.idRequerimiento from Requerimiento r where r.requerimientoUuid=:requerimientoUuid")
    Long obtenerId(String requerimientoUuid);

    @Query("SELECT r FROM Requerimiento r WHERE r.requerimientoUuid = :uuid")
    Optional<Requerimiento> obtenerPorUuid(@Param("uuid") String uuid);

    Optional<Requerimiento> findByRequerimientoUuid(String requerimientoUuid);
}
