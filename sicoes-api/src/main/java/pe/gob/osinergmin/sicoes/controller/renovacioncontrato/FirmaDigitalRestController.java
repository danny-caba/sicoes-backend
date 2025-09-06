package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.FirmaDigitalRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.FirmaDigitalResponseDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.FirmaDigitalService;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import pe.gob.osinergmin.sicoes.util.model.response.ApiResponse;
import pe.gob.osinergmin.sicoes.util.renovacioncontrato.ResponseBuilder;

import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/api/informe/renovacion/firma-digital")
public class FirmaDigitalRestController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(FirmaDigitalRestController.class);

    @Autowired
    private FirmaDigitalService firmaDigitalService;

    @PostMapping("/obtenerParametros")
    public ResponseEntity<ApiResponse> obtenerParametrosfirmaDigital(@RequestBody FirmaDigitalRequestDTO firmaDigitalRequestDTO) {
        logger.info("Obteniendo parámetros de firma digital para idInformeRenovacion: {}", firmaDigitalRequestDTO.getIdInformeRenovacion());
        ApiResponse apiResponse = new ApiResponse();
        
        try {
            FirmaDigitalResponseDTO response = firmaDigitalService.obtenerParametrosfirmaDigital(firmaDigitalRequestDTO, getContexto());
            return ResponseBuilder.buildResponse(apiResponse, "SUCCESS", 200, "Parámetros de firma digital obtenidos exitosamente", HttpStatus.OK, Arrays.asList(Collections.singletonMap("firmaDigital", response)));
        } catch (DataNotFoundException ex) {
            return ResponseBuilder.buildErrorResponse(apiResponse, "NOT_FOUND", 404, ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error al obtener parámetros de firma digital", e);
            return ResponseBuilder.buildErrorResponse(apiResponse, "ERROR", 500, "Error interno del servidor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
