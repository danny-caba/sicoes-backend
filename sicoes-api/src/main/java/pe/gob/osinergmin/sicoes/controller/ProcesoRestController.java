package pe.gob.osinergmin.sicoes.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.osinergmin.sicoes.model.Proceso;
import pe.gob.osinergmin.sicoes.service.ProcesoService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api")
public class ProcesoRestController extends BaseRestController {

	private Logger logger = LogManager.getLogger(ProcesoRestController.class);

	@Autowired
	private ProcesoService procesoService;

	@PostMapping("/procesos")
	@Raml("proceso.obtener.properties")
	public Proceso registrar(@RequestBody Proceso proceso) {
		logger.info("registrarProceso {} ", proceso);
		return procesoService.guardar(proceso, getContexto());
	}

	@PutMapping("/procesos/{procesoUuid}/publicar")
	@Raml("proceso.obtener.properties")
	public Proceso publicar(@PathVariable String procesoUuid, @RequestBody Proceso proceso) {
		logger.info("publicarProceso");
		return procesoService.publicar(procesoUuid, proceso, getContexto());
	}

	@PutMapping("/procesos/{procesoUuid}")
	@Raml("proceso.obtener.properties")
	public Proceso modificar(@PathVariable String procesoUuid, @RequestBody Proceso proceso) {
		logger.info("modificarProceso {} ", proceso);
		proceso.setProcesoUuid(procesoUuid);
		return procesoService.guardar(proceso, getContexto());
	}

	@GetMapping("/procesos/listar")
	@Raml("proceso.listar.properties")
	public Page<Proceso> buscarProcesos(@RequestParam(required = false) String fechaDesde,
			@RequestParam(required = false) String fechaHasta, @RequestParam(required = false) Long idEstado,
			@RequestParam(required = false) Long idSector, @RequestParam(required = false) Long idSubsector,
			@RequestParam(required = false) String nroProceso, @RequestParam(required = false) String nombreProceso,
			@RequestParam(required = false) String nroExpediente, Pageable pageable) {
		logger.info("buscarProcesos");
		if (getContexto().getUsuario().isRol(Constantes.ROLES.USUARIO_EXTERNO)) {
			return null;
		}
		return procesoService.listarProcesos(fechaDesde, fechaHasta, idEstado, idSector, idSubsector, nroProceso,
				nombreProceso, nroExpediente, pageable, getContexto());
	}

	@GetMapping("/procesos/{procesoUuid}")
	@Raml("proceso.obtener.properties")
	public Proceso obtener(@PathVariable String procesoUuid) {
		logger.info("obtener {} ", procesoUuid);
		return procesoService.obtener(procesoUuid, getContexto());
	}

	@GetMapping("/procesos/validacion-profesionales/{procesoUuid}")
	public void validacionVerProfesionales(@PathVariable String procesoUuid) {
		logger.info("validaciones {} ", procesoUuid);
		procesoService.validacionVerProfesionales(procesoUuid, getContexto());
	}

	@GetMapping("/procesos/validacion-usuario/{procesoUuid}/{procesoItemUuid}")
	public List<String> validacionXUsuario(@PathVariable String procesoUuid, @PathVariable String procesoItemUuid) {
		logger.info("validaciones {} ", procesoUuid);
		return procesoService.validacionXUsuario(procesoUuid, procesoItemUuid, getContexto());
	}

}
