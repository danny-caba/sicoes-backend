package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.python.parser.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.*;
import pe.gob.osinergmin.sicoes.repository.*;
import pe.gob.osinergmin.sicoes.service.NotificacionContratoService;
import pe.gob.osinergmin.sicoes.service.PersonalReemplazoService;
import pe.gob.osinergmin.sicoes.service.SupervisoraMovimientoService;
import pe.gob.osinergmin.sicoes.util.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PersonalReemplazoServiceImpl implements PersonalReemplazoService {

    Logger logger = LogManager.getLogger(PersonalReemplazoServiceImpl.class);

    @Autowired
    private PersonalReemplazoDao reemplazoDao;

    @Autowired
    private DocumentoReemDao documentoReemDao;

    @Autowired
    private ArchivoDao archivoDao;

    @Autowired
    private ListadoDetalleDao listadoDetalleDao;

    @Autowired
    private EvaluarDocuReemDao evaluarDocuReemDao;

    @Autowired
    private SupervisoraMovimientoService supervisoraMovimientoService;

    @Autowired
    private  NotificacionContratoService notificacionContratoService;

    @Autowired
    private ComboDao comboDao;

    @Autowired
    private AprobacionReempDao aprobacionReempDao;

    @Autowired
    private AprobacionDao aprobacionDao;

    @Autowired
    private AdendaDao adendaDao;

    @Autowired
    private EvaluacionDocumentacionDao evaluacionDocumentacionDao;

    @Autowired
    private EvaluacionPPDao evaluacionPPDao;


    @Override
    public Page<PersonalReemplazo> listarPersonalReemplazo(Long idSolicitud, String descAprobacion, String descEvalDocIniServ,
                                                           Pageable pageable, Contexto contexto) {
        logger.info("listarPersonalReemplazo");
        Pageable pageRequest = pageable == null ? Pageable.unpaged() : pageable;

        String listadoEstadoSolicitud = Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO;
        Long idAprobacion = null;
        Long idEvalDocIniServ = null;
        if (!descAprobacion.isEmpty()){
            ListadoDetalle listadoAprobacion = listadoDetalleDao.obtenerListadoDetalle(listadoEstadoSolicitud,descAprobacion);
            idAprobacion = listadoAprobacion.getIdListadoDetalle();
        }
        if (!descAprobacion.isEmpty()){
            ListadoDetalle listadoEvalDocIniServ = listadoDetalleDao.obtenerListadoDetalle(listadoEstadoSolicitud,descEvalDocIniServ);
            idEvalDocIniServ = listadoEvalDocIniServ.getIdListadoDetalle();
        }
        return reemplazoDao.obtenerxIdSolicitud(idSolicitud,idAprobacion,idEvalDocIniServ,pageRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo guardar(PersonalReemplazo personalReemplazo) {
        AuditoriaUtil.setAuditoriaRegistro(personalReemplazo,AuditoriaUtil.getContextoJob());
        return reemplazoDao.save(personalReemplazo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo eliminarBaja(PersonalReemplazo personalReemplazo) {
        Long id = personalReemplazo.getIdReemplazo();
        if (id == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }

        PersonalReemplazo entity = reemplazoDao.findById(id)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));

        entity.setPerfilBaja(null);
        entity.setPersonaBaja(null);
        entity.setFeFechaDesvinculacion(null);
        AuditoriaUtil.setAuditoriaRegistro(entity,AuditoriaUtil.getContextoJob());
        return reemplazoDao.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo actualizar(PersonalReemplazo personalReemplazo) {
        if (personalReemplazo.getIdReemplazo() == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }

        logger.info("actualizando {}:",personalReemplazo.getIdReemplazo());

        PersonalReemplazo existe = reemplazoDao.findById(personalReemplazo.getIdReemplazo())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO));

        if (personalReemplazo.getIdSolicitud() != null) {
            existe.setIdSolicitud(personalReemplazo.getIdSolicitud());
        }
        if (personalReemplazo.getPersonaPropuesta() != null &&
                personalReemplazo.getPersonaPropuesta().getIdSupervisora() != null) {
            existe.setPersonaPropuesta(personalReemplazo.getPersonaPropuesta());
        }
        if (personalReemplazo.getPerfil() != null) {
            existe.setPerfil(personalReemplazo.getPerfil());
        }
        if (personalReemplazo.getFeFechaRegistro() != null) {
            existe.setFeFechaRegistro(personalReemplazo.getFeFechaRegistro());
        }
        if (personalReemplazo.getFeFechaInicioContractual() != null) {
            existe.setFeFechaInicioContractual(personalReemplazo.getFeFechaInicioContractual());
        }
        if (personalReemplazo.getEstadoReemplazo() != null) {
            existe.setEstadoReemplazo(personalReemplazo.getEstadoReemplazo());
        }
        if (personalReemplazo.getPersonaBaja() != null &&
                personalReemplazo.getPersonaBaja().getIdSupervisora() != null) {
            existe.setPersonaBaja(personalReemplazo.getPersonaBaja());
        }
        if (personalReemplazo.getPerfilBaja() != null) {
            existe.setPerfilBaja(personalReemplazo.getPerfilBaja());
        }
        if (personalReemplazo.getFeFechaBaja() != null) {
            existe.setFeFechaBaja(personalReemplazo.getFeFechaBaja());
        }
        if (personalReemplazo.getFeFechaDesvinculacion() != null) {
            existe.setFeFechaDesvinculacion(personalReemplazo.getFeFechaDesvinculacion());
        }
        if (personalReemplazo.getFeFechaFinalizacionContrato() != null) {
            existe.setFeFechaFinalizacionContrato(personalReemplazo.getFeFechaFinalizacionContrato());
        }
        if (personalReemplazo.getEstadoRevisarEval() != null) {
            existe.setEstadoRevisarEval(personalReemplazo.getEstadoRevisarEval());
        }
        if (personalReemplazo.getEstadoEvalDoc() != null) {
            existe.setEstadoEvalDoc(personalReemplazo.getEstadoEvalDoc());
        }
        if (personalReemplazo.getEstadoAprobacionInforme() != null) {
            existe.setEstadoAprobacionInforme(personalReemplazo.getEstadoAprobacionInforme());
        }
        if (personalReemplazo.getEstadoAprobacionAdenda() != null) {
            existe.setEstadoAprobacionAdenda(personalReemplazo.getEstadoAprobacionAdenda());
        }
        if (personalReemplazo.getEstadoEvalDocIniServ() != null) {
            existe.setEstadoEvalDocIniServ(personalReemplazo.getEstadoEvalDocIniServ());
        }

        logger.info("actualizando_Ex {}:",existe);

        AuditoriaUtil.setAuditoriaActualizacion(existe,AuditoriaUtil.getContextoJob());
        return reemplazoDao.save(existe);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo eliminarPropuesta(PersonalReemplazo personalReemplazo) {
        Long id = personalReemplazo.getIdReemplazo();
        if (id == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }
        PersonalReemplazo entity = reemplazoDao.findById(id)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));
        //Eliminar documentos adjuntos
        Long idSeccion = listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.SECCION_DOC_REEMPLAZO.PERSONAL_PROPUESTO).get(0).getIdListadoDetalle();
        logger.info("idSeccion {}:",idSeccion);

        if (!documentoReemDao.existsByIdReemplazoPersonalAndSeccion_IdListadoDetalle(id,idSeccion)) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE);
        }
        List<Long> ids = documentoReemDao.findIdsByReemplazoAndSeccion(id, idSeccion);
        archivoDao.deleteByDocumentoIds(ids);
        documentoReemDao.deleteByIdIn(ids);
        //Quitar personal
        entity.setPerfil(null);
        entity.setPersonaPropuesta(null);
        AuditoriaUtil.setAuditoriaRegistro(entity,AuditoriaUtil.getContextoJob());
        return reemplazoDao.save(entity);
    }

    @Override
    public PersonalReemplazo registrar(PersonalReemplazo personalReemplazo, Contexto contexto) {
        Long id = personalReemplazo.getIdReemplazo();
        if (id == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }

        PersonalReemplazo existe = reemplazoDao.findById(id)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO));
        if (existe.getPersonaPropuesta() == null){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONA_PROPUESTA);
        }

        if (existe.getPersonaBaja() == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONA_BAJA);
        }

        //Buscamos que este llena la seccion 3.-Solcitud de reemplazo de supervisor
        Long idSeccion = listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.SECCION_DOC_REEMPLAZO.SOLICITUD_REEMPLAZO_SUPERVISOR).get(0).getIdListadoDetalle();
        logger.info("idSeccion-validar {}:",idSeccion);
        if (!documentoReemDao.existsByIdReemplazoPersonalAndSeccion_IdListadoDetalle(id,idSeccion)) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE);
        }
        existe.setEstadoReemplazo (listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.ESTADO_SOLICITUD.EN_EVALUACION).get(0));
        //Actualizamos el estao de movimiento del perfil:
        //SupervisoraMovimiento movi = new SupervisoraMovimiento();
        //supervisoraMovimientoService.guardar(movi,AuditoriaUtil.getContextoJob());
        AuditoriaUtil.setAuditoriaActualizacion(existe,AuditoriaUtil.getContextoJob());

        PersonalReemplazo reemplazoSave = reemplazoDao.save(existe);
        
        enviarNotificacionByRolEvaluador(existe,contexto);
        enviarNotificacionDesvinculacion(existe,contexto);

        return reemplazoSave;
    }
    
    private void enviarNotificacionDesvinculacion(PersonalReemplazo personalReemplazo, Contexto contexto) {

        if(Boolean.FALSE.equals(existeNumeroExpediente(personalReemplazo))){
            logger.info("No existe número expediente");
            return; 
        }
        String numeroExpediente = personalReemplazo.getPersonaPropuesta().getNumeroExpediente();

        String razonSocial = personalReemplazo.getPersonaPropuesta().getNombreRazonSocial();
        String nombreSupervisora = null;
        if(razonSocial!=null){
            nombreSupervisora = razonSocial;
        }else{
            String apellidoPaterno = personalReemplazo.getPersonaPropuesta().getApellidoPaterno();
            String apellidoMaterno = personalReemplazo.getPersonaPropuesta().getApellidoMaterno();
            nombreSupervisora = personalReemplazo.getPersonaPropuesta().getNombres().concat(" ").concat(apellidoPaterno).concat(" ").concat(apellidoMaterno);
        }
        logger.info("Empresa supervisora {}",nombreSupervisora);
        notificacionContratoService.notificarDesvinculacionEmpresa(numeroExpediente, nombreSupervisora,contexto);
    }

    private void enviarNotificacionByRolEvaluador(PersonalReemplazo personalReemplazo, Contexto contexto) {

        List<Rol> roles = contexto.getUsuario().getRoles();
        Long idRol = Long.parseLong(Constantes.ROLES.EVALUADOR_TECNICO);

        Rol rolEvaluador = AuditoriaUtil.getRolById(roles, idRol);

        if(Boolean.FALSE.equals(existeNumeroExpediente(personalReemplazo))){
            logger.info("No existe número expediente");
            return; 
        }

        if(rolEvaluador!=null) {
            String numeroExpediente = personalReemplazo.getPersonaPropuesta().getNumeroExpediente();

            notificacionContratoService.notificarReemplazoPersonalByEmail(
                numeroExpediente,
                rolEvaluador.getNombre(),
                contexto);    
        }else{
            logger.info("Este usuario no tiene rol evaluador");
        }        
    }    

    private boolean existeNumeroExpediente(PersonalReemplazo personalReemplazo) {
        Supervisora proposer = personalReemplazo.getPersonaPropuesta();
        return proposer != null && proposer.getNumeroExpediente() != null;
    }

    @Override
    public PersonalReemplazo obtenerPersonalReemplazo(Long idReemplazo) {
        logger.info("obtenerPersonalReemplazo");
        return reemplazoDao.obtenerxIdReemplazo(idReemplazo)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EvaluarDocuResponseDTO evaluarConformidad(EvaluarDocuRequestDTO request, Contexto contexto) {
        if (!Objects.isNull(request.getObservacion())) {
            logger.info("registrar observacion");

            return mapEvalDocuResponse(insertNuevoEvalDocuReemplazo(request, contexto));
        }

        Optional<EvaluarDocuReemplazo> registroExistente = evaluarDocuReemDao
                .findByIdDocumentoIdRol(request.getIdDocumento(), request.getIdRol());

        if (registroExistente.isPresent()) {
            logger.info("existe registro -> update conformidad");

            registroExistente.get().setFechaEvaluacion(Date.from(Instant.now()));
            registroExistente.get().setConforme(request.getConformidad());
            AuditoriaUtil.setAuditoriaActualizacion(registroExistente.get(), contexto);

            return mapEvalDocuResponse(evaluarDocuReemDao.save(registroExistente.get()));
        }

        logger.info("no existe registro -> se inserta nuevo");

        return mapEvalDocuResponse(insertNuevoEvalDocuReemplazo(request, contexto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GenericResponseDTO<List<EvaluarDocuResponseDTO>> registrarObservaciones(List<EvaluarDocuRequestDTO> request, Contexto contexto) {
        List<EvaluarDocuResponseDTO> response = request.stream()
                .map(obs -> insertNuevoEvalDocuReemplazo(obs, contexto))
                .map(this::mapEvalDocuResponse)
                .collect(Collectors.toList());

        return GenericResponseDTO.<List<EvaluarDocuResponseDTO>>builder()
                .resultado(response)
                .build();
    }

    private EvaluarDocuReemplazo insertNuevoEvalDocuReemplazo(EvaluarDocuRequestDTO request, Contexto contexto){
        DocumentoReemplazo documentoReemplazo = new DocumentoReemplazo();
        documentoReemplazo.setIdDocumento(request.getIdDocumento());

        Rol rol = new Rol();
        rol.setIdRol(request.getIdRol());

        EvaluarDocuReemplazo registroNuevo = new EvaluarDocuReemplazo();
        registroNuevo.setDocumento(documentoReemplazo);
        registroNuevo.setEvaluadoPor(contexto.getUsuario());
        registroNuevo.setFechaEvaluacion(Date.from(Instant.now()));
        registroNuevo.setConforme(request.getConformidad());
        registroNuevo.setObservacion(request.getObservacion());
        registroNuevo.setRol(rol);
        AuditoriaUtil.setAuditoriaRegistro(registroNuevo, contexto);

        return evaluarDocuReemDao.save(registroNuevo);
    }

    private EvaluarDocuResponseDTO mapEvalDocuResponse(EvaluarDocuReemplazo evalDocuReemplazo) {
        return EvaluarDocuResponseDTO.builder()
                .idEvaluarDocuReemp(evalDocuReemplazo.getIdEvalDocumento())
                .idDocuReemp(evalDocuReemplazo.getDocumento().getIdDocumento())
                .fecEvaluacion(DateUtil.getDate(evalDocuReemplazo.getFechaEvaluacion(),"dd/MM/yyyy HH:mm:ss"))
                .conformidad(evalDocuReemplazo.getConforme())
                .evaluador(evalDocuReemplazo.getEvaluadoPor().getUsuario())
                .observacion(evalDocuReemplazo.getObservacion())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GenericResponseDTO registrarRevDocumentos(RegistrarRevDocumentosRequestDTO request) {
        PersonalReemplazo personalReemplazoToUpdate = reemplazoDao
                .findById(request.getIdReemplazo())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));

        List<DocumentoReemplazo> listDocsAsociados = documentoReemDao
                .findByIdReemplazoPersonal(request.getIdReemplazo());

        boolean allDocsConforme = !listDocsAsociados.isEmpty()
                && listDocsAsociados.stream()
                .allMatch(doc -> !Objects.isNull(doc.getEvaluacion())
                        && Constantes.LISTADO.SI_NO.SI.equals(doc.getEvaluacion().getConforme()));

        if (allDocsConforme) {
            ListadoDetalle estadoEnProceso = listadoDetalleDao.listarListadoDetallePorCoodigo(
                    Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO)
                    .stream()
                    .filter(resultado -> resultado.getOrden().compareTo(1L) == 0)
                    .findFirst()
                    .orElse(new ListadoDetalle());
            personalReemplazoToUpdate.setEstadoRevisarEval(estadoEnProceso);

            reemplazoDao.save(personalReemplazoToUpdate);

            return GenericResponseDTO.builder()
                    .resultado(Constantes.ESTADO_REVISION_DOCS_REEMPLAZO.OK)
                    .build();
        } else {
            ListadoDetalle estadoPreliminar = listadoDetalleDao.listarListadoDetallePorCoodigo(
                            Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)
                    .stream()
                    .filter(resultado -> resultado.getOrden().compareTo(1L) == 0)
                    .findFirst()
                    .orElse(new ListadoDetalle());
            personalReemplazoToUpdate.setEstadoReemplazo(estadoPreliminar);

            reemplazoDao.save(personalReemplazoToUpdate);

            return GenericResponseDTO.builder()
                    .resultado(Constantes.ESTADO_REVISION_DOCS_REEMPLAZO.SUBSANAR)
                    .build();
        }
    }

    @Override
    public PersonalReemplazo guardar(PersonalReemplazo model, Contexto contexto) {
        return null;
    }

    @Override
    public PersonalReemplazo obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminar(Long id, Contexto contexto) {
        if (documentoReemDao.existsByIdReemplazoPersonal(id)) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_CON_DOCUMENTOS);
        }
        reemplazoDao.deleteById(id);
    }

     @Override
    public List<Combo> listarContratistas(String codigo){
      return   comboDao.listarContratistas(codigo);
    }

    @Override
    public List<AprobacionReemp> buscarAprobacion(String requerimiento, Long tipoaprob , Long estadoaprob, Long tiposolicitud,
                                                  Long idcontratista, Long numexpediente) {
        List<AprobacionReemp>  resultado = new ArrayList<>();

        if(requerimiento.equals(Constantes.REQUERIMIENTO.EVAL_INFO_APROB_G2_GER_DIV)){
            resultado =  aprobacionReempDao.buscarEvalInfAprobTecG2(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente);
        }
        if(requerimiento.equals(Constantes.REQUERIMIENTO.EVAL_INFO_APROB_G3_GER_LIN)){
            resultado =  aprobacionReempDao.buscarEvalInfAprobTecG3(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente);
        }
        if(requerimiento.equals(Constantes.REQUERIMIENTO.APROB_EVAL_CONTR)){
            resultado = aprobacionReempDao.buscarAprobEvalRolContr(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente);
        }
        if(requerimiento.equals(Constantes.REQUERIMIENTO.VB_APROB_G2_APROB_ADMIN)){
            resultado = aprobacionReempDao.buscarVBAprobG2Admin(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente);
        }
        if(requerimiento.equals(Constantes.REQUERIMIENTO.FIRMA_APROB_G3_APROB_ADMIN)){
            resultado = aprobacionReempDao.buscarFirmarAprobG3Admin(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente);
        }
        if(requerimiento.equals(Constantes.REQUERIMIENTO.APROB_ADMIN_G4_GAF)){
            resultado =  aprobacionReempDao.buscarAprobG4AdmGAF(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente);
        }

        return resultado;
    }

    private String obtenerNombreSupervisora(PersonalReemplazo persoReemplazo) {
        
        String razonSocial = persoReemplazo.getPersonaPropuesta().getNombreRazonSocial();
        this.logger.info("razon social juridica {} ", razonSocial);
        String nombreSupervisora = null;
        if(razonSocial!=null){
            nombreSupervisora = razonSocial;
        }else{
            String apellidoPaterno = persoReemplazo.getPersonaPropuesta().getApellidoPaterno();
            String apellidoMaterno = persoReemplazo.getPersonaPropuesta().getApellidoMaterno();
            nombreSupervisora = persoReemplazo.getPersonaPropuesta().getNombres().concat(" ").concat(apellidoPaterno).concat(" ").concat(apellidoMaterno);
            this.logger.info("razon social personal natural {} ", razonSocial);
        }
        return nombreSupervisora;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
	public Aprobacion updateAprobacion(AprobacionDTO aprobacion, Contexto contexto) {



            Optional<Aprobacion> aprobOpt = aprobacionDao.findById(aprobacion.getIdAprobacion());
            Aprobacion aprobacionFinal = aprobOpt.orElseThrow(() -> new RuntimeException("aprobacion no encontrada"));


            Optional<PersonalReemplazo> persoReempOpt = reemplazoDao.findById(aprobacionFinal.getRemplazoPersonal().getIdReemplazo());
            PersonalReemplazo persoReempFinal = persoReempOpt.orElseThrow(()
                    -> new RuntimeException("reemplazo personal no encontrada"));        


        if (aprobacion.getDeObservacion() != null) {
                   aprobacionFinal.setDeObservacion(aprobacion.getDeObservacion());
        }
        String numeroExpediente = obtenerNumeroExpediente(persoReempFinal);       

        if(aprobacion.getRequerimiento().equals(Constantes.REQUERIMIENTO.EVAL_DOC_EVAL_TEC_CONT)){ //Evaluar la documentación Rol Evaluador Técnico del Contrato
            if(aprobacion.getAccion().equals("A")) {

                String nombreSupervisora = obtenerNombreSupervisora(persoReempFinal);

                if (aprobacion.getConforme()) {
                   ListadoDetalle x =  listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO,Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO);
                persoReempFinal.setEstadoReemplazo(x); //en proceso  ---ok
                persoReempFinal.setEstadoEvalDoc(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO,Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO));  //en proceso
                persoReempFinal.setEstadoAprobacionInforme(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO,Constantes.LISTADO.ESTADO_SOLICITUD.EN_APROBACION)); // en aprobacion  -ok
                persoReempFinal.setEstadoEvalDocIniServ(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO,Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)); // preliminar

                    notificacionContratoService.notificarCargarDocumentosInicioServicio( nombreSupervisora,contexto);
                } else {
                persoReempFinal.setEstadoReemplazo(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO,Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)); //preliminar ---ok
                    // enviar notificacion x email            
                    notificacionContratoService.notificarSubsanacionDocumentos( nombreSupervisora,contexto);
                }
            }else{
                persoReempFinal.setEstadoReemplazo(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO,Constantes.LISTADO.ESTADO_SOLICITUD.ARCHIVADO)); //archivado   ---ok
                persoReempFinal.setEstadoEvalDoc(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO,Constantes.LISTADO.ESTADO_SOLICITUD.ARCHIVADO));  //archivado  ----OK
                //persoReempFinal.setIdPersonaBaja(10000000L);  //liberado ----> agregar lista---> HACE INSERTS
                //persoReempFinal.setIdPersonaPropuesta(1000000L); //bloqueado ---> agregar Lista  --> HACE INSERTS
                // buscar campo Estado personal y cambiarle el estado a propuesto
            }
               persoReempFinal.setUsuActualizacion(aprobacion.getUsuActualizacion());
               persoReempFinal.setIpActualizacion(aprobacion.getIpActualizacion());
               persoReempFinal.setFecActualizacion(new Date());
               reemplazoDao.save(persoReempFinal);
        }
        if(aprobacion.getRequerimiento().equals(Constantes.REQUERIMIENTO.EVAL_INF_APROB_TEC_G2)){ //Evaluar Informe Rol Aprobador Técnico (G2 - Gerente de Division)

           if(aprobacion.getAccion().equals("A")) {
               aprobacionFinal.setEstadoAprobGerenteDiv(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.APROBADO));  //aprobado
               persoReempFinal.setEstadoRevisarEval(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO,Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)); //preliminar
               
               notificacionContratoService.notificarRevisarDocumentacionPendiente( numeroExpediente,contexto);

           }else{
               aprobacionFinal.setEstadoAprob(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO));  //desaprobado
               aprobacionFinal.setEstadoAprobGerenteDiv(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO)); //desaprobado
               persoReempFinal.setEstadoEvalDoc(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO,Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR));  //preliminar
           }
               persoReempFinal.setUsuActualizacion(aprobacion.getUsuActualizacion());
               persoReempFinal.setIpActualizacion(aprobacion.getIpActualizacion());
               persoReempFinal.setFecActualizacion(new Date());
               reemplazoDao.save(persoReempFinal);
        }
        if(aprobacion.getRequerimiento().equals(Constantes.REQUERIMIENTO.EVAL_INF_APROB_TEC_G3)){ //Evaluar Informe Rol Aprobador Técnico (G3 - Gerente de Línea)

           if(aprobacion.getAccion().equals("A")) {
               aprobacionFinal.setEstadoAprobGerenteLinea(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.APROBADO));  //aprobado
               aprobacionFinal.setEstadoAprob(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.APROBADO));  //aprobado
               persoReempFinal.setEstadoAprobacionInforme(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO,Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO)); //concluido

               notificacionContratoService.notificarRevisarDocumentacionPendiente( numeroExpediente,contexto);
           }else{
               aprobacionFinal.setEstadoAprob(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.APROBADO));  //desaprobado
               aprobacionFinal.setEstadoAprobGerenteLinea(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO)); //desaprobado
               persoReempFinal.setEstadoRevisarEval(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO,Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)); //preliminar
           }
               persoReempFinal.setUsuActualizacion(aprobacion.getUsuActualizacion());
               persoReempFinal.setIpActualizacion(aprobacion.getIpActualizacion());
               persoReempFinal.setFecActualizacion(new Date());
               reemplazoDao.save(persoReempFinal);
        }
        if(aprobacion.getRequerimiento().equals(Constantes.REQUERIMIENTO.APROB_EVAL_CONTR)){ //Aprobación Rol Evaluador de contratos


            Optional<Adenda> adendaOpt = adendaDao.findById(persoReempFinal.getIdReemplazo());
            Adenda adendaFinal = adendaOpt.orElseThrow(() -> new RuntimeException("adenda no encontrada"));

            if(aprobacion.getAccion().equals("A")) {
                adendaFinal.setEstadoAprobacionLogistica(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ADENDA.CODIGO,Constantes.LISTADO.ESTADO_ADENDA.APROBADO));  //aprobado
                adendaFinal.setEstadoVbGAF(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ADENDA.CODIGO,Constantes.LISTADO.ESTADO_ADENDA.ASIGNADO)); //asignado
            }else{
                aprobacionFinal.setEstadoAprob(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.APROBADO));  //desaprobado
                adendaFinal.setEstadoVbGAF(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ADENDA.CODIGO,Constantes.LISTADO.ESTADO_ADENDA.RECHAZADO)); //rechazado
            }
            adendaFinal.setUsuActualizacion(aprobacion.getUsuActualizacion());
            adendaFinal.setIpActualizacion(aprobacion.getIpActualizacion());
            adendaFinal.setFecActualizacion(new Date());
            adendaDao.save(adendaFinal);
        }


        aprobacionFinal.setUsuActualizacion(aprobacion.getUsuActualizacion());
        aprobacionFinal.setIpActualizacion(aprobacion.getIpActualizacion());
        aprobacionFinal.setFecActualizacion(new Date());

        return  aprobacionDao.save(aprobacionFinal) ;

	}

    private String obtenerNumeroExpediente(PersonalReemplazo personalReemplazo) {
        if (personalReemplazo == null) {
            return "";
        }
            
        Supervisora personaPropuesta = personalReemplazo.getPersonaPropuesta();
        return personaPropuesta != null ? 
               Optional.ofNullable(personaPropuesta.getNumeroExpediente())
                .orElse("") : 
                "";
    }


    @Override
    public EvaluacionDocumentacion obtenerEvaluacionDocumentacion(Long id , Long idsol) {
       return evaluacionDocumentacionDao.obtenerListado(id, idsol)
               .orElseThrow(() -> new RuntimeException("Evaluación de documentación no encontrada"));
    }

    @Override
    public EvaluacionDocumentacionPP obtenerEvaluacionDocumentacionBPP(Long id , Long idsol) {
       return evaluacionPPDao.obtenerListadoPP(id, idsol)
               .orElseThrow(() -> new RuntimeException("Evaluación de documentación no encontrada"));
    }
}