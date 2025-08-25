package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;

import java.util.List;

@Repository
public interface RequerimientoRenovacionDao extends JpaRepository<RequerimientoRenovacion, Long> {

    @Query("SELECT r FROM RequerimientoRenovacion r " +
            "WHERE r.esRegistro = '1' " +
            "AND (:numeroExpediente IS NULL OR r.nuExpediente LIKE %:numeroExpediente%) " +
            "AND (:tipoSector IS NULL OR r.tiSector LIKE %:tipoSector%) " +
            "AND (:tipoSubSector IS NULL OR r.tiSubSector LIKE %:tipoSubSector%) " +
            "ORDER BY r.fecCreacion DESC")
    Page<RequerimientoRenovacion> findByNuExpedienteContains(
            @Param("numeroExpediente") String numeroExpediente,
            @Param("tipoSector") String tipoSector,
            @Param("tipoSubSector") String tipoSubSector,
            Pageable pageable);


    @Query("SELECT r FROM RequerimientoRenovacion r WHERE r.esRegistro = '1' ORDER BY r.fecCreacion DESC")
    List<RequerimientoRenovacion> listarActivos();

    @Query("SELECT r FROM RequerimientoRenovacion r WHERE r.idReqRenovacion = :id AND r.esRegistro = '1'")
    RequerimientoRenovacion obtenerPorId(@Param("id") Long id);

    @Query("SELECT r FROM RequerimientoRenovacion r " +
            "WHERE r.esRegistro = '1' " +
            "AND (:numeroExpediente IS NULL OR r.nuExpediente LIKE %:numeroExpediente%) " +
            "AND (:tipoSector IS NULL OR r.tiSector LIKE %:tipoSector%) " +
            "AND (:tipoSubSector IS NULL OR r.tiSubSector LIKE %:tipoSubSector%) " +
            "AND (:nombreItem IS NULL OR r.noItem LIKE %:nombreItem%) " +
            "AND (:estadoRequerimiento IS NULL OR r.estadoReqRenovacion.idListadoDetalle = :estadoRequerimiento) " +
            "ORDER BY r.fecCreacion DESC")
    Page<RequerimientoRenovacion> buscarSolicitudesRenovacion(
            @Param("numeroExpediente") String numeroExpediente,
            @Param("tipoSector") String tipoSector,
            @Param("tipoSubSector") String tipoSubSector,
            @Param("nombreItem") String nombreItem,
            @Param("estadoRequerimiento") Integer estadoRequerimiento,
            Pageable pageable);

    @Query("SELECT r FROM RequerimientoRenovacion r " +
            "WHERE r.esRegistro = '1' " +
            "AND r.feRegistro BETWEEN TO_DATE(:fechaDesde, 'DD/MM/YYYY') AND TO_DATE(:fechaHasta, 'DD/MM/YYYY') " +
            "ORDER BY r.fecCreacion DESC")
    List<RequerimientoRenovacion> buscarPorRangoFechas(
            @Param("fechaDesde") String fechaDesde,
            @Param("fechaHasta") String fechaHasta);

    @Query("SELECT r FROM RequerimientoRenovacion r " +
            "WHERE r.esRegistro = '1' " +
            "AND r.idUsuario = :idUsuario " +
            "ORDER BY r.fecCreacion DESC")
    List<RequerimientoRenovacion> listarPorUsuario(@Param("idUsuario") Long idUsuario);

    @Query("SELECT r FROM RequerimientoRenovacion r " +
            "WHERE r.esRegistro = '1' " +
            "AND r.estadoReqRenovacion.idListadoDetalle = :estadoId " +
            "ORDER BY r.fecCreacion DESC")
    List<RequerimientoRenovacion> listarPorEstado(@Param("estadoId") Long estadoId);
}

