package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoRequerimientoRenovacion;

@Repository
public interface HistorialEstadoRequerimientoRenovacionDao extends JpaRepository<HistorialEstadoRequerimientoRenovacion, Long> {

    @Query("SELECT h FROM HistorialEstadoRequerimientoRenovacion h WHERE h.esRegistro = '1' ORDER BY h.feFechaCambio DESC")
    List<HistorialEstadoRequerimientoRenovacion> listarActivos();

    @Query("SELECT h FROM HistorialEstadoRequerimientoRenovacion h " +
           "WHERE h.idRequerimiento = :idRequerimiento AND h.esRegistro = '1' " +
           "ORDER BY h.feFechaCambio DESC")
    List<HistorialEstadoRequerimientoRenovacion> listarPorRequerimiento(@Param("idRequerimiento") Long idRequerimiento);

    @Query("SELECT h FROM HistorialEstadoRequerimientoRenovacion h " +
           "WHERE h.idUsuario = :idUsuario AND h.esRegistro = '1' " +
           "ORDER BY h.feFechaCambio DESC")
    List<HistorialEstadoRequerimientoRenovacion> listarPorUsuario(@Param("idUsuario") Long idUsuario);

    @Query("SELECT h FROM HistorialEstadoRequerimientoRenovacion h " +
           "WHERE h.idRequerimiento = :idRequerimiento AND h.deEstadoNuevoLd = :estadoNuevo AND h.esRegistro = '1' " +
           "ORDER BY h.feFechaCambio DESC")
    List<HistorialEstadoRequerimientoRenovacion> listarPorRequerimientoYEstado(@Param("idRequerimiento") Long idRequerimiento, 
                                                                               @Param("estadoNuevo") Long estadoNuevo);
}