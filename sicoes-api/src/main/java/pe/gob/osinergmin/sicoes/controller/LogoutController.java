package pe.gob.osinergmin.sicoes.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

    @Autowired
    private TokenStore tokenStore;
    
    
    
    
    @RequestMapping("/api/login")
    public ResponseEntity<Respuesta> ingresar(HttpServletRequest request) {
        try {
        	
        		String tokenSOL = request.getHeader("xxyyxxx");
            	
        		
            } catch (Exception e) {
            return new ResponseEntity<Respuesta>(new Respuesta("Invalid access token"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Respuesta>(new Respuesta("Access token invalidated successfully"), HttpStatus.OK);
    }
   
    @RequestMapping("/oauth/logout")
    public ResponseEntity<Respuesta> revoke(HttpServletRequest request) {
        try {
            String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.contains("Bearer")) {
                String tokenValue = authorization.replace("Bearer", "").trim();

                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);

                OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
                tokenStore.removeRefreshToken(refreshToken);
            }
        } catch (Exception e) {
            return new ResponseEntity<Respuesta>(new Respuesta("Invalid access token"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Respuesta>(new Respuesta("Access token invalidated successfully"), HttpStatus.OK);
    }
}

class Respuesta{
	private String msg;
	public Respuesta(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}

