package pe.gob.osinergmin.sicoes.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitudAdjunto;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.service.SicoesSolicitudAdjuntoService;
import pe.gob.osinergmin.sicoes.service.SolicitudSicoesService;

@RestController
@RequestMapping("/sicoes")
public class SicoesSolicitudAdjuntoCabeceraRestController extends BaseRestController {

	private Logger logger = LogManager.getLogger(SicoesSolicitudAdjuntoCabeceraRestController.class);
	
	@Autowired
	private SolicitudSicoesService solicitudSicoesService;
	
	@PutMapping("/solicitud/{estado}")
	//@Raml("proceso.obtener.properties")
	public SicoesSolicitud updateSolicitud(@PathVariable String  estado,@RequestBody SicoesSolicitud sicoesSolicitud) {
		logger.info("Actualizar Estado {} ", sicoesSolicitud);

		return solicitudSicoesService.updateSolicitud(estado,sicoesSolicitud, getContexto());
	}
	@GetMapping("/solicitudes/proceso/{id}")
	//@Raml("proceso.obtener.properties")
	public Optional<SicoesSolicitud> getSolicitud(@PathVariable Long  id) {
		logger.info("Lista soliicitudes {} ", id);

		return solicitudSicoesService.getSolicitud(id , getContexto());
	}
	 
	
	@PutMapping("/solicitud/evaluacion/requisito/finaliza")
	//@Raml("proceso.obtener.properties")
	public Optional<SicoesSolicitud> putRequisitoFin(@RequestBody SicoesSolicitud sicoesSolicitud) {
		logger.info("Put soliicitudes fin {} ", sicoesSolicitud);

		return solicitudSicoesService.putRequisitoFinaliza(sicoesSolicitud , getContexto());
	}
	 
}
