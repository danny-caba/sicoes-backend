package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Persona;

@Repository
public interface PersonaDao extends PagingAndSortingRepository<Persona, Long> {
	
	@Query("select p from Persona p "
			+ "left join fetch p.tipoDocumento td "
			+ "where p.idPersona=:idPersona")
	public Persona obtener(Long idPersona);
	
	@Query(value="select p from Persona p "
	+ "left join fetch p.tipoDocumento td ",
	countQuery = "select count(p) from Persona p "
	+ "left join p.tipoDocumento td ")			
	Page<Persona> buscar(Pageable pageable);

}
