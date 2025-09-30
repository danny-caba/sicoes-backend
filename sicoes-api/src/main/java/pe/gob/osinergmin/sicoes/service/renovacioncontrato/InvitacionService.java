package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionCreateResponseDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionRequestDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;

public interface InvitacionService {
    RequerimientoInvitacion registrarInvitacion(InvitacionCreateRequestDTO requestDTO, Contexto contexto);
    void eliminarInvitacion(InvitacionRequestDTO requestDTO, Contexto contexto);
}
