package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoRenovacionListDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InvitacionService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RenovacionesService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RequerimientoRenovacionService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/renovaciones")
public class RenovacionRestController extends BaseRestController {

	private Logger logger = LogManager.getLogger(RenovacionRestController.class);
	
	@Autowired
    RequerimientoRenovacionService requerimientoRenovacionService;

    @Autowired
    private RenovacionesService renovacionesService;

    @Autowired
    private InvitacionService invitacionService;

    @GetMapping("/requerimientos")
    public Page<RequerimientoRenovacionListDTO> buscar(
            @RequestParam(required = false) String idSolicitud,
            @RequestParam(required = false) String nuExpediente,
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String subSector,
            Pageable pageable) {

        logger.info("buscar{} {} {} {}", idSolicitud,nuExpediente,sector,subSector);
        return requerimientoRenovacionService.buscar(idSolicitud,nuExpediente,sector,subSector,pageable,getContexto());
    }

	@PostMapping("/requerimiento")
	public RequerimientoRenovacion registrar(@RequestBody RequerimientoRenovacion requerimientoRenovacion) {
		logger.info("registrar {} ", requerimientoRenovacion);
        try {
            return requerimientoRenovacionService.guardar(requerimientoRenovacion,getContexto());
        } catch (Exception e) {
            logger.error("Error al registrar requerimiento de renovación", e);
            throw new RuntimeException("Error al registrar requerimiento: " + e.getMessage(), e);
        }
    }

    @GetMapping("/requerimientoPorNuExpediente/{nuExpediente}")
    public RequerimientoRenovacion obtener(
            @PathVariable("nuExpediente") String nuExpediente) {
        logger.info("obtener{}", nuExpediente);
        return requerimientoRenovacionService.obtener(nuExpediente,getContexto());
    }

    @GetMapping("/invitaciones")
    @Raml("renovacioncontrato.invitaciones.listar.properties")
    public Page<RequerimientoInvitacion> listarInvitaciones(
            @RequestParam(required = false) String numeroExpediente,
            @RequestParam(required = false) String nombreItem,
            @RequestParam(required = false) Integer estadoInvitacion,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            Pageable pageable) {

        logger.info("listarInvitaciones...",
                getContexto().getUsuario().getIdUsuario());

        return renovacionesService.listarInvitaciones(
                    numeroExpediente, nombreItem, estadoInvitacion,
                    fechaDesde, fechaHasta, pageable, getContexto());
    }

    @PostMapping("/invitaciones")
    @Raml("renovacioncontrato.invitacion.obtener.properties")
    public RequerimientoInvitacion registrarInvitacion(@RequestBody InvitacionCreateRequestDTO request) {
        logger.info("registrarInvitacion - Request: {}", request);
        return invitacionService.registrarInvitacion(request, getContexto());
    }

    @PostMapping("/invitaciones/eliminar")
    public void eliminarInvitacion(@RequestBody InvitacionRequestDTO request) {
        logger.info("eliminarInvitacion {}", request);
        invitacionService.eliminarInvitacion(request, getContexto());
    }

}
