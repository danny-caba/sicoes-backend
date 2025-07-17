package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumento;

import java.util.Date;
import java.util.Optional;

@Repository
public interface RequerimientoDocumentoDao extends JpaRepository<RequerimientoDocumento, Long> {

    @Query(value = "SELECT DISTINCT rd FROM RequerimientoDocumento rd " +
            "LEFT JOIN FETCH rd.requerimiento r " +
            "LEFT JOIN FETCH r.division d " +
            "LEFT JOIN FETCH rd.estado e " +
            "LEFT JOIN r.supervisora s " +
            "WHERE s.idSupervisora = :idSupervisora " +
            "AND (:idEstado IS NULL OR e.idListadoDetalle = :idEstado) " +
            "AND (:fechaInicio IS NULL OR r.feRegistro >= :fechaInicio) " +
            "AND (:fechaFin IS NULL OR r.feRegistro <= :fechaFin) " +
            "ORDER BY r.nuExpediente ASC",
            countQuery = "SELECT COUNT(rd) FROM RequerimientoDocumento rd " +
                    "LEFT JOIN rd.requerimiento r " +
                    "LEFT JOIN r.division d " +
                    "LEFT JOIN rd.estado e " +
                    "LEFT JOIN r.supervisora s " +
                    "WHERE s.idSupervisora = :idSupervisora " +
                    "AND (:idEstado IS NULL OR e.idListadoDetalle = :idEstado) " +
                    "AND (:fechaInicio IS NULL OR r.feRegistro >= :fechaInicio) " +
                    "AND (:fechaFin IS NULL OR r.feRegistro <= :fechaFin) ")
    Page<RequerimientoDocumento> listarRequerimientosDocumentos(Long idSupervisora, Long idEstado, Date fechaInicio, Date fechaFin, Pageable pageable);

    @Query(value = "SELECT DISTINCT rd FROM RequerimientoDocumento rd " +
            "LEFT JOIN FETCH rd.requerimiento r " +
            "LEFT JOIN FETCH r.division d " +
            "LEFT JOIN FETCH r.perfil p " +
            "LEFT JOIN FETCH rd.estado e " +
            "LEFT JOIN FETCH r.supervisora s " +
            "LEFT JOIN FETCH d.usuario u " +
            "LEFT JOIN FETCH rd.contrato c " +
            "WHERE u.idUsuario = :usuario " +
            "AND (:idDivision IS NULL OR d.idDivision = :idDivision) " +
            "AND (:idPerfil IS NULL OR p.idListadoDetalle = :idPerfil) " +
            "AND (:idSupervisora IS NULL OR s.idSupervisora = :idSupervisora) " +
            "AND (:idEstado IS NULL OR e.idListadoDetalle = :idEstado) " +
            "AND (:fechaInicio IS NULL OR r.feRegistro >= :fechaInicio) " +
            "AND (:fechaFin IS NULL OR r.feRegistro <= :fechaFin) " +
            "ORDER BY r.nuExpediente ASC",
            countQuery = "SELECT COUNT(DISTINCT rd) FROM RequerimientoDocumento rd " +
                    "LEFT JOIN rd.requerimiento r " +
                    "LEFT JOIN r.division d " +
                    "LEFT JOIN r.perfil p " +
                    "LEFT JOIN rd.estado e " +
                    "LEFT JOIN r.supervisora s " +
                    "LEFT JOIN d.usuario u " +
                    "LEFT JOIN rd.contrato c " +
                    "WHERE u.idUsuario = :usuario " +
                    "AND (:idDivision IS NULL OR d.idDivision = :idDivision) " +
                    "AND (:idPerfil IS NULL OR p.idListadoDetalle = :idPerfil) " +
                    "AND (:idSupervisora IS NULL OR s.idSupervisora = :idSupervisora) " +
                    "AND (:idEstado IS NULL OR e.idListadoDetalle = :idEstado) " +
                    "AND (:fechaInicio IS NULL OR r.feRegistro >= :fechaInicio) " +
                    "AND (:fechaFin IS NULL OR r.feRegistro <= :fechaFin) ")
    Page<RequerimientoDocumento> listarRequerimientosDocumentosCoordinador(Long usuario, Long idDivision, Long idPerfil, Long idSupervisora, Long idEstado, Date fechaInicio, Date fechaFin, Pageable pageable);

    @Query("select rd.idRequerimientoDocumento from RequerimientoDocumento rd where rd.requerimientoDocumentoUuid=:requerimientoDocumentoUuid")
    Long obtenerId(String requerimientoDocumentoUuid);

}
