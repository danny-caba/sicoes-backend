package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.DocumentoReemplazo;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;

public interface DocumentoReemService extends BaseService<DocumentoReemplazo,Long>{

    Page<DocumentoReemplazo> buscar(Long idReemplazoPersonal, Pageable pageable, Contexto contexto);
    Page<DocumentoReemplazo> buscarIdReemplazoSeccion(Long idReemplazoPersonal, String seccion, Pageable pageable);
}
