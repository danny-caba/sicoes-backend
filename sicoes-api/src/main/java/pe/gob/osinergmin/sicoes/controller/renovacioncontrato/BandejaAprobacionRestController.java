package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.BandejaAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl.BandejaAprobacionImplService;
import pe.gob.osinergmin.sicoes.util.Contexto;

@RestController
@RequestMapping("/api/renovacion/bandeja")
public class BandejaAprobacionRestController extends BaseRestController {

    private final Logger logger = LogManager.getLogger(BandejaAprobacionRestController.class);

    private final BandejaAprobacionImplService bandejaAprobacionService;

    @Autowired
    public BandejaAprobacionRestController(BandejaAprobacionImplService bandejaAprobacionService) {
        this.bandejaAprobacionService = bandejaAprobacionService;
    }

    @GetMapping("/aprobaciones")
    public Page<BandejaAprobacionResponseDTO> listarAprobaciones(
            @RequestParam(required = false) String numeroExpediente,
            @RequestParam(required = false) Long estadoAprobacionInforme,
            @RequestParam(required = false) Long idContratista,
            Pageable pageable) {

        logger.info("get listarInformes: {} {} {}", numeroExpediente, estadoAprobacionInforme, idContratista);

        Contexto contexto = getContexto();
        return bandejaAprobacionService.listaApobaciones(

                numeroExpediente, estadoAprobacionInforme, idContratista, contexto, pageable);
    }
}
