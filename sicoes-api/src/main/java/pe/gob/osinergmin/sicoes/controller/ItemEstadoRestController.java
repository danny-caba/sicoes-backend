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

import pe.gob.osinergmin.sicoes.model.ItemEstado;
import pe.gob.osinergmin.sicoes.service.ItemEstadoService;
import pe.gob.osinergmin.sicoes.util.Raml;



@RestController
@RequestMapping("/api")
public class ItemEstadoRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(ItemEstadoRestController.class);
	
	@Autowired
	private ItemEstadoService itemEstadoService;
	
	@PostMapping("/itemsEstado")
	public ItemEstado registrar(@RequestBody ItemEstado itemEstado) {
		logger.info("registrarItemEstado {} ",itemEstado);
		 return itemEstadoService.guardar(itemEstado,getContexto());
	}
	
	@GetMapping("/itemsEstado/{id}")
	@Raml("itemEstado.obtener.properties")
	public ItemEstado obtener(@PathVariable Long id) {
		return itemEstadoService.obtener(id ,getContexto());
	}
	
	@PutMapping("/itemsEstado/{id}")
	@Raml("itemEstado.obtener.properties")
	public ItemEstado modificar(@PathVariable Long id,@RequestBody ItemEstado itemEstado) {
		logger.info("modificarItemEstado {} ",itemEstado);
		itemEstado.setIdItemEstado(id);
		return itemEstadoService.guardar(itemEstado,getContexto());
	}
	
	@GetMapping("/itemsEstado/listar")
	@Raml("itemEstado.listar.properties")
	public Page<ItemEstado> buscar(@RequestParam(required=true) String procesoItemUuid,Pageable pageable) {
		logger.info("listarItemEstado");			
		return itemEstadoService.listarItemsEstado(procesoItemUuid,pageable,getContexto());
	}
	
	@DeleteMapping("/itemsEstado/{id}")
	public void eliminar(@PathVariable Long id,@RequestParam(required=true) String procesoItemUuid){
		logger.info("eliminarItemEstado {} ",id);
		itemEstadoService.eliminar(id,procesoItemUuid,getContexto());
	}
}
