package pe.gob.osinergmin.sicoes.service.impl;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Token;
import pe.gob.osinergmin.sicoes.repository.TokenDao;
import pe.gob.osinergmin.sicoes.service.TokenService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;


@Service
public class TokenServiceImpl implements TokenService{
	
	Logger logger = LogManager.getLogger(TokenServiceImpl.class);

	@Autowired
	private TokenDao tokenDao;
	
	@Override
	public Token obtener(Long idToken, Contexto contexto) {
		Token token= tokenDao.obtener(idToken);
		return token;
	}

	@Override
	public Token guardar(Token token, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(token,contexto);
		Token tokenBD=tokenDao.save(token);
		return tokenBD;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		tokenDao.deleteById(id);
		
	}
	
}
