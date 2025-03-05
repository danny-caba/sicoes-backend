package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Listado;

@Repository
public interface ListadoDao extends JpaRepository<Listado, Long> {
	
	@Query("select l from Listado l "			
		+ "where l.idListado=:idListado")	
	public Listado obtener(Long idListado);
	
	@Query("select l from Listado l "			
			+ "where l.codigo=:codigo")	
	public Listado obtener(String codigo);
	
	@Query(value="select l from Listado l ",
			countQuery = "select count(l) from Listado l ")			
	Page<Listado> buscar(Pageable pageable);

	@Query("select l from Listado l "
			+ "where l.codigo=:codigo")
	Listado obtenerPorCodigo(String codigo);
}
