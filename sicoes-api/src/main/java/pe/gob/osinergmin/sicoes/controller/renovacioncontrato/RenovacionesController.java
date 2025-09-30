package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.EliminarInvitacionDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.EvaluarInvitacionDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RenovacionesService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl.RequerimientoInvitacionServiceTest;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/renovaciones")
public class RenovacionesController extends BaseRestController {

    private Logger logger = LogManager.getLogger(RenovacionesController.class);

    @Autowired
    private RenovacionesService renovacionesService;
    
    @Autowired
    private RequerimientoInvitacionService requerimientoInvitacionService;
    
    @Autowired
    private RequerimientoInvitacionServiceTest testService;

    @GetMapping("/solicitudes")
    @Raml("renovacioncontrato.solicitudes.buscar.properties")
    public ResponseEntity<Page<RequerimientoRenovacion>> buscarSolicitudes(
            @RequestParam(required = false) String numeroExpediente,
            @RequestParam(required = false) String tipoSector,
            @RequestParam(required = false) String tipoSubSector,
            @RequestParam(required = false) String nombreItem,
            @RequestParam(required = false) Integer estadoRequerimiento,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            Pageable pageable) {
        
        logger.info("buscarSolicitudes - Evaluador tecnico Contrato - Acción: Requerimiento de Renovación");
        
        try {
            Page<RequerimientoRenovacion> solicitudes = renovacionesService.buscarSolicitudesRenovacion(
                    numeroExpediente, tipoSector, tipoSubSector, nombreItem, 
                    estadoRequerimiento, fechaDesde, fechaHasta, pageable, getContexto());
            
            return new ResponseEntity<>(solicitudes, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al buscar solicitudes de renovación", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/invitacion/eliminar")
    @Raml("renovacioncontrato.invitacion.eliminar.properties")
    public ResponseEntity<?> eliminarInvitacion(@RequestBody EliminarInvitacionDTO eliminarDTO) {
        
        logger.info("eliminarInvitacion - Usuario: {}, IdInvitacion: {}", 
                    getContexto().getUsuario().getIdUsuario(), eliminarDTO.getIdReqInvitacion());
        
        try {
            renovacionesService.eliminarInvitacion(eliminarDTO, getContexto());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Invitación eliminada exitosamente");
            response.put("idInvitacion", eliminarDTO.getIdReqInvitacion());
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (IllegalArgumentException e) {
            logger.warn("Error de validación al eliminar invitación: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            
        } catch (SecurityException e) {
            logger.warn("Error de seguridad al eliminar invitación: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "No tiene permisos para realizar esta operación");
            return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
            
        } catch (Exception e) {
            logger.error("Error al eliminar invitación", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error interno del servidor");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Versión sin @Raml para testing
    @PostMapping("/invitacion/aceptar-simple")
    public ResponseEntity<?> aceptarInvitacionSimple(@RequestBody EvaluarInvitacionDTO request) {
        try {
            Long idInvitacion = request.getIdReqInvitacion();
            if (idInvitacion == null) {
                throw new IllegalArgumentException("El ID de la invitación es requerido");
            }
            
            RequerimientoInvitacion reqInvitacion = new RequerimientoInvitacion();
            reqInvitacion.setIdReqInvitacion(idInvitacion);
            
            RequerimientoInvitacion invitacionAceptada = requerimientoInvitacionService.aceptar(reqInvitacion, getContexto());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Se ha aceptado la invitación");
            response.put("invitacion", invitacionAceptada);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error: " + e.getMessage());
            errorResponse.put("tipo", e.getClass().getSimpleName());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/invitacion/aceptar")
    @Raml("renovacioncontrato.invitacion.aceptar.properties")
    public ResponseEntity<?> aceptarInvitacion(@RequestBody EvaluarInvitacionDTO request) {
        System.out.println("===== INICIO aceptarInvitacion =====");
        System.out.println("Request DTO recibido: " + request);
        System.out.println("ID Invitación: " + (request != null ? request.getIdReqInvitacion() : "null"));
        
        logger.error("===== INICIO aceptarInvitacion =====");
        logger.error("Request DTO recibido: {}", request);
        logger.error("ID Invitación: {}", request != null ? request.getIdReqInvitacion() : "null");
        
        try {
            System.out.println("Obteniendo contexto...");
            Contexto ctx = null;
            try {
                ctx = getContexto();
                System.out.println("Contexto obtenido: " + (ctx != null));
                if (ctx != null && ctx.getUsuario() != null) {
                    System.out.println("Usuario ID: " + ctx.getUsuario().getIdUsuario());
                }
            } catch (Exception e) {
                System.out.println("Error al obtener contexto: " + e.getMessage());
                e.printStackTrace();
            }
            
            logger.error("Usuario contexto: {}", ctx != null && ctx.getUsuario() != null ? ctx.getUsuario().getIdUsuario() : "null");
            logger.error("Llamando al servicio aceptar...");
            
            // Crear objeto RequerimientoInvitacion con solo el ID
            Long idInvitacion = request.getIdReqInvitacion();
            System.out.println("ID recibido del DTO: " + idInvitacion);
            
            if (idInvitacion == null) {
                System.out.println("ERROR: ID de invitación es null");
                throw new IllegalArgumentException("El ID de la invitación es requerido");
            }
            
            RequerimientoInvitacion reqInvitacion = new RequerimientoInvitacion();
            reqInvitacion.setIdReqInvitacion(idInvitacion);
            
            System.out.println("Llamando al servicio con ID: " + idInvitacion);
            RequerimientoInvitacion invitacionAceptada = requerimientoInvitacionService.aceptar(reqInvitacion, ctx != null ? ctx : getContexto());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Se ha aceptado la invitación");
            response.put("invitacion", invitacionAceptada);
            
            logger.error("Invitación aceptada exitosamente - ID: {}", invitacionAceptada.getIdReqInvitacion());
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al aceptar invitación: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            
        } catch (Exception e) {
            System.out.println("===== ERROR EXCEPTION aceptarInvitacion =====");
            System.out.println("Tipo de excepción: " + e.getClass().getName());
            System.out.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            
            logger.error("===== ERROR EXCEPTION aceptarInvitacion =====", e);
            logger.error("Tipo de excepción: {}", e.getClass().getName());
            logger.error("Mensaje: {}", e.getMessage());
            logger.error("StackTrace: ", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error interno del servidor");
            errorResponse.put("errorDetail", e.getMessage()); // Temporal para debugging
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/invitacion/rechazar")
    @Raml("renovacioncontrato.invitacion.rechazar.properties")
    public ResponseEntity<?> rechazarInvitacion(@RequestBody EvaluarInvitacionDTO request) {
        logger.error("===== INICIO rechazarInvitacion =====");
        logger.error("Request DTO recibido: {}", request);
        logger.error("ID Invitación: {}", request != null ? request.getIdReqInvitacion() : "null");
        
        try {
            logger.error("Usuario contexto: {}", getContexto().getUsuario().getIdUsuario());
            logger.error("Llamando al servicio rechazar...");
            
            // Crear objeto RequerimientoInvitacion con solo el ID
            RequerimientoInvitacion reqInvitacion = new RequerimientoInvitacion();
            reqInvitacion.setIdReqInvitacion(request.getIdReqInvitacion());
            
            RequerimientoInvitacion invitacionRechazada = requerimientoInvitacionService.rechazar(reqInvitacion, getContexto());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Se ha registrado el rechazo a la invitación");
            response.put("invitacion", invitacionRechazada);
            
            logger.error("Invitación rechazada exitosamente - ID: {}", invitacionRechazada.getIdReqInvitacion());
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al rechazar invitación: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            
        } catch (Exception e) {
            logger.error("===== ERROR EXCEPTION rechazarInvitacion =====", e);
            logger.error("Tipo de excepción: {}", e.getClass().getName());
            logger.error("Mensaje: {}", e.getMessage());
            logger.error("StackTrace: ", e);
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error interno del servidor");
            errorResponse.put("errorDetail", e.getMessage()); // Temporal para debugging
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Método de diagnóstico para verificar qué pasa con la invitación
    @GetMapping("/invitacion/diagnostico/{id}")
    public ResponseEntity<?> diagnosticarInvitacion(@PathVariable Long id) {
        Map<String, Object> diagnostico = testService.testBuscarInvitacion(id);
        return new ResponseEntity<>(diagnostico, HttpStatus.OK);
    }
    
    // Método de prueba simple sin dependencias
    @GetMapping("/invitacion/test-simple")
    public ResponseEntity<?> testSimple() {
        System.out.println("===== TEST SIMPLE GET =====");
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Test GET exitoso");
        response.put("timestamp", new Date());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    // Método de prueba temporal para debugging
    @PostMapping("/invitacion/test-aceptar")
    public ResponseEntity<?> testAceptarInvitacion(@RequestBody Map<String, Object> request) {
        System.out.println("===== TEST ACEPTAR - Request Map =====");
        System.out.println("Request completo: " + request);
        
        try {
            Long idReqInvitacion = null;
            if (request.containsKey("idReqInvitacion")) {
                Object idObj = request.get("idReqInvitacion");
                System.out.println("Tipo de idReqInvitacion: " + (idObj != null ? idObj.getClass().getName() : "null"));
                System.out.println("Valor de idReqInvitacion: " + idObj);
                
                if (idObj instanceof Number) {
                    idReqInvitacion = ((Number) idObj).longValue();
                } else if (idObj instanceof String) {
                    idReqInvitacion = Long.parseLong((String) idObj);
                }
            }
            
            // Verificar también idRequerimientoInvitacion
            if (idReqInvitacion == null && request.containsKey("idRequerimientoInvitacion")) {
                Object idObj = request.get("idRequerimientoInvitacion");
                System.out.println("Encontrado idRequerimientoInvitacion: " + idObj);
                
                if (idObj instanceof Number) {
                    idReqInvitacion = ((Number) idObj).longValue();
                } else if (idObj instanceof String) {
                    idReqInvitacion = Long.parseLong((String) idObj);
                }
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Test exitoso");
            response.put("idRecibido", idReqInvitacion);
            response.put("tiposRecibidos", request.keySet());
            
            return new ResponseEntity<>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            System.out.println("ERROR en test: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error en test: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}