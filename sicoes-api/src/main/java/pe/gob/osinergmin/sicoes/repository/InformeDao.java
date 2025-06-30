package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gob.osinergmin.sicoes.model.RequerimientoInforme;

public interface InformeDao extends JpaRepository<RequerimientoInforme, Long> {
}
