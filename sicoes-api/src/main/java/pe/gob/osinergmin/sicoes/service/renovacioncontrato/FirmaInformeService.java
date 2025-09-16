package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.FirmaInformeRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.FirmaInformeResponseDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface FirmaInformeService {

    FirmaInformeResponseDTO obtenerIdArchivo(FirmaInformeRequestDTO requestDTO, Contexto contexto) throws Exception;


}
