package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.PlazoConfirmacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.PlazoConfirmacionDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.PlazoConfirmacionService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/renovaciones/plazo-confirmacion")
public class PlazoConfirmacionRestController extends BaseRestController {

    private Logger logger = LogManager.getLogger(PlazoConfirmacionRestController.class);

    @Autowired
    private PlazoConfirmacionService plazoConfirmacionService;

    @GetMapping
    @Raml("renovacioncontrato.plazoconfirmacion.listar.properties")
    public ResponseEntity<List<PlazoConfirmacion>> listar() {
        logger.info("listar - Obteniendo todos los plazos de confirmación");
        
        try {
            List<PlazoConfirmacion> plazos = plazoConfirmacionService.listar();
            return new ResponseEntity<>(plazos, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al listar plazos de confirmación", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paginado")
    @Raml("renovacioncontrato.plazoconfirmacion.listarpaginado.properties")
    public ResponseEntity<Page<PlazoConfirmacion>> listarPaginado(Pageable pageable) {
        logger.info("listarPaginado - Página: {}, Tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        
        try {
            Page<PlazoConfirmacion> plazos = plazoConfirmacionService.listarPaginado(pageable);
            return new ResponseEntity<>(plazos, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al listar plazos de confirmación paginados", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Raml("renovacioncontrato.plazoconfirmacion.obtener.properties")
    public ResponseEntity<PlazoConfirmacion> obtenerPorId(@PathVariable Long id) {
        logger.info("obtenerPorId - ID: {}", id);
        
        try {
            PlazoConfirmacion plazo = plazoConfirmacionService.obtenerPorId(id);
            return new ResponseEntity<>(plazo, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al obtener plazo de confirmación: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error al obtener plazo de confirmación", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Raml("renovacioncontrato.plazoconfirmacion.crear.properties")
    public ResponseEntity<Map<String, Object>> crear(@RequestBody PlazoConfirmacionDTO plazoDTO) {
        logger.info("crear - Datos: {}", plazoDTO);
        
        try {
            PlazoConfirmacion plazoCreado = plazoConfirmacionService.crear(plazoDTO, getContexto());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Plazo de confirmación creado exitosamente");
            response.put("id", plazoCreado.getIdPlazoConfirmacion());
            response.put("data", plazoCreado);
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al crear plazo de confirmación: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al crear plazo de confirmación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("message", "Error al crear el plazo de confirmación"));
        }
    }

    @PutMapping("/{id}")
    @Raml("renovacioncontrato.plazoconfirmacion.actualizar.properties")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long id, @RequestBody PlazoConfirmacionDTO plazoDTO) {
        logger.info("actualizar - ID: {}, Datos: {}", id, plazoDTO);
        
        try {
            PlazoConfirmacion plazoActualizado = plazoConfirmacionService.actualizar(id, plazoDTO, getContexto());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Plazo de confirmación actualizado exitosamente");
            response.put("id", plazoActualizado.getIdPlazoConfirmacion());
            response.put("data", plazoActualizado);
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al actualizar plazo de confirmación: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al actualizar plazo de confirmación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("message", "Error al actualizar el plazo de confirmación"));
        }
    }

    @DeleteMapping("/{id}")
    @Raml("renovacioncontrato.plazoconfirmacion.eliminar.properties")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        logger.info("eliminar - ID: {}", id);
        
        try {
            plazoConfirmacionService.eliminar(id, getContexto());
            
            return ResponseEntity.ok(Collections.singletonMap("message", "Plazo de confirmación eliminado exitosamente"));
            
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al eliminar plazo de confirmación: " + e.getMessage());
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al eliminar plazo de confirmación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("message", "Error al eliminar el plazo de confirmación"));
        }
    }

    @GetMapping("/por-tipo-dia")
    @Raml("renovacioncontrato.plazoconfirmacion.listartipoidia.properties")
    public ResponseEntity<List<PlazoConfirmacion>> listarPorTipoDia(@RequestParam Integer tipoDia) {
        logger.info("listarPorTipoDia - Tipo día: {}", tipoDia);
        
        try {
            List<PlazoConfirmacion> plazos = plazoConfirmacionService.listarPorTipoDia(tipoDia);
            return new ResponseEntity<>(plazos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error al listar plazos por tipo de día", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/por-dias")
    @Raml("renovacioncontrato.plazoconfirmacion.listarpordias.properties")
    public ResponseEntity<List<PlazoConfirmacion>> listarPorDias(@RequestParam Integer dias) {
        logger.info("listarPorDias - Días: {}", dias);
        
        try {
            List<PlazoConfirmacion> plazos = plazoConfirmacionService.listarPorDias(dias);
            return new ResponseEntity<>(plazos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error al listar plazos por días", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
