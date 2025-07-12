package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Division;

@Repository
public interface DivisionDao extends JpaRepository<Division, Long> {
	
	
	
	@Query("select u from Division u ")
	public List<Division> obtener();

	@Query("select d from Division d " +
			"left join fetch d.usuario u " +
			"where u.idUsuario = :usuario")
	List<Division> findByIdUsuario(Long usuario);
	
}
