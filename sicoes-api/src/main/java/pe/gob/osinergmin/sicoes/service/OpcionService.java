package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.Opcion;
import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface OpcionService extends BaseService<Opcion,Long>{

	

	public Page<Opcion> buscar(Opcion opcion, Usuario usuario, Pageable pageable);
	public List<Opcion> buscarPadres() ;
	public List<Opcion> buscarOpcionRol(Long idRol);
	public List<Opcion> opcionesRoles(List<Rol> roles,Contexto contexto);
}
