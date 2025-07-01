package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface RequerimientoInvitacionService {

    RequerimientoInvitacion guardar(RequerimientoInvitacion requerimientoInvitacionDTO, Contexto contexto);

    RequerimientoInvitacion eliminar(Long id, Contexto contexto);
}
