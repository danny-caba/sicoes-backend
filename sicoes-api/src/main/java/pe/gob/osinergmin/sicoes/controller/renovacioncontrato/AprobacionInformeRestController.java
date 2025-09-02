package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeCreateRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.AprobacionInformeCreateResponseDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.AprobacionInformeService;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.renovacioncontrato.ResponseBuilder;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import pe.gob.osinergmin.sicoes.util.model.response.ApiResponse;

import java.util.Arrays;
import java.util.Collections;

/**
 * Controlador REST para la gestión de aprobaciones de informes de renovación de contrato.
 * 
 * Este controlador maneja los diferentes niveles de aprobación del flujo de renovación de contrato:
 * - G1: Aprobación de Jefe de Unidad
 * - G2: Aprobación de Gerente de División  
 * - GPPM G3: Aprobación de Gerente de Procesos de Proyectos y Mantenimiento
 * - GSE G3: Aprobación final de Gerencia de Supervisión de Electricidad
 * 
 * Cada endpoint valida permisos, procesa la lógica de negocio y envía notificaciones correspondientes.
 * Todos los métodos implementan ResponseEntity<ApiResponse> para manejo estandarizado de respuestas.
 * 
 * @author Sistema SICOES
 * @version 1.0
 */
@RestController
@RequestMapping("/api/informe/renovacion/aprobacion-informe")
public class AprobacionInformeRestController extends BaseRestController {

    /**
     * Logger para el registro de eventos y errores del controlador.
     */
    private static final Logger logger = LogManager.getLogger(AprobacionInformeRestController.class);

    /**
     * Servicio para la lógica de aprobación de informes.
     */
    @Autowired
    private AprobacionInformeService aprobacionInformeService;

    /**
     * Endpoint para aprobar informe de renovación G1 (Jefe de Unidad).
     * 
     * Este método procesa la aprobación del primer nivel (G1) en el flujo de renovación de contrato.
     * Valida el usuario aprobador, actualiza el estado a "APROBADO" y crea el siguiente nivel G2.
     * Envía notificación al aprobador G2 para continuar el flujo.
     * 
     * @param requestDTO DTO con datos de la aprobación que incluye:
     *                   - idUsuario: ID del usuario que aprueba (obligatorio)
     *                   - observacion: Comentarios de la aprobación (obligatorio, máx 500 caracteres)
     *                   - informes: Lista de informes a aprobar (obligatorio)
     * @return ResponseEntity con ApiResponse conteniendo el resultado de la aprobación
     * 
     * Códigos de respuesta:
     * - 200: Aprobación G1 exitosa, notificación enviada a G2
     * - 400: Datos de entrada inválidos (usuario, observación o informes faltantes)
     * - 404: Usuario no autorizado como aprobador G1 o informe no encontrado
     * - 500: Error interno del servidor
     */
    @PostMapping("/aprobar-informe-renovacion-g1")
    public ResponseEntity<ApiResponse> aprobarInformeRenovacionG1(
            @RequestBody AprobacionInformeCreateRequestDTO requestDTO) {
        
        logger.info("Iniciando aprobación G1 para usuario: {} con {} informes", 
                   requestDTO.getIdUsuario(), 
                   requestDTO.getInformes() != null ? requestDTO.getInformes().size() : 0);
        
        ApiResponse apiResponse = new ApiResponse();
        
        try {
            // Obtener contexto autenticado
            Contexto contexto = getContexto();
            
            // Procesar aprobación G1
            AprobacionInformeCreateResponseDTO response = aprobacionInformeService.aprobarInformeRenovacionG1(requestDTO, contexto);
            
            logger.info("Aprobación G1 completada exitosamente para usuario: {}. Notificación enviada a G2.", requestDTO.getIdUsuario());
            
            return ResponseBuilder.buildResponse(
                apiResponse, 
                "SUCCESS", 
                200, 
                "Aprobación G1 de informe de renovación procesada exitosamente. Notificación enviada al aprobador G2.", 
                HttpStatus.OK, 
                Arrays.asList(Collections.singletonMap("aprobacion", response))
            );
            
        } catch (DataNotFoundException ex) {
            logger.warn("Error de validación en aprobación G1 para usuario {}: {}", requestDTO.getIdUsuario(), ex.getMessage());
            return ResponseBuilder.buildErrorResponse(
                apiResponse, 
                "VALIDATION_ERROR", 
                400, 
                ex.getMessage(), 
                HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            logger.error("Error interno en aprobación G1 para usuario: {}", requestDTO.getIdUsuario(), e);
            return ResponseBuilder.buildErrorResponse(
                apiResponse, 
                "INTERNAL_ERROR", 
                500, 
                "Error interno del servidor: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /**
     * Endpoint para aprobar informe de renovación G2 (Gerente de División).
     * 
     * Este método procesa la aprobación del segundo nivel (G2) en el flujo de renovación de contrato.
     * Valida el usuario aprobador G2, actualiza el estado a "APROBADO" y cambia el estado del informe a "CONCLUIDO".
     * Envía notificación al evaluador de contratos para revisión del informe.
     * 
     * @param requestDTO DTO con datos de la aprobación que incluye:
     *                   - idUsuario: ID del usuario que aprueba (debe ser aprobador G2)
     *                   - observacion: Comentarios de la aprobación (obligatorio, máx 500 caracteres)
     *                   - informes: Lista de informes a aprobar (obligatorio)
     * @return ResponseEntity con ApiResponse conteniendo el resultado de la aprobación
     * 
     * Códigos de respuesta:
     * - 200: Aprobación G2 exitosa, informe marcado como concluido
     * - 400: Datos de entrada inválidos
     * - 404: Usuario no autorizado como aprobador G2 o informe no encontrado
     * - 500: Error interno del servidor
     */
    @PostMapping("/aprobar-informe-renovacion-g2")
    public ResponseEntity<ApiResponse> aprobarInformeRenovacionG2(
            @RequestBody AprobacionInformeCreateRequestDTO requestDTO) {
        
        logger.info("Iniciando aprobación G2 para usuario: {} con {} informes", 
                   requestDTO.getIdUsuario(), 
                   requestDTO.getInformes() != null ? requestDTO.getInformes().size() : 0);
        
        ApiResponse apiResponse = new ApiResponse();
        
        try {
            // Obtener contexto autenticado
            Contexto contexto = getContexto();
            
            // Procesar aprobación G2
            AprobacionInformeCreateResponseDTO response = aprobacionInformeService.aprobarInformeRenovacionG2(requestDTO, contexto);
            
            logger.info("Aprobación G2 completada exitosamente para usuario: {}. Informe marcado como concluido.", requestDTO.getIdUsuario());
            
            return ResponseBuilder.buildResponse(
                apiResponse, 
                "SUCCESS", 
                200, 
                "Aprobación G2 de informe de renovación procesada exitosamente. Informe marcado como concluido.", 
                HttpStatus.OK, 
                Arrays.asList(Collections.singletonMap("aprobacion", response))
            );
            
        } catch (DataNotFoundException ex) {
            logger.warn("Error de validación en aprobación G2 para usuario {}: {}", requestDTO.getIdUsuario(), ex.getMessage());
            return ResponseBuilder.buildErrorResponse(
                apiResponse, 
                "VALIDATION_ERROR", 
                400, 
                ex.getMessage(), 
                HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            logger.error("Error interno en aprobación G2 para usuario: {}", requestDTO.getIdUsuario(), e);
            return ResponseBuilder.buildErrorResponse(
                apiResponse, 
                "INTERNAL_ERROR", 
                500, 
                "Error interno del servidor: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /**
     * Endpoint para aprobar informe de renovación GPPM G3 (Gerente de Procesos de Proyectos y Mantenimiento).
     * 
     * Este método procesa la aprobación del tercer nivel GPPM (G3) en el flujo de renovación de contrato.
     * Valida el usuario aprobador G3, actualiza el estado a "APROBADO" y crea el siguiente nivel GSE G3.
     * Envía notificación al aprobador GSE G3 para la evaluación final.
     * 
     * @param requestDTO DTO con datos de la aprobación que incluye:
     *                   - idUsuario: ID del usuario que aprueba (debe ser aprobador GPPM G3)
     *                   - observacion: Comentarios de la aprobación (obligatorio, máx 500 caracteres)
     *                   - informes: Lista de informes a aprobar (obligatorio)
     * @return ResponseEntity con ApiResponse conteniendo el resultado de la aprobación
     * 
     * Códigos de respuesta:
     * - 200: Aprobación GPPM G3 exitosa, flujo avanza a GSE G3
     * - 400: Datos de entrada inválidos
     * - 404: Usuario no autorizado como aprobador GPPM G3 o informe no encontrado
     * - 500: Error interno del servidor
     */
    @PostMapping("/aprobar-informe-renovacion-gppm-g3")
    public ResponseEntity<ApiResponse> aprobarInformeRenovacionGppmG3(
            @RequestBody AprobacionInformeCreateRequestDTO requestDTO) {
        
        logger.info("Iniciando aprobación GPPM G3 para usuario: {} con {} informes", 
                   requestDTO.getIdUsuario(), 
                   requestDTO.getInformes() != null ? requestDTO.getInformes().size() : 0);
        
        ApiResponse apiResponse = new ApiResponse();
        
        try {
            // Obtener contexto autenticado
            Contexto contexto = getContexto();
            
            // Procesar aprobación GPPM G3
            AprobacionInformeCreateResponseDTO response = aprobacionInformeService.aprobarInformeRenovacionGppmG3(requestDTO, contexto);
            
            logger.info("Aprobación GPPM G3 completada exitosamente para usuario: {}. Notificación enviada a GSE G3.", requestDTO.getIdUsuario());
            
            return ResponseBuilder.buildResponse(
                apiResponse, 
                "SUCCESS", 
                200, 
                "Aprobación GPPM G3 de informe de renovación procesada exitosamente. Flujo avanza a evaluación GSE G3.", 
                HttpStatus.OK, 
                Arrays.asList(Collections.singletonMap("aprobacion", response))
            );
            
        } catch (DataNotFoundException ex) {
            logger.warn("Error de validación en aprobación GPPM G3 para usuario {}: {}", requestDTO.getIdUsuario(), ex.getMessage());
            return ResponseBuilder.buildErrorResponse(
                apiResponse, 
                "VALIDATION_ERROR", 
                400, 
                ex.getMessage(), 
                HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            logger.error("Error interno en aprobación GPPM G3 para usuario: {}", requestDTO.getIdUsuario(), e);
            return ResponseBuilder.buildErrorResponse(
                apiResponse, 
                "INTERNAL_ERROR", 
                500, 
                "Error interno del servidor: " + e.getMessage(), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    /**
     * Endpoint para aprobar informe de renovación GSE G3 (Gerencia de Supervisión de Electricidad).
     * 
     * Este método procesa la aprobación final (GSE G3) en el flujo de renovación de contrato.
     * Es el último nivel de aprobación que finaliza el proceso completamente.
     * Actualiza el estado del requerimiento de renovación a "CONCLUIDO" y envía notificación 
     * de solicitud de contratos al usuario evaluador.
     * 
     * @param requestDTO DTO con datos de la aprobación que incluye:
     *                   - idUsuario: ID del usuario que aprueba (debe ser aprobador GSE G3)
     *                   - observacion: Comentarios finales de la aprobación (obligatorio, máx 500 caracteres)
     *                   - informes: Lista de informes a aprobar finalmente (obligatorio)
     * @return ResponseEntity con ApiResponse conteniendo el resultado de la aprobación final
     * 
     * Códigos de respuesta:
     * - 200: Aprobación final GSE G3 exitosa, proceso de renovación completamente concluido
     * - 400: Datos de entrada inválidos
     * - 404: Usuario no autorizado como aprobador GSE G3 o informe no encontrado
     * - 500: Error interno del servidor
     */
    @PostMapping("/aprobar-informe-renovacion-gse-g3")
    public ResponseEntity<ApiResponse> aprobarInformeRenovacionGseG3(
            @RequestBody AprobacionInformeCreateRequestDTO requestDTO) {
        
        logger.info("Iniciando aprobación final GSE G3 para usuario: {} con {} informes", 
                   requestDTO.getIdUsuario(), 
                   requestDTO.getInformes() != null ? requestDTO.getInformes().size() : 0);
        
        ApiResponse apiResponse = new ApiResponse();
        
        try {
            // Obtener contexto autenticado
            Contexto contexto = getContexto();
            
            // Procesar aprobación final GSE G3
            AprobacionInformeCreateResponseDTO response = aprobacionInformeService.aprobarInformeRenovacionGseG3(requestDTO, contexto);
            
            logger.info("Aprobación final GSE G3 completada exitosamente para usuario: {}. Proceso de renovación de contrato completamente concluido.", requestDTO.getIdUsuario());
            
            return ResponseBuilder.buildResponse(
                apiResponse, 
                "SUCCESS", 
                200, 
                "Aprobación final GSE G3 completada exitosamente. Proceso de renovación de contrato completamente concluido. Notificación de solicitud de contratos enviada.", 
                HttpStatus.OK, 
                Arrays.asList(Collections.singletonMap("aprobacion", response))
            );
            
        } catch (DataNotFoundException ex) {
            logger.warn("Error de validación en aprobación final GSE G3 para usuario {}: {}", requestDTO.getIdUsuario(), ex.getMessage());
            return ResponseBuilder.buildErrorResponse(
                apiResponse, 
                "VALIDATION_ERROR", 
                400, 
                ex.getMessage(), 
                HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            logger.error("Error interno en aprobación final GSE G3 para usuario: {}", requestDTO.getIdUsuario(), e);
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