package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.Adenda;

@Repository
public interface AdendaDao extends JpaRepository<Adenda, Long> {

@Query("select a from Adenda a "
        + "where  a.idDocumento  =:documento")
	public Adenda obtenerAdenda(Long documento);
}