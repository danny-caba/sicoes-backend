package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.DocumentoReemplazo;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;

public interface NotificacionContratoService {

    void notificarReemplazoPersonalByEmail(String numExpediente,String nombreRol, Contexto contexto);

    void notificarDesvinculacionEmpresa(String nummeroExpediente, String nombreSupervisora, Contexto contexto);

    void notificarSubsanacionDocumentos(String nombreSupervisora,Contexto contexto);

    void notificarCargarDocumentosInicioServicio(String nombreSupervisora,Contexto contexto);

    void notificarRevisarDocumentacionPendiente(String numExpediente, Contexto contexto);

    void notificarSubsanacionDocumentosReemplazo(Usuario usuario, String nombrePersonal, String nombrePerfil, List<DocumentoReemplazo> listDocsAsociados, Contexto contexto);

    void notificarRevisionDocumentosReemplazo(Usuario usuario, String numeroExpediente, Contexto contexto);

}
