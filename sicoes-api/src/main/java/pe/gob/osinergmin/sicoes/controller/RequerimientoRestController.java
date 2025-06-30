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
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoDTO;
import pe.gob.osinergmin.sicoes.service.RequerimientoService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.Raml;

import java.util.Map;

@RestController
@RequestMapping("/api/requerimientos")
@Validated
public class RequerimientoRestController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(RequerimientoRestController.class);

    @Autowired
    private RequerimientoService requerimientoService;

    @PostMapping
    @Raml("requerimiento.guardar.properties")
    public RequerimientoDTO guardarRequerimiento(@RequestBody RequerimientoDTO requerimientoDTO) {
        return requerimientoService.guardar(requerimientoDTO, getContexto());
    }

    @GetMapping
    @Raml("requerimiento.listar.properties")
    public Page<RequerimientoDTO> listarRequerimientos(@ModelAttribute FiltroRequerimientoDTO filtros, Pageable pageable, Contexto contexto) {
        return requerimientoService.listar(filtros, pageable, contexto);
    }

    @PatchMapping("/{uid}/archivar")
    @Raml("requerimiento.archivar.properties")
    public RequerimientoDTO archivarRequerimiento(@PathVariable("uid") Long id, @RequestBody Map<String, String> body) {
        String observacion = body.get("observacion");
        if (observacion == null || observacion.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Observación es obligatoria");
        }
        return requerimientoService.archivar(id, observacion, getContexto());
    }

    @GetMapping("/{uid}")
    @Raml("requerimiento.obtener.properties")
    public RequerimientoDTO obtenerRequerimiento(@PathVariable("uid") Long id) {
        return requerimientoService.obtenerPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parámetros inválidos"));
    }

}
