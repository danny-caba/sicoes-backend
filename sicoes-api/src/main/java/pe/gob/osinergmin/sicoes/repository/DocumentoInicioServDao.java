package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.DocumentoInicioServ;

import java.util.List;

@Repository
public interface DocumentoInicioServDao extends JpaRepository<DocumentoInicioServ, Long> {

    @Query(value = "WITH LISTADOS AS " +
            "             (  " +
            "             SELECT  " +
            "                 TO_NUMBER(LIDE.ID_LISTADO_DETALLE) AS ID, " +
            "                 LIDE.NO_LISTADO_DETALLE AS VALOR,  " +
            "                 LI.CD_LISTADO  " +
            "             FROM SICOES_TM_LISTADO_DETALLE LIDE  " +
            "             INNER JOIN SICOES_TM_LISTADO LI  " +
            "                 ON LIDE.ID_LISTADO = LI.ID_LISTADO " +
            "       )SELECT " +
            "                 DOCREM.ID_DOCUMENTO AS ID, " +
            "       DOCREM.ID_TIPO_DOCUMENTO AS ID_TIPO_DOCUMENTO, " +
            "       TIPO_DOC.VALOR AS TIPO_DOCUMENTO, " +
            "                 DOCREM.NU_CORRELATIVO AS NUMERO_CORRELATIVO, " +
            "                 DOCREM.DE_NOMBRE_DOCUMENTO AS NOMBRE_DOCUMENTO, " +
            "                 DOCREM.FE_FECHA_INICIO_VALIDEZ AS FECHA_INICIO_VALIDEZ, " +
            "                 DOCREM.FE_FECHA_FIN_VALIDEZ AS FECHA_FIN_VALIDEZ, " +
            "                 DOCREM.US_CREACION AS USUARIO, " +
            "                 DOCREM.FE_CREACION AS FECHA " +
            "       FROM SICOES_TZ_DOCUMENTO_REEMP DOCREM " +
            "       LEFT JOIN LISTADOS TIPO_DOC " +
            "       ON DOCREM.ID_TIPO_DOCUMENTO = TIPO_DOC.ID " +
            "                 AND TIPO_DOC.CD_LISTADO = 'DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO' " +
            "             WHERE DOCREM.ID_REEMPLAZO_PERSONAL = :id " +
            "             AND DOCREM.ID_TIPO_DOCUMENTO IN ( " +
            "               SELECT  ID_LISTADO_DETALLE " +
            "               FROM SICOES_TM_LISTADO_DETALLE " +
            "               WHERE ID_LISTADO = :idlistado " +
            "             ) ", nativeQuery = true)
    public List<DocumentoInicioServ> documentosInicioServicio(@Param("id") Long id, @Param("idlistado") Long idlistado);

}