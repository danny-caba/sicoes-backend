package pe.gob.osinergmin.sicoes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gob.osinergmin.sne.domain.dto.UsuarioSigedDTO;
import pe.gob.osinergmin.sicoes.consumer.PidoConsumer;
import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.ConfiguracionBandeja;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.Token;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.UsuarioReasignacion;
import pe.gob.osinergmin.sicoes.model.UsuarioRol;
import pe.gob.osinergmin.sicoes.model.UsuarioRolConfiguracion;
import pe.gob.osinergmin.sicoes.model.dto.ReasignacionDTO;
import pe.gob.osinergmin.sicoes.model.dto.ResponseUsuarioSigedDTO;
import pe.gob.osinergmin.sicoes.model.dto.UsuarioDetalleSigedDTO;
import pe.gob.osinergmin.sicoes.service.UsuarioRolService;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.util.bean.PidoBeanOutRO;
import pe.gob.osinergmin.sicoes.util.bean.siged.ResponseUserListDto;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController extends BaseRestController{

	private Logger logger = LogManager.getLogger(UsuarioRestController.class);
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRolService usuarioRolService;
	
	@Autowired
	PidoConsumer pidoConsumer;
	
	@GetMapping("/perfil")
	@Raml("usuario.obtener.properties")
	public Usuario obtener() {
		Usuario usuario= usuarioService.obtenerUsuario(getContexto());
//		if(usuario.isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
//			PidoBeanOutRO pidoBeanOutROO=pidoConsumer.obtenerContribuyente(usuario.getCodigoRuc());
//			if(pidoBeanOutROO!=null) {
//				usuario.setNombreTipoNegocio(pidoBeanOutROO.getNombreTipoNegocio());
//			}
//		}
		return usuario;
	}
	
	@GetMapping("/tipo-negocio")
	@Raml("usuario.obtener.properties")
	public Usuario validarTipoNegocio() {
		Usuario usuario= usuarioService.obtenerUsuario(getContexto());
		if(usuario.isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
			PidoBeanOutRO pidoBeanOutROO=pidoConsumer.obtenerContribuyente(usuario.getCodigoRuc());
			if(pidoBeanOutROO!=null) {
				usuario.setNombreTipoNegocio(pidoBeanOutROO.getNombreTipoNegocio());
			}
		}
		return usuario;
	}
	
	@GetMapping("/menu")
	@Raml("usuario.obtener.properties")
	public Usuario obtenerMenu() {
		return usuarioService.obtenerUsuario(getContexto());
	}
	
	@GetMapping
	@Raml("usuario.listar.properties")
	public Page<Usuario> listarUsuario(@RequestParam String  codigoRol,Pageable pageable) {
		return usuarioService.listarUsuarioPerfil(codigoRol,pageable,getContexto());
	}
	
	@GetMapping("/por-perfil")
	@Raml("usuario.listar.properties")
	public Page<Usuario> listarUsuarioPorPerfil(@RequestParam String  codigoRol, @RequestParam String  uuidSolicitud, @RequestParam Long idPerfil, Pageable pageable) {
		return usuarioService.listarUsuarioPerfil(codigoRol,uuidSolicitud,idPerfil,pageable,getContexto());
	}
	
	@GetMapping("/publico/obtener-correo")
	@Raml("usuario.publico.obtener.properties")
	public Usuario obtenerCorreo(@RequestParam Long idPais,@RequestParam Long idTipoDocumento,@RequestParam String nroDocumento) {
		return usuarioService.obtenerUsuario(idPais,idTipoDocumento,nroDocumento,getContextoAnonimo());
	}
	
	@PostMapping("/publico/recuperar-contrasenia")
	@Raml("usuario.publico.obtener.properties")
	public void recuperarContrasenia(@RequestBody Usuario usuario) {
		 usuarioService.recuperarContrasenia(usuario,getContextoAnonimo());
	}
	
	
	@PostMapping
	@Raml("usuario.obtener.properties")
	public Usuario registrar(@RequestBody Usuario usuario) {
		logger.info("registrar {} ",usuario);
		Usuario usuarioBD=usuarioService.guardar(usuario,getContexto());
		return usuarioBD;
	}
	
	@PostMapping("/publico/enviar-codigo-validacion")	
	public void enviarCodigo(@RequestBody Usuario usuario) {
		logger.info("enviar-codigo {} ",usuario.getCorreo());
		usuarioService.enviarCodigo(usuario.getCorreo(),usuario.getCodigoTipo(),getContextoAnonimo());
	}
	
	@PostMapping("/publico/validar-codigo-validacion")	
	public void validarCodigo(@RequestBody Token token) {
		 usuarioService.validarCodigo(token);
	}
	
	@PostMapping("/publico/cambiar-contrasenia")
	@Raml("usuario.obtener.properties")
	public Usuario cambiarContrasenia(@RequestBody Usuario usuario) {
		logger.info("cambiarContrasenia {} ",usuario);
		 return usuarioService.cambiarContrasenia(usuario);
	}
	
	@PostMapping("publico")
	@Raml("usuario.obtener.properties")
	public Usuario registrar2(@RequestBody Usuario usuario) {
		logger.info("registrar {} ",usuario);
		Usuario usuarioBD=usuarioService.guardar(usuario,getContextoAnonimo());
		return usuarioBD;
	}
	
	@PostMapping("validar-codigo-validacion")	
	public void validarCodigo2(@RequestBody Token token) {
		 usuarioService.validarCodigo(token);
	}
	
	@GetMapping("/listar-usuarios-siged")
    public List<ResponseUserListDto.Usuario> listarUsuariosSiged(HttpServletRequest request) throws Exception {
    	return usuarioService.listarUsuariosSiged();
    }
	
	@PostMapping("/obtener-usuario-siged")
    public UsuarioDetalleSigedDTO obtenerUsuarioSiged(@RequestBody ResponseUsuarioSigedDTO usuarioSiged, HttpServletRequest request) throws Exception {
    	return usuarioService.obtenerUsuarioSiged(usuarioSiged.getIdUsuario());
    }
	
	@GetMapping("/listar-bandeja-usuarios")
    public Page<Usuario> listarBandejaUsuarios(@RequestParam(value = "nombreUsuario",required = false) String nombreUsuario,@RequestParam(value = "offset",required = false) int offset, 
    		@RequestParam(value = "pageSize",required = false) int pageSize, Pageable pageable, HttpServletRequest request) {
		
		pageable = PageRequest.of(offset, pageSize);
    	return usuarioService.buscar(nombreUsuario, pageable, getContexto());
    }
	
	@PostMapping("/listar-usuario")
    public Usuario listarUsuario(@RequestBody Usuario usuario, HttpServletRequest request) {
    	return usuarioService.listarUsuario(usuario.getIdUsuario());
    }
	
	@PostMapping("/registrar-usuario")
	public Usuario registrarUsuario(@RequestBody Usuario usuario, HttpServletRequest request) {
		logger.info("registrarUsuario {} ",usuario);
		 return usuarioService.registrarUsuario(usuario,getContextoAnonimo());
	}
	
	@PostMapping("/modificar-usuario")
	public Usuario modificarUsuario(@RequestBody Usuario usuario) {
		logger.info("modificarUsuario {} ",usuario);
		return usuarioService.modificarUsuario(usuario, getContextoAnonimo());
	}
	
	@PostMapping("/actualizar-estado-usuario")
    public void actualizarEstadoUsuario(@RequestBody Usuario usuario, HttpServletRequest request) {
    	usuarioService.actualizarEstadoUsuario(usuario,getContextoAnonimo());
    }
	
	@GetMapping("/listar-roles")
    public Page<Rol> listarRoles(Pageable pageable, HttpServletRequest request) {
    	return usuarioService.listarRoles(null, pageable);
    }
	
	@GetMapping("/listar-bandeja-usuario-rol")
    public Page<UsuarioRol> listarBandejaUsuarioRol(@RequestParam(value = "idUsuario",required = false) Long idUsuario,@RequestParam(value = "offset",required = false) int offset, 
    		@RequestParam(value = "pageSize",required = false) int pageSize, Pageable pageable, HttpServletRequest request) {
		
		pageable = PageRequest.of(offset, pageSize);
    	return usuarioService.listarUsuarioRol(idUsuario, pageable);
    }
	
	@PostMapping("/registrar-usuario-rol")
	public UsuarioRol registrarUsuarioRol(@RequestBody UsuarioRol usuarioRol, HttpServletRequest request) {
		logger.info("registrar {} ",usuarioRol);
		 return usuarioRolService.registrarUsuarioRol(usuarioRol, getContextoAnonimo());
	}
	
	@PostMapping("/actualizar-estado-usuario-rol")
    public void actualizarEstadoUsuarioRol(@RequestBody UsuarioRol usuarioRol, HttpServletRequest request) {
    	usuarioRolService.actualizarEstadoUsuarioRol(usuarioRol,getContextoAnonimo());
    }
	
	@GetMapping("/obtener-usuarios-evaluadores")
    public List<Usuario> obtenerUsuariosEvaluadores(@RequestParam(value = "idPerfil",required = false) Long idPerfil, HttpServletRequest request) throws Exception {
    	return usuarioService.obtenerUsuariosPorDivision(idPerfil);
    }
	
	@PostMapping("/registrar-usuario-reasignacion")
	public ReasignacionDTO registrarUsuarioReasignacion(@RequestBody ReasignacionDTO reasignacionDto, HttpServletRequest request) {
		logger.info("registrar {} ",reasignacionDto);
		 return usuarioService.registrarUsuarioReasignacion(reasignacionDto, getContextoAnonimo());
	}
		
	@PostMapping("/registrar-usuario-rol-config")
	public UsuarioRolConfiguracion registrarUsuarioRolConfiguracion(@RequestBody UsuarioRolConfiguracion usuarioRolConfiguracion, HttpServletRequest request) {
		logger.info("registrar {} ",usuarioRolConfiguracion);
		 return usuarioService.registrarUsuarioRolConfiguracion(usuarioRolConfiguracion, getContextoAnonimo());
	}
	
	@PostMapping("/actualizar-estado-usuarioRol-conf")
    public void actualizarEstadoUsuarioRolConf(@RequestBody UsuarioRolConfiguracion usuarioRolConfiguracion, HttpServletRequest request) {
		usuarioService.actualizarEstadoUsuarioRolConf(usuarioRolConfiguracion, getContextoAnonimo());
    }
	
	@GetMapping("/listar-usuarios-codigo-rol")
    public List<Usuario> listarUsuariosXCodigoRol(@RequestParam(value = "codigoRol",required = true) String codigoRol, @RequestParam(value = "idUsuario",required = true) Long idUsuario,
    		HttpServletRequest request) {
		
    	return usuarioService.listarUsuariosXCodigoRol(codigoRol,idUsuario);
    }
	
}
