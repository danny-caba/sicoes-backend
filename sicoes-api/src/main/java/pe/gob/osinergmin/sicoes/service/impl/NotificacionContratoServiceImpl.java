package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.repository.*;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionContratoService;
import pe.gob.osinergmin.sicoes.service.PersonalReemplazoService;
import pe.gob.osinergmin.sicoes.service.SicoesSolicitudService;
import pe.gob.osinergmin.sicoes.service.SupervisoraMovimientoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    private static final String ASUNTO_NOTIFICACION_APROBACION_PENDIENTE = "NOTIFICACIÓN PARA APROBAR";
    private static final String NOMBRE_TEMPLATE_APROBACION_PENDIENTE = "35-notificacion-aprobacion-pendiente.html";

    private static final String ASUNTO_NOTIFICACION_RECHAZO_PERSONAL = "RECHAZAR EL REEMPLAZO DE PERSONAL PROPUESTO";
    private static final String NOMBRE_TEMPLATE_RECHAZO_PERSONAL = "36-notificacion-rechazo-personal.html";

    private static final String ASUNTO_NOTIFICACION_EVALUACION_PENDIENTE = "TIENE UN PENDIENTE POR EVALUAR";
    private static final String NOMBRE_TEMPLATE_EVALUACION_PENDIENTE = "37-notificacion-evaluacion-pendiente.html";

    private static final String ASUNTO_NOTIFICACION_FINALIZACION_CONTRATO_PERSONAL = "PENDIENTE EN CARGAR DOCUMENTOS DE INICIO DE SERVICIO";
    private static final String NOMBRE_TEMPLATE_FINALIZACION_CONTRATO_PERSONAL ="38-notificacion-finalizacion-contrato-personal.html";

    private final Logger logger = LogManager.getLogger(NotificacionContratoServiceImpl.class);

    private final TemplateEngine templateEngine;
    private final ListadoDetalleService listadoDetalleService;
    private final NotificacionDao notificacionDao;
    private final PersonalReemplazoService personalReemplazoService;
    private  final SicoesSolicitudService sicoesSolicitudService;
    private final PersonalReemplazoDao personalReemplazoDao;
    private final PropuestaProfesionalDao propuestaProfesionalDao;
    private final SupervisoraMovimientoService supervisoraMovimientoService;
    private final UsuarioRolDao usuarioRolDao;
    private final  UsuarioDao usuarioDao;
    private final SicoesSolicitudDao sicoesSolicitudDao;

    private static final String NUMERO_EXPEDIENTE = "numeroExpediente";
    private static final String NOMBRE_SUPERVISORA = "nombreSupervisora";
    private static final String NOMBRE_PERSONAL = "nombrePersonal";


    public NotificacionContratoServiceImpl(
            TemplateEngine templateEngine,
            ListadoDetalleService listadoDetalleService,
            NotificacionDao notificacionDao,
            PersonalReemplazoService personalReemplazoService,
            SicoesSolicitudService sicoesSolicitudService,
            PersonalReemplazoDao personalReemplazoDao,
            PropuestaProfesionalDao propuestaProfesionalDao,
            SupervisoraMovimientoService supervisoraMovimientoService,
            UsuarioRolDao usuarioRolDao, UsuarioDao usuarioDao,
            SicoesSolicitudDao sicoesSolicitudDao) {

        this.templateEngine = templateEngine;
        this.listadoDetalleService = listadoDetalleService;
        this.notificacionDao = notificacionDao;
        this.personalReemplazoService = personalReemplazoService;
        this.sicoesSolicitudService = sicoesSolicitudService;
        this.personalReemplazoDao = personalReemplazoDao;
        this.propuestaProfesionalDao = propuestaProfesionalDao;
        this.supervisoraMovimientoService = supervisoraMovimientoService;
        this.usuarioRolDao = usuarioRolDao;
        this.usuarioDao = usuarioDao;
        this.sicoesSolicitudDao = sicoesSolicitudDao;
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
        String msjLimpio = htmlContent.replaceAll("[\\n\\r\\t]", "").replaceAll(" +", " ");
        notificacion.setMensaje(msjLimpio);
        return notificacion;
    }


    @Override
    public void notificarReemplazoPersonalByEmail(Usuario usuario, String numExpediente, String nombreRol, Contexto contexto) {
        String email = usuario.getCorreo();
        logger.info(" notificarReemplazoPersonalByEmail para email: {} ",email);

        Context ctx = new Context();
        ctx.setVariable("nombreRol", nombreRol);
        ctx.setVariable(NUMERO_EXPEDIENTE, numExpediente);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_REEMPLAZO_PERSONAL,
                NOMBRE_TEMPLATE_NOTIFICACION_REEMPLAZO_PERSONAL,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);    
    }

    @Override
    public void notificarDesvinculacionEmpresa(Usuario usuario, String numeroExpediente, String nombreSupervisora, Contexto contexto) {
        String email = usuario.getCorreo();
        logger.info(" notificarDesvinculacionEmpresa para email: {} ",email);

        Context ctx = new Context();
        ctx.setVariable(NOMBRE_SUPERVISORA, nombreSupervisora);
        ctx.setVariable(NUMERO_EXPEDIENTE, numeroExpediente);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_DESVINCULACION_PERSONAL,
                NOMBRE_TEMPLATE_NOTIFICACION_DESVINCULACION_PERSONAL,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }

    @Override
    public void notificarSubsanacionDocumentos(Supervisora empresa, Supervisora personaPropuesta, Contexto contexto) {
        String email = personaPropuesta.getCorreo();
        logger.info(" notificarSubsanacionDocumentos para email: {} ",email);
        String nombreSupervisora = obtenerNombreSupervisora(empresa);
        String nombrePersonal = obtenerNombreSupervisora(personaPropuesta);

        Context ctx = new Context();
        ctx.setVariable(NOMBRE_SUPERVISORA, nombreSupervisora);
        ctx.setVariable(NOMBRE_PERSONAL, nombrePersonal);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_SUBSANAR_DOCUMENTACION_INICIO_SERVICIO,
                NOMBRE_TEMPLATE_SUBSANAR_DOCUMENTACION_INICIO_SERVICIO,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }

    @Override
    public void notificarCargarDocumentosInicioServicio(Supervisora personaPropuesta, Contexto contexto ) {
        String email = personaPropuesta.getCorreo();
        if (email == null || email.isEmpty()) {
            logger.warn("El correo de la supervisora es nulo o vacío");
        } else {
            logger.info("Correo de la Supervisora: {}", email);
        }
        String nombreSupervisora = obtenerNombreSupervisora(personaPropuesta);
        Context ctx = new Context();
        ctx.setVariable(NOMBRE_SUPERVISORA, nombreSupervisora);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_CARGAR_DOCUMENTOS_INICIO_SERVICIO,
                NOMBRE_TEMPLATE_CARGAR_DOCUMENTOS_INICIO_SERVICIO,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }

    @Override
    @Transactional
    public void notificarCargarDocumentosInicioServicio(Contexto contexto) {
        List<PersonalReemplazo> lista = personalReemplazoService.listarPersonaReemplazoxDocIniServ(
                Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR);

        ListadoDetalle plazo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.PLAZOS.CODIGO,
                Constantes.LISTADO.PLAZOS.PLAZO_DOCUMENTOS_SERVICIO);

        int plazoDiasHabiles = Integer.parseInt(plazo.getValor());
        logger.info("dias: {}",plazoDiasHabiles);
        for (PersonalReemplazo pr : lista) {
            Date fechaIngreso = pr.getFeFechaRegistro();
            Date fechaLimite = sumarDiasHabiles(fechaIngreso, plazoDiasHabiles);
            if (new Date().after(fechaLimite)) {
                Long id = pr.getIdSolicitud(); //Solicitud perfeccionamiento
                SicoesSolicitud sicoesSolicitud = sicoesSolicitudService.obtener(id,contexto);
                notificarCargarDocumentosInicioServicio(sicoesSolicitud.getSupervisora(), contexto);
            }
        }
    }

    private Date sumarDiasHabiles(Date fecha, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int added = 0;

        while (added < dias) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                added++;
            }
        }
        return cal.getTime();
    }

    @Override
    public void notificarRechazoPersonalPropuesto(Supervisora empresa, Supervisora personaPropuesta, Contexto contexto ) {
        String email = empresa.getCorreo();
        logger.info(" notificarRechazoPersonalPropuesto para email: {} ", empresa.getCorreo());
        String nombreSupervisora = obtenerNombreSupervisora(personaPropuesta);

        Context ctx = new Context();
        ctx.setVariable(NOMBRE_SUPERVISORA, nombreSupervisora);
        ctx.setVariable(NUMERO_EXPEDIENTE, personaPropuesta.getNumeroExpediente());

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_RECHAZO_PERSONAL,
                NOMBRE_TEMPLATE_RECHAZO_PERSONAL,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }

    @Override
    public void notificarRevisarDocumentacionPendiente(Usuario usuario, String numExpediente, String nombreRol, Contexto contexto) {
        String email = usuario.getCorreo();
        logger.info(" notificarRevisarDocumentacionPendiente para email: {} ", usuario.getCorreo());

        Context ctx = new Context();
        ctx.setVariable("numExpediente", numExpediente);
        ctx.setVariable("nombreRol", nombreRol);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_REVISAR_DOCUMENTACION_PENDIENTE,
                NOMBRE_TEMPLATE_REVISAR_DOCUMENTACION_PENDIENTE,
                ctx);

        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }

    @Override
    public void notificarRevDocumentos2(Supervisora empresa, String nombrePersonal, String nombrePerfil, List<DocumentoReemplazo> listDocsAsociados, Contexto contexto) {
        String email = empresa.getCorreo();
        logger.info(" notificarRevDocumentos2 para email: {} ",email);
        Context ctx = new Context();
        ctx.setVariable(NOMBRE_SUPERVISORA, this.obtenerNombreSupervisora(empresa));
        ctx.setVariable(NOMBRE_PERSONAL, nombrePersonal);
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
        ctx.setVariable(NOMBRE_SUPERVISORA, usuario.getNombreUsuario());
        ctx.setVariable(NUMERO_EXPEDIENTE, numeroExpediente);
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
    public void notificarRevDocumentos122(Supervisora empresa, String nombrePersonal, String sctr1, String sctr2, List<DocumentoInicioServ> docAdicional, Contexto contexto) {
        String email = empresa.getCorreo();
        logger.info(" notificarRevDocumentos122 para email: {} ",email);
        Context ctx = new Context();
        ctx.setVariable(NOMBRE_SUPERVISORA, this.obtenerNombreSupervisora(empresa));
        ctx.setVariable(NOMBRE_PERSONAL, nombrePersonal);
        ctx.setVariable("docAdicional", docAdicional);
        ctx.setVariable("sctr1", sctr1);
        ctx.setVariable("sctr2", sctr2);
        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_REV_DOCUMENTOS_122,
                NOMBRE_TEMPLATE_REV_DOCUMENTOS_122,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }

    @Override
    public void notificarAprobacionPendiente(Usuario usuario, String numeroExpediente, Contexto contexto) {
        String email = usuario.getCorreo();
        logger.info(" notificarAprobacionPendiente para email: {} ",email);

        Context ctx = new Context();
        ctx.setVariable("nombreAprobador", usuario.getNombreUsuario());
        ctx.setVariable(NUMERO_EXPEDIENTE, numeroExpediente);
        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_APROBACION_PENDIENTE,
                NOMBRE_TEMPLATE_APROBACION_PENDIENTE,
                ctx);

        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }

    @Override
    public void notificarEvaluacionPendiente(Usuario usuario, String numeroExpediente, Contexto contexto) {
        String email = usuario.getCorreo();
        logger.info(" notificarEvaluacionPendiente para email: {} ",email);

        Context ctx = new Context();
        ctx.setVariable(NUMERO_EXPEDIENTE, numeroExpediente);
        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_EVALUACION_PENDIENTE,
                NOMBRE_TEMPLATE_EVALUACION_PENDIENTE,
                ctx);

        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }

    private String obtenerNombreSupervisora(Supervisora personaPropuesta) {
        String razonSocial = Optional.ofNullable(personaPropuesta.getNombreRazonSocial()).orElse("");
        this.logger.info("razon social juridica {} ", razonSocial);
        String nombreSupervisora = null;
        if(!razonSocial.isEmpty()){
            nombreSupervisora = razonSocial;
        }else{
            String apellidoPaterno = personaPropuesta.getApellidoPaterno();
            String apellidoMaterno = personaPropuesta.getApellidoMaterno();
            nombreSupervisora = personaPropuesta.getNombres()
                    .concat(" ").concat(apellidoPaterno)
                    .concat(" ").concat(apellidoMaterno);
            this.logger.info("razon social personal natural {} ", razonSocial);
        }
        return nombreSupervisora;
    }


    @Override
    public void notificarFinalizacionContrato(Usuario usuario, String numeroExpediente, Contexto contexto ) {
        String email = usuario.getCorreo();
        if (email == null || email.isEmpty()) {
            logger.warn("El correo del usuario es nulo o vacío");
        } else {
            logger.info("Correo del uuario: {}", email);
        }
        logger.info(" notificarFinalizacionContrato para email: {} ",email);
        Context ctx = new Context();
        ctx.setVariable(NUMERO_EXPEDIENTE, numeroExpediente);

        Notificacion notificacion = buildNotification(
                email,
                ASUNTO_NOTIFICACION_FINALIZACION_CONTRATO_PERSONAL,
                NOMBRE_TEMPLATE_FINALIZACION_CONTRATO_PERSONAL,
                ctx);
        AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
        saveNotificacion(notificacion);
    }


    @Override
    @Transactional
    public void notificarFinalizacionContrato(Contexto contexto) {

		List<PersonalReemplazo> reemplazos = personalReemplazoDao.obtenerParaDesvinculacion();

        Optional<Usuario> usuario = usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.EVALUADOR_TECNICO).stream()
                .findFirst()
                .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));

        for(PersonalReemplazo personalReemplazo:reemplazos) {

            logger.info("personalReemplazo: {}", personalReemplazo);
            Long idSolicitud = personalReemplazo.getIdSolicitud();
            if (idSolicitud == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA);
            }

            logger.info("idSolicitud: {}", idSolicitud);
            Supervisora personalBaja = personalReemplazo.getPersonaBaja();
            if (personalBaja == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONA_BAJA);
            }
            logger.info("personalBaja: {}", personalBaja);
            PropuestaProfesional propuestaProfesional = propuestaProfesionalDao.listarXSolicitud(idSolicitud, personalBaja.getIdSupervisora());
            if (propuestaProfesional == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROFESIONAL_NO_EXISTE);
            }
            logger.info("propuestaProfesional: {}", propuestaProfesional);
            Supervisora personaPropuesta = personalReemplazo.getPersonaPropuesta();
            if (personaPropuesta == null){
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONA_PROPUESTA);
            }
            logger.info("personaPropuesta: {}", personaPropuesta);
            propuestaProfesional.setSupervisora(personaPropuesta);
            SupervisoraMovimiento supervisoraMovimiento = new SupervisoraMovimiento();
            supervisoraMovimiento.setSector(propuestaProfesional.getSector());
            supervisoraMovimiento.setSubsector(propuestaProfesional.getSubsector());
            supervisoraMovimiento.setSupervisora(personaPropuesta);
            supervisoraMovimiento.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO));
            supervisoraMovimiento.setTipoMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.CODIGO, Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.AUTOMATICO));
            supervisoraMovimiento.setMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.REEMPLAZO_PERSONAL));
            supervisoraMovimiento.setAccion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.BLOQUEO));
            supervisoraMovimiento.setPropuestaProfesional(propuestaProfesional);
            supervisoraMovimiento.setFechaRegistro(new Date());
            logger.info("supervisoraMovimiento: {}", supervisoraMovimiento);
            supervisoraMovimientoService.guardar(supervisoraMovimiento,contexto);

            SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                    .orElse(new SicoesSolicitud());
            String numeroExpediente = Optional.ofNullable(sicoesSolicitud.getNumeroExpediente()).orElse("");

            if (usuario.isPresent()) {
                notificarFinalizacionContrato(usuario.get(), numeroExpediente, contexto);
            }
		}


    }

}
