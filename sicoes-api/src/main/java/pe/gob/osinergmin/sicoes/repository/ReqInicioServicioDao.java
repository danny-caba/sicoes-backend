package pe.gob.osinergmin.sicoes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.ReqInicioServicio;

@Repository
public interface ReqInicioServicioDao extends JpaRepository<ReqInicioServicio, Long> {
    Optional<ReqInicioServicio> findBySolicitudPerfilIdAndTipoDocumento(Long solicitudPerfilId, String tipoDocumento);
}
