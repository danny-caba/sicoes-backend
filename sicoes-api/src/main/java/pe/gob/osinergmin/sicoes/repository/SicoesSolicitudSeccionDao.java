package pe.gob.osinergmin.sicoes.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.ProcesoConsulta;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitudAdjunto;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitudSeccion;
import pe.gob.osinergmin.sicoes.model.SicoesTdSolPerConSec;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Repository
public interface SicoesSolicitudSeccionDao extends JpaRepository<SicoesSolicitudSeccion, Long> {

	@Transactional
	@Modifying
	@Query("update SicoesSolicitudSeccion soli set soli.estado = :estado, soli.ipActualizacion = :ip, soli.usuActualizacion = :usuario, soli.fecActualizacion = :fecha where soli.idSolicitudSeccion = :id")
	int setEstadoxSolicitud(@Param("estado") String estado, @Param("id") Long id, @Param("ip") String ip, @Param("usuario") String usuario, @Param("fecha") Date fecha);

	@Query(value="select ss from SicoesSolicitudSeccion ss "
			+ "inner join fetch ss.requisito r "
			+ "inner join fetch r.tipoContrato tc "
			+ "left join fetch ss.usuarioEvaluacion ue "
			+ "where (:idSeccion = ss.idPerConSec) "
			+ "and ss.estado = '1' "
			+ "and (tc.idListadoDetalle = :tipoContrato or tc.codigo like 'AMBOS') "
			+ "order by ss.idSolicitudSeccion ",
			countQuery="select count(ss) from SicoesSolicitudSeccion ss "
					+ "inner join ss.requisito r "
					+ "inner join r.tipoContrato tc "
					+ "left join ss.usuarioEvaluacion ue "
					+ "where (:idSeccion = ss.idPerConSec) "
					+ "and ss.estado = '1' "
					+ "and (tc.idListadoDetalle = :tipoContrato or tc.codigo like 'AMBOS') ")
	Page<SicoesSolicitudSeccion> obtenerRequisitosPorSeccion(Long idSeccion, Long tipoContrato, Pageable pageable);

	@Query(value="select ss from SicoesSolicitudSeccion ss "
			+ "inner join fetch ss.requisito r "
			+ "inner join fetch r.tipoContrato tc "
			+ "inner join fetch ss.personalPropuesto pp "
			+ "where (:idSoliPersProp = pp.idSoliPersProp) "
			+ "and ss.estado = '1' "
			+ "and (tc.idListadoDetalle = :tipoContrato or tc.codigo like 'AMBOS') "
			+ "order by ss.idSolicitudSeccion ",
			countQuery="select count(ss) from SicoesSolicitudSeccion ss "
					+ "inner join ss.requisito r "
					+ "inner join r.tipoContrato tc "
					+ "inner join ss.personalPropuesto pp "
					+ "where (:idSoliPersProp = pp.idSoliPersProp) "
					+ "and ss.estado = '1' "
					+ "and (tc.idListadoDetalle = :tipoContrato or tc.codigo like 'AMBOS') ")
	Page<SicoesSolicitudSeccion> obtenerRequisitosPorPersonal(Long idSoliPersProp, Long tipoContrato, Pageable pageable);

	@Query("select ss from SicoesSolicitudSeccion ss "
			+ "inner join fetch ss.requisito r "
			+ "inner join fetch r.tipoContrato tc "
			+ "inner join fetch ss.personalPropuesto pp "
			+ "where (:idSoliPersProp = pp.idSoliPersProp) "
			+ "and ss.estado = '1' "
			+ "and (tc.idListadoDetalle = :tipoContrato or tc.codigo like 'AMBOS') "
			+ "order by ss.idSolicitudSeccion ")
	List<SicoesSolicitudSeccion> obtenerRequisitosPorPersonalV2(Long idSoliPersProp, Long tipoContrato);

	@Query("select ss from SicoesSolicitudSeccion ss "
			+ "inner join fetch ss.requisito r "
			+ "left join fetch ss.usuarioEvaluacion ue "
			+ "where (:idSeccion = ss.idPerConSec) "
			+ "and ss.flagRequisito = '1' "
			+ "order by ss.idSolicitudSeccion")
	List<SicoesSolicitudSeccion> obtenerRequisitosPorSeccionSub(Long idSeccion);

	@Query("select ss from SicoesSolicitudSeccion ss "
			+ "inner join fetch ss.requisito r "
			+ "left join fetch ss.usuarioEvaluacion ue "
			+ "where ss.idPerConSec IN :seccionIds "
			+ "and ss.flagRequisito = '1' "
			+ "order by ss.idSolicitudSeccion")
	List<SicoesSolicitudSeccion> obtenerRequisitosPorSeccionFinalizacion(List<Long> seccionIds);

	@Query("select ss from SicoesSolicitudSeccion ss "
			+ "inner join fetch ss.requisito r "
			+ "where (:idSeccion = ss.idPerConSec) "
			+ "and ss.flagRequisito = '1' "
			+ "order by ss.idSolicitudSeccion")
	List<SicoesSolicitudSeccion> obtenerRequisitosPorSeccionConPersonal(Long idSeccion);

	@Query("select ss from SicoesSolicitudSeccion ss "
			+ "inner join fetch ss.requisito r "
			+ "inner join fetch ss.personalPropuesto pp "
			+ "where (:idSoliPersProp = pp.idSoliPersProp) "
			+ "order by ss.idSolicitudSeccion")
	List<SicoesSolicitudSeccion> obtenerRequisitosPorPersonalSub(Long idSoliPersProp);

	@Query(value="select ss from SicoesSolicitudSeccion ss "
			+ "inner join fetch ss.requisito r "
			+ "inner join fetch r.tipoContrato tc "
			+ "inner join fetch ss.personalPropuesto pp "
			+ "inner join fetch pp.supervisora s "
			+ "where (:idSeccion = ss.idPerConSec) "
			+ "and ss.estado = '1' "
			+ "and (tc.idListadoDetalle = :tipoContrato or tc.codigo like 'AMBOS') "
			+ "and pp.idSoliPersProp <> null "
			+ "and ss.flagRequisito = '1' "
			+ "order by ss.idSolicitudSeccion ",
			countQuery="select count(ss) from SicoesSolicitudSeccion ss "
					+ "inner join ss.requisito r "
					+ "inner join r.tipoContrato tc "
					+ "inner join ss.personalPropuesto pp "
					+ "inner join pp.supervisora s "
					+ "where (:idSeccion = ss.idPerConSec) "
					+ "and ss.estado = '1' "
					+ "and (tc.idListadoDetalle = :tipoContrato or tc.codigo like 'AMBOS') "
					+ "and pp.idSoliPersProp <> null "
					+ "and ss.flagRequisito = '1' ")
	Page<SicoesSolicitudSeccion> obtenerRequisitosConFlagActivo(Long idSeccion, Long tipoContrato, Pageable pageable);

	@Query("select ss from SicoesSolicitudSeccion ss "
			+ "where ss.idPerConSec IN :seccionesIds "
			+ "order by ss.idSolicitudSeccion")
	List<SicoesSolicitudSeccion> obtenerRequisitosPorSecciones(List<Long> seccionesIds);

	@Query("select ss from SicoesSolicitudSeccion ss "
			+ "left join fetch ss.personalPropuesto pp "
			+ "where (:idSeccion = ss.idPerConSec) "
			+ "and ss.estado = '1' "
			+ "and ss.procRevision <> '0'"
			+ "and ss.procSubsanacion = :tipoRequisitoEvaluado "
			+ "and ss.flagRequisito = '1' "
			+ "order by ss.idSolicitudSeccion")
	List<SicoesSolicitudSeccion> obtenerRequisitosEvaluados(Long idSeccion, String tipoRequisitoEvaluado);

	@Query("select ss from SicoesSolicitudSeccion ss "
			+ "inner join fetch ss.personalPropuesto pp "
			+ "where (:idSoliPersProp = pp.idSoliPersProp) "
			+ "and ss.flagRequisito = '1' "
			+ "order by ss.idSolicitudSeccion")
	List<SicoesSolicitudSeccion> obtenerRequisitosPorPersonalActivo(Long idSoliPersProp);

}
