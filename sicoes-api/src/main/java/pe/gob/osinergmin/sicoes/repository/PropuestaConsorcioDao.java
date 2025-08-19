package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.PropuestaConsorcio;

@Repository
public interface PropuestaConsorcioDao extends JpaRepository<PropuestaConsorcio, Long>{
	
	@Query("select p from PropuestaConsorcio p "
		+ "left join fetch p.propuestaTecnica t "
		+ "left join fetch p.supervisora s "
		+ "where p.idPropuestaConsorcio=:idPropuestaConsorcio ")
	public PropuestaConsorcio obtener(Long idPropuestaConsorcio);
	
	@Query("select p from PropuestaConsorcio p "
		    + "left join fetch p.propuestaTecnica t "
		    + "left join fetch p.supervisora s "
		    + "where p.propuestaTecnica.idPropuestaTecnica = :idPropuestaTecnica ")
	public List<PropuestaConsorcio> obtenerConsorcios(Long idPropuestaTecnica);

	@Query("select p "
		      + "from PropuestaConsorcio p "
		      + "join p.supervisora s "
		      + "join SupervisoraDictamen d on d.supervisora.idSupervisora = s.idSupervisora "
		      + "where p.propuestaTecnica.idPropuestaTecnica = :idPropuestaTecnica "
		      + "and d.sector.idListadoDetalle = :idSectorListadoDetalle")
	public List<PropuestaConsorcio> obtenerConsorciosConFacturado(Long idPropuestaTecnica, Long idSectorListadoDetalle);

}
