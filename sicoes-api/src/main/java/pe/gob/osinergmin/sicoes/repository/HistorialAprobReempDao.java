package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.HistorialAprobReemp;

@Repository
public interface HistorialAprobReempDao extends JpaRepository<HistorialAprobReemp, Long> {

	 @Query( value = " WITH LISTADOS AS ( " +
             "    SELECT " +
             "        TO_NUMBER(LIDE.ID_LISTADO_DETALLE) AS ID, " +
             "        LIDE.NO_LISTADO_DETALLE AS VALOR, " +
             "        LI.CD_LISTADO " +
             "    FROM SICOES_TM_LISTADO_DETALLE LIDE " +
             "    INNER JOIN SICOES_TM_LISTADO LI " +
             "        ON LIDE.ID_LISTADO = LI.ID_LISTADO " +
             ") " +
             "SELECT " +
             "HISTAP.ID_HISTORIAL AS ID, " +
             "ROL.NO_ROL AS TIPO_APROBADOR, " +
             "GRUPOS.VALOR AS GRUPO, " +
             "HISTAP.FE_FECHA_DESIGNACION AS FECHA_DESIGNACION, " +
             "USUA.NO_USUARIO AS APROBADOR, " +
             "HISTAP.FE_FECHA_APROBACION AS FECHA_APROBACION, " +
             "EST_APROB.VALOR AS RESULTADO, " +
             "HISTAP.DE_OBSERVACION AS OBSERVACION " +
             "FROM SICOES_TH_HIST_APRO_REEMP  HISTAP " +
             "LEFT JOIN SICOES_TM_USUARIO USUA " +
             "    ON HISTAP.ID_APROBADOR = USUA.ID_USUARIO " +
             "LEFT JOIN LISTADOS EST_APROB " +
             "    ON HISTAP.CO_RESULTADO = EST_APROB.ID AND EST_APROB.CD_LISTADO = 'ESTADO_APROBACION_REEMP' " +
             "LEFT JOIN SICOES_TM_ROL ROL " +
             "   ON HISTAP.ID_ROL =  ROL.ID_ROL " +
             "LEFT JOIN LISTADOS GRUPOS  " +
             "    ON HISTAP.CO_GRUPO = GRUPOS.ID AND GRUPOS.CD_LISTADO = 'GRUPOS' " +
             "LEFT  JOIN SICOES_TZ_APROBACION_REEMP APROB " +
			 "    ON HISTAP.ID_APROBACION = APROB.ID_APROBACION " +
			 "WHERE APROB.ID_REEMPLAZO_PERSONAL = :id " , nativeQuery = true)
	public Page<HistorialAprobReemp> buscarHistorial(@Param("id") Long idReemplazo, Pageable pageable );
}