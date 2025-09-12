package pe.gob.osinergmin.sicoes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.PerfilAprobador;

@Repository
public interface PerfilAprobadorDao extends JpaRepository<PerfilAprobador, Long> {
	
	@Query("select pa from PerfilAprobador pa "	
			+ "left join fetch pa.perfil p "
			+ "left join fetch pa.aprobadorG1 apg1 "
			+ "left join fetch pa.aprobadorG2 apg2 "
			+ "left join fetch pa.evaluador evd "
			+ "left join fetch pa.evaluador2 evd2 "
			+ "left join fetch pa.evaluador3 evd3 "
			+ "left join fetch pa.evaluador4 evd4 "
			+ "left join fetch pa.evaluador5 evd5 "
			+ "left join fetch pa.evaluador6 evd6 "
			+ "left join fetch pa.evaluador7 evd7 "
			+ "left join fetch pa.evaluador8 evd8 "
			+ "where p.idListadoDetalle = :idPerfil")
	public List<PerfilAprobador> obtenerPerfilAprobadorPorIdPerfil(Long idPerfil);
	
	@Query("select pa from PerfilAprobador pa "	
			+ "left join fetch pa.perfil p "
			+ "left join fetch pa.aprobadorG1 apg1 "
			+ "left join fetch pa.aprobadorG2 apg2 "
			+ "left join fetch pa.evaluador evd "
			+ "left join fetch pa.evaluador2 evd2 "
			+ "left join fetch pa.evaluador3 evd3 "
			+ "left join fetch pa.evaluador4 evd4 "
			+ "left join fetch pa.evaluador5 evd5 "
			+ "left join fetch pa.evaluador6 evd6 "
			+ "left join fetch pa.evaluador7 evd7 "
			+ "left join fetch pa.evaluador8 evd8 "
			+ "where apg2.idUsuario = :idAprobador")
	public List<PerfilAprobador> obtenerPerfilAprobadorPorIdAprobadorG2(Long idAprobador);
	
	@Modifying
	@Transactional
	@Query(value="update PerfilAprobador set aprobadorG2.idUsuario = :idUsuario where idPerfilAprobador=:idPerfilAprobador")
	public void actualizarAprobadorG2(Long idPerfilAprobador, Long idUsuario);
	

	@Query("select pa from PerfilAprobador pa "	
			+ "left join fetch pa.perfil p "
			+ "left join fetch pa.aprobadorG1 apg1 "
			+ "left join fetch pa.aprobadorG2 apg2 "
			+ "left join fetch pa.evaluador evd "
			+ "left join fetch pa.evaluador2 evd2 "
			+ "left join fetch pa.evaluador3 evd3 "
			+ "left join fetch pa.evaluador4 evd4 "
			+ "left join fetch pa.evaluador5 evd5 "
			+ "left join fetch pa.evaluador6 evd6 "
			+ "left join fetch pa.evaluador7 evd7 "
			+ "left join fetch pa.evaluador8 evd8 "
			+ "where pa.aprobadorG1 = :idAprobador")
	public List<Integer> obtenerIdsPerfilesAsignadosAproba1dor(Long idAprobador);
	
	@Query("SELECT pa.perfil.idListadoDetalle " +
	           "FROM PerfilAprobador pa " +
	           "JOIN pa.aprobadorG1 ag1 " + // Join explícito con la entidad Usuario (alias ag1)
	           "WHERE ag1.idUsuario = :idAprobador") // Comparación con el ID del usuario
	public List<Integer> obtenerIdsPerfilesAsignadosAprobador(Long idAprobador);

	Optional<PerfilAprobador> findFirstByPerfilIdListadoDetalle(Long idPerfil);

}
