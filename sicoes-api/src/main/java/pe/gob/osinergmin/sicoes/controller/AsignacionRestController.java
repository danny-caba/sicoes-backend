package pe.gob.osinergmin.sicoes.controller;

import java.util.List;

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

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.service.AsignacionService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.SolicitudService;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.util.bean.siged.AccessRequestInFirmaDigital;
@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionRestController extends BaseRestController{

	private Logger logger = LogManager.getLogger(AsignacionRestController.class);
	
	@Autowired
	AsignacionService asignacionService;
	
	@Autowired
	SolicitudService solicitudService; 
	
	@Autowired
	NotificacionService notificacionService;
	
	@GetMapping
	@Raml("asignacion.listar.properties")
	public Page<Asignacion> buscar(@RequestParam(required=false) String solicitudUuid,@RequestParam(required=false) String codigoTipoAprobador,Pageable pageable) throws InterruptedException {
		logger.info("buscar {} {}",solicitudUuid, codigoTipoAprobador);	
		Long idSolicitud = solicitudService.obtenerId(solicitudUuid);
		return asignacionService.buscar(idSolicitud,codigoTipoAprobador,pageable,getContexto());
	}


	@GetMapping("/{id}")
	@Raml("asignacion.obtener.properties")
	public Asignacion obtener(@PathVariable Long  id){
		logger.info("obtener {} ",id);
		return asignacionService.obtener(id,getContexto());
	}	
	
	@PostMapping
	public List<Asignacion> registrar(@RequestBody List<Asignacion> asignacion) {
		logger.info("registrar {} ",asignacion);
		 return asignacionService.guardar(asignacion,getContexto());
	}
	
	@PostMapping("/aprobadores")
	public Asignacion registrarAprobador(@RequestBody Asignacion asignacion) {
		logger.info("registrarAprobador {} ",asignacion);
		 return asignacionService.guardarAprobador(asignacion,getContexto());
	}
	
	@PutMapping("/aprobadores/{id}")
	public boolean modificarAprobador(@PathVariable Long  id,@RequestBody Asignacion asignacion) {
		logger.info("modificarAprobador {} ",asignacion);
		asignacion.setIdAsignacion(id);
		return asignacionService.modificarAprobador(asignacion,getContexto());
	}
	
	@DeleteMapping("/aprobadores/{id}")
	public void eliminarAprobador(@PathVariable Long  id) {
		logger.info("eliminarAprobador {} ",id);
		asignacionService.eliminarAprobador(id,getContexto());		
	}
	
	@PutMapping("/{id}")
	@Raml("asignacion.obtener.properties")
	public Asignacion modificar(@PathVariable Long  id,@RequestBody Asignacion asignacion) {
		logger.info("modificar {} {}",id,asignacion);
		asignacion.setIdAsignacion(id);
		return asignacionService.guardar(asignacion,getContexto());
	}

	@GetMapping("/aprobaciones")
	@Raml("asignacion.listar.properties")
	public Page<Asignacion> buscarAprobaciones(@RequestParam(required=false) String solicitudUuid,Pageable pageable) {
		logger.info("buscarAprobaciones {} ",solicitudUuid);			
		return asignacionService.buscarAprobaciones(solicitudUuid,pageable,getContexto());
	}
	
	@GetMapping("/obtenerIdArchivo/{numeroExpediente}")
	public Long obtenerIdArchivo(@PathVariable String numeroExpediente) throws Exception{

		logger.info("obtenerIdArchivo {} ", numeroExpediente);
		
		return asignacionService.obtenerIdArchivo(numeroExpediente, getContexto().getUsuario().getUsuario());
	}
	
	@GetMapping("/obtenerParametros/firmaDigital")
	public AccessRequestInFirmaDigital obtenerParametrosfirmaDigital(@RequestParam(required=false) String usuario) throws Exception{
		
		logger.info("obtenerParametrosfirmaDigital");
		return asignacionService.obtenerParametrosfirmaDigital(usuario, getContexto().getUsuario().getUsuario());
	}
}
