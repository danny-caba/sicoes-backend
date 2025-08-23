package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionCreateResponseDTO;

public interface InvitacionService {
    public InvitacionCreateResponseDTO registrarInvitacion(InvitacionCreateRequestDTO requestDTO);
}
