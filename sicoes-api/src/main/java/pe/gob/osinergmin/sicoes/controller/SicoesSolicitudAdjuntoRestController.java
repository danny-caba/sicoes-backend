package pe.gob.osinergmin.sicoes.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.SicoesSolicitudAdjunto;
import pe.gob.osinergmin.sicoes.service.SicoesSolicitudAdjuntoService;

@RestController
@RequestMapping("/sicoes/solicitud/seccion/requisito")
public class SicoesSolicitudAdjuntoRestController extends BaseRestController {

	private Logger logger = LogManager.getLogger(SicoesSolicitudAdjuntoRestController.class);
	
	@Autowired
	private SicoesSolicitudAdjuntoService sicoesSolicitudAdjuntoService;
	
	@PutMapping("/archivo/{id}")
	//@Raml("proceso.obtener.properties")
	public SicoesSolicitudAdjunto registrar(@PathVariable Long  id,@RequestBody SicoesSolicitudAdjunto solicitudSeccionAdjunto) {
		logger.info("registrar carga archivo {} ", solicitudSeccionAdjunto);

		return sicoesSolicitudAdjuntoService.registrarCargaArchivo(id,solicitudSeccionAdjunto, getContexto());
	}
	
	@GetMapping("/{id}")
	//@Raml("proceso.obtener.properties")
	public List<Map<String,Object>> getRequisito(@PathVariable int  id   ) {
		logger.info("Listar Solicitudes adjuntis {} ", id);

		return sicoesSolicitudAdjuntoService.getSicoesSolicitudAdjunto(id, getContexto());
	}
 
	@DeleteMapping("/archivo/{id}")
	public void eliminar(@PathVariable Long  id){
		logger.info("eliminar carga archivo {} ",id);
		sicoesSolicitudAdjuntoService.eliminarArchivoCargado(id,getContexto());
	}
}
