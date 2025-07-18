package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.osinergmin.sicoes.model.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.service.RequerimientoAprobacionService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/aprobaciones")
public class RequerimientoAprobacionRestController extends BaseRestController  {
    private static final Logger logger = LogManager.getLogger(RequerimientoAprobacionRestController.class);

    @Autowired
    private RequerimientoAprobacionService requerimientoAprobacionService;

    @GetMapping
//    @Raml("requerimientoAprobacion.listar.properties")
    public Page<RequerimientoAprobacionResponseDTO> getRequerimientoAprobacion(FiltroRequerimientoDTO filtroRequerimientoDTO, Pageable pageable) {
        return requerimientoAprobacionService.buscar(filtroRequerimientoDTO, pageable, getContexto());
    }

    @GetMapping("/{uuid}/historial")
    @Raml("requerimientoAprobacion.historial.properties")
    public Page<RequerimientoAprobacion> obtenerHistorial(@PathVariable String  uuid, Pageable pageable) {
        return requerimientoAprobacionService.obtenerHistorial(uuid, getContexto(), pageable);
    }
}
