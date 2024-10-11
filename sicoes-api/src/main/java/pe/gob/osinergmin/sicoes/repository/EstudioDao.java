package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Estudio;

@Repository
public interface EstudioDao extends JpaRepository<Estudio, Long> {
	
	@Query("select e from Estudio e "	
			+ "left join fetch e.tipo td "
			+ "left join fetch e.evaluacion ev "
			+ "left join fetch e.solicitud s "
		+ "where e.idEstudio=:idEstudio")	
	public Estudio obtener(Long idEstudio);
	
	@Query(value="select e from Estudio e "
			+ "left join fetch e.tipoEstudio te "
			+ "left join fetch e.tipo td "
			+ "left join fetch e.evaluacion ev "
			+ "left join fetch e.solicitud s "
			+ "where e.solicitud.idSolicitud=:idSolicitud and te.codigo=:tipo",
			countQuery = "select count(e) from Estudio e "
					+ "left join e.tipoEstudio te "
					+ "left join e.tipo td "
					+ "left join e.evaluacion ev "
					+ "left join e.solicitud s "
					+ "where e.solicitud.idSolicitud=:idSolicitud and te.codigo=:tipo")			
		public Page<Estudio> buscar(String tipo,Long  idSolicitud,Pageable pageable);

	@Modifying
	@Query("delete from Estudio e where e.tipoEstudio.idListadoDetalle=:idTipoEstudio and e.fuente.idListadoDetalle=:idFuente ")
	public void limpiarEstudios(Long idTipoEstudio, Long idFuente);
	
	@Query(value="select e from Estudio e "
			+ "left join fetch e.tipoEstudio te "
			+ "left join fetch e.tipo td "
			+ "left join fetch e.evaluacion ev "
			+ "left join fetch e.solicitud s "
			+ "where e.solicitud.idSolicitud=:idSolicitud and te.codigo=:tipo ")
	public List<Estudio> buscar(String tipo, Long idSolicitud);
	
	@Query(value="select e from Estudio e "
			+ "left join fetch e.tipoEstudio te "
			+ "left join fetch e.tipo td "
			+ "left join fetch e.evaluacion ev "
			+ "left join fetch e.solicitud s "
			+ "where e.solicitud.idSolicitud=:idSolicitud and te.codigo=:tipo and (e.flagSiged is null  or e.flagSiged=0) ")
	public List<Estudio> buscarPresentacion(String tipo, Long idSolicitud);
	
	@Query(value="select e from Estudio e "
			+ "left join fetch e.tipoEstudio te "
			+ "left join fetch e.tipo td "
			+ "left join fetch e.evaluacion ev "
			+ "left join fetch e.solicitud s "
			+ "where e.solicitud.idSolicitud=:idSolicitud ")
	public List<Estudio> buscar( Long idSolicitud);
	
	
}
