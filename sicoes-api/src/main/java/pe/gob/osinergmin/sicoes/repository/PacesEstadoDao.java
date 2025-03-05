package pe.gob.osinergmin.sicoes.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.PacesEstado;


public interface PacesEstadoDao  extends JpaRepository<PacesEstado, Long> {

		
	@Query("select  a from PacesEstado a "				
			+ "where a.paces.idPaces = :idPace order by idPacesEstado desc")	
		public List<PacesEstado> obtenerPorIdPace(Long idPace);
	
	
}
