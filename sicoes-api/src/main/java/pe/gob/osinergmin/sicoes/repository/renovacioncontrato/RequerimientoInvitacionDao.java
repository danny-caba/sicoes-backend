package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;

@Repository
public interface RequerimientoInvitacionDao extends JpaRepository<RequerimientoInvitacion, Long> {

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.flActivo = '1' ORDER BY r.fecCreacion DESC")
    List<RequerimientoInvitacion> listarActivos();

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.idReqInvitacion = :id AND r.flActivo = '1'")
    RequerimientoInvitacion obtenerPorId(@Param("id") Long id);

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.idRequerimiento = :idRequerimiento AND r.flActivo = '1'")
    List<RequerimientoInvitacion> listarPorRequerimiento(@Param("idRequerimiento") Long idRequerimiento);

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.idSupervisora = :idSupervisora AND r.flActivo = '1'")
    List<RequerimientoInvitacion> listarPorSupervisora(@Param("idSupervisora") Long idSupervisora);

    @Query("SELECT r FROM RequerimientoInvitacion r WHERE r.idEstadoLd = :estadoId AND r.flActivo = '1'")
    List<RequerimientoInvitacion> listarPorEstado(@Param("estadoId") Long estadoId);
}