package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.repository.NotificacionDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.NotificacionRenovacionContratoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pe.gob.osinergmin.sicoes.util.EstadoUtil;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Iterator;
import java.util.List;

@Service
public class NotificacionRenovacionContratoServiceImpl implements NotificacionRenovacionContratoService {
    private Logger logger = LogManager.getLogger(NotificacionRenovacionContratoServiceImpl.class);

    private static final String ASUNTO_NOTIFICACION_INFORME_POR_APROBAR = "INFORME POR APROBAR";
    private static final String NOMBRE_TEMPLATE_NOTIFICACION_INFORME_POR_APROBAR ="notificacion-renovacion-contrato-informe-por-aprobar.html";


    @Value("${spring.mail.username}")
    private String springMailUsername;

    private final TemplateEngine templateEngine;
    private final ListadoDetalleService listadoDetalleService;
    private final NotificacionDao notificacionDao;


    public NotificacionRenovacionContratoServiceImpl(
        TemplateEngine templateEngine, 
        ListadoDetalleService listadoDetalleService, 
        NotificacionDao notificacionDao) {

        this.templateEngine = templateEngine;
        this.listadoDetalleService = listadoDetalleService;
        this.notificacionDao = notificacionDao;

    }



    private Notificacion buildNotification (String email, String subject, String template,Context context){
    Notificacion notificacion = new Notificacion();
    notificacion.setCorreo(email);
    notificacion.setAsunto(subject);

    String htmlContent = templateEngine.process(template, context);
    String msjLimpio = htmlContent.replaceAll("[\\n\\r\\t]", "").replaceAll(" +", " ");
    notificacion.setMensaje(msjLimpio);
    return notificacion;
    }

    private Notificacion saveNotificacion(Notificacion notificacion) {

    ListadoDetalle pendiente = listadoDetalleService.obtenerListadoDetalle(
        Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
        Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
    if (pendiente == null) {
        throw  new RuntimeException("Estado 'Pendiente' no encontrado en listado detalle");
    }
    notificacion.setEstado(pendiente);
    return notificacionDao.save(notificacion);
    }

    @Override
    public Long notificacionInformePorAprobar(Usuario usuario, String numExpediente, Contexto contexto) {
        String email = usuario.getCorreo();
        String nombreUsuario = usuario.getNombreUsuario();
        logger.info(" notificacionInformePorAprobar para email: {} nombre: {} ",email,nombreUsuario);
        Context ctx = new Context();
        ctx.setVariable("nombreRol", nombreUsuario);
        ctx.setVariable("numeroExpediente", numExpediente);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_INFORME_POR_APROBAR,
                NOMBRE_TEMPLATE_NOTIFICACION_INFORME_POR_APROBAR,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        Notificacion notificacionGuardada = saveNotificacion(notificacion);
        
        logger.info("Notificación guardada con ID: {}", notificacionGuardada.getIdNotificacion());
        return notificacionGuardada.getIdNotificacion();
    }
}
