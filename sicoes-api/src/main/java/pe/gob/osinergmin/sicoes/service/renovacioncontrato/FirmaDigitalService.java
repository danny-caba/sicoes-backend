package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.FirmaDigitalRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.FirmaDigitalResponseDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface FirmaDigitalService {

    FirmaDigitalResponseDTO obtenerParametrosfirmaDigital(FirmaDigitalRequestDTO firmaDigitalRequestDTO, Contexto contexto) throws Exception;

    Long obtenerIdArchivos(String numeroExpediente, String nombreUsuario) throws Exception;
}
