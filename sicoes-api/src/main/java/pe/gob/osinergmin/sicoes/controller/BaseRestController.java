package pe.gob.osinergmin.sicoes.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

public class BaseRestController {
	
	private Logger logger = LogManager.getLogger(BaseRestController.class);
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public Usuario getUsuario() {
		
		Usuario usuario = null;
//		String produccion = env.getProperty("produccion");
		try {
//			if (Constantes.FLAG.ACTIVO.toString().equals(produccion)) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (authentication != null && authentication.getPrincipal() instanceof User) {
					String username = ((User) authentication.getPrincipal()).getUsername();
					usuario = usuarioService.buscarUsuario(username);
				} else {
					// Usuario simulado para entorno local
					usuario = new Usuario();
					usuario.setIdUsuario(999L);
					usuario.setCorreo("dev@local.test");
				}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return usuario;
	}

	public Contexto getContexto() {
		Contexto contexo = new Contexto();
		contexo.setUsuario(getUsuario());
		Usuario usuarioBD=usuarioService.obtenerUsuario(contexo);
		contexo.setUsuario(usuarioBD);
		contexo.setIp(getClientIp());
		contexo.setAplicacion(request.getHeader("application"));
		return contexo;
	}
	
	public Contexto getContextoAnonimo() {
		Contexto contexo = new Contexto();		
		contexo.setAplicacion(request.getHeader("application"));
		contexo.setUsuarioApp("ASAP");
		contexo.setIp(getClientIp());
		return contexo;
	}
	
	private final String LOCALHOST_IPV4 = "127.0.0.1";
    private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
 
    
    public String getClientIp() {
 
        String ipAddress = request.getHeader("X-Forwarded-For");
 
        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
 
        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
 
        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ipAddress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }
 
        if (!StringUtils.isEmpty(ipAddress)
                && ipAddress.length() > 15
                && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }
 
        return ipAddress;
    }
}
