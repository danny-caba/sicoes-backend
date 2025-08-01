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
    + " left join fetch p.personaPropuesta pp"
    + " left join fetch p.personaBaja pb"
    + " left join fetch p.estadoReemplazo e"
    + " left join fetch p.estadoEvalDoc e1"
    + " left join fetch p.estadoRevisarEval e2"
    + " left join fetch p.estadoAprobacionInforme e3"
    + " left join fetch p.estadoAprobacionAdenda e4"
    + " left join fetch p.estadoEvalDocIniServ e5"
    + " where p.idSolicitud = :idSolicitud"
    + " order by p.idReemplazo",
    countQuery = "select count(p) from PersonalReemplazo p"
    + " left join p.personaPropuesta pp"
    + " left join p.personaBaja pb"
    + " left join p.estadoReemplazo e"
    + " left join p.estadoEvalDoc e1"
    + " left join p.estadoRevisarEval e2"
    + " left join p.estadoAprobacionInforme e3"
    + " left join p.estadoAprobacionAdenda e4"
    + " left join p.estadoEvalDocIniServ e5"
    + " where p.idSolicitud = :idSolicitud")
    Page<PersonalReemplazo> obtenerxIdSolicitud(Long idSolicitud, Pageable pageable);
}
