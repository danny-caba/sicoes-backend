package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.AdendaReemplazo;

@Repository
public interface AdendaReemplazoDao extends JpaRepository<AdendaReemplazo,Long> {

}
