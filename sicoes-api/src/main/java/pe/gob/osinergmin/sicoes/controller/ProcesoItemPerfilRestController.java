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

import pe.gob.osinergmin.sicoes.model.ProcesoItemPerfil;
import pe.gob.osinergmin.sicoes.service.ProcesoItemPerfilService;
import pe.gob.osinergmin.sicoes.util.Raml;



@RestController
@RequestMapping("/api")
public class ProcesoItemPerfilRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(ProcesoItemPerfilRestController.class);
	
	@Autowired
	private ProcesoItemPerfilService procesoItemPerfilService;
	
	@PostMapping("/itemPerfiles")
	@Raml("procesoItemPerfil.obtener.properties")
	public ProcesoItemPerfil registrar(@RequestBody ProcesoItemPerfil itemPerfil) {
		logger.info("registrarPerfilItem {} ",itemPerfil);
		 return procesoItemPerfilService.guardar(itemPerfil,getContexto());
	}
	
	@GetMapping("/itemPerfiles/listar")
	@Raml("procesoItemPerfil.listar.properties")
	public Page<ProcesoItemPerfil> buscar(@RequestParam(required=true) String procesoItemUuid,Pageable pageable) {
		logger.info("listarItems");			
		return procesoItemPerfilService.listarPerfiles(procesoItemUuid,pageable,getContexto());
	}
	
	@DeleteMapping("/itemPerfiles/{idItemPerfil}")
	public void eliminar(@PathVariable Long idItemPerfil,@RequestParam(required=true) String procesoItemUuid){
		logger.info("eliminar {} ",idItemPerfil);
		procesoItemPerfilService.eliminar(idItemPerfil,getContexto());
	}
	
	@GetMapping("/itemPerfiles/{idProcesoItemPerfil}")
	@Raml("procesoItemPerfil.obtener.properties")
	public ProcesoItemPerfil obtener(@PathVariable Long idProcesoItemPerfil,@RequestParam(required=true) String procesoItemUuid) {
		return procesoItemPerfilService.obtener(idProcesoItemPerfil,procesoItemUuid ,getContexto());
	}
	
	@PutMapping("/itemPerfiles/{idProcesoItemPerfil}")
	@Raml("procesoItemPerfil.obtener.properties")
	public ProcesoItemPerfil modificar(@PathVariable Long idProcesoItemPerfil,@RequestBody ProcesoItemPerfil perfil,@RequestParam(required=true) String procesoItemUuid) {
		logger.info("modificarItems {} ",perfil);
		perfil.setIdProcesoItemPerfil(idProcesoItemPerfil);
		perfil.getProcesoItem().setProcesoItemUuid(procesoItemUuid);
		return procesoItemPerfilService.guardar(perfil,getContexto());
	}
	
}
