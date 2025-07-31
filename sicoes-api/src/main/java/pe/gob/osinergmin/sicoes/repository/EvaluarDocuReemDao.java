package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.EvaluarDocuReemplazo;

import java.util.List;

@Repository
public interface EvaluarDocuReemDao extends JpaRepository<EvaluarDocuReemplazo,Long> {
    List<EvaluarDocuReemplazo> findByDocumentoIdDocumento(Long idDocumento);
}
