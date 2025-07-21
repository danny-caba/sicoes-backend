package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.DocumentoReemplazo;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;

public interface DocumentoReemService extends BaseService<DocumentoReemplazo,Long>{

    public List<DocumentoReemplazo> buscar(Long idReemplazoPersonal, Contexto contexto);

}
