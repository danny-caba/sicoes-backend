package pe.gob.osinergmin.sicoes.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.util.Constantes;

@Repository
public interface SicoesSolicitudDao extends JpaRepository<SicoesSolicitud, Long> {


	@Query("SELECT s FROM SicoesSolicitud s " +
			"LEFT JOIN FETCH s.propuesta p " +
			"LEFT JOIN FETCH s.supervisora sup " +
			"LEFT JOIN FETCH s.tipoContratacion tc " +
			"WHERE s.idSolicitud = :idSolicitud")
	Optional<SicoesSolicitud> findSolicitudById(@Param("idSolicitud") Long idSolicitud);

	@Query(value = "select s from SicoesSolicitud s "
			+ "left join fetch s.propuesta p "
            + "left join fetch p.procesoItem pi "
			+ "left join fetch pi.proceso pr "
			+ "left join fetch s.supervisora su "
			+ "where su.idSupervisora = :idSupervisora "
			+ "and (:estado is null or s.estadoProcesoSolicitud = :estado) "
			+ "and (:tipoSolicitud is null or s.tipoSolicitud = :tipoSolicitud) "
			+ "and (:nroConcurso is null or pr.numeroProceso = :nroConcurso) "
			+ "and (:item is null or pi.numeroItem = :item) "
			+ "and (:convocatoria is null or pr.nombreProceso like :convocatoria) "
			+ "order by s.idSolicitud",
			countQuery = "select count(s) from SicoesSolicitud s "
					+ "left join s.propuesta p "
                    + "left join p.procesoItem pi "
					+ "left join pi.proceso pr "
					+ "left join s.supervisora su "
					+ "where su.idSupervisora = :idSupervisora "
					+ "and (:estado is null or s.estadoProcesoSolicitud = :estado) "
					+ "and (:tipoSolicitud is null or s.tipoSolicitud = :tipoSolicitud) "
					+ "and (:nroConcurso is null or pr.numeroProceso = :nroConcurso) "
					+ "and (:item is null or pi.numeroItem = :item) "
					+ "and (:convocatoria is null or pr.nombreProceso like :convocatoria) "
					+ "and (s.fechaPlazoSubsanacion is not null or s.fechaPlazoInscripcion is not null) "
					+ "order by s.idSolicitud")
	Page<SicoesSolicitud> obtenerxTipoEstadoProceso(
		String estado, String nroConcurso, Long item,
		String convocatoria, String tipoSolicitud,
		Long idSupervisora, Pageable pageable);

	@Query(value = "select s from SicoesSolicitud s "
			+ "left join fetch s.propuesta p "
            + "left join fetch p.procesoItem pi "
			+ "left join fetch pi.proceso pr "
			+ "left join fetch s.supervisora su "
			+ "where (:estado is null or s.estadoProcesoSolicitud = :estado) "
			+ "and (:tipoSolicitud is null or s.tipoSolicitud = :tipoSolicitud) "
			+ "and (:nroConcurso is null or pr.numeroProceso = :nroConcurso) "
			+ "and (:item is null or pi.numeroItem = :item) "
			+ "and (:convocatoria is null or pr.nombreProceso like :convocatoria) "
			+ "order by s.idSolicitud",
			countQuery = "select count(s) from SicoesSolicitud s "
					+ "left join s.propuesta p "
                    + "left join p.procesoItem pi "
					+ "left join pi.proceso pr "
					+ "left join s.supervisora su "
					+ "where (:estado is null or s.estadoProcesoSolicitud = :estado) "
					+ "and (:tipoSolicitud is null or s.tipoSolicitud = :tipoSolicitud) "
					+ "and (:nroConcurso is null or pr.numeroProceso = :nroConcurso) "
					+ "and (:item is null or pi.numeroItem = :item) "
					+ "and (:convocatoria is null or pr.nombreProceso like :convocatoria) "
					+ "order by s.idSolicitud")
	Page<SicoesSolicitud> obtenerxTipoEstadoProcesoInterno(
		String estado, String nroConcurso, Long item,
		String convocatoria, String tipoSolicitud, Pageable pageable);

	@Query( value = "SELECT COUNT(*) FROM ES_SICOES.SICOES_TM_REMYPE r WHERE r.ID_SUPERVISORA = :idSupervisora AND r.ES_REMYPE = '1'", nativeQuery = true)
	Integer buscarRegistroRemype(Long idSupervisora);

	@Query("select s from SicoesSolicitud s "
			+ "inner join fetch s.propuesta p "
			+ "inner join fetch p.procesoItem pi "
			+ "inner join fetch pi.proceso pr "
			+ "inner join fetch s.supervisora su "
			+ "where s.idSolicitud = :idSolicitud "
			+ "order by s.idSolicitud")
	SicoesSolicitud obtenerSolicitudDetallado(Long idSolicitud);

	@Query(value="select s from SicoesSolicitud s "
			+ "where s.estadoProcesoSolicitud='"+Constantes.ESTADO_PROCESO_PERF_CONTRATO.OBSERVADO+"' "
			+ "and s.estado='1' and s.idSolicitudPadre is not null and s.fechaPlazoSubsanacion is null")
	List<SicoesSolicitud> listarSolicitudesPorNotificar();

	@Query("select s from SicoesSolicitud s "
			+ "where s.idSolicitudPadre=:idSolicitud")
	SicoesSolicitud obtenerPorIdSolicitudPadre(Long idSolicitud);

	@Query(value="select s from SicoesSolicitud s "
			+ "where s.estadoProcesoSolicitud='"+Constantes.ESTADO_PROCESO_PERF_CONTRATO.PRELIMINAR+"' "
			+ "and s.tipoSolicitud='"+Constantes.TIPO_SOLICITUD_PERF_CONTRATO.INSCRIPCION+"' "
			+ "and s.fechaHoraPresentacion is null "
			+ "and trunc(s.fechaPlazoInscripcion) < trunc(sysdate) "
			+ "and s.estado='1'")
	List<SicoesSolicitud>listarSolicitudesPorInscripcion();

	@Query(value="select s from SicoesSolicitud s "
			+ "where s.estadoProcesoSolicitud='"+Constantes.ESTADO_PROCESO_PERF_CONTRATO.PRELIMINAR+"' "
			+ "and s.tipoSolicitud='"+Constantes.TIPO_SOLICITUD_PERF_CONTRATO.SUBSANACION+"' "
			+ "and s.fechaHoraPresentacion is null "
			+ "and trunc(s.fechaPlazoSubsanacion) < trunc(sysdate) "
			+ "and s.estado='1'")
	List<SicoesSolicitud>listarSolicitudesPorSubsanacion();
	
	@Modifying
    @Transactional
    @Query(value = "UPDATE SICOES_TC_SOLI_PERF_CONT SET id_doc_inicio = 1 WHERE id_soli_perf_cont IN " +
                   "(SELECT c.id_soli_perf_cont FROM SICOES_TD_CONTRATO c WHERE id_contrato = :idContrato)", nativeQuery = true)
    int actualizarIdDocInicioPorContrato(Long idContrato);

}
