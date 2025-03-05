package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

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

	@Query("select pd from PerfilDivision pd "
			+ "left join fetch pd.perfil p "
			+ "left join fetch pd.division d "
			+ "where d.idDivision = :idDivision")
	List<PerfilDivision> obtenerPorIdDivision(Long idDivision);

	@Query("select pd from PerfilDivision pd "
			+ "left join fetch pd.perfil p "
			+ "left join fetch pd.division d "
			+ "join ConfiguracionBandeja cb on cb.perfil = pd.perfil "
			+ "join cb.usuario u "
			+ "where u.idUsuario = :idUsuario "
			+ "and cb.estadoConfiguracion = '1' ")
	List<PerfilDivision> obtenerDivisionesPorUsuario(Long idUsuario);
	
}
