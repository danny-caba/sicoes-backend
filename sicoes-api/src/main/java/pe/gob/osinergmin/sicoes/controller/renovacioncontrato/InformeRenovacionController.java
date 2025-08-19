package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RechazoInformeDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.ActualizacionBandejaDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/informe/renovacion")
public class InformeRenovacionController extends BaseRestController {

    private Logger logger = LogManager.getLogger(InformeRenovacionController.class);

    @Autowired
    private InformeRenovacionService informeRenovacionService;

    @PostMapping("/rechazar")
    @Raml("renovacioncontrato.informe.rechazar.properties")
    public ResponseEntity<Map<String, String>> rechazarInformeRenovacion(@RequestBody RechazoInformeDTO rechazoDTO) {
        logger.info("rechazarInformeRenovacion - Aprobador tecnico G1 y Aprobador tecnico G2");
        
        try {
            // Validar datos de entrada
            if (rechazoDTO.getIdInformeRenovacion() == null) {
                return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "El ID del informe de renovación es requerido"));
            }
            
            if (rechazoDTO.getMotivoRechazo() == null || rechazoDTO.getMotivoRechazo().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "El motivo de rechazo es requerido"));
            }
            
            // Procesar el rechazo
            informeRenovacionService.rechazarInforme(rechazoDTO, getContexto());
            
            return ResponseEntity.ok(Collections.singletonMap("message", "Informe de renovación rechazado exitosamente"));
            
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al rechazar informe: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al rechazar informe de renovación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("message", "Error al procesar el rechazo del informe"));
        }
    }

    @PutMapping("/actualizar-bandeja-aprobaciones")
    @Raml("renovacioncontrato.bandeja.aprobaciones.actualizar.properties")
    public ResponseEntity<Map<String, String>> actualizarBandejaAprobaciones(@RequestBody ActualizacionBandejaDTO actualizacionDTO) {
        logger.info("actualizarBandejaAprobaciones - Para actualizar la Bandeja de Aprobaciones");
        
        try {
            // Validar datos de entrada
            if (actualizacionDTO.getIdRequerimientoAprobacion() == null) {
                return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "El ID del requerimiento de aprobación es requerido"));
            }
            
            // Actualizar bandeja de aprobaciones
            informeRenovacionService.actualizarBandejaAprobaciones(actualizacionDTO, getContexto());
            
            return ResponseEntity.ok(Collections.singletonMap("message", "Bandeja de aprobaciones actualizada exitosamente"));
            
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al actualizar bandeja de aprobaciones: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al actualizar bandeja de aprobaciones", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("message", "Error al actualizar la bandeja de aprobaciones"));
        }
    }

    @PutMapping("/actualizar-grilla-renovacion")
    @Raml("renovacioncontrato.grilla.renovacion.actualizar.properties")
    public ResponseEntity<Map<String, String>> actualizarGrillaRenovacion(@RequestBody ActualizacionBandejaDTO actualizacionDTO) {
        logger.info("actualizarGrillaRenovacion - Para actualizar la grilla de Renovación de Contrato");
        
        try {
            // Validar datos de entrada
            if (actualizacionDTO.getIdRequerimientoRenovacion() == null) {
                return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "El ID del requerimiento de renovación es requerido"));
            }
            
            // Actualizar grilla de renovación de contrato
            informeRenovacionService.actualizarGrillaRenovacionContrato(actualizacionDTO, getContexto());
            
            return ResponseEntity.ok(Collections.singletonMap("message", "Grilla de renovación de contrato actualizada exitosamente"));
            
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al actualizar grilla de renovación: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al actualizar grilla de renovación de contrato", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("message", "Error al actualizar la grilla de renovación de contrato"));
        }
    }
}