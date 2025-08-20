package pe.gob.osinergmin.sicoes.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitudAdjunto;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitudSeccion;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudAdjuntoDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudSeccionDao;
import pe.gob.osinergmin.sicoes.service.SicoesSolicitudAdjuntoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class SicoesSolicitudAdjuntoServiceImpl implements SicoesSolicitudAdjuntoService {
	
	Logger logger = LogManager.getLogger(SicoesSolicitudAdjuntoServiceImpl.class);

	@Autowired
	private SicoesSolicitudSeccionDao sicoesSolicitudSeccionDao;
	
	@Autowired
	private SicoesSolicitudAdjuntoDao sicoesSolicitudAdjuntoDao;


	
	@Transactional(rollbackFor = Exception.class)
	public SicoesSolicitudAdjunto registrarCargaArchivo(Long idDetalleSolicitud, SicoesSolicitudAdjunto solicitudAdjunto, Contexto contexto) {
		
		if (sicoesSolicitudSeccionDao.findById(idDetalleSolicitud).isPresent()) {
			
			solicitudAdjunto.setEstado("1"); //Activo
			AuditoriaUtil.setAuditoriaRegistro(solicitudAdjunto,contexto);
			solicitudAdjunto.setIdDetalleSolicitud(idDetalleSolicitud); 
			SicoesSolicitudAdjunto adjuntoBD=sicoesSolicitudAdjuntoDao.save(solicitudAdjunto);
			return adjuntoBD;
		}else {
			
			throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
		}
	
	}
	
	@Override
	public void eliminarArchivoCargado(Long id, Contexto contexto) {
		//Long idDetalleSolicitud = new Long(0);
		if (sicoesSolicitudAdjuntoDao.findById(id).isPresent()) {
			//SicoesSolicitudAdjunto adjuntoBD=sicoesSolicitudAdjuntoDao.findById(id).get();
			//idDetalleSolicitud = adjuntoBD.getIdDetalleSolicitud();
			
			//eliminacion fisica
			//sicoesSolicitudAdjuntoDao.deleteById(id);
			
			//eliminacion logica
			String estado = "0"; //Inactivo
			Date hoy = new Date();
			sicoesSolicitudAdjuntoDao.setEstadoxSolicitudAdjunto(estado, id, contexto.getIp(), contexto.getUsuario().getUsuario(), hoy);
//			sicoesSolicitudSeccionDao.setEstadoxSolicitud(estado, idDetalleSolicitud,  contexto.getIp(), contexto.getUsuario().getUsuario(), hoy);
		}
		
		
		
	}


	@Override
	public SicoesSolicitudAdjunto obtener(Long id, Contexto contexto) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SicoesSolicitudAdjunto guardar(SicoesSolicitudAdjunto model, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String,Object>> getSicoesSolicitudAdjunto(int idSolcitudAdjunto, Contexto contexto) {
		List<Map<String,Object>> sicoesSolicitudAdjuntoOpt = sicoesSolicitudAdjuntoDao.getSicoesSolicitudAdjunto( idSolcitudAdjunto);
		 
		return sicoesSolicitudAdjuntoOpt;
	 
	}




	

}
