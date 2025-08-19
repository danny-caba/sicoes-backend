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

import pe.gob.osinergmin.sicoes.model.PropuestaEconomica;
import pe.gob.osinergmin.sicoes.service.BitacoraService;
import pe.gob.osinergmin.sicoes.service.PropuestaEconomicaService;
import pe.gob.osinergmin.sicoes.util.Raml;


@RestController
@RequestMapping("/api")
public class PropuestaEconomicaRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(PropuestaEconomicaRestController.class);
	
	@Autowired
	private PropuestaEconomicaService propuestaEconomicaService;
	
	@Autowired
	private BitacoraService bitacoraService;

	@PostMapping("/propuestasEconomicas")
	@Raml("propuestasEconomicas.obtener.properties")
	public PropuestaEconomica registrar(@RequestBody PropuestaEconomica propuestaEconomica) {
		logger.info("propuestaEconomica {} ",propuestaEconomica);
		try {
			bitacoraService.registroPropuestaEconomicaInicio(propuestaEconomica, getContexto());		
			PropuestaEconomica propuestaEconomicaBD= propuestaEconomicaService.guardar(propuestaEconomica,getContexto());
			bitacoraService.registroPropuestaEconomicaFin(propuestaEconomica, getContexto());	
			return propuestaEconomicaBD;
		}catch (Exception e) {	
			bitacoraService.registrarPropuestaEconomicaError(propuestaEconomica,e.getMessage(), getContexto());
			throw e;
		}  
	}
	
	@PutMapping("/propuestasEconomicas/{id}")
	@Raml("propuestasEconomicas.obtener.properties")
	public PropuestaEconomica presentar(@PathVariable Long  id,@RequestParam(required=false) String propuestaUuid,@RequestBody PropuestaEconomica propuestaEconomica) {
		logger.info("actualizarPropuestaTecnica");
		propuestaEconomica.setIdPropuestaEconomica(id);
		propuestaEconomica.setPropuestaUuid(propuestaUuid);
		PropuestaEconomica propuestaEconomicaBD = propuestaEconomicaService.guardar(propuestaEconomica,getContexto());
		return propuestaEconomicaBD;
	}
	
	@GetMapping("/propuestasEconomicas")
	@Raml("propuestasEconomicas.listar.properties")
	public Page<PropuestaEconomica> buscarPropuestasEconomicas(Pageable pageable) {
		logger.info("buscarPropuestaEconomica");			
		return propuestaEconomicaService.listarPropuestasEconomicas(pageable,getContexto());
	}

	@DeleteMapping("/propuestasEconomicas/{id}")
	public void eliminar(@PathVariable Long  id,@RequestParam(required=false) String propuestaUuid){
		logger.info("eliminar {} ",id);
		propuestaEconomicaService.eliminar(id,propuestaUuid,getContexto());
	}

	
	@GetMapping("/propuestasEconomicas/{id}")
	@Raml("propuestasEconomicas.obtener.properties")
	public PropuestaEconomica obtener(@PathVariable Long id,@RequestParam(required=false) String propuestaUuid){
		logger.info("obtener {} ",id);
		return propuestaEconomicaService.obtener(id,propuestaUuid,getContexto());
	}
}
