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

import pe.gob.osinergmin.sicoes.model.ProcesoItem;
import pe.gob.osinergmin.sicoes.service.ProcesoItemService;
import pe.gob.osinergmin.sicoes.util.GenerarBatchMasivo;
import pe.gob.osinergmin.sicoes.util.Raml;



@RestController
@RequestMapping("/api")
public class ProcesoItemRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(ProcesoItemRestController.class);
	
	@Autowired
	private ProcesoItemService procesoItemService;
	
	@PostMapping("/items")
	@Raml("procesoItem.obtener.properties")
	public ProcesoItem registrar(@RequestBody ProcesoItem item) {
		logger.info("registrarProceso {} ",item);
		 return procesoItemService.guardar(item,getContexto());
	}
	
	@GetMapping("/items/{procesoItemUuid}")
	@Raml("procesoItem.obtener.properties")
	public ProcesoItem obtener(@PathVariable String procesoItemUuid) {
		return procesoItemService.obtener(procesoItemUuid ,getContexto());
	}
	
	@PutMapping("/items/{procesoItemUuid}")
	@Raml("procesoItem.obtener.properties")
	public ProcesoItem modificar(@PathVariable String procesoItemUuid,@RequestBody ProcesoItem item) {
		logger.info("modificarItems {} ",item);
		item.setProcesoItemUuid(procesoItemUuid);
		return procesoItemService.guardar(item,getContexto());
	}
	
	@GetMapping("/items/listar")
	@Raml("procesoItem.listar.properties")
	public Page<ProcesoItem> buscar(@RequestParam(required=true) String procesoUuid,Pageable pageable) {
		logger.info("listarItems");			
		return procesoItemService.listarItems(procesoUuid,pageable,getContexto());
	}
	
	@GetMapping("/items/procesos")
	@Raml("procesoItem.listar.properties")
	public Page<ProcesoItem> buscarProcesos(
			@RequestParam(required=false) String fechaDesde,
			@RequestParam(required=false) String fechaHasta,
			@RequestParam(required=false) Long idEstadoItem,
			@RequestParam(required=false) Long idSector,
			@RequestParam(required=false) Long idSubSector,
			@RequestParam(required=false) String nroProceso,
			@RequestParam(required=false) String nombreProceso,
			@RequestParam(required=false) String descripcionItem,
			@RequestParam(required=false) String nroExpediente,
			@RequestParam(required=false) Long idEstadoProceso,
			Pageable pageable) {
		logger.info("listarItems");			
		return procesoItemService.listarProcesos(fechaDesde,fechaHasta,idEstadoItem,idSector,idSubSector,nroProceso,nombreProceso,descripcionItem,nroExpediente,idEstadoProceso,pageable,getContexto());
	}
	
	@DeleteMapping("/items/{procesoItemUuid}")
	public void eliminar(@PathVariable String procesoItemUuid){
		logger.info("eliminar {} ",procesoItemUuid);
		procesoItemService.eliminar(procesoItemUuid,getContexto());
	}
	
	
	@GetMapping("/items/generar-zip/{procesoItemUuid}")
	public void generarZip(@PathVariable String procesoItemUuid){
		logger.info("generarZip {} ",procesoItemUuid);
		ProcesoItem procesoItem = new ProcesoItem();
		procesoItem.setProcesoItemUuid(procesoItemUuid);
		GenerarBatchMasivo generarBatch =new GenerarBatchMasivo(procesoItemService, procesoItem,getContexto());
		Thread thread=new Thread(generarBatch);
		thread.start();		

	}
}
