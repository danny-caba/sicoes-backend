package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import pe.gob.osinergmin.sicoes.model.dto.SaldoSupervisoraDTO;
import pe.gob.osinergmin.sicoes.service.SaldoSupervisoraService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/saldos")
@Validated
public class SaldoSupervisoraController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(SaldoSupervisoraController.class);

    @Autowired
    private SaldoSupervisoraService saldoSupervisoraService;

    @GetMapping("/{uid}")
    @Raml("saldoSupervisora.obtener.properties")
    public SaldoSupervisoraDTO obtenerSaldoSupervisora(@PathVariable("uid") Long id) {
        return saldoSupervisoraService.obtenerPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parámetros inválidos"));
    }

}
