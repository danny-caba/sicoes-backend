package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.*;
import pe.gob.osinergmin.sicoes.service.PersonalReemplazoService;
import pe.gob.osinergmin.sicoes.util.Raml;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonalReemplazoRestController extends BaseRestController {

    private static final Logger logger = LogManager.getLogger(PersonalReemplazoRestController.class);

    private final PersonalReemplazoService personalReemplazoService;

    @Autowired
    public PersonalReemplazoRestController(PersonalReemplazoService personalReemplazoService) {
        this.personalReemplazoService = personalReemplazoService;
    }

    @GetMapping("/externo/reemplazo/solicitud/obtener/{idSolicitud}")
    @Raml("personalReemplazo.obtener.properties")
    public Page<PersonalReemplazo> listarReemplazoPorIdSolicitud(@PathVariable Long idSolicitud,
                                                                 @RequestParam(required = false) String descAprobacion,
                                                                 @RequestParam(required = false) String descRevisarDoc,
                                                                 @RequestParam(required = false) String descEvalDoc,
                                                                 @RequestParam(required = false) String descRevisarEval,
                                                                 @RequestParam(required = false) String descAprobacionInforme,
                                                                 @RequestParam(required = false) String descAprobacionAdenda,
                                                                 @RequestParam(required = false) String descDocIniServ,
                                                                 @RequestParam(required = false) String descEvalDocIniServ,
                                                                 Pageable pageable){
        logger.info("obtener listado reemplazo personal");
        Page<PersonalReemplazo> page = personalReemplazoService
                .listarPersonalReemplazo(idSolicitud, descAprobacion, descRevisarDoc, descEvalDoc, descRevisarEval,
                        descAprobacionInforme, descAprobacionAdenda,descDocIniServ, descEvalDocIniServ, pageable, getContexto());
        logger.info("page:p {}",page);
        return page;
    }

    @DeleteMapping("/externo/reemplazo/solicitud/{idreemplazo}")
    public void eliminar(@PathVariable Long idreemplazo) {
        logger.info("eliminar reemplazo {} ", idreemplazo);
        personalReemplazoService.eliminar(idreemplazo, getContexto());
    }

    @PostMapping("/externo/reemplazo/solicitud/baja/inserta/propuesto")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo registrar(@RequestBody PersonalReemplazo personalReemplazo) {
        logger.info(" registrar reemplazo {}", personalReemplazo);
        return personalReemplazoService.guardar(personalReemplazo, getContexto());
    }

    @PutMapping("/externo/reemplazo/solicitud/baja/elimina/propuesto")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo eliminaBajaPropuesto(@RequestBody PersonalReemplazoDTO personalReemplazo) {
        logger.info("eliminar baja {}", personalReemplazo);
        return personalReemplazoService.eliminarBaja(personalReemplazo, getContexto());
    }

    @PutMapping("/externo/reemplazo/solicitud/propuesta/inserta/propuesto")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo registrarPropuesto(@RequestBody PersonalReemplazo personalReemplazo) {
        logger.info(" registrar propuesto {}", personalReemplazo);
        return personalReemplazoService.actualizar(personalReemplazo,getContexto());
    }

    @PutMapping("/externo/reemplazo/solicitud/propuesta/elimina/propuesto")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo eliminaPropuesto(@RequestBody PersonalReemplazoDTO personalReemplazo) {
        logger.info(" eliminar propuesto {}", personalReemplazo);
        return personalReemplazoService.eliminarPropuesta(personalReemplazo,getContexto());
    }

    @PutMapping("/externo/reemplazo/inserta")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo finalizarRegistro(@RequestBody PersonalReemplazoDTO personalReemplazo) {
        logger.info("Finalizar registro propuesto {}", personalReemplazo);
        return personalReemplazoService.registrar(personalReemplazo,getContexto());
    }

    @PostMapping("/reemplazo/solicitud/propuesto/revisa")
    @Raml("evalDocuReemplazo.revisar.properties")
    public EvaluarDocuResponseDTO evaluarConformidad(@RequestBody EvaluarDocuRequestDTO request){
        logger.info("evaluarConformidad Request  {}", request);
        return personalReemplazoService.evaluarConformidad(request, getContexto());
    }

    @PostMapping("/reemplazo/solicitud/propuesto/observaciones")
    @Raml("generic.response.properties")
    public GenericResponseDTO<List<EvaluarDocuResponseDTO>> registrarObservaciones(@RequestBody List<EvaluarDocuRequestDTO> request){
        logger.info("registrarObservaciones Request {}", request);
        return personalReemplazoService.registrarObservaciones(request, getContexto());
    }


    @GetMapping("/interno/reemplazo/solicitud/aprobaciones/contratistas")
    public List<Combo> listarContratistas(@RequestParam(required = false)  String filtro){
        logger.info("obtener listado de contratistas");
        return personalReemplazoService.listarContratistas(filtro);
    }

    @GetMapping("/interno/reemplazo/solicitud/aprobaciones/{requerimiento}/{corol}")
    public List<AprobacionReemp> buscarAprobacion(
            @PathVariable String requerimiento,
            @PathVariable String corol,
            @RequestParam(required = false) Long tipoaprob,
            @RequestParam(required = false) Long estadoaprob,
            @RequestParam(required = false) Long tiposolicitud,
            @RequestParam(required = false) Long idcontratista,
            @RequestParam(required = false) Long numexpediente) {

        logger.info("buscar aprobaciones");

        return personalReemplazoService.buscarAprobacion( requerimiento, corol, tipoaprob, estadoaprob, tiposolicitud,  idcontratista, numexpediente
        );
    }

	@PostMapping("/interno/reemplazo/solicitud/aprobacion")
	public Aprobacion updateAprobacion(@RequestBody AprobacionDTO aprobacion) {
		logger.info("Actualizar Estado aprobacion {} ", aprobacion);
		return personalReemplazoService.updateAprobacion(aprobacion,getContexto());
	}

    @GetMapping("/interno/reemplazo/solicitud/documentos")
    public EvaluacionDocumentacion obtenerEvaluacionDocumentacion(@RequestParam Long id) {
        logger.info("obtener evaluacion documentacion");
        return  personalReemplazoService.obtenerEvaluacionDocumentacion (id);
    }


    @PostMapping("/reemplazo/solicitud/registra/propuesto/revision")
    @Raml("generic.response.properties")
    public PersonalReemplazo registroRevisarDocumentacion(
            @RequestBody RegistrarRevDocumentosRequestDTO request){
        logger.info("registroRevisarDocumentacion Request {}", request);
        return personalReemplazoService.registrarRevDocumentos(request, getContexto());
    }

    @GetMapping("/reemplazo/{idReemplazo}")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo obtenerPersonalReemplazo(@PathVariable Long idReemplazo){
        logger.info("obtenerPersonalReemplazo Request {}", request);
        return personalReemplazoService.obtenerPersonalReemplazo(idReemplazo);
    }

    @PutMapping("/reemplazo/solicitud/registra/inicio-servicio")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo registroInicioServicio(@RequestBody PersonalReemplazoDTO personalReemplazo) {
        logger.info("Registro inicio servicio {}", personalReemplazo);
        return personalReemplazoService.registrarDocIniServ(personalReemplazo,getContexto());
    }

    @GetMapping("/interno/reemplazo/solicitud/aprobaciones/historial/{idReemplazo}")
    public Page<HistorialAprobReemp> listarHistorialAprobaciones(@PathVariable Long idReemplazo,
                                                                 Pageable pageable){
        logger.info("obtener listado reemplazo personal");
        return personalReemplazoService.listarHistorialReemp(idReemplazo, pageable );
    }

    @GetMapping("/interno/reemplazo/solicitud/documentos/{id}")
    public EvaluacionDocInicioServ obtenerEvaluacionDocInicioServicio(@PathVariable Long id){
        logger.info("obtener evaluacion de documento de inicio de servicio");
        return personalReemplazoService.obtenerEvaluacionDocInicioServicio(id);
    }

    @GetMapping("/interno/reemplazo/solicitud/{id}/propuestos/{seccion}")
    public List<DocumentoInicioServ> obtenerDocumentosInicioServicio(@PathVariable Long id, @PathVariable String seccion){
        logger.info("obtener documentos de inicio de servicio");
        return personalReemplazoService.obtenerDocumentosInicioServicio(id, seccion);
    }


    @PutMapping("/interno/reemplazo/solicitud/propuesto/evalua/fecha")
    public Boolean listarContratistas(@RequestParam Long id,  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha){
        logger.info("evaluar fecha desvinculacion");
        return personalReemplazoService.evaluarFechaDesvinculacion(id,fecha);
    }


    @PutMapping("/interno/reemplazo/solicitud/propuesto/evalua/informe")
    public PersonalReemplazo actualizzarEvaluarInforme(@RequestParam Long id, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha){
        logger.info("evaluar fecha desvinculacion");
        return personalReemplazoService.evaluarDocumentoInforme(id, fecha);
    }


    @GetMapping("/interno/reemplazo/solicitud/baja/propuestos")
    public EvaluacionDocumentacionPP obtenerEvaluacionDocumentacionPP(@RequestParam Long id, @RequestParam(required = false)  Long idsol) {
        logger.info("obtener evaluacion documentacion propuesto");
        return  personalReemplazoService.obtenerEvaluacionDocumentacionBPP (id, idsol);
    }

    @GetMapping("/interno/reemplazo/solicitud/propuesto/documentos")
    public List<DocumentoPP> listarDocumentacionPPxSeccion(@RequestParam Long id,@RequestParam String seccion) {
        logger.info("obtener evaluacion documentacion propuesto");
        return  personalReemplazoService.obtenerDocumentoPPxSeccion(id, seccion);
    }

    @PostMapping("/interno/reemplazo/solicitud/propuesto/evalua")
    public Boolean evaluarConformidad(@RequestBody EvaluarDocuDTO evaluacion){
        logger.info("evaluar conformidad");
        return personalReemplazoService.evaluarDocumReemplazo(evaluacion, getContexto());
    }

    @PutMapping("/interno/reemplazo/solicitud/registra/inicio-servicio")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo rechazarSolicitudContrato(@RequestBody PersonalReemplazo personalReemplazo,
                                                       @RequestParam(defaultValue = "false") boolean conforme) {
        logger.info("registro inicio servicio {}", personalReemplazo);
        return personalReemplazoService.registrarInicioServicioSolContr(personalReemplazo, conforme , getContexto());
    }

    @PutMapping("/interno/reemplazo/solicitud/propuesto")
    @Raml("personalReemplazo.listar.properties")
    public PersonalReemplazo evaluarDocumentos(@RequestBody PersonalReemplazoDTO personalReemplazo,
                                               @RequestParam(defaultValue = "false") boolean conforme, @RequestParam String accion) {
        logger.info("evaluar documentos {}", personalReemplazo);
        return personalReemplazoService.evaluarDocumentos(personalReemplazo, conforme,accion , getContexto());
    }
}
