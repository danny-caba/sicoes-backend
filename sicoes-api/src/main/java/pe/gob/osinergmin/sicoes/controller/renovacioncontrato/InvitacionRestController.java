package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionCreateResponseDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InvitacionService;

@RestController
@RequestMapping("/api/invitaciones")
public class InvitacionRestController extends BaseRestController {

    private Logger logger = LogManager.getLogger(InvitacionRestController.class);

    @Autowired
    private InvitacionService invitacionService;

    @PostMapping("/registrar")
    public InvitacionCreateResponseDTO registrarInvitacion(@RequestBody InvitacionCreateRequestDTO request) {
        logger.info("registrarInvitacion - Request: {}", request);
        try {
            return invitacionService.registrarInvitacion(request);
        } catch (Exception e) {
            logger.error("Error al registrar invitación", e);
            throw new RuntimeException(e);
        }
    }
}
