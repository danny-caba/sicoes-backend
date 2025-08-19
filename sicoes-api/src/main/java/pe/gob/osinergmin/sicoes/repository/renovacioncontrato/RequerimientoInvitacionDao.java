package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;

@Repository
public interface RequerimientoInvitacionDao extends JpaRepository<RequerimientoInvitacion, Long> {

}

