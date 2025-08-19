package pe.gob.osinergmin.sicoes.config.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.service.UsuarioService;

@Component
public class InfoAdicionalToken implements TokenEnhancer{
	
	@Autowired
	UsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		Usuario usuario = usuarioService.buscarUsuario(authentication.getName());
//		info.put("idPerfil", usuario.getIdPerfil());
		info.put("idUsuario", usuario.getIdUsuario());
//		info.put("nombreSesion", usuario.getNombreSesion());
//		info.put("idSede", usuario.getIdSede());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}
}
