package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoAprobacionCampo;

@Repository
public interface HistorialEstadoAprobacionCampoDao extends JpaRepository<HistorialEstadoAprobacionCampo, Long> {

    @Query("SELECT h FROM HistorialEstadoAprobacionCampo h WHERE h.esRegistro = '1' ORDER BY h.feFechaCambio DESC")
    List<HistorialEstadoAprobacionCampo> listarActivos();

    @Query("SELECT h FROM HistorialEstadoAprobacionCampo h " +
           "WHERE h.idReqAprobacion = :idReqAprobacion AND h.esRegistro = '1' " +
           "ORDER BY h.feFechaCambio DESC")
    List<HistorialEstadoAprobacionCampo> listarPorRequerimientoAprobacion(@Param("idReqAprobacion") Long idReqAprobacion);

    @Query("SELECT h FROM HistorialEstadoAprobacionCampo h " +
           "WHERE h.idUsuario = :idUsuario AND h.esRegistro = '1' " +
           "ORDER BY h.feFechaCambio DESC")
    List<HistorialEstadoAprobacionCampo> listarPorUsuario(@Param("idUsuario") Long idUsuario);

    @Query("SELECT h FROM HistorialEstadoAprobacionCampo h " +
           "WHERE h.idGrupoAprobadorLd = :idGrupoAprobador AND h.esRegistro = '1' " +
           "ORDER BY h.feFechaCambio DESC")
    List<HistorialEstadoAprobacionCampo> listarPorGrupoAprobador(@Param("idGrupoAprobador") Long idGrupoAprobador);
}