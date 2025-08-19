package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.PlazoConfirmacion;

@Repository
public interface PlazoConfirmacionDao extends JpaRepository<PlazoConfirmacion, Long> {
    
    @Query("SELECT p FROM PlazoConfirmacion p WHERE p.idPlazoConfirmacion = :idPlazoConfirmacion")
    PlazoConfirmacion obtener(@Param("idPlazoConfirmacion") Long idPlazoConfirmacion);
    
    @Query("SELECT p FROM PlazoConfirmacion p WHERE (:estado IS NULL OR p.esRegistro = :estado) ORDER BY p.fecCreacion DESC")
    List<PlazoConfirmacion> buscarPorEstado(@Param("estado") String estado);
}
