package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.DocumentoReemplazo;

import java.util.Collection;
import java.util.List;

@Repository
public interface DocumentoReemDao extends JpaRepository<DocumentoReemplazo,Long> {

    @Query("select d from DocumentoReemplazo d "
            + "where d.idDocumento=:idDocumento")
    DocumentoReemplazo obtener(Long idDocumento);

    @Query(value = "select d from DocumentoReemplazo d "
            + "left join fetch d.seccion s "
            + "left join fetch d.tipoDocumento t "
            + "where d.idReemplazoPersonal=:idReemplazoPersonal",
            countQuery = "select count(d) from DocumentoReemplazo d "
            + "left join d.seccion s "
            + "left join d.tipoDocumento t "
            + "where d.idReemplazoPersonal=:idReemplazoPersonal")
    Page<DocumentoReemplazo> buscar(Long idReemplazoPersonal, Pageable pageable);

    boolean existsByIdReemplazoPersonal(Long idReemplazoPersonal);

    boolean existsByIdReemplazoPersonalAndSeccion_IdListadoDetalle(Long idReemplazoPersonal, Long idSeccion);

    @Query("select d.idDocumento from DocumentoReemplazo d "
            + "where d.idReemplazoPersonal = :idReemplazoPersonal "
            + "and d.seccion.idListadoDetalle = :idSeccion")
    List<Long> findIdsByReemplazoAndSeccion(Long idReemplazoPersonal, Long idSeccion);

    @Modifying(clearAutomatically = true)
    @Query("delete from DocumentoReemplazo d where d.idDocumento in :ids")
    int deleteByIdIn(Collection<Long> ids);

}
