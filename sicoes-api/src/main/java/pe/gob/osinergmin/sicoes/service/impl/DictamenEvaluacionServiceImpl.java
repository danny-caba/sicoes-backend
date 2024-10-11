package pe.gob.osinergmin.sicoes.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.DictamenEvaluacion;
import pe.gob.osinergmin.sicoes.model.OtroRequisito;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.repository.DictamenEvaluacionDao;
import pe.gob.osinergmin.sicoes.service.DictamenEvaluacionService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class DictamenEvaluacionServiceImpl implements DictamenEvaluacionService {

	Logger logger = LogManager.getLogger(DictamenEvaluacionServiceImpl.class);
	
	@Autowired
	private DictamenEvaluacionDao dictamenEvaluacionDao;
	
	@Autowired
	private SolicitudService solicitudService;

	@Override
	public DictamenEvaluacion guardar(DictamenEvaluacion dictamenEvaluacion, Contexto contexto) {
		
		DictamenEvaluacion dictamenEvaluacionBD = null;
		if(dictamenEvaluacion.getIdDictamenEvaluacion() == null) {
			Solicitud sol = solicitudService.obtener(dictamenEvaluacion.getSolicitud().getSolicitudUuid(), contexto);
			dictamenEvaluacion.setSolicitud(sol);
			dictamenEvaluacionBD = dictamenEvaluacion;
		}else {
			dictamenEvaluacionBD = dictamenEvaluacionDao.obtener(dictamenEvaluacion.getIdDictamenEvaluacion());
			dictamenEvaluacionBD.setMontoFacturado(dictamenEvaluacion.getMontoFacturado());
		}
		AuditoriaUtil.setAuditoriaRegistro(dictamenEvaluacionBD,contexto);
		dictamenEvaluacionDao.save(dictamenEvaluacionBD);
		return dictamenEvaluacionBD;
	}


	@Override
	public DictamenEvaluacion obtener(Long idDictamenEvaluacion, Contexto contexto) {
		return dictamenEvaluacionDao.obtener(idDictamenEvaluacion);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long id, Contexto contexto) {
		dictamenEvaluacionDao.deleteById(id);
	}
	
	@Override
	public DictamenEvaluacion obtenerXSolicitud(Long idSolicitud, Long idSector) {
		return dictamenEvaluacionDao.obtenerXSolicitud(idSolicitud,idSector);
	}


	@Override
	public Page<DictamenEvaluacion> listarXSolicitud(String solicitudUuid, Pageable pageable, Contexto contexto) {
		Long idSolicitud = solicitudService.obtenerId(solicitudUuid);
		return dictamenEvaluacionDao.obtenerXSolicitud(idSolicitud,pageable);
	}
	
	@Override
	public List<DictamenEvaluacion> listar(Long idSolicitud, Contexto contexto) {
		return dictamenEvaluacionDao.listar(idSolicitud);
	}


	@Override
	public DictamenEvaluacion obtenerXSol(Long idSolicitud, Long idSector, Contexto contexto) {
		return dictamenEvaluacionDao.obtenerXSol(idSolicitud,idSector);
	}
	
	@Override
	public DictamenEvaluacion obtenerXCodigoSector(Long idSolicitud, String codigoSector, Contexto contexto) {
		return dictamenEvaluacionDao.obtenerXCodigoSector(idSolicitud,codigoSector);
	}

	@Override
	public void eliminarXSector(OtroRequisito requisito, Contexto contexto) {
		dictamenEvaluacionDao.eliminarXSector(requisito.getSolicitud().getIdSolicitud(),requisito.getSector().getIdListadoDetalle());
	}
	
	@Override
	public void eliminarXCodigoSector(String codigoSector, Long idSolicitud, Contexto contexto) {
		DictamenEvaluacion dictamen = dictamenEvaluacionDao.obtenerXCodigoSector(idSolicitud,codigoSector);
		if(dictamen != null) {
			dictamenEvaluacionDao.delete(dictamen);
		}
	}


	@Override
	public void guardarDictamen(DictamenEvaluacion dictamenNuevo, Contexto contexto) {
		AuditoriaUtil.setAuditoriaRegistro(dictamenNuevo,contexto);
		dictamenEvaluacionDao.save(dictamenNuevo);
	}


}
