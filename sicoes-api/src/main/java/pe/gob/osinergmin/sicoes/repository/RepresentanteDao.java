package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Representante;

@Repository
public interface RepresentanteDao extends JpaRepository<Representante, Long> {
	
	@Query("select r from Representante r "
			+ "where r.idRepresentante=:idRepresentante")
	public Representante obtener(Long idRepresentante);

	@Query(value="select r from Representante r ",
			countQuery = "select count(r) from Representante r ")			
			Page<Representante> buscar(Long idRepresentante,Pageable pageable);
}
