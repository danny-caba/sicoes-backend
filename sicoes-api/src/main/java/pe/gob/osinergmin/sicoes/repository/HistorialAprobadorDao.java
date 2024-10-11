package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.HistorialAprobador;

@Repository
public interface HistorialAprobadorDao extends JpaRepository<HistorialAprobador, Long> {
	
	@Query("select h from HistorialAprobador h "
			+ "where h.idAprobadorG2=:idUsuario  and h.flagActivo=1 ")
	public List<HistorialAprobador> obtenerHistorialAprobadorG2(Long idUsuario);
}
