package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.repository.NotificacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.NotificacionAprobacionInformeService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

/**
 * Implementación del servicio para el manejo de notificaciones de aprobación de informes de renovación de contrato.
 */
@Service
public class NotificacionAprobacionInformeServiceImpl implements NotificacionAprobacionInformeService {

    private final Logger logger = LogManager.getLogger(NotificacionAprobacionInformeServiceImpl.class);
    private static final String ASUNTO_NOTIFICACION_INFORME_POR_APROBAR_Y_FIRMAR = "INFORME POR APROBAR Y FIRMAR";
    private static final String NOMBRE_TEMPLATE_NOTIFICACION_INFORME_POR_APROBAR_Y_FIRMAR = "notificacion-renovacion-contrato-informe-por-aprobar-y-firmar.html";
    private static final String ASUNTO_NOTIFICACION_INFORME_POR_REVISAR = "INFORME POR REVISAR";
    private static final String NOMBRE_TEMPLATE_NOTIFICACION_INFORME_POR_REVISAR = "notificacion-renovacion-contrato-informe-por-revisar.html";
    private static final String ASUNTO_NOTIFICACION_INFORME_POR_EVALUAR = "INFORME POR EVALUAR";
    private static final String NOMBRE_TEMPLATE_NOTIFICACION_INFORME_POR_EVALUAR = "notificacion-renovacion-contrato-informe-por-evaluar.html";
    
    private static final String ASUNTO_NOTIFICACION_SOLICITUD_DE_CONTRATOS = "SOLICITUD DE CONTRATOS";
    private static final String NOMBRE_TEMPLATE_NOTIFICACION_SOLICITUD_DE_CONTRATOS = "notificacion-renovacion-contrato-solicitud-de-contratos.html";

    private final TemplateEngine templateEngine;
    private final ListadoDetalleService listadoDetalleService;
    private final NotificacionDao notificacionDao;

    public NotificacionAprobacionInformeServiceImpl(
            TemplateEngine templateEngine,
            ListadoDetalleService listadoDetalleService,
            NotificacionDao notificacionDao) {
        this.templateEngine = templateEngine;
        this.listadoDetalleService = listadoDetalleService;
        this.notificacionDao = notificacionDao;
    }

    @Override
    public Long notificacionInformePorAprobaryFirmar(Usuario usuario, String numExpediente, Contexto contexto) {
        String email = usuario.getCorreo();
        String nombreUsuario = usuario.getNombreUsuario();
        logger.info("notificacionInformePorAprobaryFirmar para email: {} nombre: {}", email, nombreUsuario);
        
        Context ctx = new Context();
        ctx.setVariable("nombreRol", nombreUsuario);
        ctx.setVariable("numeroExpediente", numExpediente);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_INFORME_POR_APROBAR_Y_FIRMAR,
                NOMBRE_TEMPLATE_NOTIFICACION_INFORME_POR_APROBAR_Y_FIRMAR,
                ctx);
        
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        return saveNotificacion(notificacion);
    }

    private Notificacion buildNotification(String email, String subject, String template, Context context) {
        Notificacion notificacion = new Notificacion();
        notificacion.setCorreo(email);
        notificacion.setAsunto(subject);

        String htmlContent = templateEngine.process(template, context);
        String msjLimpio = htmlContent.replaceAll("[\\n\\r\\t]", "").replaceAll(" +", " ");
        notificacion.setMensaje(msjLimpio);
        return notificacion;
    }

    private Long saveNotificacion(Notificacion notificacion) {
        ListadoDetalle pendiente = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
                Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
        
        if (pendiente == null) {
            throw new RuntimeException("Estado 'Pendiente' no encontrado en listado detalle");
        }
        
        notificacion.setEstado(pendiente);
        Notificacion notificacionGuardada = notificacionDao.save(notificacion);
        
        return notificacionGuardada.getIdNotificacion();
    }

    @Override
    public Long notificacionInformePorRevisar(Usuario usuario, String numExpediente, Contexto contexto) {
        String email = usuario.getCorreo();
        String nombreUsuario = usuario.getNombreUsuario();
        logger.info("notificacionInformePorRevisar para email: {} nombre: {}", email, nombreUsuario);
        
        Context ctx = new Context();
        ctx.setVariable("nombreRol", nombreUsuario);
        ctx.setVariable("numeroExpediente", numExpediente);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_INFORME_POR_REVISAR,
                NOMBRE_TEMPLATE_NOTIFICACION_INFORME_POR_REVISAR,
                ctx);
        
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        return saveNotificacion(notificacion);
    }

    @Override
    public Long notificacionInformePorEvaluar(Usuario usuario, String numExpediente, Contexto contexto) {
        String email = usuario.getCorreo();
        String nombreUsuario = usuario.getNombreUsuario();
        logger.info("notificacionInformePorEvaluar para email: {} nombre: {}", email, nombreUsuario);
        
        Context ctx = new Context();
        ctx.setVariable("nombreRol", nombreUsuario);
        ctx.setVariable("numeroExpediente", numExpediente);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_INFORME_POR_EVALUAR,
                NOMBRE_TEMPLATE_NOTIFICACION_INFORME_POR_EVALUAR,
                ctx);
        
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        return saveNotificacion(notificacion);
    }

    /**
     * Envía notificación de solicitud de contratos (flujo final GSE G3).
     * @param usuario Usuario que recibirá la notificación
     * @param numExpediente Número de expediente del requerimiento
     * @param contexto Contexto de la operación
     * @return ID de la notificación creada
     */
    @Override
    public Long notificacionSolicitudDeContratos(Usuario usuario, String numExpediente, Contexto contexto) {
        String email = usuario.getCorreo();
        String nombreUsuario = usuario.getNombreUsuario();
        logger.info("notificacionSolicitudDeContratos para email: {} nombre: {}", email, nombreUsuario);
        
        Context ctx = new Context();
        ctx.setVariable("nombreRol", nombreUsuario);
        ctx.setVariable("numeroExpediente", numExpediente);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_SOLICITUD_DE_CONTRATOS,
                NOMBRE_TEMPLATE_NOTIFICACION_SOLICITUD_DE_CONTRATOS,
                ctx);
        
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        return saveNotificacion(notificacion);
    }
}
