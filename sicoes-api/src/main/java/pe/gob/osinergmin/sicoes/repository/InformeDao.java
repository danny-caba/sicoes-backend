package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.osinergmin.sicoes.model.Informe;

public interface InformeDao extends JpaRepository<Informe, Long> {
}
