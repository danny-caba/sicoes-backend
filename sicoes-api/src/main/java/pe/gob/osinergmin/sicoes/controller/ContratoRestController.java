package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.Contrato;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.SicoesTdSolPerConSec;
import pe.gob.osinergmin.sicoes.service.ContratoService;
import pe.gob.osinergmin.sicoes.service.SicoesTdSolPerConSecService;
import pe.gob.osinergmin.sicoes.util.Raml;

import java.util.List;

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
            Pageable pageable
    ) {
        logger.info("obtenerContratos");
        return contratoService.obtenerContratos(expediente, contratista, tipoContrato, areaSolicitante, pageable, getContexto());
    }

}
