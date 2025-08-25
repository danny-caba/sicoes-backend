package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.Aprobacion;

import java.util.Optional;

@Repository
public interface AprobacionDao extends JpaRepository<Aprobacion, Long> {

    @Query(value = "SELECT * FROM ("
            + "  SELECT ap.* "
            + "  FROM SICOES_TZ_APROBACION_REEMP ap "
            + "  LEFT JOIN SICOES_TZ_REEMPLAZO_PERSONAL rp ON ap.ID_REEMPLAZO_PERSONAL = rp.ID_REEMPLAZO_PERSONAL "
            + "  WHERE rp.ID_REEMPLAZO_PERSONAL = :idReemplazo "
            + "  ORDER BY ap.FE_CREACION DESC"
            + ") WHERE ROWNUM = 1",
            nativeQuery = true)
    Optional<Aprobacion> findByRemplazoPersonal(@Param("idReemplazo") Long idReemplazo);


}

