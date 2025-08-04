package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.AdendaReemplazo;
import pe.gob.osinergmin.sicoes.model.dto.FirmaRequestDTO;

import java.util.Map;

public interface AdendaReemplazoService extends BaseService<AdendaReemplazo,Long>{

    Map<String,Object> iniciarFirma(Long idAdenda,Boolean visto, Boolean firmaJefe, Boolean firmaGerente);
    Map<String,Object> procesarFirma(Integer idArchivoSiged,
                                            String usuarioSiged, String passwordSiged,
                                            String urlFirma, String motivo);

    Map<String,Object> finalizarFirma(FirmaRequestDTO firmaRequestDTO);
    AdendaReemplazo actualizar(AdendaReemplazo adendaReemplazo);
    AdendaReemplazo rechazarVisto(AdendaReemplazo adendaReemplazo);
    AdendaReemplazo rechazarFirma(AdendaReemplazo adendaReemplazo, Boolean firmaJefe, Boolean firmaGerente);
}
