package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeRenovacionCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeRenovacionCreateResponseDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.BandejaAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.AprobacionInformeService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl.BandejaAprobacionImplService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import pe.gob.osinergmin.sicoes.util.model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.Collections;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.renovacioncontrato.ResponseBuilder;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import pe.gob.osinergmin.sicoes.util.model.response.ApiResponse;
@RestController
@RequestMapping("/api/renovacion/bandeja")
public class BandejaAprobacionRestController extends BaseRestController {

    private final Logger logger = LogManager.getLogger(BandejaAprobacionRestController.class);

    private final BandejaAprobacionImplService bandejaAprobacionService;

    @Autowired
    public BandejaAprobacionRestController(BandejaAprobacionImplService bandejaAprobacionService) {
        this.bandejaAprobacionService = bandejaAprobacionService;
    }

    @Autowired
    private AprobacionInformeService aprobacionInformeService;

    @PostMapping("/aprobar-informe-renovacion")
    public ResponseEntity<ApiResponse> aprobarInformeRenovacion(
            @RequestBody AprobacionInformeRenovacionCreateRequestDTO requestDTO) {
        
        logger.info("Iniciando aprobación G1 con {} requerimientos para aprobar", 
                   requestDTO.getIdRequerimientosAprobacion() != null ? 
                   requestDTO.getIdRequerimientosAprobacion().size() : 0);
        
        ApiResponse apiResponse = new ApiResponse();
        
        try {
            // Obtener contexto autenticado
            Contexto contexto = getContexto();
            
            // Procesar aprobación G1
            AprobacionInformeRenovacionCreateResponseDTO response = aprobacionInformeService.aprobarInformeRenovacion(requestDTO, contexto);
            
            logger.info("Aprobación G1 completada exitosamente. Notificación enviada a G2.");
            
            return ResponseBuilder.buildResponse(
                apiResponse, 
                "SUCCESS", 
                200, 
                "Aprobación G1 de informe de renovación procesada exitosamente. Notificación enviada al aprobador G2.", 
                HttpStatus.OK, 
                Arrays.asList(Collections.singletonMap("aprobacion", response))
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

            return ResponseBuilder.buildErrorResponse(
                apiResponse, 
                "INTERNAL_ERROR", 
                500, 
                "Error interno del servidor: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/aprobaciones")
    public Page<BandejaAprobacionResponseDTO> listarAprobaciones(
            @RequestParam(required = false) String numeroExpediente,
            @RequestParam(required = false) Long estadoAprobacionInforme,
            @RequestParam(required = false) Long idContratista,
            Pageable pageable) {

        logger.info("get listarInformes: {} {} {}", numeroExpediente, estadoAprobacionInforme, idContratista);

        Contexto contexto = getContexto();
        return bandejaAprobacionService.listaApobaciones(

                numeroExpediente, estadoAprobacionInforme, idContratista, contexto, pageable);
    }
}
