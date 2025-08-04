package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;



import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.repository.NotificacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionContratoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class NotificacionContratoServiceImpl implements NotificacionContratoService{
    private static final String ASUNTO_NOTIFICACION_REEMPLAZO_PERSONAL = "DOCUMENTACIÓN PENDIENTE POR EVALUAR (REEMPLAZO DE PERSONAL PROPUESTO)";
    private static final String NOMBRE_TEMPLATE_NOTIFICACION_REEMPLAZO_PERSONAL ="27-notificacion-reemplazo-personal.html";

    private static final String ASUNTO_NOTIFICACION_DESVINCULACION_PERSONAL = "NOTIFICACIÓN INGRESO FECHA DESVINCULACIÓN";
    private static final String NOMBRE_TEMPLATE_NOTIFICACION_DESVINCULACION_PERSONAL ="26-notificacion-desvinculacion-personal.html";

    private static final String ASUNTO_NOTIFICACION_SUBSANAR_DOCUMENTACION_INICIO_SERVICIO = "SUBASANAR DOCUMENTOS DE INICIO DE SERVICIO";
    private static final String NOMBRE_TEMPLATE_SUBSANAR_DOCUMENTACION_INICIO_SERVICIO ="28-notificacion-subsanar-documento.htm";

    private static final String ASUNTO_NOTIFICACION_CARGAR_DOCUMENTOS_INICIO_SERVICIO = "PENDIENTE EN CARGAR DOCUMENTOS DE INICIO DE SERVICIO";
    private static final String NOMBRE_TEMPLATE_CARGAR_DOCUMENTOS_INICIO_SERVICIO ="29-notificacion-cargar-documento-servicio.htm";

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
	

    @Override
    public void notificarReemplazoPersonalByEmail(String expedienteId,String nombreRol, Contexto contexto) {
        Notificacion notificacion = new Notificacion();
        logger.info(" notificarReemplazoPersonalByEmail para email: {} ",contexto.getUsuario().getCorreo());
        notificacion.setCorreo(contexto.getUsuario().getCorreo());
        notificacion.setAsunto(ASUNTO_NOTIFICACION_REEMPLAZO_PERSONAL);
        
        Context ctx = new Context();
        
        ctx.setVariable("nombreRol", nombreRol);
        ctx.setVariable("numeroExpediente", expedienteId);
        
        String htmlContent = templateEngine.process(NOMBRE_TEMPLATE_NOTIFICACION_REEMPLAZO_PERSONAL, ctx);

        notificacion.setMensaje(htmlContent);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        
        ListadoDetalle pendiente = listadoDetalleService.obtenerListadoDetalle(
            Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
            Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
        
        notificacion.setEstado(pendiente);
        notificacionDao.save(notificacion);

    }


    @Override
    public void notificarDesvinculacionEmpresa(String numeroExpediente, String nombreSupervisora, Contexto contexto) {
        Notificacion notificacion = new Notificacion();

        logger.info(" notificarDesvinculacionEmpresa para email: {} ",contexto.getUsuario().getCorreo());
        notificacion.setCorreo(contexto.getUsuario().getCorreo());
        notificacion.setAsunto(ASUNTO_NOTIFICACION_DESVINCULACION_PERSONAL);
        
        Context ctx = new Context();
        
        ctx.setVariable("nombreSupervisora", nombreSupervisora);
        ctx.setVariable("numeroExpediente", numeroExpediente);
        
        String htmlContent = templateEngine.process(NOMBRE_TEMPLATE_NOTIFICACION_DESVINCULACION_PERSONAL, ctx);

        notificacion.setMensaje(htmlContent);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        
        ListadoDetalle pendiente = listadoDetalleService.obtenerListadoDetalle(
            Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
            Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
        
        notificacion.setEstado(pendiente);
        notificacionDao.save(notificacion);
    }


    @Override
    public void notificarSubsanacionDocumentos(String nombreSupervisora,Contexto contexto) {       
         Notificacion notificacion = new Notificacion();

        logger.info(" notificarSubsanacionDocumentos para email: {} ",contexto.getUsuario().getCorreo());
        notificacion.setCorreo(contexto.getUsuario().getCorreo());
        notificacion.setAsunto(ASUNTO_NOTIFICACION_SUBSANAR_DOCUMENTACION_INICIO_SERVICIO);

        Context ctx = new Context();
        
        ctx.setVariable("nombreSupervisora", nombreSupervisora);
        
        String htmlContent = templateEngine.process(NOMBRE_TEMPLATE_SUBSANAR_DOCUMENTACION_INICIO_SERVICIO, ctx);

        notificacion.setMensaje(htmlContent);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        
        ListadoDetalle pendiente = listadoDetalleService.obtenerListadoDetalle(
            Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
            Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
        
        notificacion.setEstado(pendiente);
        notificacionDao.save(notificacion);
    }


    @Override
    public void notifcarCargarDocumentosInicioServicio(String nombreSupervisora,Contexto contexto ) {
        
        Notificacion notificacion = new Notificacion();

        logger.info(" notifcarCargarDocumentosInicioServicio para email: {} ",contexto.getUsuario().getCorreo());
        notificacion.setCorreo(contexto.getUsuario().getCorreo());
        notificacion.setAsunto(ASUNTO_NOTIFICACION_CARGAR_DOCUMENTOS_INICIO_SERVICIO);

        Context ctx = new Context();
        
        ctx.setVariable("nombreSupervisora", nombreSupervisora);
        
        String htmlContent = templateEngine.process(NOMBRE_TEMPLATE_CARGAR_DOCUMENTOS_INICIO_SERVICIO, ctx);

        notificacion.setMensaje(htmlContent);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        
        ListadoDetalle pendiente = listadoDetalleService.obtenerListadoDetalle(
            Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
            Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
        
        notificacion.setEstado(pendiente);
        notificacionDao.save(notificacion);
    }
    
}
