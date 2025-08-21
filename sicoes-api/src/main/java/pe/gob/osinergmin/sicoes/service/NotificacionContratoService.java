package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.DocumentoReemplazo;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;

public interface NotificacionContratoService {

    void notificarReemplazoPersonalByEmail(Usuario usuario, String numExpediente,String nombreRol, Contexto contexto);

    void notificarDesvinculacionEmpresa(Usuario usuario, String nummeroExpediente, String nombreSupervisora, Contexto contexto);

    void notificarSubsanacionDocumentos(Supervisora personaPropuesta, Contexto contexto);

    void notificarCargarDocumentosInicioServicio(Supervisora personaPropuesta, Contexto contexto);

    void notificarCargarDocumentosInicioServicio(Contexto contexto);

    void notificarRechazoPersonalPropuesto(Supervisora empresa, Supervisora personaPropuesta, Contexto contexto);

    void notificarRevisarDocumentacionPendiente(Usuario usuario, String numExpediente, String nombreRol, Contexto contexto);

    void notificarRevDocumentos2(Supervisora empresa, String nombrePersonal, String nombrePerfil, List<DocumentoReemplazo> listDocsAsociados, Contexto contexto);

    void notificarRevDocumentos15(Usuario usuario, String numeroExpediente, Contexto contexto);

    void notificarRevDocumentos12(Usuario usuario, Contexto contexto);

    void notificarRevDocumentos122(Supervisora empresa, String nombrePersonal, String nombrePerfil, Contexto contexto);

    void notificarAprobacionPendiente(Usuario usuario, String numeroExpediente, Contexto contexto);

    void notificarEvaluacionPendiente(Usuario usuario, String numeroExpediente, Contexto contexto);
}
