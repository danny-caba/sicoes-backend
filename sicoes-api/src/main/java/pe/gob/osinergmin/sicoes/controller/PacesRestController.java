
package pe.gob.osinergmin.sicoes.controller;

import java.util.List;

import javax.persistence.PostUpdate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.bytebuddy.implementation.bytecode.Throw;
import pe.gob.osinergmin.sicoes.model.Paces;
import pe.gob.osinergmin.sicoes.model.ProcesoConsulta;
import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.dto.PacesAprobadorDTO;
import pe.gob.osinergmin.sicoes.model.dto.PacesObservarDivisionDTO;
import pe.gob.osinergmin.sicoes.model.dto.PacesUpdateDTO;
import pe.gob.osinergmin.sicoes.service.PacesService;
import pe.gob.osinergmin.sicoes.service.ProcesoConsultaService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/paces")
public class PacesRestController extends BaseRestController {

    private Logger logger = LogManager.getLogger(ProcesoItemRestController.class);

    @Autowired
    private PacesService pacesService;

    @GetMapping("/obtenerPor")    
    public Page<Paces> obtenerPor(
    		@RequestParam(required = false) Long idArea,
			@RequestParam(required = false) Long idEstado, Pageable pageable) {
        logger.info("registrarConsulta {} ", "");
         
        Page<Paces> aa=pacesService.obtenerPor(idArea, idEstado, pageable, getContexto());
        return    aa;    
    }
      
    @GetMapping("/obtenerAsignadosG2Por")    
    public Page<Paces> obtenerAsignadosG2Por(
    		@RequestParam(required = false) Long idArea,
			@RequestParam(required = false) Long idEstado, Pageable pageable) {
        logger.info("registrarConsulta {} ", "");         
        return pacesService.obtenerAsignadosG2Por(idArea, idEstado, pageable, getContexto());          
    }
    
    @GetMapping("/obtenerAsignadosG3Por")    
    public Page<Paces> obtenerAsignadosG3Por(
    		@RequestParam(required = false) Long idArea,
			@RequestParam(required = false) Long idEstado, Pageable pageable) {
        logger.info("registrarConsulta {} ", "");         
        return pacesService.obtenerAsignadosG3Por(idArea, idEstado, pageable, getContexto());          
    }

    @GetMapping("/obtenerAceptadosEnviadosPor")    
    public Page<Paces> obtenerAceptadosEnviadosPor(
    		@RequestParam(required = false) Long idArea,
			@RequestParam(required = false) Long idEstado, Pageable pageable) {
        logger.info("registrarConsulta {} ", "");         
        return pacesService.obtenerAceptadosEnviadosPor(idArea, idEstado, pageable, getContexto());          
    }
               
	@PutMapping("/actualizar")	
	public PacesUpdateDTO actualizar(@RequestBody PacesUpdateDTO request) {
		
		logger.info("guardarObservacion {} {}");		
        Boolean result = pacesService.actualizar(request.getIdPaces(), request.getMesConvocatoria(), request.getNoConvocatoria(),request.getDePresupuesto() , request.getDeItemPaces() , request.getReProgramaAnualSupervision() , getContexto());
  
        request.setResultado(result);
        logger.info("result"+result);
        return request;        
		
	}
	
	@PutMapping("/observarPaceDivision")	
	public PacesObservarDivisionDTO observarPaceDivision(@RequestBody PacesObservarDivisionDTO request) {
		
		logger.info("guardarObservacion {} {}");		
        Boolean result = pacesService.observarPaceDivision(request.getIdPaces(),request.getObservacion(), getContexto());
          
        logger.info("result"+result);
        return request;        
		
	}
	
	@PutMapping("/aprobarPaceDivision")	
	public PacesObservarDivisionDTO aprobarPaceDivision(@RequestBody PacesObservarDivisionDTO request) {
		
		logger.info("guardarObservacion {} {}");		
        Boolean result = pacesService.aprobarPaceDivision(request.getIdPaces(),request.getObservacion(), getContexto());
          
        logger.info("result"+result);
        return request;        
		
	}
	
	@PutMapping("/aprobacionMasivaPaceDivision")	
	public List< PacesObservarDivisionDTO> aprobacionMasivaPaceDivision(@RequestBody List< PacesObservarDivisionDTO> request) {
				
		for (PacesObservarDivisionDTO paces : request) {
			Boolean result = pacesService.aprobarPaceDivision(paces.getIdPaces(),paces.getObservacion(), getContexto());
			paces.setResultado(true);
		}                         
        return request;        		
	}

    @PutMapping("/aprobadoEnviadoMasivaPaceGerencia")
    public List< PacesObservarDivisionDTO> aprobadoEnviadoMasivaPaceGerencia(@RequestBody List< PacesObservarDivisionDTO> request) {
        for (PacesObservarDivisionDTO paces : request) {
            Boolean result = pacesService.aprobarEnviarPaceGerencia(paces.getIdPaces(),paces.getObservacion(), getContexto());
            paces.setResultado(true);
        }
        return request;
    }

    @PutMapping("/cancelar")
	public PacesObservarDivisionDTO cancelar(@RequestBody PacesObservarDivisionDTO request) {
		
			
        Boolean result = pacesService.cancelarPaceGerencia(request.getIdPaces(),request.getObservacion(), getContexto());
          
        logger.info("result"+result);
        return request;        
		
	}

	@PutMapping("/aprobarEnviar")	
	public PacesObservarDivisionDTO aprobarEnviar(@RequestBody PacesObservarDivisionDTO request) {				
        Boolean result = pacesService.aprobarEnviarPaceGerencia(request.getIdPaces(),request.getObservacion(), getContexto());         
        logger.info("result"+result);
        return request;        
		
	}
	
	@DeleteMapping("/eliminar/{id}")
	public void eliminar(@PathVariable Long  id){		
		pacesService.eliminar(id,getContexto());
	}
	
	@PutMapping("/actualizarAprobadores")	
	public PacesAprobadorDTO actualizarAprobadores(@RequestBody PacesAprobadorDTO request) {
		
		logger.info("guardarObservacion {} {}");		
        Boolean result = pacesService.actualizarAprobador(request.getIdPace(), request.getIdAprobadorG2(), request.getIdAprobadorG3() , getContexto());
  
        request.setResultado(result);
        logger.info("result"+result);
        return request;        
		
	}	
}