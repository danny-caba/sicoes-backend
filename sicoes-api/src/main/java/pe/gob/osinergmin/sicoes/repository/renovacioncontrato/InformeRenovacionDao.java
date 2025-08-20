package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoAprobacionCampo;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;

@Repository
public interface InformeRenovacionDao extends JpaRepository<InformeRenovacion, Long> {

    @Query(value = "select i from InformeRenovacion i "
            + "join fetch i.requerimientoRenovacion c "
            + "join fetch c.solicitudPerfil s "
            + "join fetch s.propuesta p "
            + "join fetch p.supervisora su "
            + "where (:nuExpediente is null or lower(s.numeroExpediente) like lower(concat('%', :nuExpediente, '%'))) "
            + "and (:contratista is null or lower(su.nombreRazonSocial) like lower(concat('%', :contratista, '%')))",

            countQuery = "select count(i) from InformeRenovacion i "
                    + "inner join i.requerimientoRenovacion c "
                    + "inner join c.solicitudPerfil s "
                    + "inner join s.propuesta p "
                    + "inner join p.supervisora su "
                    + "where (:nuExpediente is null or lower(s.numeroExpediente) like lower(concat('%', :nuExpediente, '%'))) "
                    + "and (:contratista is null or lower(su.nombreRazonSocial) like lower(concat('%', :contratista, '%')))"
    )
    Page<InformeRenovacion> findByNuExpedienteContratistaEstado(String nuExpediente, String contratista, Pageable pageable);



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

    @Query("SELECT i FROM InformeRenovacion i " +
            "LEFT JOIN i.requerimientoRenovacion req " +
            "LEFT JOIN req.solicitudPerfil sp " +
            "LEFT JOIN sp.supervisora s " +
            "WHERE i.esRegistro = '1' " +
            "AND (:estadoAprobacion IS NULL OR i.estadoAprobacionInforme.idListadoDetalle = :estadoAprobacion) " +
            "AND (:numeroExpediente IS NULL OR UPPER(req.nuExpediente) LIKE UPPER(CONCAT('%', :numeroExpediente, '%'))) " +
            "AND (:nombreItem IS NULL OR UPPER(req.noItem) LIKE UPPER(CONCAT('%', :nombreItem, '%'))) " +
            "AND (:rucSupervisora IS NULL OR s.nuRucSupervisora = :rucSupervisora) " +
            "ORDER BY i.feCreacion DESC")
    Page<InformeRenovacion> buscarInformesParaAprobar(@Param("estadoAprobacion") Long estadoAprobacion,
                                                      @Param("grupoAprobador") Long grupoAprobador,
                                                      @Param("numeroExpediente") String numeroExpediente,
                                                      @Param("nombreItem") String nombreItem,
                                                      @Param("rucSupervisora") String rucSupervisora,
                                                      Pageable pageable);

    @Query("SELECT h FROM HistorialEstadoAprobacionCampo h " +
            "LEFT JOIN RequerimientoAprobacion ra ON h.idReqAprobacion = ra.idReqAprobacion " +
            "WHERE h.esRegistro = '1' " +
            "AND ra.idInformeRenovacion = :idInformeRenovacion " +
            "ORDER BY h.feFechaCambio DESC")
    List<HistorialEstadoAprobacionCampo> buscarHistorialAprobaciones(@Param("idInformeRenovacion") Long idInformeRenovacion);
}