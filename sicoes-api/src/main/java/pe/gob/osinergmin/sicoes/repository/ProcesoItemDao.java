package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Estudio;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface ProcesoItemDao extends JpaRepository<ProcesoItem, Long> {
	
	@Query("select i from ProcesoItem i "	
			+ "left join fetch i.proceso p "
			+ "left join fetch i.estado e "
			+ "where i.procesoItemUuid=:procesoItemUuid ")	
	public ProcesoItem obtener(String procesoItemUuid);
	
	@Query("select i from ProcesoItem i "	
			+ "left join fetch i.proceso p "
			+ "left join fetch i.estado e "
			+ "where i.idProcesoItem=:idProcesoItem ")	
	public ProcesoItem obtener(Long idProcesoItem);
	
	@Query(value="select i from ProcesoItem i "	
			+ "left join fetch i.proceso p "
			+ "left join fetch i.estado e "
			+ "where p.procesoUuid=:procesoUuid ")
	public List<ProcesoItem> buscar(String procesoUuid);
	
	@Modifying
	@Query("delete from ProcesoItem i where i.procesoItemUuid=:procesoItemUuid ")
	public void eliminarItem(String procesoItemUuid);
	
	@Query(value="select i from ProcesoItem i "
			+ "left join fetch i.proceso p "
			+ "left join fetch i.estado e "
			+ "where p.procesoUuid=:procesoUuid "
			+ "order by p.fechaPublicacion desc ",
			countQuery ="select count(i) from ProcesoItem i "
					+ "left join i.proceso p "
					+ "left join i.estado e "
					+ "where p.procesoUuid=:procesoUuid "
					+ "order by p.fechaPublicacion desc")		
	public Page<ProcesoItem> buscar(String procesoUuid,Pageable pageable);
	
	@Query(value="select i from ProcesoItem i "
			+ "left join fetch i.proceso p "
			+ "left join fetch i.estado e "
			+ "where (:fechaDesde is null or trunc(p.fechaPublicacion)>=:fechaDesde) "
			+ "and (:fechaHasta is null or trunc(p.fechaPublicacion)<=:fechaHasta) "
			+ "and (:idEstadoProceso is null or p.estado.idListadoDetalle = :idEstadoProceso) "
			+ "and (:idEstadoItem is null or e.idListadoDetalle = :idEstadoItem) "
			+ "and (:idSector is null or p.sector.idListadoDetalle = :idSector) "
			+ "and (:idSubSector is null or p.subsector.idListadoDetalle = :idSubSector) "
			+ "and (:nroProceso is null or p.numeroProceso like :nroProceso) "
			+ "and (:nombreProceso is null or p.nombreProceso like :nombreProceso) "
			+ "and (:descripcionItem is null or i.descripcionItem like :descripcionItem) "
			+ "and (:nroExpediente is null or p.numeroExpediente like :nroExpediente) "
			+ "and p.estado.codigo !='"+Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION+"' "
			+ "order by p.fechaPublicacion desc",
			countQuery ="select count(i) from ProcesoItem i "
					+ "left join i.proceso p "
					+ "left join i.estado e "
					+ "where (:fechaDesde is null or trunc(p.fechaPublicacion)>=:fechaDesde) "
					+ "and (:fechaHasta is null or trunc(p.fechaPublicacion)<=:fechaHasta) "
					+ "and (:idEstadoProceso is null or p.estado.idListadoDetalle = :idEstadoProceso) "
					+ "and (:idEstadoItem is null or e.idListadoDetalle = :idEstadoItem) "
					+ "and (:idSector is null or p.sector.idListadoDetalle = :idSector) "
					+ "and (:idSubSector is null or p.subsector.idListadoDetalle = :idSubSector) "
					+ "and (:nroProceso is null or p.numeroProceso like :nroProceso) "
					+ "and (:nombreProceso is null or p.nombreProceso like :nombreProceso) "
					+ "and (:descripcionItem is null or i.descripcionItem like :descripcionItem) "
					+ "and (:nroExpediente is null or p.numeroExpediente like :nroExpediente) "
					+ "and p.estado.codigo !='"+Constantes.LISTADO.ESTADO_PROCESO.EN_ELABORACION+"' "
					+ "order by p.fechaPublicacion desc")		
	public Page<ProcesoItem> buscarProcesos(Date fechaDesde,Date fechaHasta,Long idEstadoItem,Long idSector,Long idSubSector,String nroProceso,String nombreProceso,String descripcionItem,String nroExpediente,Long idEstadoProceso,Pageable pageable);

	@Query(value="select i from ProcesoItem i "
			+ "left join fetch i.proceso p "
			+ "left join fetch i.estado e "
			+ "where p.idProceso=:idProceso and i.numeroItem=:numeroItem and (i.idProcesoItem<>:idProcesoItem or :idProcesoItem is null)")
	public List<ProcesoItem> obtenerProcesoNumero(Long numeroItem, Long idProceso,Long idProcesoItem);

}
