package pe.gob.osinergmin.sicoes.repository;

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
    public DocumentoReemplazo obtener(Long idDocumento);

    @Query("select d from DocumentoReemplazo d "
            + "where d.idReemplazoPersonal=:idReemplazoPersonal")
    public List<DocumentoReemplazo> buscar(Long idReemplazoPersonal);

    boolean existsByIdReemplazoPersonal(Long idReemplazoPersonal);

    boolean existsByIdReemplazoPersonalAndIdSeccion(Long idReemplazoPersonal, Long idSeccion);

    @Query("select d.idDocumento from DocumentoReemplazo d "
            + "where d.idReemplazoPersonal = :idReemplazoPersonal "
            + "and d.idSeccion = :idSeccion")
    List<Long> findIdsByReemplazoAndSeccion(Long idReemplazoPersonal, Long idSeccion);

    @Modifying(clearAutomatically = true)
    @Query("delete from DocumentoReemplazo d where d.idDocumento in :ids")
    int deleteByIdIn(Collection<Long> ids);

}
