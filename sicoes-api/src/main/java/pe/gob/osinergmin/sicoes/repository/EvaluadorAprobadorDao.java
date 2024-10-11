package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.EvaluadorAprobador;

@Repository
public interface EvaluadorAprobadorDao extends JpaRepository<EvaluadorAprobador, Long> {
	
	@Query("select ea from EvaluadorAprobador ea "
			+ "left join fetch ea.usuario us "
			+ "left join fetch ea.grupo g "
			+ "where us.idUsuario = :idUsuario and g.idListadoDetalle = :idGrupo ")
	public EvaluadorAprobador obtenerResponsablePorIdUsuario(Long idUsuario, Long idGrupo);

}
