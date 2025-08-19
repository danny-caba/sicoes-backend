package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.PlazoConfirmacion;

@Repository
public interface PlazoConfirmacionDao extends JpaRepository<PlazoConfirmacion, Long> {

    @Query("SELECT p FROM PlazoConfirmacion p WHERE p.esRegistro = '1' ORDER BY p.feCreacion DESC")
    List<PlazoConfirmacion> listarActivos();

    @Query("SELECT p FROM PlazoConfirmacion p WHERE p.esRegistro = '1' ORDER BY p.feCreacion DESC")
    Page<PlazoConfirmacion> listarActivosPaginado(Pageable pageable);

    @Query("SELECT p FROM PlazoConfirmacion p WHERE p.idPlazoConfirmacion = :id AND p.esRegistro = '1'")
    PlazoConfirmacion obtenerPorId(@Param("id") Long id);

    @Query("SELECT p FROM PlazoConfirmacion p WHERE p.inTipoDia = :tipoDia AND p.esRegistro = '1' ORDER BY p.feCreacion DESC")
    List<PlazoConfirmacion> listarPorTipoDia(@Param("tipoDia") Integer tipoDia);

    @Query("SELECT p FROM PlazoConfirmacion p WHERE p.nuDias = :dias AND p.esRegistro = '1' ORDER BY p.feCreacion DESC")
    List<PlazoConfirmacion> listarPorDias(@Param("dias") Integer dias);

    @Query("SELECT p FROM PlazoConfirmacion p WHERE p.feBase LIKE :feBase% AND p.esRegistro = '1' ORDER BY p.feCreacion DESC")
    List<PlazoConfirmacion> listarPorFechaBase(@Param("feBase") String feBase);
}
