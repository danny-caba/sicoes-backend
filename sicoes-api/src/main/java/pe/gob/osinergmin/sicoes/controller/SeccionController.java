package pe.gob.osinergmin.sicoes.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.service.SeccionService;
 



@RestController
@RequestMapping("/api")
public class SeccionController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(SeccionController.class);
	
	@Autowired
	private SeccionService seccionService;

 	@PostMapping("/secciones/seccion")
	public Seccion guardar(@RequestBody Seccion  seccion) 
 	{
		return seccionService.guardar(seccion, getContexto()) ;
	}
 	@DeleteMapping("/secciones/seccion")
	public void eliminar(@RequestBody Seccion  seccion) 
 	{
		  seccionService.eliminarSeccion(seccion, getContexto()) ;
	}
	@GetMapping("/sicoes/secciones/secciones")	
	public Map<String, Object>  obtenerSecciones(@RequestParam(required = false ) Integer pagina,
	 											 @RequestParam(required = false ) 	Integer tamaño) 
	{
		if(pagina==null || tamaño==null) 
		{
			return seccionService.obtenerSinPaginacion();
		}
		else {
			return seccionService.obtenerConPaginacion(pagina,tamaño) ;
		}
	 }
	@PutMapping("/secciones/seccion")
	public Seccion  actualizarSecciones( @RequestBody Seccion  seccion) 
	{
		return  seccionService.actualizar(seccion, getContexto()) ;
	}

	@GetMapping("/secciones/listar")
	public Page<Seccion> buscarSecciones(Pageable pageable) {
		logger.info("buscarSecciones");
		return seccionService.listarSecciones(pageable, getContexto());
	}

	@GetMapping("/secciones/{idSeccion}/maestra")
	public Seccion obtenerSeccionPorId(@PathVariable Long idSeccion) {
		logger.info("obtenerSeccionPorId");
		return seccionService.obtenerSeccionPorId(idSeccion);
	}
}
