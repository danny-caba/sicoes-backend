package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.Token;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface TokenService extends BaseService<Token, Long> {

	public Token obtener(Long idToken,Contexto contexto);

}