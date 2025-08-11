package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.EvaluacionDocInicioServ;

@Repository
public interface EvaluacionDocInicioServDao extends JpaRepository<EvaluacionDocInicioServ, Long> {

    @Query(value = " WITH  " +
            "DOCS_COUNT AS ( " +
            "    SELECT " +
            "        ID_REEMPLAZO_PERSONAL, " +
            "        COUNT(*) AS NU_DOCS " +
            "    FROM SICOES_TZ_DOCUMENTO_REEMP " +
            "    GROUP BY ID_REEMPLAZO_PERSONAL " +
            ") " +
            "SELECT  " +
            "    REPE.ID_REEMPLAZO_PERSONAL AS ID, " +
            "    SUP.NU_DOCUMENTO AS NUMERO_DOCUMENTO, " +
            "    REPE.ID_PERSONA_PROPUESTA AS ID_PERSONA, " +
            "    TRIM( " +
            "            NVL(SUP.NO_PERSONA, '') || ' ' || " +
            "            NVL(SUP.AP_PATERNO, '') || ' ' || " +
            "            NVL(SUP.AP_MATERNO, '') " +
            "        ) AS PERSONA, " +
            "    REPE.CO_PERFIL AS PERFIL, " +
            "    NVL(DOCS.NU_DOCS, 0) AS NU_DOCS, " +
            "    REPE.FE_FECHA_INICIO_CONTRACTUAL AS FECHA_INICIO_CONTRATO " +
            "FROM SICOES_TZ_REEMPLAZO_PERSONAL  REPE " +
            "LEFT JOIN SICOES_TM_SUPERVISORA SUP " +
            "    ON REPE.ID_PERSONA_PROPUESTA = SUP.ID_SUPERVISORA " +
            "LEFT JOIN DOCS_COUNT DOCS " +
            "    ON REPE.ID_REEMPLAZO_PERSONAL = DOCS.ID_REEMPLAZO_PERSONAL " +
            "WHERE (:id IS NULL OR REPE.ID_REEMPLAZO_PERSONAL = :id) ", nativeQuery = true)
    public EvaluacionDocInicioServ buscarEvalDocInicioServ(@Param("id") Long id);
}