package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.EliminarInvitacionDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RenovacionesService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/renovaciones")
public class RenovacionesController extends BaseRestController {

    private Logger logger = LogManager.getLogger(RenovacionesController.class);

    @Autowired
    private RenovacionesService renovacionesService;

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

    @GetMapping("/invitaciones")
    @Raml("renovacioncontrato.invitaciones.listar.properties")
    public ResponseEntity<Page<InvitacionResponseDTO>> listarInvitaciones(
            @RequestParam(required = false) String numeroExpediente,
            @RequestParam(required = false) String nombreItem,
            @RequestParam(required = false) Integer estadoInvitacion,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            Pageable pageable) {
        
        logger.info("listarInvitaciones - Evaluar Invitación - Usuario: {}", 
                    getContexto().getUsuario().getIdUsuario());
        
        try {
            Page<InvitacionResponseDTO> invitaciones = renovacionesService.listarInvitaciones(
                    numeroExpediente, nombreItem, estadoInvitacion, 
                    fechaDesde, fechaHasta, pageable, getContexto());
            
            return new ResponseEntity<>(invitaciones, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al listar invitaciones", e);
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
}