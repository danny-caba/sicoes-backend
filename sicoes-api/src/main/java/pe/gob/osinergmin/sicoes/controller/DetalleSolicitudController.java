package pe.gob.osinergmin.sicoes.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.SicoesTdSolPerConSec;
import pe.gob.osinergmin.sicoes.service.SeccionService;
import pe.gob.osinergmin.sicoes.service.SicoesTdSolPerConSecService;
@RestController
@RequestMapping("/api")
public class DetalleSolicitudController extends BaseRestController {
private Logger logger = LogManager.getLogger(DetalleSolicitudController.class);
	
	@Autowired
	private SicoesTdSolPerConSecService seccionService;
	
	@PostMapping("/sicoes/solicitud")	
	public List<Map<String,Object>> guardar(@RequestBody SicoesSolicitud model)
 	{
		return null;
	}
}


 
	 
 
	
 
 	