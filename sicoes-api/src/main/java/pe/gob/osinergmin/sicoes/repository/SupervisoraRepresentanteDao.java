package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.SupervisoraRepresentante;

@Repository
public interface SupervisoraRepresentanteDao extends JpaRepository<SupervisoraRepresentante, Long> {
	
	@Query("select r from SupervisoraRepresentante r "
			+ "where r.idSupervisoraRepresentante=:idSupervisoraRepresentante")
	public SupervisoraRepresentante obtener(Long idSupervisoraRepresentante);
	
	@Query("select r from SupervisoraRepresentante r "
			+ "where r.idSupervisora=:idSupervisora")
	public SupervisoraRepresentante obtenerXIdSupervisora(Long idSupervisora);

	@Query(value="select r from SupervisoraRepresentante r ",
			countQuery = "select count(r) from Representante r ")			
			Page<SupervisoraRepresentante> buscar(Long idRepresentante,Pageable pageable);
}
