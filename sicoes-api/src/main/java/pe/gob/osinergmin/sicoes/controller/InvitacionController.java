package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import pe.gob.osinergmin.sicoes.model.dto.InvitacionDTO;
import pe.gob.osinergmin.sicoes.service.InvitacionService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/invitaciones")
@Validated
public class InvitacionController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(InvitacionController.class);

    @Autowired
    private InvitacionService invitacionService;

    @PostMapping
    @Raml("invitacion.guardar.properties")
    public InvitacionDTO guardarInvitacion(@Valid @RequestBody InvitacionDTO invitacionDTO) {
        return invitacionService.guardar(invitacionDTO, getContexto());
    }

    @DeleteMapping("/{uid}/eliminar")
    @Raml("invitacion.eliminar.properties")
    public InvitacionDTO eliminarInvitacion(@PathVariable("uid") Long id) {
        return invitacionService.eliminar(id, getContexto());
    }

}
