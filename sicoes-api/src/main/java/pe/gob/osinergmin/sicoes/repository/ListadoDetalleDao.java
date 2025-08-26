package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;

@Repository
public interface ListadoDetalleDao extends JpaRepository<ListadoDetalle, Long> {
	@Query("select l from ListadoDetalle l "	
		+ "where l.idListadoDetalle=:idListadoDetalle")	
	public ListadoDetalle obtener(Long idListadoDetalle);
	
	@Query("select l.idListadoSuperior from ListadoDetalle l "
	+ "where l.idListadoDetalle=:idListadoDetalle")
	public Long idSuperiod(Long idListadoDetalle);
	
	@Query(value="select l from ListadoDetalle l "	,
			countQuery = "select count(l) from ListadoDetalle l ")			
		public Page<ListadoDetalle> buscar(Pageable pageable);
	
	@Query("select new ListadoDetalle(ld,l) from ListadoDetalle ld "
			+ "left join  Listado l on (ld.idListado=l.idListado)"
			+ "where l.codigo=:codidoListado order by ld.orden asc")
	List<ListadoDetalle> listarListadoDetalle(String codidoListado);
	
	@Query("select new ListadoDetalle(ld,l) from ListadoDetalle ld "
			+ "left join  Listado l on (ld.idListado=l.idListado)"
			+ "where l.codigo=:codidoListado and ld.idListadoSuperior=:idSuperior order by ld.orden asc")
	List<ListadoDetalle> listarListadoDetalle(String codidoListado, Long idSuperior);
	
	@Query("select ld from ListadoDetalle ld "	
			+ "where ld.idListado=:idListado")	
	public List<ListadoDetalle> buscar(Long idListado);

	@Query("select new ListadoDetalle(ld,l) from ListadoDetalle ld "
			+ "left join  Listado l on (ld.idListado=l.idListado)"
			+ "where l.codigo=:codidoListado and ld.codigo=:codigoListadoDetalle order by ld.orden asc")
	public ListadoDetalle obtenerListadoDetalle(String codidoListado,String codigoListadoDetalle);

	@Query("select new ListadoDetalle(ld,l) from ListadoDetalle ld "
			+ "left join  Listado l on (ld.idListado=l.idListado)"
			+ "where l.codigo=:codidoListado and ld.valor=:abreviaturaTitulo order by ld.orden asc")
	public ListadoDetalle obtenerListadoDetalleValor(String codidoListado, String abreviaturaTitulo);

	@Query("select new ListadoDetalle(ld,l) from ListadoDetalle ld "
			+ "left join  Listado l on (ld.idListado=l.idListado)"
			+ "where l.codigo=:codidoListado and ld.orden=:orden order by ld.orden asc")
	public ListadoDetalle obtenerListadoDetalleOrden(String codidoListado, Long orden);

	@Query("select ld from ListadoDetalle ld "
			+ "where ld.codigo = :codidoListado "
			+ "order by ld.orden asc")
	public List<ListadoDetalle> listarListadoDetallePorCoodigo(String codidoListado);

	@Query("select ld from ListadoDetalle ld "
			+ "where ld.idListado = :idListado order by ld.orden asc")
	List<ListadoDetalle> listarPerfilesDetalle(Long idListado);

	@Query(value = "SELECT " +
			"pd.ID_DIVISION AS idDivision, " +
			"ld.ID_LISTADO_DETALLE AS idListadoDetalle, " +
			"ld.DE_LISTADO_DETALLE AS dePerfil, " +
			"sector.DE_LISTADO_DETALLE || ' / ' || " +
			"subsector.DE_LISTADO_DETALLE || ' / ' || " +
			"actividad.DE_LISTADO_DETALLE || ' / ' || " +
			"unidad.DE_LISTADO_DETALLE || ' / ' || " +
			"subcategoria.DE_LISTADO_DETALLE || ' / ' || " +
			"ld.DE_LISTADO_DETALLE AS detalle " +
			"FROM SICOES_TM_LISTADO_DETALLE ld " +
			"LEFT JOIN SICOES_TM_LISTADO_DETALLE subcategoria ON subcategoria.ID_LISTADO_DETALLE = ld.ID_SUPERIOR_LD " +
			"LEFT JOIN SICOES_TM_LISTADO_DETALLE unidad ON unidad.ID_LISTADO_DETALLE = subcategoria.ID_SUPERIOR_LD " +
			"LEFT JOIN SICOES_TM_LISTADO_DETALLE actividad ON actividad.ID_LISTADO_DETALLE = unidad.ID_SUPERIOR_LD " +
			"LEFT JOIN SICOES_TM_LISTADO_DETALLE subsector ON subsector.ID_LISTADO_DETALLE = actividad.ID_SUPERIOR_LD " +
			"LEFT JOIN SICOES_TM_LISTADO_DETALLE sector ON sector.ID_LISTADO_DETALLE = subsector.ID_SUPERIOR_LD " +
			"LEFT JOIN SICOES_TX_PERFIL_DIVISION pd ON pd.ID_PERFIL = ld.ID_LISTADO_DETALLE " +
			"WHERE ld.ID_LISTADO = :idListado",
			nativeQuery = true)
	List<Object[]> listarPerfilesDetalleV2(@Param("idListado") Long idListado);

	@Query("select pd.perfil from PerfilDivision pd "
			+ "left join pd.division d "
			+ "where d.idDivision = :idDivision")
	List<ListadoDetalle> listarPerfilesDetallePorDivision(Long idDivision);

	
}
