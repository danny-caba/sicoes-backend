package pe.gob.osinergmin.sicoes.controller;

import java.io.InputStream;
import java.util.Map;

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

import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.PropuestaProfesional;
import pe.gob.osinergmin.sicoes.service.PropuestaProfesionalService;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.controller.EmpresasSancionadaRestController;


@RestController
@RequestMapping("/api")
public class PropuestaProfesionalesRestController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(PropuestaProfesionalesRestController.class);
	
	@Autowired
	private PropuestaProfesionalService propuestaProfesionalService;
	
	@Autowired
	private EmpresasSancionadaRestController empresasSancionadaRestController;

	@PostMapping("/propuestasProfesionales")
	@Raml("propuestasProfesionales.obtener.properties")
	public PropuestaProfesional registrar(@RequestBody PropuestaProfesional propuestaProfesional) {
		logger.info("registrarpropuestaProfesional {} ",propuestaProfesional);	
		 return propuestaProfesionalService.guardar(propuestaProfesional,getContexto());
	}
	
	@PostMapping("/propuestasProfesionales2")
	@Raml("propuestasProfesionales.obtener.properties")
	public PropuestaProfesional registrar2(@RequestBody PropuestaProfesional propuestaProfesional) {
		logger.info("registrarpropuestaProfesional {} ",propuestaProfesional);
		
		
		Map<String, String> valor = empresasSancionadaRestController.ValidadSancion(propuestaProfesional.getSupervisora().getCodigoRuc());
		if ("1".equals(valor.get("respuestaFec"))) {
			
			PropuestaProfesional propuestaProfesional2 = new PropuestaProfesional();
			propuestaProfesional2.setIdPropuestaProfesional((long) 1);
			return propuestaProfesional2;
			
		}
		if ("1".equals(valor.get("respuestaPN"))) {
			
			PropuestaProfesional propuestaProfesional2 = new PropuestaProfesional();
			propuestaProfesional2.setIdPropuestaProfesional((long) 2);
			return propuestaProfesional2;
		}
		
		 return propuestaProfesionalService.guardar(propuestaProfesional,getContexto());
	}
	
	@GetMapping("/propuestasProfesionales")
	@Raml("propuestasProfesionales.listar.properties")
	public Page<PropuestaProfesional> buscarPropuestasProfesionales(
			@RequestParam(required=false) String fechaDesde,
			@RequestParam(required=false) String fechaHasta,
			@RequestParam(required=false) Long idSector,
			@RequestParam(required=false) Long idSubsector,
			@RequestParam(required=false) String nroProceso,
			@RequestParam(required=false) String nombreProceso,
			@RequestParam(required=false) Long idEstadoItem,
			@RequestParam(required=false) Long idEstadoInvitacion,
			@RequestParam(required=false) String empresa,
			@RequestParam(required=false) String item,
			Pageable pageable) {
		logger.info("buscarPropuestasProfesionales");			
		return propuestaProfesionalService.listarPropuestasProfesionales(fechaDesde,fechaHasta,idSector,idSubsector,nroProceso, nombreProceso,idEstadoItem,idEstadoInvitacion,empresa,item,pageable,getContexto());
	}
	
	@GetMapping("/propuestasProfesionales/listar/{propuestaUuid}")
	@Raml("propuestasProfesionales.listar.properties")
	public Page<PropuestaProfesional> buscarPropuestasProfesionales(@PathVariable String propuestaUuid,Pageable pageable) {
		logger.info("buscarPropuestasProfesionales");			
		return propuestaProfesionalService.listarPropuestasProfesionalesXPropuesta(propuestaUuid, pageable,getContexto());
	}
	
	@GetMapping("/propuestasProfesionales/aceptados/{propuestaUuid}")
	@Raml("propuestasProfesionales.listar.properties")
	public Page<PropuestaProfesional> buscarProfesionalesAceptados(@PathVariable String propuestaUuid,Pageable pageable) {
		logger.info("buscarPropuestasProfesionales");			
		return propuestaProfesionalService.listarProfesionalesAceptados(propuestaUuid, pageable,getContexto());
	}
	
	@DeleteMapping("/propuestasProfesionales/{id}")
	public void eliminar(@PathVariable Long  id,@RequestParam(required=false) String propuestaUuid){
		logger.info("eliminar {} ",id);
		propuestaProfesionalService.eliminar(id,propuestaUuid,getContexto());
	}
	
	@PutMapping("/propuestasProfesionales/{id}/aceptar")
	@Raml("propuestasProfesionales.obtener.properties")
	public PropuestaProfesional aceptarInvitacion(@PathVariable Long id,@RequestBody PropuestaProfesional propuestaProfesional,@RequestParam(required=false) String propuestaUuid) {
		logger.info("responderInvitacion");
		return propuestaProfesionalService.aceptarInvitacion(id,propuestaProfesional,propuestaUuid,getContexto());
	}
	
	@PutMapping("/propuestasProfesionales/{id}/cancelar")
	@Raml("propuestasProfesionales.obtener.properties")
	public PropuestaProfesional cancelarInvitacion(@PathVariable Long id,@RequestBody PropuestaProfesional propuestaProfesional,@RequestParam(required=false) String propuestaUuid) {
		logger.info("responderInvitacion");
		return propuestaProfesionalService.cancelarInvitacion(id,propuestaProfesional,propuestaUuid,getContexto());
	}
	
	@PutMapping("/propuestasProfesionales/{id}/rechazar")
	@Raml("propuestasProfesionales.obtener.properties")
	public PropuestaProfesional rechazarInvitacion(@PathVariable Long id,@RequestBody PropuestaProfesional propuestaProfesional,@RequestParam(required=false) String propuestaUuid) {
		logger.info("responderInvitacion");
		return propuestaProfesionalService.rechazarInvitacion(id,propuestaProfesional,propuestaUuid,getContexto());
	}

	
	@GetMapping("/propuestasProfesionales/{id}")
	@Raml("propuestasProfesionales.obtener.properties")
	public PropuestaProfesional obtener(@PathVariable Long id,@RequestParam(required=false) String propuestaUuid){
		logger.info("obtener {} ",id);
		return propuestaProfesionalService.obtener(id,propuestaUuid,getContexto());
	}
	
	
	@GetMapping("/propuestasProfesionales/{procesoItemUuid}/export")
	public void exportar(HttpServletRequest request,HttpServletResponse response,@PathVariable String procesoItemUuid)throws Exception {
		InputStream is=propuestaProfesionalService.generarExport(procesoItemUuid,getContexto());
		response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "attachment; filename=Reporte_Profesionales.xlsx");
	    IOUtils.copy(is, response.getOutputStream());
	}

}
