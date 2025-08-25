package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.Aprobacion;

import java.util.Optional;

@Repository
public interface AprobacionDao extends JpaRepository<Aprobacion, Long> {

    @Query( "select ap from Aprobacion ap "
            + "left join fetch ap.remplazoPersonal rp "
            + "where rp.idReemplazo=:idReemplazo "
            + "and rownum <=1 "
            + "order by ap.idAprobacion desc")
    Optional<Aprobacion> findByRemplazoPersonal(Long idReemplazo);


}

