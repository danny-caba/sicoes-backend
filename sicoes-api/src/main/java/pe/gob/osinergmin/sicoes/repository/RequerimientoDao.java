package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.Requerimiento;

@Repository
public interface RequerimientoDao extends JpaRepository<Requerimiento, Long> {
}
