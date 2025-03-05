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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.osinergmin.sicoes.model.Requisito;
import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.service.RequisitoService;
import pe.gob.osinergmin.sicoes.util.Raml;


@RestController
@RequestMapping("/api")
public class RequisitoController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(RequisitoController.class);
	
	@Autowired
	private RequisitoService requisitoService;
 
 	@PostMapping("/sicoes/secciones/requisito")
	@Raml("requisito.obtener.properties")
	public Requisito guardar(@RequestBody Requisito  requisito) 
 	{
		return requisitoService.guardar(requisito, getContexto()) ;
	}
 	@DeleteMapping("/sicoes/secciones/requisito")	
	public void eliminar(@RequestBody Requisito  requisito) 
 	{
		  requisitoService.eliminarRequisito(requisito, getContexto()) ;
	}
	@GetMapping("/sicoes/secciones/requisitos")	
	public Map<String, Object>  obtenerRequisitoes(@RequestParam(required = false ) Integer pagina,
	 											 @RequestParam(required = false ) 	Integer tamaño) 
	{
		if(pagina==null || tamaño==null) 
		{
			return requisitoService.obtenerSinPaginacion();
		}
		else {
			return requisitoService.obtenerConPaginacion(pagina,tamaño) ;
		}
	 }
	@PutMapping("/sicoes/secciones/requisito")
	@Raml("requisito.obtener.properties")
	public Requisito  actualizarRequisitoes( @RequestBody Requisito  requisito) 
	{
		return  requisitoService.guardar(requisito, getContexto()) ;
	}

	@GetMapping("/requisitos/listar")
	@Raml("requisito.listar.properties")
	public Page<Requisito> buscarRequisitos(Pageable pageable) {
		logger.info("buscarRequisitos");
		return requisitoService.listarRequisitos(pageable, getContexto());
	}
	  
}
