package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.PerfilAprobador;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface PerfilAprobadorService extends BaseService<PerfilAprobador, Long> {

    PerfilAprobador obtenerPorPerfil(ListadoDetalle perfil, Contexto contexto);

}
