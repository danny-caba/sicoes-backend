package pe.gob.osinergmin.sicoes.controller;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.Contrato;
import pe.gob.osinergmin.sicoes.model.dto.ContratoDetalleDTO;
import pe.gob.osinergmin.sicoes.service.ContratoService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api")
public class ContratoRestController extends BaseRestController {

    private Logger logger = LogManager.getLogger(ContratoRestController.class);

    @Autowired
    private ContratoService contratoService;

    @GetMapping("/contratos/listar")
    @Raml("contrato.listar.properties")
    public Page<Contrato> obtenerContratos(
            @RequestParam(required = false) String expediente,
            @RequestParam(required = false) String contratista,
            @RequestParam(required = false) String tipoContrato,
            @RequestParam(required = false) String areaSolicitante,
            @RequestParam(required = false) String estado,
            Pageable pageable
    ) {
        logger.info("obtenerContratos");
        return contratoService.obtenerContratos(expediente, contratista, tipoContrato, areaSolicitante, pageable, getContexto());
    }
   
    @GetMapping("/contratos/{id}")
    @Raml("contrato.obtenerPorId.properties")
    public ResponseEntity<ContratoDetalleDTO> obtenerContratoPorId(@PathVariable("id") Long idContrato) {
        logger.info("obtenerContratoPorId - ID: " + idContrato);
        try {
            Optional<ContratoDetalleDTO> contratoDtoOptional = contratoService.obtenerContratoDetallePorId(idContrato);
            if (contratoDtoOptional.isPresent()) {
                return new ResponseEntity<>(contratoDtoOptional.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error al obtener contrato por ID: " + idContrato, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/contratos/{id}")
    @Raml("contrato.actualizar.properties")
    public ResponseEntity<ContratoDetalleDTO> actualizarContrato(@PathVariable("id") Long idContrato,@RequestBody ContratoDetalleDTO contratoActualizadoDto) {
        logger.info("actualizarContrato - ID: " + idContrato + ", Datos: " + contratoActualizadoDto.toString());
        try {
            contratoActualizadoDto.setIdContrato(idContrato); 
            ContratoDetalleDTO contratoGuardadoDto = contratoService.actualizarContratoDetalle(contratoActualizadoDto, getContexto());
            return new ResponseEntity<>(contratoGuardadoDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Error de datos al actualizar contrato con ID: " + idContrato + ": " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            logger.error("Error al actualizar contrato con ID: " + idContrato + ": " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar contrato con ID: " + idContrato, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
    
    @PostMapping("/contratos/acciones-masivas")
    public ResponseEntity<Map<String, String>> realizarAccionMasivaContratos(
            @RequestBody Contrato req) {

        if (req.getIds() == null || req.getIds().isEmpty()) {
            return ResponseEntity
                .badRequest()
                .body(Collections.singletonMap("message", "La lista de IDs no puede estar vacía."));
        }

        try {
            contratoService.procesarAccionesMasivas(req);
            return ResponseEntity
                .ok(Collections.singletonMap("message", "Acciones masivas procesadas con éxito."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .badRequest()
                .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("message", "Error interno al procesar acciones masivas."));
        }
    }
    
}
