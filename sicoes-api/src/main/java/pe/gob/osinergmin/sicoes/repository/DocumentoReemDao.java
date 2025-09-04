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
import java.util.Optional;

@Repository
public interface DocumentoReemDao extends JpaRepository<DocumentoReemplazo,Long> {

    @Query("select d from DocumentoReemplazo d "+
           "left join fetch d.seccion " +
           "left join fetch d.tipoDocumento " +
           "left join fetch d.evaluacion " +
           "where d.idDocumento=:idDocumento")
    DocumentoReemplazo obtenerPorIdDocumento(Long idDocumento);

    @Query("select d from DocumentoReemplazo d "+
            "left join fetch d.seccion " +
            "left join fetch d.tipoDocumento " +
            "left join fetch d.evaluacion " +
            "where d.idReemplazoPersonal=:idReemplazo "+
            "and d.seccion.idListadoDetalle=:idSeccion")
    List<DocumentoReemplazo> obtenerPorIdReemplazoSeccion(Long idReemplazo, Long idSeccion);

    @Query("select d.idDocumento from DocumentoReemplazo d " +
            "where d.idReemplazoPersonal = :id")
    Page<Long> findDocumentIds(Long id, Pageable pageable);

    @Query("select d.idDocumento from DocumentoReemplazo d " +
            "where d.idReemplazoPersonal = :id "+
            "and d.seccion.idListadoDetalle=:idSeccion")
    Page<Long> findDocumentSeccionIds(Long id, Long idSeccion,Pageable pageable);

    @Query("select distinct d from DocumentoReemplazo d " +
            "left join fetch d.seccion " +
            "left join fetch d.tipoDocumento " +
            "left join fetch d.evaluacion " +
            "where d.idDocumento in :ids")
    List<DocumentoReemplazo> findDocumentosFull(List<Long> ids);

    boolean existsByIdReemplazoPersonal(Long idReemplazoPersonal);

    boolean existsByIdReemplazoPersonalAndSeccion_IdListadoDetalle(Long idReemplazoPersonal, Long idSeccion);

    @Query("select d.idDocumento from DocumentoReemplazo d "
            + "where d.idReemplazoPersonal = :idReemplazoPersonal "
            + "and d.seccion.idListadoDetalle = :idSeccion")
    List<Long> findIdsByReemplazoAndSeccion(Long idReemplazoPersonal, Long idSeccion);

    @Modifying(clearAutomatically = true)
    @Query("delete from DocumentoReemplazo d where d.idDocumento in :ids")
    int deleteByIdIn(Collection<Long> ids);

    @Query("select distinct d from DocumentoReemplazo d "+
            "left join fetch d.seccion " +
            "left join fetch d.tipoDocumento " +
            "left join fetch d.evaluacion " +
            "where d.idReemplazoPersonal=:idReemplazo ")
    List<DocumentoReemplazo> findByIdReemplazoPersonal(Long idReemplazo);

    @Query("select distinct d from DocumentoReemplazo d "+
       "left join fetch d.seccion s "+
       "left join fetch d.tipoDocumento td "+
       "left join fetch d.evaluacion e "+
       "where d.idReemplazoPersonal = :idReemplazo "+
       "and s.idListadoDetalle in :idsSeccion ")
    List<DocumentoReemplazo> obtenerPorIdReemplazoSecciones(
            Long idReemplazo, Collection<Long> idsSeccion);

}
