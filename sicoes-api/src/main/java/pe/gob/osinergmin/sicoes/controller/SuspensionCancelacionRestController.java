package pe.gob.osinergmin.sicoes.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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

import pe.gob.osinergmin.sicoes.model.Solicitud;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.SuspensionCancelacion;
import pe.gob.osinergmin.sicoes.service.SuspensionCancelacionService;
import pe.gob.osinergmin.sicoes.util.Raml;



@RestController
@RequestMapping("/api/suspension-cancelacion")
public class SuspensionCancelacionRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(SuspensionCancelacionRestController.class);
	
	@Autowired
	private SuspensionCancelacionService suspensionCancelacionService;
	
	
	@GetMapping
	@Raml("suspensionCancelacion.listar.properties")
	public Page<SuspensionCancelacion> buscar(			
				@RequestParam(required=false) String nroExpediente,
				@RequestParam(required=false) Long idTipoDocumento,
				@RequestParam(required=false) Long idTipoPersona,
				@RequestParam(required=false)String ruc,
				@RequestParam(required=false)String nombres,
				@RequestParam(required=false) Long idEstado,Pageable pageable){
		Page<SuspensionCancelacion> supervisoras= suspensionCancelacionService.buscar(nroExpediente,idTipoPersona,idTipoDocumento,ruc,nombres,idEstado,pageable,getContexto());
		return supervisoras;
	}

	@GetMapping("/export")
	public void exportar(HttpServletRequest request,HttpServletResponse response,		
			@RequestParam(required=false) String nroExpediente,
			@RequestParam(required=false) Long idTipoDocumento,
			@RequestParam(required=false) Long idTipoPersona,
			@RequestParam(required=false)String ruc,
			@RequestParam(required=false)String nombres,
			@RequestParam(required=false) Long idEstado)throws Exception {
		InputStream is=suspensionCancelacionService.generarExport(nroExpediente,idTipoPersona,idTipoDocumento,ruc,nombres,idEstado,getContexto());
		response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename=Empresas_Supervisoras.xlsx");
	    IOUtils.copy(is, response.getOutputStream());
	}
	
	
	
	@GetMapping("/{id}")
	@Raml("suspensionCancelacion.obtener.properties")
	public SuspensionCancelacion obtener(@PathVariable Long id) {
		return suspensionCancelacionService.obtener(id,getContexto());
	}
	
	@PostMapping
	@Raml("suspensionCancelacion.obtener.properties")
	public SuspensionCancelacion registrar(@RequestBody SuspensionCancelacion suspensionCancelacion) {
		logger.info("registrar {} ",suspensionCancelacion);		
		SuspensionCancelacion suspensionCancelacionBD=  suspensionCancelacionService.guardar(suspensionCancelacion,getContexto());
		return suspensionCancelacionBD;
	}
	
	@PutMapping("/{id}")
	@Raml("suspensionCancelacion.obtener.properties")
	public SuspensionCancelacion modificar(@PathVariable Long  id,@RequestBody SuspensionCancelacion suspensionCancelacion) {
		logger.info("modificar {} {}",id,suspensionCancelacion);
		suspensionCancelacion.setIdSuspensionCancelacion(id);				
		return suspensionCancelacionService.guardar(suspensionCancelacion,getContexto());
		
	}
	
	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long  id){
		logger.info("eliminar {} ",id);
		suspensionCancelacionService.eliminar(id,getContexto());
	}
	
}
