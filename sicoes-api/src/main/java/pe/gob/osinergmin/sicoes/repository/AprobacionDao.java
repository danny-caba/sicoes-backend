package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.Aprobacion;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;

import java.util.Optional;

@Repository
public interface AprobacionDao extends JpaRepository<Aprobacion, Long> {

    Optional<Aprobacion> findByRemplazoPersonal(PersonalReemplazo personalReemplazo);


}

