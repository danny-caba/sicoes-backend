package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;
import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.repository.*;
import pe.gob.osinergmin.sicoes.service.NotificacionContratoService;
import pe.gob.osinergmin.sicoes.service.PersonalReemplazoService;
import pe.gob.osinergmin.sicoes.service.SupervisoraMovimientoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import java.util.List;

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
    private SupervisoraMovimientoService supervisoraMovimientoService;

    @Autowired
    private  NotificacionContratoService notificacionContratoService;

    @Override
    public Page<PersonalReemplazo> listarPersonalReemplazo(Long idSolicitud, Pageable pageable, Contexto contexto) {
        logger.info("listarPersonalReemplazo");
        Pageable pageRequest = pageable == null ? Pageable.unpaged() : pageable;
        return reemplazoDao.obtenerxIdSolicitud(idSolicitud,pageRequest);
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
}
