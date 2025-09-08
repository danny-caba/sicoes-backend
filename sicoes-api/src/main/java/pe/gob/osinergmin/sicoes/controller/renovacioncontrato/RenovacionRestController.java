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
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoRenovacionListDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
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
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/requerimientoPorNuExpediente/{nuExpediente}")
    public RequerimientoRenovacion obtener(
            @PathVariable("nuExpediente") String nuExpediente) {
        logger.info("obtener{}", nuExpediente);
        return requerimientoRenovacionService.obtener(nuExpediente,getContexto());
    }

    @GetMapping("/invitaciones")
    //@Raml("renovacioncontrato.invitaciones.listar.properties")
    public ResponseEntity<Page<InvitacionResponseDTO>> listarInvitaciones(
            @RequestParam(required = false) String numeroExpediente,
            @RequestParam(required = false) String nombreItem,
            @RequestParam(required = false) Integer estadoInvitacion,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            Pageable pageable) {

        logger.info("listarInvitaciones - Evaluar Invitación - Usuario: {}",
                getContexto().getUsuario().getIdUsuario());

        try {
            Page<InvitacionResponseDTO> invitaciones = renovacionesService.listarInvitaciones(
                    numeroExpediente, nombreItem, estadoInvitacion,
                    fechaDesde, fechaHasta, pageable, getContexto());

            return new ResponseEntity<>(invitaciones, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al listar invitaciones", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
