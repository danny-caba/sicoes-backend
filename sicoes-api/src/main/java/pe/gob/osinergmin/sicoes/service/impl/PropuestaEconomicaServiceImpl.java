package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.PropuestaEconomica;
import pe.gob.osinergmin.sicoes.repository.PropuestaEconomicaDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.PropuestaEconomicaService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;


@Service
public class PropuestaEconomicaServiceImpl implements PropuestaEconomicaService {

	Logger logger = LogManager.getLogger(PropuestaEconomicaServiceImpl.class);

	@Autowired
	private PropuestaEconomicaDao propuestaEconomicaDao;
	
	@Autowired
	private ArchivoService archivoService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PropuestaEconomica guardar(PropuestaEconomica propuestaEconomica, Contexto contexto) {
		PropuestaEconomica propuestaEconomicaBD=null;
		if(propuestaEconomica.getIdPropuestaEconomica()==null) {
			propuestaEconomicaBD=propuestaEconomica;
		}else { 
			Double importe=null;
			if(propuestaEconomica.getImporte() ==null) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_NO_ENVIADO);
			}
			try {
				importe=Double.parseDouble(propuestaEconomica.getImporte());
			}catch (Exception e) {
				throw new ValidacionException(Constantes.CODIGO_MENSAJE.MONTO_NO_ENVIADO);
			}
			propuestaEconomicaBD = propuestaEconomicaDao.obtener(propuestaEconomica.getIdPropuestaEconomica());			
			propuestaEconomicaBD.setImporte(importe.toString());
		}
		
		AuditoriaUtil.setAuditoriaRegistro(propuestaEconomicaBD,contexto);
		propuestaEconomicaDao.save(propuestaEconomicaBD);
		return propuestaEconomicaBD;
	}

	@Override
	public PropuestaEconomica obtener(Long id,String codigo, Contexto contexto) {
		return propuestaEconomicaDao.obtener(id,codigo);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long idPropuestaEconomica,String uuidPropuesta, Contexto contexto) {
		propuestaEconomicaDao.eliminarPropuestaEconomica(idPropuestaEconomica,uuidPropuesta);		
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eliminar(Long idPropuestaEconomica, Contexto contexto) {
		propuestaEconomicaDao.deleteById(idPropuestaEconomica);		
	}

	@Override
	public Page<PropuestaEconomica> listarPropuestasEconomicas(Pageable pageable, Contexto contexto) {
		Page<PropuestaEconomica> propuestas=propuestaEconomicaDao.buscar(pageable);
		
		for(PropuestaEconomica propuesta:propuestas) {
			propuesta.setArchivos(archivoService.buscarPropuesta(propuesta.getIdPropuestaEconomica(),contexto));
		}
		return propuestas;
	}

	@Override
	public PropuestaEconomica obtener(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	}  

	

}
