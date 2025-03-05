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

import pe.gob.osinergmin.sicoes.model.ProcesoEtapa;
import pe.gob.osinergmin.sicoes.service.ProcesoEtapaService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api")
public class ProcesoEtapaRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(ProcesoEtapaRestController.class);
	
	@Autowired
	private ProcesoEtapaService procesoEtapaService;
	
	@PostMapping("/etapas")
	@Raml("procesoEtapa.obtener.properties")
	public ProcesoEtapa registrar(@RequestBody ProcesoEtapa etapa) {
		logger.info("registrarEtapa {} ",etapa);
		 return procesoEtapaService.guardar(etapa,getContexto());
	}
	
	@GetMapping("/etapas/{id}")
	@Raml("procesoEtapa.obtener.properties")
	public ProcesoEtapa obtener(@PathVariable Long  id,@RequestParam(required=true) String procesoUuid) {
		return procesoEtapaService.obtener(id, procesoUuid, getContexto());
	}
	
	@PutMapping("/etapas/{id}")
	@Raml("procesoEtapa.obtener.properties")
	public ProcesoEtapa modificar(@PathVariable Long  id,@RequestBody ProcesoEtapa etapa,@RequestParam(required=true) String procesoUuid) {
		logger.info("modificarEtapas {} ",etapa);
		etapa.setIdProcesoEtapa(id);
		etapa.getProceso().setProcesoUuid(procesoUuid);
		return procesoEtapaService.guardar(etapa,getContexto());
	}
	
	@GetMapping("/etapas/listar")
	@Raml("procesoEtapa.listar.properties")
	public Page<ProcesoEtapa> buscar(@RequestParam(required=true) String procesoUuid,Pageable pageable) {
		logger.info("listarEtapas");			
		return procesoEtapaService.listarEtapas(procesoUuid, pageable, getContexto());
	}
	
	@DeleteMapping("/etapas/{id}")
	public void eliminar(@PathVariable Long  id,@RequestParam(required=false) String procesoUuid){
		logger.info("eliminar {} ",id);
		procesoEtapaService.eliminar(id,procesoUuid,getContexto());
	}
}
