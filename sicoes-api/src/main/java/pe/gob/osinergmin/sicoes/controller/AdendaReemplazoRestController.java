package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.Adenda;
import pe.gob.osinergmin.sicoes.model.AdendaReemplazo;
import pe.gob.osinergmin.sicoes.model.dto.FirmaRequestDTO;
import pe.gob.osinergmin.sicoes.service.AdendaReemplazoService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.util.Raml;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AdendaReemplazoRestController extends BaseRestController {

    private Logger logger = LogManager.getLogger(AdendaReemplazoRestController.class);

    @Autowired
    private AdendaReemplazoService adendaReemplazoService;

    @Autowired
    NotificacionService notificacionService;

    @PostMapping("/reemplazo/solicitud/registra/propuesto/adenda")
    @Raml("adendaReemplazo.listar.properties")
    public AdendaReemplazo registrar(@RequestBody AdendaReemplazo adendaReemplazo){
        logger.info(" registrar adenda {}", adendaReemplazo);
        return adendaReemplazoService.guardar(adendaReemplazo,getContexto());
    }

    @PostMapping("/reemplazo/solicitud/visto/")
    public Map<String,Object> vistoBueno(@RequestBody FirmaRequestDTO firmaRequestDTO){
        logger.info("visto bueno inicio {}");
        return adendaReemplazoService.iniciarFirma(firmaRequestDTO.getIdAdenda(), firmaRequestDTO.getVisto(),
                firmaRequestDTO.getFirmaJefe(),firmaRequestDTO.getFirmaGerente());
    }

    @PostMapping("/reemplazo/solicitud/finalizar-visto/")
    public Map<String,Object> finalizarvistoBueno(@RequestBody FirmaRequestDTO firmaRequestDTO){
        logger.info("visto bueno finalizar {}");
        return adendaReemplazoService.finalizarFirma(firmaRequestDTO,getContexto());
    }

    @PutMapping("/reemplazo/solicitud/rechazar-visto")
    @Raml("adendaReemplazo.listar.properties")
    public AdendaReemplazo rechazarVisto(@RequestBody AdendaReemplazo adendaReemplazo){
        logger.info(" rechazar visto adenda {}", adendaReemplazo);
        return adendaReemplazoService.rechazarVisto(adendaReemplazo,getContexto());
    }

    @PostMapping("/reemplazo/solicitud/firmar/")
    public Map<String,Object> firmar(@RequestBody FirmaRequestDTO firmaRequestDTO){
        logger.info("firma inicio {}");
        return adendaReemplazoService.iniciarFirma(firmaRequestDTO.getIdAdenda(), firmaRequestDTO.getVisto(),
                firmaRequestDTO.getFirmaJefe(),firmaRequestDTO.getFirmaGerente());
    }

    @PostMapping("/reemplazo/solicitud/finalizar-firma/")
    public Map<String,Object> finalizarFirma(@RequestBody FirmaRequestDTO firmaRequestDTO){
        logger.info("firma finalizar {}");
        return adendaReemplazoService.finalizarFirma(firmaRequestDTO,getContexto());
    }

    @PutMapping("/reemplazo/solicitud/rechazar-firma")
    @Raml("adendaReemplazo.listar.properties")
    public AdendaReemplazo rechazarFirma(@RequestBody AdendaReemplazo adendaReemplazo,
                                         @RequestParam(required = false) Boolean firmaJefe,
                                         @RequestParam(required = false) Boolean firmaGerente){
        logger.info(" rechazar firma {}", adendaReemplazo);
        return adendaReemplazoService.rechazarFirma(adendaReemplazo,firmaJefe,firmaGerente,getContexto());
    }


}
