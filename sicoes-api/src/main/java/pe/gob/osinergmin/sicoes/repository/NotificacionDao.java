package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Notificacion;

@Repository
public interface NotificacionDao extends JpaRepository<Notificacion, Long> {
	
	@Query("select n from Notificacion n "
			+ "left join fetch n.estado e "
		+ "where n.idNotificacion=:idNotificacion")	
	public Notificacion obtener(Long idNotificacion);
	
	@Query(value="select n from Notificacion n "
			+ "left join fetch n.estado e ",
			countQuery = "select count(a) from Notificacion n "
					+ "left join n.estado e ")			
			Page<Notificacion> buscar(Pageable pageable);
	
	@Query("select n from Notificacion n "
			+ "left join fetch n.estado e "
			+ "where e.codigo=:codigoEstado")
	List<Notificacion> listarNotificacion(String codigoEstado);
}
