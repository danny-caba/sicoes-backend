package pe.gob.osinergmin.sicoes.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.PropuestaConsorcio;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.repository.EmpresasConsorcioDao;
import pe.gob.osinergmin.sicoes.repository.PropuestaConsorcioDao;
import pe.gob.osinergmin.sicoes.service.BitacoraService;
import pe.gob.osinergmin.sicoes.service.PropuestaConsorcioService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class PropuestaConsorcioServiceImpl implements PropuestaConsorcioService {

	Logger logger = LogManager.getLogger(PropuestaConsorcioServiceImpl.class);
	
	@Autowired
	private PropuestaConsorcioDao propuestaConsorcioDao;
	
	@Autowired
	private EmpresasConsorcioDao empresasConsorcioDao;
	
	@Autowired
	private BitacoraService bitacoraService;
	
	@Override
	public List<Supervisora> obtenerEmpresasSupervisoraSector(Long idSector) {
		
		return empresasConsorcioDao.obtenerEmpresasSupervisoraSector(idSector);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public PropuestaConsorcio guardar(PropuestaConsorcio propuestaConsorcio, Contexto contexto) {
		
		PropuestaConsorcio propuestaConsorcioBD=null;
		
		if(propuestaConsorcio.getIdPropuestaConsorcio() == null) {
			propuestaConsorcioBD = propuestaConsorcio;
		}else { 
			propuestaConsorcioBD = propuestaConsorcioDao.obtener(propuestaConsorcio.getIdPropuestaConsorcio());
			propuestaConsorcioBD.setParticipacion(propuestaConsorcio.getParticipacion());
		}
		
		AuditoriaUtil.setAuditoriaRegistro(propuestaConsorcioBD, contexto);
		propuestaConsorcioDao.save(propuestaConsorcioBD);
		
		return propuestaConsorcioBD;
	}
	
	@Override
	public List<PropuestaConsorcio> obtenerEmpresasConsorcio(Long idPropuestaTecnica, Long idSector) {
		
		return empresasConsorcioDao.obtenerEmpresasConsorcio(idPropuestaTecnica, idSector);
	}

	@Override
	public PropuestaConsorcio obtener(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
	    PropuestaConsorcio empresa = propuestaConsorcioDao.obtener(id);
	    if (empresa != null) {
	        logger.info("Empresa encontrada, eliminando: {}", empresa);
	        propuestaConsorcioDao.delete(empresa);  // Eliminar la empresa
	    } else {
	        logger.error("Empresa no encontrada con ID: {}", id);
	        throw new ValidacionException("Empresa no encontrada.");
	    }
	}


}
