package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.osinergmin.sicoes.model.Invitacion;

public interface InvitacionDao extends JpaRepository<Invitacion, Long> {
}
