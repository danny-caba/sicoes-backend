package pe.gob.osinergmin.sicoes.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Requisito;
import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface RequisitoDao extends JpaRepository<Requisito, Long> {
	
	 @Query( value = "select  ID_SECCION_REQUISITO as idRequisito,\r\n"
	 		+ "        ES_SECCION_REQUISITO as estado,\r\n"
	 		+ "        DE_SECCION_REQUISITO as descripcion,\r\n"
	 		+ "        CO_REQUISITO as codigo, \r\n"
	 		+ "        ID_TIPO_DATO_LD as tipoDatoLd, \r\n"
	 		+ "        ID_TIPO_DATO_ENTRADA_LD as idtipodatoentradaLd, \r\n"
	 		+ "        ID_TIPO_CONTRATO_LD as idTipoContrato\r\n"
	 		+ "        from ES_SICOES.SICOES_TM_SECCION_REQUISITO   order by ID_SECCION_REQUISITO asc", nativeQuery = true)
	    Page<Map<String, Object>> getRequisito(  Pageable pageable);

	@Query(value="select distinct r from Requisito r "
			+ "order by r.idSeccionRequisito ",
			countQuery ="select count(distinct r) from Requisito r "
					+ "order by r.idSeccionRequisito ")
	public Page<Requisito> buscar(Pageable pageable);

	@Query(value="select distinct r from Requisito r "
			+ "left join fetch r.seccion s "
			+ "where r.esSeccionRequisito = '1' "
			+ "order by r.idSeccionRequisito ")
	public List<Requisito> buscarRequisitos();
 
}
