package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RequerimientoRenovacionService;

@RestController
@RequestMapping("/api/renovaciones")
public class RenovacionRestController extends BaseRestController {

	private Logger logger = LogManager.getLogger(RenovacionRestController.class);
	
	@Autowired
    RequerimientoRenovacionService requerimientoRenovacionService;

    @GetMapping("/requerimientos")
    public Page<RequerimientoRenovacion> buscar(@RequestParam(defaultValue = "") String numeroExpediente, Pageable pageable) {
        logger.info("buscar {} ", numeroExpediente);
        return requerimientoRenovacionService.buscar(numeroExpediente,pageable,getContexto());
    }

	@PostMapping("/requerimiento")
	public RequerimientoRenovacion registrar(@RequestBody RequerimientoRenovacion requerimientoRenovacion) {
		logger.info("registrar {} ", requerimientoRenovacion);
        try {
            return requerimientoRenovacionService.guardar(requerimientoRenovacion,getContexto());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
