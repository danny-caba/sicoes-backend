package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RequerimientoRenovacionService;

@RestController
@RequestMapping("/api/renovacion/invitacion")
public class EvaluarInvitacionController extends BaseRestController {

	private Logger logger = LogManager.getLogger(EvaluarInvitacionController.class);
	
	@Autowired
    RequerimientoInvitacionService requerimientoInvitacionService;


	@PostMapping("/aceptar")
	public RequerimientoInvitacion aceptar(@RequestBody RequerimientoInvitacion requerimientoInvitacion) {
		logger.info("registrar {} ", requerimientoInvitacion);
        try {
            return requerimientoInvitacionService.aceptar(requerimientoInvitacion,getContexto());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
