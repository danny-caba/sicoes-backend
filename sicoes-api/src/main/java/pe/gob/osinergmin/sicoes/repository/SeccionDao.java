package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface SeccionDao extends JpaRepository<Seccion, Long> {
	
	 @Query( value = "select  id_seccion as idSeccion,es_seccion as esSeccion," +
			 "de_seccion as deSeccion,co_seccion as coSeccion,fl_req_personal as flReqPersonal " +
			 "from ES_SICOES.SICOES_TM_SECCION where ES_SICOES.SICOES_TM_SECCION.ES_SECCION = '1' order by id_seccion asc", nativeQuery = true)
	    Page<Map<String, Object>> getListaSeccion(  Pageable pageable);

	@Query(value="select distinct s from Seccion s "
			+ "order by s.idSeccion ",
			countQuery ="select count(distinct s) from Seccion s "
					+ "order by s.idSeccion ")
	public Page<Seccion> buscar(Pageable pageable);

}
