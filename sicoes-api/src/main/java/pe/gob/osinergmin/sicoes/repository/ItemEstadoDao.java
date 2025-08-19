package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.ItemEstado;
import pe.gob.osinergmin.sicoes.model.Opcion;
import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.model.Rol;

@Repository
public interface ItemEstadoDao  extends JpaRepository<ItemEstado, Long> {

	@Query("select i from ItemEstado i "
			+ "left join fetch i.procesoItem p "
			+ "left join fetch i.estado e "
			+ "where i.idItemEstado=:idItemEstado")
	public ItemEstado obtener(Long idItemEstado);
	
	@Query("select i from ItemEstado i "
			+ "left join fetch i.procesoItem p "
			+ "left join fetch i.estado e "
			+ "where i.idItemEstado=:idItemEstado "
			+ "and p.procesoItemUuid=:procesoItemUuid")
	public ItemEstado obtener(Long idItemEstado,String procesoItemUuid);
	
	@Query(value="select i from ItemEstado i "
			+ "left join fetch i.procesoItem p "
			+ "left join fetch i.estado e "
			+ "where p.procesoItemUuid=:procesoItemUuid",
			countQuery ="select count(i) from ItemEstado i "
					+ "left join i.procesoItem p "
					+ "left join i.estado e "
					+ "where p.procesoItemUuid=:procesoItemUuid order by i.fechaRegistro desc")		
	public Page<ItemEstado> buscar(String procesoItemUuid,Pageable pageable);

}
