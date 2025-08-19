package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.ParametrosRenovacionService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/renovaciones/parametro")
public class ParametrosRenovacionController extends BaseRestController {

    private Logger logger = LogManager.getLogger(ParametrosRenovacionController.class);

    @Autowired
    private ParametrosRenovacionService parametrosRenovacionService;

    @GetMapping("/tipo-solicitudes")
    @Raml("renovacioncontrato.parametros.tiposolicitudes.properties")
    public ResponseEntity<List<Map<String, Object>>> listarTiposSolicitudes() {
        logger.info("listarTiposSolicitudes - Evaluador tecnico Contrato");
        try {
            List<Map<String, Object>> tiposSolicitudes = parametrosRenovacionService.listarTiposSolicitudes();
            return new ResponseEntity<>(tiposSolicitudes, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al listar tipos de solicitudes", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/subsectores")
    @Raml("renovacioncontrato.parametros.subsectores.properties")
    public ResponseEntity<List<Map<String, Object>>> listarSubsectores() {
        logger.info("listarSubsectores - Evaluador tecnico Contrato");
        try {
            List<Map<String, Object>> subsectores = parametrosRenovacionService.listarSubsectores();
            return new ResponseEntity<>(subsectores, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al listar subsectores", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}