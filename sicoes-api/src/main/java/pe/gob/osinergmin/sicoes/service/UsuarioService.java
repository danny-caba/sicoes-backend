package pe.gob.osinergmin.sicoes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import pe.gob.osinergmin.sicoes.model.ConfiguracionBandeja;
import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.Token;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.UsuarioReasignacion;
import pe.gob.osinergmin.sicoes.model.UsuarioRol;
import pe.gob.osinergmin.sicoes.model.UsuarioRolConfiguracion;
import pe.gob.osinergmin.sicoes.model.dto.PerfilDTO;
import pe.gob.osinergmin.sicoes.model.dto.ReasignacionDTO;
import pe.gob.osinergmin.sicoes.model.dto.ResponseUsuarioSigedDTO;
import pe.gob.osinergmin.sicoes.model.dto.UsernameDTO;
import pe.gob.osinergmin.sicoes.model.dto.UsuarioDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.bean.siged.ResponseUserListDto;
import pe.gob.osinergmin.sicoes.util.vo.OpcionDTO;

public interface UsuarioService extends UserDetailsService{
	Usuario buscarUsuario(String username);
	UserDetails loadUserByUsername(String string, String credentials);
	List<UsernameDTO> listarEvaluadores();
	UsernameDTO obtenerPerfil(Contexto contexto);
	Usuario obtener(String username);
	List<PerfilDTO> obtenerPerfiles();
	public List<UsernameDTO> listarUsuarios(Long idPerfil);
	public List<OpcionDTO> obtenerOpcion(Long idPerfil);
	Usuario obtenerUsuario(Contexto contexto);
	void enviarCodigo(String correo,String codigoTipo,Contexto contexto);
	Token validarCodigo(Token token);
	Page<Usuario> listarUsuarioPerfil(String codigoRol,Pageable pageable, Contexto contexto);
	Page<Usuario> listarUsuarioPerfil(String codigoRol, String uuidSolicitud, Long idPerfil, Pageable pageable,Contexto contexto);
	Usuario guardar(Usuario usuario, Contexto contexto);
	Usuario obtenerUsuario(Long idPais, Long idTipoDocumento, String nroDocumento, Contexto contextoAnonimo);
	void recuperarContrasenia(Usuario usuario, Contexto contextoAnonimo);
	Usuario cambiarContrasenia(Usuario usuario);
	public Usuario obtener(Long idUsuario);
	public List<ResponseUserListDto.Usuario> listarUsuariosSiged() throws Exception;
	public ResponseUsuarioSigedDTO obtenerUsuarioSiged(Long idUsuario) throws Exception;
	public Page<Usuario> buscar(String nombreUsuario, Pageable pageable);
	public Page<UsuarioRol> listarUsuarioRol(Long idUsuario, Pageable pageable);
	public Usuario registrarUsuario(Usuario usuario, Contexto contexto);
	public Usuario modificarUsuario(Usuario usuario,Contexto contexto);
	public void actualizarEstadoUsuario(Usuario usuario, Contexto contexto);
	public Page<Rol> listarRoles(String nombre, Pageable pageable);
	public Usuario listarUsuario(Long idUsuario);
	public List<Usuario> obtenerUsuariosPorDivision(Long idPerfil);
	public ReasignacionDTO registrarUsuarioReasignacion(ReasignacionDTO reasignacionDto,Contexto contexto);
	public UsuarioRolConfiguracion registrarUsuarioRolConfiguracion(UsuarioRolConfiguracion usuarioRolConfiguracion,Contexto contexto);
	public void actualizarEstadoUsuarioRolConf(UsuarioRolConfiguracion usuarioRolConfiguracion,Contexto contexto);
	public List<Usuario> listarUsuariosXCodigoRol(String codigoRol, Long idUsuario);
}
