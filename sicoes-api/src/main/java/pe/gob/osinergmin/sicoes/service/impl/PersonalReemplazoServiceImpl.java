package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.PersonalReemplazo;
import pe.gob.osinergmin.sicoes.model.SupervisoraMovimiento;
import pe.gob.osinergmin.sicoes.repository.*;
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

    @Override
    public Page<PersonalReemplazo> listarPersonalReemplazo(Long idSolicitud, Pageable pageable, Contexto contexto) {
        logger.info("listarPersonalReemplazo");
        return reemplazoDao.obtenerxIdSolicitud(idSolicitud,pageable);
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

        entity.setCoPerfilPerBaja(null);
        entity.setIdPersonaBaja(null);
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

        PersonalReemplazo existe = reemplazoDao.findById(personalReemplazo.getIdReemplazo())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO));

        if (personalReemplazo.getIdSolicitud() != null) {
            existe.setIdSolicitud(personalReemplazo.getIdSolicitud());
        }
        if (personalReemplazo.getIdPersonaPropuesta() != null) {
            existe.setIdPersonaPropuesta(personalReemplazo.getIdPersonaPropuesta());
        }
        if (personalReemplazo.getCoPerfil() != null) {
            existe.setCoPerfil(personalReemplazo.getCoPerfil());
        }
        if (personalReemplazo.getFeFechaRegistro() != null) {
            existe.setFeFechaRegistro(personalReemplazo.getFeFechaRegistro());
        }
        if (personalReemplazo.getFeFechaInicioContractual() != null) {
            existe.setFeFechaInicioContractual(personalReemplazo.getFeFechaInicioContractual());
        }
        if (personalReemplazo.getEsEstadoReemplazo() != null) {
            existe.setEsEstadoReemplazo(personalReemplazo.getEsEstadoReemplazo());
        }
        if (personalReemplazo.getIdPersonaBaja() != null) {
            existe.setIdPersonaBaja(personalReemplazo.getIdPersonaBaja());
        }
        if (personalReemplazo.getCoPerfilPerBaja() != null) {
            existe.setCoPerfilPerBaja(personalReemplazo.getCoPerfilPerBaja());
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
        if (personalReemplazo.getEsEstadoRevisarEval() != null) {
            existe.setEsEstadoEvalDoc(personalReemplazo.getEsEstadoEvalDoc());
        }
        if (personalReemplazo.getEsEstadoEvalDoc() != null) {
            existe.setEsEstadoEvalDoc(personalReemplazo.getEsEstadoEvalDoc());
        }
        if (personalReemplazo.getEsEstadoAprobacionInforme() != null) {
            existe.setEsEstadoAprobacionInforme(personalReemplazo.getEsEstadoAprobacionInforme());
        }
        if (personalReemplazo.getEsEstadoAprobacionAdenda() != null) {
            existe.setEsEstadoAprobacionAdenda(personalReemplazo.getEsEstadoAprobacionAdenda());
        }
        if (personalReemplazo.getEsEstadoEvalDocIniServ() != null) {
            existe.setEsEstadoEvalDocIniServ(personalReemplazo.getEsEstadoEvalDocIniServ());
        }

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
                Constantes.LISTADO.SECCION_DOC_REEMPLZO.PERSONAL_PROPUESTO).get(0).getIdListadoDetalle();
        logger.info("idSeccion {}:",idSeccion);

        if (!documentoReemDao.existsByIdReemplazoPersonalAndIdSeccion(id,idSeccion)) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE);
        }
        List<Long> ids = documentoReemDao.findIdsByReemplazoAndSeccion(id, idSeccion);
        archivoDao.deleteByDocumentoIds(ids);
        documentoReemDao.deleteByIdIn(ids);
        //Quitar personal
        entity.setCoPerfil(null);
        entity.setIdPersonaPropuesta(null);
        AuditoriaUtil.setAuditoriaRegistro(entity,AuditoriaUtil.getContextoJob());
        return reemplazoDao.save(entity);
    }

    @Override
    public PersonalReemplazo registrar(PersonalReemplazo personalReemplazo) {
        Long id = personalReemplazo.getIdReemplazo();
        if (id == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }

        PersonalReemplazo existe = reemplazoDao.findById(id)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO));

        if (personalReemplazo.getIdSolicitud() != null) {
            existe.setIdSolicitud(personalReemplazo.getIdSolicitud());
        }
        if (personalReemplazo.getIdPersonaPropuesta() != null) {
            existe.setIdPersonaPropuesta(personalReemplazo.getIdPersonaPropuesta());
        } else {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONA_PROPUESTA);
        }
        if (personalReemplazo.getCoPerfil() != null) {
            existe.setCoPerfil(personalReemplazo.getCoPerfil());
        }
        if (personalReemplazo.getFeFechaRegistro() != null) {
            existe.setFeFechaRegistro(personalReemplazo.getFeFechaRegistro());
        }
        if (personalReemplazo.getFeFechaInicioContractual() != null) {
            existe.setFeFechaInicioContractual(personalReemplazo.getFeFechaInicioContractual());
        }
        if (personalReemplazo.getEsEstadoReemplazo() != null) {
            existe.setEsEstadoReemplazo(personalReemplazo.getEsEstadoReemplazo());
        }
        if (personalReemplazo.getIdPersonaBaja() != null) {
            existe.setIdPersonaBaja(personalReemplazo.getIdPersonaBaja());
        } else {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONA_BAJA);
        }
        if (personalReemplazo.getCoPerfilPerBaja() != null) {
            existe.setCoPerfilPerBaja(personalReemplazo.getCoPerfilPerBaja());
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

        //Buscamos que este llena la seccion 3.-Solcitud de reemplazo de supervisor
        Long idSeccion = listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.SECCION_DOC_REEMPLZO.SOLICITUD_REEMPLAZO_SUPERVISOR).get(0).getIdListadoDetalle();
        logger.info("idSeccion-validar {}:",idSeccion);
        if (!documentoReemDao.existsByIdReemplazoPersonalAndIdSeccion(id,idSeccion)) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE);
        }
        existe.setEsEstadoReemplazo(listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.ESTADO_SOLICITUD.EN_EVALUACION).get(0).getIdListadoDetalle());

        //solicitudHija.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.ARCHIVADO));
        //solicitudHija.setEstadoRevision(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REVISION.CODIGO, Constantes.LISTADO.ESTADO_REVISION.ARCHIVADO));
        if (personalReemplazo.getEsEstadoEvalDoc() != null) {
            existe.setEsEstadoEvalDoc(personalReemplazo.getEsEstadoEvalDoc());
        }
        if (personalReemplazo.getEsEstadoRevisarEval() != null) {
            existe.setEsEstadoEvalDoc(personalReemplazo.getEsEstadoEvalDoc());
        }
        if (personalReemplazo.getEsEstadoAprobacionInforme() != null) {
            existe.setEsEstadoAprobacionInforme(personalReemplazo.getEsEstadoAprobacionInforme());
        }
        if (personalReemplazo.getEsEstadoAprobacionAdenda() != null) {
            existe.setEsEstadoAprobacionAdenda(personalReemplazo.getEsEstadoAprobacionAdenda());
        }
        if (personalReemplazo.getEsEstadoEvalDocIniServ() != null) {
            existe.setEsEstadoEvalDocIniServ(personalReemplazo.getEsEstadoEvalDocIniServ());
        }

        //Actualizamos el estao de movimiento del perfil:
        //SupervisoraMovimiento movi = new SupervisoraMovimiento();
        //supervisoraMovimientoService.guardar(movi,AuditoriaUtil.getContextoJob());
        AuditoriaUtil.setAuditoriaActualizacion(existe,AuditoriaUtil.getContextoJob());
        return reemplazoDao.save(existe);
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
