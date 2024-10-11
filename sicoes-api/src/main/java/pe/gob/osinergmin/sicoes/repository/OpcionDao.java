package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Opcion;
import pe.gob.osinergmin.sicoes.model.Rol;

@Repository
public interface OpcionDao  extends JpaRepository<Opcion, Long> {

	@Query("select o from Opcion o "
			+ "left join fetch o.padre p "
			+ "left join fetch p.estado pe "
			+ "left join fetch o.estado e "
			+ " where o.idOpcion=:idOpcion")
	Opcion obtener(Long idOpcion);
	
	@Query(value="select o from Opcion o "
			+ "left join fetch o.padre p "
			+ "left join fetch p.estado pe "
			+ "left join fetch o.estado e "
			+ "where (:idOpcion is null or p.idOpcion=:idOpcion ) order by o.orden asc ",
			countQuery = "select count(1) from Opcion o "
					+ "left join o.padre p "
					+ "left join p.estado pe "
					+ "where (:idOpcion is null or p.idOpcion=:idOpcion )"
			)
	Page<Opcion> buscar(Long idOpcion,Pageable pageable);
	@Query("select o from Opcion o "
			+ "left join fetch o.estado e "
			+ "where o.padre is null")
	List<Opcion> buscarPadres();
	
	
	@Query( "select roo from RolOpcion ro "
			+ "left join ro.opcion roo "
			+ "left join fetch roo.padre p "
			+ "left join fetch p.estado pe "
			+ "left join fetch roo.estado e "
			+ "left join ro.rol ror "
			+ "where ror.idRol=:idRol ")
	List<Opcion> buscarOpcionRol(Long idRol);

	@Query( "select distinct roo from RolOpcion ro "
			+ "left join ro.opcion roo "
			+ "left join fetch roo.padre p "
			+ "left join fetch p.estado pe "
			+ "left join fetch roo.estado e "
			+ "left join ro.rol ror "
			+ "where ror in (:roles)")
	List<Opcion> opcionesRoles(List<Rol> roles);

}
