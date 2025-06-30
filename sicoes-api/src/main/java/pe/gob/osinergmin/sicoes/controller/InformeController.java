package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import pe.gob.osinergmin.sicoes.model.dto.InformeDTO;
import pe.gob.osinergmin.sicoes.service.InformeService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/informes")
@Validated
public class InformeController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(InformeController.class);

    @Autowired
    private InformeService informeService;

    @PostMapping
    @Raml("informe.guardar.properties")
    public InformeDTO guardarInforme(@Valid @RequestBody InformeDTO informeDTO) {
        return informeService.guardar(informeDTO, getContexto());
    }

}
