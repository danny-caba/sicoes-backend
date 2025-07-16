package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.PersonalReemplazoService;

@RestController
@RequestMapping("/api")
public class PersonalReemplazoRestController extends BaseRestController {

    private Logger logger = LogManager.getLogger(PersonalReemplazoRestController.class);

    @Autowired
    private PersonalReemplazoService personalReemplazoService;

    @Autowired
    NotificacionService notificacionService;

    @GetMapping("/externo/reemplazo/solicitud/obtener/{idSolicitud}")
    public Page<PersonalReemplazo> listarReemplazoPorIdSolicitud(@PathVariable Long idSolicitud,
                                                                 Pageable pageable){
        logger.info("obtener listado reemplazo personal");
        return personalReemplazoService.listarPersonalReemplazo(idSolicitud,pageable,getContexto());
    }

    @DeleteMapping("/externo/reemplazo/solicitud/{idreemplazo}")
    public void eliminar(@PathVariable Long idreemplazo){
        logger.info("eliminar reemplazo {} ", idreemplazo);
        personalReemplazoService.eliminar(idreemplazo,getContexto());
    }

    @PostMapping("/externo/reemplazo/solicitud/baja/inserta/propuesto")
    public PersonalReemplazo registrar(@RequestBody PersonalReemplazo personalReemplazo){
        logger.info(" registrar reemplazo {}", personalReemplazo);
        return personalReemplazoService.guardar(personalReemplazo);
    }

    @PutMapping("/externo/reemplazo/solicitud/baja/elimina/propuesto")
    public PersonalReemplazo eliminaPropuesto(@RequestBody PersonalReemplazo personalReemplazo){
        logger.info("eliminar propuesto {}",personalReemplazo);
        return personalReemplazoService.eliminarPropuesto(personalReemplazo);
    }

}
