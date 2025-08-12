package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.EvaluarDocuReemplazo;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluarDocuReemDao extends JpaRepository<EvaluarDocuReemplazo,Long> {
    List<EvaluarDocuReemplazo> findByDocumentoIdDocumento(Long idDocumento);

    @Query("select e from EvaluarDocuReemplazo e"
            + " where e.documento.idDocumento = :idDocumento"
            + " and e.rol.idRol = :idRol")
    Optional<EvaluarDocuReemplazo> findByIdDocumentoIdRol(@Param("idDocumento") Long idDocumento, @Param("idRol") Long idRol);

     @Query("select e from EvaluarDocuReemplazo e"
        + " where e.documento.idDocumento = :idDocumento")
    Optional<EvaluarDocuReemplazo> findByIdDocumentoId(@Param("idDocumento") Long idDocumento);
}
