package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.PropuestaTecnica;
import pe.gob.osinergmin.sicoes.repository.PropuestaTecnicaDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.PropuestaTecnicaService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class PropuestaTecnicaServiceImpl implements PropuestaTecnicaService {

	Logger logger = LogManager.getLogger(PropuestaTecnicaServiceImpl.class);

	@Autowired
	private PropuestaTecnicaDao propuestaTecnicaDao;
	
	@Autowired
	private ArchivoService archivoService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PropuestaTecnica guardar(PropuestaTecnica propuestaTecnica, Contexto contexto) {
		PropuestaTecnica propuestaTecnicaBD=null;
		if(propuestaTecnica.getIdPropuestaTecnica()==null) {
			propuestaTecnicaBD=propuestaTecnica;
		}else { 
			propuestaTecnicaBD = propuestaTecnicaDao.obtener(propuestaTecnica.getIdPropuestaTecnica());			
			propuestaTecnicaBD.setConsorcio(propuestaTecnica.getConsorcio());
		}
		AuditoriaUtil.setAuditoriaRegistro(propuestaTecnicaBD,contexto);
		propuestaTecnicaDao.save(propuestaTecnicaBD);
		archivoService.asociarArchivos(propuestaTecnicaBD.getIdPropuestaTecnica(),propuestaTecnicaBD.getArchivos(),contexto);
		return propuestaTecnicaBD;
	}

	@Override
	public PropuestaTecnica obtener(Long id,String uuidPropuesta, Contexto contexto) {
		PropuestaTecnica propuestaTecnicaBD = propuestaTecnicaDao.obtener(id,uuidPropuesta);
		if(propuestaTecnicaBD != null) {
			propuestaTecnicaBD.setArchivos(archivoService.buscarPropuesta(propuestaTecnicaBD.getIdPropuestaTecnica(),contexto));
		}
		
		return propuestaTecnicaBD ;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long idPropuestaTecnica,String uuidPropuesta, Contexto contexto) {
		propuestaTecnicaDao.eliminarPropuestaTecnica(idPropuestaTecnica,uuidPropuesta);		
	}

	@Override
	public Page<PropuestaTecnica> listarPropuestasTecnicas(Pageable pageable, Contexto contexto) {
		Page<PropuestaTecnica> propuestas=propuestaTecnicaDao.buscar(pageable);
		
		for(PropuestaTecnica propuesta:propuestas) {
			propuesta.setArchivos(archivoService.buscarPropuesta(propuesta.getIdPropuestaTecnica(),contexto));
		}
		return propuestas;
	}

	@Override
	public PropuestaTecnica obtener(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		
	}  

	

}
