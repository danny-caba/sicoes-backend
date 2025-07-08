package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;

@Repository
public interface PersonalReemplazoDao extends JpaRepository<PersonalReemplazo, Long> {

    @Query(value = "select p from PersonalReemplazo p"
    + " where p.idSolicitud = :idSolicitud"
    + " order by p.idReemplazo",
    countQuery = "select count(p) from PersonalReemplazo p"
    + " where p.idSolicitud = :idSolicitud"
    + " order by p.idReemplazo")
    Page<PersonalReemplazo> obtenerxIdSolicitud(Long idSolicitud, Pageable pageable);
}
