package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.AdendaReemplazo;
import pe.gob.osinergmin.sicoes.model.dto.FirmaRequestDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.Map;

public interface AdendaReemplazoService extends BaseService<AdendaReemplazo,Long>{

    Map<String,Object> iniciarFirma(Long idAdenda,Boolean visto, Boolean firmaJefe, Boolean firmaGerente);
    Map<String,Object> procesarFirma(Integer idArchivoSiged,
                                            String usuarioSiged, String passwordSiged,
                                            String urlFirma, String motivo);

    Map<String,Object> finalizarFirma(FirmaRequestDTO firmaRequestDTO, Contexto contexto);
    AdendaReemplazo finalizarFirmaAdenda(FirmaRequestDTO firmaRequestDTO, Contexto contexto);
    AdendaReemplazo actualizar(AdendaReemplazo adendaReemplazo, Contexto contexto);
    AdendaReemplazo rechazarVisto(AdendaReemplazo adendaReemplazo, Contexto contexto);
    AdendaReemplazo rechazarFirma(AdendaReemplazo adendaReemplazo, Boolean firmaJefe, Boolean firmaGerente,
                                  Contexto contexto);
}
