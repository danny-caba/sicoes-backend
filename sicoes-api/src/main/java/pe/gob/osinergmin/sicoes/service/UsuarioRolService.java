package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import pe.gob.osinergmin.sicoes.model.UsuarioRol;
import pe.gob.osinergmin.sicoes.model.UsuarioRolConfiguracion;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface UsuarioRolService  extends BaseService<UsuarioRol,Long>{
	public UsuarioRol registrarUsuarioRol(UsuarioRol usuarioRol,Contexto contexto);
	public void actualizarEstadoUsuarioRol(UsuarioRol usuarioRol,Contexto contexto);
	public UsuarioRolConfiguracion registrarUsuarioRolConfiguracion(UsuarioRolConfiguracion usuarioRolConfiguracion,Contexto contexto);
	public void actualizarEstadoUsuarioRolConf(UsuarioRolConfiguracion usuarioRolConfiguracion,Contexto contexto);
}
