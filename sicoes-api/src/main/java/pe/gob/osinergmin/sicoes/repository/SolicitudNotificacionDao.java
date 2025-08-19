package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.SolicitudNotificacion;

@Repository
public interface SolicitudNotificacionDao extends JpaRepository<SolicitudNotificacion, Long> {
	
	@Query("select s from SolicitudNotificacion s "
			+ "left join fetch s.tipo t "
			+ "left join fetch s.estado e "
			+ "where s.idSolNotificacion=:idSolNotificacion")
	public SolicitudNotificacion obtener(Long idSolNotificacion);
	
	@Query(value="select s from SolicitudNotificacion s "
			+ "left join fetch s.tipo t "
			+ "left join fetch s.estado e "
			+ "where "
			+ "(t.codigo=:codigoTipo) "
			,
	countQuery = "select count(s) from SolicitudNotificacion s "
			+ "left join  s.tipo t "
			+ "left join  s.estado e "
			+ "where "
			+ "(t.codigo=:codigoTipo) "
			)			
	Page<SolicitudNotificacion> buscar(String codigoTipo,Pageable pageable);

	@Query("select s from SolicitudNotificacion s "
			+ "left join fetch Solicitud sol on sol.idSolicitud = s.idSolicitud  "
			+ "left join fetch s.tipo t "
			+ "left join fetch s.estado e "
			+ "where sol.solicitudUuid = :solicitudUuid and t.codigo = :tipo ")
	public SolicitudNotificacion obtenerXSolicitud(String tipo, String solicitudUuid);

}
