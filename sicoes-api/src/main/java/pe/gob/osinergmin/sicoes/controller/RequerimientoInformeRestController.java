package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import pe.gob.osinergmin.sicoes.model.RequerimientoInforme;
import pe.gob.osinergmin.sicoes.model.RequerimientoInformeDetalle;
import pe.gob.osinergmin.sicoes.service.RequerimientoInformeService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/informes")
@Validated
public class RequerimientoInformeRestController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(RequerimientoInformeRestController.class);

    @Autowired
    private RequerimientoInformeService requerimientoInformeService;

    @PostMapping
    @Raml("informe.guardar.properties")
    public RequerimientoInformeDetalle guardarInforme(@RequestBody RequerimientoInformeDetalle requerimientoInformeDetalle) {
        return requerimientoInformeService.guardar(requerimientoInformeDetalle, getContexto());
    }

}
