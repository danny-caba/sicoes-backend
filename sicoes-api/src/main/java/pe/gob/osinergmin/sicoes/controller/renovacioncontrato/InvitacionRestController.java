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
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InvitacionService;
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
    public ResponseEntity<ApiResponse> registrarInvitacion(@RequestBody InvitacionCreateRequestDTO request) {
        logger.info("registrarInvitacion - Request: {}", request);
        ApiResponse apiResponse = new ApiResponse();
        List<String> log=new ArrayList<>();
        try {
            InvitacionCreateResponseDTO response=invitacionService.registrarInvitacion(request,log);
            return ResponseBuilder.buildResponse(apiResponse, "SUCCESS", 201, "Se encontro registro la Invitacion", HttpStatus.CREATED, Arrays.asList(Collections.singletonMap("invitacion", response)));
        } catch (DataNotFoundException ex) {
            return ResponseBuilder.buildErrorResponse(apiResponse, "NOT_FOUND", 404, ex.getMessage(), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseBuilder.buildErrorResponse(apiResponse, "ERROR", 508, "Service Unavailable:" + e.getMessage()+" - "+log.toString(), HttpStatus.OK);
        }
    }
}
