package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.DictamenEvaluacion;
import pe.gob.osinergmin.sicoes.util.Constantes;


@Repository
public interface DictamenEvaluacionDao extends JpaRepository<DictamenEvaluacion, Long> {
	
	@Query("select d from DictamenEvaluacion d "	
			+ "left join fetch d.solicitud s "
			+ "left join fetch d.sector sc "
			+ "where d.idDictamenEvaluacion=:idDictamenEvaluacion ")	
	public DictamenEvaluacion obtener(Long idDictamenEvaluacion);
	
	@Query("select d from DictamenEvaluacion d "	
			+ "left join fetch d.solicitud s "
			+ "left join fetch d.sector sc "
			+ "where s.idSolicitud=:idSolicitud and sc.idListadoDetalle=:idSector ")	
	public DictamenEvaluacion obtenerXSolicitud(Long idSolicitud, Long idSector);
	
	@Query("select d from DictamenEvaluacion d "	
			+ "left join fetch d.solicitud s "
			+ "left join fetch d.sector sc "
			+ "where s.idSolicitud=:idSolicitud and sc.codigo=:codigoSector ")	
	public DictamenEvaluacion obtenerXCodigoSector(Long idSolicitud, String codigoSector);
	
	@Query(value="select d from DictamenEvaluacion d "	
			+ "left join fetch d.solicitud s "
			+ "left join fetch d.sector sc "
			+ "where s.idSolicitud=:idSolicitud ",
			countQuery ="select count(d) from DictamenEvaluacion d "
					+ "left join d.solicitud s "
					+ "left join d.sector sc "
					+ "where s.idSolicitud=:idSolicitud ")		
	public Page<DictamenEvaluacion> obtenerXSolicitud(Long idSolicitud, Pageable pageable);
	
	
	@Query(value="select d from DictamenEvaluacion d "	
			+ "left join fetch d.solicitud s "
			+ "left join fetch d.sector sc "
			+ "where s.idSolicitud=:idSolicitud ")	
	public List<DictamenEvaluacion> listar(Long idSolicitud);

	
	@Query("select d from DictamenEvaluacion d "	
			+ "left join fetch d.solicitud s "
			+ "left join fetch d.sector sc "
			+ "where s.idSolicitud=:idSolicitud and sc.idListadoDetalle=:idSector ")
	public DictamenEvaluacion obtenerXSol(Long idSolicitud, Long idSector);
	
	@Modifying	
	@Query("delete from DictamenEvaluacion d where d.solicitud.idSolicitud =:idSolicitud and d.sector.idListadoDetalle=:idListadoDetalle ")
	public void eliminarXSector(Long idSolicitud, Long idListadoDetalle);

	@Modifying	
	@Query("delete from DictamenEvaluacion d where d.solicitud.idSolicitud =:idSolicitud and d.sector.codigo=:codigoSector ")
	public void eliminarXCodigoSector(String codigoSector, Long idSolicitud);

	@Query(value="select sum(d.montoFacturado) from DictamenEvaluacion d "
			+ "left join  d.solicitud s "
			+ "where s.idSolicitud = :idSolicitud ")
	Double sumarMontoEvaluado(Long idSolicitud);

}
