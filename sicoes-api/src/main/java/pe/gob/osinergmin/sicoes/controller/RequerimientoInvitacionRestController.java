package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.service.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/invitaciones")
@Validated
public class RequerimientoInvitacionRestController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(RequerimientoInvitacionRestController.class);

    @Autowired
    private RequerimientoInvitacionService invitacionService;

    @PostMapping
    @Raml("invitacion.guardar.properties")
    public RequerimientoInvitacion guardarInvitacion(@RequestBody RequerimientoInvitacion requerimientoInvitacionDTO) {
        return invitacionService.guardar(requerimientoInvitacionDTO, getContexto());
    }

    @DeleteMapping("/{uid}/eliminar")
    @Raml("invitacion.eliminar.properties")
    public RequerimientoInvitacion eliminarInvitacion(@PathVariable("uid") Long id) {
        return invitacionService.eliminar(id, getContexto());
    }

}
