package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.EvaluacionDocumentacion;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluacionDocumentacionDao extends JpaRepository<EvaluacionDocumentacion, Long> {

    @Query(value = " WITH LISTADOS AS ( " +
            "    SELECT " +
            "        TO_NUMBER(LIDE.ID_LISTADO_DETALLE) AS ID, " +
            "        LIDE.NO_LISTADO_DETALLE AS VALOR, " +
            "        LI.CD_LISTADO " +
            "    FROM SICOES_TM_LISTADO_DETALLE LIDE " +
            "    INNER JOIN SICOES_TM_LISTADO LI " +
            "        ON LIDE.ID_LISTADO = LI.ID_LISTADO " +
            ") " +
            "SELECT " +
            "    REPE.ID_REEMPLAZO_PERSONAL AS ID, " +
            "    REPE.ID_SOLI_PERF_CONT AS ID_SOLICITUD, " +
            "    TIPO_DOC.VALOR AS TIPO_DOCUMENTO, " +
            "    SUP.NU_DOCUMENTO AS NUMERO_DOCUMENTO, " +
            "    REPE.ID_PERSONA_PROPUESTA AS ID_PERSONA, " +
            "    TRIM( " +
            "            NVL(SUP.NO_PERSONA, '') || ' ' || " +
            "            NVL(SUP.AP_PATERNO, '') || ' ' || " +
            "            NVL(SUP.AP_MATERNO, '') " +
            "        ) AS PERSONA, " +
            "    REPE.CO_PERFIL AS PERFIL, " +
            "    REPE.FE_FECHA_REGISTRO AS FECHA_REGISTRO, " +
            "    REPE.ES_ESTADO_REEMPLAZO AS ID_ESTADO_EVAL_DOC_PERS_REEMP, " +
            "    ESTADO_REEMP.VALOR  AS ESTADO_EVAL_DOC_PERS_REEMP, " +
            "    REPE.ES_ESTADO_APROBACION_INFORME AS ID_ESTADO_APROBACION_INFORME, " +
            "    ESTADO_APROB_INF.VALOR  AS ESTADO_APROBACION_INFORME, " +
            "    REPE.ES_ESTADO_APROBACION_ADENDA AS ID_ESTADO_APROBACION_ADENDA, " +
            "    ESTADO_APROB_ADEN.VALOR  AS ESTADO_APROBACION_ADENDA, " +
            "    REPE.ES_ESTADO_EVAL_DOC_INI_SERV AS ID_ESTADO_EVAL_DOC_INI_SERV, " +
            "    ESTADO_EVAL_DOC.VALOR  AS ESTADO_EVAL_DOC_INI_SERV " +
            "FROM SICOES_TZ_REEMPLAZO_PERSONAL  REPE " +
            "LEFT JOIN SICOES_TM_SUPERVISORA SUP " +
            "    ON REPE.ID_PERSONA_PROPUESTA = SUP.ID_SUPERVISORA " +
            "LEFT JOIN LISTADOS ESTADO_REEMP " +
            "    ON REPE.ES_ESTADO_REEMPLAZO = ESTADO_REEMP.ID AND ESTADO_REEMP.CD_LISTADO = 'ESTADO_SOLICITUD' " +
            "LEFT JOIN LISTADOS ESTADO_APROB_INF " +
            "    ON REPE.ES_ESTADO_APROBACION_INFORME = ESTADO_APROB_INF.ID AND ESTADO_APROB_INF.CD_LISTADO = 'ESTADO_SOLICITUD' " +
            "LEFT JOIN LISTADOS ESTADO_APROB_ADEN " +
            "    ON REPE.ES_ESTADO_APROBACION_ADENDA = ESTADO_APROB_ADEN.ID AND ESTADO_APROB_ADEN.CD_LISTADO = 'ESTADO_SOLICITUD' " +
            "LEFT JOIN LISTADOS ESTADO_EVAL_DOC " +
            "    ON REPE.ES_ESTADO_EVAL_DOC_INI_SERV = ESTADO_EVAL_DOC.ID AND ESTADO_EVAL_DOC.CD_LISTADO = 'ESTADO_SOLICITUD' " +
            "LEFT JOIN LISTADOS TIPO_DOC " +
            "    ON SUP.ID_TIPO_DOCUMENTO_LD = TIPO_DOC.ID AND TIPO_DOC.CD_LISTADO = 'TIPO_DOCUMENTO' "+
            "    WHERE (:id IS NULL OR REPE.ID_REEMPLAZO_PERSONAL = :id) " +
            "    AND ( :param1 IS NULL OR REPE.ES_ESTADO_REEMPLAZO = :param1 ) "
            , nativeQuery = true)
    public List<EvaluacionDocumentacion> obtenerListado(@Param("id") Long id , @Param("param1") Long param1);
}