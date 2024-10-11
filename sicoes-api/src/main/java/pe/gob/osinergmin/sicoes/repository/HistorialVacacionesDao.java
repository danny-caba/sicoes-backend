package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.HistorialVacaciones;

@Repository
public interface HistorialVacacionesDao extends JpaRepository<HistorialVacaciones, Long> {

	@Query("select h from HistorialVacaciones h "
			+ "where h.usuarioSaliente=:usuario  and h.flagActivo=1 ")
	public HistorialVacaciones obtenerRegistroVacacionesUsuario(String usuario);
	
	
	@Query("select h from HistorialVacaciones h "
			+ "where h.flagActivo=1 ")
	public List<HistorialVacaciones> obtenerHistorialVacacionesActivos();
}
