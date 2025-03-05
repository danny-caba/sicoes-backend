package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitudSeccion;
import pe.gob.osinergmin.sicoes.model.SicoesTdSolPerConSec;
import pe.gob.osinergmin.sicoes.service.SicoesSolicitudSeccionService;
import pe.gob.osinergmin.sicoes.service.SicoesTdSolPerConSecService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SicoesTdSolPerConSecRestController extends BaseRestController {

private Logger logger = LogManager.getLogger(SicoesTdSolPerConSecRestController.class);
	
	
	@Autowired
	private SicoesTdSolPerConSecService sicoesTdSolPerConSecService;


	@GetMapping("/secciones/{idSolicitud}")
	//@Raml("proceso.obtener.properties")
	public List<SicoesTdSolPerConSec> obtener(@PathVariable Long idSolicitud) {
		logger.info("obtener seciones x solicitud {} ", idSolicitud);

		return sicoesTdSolPerConSecService.obtenerSeccionesXSolicitud(idSolicitud);
	}
}
