package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.DocumentoPP;

import java.util.List;

@Repository
public interface DocumentoPPDao extends JpaRepository<DocumentoPP, Long> {

    @Query(value = " WITH LISTADOS AS " +
            "             (  " +
            "             SELECT   " +
            "                 TO_NUMBER(LIDE.ID_LISTADO_DETALLE) AS ID,  " +
            "                 LIDE.NO_LISTADO_DETALLE AS VALOR,  " +
            "                 LI.CD_LISTADO  " +
            "             FROM SICOES_TM_LISTADO_DETALLE LIDE  " +
            "             INNER JOIN SICOES_TM_LISTADO LI   " +
            "                 ON LIDE.ID_LISTADO = LI.ID_LISTADO  " +
            "            )SELECT   " +
            "                 DOCREM.ID_DOCUMENTO AS ID, " +
            "                 EVALDOC.ID_EVAL_DOCUMENTO AS ID_EVAL_DOCUMENTO, " +
            "                 DOCREM.ID_TIPO_DOCUMENTO AS ID_TIPO_DOCUMENTO,  " +
            "                 TIPO_DOC.VALOR AS TIPO_DOCUMENTO, " +
            "                 DOCREM.DE_NOMBRE_DOCUMENTO AS NOMBRE_DOCUMENTO, " +
            "                 EVALDOC.CO_CONFORME AS CONFORMIDAD, " +
            "                 EVALDOC.ID_EVALUADO_POR AS ID_USUARIO_EVALUACION, " +
            "                 USU.DE_USUARIO AS USUARIO_EVALUACION, " +
            "                 EVALDOC.FE_FECHA_EVALUACION AS FECHA_EVALUACION " +
            "            FROM SICOES_TZ_DOCUMENTO_REEMP DOCREM " +
            "            LEFT JOIN LISTADOS TIPO_DOC  " +
            "                 ON DOCREM.ID_TIPO_DOCUMENTO = TIPO_DOC.ID " +
            "                 AND TIPO_DOC.CD_LISTADO = 'DOCUMENTOS_PERSONAL_PROPUESTO'  " +
            "            LEFT JOIN SICOES_TZ_EVALUAR_DOCU_REEMP EVALDOC " +
            "                ON  DOCREM.ID_DOCUMENTO = EVALDOC.ID_DOCUMENTO " +
            "            LEFT JOIN SICOES_TM_USUARIO USU " +
            "                ON  EVALDOC.ID_EVALUADO_POR = USU.ID_USUARIO " +
            "            WHERE DOCREM.ID_REEMPLAZO_PERSONAL = :id " +
            "            AND DOCREM.ID_SECCION = :idseccion ", nativeQuery = true)
    public List<DocumentoPP> buscarDocumentoPP(@Param("id") Long id, @Param("idseccion") Long idseccion);
}
