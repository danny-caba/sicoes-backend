package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoInvitacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoRequerimientoRenovacion;

import java.util.List;

@Repository
public interface HistorialEstadoInvitacionDao extends JpaRepository<HistorialEstadoInvitacion, Long> {
}