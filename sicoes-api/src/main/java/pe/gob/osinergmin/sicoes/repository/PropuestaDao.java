package pe.gob.osinergmin.sicoes.repository;
import java.util.List;

import javax.persistence.Column;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface PropuestaDao extends JpaRepository<Propuesta, Long> {
	
	@Query("select p from Propuesta p "	
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.procesoItem i "
			+ "left join fetch p.estado e "
			+ "left join fetch p.propuestaTecnica pt "
			+ "left join fetch p.propuestaEconomica pe "
		+ "where p.propuestaUuid = :uuidPropuesta")	
	public Propuesta obtener(String uuidPropuesta);
	
	@Query("select p from Propuesta p "	
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.procesoItem i "
			+ "left join fetch p.estado e "
			+ "left join fetch p.propuestaTecnica pt "
			+ "left join fetch p.propuestaEconomica pe "
		+ "where p.idPropuesta=:idPropuesta")	
	public Propuesta obtener(Long idPropuesta);
	
	@Query(value="select p from Propuesta p "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.procesoItem i "
			+ "left join fetch p.propuestaTecnica pt "
			+ "left join fetch p.propuestaEconomica pe "
			+ "left join fetch p.estado e "
			+ "where i.procesoItemUuid=:procesoItemUuid "
			+ "and e.codigo='"+Constantes.LISTADO.ESTADO_PRESENTACION.PRESENTADO+ "'"	,
			countQuery ="select count(p) from Propuesta p "
					+ "left join p.supervisora s "
					+ "left join p.procesoItem i "
					+ "left join p.propuestaTecnica pt "
					+ "left join p.propuestaEconomica pe "
					+ "left join p.estado e "
					+ "where i.procesoItemUuid=:procesoItemUuid "
					+ "and e.codigo='"+Constantes.LISTADO.ESTADO_PRESENTACION.PRESENTADO+ "'")		
	public Page<Propuesta> listarXItem(String procesoItemUuid,Pageable pageable);
	
	@Query(value="select p from Propuesta p "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.procesoItem i "
			+ "left join fetch p.propuestaTecnica pt "
			+ "left join fetch p.propuestaEconomica pe "
			+ "left join fetch p.estado e ",
			countQuery ="select count(p) from Propuesta p "
					+ "left join p.supervisora s "
					+ "left join p.procesoItem i "
					+ "left join p.propuestaTecnica pt "
					+ "left join p.propuestaEconomica pe "
					+ "left join p.estado e ")		
	public Page<Propuesta> buscar(Pageable pageable);

	@Query("select p from Propuesta p "	
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.procesoItem i "
			+ "left join fetch p.estado e "
			+ "left join fetch p.propuestaTecnica pt "
			+ "left join fetch p.propuestaEconomica pe "
		+ "where i.idProcesoItem=:idProcesoItem and s.idSupervisora=:idSupervisora ")	
	public Propuesta obtenerPropuestaXProcesoItem(Long idProcesoItem,Long idSupervisora);

	@Query("select p from Propuesta p "	
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.procesoItem i "
			+ "left join fetch p.estado e "
			+ "left join fetch p.propuestaTecnica pt "
			+ "left join fetch p.propuestaEconomica pe "
		+ "where i.idProcesoItem=:idProcesoItem")	
	public List<Propuesta> obtenerPropuestaXProcesoItem(Long idProcesoItem);


	@Query("select pe.idPropuestaEconomica from Propuesta p "	
			+ "left join p.supervisora s "
			+ "left join p.procesoItem i "
			+ "left join p.estado e "
			+ "left join p.propuestaTecnica pt "
			+ "left join p.propuestaEconomica pe "
		+ "where p.propuestaUuid=:propuestaUuid ")	
	public Long obtenerIdEconomica(String propuestaUuid);
	
	@Query("select pt.idPropuestaTecnica from Propuesta p "	
			+ "left join p.supervisora s "
			+ "left join p.procesoItem i "
			+ "left join p.estado e "
			+ "left join p.propuestaTecnica pt "
			+ "left join p.propuestaEconomica pe "
		+ "where p.propuestaUuid=:propuestaUuid ")	
	public Long obtenerIdTecnica(String propuestaUuid);

	
	@Query("select p from Propuesta p "	
			+ "left join p.supervisora s "
			+ "left join p.procesoItem i "
			+ "left join p.estado e "
			+ "left join p.propuestaTecnica pt "
			+ "left join p.propuestaEconomica pe "
		+ "where i.procesoItemUuid=:procesoItemUuid ")
	public List<Propuesta> listar(String procesoItemUuid);
	
	@Modifying
	@Query("update Propuesta set rutaDescarga=null,decripcionDescarga=null where decripcionDescarga is not null ")	
	public void limpiarDescarga();

	
	@Query("select p from Propuesta p "	
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.procesoItem i "
			+ "left join fetch p.estado e "
			+ "left join fetch p.propuestaTecnica pt "
			+ "left join fetch p.propuestaEconomica pe "
			+ "left join fetch p.ganador g "
			+ "where i.procesoItemUuid=:procesoItemUuid "
			+ "and g.codigo='"+Constantes.LISTADO.SI_NO.SI+"' ")	
	public Propuesta obtenerPropuestaGanadora(String procesoItemUuid);

	@Query("select p from Propuesta p "
			+ "left join fetch p.supervisora s "
			+ "left join fetch p.procesoItem i "
			+ "left join fetch p.estado e "
			+ "left join fetch p.propuestaTecnica pt "
			+ "left join fetch p.propuestaEconomica pe "
			+ "where i.idProcesoItem=:idProcesoItem "
			+ "and e.codigo='"+Constantes.LISTADO.ESTADO_PRESENTACION.PRESENTADO+"' ")
	Propuesta obtenerPropuestaPorItem(Long idProcesoItem);
}
