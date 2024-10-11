package pe.gob.osinergmin.sicoes.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.SolicitudNotificacion;
import pe.gob.osinergmin.sicoes.repository.SolicitudNotificacionDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.SolicitudNotificacionService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class SolicitudNotificacionServiceImpl implements SolicitudNotificacionService{
	
	Logger logger = LogManager.getLogger(SolicitudServiceImpl.class);

	@Autowired
	SolicitudNotificacionDao solicitudNotificacionDao;
	
	@Autowired
	ArchivoService  archivoService;
	
	@Autowired
	ListadoDetalleService listadoDetalleService;
	
	@Autowired
	SolicitudService solicitudService;
	
	@Override
	public SolicitudNotificacion obtener(Long id, Contexto contexto) {
		SolicitudNotificacion solicitudNotificacion= solicitudNotificacionDao.obtener(id);
		List<Archivo> list = archivoService.buscarArchivo(solicitudNotificacion, contexto);
		if (list != null && !list.isEmpty()) {
			solicitudNotificacion.setArchivo(list.get(0));
		}

		return solicitudNotificacion;
	}

	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
	public SolicitudNotificacion guardar(SolicitudNotificacion solicitudNotificacion, Contexto contexto) {
		
		Long idSolicitud = solicitudService.obtenerId(solicitudNotificacion.getSolicitudUuid());
		solicitudNotificacion.setIdSolicitud(idSolicitud);
		AuditoriaUtil.setAuditoriaRegistro(solicitudNotificacion,contexto);
		ListadoDetalle tipo=listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_NOTIFICACION_SOLICITUD.CODIGO, solicitudNotificacion.getTipo().getCodigo());
		if(tipo!=null) {
			solicitudNotificacion.setTipo(tipo);
		}
		SolicitudNotificacion solicitudNotificacionBD=solicitudNotificacionDao.save(solicitudNotificacion);
		if(solicitudNotificacionBD.getArchivo()!=null) {
			archivoService.asociarArchivo(solicitudNotificacionBD, solicitudNotificacionBD.getArchivo(), contexto);
		}
		if(Constantes.LISTADO.TIPO_NOTIFICACION_SOLICITUD.RESPUESTA.equals(solicitudNotificacion.getTipo().getCodigo())) {
			solicitudService.marcarSolicitudRespuesta(idSolicitud,solicitudNotificacion.getFechaNotificacion(), contexto);
		}else {
			solicitudService.marcarSolicitudArchivado(idSolicitud, contexto);
		}
		
		return solicitudNotificacionBD;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		solicitudNotificacionDao.deleteById(id);
	}

	@Override
	public Page<SolicitudNotificacion> buscar(String codigoTipoDocumento,Pageable pageable, Contexto contexto) {
		
		Page<SolicitudNotificacion> solicitudNotificaciones =solicitudNotificacionDao.buscar(codigoTipoDocumento,pageable);
		return solicitudNotificaciones;
	}

	@Override
	public SolicitudNotificacion obtenerXSolicitud(String tipo, String solicitudUuid, Contexto contexto) {
		SolicitudNotificacion solicitudNotificacion=solicitudNotificacionDao.obtenerXSolicitud(tipo, solicitudUuid);
		if(solicitudNotificacion != null) {
			List<Archivo> list = archivoService.buscarArchivo(solicitudNotificacion, contexto);
			if (list != null && !list.isEmpty()) {
				solicitudNotificacion.setArchivo(list.get(0));
			}	
		}
		return solicitudNotificacion;
	}

}
