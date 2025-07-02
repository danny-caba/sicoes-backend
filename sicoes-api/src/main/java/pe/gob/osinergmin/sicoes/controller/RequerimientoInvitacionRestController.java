package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import pe.gob.osinergmin.sicoes.service.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.util.Raml;

import java.util.List;

@RestController
@RequestMapping("/invitaciones")
public class RequerimientoInvitacionRestController extends BaseRestController{

    private Logger logger = LogManager.getLogger(RequerimientoInvitacionRestController.class);

    @Autowired
    private RequerimientoInvitacionService requerimientoInvitacionService;

    @GetMapping
    @Raml("requerimientoInvitacion.obtener.properties")
    public Page<RequerimientoInvitacion> obtener(
            @RequestParam(required=false) Long idEstado,
            @RequestParam(required=false) String fechaInicioInvitacion,
            @RequestParam(required=false) String fechaFinInvitacion,
            Pageable pageable) {
        return requerimientoInvitacionService.obtener(idEstado, fechaInicioInvitacion,
                fechaFinInvitacion, getContexto(), pageable);
    }

    @PatchMapping("/{id}/evaluar")
    public Requerimiento evaluarInvitacion(
            @PathVariable Long  id,
            @RequestBody ListadoDetalleDTO estado) {
        return requerimientoInvitacionService.evaluar(id, estado, getContexto());
    }
}
