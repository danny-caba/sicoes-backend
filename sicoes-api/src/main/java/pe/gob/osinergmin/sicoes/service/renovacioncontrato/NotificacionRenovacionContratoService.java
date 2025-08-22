package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface NotificacionRenovacionContratoService {

    void notificacionInformePorAprobar(Usuario usuario, String numExpediente, Contexto contexto);
    
}
