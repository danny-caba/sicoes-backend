package pe.gob.osinergmin.sicoes.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.consumer.PidoConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Estudio;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.EstudioDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.EstudioService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.ValidacionUtil;
import pe.gob.osinergmin.sicoes.util.bean.GradoTituloRO;
import pe.gob.osinergmin.sicoes.util.bean.SuneduOutRO;


@Service
public class EstudioServiceImpl implements EstudioService{
	
	Logger logger = LogManager.getLogger(EstudioServiceImpl.class);

	@Autowired
	private EstudioDao estudioDao;
	
	@Autowired
	private ListadoDetalleService listadoDetalleService; 
	
	@Autowired
	private ArchivoService archivoService; 
	
	@Autowired
	private PidoConsumer pidoConsumer; 
	
	@Autowired
	SolicitudService solicitudService;
	
	@Autowired
	ArchivoDao archivoDao;
	
	@Override
	public Estudio obtener(Long idEstudio, Contexto contexto) {
		Estudio estudio= estudioDao.obtener(idEstudio);
		estudio.setArchivos(archivoService.buscar(idEstudio,null,null, contexto));
		return estudio;
	}
	
	@Override
	public Page<Estudio> buscar(String tipo,Long idSolicitud,Pageable pageable,Contexto contexto) {
		Page<Estudio> estudios =estudioDao.buscar(tipo,idSolicitud,pageable);
		for(Estudio estudio:estudios) {
			estudio.setArchivos(archivoService.buscar(estudio.getIdEstudio(),null,null, contexto));
		}
		return estudios;
	}

	@Transactional(rollbackFor = Exception.class)
	public Estudio guardar(Estudio estudio, Contexto contexto) {
		if(estudio.getSolicitud()==null||estudio.getSolicitud().getSolicitudUuid()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		if(estudio.getTipo()==null||estudio.getTipo().getIdListadoDetalle()==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.E_TIPO_NO_ENVIADO);
		}
		if(StringUtils.isBlank(estudio.getEspecialidad())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESPECIALIDAD_NO_ENVIADO);
		}
		if(StringUtils.isBlank(estudio.getInstitucion())) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.INSTITUCION_NO_ENVIADO);
		}
		if(estudio.getArchivos().isEmpty()) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.E_ARCHIVOS_NO_ENVIADO);
		}
		
		if(estudio.getTipoEstudio().getCodigo().equals(Constantes.LISTADO.TIPO_ESTUDIO.ACADEMICOS)) {
			if(estudio.getFechaVigenciaGrado()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_GRADO_NO_ENVIADO);
			}
			if(estudio.getFlagColegiatura()=="1") {
				if(StringUtils.isBlank(estudio.getColegiatura())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.COLEGIATURA_NO_ENVIADO);
				}
				if(StringUtils.isBlank(estudio.getInstitucionColegiatura())) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.INST_COLEGIATURA_NO_ENVIADO);
				}
				if(estudio.getFechaVigenciaColegiatura()==null) {
					throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_COLEGIATURA_NO_ENVIADO);
				}
			}
			
		}
		if(estudio.getTipoEstudio().getCodigo().equals(Constantes.LISTADO.TIPO_ESTUDIO.CAPACITACION)) {
			if(estudio.getFechaVigencia()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_VIGENCIA_NO_ENVIADO );
			}
			if(estudio.getFechaInicio()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_INICIO_NO_ENVIADO);
			}
			if(estudio.getFechaFin()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_FIN_NO_ENVIADO);
			}
			if(estudio.getHora()==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.HORAS_NO_ENVIADO);
			}
			if(DateUtil.esMayorIgual(estudio.getFechaInicio(), estudio.getFechaFin())) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.FECHA_INICIO_MAYOR);
			}
		}
		Solicitud solicitud=solicitudService.obtener(estudio.getSolicitud().getSolicitudUuid(), contexto);
		ValidacionUtil.validarPresentacion(contexto,solicitud);
		Estudio estudioBD=null;
		if(estudio.getIdEstudio()==null) {
			estudio.getSolicitud().setIdSolicitud(solicitud.getIdSolicitud());
			estudio.setTipoEstudio(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ESTUDIO.CODIGO,estudio.getTipoEstudio().getCodigo()));
			estudio.setFuente(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.FUENTE_ESTUDIO.CODIGO,Constantes.LISTADO.FUENTE_ESTUDIO.MANUAL));
			estudio.setEvaluacion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO,Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR));
			estudio.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ESTUDIO.CODIGO,Constantes.LISTADO.ESTADO_ESTUDIO.ACTUAL));
			estudioBD=estudio;
		}else { 
			estudioBD = estudioDao.obtener(estudio.getIdEstudio());			
				if(Constantes.LISTADO.FUENTE_ESTUDIO.MANUAL.equals(estudioBD.getFuente().getCodigo())){
					estudioBD.setTipo(estudio.getTipo());
					estudioBD.setTipo(listadoDetalleService.obtener(estudio.getTipo().getIdListadoDetalle(), contexto));
					estudioBD.setEspecialidad(estudio.getEspecialidad());
					estudioBD.setInstitucion(estudio.getInstitucion());
					estudioBD.setFechaVigenciaGrado(estudio.getFechaVigenciaGrado());					
				}
				estudioBD.setFlagEgresado(estudio.getFlagEgresado());
				estudioBD.setFlagColegiatura(estudio.getFlagColegiatura());
				estudioBD.setColegiatura(estudio.getColegiatura());
				estudioBD.setFechaVigenciaColegiatura(estudio.getFechaVigenciaColegiatura());
				estudioBD.setInstitucionColegiatura(estudio.getInstitucionColegiatura());
				estudioBD.setDetalleTipo(estudio.getDetalleTipo());
		}
		if(Constantes.FLAG.ACTIVO.toString().equals(estudioBD.getFlagColegiatura())) {
//			if(estudio.getArchivos()==null||estudio.getArchivos().size()<2) {
//				throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTUDIO_SIN_ARCHIVO);	
//			}
		}
		
		if(Constantes.LISTADO.ESTADO_SOLICITUD.OBSERVADO.equals(solicitud.getEstado().getCodigo())&&
				Constantes.LISTADO.RESULTADO_EVALUACION.OBSERVADO.equals(estudioBD.getEvaluacion().getCodigo())	) {
			estudioBD.setEvaluacion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO,Constantes.LISTADO.RESULTADO_EVALUACION.RESPONDIDO));
		}
		estudioBD.setFlagSiged(Constantes.FLAG.INACTIVO);
		AuditoriaUtil.setAuditoriaRegistro(estudio,contexto);
		estudioDao.save(estudioBD);
		archivoService.asociarArchivos(estudioBD.getSolicitud().getIdSolicitud(), estudioBD.getIdEstudio(), estudio.getArchivos(),contexto);
		actualizarNombreArchivo(estudioBD.getSolicitud().getIdSolicitud(), contexto);
		return estudioBD;
	}
	
	private void actualizarNombreArchivo(Long idSolicitud,Contexto contexto) {
		List<Estudio> 		academicos=buscar(Constantes.LISTADO.TIPO_ESTUDIO.ACADEMICOS, idSolicitud,  contexto);
		int indice=1;
		for(Estudio estudioAux:academicos) {
			List<Archivo> archivosEstudio=archivoService.buscar(estudioAux.getIdEstudio(),null,null, contexto);
			for(Archivo archivoAux:archivosEstudio) {
				archivoAux.setCorrelativo(Long.parseLong(indice+""));
				if(Constantes.LISTADO.RESULTADO_EVALUACION.RESPONDIDO.equals(estudioAux.getEvaluacion().getCodigo())) {
					archivoAux.setVersion(2L);
				}else {
					archivoAux.setVersion(1L);
				}
				
				String nombre="";
				if(Constantes.LISTADO.TIPO_ARCHIVO.COLEGIATURA.equals(archivoAux.getTipoArchivo().getCodigo())) {
					nombre=archivoAux.getTipoArchivo().getValor()+"_"+archivoAux.getTipoArchivo().getDescripcion()+"_"+StringUtils.leftPad(archivoAux.getCorrelativo()+"",2,'0')+"_v"+archivoAux.getVersion()+".pdf";
				}else {
					nombre=archivoAux.getTipoArchivo().getValor()+"_"+estudioAux.getTipo().getValor()+"_"+StringUtils.leftPad(archivoAux.getCorrelativo()+"",2,'0')+"_v"+archivoAux.getVersion()+".pdf";
				}
				archivoAux.setNombre(nombre);
				logger.info(archivoAux.getCorrelativo()+":"+archivoAux.getNombre());
				archivoService.guardar(archivoAux, contexto);
			}
			indice++;
		}
		List<Estudio> 		capacitaciones=buscar(Constantes.LISTADO.TIPO_ESTUDIO.CAPACITACION, idSolicitud,  contexto);
		indice=1;		
		for(Estudio estudio:capacitaciones) {
			List<Archivo> archivosEstudio=archivoService.buscar(estudio.getIdEstudio(),null,null, contexto);
			for(Archivo archivoAux:archivosEstudio) {
				archivoAux.setCorrelativo(Long.parseLong(indice+""));
				if(Constantes.LISTADO.RESULTADO_EVALUACION.RESPONDIDO.equals(estudio.getEvaluacion().getCodigo())) {
					archivoAux.setVersion(2L);
				}else {
					archivoAux.setVersion(1L);
				}
				String nombre=archivoAux.getTipoArchivo().getValor()+"_"+estudio.getTipo().getValor()+"_"+StringUtils.leftPad(archivoAux.getCorrelativo()+"",2,'0')+"_v"+archivoAux.getVersion()+".pdf";
				archivoAux.setNombre(nombre);
				logger.info(archivoAux.getCorrelativo()+":"+archivoAux.getNombre());
				archivoService.guardar(archivoAux, contexto);
			}
			indice++;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, Contexto contexto) {
		archivoService.eliminarIdEstudio(id, contexto);
		estudioDao.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void calcularEstudioSunedu(Long idSolicitud, Pageable pageable, Contexto contexto) {
		if(idSolicitud==null) {
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
		Solicitud solicitudBD=solicitudService.obtener(idSolicitud, contexto);
		String DNI="";
		DNI=solicitudBD.getPersona().getNumeroDocumento();
//		DNI=DNI.substring(2,10);
		try {
			SuneduOutRO suneduOutRO = pidoConsumer.obtenerGrados(DNI);
			ListadoDetalle estudioAcademico=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_ESTUDIO.CODIGO,Constantes.LISTADO.TIPO_ESTUDIO.ACADEMICOS);
			ListadoDetalle sunedu=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.FUENTE_ESTUDIO.CODIGO,Constantes.LISTADO.FUENTE_ESTUDIO.SUNEDU);
			List<Estudio> estudios=buscar(idSolicitud, contexto);
			for(Estudio estudio:estudios) {
				if(estudio.getFuente().getIdListadoDetalle()==sunedu.getIdListadoDetalle()) {
					eliminar(estudio.getIdEstudio(), contexto);
				}
			}
			//estudioDao.limpiarEstudios(estudioAcademico.getIdListadoDetalle(),sunedu.getIdListadoDetalle());
			
			if(suneduOutRO!=null&&suneduOutRO.getGradosyTitulos()!=null) {
				List<GradoTituloRO> listGradoTituloRO= suneduOutRO.getGradosyTitulos();		
				for(GradoTituloRO gradoTituloRO:listGradoTituloRO) {
					Estudio estudio=new Estudio();
					estudio.setTipo(listadoDetalleService.obtenerListadoDetalleValor(Constantes.LISTADO.GRADO_ACADEMICO.CODIGO,gradoTituloRO.getAbreviaturaTitulo()));
					estudio.setTipoEstudio(estudioAcademico);
					if(Constantes.LISTADO.GRADO_ACADEMICO.BACHILLER.equals(estudio.getTipo().getCodigo())) {
						estudio.setEspecialidad(gradoTituloRO.getTituloProfesional()+" - "+gradoTituloRO.getEspecialidad());
					}else {
						estudio.setEspecialidad(gradoTituloRO.getTituloProfesional());
					}					
					estudio.setInstitucion(gradoTituloRO.getUniversidad());
					if(gradoTituloRO.getFechaEmision()!=null&&gradoTituloRO.getFechaEmision().length()==8) {
						estudio.setFechaVigenciaGrado(DateUtil.getFormat(gradoTituloRO.getFechaEmision(),"dd/MM/yy"));
					}else {
						estudio.setFechaVigenciaGrado(DateUtil.getFormat(gradoTituloRO.getFechaEmision(),"dd/MM/yyyy"));
					}
					estudio.setSolicitud(solicitudBD);
					estudio.setEvaluacion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.RESULTADO_EVALUACION.CODIGO,Constantes.LISTADO.RESULTADO_EVALUACION.POR_EVALUAR));
					estudio.setFuente(sunedu);
					AuditoriaUtil.setAuditoriaRegistro(estudio,contexto);
					estudioDao.save(estudio);
				}
			}
		}catch (Exception e) {
			logger.error("ERROR {} ",e.getMessage(), e);
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ERROR_OBTENER_ESTUDIO);
		}
	}

	@Override
	public List<Estudio> buscar(String tipo, Long idSolicitud, Contexto contexto) {
		List<Estudio> estudios =estudioDao.buscar(tipo,idSolicitud);
		return estudios;
	}
	
	@Override
	public List<Estudio> buscarPresentacion(String tipo, Long idSolicitud, Contexto contexto) {
		List<Estudio> estudios =estudioDao.buscarPresentacion(tipo,idSolicitud);
		return estudios;
	}
	
	@Override
	public List<Estudio> buscar(Long idSolicitud, Contexto contexto) {
		List<Estudio> estudios =estudioDao.buscar(idSolicitud);
		return estudios;
	}

	@Override
	public Estudio evalular(Estudio estudio, Contexto contexto) {
		Estudio estudioBD=estudioDao.obtener(estudio.getIdEstudio());
		estudioBD.setEvaluacion(estudio.getEvaluacion());
		estudioBD.setObservacion(estudio.getObservacion());
		AuditoriaUtil.setAuditoriaRegistro(estudioBD,contexto);
		estudioDao.save(estudioBD);
//		String codigoTipoEvaluador=null;
//		if(Constantes.LISTADO.TIPO_ESTUDIO.ACADEMICOS.equals(estudioBD.getTipoEstudio().getCodigo())) {
//			codigoTipoEvaluador=Constantes.LISTADO.TIPO_EVALUADOR.ADMINISTRATIVO;
//		}else {
//			codigoTipoEvaluador=Constantes.LISTADO.TIPO_EVALUADOR.TECNICO;
//		}
		
		solicitudService.actualizarProceso(estudioBD.getSolicitud(),Constantes.LISTADO.TIPO_EVALUADOR.TECNICO,contexto);
		return estudioBD;
	}
	
	public void copiarCapacitacionesUltimaSolicitud(String solicitudUuidUltima, String solicitudUuid, Contexto contexto) {
		Long idSolicitudUltima = solicitudService.obtenerId(solicitudUuidUltima);
		Long idSolicitud = solicitudService.obtenerId(solicitudUuid);
		List<Estudio> listaEstudiosUltimaSolicitud = estudioDao.buscar(idSolicitudUltima);
		List<Estudio> listaEstudiosSolicitud = estudioDao.buscar(idSolicitud);
		
		if (listaEstudiosSolicitud == null || listaEstudiosSolicitud.isEmpty()) {
			if (listaEstudiosUltimaSolicitud != null) {
				Estudio estudioNuevo = null;
				for (Estudio estudio : listaEstudiosUltimaSolicitud) {
					estudioNuevo = new Estudio();
					Solicitud solicitud = new Solicitud();
					solicitud.setIdSolicitud(idSolicitud);
					estudioNuevo.setSolicitud(solicitud);
					estudioNuevo.setTipo(estudio.getTipo());
					estudioNuevo.setFlagEgresado(estudio.getFlagEgresado());
					estudioNuevo.setColegiatura(estudio.getColegiatura());
					estudioNuevo.setEspecialidad(estudio.getEspecialidad());
					estudioNuevo.setDetalleTipo(estudio.getDetalleTipo());
					estudioNuevo.setFechaVigenciaGrado(estudio.getFechaVigenciaGrado());
					estudioNuevo.setInstitucion(estudio.getInstitucion());
					estudioNuevo.setFlagColegiatura(estudio.getFlagColegiatura());
					estudioNuevo.setFechaVigenciaColegiatura(estudio.getFechaVigenciaColegiatura());
					estudioNuevo.setInstitucionColegiatura(estudio.getInstitucionColegiatura());
					estudioNuevo.setHora(estudio.getHora());
					estudioNuevo.setFechaVigencia(estudio.getFechaVigencia());
					estudioNuevo.setFechaInicio(estudio.getFechaInicio());
					estudioNuevo.setFechaFin(estudio.getFechaFin());
					ListadoDetalle evaluacion = new ListadoDetalle();
					evaluacion.setIdListadoDetalle(estudio.getEvaluacion().getIdListadoDetalle());
					estudioNuevo.setEvaluacion(evaluacion);
					estudioNuevo.setTipoEstudio(estudio.getTipoEstudio());
					estudioNuevo.setFuente(estudio.getFuente());
					estudioNuevo.setFlagSiged(estudio.getFlagSiged());
					estudioNuevo.setUsuCreacion(contexto.getUsuario().getUsuario());
					estudioNuevo.setIpCreacion(contexto.getIp());
					estudioNuevo.setFecCreacion(new Date());
					estudioNuevo = estudioDao.save(estudioNuevo);
					
					List<Archivo> listaArchivos = archivoDao.buscarXEstudio(estudio.getIdEstudio());
					if (listaArchivos != null) {
						Archivo archivoNuevo = null;
						for (Archivo archivo : listaArchivos) {
							archivoNuevo = new Archivo();
							archivoNuevo.setIdEstudio(estudioNuevo.getIdEstudio());
							archivoNuevo.setEstado(archivo.getEstado());
							archivoNuevo.setTipoArchivo(archivo.getTipoArchivo());
							archivoNuevo.setIdSolicitud(idSolicitud);
							archivoNuevo.setNombre(archivo.getNombre());
							archivoNuevo.setNombreReal(archivo.getNombreReal());
							archivoNuevo.setNombreAlFresco(archivo.getNombreAlFresco());
							archivoNuevo.setCodigo(UUID.randomUUID().toString());
							archivoNuevo.setTipo(archivo.getTipo());
							archivoNuevo.setCorrelativo(archivo.getCorrelativo());
							archivoNuevo.setVersion(archivo.getVersion());
							archivoNuevo.setNroFolio(archivo.getNroFolio());
							archivoNuevo.setPeso(archivo.getPeso());
							archivoNuevo.setUsuCreacion(contexto.getUsuario().getUsuario());
							archivoNuevo.setIpCreacion(contexto.getIp());
							archivoNuevo.setFecCreacion(new Date());
							archivoNuevo = archivoDao.save(archivoNuevo);
						}
					}
				}
			}
		}
	}

}
