package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformePresupuestalCreateRequest;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformePresupuestalCreateResponse;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformePresupuestalService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import pe.gob.osinergmin.sicoes.util.model.response.ApiResponse;
import pe.gob.osinergmin.sicoes.util.renovacioncontrato.ResponseBuilder;
import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/api/informe/renovacion/presupuestal")
public class InformePresupuestalRestController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(InformePresupuestalRestController.class);

    @Autowired
    private InformePresupuestalService informePresupuestalService;

    @PostMapping("/cargar")
    public ResponseEntity<ApiResponse> cargar(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idRequerimientoAprobacion") Long idRequerimientoAprobacion) {
        
        logger.info("Iniciando carga de informe presupuestal");
        ApiResponse apiResponse = new ApiResponse();
        
        try {
            InformePresupuestalCreateResponse response = informePresupuestalService.uploadDocument(
                    idRequerimientoAprobacion,
                    file,
                    getContexto());
            
            logger.info("Carga de informe presupuestal completada exitosamente");
            
            return ResponseBuilder.buildResponse(
                apiResponse,
                "SUCCESS",
                200,
                "Informe presupuestal cargado exitosamente",
                HttpStatus.OK,
                Arrays.asList(Collections.singletonMap("informePresupuestal", response))
            );
            
        } catch (DataNotFoundException ex) {
            return ResponseBuilder.buildErrorResponse(
                apiResponse,
                "VALIDATION_ERROR",
                400,
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
            );
            
        } catch (Exception e) {
            logger.error("Error al cargar informe presupuestal", e);
            return ResponseBuilder.buildErrorResponse(
                apiResponse,
                "INTERNAL_ERROR",
                500,
                "Error interno del servidor: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/anular")
    public ResponseEntity<ApiResponse> anular(
            @RequestParam("idRequerimientoAprobacion") Long idRequerimientoAprobacion) {
        
        logger.info("Iniciando anulación de informe presupuestal");
        ApiResponse apiResponse = new ApiResponse();
        
        try {
            InformePresupuestalCreateResponse response = informePresupuestalService.anularDocumentoSiged(
                    idRequerimientoAprobacion,
                    getContexto());
            
            logger.info("Anulación de informe presupuestal completada exitosamente");
            
            return ResponseBuilder.buildResponse(
                apiResponse,
                "SUCCESS",
                200,
                "Informe presupuestal anulado exitosamente",
                HttpStatus.OK,
                Arrays.asList(Collections.singletonMap("informePresupuestal", response))
            );
            
        } catch (DataNotFoundException ex) {
            return ResponseBuilder.buildErrorResponse(
                apiResponse,
                "VALIDATION_ERROR",
                400,
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
            );
            
        } catch (Exception e) {
            logger.error("Error al anular informe presupuestal", e);
            return ResponseBuilder.buildErrorResponse(
                apiResponse,
                "INTERNAL_ERROR",
                500,
                "Error interno del servidor: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
