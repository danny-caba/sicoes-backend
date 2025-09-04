package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.Adenda;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdendaDao extends JpaRepository<Adenda, Long> {

 @Query(value= "  SELECT * FROM  SICOES_TZ_ADENDA_REEMP  ADEND " +
		  "LEFT JOIN SICOES_TZ_REEMPLAZO_PERSONAL REEMP " +
		  "ON REEMP.ID_REEMPLAZO_PERSONAL = ADEND.ID_REEMPLAZO_PERSONAL " +
		  "WHERE ( ADEND.ID_REEMPLAZO_PERSONAL = :idreemplazo ) " +
		  "ORDER BY ADEND.FE_CREACION DESC", nativeQuery = true)
  List<Adenda> obtenerAdenda(@Param("idreemplazo") Long idreemplazo);


}