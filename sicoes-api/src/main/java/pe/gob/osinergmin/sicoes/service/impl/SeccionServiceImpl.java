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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.repository.SeccionDao;
import pe.gob.osinergmin.sicoes.service.SeccionService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;

@Service
public class SeccionServiceImpl  implements SeccionService{
	Logger logger = LogManager.getLogger(SeccionServiceImpl.class);
	
	@Autowired
	private SeccionDao seccionDao;
	@Override
	public Seccion guardar(Seccion model, Contexto contexto) {
	  try {
		  AuditoriaUtil.setAuditoriaRegistro(model, contexto);
		  return seccionDao.save(model);
	  } catch (Exception ex) {
		logger.error("Ocurrio un error", ex, ex, ex, ex, ex, ex, model, contexto, ex);
	  }
	  return null;
	}

	@Override
	public Seccion obtener(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		return null;
	} 

	@Override
	public Seccion actualizar(Seccion seccion, Contexto contexto) {
		Optional<Seccion> seccionOpt = seccionDao.findById(seccion.getIdSeccion());
		Seccion seccionFinal=seccionOpt.orElseThrow(() -> new RuntimeException("Seccion no encontrado"));

		seccionFinal.setEsSeccion(seccion.getEsSeccion());
		if (seccion.getDeSeccion() != null) {
			seccionFinal.setDeSeccion(seccion.getDeSeccion());
		}

		if (seccion.getCoSeccion() != null) {
			seccionFinal.setCoSeccion(seccion.getCoSeccion());
		}

		if (seccion.getFlReqPersonal() != null) {
			seccionFinal.setFlReqPersonal(seccion.getFlReqPersonal());
		}

		if (seccion.getFlVisibleSeccion() != null) {
			seccionFinal.setFlVisibleSeccion(seccion.getFlVisibleSeccion());
		}

		AuditoriaUtil.setAuditoriaActualizacion(seccionFinal, contexto);
		return seccionDao.save(seccionFinal);
	}

	@Override
	public Map<String, Object> obtenerSinPaginacion ()  {
		Pageable pageable = Pageable.unpaged();
		Map<String, Object> response = new HashMap<>();
		Page<Map<String, Object>> rpta  =seccionDao.getListaSeccion(pageable) ;
		response.put("lista", rpta.getContent());
		response.put("elementos", rpta.getTotalElements());
		response.put("paginas", rpta.getTotalPages());
	             
	    return response;
	}

	@Override
	public Page<Seccion> listarSecciones(Pageable pageable, Contexto contexto) {
		return seccionDao.buscar(pageable);
	}

	@Override
	public Seccion obtenerSeccionPorId(Long idSeccion) {
		return seccionDao.findById(idSeccion)
				.orElseThrow(() -> new RuntimeException("Seccion no encontrado"));
	}

	@Override
	public Map<String, Object> obtenerConPaginacion (int pagina,int tamaño)  {
		
		 
	        	 Pageable pageable = PageRequest.of(pagina -1, tamaño);
	    	 
	        	  Map<String, Object> response = new HashMap<>();
	    		 Page<Map<String, Object>> rpta  =seccionDao.getListaSeccion(pageable) ;
	             response.put("lista", rpta.getContent());
	             response.put("elementos", rpta.getTotalElements());
	             response.put("paginas", rpta.getTotalPages());
	             
	           return response;
	       
		  
	}
 
	@Override
	public void eliminar(Long id, Contexto contexto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarSeccion(Seccion seccion, Contexto contexto) {
		seccionDao.delete(seccion);
		
	}

	  
}
