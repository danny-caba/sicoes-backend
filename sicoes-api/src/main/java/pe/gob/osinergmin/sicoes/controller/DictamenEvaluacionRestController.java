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
import pe.gob.osinergmin.sicoes.model.DictamenEvaluacion;
import pe.gob.osinergmin.sicoes.service.DictamenEvaluacionService;
import pe.gob.osinergmin.sicoes.util.Raml;


@RestController
@RequestMapping("/api")
public class DictamenEvaluacionRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(DictamenEvaluacionRestController.class);
	
	@Autowired
	private DictamenEvaluacionService dictamenEvaluacionService;
	
//	@GetMapping("/dictamenEvaluaciones/{id}")
//	@Raml("dictamenEvaluacion.obtener.properties")
//	public DictamenEvaluacion obtener(@PathVariable Long id) {
//		return dictamenEvaluacionService.obtener(id,getContexto());
//	}
	
	@GetMapping("/dictamenEvaluaciones")
	@Raml("dictamenEvaluacion.listar.properties")
	public Page<DictamenEvaluacion> buscar(@RequestParam String solicitudUuid,Pageable pageable){
		return dictamenEvaluacionService.listarXSolicitud(solicitudUuid, pageable,getContexto());	
	}
	
	@PostMapping("/dictamenEvaluaciones")
	@Raml("dictamenEvaluacion.obtener.properties")
	public DictamenEvaluacion registrar(@RequestBody DictamenEvaluacion dictamenEvaluacion) {
		logger.info("registrarEtapa {} ",dictamenEvaluacion);
		 return dictamenEvaluacionService.guardar(dictamenEvaluacion,getContexto());
	}
	
	@PutMapping("/dictamenEvaluaciones/{id}")
	@Raml("dictamenEvaluacion.obtener.properties")
	public DictamenEvaluacion modificar(@PathVariable Long  id,@RequestBody DictamenEvaluacion dictamenEvaluacion) {
		logger.info("modificarDictamenEva {} ",dictamenEvaluacion);
		dictamenEvaluacion.setIdDictamenEvaluacion(id);
		return dictamenEvaluacionService.guardar(dictamenEvaluacion,getContexto());
	}
	
	@DeleteMapping("/dictamenEvaluaciones/{id}")
	public void eliminar(@PathVariable Long  id){
		logger.info("eliminar {} ",id);
		dictamenEvaluacionService.eliminar(id,getContexto());
	}

}
