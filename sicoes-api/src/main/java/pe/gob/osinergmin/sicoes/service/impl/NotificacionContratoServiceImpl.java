package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import pe.gob.osinergmin.sicoes.model.DocumentoReemplazo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.repository.NotificacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionContratoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;

@Service
public class NotificacionContratoServiceImpl implements NotificacionContratoService{
    private static final String ASUNTO_NOTIFICACION_REEMPLAZO_PERSONAL = "DOCUMENTACIÓN PENDIENTE POR EVALUAR (REEMPLAZO DE PERSONAL PROPUESTO)";
    private static final String NOMBRE_TEMPLATE_NOTIFICACION_REEMPLAZO_PERSONAL ="27-notificacion-reemplazo-personal.html";

    private static final String ASUNTO_NOTIFICACION_DESVINCULACION_PERSONAL = "NOTIFICACIÓN INGRESO FECHA DESVINCULACIÓN";
    private static final String NOMBRE_TEMPLATE_NOTIFICACION_DESVINCULACION_PERSONAL ="26-notificacion-desvinculacion-personal.html";

    private static final String ASUNTO_NOTIFICACION_SUBSANAR_DOCUMENTACION_INICIO_SERVICIO = "SUBSANAR DOCUMENTOS DE INICIO DE SERVICIO";
    private static final String NOMBRE_TEMPLATE_SUBSANAR_DOCUMENTACION_INICIO_SERVICIO ="28-notificacion-subsanar-documento.html";

    private static final String ASUNTO_NOTIFICACION_CARGAR_DOCUMENTOS_INICIO_SERVICIO = "PENDIENTE EN CARGAR DOCUMENTOS DE INICIO DE SERVICIO";
    private static final String NOMBRE_TEMPLATE_CARGAR_DOCUMENTOS_INICIO_SERVICIO ="29-notificacion-cargar-documento-servicio.html";

    private static final String ASUNTO_NOTIFICACION_REVISAR_DOCUMENTACION_PENDIENTE = "DOCUMENTACION PENDIENTE POR REVISAR (REEMPLAZO DE PERSONAL PROPUESTO)";
    private static final String NOMBRE_TEMPLATE_REVISAR_DOCUMENTACION_PENDIENTE = "30-notificacion-revisar-documento-pendiente.html";

    private static final String ASUNTO_NOTIFICACION_REV_DOCUMENTOS_2 = "SUBSANAR DOCUMENTOS DE INICIO DE SERVICIO";
    private static final String NOMBRE_TEMPLATE_REV_DOCUMENTOS_2 = "31-notificacion-rev-documentos-2.html";

    private static final String ASUNTO_NOTIFICACION_REV_DOCUMENTOS_15 = "DOCUMENTACION REVISADA";
    private static final String NOMBRE_TEMPLATE_REV_DOCUMENTOS_15 = "32-notificacion-rev-documentos-15.html";

    private static final String ASUNTO_NOTIFICACION_REV_DOCUMENTOS_12 = "SUBSANAR DOCUMENTOS";
    private static final String NOMBRE_TEMPLATE_REV_DOCUMENTOS_12 = "33-notificacion-rev-documentos-12.html";

    private static final String ASUNTO_NOTIFICACION_REV_DOCUMENTOS_122 = "SUBSANAR DOCUMENTOS DE REEMPLAZO";
    private static final String NOMBRE_TEMPLATE_REV_DOCUMENTOS_122 = "34-notificacion-rev-documentos-12-2.html";

    private Logger logger = LogManager.getLogger(NotificacionContratoServiceImpl.class);

    private final TemplateEngine templateEngine;
    private final ListadoDetalleService listadoDetalleService;
    private final NotificacionDao notificacionDao;

    public NotificacionContratoServiceImpl(
        TemplateEngine templateEngine, 
        ListadoDetalleService listadoDetalleService, 
        NotificacionDao notificacionDao) {

        this.templateEngine = templateEngine;
        this.listadoDetalleService = listadoDetalleService;
        this.notificacionDao = notificacionDao;
    }
	
    private void saveNotificacion(Notificacion notificacion) {

        ListadoDetalle pendiente = listadoDetalleService.obtenerListadoDetalle(
            Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
            Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
        if (pendiente == null) {
            throw  new RuntimeException("Estado 'Pendiente' no encontrado en listado detalle");
        }
        notificacion.setEstado(pendiente);
        notificacionDao.save(notificacion);
    }

    private Notificacion buildNotification (String email, String subject, String template,Context context){
        Notificacion notificacion = new Notificacion();
        notificacion.setCorreo(email);
        notificacion.setAsunto(subject);

        String htmlContent = templateEngine.process(template, context);
        notificacion.setMensaje(htmlContent);
        return notificacion;
    }


    @Override
    public void notificarReemplazoPersonalByEmail(String expedienteId,String nombreRol, Contexto contexto) {

        String email = contexto.getUsuario().getCorreo();
        logger.info(" notificarReemplazoPersonalByEmail para email: {} ",email);

        Context ctx = new Context();
        ctx.setVariable("nombreRol", nombreRol);
        ctx.setVariable("numeroExpediente", expedienteId);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_REEMPLAZO_PERSONAL,
                NOMBRE_TEMPLATE_NOTIFICACION_REEMPLAZO_PERSONAL,
                ctx);

        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        
        saveNotificacion(notificacion);    
    }

    @Override
    public void notificarDesvinculacionEmpresa(String numeroExpediente, String nombreSupervisora, Contexto contexto) {

        String email = contexto.getUsuario().getCorreo();
        logger.info(" notificarDesvinculacionEmpresa para email: {} ",email);

        Context ctx = new Context();
        
        ctx.setVariable("nombreSupervisora", nombreSupervisora);
        ctx.setVariable("numeroExpediente", numeroExpediente);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_DESVINCULACION_PERSONAL,
                NOMBRE_TEMPLATE_NOTIFICACION_DESVINCULACION_PERSONAL,
                ctx);

        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        
        saveNotificacion(notificacion);
    }


    @Override
    public void notificarSubsanacionDocumentos(String nombreSupervisora,Contexto contexto) {

        String email = contexto.getUsuario().getCorreo();
        logger.info(" notificarSubsanacionDocumentos para email: {} ",email);

        Context ctx = new Context();
        ctx.setVariable("nombreSupervisora", nombreSupervisora);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_SUBSANAR_DOCUMENTACION_INICIO_SERVICIO,
                NOMBRE_TEMPLATE_SUBSANAR_DOCUMENTACION_INICIO_SERVICIO,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        
        saveNotificacion(notificacion);
    }


    @Override
    public void notificarCargarDocumentosInicioServicio(String nombreSupervisora,Contexto contexto ) {

        String email = contexto.getUsuario().getCorreo();
        logger.info(" notifcarCargarDocumentosInicioServicio para email: {} ",contexto.getUsuario().getCorreo());

        Context ctx = new Context();
        ctx.setVariable("nombreSupervisora", nombreSupervisora);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_CARGAR_DOCUMENTOS_INICIO_SERVICIO,
                NOMBRE_TEMPLATE_CARGAR_DOCUMENTOS_INICIO_SERVICIO,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        
        saveNotificacion(notificacion);
    }


    @Override
    public void notificarRevisarDocumentacionPendiente(String numExpediente, Contexto contexto) {

        String email = contexto.getUsuario().getCorreo();
        logger.info(" notificarRevisarDocumentacionPendiente para email: {} ",contexto.getUsuario().getCorreo());

        Context ctx = new Context();
        ctx.setVariable("numExpediente", numExpediente);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_REVISAR_DOCUMENTACION_PENDIENTE,
                NOMBRE_TEMPLATE_REVISAR_DOCUMENTACION_PENDIENTE,
                ctx);

        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        
        saveNotificacion(notificacion);
    }

    @Override
    public void notificarRevDocumentos2(Usuario usuario, String nombrePersonal, String nombrePerfil, List<DocumentoReemplazo> listDocsAsociados, Contexto contexto) {
        String email = usuario.getCorreo();
        logger.info(" notificarRevDocumentos2 para email: {} ",email);
        Context ctx = new Context();
        ctx.setVariable("nombreSupervisora", usuario.getNombreUsuario());
        ctx.setVariable("nombrePersonal", nombrePersonal);
        ctx.setVariable("nombrePerfil", nombrePerfil);
        ctx.setVariable("listDocsAsociados", listDocsAsociados);
        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_REV_DOCUMENTOS_2,
                NOMBRE_TEMPLATE_REV_DOCUMENTOS_2,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }

    @Override
    public void notificarRevDocumentos15(Usuario usuario, String numeroExpediente, Contexto contexto) {
        String email = usuario.getCorreo();
        logger.info(" notificarRevDocumentos15 para email: {} ",email);
        Context ctx = new Context();
        ctx.setVariable("nombreSupervisora", usuario.getNombreUsuario());
        ctx.setVariable("numeroExpediente", numeroExpediente);
        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_REV_DOCUMENTOS_15,
                NOMBRE_TEMPLATE_REV_DOCUMENTOS_15,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }

    @Override
    public void notificarRevDocumentos12(Usuario usuario, Contexto contexto) {
        String email = usuario.getCorreo();
        logger.info(" notificarRevDocumentos12 para email: {} ",email);
        Context ctx = new Context();
        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_REV_DOCUMENTOS_12,
                NOMBRE_TEMPLATE_REV_DOCUMENTOS_12,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }

    @Override
    public void notificarRevDocumentos122(Usuario usuario, Contexto contexto) {
        String email = usuario.getCorreo();
        logger.info(" notificarRevDocumentos122 para email: {} ",email);
        Context ctx = new Context();
        ctx.setVariable("nombreSupervisora", usuario.getNombreUsuario());
        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_REV_DOCUMENTOS_122,
                NOMBRE_TEMPLATE_REV_DOCUMENTOS_122,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }
    
}
