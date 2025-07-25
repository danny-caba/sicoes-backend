package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoAprobacionDTO;
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
        logger.info("Guardar requerimiento {}", requerimiento);
        return requerimientoService.guardar(requerimiento, getContexto());
    }

    @GetMapping
    @Raml("requerimiento.listar.properties")
    public Page<Requerimiento> listarRequerimientos(@ModelAttribute FiltroRequerimientoDTO filtros, Pageable pageable) {
        logger.info("Listar requerimientos");
        return requerimientoService.listar(filtros, pageable, getContexto());
    }

    @PatchMapping("/{uuid}/archivar")
    @Raml("requerimiento.archivar.properties")
    public Requerimiento archivarRequerimiento(@PathVariable("uuid") String requerimientoUuid, @RequestBody Requerimiento requerimiento) {
        logger.info("Archivar requerimiento {} {}", requerimientoUuid, requerimiento);
        Requerimiento requerimientoDB = requerimientoService.obtenerPorUuid(requerimientoUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Requerimiento no encontrado"));
        requerimientoDB.setDeObservacion(requerimiento.getDeObservacion());
        return requerimientoService.archivar(requerimientoDB, getContexto());
    }

    @GetMapping("/{uid}")
    @Raml("requerimiento.obtener.properties")
    public Requerimiento obtenerRequerimiento(@PathVariable("uid") String uuid) {
        logger.info("Obtener requerimiento {}", uuid);
        return requerimientoService.obtenerPorUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Requerimiento no encontrado con UUID: " + uuid));
    }

    @PatchMapping("/{uuid}/aprobar")
    @Raml("requerimiento.obtener.properties")
    public Requerimiento aprobar(@PathVariable("uuid") String uuid, @RequestBody RequerimientoAprobacionDTO aprobacion) {
        logger.info("Aprobar requerimiento {}", uuid);
        return requerimientoService.aprobar(uuid, aprobacion, getContexto());
    }

    @GetMapping("/aprobar")
    @Raml("requerimiento.aprobar.properties")
    public Page<Requerimiento> listarRequerimientosPorAprobar(@ModelAttribute FiltroRequerimientoDTO filtros, Pageable pageable) {
        logger.info("Listar requerimientos por aprobar");
        return requerimientoService.listarPorAprobar(filtros, pageable, getContexto());
    }
}
