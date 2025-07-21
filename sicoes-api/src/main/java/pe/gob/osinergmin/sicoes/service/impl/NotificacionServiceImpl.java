package pe.gob.osinergmin.sicoes.service.impl;



import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumento;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumentoDetalle;
import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.UsuarioRol;
import pe.gob.osinergmin.sicoes.repository.AsignacionDao;
import pe.gob.osinergmin.sicoes.repository.ConfBandejaDao;
import pe.gob.osinergmin.sicoes.repository.NotificacionDao;
import pe.gob.osinergmin.sicoes.repository.OtroRequisitoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDocumentoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioRolDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.ProcesoService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.service.TokenService;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.*;


@Service
public class NotificacionServiceImpl implements NotificacionService{
	
	Logger logger = LogManager.getLogger(NotificacionServiceImpl.class);

	@Autowired
	private NotificacionDao notificacionDao;
	
	@Autowired
	private SigedApiConsumer sigedApiConsumer;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private ConfBandejaDao confBandejaDao;
	
	@Autowired
	private UsuarioRolDao usuarioRolDao;
	
	@Autowired
	private AsignacionDao asignacionDao;
	
	@Autowired
	private OtroRequisitoDao otroRequisitoDao;
	
	@Autowired
    private JavaMailSender  emailSender;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private SigedOldConsumer sigedOldConsumer;
	
	@Autowired
	private ArchivoService archivoService;
	
	@Autowired
	private ProcesoService procesoService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RequerimientoDao requerimientoDao;

	@Autowired
	private RequerimientoDocumentoDetalleDao requerimientoDocumentoDetalleDao;

	@Override
	public Notificacion obtener(Long idNotificacion, Contexto contexto) {
		Notificacion notificacion= notificacionDao.obtener(idNotificacion);
		return notificacion;
	}
	
	@Override
	public Page<Notificacion> buscar(Pageable pageable,Contexto contexto) {
		Page<Notificacion> requisitos =notificacionDao.buscar(pageable);
		return requisitos;
	}

	@Override
	public Notificacion guardar(Notificacion notificacion, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		Notificacion notificacionBD=notificacionDao.save(notificacion);
		return notificacionBD;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		notificacionDao.deleteById(id);
		
	}

	public void enviarCorreos() {
		logger.info("enviarCorreos inicio");
		List<Notificacion> listNotificaciones = notificacionDao.listarNotificacionV2(EstadoUtil.getEstadoNotificacionPendiente());
		ListadoDetalle estadoEnviado = listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.ENVIADO);
		ListadoDetalle estadoError = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.FALLADO);
		for(Notificacion notificacion:listNotificaciones) {
			logger.info("Procesando Notificacion: {}",notificacion.getIdNotificacion());
			List<File> archivos=archivoService.obtenerArchivosContenido(notificacion.getIdNotificacion());
			try {
				final MimeMessage mimeMessage = emailSender.createMimeMessage();
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
				message.setSubject(notificacion.getAsunto());
				message.setFrom(env.getProperty("spring.mail.username"));
				message.setTo(correosUnicos(notificacion.getCorreo()));				
	            message.setText(notificacion.getMensaje(), true);
	            if(archivos!=null) {
	            	for(File file:archivos) {
	            		message.addAttachment(file.getName(),file);
		            }
		            
	            }
	            emailSender.send(mimeMessage);
	            
				
	            notificacion.setEstado(estadoEnviado);	        
			}
			catch (Exception ex) {
				logger.error(ex.getMessage(),ex);
	            notificacion.setEstado(estadoError);
	        }
	        notificacionDao.save(notificacion);	
		}
		logger.info("enviarCorreos fin");
	}
	
	
	private String correosUnicos(String correos) {
		HashedMap mapa=new HashedMap();
		String correosArray[]=correos.split(";");
		for(String correo:correosArray) {
			mapa.put(correo, correo);	
		}
		String correoSalida="";
		Iterator<String> it=mapa.keySet().iterator();
		while(it.hasNext()) {			
			correoSalida=it.next()+";";
		}
		return correoSalida.substring(0,correoSalida.length()-1);
	}
	
	
	
	public String correoResponsableAdministrativo(Solicitud solicitud) {
		String correos="";
		//if(solicitud.getPersona().isPesonaJuridica()){
		if(solicitudService.validarJuridicoPostor(solicitud.getSolicitudUuid())){
			List<UsuarioRol> usuarios=usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.RESPONSABLE_ADMINISTRATIVO);
			for(UsuarioRol usuarioRol:usuarios) {
				Usuario usuario =usuarioDao.obtener(usuarioRol.getUsuario().getIdUsuario());
				if("".equals(correos)) {
				correos=usuario.getCorreo();
				}else {
					correos+=";"+usuario.getCorreo();
				}
			}
		}else {
			List<OtroRequisito> perfiles =otroRequisitoDao.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, solicitud.getIdSolicitud());
			Long codigosPerfil[]=new Long[perfiles.size()];
			for(int i = 0;i<codigosPerfil.length;i++) {
				OtroRequisito otroRequisito=perfiles.get(0);
				codigosPerfil[i]=otroRequisito.getPerfil().getIdListadoDetalle();
			};
			List<Usuario> usuarios = confBandejaDao.listarConfiguraciones(codigosPerfil,Constantes.ROLES.RESPONSABLE_ADMINISTRATIVO);
			for(Usuario usuario:usuarios) {
				Usuario usuarioBD =usuarioDao.obtener(usuario.getIdUsuario());
				if("".equals(correos)) {
				correos=usuarioBD.getCorreo();
				}else {
					correos+=";"+usuarioBD.getCorreo();
				}
			}
		}
		
		return correos;
	}
	
	public String correoResponsableTecnico(Solicitud solicitud) {
	String correos="";
	if(solicitudService.validarJuridicoPostor(solicitud.getSolicitudUuid())){
		List<UsuarioRol> usuarios=usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.RESPONSABLE_TECNICO);
		for(UsuarioRol usuarioRol:usuarios) {
			Usuario usuario =usuarioDao.obtener(usuarioRol.getUsuario().getIdUsuario());
			if("".equals(correos)) {
				
				correos=usuario.getCorreo();
			}else {
				correos+=";"+usuario.getCorreo();
			}
		}
	}else {
		List<OtroRequisito> perfiles =otroRequisitoDao.listarOtroRequisito(Constantes.LISTADO.TIPO_DOCUMENTO_ACREDITA.PERFIL, solicitud.getIdSolicitud());
		Long codigosPerfil[]=new Long[perfiles.size()];
		for(int i = 0;i<codigosPerfil.length;i++) {
			OtroRequisito otroRequisito=perfiles.get(0);
			codigosPerfil[i]=otroRequisito.getPerfil().getIdListadoDetalle();
		};
		List<Usuario> usuarios = confBandejaDao.listarConfiguraciones(codigosPerfil,Constantes.ROLES.RESPONSABLE_TECNICO);
		for(Usuario usuario:usuarios) {
			Usuario usuarioBD =usuarioDao.obtener(usuario.getIdUsuario());
			if("".equals(correos)) {
				
				correos=usuarioBD.getCorreo();
			}else {
				correos+=";"+usuarioBD.getCorreo();
			}
		}
	}
	
	return correos;
}
	
	public String correoEvaluadorTecnico(Solicitud solicitud) {
		String correos="";
		ListadoDetalle tecnico=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_EVALUADOR.CODIGO, Constantes.LISTADO.TIPO_EVALUADOR.TECNICO);
		List<Asignacion> asignaciones=asignacionDao.obtenerAsignaciones(solicitud.getIdSolicitud(),tecnico.getIdListadoDetalle());
		for(Asignacion asignado:asignaciones) {
			Usuario evaluador =usuarioDao.obtener(asignado.getUsuario().getIdUsuario());
			if("".equals(correos)) {
				
				correos=evaluador.getCorreo();
			}else {
				correos+=";"+evaluador.getCorreo();
			}
		}
		
		return correos;
	}
	
	public String correoEvaluadorAdministrativo(Solicitud solicitud) {
		String correos="";
		ListadoDetalle tecnico=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_EVALUADOR.CODIGO, Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO);
		List<Asignacion> asignaciones=asignacionDao.obtenerAsignaciones(solicitud.getIdSolicitud(),tecnico.getIdListadoDetalle());
		for(Asignacion asignado:asignaciones) {
			Usuario evaluador =usuarioDao.obtener(asignado.getUsuario().getIdUsuario());
			if("".equals(correos)) {
				
				correos=evaluador.getCorreo();
			}else {
				correos+=";"+evaluador.getCorreo();
			}
		}
		
		return correos;
	}
	
	public String correoAprobadorAdministrativo(Solicitud solicitud) {
		String correos="";
		ListadoDetalle administrativo=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_EVALUADOR.CODIGO, Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO);
		ListadoDetalle asignados=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO,Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO);
		List<Asignacion> asignaciones=asignacionDao.obtenerAsignacionesxEstado(solicitud.getIdSolicitud(),administrativo.getIdListadoDetalle(),asignados.getIdListadoDetalle());
		for(Asignacion asignado:asignaciones) {
			Usuario evaluador =usuarioDao.obtener(asignado.getUsuario().getIdUsuario());
			if("".equals(correos)) {
				
				correos=evaluador.getCorreo();
			}else {
				correos+=";"+evaluador.getCorreo();
			}
		}
		
		return correos;
	}
	
	/*
	public String correoAprobadorTecnico(Solicitud solicitud) {
		String correos="";
		ListadoDetalle tecnico=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_EVALUADOR.CODIGO, Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO);
		ListadoDetalle asignados=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO,Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO);
		List<Asignacion> asignaciones=asignacionDao.obtenerAsignacionesxEstado(solicitud.getIdSolicitud(),tecnico.getIdListadoDetalle(),asignados.getIdListadoDetalle());
		for(Asignacion asignado:asignaciones) {
			Usuario evaluador =usuarioDao.obtener(asignado.getUsuario().getIdUsuario());
			if("".equals(correos)) {
				
				correos=evaluador.getCorreo();
			}else {
				correos+=";"+evaluador.getCorreo();
			}
		}
		
		return correos;
	}*/
	
	public String correoResponsable(Contexto contexto) {
		Usuario responsable =usuarioDao.obtener(contexto.getUsuario().getIdUsuario());
		
		return responsable.getCorreo();
	}
	
	public String correoTodosEvaluadores(Solicitud solicitud) {
		String correos="";
		ListadoDetalle tecnico=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_EVALUADOR.CODIGO, Constantes.LISTADO.TIPO_EVALUADOR.TECNICO);
		List<Asignacion> asignacionesTecnico=asignacionDao.obtenerAsignaciones(solicitud.getIdSolicitud(),tecnico.getIdListadoDetalle());
		for(Asignacion asignado:asignacionesTecnico) {
			Usuario evaluador =usuarioDao.obtener(asignado.getUsuario().getIdUsuario());
			if("".equals(correos)) {
				
				correos=evaluador.getCorreo();
			}else {
				correos+=";"+evaluador.getCorreo();
			}
		}
		ListadoDetalle administrativo=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_EVALUADOR.CODIGO, Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO);
		List<Asignacion> asignacionesAdministrativo=asignacionDao.obtenerAsignaciones(solicitud.getIdSolicitud(),administrativo.getIdListadoDetalle());
		for(Asignacion asignado:asignacionesAdministrativo) {
			Usuario evaluador =usuarioDao.obtener(asignado.getUsuario().getIdUsuario());
			if("".equals(correos)) {
				
				correos=evaluador.getCorreo();
			}else {
				correos+=";"+evaluador.getCorreo();
			}
		}
		return correos;
	}
	
	public String correoTodosEvaluadoresNoAsignados() {
		String correos="";
		List<UsuarioRol> usuariosAdm=usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.EVALUADOR_ADMINISTRATIVO);
		for(UsuarioRol usuarioRolAdm:usuariosAdm) {
			Usuario evaluador =usuarioDao.obtener(usuarioRolAdm.getUsuario().getIdUsuario());
			if("".equals(correos)) {	
				correos=evaluador.getCorreo();
			}else {
				correos+=";"+evaluador.getCorreo();
			}
		}
		List<UsuarioRol> usuariosTec=usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.EVALUADOR_TECNICO);
		for(UsuarioRol usuarioRolTec:usuariosTec) {
			Usuario evaluador =usuarioDao.obtener(usuarioRolTec.getUsuario().getIdUsuario());
			if("".equals(correos)) {
				correos=evaluador.getCorreo();
			}else {
				correos+=";"+evaluador.getCorreo();
			}
		}
		return correos;
	}
	
	private Date calcularFechaFin(Date fechaPresentacion, Long dia) {		
		return sigedApiConsumer.calcularFechaFin(fechaPresentacion, dia, Constantes.DIAS_HABILES);
	}
	

	@Override
	public void enviarMensajeSolicitudInscripcion01(Solicitud solicitud, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(correoResponsableAdministrativo(solicitud)+";"+correoResponsableTecnico(solicitud)+";"+correoTodosEvaluadoresNoAsignados());
		notificacion.setAsunto("Nueva solicitud de Inscripción en el Registro de Empresa Supervisora - RUC "+solicitud.getPersona().getNumeroDocumento()+ "-" +solicitud.getNumeroExpediente());
		final Context ctx = new Context();
		if(solicitudService.validarJuridicoPostor(solicitud.getSolicitudUuid())){
			ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		}else {
			ctx.setVariable("razonSocial", solicitud.getPersona().getNombres()+" "+solicitud.getPersona().getApellidoPaterno()+" "+solicitud.getPersona().getApellidoMaterno());
		}
		ctx.setVariable("codigoRuc", solicitud.getPersona().getCodigoRuc());
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		ctx.setVariable("fechaIngreso",DateUtil.getDate(solicitud.getFechaRegistro(),"dd/MM/yyyy"));
		ctx.setVariable("plazo", DateUtil.getDate(solicitud.getFechaPlazoResp(),"dd/MM/yyyy"));	
		String htmlContent = templateEngine.process("01-solicitud_inscripcion.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}
	
	@Override
	public void enviarMensajeAsignacionEvaluacion02(Asignacion asignacion, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		Solicitud solicitudBD =solicitudService.obtener(asignacion.getSolicitud().getIdSolicitud(), contexto);
		String tipo =null;
		if(asignacion.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO)) {
			Usuario evaluadorAdm = usuarioService.obtener(asignacion.getUsuario().getIdUsuario());
			tipo="administrativa";
			notificacion.setCorreo(evaluadorAdm.getCorreo());
		}else if(asignacion.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO)) {
			Usuario evaluadorTecnico = usuarioService.obtener(asignacion.getUsuario().getIdUsuario());
			tipo="técnica";
			notificacion.setCorreo(evaluadorTecnico.getCorreo());
		}
		notificacion.setAsunto("Asignación de evaluación de solicitud de Inscripción en el Registro de Precalificación de Empresa Supervisora - RUC"+solicitudBD.getPersona().getNumeroDocumento()+ "-" +solicitudBD.getNumeroExpediente());
		final Context ctx = new Context();
		if(solicitudService.validarJuridicoPostor(solicitudBD.getSolicitudUuid())){
			ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombreRazonSocial());
		}else {
			ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombres()+" "+solicitudBD.getPersona().getApellidoPaterno()+" "+solicitudBD.getPersona().getApellidoMaterno());
		}
		ctx.setVariable("tipo", tipo);
		ctx.setVariable("codigoRuc", solicitudBD.getPersona().getCodigoRuc());
		ctx.setVariable("nroExpediente", solicitudBD.getNumeroExpediente());
		ctx.setVariable("fechaIngreso",DateUtil.getDate(asignacion.getFechaRegistro(),"dd/MM/yyyy"));
		ctx.setVariable("etapa", "Evaluación "+tipo);	
		ListadoDetalle plazoAsignar=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.CONCLUIR_EVALUACION_TECNICA);
		ctx.setVariable("plazo",  DateUtil.getDate(calcularFechaFin(solicitudBD.getFechaPresentacion(),Long.parseLong(plazoAsignar.getValor())),"dd/MM/yyyy"));	
		String htmlContent = templateEngine.process("02-asignacion-evaluacion.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}
	
	@Override
	public void enviarMensajeAsignacionEvaluacion04(Long idOtroRequisito, Contexto contexto) {
	    // 1. Obtener la asignación relacionada al requisito
	    Asignacion asignacion = asignacionDao.obtenerAsignacionPorIdOtroRequisito(idOtroRequisito);
	    if (asignacion == null) {
	        throw new ValidacionException("No existe asignación activa para el requisito: " + idOtroRequisito);
	    }

	    // 2. Usar el mismo flujo que enviarMensajeAsignacionEvaluacion03 pero con la asignación obtenida
	    Notificacion notificacion = new Notificacion();
	    Solicitud solicitudBD = solicitudService.obtener(asignacion.getSolicitud().getIdSolicitud(), contexto);
	    
	    Usuario evaluadorTecnico = usuarioService.obtener(asignacion.getUsuario().getIdUsuario());
        String tipo = "técnica";
        notificacion.setCorreo(evaluadorTecnico.getCorreo());
	    
	    // Mismo asunto que la versión 03
	    notificacion.setAsunto("Asignación de evaluación por RECHAZO DE APROBACIÓN en el Registro de Precalificación de Empresa Supervisora - RUC" + 
	                          solicitudBD.getPersona().getNumeroDocumento() + "-" + solicitudBD.getNumeroExpediente());
	    
	    String nombrePerfil = (otroRequisitoDao.obtener(idOtroRequisito) != null && otroRequisitoDao.obtener(idOtroRequisito).getPerfil() != null) ? otroRequisitoDao.obtener(idOtroRequisito).getPerfil().getNombre() : "";
	    
	    // Mismo contexto que la versión 03
	    final Context ctx = new Context();
	    if (solicitudService.validarJuridicoPostor(solicitudBD.getSolicitudUuid())) {
	        ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombreRazonSocial());
	    } else {
	        ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombres() + " " + 
	                       solicitudBD.getPersona().getApellidoPaterno() + " " + 
	                       solicitudBD.getPersona().getApellidoMaterno());
	    }
	    
	    // Mismas variables de contexto
	    ctx.setVariable("tipo", tipo);
	    ctx.setVariable("codigoRuc", solicitudBD.getPersona().getCodigoRuc());
	    ctx.setVariable("nroExpediente", solicitudBD.getNumeroExpediente());
	    ctx.setVariable("fechaIngreso", DateUtil.getDate(asignacion.getFechaRegistro(), "dd/MM/yyyy"));
	    ctx.setVariable("etapa", "Evaluación " + tipo);
	    ctx.setVariable("plazo", DateUtil.getDate(asignacion.getFechaPlazoResp(), "dd/MM/yyyy"));
	    ctx.setVariable("perfil", nombrePerfil);
	    
	    // Usar la misma plantilla HTML
	    String htmlContent = templateEngine.process("23-rechazo-evaluacion.html", ctx);
	    notificacion.setMensaje(htmlContent);
	    
	    // Mismo proceso de guardado
	    AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
	    ListadoDetalle estadoPendiente = listadoDetalleService.obtenerListadoDetalle(
	            Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
	            Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
	    notificacion.setEstado(estadoPendiente);
	    notificacionDao.save(notificacion);
	}
	
	@Override
	public void enviarMensajeSolicitudRevertirEvaluacion(Long idOtroRequisito, Contexto contexto) {
	    Asignacion asignacion = asignacionDao.obtenerAsignacionPorIdOtroRequisito(idOtroRequisito);
	    if (asignacion == null) {
	        throw new ValidacionException("No existe asignación activa para el requisito: " + idOtroRequisito);
	    }
	    Notificacion notificacion = new Notificacion();
	    Solicitud solicitudBD = solicitudService.obtener(asignacion.getSolicitud().getIdSolicitud(), contexto);
	    Usuario coordinadorDivision = usuarioService.obtener(solicitudBD.getDivision().getUsuario().getIdUsuario());

        String tipo = "técnica";
        notificacion.setCorreo(coordinadorDivision.getCorreo());
	    
	    notificacion.setAsunto("Solicitud para revertir evaluacion de la Empresa Supervisora - RUC " + 
	                          solicitudBD.getPersona().getNumeroDocumento() + " - " + solicitudBD.getNumeroExpediente());
	    
	    String nombrePerfil = (otroRequisitoDao.obtener(idOtroRequisito) != null && otroRequisitoDao.obtener(idOtroRequisito).getPerfil() != null) ? otroRequisitoDao.obtener(idOtroRequisito).getPerfil().getNombre() : "";
	    
	    final Context ctx = new Context();
	    if (solicitudService.validarJuridicoPostor(solicitudBD.getSolicitudUuid())) {
	        ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombreRazonSocial());
	    } else {
	        ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombres() + " " + 
	                       solicitudBD.getPersona().getApellidoPaterno() + " " + 
	                       solicitudBD.getPersona().getApellidoMaterno());
	    }
	    
	    ctx.setVariable("tipo", tipo);
	    ctx.setVariable("codigoRuc", solicitudBD.getPersona().getCodigoRuc());
	    ctx.setVariable("nroExpediente", solicitudBD.getNumeroExpediente());
	    ctx.setVariable("fechaIngreso", DateUtil.getDate(asignacion.getFechaRegistro(), "dd/MM/yyyy"));
	    ctx.setVariable("etapa", "Evaluación " + tipo);
	    ctx.setVariable("plazo", DateUtil.getDate(asignacion.getFechaPlazoResp(), "dd/MM/yyyy"));
	    ctx.setVariable("perfil", nombrePerfil);
	    
	    String htmlContent = templateEngine.process("24-solicitud-revertir-evaluacion.html", ctx);
	    notificacion.setMensaje(htmlContent);
	    
	    AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
	    ListadoDetalle estadoPendiente = listadoDetalleService.obtenerListadoDetalle(
	            Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
	            Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
	    notificacion.setEstado(estadoPendiente);
	    notificacionDao.save(notificacion);
	}
	
	@Override
	public void enviarMensajeAprobacionRevertirEvaluacion(Long idOtroRequisito, Contexto contexto) {
	    Asignacion asignacion = asignacionDao.obtenerAsignacionPorIdOtroRequisito(idOtroRequisito);
	    if (asignacion == null) {
	        throw new ValidacionException("No existe asignación activa para el requisito: " + idOtroRequisito);
	    }
	    Notificacion notificacion = new Notificacion();
	    Solicitud solicitudBD = solicitudService.obtener(asignacion.getSolicitud().getIdSolicitud(), contexto);
	    Usuario evaluadorTecnico = usuarioService.obtener(asignacion.getUsuario().getIdUsuario());

	    String tipo = "técnica";
        notificacion.setCorreo(evaluadorTecnico.getCorreo());
	    
	    notificacion.setAsunto("Se aprobó la solicitud de revertir evaluacion de la Empresa Supervisora - RUC " + 
	                          solicitudBD.getPersona().getNumeroDocumento() + " - " + solicitudBD.getNumeroExpediente());
	    
	    String nombrePerfil = (otroRequisitoDao.obtener(idOtroRequisito) != null && otroRequisitoDao.obtener(idOtroRequisito).getPerfil() != null) ? otroRequisitoDao.obtener(idOtroRequisito).getPerfil().getNombre() : "";
	    
	    final Context ctx = new Context();
	    if (solicitudService.validarJuridicoPostor(solicitudBD.getSolicitudUuid())) {
	        ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombreRazonSocial());
	    } else {
	        ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombres() + " " + 
	                       solicitudBD.getPersona().getApellidoPaterno() + " " + 
	                       solicitudBD.getPersona().getApellidoMaterno());
	    }
	    
	    ctx.setVariable("tipo", tipo);
	    ctx.setVariable("codigoRuc", solicitudBD.getPersona().getCodigoRuc());
	    ctx.setVariable("nroExpediente", solicitudBD.getNumeroExpediente());
	    ctx.setVariable("fechaIngreso", DateUtil.getDate(asignacion.getFechaRegistro(), "dd/MM/yyyy"));
	    ctx.setVariable("etapa", "Evaluación " + tipo);
	    ctx.setVariable("plazo", DateUtil.getDate(asignacion.getFechaPlazoResp(), "dd/MM/yyyy"));
	    ctx.setVariable("perfil", nombrePerfil);
	    
	    String htmlContent = templateEngine.process("25-aprobacion-revertir-evaluacion.html", ctx);
	    notificacion.setMensaje(htmlContent);
	    
	    AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
	    ListadoDetalle estadoPendiente = listadoDetalleService.obtenerListadoDetalle(
	            Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
	            Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
	    notificacion.setEstado(estadoPendiente);
	    notificacionDao.save(notificacion);
	}
	
	@Override
	public void enviarMensajeEvaluacionConcluida03(Solicitud solicitud,String type, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(correoResponsable(contexto));
		ListadoDetalle plazoRespuesta=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.CONCLUIR_EVALUACION_TECNICA);
		Date fechaPlazo=DateUtil.sumarDia(solicitud.getFechaPresentacion(), Long.parseLong(plazoRespuesta.getValor()));
		String tipo =null;
		if(type.equals(Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO)) {
			tipo="Administrativa";
		}else if(type.equals(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO)) {
			tipo="Técnica";
		}
		notificacion.setAsunto("Evaluación "+tipo+" ha concluido - RUC"+solicitud.getPersona().getNumeroDocumento()+ "-" +solicitud.getNumeroExpediente());
		final Context ctx = new Context();
		ctx.setVariable("tipo", tipo);
		ctx.setVariable("codigoRuc", solicitud.getPersona().getNumeroDocumento());
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		ctx.setVariable("fechaIngreso",DateUtil.getDate(solicitud.getFechaRegistro(),"dd/MM/yyyy"));
		ctx.setVariable("plazo", DateUtil.getDate(fechaPlazo,"dd/MM/yyyy"));
		String htmlContent = templateEngine.process("03-evaluacion-concluida.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajeResultadoSolicitud04(Solicitud solicitud, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(solicitud.getUsuario().getCorreo()+";"+correoResponsableAdministrativo(solicitud)+correoResponsableTecnico(solicitud));
		notificacion.setAsunto("Resultado de Solicitud de Inscripción - RUC "+solicitud.getPersona().getNumeroDocumento()+ "-" +solicitud.getNumeroExpediente());
		final Context ctx = new Context();
		ctx.setVariable("resultado", solicitud.getEstadoRevision());	
		ctx.setVariable("codigoRuc", solicitud.getPersona().getNumeroDocumento());
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		ctx.setVariable("fechaIngreso",DateUtil.getDate(solicitud.getFechaRegistro(),"dd/MM/yyyy"));
		String htmlContent = templateEngine.process("04-resultado-inscripcion.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}
	
	@Override
	public void enviarMensajeSubsanacionIObservaciones05(Solicitud solicitud, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(correoResponsableAdministrativo(solicitud)+";"+correoResponsableTecnico(solicitud)+";"+correoEvaluadorAdministrativo(solicitud)+";"+correoEvaluadorTecnico(solicitud));
		notificacion.setAsunto("Subsanación de observaciones de solicitud de Inscripción en el Registro de Precalificación de Empresa Supervisora - RUC"+solicitud.getPersona().getNumeroDocumento()+ "-" +solicitud.getNumeroExpediente());
		final Context ctx = new Context();
		ctx.setVariable("codigoRuc", solicitud.getPersona().getCodigoRuc());
		if(solicitud.getPersona().isPesonaJuridica()) {
			ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		}else {
			ctx.setVariable("razonSocial", solicitud.getPersona().getNombres()+" "+solicitud.getPersona().getApellidoPaterno()+" "+solicitud.getPersona().getApellidoMaterno());
		}
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		ctx.setVariable("fechaIngreso",DateUtil.getDate(solicitud.getFechaPresentacion(),"dd/MM/yyyy"));
		ctx.setVariable("plazo",DateUtil.getDate(solicitud.getFechaPlazoResp(),"dd/MM/yyyy"));
		String htmlContent = templateEngine.process("05-subsanacion-observaciones-inscripcion.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}
	
	
	
	@Override
	public void enviarMensajeAsignacionEvaluador06(Asignacion asignacion,String type, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		Solicitud solicitudBD =solicitudService.obtener(asignacion.getSolicitud().getIdSolicitud(), contexto);
		notificacion.setCorreo(correoResponsableAdministrativo(solicitudBD)+";"+correoResponsableTecnico(solicitudBD)+";"+correoEvaluadorTecnico(solicitudBD)+";"+correoEvaluadorAdministrativo(solicitudBD));
		ListadoDetalle plazoRespuesta=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.RESPUESTA_SUBSANACION_OBSERVACIONES);
		Date fechaPlazo=DateUtil.sumarDia(solicitudBD.getFechaPresentacion(), Long.parseLong(plazoRespuesta.getValor()));
		String tipo =null;
		if(type.equals(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO)) {
			tipo="Técnico";
		}else {
			tipo="Administrativo";
		}
		notificacion.setAsunto("Asignación de evaluador "+tipo+" fuera de plazo - RUC "+solicitudBD.getPersona().getNumeroDocumento()+ "-" +solicitudBD.getNumeroExpediente());
		final Context ctx = new Context();	
		ctx.setVariable("codigoRuc", solicitudBD.getPersona().getNumeroDocumento());
		ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombreRazonSocial());
		ctx.setVariable("nroExpediente", solicitudBD.getNumeroExpediente());
		ctx.setVariable("fechaIngreso",DateUtil.getDate(solicitudBD.getFechaRegistro(),"dd/MM/yyyy"));
		ctx.setVariable("plazo", DateUtil.getDate(fechaPlazo,"dd/MM/yyyy"));
		String htmlContent = templateEngine.process("06-asignacion-evaluador.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajePlazoConcluirTecnica07(Solicitud solicitud, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(correoResponsableTecnico(solicitud)+";"+correoEvaluadorTecnico(solicitud));
		notificacion.setAsunto("Queda 1 día de plazo para concluir la revisión Técnica - RUC "+solicitud.getPersona().getNumeroDocumento()+ "-" +solicitud.getNumeroExpediente());
		ListadoDetalle plazoRespuesta=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.CONCLUIR_EVALUACION_TECNICA);
		Date fechaPlazo=DateUtil.sumarDia(solicitud.getFechaPresentacion(), Long.parseLong(plazoRespuesta.getValor()));
		final Context ctx = new Context();	
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		ctx.setVariable("codigoRuc", solicitud.getPersona().getNumeroDocumento());
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("fechaIngreso",DateUtil.getDate(solicitud.getFechaRegistro(),"dd/MM/yyyy"));
		ctx.setVariable("plazo", DateUtil.getDate(fechaPlazo,"dd/MM/yyyy"));
		String htmlContent = templateEngine.process("07-plazo-revision-tecnica.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajeFueraPlazo08(Solicitud solicitud, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(correoResponsableTecnico(solicitud)+";"+correoEvaluadorTecnico(solicitud));
		notificacion.setAsunto("Revisión Técnica Fuera de Plazo - RUC "+solicitud.getPersona().getNumeroDocumento()+ "-" +solicitud.getNumeroExpediente());
		ListadoDetalle plazoRespuesta=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.CONCLUIR_EVALUACION_TECNICA);
		Date fechaPlazo=DateUtil.sumarDia(solicitud.getFechaPresentacion(), Long.parseLong(plazoRespuesta.getValor()));
		final Context ctx = new Context();	
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		ctx.setVariable("codigoRuc", solicitud.getPersona().getNumeroDocumento());
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("fechaIngreso",DateUtil.getDate(solicitud.getFechaRegistro(),"dd/MM/yyyy"));
		ctx.setVariable("plazo", DateUtil.getDate(fechaPlazo,"dd/MM/yyyy"));
		ctx.setVariable("diasAtraso", "restar fechaActual - fechaPlazo");
		String htmlContent = templateEngine.process("08-revision-fuera-plazo.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajePlazoRespuesta09(Solicitud solicitud, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(correoResponsableAdministrativo(solicitud)+";"+correoEvaluadorAdministrativo(solicitud));
		notificacion.setAsunto("Quedan 2 días de plazo para dar respuesta al solicitante - RUC "+solicitud.getPersona().getNumeroDocumento()+ "-" +solicitud.getNumeroExpediente());
		ListadoDetalle plazoRespuesta=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.CONCLUIR_EVALUACION_TECNICA);
		Date fechaPlazo=DateUtil.sumarDia(solicitud.getFechaPresentacion(), Long.parseLong(plazoRespuesta.getValor()));
		final Context ctx = new Context();	
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		ctx.setVariable("codigoRuc", solicitud.getPersona().getNumeroDocumento());
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("fechaIngreso",DateUtil.getDate(solicitud.getFechaRegistro(),"dd/MM/yyyy"));
		ctx.setVariable("plazo", DateUtil.getDate(fechaPlazo,"dd/MM/yyyy"));
		String htmlContent = templateEngine.process("09-plazo-respuesta.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajePlazoRespuestaVencido10(Solicitud solicitud, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(correoResponsableAdministrativo(solicitud)+";"+correoEvaluadorAdministrativo(solicitud)+";"+solicitud.getUsuario().getCorreo());
		notificacion.setAsunto("Respuesta a solicitante pendiente y Fuera de Plazo - RUC "+solicitud.getPersona().getNumeroDocumento()+ "-" +solicitud.getNumeroExpediente());
		ListadoDetalle plazoRespuesta=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.RESPUESTA_SOLICITUD);
		Date fechaPlazo=DateUtil.sumarDia(solicitud.getFechaPresentacion(), Long.parseLong(plazoRespuesta.getValor()));
		final Context ctx = new Context();	
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		ctx.setVariable("codigoRuc", solicitud.getPersona().getNumeroDocumento());
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("fechaIngreso",DateUtil.getDate(solicitud.getFechaRegistro(),"dd/MM/yyyy"));
		ctx.setVariable("plazo", DateUtil.getDate(fechaPlazo,"dd/MM/yyyy"));
		ctx.setVariable("diasAtraso", "restar fechaActual - fechaPlazo");
		String htmlContent = templateEngine.process("10-respuesta-solicitante.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}
	
	@Override
	public void enviarMensajeSuspension11(Solicitud solicitud,String type, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(solicitud.getUsuario().getCorreo()+";"+correoResponsable(contexto));
		final Context ctx = new Context();
		String tipo =null;
		if(type.equals(Constantes.LISTADO.TIPO_SUSPENSION_CANCELACION.SUSPENSION)) {
			tipo="suspensión";
			ctx.setVariable("fechaInicioSus", DateUtil.getDate(solicitud.getFechaPlazoAsig(),"dd/MM/yyyy"));
			ctx.setVariable("fechaFinSus", DateUtil.getDate(solicitud.getFechaPlazoAsig(),"dd/MM/yyyy"));
		}else {
			tipo="cancelación";
			ctx.setVariable("fechaInicioCan", DateUtil.getDate(solicitud.getFechaPlazoAsig(),"dd/MM/yyyy"));
		}
		ctx.setVariable("tipo", solicitud.getTipoSolicitud().getNombre());
		ctx.setVariable("type", tipo);
		ctx.setVariable("codigoRuc", solicitud.getPersona().getNumeroDocumento());
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		ctx.setVariable("fechaSuspension",DateUtil.getDate(solicitud.getFechaArchivamiento(),"dd/MM/yyyy"));
		ctx.setVariable("causal", solicitud.getObservacionNoCalifica());
		String htmlContent = templateEngine.process("11-suspension-cancelacion.html", ctx);
		notificacion.setAsunto("Se realizó "+tipo+" en el Registro de Empresas Supervisoras del RUC "+solicitud.getPersona().getNumeroDocumento()+"-" +solicitud.getNumeroExpediente());
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajeActualizacion12(Solicitud solicitud, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(correoResponsableAdministrativo(solicitud)+";"+correoResponsableTecnico(solicitud)+";"+correoEvaluadorTecnico(solicitud)+";"+correoEvaluadorAdministrativo(solicitud)+";"+solicitud.getUsuario().getCorreo());
		notificacion.setAsunto("Se realizó actualización en el Registro de Empresas Supervisoras del RUC "+solicitud.getPersona().getNumeroDocumento()+"-" +solicitud.getNumeroExpediente());
		final Context ctx = new Context();
		ctx.setVariable("codigoRuc", solicitud.getPersona().getNumeroDocumento());
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		ctx.setVariable("fechaActualizacion",DateUtil.getDate(solicitud.getFechaRegistro(),"dd/MM/yyyy"));
		String htmlContent = templateEngine.process("12-realizo-actualizacion.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajeCorreoValidacion13(Notificacion notificacion, Contexto contexto) {
		Notificacion notificacionNueva = new Notificacion();
		notificacionNueva.setCorreo(notificacion.getCorreo());
		notificacionNueva.setAsunto(notificacion.getAsunto());
		final Context ctx = new Context();
		ctx.setVariable("codigoValidacion", notificacion.getMensaje());
		String htmlContent = templateEngine.process("13-validacion-codigo.html", ctx);
		notificacionNueva.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacionNueva,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacionNueva.setEstado(estadoPendiente);
		notificacionDao.save(notificacionNueva);
	}

	@Override
	public void enviarMensajeAsignacionAprobacion14(Asignacion asignacion, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		Solicitud solicitudBD =solicitudService.obtener(asignacion.getSolicitud().getIdSolicitud(), contexto);
		ListadoDetalle plazoRespuesta=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.RESPUESTA_SOLICITUD);		
		String tipo =null;
		if(asignacion.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO)) {
			tipo="administrativa";
			notificacion.setCorreo(correoAprobadorAdministrativo(solicitudBD));
		}else {
			tipo="técnica";
			Usuario evaluador =usuarioDao.obtener(asignacion.getUsuario().getIdUsuario());
			String correo=evaluador.getCorreo();
			notificacion.setCorreo(correo);
		}
		notificacion.setAsunto("Asignación de aprobación de solicitud de Inscripción en el Registro de Precalificación de Empresa Supervisora - RUC "+solicitudBD.getPersona().getNumeroDocumento()+ "-" +solicitudBD.getNumeroExpediente());
		final Context ctx = new Context();
//		if(solicitudBD.getPersona().isPesonaJuridica()) {
		if(solicitudService.validarJuridicoPostor(solicitudBD.getSolicitudUuid())){
			ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombreRazonSocial());
		}else {
			ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombres()+" "+solicitudBD.getPersona().getApellidoPaterno()+" "+solicitudBD.getPersona().getApellidoMaterno());
		}
		ctx.setVariable("tipo", tipo);
		ctx.setVariable("codigoRuc", solicitudBD.getPersona().getCodigoRuc());
		ctx.setVariable("nroExpediente", solicitudBD.getNumeroExpediente());
		ctx.setVariable("fechaIngreso",DateUtil.getDate(asignacion.getFechaRegistro(),"dd/MM/yyyy"));
		ctx.setVariable("plazo",DateUtil.getDate(solicitudBD.getFechaPlazoResp(),"dd/MM/yyyy"));	
		String htmlContent = templateEngine.process("14-asignacion-aprobacion.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
		
	}
	
	@Override
	public void enviarMensajeSolicitudInscripcionExtranjera15(Solicitud solicitud, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(solicitud.getPersona().getCorreo());
		notificacion.setAsunto("Registro de Solicitud de Inscripción en el Registro de Precalificación de Empresa Supervisora - RUC "+solicitud.getPersona().getCodigoRuc()+ "-" +solicitud.getNumeroExpediente());
		final Context ctx = new Context();
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("codigoRuc", solicitud.getPersona().getCodigoRuc());
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		String htmlContent = templateEngine.process("15-solicitud_inscripcion_extranjera.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}
	
	@Override
	public void enviarMensajeSolicitudSubsanacionExtranjera16(Solicitud solicitud, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(solicitud.getPersona().getCorreo());
		notificacion.setAsunto("Subsanacion de solicitud de Inscripción en el Registro de Precalificación de Empresa Supervisora - RUC "+solicitud.getPersona().getCodigoRuc()+ "-" +solicitud.getNumeroExpediente());
		final Context ctx = new Context();
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("codigoRuc", solicitud.getPersona().getCodigoRuc());
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		String htmlContent = templateEngine.process("16-solicitud_subsanacion_extranjera.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}
	
	@Override
	public void enviarMensajeSolicitudResultadoExtranjera17(Solicitud solicitud,Archivo archivo, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(solicitud.getPersona().getCorreo());
		notificacion.setAsunto("Resultado de la Solicitud de Inscripción - RUC "+solicitud.getPersona().getCodigoRuc()+ "-" +solicitud.getNumeroExpediente());
		final Context ctx = new Context();
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("codigoRuc", solicitud.getPersona().getCodigoRuc());
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		String htmlContent = templateEngine.process("17-solicitud_resultado_extranjera.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacion=notificacionDao.save(notificacion);
		archivo.setIdNotificacion(notificacion.getIdNotificacion());
		archivoService.guardar(archivo, contexto);
	}
	
	@Override
	public void enviarMensajeSolicitudSubsanacionResultadoExtranjera18(Solicitud solicitud,Archivo archivo, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(solicitud.getPersona().getCorreo());
		notificacion.setAsunto("Resultado de la Subsanación de Solicitud de Inscripción - RUC "+solicitud.getPersona().getCodigoRuc()+ "-" +solicitud.getNumeroExpediente());
		final Context ctx = new Context();
		ctx.setVariable("razonSocial", solicitud.getPersona().getNombreRazonSocial());
		ctx.setVariable("codigoRuc", solicitud.getPersona().getCodigoRuc());
		ctx.setVariable("nroExpediente", solicitud.getNumeroExpediente());
		String htmlContent = templateEngine.process("18-solicitud_subsanacion_resultado_extranjera.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacion=notificacionDao.save(notificacion);
		archivo.setIdNotificacion(notificacion.getIdNotificacion());
		archivoService.guardar(archivo, contexto);
	}

	@Override
	public void enviarMensajePresentacionPropuesta(Propuesta propuesta,int numeroDocTec, int numeroDocEco, Contexto contexto) {
		Proceso procesoBD =procesoService.obtener(propuesta.getProcesoItem().getProceso().getProcesoUuid() ,contexto);
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(contexto.getUsuario().getCorreo());
		notificacion.setAsunto("Correo de confirmación");
		final Context ctx = new Context();
		ctx.setVariable("fechaPresentacion", DateUtil.getDate(propuesta.getFechaPresentacion(),"dd/MM/yyyy"));
		ctx.setVariable("proceso", procesoBD.getNumeroProceso()+" "+procesoBD.getNombreProceso());
		ctx.setVariable("item", propuesta.getProcesoItem().getNumeroItem()+" "+propuesta.getProcesoItem().getDescripcionItem());
		ctx.setVariable("numeroDocTec", numeroDocTec);
		ctx.setVariable("numeroDocEco", numeroDocEco);
		String htmlContent = templateEngine.process("19-confirmacion-propuesta.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacion=notificacionDao.save(notificacion);
	}
	
	@Override
	public void enviarMensajeAsignacionEvaluacion03(Asignacion asignacion, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		Solicitud solicitudBD = solicitudService.obtener(asignacion.getSolicitud().getIdSolicitud(), contexto);
		String tipo = null;
		if (asignacion.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO)) {
			Usuario evaluadorAdm = usuarioService.obtener(asignacion.getUsuario().getIdUsuario());
			tipo = "administrativa";
			notificacion.setCorreo(evaluadorAdm.getCorreo());
		} else if (asignacion.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO)) {
			Usuario evaluadorTecnico = usuarioService.obtener(asignacion.getUsuario().getIdUsuario());
			tipo = "técnica";
			notificacion.setCorreo(evaluadorTecnico.getCorreo());
		}
		notificacion.setAsunto("Asignación de evaluación de solicitud de Inscripción en el Registro de Precalificación de Empresa Supervisora - RUC" + solicitudBD.getPersona().getNumeroDocumento() + "-" + solicitudBD.getNumeroExpediente());
		final Context ctx = new Context();
		if (solicitudService.validarJuridicoPostor(solicitudBD.getSolicitudUuid())) {
			ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombreRazonSocial());
		} else {
			ctx.setVariable("razonSocial", solicitudBD.getPersona().getNombres() + " " + solicitudBD.getPersona().getApellidoPaterno() +" " + solicitudBD.getPersona().getApellidoMaterno());
		}
		ctx.setVariable("tipo", tipo);
		ctx.setVariable("codigoRuc", solicitudBD.getPersona().getCodigoRuc());
		ctx.setVariable("nroExpediente", solicitudBD.getNumeroExpediente());
		ctx.setVariable("fechaIngreso", DateUtil.getDate(asignacion.getFechaRegistro(), "dd/MM/yyyy"));
		ctx.setVariable("etapa", "Evaluación " + tipo);
		ctx.setVariable("plazo", DateUtil.getDate(asignacion.getFechaPlazoResp(),"dd/MM/yyyy"));	
		String htmlContent = templateEngine.process("02-asignacion-evaluacion.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente = listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}
	
	@Override
	public void enviarMensajeEvaluacionPendiente(String asunto, String correo, String tipo, String table, Contexto contexto) {
		
		logger.info("enviarMensajeEvaluacionPendiente inicio");
	
		final Context ctx = new Context();		
		ctx.setVariable("tipo", tipo);
		
		String htmlContent = templateEngine.process("20-notificacion-evaluacion-pendiente.html", ctx);
		
		htmlContent = htmlContent.replace("evaluaciones", table);
		
		try {
			final MimeMessage mimeMessage = emailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
			
			message.setSubject(asunto);
			message.setFrom(env.getProperty("spring.mail.username"));
			message.setTo(correo);
            message.setText(htmlContent, true);
            
            emailSender.send(mimeMessage);
            
            logger.info("Notificación enviada: " + correo);
		}
		catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
        }
		
		logger.info("enviarMensajeEvaluacionPendiente fin");
	}

	@Override
	public void enviarMensajeAprobacionRechazoReqInvitacion(RequerimientoInvitacion invitacion, List<String> correos, boolean esAprobacion, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setAsunto((esAprobacion ? "ACEPTACIÓN" : "RECHAZO") + " DE INVITACIÓN SUPERVISOR PERSONA NATURAL");
		final Context ctx = new Context();
		ctx.setVariable("esAprobacion", esAprobacion);
		ctx.setVariable("nombre", invitacion.getSupervisora().getNombreRazonSocial());
		ctx.setVariable("division", invitacion.getRequerimiento().getDivision().getDeDivision());
		ctx.setVariable("fechaInvitacion", DateUtil.getDate(invitacion.getFechaInvitacion(), "dd/MM/yyyy"));
		ctx.setVariable("fechaCaducidad", DateUtil.getDate(invitacion.getFechaCaducidad(), "dd/MM/yyyy"));
		ctx.setVariable("fechaAceptacion", DateUtil.getDate(invitacion.getFechaAceptacion(), "dd/MM/yyyy"));
		String htmlContent = templateEngine.process("26-aprobacion-invitacion.html", ctx);
		notificacion.setMensaje(htmlContent);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
				Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		for(String correo: correos) {
			notificacion.setCorreo(correo);
			AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
			notificacionDao.save(notificacion);
		}
	}

	@Override
	public void enviarMensajeRequerimientoPorAprobar(Requerimiento requerimiento, Usuario usuario, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(usuario.getCorreo());
		notificacion.setAsunto("NOTIFICACIÓN PARA APROBAR");
		final Context ctx = new Context();
		ctx.setVariable("nombre", usuario.getNombreUsuario());
		ctx.setVariable("nuExpediente", requerimiento.getNuExpediente());
		String htmlContent = templateEngine.process("30-requerimiento-por-aprobar.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
				Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajeRechazoRequerimiento(Requerimiento requerimiento, Usuario usuario, String rol, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(usuario.getCorreo());
		notificacion.setAsunto("NOTIFICACIÓN DE RECHAZAR APROBACIÓN");
		final Context ctx = new Context();
		ctx.setVariable("nombre", usuario.getNombreUsuario());
		ctx.setVariable("nombreRol", contexto.getUsuario().getNombreUsuario());
		ctx.setVariable("rol", rol);
		ctx.setVariable("nuExpediente", requerimiento.getNuExpediente());
		String htmlContent = templateEngine.process("31-requerimiento-rechazado.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
				Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajeCargarDocumentosRequerimiento(Requerimiento requerimiento, Contexto contexto) {
		Supervisora supervisora = requerimiento.getSupervisora();
		String tipoDocumento = supervisora.getTipoDocumento().getCodigo();
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(supervisora.getCorreo());
		notificacion.setAsunto("NOTIFICACIÓN CARGAR DOCUMENTOS");
		final Context ctx = new Context();
		if (tipoDocumento.equalsIgnoreCase(Constantes.LISTADO.TIPO_DOCUMENTO.RUC)){
			ctx.setVariable("nombre", supervisora.getNombreRazonSocial());
		} else if (tipoDocumento.equalsIgnoreCase(Constantes.LISTADO.TIPO_DOCUMENTO.DNI)) {
			ctx.setVariable("nombre", supervisora.getNombres()+" "+supervisora.getApellidoPaterno()+" "+supervisora.getApellidoMaterno());
		}
		ctx.setVariable("nuExpediente", requerimiento.getNuExpediente());
		String htmlContent = templateEngine.process("29-requerimiento-cargar-documentos.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
				Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajeSolicitudFirmaArchivamientoRequerimiento(Usuario aprobadorG2, Requerimiento requerimiento, Contexto contexto) {
			Notificacion notificacion = new Notificacion();
			String correos = aprobadorG2.getCorreo();
			correos = "tripalovski5@gmail.com";
			notificacion.setCorreo(correos);
			notificacion.setAsunto("NOTIFICACIÓN PARA FIRMAR ARCHIVAMIENTO DE REQUERIMIENTO");
			final Context ctx = new Context();
			ctx.setVariable("nombre_rol_gerente", aprobadorG2.getNombreUsuario());
			ctx.setVariable("expediente", requerimiento.getNuExpediente());
			String htmlContent = templateEngine.process("27-solicitud-firma-archivamiento-requerimiento.html", ctx);
			notificacion.setMensaje(htmlContent);
			AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
			ListadoDetalle estadoPendiente = listadoDetalleService.obtenerListadoDetalle(
							Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
							Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
			notificacion.setEstado(estadoPendiente);
			notificacionDao.save(notificacion);
	}

	@Override
	public void enviarRequerimientoInvitacion(Supervisora supervisoraPN, RequerimientoInvitacion requerimientoInvitacion, Contexto contexto) {
			Notificacion notificacion = new Notificacion();
			String correos = supervisoraPN.getCorreo();
			notificacion.setCorreo(correos);
			notificacion.setAsunto("INVITACIÓN PERSONA NATURAL S4");
			final Context ctx = new Context();
			ctx.setVariable("nombre_supervisor_pn", supervisoraPN.getNombres());
			Requerimiento requerimiento = requerimientoDao.obtener(requerimientoInvitacion.getRequerimiento().getIdRequerimiento())
				.orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_NO_ENCONTRADO));
			String tipoDocumento = supervisoraPN.getTipoDocumento().getCodigo();
			if (tipoDocumento.equalsIgnoreCase(Constantes.LISTADO.TIPO_DOCUMENTO.RUC)){
				ctx.setVariable("nombre_supervisor_pn", supervisoraPN.getNombreRazonSocial());
			} else if (tipoDocumento.equalsIgnoreCase(Constantes.LISTADO.TIPO_DOCUMENTO.DNI)) {
				ctx.setVariable("nombre_supervisor_pn", supervisoraPN.getNombres()+" "+supervisoraPN.getApellidoPaterno()+" "+supervisoraPN.getApellidoMaterno());
			}
			ctx.setVariable("division", requerimiento.getDivision().getDeDivision());
			ctx.setVariable("fechaInvitacion", DateUtil.getDate(requerimientoInvitacion.getFechaInvitacion(), "dd/MM/yyyy HH:mm"));
			ctx.setVariable("fechaCancelacion", DateUtil.getDate(requerimientoInvitacion.getFechaCaducidad(), "dd/MM/yyyy HH:mm"));
			String htmlContent = templateEngine.process("28-invitacion-requerimiento.html", ctx);
			notificacion.setMensaje(htmlContent);
			AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
			ListadoDetalle estadoPendiente = listadoDetalleService.obtenerListadoDetalle(
							Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
							Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
			notificacion.setEstado(estadoPendiente);
			notificacionDao.save(notificacion);
	}

	@Override
	public void enviarRequerimientoEvaluacion(Supervisora supervisoraPN, RequerimientoDocumento requerimientoDocumento, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		String correos = supervisoraPN.getCorreo();
		notificacion.setCorreo(correos);
		notificacion.setAsunto("SUBSANAR CARGA DE DOCUMENTOS");
		final Context ctx = new Context();
		ctx.setVariable("nombre_supervisor_pn", supervisoraPN.getNombres());
		Long tipoDocumento = supervisoraPN.getTipoDocumento().getIdListadoDetalle();
		if (tipoDocumento == 2){
			ctx.setVariable("nombre_supervisor_pn", supervisoraPN.getNombreRazonSocial());
		} else if (tipoDocumento == 3) {
			ctx.setVariable("nombre_supervisor_pn", supervisoraPN.getNombres()+" "+supervisoraPN.getApellidoPaterno()+" "+supervisoraPN.getApellidoMaterno());
		}
		ctx.setVariable("detalles", requerimientoDocumentoDetalleDao.listarPorUuid(requerimientoDocumento.getRequerimientoDocumentoUuid()));
		String htmlContent = templateEngine.process("31-evaluacion-requerimiento-documento.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
		ListadoDetalle estadoPendiente = listadoDetalleService.obtenerListadoDetalle(
				Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
				Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajeVistoBuenoCoordinador(String correo, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(correo);
		notificacion.setAsunto("OBSERVACIÓN INFORME");
		final Context ctx = new Context();
		String htmlContent = templateEngine.process("32-documento-sin-visto-bueno-coordinador.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
				Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajeVistoBuenoSupervisor(Supervisora supervisora, List<RequerimientoDocumentoDetalle> listaReqDocDetalle, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(supervisora.getCorreo());
		notificacion.setAsunto("NOTIFICACIÓN CARGAR DOCUMENTOS");
		final Context ctx = new Context();
		String tipoDocumento = supervisora.getTipoDocumento().getCodigo();
		if (tipoDocumento.equalsIgnoreCase(Constantes.LISTADO.TIPO_DOCUMENTO.RUC)){
			ctx.setVariable("nombre", supervisora.getNombreRazonSocial());
		} else if (tipoDocumento.equalsIgnoreCase(Constantes.LISTADO.TIPO_DOCUMENTO.DNI)) {
			ctx.setVariable("nombre", supervisora.getNombres()+" "+supervisora.getApellidoPaterno()+" "+supervisora.getApellidoMaterno());
		}
		ctx.setVariable("documentos", listaReqDocDetalle);
		String htmlContent = templateEngine.process("33-documento-sin-visto-bueno-supervisor.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
				Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

	@Override
	public void enviarMensajeFinalizacionContratacion(Supervisora supervisora, Contexto contexto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setCorreo(supervisora.getCorreo());
		notificacion.setAsunto("FINALIZACIÓN DEL PROCESO DE CONTRATACIÓN");
		final Context ctx = new Context();
		String tipoDocumento = supervisora.getTipoDocumento().getCodigo();
		if (tipoDocumento.equalsIgnoreCase(Constantes.LISTADO.TIPO_DOCUMENTO.RUC)){
			ctx.setVariable("nombre", supervisora.getNombreRazonSocial());
		} else if (tipoDocumento.equalsIgnoreCase(Constantes.LISTADO.TIPO_DOCUMENTO.DNI)) {
			ctx.setVariable("nombre", supervisora.getNombres()+" "+supervisora.getApellidoPaterno()+" "+supervisora.getApellidoMaterno());
		}
		String htmlContent = templateEngine.process("34-fin-contratacion.html", ctx);
		notificacion.setMensaje(htmlContent);
		AuditoriaUtil.setAuditoriaRegistro(notificacion,contexto);
		ListadoDetalle estadoPendiente	= listadoDetalleService.obtenerListadoDetalle( Constantes.LISTADO.ESTADO_NOTIFICACIONES.CODIGO,
				Constantes.LISTADO.ESTADO_NOTIFICACIONES.PENDIENTE);
		notificacion.setEstado(estadoPendiente);
		notificacionDao.save(notificacion);
	}

}
