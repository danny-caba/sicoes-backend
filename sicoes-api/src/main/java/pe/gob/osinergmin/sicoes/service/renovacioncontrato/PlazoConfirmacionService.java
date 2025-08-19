package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import java.util.List;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.PlazoConfirmacionRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.PlazoConfirmacionResponseDTO;

public interface PlazoConfirmacionService {
    
    List<PlazoConfirmacionResponseDTO> listarPlazoConfirmacion(String estado);
    
    PlazoConfirmacionResponseDTO registrarPlazoConfirmacion(PlazoConfirmacionRequestDTO requestDTO);
}
