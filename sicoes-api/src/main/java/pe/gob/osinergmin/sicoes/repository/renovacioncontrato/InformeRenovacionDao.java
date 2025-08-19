package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;

@Repository
public interface InformeRenovacionDao extends JpaRepository<InformeRenovacion, Long> {

    @Query("SELECT i FROM InformeRenovacion i WHERE i.esRegistro = '1' ORDER BY i.feCreacion DESC")
    List<InformeRenovacion> listarActivos();

    @Query("SELECT i FROM InformeRenovacion i WHERE i.idInformeRenovacion = :id AND i.esRegistro = '1'")
    InformeRenovacion obtenerPorId(@Param("id") Long id);

    @Query("SELECT i FROM InformeRenovacion i " +
           "WHERE i.esRegistro = '1' " +
           "AND i.requerimientoRenovacion.idReqRenovacion = :idRequerimiento")
    List<InformeRenovacion> listarPorRequerimiento(@Param("idRequerimiento") Long idRequerimiento);

    @Query("SELECT i FROM InformeRenovacion i " +
           "WHERE i.esRegistro = '1' " +
           "AND i.usuario.idUsuario = :idUsuario " +
           "ORDER BY i.feCreacion DESC")
    List<InformeRenovacion> listarPorUsuario(@Param("idUsuario") Long idUsuario);

    @Query("SELECT i FROM InformeRenovacion i " +
           "WHERE i.esRegistro = '1' " +
           "AND i.estadoAprobacionInforme.idListadoDetalle = :estadoId " +
           "ORDER BY i.feCreacion DESC")
    List<InformeRenovacion> listarPorEstadoAprobacion(@Param("estadoId") Long estadoId);

    @Query("SELECT i FROM InformeRenovacion i " +
           "WHERE i.esRegistro = '1' " +
           "AND i.esVigente = 1 " +
           "AND i.esCompletado = '1' " +
           "ORDER BY i.feCreacion DESC")
    List<InformeRenovacion> listarVigentesCompletados();
}