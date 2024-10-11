package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Profesion;

@Repository
public interface ProfesionDao extends PagingAndSortingRepository<Profesion, Long> {

	@Query("select p from Profesion p")	
	public List<Profesion> listarTodos();
	
}
