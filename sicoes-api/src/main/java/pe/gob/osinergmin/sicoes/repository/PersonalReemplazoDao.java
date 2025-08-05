package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;

import java.util.Optional;

@Repository
public interface PersonalReemplazoDao extends JpaRepository<PersonalReemplazo, Long> {

    @Query(value = "select p from PersonalReemplazo p"
    + " left join fetch p.personaPropuesta pp"
    + " left join fetch p.perfil pe"
    + " left join fetch p.personaBaja pb"
    + " left join fetch p.perfilBaja peb"
    + " left join fetch p.estadoReemplazo e"
    + " left join fetch p.estadoEvalDoc e1"
    + " left join fetch p.estadoRevisarEval e2"
    + " left join fetch p.estadoAprobacionInforme e3"
    + " left join fetch p.estadoAprobacionAdenda e4"
    + " left join fetch p.estadoEvalDocIniServ e5"
    + " where p.idSolicitud = :idSolicitud"
    + " and ( :idAprobacion is null or e.idListadoDetalle = :idAprobacion )"
    + " and ( :idEvalDocIniServ is null or e5.idListadoDetalle = :idEvalDocIniServ)"
    + " order by p.idReemplazo",
    countQuery = "select count(p) from PersonalReemplazo p"
    + " left join p.personaPropuesta pp"
    + " left join p.perfil pe"
    + " left join p.personaBaja pb"
    + " left join p.perfilBaja peb"
    + " left join p.estadoReemplazo e"
    + " left join p.estadoEvalDoc e1"
    + " left join p.estadoRevisarEval e2"
    + " left join p.estadoAprobacionInforme e3"
    + " left join p.estadoAprobacionAdenda e4"
    + " left join p.estadoEvalDocIniServ e5"
    + " where p.idSolicitud = :idSolicitud"
    + " and ( :idAprobacion is null or e.idListadoDetalle = :idAprobacion)"
    + " and ( :idEvalDocIniServ is null or e5.idListadoDetalle = :idEvalDocIniServ)"
    )
    Page<PersonalReemplazo> obtenerxIdSolicitud(Long idSolicitud, Long idAprobacion,
                                                Long idEvalDocIniServ, Pageable pageable);

    @Query("select p from PersonalReemplazo p"
            + " left join fetch p.personaPropuesta pp"
            + " left join fetch p.personaBaja pb"
            + " left join fetch p.estadoReemplazo e"
            + " left join fetch p.estadoEvalDoc e1"
            + " left join fetch p.estadoRevisarEval e2"
            + " left join fetch p.estadoAprobacionInforme e3"
            + " left join fetch p.estadoAprobacionAdenda e4"
            + " left join fetch p.estadoEvalDocIniServ e5"
            + " where p.idReemplazo = :idReemplazo")
    Optional<PersonalReemplazo> obtenerxIdReemplazo(@Param("idReemplazo") Long idReemplazo);
}
