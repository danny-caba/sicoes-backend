package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.PerfilDivision;

@Repository
public interface PerfilDivisionDao extends JpaRepository<PerfilDivision, Long> {

	@Query("select pd from PerfilDivision pd "	
			+ "left join fetch pd.perfil p "
			+ "left join fetch pd.division d "
			+ "where p.idListadoDetalle = :idPerfil")
	public PerfilDivision obtenerPerfilDivisionPorIdPerfil(Long idPerfil);
	
}
