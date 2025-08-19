package pe.gob.osinergmin.sicoes.service.impl;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.Requisito;
import pe.gob.osinergmin.sicoes.repository.RequisitoDao;
import pe.gob.osinergmin.sicoes.service.RequisitoService;
import pe.gob.osinergmin.sicoes.service.RequisitoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;

@Service
public class RequisitoServiceImpl  implements RequisitoService{
	Logger logger = LogManager.getLogger(RequisitoServiceImpl.class);
	
	@Autowired
	private RequisitoDao requisitoDao;
	@Override
	public Requisito guardar(Requisito model, Contexto contexto) {
	  try {
		  AuditoriaUtil.setAuditoriaRegistro(model, contexto);
		  return requisitoDao.save(model);
	  }catch (Exception ex) {
		logger.error("Ocurrio un error", ex, ex, ex, ex, ex, ex, model, contexto, ex);
		  
	  }
		
		return null;
	}

	@Override
	public Requisito obtener(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	} 

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Requisito actualizar(Requisito requisito, Contexto contexto) {
		
		Optional<Requisito> requisitoOpt = requisitoDao.findById(requisito.getIdSeccionRequisito());
		Requisito requisitoFinal=requisitoOpt.orElseThrow(() -> new RuntimeException("Requisito no encontrado"));
		requisitoFinal.setFecActualizacion(new Date());
		
//		if (Requisito.getEsSeccionRequisito() != null) {
//			RequisitoFinal.setEsSeccionRequisito(Requisito.getEsSeccionRequisito());
//         }
//		if (Requisito.getIdSeccion() != null) {
//			 RequisitoFinal.setIdSeccion(Requisito.getIdSeccion());
//          }
//		 if (Requisito.getCoRequisito() != null) {
//			 RequisitoFinal.setCoRequisito(Requisito.getCoRequisito());
//          }
//
//		 if (Requisito.getIdTipoDatold() != null) {
//			 RequisitoFinal.setIdTipoDatold(Requisito.getIdTipoDatold());
//          }
//		 if (Requisito.getIdTipoDatoEntradaLd() != null) {
//			 RequisitoFinal.setIdTipoDatoEntradaLd(Requisito.getIdTipoDatoEntradaLd());
//          }
//
//		 if (Requisito.getIdTipoContratoLd() != null) {
//			 RequisitoFinal.setIdTipoContratoLd((Requisito.getIdTipoContratoLd()));
//          }
//		 if (Requisito.getDeSeccionRequisito() != null) {
//			 RequisitoFinal.setDeSeccionRequisito(((Requisito.getDeSeccionRequisito())));
//          }

		requisitoFinal.setIpActualizacion(contexto.getIp());
		requisitoFinal.setUsuActualizacion(contexto.getUsuario().getUsuario());
		return  requisitoDao.save(requisitoFinal) ;
		 
		   
	}
	@Override
	public Map<String, Object> obtenerSinPaginacion ()  {
		
		
		   
	        	 Pageable pageable = Pageable.unpaged();	
	    	 
	        	  Map<String, Object> response = new HashMap<>();
	    		 Page<Map<String, Object>> rpta  =requisitoDao.getRequisito(pageable) ;
	    		 response.put("lista", rpta.getContent());
	             response.put("elementos", rpta.getTotalElements());
	             response.put("paginas", rpta.getTotalPages());
	             
	           return response;	
	         
		  
	}

	@Override
	public Page<Requisito> listarRequisitos(Pageable pageable, Contexto contexto) {
		return requisitoDao.buscar(pageable);
	}

	@Override
	public Map<String, Object> obtenerConPaginacion (int pagina,int tamaño)  {
		
		 
	        	 Pageable pageable = PageRequest.of(pagina -1, tamaño);
	    	 
	        	  Map<String, Object> response = new HashMap<>();
	    		 Page<Map<String, Object>> rpta  =requisitoDao.getRequisito(pageable) ;
	             response.put("lista", rpta.getContent());
	             response.put("elementos", rpta.getTotalElements());
	             response.put("paginas", rpta.getTotalPages());
	             
	           return response;
	       
		  
	}
 
	@Override
	public void eliminar(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		
	}
	@Transactional
	@Override
	public void eliminarRequisito(Requisito requisito, Contexto contexto) {
		try {
			requisitoDao.deleteById(requisito.getIdSeccionRequisito());
		}
		catch(Exception ex){
			
			logger.error(ex.toString());
		}
		
	}

 
 
	  
}
