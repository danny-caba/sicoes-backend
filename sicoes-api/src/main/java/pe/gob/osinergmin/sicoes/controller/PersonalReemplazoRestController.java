package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;
import pe.gob.osinergmin.sicoes.service.PersonalReemplazoService;

@RestController
@RequestMapping("/api")
public class PersonalReemplazoRestController extends BaseRestController {

    private Logger logger = LogManager.getLogger(PersonalReemplazoRestController.class);

    @Autowired
    private PersonalReemplazoService personalReemplazoService;

    @GetMapping("/externo/reemplazo/solicitud/obtener/{idSolicitud}")
    public Page<PersonalReemplazo> listarReemplazoPorIdSolicitud(@PathVariable Long idSolicitud,
                                                                 Pageable pageable){
        logger.info("obtener listado reemplazo personal");
        return personalReemplazoService.listarPersonalReemplazo(idSolicitud,pageable,getContexto());
    }
}
