package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;

@Repository
public interface ListadoDetalleDao extends JpaRepository<ListadoDetalle, Long> {
	@Query("select l from ListadoDetalle l "	
		+ "where l.idListadoDetalle=:idListadoDetalle")	
	public ListadoDetalle obtener(Long idListadoDetalle);
	
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

	@Query("select ld from ListadoDetalle ld "
			+ "where ld.codigo = :codidoListado "
			+ "order by ld.orden asc")
	public List<ListadoDetalle> listarListadoDetallePorCoodigo(String codidoListado);
	
}
