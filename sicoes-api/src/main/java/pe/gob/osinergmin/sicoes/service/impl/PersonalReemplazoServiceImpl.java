package pe.gob.osinergmin.sicoes.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonalReemplazoServiceImpl implements PersonalReemplazoService {

    Logger logger = LogManager.getLogger(PersonalReemplazoServiceImpl.class);

    @Autowired
    private PersonalReemplazoDao reemplazoDao;

    @Autowired
    private DocumentoReemDao documentoReemDao;

    @Autowired
    private DocumentoReemService documentoReemService;

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
    private SicoesSolicitudDao sicoesSolicitudDao;

    @Autowired
    private ArchivoService archivoService;

    @Autowired
    private SigedApiConsumer sigedApiConsumer;

    @Autowired
    private Environment env;

    @Value("${siged.old.proyecto}")
    private String SIGLA_PROYECTO;

    @Autowired
    private AprobacionDao aprobacionDao;

    @Autowired
    private AdendaDao adendaDao;

    @Autowired
    private EvaluacionDocumentacionDao evaluacionDocumentacionDao;

    @Autowired
    private EvaluacionPPDao evaluacionPPDao;

    @Autowired
    private PropuestaProfesionalDao propuestaProfesionalDao;

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    private HistorialAprobReempDao historialAprobReempDao;

    @Autowired
    private SupervisoraDao supervisoraDao;

    @Autowired
    private SolicitudDao solicitudDao;

    @Autowired
    private UsuarioRolDao usuarioRolDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private EvaluacionDocInicioServDao evaluacionDocInicioServDao;

    @Autowired
    private  DocumentoInicioServDao documentoInicioServDao;

    @Autowired
    private ListadoDao listadoDao;

    @Autowired
    private ArchivoUtil archivoUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Value("${path.jasper}")
    private String pathJasper;

    @Value("${siged.ws.cliente.osinergmin.numero.documento}")
    private String OSI_DOCUMENTO;

    @Value("${consolidado.documentos}")
    private String CONSOLIDADO_DOCUMENTOS;

    @Value("${finalizacion.evaluacion}")
    private String FINALIZACION_EVALUACION;

    @Value("${path.temporal}")
    private String pathTemporal;

    @Autowired
    private DocumentoPPDao documentoPPDao;

    @Override
    public Page<PersonalReemplazo> listarPersonalReemplazo(Long idSolicitud, String descAprobacion,
                                                           String descEvalDoc,
                                                           String descRevisarEval,
                                                           String descAprobacionInforme,
                                                           String descAprobacionAdenda,
                                                           String descEvalDocIniServ,
                                                           Pageable pageable, Contexto contexto) {
        logger.info("listarPersonalReemplazo");
        Pageable pageRequest = pageable == null ? Pageable.unpaged() : pageable;

        String listadoEstadoSolicitud = Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO;
        String listadoEstadoAprobacion = Constantes.LISTADO.ESTADO_APROBACION.CODIGO;
        Long idAprobacion = null;
        Long idEvalDoc = null;
        Long idRevisarEval = null;
        Long idAprobacionInforme = null;
        Long idAprobacionAdenda = null;
        Long idEvalDocIniServ = null;

        if (descAprobacion != null && !descAprobacion.isEmpty()) {
            ListadoDetalle ld = listadoDetalleDao.obtenerListadoDetalle(listadoEstadoSolicitud, descAprobacion.trim());
            if (ld != null) idAprobacion = ld.getIdListadoDetalle();
        }

        // Estado evaluación de documentos
        if (descEvalDoc != null && !descEvalDoc.isEmpty()) {
            ListadoDetalle ld = listadoDetalleDao.obtenerListadoDetalle(listadoEstadoSolicitud, descEvalDoc.trim());
            if (ld != null) idEvalDoc = ld.getIdListadoDetalle();
        }

        // Estado revisar evaluación documentos
        if (descRevisarEval != null && !descRevisarEval.isEmpty()) {
            ListadoDetalle ld = listadoDetalleDao.obtenerListadoDetalle(listadoEstadoSolicitud, descRevisarEval.trim());
            if (ld != null) idRevisarEval = ld.getIdListadoDetalle();
        }

        // Estado aprobación informe
        if (descAprobacionInforme != null && !descAprobacionInforme.isEmpty()) {
            ListadoDetalle ld = listadoDetalleDao.obtenerListadoDetalle(listadoEstadoAprobacion, descAprobacionInforme.trim());
            if (ld != null) idAprobacionInforme = ld.getIdListadoDetalle();
        }

        // Estado aprobación adenda
        if (descAprobacionAdenda != null && !descAprobacionAdenda.isEmpty()) {
            ListadoDetalle ld = listadoDetalleDao.obtenerListadoDetalle(listadoEstadoAprobacion, descAprobacionAdenda.trim());
            if (ld != null) idAprobacionAdenda = ld.getIdListadoDetalle();
        }

        if (descEvalDocIniServ != null && !descEvalDocIniServ.isEmpty()) {
            ListadoDetalle ld = listadoDetalleDao.obtenerListadoDetalle(listadoEstadoSolicitud, descEvalDocIniServ);
            if (ld != null) idEvalDocIniServ = ld.getIdListadoDetalle();
        }
        return reemplazoDao.obtenerxIdSolicitud(
                idSolicitud,idAprobacion,idEvalDoc,idRevisarEval,idAprobacionInforme,
                idAprobacionAdenda,idEvalDocIniServ,pageRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo guardar(PersonalReemplazo personalReemplazo, Contexto contexto) {
        AuditoriaUtil.setAuditoriaRegistro(personalReemplazo,contexto);
        return reemplazoDao.save(personalReemplazo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo eliminarBaja(PersonalReemplazo personalReemplazo, Contexto contexto) {
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

        if (personalReemplazo.getEstadoRevisarDoc() != null){
            existe.setEstadoRevisarDoc(personalReemplazo.getEstadoRevisarDoc());
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
        if (personalReemplazo.getEstadoDocIniServ() != null){
            existe.setEstadoRevisarDoc(personalReemplazo.getEstadoDocIniServ());
        }
        if (personalReemplazo.getEstadoEvalDocIniServ() != null) {
            existe.setEstadoEvalDocIniServ(personalReemplazo.getEstadoEvalDocIniServ());
        }

        logger.info("actualizando_Ex {}:",existe);
        AuditoriaUtil.setAuditoriaActualizacion(existe,contexto);
        return reemplazoDao.save(existe);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo eliminarPropuesta(PersonalReemplazo personalReemplazo, Contexto contexto) {
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
        AuditoriaUtil.setAuditoriaRegistro(entity,contexto);
        return reemplazoDao.save(entity);
    }

    @Override
    public PersonalReemplazo registrar(PersonalReemplazo personalReemplazoIN, Contexto contexto) {
        Long idReemplazo = personalReemplazoIN.getIdReemplazo();
        if (idReemplazo == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONAL_REEMPLAZO_NO_ENVIADO);
        }
        logger.info("idReemplazo: {}", idReemplazo);
        PersonalReemplazo personalReemplazo = reemplazoDao.findById(idReemplazo)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.PERSONAL_REEMPLAZO_NO_EXISTE));
        personalReemplazo.setEstadoReemplazo (listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.ESTADO_SOLICITUD.EN_EVALUACION).get(0));
        logger.info("personalReemplazo: {}", personalReemplazo);
        Long idSolicitud = personalReemplazo.getIdSolicitud();
        if (idSolicitud == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA);
        }
        logger.info("idSolicitud: {}", idSolicitud);
        Supervisora personalBaja = personalReemplazo.getPersonaBaja();
        if (personalBaja == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONA_BAJA);
        }
        logger.info("personalBaja: {}", personalBaja);
        PropuestaProfesional propuestaProfesional = propuestaProfesionalDao.listarXSolicitud(idSolicitud, personalBaja.getIdSupervisora());
        if (propuestaProfesional == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROFESIONAL_NO_EXISTE);
        }
        logger.info("propuestaProfesional: {}", propuestaProfesional);
        Supervisora personaPropuesta = personalReemplazo.getPersonaPropuesta();
        if (personaPropuesta == null){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_PERSONA_PROPUESTA);
        }
        logger.info("personaPropuesta: {}", personaPropuesta);
        propuestaProfesional.setSupervisora(personaPropuesta);
        SupervisoraMovimiento supervisoraMovimiento = new SupervisoraMovimiento();
        supervisoraMovimiento.setSector(propuestaProfesional.getSector());
        supervisoraMovimiento.setSubsector(propuestaProfesional.getSubsector());
        supervisoraMovimiento.setSupervisora(personaPropuesta);
        supervisoraMovimiento.setEstado(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SUP_PERFIL.CODIGO, Constantes.LISTADO.ESTADO_SUP_PERFIL.BLOQUEADO));
        supervisoraMovimiento.setTipoMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.CODIGO, Constantes.LISTADO.TIPO_MOTIVO_BLOQUEO.AUTOMATICO));
        supervisoraMovimiento.setMotivo(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.MOTIVO_BLOQUEO_DESBLOQUEO.REEMPLAZO_PERSONAL));
        supervisoraMovimiento.setAccion(listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.CODIGO, Constantes.LISTADO.ACCION_BLOQUEO_DESBLOQUEO.BLOQUEO));
        supervisoraMovimiento.setPropuestaProfesional(propuestaProfesional);
        supervisoraMovimiento.setFechaRegistro(new Date());
        logger.info("supervisoraMovimiento: {}", supervisoraMovimiento);
        supervisoraMovimientoService.guardar(supervisoraMovimiento,contexto);
        AuditoriaUtil.setAuditoriaActualizacion(personalReemplazo,contexto);
        PersonalReemplazo personalReemplazoOUT = reemplazoDao.save(personalReemplazo);
        SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
        logger.info("sicoesSolicitud: {}", sicoesSolicitud);
        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                Constantes.LISTADO.TIPO_ARCHIVO.CONSOLIDADO_DOCUMENTOS);
        if (tipoArchivo == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_ARCHIVO_NO_EXISTE);
        }
        logger.info("tipoArchivo: {}", tipoArchivo);
        logger.info("contexto: {}", contexto);
        generarArchivoSigedRegistrar(personalReemplazoOUT, tipoArchivo, contexto);
        enviarNotificacionByRolEvaluador(personalReemplazo,contexto);
        enviarNotificacionDesvinculacion(personalReemplazo,contexto);
        return personalReemplazoOUT;
    }

    private void generarArchivoSigedRegistrar(PersonalReemplazo personalReemplazo, ListadoDetalle tipoArchivo, Contexto contexto) {
        Archivo archivo = generarReporteRegistrar(personalReemplazo, ArchivoUtil.obtenerNombreArchivo(tipoArchivo));
        archivo.setIdReemplazoPersonal(personalReemplazo.getIdReemplazo());
        archivo.setTipoArchivo(tipoArchivo);
        Archivo archivoDB = archivoService.guardarPorPersonalReemplazo(archivo, contexto);
        if (Objects.equals(tipoArchivo.getCodigo(), Constantes.LISTADO.TIPO_ARCHIVO.CONSOLIDADO_DOCUMENTOS)) {
            registrarExpedienteSiged(archivoDB, personalReemplazo, CONSOLIDADO_DOCUMENTOS);
        } else {
            adjuntarDocumentoSiged(archivoDB, personalReemplazo, CONSOLIDADO_DOCUMENTOS);
        }
    }

    private Archivo generarReporteRegistrar(PersonalReemplazo personalReemplazo, String nombreArchivo) {
        Archivo archivo = new Archivo();
        archivo.setNombre(nombreArchivo);
        archivo.setNombreReal(nombreArchivo);
        archivo.setTipo("application/pdf");
        ByteArrayOutputStream output;
        JasperPrint print;
        InputStream isLogoSicoes = null;
        InputStream isLogoOsinergmin = null;
        try {
            File jrxml = new File(pathJasper + "Formato_04_Personal_Reemplazo.jrxml");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SUBREPORT_DIR", pathJasper);
            isLogoSicoes = Files.newInputStream(Paths.get(pathJasper + "logo-sicoes.png"));
            parameters.put("P_LOGO_SICOES", isLogoSicoes);
            isLogoOsinergmin = Files.newInputStream(Paths.get(pathJasper + "logo-osinerming.png"));
            parameters.put("P_LOGO_OSINERGMIN", isLogoOsinergmin);
            parameters.put("P_TITULO", "CONSOLIDADO DE DOCUMENTOS CARGADOS REEMPLAZO PERSONAL PROPUESTO");
            parameters.put("P_INTRODUCCION", "La carga de documentos de inicio de servicio ha sido ingresada satisfactoriamente. A continuación, la lista de documentos cargados.");
            parameters.put("P_SUBTITULO_1", "5. PERSONA JURÍDICA");
            parameters.put("P_SUBTITULO_2", "6. DOCUMENTOS PRESENTADOS");
            parameters.put("P_MOSTRAR_CABECERA_2", Boolean.FALSE);
            parameters.put("P_MOSTRAR_CABECERA_4", Boolean.TRUE);
            parameters.put("P_MOSTRAR_DETALLE_2", Boolean.FALSE);
            parameters.put("P_MOSTRAR_DETALLE_4", Boolean.TRUE);
            parameters.put("P_MOSTRAR_ADICIONAL_2", Boolean.FALSE);
            parameters.put("P_MOSTRAR_ADICIONAL_4", Boolean.FALSE);
            Long idSolicitud = personalReemplazo.getIdSolicitud();
            if (idSolicitud == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA);
            }
            logger.info("idSolicitud: {}", idSolicitud);
            SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                    .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
            logger.info("sicoesSolicitud: {}", sicoesSolicitud);
            String numeroExpediente = sicoesSolicitud.getNumeroExpediente();
            if (numeroExpediente == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_EXPEDIENTE_NO_ENVIADO);
            }
            logger.info("numeroExpediente: {}", numeroExpediente);
            personalReemplazo.setNumeroExpediente(numeroExpediente);
            Supervisora supervisora = sicoesSolicitud.getSupervisora();
            logger.info("supervisora: {}", supervisora);
            personalReemplazo.setSupervisora(supervisora);
            List<Archivo> archivos = new ArrayList<>();
            Archivo archivoBD = new Archivo();
            ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle("DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO", "CONTRATO_LABORAL");
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
            logger.error(e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SICOES_SOLICITUD_GUARDAR_FORMATO_04, e);
        } finally {
            archivoUtil.close(isLogoSicoes);
            archivoUtil.close(isLogoOsinergmin);
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
                Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear")),
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
                Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear")),
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
        logger.info("idSolicitud: {}", idSolicitud);
        SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
        logger.info("sicoesSolicitud: {}", sicoesSolicitud);
        String numeroExpediente = sicoesSolicitud.getNumeroExpediente();
        if (numeroExpediente == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_EXPEDIENTE_NO_ENVIADO);
        }
        logger.info("numeroExpediente: {}", numeroExpediente);
        expediente.setNroExpediente(numeroExpediente);
        documento.setAsunto(asunto);
        documento.setAppNameInvokes(SIGLA_PROYECTO);
        documento.setCodTipoDocumento(codigoTipoDocumento);
        documento.setNroFolios(Integer.parseInt(env.getProperty("crear.expediente.parametros.crea.folio")));
        documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
        if (Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.respuesta.solicitud.pn")) == codigoTipoDocumento) {
            documento.setFirmante(Integer.parseInt(env.getProperty("siged.firmante.informe.respuesta.id.usuario")));
        }
        cs.setCodigoTipoIdentificacion(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
        cs.setNombre("OSINERGMIN");
        cs.setApellidoPaterno("-");
        cs.setApellidoMaterno("-");
        cs.setRazonSocial("OSINERGMIN");
        cs.setNroIdentificacion(OSI_DOCUMENTO);
        cs.setTipoCliente(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
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
    public GenericResponseDTO<String> registrarRevDocumentos(RegistrarRevDocumentosRequestDTO request, Contexto contexto) {
        PersonalReemplazo personalReemplazoToUpdate = reemplazoDao
                .findById(request.getIdReemplazo())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REEMPLAZO_PERSONAL_NO_EXISTE));
        List<DocumentoReemplazo> listDocsAsociados = documentoReemDao
                .findByIdReemplazoPersonal(request.getIdReemplazo());
        if (Constantes.ROLES.RESPONSABLE_TECNICO.equals(request.getCodRol())) {
            return GenericResponseDTO.<String>builder()
                    .resultado(flujoRevisionResponsableTecnico(
                            contexto, personalReemplazoToUpdate, listDocsAsociados))
                    .build();
        } else {
            return GenericResponseDTO.<String>builder()
                    .resultado(flujoRevisionEvaluadorContratos(
                            contexto, personalReemplazoToUpdate, listDocsAsociados))
                    .build();
        }
    }

    private String flujoRevisionResponsableTecnico(Contexto contexto,
                                                   PersonalReemplazo personalReemplazo,
                                                   List<DocumentoReemplazo> listDocsAsociados) {
        boolean allDocsConforme = !listDocsAsociados.isEmpty()
                && listDocsAsociados.stream()
                .allMatch(doc -> !Objects.isNull(doc.getEvaluacion())
                        && Constantes.LISTADO.SI_NO.SI.equals(doc.getEvaluacion().getConforme()));
        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                Constantes.LISTADO.TIPO_ARCHIVO.FINALIZACION_EVALUACION);
        if (tipoArchivo == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_ARCHIVO_NO_EXISTE);
        }
        logger.info("tipoArchivo: {}", tipoArchivo);
        generarArchivoSigedRegistrarRevDocumentos(personalReemplazo, tipoArchivo, contexto);
        if (allDocsConforme) {
            ListadoDetalle estadoEnProceso = listadoDetalleDao.listarListadoDetallePorCoodigo(
                            Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO)
                    .stream()
                    .filter(resultado -> resultado.getOrden().compareTo(1L) == 0)
                    .findFirst()
                    .orElse(new ListadoDetalle());
            personalReemplazo.setEstadoRevisarEval(estadoEnProceso);
            AuditoriaUtil.setAuditoriaActualizacion(personalReemplazo, contexto);
            reemplazoDao.save(personalReemplazo);
            if (contexto.getUsuario().getRoles().stream().anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.RESPONSABLE_TECNICO) || rol.getCodigo().equals(Constantes.ROLES.INVITADO) || rol.getCodigo().equals(Constantes.ROLES.EVALUADOR_CONTRATOS))) {
                logger.info("contexto: {}", contexto);
                if (contexto.getUsuario().getRoles().stream().anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.RESPONSABLE_TECNICO))) {
                    //SE COMENTA PORQUE DEPENDE DE allDocsConforme
                    // generarArchivoSigedRegistrarRevDocumentos(personalReemplazo, tipoArchivo, contexto);
                } else if (contexto.getUsuario().getRoles().stream().anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.INVITADO))) {
                    Optional<Usuario> evaluadorContratos = usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.EVALUADOR_CONTRATOS).stream()
                            .findFirst()
                            .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));
                    if (evaluadorContratos.isPresent()) {
                        Solicitud solicitud = solicitudDao.obtener(personalReemplazo.getIdSolicitud());
                        String numeroExpediente = solicitud.getNumeroExpediente();
                        notificacionContratoService.notificarRevDocumentos15(evaluadorContratos.get(), numeroExpediente, contexto);
                    } else {
                        throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUADOR_CONTRATOS_NO_EXISTE);
                    }
                } else if (contexto.getUsuario().getRoles().stream().anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.EVALUADOR_CONTRATOS))) {
                    //SE COMENTA PORQUE DEPENDE DE allDocsConforme
                    //generarArchivoSigedRegistrarRevDocumentos(personalReemplazo, tipoArchivo, contexto);
                }
            } else {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.ACCESO_NO_AUTORIZADO);
            }
            return Constantes.ESTADO_REVISION_DOCS_REEMPLAZO.OK;
        } else {
            ListadoDetalle estadoPreliminar = listadoDetalleDao.listarListadoDetallePorCoodigo(
                            Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)
                    .stream()
                    .filter(resultado -> resultado.getOrden().compareTo(1L) == 0)
                    .findFirst()
                    .orElse(new ListadoDetalle());
            personalReemplazo.setEstadoReemplazo(estadoPreliminar);
            AuditoriaUtil.setAuditoriaActualizacion(personalReemplazo, contexto);
            reemplazoDao.save(personalReemplazo);
            if (contexto.getUsuario().getRoles().stream().anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.RESPONSABLE_TECNICO) || rol.getCodigo().equals(Constantes.ROLES.INVITADO) || rol.getCodigo().equals(Constantes.ROLES.EVALUADOR_CONTRATOS))) {
                if (contexto.getUsuario().getRoles().stream().anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.RESPONSABLE_TECNICO))) {
                    Optional<Usuario> usuarioExterno = usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.USUARIO_EXTERNO).stream()
                            .findFirst()
                            .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));
                    if (usuarioExterno.isPresent()) {
                        String nombrePersonal = nombrePersonal(personalReemplazo);
                        String nombrePerfil = personalReemplazo.getPerfil().getNombre();
                        notificacionContratoService.notificarRevDocumentos2(usuarioExterno.get(), nombrePersonal, nombrePerfil, listDocsAsociados, contexto);
                    } else {
                        throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_EXTERNO_NO_EXISTE);
                    }
                } else if (contexto.getUsuario().getRoles().stream().anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.INVITADO))) {
                    Optional<Usuario> evaluadorContratos = usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.EVALUADOR_CONTRATOS).stream()
                            .findFirst()
                            .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));
                    if (evaluadorContratos.isPresent()) {
                        Solicitud solicitud = solicitudDao.obtener(personalReemplazo.getIdSolicitud());
                        String numeroExpediente = solicitud.getNumeroExpediente();
                        notificacionContratoService.notificarRevDocumentos15(evaluadorContratos.get(), numeroExpediente, contexto);
                    } else {
                        throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUADOR_CONTRATOS_NO_EXISTE);
                    }
                } else if (contexto.getUsuario().getRoles().stream().anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.EVALUADOR_CONTRATOS))) {
                    Optional<Usuario> evaluadorContratos = usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.EVALUADOR_CONTRATOS).stream()
                            .findFirst()
                            .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));
                    if (evaluadorContratos.isPresent()) {
                        notificacionContratoService.notificarRevDocumentos12(evaluadorContratos.get(), contexto);
                        Optional<Usuario> usuarioExterno = usuarioRolDao.obtenerUsuariosRol(Constantes.ROLES.USUARIO_EXTERNO).stream()
                                .findFirst()
                                .map(rol -> usuarioDao.obtener(rol.getUsuario().getIdUsuario()));
                        if (usuarioExterno.isPresent()) {
                            String nombrePersonal = nombrePersonal(personalReemplazo);
                            String nombrePerfil = personalReemplazo.getPerfil().getNombre();
                            notificacionContratoService.notificarRevDocumentos2(usuarioExterno.get(), nombrePersonal, nombrePerfil, listDocsAsociados, contexto);
                        } else {
                            throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_EXTERNO_NO_EXISTE);
                        }
                        notificacionContratoService.notificarRevDocumentos122(usuarioExterno.get(), contexto);
                    } else {
                        throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUADOR_CONTRATOS_NO_EXISTE);
                    }
                }
            } else {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.ACCESO_NO_AUTORIZADO);
            }
            return Constantes.ESTADO_REVISION_DOCS_REEMPLAZO.SUBSANAR;
        }
    }

    private void generarArchivoSigedRegistrarRevDocumentos(PersonalReemplazo personalReemplazo, ListadoDetalle tipoArchivo, Contexto contexto) {
        Archivo archivo = generarReporteRegistrarRevDocumentos(personalReemplazo, ArchivoUtil.obtenerNombreArchivo(tipoArchivo));
        archivo.setIdReemplazoPersonal(personalReemplazo.getIdReemplazo());
        archivo.setTipoArchivo(tipoArchivo);
        Archivo archivoDB = archivoService.guardarPorPersonalReemplazo(archivo, contexto);
        if (Objects.equals(tipoArchivo.getCodigo(), Constantes.LISTADO.TIPO_ARCHIVO.FINALIZACION_EVALUACION)) {
            registrarExpedienteSiged(archivoDB, personalReemplazo, FINALIZACION_EVALUACION);
        } else {
            adjuntarDocumentoSiged(archivoDB, personalReemplazo, FINALIZACION_EVALUACION);
        }
    }

    private Archivo generarReporteRegistrarRevDocumentos(PersonalReemplazo personalReemplazo, String nombreArchivo) {
        Archivo archivo = new Archivo();
        archivo.setNombre(nombreArchivo);
        archivo.setNombreReal(nombreArchivo);
        archivo.setTipo("application/pdf");
        ByteArrayOutputStream output;
        JasperPrint print;
        InputStream isLogoSicoes = null;
        InputStream isLogoOsinergmin = null;
        try {
            File jrxml = new File(pathJasper + "Formato_04_Personal_Reemplazo.jrxml");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SUBREPORT_DIR", pathJasper);
            isLogoSicoes = Files.newInputStream(Paths.get(pathJasper + "logo-sicoes.png"));
            parameters.put("P_LOGO_SICOES", isLogoSicoes);
            isLogoOsinergmin = Files.newInputStream(Paths.get(pathJasper + "logo-osinerming.png"));
            parameters.put("P_LOGO_OSINERGMIN", isLogoOsinergmin);
            parameters.put("P_TITULO", "FINALIZACIÓN DE EVALUACIÓN DE DOCUMENTOS DE INICIO DE SERVICIO");
            parameters.put("P_INTRODUCCION", "La evaluación de documentos de inicio de servicio finalizó.");
            parameters.put("P_SUBTITULO_1", "3. PERSONA JURÍDICA");
            parameters.put("P_SUBTITULO_2", "4. DOCUMENTOS PRESENTADOS");
            parameters.put("P_MOSTRAR_CABECERA_2", Boolean.TRUE);
            parameters.put("P_MOSTRAR_CABECERA_4", Boolean.FALSE);
            parameters.put("P_MOSTRAR_DETALLE_2", Boolean.TRUE);
            parameters.put("P_MOSTRAR_DETALLE_4", Boolean.FALSE);
            parameters.put("P_MOSTRAR_ADICIONAL_2", Boolean.TRUE);
            parameters.put("P_MOSTRAR_ADICIONAL_4", Boolean.FALSE);
            Long idSolicitud = personalReemplazo.getIdSolicitud();
            if (idSolicitud == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA);
            }
            logger.info("idSolicitud: {}", idSolicitud);
            SicoesSolicitud sicoesSolicitud = sicoesSolicitudDao.findById(idSolicitud)
                    .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA));
            logger.info("sicoesSolicitud: {}",sicoesSolicitud);
            String numeroExpediente = sicoesSolicitud.getNumeroExpediente();
            if (numeroExpediente == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.NUMERO_EXPEDIENTE_NO_ENVIADO);
            }
            logger.info("numeroExpediente: {}", numeroExpediente);
            personalReemplazo.setNumeroExpediente(numeroExpediente);
            Supervisora supervisora = sicoesSolicitud.getSupervisora();
            logger.info("supervisora: {}", supervisora);
            personalReemplazo.setSupervisora(supervisora);
            List<Archivo> archivos = new ArrayList<>();
            Archivo archivoBD = new Archivo();
            ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle("DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO", "CONTRATO_LABORAL");
            logger.info("tipoArchivo: {}", tipoArchivo);
            archivoBD.setTipoArchivo(tipoArchivo);
            archivos.add(archivoBD);
            personalReemplazo.setArchivos(archivos);
            List<Archivo> adicionales = new ArrayList<>();
            Archivo adicionalBD = new Archivo();
            ListadoDetalle tipoArchivoAdicional = listadoDetalleService.obtenerListadoDetalle("DOCUMENTO_EVAL_INI_SERV_ADICIONAL", "CONTRATO_ALQUILER_CAMIONETA");
            logger.info("tipoArchivoAdicional: {}", tipoArchivoAdicional);
            adicionalBD.setTipoArchivo(tipoArchivoAdicional);
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
        } finally {
            archivoUtil.close(isLogoSicoes);
            archivoUtil.close(isLogoOsinergmin);
        }
        byte[] bytesSalida = output.toByteArray();
        archivo.setPeso((long) bytesSalida.length);
        archivo.setNroFolio(1L);
        archivo.setContenido(bytesSalida);
        return archivo;
    }

    private String flujoRevisionEvaluadorContratos(Contexto contexto,
                                                   PersonalReemplazo personalReemplazoToUpdate,
                                                   List<DocumentoReemplazo> listDocsAsociados) {

        List<DocumentoReemplazo> listDocumentosInforme = listDocsAsociados.stream()
                .filter(doc -> Constantes.LISTADO.SECCION_DOC_REEMPLAZO.INFORME.equals(doc.getSeccion().getCodigo()))
                .collect(Collectors.toList());
        List<DocumentoReemplazo> listDocumentosPersPropuestoSolSuperv = listDocsAsociados.stream()
                .filter(doc -> (doc.getSeccion().getIdListado().compareTo(97L) == 0)
                || doc.getSeccion().getIdListado().compareTo(104L) == 0)
                .collect(Collectors.toList());

        boolean allDocsConformeInforme = !listDocumentosInforme.isEmpty()
                && listDocumentosInforme.stream()
                .allMatch(doc -> !Objects.isNull(doc.getEvaluacion())
                        && Constantes.LISTADO.SI_NO.SI.equals(doc.getEvaluacion().getConforme()));
        boolean allDocsConformePersPropuestoSolSuperv = !listDocumentosPersPropuestoSolSuperv.isEmpty()
                && listDocumentosPersPropuestoSolSuperv.stream()
                .allMatch(doc -> !Objects.isNull(doc.getEvaluacion())
                        && Constantes.LISTADO.SI_NO.SI.equals(doc.getEvaluacion().getConforme()));

        if (!(allDocsConformeInforme && allDocsConformePersPropuestoSolSuperv)) {

            ListadoDetalle estadoPreliminar = listadoDetalleDao.listarListadoDetallePorCoodigo(
                            Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)
                    .stream()
                    .filter(resultado -> resultado.getOrden().compareTo(1L) == 0)
                    .findFirst()
                    .orElse(new ListadoDetalle());

            if (!allDocsConformeInforme) {
                personalReemplazoToUpdate.setEstadoRevisarEval(estadoPreliminar);
            }

            if (!allDocsConformePersPropuestoSolSuperv) {
                personalReemplazoToUpdate.setEstadoReemplazo(estadoPreliminar);
            }

            AuditoriaUtil.setAuditoriaActualizacion(personalReemplazoToUpdate, contexto);
            reemplazoDao.save(personalReemplazoToUpdate);

            return Constantes.ESTADO_REVISION_DOCS_REEMPLAZO.SUBSANAR;

        } else {
            ListadoDetalle estadoEnProceso = listadoDetalleDao.listarListadoDetallePorCoodigo(
                            Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO)
                    .stream()
                    .filter(resultado -> resultado.getOrden().compareTo(1L) == 0)
                    .findFirst()
                    .orElse(new ListadoDetalle());
            personalReemplazoToUpdate.setEstadoRevisarEval(estadoEnProceso);
            AuditoriaUtil.setAuditoriaActualizacion(personalReemplazoToUpdate, contexto);

            return Constantes.ESTADO_REVISION_DOCS_REEMPLAZO.OK;
        }
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
    public PersonalReemplazo registrarDocIniServ(PersonalReemplazo personalReemplazo, Contexto contexto) {
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

        Long idPerfContrato = existe.getIdSolicitud();
        SicoesSolicitud solicitud = sicoesSolicitudDao.obtenerSolicitudDetallado(idPerfContrato);

        //Validar que existan documentos de inicio de servicio
        existe.setEstadoDocIniServ(listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.ESTADO_SOLICITUD.EN_EVALUACION).get(0));
        existe.setEstadoEvalDocIniServ(listadoDetalleDao.listarListadoDetallePorCoodigo(
                Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR).get(0));
        if (contexto.getUsuario().getRoles().stream().anyMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.USUARIO_EXTERNO))) {
           logger.info("GENPDF:USUARIO EXTERNO - Registrar documentación inicio de servicio (Rol Empresa Supervisora)");
            //Integrar documentacion en la plataforma SIGED
            List<ListadoDetalle> detalleSeccion = listadoDetalleDao.listarListadoDetalle(Constantes.LISTADO.SECCIONES_REEMPLAZO_PERSONAL);
            List<Long> idsSeccion = detalleSeccion.stream()
                    .map(ListadoDetalle::getIdListadoDetalle)
                    .collect(Collectors.toList());
            List<DocumentoReemplazo> documentos = documentoReemDao.obtenerPorIdReemplazoSecciones(existe.getIdReemplazo(),idsSeccion);
            List<File> archivosAlfresco=null;

            for (DocumentoReemplazo documento : documentos) {
                ExpedienteInRO expedienteInRO = crearExpedienteAgregarDocumentos(solicitud, contexto);
                archivosAlfresco = archivoService.obtenerArchivosPorIdDocumentoReem(documento.getIdDocumento(), contexto);
                try {
                    DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO,archivosAlfresco);
                    if (documentoOutRO.getResultCode() != 1){
                        throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE,
                                documentoOutRO.getMessage());
                    }
                    Integer idArchivo = documentoOutRO.getArchivos().getArchivo().get(0).getIdArchivo();
                    Integer idDocumento = documentoOutRO.getCodigoDocumento();

                    documento.setIdArchivoSiged(String.valueOf(idArchivo));
                    documento.setIdDocumento(Long.valueOf(idDocumento));
                    documentoReemService.actualizar(documento,contexto);
                } catch (ValidacionException e) {
                    throw e;
                } catch (Exception e) {
                    logger.error("ERROR {} ", e.getMessage(), e);
                    throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE);
                }
            }
        } else {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ACCESO_NO_AUTORIZADO);
        }
        return reemplazoDao.save(existe);
    }

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
        Integer codigoTipoDocumento = Integer.parseInt(Objects.requireNonNull(env.getProperty("crear.expediente.parametros.tipo.documento.crear")));

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
        documento.setAppNameInvokes(SIGLA_PROYECTO);
        cs.setCodigoTipoIdentificacion(1);
        if (cs.getCodigoTipoIdentificacion() == 1) {
            cs.setNombre(solicitud.getSupervisora().getNombreRazonSocial());
            cs.setApellidoPaterno("-");
            cs.setApellidoMaterno("-");
        }
        cs.setRazonSocial(solicitud.getSupervisora().getNombreRazonSocial());
        cs.setNroIdentificacion(solicitud.getSupervisora().getNumeroDocumento());
        cs.setTipoCliente(Integer.parseInt(
                Objects.requireNonNull(env.getProperty("crear.expediente.parametros.tipo.cliente"))));
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
    public List<AprobacionReemp> buscarAprobacion(String requerimiento, Long tipoaprob , Long estadoaprob, Long tiposolicitud,
                                                  Long idcontratista, Long numexpediente) {
        List<AprobacionReemp>  resultado = new ArrayList<>();

          if(requerimiento.equals(Constantes.REQUERIMIENTO.EVAL_INFO_APROB_G2_GER_DIV)){
            resultado =  aprobacionReempDao.buscarEvalInfAprobTecG2(tipoaprob, estadoaprob, tiposolicitud,
                idcontratista, numexpediente, listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.TIPO_APROBACION.CODIGO,Constantes.LISTADO.TIPO_APROBACION.APROBAR).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION).getIdListadoDetalle(),
                listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO).getIdListadoDetalle());
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
         logger.info("obtenerEvaluacionDocumentacion");
         return evaluacionDocumentacionDao.obtenerListado(id, idsol)
               .orElseThrow(() -> new RuntimeException("Evaluación de documentación no encontrada"));
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
    public List<DocumentoInicioServ> obtenerDocumentosInicioServicio( Long id, String seccion){
        logger.info("ObtenerDocumentosInicioServicio");
        List<DocumentoInicioServ>  resultado = new ArrayList<>();
        if (seccion.equals(Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO)){

            resultado = documentoInicioServDao.documentosInicioServicio(id, listadoDao
                    .obtenerPorCodigo( Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_PERSONAL_PROPUESTO).getIdListado());
        }
        if (seccion.equals(Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ADICIONAL)){

            resultado = documentoInicioServDao.documentosInicioServicio(id, listadoDao
                    .obtenerPorCodigo( Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ADICIONAL).getIdListado());
        }
        if (seccion.equals(Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ACTA_INICIO)){

            resultado = documentoInicioServDao.documentosInicioServicio(id, listadoDao
                    .obtenerPorCodigo( Constantes.DOCUMENTOS_INICIO_SERVICIO.DOCUMENTO_EVAL_INI_SERV_ACTA_INICIO).getIdListado());
        }
        return resultado;
    }


    @Override
       public Boolean evaluarFechaDesvinculacion (Long id, Date fecha) {
        logger.info("evaluarFechaDesvinculacion");

        Boolean resultado =false;
        Optional<PersonalReemplazo> persoReempOpt = reemplazoDao.findById(id);
            PersonalReemplazo persoReempFinal = persoReempOpt.orElseThrow(()  -> new RuntimeException("reemplazo personal no encontrada"));
            if(persoReempFinal.getFeFechaFinalizacionContrato() == null) {
                throw new ValidacionException("No se encuentra la fecha");
            }
            if(fecha.before(persoReempFinal.getFeFechaFinalizacionContrato()) || fecha.equals(persoReempFinal.getFeFechaFinalizacionContrato())){
              if (!fecha.equals(persoReempFinal.getFeFechaDesvinculacion())){
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
            PersonalReemplazo persoReempFinal = persoReempOpt.orElseThrow(()  -> new RuntimeException("reemplazo personal no encontrada"));
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
	public Boolean evaluarDocumReemplazo(EvaluarDocuDTO evaluacion) {
       logger.info("evaluarDocumReemplazo");
           Boolean result = false;
           Usuario usu = usuarioDao.obtener(evaluacion.getIdEvaluadoPor());

           DocumentoReemplazo doc = documentoReemDao.findById(evaluacion.getIdDocumento())
                   .orElseThrow(()  -> new RuntimeException("documento no encontrado"));

       if(evaluacion.getIdEvalDocumento()!=null){
        Optional<EvaluarDocuReemplazo> evalOpt = evaluarDocuReemDao.findByIdDocumentoId(evaluacion.getIdEvalDocumento());
           EvaluarDocuReemplazo evalFinal = evalOpt.orElseThrow(()  -> new RuntimeException("evaluación no encontrada"));
           evalFinal.setDocumento(doc);
           evalFinal.setConforme(evaluacion.getConforme());
           evalFinal.setFechaEvaluacion(evaluacion.getFechaEvaluacion());
           evalFinal.setEvaluadoPor(usu);
           evalFinal.setFecActualizacion(new Date());
           evalFinal.setIpActualizacion(evaluacion.getIpActualizacion());
           evalFinal.setUsuActualizacion(evaluacion.getUsuActualizacion());
           evaluarDocuReemDao.save(evalFinal);
           result = true;
       }else{
           EvaluarDocuReemplazo evalNuevo = new EvaluarDocuReemplazo();
           evalNuevo.setConforme(evaluacion.getConforme());
           evalNuevo.setFechaEvaluacion(evaluacion.getFechaEvaluacion());
           evalNuevo.setEvaluadoPor(usu);
           evalNuevo.setFecActualizacion(new Date());
           evalNuevo.setIpActualizacion(evaluacion.getIpActualizacion());
           evalNuevo.setUsuActualizacion(evaluacion.getUsuActualizacion());
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

        if(conforme){

            solicitud.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.CONCLUIDO);
            solicitud.setDescripcionSolicitud(Constantes.DESC_PROCESO_PERF_CONTRATO.CONCLUIDO);
            existe.setEstadoReemplazo(listadoDetalleDao.listarListadoDetallePorCoodigo(
                    Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO).get(0));
            existe.setEstadoEvalDocIniServ(listadoDetalleDao.listarListadoDetallePorCoodigo(
                    Constantes.LISTADO.ESTADO_SOLICITUD.CONCLUIDO).get(0));
        }else{
            solicitud.setEstadoProcesoSolicitud(Constantes.ESTADO_PROCESO_PERF_CONTRATO.PRELIMINAR);
            solicitud.setDescripcionSolicitud(Constantes.DESC_PROCESO_PERF_CONTRATO.PRELIMINAR);

            //La plataforma SICOES descargará en formato PDF el resultado de la revisión de documentos de inicio de servicio.

            //La plataforma SICOES notificará mediante email al rol Empresa Supervisora que tiene que subsanar la carga de documentos de inicio de servicio.

        }

        AuditoriaUtil.setAuditoriaRegistro(existe,contexto);
        return reemplazoDao.save(existe);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonalReemplazo evaluarDocumentos(PersonalReemplazo personalReemplazo,Boolean conforme, String accion, Contexto contexto) {

        Long id = personalReemplazo.getIdReemplazo();
        if (id == null) {
            throw new ValidacionException("No existe id");
        }
        PersonalReemplazo existe = reemplazoDao.findById(id)
                .orElseThrow(() -> new ValidacionException("Id personal no enviado"));

        if (accion.equals("A")) {

              String nombreSupervisora = obtenerNombreSupervisora(existe);

            if (conforme) {
                existe.setEstadoReemplazo(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO)); //en proceso  ---ok
                existe.setEstadoEvalDoc(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO));  //en proceso
                existe.setEstadoAprobacionInforme(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.EN_APROBACION)); // en aprobacion  -ok
                existe.setEstadoEvalDocIniServ(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)); // preliminar
                    Long idPerfContrato = existe.getIdSolicitud();
                    SicoesSolicitud solicitud = sicoesSolicitudDao.obtenerSolicitudDetallado(idPerfContrato);
                    Aprobacion aprob = new Aprobacion();

                    aprob.setCoTipoAprobacion(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.TIPO_APROBACION.CODIGO,Constantes.LISTADO.TIPO_APROBACION.APROBAR));
                    aprob.setRemplazoPersonal(existe);
                    aprob.setNumeroExpediente(sicoesSolicitudDao.obtenerNumExpedienteAprobacion(solicitud.getIdSolicitud()));
                    DocumentoReemplazo doc = documentoReemDao.findById(solicitud.getIdDocInicio())
                            .orElseThrow(() -> new ValidacionException("Id documento no encontrado"));
                    aprob.setDocumento(doc);
                    aprob.setIdRol(5L);
                    aprob.setDeTp(solicitud.getSupervisora().getTipoPersona().getValor());
                    aprob.setIdContratista(solicitud.getSupervisora().getIdSupervisora());
                    aprob.setCoTipoSolicitud(solicitud.getTipoSolicitud());
                    aprob.setFechaIngreso(new Date());
                    aprob.setEstadoAprob(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.EN_APROBACION));
                    aprob.setEstadoAprobGerenteDiv(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO));
                    aprob.setEstadoAprobGerenteLinea(null);
                    AuditoriaUtil.setAuditoriaRegistro(aprob,contexto);
                    aprobacionDao.save(aprob);
                notificacionContratoService.notificarCargarDocumentosInicioServicio( nombreSupervisora,contexto);
            } else {
                existe.setEstadoReemplazo(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.BORRADOR)); //preliminar ---ok
                // enviar notificacion x email
                notificacionContratoService.notificarSubsanacionDocumentos( nombreSupervisora,contexto);
            }
        } else {
            existe.setEstadoReemplazo(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.ARCHIVADO)); //archivado   ---ok
            existe.setEstadoEvalDoc(listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_SOLICITUD.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.ARCHIVADO));  //archivado  ----OK
            //persoReempFinal.setIdPersonaBaja(10000000L);  //liberado ----> agregar lista---> HACE INSERTS
            //persoReempFinal.setIdPersonaPropuesta(1000000L); //bloqueado ---> agregar Lista  --> HACE INSERTS
            // buscar campo Estado personal y cambiarle el estado a propuesto
        }
           AuditoriaUtil.setAuditoriaRegistro(existe,contexto);

        return reemplazoDao.save(existe);
    }
}