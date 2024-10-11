package pe.gob.osinergmin.sicoes.consumer;

import pe.gob.osinergmin.sicoes.model.Usuario;

public interface SissegApiConsumer {
	
	public String obtenerAccessToken(String token) throws Exception;
	
	public Long obtenerIdUsuario(String username);
	
	public Usuario obtenerUsuario(Long idUsuario, String token) throws Exception;
}
