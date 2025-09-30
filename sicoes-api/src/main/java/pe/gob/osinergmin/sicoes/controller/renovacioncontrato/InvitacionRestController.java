package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionCreateResponseDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InvitacionService;
import pe.gob.osinergmin.sicoes.util.Raml;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import pe.gob.osinergmin.sicoes.util.model.response.ApiResponse;
import pe.gob.osinergmin.sicoes.util.renovacioncontrato.ResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/invitaciones")
public class InvitacionRestController extends BaseRestController {

    private Logger logger = LogManager.getLogger(InvitacionRestController.class);

    @Autowired
    private InvitacionService invitacionService;

    @PostMapping("/registrar")
    @Raml("renovacioncontrato.invitacion.obtener.properties")
    public RequerimientoInvitacion registrarInvitacion(@RequestBody InvitacionCreateRequestDTO request) {
        logger.info("registrarInvitacion - Request: {}", request);
            return invitacionService.registrarInvitacion(request, getContexto());
    }
}
