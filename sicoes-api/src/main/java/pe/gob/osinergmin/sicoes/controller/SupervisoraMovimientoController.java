package pe.gob.osinergmin.sicoes.controller;
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
import pe.gob.osinergmin.sicoes.model.SupervisoraMovimiento;
import pe.gob.osinergmin.sicoes.service.SupervisoraMovimientoService;
import pe.gob.osinergmin.sicoes.util.Raml;



@RestController
@RequestMapping("/api")
public class SupervisoraMovimientoController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(SupervisoraMovimientoController.class);
	
	@Autowired
	private SupervisoraMovimientoService supervisoraMovimientoService;
	
	@PostMapping("/movimientos")
	@Raml("supervisoraMovimiento.obtener.properties")
	public SupervisoraMovimiento registrar(@RequestBody SupervisoraMovimiento supervisoraMovimiento) {
		logger.info("registrarSupervisoraMovimientos {} ",supervisoraMovimiento);
		 return supervisoraMovimientoService.desbloquear(supervisoraMovimiento,getContexto());
	}
	
	@GetMapping("/movimientos")
	@Raml("supervisoraMovimiento.listar.properties")
	public Page<SupervisoraMovimiento> buscar(
			@RequestParam(required=false) Long idSector,
			@RequestParam(required=false) Long idSubsector,
			@RequestParam(required=false) Long idEstadoItem,
			@RequestParam(required=false) String item,
			@RequestParam(required=false) String nombreProceso,
			@RequestParam(required=false) String ruc,
			Pageable pageable) {
		logger.info("listarSupervisoraMovimientos");			
		return supervisoraMovimientoService.listarMovimientos(idSector,idSubsector,idEstadoItem,item,nombreProceso,ruc,pageable,getContexto());
	}
	
	@GetMapping("/movimientos/{idSupervisora}/historial")
	@Raml("supervisoraMovimiento.listar.properties")
	public Page<SupervisoraMovimiento> buscarHistorial(@PathVariable Long  idSupervisora ,
			@RequestParam(required=true) Long idSector,
			@RequestParam(required=true) Long idSubsector,
			Pageable pageable) {
		logger.info("listarHistorial");			
		return supervisoraMovimientoService.listarHistorial(idSupervisora,idSector,idSubsector,pageable,getContexto());
	}
	
}
