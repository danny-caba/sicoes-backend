package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoRenovacionListDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RequerimientoRenovacionService;

@RestController
@RequestMapping("/api/renovaciones")
public class RenovacionRestController extends BaseRestController {

	private Logger logger = LogManager.getLogger(RenovacionRestController.class);
	
	@Autowired
    RequerimientoRenovacionService requerimientoRenovacionService;

    @GetMapping("/requerimientos")
    public Page<RequerimientoRenovacionListDTO> buscar(
            @RequestParam(required = false) String idSolicitud,
            @RequestParam(required = false) String nuExpediente,
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String subSector,
            Pageable pageable) {
        logger.info("buscar{} {} {} {}", idSolicitud,nuExpediente,sector,subSector);
        return requerimientoRenovacionService.buscar(idSolicitud,nuExpediente,sector,subSector,pageable,getContexto());
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
