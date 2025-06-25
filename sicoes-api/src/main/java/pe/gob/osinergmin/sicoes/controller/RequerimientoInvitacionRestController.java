package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.osinergmin.sicoes.service.RequerimientoInvitacionService;

@RestController
@RequestMapping("/invitaciones")
public class RequerimientoInvitacionRestController extends BaseRestController{

    private Logger logger = LogManager.getLogger(RequerimientoInvitacionRestController.class);

    @Autowired
    private RequerimientoInvitacionService requerimientoInvitacionService;
}
