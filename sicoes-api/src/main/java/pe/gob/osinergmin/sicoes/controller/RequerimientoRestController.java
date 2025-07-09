package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.service.RequerimientoService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/requerimientos")
@Validated
public class RequerimientoRestController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(RequerimientoRestController.class);

    @Autowired
    private RequerimientoService requerimientoService;

    @PostMapping
    @Raml("requerimiento.guardar.properties")
    public Requerimiento guardarRequerimiento(@RequestBody Requerimiento requerimiento) {
        return requerimientoService.guardar(requerimiento, getContexto());
    }

    @GetMapping
    @Raml("requerimiento.listar.properties")
    public Page<Requerimiento> listarRequerimientos(@ModelAttribute FiltroRequerimientoDTO filtros, Pageable pageable) {
        return requerimientoService.listar(filtros, pageable, getContexto());
    }

    @PatchMapping("/{uid}/archivar")
    @Raml("requerimiento.archivar.properties")
    public Requerimiento archivarRequerimiento(@PathVariable("uid") String requerimientoUuid, @RequestBody Requerimiento requerimiento) {
        Requerimiento requerimientoDB = requerimientoService.obtenerPorUuid(requerimientoUuid);
        requerimientoDB.setDeObservacion(requerimiento.getDeObservacion());
        return requerimientoService.archivar(requerimientoDB, getContexto());
    }

    @GetMapping("/{uid}")
    @Raml("requerimiento.obtener.properties")
    public Requerimiento obtenerRequerimiento(@PathVariable("uid") Long id) {
        return requerimientoService.obtenerPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parámetros inválidos"));
    }

}
