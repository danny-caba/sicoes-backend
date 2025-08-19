package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.PropuestaTecnica;
import pe.gob.osinergmin.sicoes.service.BitacoraService;
import pe.gob.osinergmin.sicoes.service.PropuestaTecnicaService;
import pe.gob.osinergmin.sicoes.util.Raml;



@RestController
@RequestMapping("/api")
public class PropuestaTecnicaRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(PropuestaTecnicaRestController.class);
	
	@Autowired
	private PropuestaTecnicaService propuestaTecnicaService;
	
	@Autowired
	private BitacoraService bitacoraService;
	

	@PostMapping("/propuestasTecnicas")
	@Raml("propuestasTecnicas.obtener.properties")
	public PropuestaTecnica registrar(@RequestBody PropuestaTecnica propuestaTecnica) {
		logger.info("registrarPropuestaTecnica {} ",propuestaTecnica);
		try {
			bitacoraService.registroPropuestaTecnicaInicio(propuestaTecnica, getContexto());		
			PropuestaTecnica propuestaTecnicaBD= propuestaTecnicaService.guardar(propuestaTecnica,getContexto());
			bitacoraService.registroPropuestaTecnicaFin(propuestaTecnica, getContexto());	
			return propuestaTecnicaBD;
		}catch (Exception e) {	
			bitacoraService.registrarPropuestaTecnicaError(propuestaTecnica,e.getMessage(), getContexto());
			throw e;
		}  
	}
	
	@PutMapping("/propuestasTecnicas/{id}")
	@Raml("propuestasTecnicas.obtener.properties")
	public PropuestaTecnica presentar(@PathVariable Long  id,@RequestParam(required=false) String propuestaUuid,@RequestBody PropuestaTecnica propuestaTecnica) {
		logger.info("actualizarPropuestaTecnica");
		propuestaTecnica.setIdPropuestaTecnica(id);
		propuestaTecnica.setPropuestaUuid(propuestaUuid);
		PropuestaTecnica propuestaTecnicaBD = propuestaTecnicaService.guardar(propuestaTecnica,getContexto());
		return propuestaTecnicaBD;
	}
	
	@GetMapping("/propuestasTecnicas")
	@Raml("propuestasTecnicas.listar.properties")
	public Page<PropuestaTecnica> buscarPropuestasTecnicas(Pageable pageable) {
		logger.info("buscarPropuestasTecnicas");			
		return propuestaTecnicaService.listarPropuestasTecnicas(pageable,getContexto());
	}

	
	@DeleteMapping("/propuestasTecnicas/{id}")
	public void eliminar(@PathVariable Long  id,@RequestParam(required=false) String propuestaUuid){
		logger.info("eliminar {} ",id);
		propuestaTecnicaService.eliminar(id,propuestaUuid,getContexto());
	}
	
	@GetMapping("/propuestasTecnicas/{id}")
	@Raml("propuestasTecnicas.obtener.properties")
	public PropuestaTecnica obtener(@PathVariable Long id,@RequestParam(required=false) String propuestaUuid){
		logger.info("obtener {} ",id);
		return propuestaTecnicaService.obtener(id,propuestaUuid,getContexto());
	}

}
