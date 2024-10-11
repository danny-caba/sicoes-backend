package pe.gob.osinergmin.sicoes.controller;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.service.BitacoraService;
import pe.gob.osinergmin.sicoes.service.PropuestaService;
import pe.gob.osinergmin.sicoes.util.GenerarBatch;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.util.ValidacionException;




@RestController
@RequestMapping("/api")
public class PropuestaRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(PropuestaRestController.class);
	
	@Autowired
	private PropuestaService propuestaService;
	
	@Autowired
	private BitacoraService bitacoraService;
	

	@PostMapping("/propuestas")
	public Propuesta registrar(@RequestBody Propuesta propuesta) {
		logger.info("registrarPropuesta {} ",propuesta);
		try {
			bitacoraService.registroPropuesta(propuesta, getContexto());		
			Propuesta propuestaBD= propuestaService.guardar(propuesta,getContexto());
			return propuestaBD;
		}catch (ValidacionException e) {	
			throw e;
		} 
	}

	@GetMapping("/propuestas/{propuestaUuid}")
	@Raml("propuesta.obtener.properties")
	public Propuesta obtener(@PathVariable String propuestaUuid){
		logger.info("obtener {} ",propuestaUuid);
		return propuestaService.obtener(propuestaUuid,getContexto());
	}
	
	@GetMapping("/propuestas/validaciones/{propuestaUuid}")
	public List<String> validaciones(@PathVariable String propuestaUuid){
		logger.info("validaciones {} ",propuestaUuid);
		 return propuestaService.validaciones(propuestaUuid,getContexto());
	}

	@GetMapping("/propuestas/generar-zip/{propuestaUuid}")
	public void generarZip(@PathVariable String propuestaUuid){
		logger.info("generarZip {} ",propuestaUuid);
		Propuesta propuesta=new Propuesta();
		propuesta.setPropuestaUuid(propuestaUuid);
		GenerarBatch generarBatch =new GenerarBatch(propuestaService, propuesta,getContexto());
		Thread thread=new Thread(generarBatch);
		thread.start();
	}
	
	@PutMapping("/propuestas/{propuestaUuid}/presentar")
	@Raml("propuesta.obtener.properties")
	public Propuesta presentar(@PathVariable String propuestaUuid,@RequestBody Propuesta propuesta) {
		logger.info("presentarPropuesta");
	
		try {
			propuesta.setPropuestaUuid(propuestaUuid);
			bitacoraService.presentarPropuesta(propuesta, getContexto());		
			Propuesta propuestaBD= propuestaService.presentar(propuestaUuid,propuesta,getContexto());
			return propuestaBD;
		}catch (ValidacionException e) {	
			throw e;
		}
	}
	
	@PutMapping("/propuestas/{propuestaUuid}/seleccionar")
	@Raml("propuesta.obtener.properties")
	public Propuesta seleccionar(@PathVariable String propuestaUuid,@RequestBody Propuesta propuesta) {
		logger.info("presentarPropuesta");
		propuesta.setPropuestaUuid(propuestaUuid);
		Propuesta propuestaBD= propuestaService.seleccionar(propuestaUuid,propuesta,getContexto());
		return propuestaBD;
	}

	@GetMapping("/propuestas/{procesoItemUuid}/export")
	public void exportar(HttpServletRequest request,HttpServletResponse response,@PathVariable String procesoItemUuid)throws Exception {
//		logger.info("export {} {} {} {} {} {} ",numeroExpediente,idTipoEmpresa,nombreRazonSocial,numeroDocumento);
		InputStream is=propuestaService.generarExport(procesoItemUuid,getContexto());
		response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename=Resumen_empresas_postulantes.xlsx");
	    IOUtils.copy(is, response.getOutputStream());
	}
	
	@GetMapping("/propuestas/item/{procesoItemUuid}")
	@Raml("propuesta.listar.properties")
	public Page<Propuesta> buscarPropuestasXItem(@PathVariable String procesoItemUuid,Pageable pageable) {
		logger.info("buscarPropuestasProfesionales");			
		return propuestaService.listarPropuestasXItem(procesoItemUuid, pageable,getContexto());
	}
	

}
