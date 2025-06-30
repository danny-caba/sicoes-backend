package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.dto.InvitacionDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface InvitacionService {

    InvitacionDTO guardar(InvitacionDTO invitacionDTO, Contexto contexto);

    InvitacionDTO eliminar(Long id, Contexto contexto);
}
