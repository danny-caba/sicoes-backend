package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Rol;

@Repository
public interface RolDao extends JpaRepository<Rol, Long> {

	@Query("select r from Rol r "
			+ "left join fetch r.estado re "
			+ "where r.idRol=:id")
	Rol obtener(Long id);

	@Query(value="select r from Rol r "
			+ "left join fetch r.estado re "
			+ "where (:nombre is null or r.nombre like :nombre ) order by r.nombre asc ",
			countQuery ="select count(1) from Rol r "
					+ "left join r.estado re "
					+ "where (:nombre is null or r.nombre like :nombre )")
	Page<Rol> buscar(String nombre, Pageable pageable);

	@Query("select ur.rol from UsuarioRol ur "
			+ "left join ur.rol urr "
			+ "left join ur.usuario uru "
			+ "left join fetch urr.estado re "
			+ "where uru.idUsuario=:idUsuario "
			+ "and ur.estadoUsuarioRol = '1' "
			+ "order by ur.rol.idRol asc"
			)
	List<Rol> buscarRolUsuario(Long idUsuario);
	@Query("select r from Rol r "
			+ "left join fetch r.estado re "
			+ "where r.codigo=:codigo")
	Rol obtenerCodigo(String codigo);
	
	@Query("select ur.rol from UsuarioRol ur "
			+ "left join ur.rol urr "
			+ "left join ur.usuario uru "
			+ "left join fetch urr.estado re "
			+ "where uru.idUsuario=:idUsuario and ur.estadoUsuarioRol='1' "
			+" order by ur.rol.idRol asc"
			)
	List<Rol> buscarRolUsuario2(Long idUsuario);
}
