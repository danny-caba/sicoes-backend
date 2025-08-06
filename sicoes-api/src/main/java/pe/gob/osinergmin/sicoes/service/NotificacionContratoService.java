package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.DocumentoReemplazo;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;

public interface NotificacionContratoService {

    void notificarReemplazoPersonalByEmail(String numExpediente,String nombreRol, Contexto contexto);

    void notificarDesvinculacionEmpresa(String nummeroExpediente, String nombreSupervisora, Contexto contexto);

    void notificarSubsanacionDocumentos(String nombreSupervisora,Contexto contexto);

    void notificarCargarDocumentosInicioServicio(String nombreSupervisora,Contexto contexto);

    void notificarRevisarDocumentacionPendiente(String numExpediente, Contexto contexto);

    void notificarSubsanacionDocumentosReemplazo(String nombreSupervisora, String nombrePersonal, String nombrePerfil, List<DocumentoReemplazo> listDocsAsociados, Contexto contexto);

    void notificarRevisionDocumentosReemplazo(String nombreSupervisora, String numeroExpediente, Contexto contexto);

}
