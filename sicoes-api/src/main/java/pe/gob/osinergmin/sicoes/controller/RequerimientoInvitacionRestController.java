package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import pe.gob.osinergmin.sicoes.service.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/invitaciones")
@Validated
public class RequerimientoInvitacionRestController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(RequerimientoInvitacionRestController.class);

    @Autowired
    private RequerimientoInvitacionService requerimientoInvitacionService;

    @GetMapping
    @Raml("requerimientoInvitacion.obtener.properties")
    public Page<RequerimientoInvitacion> obtener(
            @RequestParam(required = false) Long idEstado,
            @RequestParam(required = false) String fechaInicioInvitacion,
            @RequestParam(required = false) String fechaFinInvitacion,
            @RequestParam(required = false) String requerimientoUuid,
            Pageable pageable) {
        logger.info("Obtener lista de invitaciones");
        return requerimientoInvitacionService.obtener(idEstado, fechaInicioInvitacion, fechaFinInvitacion, requerimientoUuid,   getContexto(), pageable);
    }

    @PostMapping
    @Raml("invitacion.guardar.properties")
    public RequerimientoInvitacion guardarRequerimientoInvitacion(@RequestBody RequerimientoInvitacion requerimientoInvitacion) {
        return requerimientoInvitacionService.guardar(requerimientoInvitacion, getContexto());
    }

    @DeleteMapping("/{uuid}/eliminar")
    @Raml("requerimientoInvitacion.eliminar.properties")
    public RequerimientoInvitacion eliminarRequerimientoInvitacion(@PathVariable("uuid") String uuid) {
        logger.info("Eliminando invitación con uuid: {}", uuid);
        return requerimientoInvitacionService.eliminar(uuid, getContexto());
    }

    @PatchMapping("/{uuid}/evaluar")
    @Raml("requerimiento.obtener.properties")
    public Requerimiento evaluarInvitacion(
            @PathVariable String  uuid,
            @RequestBody ListadoDetalleDTO estado) {
        logger.info("Evaluacion invitacion con uuid: {}", uuid);
        return requerimientoInvitacionService.evaluar(uuid, estado, getContexto());
    }
}
