package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.Usuario;

public interface RolService extends BaseService<Rol,Long>{
	
	Rol obtenerCodigo(String codigo);

	Page<Rol> buscar(String nombre, Usuario usuario, Pageable pageable);
	
	List<Rol> buscarRolUsuario(Long idUsuario);

}
