package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;

@Repository
public interface RequerimientoAprobacionDao extends JpaRepository<RequerimientoAprobacion, Long> {

    @Query("SELECT r FROM RequerimientoAprobacion r WHERE r.idEstadoLd = '1' ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> listarActivos();

    @Query("SELECT r FROM RequerimientoAprobacion r WHERE r.idReqAprobacion = :id AND r.idEstadoLd = '1'")
    RequerimientoAprobacion obtenerPorId(@Param("id") Long id);

    @Query("SELECT r FROM RequerimientoAprobacion r " +
           "WHERE r.idEstadoLd = '1' " +
           "AND r.informeRenovacion.idInformeRenovacion = :idInforme " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> listarPorInforme(@Param("idInforme") Long idInforme);

    @Query("SELECT r FROM RequerimientoAprobacion r " +
           "WHERE r.idEstadoLd = '1' " +
           "AND r.idEstadoLd = :estadoId " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> listarPorEstado(@Param("estadoId") Long estadoId);

    @Query("SELECT r FROM RequerimientoAprobacion r " +
           "WHERE  r.idEstadoLd = '1' " +
           "AND r.idGrupoAprobadorLd = :idUsuario " +
           "ORDER BY r.fecCreacion DESC")
    List<RequerimientoAprobacion> listarPorAprobador(@Param("idUsuario") Long idUsuario);
}