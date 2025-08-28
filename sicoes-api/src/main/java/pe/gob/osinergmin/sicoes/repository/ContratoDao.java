package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Contrato;
import pe.gob.osinergmin.sicoes.model.SicoesTdSolPerConSec;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContratoDao extends JpaRepository<Contrato, Long> {

    @Query(" SELECT c FROM Contrato c WHERE c.solicitudPerfCont.idSolicitud = :idSoliPerfCont ")
    Optional<Contrato> findBySolicitudPerfContId(@Param("idSoliPerfCont") Long idSoliPerfCont);
    

	@Query(value = "select c from Contrato c " + "inner join fetch c.solicitudPerfCont s "
			+ "inner join fetch s.propuesta p " + "inner join fetch p.supervisora su "
			+ "inner join fetch p.procesoItem pi " + "inner join fetch pi.proceso pr "
			+ "inner join fetch c.estado est " + "left join fetch c.estadoEvalLog elog "
			+ "left join fetch c.estadoEvalGaf egaf " + "left join fetch c.estadoEvalUni euni "
			+ "left join fetch c.estadoEvalApr eapr "
			+ "where (:expediente is null or lower(pr.numeroExpediente) like lower(concat('%', :expediente, '%'))) "
			+ "and (:contratista is null or lower(su.nombreRazonSocial) like lower(concat('%', :contratista, '%'))) "
			+ "and (:tipoContrato is null or c.estadoContrato = :tipoContrato) "
			+ "and (:areaSolicitante is null or lower(pr.nombreArea) like lower(concat('%', :areaSolicitante, '%'))) "
			+ "order by c.idContrato", countQuery = "select count(c) from Contrato c "
					+ "inner join c.solicitudPerfCont s " + "inner join s.propuesta p " + "inner join p.supervisora su "
					+ "inner join p.procesoItem pi " + "inner join pi.proceso pr " + "left join c.estado est "
					+ "left join c.estadoEvalLog elog " + "left join c.estadoEvalGaf egaf "
					+ "left join c.estadoEvalUni euni " + "left join c.estadoEvalApr eapr "
					+ "where (:expediente is null or lower(pr.numeroExpediente) like lower(concat('%', :expediente, '%'))) "
					+ "and (:contratista is null or lower(su.nombreRazonSocial) like lower(concat('%', :contratista, '%'))) "
					+ "and (:tipoContrato is null or c.estadoContrato = :tipoContrato) "
					+ "and (:areaSolicitante is null or lower(pr.nombreArea) like lower(concat('%', :areaSolicitante, '%'))) "
					+ "order by c.idContrato")
	Page<Contrato> obtenerContratos(String expediente, String contratista, String tipoContrato, String areaSolicitante,
			Pageable pageable);

	@Query(value = "select c from Contrato c " + "inner join fetch c.solicitudPerfCont s "
			+ "inner join fetch s.propuesta p " + "inner join fetch p.supervisora su "
			+ "inner join fetch p.procesoItem pi " + "inner join fetch pi.proceso pr "
			+ "where (:expediente is null or lower(pr.numeroExpediente) like lower(concat('%', :expediente, '%'))) "
			+ "and (:contratista is null or lower(su.nombreRazonSocial) like lower(concat('%', :contratista, '%'))) "
			+ "and (:tipoContrato is null or c.estadoContrato = :tipoContrato) "
			+ "and (:areaSolicitante is null or lower(pr.nombreArea) like lower(concat('%', :areaSolicitante, '%'))) "
			+ "order by c.idContrato", countQuery = "select count(c) from Contrato c "
					+ "inner join c.solicitudPerfCont s " + "inner join s.propuesta p " + "inner join p.supervisora su "
					+ "inner join p.procesoItem pi " + "inner join pi.proceso pr "
					+ "where (:expediente is null or lower(pr.numeroExpediente) like lower(concat('%', :expediente, '%'))) "
					+ "and (:contratista is null or lower(su.nombreRazonSocial) like lower(concat('%', :contratista, '%'))) "
					+ "and (:tipoContrato is null or c.estadoContrato = :tipoContrato) "
					+ "and (:areaSolicitante is null or lower(pr.nombreArea) like lower(concat('%', :areaSolicitante, '%'))) "
					+ "order by c.idContrato")
	Page<Contrato> obtenerContratosAprobados(String expediente, String contratista, String tipoContrato,
			String areaSolicitante, Pageable pageable);

	@Modifying
	@Transactional
	@Query("UPDATE Contrato c SET c.numeroContrato = :numeroContrato, c.fechaSuscripcionContrato = :fechaSuscripcionContrato, c.fechaInicioContrato = :fechaInicioContrato, c.fechaFinalContrato = :fechaFinalContrato WHERE c.idContrato = :idContrato")
	int actualizarContrato(Long idContrato, String numeroContrato, Date fechaSuscripcionContrato,
			Date fechaInicioContrato, Date fechaFinalContrato);

}
