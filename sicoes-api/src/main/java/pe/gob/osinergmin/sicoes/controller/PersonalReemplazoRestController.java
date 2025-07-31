package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;
import pe.gob.osinergmin.sicoes.model.dto.EvaluarConformidadRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.EvaluarConformidadResponseDTO;
import pe.gob.osinergmin.sicoes.service.DocumentoReemService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.PersonalReemplazoService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api")
public class PersonalReemplazoRestController extends BaseRestController {

    private Logger logger = LogManager.getLogger(PersonalReemplazoRestController.class);

    @Autowired
    private PersonalReemplazoService personalReemplazoService;

    @Autowired
    private DocumentoReemService documentoReemService;

    @Autowired
    NotificacionService notificacionService;

    @GetMapping("/externo/reemplazo/solicitud/obtener/{idSolicitud}")
    @Raml("personalReemplazo.obtener.properties")
    public Page<PersonalReemplazo> listarReemplazoPorIdSolicitud(@PathVariable Long idSolicitud,
                                                                 Pageable pageable){
        //logger.info("obtener listado reemplazo personal");
        //return personalReemplazoService.listarPersonalReemplazo(idSolicitud,pageable,getContexto());

        logger.info("[CTRL] pageable={}", pageable);
        Page<PersonalReemplazo> page = personalReemplazoService
                .listarPersonalReemplazo(idSolicitud, pageable, getContexto());
        logger.info("[CTRL] page.getContent() size={}", page.getContent().size());
        return page;

    }

    @DeleteMapping("/externo/reemplazo/solicitud/{idreemplazo}")
    public void eliminar(@PathVariable Long idreemplazo){
        logger.info("eliminar reemplazo {} ", idreemplazo);
        personalReemplazoService.eliminar(idreemplazo,getContexto());
    }

    @PostMapping("/externo/reemplazo/solicitud/baja/inserta/propuesto")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo registrar(@RequestBody PersonalReemplazo personalReemplazo){
        logger.info(" registrar reemplazo {}", personalReemplazo);
        return personalReemplazoService.guardar(personalReemplazo);
    }

    @PutMapping("/externo/reemplazo/solicitud/baja/elimina/propuesto")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo eliminaBajaPropuesto(@RequestBody PersonalReemplazo personalReemplazo){
        logger.info("eliminar baja {}",personalReemplazo);
        return personalReemplazoService.eliminarBaja(personalReemplazo);
    }

    @PutMapping("/externo/reemplazo/solicitud/propuesta/inserta/propuesto")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo registrarPropuesto(@RequestBody PersonalReemplazo personalReemplazo){
        logger.info(" registrar propuesto {}", personalReemplazo);
        PersonalReemplazo p = personalReemplazoService.actualizar(personalReemplazo);
        logger.info("p:{}",p);
        return personalReemplazoService.actualizar(personalReemplazo);
    }

    @PutMapping("/externo/reemplazo/solicitud/propuesta/elimina/propuesto")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo eliminaPropuesto(@RequestBody PersonalReemplazo personalReemplazo){
        logger.info(" eliminar propuesto {}", personalReemplazo);
        return personalReemplazoService.eliminarPropuesta(personalReemplazo);
    }

    @PutMapping("/externo/reemplazo/inserta")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo finalizarRegistro(@RequestBody PersonalReemplazo personalReemplazo){
        logger.info("Finalizar registro propuesto {}", personalReemplazo);
        return personalReemplazoService.registrar(personalReemplazo,getContexto());
    }

    @PostMapping("/reemplazo/solicitud/propuesto/revisa")
    @Raml("evalDocuReemplazo.revisar.properties")
    public EvaluarConformidadResponseDTO evaluarConformidad(@RequestBody EvaluarConformidadRequestDTO request){
        logger.info(" Request {}", request);
        return documentoReemService.evaluarConformidad(request, getContexto());
    }
}
