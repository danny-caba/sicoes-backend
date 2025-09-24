package pe.gob.osinergmin.sicoes.service.impl;

import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.*;
import pe.gob.osinergmin.sicoes.repository.*;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.*;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class PersonalReemplazoServiceImpl implements PersonalReemplazoService {

    private static final Logger logger = LogManager.getLogger(PersonalReemplazoServiceImpl.class);

    private final PersonalReemplazoDao reemplazoDao;
    private final DocumentoReemDao documentoReemDao;
    private final DocumentoReemService documentoReemService;
    private final ArchivoDao archivoDao;
    private final ListadoDetalleDao listadoDetalleDao;
    private final EvaluarDocuReemDao evaluarDocuReemDao;
    private final SupervisoraMovimientoService supervisoraMovimientoService;
    private final NotificacionContratoService notificacionContratoService;
    private final ComboDao comboDao;
    private final AprobacionReempDao aprobacionReempDao;
    private final SicoesSolicitudDao sicoesSolicitudDao;
    private final ArchivoService archivoService;
    private final SigedOldConsumer sigedOldConsumer;
    private final SigedApiConsumer sigedApiConsumer;
    private final Environment env;
    private final AprobacionDao aprobacionDao;
    private final AdendaDao adendaDao;
    private final EvaluacionDocumentacionDao evaluacionDocumentacionDao;
    private final EvaluacionPPDao evaluacionPPDao;
    private final PropuestaProfesionalDao propuestaProfesionalDao;
    private final ListadoDetalleService listadoDetalleService;
    private final HistorialAprobReempDao historialAprobReempDao;
    private final SupervisoraDao supervisoraDao;
    private final UsuarioRolDao usuarioRolDao;
    private final UsuarioDao usuarioDao;
    private final EvaluacionDocInicioServDao evaluacionDocInicioServDao;
    private final DocumentoInicioServDao documentoInicioServDao;
    private final ListadoDao listadoDao;
    private final ArchivoUtil archivoUtil;
    private final RolDao rolDao;
    private final DocumentoPPDao documentoPPDao;
    private final SupervisoraMovimientoDao supervisoraMovimientoDao;
    private final ContratoDao contratoDao;


    @Value("${siged.old.proyecto}")
    private String siglaProyecto;

    @Value("${path.jasper}")
    private String pathJasper;

    @Value("${siged.ws.cliente.osinergmin.numero.documento}")
    private String osiDocumento;

    @Value("${consolidado.documentos}")
    private String consolidadoDocumentos;

    @Value("${finalizacion.evaluacion}")
    private String finalizacionEvaluacion;

    @Value("${carga.documentos}")
    private String cargaDocumentos;

    @Value("${evaluacion}")
    private String evaluacion;

    @Value("${path.temporal}")
    private String pathTemporal;

    @Autowired
    public PersonalReemplazoServiceImpl(PersonalReemplazoDao reemplazoDao,
                                        DocumentoReemDao documentoReemDao,
                                        DocumentoReemService documentoReemService,
                                        ArchivoDao archivoDao,
                                        ListadoDetalleDao listadoDetalleDao,
                                        EvaluarDocuReemDao evaluarDocuReemDao,
                                        SupervisoraMovimientoService supervisoraMovimientoService,
                                        NotificacionContratoService notificacionContratoService,
                                        ComboDao comboDao, AprobacionReempDao aprobacionReempDao,
                                        SicoesSolicitudDao sicoesSolicitudDao,
                                        ArchivoService archivoService,
                                        SigedOldConsumer sigedOldConsumer,
                                        SigedApiConsumer sigedApiConsumer,
                                        Environment env,
                                        AprobacionDao aprobacionDao,
                                        AdendaDao adendaDao,
                                        EvaluacionDocumentacionDao evaluacionDocumentacionDao,
                                        EvaluacionPPDao evaluacionPPDao,
                                        PropuestaProfesionalDao propuestaProfesionalDao,
                                        ListadoDetalleService listadoDetalleService,
                                        HistorialAprobReempDao historialAprobReempDao,
                                        SupervisoraDao supervisoraDao,
                                        UsuarioRolDao usuarioRolDao,
                                        UsuarioDao usuarioDao,
                                        EvaluacionDocInicioServDao evaluacionDocInicioServDao,
                                        DocumentoInicioServDao documentoInicioServDao,
                                        ListadoDao listadoDao,
                                        ArchivoUtil archivoUtil,
                                        RolDao rolDao,
                                        DocumentoPPDao documentoPPDao,
                                        SupervisoraMovimientoDao supervisoraMovimientoDao,
                                        ContratoDao contratoDao) {
        this.reemplazoDao = reemplazoDao;
        this.documentoReemDao = documentoReemDao;
        this.documentoReemService = documentoReemService;
        this.archivoDao = archivoDao;
        this.listadoDetalleDao = listadoDetalleDao;
        this.evaluarDocuReemDao = evaluarDocuReemDao;
        this.supervisoraMovimientoService = supervisoraMovimientoService;
        this.notificacionContratoService = notificacionContratoService;
        this.comboDao = comboDao;
        this.aprobacionReempDao = aprobacionReempDao;
        this.sicoesSolicitudDao = sicoesSolicitudDao;
        this.archivoService = archivoService;
        this.sigedOldConsumer = sigedOldConsumer;
        this.sigedApiConsumer = sigedApiConsumer;
        this.env = env;
        this.aprobacionDao = aprobacionDao;
        this.adendaDao = adendaDao;
        this.evaluacionDocumentacionDao = evaluacionDocumentacionDao;
        this.evaluacionPPDao = evaluacionPPDao;
        this.propuestaProfesionalDao = propuestaProfesionalDao;
        this.listadoDetalleService = listadoDetalleService;
        this.historialAprobReempDao = historialAprobReempDao;
        this.supervisoraDao = supervisoraDao;
        this.usuarioRolDao = usuarioRolDao;
        this.usuarioDao = usuarioDao;
        this.evaluacionDocInicioServDao = evaluacionDocInicioServDao;
        this.documentoInicioServDao = documentoInicioServDao;
        this.listadoDao = listadoDao;
        this.archivoUtil = archivoUtil;
        this.rolDao = rolDao;
        this.documentoPPDao = documentoPPDao;
        this.supervisoraMovimientoDao = supervisoraMovimientoDao;
        this.contratoDao = contratoDao;
    }


    private static final String TIPO_ARCHIVO = "application/pdf";
    private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
    private static final String LOGO_SICOES = "logo-sicoes.png";
    private static final String LOGO_OSINERGMIN = "logo-osinerming.png";
    private static final String P_LOGO_SICOES = "P_LOGO_SICOES";
    private static final String P_LOGO_OSINERGMIN = "P_LOGO_OSINERGMIN";
    private static final String P_TITULO = "P_TITULO";
    private static final String P_INTRODUCCION = "P_INTRODUCCION";
    private static final String P_SUBTITULO_1 = "P_SUBTITULO_1";
    private static final String P_SUBTITULO_2 = "P_SUBTITULO_2";
    private static final String P_MOSTRAR_CABECERA_2 = "P_MOSTRAR_CABECERA_2";
    private static final String P_MOSTRAR_CABECERA_4 = "P_MOSTRAR_CABECERA_4";
    private static final String P_MOSTRAR_DETALLE_2 = "P_MOSTRAR_DETALLE_2";
    private static final String P_MOSTRAR_DETALLE_4 = "P_MOSTRAR_DETALLE_4";
    private static final String P_MOSTRAR_ADICIONAL_2 = "P_MOSTRAR_ADICIONAL_2";
    private static final String P_MOSTRAR_ADICIONAL_4 = "P_MOSTRAR_ADICIONAL_4";
    private static final String FORMATO_04 = "Formato_04_Personal_Reemplazo.jrxml";
    private static final String CONTRATO_LABORAL = "CONTRATO_LABORAL";
    private static final String TIPO_DOCUMENTO_CREAR = "crear.expediente.parametros.tipo.documento.crear";
    private static final String TIPO_CLIENTE = "crear.expediente.parametros.tipo.cliente";
    private static final String PERSONA_JURIDICA_3 = "3. PERSONA JURÍDICA";
    private static final String DOCUMENTOS_PRESENTADOS_4 = "4. DOCUMENTOS PRESENTADOS";
    private static final String CONFORME = "Conforme:";
    private static final String CONTRATO_ALQUILER_CAMIONETA = "CONTRATO_ALQUILER_CAMIONETA";

    @Override
    public Page<PersonalReemplazo> listarPersonalReemplazo(Long idSolicitud, String descAprobacion,
                                                           String descRevisarDoc,
                                                           String descEvalDoc,
                                                           String descRevisarEval,
                                                           String descAprobacionInforme,
                                                           String descAprobacionAdenda,
                                                           String descDocIniServ,
                                                           String descEvalDocIniServ,
                                                           Pageable pageable, Contexto contexto) {
        logger.info("listarPersonalReemplazo");
        Pageable pageRequest = pageable == null ? Pageable.unpaged() : pageable;

        String listadoEstadoSolicitud = Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO;
        String listadoEstadoAprobacion = Constantes.LISTADO.ESTADO_APROBACION.CODIGO;
        Long idAprobacion        = obtenerIdListadoDetalle(listadoEstadoSolicitud, descAprobacion);
        Long idRevisarDoc        = obtenerIdListadoDetalle(listadoEstadoSolicitud, descRevisarDoc);
        Long idEvalDoc           = obtenerIdListadoDetalle(listadoEstadoSolicitud, descEvalDoc);
        Long idRevisarEval       = obtenerIdListadoDetalle(listadoEstadoSolicitud, descRevisarEval);
        Long idAprobacionInforme = obtenerIdListadoDetalle(listadoEstadoAprobacion, descAprobacionInforme);
        Long idAprobacionAdenda  = obtenerIdListadoDetalle(listadoEstadoAprobacion, descAprobacionAdenda);
        Long idDocIniServ        = obtenerIdListadoDetalle(listadoEstadoSolicitud, descDocIniServ);
        Long idEvalDocIniServ    = obtenerIdListadoDetalle(listadoEstadoSolicitud, descEvalDocIniServ);

        return reemplazoDao.obtenerxIdSolicitud(
                idSolicitud,
                idAprobacion,
                idRevisarDoc,
                idEvalDoc,
                idRevisarEval,
                idAprobacionInforme,
                idAprobacionAdenda,
                idDocIniServ,
                idEvalDocIniServ,
                pageRequest);
    }

    @Override
    public List<PersonalReemplazo> listarPersonaReemplazoxDocIniServ(String descDocIniServ) {
        logger.info("listarPersonalReemplazoxDocIniServ");
        String listadoEstadoSolicitud = Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO;
        Long idDocIniServ = null;
        if (descDocIniServ != null && !descDocIniServ.isEmpty()) {
            ListadoDetalle ld = listadoDetalleDao.obtenerListadoDetalle(listadoEstadoSolicitud, descDocIniServ);
            if (ld != null) idDocIniServ = ld.getIdListadoDetalle();
        }
        return reemplazoDao.obtenerxEstadoDocuIniServ(idDocIniServ);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo guardar(PersonalReemplazo personalReemplazo, Contexto contexto) {
        AuditoriaUtil.setAuditoriaRegistro(personalReemplazo,contexto);
        personalReemplazo.setFeFechaRegistro(new Date());
        return reemplazoDao.save(personalReemplazo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo eliminarBaja(PersonalReemplazoDTO personalReemplazo, Contexto contexto) {
        Long id = personalReemplazo.getIdReemplazo();
        if (id == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }

        PersonalReemplazo entity = reemplazoDao.findById(id)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));

        entity.setPerfilBaja(null);
        entity.setPersonaBaja(null);
        entity.setFeFechaDesvinculacion(null);
        AuditoriaUtil.setAuditoriaRegistro(entity,contexto);
        return reemplazoDao.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo actualizar(PersonalReemplazo personalReemplazo, Contexto contexto) {
        if (personalReemplazo.getIdReemplazo() == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }

        logger.info("actualizar - actualizando {}:",personalReemplazo.getIdReemplazo());

        PersonalReemplazo existe = reemplazoDao.findById(personalReemplazo.getIdReemplazo())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO));
        // Actualización de campos
        actualizarCampoSiNoNulo(personalReemplazo.getIdSolicitud(), existe::setIdSolicitud);
        actualizarCampoSiNoNulo(personalReemplazo.getPersonaPropuesta(), p -> {
            if (p.getIdSupervisora() != null) existe.setPersonaPropuesta(p);
        });
        actualizarCampoSiNoNulo(personalReemplazo.getPerfil(), existe::setPerfil);
        actualizarCampoSiNoNulo(personalReemplazo.getFeFechaRegistro(), existe::setFeFechaRegistro);
        actualizarCampoSiNoNulo(personalReemplazo.getFeFechaInicioContractual(), existe::setFeFechaInicioContractual);
        actualizarCampoSiNoNulo(personalReemplazo.getEstadoReemplazo(), existe::setEstadoReemplazo);
        actualizarCampoSiNoNulo(personalReemplazo.getPersonaBaja(), p -> {
            if (p.getIdSupervisora() != null) existe.setPersonaBaja(p);
        });
        actualizarCampoSiNoNulo(personalReemplazo.getPerfilBaja(), existe::setPerfilBaja);
        actualizarCampoSiNoNulo(personalReemplazo.getFeFechaBaja(), existe::setFeFechaBaja);
        actualizarCampoSiNoNulo(personalReemplazo.getFeFechaDesvinculacion(), existe::setFeFechaDesvinculacion);
        actualizarCampoSiNoNulo(personalReemplazo.getFeFechaFinalizacionContrato(), existe::setFeFechaFinalizacionContrato);

        actualizarCampoSiNoNulo(personalReemplazo.getEstadoRevisarDoc(), existe::setEstadoRevisarDoc);
        actualizarCampoSiNoNulo(personalReemplazo.getEstadoRevisarEval(), existe::setEstadoRevisarEval);
        actualizarCampoSiNoNulo(personalReemplazo.getEstadoEvalDoc(), existe::setEstadoEvalDoc);
        actualizarCampoSiNoNulo(personalReemplazo.getEstadoAprobacionInforme(), existe::setEstadoAprobacionInforme);
        actualizarCampoSiNoNulo(personalReemplazo.getEstadoAprobacionAdenda(), existe::setEstadoAprobacionAdenda);
        actualizarCampoSiNoNulo(personalReemplazo.getEstadoDocIniServ(), existe::setEstadoDocIniServ);
        actualizarCampoSiNoNulo(personalReemplazo.getEstadoEvalDocIniServ(), existe::setEstadoEvalDocIniServ);

        logger.info("actualizar - actualizando_Ex {}:",existe);
        AuditoriaUtil.setAuditoriaActualizacion(existe,contexto);
        return reemplazoDao.save(existe);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo eliminarPropuesta(PersonalReemplazoDTO personalReemplazo, Contexto contexto) {
        Long id = personalReemplazo.getIdReemplazo();
        if (id == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }
        PersonalReemplazo entity = reemplazoDao.findById(id)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));
        //Eliminar documentos adjuntos
        Long idSeccion = listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.SECCION_DOC_REEMPLAZO.PERSONAL_PROPUESTO).get(0).getIdListadoDetalle();
        logger.info("eliminar propuesta  - idSeccion {}:",idSeccion);

        if (!documentoReemDao.existsByIdReemplazoPersonalAndSeccion_IdListadoDetalle(id,idSeccion)) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE);
        }
        List<Long> ids = documentoReemDao.findIdsByReemplazoAndSeccion(id, idSeccion);
        archivoDao.deleteByDocumentoIds(ids);
        documentoReemDao.deleteByIdIn(ids);
        //Quitar personal
        entity.setPerfil(null);
        entity.setPersonaPropuesta(null);
        AuditoriaUtil.setAuditoriaRegistro(entity,contexto);
        return reemplazoDao.save(entity);
    }

    @Override
    @Transactional
    public PersonalReemplazo registrar(PersonalReemplazoDTO personalReemplazoIN, Contexto contexto) {
        Long idReemplazo = personalReemplazoIN.getIdReemplazo();
        if (idReemplazo == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }
        logger.info("registrar - idReemplazo: {}", idReemplazo);
        PersonalReemplazo personalReemplazo = reemplazoDao.findById(idReemplazo)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.PERSONAL_REEMPLAZO_NO_EXISTE));
        //Validar que exista un documento adjunto 3.Solicitud reemplazo supervisor
        String listadoSeccion = Constantes.LISTADO.SECCIONES_REEMPLAZO_PERSONAL;
        String descSeccion2 = Constantes.LISTADO.SECCION_DOC_REEMPLAZO.PERSONAL_PROPUESTO;
        String descSeccion3 = Constantes.LISTADO.SECCION_DOC_REEMPLAZO.SOLICITUD_REEMPLAZO_SUPERVISOR;
        ListadoDetalle seccion2 = listadoDetalleDao.obtenerListadoDetalle(listadoSeccion,descSeccion2);
        ListadoDetalle seccion3 = listadoDetalleDao.obtenerListadoDetalle(listadoSeccion,descSeccion3);
        if (!documentoReemDao.existsByIdReemplazoPersonalAndSeccion_IdListadoDetalle(
                personalReemplazo.getIdReemplazo(),seccion3.getIdListadoDetalle())) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTO_REEMPLAZO_NO_EXISTE);
        }


        String listadoSolicitud = Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO;
        personalReemplazo.setEstadoReemplazo (
                listadoDetalleDao.obtenerListadoDetalle(listadoSolicitud,
                        Constantes.LISTADO.ESTADO_SOLICITUD.EN_EVALUACION)
        );
        personalReemplazo.setEstadoEvalDoc(
                listadoDetalleDao.obtenerListadoDetalle(listadoSolicitud,
                        Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)
        );
        logger.info("personalReemplazo: {}", personalReemplazo);
        Long idSolicitud = personalReemplazo.getIdSolicitud();
        if (idSolicitud == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA);
        }
        logger.info("registrar - idSolicitud: {}", idSolicitud);
        Supervisora personalBaja = personalReemplazo.getPersonaBaja();
        if (personalBaja == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONA_BAJA);
        }
        logger.info("registrar - personalBaja: {}", personalBaja);
        PropuestaProfesional propuestaProfesional = propuestaProfesionalDao.listarXSolicitud(idSolicitud, personalBaja.getIdSupervisora());
        /*
        if (propuestaProfesional == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROFESIONAL_NO_EXISTE);
        }
         */
        Supervisora personaPropuesta = personalReemplazo.getPersonaPropuesta();
        if (personaPropuesta == null){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONA_PROPUESTA);
        }
        logger.info("registrar - personaPropuesta: {}", personaPropuesta);

        SupervisoraMovimiento supervisoraMovimiento = new SupervisoraMovimiento();

        if (propuestaProfesional != null){
            propuestaProfesional.setSupervisora(personaPropuesta);
            supervisoraMovimiento.setSector(propuestaProfesional.getSector());
            supervisoraMovimiento.setSubsector(propuestaProfesional.getSubsector());
            supervisoraMovimiento.setPropuestaProfesional(propuestaProfesional);
        }

        supervisoraMovimiento.setSupervisora(personaPropuesta);
        supervisoraMovimiento.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO));
        supervisoraMovimiento.setTipoMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.CODIGO, Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.AUTOMATICO));
        supervisoraMovimiento.setMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.REEMPLAZO_PERSONAL));
        supervisoraMovimiento.setAccion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.BLOQUEO));
        supervisoraMovimiento.setFechaRegistro(new Date());
        logger.info("registrar - supervisoraMovimiento: {}", supervisoraMovimiento);
        supervisoraMovimientoService.guardar(supervisoraMovimiento,contexto);
        AuditoriaUtil.setAuditoriaActualizacion(personalReemplazo,contexto);
        PersonalReemplazo personalReemplazoOUT = reemplazoDao.save(personalReemplazo);
        SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
        //Agregar documentos adjuntos en SIGED
        List<Long> idsSeccion = Arrays.asList(
                seccion2.getIdListadoDetalle(),
                seccion3.getIdListadoDetalle()
        );
        List<DocumentoReemplazo> documentos = documentoReemDao.obtenerPorIdReemplazoSecciones(
                personalReemplazoOUT.getIdReemplazo(),idsSeccion);
        procesarDocumentosReemplazo(documentos, sicoesSolicitud, contexto);
        //
        logger.info("registrar - sicoesSolicitud: {}", sicoesSolicitud);
        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                Constantes.LISTADO.TIPO_ARCHIVO.CONSOLIDADO_DOCUMENTOS);
        if (tipoArchivo == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_ARCHIVO_NO_EXISTE);
        }
        logger.info("registrar - tipoArchivo: {}", tipoArchivo);
        logger.info("registrar - contexto: {}", contexto);
        Archivo archivo = generarArchivoSigedRegistrar(personalReemplazoOUT, tipoArchivo, contexto);
        Optional<Usuario> usuarioExterno = usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.EVALUADOR_CONTRATOS).stream()
                .findFirst()
                .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));
        enviarNotificacionByRolEvaluador(usuarioExterno.get(), sicoesSolicitud, contexto);
        enviarNotificacionDesvinculacion(usuarioExterno.get(), sicoesSolicitud, contexto);
        personalReemplazoOUT.setArchivo(archivo);
        return personalReemplazoOUT;
    }

    private Archivo generarArchivoSigedRegistrar(PersonalReemplazo personalReemplazo, ListadoDetalle tipoArchivo, Contexto contexto) {
        Archivo archivo = generarReporteRegistrar(personalReemplazo, ArchivoUtil.obtenerNombreArchivo(tipoArchivo));
        archivo.setIdReemplazoPersonal(personalReemplazo.getIdReemplazo());
        archivo.setTipoArchivo(tipoArchivo);
        return archivoService.guardarPorPersonalReemplazo(archivo, contexto);
    }

    private Archivo generarReporteRegistrar(PersonalReemplazo personalReemplazo, String nombreArchivo) {
        Archivo archivo = new Archivo();
        archivo.setNombre(nombreArchivo);
        archivo.setNombreReal(nombreArchivo);
        archivo.setTipo(TIPO_ARCHIVO);
        ByteArrayOutputStream output = null;
        JasperPrint print = null;
        try (
                InputStream isLogoSicoes = Files.newInputStream(Paths.get(pathJasper + LOGO_SICOES));
                InputStream isLogoOsinergmin = Files.newInputStream(Paths.get(pathJasper + LOGO_OSINERGMIN));
            )
        {
            File jrxml = new File(pathJasper + FORMATO_04);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(SUBREPORT_DIR, pathJasper);
            parameters.put(P_LOGO_SICOES, isLogoSicoes);
            parameters.put(P_LOGO_OSINERGMIN, isLogoOsinergmin);
            parameters.put(P_TITULO, "CONSOLIDADO DE DOCUMENTOS CARGADOS REEMPLAZO PERSONAL PROPUESTO");
            parameters.put(P_INTRODUCCION, "La carga de documentos de inicio de servicio ha sido ingresada satisfactoriamente. A continuación, la lista de documentos cargados.");
            parameters.put(P_SUBTITULO_1, "5. PERSONA JURÍDICA");
            parameters.put(P_SUBTITULO_2, "6. DOCUMENTOS PRESENTADOS");
            parameters.put(P_MOSTRAR_CABECERA_2, Boolean.FALSE);
            parameters.put(P_MOSTRAR_CABECERA_4, Boolean.TRUE);
            parameters.put(P_MOSTRAR_DETALLE_2, Boolean.FALSE);
            parameters.put(P_MOSTRAR_DETALLE_4, Boolean.TRUE);
            parameters.put(P_MOSTRAR_ADICIONAL_2, Boolean.FALSE);
            parameters.put(P_MOSTRAR_ADICIONAL_4, Boolean.FALSE);
            Long idSolicitud = personalReemplazo.getIdSolicitud();
            if (idSolicitud == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA);
            }
            logger.info("Reporte registrar - idSolicitud: {}", idSolicitud);
            SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                    .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
            logger.info("Reporte registrar - sicoesSolicitud: {}", sicoesSolicitud);
            String numeroExpediente = sicoesSolicitud.getNumeroExpediente();
            if (numeroExpediente == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_EXPEDIENTE_NO_ENVIADO);
            }
            logger.info("Reporte registrar - numeroExpediente: {}", numeroExpediente);
            personalReemplazo.setNumeroExpediente(numeroExpediente);
            Supervisora supervisora = sicoesSolicitud.getSupervisora();
            logger.info("supervisora: {}", supervisora);
            personalReemplazo.setSupervisora(supervisora);
            List<Archivo> archivos = new ArrayList<>();
            Archivo archivoBD = new Archivo();
            ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO,
                    CONTRATO_LABORAL);
            logger.info("tipoArchivo: {}", tipoArchivo);
            archivoBD.setTipoArchivo(tipoArchivo);
            archivos.add(archivoBD);
            personalReemplazo.setArchivos(archivos);
            List<PersonalReemplazo> personalesReemplazo = java.util.Collections.singletonList(personalReemplazo);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(personalesReemplazo, true);
            JasperReport jasperReport = archivoUtil.getJasperCompilado(jrxml);
            print = JasperFillManager.fillReport(jasperReport, parameters, ds);
            output = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, output);
        } catch (Exception e) {
            logger.error("Error al generar el reporte: {}", e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SICOES_SOLICITUD_GUARDAR_FORMATO_04, e);
        }
        byte[] bytesSalida = output.toByteArray();
        archivo.setPeso((long) bytesSalida.length);
        archivo.setNroFolio(1L);
        archivo.setContenido(bytesSalida);
        return archivo;
    }

    private void registrarExpedienteSiged(Archivo archivo, PersonalReemplazo personalReemplazo, String asunto) {
        List<File> archivosAlfresco = new ArrayList<>();
        ExpedienteInRO expedienteInRO = crearExpediente(
                personalReemplazo,
                Integer.parseInt(env.getProperty(TIPO_DOCUMENTO_CREAR)),
                asunto
        );
        File file = fileRequerimiento(archivo, personalReemplazo.getIdReemplazo());
        archivosAlfresco.add(file);
        ExpedienteOutRO expedienteOutRO = null;
        try {
            expedienteOutRO = sigedApiConsumer.crearExpediente(expedienteInRO, archivosAlfresco);
            logger.info("SIGED RESULT: {}", expedienteOutRO.getMessage());
            if (expedienteOutRO.getResultCode() != 1) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO, expedienteOutRO.getMessage());
            }
        } catch (Exception e) {
            logger.error("Error al agregar documento en SIGED", e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO, expedienteOutRO.getMessage());
        }
    }

    private void adjuntarDocumentoSiged(Archivo archivo, PersonalReemplazo personalReemplazo, String asunto) {
        List<File> archivosAlfresco = new ArrayList<>();
        ExpedienteInRO expedienteInRO = crearExpediente(
                personalReemplazo,
                Integer.parseInt(env.getProperty(TIPO_DOCUMENTO_CREAR)),
                asunto
        );
        File file = fileRequerimiento(archivo, personalReemplazo.getIdReemplazo());
        archivosAlfresco.add(file);
        try {
            DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
            logger.info("SIGED RESULT: {}", documentoOutRO.getMessage());
            if (documentoOutRO.getResultCode() != 1) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO, documentoOutRO.getMessage());
            }
        } catch (Exception e) {
            logger.error("Error al agregar documento en SIGED", e);
        }
    }

    private ExpedienteInRO crearExpediente(PersonalReemplazo personalReemplazo, Integer codigoTipoDocumento, String asunto) {
        ExpedienteInRO expediente = new ExpedienteInRO();
        DocumentoInRO documento = new DocumentoInRO();
        ClienteListInRO clientes = new ClienteListInRO();
        ClienteInRO cs = new ClienteInRO();
        List<ClienteInRO> cliente = new ArrayList<>();
        DireccionxClienteListInRO direcciones = new DireccionxClienteListInRO();
        DireccionxClienteInRO d = new DireccionxClienteInRO();
        List<DireccionxClienteInRO> direccion = new ArrayList<>();
        expediente.setProceso(Integer.parseInt(env.getProperty("crear.expediente.parametros.proceso")));
        expediente.setDocumento(documento);
        Long idSolicitud = personalReemplazo.getIdSolicitud();
        if (idSolicitud == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA);
        }
        logger.info("Crear Expediente - idSolicitud: {}", idSolicitud);
        SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
        logger.info("Crear Expediente - sicoesSolicitud: {}", sicoesSolicitud);
        String numeroExpediente = sicoesSolicitud.getNumeroExpediente();
        if (numeroExpediente == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_EXPEDIENTE_NO_ENVIADO);
        }
        logger.info("Crear Expediente - numeroExpediente: {}", numeroExpediente);
        expediente.setNroExpediente(numeroExpediente);
        documento.setAsunto(asunto);
        documento.setAppNameInvokes(siglaProyecto);
        documento.setCodTipoDocumento(codigoTipoDocumento);
        documento.setNroFolios(Integer.parseInt(env.getProperty("crear.expediente.parametros.crea.folio")));
        documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
        if (Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.respuesta.solicitud.pn")) == codigoTipoDocumento) {
            documento.setFirmante(Integer.parseInt(env.getProperty("siged.firmante.informe.respuesta.id.usuario")));
        }
        cs.setCodigoTipoIdentificacion(Integer.parseInt(env.getProperty(TIPO_CLIENTE)));
        cs.setNombre("OSINERGMIN");
        cs.setApellidoPaterno("-");
        cs.setApellidoMaterno("-");
        cs.setRazonSocial("OSINERGMIN");
        cs.setNroIdentificacion(osiDocumento);
        cs.setTipoCliente(Integer.parseInt(env.getProperty(TIPO_CLIENTE)));
        cliente.add(cs);
        d.setDireccion("-");
        d.setDireccionPrincipal(true);
        d.setEstado(env.getProperty("crear.expediente.parametros.direccion.estado").charAt(0));
        d.setTelefono("-");
        d.setUbigeo(Integer.parseInt(env.getProperty("siged.ws.cliente.osinergmin.ubigeo")));
        direccion.add(d);
        direcciones.setDireccion(direccion);
        cs.setDirecciones(direcciones);
        clientes.setCliente(cliente);
        documento.setClientes(clientes);
        documento.setEnumerado(env.getProperty("crear.expediente.parametros.enumerado").charAt(0));
        documento.setEstaEnFlujo(env.getProperty("crear.expediente.parametros.esta.en.flujo").charAt(0));
        documento.setFirmado(env.getProperty("crear.expediente.parametros.firmado").charAt(0));
        documento.setCreaExpediente(env.getProperty("crear.expediente.parametros.crea.expediente").charAt(0));
        documento.setPublico(env.getProperty("crear.expediente.parametros.crea.publico").charAt(0));
        return expediente;
    }

    private File fileRequerimiento(Archivo archivo, Long idReemplazo) {
        try {
            String dirPath = pathTemporal + File.separator + "temporales" + File.separator + idReemplazo;
            File dir = new File(dirPath);
            if (!dir.exists()) {
                boolean creado = dir.mkdirs();
                if (!creado) {
                    logger.warn("No se pudo crear el directorio temporal: {}", dirPath);
                }
            }
            File file = new File(dirPath + File.separator + archivo.getNombre());
            FileUtils.writeByteArrayToFile(file, archivo.getContenido());
            archivo.setContenido(Files.readAllBytes(file.toPath()));
            return file;
        } catch (Exception e) {
            logger.error("Error al escribir archivo temporal", e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO);
        }
    }

    private void enviarNotificacionDesvinculacion(Usuario usuario, SicoesSolicitud solicitud, Contexto contexto) {
        String numeroExpediente = Optional.ofNullable(solicitud.getNumeroExpediente())
                .orElse("");
        String nombreSupervisora = obtenerNombreEmpresaSupervisora(solicitud);
        logger.info("Empresa supervisora {}", nombreSupervisora);
        notificacionContratoService.notificarDesvinculacionEmpresa(usuario, numeroExpediente, nombreSupervisora, contexto);
    }

    private static String obtenerNombreEmpresaSupervisora(SicoesSolicitud solicitud) {
        Supervisora empresa = solicitud.getSupervisora();
        String razonSocial = Optional.ofNullable(empresa.getNombreRazonSocial()).orElse("");
        String nombreSupervisora = null;
        if(!razonSocial.isEmpty()){
            nombreSupervisora = razonSocial;
        }else{
            String apellidoPaterno = empresa.getApellidoPaterno();
            String apellidoMaterno = empresa.getApellidoMaterno();
            nombreSupervisora = empresa.getNombres().concat(" ").concat(apellidoPaterno).concat(" ").concat(apellidoMaterno);
        }
        return nombreSupervisora;
    }

    private void enviarNotificacionByRolEvaluador(Usuario usuario, SicoesSolicitud sicoesSolicitud, Contexto contexto) {
        Rol rol = rolDao.obtenerCodigo(Constantes.ROLES.EVALUADOR_CONTRATOS);
        String numeroExpediente = Optional.ofNullable(sicoesSolicitud.getNumeroExpediente()).orElse("");
        notificacionContratoService.notificarReemplazoPersonalByEmail(usuario, numeroExpediente, rol.getNombre(), contexto);
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
    public PersonalReemplazo registrarRevDocumentos(RegistrarRevDocumentosRequestDTO request, Contexto contexto) {
        PersonalReemplazo personalReemplazoToUpdate = reemplazoDao
                .findById(request.getIdReemplazo())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));
        List<DocumentoReemplazo> listDocsAsociados = documentoReemDao
                .findByIdReemplazoPersonal(request.getIdReemplazo())
                .stream()
                .filter(doc -> !doc.getEvaluacion().isEmpty())
                .collect(Collectors.toList());
        if (Constantes.ROLES.RESPONSABLE_TECNICO.equals(request.getCodRol())) {
            List<EvaluarDocuReemplazo> evaluacionesResTecnico = listDocsAsociados
                    .stream()
                    .flatMap(doc -> doc.getEvaluacion().stream())
                    .filter(eval -> Constantes.ROLES.RESPONSABLE_TECNICO.equals(eval.getRol().getCodigo()))
                    .collect(Collectors.toList());
            return flujoRevisionResponsableTecnico(contexto, personalReemplazoToUpdate, evaluacionesResTecnico, new ArrayList<>());
        } else {
            List<EvaluarDocuReemplazo> listEvalInforme = listDocsAsociados
                    .stream()
                    .filter(doc -> Constantes.LISTADO.SECCION_DOC_REEMPLAZO.INFORME.equals(doc.getSeccion().getCodigo()))
                    .flatMap(doc -> doc.getEvaluacion().stream())
                    .filter(eval -> Constantes.ROLES.EVALUADOR_CONTRATOS.equals(eval.getRol().getCodigo()))
                    .collect(Collectors.toList());
            List<EvaluarDocuReemplazo> listEvalPersPropuestoSolSuperv = listDocsAsociados
                    .stream()
                    .filter(doc -> Arrays.asList(Constantes.LISTADO.SECCION_DOC_REEMPLAZO.PERSONAL_PROPUESTO,
                            Constantes.LISTADO.SECCION_DOC_REEMPLAZO.SOLICITUD_REEMPLAZO_SUPERVISOR)
                            .contains(doc.getSeccion().getCodigo()))
                    .flatMap(doc -> doc.getEvaluacion().stream())
                    .filter(eval -> Constantes.ROLES.EVALUADOR_CONTRATOS.equals(eval.getRol().getCodigo()))
                    .collect(Collectors.toList());
            return flujoRevisionEvaluadorContratos(contexto, personalReemplazoToUpdate, listEvalInforme, listEvalPersPropuestoSolSuperv);
        }
    }

    private PersonalReemplazo flujoRevisionResponsableTecnico(Contexto contexto,
                                                   PersonalReemplazo personalReemplazo,
                                                   List<EvaluarDocuReemplazo> listEvaluaciones,
                                                   List<DocumentoReemplazo> listDocsAsociados) {
        logger.info("flujo revision responsable tecnico contexto: {}", contexto);
        logger.info("flujo revision responsable tecnico listEvaluaciones: {}", listEvaluaciones);
        logger.info("flujo revision responsable tecnico listDocsAsociados: {}", listDocsAsociados);

        boolean allDocsConforme = checkAllDocsConforme(listEvaluaciones);
        logger.info("allDocsConforme: {}", allDocsConforme);

        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                Constantes.LISTADO.TIPO_ARCHIVO.FINALIZACION_EVALUACION);
        if (tipoArchivo == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_ARCHIVO_NO_EXISTE);
        }
        logger.info("Revision Responsable - tipoArchivo: {}", tipoArchivo);
        Archivo archivo = generarArchivoSigedRegistrarRevDocumentos(personalReemplazo, tipoArchivo, contexto);
        logger.info("archivo siged: {}", archivo);
        personalReemplazo.setArchivo(archivo);
        if (allDocsConforme) {
            procesarDocsConforme(contexto, personalReemplazo);
        } else {
            procesarDocsPreliminar(contexto, personalReemplazo);
        }
        logger.info("flujo revision responsable tecnico personalReemplazo: {}", personalReemplazo);
        return personalReemplazo;
    }

    private Archivo generarArchivoSigedRegistrarRevDocumentos(PersonalReemplazo personalReemplazo, ListadoDetalle tipoArchivo, Contexto contexto) {
        Archivo archivo = generarReporteRegistrarRevDocumentos(personalReemplazo, ArchivoUtil.obtenerNombreArchivo(tipoArchivo));
        archivo.setIdReemplazoPersonal(personalReemplazo.getIdReemplazo());
        archivo.setTipoArchivo(tipoArchivo);
        return archivoService.guardarPorPersonalReemplazo(archivo, contexto);
    }

    private Archivo generarReporteRegistrarRevDocumentos(PersonalReemplazo personalReemplazo, String nombreArchivo) {
        Archivo archivo = new Archivo();
        archivo.setNombre(nombreArchivo);
        archivo.setNombreReal(nombreArchivo);
        archivo.setTipo(TIPO_ARCHIVO);
        ByteArrayOutputStream output;
        JasperPrint print;
        try (
                InputStream isLogoSicoes = Files.newInputStream(Paths.get(pathJasper + LOGO_SICOES));
                InputStream isLogoOsinergmin = Files.newInputStream(Paths.get(pathJasper + LOGO_OSINERGMIN))
        ) {
            File jrxml = new File(pathJasper + FORMATO_04);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(SUBREPORT_DIR, pathJasper);
            parameters.put(P_LOGO_SICOES, isLogoSicoes);
            parameters.put(P_LOGO_OSINERGMIN, isLogoOsinergmin);
            parameters.put(P_TITULO, "FINALIZACIÓN DE EVALUACIÓN DE DOCUMENTOS DE INICIO DE SERVICIO");
            parameters.put(P_INTRODUCCION, "La evaluación de documentos de inicio de servicio finalizó.");
            parameters.put(P_SUBTITULO_1, PERSONA_JURIDICA_3);
            parameters.put(P_SUBTITULO_2, DOCUMENTOS_PRESENTADOS_4);
            parameters.put(P_MOSTRAR_CABECERA_2, Boolean.TRUE);
            parameters.put(P_MOSTRAR_CABECERA_4, Boolean.FALSE);
            parameters.put(P_MOSTRAR_DETALLE_2, Boolean.TRUE);
            parameters.put(P_MOSTRAR_DETALLE_4, Boolean.FALSE);
            parameters.put(P_MOSTRAR_ADICIONAL_2, Boolean.TRUE);
            parameters.put(P_MOSTRAR_ADICIONAL_4, Boolean.FALSE);
            Long idSolicitud = personalReemplazo.getIdSolicitud();
            if (idSolicitud == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA);
            }
            logger.info("registrar revision documentos - idSolicitud: {}", idSolicitud);
            SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                    .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
            logger.info("registrar revision documentos - sicoesSolicitud: {}", sicoesSolicitud);
            String numeroExpediente = sicoesSolicitud.getNumeroExpediente();
            if (numeroExpediente == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_EXPEDIENTE_NO_ENVIADO);
            }
            logger.info("registrar revision documentos - numeroExpediente: {}", numeroExpediente);
            personalReemplazo.setNumeroExpediente(numeroExpediente);
            Supervisora supervisora = sicoesSolicitud.getSupervisora();
            logger.info("registrar revision documentos - supervisora: {}", supervisora);
            personalReemplazo.setSupervisora(supervisora);
            List<Archivo> archivos = new ArrayList<>();
            ListadoDetalle tipoArchivoContratoLaboral = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO,
                    CONTRATO_LABORAL);
            logger.info("registrar revision documentos - tipoArchivoContratoLaboral: {}", tipoArchivoContratoLaboral);
            Archivo archivoBDContratoLaboral = new Archivo();
            archivoBDContratoLaboral.setTipoArchivo(tipoArchivoContratoLaboral);
            archivoBDContratoLaboral.setConforme(CONFORME);
            archivos.add(archivoBDContratoLaboral);
            ListadoDetalle tipoArchivoSctr = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO,
                    "SCTR");
            logger.info("registrar revision documentos - tipoArchivoSctr: {}", tipoArchivoSctr);
            Archivo archivoBDSctr = new Archivo();
            archivoBDSctr.setTipoArchivo(tipoArchivoSctr);
            archivoBDSctr.setConforme(CONFORME);
            archivos.add(archivoBDSctr);
            personalReemplazo.setArchivos(archivos);
            List<Archivo> adicionales = new ArrayList<>();
            Archivo adicionalBD = new Archivo();
            ListadoDetalle tipoArchivoAdicional = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ADICIONAL,
                    CONTRATO_ALQUILER_CAMIONETA);
            logger.info("registrar revision documentos - tipoArchivoAdicional: {}", tipoArchivoAdicional);
            adicionalBD.setTipoArchivo(tipoArchivoAdicional);
            adicionalBD.setConforme(CONFORME);
            adicionales.add(adicionalBD);
            personalReemplazo.setAdicionales(adicionales);
            List<PersonalReemplazo> personalesReemplazo = java.util.Collections.singletonList(personalReemplazo);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(personalesReemplazo, true);
            JasperReport jasperReport = archivoUtil.getJasperCompilado(jrxml);
            print = JasperFillManager.fillReport(jasperReport, parameters, ds);
            output = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, output);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SICOES_SOLICITUD_GUARDAR_FORMATO_04, e);
        }
        byte[] bytesSalida = output.toByteArray();
        archivo.setPeso((long) bytesSalida.length);
        archivo.setNroFolio(1L);
        archivo.setContenido(bytesSalida);
        return archivo;
    }

    private PersonalReemplazo flujoRevisionEvaluadorContratos(Contexto contexto,
                                                   PersonalReemplazo personalReemplazo,
                                                   List<EvaluarDocuReemplazo> listEvalInforme,
                                                   List<EvaluarDocuReemplazo> listEvalPersPropuestoSolSuperv) {
        logger.info("flujo revision - contexto: {}", contexto);
        logger.info("flujo revision - listEvalInforme: {}", listEvalInforme);
        logger.info("flujo revision - listEvalPersPropuestoSolSuperv: {}", listEvalPersPropuestoSolSuperv);
        boolean allDocsConformeInforme = !listEvalInforme.isEmpty()
                && listEvalInforme.stream()
                .allMatch(eval -> Constantes.LISTADO.SI_NO.SI.equals(eval.getConforme()));
        logger.info("flujo revision -  allDocsConformeInforme: {}", allDocsConformeInforme);
        boolean allDocsConformePersPropuestoSolSuperv = !listEvalPersPropuestoSolSuperv.isEmpty()
                && listEvalPersPropuestoSolSuperv.stream()
                .allMatch(eval -> Constantes.LISTADO.SI_NO.SI.equals(eval.getConforme()));
        logger.info("flujo revision -  allDocsConformePersPropuestoSolSuperv: {}", allDocsConformePersPropuestoSolSuperv);
        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                Constantes.LISTADO.TIPO_ARCHIVO.FINALIZACION_EVALUACION);
        if (tipoArchivo == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_ARCHIVO_NO_EXISTE);
        }
        logger.info("flujo revision - tipoArchivo: {}", tipoArchivo);
        Archivo archivo = generarArchivoSigedRegistrarRevDocumentos(personalReemplazo, tipoArchivo, contexto);
        logger.info("flujo revision - archivo: {}", archivo);
        personalReemplazo.setArchivo(archivo);
        if (!(allDocsConformeInforme && allDocsConformePersPropuestoSolSuperv)) {
            ListadoDetalle estadoPreliminar = listadoDetalleDao.listarListadoDetallePorCoodigo(
                            Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)
                    .stream()
                    .filter(resultado -> resultado.getOrden().compareTo(1L) == 0)
                    .findFirst()
                    .orElse(new ListadoDetalle());
            logger.info("flujo revision - estadoPreliminar: {}", estadoPreliminar);
            if (!allDocsConformeInforme) {
                personalReemplazo.setEstadoRevisarEval(estadoPreliminar);
            }
            if (!allDocsConformePersPropuestoSolSuperv) {
                personalReemplazo.setEstadoReemplazo(estadoPreliminar);
            }
            AuditoriaUtil.setAuditoriaActualizacion(personalReemplazo, contexto);
            reemplazoDao.save(personalReemplazo);
        } else {
            ListadoDetalle estadoEnProceso = listadoDetalleDao.listarListadoDetallePorCoodigo(
                            Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO)
                    .stream()
                    .filter(resultado -> resultado.getOrden().compareTo(1L) == 0)
                    .findFirst()
                    .orElse(new ListadoDetalle());
            logger.info("flujo revision - estadoEnProceso: {}", estadoEnProceso);
            personalReemplazo.setEstadoRevisarEval(estadoEnProceso);
            AuditoriaUtil.setAuditoriaActualizacion(personalReemplazo, contexto);
        }
        logger.info("flujo revision - personalReemplazo: {}", personalReemplazo);
        return personalReemplazo;
    }

    private String nombrePersonal(PersonalReemplazo personalReemplazo) {
        Supervisora supervisora = personalReemplazo.getPersonaPropuesta();
        ListadoDetalle tipoDocumento = supervisora.getTipoDocumento();
        if (tipoDocumento != null && tipoDocumento.getCodigo().equalsIgnoreCase(Constantes.LISTADO.TIPO_DOCUMENTO.DNI)) {
            String nombres = supervisora.getNombres();
            String apellidoPaterno = supervisora.getApellidoPaterno();
            String apellidoMaterno = supervisora.getApellidoMaterno();
            return String.format("%s %s %s",
                    nombres != null ? nombres.trim() : "",
                    apellidoPaterno != null ? apellidoPaterno.trim() : "",
                    apellidoMaterno != null ? apellidoMaterno.trim() : ""
            ).trim();
        } else if (tipoDocumento != null && tipoDocumento.getCodigo().equalsIgnoreCase(Constantes.LISTADO.TIPO_DOCUMENTO.RUC)) {
            String nombreRazonSocial = supervisora.getNombreRazonSocial();
            return nombreRazonSocial != null ? nombreRazonSocial.trim() : "";
        } else{
            return null;
        }
    }

    @Override
    public PersonalReemplazo registrarDocIniServ(PersonalReemplazoDTO personalReemplazo, Contexto contexto) {
        logger.info("registrar documento Inicio Servicio - personalReemplazo: {}", personalReemplazo);
        logger.info("registrar documento Inicio Servicio - contexto: {}", contexto);
        Long id = personalReemplazo.getIdReemplazo();
        if (id == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }
        logger.info("registrar documento Inicio Servicio - personalReemplazo.getIdReemplazo(): {}", id);
        PersonalReemplazo existe = reemplazoDao.findById(id)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO));
        if (existe.getPersonaPropuesta() == null){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONA_PROPUESTA);
        }
        logger.info("registrar documento Inicio Servicio - existe.getPersonaPropuesta: {}", existe.getPersonaPropuesta());
        if (existe.getPersonaBaja() == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONA_BAJA);
        }
        logger.info("registrar documento Inicio Servicio - existe.getPersonaBaja: {}", existe.getPersonaBaja());
        Long idPerfContrato = existe.getIdSolicitud();
        logger.info("registrar documento Inicio Servicio - idPerfContrato: {}", idPerfContrato);
        SicoesSolicitud solicitud = sicoesSolicitudDao.obtenerSolicitudDetallado(idPerfContrato);
        logger.info("registrar documento Inicio Servicio - solicitud: {}", solicitud);
        //Validar que existan documentos de inicio de servicio
        existe.setEstadoDocIniServ(listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.ESTADO_SOLICITUD.EN_EVALUACION).get(0));
        existe.setEstadoEvalDocIniServ(listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR).get(0));
        List<ListadoDetalle> detalleSeccion = listadoDetalleDao.listarListadoDetalle(Constantes.LISTADO.SECCIONES_REEMPLAZO_PERSONAL);
        logger.info("registrar documento Inicio Servicio - detalleSeccion: {}", detalleSeccion);
        List<Long> idsSeccion = detalleSeccion.stream()
                .map(ListadoDetalle::getIdListadoDetalle)
                .collect(Collectors.toList());
        logger.info("registrar documento Inicio Servicio - idsSeccion: {}", idsSeccion);
        List<DocumentoReemplazo> documentos = documentoReemDao.obtenerPorIdReemplazoSecciones(existe.getIdReemplazo(),idsSeccion);
        logger.info("registrar documento Inicio Servicio - documentos: {}", documentos);
        procesarDocumentosReemplazo(documentos, solicitud, contexto);

        String numeroExpediente = solicitud.getNumeroExpediente();
        logger.info("registrar documento Inicio Servicio - numeroExpediente: {}", numeroExpediente);
        Optional<Usuario> usuario = usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.RESPONSABLE_TECNICO)
                .stream()
                .findFirst()
                .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));
        logger.info("registrar documento Inicio Servicio - usuario: {}", usuario);
        if (usuario.isPresent()) {
            notificacionContratoService.notificarEvaluacionPendiente(usuario.get(), numeroExpediente, contexto);
        } else {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_RESPONSABLE_TECNICO_NO_EXISTE);
        }
        logger.info("registrar documento Inicio Servicio - existe: {}", existe);
        PersonalReemplazo personalReemplazoOUT = reemplazoDao.save(existe);
        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                Constantes.LISTADO.TIPO_ARCHIVO.CARGA_DOCUMENTOS);
        if (tipoArchivo == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_ARCHIVO_NO_EXISTE);
        }
        logger.info("registrar documento Inicio Servicio - tipoArchivo: {}", tipoArchivo);
        Archivo archivo = generarArchivoSigedRegistrarDocIniServ(existe, tipoArchivo, contexto);
        logger.info("registrar documento Inicio Servicio - archivo: {}", archivo);
        personalReemplazoOUT.setArchivo(archivo);
        return personalReemplazoOUT;
    }

    private Archivo generarArchivoSigedRegistrarDocIniServ(PersonalReemplazo personalReemplazo, ListadoDetalle tipoArchivo, Contexto contexto) {
        Archivo archivo = generarReporteRegistrarDocIniServ(personalReemplazo, ArchivoUtil.obtenerNombreArchivo(tipoArchivo));
        archivo.setIdReemplazoPersonal(personalReemplazo.getIdReemplazo());
        archivo.setTipoArchivo(tipoArchivo);
        Archivo archivoDB = archivoService.guardarPorPersonalReemplazo(archivo, contexto);
        if (Objects.equals(tipoArchivo.getCodigo(), Constantes.LISTADO.TIPO_ARCHIVO.CARGA_DOCUMENTOS)) {
            registrarExpedienteSiged(archivoDB, personalReemplazo, cargaDocumentos);
        } else {
            adjuntarDocumentoSiged(archivoDB, personalReemplazo, cargaDocumentos);
        }
        return archivoDB;
    }

    private Archivo generarReporteRegistrarDocIniServ(PersonalReemplazo personalReemplazo, String nombreArchivo) {
        Archivo archivo = new Archivo();
        archivo.setNombre(nombreArchivo);
        archivo.setNombreReal(nombreArchivo);
        archivo.setTipo(TIPO_ARCHIVO);
        ByteArrayOutputStream output;
        JasperPrint print;
        try (
                InputStream isLogoSicoes = Files.newInputStream(Paths.get(pathJasper + LOGO_SICOES));
                InputStream isLogoOsinergmin = Files.newInputStream(Paths.get(pathJasper + LOGO_OSINERGMIN))
        ) {
            File jrxml = new File(pathJasper + FORMATO_04);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(SUBREPORT_DIR, pathJasper);
            parameters.put(P_LOGO_SICOES, isLogoSicoes);
            parameters.put(P_LOGO_OSINERGMIN, isLogoOsinergmin);
            parameters.put(P_TITULO, "CARGAR DOCUMENTOS DE INICIO DE SERVICIO");
            parameters.put(P_INTRODUCCION, "La carga de documentos de inicio de servicio ha sido ingresada satisfactoriamente. A continuación, la lista de documentos cargados.");
            parameters.put(P_SUBTITULO_1, "1. PERSONA JURÍDICA");
            parameters.put(P_SUBTITULO_2, "2. DOCUMENTOS PRESENTADOS");
            parameters.put(P_MOSTRAR_CABECERA_2, Boolean.FALSE);
            parameters.put(P_MOSTRAR_CABECERA_4, Boolean.TRUE);
            parameters.put(P_MOSTRAR_DETALLE_2, Boolean.FALSE);
            parameters.put(P_MOSTRAR_DETALLE_4, Boolean.TRUE);
            parameters.put(P_MOSTRAR_ADICIONAL_2, Boolean.FALSE);
            parameters.put(P_MOSTRAR_ADICIONAL_4, Boolean.TRUE);
            Long idSolicitud = personalReemplazo.getIdSolicitud();
            if (idSolicitud == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA);
            }
            logger.info("reporte documento Inicio Servicio - idSolicitud: {}", idSolicitud);
            SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                    .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
            logger.info("reporte documento Inicio Servicio - sicoesSolicitud: {}",sicoesSolicitud);
            String numeroExpediente = sicoesSolicitud.getNumeroExpediente();
            if (numeroExpediente == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_EXPEDIENTE_NO_ENVIADO);
            }
            logger.info("reporte documento Inicio Servicio - numeroExpediente: {}", numeroExpediente);
            personalReemplazo.setNumeroExpediente(numeroExpediente);
            Supervisora supervisora = sicoesSolicitud.getSupervisora();
            logger.info("reporte documento Inicio Servicio - supervisora: {}", supervisora);
            personalReemplazo.setSupervisora(supervisora);
            List<Archivo> archivos = new ArrayList<>();
            Archivo archivoBD = new Archivo();
            ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO,
                    CONTRATO_LABORAL);
            logger.info("reporte documento Inicio Servicio -tipoArchivo: {}", tipoArchivo);
            archivoBD.setTipoArchivo(tipoArchivo);
            archivoBD.setNombre("ContratoLaboral.PDF");
            archivoBD.setNroFolio(1L);
            archivoBD.setPeso(24576L);
            archivos.add(archivoBD);
            personalReemplazo.setArchivos(archivos);
            List<Archivo> adicionales = new ArrayList<>();
            Archivo adicionalBD = new Archivo();
            ListadoDetalle tipoArchivoAdicional = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ADICIONAL,
                    CONTRATO_ALQUILER_CAMIONETA);
            logger.info("reporte documento Inicio Servicio -tipoArchivoAdicional: {}", tipoArchivoAdicional);
            adicionalBD.setTipoArchivo(tipoArchivoAdicional);
            adicionalBD.setNombre("Contratofinal.PDF");
            adicionalBD.setNroFolio(1L);
            adicionalBD.setPeso(24576L);
            adicionales.add(adicionalBD);
            personalReemplazo.setAdicionales(adicionales);
            List<PersonalReemplazo> personalesReemplazo = java.util.Collections.singletonList(personalReemplazo);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(personalesReemplazo, true);
            JasperReport jasperReport = archivoUtil.getJasperCompilado(jrxml);
            print = JasperFillManager.fillReport(jasperReport, parameters, ds);
            output = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, output);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SICOES_SOLICITUD_GUARDAR_FORMATO_04, e);
        }
        byte[] bytesSalida = output.toByteArray();
        archivo.setPeso((long) bytesSalida.length);
        archivo.setNroFolio(1L);
        archivo.setContenido(bytesSalida);
        return archivo;
    }

    @Override
    public ExpedienteInRO crearExpedienteAgregarDocumentos(SicoesSolicitud solicitud, Contexto contexto){
        String codExpediente = null;
        if (solicitud.getIdSolicitudPadre() != null){
            SicoesSolicitud solicitudPadre = sicoesSolicitudDao.findById(solicitud.getIdSolicitudPadre()).orElse(null);
            if (solicitudPadre !=null){
                codExpediente = solicitudPadre.getNumeroExpediente();
            }
        } else {
            codExpediente = solicitud.getNumeroExpediente();
        }
        Integer codigoTipoDocumento = Integer.parseInt(Objects.requireNonNull(env.getProperty(TIPO_DOCUMENTO_CREAR)));

        ExpedienteInRO expediente = new ExpedienteInRO();
        DocumentoInRO documento = new DocumentoInRO();
        ClienteListInRO clientes = new ClienteListInRO();
        ClienteInRO cs = new ClienteInRO();
        List<ClienteInRO> cliente = new ArrayList<>();
        DireccionxClienteListInRO direcciones = new DireccionxClienteListInRO();
        DireccionxClienteInRO d = new DireccionxClienteInRO();
        List<DireccionxClienteInRO> direccion = new ArrayList<>();
        expediente.setProceso(Integer.parseInt(
                Objects.requireNonNull(env.getProperty("crear.expediente.parametros.proceso"))));
        expediente.setDocumento(documento);
        expediente.setNroExpediente(codExpediente);
        documento.setAsunto("Solicitud de Perfeccionamiento de Contrato - Reemplazo");
        documento.setAppNameInvokes(siglaProyecto);
        cs.setCodigoTipoIdentificacion(1);
        if (cs.getCodigoTipoIdentificacion() == 1) {
            cs.setNombre(solicitud.getSupervisora().getNombreRazonSocial());
            cs.setApellidoPaterno("-");
            cs.setApellidoMaterno("-");
        }
        cs.setRazonSocial(solicitud.getSupervisora().getNombreRazonSocial());
        cs.setNroIdentificacion(solicitud.getSupervisora().getNumeroDocumento());
        cs.setTipoCliente(Integer.parseInt(
                Objects.requireNonNull(env.getProperty(TIPO_CLIENTE))));
        cliente.add(cs);
        d.setDireccion(solicitud.getSupervisora().getDireccion());
        d.setDireccionPrincipal(true);
        d.setEstado(
                Objects.requireNonNull(env.getProperty("crear.expediente.parametros.direccion.estado")).charAt(0));
        d.setTelefono(solicitud.getSupervisora().getTelefono1());
        if (solicitud.getSupervisora().getCodigoDistrito() != null) {
            d.setUbigeo(Integer.parseInt(solicitud.getSupervisora().getCodigoDistrito()));
        }
        direccion.add(d);
        direcciones.setDireccion(direccion);
        cs.setDirecciones(direcciones);
        clientes.setCliente(cliente);
        documento.setClientes(clientes);
        documento.setCodTipoDocumento(codigoTipoDocumento);
        documento.setEnumerado(Objects.requireNonNull(env.getProperty("crear.expediente.parametros.enumerado")).charAt(0));
        documento.setEstaEnFlujo(Objects.requireNonNull(env.getProperty("crear.expediente.parametros.esta.en.flujo")).charAt(0));
        documento.setFirmado(Objects.requireNonNull(env.getProperty("crear.expediente.parametros.firmado")).charAt(0));
        documento.setCreaExpediente(Objects.requireNonNull(env.getProperty("crear.expediente.parametros.crea.expediente")).charAt(0));
        documento.setPublico(Objects.requireNonNull(env.getProperty("crear.expediente.parametros.crea.publico")).charAt(0));
        documento.setNroFolios(Integer.parseInt(Objects.requireNonNull(env.getProperty("crear.expediente.parametros.crea.folio"))));
        documento.setUsuarioCreador(Integer.parseInt(Objects.requireNonNull(env.getProperty("siged.bus.server.id.usuario"))));
        logger.info("DOC_EXPEDIENTE {}",documento);
        logger.info("EXPEDIENTE_REEMPLAZO_PERSONAL {}",expediente);
        return expediente;
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
    public List<AprobacionReemp> buscarAprobacion(String requerimiento, String corol, Long tipoaprob , Long estadoaprob, Long tiposolicitud,
                                                  Long idcontratista, Long numexpediente) {
        List<AprobacionReemp>  resultado = new ArrayList<>();

         Rol rolUsuarioInterno = rolDao.obtenerCodigo(corol);

       if(rolUsuarioInterno.getCodigo().equals(Constantes.ROLES.APROBADOR_TECNICO) && requerimiento.equals(Constantes.REQUERIMIENTO.EVAL_INFO_APROB_G2_GER_DIV)){
            resultado =  aprobacionReempDao.buscarEvalInfAprobTecG2(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente, listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.TIPO_APROBACION.CODIGO,Constantes.LISTADO.TIPO_APROBACION.APROBAR).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO).getIdListadoDetalle(), rolUsuarioInterno.getIdRol());
        }
       if(rolUsuarioInterno.getCodigo().equals(Constantes.ROLES.APROBADOR_TECNICO) && requerimiento.equals(Constantes.REQUERIMIENTO.EVAL_INFO_APROB_G3_GER_LIN)){
            resultado =  aprobacionReempDao.buscarEvalInfAprobTecG3(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente, listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.TIPO_APROBACION.CODIGO,Constantes.LISTADO.TIPO_APROBACION.APROBAR).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO).getIdListadoDetalle(), rolUsuarioInterno.getIdRol());
        }
        if(rolUsuarioInterno.getCodigo().equals(Constantes.ROLES.EVALUADOR_CONTRATOS) && requerimiento.equals(Constantes.REQUERIMIENTO.APROB_EVAL_CONTR)){
            resultado = aprobacionReempDao.buscarAprobEvalRolContr(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente, listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.TIPO_APROBACION.CODIGO,Constantes.LISTADO.TIPO_APROBACION.APROBAR).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ADENDA.CODIGO,Constantes.LISTADO.ESTADO_ADENDA.ASIGNADO).getIdListadoDetalle(), rolUsuarioInterno.getIdRol());
        }
        if(rolUsuarioInterno.getCodigo().equals(Constantes.ROLES.G2_APROBADOR_ADMINISTRATIVO) && requerimiento.equals(Constantes.REQUERIMIENTO.VB_APROB_G2_APROB_ADMIN)){
            resultado = aprobacionReempDao.buscarVBAprobG2Admin(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente, listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.TIPO_APROBACION.CODIGO,Constantes.LISTADO.TIPO_APROBACION.VISTO_BUENO).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ADENDA.CODIGO,Constantes.LISTADO.ESTADO_ADENDA.ASIGNADO).getIdListadoDetalle(),rolUsuarioInterno.getIdRol());
        }
       if(rolUsuarioInterno.getCodigo().equals(Constantes.ROLES.G3_APROBADOR_ADMINISTRATIVO) && requerimiento.equals(Constantes.REQUERIMIENTO.FIRMA_APROB_G3_APROB_ADMIN)){
            resultado = aprobacionReempDao.buscarFirmarAprobG3Admin(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente, listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.TIPO_APROBACION.CODIGO,Constantes.LISTADO.TIPO_APROBACION.FIRMAR).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ADENDA.CODIGO,Constantes.LISTADO.ESTADO_ADENDA.ASIGNADO).getIdListadoDetalle(),rolUsuarioInterno.getIdRol());
        }
       if(rolUsuarioInterno.getCodigo().equals(Constantes.ROLES.G4_APROBADOR_ADMINISTRATIVO) && requerimiento.equals(Constantes.REQUERIMIENTO.APROB_ADMIN_G4_GAF)){
            resultado =  aprobacionReempDao.buscarAprobG4AdmGAF(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente, listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.TIPO_APROBACION.CODIGO,Constantes.LISTADO.TIPO_APROBACION.FIRMAR).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ADENDA.CODIGO,Constantes.LISTADO.ESTADO_ADENDA.ASIGNADO).getIdListadoDetalle(), rolUsuarioInterno.getIdRol());
        }

        return resultado;
    }

    private String obtenerNombreSupervisora(PersonalReemplazo persoReemplazo) {
        Supervisora personaPropuesta = persoReemplazo.getPersonaPropuesta();
        String razonSocial = personaPropuesta.getNombreRazonSocial();
        this.logger.info("razon social juridica {} ", razonSocial);
        String nombreSupervisora = null;
        if(razonSocial!=null){
            nombreSupervisora = razonSocial;
        }else{
            String apellidoPaterno = personaPropuesta.getApellidoPaterno();
            String apellidoMaterno = personaPropuesta.getApellidoMaterno();
            nombreSupervisora = personaPropuesta.getNombres()
                    .concat(" ").concat(apellidoPaterno)
                    .concat(" ").concat(apellidoMaterno);
            this.logger.info("razon social personal natural {} ", razonSocial);
        }
        return nombreSupervisora;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Aprobacion updateAprobacion(AprobacionDTO aprobacion, Contexto contexto) {
        Optional<Aprobacion> aprobOpt = aprobacionDao.findById(aprobacion.getIdAprobacion());
        Aprobacion aprobacionFinal = aprobOpt.orElseThrow(() -> new RuntimeException("aprobacion no encontrada"));

        Optional<PersonalReemplazo> persoReempOpt = reemplazoDao.obtenerXIdAprobacion(aprobacionFinal.getIdAprobacion());
        PersonalReemplazo persoReempFinal = persoReempOpt.orElseThrow(() -> new RuntimeException("reemplazo personal no encontrado"));

        if (aprobacion.getDeObservacion() != null) {
            aprobacionFinal.setDeObservacion(aprobacion.getDeObservacion());
        }

        String numeroExpediente = obtenerNroExpEmpresa(persoReempFinal);

        switch (aprobacion.getRequerimiento()) {
            case Constantes.REQUERIMIENTO.EVAL_INF_APROB_TEC_G2:
                procesarEvaluacionAprobacionG2(aprobacion, aprobacionFinal, persoReempFinal, numeroExpediente, contexto);
                break;

            case Constantes.REQUERIMIENTO.EVAL_INF_APROB_TEC_G3:
                procesarEvaluacionAprobacionG3(aprobacion, aprobacionFinal, persoReempFinal, numeroExpediente, contexto);
                break;

            case Constantes.REQUERIMIENTO.APROB_EVAL_CONTR:
                procesarAprobacionAdenda(aprobacion, aprobacionFinal, persoReempFinal, contexto);
                break;

            default:
                throw new RuntimeException("Requerimiento no reconocido");
        }

        aprobacionFinal.setFecActualizacion(new Date());
        AuditoriaUtil.setAuditoriaRegistro(aprobacionFinal, contexto);
        return aprobacionDao.save(aprobacionFinal);
    }

    private String obtenerNroExpPersona(PersonalReemplazo personalReemplazo) {
        if (personalReemplazo == null) {
            return "";
        }

        Supervisora personaPropuesta = personalReemplazo.getPersonaPropuesta();
        return personaPropuesta != null ?
                Optional.ofNullable(personaPropuesta.getNumeroExpediente())
                        .orElse("") :
                "";
    }

    private String obtenerNroExpEmpresa(PersonalReemplazo personalReemplazo) {
        if (personalReemplazo == null) {
            return "";
        }
        SicoesSolicitud solicitud = sicoesSolicitudDao.findById(personalReemplazo.getIdSolicitud())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_EXISTE));
        return Optional.ofNullable(solicitud.getNumeroExpediente())
                .orElse("");
    }


    @Override
    public EvaluacionDocumentacion obtenerEvaluacionDocumentacion(Long id) {
         logger.info("obtenerEvaluacionDocumentacion");
          List<EvaluacionDocumentacion> lista = evaluacionDocumentacionDao.obtenerListado(id,
                         listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO,
                                 Constantes.LISTADO.ESTADO_SOLICITUD.EN_EVALUACION).getIdListadoDetalle());
         if(!lista.isEmpty()){ return lista.get(0);}
         else{return null;}


    }

    @Override
    public EvaluacionDocumentacionPP obtenerEvaluacionDocumentacionBPP(Long id , Long idsol) {
         logger.info("obtenerEvaluacionDocumentacionBPP");
         return evaluacionPPDao.obtenerListadoPP(id, idsol)
               .orElseThrow(() -> new RuntimeException("Evaluación de documentación no encontrada"));
    }

    @Override
    public Page<HistorialAprobReemp> listarHistorialReemp(Long idReemplazo, Pageable pageable ) {
        logger.info("listarHistorialReemp");
        return historialAprobReempDao.buscarHistorial(idReemplazo,pageable);
    }

    @Override
    public EvaluacionDocInicioServ obtenerEvaluacionDocInicioServicio(Long id) {
        logger.info("obtenerEvaluacionDocInicioServicio");
        return evaluacionDocInicioServDao.buscarEvalDocInicioServ(id);
    }

    @Override
    public List<DocumentoInicioServ> obtenerDocumentosInicioServicio(Long id, String seccion){
        logger.info("ObtenerDocumentosInicioServicio");
        List<DocumentoInicioServ>  resultado = new ArrayList<>();
        if (seccion.equals(Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO)){

            resultado = documentoInicioServDao.documentosInicioServicio(id, listadoDao
                    .obtenerPorCodigo(
                            Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO).getIdListado());
        }
        if (seccion.equals(Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ADICIONAL)){

            resultado = documentoInicioServDao.documentosInicioServicio(id, listadoDao
                    .obtenerPorCodigo(
                            Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ADICIONAL).getIdListado());
        }
        if (seccion.equals(Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ACTA_INICIO)){

            resultado = documentoInicioServDao.documentosInicioServicio(id, listadoDao
                    .obtenerPorCodigo(
                            Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ACTA_INICIO).getIdListado());
        }
        return resultado;
    }


    @Override
    public Boolean evaluarFechaDesvinculacion (Long id, Date fecha) {
        logger.info("evaluarFechaDesvinculacion");

        boolean resultado =false;
        PersonalReemplazo persoReemp = reemplazoDao.findById(id).orElseThrow(()  -> new RuntimeException("reemplazo personal no encontrada"));

        Contrato contrato = contratoDao.obtenerSolicitudPerfCont(persoReemp.getIdSolicitud()).orElseThrow(()  -> new RuntimeException("contrato no encontrado"));

            if(contrato.getFechaFinalContrato() == null) {
                throw new ValidacionException("No se encuentra la Fecha de Finalización de Contrato");
            }
             if (contrato.getFechaFinalContrato().before(persoReemp.getFeFechaDesvinculacion())) {
               throw new ValidacionException("La Fecha Desvinculación no puede ser posterior a la fecha de finalización del Contrato");
            }
            if(fecha.before(contrato.getFechaFinalContrato()) || fecha.equals(contrato.getFechaFinalContrato())){
              if (!fecha.equals(persoReemp.getFeFechaDesvinculacion())){
                  resultado = true;
              }
            }
        return resultado;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
	public PersonalReemplazo evaluarDocumentoInforme(Long id, Date fecha) {
       logger.info("evaluarDocumentoInforme");

       Optional<PersonalReemplazo> persoReempOpt = reemplazoDao.findById(id);
            PersonalReemplazo persoReempFinal = persoReempOpt.orElseThrow(()  -> new RuntimeException("Reemplazo personal no encontrada"));
            if(fecha == null) {
                throw new ValidacionException("No se encuentra la fecha");
            }
            persoReempFinal.setFeFechaDesvinculacion(fecha);
            reemplazoDao.save(persoReempFinal);
        return persoReempFinal;
    }

    @Override
	public List<DocumentoPP> obtenerDocumentoPPxSeccion(Long id, String seccion) {
       logger.info("obtenerDocumentoPPxSeccion");
       Long idseccion = 0L;
       if (seccion.equals(Constantes.SECCION_EVALUAR_DOCUMENTOS.INFORME)){

            idseccion = listadoDetalleDao
                    .obtenerListadoDetalle(Constantes.SECCION_EVALUAR_DOCUMENTOS.CODIGO, Constantes.SECCION_EVALUAR_DOCUMENTOS.INFORME).getIdListadoDetalle();
        }
        if (seccion.equals(Constantes.SECCION_EVALUAR_DOCUMENTOS.BAJA_PERSONAL_PROPUESTO)){

            idseccion =listadoDetalleDao
                    .obtenerListadoDetalle(Constantes.SECCION_EVALUAR_DOCUMENTOS.CODIGO, Constantes.SECCION_EVALUAR_DOCUMENTOS.BAJA_PERSONAL_PROPUESTO).getIdListadoDetalle();
        }
        if (seccion.equals(Constantes.SECCION_EVALUAR_DOCUMENTOS.PERSONAL_PROPUESTO)){

            idseccion =listadoDetalleDao
                    .obtenerListadoDetalle(Constantes.SECCION_EVALUAR_DOCUMENTOS.CODIGO,  Constantes.SECCION_EVALUAR_DOCUMENTOS.PERSONAL_PROPUESTO).getIdListadoDetalle();
        }
        if (seccion.equals(Constantes.SECCION_EVALUAR_DOCUMENTOS.SOLICITUD_REEMPLAZO_SUPERVISOR)){

            idseccion =listadoDetalleDao
                    .obtenerListadoDetalle( Constantes.SECCION_EVALUAR_DOCUMENTOS.CODIGO, Constantes.SECCION_EVALUAR_DOCUMENTOS.SOLICITUD_REEMPLAZO_SUPERVISOR).getIdListadoDetalle();
        }

        return documentoPPDao.buscarDocumentoPP(id,idseccion);
        }


    @Override
    @Transactional(rollbackFor = Exception.class)
	public Boolean evaluarDocumReemplazo(EvaluarDocuDTO evaluacion, Contexto contexto) {
       logger.info("evaluarDocumReemplazo");
           Boolean result = false;

           DocumentoReemplazo doc = documentoReemDao.findById(evaluacion.getIdDocumento())
                   .orElseThrow(()  -> new RuntimeException("documento no encontrado"));

       if(evaluacion.getIdEvalDocumento()!=null){
        Optional<EvaluarDocuReemplazo> evalOpt = evaluarDocuReemDao.findByIdDocumentoId(evaluacion.getIdEvalDocumento());
           EvaluarDocuReemplazo evalFinal = evalOpt.orElseThrow(()  -> new RuntimeException("evaluación no encontrada"));
           evalFinal.setDocumento(doc);
           evalFinal.setConforme(evaluacion.getConforme());
           evalFinal.setFechaEvaluacion(evaluacion.getFechaEvaluacion());
           AuditoriaUtil.setAuditoriaActualizacion(evalFinal,contexto);
           evaluarDocuReemDao.save(evalFinal);
           result = true;
       }else{
           EvaluarDocuReemplazo evalNuevo = new EvaluarDocuReemplazo();
           evalNuevo.setConforme(evaluacion.getConforme());
           evalNuevo.setFechaEvaluacion(evaluacion.getFechaEvaluacion());
           AuditoriaUtil.setAuditoriaRegistro(evalNuevo,contexto);
           evaluarDocuReemDao.save(evalNuevo);
           result = true;
       }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo registrarInicioServicioSolContr(PersonalReemplazo personalReemplazo,Boolean conforme, Contexto contexto) {
        Long id = personalReemplazo.getIdReemplazo();
        if (id == null) {
            throw new ValidacionException("No existe id");
        }

        PersonalReemplazo existe = reemplazoDao.findById(id)
                .orElseThrow(() -> new ValidacionException("Id personal no enviado"));

        if(existe.getEstadoAprobacionInforme() != listadoDetalleDao.listarListadoDetallePorCoodigo(
                    Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO).get(0)){
            throw new ValidacionException("La solicitud aún no cuenta con todas las aprobaciones");
        }
        Long idPerfContrato = existe.getIdSolicitud();
        SicoesSolicitud solicitud = sicoesSolicitudDao.obtenerSolicitudDetallado(idPerfContrato);
        Archivo archivo;
        if(conforme){
            solicitud.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.CONCLUIDO);
            solicitud.setDescripcionSolicitud(Constantes.DESC_PROCESO_PERF_CONTRATO.CONCLUIDO);
            existe.setEstadoReemplazo(listadoDetalleDao.listarListadoDetallePorCoodigo(
                    Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO).get(0));
            existe.setEstadoEvalDocIniServ(listadoDetalleDao.listarListadoDetallePorCoodigo(
                    Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO).get(0));
            ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                    Constantes.LISTADO.TIPO_ARCHIVO.FINALIZACION_EVALUACION);
            if (tipoArchivo == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_ARCHIVO_NO_EXISTE);
            }
            logger.info("inicio serv contrato - tipoArchivo: {}", tipoArchivo);
            archivo = generarArchivoSigedregistrarInicioServicioSolContrSI(existe, tipoArchivo, contexto);
        }else{
            ListadoDetalle tipoSctr = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO.CODIGO,
                    Constantes.LISTADO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO.SCTR);
            Long idListadoPersonal = listadoDao.obtenerPorCodigo( Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO)
                    .getIdListado();
            List<DocumentoInicioServ> docStcr = documentoInicioServDao.documentosInicioServicio(id, idListadoPersonal )
                    .stream()
                    .filter(documentoInicioServ -> documentoInicioServ.getIdTipoDocumento().equals(tipoSctr.getIdListadoDetalle()))
                    .collect(Collectors.toList());
            String sctr1 = Optional.ofNullable(docStcr.get(0).getNombreDocumento()).orElse("");
            String sctr2 = Optional.ofNullable(docStcr.get(1).getNombreDocumento()).orElse("");

            Long idListadoAdicional = listadoDao.obtenerPorCodigo( Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ADICIONAL)
                    .getIdListado();
            List<DocumentoInicioServ> docAdicional = documentoInicioServDao.documentosInicioServicio(id, idListadoAdicional);
            String nombrePersonal = nombrePersonal(personalReemplazo);
            notificacionContratoService.notificarRevDocumentos122(solicitud.getSupervisora(), nombrePersonal, sctr1, sctr2, docAdicional, contexto);

            solicitud.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.PRELIMINAR);
            solicitud.setDescripcionSolicitud(Constantes.DESC_PROCESO_PERF_CONTRATO.PRELIMINAR);
            ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                    Constantes.LISTADO.TIPO_ARCHIVO.EVALUACION);
            if (tipoArchivo == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_ARCHIVO_NO_EXISTE);
            }
            logger.info("inicio serv contrato - tipoArchivo: {}", tipoArchivo);
            archivo = generarArchivoSigedregistrarInicioServicioSolContrNO(existe, tipoArchivo, contexto);
        }
        existe.setArchivo(archivo);
        AuditoriaUtil.setAuditoriaRegistro(existe,contexto);
        return reemplazoDao.save(existe);
    }

    private Archivo generarArchivoSigedregistrarInicioServicioSolContrSI(PersonalReemplazo personalReemplazo, ListadoDetalle tipoArchivo, Contexto contexto) {
        Archivo archivo = generarReporteRegistrarInicioServicioSolContrSI(personalReemplazo, ArchivoUtil.obtenerNombreArchivo(tipoArchivo));
        archivo.setIdReemplazoPersonal(personalReemplazo.getIdReemplazo());
        archivo.setTipoArchivo(tipoArchivo);
        Archivo archivoDB = archivoService.guardarPorPersonalReemplazo(archivo, contexto);
        if (Objects.equals(tipoArchivo.getCodigo(), Constantes.LISTADO.TIPO_ARCHIVO.FINALIZACION_EVALUACION)) {
            registrarExpedienteSiged(archivoDB, personalReemplazo, finalizacionEvaluacion);
        } else {
            adjuntarDocumentoSiged(archivoDB, personalReemplazo, finalizacionEvaluacion);
        }
        return archivoDB;
    }

    private Archivo generarReporteRegistrarInicioServicioSolContrSI(PersonalReemplazo personalReemplazo, String nombreArchivo) {
        Archivo archivo = new Archivo();
        archivo.setNombre(nombreArchivo);
        archivo.setNombreReal(nombreArchivo);
        archivo.setTipo(TIPO_ARCHIVO);
        ByteArrayOutputStream output;
        JasperPrint print;
        try (
                InputStream isLogoSicoes = Files.newInputStream(Paths.get(pathJasper + LOGO_SICOES));
                InputStream isLogoOsinergmin = Files.newInputStream(Paths.get(pathJasper + LOGO_OSINERGMIN))
        ) {
            File jrxml = new File(pathJasper + FORMATO_04);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(SUBREPORT_DIR, pathJasper);
            parameters.put(P_LOGO_SICOES, isLogoSicoes);
            parameters.put(P_LOGO_OSINERGMIN, isLogoOsinergmin);
            parameters.put(P_TITULO, "FINALIZACIÓN DE EVALUACIÓN DE DOCUMENTOS DE INICIO DE SERVICIO");
            parameters.put(P_INTRODUCCION, "La evaluación de documentos de inicio de servicio finalizó.");
            parameters.put(P_SUBTITULO_1,PERSONA_JURIDICA_3);
            parameters.put(P_SUBTITULO_2, DOCUMENTOS_PRESENTADOS_4);
            parameters.put(P_MOSTRAR_CABECERA_2, Boolean.TRUE);
            parameters.put(P_MOSTRAR_CABECERA_4, Boolean.FALSE);
            parameters.put(P_MOSTRAR_DETALLE_2, Boolean.TRUE);
            parameters.put(P_MOSTRAR_DETALLE_4, Boolean.FALSE);
            parameters.put(P_MOSTRAR_ADICIONAL_2, Boolean.TRUE);
            parameters.put(P_MOSTRAR_ADICIONAL_4, Boolean.FALSE);
            Long idSolicitud = personalReemplazo.getIdSolicitud();
            if (idSolicitud == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA);
            }
            logger.info("reporte inicio serv contrato SI - idSolicitud: {}", idSolicitud);
            SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                    .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
            logger.info("reporte inicio serv contrato SI - sicoesSolicitud: {}",sicoesSolicitud);
            String numeroExpediente = sicoesSolicitud.getNumeroExpediente();
            if (numeroExpediente == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_EXPEDIENTE_NO_ENVIADO);
            }
            logger.info("reporte inicio serv contrato SI - numeroExpediente: {}", numeroExpediente);
            personalReemplazo.setNumeroExpediente(numeroExpediente);
            Supervisora supervisora = sicoesSolicitud.getSupervisora();
            logger.info("reporte inicio serv contrato SI - supervisora: {}", supervisora);
            personalReemplazo.setSupervisora(supervisora);
            List<Archivo> archivos = new ArrayList<>();
            ListadoDetalle tipoArchivoContratoLaboral = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO, CONTRATO_LABORAL);
            logger.info("reporte inicio serv contrato SI - tipoArchivoContratoLaboral: {}", tipoArchivoContratoLaboral);
            Archivo archivoBDContratoLaboral = new Archivo();
            archivoBDContratoLaboral.setTipoArchivo(tipoArchivoContratoLaboral);
            archivoBDContratoLaboral.setConforme(CONFORME+" Sí");
            archivos.add(archivoBDContratoLaboral);
            ListadoDetalle tipoArchivoSctr = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO, "SCTR");
            logger.info("reporte inicio serv contrato SI - tipoArchivoSctr: {}", tipoArchivoSctr);
            Archivo archivoBDSctr = new Archivo();
            archivoBDSctr.setTipoArchivo(tipoArchivoSctr);
            archivoBDSctr.setConforme(CONFORME+" Si");
            archivos.add(archivoBDSctr);
            personalReemplazo.setArchivos(archivos);
            List<Archivo> adicionales = new ArrayList<>();
            Archivo adicionalBD = new Archivo();
            ListadoDetalle tipoArchivoAdicional = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ADICIONAL,
                    CONTRATO_ALQUILER_CAMIONETA);
            logger.info("reporte inicio serv contrato SI - tipoArchivoAdicional: {}", tipoArchivoAdicional);
            adicionalBD.setTipoArchivo(tipoArchivoAdicional);
            adicionalBD.setConforme(CONFORME+" Sí");
            adicionales.add(adicionalBD);
            personalReemplazo.setAdicionales(adicionales);
            List<PersonalReemplazo> personalesReemplazo = java.util.Collections.singletonList(personalReemplazo);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(personalesReemplazo, true);
            JasperReport jasperReport = archivoUtil.getJasperCompilado(jrxml);
            print = JasperFillManager.fillReport(jasperReport, parameters, ds);
            output = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, output);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SICOES_SOLICITUD_GUARDAR_FORMATO_04, e);
        }
        byte[] bytesSalida = output.toByteArray();
        archivo.setPeso((long) bytesSalida.length);
        archivo.setNroFolio(1L);
        archivo.setContenido(bytesSalida);
        return archivo;
    }

    private Archivo generarArchivoSigedregistrarInicioServicioSolContrNO(PersonalReemplazo personalReemplazo, ListadoDetalle tipoArchivo, Contexto contexto) {
        Archivo archivo = generarReporteRegistrarInicioServicioSolContrNO(personalReemplazo, ArchivoUtil.obtenerNombreArchivo(tipoArchivo));
        archivo.setIdReemplazoPersonal(personalReemplazo.getIdReemplazo());
        archivo.setTipoArchivo(tipoArchivo);
        Archivo archivoDB = archivoService.guardarPorPersonalReemplazo(archivo, contexto);
        if (Objects.equals(tipoArchivo.getCodigo(), Constantes.LISTADO.TIPO_ARCHIVO.EVALUACION)) {
            registrarExpedienteSiged(archivoDB, personalReemplazo, evaluacion);
        } else {
            adjuntarDocumentoSiged(archivoDB, personalReemplazo, evaluacion);
        }
        return archivoDB;
    }

    private Archivo generarReporteRegistrarInicioServicioSolContrNO(PersonalReemplazo personalReemplazo, String nombreArchivo) {
        Archivo archivo = new Archivo();
        archivo.setNombre(nombreArchivo);
        archivo.setNombreReal(nombreArchivo);
        archivo.setTipo(TIPO_ARCHIVO);
        ByteArrayOutputStream output;
        JasperPrint print;
        try (
                InputStream isLogoSicoes = Files.newInputStream(Paths.get(pathJasper + LOGO_SICOES));
                InputStream isLogoOsinergmin = Files.newInputStream(Paths.get(pathJasper + LOGO_OSINERGMIN))

        ) {
            File jrxml = new File(pathJasper + FORMATO_04);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(SUBREPORT_DIR, pathJasper);
            parameters.put(P_LOGO_SICOES, isLogoSicoes);
            parameters.put(P_LOGO_OSINERGMIN, isLogoOsinergmin);
            parameters.put(P_TITULO, "EVALUACIÓN DE DOCUMENTOS DE INICIO DE SERVICIO");
            parameters.put(P_INTRODUCCION, "La evaluación de documentos de inicio de servicio finalizó con el siguiente resultado.");
            parameters.put(P_SUBTITULO_1, PERSONA_JURIDICA_3);
            parameters.put(P_SUBTITULO_2, DOCUMENTOS_PRESENTADOS_4);
            parameters.put(P_MOSTRAR_CABECERA_2, Boolean.TRUE);
            parameters.put(P_MOSTRAR_CABECERA_4, Boolean.FALSE);
            parameters.put(P_MOSTRAR_DETALLE_2, Boolean.TRUE);
            parameters.put(P_MOSTRAR_DETALLE_4, Boolean.FALSE);
            parameters.put(P_MOSTRAR_ADICIONAL_2, Boolean.TRUE);
            parameters.put(P_MOSTRAR_ADICIONAL_4, Boolean.FALSE);
            Long idSolicitud = personalReemplazo.getIdSolicitud();
            if (idSolicitud == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA);
            }
            logger.info("reporte inicio serv contrato NO - idSolicitud: {}", idSolicitud);
            SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                    .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
            logger.info("reporte inicio serv contrato NO - sicoesSolicitud: {}",sicoesSolicitud);
            String numeroExpediente = sicoesSolicitud.getNumeroExpediente();
            if (numeroExpediente == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_EXPEDIENTE_NO_ENVIADO);
            }
            logger.info("reporte inicio serv contrato NO - numeroExpediente: {}", numeroExpediente);
            personalReemplazo.setNumeroExpediente(numeroExpediente);
            Supervisora supervisora = sicoesSolicitud.getSupervisora();
            logger.info("reporte inicio serv contrato NO - supervisora: {}", supervisora);
            personalReemplazo.setSupervisora(supervisora);
            List<Archivo> archivos = new ArrayList<>();
            ListadoDetalle tipoArchivoContratoLaboral = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO, CONTRATO_LABORAL);
            logger.info("reporte inicio serv contrato NO - tipoArchivoContratoLaboral: {}", tipoArchivoContratoLaboral);
            Archivo archivoBDContratoLaboral = new Archivo();
            archivoBDContratoLaboral.setTipoArchivo(tipoArchivoContratoLaboral);
            archivoBDContratoLaboral.setConforme(CONFORME+" Sí");
            archivos.add(archivoBDContratoLaboral);
            ListadoDetalle tipoArchivoSctr = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO, "SCTR");
            logger.info("reporte inicio serv contrato NO - tipoArchivoSctr: {}", tipoArchivoSctr);
            Archivo archivoBDSctr = new Archivo();
            archivoBDSctr.setTipoArchivo(tipoArchivoSctr);
            archivoBDSctr.setConforme(CONFORME+" No");
            archivos.add(archivoBDSctr);
            personalReemplazo.setArchivos(archivos);
            List<Archivo> adicionales = new ArrayList<>();
            Archivo adicionalBD = new Archivo();
            ListadoDetalle tipoArchivoAdicional = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ADICIONAL,
                    CONTRATO_ALQUILER_CAMIONETA);
            logger.info("reporte inicio serv contrato NO - tipoArchivoAdicional: {}", tipoArchivoAdicional);
            adicionalBD.setTipoArchivo(tipoArchivoAdicional);
            adicionalBD.setConforme(CONFORME+" Sí");
            adicionales.add(adicionalBD);
            personalReemplazo.setAdicionales(adicionales);
            List<PersonalReemplazo> personalesReemplazo = java.util.Collections.singletonList(personalReemplazo);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(personalesReemplazo, true);
            JasperReport jasperReport = archivoUtil.getJasperCompilado(jrxml);
            print = JasperFillManager.fillReport(jasperReport, parameters, ds);
            output = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, output);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SICOES_SOLICITUD_GUARDAR_FORMATO_04, e);
        }
        byte[] bytesSalida = output.toByteArray();
        archivo.setPeso((long) bytesSalida.length);
        archivo.setNroFolio(1L);
        archivo.setContenido(bytesSalida);
        return archivo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo evaluarDocumentos(PersonalReemplazoDTO personalReemplazo,Boolean conforme, String accion, Contexto contexto) {
        logger.info("evaluar documentos personalReemplazo: {}", personalReemplazo);
        logger.info("evaluar documentos conforme: {}", conforme);
        logger.info("evaluar documentos accion: {}", accion);
        logger.info("evaluar documentos contexto: {}", contexto);
        Long id = personalReemplazo.getIdReemplazo();
        if (id == null) {
            throw new ValidacionException("No existe id");
        }
        logger.info("evaluar documentos - personalReemplazo.getIdReemplazo(): {}", id);
        PersonalReemplazo existe = reemplazoDao.findById(id)
                .orElseThrow(() -> new ValidacionException("Id personal no enviado"));
        Long idPerfContrato = existe.getIdSolicitud();
        logger.info("evaluar documentos - idPerfContrato: {}", idPerfContrato);
        SicoesSolicitud solicitud = sicoesSolicitudDao.obtenerSolicitudDetallado(idPerfContrato);
        logger.info("evaluar documentos - solicitud: {}", solicitud.getIdSolicitud());
        Supervisora supervisora = supervisoraDao.obtener(existe.getPersonaPropuesta().getIdSupervisora());
        logger.info("evaluar documentos - supervisora: {}", supervisora.getIdSupervisora());
        if (accion.equals("A")) {
            if (conforme) {
                existe.setEstadoReemplazo(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO)); //en proceso  ---ok
                existe.setEstadoEvalDoc(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO));  //en proceso
                existe.setEstadoAprobacionInforme(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.EN_APROBACION)); // en aprobacion  -ok
                existe.setEstadoEvalDocIniServ(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)); // preliminar
                Aprobacion aprob = new Aprobacion();
                aprob.setCoTipoAprobacion(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.TIPO_APROBACION.CODIGO,Constantes.LISTADO.TIPO_APROBACION.APROBAR));
                aprob.setRemplazoPersonal(existe);
                aprob.setNumeroExpediente(sicoesSolicitudDao.obtenerNumExpedienteAprobacion(solicitud.getIdSolicitud()));
                DocumentoReemplazo doc = documentoReemDao.obtenerPorIdReemplazoSeccion(existe.getIdReemplazo(), listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.SECCION_DOC_REEMPLAZO.INFORME).get(0).getIdListadoDetalle()).get(0);
                aprob.setDocumento(doc);
                Rol rolUsuarioInterno = rolDao.obtenerCodigo(Constantes.ROLES.APROBADOR_TECNICO);
                logger.info("evaluar documentos - rolUsuarioInterno: {}", rolUsuarioInterno);
                aprob.setIdRol(rolUsuarioInterno.getIdRol());
                aprob.setDeTp(supervisora.getTipoPersona().getValor());
                aprob.setIdContratista(supervisora.getIdSupervisora());
                aprob.setCoTipoSolicitud(solicitud.getTipoSolicitud());
                aprob.setFechaIngreso(new Date());
                aprob.setEstadoAprob(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION));
                aprob.setEstadoAprobGerenteDiv(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO));
                aprob.setEstadoAprobGerenteLinea(null);
                logger.info("evaluar documentos - aprob: {}", aprob);
                AuditoriaUtil.setAuditoriaRegistro(aprob,contexto);
                aprobacionDao.save(aprob);
                notificacionContratoService.notificarCargarDocumentosInicioServicio(supervisora, contexto);
                ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                        Constantes.LISTADO.TIPO_ARCHIVO.FINALIZACION_EVALUACION);
                if (tipoArchivo == null) {
                    throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_ARCHIVO_NO_EXISTE);
                }
                logger.info("evaluar documentos - tipoArchivo: {}", tipoArchivo);
                Archivo archivo = generarArchivoSigedRegistrarRevDocumentos(existe, tipoArchivo, contexto);
                logger.info("evaluar documentos - archivo: {}", archivo);
                existe.setArchivo(archivo);
            } else {
                existe.setEstadoReemplazo(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)); //preliminar ---ok
                notificacionContratoService.notificarSubsanacionDocumentos( supervisora, existe.getPersonaPropuesta(), contexto);
            }
        } else {
            existe.setEstadoReemplazo(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.ARCHIVADO)); //archivado   ---ok
            existe.setEstadoEvalDoc(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.ARCHIVADO));  //archivado  ----OK
            SupervisoraMovimiento obSupMov = new SupervisoraMovimiento();
            obSupMov.setSupervisora(supervisora);
            obSupMov.setEstado(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.ACTIVO ));
            AuditoriaUtil.setAuditoriaRegistro(obSupMov,contexto);
            supervisoraMovimientoDao.save(obSupMov);
            Supervisora personal = existe.getPersonaPropuesta();
            logger.info("evaluar documentos - personal: {}", personal.getIdSupervisora());
            notificacionContratoService.notificarRechazoPersonalPropuesto(supervisora, personal, contexto);
        }
        AuditoriaUtil.setAuditoriaRegistro(existe,contexto);
        logger.info("evaluar documentos - existe: {}", existe);
        return reemplazoDao.save(existe);
    }

    @Override
    public void procesarDocumentosReemplazo(List<DocumentoReemplazo> documentos,
                                            SicoesSolicitud sicoesSolicitud,
                                            Contexto contexto) throws ValidacionException {
        List<File> archivosAlfresco;
        ExpedienteInRO expedienteInRO = crearExpedienteAgregarDocumentos(sicoesSolicitud, contexto);

        for (DocumentoReemplazo documento : documentos) {
            if (documento.getIdArchivoSiged() != null) {
                continue;
            }
            // Obtener los archivos de Alfresco relacionados con el documento
            archivosAlfresco = archivoService.obtenerArchivosPorIdDocumentoReem(documento.getIdDocumento(), contexto);
            try {
                // Agregar documento a SIGED
                DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
                if (documentoOutRO.getResultCode() != 1) {
                    throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE,
                            documentoOutRO.getMessage());
                }
                // Obtener los IDs de los archivos de SIGED
                String nombreDocumento = archivosAlfresco.get(0).getName();
                IdsDocumentoArchivoDTO idsDocumentoArchivoDTO = sigedOldConsumer.obtenerIdArchivo(
                        sicoesSolicitud.getNumeroExpediente(), contexto.getUsuario().getUsuario(), nombreDocumento);

                // Actualizar los valores del documento con los IDs obtenidos
                documento.setIdArchivoSiged(String.valueOf(idsDocumentoArchivoDTO.getIdArchivo()));
                documento.setIdDocumentoSiged(String.valueOf(idsDocumentoArchivoDTO.getIdDocumento()));
                documentoReemService.actualizar(documento, contexto);
            } catch (ValidacionException e) {
                throw e; // Si es una excepción de validación, la volvemos a lanzar
            } catch (Exception e) {
                logger.error("procesar documentos reemplazo - ERROR {} ", e.getMessage(), e);
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE);
            }
        }
    }

    private Long obtenerIdListadoDetalle(String codigoListado, String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            return null;
        }
        ListadoDetalle ld = listadoDetalleDao.obtenerListadoDetalle(codigoListado, descripcion.trim());
        return (ld != null) ? ld.getIdListadoDetalle() : null;
    }

    private <T> void actualizarCampoSiNoNulo(T valor, Consumer<T> setter) {
        if (valor != null) {
            setter.accept(valor);
        }
    }

    private boolean checkAllDocsConforme(List<EvaluarDocuReemplazo> listEvaluaciones) {
        return !listEvaluaciones.isEmpty()
                && listEvaluaciones.stream()
                .allMatch(evaluacion -> Constantes.LISTADO.SI_NO.SI.equals(evaluacion.getConforme()));
    }

    private void procesarDocsConforme(Contexto contexto, PersonalReemplazo personalReemplazo) {
        // Actualizar estado y auditoría
        ListadoDetalle estadoEnProceso = obtenerEstadoEnProceso();
        logger.info("Docs conforme - estadoEnProceso: {}", estadoEnProceso);
        personalReemplazo.setEstadoRevisarEval(estadoEnProceso);
        AuditoriaUtil.setAuditoriaActualizacion(personalReemplazo, contexto);
        personalReemplazo = reemplazoDao.save(personalReemplazo);

        // Enviar notificaciones si es necesario
        enviarNotificacionesConforme(contexto, personalReemplazo);

        // Actualizar aprobación
        Aprobacion aprobacion = obtenerAprobacion(personalReemplazo);
        AuditoriaUtil.setAuditoriaActualizacion(aprobacion, contexto);
        aprobacionDao.save(aprobacion);

        // Agregar documentos adjuntos en SIGED
        agregarDocumentosSiged(personalReemplazo,contexto);
    }

    private void procesarDocsPreliminar(Contexto contexto, PersonalReemplazo personalReemplazo) {
        // Actualizar estado y auditoría para estado preliminar
        ListadoDetalle estadoPreliminar = obtenerEstadoPreliminar();
        logger.info("estadoPreliminar: {}", estadoPreliminar);
        personalReemplazo.setEstadoReemplazo(estadoPreliminar);
        AuditoriaUtil.setAuditoriaActualizacion(personalReemplazo, contexto);
        personalReemplazo = reemplazoDao.save(personalReemplazo);

        // Enviar notificaciones dependiendo del rol del usuario
        enviarNotificacionesPreliminar(contexto, personalReemplazo);
    }

    private ListadoDetalle obtenerEstadoEnProceso() {
        return listadoDetalleDao.listarListadoDetallePorCoodigo(
                        Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO)
                .stream()
                .filter(resultado -> resultado.getOrden().compareTo(1L) == 0)
                .findFirst()
                .orElse(new ListadoDetalle());
    }

    private ListadoDetalle obtenerEstadoPreliminar() {
        return listadoDetalleDao.listarListadoDetallePorCoodigo(
                        Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)
                .stream()
                .filter(resultado -> resultado.getOrden().compareTo(1L) == 0)
                .findFirst()
                .orElse(new ListadoDetalle());
    }

    private Aprobacion obtenerAprobacion(PersonalReemplazo personalReemplazo) {
        Aprobacion aprobacion = aprobacionDao.findByRemplazoPersonal(personalReemplazo.getIdReemplazo())
                .orElseThrow(() -> new RuntimeException("Aprobacion no encontrada"));
        aprobacion.setEstadoAprob(listadoDetalleDao.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION));
        aprobacion.setEstadoAprobGerenteLinea(listadoDetalleDao.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO));
        return aprobacion;
    }

    private void enviarNotificacionesConforme(Contexto contexto, PersonalReemplazo personalReemplazo) {
        if (esInvitado(contexto)) {
            Optional<Usuario> evaluadorContratos = obtenerEvaluadorContratos();
            evaluadorContratos.ifPresent(evaluador -> enviarNotificacionEvaluador(contexto, evaluador, personalReemplazo));
        }
    }

    private void enviarNotificacionesPreliminar(Contexto contexto, PersonalReemplazo personalReemplazo) {
        if (esResponsableTecnico(contexto)) {
            enviarNotificacionRevDocumentos2(contexto, personalReemplazo);
        } else if (esInvitado(contexto)) {
            Optional<Usuario> evaluadorContratos = obtenerEvaluadorContratos();
            evaluadorContratos.ifPresent(evaluador -> enviarNotificacionEvaluador(contexto, evaluador, personalReemplazo));
        } else if (esEvaluadorContratos(contexto)) {
            enviarNotificacionEvaluadorRevDocumentos12(contexto, personalReemplazo);
        }
    }

    private void enviarNotificacionEvaluador(Contexto contexto, Usuario evaluador, PersonalReemplazo personalReemplazo) {
        SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(personalReemplazo.getIdSolicitud())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
        String numeroExpediente = Optional.ofNullable(sicoesSolicitud.getNumeroExpediente()).orElse("");
        logger.info("flujo revision responsable tec numeroExpediente: {}", numeroExpediente);
        notificacionContratoService.notificarRevDocumentos15(evaluador, numeroExpediente, contexto);
    }

    private void enviarNotificacionRevDocumentos2(Contexto contexto, PersonalReemplazo personalReemplazo) {
        SicoesSolicitud solicitud = sicoesSolicitudDao.findById(personalReemplazo.getIdSolicitud())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_EXISTE));
        String nombrePersonal = nombrePersonal(personalReemplazo);
        String nombrePerfil = personalReemplazo.getPerfil().getNombre();
        notificacionContratoService.notificarRevDocumentos2(solicitud.getSupervisora(), nombrePersonal, nombrePerfil, new ArrayList<>(), contexto);
    }

    private void enviarNotificacionEvaluadorRevDocumentos12(Contexto contexto, PersonalReemplazo personalReemplazo) {
        Optional<Usuario> evaluadorContratos = obtenerEvaluadorContratos();
        evaluadorContratos.ifPresent(evaluador -> notificacionContratoService.notificarRevDocumentos12(evaluador, contexto));

        SicoesSolicitud solicitud = sicoesSolicitudDao.findById(personalReemplazo.getIdSolicitud())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_EXISTE));
        String nombrePersonal = nombrePersonal(personalReemplazo);
        String nombrePerfil = personalReemplazo.getPerfil().getNombre();
        notificacionContratoService.notificarRevDocumentos122(solicitud.getSupervisora(), nombrePersonal, "", "", new ArrayList<>(), contexto);
    }

    private void agregarDocumentosSiged(PersonalReemplazo personalReemplazo, Contexto contexto) {
        Long idSolicitud = personalReemplazo.getIdSolicitud();
        SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
        String listadoSeccion = Constantes.LISTADO.SECCIONES_REEMPLAZO_PERSONAL;
        String descSeccion5 = Constantes.LISTADO.SECCION_DOC_REEMPLAZO.PROYECTO_ADENDA;
        ListadoDetalle seccion5 = listadoDetalleDao.obtenerListadoDetalle(listadoSeccion, descSeccion5);
        List<Long> idsSeccion = Arrays.asList(seccion5.getIdListadoDetalle());
        List<DocumentoReemplazo> documentos = documentoReemDao.obtenerPorIdReemplazoSecciones(
                personalReemplazo.getIdReemplazo(), idsSeccion);
        procesarDocumentosReemplazo(documentos, sicoesSolicitud, contexto);
    }

    private boolean esInvitado(Contexto contexto) {
        return contexto.getUsuario().getRoles().stream()
                .anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.INVITADO));
    }

    private boolean esResponsableTecnico(Contexto contexto) {
        return contexto.getUsuario().getRoles().stream()
                .anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.RESPONSABLE_TECNICO));
    }

    private boolean esEvaluadorContratos(Contexto contexto) {
        return contexto.getUsuario().getRoles().stream()
                .anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.EVALUADOR_CONTRATOS));
    }

    private Optional<Usuario> obtenerEvaluadorContratos() {
        return usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.EVALUADOR_CONTRATOS).stream()
                .findFirst()
                .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));
    }

    // Función común para procesar evaluación y aprobación en G2 y G3
    private void procesarEvaluacionAprobacionG2(AprobacionDTO aprobacion, Aprobacion aprobacionFinal, PersonalReemplazo persoReempFinal, String numeroExpediente, Contexto contexto) {
        if (aprobacion.getAccion().equals("A")) {
            actualizarEstadoAprobacion(aprobacionFinal, persoReempFinal, Constantes.LISTADO.ESTADO_APROBACION.APROBADO, Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO);
            notificarUsuarioPorRol(Constantes.ROLES.RESPONSABLE_TECNICO, numeroExpediente, contexto);
        } else {
            actualizarEstadoAprobacion(aprobacionFinal, persoReempFinal, Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO, Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR);
            Rol rolUsuarioInterno = rolDao.obtenerCodigo(Constantes.ROLES.EVALUADOR_TECNICO);
            aprobacionFinal.setIdRol(rolUsuarioInterno.getIdRol());
        }
        persoReempFinal.setFecActualizacion(new Date());
        AuditoriaUtil.setAuditoriaRegistro(persoReempFinal, contexto);
        reemplazoDao.save(persoReempFinal);
    }

    // Función para procesar la aprobación en G3
    private void procesarEvaluacionAprobacionG3(AprobacionDTO aprobacion, Aprobacion aprobacionFinal, PersonalReemplazo persoReempFinal, String numeroExpediente, Contexto contexto) {
        if (aprobacion.getAccion().equals("A")) {
            actualizarEstadoAprobacion(aprobacionFinal, persoReempFinal, Constantes.LISTADO.ESTADO_APROBACION.APROBADO, Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO);
            notificarUsuarioPorRol(Constantes.ROLES.EVALUADOR_CONTRATOS, numeroExpediente, contexto);
        } else {
            actualizarEstadoAprobacion(aprobacionFinal, persoReempFinal, Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO, Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR);
            Rol rolUsuarioInterno = rolDao.obtenerCodigo(Constantes.ROLES.EVALUADOR_TECNICO);
            aprobacionFinal.setIdRol(rolUsuarioInterno.getIdRol());
        }
        persoReempFinal.setFecActualizacion(new Date());
        AuditoriaUtil.setAuditoriaRegistro(persoReempFinal, contexto);
        reemplazoDao.save(persoReempFinal);
    }

    // Función común para actualizar los estados de Aprobación y Reemplazo Personal
    private void actualizarEstadoAprobacion(Aprobacion aprobacionFinal, PersonalReemplazo persoReempFinal, String estadoAprobacion, String estadoSolicitud) {
        aprobacionFinal.setEstadoAprob(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO, estadoAprobacion));
        aprobacionFinal.setEstadoAprobGerenteDiv(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO, estadoAprobacion));
        persoReempFinal.setEstadoEvalDoc(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, estadoSolicitud));
    }

    // Función común para notificar al usuario por rol
    private void notificarUsuarioPorRol(String rolCodigo, String numeroExpediente, Contexto contexto) {
        Optional<Usuario> usuario = usuarioRolDao.obtenerUsuariosRol(rolCodigo)
                .stream()
                .findFirst()
                .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));

        Rol rol = rolDao.obtenerCodigo(rolCodigo);
        if (usuario.isPresent()) {
            notificacionContratoService.notificarRevisarDocumentacionPendiente(usuario.get(), numeroExpediente, rol.getNombre(), contexto);
        } else {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_RESPONSABLE_TECNICO_NO_EXISTE);
        }
    }

    // Función para procesar la aprobación de la adenda
    private void procesarAprobacionAdenda(AprobacionDTO aprobacion, Aprobacion aprobacionFinal, PersonalReemplazo persoReempFinal, Contexto contexto) {
        Adenda adendaFinal = adendaDao.obtenerAdenda(persoReempFinal.getIdReemplazo())
                .stream().findFirst().orElseThrow(() -> new RuntimeException("No se encuentra adenda"));

        if (aprobacion.getAccion().equals("A")) {
            adendaFinal.setEstadoAprobacionLogistica(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ADENDA.CODIGO, Constantes.LISTADO.ESTADO_ADENDA.APROBADO));
            adendaFinal.setEstadoVbGAF(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ADENDA.CODIGO, Constantes.LISTADO.ESTADO_ADENDA.ASIGNADO));
            Long idPerfContrato = persoReempFinal.getIdSolicitud();
            SicoesSolicitud solicitud = sicoesSolicitudDao.obtenerSolicitudDetallado(idPerfContrato);
            //Supervisora supervisora = supervisoraDao.obtener(solicitud.getSupervisora().getIdSupervisora());

            Aprobacion aprob = new Aprobacion();
            aprob.setCoTipoAprobacion(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.TIPO_APROBACION.CODIGO, Constantes.LISTADO.TIPO_APROBACION.VISTO_BUENO));
            aprob.setRemplazoPersonal(persoReempFinal);
            aprob.setNumeroExpediente(sicoesSolicitudDao.obtenerNumExpedienteAprobacion(solicitud.getIdSolicitud()));
            DocumentoReemplazo doc = documentoReemDao.obtenerPorIdReemplazoSeccion(persoReempFinal.getIdReemplazo(),
                    listadoDetalleDao.listarListadoDetallePorCoodigo(Constantes.LISTADO.SECCION_DOC_REEMPLAZO.PROYECTO_ADENDA).get(0).getIdListadoDetalle()).get(0);
            aprob.setDocumento(doc);
            Rol rolUsuarioInterno = rolDao.obtenerCodigo(Constantes.ROLES.G2_APROBADOR_ADMINISTRATIVO);
            aprob.setIdRol(rolUsuarioInterno.getIdRol());

            aprob.setFechaIngreso(new Date());
            aprob.setEstadoAprob(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION));
            aprob.setEstadoAprobGerenteDiv(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO));
            aprob.setEstadoAprobGerenteLinea(null);

            AuditoriaUtil.setAuditoriaRegistro(aprob, contexto);
            aprobacionDao.save(aprob);

            notificarUsuarioPorRol(Constantes.ROLES.G2_APROBADOR_ADMINISTRATIVO, obtenerNroExpPersona(persoReempFinal), contexto);
        } else {
            aprobacionFinal.setEstadoAprob(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_APROBACION.APROBADO));
            adendaFinal.setEstadoVbGAF(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_ADENDA.CODIGO, Constantes.LISTADO.ESTADO_ADENDA.RECHAZADO));
        }

        adendaFinal.setFecActualizacion(new Date());
        AuditoriaUtil.setAuditoriaRegistro(adendaFinal, contexto);
        adendaDao.save(adendaFinal);
    }

}