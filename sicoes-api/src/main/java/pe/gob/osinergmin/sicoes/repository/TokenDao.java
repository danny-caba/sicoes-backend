package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Token;

@Repository
public interface TokenDao extends JpaRepository<Token, Long> {
	
	@Query("select t from Token t "
			+ "left join fetch t.tipo tt "		
			+ "left join fetch t.estado e "
//			+ "left join fetch t.usuario u "
		+ "where t.idToken=:idToken")	
	public Token obtener(Long idToken);
	
	@Query("select t from Token t "
			+ "left join fetch t.tipo tt "		
			+ "left join fetch t.estado e "
//			+ "left join fetch t.usuario u "
			+ "where t.codigo =:codigo ")
	Token obtenerToken(String codigo);
}
