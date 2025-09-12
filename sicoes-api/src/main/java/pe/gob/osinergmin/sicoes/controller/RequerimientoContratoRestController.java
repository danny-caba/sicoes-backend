package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.osinergmin.sicoes.model.RequerimientoContrato;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoContratoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoContratoDTO;
import pe.gob.osinergmin.sicoes.service.RequerimientoContratoService;
import pe.gob.osinergmin.sicoes.util.Raml;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/requerimientos/contratos")
public class RequerimientoContratoRestController  extends BaseRestController {

    private final Logger logger = LogManager.getLogger(RequerimientoContratoRestController.class);

    @Autowired
    private RequerimientoContratoService requerimientoContratoService;

    @GetMapping
    @Raml("requerimientoContrato.listar.properties")
    public Page<RequerimientoContrato> listar(@ModelAttribute FiltroRequerimientoContratoDTO filtros, Pageable pageable) {
        logger.info("listar requerimientos contratos: {}", filtros);
        return requerimientoContratoService.listar(filtros, pageable, getContexto());
    }

    @PostMapping()
    @Raml("requerimientoContrato.registrar.properties")
    @Transactional
    public RequerimientoContrato registrar(@RequestBody RequerimientoContrato contrato) {
        logger.info("registrar requerimiento contrato: {}", contrato);
        return requerimientoContratoService.guardar(contrato, getContexto());
    }

    @PostMapping("/{uuid}/editar")
    @Raml("requerimientoContrato.registrar.properties")
    @Transactional
    public RequerimientoContrato editar(@PathVariable("uuid") String uuid,
                                         @RequestBody RequerimientoContratoDTO contrato) {
        logger.info("editar requerimiento contrato: {}", uuid);
        return requerimientoContratoService.editar(uuid, contrato, getContexto());
    }
}
