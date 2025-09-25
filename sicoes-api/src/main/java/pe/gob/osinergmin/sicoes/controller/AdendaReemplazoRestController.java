package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.AdendaReemplazo;
import pe.gob.osinergmin.sicoes.model.dto.FirmaRequestDTO;
import pe.gob.osinergmin.sicoes.service.AdendaReemplazoService;
import pe.gob.osinergmin.sicoes.util.Raml;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AdendaReemplazoRestController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(AdendaReemplazoRestController.class);

    private final AdendaReemplazoService adendaReemplazoService;
    public AdendaReemplazoRestController(AdendaReemplazoService adendaReemplazoService) {
        this.adendaReemplazoService = adendaReemplazoService;
    }

    @PostMapping("/reemplazo/solicitud/registra/propuesto/adenda")
    @Raml("adendaReemplazo.listar.properties")
    public AdendaReemplazo registrar(@RequestBody AdendaReemplazo adendaReemplazo){
        logger.info(" registrar adenda {}", adendaReemplazo);
        return adendaReemplazoService.guardar(adendaReemplazo,getContexto());
    }

    @PostMapping("/reemplazo/solicitud/visto/")
    public Map<String,Object> vistoBueno(@RequestBody FirmaRequestDTO firmaRequestDTO){
        logger.info("visto bueno inicio {}", firmaRequestDTO);
        return adendaReemplazoService.iniciarFirma(firmaRequestDTO.getIdAdenda(), firmaRequestDTO.isVisto(),
                firmaRequestDTO.isFirmaJefe(),firmaRequestDTO.isFirmaGerente());
    }

    @PostMapping("/reemplazo/solicitud/finalizar-visto/")
    public Map<String,Object> finalizarvistoBueno(@RequestBody FirmaRequestDTO firmaRequestDTO){
        logger.info("visto bueno finalizar {}", firmaRequestDTO);
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
        logger.info("firma inicio {}", firmaRequestDTO);
        return adendaReemplazoService.iniciarFirma(firmaRequestDTO.getIdAdenda(), firmaRequestDTO.isVisto(),
                firmaRequestDTO.isFirmaJefe(),firmaRequestDTO.isFirmaGerente());
    }

    @PostMapping("/reemplazo/solicitud/finalizar-firma/")
    public Map<String,Object> finalizarFirma(@RequestBody FirmaRequestDTO firmaRequestDTO){
        logger.info("firma finalizar {}", firmaRequestDTO);
        return adendaReemplazoService.finalizarFirma(firmaRequestDTO,getContexto());
    }

    @PostMapping("/reemplazo/solicitud/visto-firma/")
    public AdendaReemplazo finalizarVistoFirma(@RequestBody FirmaRequestDTO firmaRequestDTO){
        logger.info("visto - firma {}",firmaRequestDTO);
        logger.info("contexto {}",getContexto());
        return adendaReemplazoService.finalizarFirmaAdenda(firmaRequestDTO,getContexto());
    }

    @PutMapping("/reemplazo/solicitud/rechazar-firma")
    @Raml("adendaReemplazo.listar.properties")
    public AdendaReemplazo rechazarFirma(@RequestBody AdendaReemplazo adendaReemplazo,
                                         @RequestParam(defaultValue = "false") boolean firmaJefe,
                                         @RequestParam(defaultValue = "false") boolean firmaGerente){
        logger.info(" rechazar firma {}", adendaReemplazo);
        return adendaReemplazoService.rechazarFirma(adendaReemplazo,firmaJefe,firmaGerente,getContexto());
    }


}
