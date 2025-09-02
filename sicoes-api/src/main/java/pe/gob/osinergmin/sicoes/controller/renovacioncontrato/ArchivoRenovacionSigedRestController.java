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
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.ArchivoRenovacionSigedRequest;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.ArchivoRenovacionSigedResponse;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.ArchivoRenovacionSigedService;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import pe.gob.osinergmin.sicoes.util.model.response.ApiResponse;
import pe.gob.osinergmin.sicoes.util.renovacioncontrato.ResponseBuilder;

import java.util.Arrays;
import java.util.Collections;

/**
 * Controlador REST para la gestión de archivos SIGED en renovación de contrato.
 * 
 * Este controlador maneja las operaciones de adjuntar y anular documentos
 * asociados a informes de renovación de contrato en el sistema SIGED.
 * 
 * @author Sistema SICOES
 * @version 1.0
 */
@RestController
@RequestMapping("/api/informe/renovacion/archivo")
public class ArchivoRenovacionSigedRestController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(ArchivoRenovacionSigedRestController.class);

    @Autowired
    private ArchivoRenovacionSigedService archivoRenovacionSigedService;

    @PostMapping("/adjuntar")
    public ResponseEntity<ApiResponse> adjuntarArchivo(
            @RequestParam("idInformeRenovacion") Long idInformeRenovacion,
            @RequestParam("file") MultipartFile file) {
        
        logger.info("Adjuntando archivo para informe de renovación: {}", idInformeRenovacion);
        ApiResponse apiResponse = new ApiResponse();
        
        try {
            ArchivoRenovacionSigedRequest request = new ArchivoRenovacionSigedRequest();
            request.setIdInformeRenovacion(idInformeRenovacion);
            
            ArchivoRenovacionSigedResponse response = archivoRenovacionSigedService.adjuntarArchivo(request, file);
            
            return ResponseBuilder.buildResponse(
                apiResponse, 
                "SUCCESS", 
                200, 
                "Archivo adjuntado exitosamente", 
                HttpStatus.OK, 
                Arrays.asList(Collections.singletonMap("archivo", response))
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
            logger.error("Error al adjuntar archivo para informe: {}", idInformeRenovacion, e);
            return ResponseBuilder.buildErrorResponse(
                apiResponse, 
                "ERROR", 
                500, 
                "Error interno del servidor: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/anular")
    public ResponseEntity<ApiResponse> anularArchivo(@RequestBody ArchivoRenovacionSigedRequest request) {
        logger.info("Anulando archivo para informe de renovación: {}", request.getIdInformeRenovacion());
        ApiResponse apiResponse = new ApiResponse();
        
        try {
            ArchivoRenovacionSigedResponse response = archivoRenovacionSigedService.anularArchivo(request);
            
            return ResponseBuilder.buildResponse(
                apiResponse, 
                "SUCCESS", 
                200, 
                "Archivo anulado exitosamente", 
                HttpStatus.OK, 
                Arrays.asList(Collections.singletonMap("archivo", response))
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
            logger.error("Error al anular archivo para informe: {}", request.getIdInformeRenovacion(), e);
            return ResponseBuilder.buildErrorResponse(
                apiResponse, 
                "ERROR", 
                500, 
                "Error interno del servidor: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
