package pe.gob.osinergmin.sicoes.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.service.SeccionService;
import pe.gob.osinergmin.sicoes.service.SolicitudSicoesService;
 



@RestController
@RequestMapping("/api")
public class SolicitudSicoesController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(SolicitudSicoesController.class);
	
	@Autowired
	private SolicitudSicoesService solicitudSicoesService ;
	
 
	
 
 	@GetMapping("/sicoes/solicitud/seccion/requisitoxxx")	
	public SicoesSolicitud getSoolicitud(   @PathVariable("idSolicitud") Long id) 
 	{
		return solicitudSicoesService.obtener(id, getContexto())   ;
	}
 
}

