package pe.gob.osinergmin.sicoes.service.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.consumer.SissegApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SneApiConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.HistorialAprobador;
import pe.gob.osinergmin.sicoes.model.HistorialVacaciones;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.model.PerfilAprobador;
import pe.gob.osinergmin.sicoes.model.PerfilDivision;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.DetalleVacacionesDTO;
import pe.gob.osinergmin.sicoes.model.dto.EvaluacionPendienteDTO;
import pe.gob.osinergmin.sicoes.repository.AsignacionDao;
import pe.gob.osinergmin.sicoes.repository.AsignacionPerfilDivisionDao;
import pe.gob.osinergmin.sicoes.repository.EvaluacionPendienteDao;
import pe.gob.osinergmin.sicoes.repository.HistorialAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.HistorialVacacionesDao;
import pe.gob.osinergmin.sicoes.repository.OtroRequisitoDao;
import pe.gob.osinergmin.sicoes.repository.PerfilAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.PerfilDivisionDao;
import pe.gob.osinergmin.sicoes.repository.SolicitudDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.AsignacionService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.OtroRequisitoService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.bean.siged.AccessRequestInFirmaDigital;


@Service
public class AsignacionServiceImpl implements AsignacionService{
	
	Logger logger = LogManager.getLogger(AsignacionServiceImpl.class);

	@Autowired
	private AsignacionDao asignacionDao;
	
	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService;
	
	@Autowired
	private NotificacionService notificacionService; 
	
	@Autowired
	private UsuarioService  usuarioService;
	
	@Autowired
	private SigedApiConsumer sigedApiConsumer;
	
	@Autowired
	private ArchivoService archivoService; 
	
	@Autowired
	private Environment env;
	
	@Value("${path.temporal}")
	private String pathTemporal;
	
	@Autowired
	OtroRequisitoService otroRequisitoService;
	
	@Autowired
	SolicitudDao solicitudDao;
	
	@Autowired
	private EvaluacionPendienteDao evaluacionPendienteDao;
	
	@Autowired
	private SneApiConsumer sneApiConsumer;
	
	@Autowired
	private HistorialVacacionesDao historialVacacionesDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private PerfilAprobadorDao perfilAprobadorDao;
	
	@Autowired
	private HistorialAprobadorDao historialAprobadorDao;
	
	@Autowired
	private PerfilDivisionDao perfilAprobador;
	
	@Autowired
	private AsignacionPerfilDivisionDao asignacionPerfilDivisionDao;
	
	@Autowired
	private SigedOldConsumer sigedOldConsumer;
	
	@Autowired
	private SissegApiConsumer sissegApiConsumer;
	
	@Autowired
	private OtroRequisitoDao otroRequisitoDao;
	
	@Override
	public Asignacion obtener(Long idEstudio, Contexto contexto) {
		Asignacion estudio= asignacionDao.obtener(idEstudio);
		return estudio;
	}
	
	@Override
	public Page<Asignacion> buscar(Long idSolicitud,String codigoTipoAprobador,Pageable pageable,Contexto contexto) {
		Page<Asignacion> asignaciones =asignacionDao.buscar(idSolicitud,codigoTipoAprobador,pageable);
		return asignaciones;
	}

	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public List<Asignacion> guardar(List<Asignacion> asignaciones, Contexto contexto) {
		Asignacion asignacionBD=null;
		validar( asignaciones,true,  contexto);
		
		for(Asignacion asignacion:asignaciones) {
			Long idSolicitud = solicitudService.obtenerId(asignacion.getSolicitud().getSolicitudUuid());
			asignacion.getSolicitud().setIdSolicitud(idSolicitud);
			asignacion.setFlagActivo(Constantes.FLAG.ACTIVO);
			asignacion.setFechaRegistro(new Date());
			AuditoriaUtil.setAuditoriaRegistro(asignacion,contexto);
			ListadoDetalle tipo= listadoDetalleService.obtener(asignacion.getTipo().getIdListadoDetalle(), contexto);
			asignacion.setTipo(tipo);	
			if(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO.equals(asignacion.getTipo().getCodigo())) {
				ListadoDetalle plazoConcluir=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.CONCLUIR_EVALUACION_TECNICA);
				asignacion.setNumeroPlazoResp(Long.parseLong(plazoConcluir.getValor()));
				asignacion.setFechaPlazoResp(calcularFechaFin(asignacion.getFechaRegistro(),asignacion.getNumeroPlazoResp()));		
				asignacion.setEvaluacion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.REGISTRADO));
			}
			asignacionBD=asignacionDao.save(asignacion);
			if(asignacionBD.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO)) {
				//notificacionService.enviarMensajeAsignacionAprobacion14(asignacionBD, contexto);
			}else if(asignacionBD.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO)) {
				//notificacionService.enviarMensajeAsignacionAprobacion14(asignacionBD, contexto);
			}else {
				notificacionService.enviarMensajeAsignacionEvaluacion02(asignacionBD, contexto);	
			}
		}
		solicitudService.actualizarAsignado(asignacionBD,contexto);		
		
		return asignaciones;
	}
	
	private void validar(List<Asignacion> asignaciones,Boolean crearBoolean , Contexto contexto) {
		Solicitud solicitud = null;
		if(asignaciones==null||asignaciones.isEmpty()) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ASIGNE_EVALUADORES);
		}
		Map<Long, Long> evaluadores=new HashMap<Long,Long>();
		Map<Long, Long> grupos=new HashMap<Long,Long>();
		for(Asignacion asignacion:asignaciones) {
			if(asignacion.getSolicitud()==null&&asignacion.getSolicitud().getSolicitudUuid()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
			}
			if(asignacion.getUsuario()==null&&asignacion.getUsuario().getIdUsuario()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_USUARIO_NO_ENVIADO);
			}
			if(asignacion.getTipo()==null&&asignacion.getTipo().getIdListadoDetalle()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_TIPO_NO_ENVIADO);
			}
			if(asignacion.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO)) {
				if(asignacion.getGrupo()==null&&asignacion.getGrupo().getIdListadoDetalle()==null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_GRUPO_NO_ENVIADO);
				}
				ListadoDetalle grupo=listadoDetalleService.obtener(asignacion.getGrupo().getIdListadoDetalle(), contexto);
				grupos.put(grupo.getOrden(), grupo.getOrden());
			}
			evaluadores.put(asignacion.getUsuario().getIdUsuario(), asignacion.getUsuario().getIdUsuario());
			solicitud = solicitudDao.obtener(asignacion.getSolicitud().getIdSolicitud());
		}
		
		/*if(evaluadores.size()!=asignaciones.size()) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUADORES_REPETIDOS);
		}*/
		
		if (solicitud != null && solicitud.getDivision() != null && !solicitud.getDivision().getIdDivision().equals(Constantes.DIVISION.IDENTIFICADOR.ID_GSM)) {
			for(Long grupo:grupos.values()) {
				if(crearBoolean && grupo != 1L && grupos.get(grupo-1L) == null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.AGREGAR_GRUPO_MENOR);
				}
			}
			for(Long i=1L;i<=grupos.size();i++) {
				if(grupos.get(i)==null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.GRUPOS_NO_EXISTE);
				}
			}
		}
	}

	private Date calcularFechaFin(Date fechaPresentacion, Long dia) {
		return sigedApiConsumer.calcularFechaFin(fechaPresentacion, dia, Constantes.DIAS_HABILES);
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		asignacionDao.deleteById(id);
		
	}
	public Asignacion guardar(Asignacion asignacion) {
		return asignacionDao.save(asignacion);
	}
	
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public Asignacion guardarAprobador(Asignacion asignacion, Contexto contexto) {
		
		if(asignacion.getSolicitud()==null&&asignacion.getSolicitud().getSolicitudUuid()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		
		Asignacion asignacionBD=null;
		Long idSolicitud = solicitudService.obtenerId(asignacion.getSolicitud().getSolicitudUuid());
		
		//Validar que el usuario no este registrado
		List<Asignacion> listaAsignaciones = asignacionDao.obtenerAsignaciones(idSolicitud);
		
		for (Asignacion asig : listaAsignaciones) {
			
			if (asig.getUsuario().getIdUsuario().equals(asignacion.getUsuario().getIdUsuario())){
				return asignacionBD;
			}
		}
		
		asignacion.getSolicitud().setIdSolicitud(idSolicitud);		
		asignacion.setFlagActivo(Constantes.FLAG.ACTIVO);
		asignacion.setFechaRegistro(new Date());
		AuditoriaUtil.setAuditoriaRegistro(asignacion,contexto);
		ListadoDetalle tipo= listadoDetalleService.obtener(asignacion.getTipo().getIdListadoDetalle(), contexto);
		asignacion.setTipo(tipo);	
		if(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO.equals(asignacion.getTipo().getCodigo())) {
			ListadoDetalle plazoConcluir=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.CONCLUIR_EVALUACION_TECNICA);
			asignacion.setNumeroPlazoResp(Long.parseLong(plazoConcluir.getValor()));
			asignacion.setFechaPlazoResp(calcularFechaFin(asignacion.getFechaRegistro(),asignacion.getNumeroPlazoResp()));		
			asignacion.setEvaluacion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.REGISTRADO));
		}
		asignacionBD=asignacionDao.save(asignacion);		
		//notificacionService.enviarMensajeAsignacionAprobacion14(asignacion, contexto); //Inicio cambio
		solicitudService.actualizarAsignado(asignacionBD,contexto);		
		Pageable pageable = PageRequest.of(0, Integer.parseInt(env.getProperty("maximo.paginas")));
		Page<Asignacion> page = asignacionDao.buscar(asignacion.getSolicitud().getIdSolicitud(), Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO, pageable);
		validar( page.getContent(),true,  contexto);
		return asignacionBD;
	}

	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public boolean modificarAprobador(Asignacion asignacion, Contexto contexto) {
		boolean modify = false;
			if(asignacion.getSolicitud()==null&&asignacion.getSolicitud().getSolicitudUuid()==null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
			}
			Long idSolicitud = solicitudService.obtenerId(asignacion.getSolicitud().getSolicitudUuid());
			asignacion.getSolicitud().setIdSolicitud(idSolicitud);
			
			Asignacion asignacionBD = null;
			asignacionBD = asignacionDao.obtener(asignacion.getIdAsignacion());
			if (asignacionBD.getEvaluacion()== null || !asignacionBD.getEvaluacion().getCodigo().equals(Constantes.LISTADO.RESULTADO_APROBACION.APROBADO)) {
			asignacion.setFlagActivo(Constantes.FLAG.ACTIVO);
			asignacion.setFecActualizacion(new Date());
			asignacion.setIpActualizacion(contexto.getIp());
			asignacion.setUsuActualizacion(contexto.getUsuario().getUsuario());
			asignacion.setEvaluacion(asignacionBD.getEvaluacion());
			asignacion.setFechaRegistro(asignacionBD.getFechaRegistro());
			AuditoriaUtil.setAuditoriaRegistro(asignacion,contexto);
			ListadoDetalle tipo= listadoDetalleService.obtener(asignacion.getTipo().getIdListadoDetalle(), contexto);
			asignacion.setTipo(tipo);    
			if(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO.equals(asignacion.getTipo().getCodigo())) {
					ListadoDetalle plazoConcluir=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.CONCLUIR_EVALUACION_TECNICA);
					asignacion.setNumeroPlazoResp(Long.parseLong(plazoConcluir.getValor()));
					asignacion.setFechaPlazoResp(calcularFechaFin(asignacion.getFechaRegistro(),asignacion.getNumeroPlazoResp()));        
					asignacion.setEvaluacion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.REGISTRADO));
			}
			asignacionBD=asignacionDao.save(asignacion);
				modify = true;
			}
			return modify;
			
	}

	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public Asignacion guardar(Asignacion asignacion, Contexto contexto) {
		Asignacion asignacionBD=asignacionDao.obtener(asignacion.getIdAsignacion());
		ListadoDetalle evaluacion= listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO,asignacion.getEvaluacion().getCodigo());
		asignacionBD.setEvaluacion(evaluacion);
		asignacionBD.setFechaAprobacion(new Date());
		asignacionBD.setObservacion(asignacion.getObservacion());
		AuditoriaUtil.setAuditoriaRegistro(asignacionBD, contexto);
		asignacionDao.save(asignacionBD);
		if(Constantes.LISTADO.RESULTADO_APROBACION.APROBADO.equals(evaluacion.getCodigo())) {
			if(asignacionBD.getUsuario().getIdUsuario()!=contexto.getUsuario().getIdUsuario()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ASIGNACION_NO_CORRESPONDE);
			}
			Usuario usuario = contexto.getUsuario();
			List<Asignacion> asignaciones=asignacionDao.obtenerAsignaciones(asignacionBD.getSolicitud().getIdSolicitud(), asignacionBD.getTipo().getIdListadoDetalle());
			Long grupoActual=asignacionBD.getGrupo().getOrden();
			boolean existePendienteEnElGrupo=false;
			for(Asignacion asignacionPendiente:asignaciones) {
				if(grupoActual==asignacionPendiente.getGrupo().getOrden()) {
					if(asignacionPendiente.getEvaluacion()==null||Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO.equals(asignacionPendiente.getEvaluacion().getCodigo())) {
						existePendienteEnElGrupo=true;
						break;
					}	
				}
			}
			 if(usuario.isRol(Constantes.ROLES.APROBADOR_ADMINISTRATIVO)){//AFC
					generarArchivoSubirAlfrescoAdministrativo(asignacionBD,contexto); 
				}
			
			if(existePendienteEnElGrupo) {
				Long maximoGrupo=asignacionDao.obtenerMaximoGrupo(asignacionBD.getSolicitud().getIdSolicitud(), asignacionBD.getTipo().getIdListadoDetalle());
				if(maximoGrupo==asignacionBD.getGrupo().getOrden()) {
					if(usuario.isRol(Constantes.ROLES.APROBADOR_TECNICO)) {
						generarArchivoSubirAlfresco(asignacionBD,contexto);		
					}
				}
			}else {
				boolean existeGrupoAsignado=actualizarAsignados(asignacionBD.getSolicitud().getIdSolicitud(),asignacionBD.getTipo().getCodigo(), grupoActual+1,contexto); 				
				if(existeGrupoAsignado) {
					//notificacionService.enviarMensajeAsignacionAprobacion14(asignacionBD, contexto);
				}
				if(!existeGrupoAsignado) {
					if(usuario.isRol(Constantes.ROLES.APROBADOR_TECNICO)) {
						generarArchivoSubirAlfresco(asignacionBD,contexto);
					}
					solicitudService.finalizarRevision(asignacionBD.getSolicitud().getIdSolicitud(),asignacionBD.getTipo().getCodigo(),contexto);
				}
			}
		}else if(Constantes.LISTADO.RESULTADO_APROBACION.RECHAZADO.equals(evaluacion.getCodigo())) {
			if(asignacionBD.getUsuario().getIdUsuario()!=contexto.getUsuario().getIdUsuario()) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ASIGNACION_NO_CORRESPONDE);
			}
			solicitudService.regresarProceso(asignacionBD.getSolicitud(), asignacionBD.getTipo().getCodigo(), contexto);
			List<Asignacion> asignaciones=asignacionDao.obtenerAsignaciones(asignacionBD.getSolicitud().getIdSolicitud(), asignacionBD.getTipo().getIdListadoDetalle());
			for(Asignacion asignacionAux:asignaciones) {
				if(asignacionAux.getEvaluacion()==null||Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO.equals(asignacionAux.getEvaluacion().getCodigo())) {
					ListadoDetalle estadoCancelado=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.CANCELADO);
					asignacionAux.setEvaluacion(estadoCancelado);
				}
				asignacionAux.setFlagActivo(Constantes.FLAG.INACTIVO);
				asignacionDao.save(asignacionAux);
			}
		}
		return asignacionBD;
	}
	
	public Asignacion rechazarPerfil(Asignacion asignacion) {
		return asignacionDao.save(asignacion);
	}
	//RECHAZO
	public void crearHistorialAsignacion(Long idAsignacionOriginal, String accion, String observacion, Contexto contexto) {
        Asignacion asignacionOriginal = asignacionDao.obtener(idAsignacionOriginal);
        
        if (asignacionOriginal == null) {
            throw new ValidacionException("Asignación original no encontrada");
        }

        // Crear copia para historial
        Asignacion historico = new Asignacion();
        
        // Copiar propiedades básicas
        historico.setSolicitud(asignacionOriginal.getSolicitud());
        historico.setTipo(asignacionOriginal.getTipo());
        historico.setUsuario(asignacionOriginal.getUsuario());
        historico.setGrupo(asignacionOriginal.getGrupo());
        historico.setFechaPlazoResp(asignacionOriginal.getFechaPlazoResp());
        historico.setNumeroPlazoResp(asignacionOriginal.getNumeroPlazoResp());
        historico.setFechaRegistro(new Date());

        // Configurar datos específicos del historial
        historico.setEvaluacion(null);
        historico.setObservacion(null);
        historico.setFechaAprobacion(null);
        historico.setFlagActivo(1L);
        
        // Auditoría
        AuditoriaUtil.setAuditoriaRegistro(historico, contexto);
        
        // Guardar registro histórico
        asignacionDao.save(historico);
    }
	
	//RECHAZO
	@Transactional(rollbackFor = Exception.class)
	public void rechazarPerfil(Long idAsignacionRechazo, Long idOtroRequisito, String observacion, Contexto contexto) {
	    try {
	        Asignacion asignacionRechazo = asignacionDao.obtener(idAsignacionRechazo);
	        if (asignacionRechazo == null) {
	            logger.error("Asignación no encontrada con ID: {}", idAsignacionRechazo);
	            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ASIGNACION_NO_CORRESPONDE);
	        }
	        if (!asignacionRechazo.getUsuario().getIdUsuario().equals(contexto.getUsuario().getIdUsuario())) {
	            logger.error("El usuario {} no tiene permisos sobre la asignación {}",
	                    contexto.getUsuario().getIdUsuario(), idAsignacionRechazo);
	            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ASIGNACION_NO_CORRESPONDE);
	        }
	
	        Long idGrupoRechazo = asignacionRechazo.getGrupo().getIdListadoDetalle();
	        Long idSolicitud = asignacionRechazo.getSolicitud().getIdSolicitud();
	
	        // Lógica para insertar nuevos registros G1 si el que rechaza es G2
	        if (idGrupoRechazo.equals(543L)) {
	            // Obtener todos los registros G1 APROBADOS para la misma solicitud
	            List<Asignacion> asignacionesG1Aprobadas = asignacionDao
	                    .obtenerAsignacionesPorGrupoYSolicitud(544L, 542L, idSolicitud, 560L);
	
	            // Insertar nuevos registros para cada G1 aprobado
	            for (Asignacion asignacionG1Original : asignacionesG1Aprobadas) {
	                Asignacion nuevaAsignacionG1 = new Asignacion();
	                nuevaAsignacionG1.setSolicitud(asignacionG1Original.getSolicitud());
	                nuevaAsignacionG1.setTipo(asignacionG1Original.getTipo());
	                nuevaAsignacionG1.setUsuario(asignacionG1Original.getUsuario());
	                nuevaAsignacionG1.setGrupo(listadoDetalleService.obtener(542L, contexto));
	                nuevaAsignacionG1.setFechaPlazoResp(asignacionG1Original.getFechaPlazoResp());
	                nuevaAsignacionG1.setNumeroPlazoResp(asignacionG1Original.getNumeroPlazoResp());
	                nuevaAsignacionG1.setFechaRegistro(new Date());
	                nuevaAsignacionG1.setEvaluacion(null); // Establecer estado inicial
	                nuevaAsignacionG1.setObservacion(null);
	                nuevaAsignacionG1.setFechaAprobacion(null);
	                nuevaAsignacionG1.setFlagActivo(1L);
	                AuditoriaUtil.setAuditoriaRegistro(nuevaAsignacionG1, contexto);
	                asignacionDao.save(nuevaAsignacionG1);
	            }
	        }
	
	        // Lógica principal de rechazo del perfil (la que ya tenías)
	        OtroRequisito perfil = otroRequisitoDao.obtenerPorIdYSolicitud(
	                idOtroRequisito,
	                asignacionRechazo.getSolicitud().getIdSolicitud());
	
	        if (perfil == null) {
	            logger.error("Perfil no encontrado con ID: {} para solicitud {}",
	                    idOtroRequisito, asignacionRechazo.getSolicitud().getIdSolicitud());
	            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ASIGNACION_NO_CORRESPONDE);
	        }
	
	        if (perfil.getEvaluacion() != null &&
	                Constantes.LISTADO.RESULTADO_APROBACION.RECHAZADO.equals(perfil.getEvaluacion().getCodigo())) {
	            logger.error("El perfil {} ya está en estado RECHAZADO", idOtroRequisito);
	            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ASIGNACION_NO_CORRESPONDE);
	        }
	
	        ListadoDetalle estadoEvaluacion = listadoDetalleService.obtenerListadoDetalle(
	                Constantes.LISTADO.RESULTADO_EVALUACION_TEC_ADM.CODIGO,
	                Constantes.LISTADO.RESULTADO_EVALUACION_TEC_ADM.ASIGNADO);
	        asignacionRechazo.getSolicitud().setEstadoEvaluacionTecnica(estadoEvaluacion);
	
	        ListadoDetalle estadoPorEvaluar = listadoDetalleService.obtenerListadoDetalle(
	                Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO,
	                Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR);
	        perfil.setEvaluacion(estadoPorEvaluar);
	
	        perfil.setFinalizado(null);
	        perfil.setFechaFinalizador(new Date());
	        AuditoriaUtil.setAuditoriaRegistro(perfil, contexto);
	
	        asignacionRechazo.setObservacion(observacion);
	
	        ListadoDetalle estadoRechazado = listadoDetalleService.obtenerListadoDetalle(
	                Constantes.LISTADO.RESULTADO_APROBACION.CODIGO,
	                Constantes.LISTADO.RESULTADO_APROBACION.RECHAZADO);
	        asignacionRechazo.setEvaluacion(estadoRechazado);
	
	        Asignacion asignacionBD = asignacionDao.obtener(asignacionRechazo.getIdAsignacion());
	        List<Asignacion> asignaciones = asignacionDao.obtenerAsignaciones(
	                asignacionBD.getSolicitud().getIdSolicitud(),
	                asignacionBD.getTipo().getIdListadoDetalle());
	
	        ListadoDetalle estadoAsignado = listadoDetalleService.obtenerListadoDetalle(
	                Constantes.LISTADO.RESULTADO_APROBACION.CODIGO,
	                Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO);
	
	        int contadorActualizaciones = 0;
	        for (Asignacion asignacionAux : asignaciones) {
	            if (asignacionAux.getEvaluacion() != null && estadoAsignado != null) {
	                if (estadoAsignado.getIdListadoDetalle().equals(asignacionAux.getEvaluacion().getIdListadoDetalle())) {
	                    asignacionAux.setEvaluacion(null);
	                    contadorActualizaciones++;
	                }
	            }
	            asignacionDao.save(asignacionAux);
	            logger.debug("Asignación ID {} guardada", asignacionAux.getIdAsignacion());
	        }
	
	        logger.info("Total de asignaciones actualizadas: {}", contadorActualizaciones);
	        asignacionRechazo.setFechaAprobacion(new Date());
	
	        otroRequisitoDao.save(perfil);
	        asignacionDao.save(asignacionRechazo);
	        
	        notificacionService.enviarMensajeAsignacionEvaluacion04(idOtroRequisito, contexto);
	
	        logger.info("Rechazo de perfil completado exitosamente");
	
	    } catch (ValidacionException e) {
	        logger.error("Error de validación al rechazar perfil: {}", e.getMessage());
	        throw e;
	    } catch (Exception e) {
	        logger.error("Error inesperado al rechazar perfil", e);
	        throw new ValidacionException("Error interno al procesar el rechazo del perfil");
	    }
	}
	
	@Override
    public List<Integer> obtenerIdsPerfilesAsignadosAprobador(Long idAprobador) {
        return perfilAprobadorDao.obtenerIdsPerfilesAsignadosAprobador(idAprobador);
    }

	@Override
	public List<Asignacion> obtenerEvaluadoresAdministrativos(Long idSolicitud) {
		return asignacionDao.obtenerAsignacionesEvalAdm(idSolicitud);
	}

	public void generarArchivoSubirAlfresco(Asignacion asignacionBD,Contexto contexto) {
			Archivo informeVT=null;
			try {
				informeVT = archivoService.obtenerTipoArchivo(asignacionBD.getSolicitud().getIdSolicitud(),
						Constantes.LISTADO.TIPO_ARCHIVO.INFORME_TECNICO,asignacionBD.getIdAsignacion());
				if(informeVT == null) {
					informeVT=solicitudService.generarInformeTecnico(asignacionBD.getSolicitud().getIdSolicitud(),asignacionBD.getIdAsignacion(),contexto);
					informeVT.setFlagSiged(0L);
					archivoService.guardar(informeVT, contexto);
				}
				solicitudService.subirDocumentoTecnicos(informeVT,contexto);
			} catch (Exception e) {
				logger.error("ERROR S-----", e.getMessage(), e);
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_TECNICO);
			}	
			
	}

	//AFC
	public void generarArchivoSubirAlfrescoAdministrativo(Asignacion asignacionBD,Contexto contexto) {
		Archivo informeVT=null;
		try {
			informeVT = archivoService.obtenerTipoArchivo(asignacionBD.getSolicitud().getIdSolicitud(),
					Constantes.LISTADO.TIPO_ARCHIVO.INFORME_ADMINISTRATIVO,asignacionBD.getIdAsignacion());//cambiar
			if(informeVT == null) {
				informeVT=solicitudService.generarInformeAdministrativo(asignacionBD.getSolicitud().getIdSolicitud(),asignacionBD.getIdAsignacion(),contexto);
				informeVT.setFlagSiged(0L);
				archivoService.guardar(informeVT, contexto);
			}
			solicitudService.subirDocumentoAdministrativos(informeVT,contexto);
		} catch (Exception e) {
			logger.error("ERROR S-----", e.getMessage(), e);
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_ADMINISTRATIVO);//cambiar
		}	
		
	}
	//AFC
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public boolean actualizarAsignados(Long idSolicitud,String codigoTipoAprobador, Long numeroOrden,Contexto contexto) {
		boolean asignado=false;
		List<Asignacion> asignaciong1 = asignacionDao.listarAsiganacionesPendientesG1(idSolicitud);
		ListadoDetalle tipo=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_EVALUADOR.CODIGO, codigoTipoAprobador);
		List<Asignacion> asignaciones=asignacionDao.obtenerAsignaciones( idSolicitud,tipo.getIdListadoDetalle());
		for(Asignacion asignacion:asignaciones) {
			if(asignacion.getGrupo().getOrden()==numeroOrden) {
				ListadoDetalle evaluacion=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO);
				ListadoDetalle evaluacionActual = asignacion.getEvaluacion();
				if(evaluacionActual == null ) {
				    asignacion.setEvaluacion(evaluacion);
					AuditoriaUtil.setAuditoriaRegistro(asignacion,contexto);
					asignacionDao.save(asignacion);
					asignado=true;
					notificacionService.enviarMensajeAsignacionAprobacion14(asignacion, contexto);
				}
			}
			if (asignacion.getGrupo().getOrden()==1L && asignaciong1.size()>=2 && !asignacion.getEvaluacion().getCodigo().equals(Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO)){
				ListadoDetalle evaluacion=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.APROBADO);
				if(asignacion.getEvaluacion().getIdListadoDetalle() != 561 ) {
					asignacion.setEvaluacion(evaluacion);
					AuditoriaUtil.setAuditoriaRegistro(asignacion,contexto);
					asignacionDao.save(asignacion);
					//asignado=true;
					//notificacionService.enviarMensajeAsignacionAprobacion14(asignacion, contexto);
				}

			}
		}
		/* --- 04-01-2024 --- INI ---\*/
		if(asignaciong1.isEmpty()) {
			for(Asignacion asignacion:asignaciones) {
				if(asignacion.getGrupo().getOrden()==2L && Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO.equals(asignacion.getTipo().getCodigo()) && !numeroOrden.equals(3L)) {
					ListadoDetalle evaluacion=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO);
					asignacion.setEvaluacion(evaluacion);
					AuditoriaUtil.setAuditoriaRegistro(asignacion,contexto);
					asignacionDao.save(asignacion);
				notificacionService.enviarMensajeAsignacionAprobacion14(asignacion, contexto);
			}
			}
		/* --- 04-01-2024 --- FIN ---\*/	
		}
		return asignado;
	}

	@Override
	public Page<Asignacion> buscarAprobaciones(String solicitudUuid, Pageable pageable, Contexto contexto) {
		Page<Asignacion> asignaciones =asignacionDao.buscar(solicitudUuid, pageable);
		return asignaciones;
	}

	@Override
	public void eliminarXIdSolicitud(Long idSolicitud, Contexto contexto) {
		asignacionDao.eliminarXIdSolicitud(idSolicitud);		
	}

	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public void eliminarAprobador(Long id, Contexto contexto) {
		Asignacion asignacion=asignacionDao.obtener(id);
		eliminar(id, contexto);
		Pageable pageable = PageRequest.of(0, Integer.parseInt(env.getProperty("maximo.paginas")));
		Page<Asignacion> page=asignacionDao.buscar(asignacion.getSolicitud().getIdSolicitud(),Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO,pageable);
		validar( page.getContent(),false,  contexto);
	}

	@Override
	public List<Asignacion> obtener(Long idSolicitud, Long idUsuario) {
		return asignacionDao.obtener(idSolicitud,idUsuario);
	}

	@Override
	public List<Asignacion> buscarAprobadores(Long idSolicitud,Long idUsuario) {
		return asignacionDao.listarAprobadoresTecnicos(idSolicitud,idUsuario);
	}
	//AFC
	@Override
	public List<Asignacion> buscarAprobadoresAdministrativos(Long idSolicitud,Long idUsuario) {
		return asignacionDao.listarAprobadoresAdminstrativos(idSolicitud,idUsuario);
	}
	
	@Override
	public List<Asignacion> buscarAprobadoresAdministrativosPJ(Long idSolicitud,Long idUsuario) {
		return asignacionDao.listarAprobadoresAdministrativosPJ(idSolicitud,idUsuario);
	}
	
	//AFC
	@Override
	public List<Asignacion> buscarAprobadoresPJ(Long idSolicitud,Long idUsuario) {
		return asignacionDao.listarAprobadoresTecnicosPJ(idSolicitud,idUsuario);
	}
	
	public void listarOtroRequisitoPerfilesPendientesDeEvaluacion(Contexto contexto) {
		Asignacion asignacionBD = null;
		List<OtroRequisito> listaOtroRequisitos = otroRequisitoService.listarOtroRequisitoPerfilesPendientesDeEvaluacion();
		
		for (OtroRequisito otroRequisito : listaOtroRequisitos) {
			asignacionBD = asignacionDao.obtenerAsignacionPorIdOtroRequisito(otroRequisito.getIdOtroRequisito());
			if (asignacionBD != null && asignacionBD.getGrupo() == null && asignacionBD.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO)
					&& asignacionBD.getEvaluacion().getCodigo().equals(Constantes.LISTADO.RESULTADO_APROBACION.REGISTRADO)) {
				Date hoy = new Date();
				DateFormat df = new SimpleDateFormat("dd/MM//yyyy");
				ListadoDetalle plazoAsignar5 = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.EVALUACION_5_DIAS);
				Date fechaFin5 = calcularFechaFin(asignacionBD.getFechaRegistro(), Long.parseLong(plazoAsignar5.getValor()));
				
				if (df.format(hoy).equals(df.format(fechaFin5))) {
					notificacionService.enviarMensajeAsignacionEvaluacion03(asignacionBD, contexto);
				}
				
				ListadoDetalle plazoAsignar1 = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.EVALUACION_1_DIA);
				Date fechaFin1 = calcularFechaFin(asignacionBD.getFechaRegistro(), Long.parseLong(plazoAsignar1.getValor()));
				
				if (df.format(hoy).equals(df.format(fechaFin1))) {
					notificacionService.enviarMensajeAsignacionEvaluacion03(asignacionBD, contexto);
				}
			}
		}
	}
	
	public void listarOtroRequisitoPerfilesPendientesDeAprobacion(Contexto contexto) {
		List<Asignacion> listaAsignaciones = asignacionDao.listarAsiganacionesPendintesAproobacion();
		
		if (listaAsignaciones != null) {
			for (Asignacion asignacion : listaAsignaciones) {
				Date hoy = new Date();
				DateFormat df = new SimpleDateFormat("dd/MM//yyyy");
				ListadoDetalle plazoAsignar = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.EVALUACION_9_DIAS);
				Date fechaFin = calcularFechaFin(asignacion.getFechaRegistro(), Long.parseLong(plazoAsignar.getValor()));
				
				if (df.format(hoy).equals(df.format(fechaFin))) {
					notificacionService.enviarMensajeAsignacionAprobacion14(asignacion, contexto);
				}
			}
		}
	}

	@Override
	/* --- 04-01-2024 --- INI ---*/
	public Asignacion clonarAsignacion(Solicitud solicitudBD, Solicitud soliNueva ,Contexto contexto) {
		boolean evalAdministrativo=false;
		boolean otroRequisitoObservado = false;
		 
		List<OtroRequisito> listarOtroRequisitosPerfilObservado = otroRequisitoService.listarOtroRequisitosPerfilObservado(solicitudBD.getIdSolicitud());

		List<Asignacion> listaAsiganaciones = asignacionDao.obtenerAsignaciones(solicitudBD.getIdSolicitud());
		evalAdministrativo = Constantes.LISTADO.RESULTADO_EVALUACION_ADM.OBSERVADO.equals(solicitudBD.getResultadoAdministrativo().getCodigo());
		otroRequisitoObservado = Constantes.LISTADO.ESTADO_REVISION.OBSERVADO.equals(solicitudBD.getEstadoRevision().getCodigo());
		
		for(Asignacion asignacion:listaAsiganaciones) {
				Asignacion asignacionNuevo = new Asignacion();
			if (evalAdministrativo  && listarOtroRequisitosPerfilObservado.isEmpty()) {
				ListadoDetalle evaluacion=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO);
				asignacionNuevo.setFecCreacion(new Date());
				asignacionNuevo.setTipo(asignacion.getTipo());
				asignacionNuevo.setUsuario(asignacion.getUsuario());
				asignacionNuevo.setUsuActualizacion(asignacion.getUsuActualizacion());
				asignacionNuevo.setGrupo(asignacion.getGrupo());
				asignacionNuevo.setIpCreacion(asignacion.getIpActualizacion());
				asignacionNuevo.setSolicitud(soliNueva);
				asignacionNuevo.setFlagActivo(Constantes.FLAG.ACTIVO);
				asignacionNuevo.setFechaAprobacion(new Date());
				

				ListadoDetalle tipo= listadoDetalleService.obtener(asignacion.getTipo().getIdListadoDetalle(), contexto);
				asignacionNuevo.setTipo(tipo);	
				
				if(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO.equals(asignacion.getTipo().getCodigo())) {
					ListadoDetalle plazoConcluir=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.PLAZOS.CODIGO, Constantes.LISTADO.PLAZOS.CONCLUIR_EVALUACION_TECNICA);
					asignacionNuevo.setNumeroPlazoResp(Long.parseLong(plazoConcluir.getValor()));
					asignacionNuevo.setFechaPlazoResp(calcularFechaFin(asignacion.getFechaRegistro(),asignacion.getNumeroPlazoResp()));		
					asignacionNuevo.setEvaluacion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.REGISTRADO));
				}
				
				if (asignacion.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO)|| asignacion.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO)) {
					ListadoDetalle evaluacionNueva= listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.APROBADO);
					asignacionNuevo.setFechaRegistro(new Date());
					asignacionNuevo.setEvaluacion(evaluacionNueva);
				}else {
					asignacionNuevo.setEvaluacion(evaluacion);

				}
				if(Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO.equals(asignacion.getTipo().getCodigo())) {
					ListadoDetalle evaluacionNueva= listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.ASIGNADO);
					asignacionNuevo.setEvaluacion(evaluacionNueva);
				}
				AuditoriaUtil.setAuditoriaRegistro(asignacionNuevo,contexto);
				
				if(!Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO.equals(asignacion.getTipo().getCodigo())) {
				asignacionDao.save(asignacionNuevo);
				notificacionService.enviarMensajeAsignacionAprobacion14(asignacionNuevo, contexto);
				}
				
				
				
			}else if (!listarOtroRequisitosPerfilObservado.isEmpty() && !evalAdministrativo){
				
				ListadoDetalle evaluacion=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO);
				asignacionNuevo.setFecCreacion(new Date());
				asignacionNuevo.setTipo(asignacion.getTipo());
				asignacionNuevo.setUsuario(asignacion.getUsuario());
				asignacionNuevo.setUsuActualizacion(asignacion.getUsuActualizacion());
				asignacionNuevo.setGrupo(asignacion.getGrupo());
				asignacionNuevo.setIpCreacion(asignacion.getIpActualizacion());
				asignacionNuevo.setSolicitud(soliNueva);
				asignacionNuevo.setFlagActivo(Constantes.FLAG.ACTIVO);
				asignacionNuevo.setFechaAprobacion(new Date());

				ListadoDetalle tipo= listadoDetalleService.obtener(asignacion.getTipo().getIdListadoDetalle(), contexto);
				asignacionNuevo.setTipo(tipo);	
				
				if(Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO.equals(asignacion.getTipo().getCodigo())) {
					asignacionNuevo.setFechaRegistro(new Date());
					asignacionNuevo.setEvaluacion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.REGISTRADO));
				}
				
				if (asignacion.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO)|| asignacion.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO)) {
					ListadoDetalle evaluacionNueva= listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.APROBADO);
					asignacionNuevo.setFechaRegistro(new Date());
					asignacionNuevo.setEvaluacion(evaluacionNueva);
				}else {
					asignacionNuevo.setEvaluacion(evaluacion);

				}
				if(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO.equals(asignacion.getTipo().getCodigo())) {
					ListadoDetalle evaluacionNueva= listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.ASIGNADO);
					asignacionNuevo.setEvaluacion(evaluacionNueva);
				}
								
				AuditoriaUtil.setAuditoriaRegistro(asignacionNuevo,contexto);
				
				if(!Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO.equals(asignacion.getTipo().getCodigo()) && !Constantes.LISTADO.TIPO_EVALUADOR.TECNICO.equals(asignacion.getTipo().getCodigo())) {
				asignacionDao.save(asignacionNuevo);
				notificacionService.enviarMensajeAsignacionAprobacion14(asignacionNuevo, contexto);

				}
				
			}else if (!listarOtroRequisitosPerfilObservado.isEmpty() && evalAdministrativo){
				
				ListadoDetalle evaluacion=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO);
				asignacionNuevo.setFecCreacion(new Date());
				asignacionNuevo.setTipo(asignacion.getTipo());
				asignacionNuevo.setUsuario(asignacion.getUsuario());
				asignacionNuevo.setUsuActualizacion(asignacion.getUsuActualizacion());
				asignacionNuevo.setGrupo(asignacion.getGrupo());
				asignacionNuevo.setIpCreacion(asignacion.getIpActualizacion());
				asignacionNuevo.setSolicitud(soliNueva);
				asignacionNuevo.setFlagActivo(Constantes.FLAG.ACTIVO);
				asignacionNuevo.setFechaAprobacion(new Date());

				ListadoDetalle tipo= listadoDetalleService.obtener(asignacion.getTipo().getIdListadoDetalle(), contexto);
				asignacionNuevo.setTipo(tipo);	
				
				if(Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO.equals(asignacion.getTipo().getCodigo())) {
					ListadoDetalle evaluacionNueva= listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.ASIGNADO);
					asignacionNuevo.setFechaRegistro(new Date());
					asignacionNuevo.setEvaluacion(evaluacionNueva);
				}
				AuditoriaUtil.setAuditoriaRegistro(asignacionNuevo,contexto);
				
				if(!Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO.equals(asignacion.getTipo().getCodigo()) && !Constantes.LISTADO.TIPO_EVALUADOR.TECNICO.equals(asignacion.getTipo().getCodigo()) && !Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO.equals(asignacion.getTipo().getCodigo())) {
				asignacionDao.save(asignacionNuevo);
				notificacionService.enviarMensajeAsignacionAprobacion14(asignacionNuevo, contexto);
				}
			}
		}
		return null;
	}
	/* --- 04-01-2024 --- FIN ---*/
	
	public List<EvaluacionPendienteDTO> listarPerfilesPendientesDeEvaluacion() {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Asignacion asignacionBD = null;
		String numeroExpediente = "";
		List<EvaluacionPendienteDTO> listaEvaluacionesPendientes = new ArrayList<EvaluacionPendienteDTO>();
		List<OtroRequisito> listaOtroRequisitos = otroRequisitoService.listarPerfilesPendientesDeEvaluacion();
		
		//Agrupar por el número de días por vencer
		for (OtroRequisito otroRequisito : listaOtroRequisitos) {
			
			try {
				EvaluacionPendienteDTO evaluacion = new EvaluacionPendienteDTO();
				numeroExpediente = "";
				
				//Número de expediente
				numeroExpediente = solicitudDao.obtener(otroRequisito.getSolicitud().getIdSolicitud()).getNumeroExpediente();
				
				if (numeroExpediente == null || numeroExpediente.isEmpty()) continue;
				
				asignacionBD = asignacionDao.obtenerAsignacionPorIdOtroRequisito(otroRequisito.getIdOtroRequisito());
				
				if (asignacionBD != null && asignacionBD.getGrupo() == null &&
					(asignacionBD.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.TECNICO) ||
					asignacionBD.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO)) &&
					asignacionBD.getEvaluacion().getCodigo().equals(Constantes.LISTADO.RESULTADO_APROBACION.REGISTRADO)) {
					
					Date hoy = new Date();
					
					evaluacion = evaluacionPendienteDao.listarEvaluacionesPendientes(otroRequisito.getIdOtroRequisito());
					
					//Número de expediente
					evaluacion.setNumeroExpediente(numeroExpediente);
					
					//Evaluación a no considerar
					Date fechaFin = calcularFechaFin(evaluacion.getFechaIngreso(), 7L);
					
					if (df.format(hoy).equals(df.format(fechaFin)) || hoy.before(fechaFin)) {
						continue;
					}
					
					//Evaluación vencida
					fechaFin = calcularFechaFin(evaluacion.getFechaIngreso(), 11L);
					
					if (df.format(hoy).equals(df.format(fechaFin)) || hoy.after(fechaFin)) {
						evaluacion.setDiasPendientes(0L);
						listaEvaluacionesPendientes.add(evaluacion);
						continue;
					}
					
					//Evaluación con 3 días por vencer
					fechaFin = calcularFechaFin(evaluacion.getFechaIngreso(), 8L);

					if (df.format(hoy).equals(df.format(fechaFin))) {
						evaluacion.setDiasPendientes(3L);
						listaEvaluacionesPendientes.add(evaluacion);
						continue;
					}
					
					//Evaluación con 2 días por vencer
					fechaFin = calcularFechaFin(evaluacion.getFechaIngreso(), 9L);

					if (df.format(hoy).equals(df.format(fechaFin))) {
						evaluacion.setDiasPendientes(2L);
						listaEvaluacionesPendientes.add(evaluacion);
						continue;
					}
					
					//Evaluación con 1 día por vencer
					fechaFin = calcularFechaFin(evaluacion.getFechaIngreso(), 10L);

					if (df.format(hoy).equals(df.format(fechaFin))) {
						evaluacion.setDiasPendientes(1L);
						listaEvaluacionesPendientes.add(evaluacion);
						continue;
					}
				}
			}
			catch (Exception e) {
				logger.info("Error al calcular la fecha fin de atención de la solicitud " + numeroExpediente);
				continue;
			}
		}
		
		//Ordenar por id de usuario
		listaEvaluacionesPendientes.sort(Comparator.comparing(EvaluacionPendienteDTO::getIdUsuario));
		
		return listaEvaluacionesPendientes;
	}
		
		
	public void notificarEvaluacionesPendientes(List<EvaluacionPendienteDTO> listaEvaluacionesPendientes, Contexto contexto) { 
		
		List<EvaluacionPendienteDTO> listaEvaluacionesPendientePorUsuario = new ArrayList<EvaluacionPendienteDTO>();
		String correoUsuario, tipo;
		
		Long idUsuarioPorNotificar = listaEvaluacionesPendientes.get(0).getIdUsuario();
		correoUsuario = listaEvaluacionesPendientes.get(0).getCorreoUsuario();
		tipo = listaEvaluacionesPendientes.get(0).getTipo();
		
		for (EvaluacionPendienteDTO evaluacion : listaEvaluacionesPendientes) {
			
			if (!idUsuarioPorNotificar.equals(evaluacion.getIdUsuario())) { //Inicio de otro usuario

				enviarMensajeEvaluacionPendiente(listaEvaluacionesPendientePorUsuario, correoUsuario, tipo, contexto);
				
				//Inicio del siguiente usuario
				listaEvaluacionesPendientePorUsuario = new ArrayList<EvaluacionPendienteDTO>();
				
				idUsuarioPorNotificar = evaluacion.getIdUsuario();
				correoUsuario = evaluacion.getCorreoUsuario();
				tipo = evaluacion.getTipo();
				listaEvaluacionesPendientePorUsuario.add(evaluacion);
			}
			else {//Evaluacion por notificar del usuario
				listaEvaluacionesPendientePorUsuario.add(evaluacion);
			}
		}

		enviarMensajeEvaluacionPendiente(listaEvaluacionesPendientePorUsuario, correoUsuario, tipo, contexto);
	}
	
	public void enviarMensajeEvaluacionPendiente(List<EvaluacionPendienteDTO> evaluaciones, String correoUsuario, String tipo, Contexto contexto) {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");		
		StringBuilder texto_html = new StringBuilder();
		List<EvaluacionPendienteDTO> evaluacionesANotificar = new ArrayList<EvaluacionPendienteDTO>();
		String numeroExpedienteActual = "";
		
		evaluaciones.sort(Comparator.comparing(EvaluacionPendienteDTO::getNumeroExpediente));
		evaluaciones.sort(Comparator.comparing(EvaluacionPendienteDTO::getDiasPendientes));
		
		for (EvaluacionPendienteDTO evaluacion : evaluaciones) {
			
			if (!numeroExpedienteActual.equals(evaluacion.getNumeroExpediente())) {
				numeroExpedienteActual = evaluacion.getNumeroExpediente();
				evaluacionesANotificar.add(evaluacion);
			}
		}
		
		for (EvaluacionPendienteDTO evaluacion : evaluacionesANotificar) {
			if (evaluacion.getDiasPendientes() == 0)
				texto_html
				.append("<tr >")
				.append("<td style=\"text-align: center; color: #FF0000\">").append(evaluacion.getFechaIngreso() != null ? df.format(evaluacion.getFechaIngreso()) : "").append("</td>")
				.append("<td style=\"text-align: center; color: #FF0000\">").append(evaluacion.getNumeroExpediente()).append("</td>")
				.append("<td style=\"text-align: center; color: #FF0000\">").append(evaluacion.getPerfil()).append("</td>")
				.append("<td style=\"text-align: center; color: #FF0000\">").append(evaluacion.getFechaAsignacion() != null ? df.format(evaluacion.getFechaAsignacion()) : "").append("</td>")
				.append("<td style=\"text-align: center; color: #FF0000\">").append(evaluacion.getDivisionGerencia()).append("</td>")
				.append("<td style=\"text-align: center; color: #FF0000\">").append(evaluacion.getDiasPendientes()).append("</td>")
				.append("</tr>");
			else if (evaluacion.getDiasPendientes() == 1)
				texto_html
				.append("<tr >")
				.append("<td style=\"text-align: center; color: #FFA600\">").append(evaluacion.getFechaIngreso() != null ? df.format(evaluacion.getFechaIngreso()) : "").append("</td>")
				.append("<td style=\"text-align: center; color: #FFA600\">").append(evaluacion.getNumeroExpediente()).append("</td>")
				.append("<td style=\"text-align: center; color: #FFA600\">").append(evaluacion.getPerfil()).append("</td>")
				.append("<td style=\"text-align: center; color: #FFA600\">").append(evaluacion.getFechaAsignacion() != null ? df.format(evaluacion.getFechaAsignacion()) : "").append("</td>")
				.append("<td style=\"text-align: center; color: #FFA600\">").append(evaluacion.getDivisionGerencia()).append("</td>")
				.append("<td style=\"text-align: center; color: #FFA600\">").append(evaluacion.getDiasPendientes()).append("</td>")
				.append("</tr>");
			else if (evaluacion.getDiasPendientes() == 2)
				texto_html
				.append("<tr >")
				.append("<td style=\"text-align: center; color: #00AF55\">").append(evaluacion.getFechaIngreso() != null ? df.format(evaluacion.getFechaIngreso()) : "").append("</td>")
				.append("<td style=\"text-align: center; color: #00AF55\">").append(evaluacion.getNumeroExpediente()).append("</td>")
				.append("<td style=\"text-align: center; color: #00AF55\">").append(evaluacion.getPerfil()).append("</td>")
				.append("<td style=\"text-align: center; color: #00AF55\">").append(evaluacion.getFechaAsignacion() != null ? df.format(evaluacion.getFechaAsignacion()) : "").append("</td>")
				.append("<td style=\"text-align: center; color: #00AF55\">").append(evaluacion.getDivisionGerencia()).append("</td>")
				.append("<td style=\"text-align: center; color: #00AF55\">").append(evaluacion.getDiasPendientes()).append("</td>")
				.append("</tr>");
			else if (evaluacion.getDiasPendientes() == 3)
				texto_html
				.append("<tr >")
				.append("<td style=\"text-align: center; color: #000FFF\">").append(evaluacion.getFechaIngreso() != null ? df.format(evaluacion.getFechaIngreso()) : "").append("</td>")
				.append("<td style=\"text-align: center; color: #000FFF\">").append(evaluacion.getNumeroExpediente()).append("</td>")
				.append("<td style=\"text-align: center; color: #000FFF\">").append(evaluacion.getPerfil()).append("</td>")
				.append("<td style=\"text-align: center; color: #000FFF\">").append(evaluacion.getFechaAsignacion() != null ? df.format(evaluacion.getFechaAsignacion()) : "").append("</td>")
				.append("<td style=\"text-align: center; color: #000FFF\">").append(evaluacion.getDivisionGerencia()).append("</td>")
				.append("<td style=\"text-align: center; color: #000FFF\">").append(evaluacion.getDiasPendientes()).append("</td>")
				.append("</tr>");
		}

		if (correoUsuario != null) {
			notificacionService.enviarMensajeEvaluacionPendiente("SICOES - Reporte de solicitudes pendientes de evaluación",
					correoUsuario,
					tipo,
					texto_html.toString(),
					contexto);
		}
	}
	
  	@Transactional(rollbackFor = Exception.class)
	public void actualizarEvaluacionPendientePorVacaciones(Contexto contexto) throws Exception {
		
		logger.info("actualizarEvaluacionPendientePorVacaciones Inicio");
		
		//********************** Historial de vacaciones **********************************
		List<DetalleVacacionesDTO> listaVacaciones = new ArrayList<DetalleVacacionesDTO>();
		
		//Consumir el servicio de vacaciones
		listaVacaciones = obtenerListadoVacaciones();
		
		for (DetalleVacacionesDTO vacaciones : listaVacaciones) {
			
			logger.info(vacaciones);
			
			if (vacaciones.getUsuarioSaliente() == null || vacaciones.getUsuarioSaliente().isEmpty() ||
				vacaciones.getFechaInicioAusencia() == null || vacaciones.getFechaFinAusencia() == null ||
				vacaciones.getUsuarioReemplazo() == null || vacaciones.getUsuarioReemplazo().isEmpty() ||
				vacaciones.getFechaInicioReemplazo() == null  || vacaciones.getFechaFinReemplazo() == null ||
				vacaciones.getFechaConsulta() == null || vacaciones.getCodigoConsulta() == null) continue;
				
			//Verificar existencia de registro de vacaciones activo (FL_ACTIVO = 1)
			HistorialVacaciones historialBD = new HistorialVacaciones();
			historialBD = historialVacacionesDao.obtenerRegistroVacacionesUsuario(vacaciones.getUsuarioSaliente());

			//No existe registro de vacaciones
			if (historialBD == null) {
				
				//Obtener el id del usuario saliente
				Usuario usuarioSaliente = usuarioDao.obtener(vacaciones.getUsuarioSaliente());
				
				//Obtener el id del usuario reemplazo
				Usuario usuarioReemplazo = usuarioDao.obtener(vacaciones.getUsuarioReemplazo());
				
				if (usuarioSaliente == null || usuarioReemplazo == null) {
					logger.info("El usuario " + vacaciones.getUsuarioSaliente() + " o " + vacaciones.getUsuarioReemplazo() + " no esta registrado.");
					continue;
				}
				
				Boolean checkRegistro = registrarVacaciones(vacaciones, contexto);
				
				if (checkRegistro)
              	{
              		actualizarPerfilAprobadorG2XVacaciones(usuarioSaliente.getIdUsuario(), usuarioReemplazo.getIdUsuario(), contexto);				
              		actualizarAsignacionXVacaciones(usuarioSaliente.getIdUsuario(), usuarioReemplazo.getIdUsuario(), contexto);
              	}
			}
		}
		
		retornarAsignacionXFinalVacaciones(listaVacaciones, contexto);
		
		logger.info("actualizarEvaluacionPendientePorVacaciones Fin");
	}
	
	
	public List<DetalleVacacionesDTO> obtenerListadoVacaciones() throws Exception {
		
		logger.info("obtenerListadoVacaciones Inicio");
		
		List<DetalleVacacionesDTO> listaVacaciones = new ArrayList<DetalleVacacionesDTO>();
		
        try {
			listaVacaciones = sneApiConsumer.obtenerListadoVacaciones();
        }
        catch (Exception e) {
			logger.info("obtenerListadoVacaciones: No se obtuvo el listado de vacaciones");
		}
		
		logger.info("obtenerListadoVacaciones Fin");
		
		return listaVacaciones;
	}
	
	public Boolean registrarVacaciones(DetalleVacacionesDTO vacaciones, Contexto contexto) throws ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Boolean checkRegistro = false; 

		//Obtener el id del usuario saliente
		Usuario usuarioSaliente = usuarioDao.obtener(vacaciones.getUsuarioSaliente());
		
		//Obtener el id del usuario reemplazo
		Usuario usuarioReemplazo = usuarioDao.obtener(vacaciones.getUsuarioReemplazo());
		
		//G1(542) o G2(543)
		List<PerfilAprobador> perfilAprobadorReemplazo = new ArrayList<PerfilAprobador>();
		perfilAprobadorReemplazo = perfilAprobadorDao.obtenerPerfilAprobadorPorIdAprobadorG2(usuarioReemplazo.getIdUsuario());
		
		if (perfilAprobadorReemplazo != null) {
			
			//Division del usuario saliente
			List<PerfilAprobador> perfilAprobadorSaliente = new ArrayList<PerfilAprobador>();
			perfilAprobadorSaliente = perfilAprobadorDao.obtenerPerfilAprobadorPorIdAprobadorG2(usuarioSaliente.getIdUsuario());
			
			if (perfilAprobadorSaliente != null) {
				
				PerfilDivision perfilDivision = perfilAprobador.obtenerPerfilDivisionPorIdPerfil(perfilAprobadorSaliente.get(0).getPerfil().getIdListadoDetalle());
				
				//Ingresar el nuevo registro de vacaciones
				HistorialVacaciones historial = new HistorialVacaciones();
				
				historial.setId(vacaciones.getId());
				historial.setDniEmpleado(vacaciones.getDniEmpleado());
				historial.setUsuarioSaliente(vacaciones.getUsuarioSaliente());
				historial.setApellidos(vacaciones.getApellidos());
				historial.setNombres(vacaciones.getNombres());
				historial.setFechaInicioAusencia(formatter.parse(vacaciones.getFechaInicioAusencia()));
				historial.setFechaFinAusencia(formatter.parse(vacaciones.getFechaFinAusencia()));
				historial.setMotivoAusencia(vacaciones.getMotivoAusencia());
				historial.setCodigoUsuarioReemplazo(vacaciones.getCodigoUsuarioReemplazo());
				historial.setFechaInicioReemplazo(formatter.parse(vacaciones.getFechaInicioReemplazo()));
				historial.setFechaFinReemplazo(formatter.parse(vacaciones.getFechaFinReemplazo()));
				historial.setDocumentoReemplazo(vacaciones.getDocumentoReemplazo());
				historial.setUsuarioReemplazo(vacaciones.getUsuarioReemplazo());
				historial.setApellidosReemplazo(vacaciones.getApellidosReemplazo());
				historial.setNombresReemplazo(vacaciones.getNombresReemplazo());
				historial.setNombreCompletoReemplazo(vacaciones.getNombreCompletoReemplazo());
				historial.setFechaConsulta(formatter.parse(vacaciones.getFechaConsulta()));
				historial.setCodigoConsulta(vacaciones.getCodigoConsulta());
				historial.setIdGrupoAprobadorReemplazo(perfilAprobadorReemplazo != null ? 543L : 542L);
				historial.setIdDivisionAprobadorSaliente(perfilDivision.getDivision().getIdDivision());
				historial.setFlagActivo(1L);
				historial.setUsuCreacion(contexto.getUsuarioApp());
				historial.setIpCreacion(contexto.getIp());
				historial.setFecCreacion(new Date());
				
				historial = historialVacacionesDao.save(historial);
				
				checkRegistro = true;
			}
		}
		
		return checkRegistro;
	}
	
	public void actualizarPerfilAprobadorG2XVacaciones(Long idUsuarioSaliente, Long idUsuarioReemplazo, Contexto contexto) {
	
		//Obtener perfiles asignados del usuario saliente (G2)
		List<PerfilAprobador> perfilAprobadorG2 = perfilAprobadorDao.obtenerPerfilAprobadorPorIdAprobadorG2(idUsuarioSaliente);
		
		for (PerfilAprobador perfilAprobador : perfilAprobadorG2) {
			
			//Guardar copia del perfil aprobador a modificar
			HistorialAprobador aprobador = new HistorialAprobador();
			aprobador.setIdPerfil(perfilAprobador.getPerfil().getIdListadoDetalle());
			aprobador.setIdAprobadorG1(perfilAprobador.getAprobadorG1().getIdUsuario());
			aprobador.setIdAprobadorG2(perfilAprobador.getAprobadorG2().getIdUsuario());
			aprobador.setFlagActivo(1L);
			aprobador.setUsuCreacion(contexto.getUsuarioApp());
			aprobador.setIpCreacion(contexto.getIp());
			aprobador.setFecCreacion(new Date());
			
			historialAprobadorDao.save(aprobador);
			
			//Actualizar la tabla perfil aprobador
			AuditoriaUtil.setAuditoriaActualizacion(perfilAprobador, AuditoriaUtil.getContextoJob());
			perfilAprobadorDao.actualizarAprobadorG2(perfilAprobador.getIdPerfilAprobador(), idUsuarioReemplazo);				
		}
	}
	
	public void actualizarAsignacionXVacaciones(Long idUsuarioSaliente, Long idUsuarioReemplazo, Contexto contexto) {
		
		//Actualizar la tabla asignación
		List<Asignacion> listaAsignacion = asignacionDao.listarAsignacionesPendintesAprobacion(idUsuarioSaliente);
		
		for (Asignacion asignacion : listaAsignacion) {			
			
			//Actualizar la tabla de asignación
			AuditoriaUtil.setAuditoriaActualizacion(asignacion, AuditoriaUtil.getContextoJob());
			asignacionDao.actualizarUsuario(asignacion.getIdAsignacion(), idUsuarioReemplazo, idUsuarioSaliente);
		}
	}
	
	public void retornarAsignacionXFinalVacaciones(List<DetalleVacacionesDTO> listaVacaciones, Contexto contexto) {
		
		//Validar las vacaciones registradas en la base datos
		List<HistorialVacaciones> historialVacacionesBD = new ArrayList<HistorialVacaciones>();
		Boolean checkVacacionesActivas;
		
		//Obtener el historial de vacaciones activas
		historialVacacionesBD = historialVacacionesDao.obtenerHistorialVacacionesActivos();
		
		for (HistorialVacaciones vacacionesBD : historialVacacionesBD) {
			
			checkVacacionesActivas = false;
			
			for (DetalleVacacionesDTO vacaciones : listaVacaciones) {
				
				if (vacacionesBD.getUsuarioSaliente().equals(vacaciones.getUsuarioSaliente())) {
					checkVacacionesActivas = true;
					break;					
				}
			}
			
			if (!checkVacacionesActivas) {
				
				//Desactivar registro de vacacaciones
				vacacionesBD.setFlagActivo(0L);
              	AuditoriaUtil.setAuditoriaActualizacion(vacacionesBD, AuditoriaUtil.getContextoJob());
				historialVacacionesDao.save(vacacionesBD);
				
				Long idUsuarioSaliente = usuarioDao.obtener(vacacionesBD.getUsuarioSaliente()).getIdUsuario();
				Long idUsuarioReemplazo = usuarioDao.obtener(vacacionesBD.getUsuarioReemplazo()).getIdUsuario();
				
				//G1
				if (vacacionesBD.getIdGrupoAprobadorReemplazo().equals(542L)) {
					
					//Obtener los registros perfil aprobador activos para desactivarlos
					List<HistorialAprobador> historialAprobador = new ArrayList<HistorialAprobador>();
					historialAprobador = historialAprobadorDao.obtenerHistorialAprobadorG2(idUsuarioSaliente);
					
					for (HistorialAprobador aprobador : historialAprobador) {
						aprobador.setFlagActivo(0L);
						aprobador.setUsuActualizacion(contexto.getUsuarioApp());
						aprobador.setIpActualizacion(contexto.getIp());
						aprobador.setFecActualizacion(new Date());
						historialAprobadorDao.save(aprobador);
					}
					
					//Retornar perfiles asignados al usuario reemplazo
					List<PerfilAprobador> perfilAprobadorG2 = new ArrayList<PerfilAprobador>();
					perfilAprobadorG2 = perfilAprobadorDao.obtenerPerfilAprobadorPorIdAprobadorG2(idUsuarioReemplazo);
					
					for (PerfilAprobador perfilAprobador : perfilAprobadorG2) {
						
						AuditoriaUtil.setAuditoriaActualizacion(perfilAprobador, AuditoriaUtil.getContextoJob());
						perfilAprobadorDao.actualizarAprobadorG2(perfilAprobador.getIdPerfilAprobador(), idUsuarioSaliente);
					}
					
					//Retornar asignaciones
					List<Asignacion> listaAsignacion = new ArrayList<Asignacion>();
					listaAsignacion = asignacionDao.listarAsignacionesPendintesAprobacion(idUsuarioReemplazo);
					
					for (Asignacion asignacion : listaAsignacion) {			
						
						//Actualizar la tabla de asignación
						AuditoriaUtil.setAuditoriaActualizacion(asignacion, AuditoriaUtil.getContextoJob());
						asignacionDao.actualizarUsuario(asignacion.getIdAsignacion(), idUsuarioSaliente, idUsuarioReemplazo);
					}
				}
				//G2
				else if (vacacionesBD.getIdGrupoAprobadorReemplazo().equals(543L)) {
					
					//Obtener los registros perfil aprobador activos
					List<HistorialAprobador> listaHistorialAprobadores = new ArrayList<HistorialAprobador>();
					listaHistorialAprobadores = historialAprobadorDao.obtenerHistorialAprobadorG2(idUsuarioSaliente);
					
					for (HistorialAprobador aprobador : listaHistorialAprobadores) {
						
						//Desactivar registros perfil aprpbador
						aprobador.setFlagActivo(0L);
						aprobador.setUsuActualizacion(contexto.getUsuarioApp());
						aprobador.setIpActualizacion(contexto.getIp());
						aprobador.setFecActualizacion(new Date());
						historialAprobadorDao.save(aprobador);
						
						//Retornar perfiles asignados al usuario reemplazo
						List<PerfilAprobador> listaPerfilAprobador = new ArrayList<PerfilAprobador>();
						listaPerfilAprobador = perfilAprobadorDao.obtenerPerfilAprobadorPorIdPerfil(aprobador.getIdPerfil());
						
						for (PerfilAprobador perfilAprobador : listaPerfilAprobador) {
							
							AuditoriaUtil.setAuditoriaActualizacion(perfilAprobador, AuditoriaUtil.getContextoJob());
							perfilAprobadorDao.actualizarAprobadorG2(perfilAprobador.getIdPerfilAprobador(), idUsuarioSaliente);
						}
					}
					
					//Retornar asignaciones
					List<Long> listaIdAsignacion = new ArrayList<Long>();
					listaIdAsignacion = asignacionPerfilDivisionDao.listarAsignacionesPendientesAprobacionXRetornar(vacacionesBD.getIdDivisionAprobadorSaliente());
					
					for (Long idAsignacion : listaIdAsignacion) {
						
						Asignacion asignacion = new Asignacion();
						asignacion = asignacionDao.obtener(idAsignacion);
						
						AuditoriaUtil.setAuditoriaActualizacion(asignacion, AuditoriaUtil.getContextoJob());
						asignacionDao.actualizarUsuario(asignacion.getIdAsignacion(), idUsuarioSaliente, idUsuarioReemplazo);
					}
				}
			}
		}
	}
	
	public Long obtenerIdArchivo(String numeroExpediente, String nombreUsuario) throws Exception {
		
		logger.info("obtenerIdArchivo {} {}", numeroExpediente, nombreUsuario);
		return sigedOldConsumer.obtenerIdArchivos(numeroExpediente, nombreUsuario);
	}
	
	public  AccessRequestInFirmaDigital obtenerParametrosfirmaDigital(String token, String usuario) throws Exception {
		
		AccessRequestInFirmaDigital acceso = new AccessRequestInFirmaDigital();
		
		acceso = sigedOldConsumer.obtenerParametrosfirmaDigital();
	
		String accessToken = sissegApiConsumer.obtenerAccessToken(token.replaceAll(" ", "+"));
		
		Long idUsuario = sissegApiConsumer.obtenerIdUsuario(usuario);
		
		Usuario user = sissegApiConsumer.obtenerUsuario(idUsuario, accessToken);
		
		acceso.setLoginUsuario(user.getUsuario());
		acceso.setPasswordUsuario(user.getContrasenia());
		
		return acceso;
	}

	@Override
	public Asignacion validarAprobador(Asignacion asignacion, Contexto contexto) {
		//Validar que algun aprobador de grupo este en asignado

		if(asignacion.getSolicitud()==null&&asignacion.getSolicitud().getSolicitudUuid()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		Long idSolicitud = solicitudService.obtenerId(asignacion.getSolicitud().getSolicitudUuid());

		ListadoDetalle tipoAprobador = listadoDetalleService.obtener(asignacion.getTipo().getIdListadoDetalle(), contexto);
		ListadoDetalle grupoAprobador = listadoDetalleService.obtener(asignacion.getGrupo().getIdListadoDetalle(), contexto);
		List<Asignacion> asignados = asignacionDao.obtenerAsignados(idSolicitud, tipoAprobador.getIdListadoDetalle(), grupoAprobador.getIdListadoDetalle());

		if (asignados.isEmpty() || asignados == null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ASIGNACION_FUERA_PROCESO);
		} else {
			boolean existeAsignado = asignados.stream()
					.anyMatch(a -> a.getEvaluacion() != null &&
							Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO.equals(a.getEvaluacion().getCodigo()));
			if (existeAsignado) {
				asignacion.setEvaluacion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_APROBACION.CODIGO, Constantes.LISTADO.RESULTADO_APROBACION.ASIGNADO));
			}
		}
		return asignacion;
	}
}
