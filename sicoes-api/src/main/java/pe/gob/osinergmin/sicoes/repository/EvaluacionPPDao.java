package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.EvaluacionDocumentacionPP;

import java.util.Optional;

@Repository
public interface EvaluacionPPDao extends JpaRepository<EvaluacionDocumentacionPP, Long> {

    @Query(value = "WITH LISTADOS AS ( " +
            "    SELECT \n" +
            "        TO_NUMBER(LIDE.ID_LISTADO_DETALLE) AS ID, " +
            "        LIDE.NO_LISTADO_DETALLE AS VALOR, " +
            "        LI.CD_LISTADO " +
            "    FROM SICOES_TM_LISTADO_DETALLE LIDE " +
            "    INNER JOIN SICOES_TM_LISTADO LI " +
            "        ON LIDE.ID_LISTADO = LI.ID_LISTADO " +
            ") " +
            "SELECT \n" +
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
            "    REPE.FE_FECHA_BAJA AS FECHA_BAJA, " +
            "    REPE.FE_FECHA_FINALIZACION_CONTRATO AS FECHA_FINALIZACION_CONTRATO " +
            "FROM SICOES_TZ_REEMPLAZO_PERSONAL  REPE " +
            "LEFT JOIN SICOES_TM_SUPERVISORA SUP  " +
            "    ON REPE.ID_PERSONA_PROPUESTA = SUP.ID_SUPERVISORA " +
            "LEFT JOIN LISTADOS TIPO_DOC  " +
            "    ON SUP.ID_TIPO_DOCUMENTO_LD = TIPO_DOC.ID AND TIPO_DOC.CD_LISTADO = 'TIPO_DOCUMENTO' " +
            "WHERE (:id IS NULL OR REPE.ID_REEMPLAZO_PERSONAL = :id) " +
            "AND (:idsol IS NULL OR REPE.ID_SOLI_PERF_CONT = :idsol) ", nativeQuery = true)
    public Optional<EvaluacionDocumentacionPP> obtenerListadoPP(@Param("id") Long id, @Param("idsol") Long idsol);
}