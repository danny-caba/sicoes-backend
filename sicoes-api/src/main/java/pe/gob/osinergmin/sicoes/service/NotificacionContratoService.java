package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.util.Contexto;

public interface NotificacionContratoService {

    void notificarReemplazoPersonalByEmail(String numExpediente,String nombreRol, Contexto contexto);

    void notificarDesvinculacionEmpresa(String nummeroExpediente, String nombreSupervisora, Contexto contexto);

    void notificarSubsanacionDocumentos(String nombreSupervisora,Contexto contexto);

    void notifcarCargarDocumentosInicioServicio(String nombreSupervisora,Contexto contexto);

}
