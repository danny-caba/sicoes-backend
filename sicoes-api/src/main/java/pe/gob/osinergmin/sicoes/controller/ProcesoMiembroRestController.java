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

import pe.gob.osinergmin.sicoes.model.ProcesoMiembro;
import pe.gob.osinergmin.sicoes.service.ProcesoMiembroService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api")
public class ProcesoMiembroRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(ProcesoMiembroRestController.class);
	
	@Autowired
	private ProcesoMiembroService procesoMiembroService;
	
	@PostMapping("/miembros")
	@Raml("procesoMiembro.obtener.properties")
	public ProcesoMiembro registrar(@RequestBody ProcesoMiembro miembro) {
		logger.info("registrarMiembro {} ",miembro);
		 return procesoMiembroService.guardar(miembro,getContexto());
	}
	
	@GetMapping("/miembros/{id}")
	@Raml("procesoMiembro.obtener.properties")
	public ProcesoMiembro obtener(@PathVariable Long  id,@RequestParam(required=false) String procesoUuid) {
		return procesoMiembroService.obtener(id,procesoUuid,getContexto());
	}
	
	@PutMapping("/miembros/{id}")
	@Raml("procesoMiembro.obtener.properties")
	public ProcesoMiembro modificar(@PathVariable Long  id,@RequestBody ProcesoMiembro miembro,@RequestParam(required=false) String procesoUuid) {
		logger.info("modificarMiembro {} ",miembro);
		miembro.setIdProcesoMiembro(id);
		miembro.getProceso().setProcesoUuid(procesoUuid);
		return procesoMiembroService.guardar(miembro,getContexto());
	}
	
	@PutMapping("/miembros/{id}/inactivar")
	@Raml("procesoMiembro.obtener.properties")
	public ProcesoMiembro inactivar(@PathVariable Long  id,@RequestBody ProcesoMiembro miembro,@RequestParam(required=false) String procesoUuid) {
		logger.info("inactivarMiembro {} ",miembro);
		miembro.setIdProcesoMiembro(id);
		miembro.getProceso().setProcesoUuid(procesoUuid);
		return procesoMiembroService.inactivar(miembro,getContexto());
	}
	
	@GetMapping("/miembros/listar")
	@Raml("procesoMiembro.listar.properties")
	public Page<ProcesoMiembro> buscar(@RequestParam(required=true) String procesoUuid,Pageable pageable) {
		logger.info("listarMiembros");			
		return procesoMiembroService.listarMiembros(procesoUuid,pageable,getContexto());
	}
	
	@DeleteMapping("/miembros/{id}")
	public void eliminar(@PathVariable Long  id,@RequestParam(required=false) String procesoUuid){
		logger.info("eliminar {} ",id);
		procesoMiembroService.eliminarMiembro(id,procesoUuid,getContexto());
	}
}
