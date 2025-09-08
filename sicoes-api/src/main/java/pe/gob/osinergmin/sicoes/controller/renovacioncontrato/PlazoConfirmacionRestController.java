package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.PlazoConfirmacionRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.PlazoConfirmacionResponseDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.PlazoConfirmacionService;

@RestController
@RequestMapping("/api/renovacion")
public class PlazoConfirmacionRestController extends BaseRestController {
    
    private static final Logger logger = LogManager.getLogger(PlazoConfirmacionRestController.class);
    
    @Autowired
    private PlazoConfirmacionService plazoConfirmacionService;
    
    @GetMapping("/buscarPlazoConfirmacion")
    public ResponseEntity<List<PlazoConfirmacionResponseDTO>> buscarPlazoConfirmacion(@RequestParam(required = false) String estado) {
        try {
            List<PlazoConfirmacionResponseDTO> response = plazoConfirmacionService.listarPlazoConfirmacion(estado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al buscar plazo confirmacion: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/registrarPlazoConfirmacion")
    public ResponseEntity<PlazoConfirmacionResponseDTO> registrarPlazoConfirmacion(@RequestBody PlazoConfirmacionRequestDTO requestDTO) {
        try {
            PlazoConfirmacionResponseDTO response = plazoConfirmacionService.registrarPlazoConfirmacion(requestDTO,getContexto());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            logger.error("Error de validacion: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error al registrar plazo confirmacion: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
