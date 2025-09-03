package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.ProcesoConsulta;

import java.util.List;

@Repository
public interface ProcesoConsultaDao extends JpaRepository<ProcesoConsulta, Long> {

    @Query("select c from ProcesoConsulta c "
            + "left join fetch c.proceso p "
            + "left join fetch c.estado e "
            + "left join fetch c.seccion s "
            + "where c.procesoConsultaUuid=:procesoConsultaUuid ")
    ProcesoConsulta obtener(String procesoConsultaUuid);

    @Query(value = "select c from ProcesoConsulta c "
            + "left join fetch c.proceso p "
            + "left join fetch c.estado e "
            + "left join fetch c.seccion s "
            + "where (:idProceso = p.idProceso) "
            + "and (:idUsuario = c.usuCreacion) "
            + "order by c.fecCreacion ",
            countQuery = "select count(c) from ProcesoConsulta c "
                    + "left join c.proceso p "
                    + "left join c.estado e "
                    + "left join c.seccion s "
                    + "where (:idProceso = p.idProceso) "
                    + "and (:idUsuario = c.usuCreacion) "
                    + "order by c.idProcesoConsulta")
    Page<ProcesoConsulta> buscarConsultasPorUsuario(Long idProceso, String idUsuario, Pageable pageable);

    @Modifying
    @Query("delete from ProcesoConsulta c where c.procesoConsultaUuid=:procesoConsultaUuid ")
    void eliminarConsulta(String procesoConsultaUuid);

    @Query("select su.codigoRuc, " +
            "su.nombreRazonSocial, " +
            "s.valor, c.deNumeral, " +
            "c.deLiteral, c.dePagina, " +
            "c.deConsulta, c.deArticuloNorma, " +
            "FUNCTION('TO_CHAR', c.fecActualizacion, 'YYYY-MM-DD HH24:MI:SS') from ProcesoConsulta c "
            + "left join c.proceso p "
            + "left join c.estado e "
            + "left join c.seccion s "
            + "left join c.supervisora su "
            + "where (:idProceso = p.idProceso) "
            + "and (:estado = e.codigo) "
            + "order by c.idProcesoConsulta")
    List<Object[]> listarConsultasEnviado(Long idProceso, String estado);

    @Query("select c from ProcesoConsulta c "
            + "left join fetch c.proceso p "
            + "left join fetch c.estado e "
            + "left join fetch c.seccion s "
            + "where :idProceso = p.idProceso "
            + "and :idUsuario = c.usuCreacion "
            + "order by c.idProcesoConsulta")
    List<ProcesoConsulta> listarConsultasNoEnviado(Long idProceso, String idUsuario);

}
