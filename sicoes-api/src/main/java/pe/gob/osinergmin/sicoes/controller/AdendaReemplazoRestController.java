package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.osinergmin.sicoes.model.AdendaReemplazo;
import pe.gob.osinergmin.sicoes.service.AdendaReemplazoService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.util.Raml;

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

}
