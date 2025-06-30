package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.dto.RequerimientoInvitacionDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface RequerimientoInvitacionService {

    RequerimientoInvitacionDTO guardar(RequerimientoInvitacionDTO requerimientoInvitacionDTO, Contexto contexto);

    RequerimientoInvitacionDTO eliminar(Long id, Contexto contexto);
}
