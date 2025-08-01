package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.AprobacionDTO;
import pe.gob.osinergmin.sicoes.model.dto.EvaluarConformidadRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.EvaluarConformidadResponseDTO;
import pe.gob.osinergmin.sicoes.service.DocumentoReemService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.PersonalReemplazoService;
import pe.gob.osinergmin.sicoes.util.Raml;

import java.util.List;

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
        return personalReemplazoService.registrar(personalReemplazo);
    }

    @PostMapping("/reemplazo/solicitud/propuesto/revisa")
    @Raml("evalDocuReemplazo.revisar.properties")
    public EvaluarConformidadResponseDTO evaluarConformidad(@RequestBody EvaluarConformidadRequestDTO request){
        logger.info(" Request {}", request);
        return documentoReemService.evaluarConformidad(request, getContexto());
    }

    @GetMapping("/interno/reemplazo/solicitud/aprobaciones/{requerimiento}")
    public List<AprobacionReemp> buscarAprobacion(
            @PathVariable String requerimiento,
            @RequestParam(required = false) Long tipoaprob,
            @RequestParam(required = false) Long estadoaprob,
            @RequestParam(required = false) Long tiposolicitud,
            @RequestParam(required = false) Long idcontratista,
            @RequestParam(required = false) Long numexpediente) {

        logger.info("buscar aprobaciones");

        return personalReemplazoService.buscarAprobacion( requerimiento, tipoaprob, estadoaprob, tiposolicitud,  idcontratista, numexpediente
        );
    }

	@PostMapping("/interno/reemplazo/solicitud/aprobacion")
	public Aprobacion updateAprobacion(@RequestBody AprobacionDTO aprobacion) {
		logger.info("Actualizar Estado aprobacion {} ", aprobacion);
		return personalReemplazoService.updateAprobacion(aprobacion);
	}

    @GetMapping("/interno/reemplazo/solicitud/documentos")
    public EvaluacionDocumentacion obtenerEvaluacionDocumentacion(@RequestParam Long id, @RequestParam(required = false)  Long idsol) {
        logger.info("obtener evaluacion documentacion");
        return  personalReemplazoService.obtenerEvaluacionDocumentacion (id, idsol);
    }

    @GetMapping("/interno/reemplazo/solicitud/baja/propuestos")
    public EvaluacionDocumentacionPP obtenerEvaluacionDocumentacionPP(@RequestParam Long id, @RequestParam(required = false)  Long idsol) {
        logger.info("obtener evaluacion documentacion propuesto");
        return  personalReemplazoService.obtenerEvaluacionDocumentacionBPP (id, idsol);
    }
}
