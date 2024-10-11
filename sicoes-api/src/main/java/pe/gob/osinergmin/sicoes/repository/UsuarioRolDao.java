package pe.gob.osinergmin.sicoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.UsuarioRol;

@Repository
public interface UsuarioRolDao extends JpaRepository<UsuarioRol, Long> {
	
	@Modifying
	@Query("delete from UsuarioRol ur  where ur.usuario.idUsuario=:idUsuario and  ur.rol.idRol=:idRol")
	void eliminar(Long idUsuario,Long idRol);

	@Modifying
	@Query("delete from UsuarioRol ur  where ur.usuario.idUsuario=:idUsuario ")
	void eliminar(Long idUsuario);
	
	@Query(value="select ur from UsuarioRol ur "	
			+ "left join fetch ur.usuario u "
			+ "left join fetch ur.rol r "
			+ "where ur.rol.codigo=:codigoRol")
	public List<UsuarioRol> obtenerUsuariosRol(String codigoRol);
	
	@Modifying
	@Transactional
	@Query(value="update UsuarioRol set estadoUsuarioRol = :estadoUsuarioRol, usuActualizacion = :usuActualizacion, fecActualizacion = sysdate, ipActualizacion = :ipActualizacion where idUsuarioRol=:idUsuarioRol")
	public void actualizarEstadoUsuarioRol(Long idUsuarioRol, String estadoUsuarioRol, String usuActualizacion, String ipActualizacion);
	
	@Query(value="select ur from UsuarioRol ur "	
			+ "left join fetch ur.usuario u "
			+ "left join fetch ur.rol r "
			+ "where ur.estadoUsuarioRol = '1' and u.idUsuario=:idUsuario")
	public List<UsuarioRol> listarUsuarioRolXIdUsuario(Long idUsuario);
}
