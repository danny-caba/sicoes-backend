package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import pe.gob.osinergmin.sicoes.model.SaldoSupervisora;
import pe.gob.osinergmin.sicoes.service.SaldoSupervisoraService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/saldos")
@Validated
public class SaldoSupervisoraController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(SaldoSupervisoraController.class);

    @Autowired
    private SaldoSupervisoraService saldoSupervisoraService;

    @GetMapping("/supervisora")
    @Raml("saldoSupervisora.obtener.properties")
    public SaldoSupervisora obtenerSaldoSupervisora(@RequestParam Long idSupervisora) {
        logger.info("Obteniendo saldo de supervisora con ID: {}", idSupervisora);
        return saldoSupervisoraService.obtenerPorSupervisoraId(idSupervisora).orElse(null);
    }

}
