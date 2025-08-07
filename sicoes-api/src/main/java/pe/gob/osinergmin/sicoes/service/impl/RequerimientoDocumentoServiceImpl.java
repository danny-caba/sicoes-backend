package pe.gob.osinergmin.sicoes.service.impl;

import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ACCESO_NO_AUTORIZADO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_FIN_ANTES_INICIO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_INICIO_ANTES_HOY;
import static pe.gob.osinergmin.sicoes.util.Constantes.ROLES.EVALUADOR_CONTRATOS;

import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
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
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumento;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumentoDetalle;
import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoCoordinadorDTO;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoDTO;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDocumentoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDocumentoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.RequerimientoDocumentoService;
import pe.gob.osinergmin.sicoes.service.RequerimientoService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.ArchivoUtil;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RequerimientoDocumentoServiceImpl implements RequerimientoDocumentoService {

    private static final Logger logger = LogManager.getLogger(RequerimientoDocumentoServiceImpl.class);

    @Autowired
    private RequerimientoDocumentoDao requerimientoDocumentoDao;

    @Autowired
    private RequerimientoDocumentoDetalleDao requerimientoDocumentoDetalleDao;

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    private SupervisoraService supervisoraService;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private SigedApiConsumer sigedApiConsumer;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RequerimientoService requerimientoService;

    @Autowired
    private ArchivoUtil archivoUtil;

    @Autowired
    private ArchivoService archivoService;

    @Autowired
    private RequerimientoInvitacionDao requerimientoInvitacionDao;

    @Autowired
    private ArchivoDao archivoDao;

    @Autowired
    private Environment env;

    @Value("${invitacion.supervisor.persona.natural}")
    private String INVITACION_SUPERVISOR_PERSONA_NATURAL;

    @Value("${resultado.evaluacion.documentos}")
    private String RESULTADO_EVALUACION_DOCUMENTOS;

    @Value("${siged.old.proyecto}")
    private String SIGLA_PROYECTO;

    @Value("${siged.ws.cliente.osinergmin.numero.documento}")
    private String OSI_DOCUMENTO;

    @Value("${path.jasper}")
    private String pathJasper;

    @Value("${path.temporal}")
    private String pathTemporal;

    public Page<RequerimientoDocumento> listarRequerimientosDocumentos(FiltroRequerimientoDocumentoDTO filtro, Pageable pageable, Contexto contexto) {
        Date fechaInicio = filtro.getFechaInicio();
        Date fechaFin = filtro.getFechaFin();
        if (fechaInicio != null && fechaInicio.after(new Date())) {
            throw new ValidacionException(ERROR_FECHA_INICIO_ANTES_HOY);
        }
        if (fechaInicio != null && fechaFin != null && fechaFin.before(fechaInicio)) {
            throw new ValidacionException(ERROR_FECHA_FIN_ANTES_INICIO);
        }
        if (fechaInicio != null) fechaInicio = DateUtil.getInitDay(fechaInicio);
        if (fechaFin != null) fechaFin = DateUtil.getEndDay(fechaFin);
        Supervisora supervisora = supervisoraService.obtenerSupervisoraXRUCVigente(contexto.getUsuario().getCodigoRuc());
        return requerimientoDocumentoDao.listarRequerimientosDocumentos(supervisora.getIdSupervisora(), filtro.getEstado(), fechaInicio, fechaFin, pageable);
    }

    @Override
    public List<RequerimientoDocumentoDetalle> listarRequerimientosDocumentosDetalle(String requerimientoUuid, Contexto contexto) {
        Long idOrigenRequisito = null;
        if (contexto.getUsuario().getCodigoUsuarioInterno() == null) {
            ListadoDetalle origenRequisito = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ORIGEN_REQUISITO.CODIGO,
                    Constantes.LISTADO.ORIGEN_REQUISITO.EXTERNO);
            idOrigenRequisito = origenRequisito.getIdListadoDetalle();
        }
        List<RequerimientoDocumentoDetalle> listaDetalle = requerimientoDocumentoDetalleDao.listarPorUuid(requerimientoUuid, idOrigenRequisito);

        if (listaDetalle == null) {
            return Collections.emptyList();
        }

        return listaDetalle.stream()
                .filter(Objects::nonNull)
                .map(this::cargarArchivo)
                .collect(Collectors.toList());
    }

    private RequerimientoDocumentoDetalle cargarArchivo(RequerimientoDocumentoDetalle detalle) {
        if (Objects.equals(detalle.getOrigenRequisito().getCodigo(), Constantes.LISTADO.ORIGEN_REQUISITO.REQUERIMIENTO)) {
            Archivo informe = archivoDao.obtenerTipoArchivoRequerimiento(
                    detalle.getRequerimientoDocumento().getRequerimiento().getIdRequerimiento(),
                    Constantes.LISTADO.TIPO_ARCHIVO.INFORME_REQUERIMIENTO);
            detalle.setArchivo(informe);
        } else {
            List<Archivo> list = archivoService.buscarPorReqDocDetalle(detalle.getIdRequerimientoDocumentoDetalle());
            if (!list.isEmpty()) {
                detalle.setArchivo(list.get(0));
            }
        }
        return detalle;
    }

    @Override
    @Transactional
    public List<RequerimientoDocumentoDetalle> actualizarRequerimientosDocumentosDetalle(List<RequerimientoDocumentoDetalle> lstDetalle, Contexto contexto){
        RequerimientoDocumento requerimientoDocumentoDB = actualizarEstadoReqDocumento(lstDetalle, contexto);
        List<RequerimientoDocumentoDetalle> lstDetalleDB = lstDetalle.stream()
                .map( rd -> guardarFlagPresentado(rd, requerimientoDocumentoDB, contexto))
                .collect(Collectors.toList());
        ExpedienteInRO expedienteInRO = crearExpediente(
                requerimientoDocumentoDB,
                Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear"))
        );
        List<File> archivosAlfresco = new ArrayList<>();
        for (RequerimientoDocumentoDetalle detalle : lstDetalle) {
            RequerimientoDocumentoDetalle detalleDB = requerimientoDocumentoDetalleDao.buscarPorUuid(detalle.getRequerimientoDocumentoDetalleUuid());
            detalle.setDescripcionRequisito(detalleDB.getDescripcionRequisito());
        }
        Archivo archivo = archivoRequerimiento(requerimientoDocumentoDB, lstDetalle);
        archivoService.guardarXRequerimientoDocumento(archivo, contexto);
        File file = fileRequerimiento(archivo, requerimientoDocumentoDB.getRequerimiento().getIdRequerimiento());
        archivosAlfresco.add(file);
        try {
            DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
            logger.info("SIGED RESULT: {}", documentoOutRO.getMessage());
            if (documentoOutRO.getResultCode() != 1) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO, documentoOutRO.getMessage());
            }
        } catch (ValidacionException e) {
            logger.error("ERROR 2 {} ", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("ERROR {} ", e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE, e.getMessage());
        }
        return lstDetalleDB;
    }

    private RequerimientoDocumento actualizarEstadoReqDocumento(List<RequerimientoDocumentoDetalle> detalle, Contexto contexto) {
        String requerimientoDocumentoUuid = requerimientoDocumentoDetalleDao
                .obtenerRequerimientoDocumentoUuidPorDetalleUuid(detalle.get(0).getRequerimientoDocumentoDetalleUuid());
        RequerimientoDocumento requerimientoDocumento = requerimientoDocumentoDao.obtenerPorUuid(requerimientoDocumentoUuid)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_DOCUMENTO_NO_ENCONTRADO));
        ListadoDetalle estadoEnProceso = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.EN_PROCESO);
        requerimientoDocumento.setEstado(estadoEnProceso);
        requerimientoDocumento.setFechaIngreso(new Date());
        AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumento, contexto);
        return requerimientoDocumentoDao.save(requerimientoDocumento);
    }

    private RequerimientoDocumentoDetalle guardarFlagPresentado(RequerimientoDocumentoDetalle detalle, RequerimientoDocumento documento, Contexto contexto) {
        RequerimientoDocumentoDetalle detalleDB = requerimientoDocumentoDetalleDao.buscarPorUuid(detalle.getRequerimientoDocumentoDetalleUuid());
        detalleDB.setArchivo(detalle.getArchivo());
        detalleDB.setRequerimientoDocumento(documento);
        if (detalleDB.getArchivo() != null) {
            detalleDB.setPresentado(Constantes.FLAG.PRESENTADO);
        } else {
            detalleDB.setPresentado(Constantes.FLAG.NO_PRESENTADO);
        }
        AuditoriaUtil.setAuditoriaActualizacion(detalleDB, contexto);
        return requerimientoDocumentoDetalleDao.save(detalleDB);
    }

    private ExpedienteInRO crearExpediente(RequerimientoDocumento requerimientoDocumento, Integer codigoTipoDocumento) {
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
        if (requerimientoDocumento.getRequerimiento().getNuExpediente() != null) {
            expediente.setNroExpediente(requerimientoDocumento.getRequerimiento().getNuExpediente());
        }
        documento.setAsunto(INVITACION_SUPERVISOR_PERSONA_NATURAL);
        documento.setAppNameInvokes(SIGLA_PROYECTO);
        documento.setCodTipoDocumento(codigoTipoDocumento);
        documento.setNroFolios(Integer.parseInt(env.getProperty("crear.expediente.parametros.crea.folio")));
        documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
        if (Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.respuesta.solicitud.pn")) == codigoTipoDocumento) {
            documento.setFirmante(Integer.parseInt(env.getProperty("siged.firmante.informe.respuesta.id.usuario")));
        }
        cs.setCodigoTipoIdentificacion(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
        Supervisora supervisora = requerimientoDocumento.getRequerimiento().getSupervisora();
        cs.setNombre(supervisora.getNombres());
        cs.setApellidoPaterno(supervisora.getApellidoPaterno());
        cs.setApellidoMaterno(supervisora.getApellidoMaterno());
        cs.setRazonSocial(supervisora.getNombreRazonSocial());
        cs.setNroIdentificacion(OSI_DOCUMENTO);
        cs.setTipoCliente(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
        cliente.add(cs);
        d.setDireccion(supervisora.getDireccion());
        d.setDireccionPrincipal(true);
        d.setEstado(env.getProperty("crear.expediente.parametros.direccion.estado").charAt(0));
        d.setTelefono(supervisora.getTelefono1());
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

    private Archivo archivoRequerimiento(RequerimientoDocumento requerimientoDocumento, List<RequerimientoDocumentoDetalle> lstDetalle) {
        Archivo archivo = new Archivo();
        Long idRequerimiento = requerimientoDocumento.getRequerimiento().getIdRequerimiento();
        archivo.setIdRequerimiento(idRequerimiento);
        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
            Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
            Constantes.LISTADO.TIPO_ARCHIVO.DOCUMENTO_REQUERIMIENTO);
        archivo.setTipoArchivo(tipoArchivo);
        String nombreArchivo = requerimientoDocumento.getTipo().getCodigo().equals(Constantes.LISTADO.TIPO_REQ_DOCUMENTO.REGISTRO)
                ? "Presentacion_Documentos_RN.pdf"
                : "Subsanacion_Documentos_PN.pdf";
        archivo.setNombre(nombreArchivo);
        archivo.setNombreReal(nombreArchivo);
        archivo.setTipo("application/pdf");
        ByteArrayOutputStream output;
        RequerimientoInvitacion invitacion = requerimientoInvitacionDao
                .buscarPorIdRequerimiento(idRequerimiento).orElse(null);
        List<RequerimientoDocumentoDetalle> lstDetalleFormated = lstDetalle.stream().map(detalle -> {
            if (detalle.getArchivo() != null) {
                Long peso = detalle.getArchivo().getPeso() / 8 / 1024;
                detalle.getArchivo().setPeso(peso);
            }
            return detalle;
        }).collect(Collectors.toList());
        JasperPrint print;
        InputStream appLogo = null;
        InputStream osinermingLogo = null;
        RequerimientoDocumento requerimientoDocumentoJasper = new RequerimientoDocumento();
        requerimientoDocumentoJasper.setFechaIngreso(requerimientoDocumento.getFechaIngreso());
        requerimientoDocumentoJasper.setFechaInvitacion(invitacion.getFechaInvitacion());
        requerimientoDocumentoJasper.setRequerimiento(requerimientoDocumento.getRequerimiento());
        requerimientoDocumentoJasper.setRequerimientosDocumentosDetalles(lstDetalleFormated);
        try {
            File jrxml = new File(pathJasper + "Formato_04_Requerimiento_Documento.jrxml");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SUBREPORT_DIR", pathJasper);
            appLogo = Files.newInputStream(Paths.get(pathJasper + "logo-sicoes.png"));
            osinermingLogo = Files.newInputStream(Paths.get(pathJasper + "logo-osinerming.png"));
            parameters.put("P_LOGO_APP", appLogo);
            parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
            List<RequerimientoDocumento> requerimientosDocumentos = new ArrayList<>();
            requerimientosDocumentos.add(requerimientoDocumentoJasper);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(requerimientosDocumentos);
            JasperReport jasperReport = archivoUtil.getJasperCompilado(jrxml);
            print = JasperFillManager.fillReport(jasperReport, parameters, ds);
            output = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, output);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_GUARDAR_FORMATO_04, e);
        } finally {
            archivoUtil.close(appLogo);
            archivoUtil.close(osinermingLogo);
        }
        byte[] bytesSalida = output.toByteArray();
        archivo.setPeso((long) bytesSalida.length);
        archivo.setNroFolio(1L);
        archivo.setContenido(bytesSalida);
        return archivo;
    }

    private File fileRequerimiento(Archivo archivo, Long idRequerimiento) {
        try {
            String dirPath = pathTemporal + File.separator + "temporales" + File.separator + idRequerimiento;
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

    public Page<RequerimientoDocumento> listarRequerimientosDocumentosCoordinador(FiltroRequerimientoDocumentoCoordinadorDTO filtro, Pageable pageable, Contexto contexto) {
        Date fechaInicio = filtro.getFechaInicio();
        Date fechaFin = filtro.getFechaFin();
        if (fechaInicio != null && fechaInicio.after(new Date())) {
            throw new ValidacionException(ERROR_FECHA_INICIO_ANTES_HOY);
        }
        if (fechaInicio != null && fechaFin != null && fechaFin.before(fechaInicio)) {
            throw new ValidacionException(ERROR_FECHA_FIN_ANTES_INICIO);
        }
        if (fechaInicio != null) fechaInicio = DateUtil.getInitDay(fechaInicio);
        if (fechaFin != null) fechaFin = DateUtil.getEndDay(fechaFin);
        return requerimientoDocumentoDao.listarRequerimientosDocumentosCoordinador(contexto.getUsuario().getIdUsuario(), filtro.getDivision(), filtro.getPerfil(), filtro.getSupervisora(), filtro.getEstado(), fechaInicio, fechaFin, pageable);
    }

    @Override
    @Transactional
    public RequerimientoDocumentoDetalle patchRequerimientoDocumentoDetalle(RequerimientoDocumentoDetalle requerimientoDocumentoDetalle, Contexto contexto) {
        validarReqDocumentoDetalle(requerimientoDocumentoDetalle);
        RequerimientoDocumentoDetalle detalleDB = requerimientoDocumentoDetalleDao.buscarPorUuid(requerimientoDocumentoDetalle.getRequerimientoDocumentoDetalleUuid());
        detalleDB.setEvaluacion(requerimientoDocumentoDetalle.getEvaluacion());
        detalleDB.setObservacion(requerimientoDocumentoDetalle.getObservacion());
        detalleDB.setUsuario(contexto.getUsuario());
        detalleDB.setFechaEvaluacion(new Date());
        AuditoriaUtil.setAuditoriaRegistro(detalleDB, contexto);
        return requerimientoDocumentoDetalleDao.save(detalleDB);
    }

    private void validarReqDocumentoDetalle(RequerimientoDocumentoDetalle detalle) {
        if (detalle.getEvaluacion().getCodigo().equals(
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO_DETALLE.OBSERVADO)) {
            if (detalle.getObservacion() == null || detalle.getObservacion().trim().isEmpty()) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.OBSERVACION_AUSENTE);
            }
        } else {
            if (detalle.getEvaluacion() == null) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.EVALUACION_AUSENTE);
            }
        }
    }

    @Override
    @Transactional
    public RequerimientoDocumento evaluarRequerimientosDocumento(String uuid, Contexto contexto) {
        ListadoDetalle origenRequisito = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ORIGEN_REQUISITO.CODIGO,
                Constantes.LISTADO.ORIGEN_REQUISITO.EXTERNO);
        List<RequerimientoDocumentoDetalle> listaDetalle = requerimientoDocumentoDetalleDao.listarPorUuid(uuid, origenRequisito.getIdListadoDetalle());
        boolean todosCargadosYEvaluados = true;
        for (RequerimientoDocumentoDetalle detalle : listaDetalle) {
            String presentado = detalle.getPresentado();
            ListadoDetalle evaluacion = detalle.getEvaluacion();
            logger.info("Detalle UUID: {}, Presentado: {}, Evaluación: {}",
                    detalle.getRequerimientoDocumentoDetalleUuid(),
                    presentado,
                    (evaluacion != null ? evaluacion.getNombre() : "null"));
            if (!Objects.equals(Constantes.FLAG.PRESENTADO, presentado) || evaluacion == null) {
                todosCargadosYEvaluados = false;
                break;
            }
        }
        if (todosCargadosYEvaluados) {
            boolean todosCumplen = listaDetalle.stream()
                    .allMatch(det -> "CUMPLE".equalsIgnoreCase(det.getEvaluacion().getNombre()));
            RequerimientoDocumento requerimientoDocumento = requerimientoDocumentoDao.obtenerPorUuid(uuid)
                    .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_DOCUMENTO_NO_ENCONTRADO));
            if (todosCumplen) {
                return todosCumplen(requerimientoDocumento, listaDetalle, contexto);
            } else {
                RequerimientoDocumento requerimientoDocumentoClon = clonarSolicitud(requerimientoDocumento, contexto);
                Supervisora supervisoraPN = supervisoraService.obtener(requerimientoDocumento.getRequerimiento().getSupervisora().getIdSupervisora(), contexto);
                notificacionService.enviarRequerimientoEvaluacion(supervisoraPN, requerimientoDocumento, contexto);
                logger.info("Al menos un documento está observado.");
                ListadoDetalle estadoObservado = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.CODIGO,
                        Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.OBSERVADO);
                requerimientoDocumento.setEstado(estadoObservado);
                requerimientoDocumento.setFlagActivo(Constantes.FLAG.NO_PRESENTADO);
                return requerimientoDocumentoClon;
            }
        } else {
            logger.info("Existen documentos no presentados o sin evaluación.");
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.DOCUMENTOS_SIN_CARGAR_EVALUAR);
        }
    }

    private RequerimientoDocumento todosCumplen(RequerimientoDocumento requerimientoDocumento, List<RequerimientoDocumentoDetalle> listaDetalle, Contexto contexto) {
        logger.info("Todos los documentos cumplen con la evaluación.");
        ListadoDetalle estadoConcluido = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.CONCLUIDO
        );
        if (estadoConcluido == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_CONCLUIDO_NO_CONFIGURADO_EN_LISTADODETALLE);
        }
        AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumento, contexto);
        requerimientoDocumento.setEstado(estadoConcluido);
        ExpedienteInRO expedienteInRO = crearExpediente2(
                requerimientoDocumento,
                Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear"))
        );
        List<File> archivosAlfresco = new ArrayList<>();
        Archivo archivo = archivoRequerimiento2(requerimientoDocumento, listaDetalle);
        archivoService.guardarXRequerimientoDocumento(archivo, contexto);
        File file = fileRequerimiento(archivo, requerimientoDocumento.getRequerimiento().getIdRequerimiento());
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
        return requerimientoDocumentoDao.save(requerimientoDocumento);
    }

    private ExpedienteInRO crearExpediente2(RequerimientoDocumento requerimientoDocumento, Integer codigoTipoDocumento) {
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
        if (requerimientoDocumento.getRequerimiento().getNuExpediente() != null) {
            expediente.setNroExpediente(requerimientoDocumento.getRequerimiento().getNuExpediente());
        }
        documento.setAsunto(RESULTADO_EVALUACION_DOCUMENTOS);
        documento.setAppNameInvokes(SIGLA_PROYECTO);
        documento.setCodTipoDocumento(codigoTipoDocumento);
        documento.setNroFolios(Integer.parseInt(env.getProperty("crear.expediente.parametros.crea.folio")));
        documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
        if (Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.informe.respuesta.solicitud.pn")) == codigoTipoDocumento) {
            documento.setFirmante(Integer.parseInt(env.getProperty("siged.firmante.informe.respuesta.id.usuario")));
        }
        cs.setCodigoTipoIdentificacion(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
        Supervisora supervisora = requerimientoDocumento.getRequerimiento().getSupervisora();
        cs.setNombre(supervisora.getNombres());
        cs.setApellidoPaterno(supervisora.getApellidoPaterno());
        cs.setApellidoMaterno(supervisora.getApellidoMaterno());
        cs.setRazonSocial(supervisora.getNombreRazonSocial());
        cs.setNroIdentificacion(OSI_DOCUMENTO);
        cs.setTipoCliente(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
        cliente.add(cs);
        d.setDireccion(supervisora.getDireccion());
        d.setDireccionPrincipal(true);
        d.setEstado(env.getProperty("crear.expediente.parametros.direccion.estado").charAt(0));
        d.setTelefono(supervisora.getTelefono1());
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

    private Archivo archivoRequerimiento2(RequerimientoDocumento requerimientoDocumento, List<RequerimientoDocumentoDetalle> lstDetalle) {
        Archivo archivo = new Archivo();
        Long idRequerimiento = requerimientoDocumento.getRequerimiento().getIdRequerimiento();
        archivo.setIdRequerimiento(idRequerimiento);
        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                Constantes.LISTADO.TIPO_ARCHIVO.DOCUMENTO_REQUERIMIENTO);
        archivo.setTipoArchivo(tipoArchivo);
        String nombreArchivo = "Resultado_Evaluacion_Documentos_PN.pdf";
        archivo.setNombre(nombreArchivo);
        archivo.setNombreReal(nombreArchivo);
        archivo.setTipo("application/pdf");
        ByteArrayOutputStream output;
        List<RequerimientoDocumentoDetalle> lstDetalleFormated = lstDetalle.stream().map(detalle -> {
            if (detalle.getArchivo() != null) {
                Long peso = detalle.getArchivo().getPeso() / 8 / 1024;
                detalle.getArchivo().setPeso(peso);
            }
            return detalle;
        }).collect(Collectors.toList());
        JasperPrint print;
        InputStream appLogo = null;
        InputStream osinermingLogo = null;
        RequerimientoDocumento requerimientoDocumentoJasper = new RequerimientoDocumento();
        requerimientoDocumentoJasper.setRequerimiento(requerimientoDocumento.getRequerimiento());
        requerimientoDocumentoJasper.setRequerimientosDocumentosDetalles(lstDetalleFormated);
        try {
            File jrxml = new File(pathJasper + "Formato_04_Req_Doc_Evaluacion.jrxml");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SUBREPORT_DIR", pathJasper);
            appLogo = Files.newInputStream(Paths.get(pathJasper + "logo-sicoes.png"));
            osinermingLogo = Files.newInputStream(Paths.get(pathJasper + "logo-osinerming.png"));
            parameters.put("P_LOGO_APP", appLogo);
            parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
            List<RequerimientoDocumento> requerimientosDocumentos = new ArrayList<>();
            requerimientosDocumentos.add(requerimientoDocumentoJasper);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(requerimientosDocumentos);
            JasperReport jasperReport = archivoUtil.getJasperCompilado(jrxml);
            print = JasperFillManager.fillReport(jasperReport, parameters, ds);
            output = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, output);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_GUARDAR_FORMATO_04, e);
        } finally {
            archivoUtil.close(appLogo);
            archivoUtil.close(osinermingLogo);
        }
        byte[] bytesSalida = output.toByteArray();
        archivo.setPeso((long) bytesSalida.length);
        archivo.setNroFolio(1L);
        archivo.setContenido(bytesSalida);
        return archivo;
    }

    private RequerimientoDocumento clonarSolicitud(RequerimientoDocumento requerimientoDocumentoBD, Contexto contexto) {
        RequerimientoDocumento requerimientoDocumentoNuevo = new RequerimientoDocumento();
        requerimientoDocumentoNuevo.setRequerimiento(requerimientoDocumentoBD.getRequerimiento());
        ListadoDetalle estadoSolicitudPreliminar = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.SOLICITUD_PRELIMINAR
        );
        if (estadoSolicitudPreliminar == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_SOLICITUD_PRELIMINAR_NO_CONFIGURADO_EN_LISTADODETALLE);
        }
        requerimientoDocumentoNuevo.setEstado(estadoSolicitudPreliminar);
        requerimientoDocumentoNuevo.setFlagActivo(Constantes.FLAG.ACTIVO_STR);
        requerimientoDocumentoNuevo.setFechaIngreso(new Date());
        ListadoDetalle tipoSubsanacion = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_REQ_DOCUMENTO.CODIGO,
                Constantes.LISTADO.TIPO_REQ_DOCUMENTO.SUBSANACION
        );
        if (tipoSubsanacion == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.TIPO_SUBSANACION_NO_CONFIGURADO_EN_LISTADODETALLE);
        }
        requerimientoDocumentoNuevo.setTipo(tipoSubsanacion);
        requerimientoDocumentoNuevo.setFechaplazoEntrega(requerimientoDocumentoBD.getFechaplazoEntrega());
        requerimientoDocumentoNuevo.setRevision(requerimientoDocumentoBD.getRevision());
        requerimientoDocumentoNuevo.setRequerimientoDocumentoUuid(UUID.randomUUID().toString());
        requerimientoDocumentoNuevo.setContrato(requerimientoDocumentoBD.getContrato());
        requerimientoDocumentoNuevo.setIdRequerimientoDocumentoPadre(requerimientoDocumentoBD.getIdRequerimientoDocumento());
        requerimientoDocumentoNuevo.setRequerimientosDocumentoUuidPadre(requerimientoDocumentoBD.getRequerimientoDocumentoUuid());
        AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumentoNuevo, contexto);
        RequerimientoDocumento clonRequerimientoDocumento = requerimientoDocumentoDao.save(requerimientoDocumentoNuevo);
        List<RequerimientoDocumentoDetalle> listaDetalle = requerimientoDocumentoDetalleDao.listarPorUuid(requerimientoDocumentoBD.getRequerimientoDocumentoUuid(), null);
        for (RequerimientoDocumentoDetalle requerimientoDocumentoDetalleBD : listaDetalle) {
            RequerimientoDocumentoDetalle requerimientoDocumentoDetalleNuevo = new RequerimientoDocumentoDetalle();
            requerimientoDocumentoDetalleNuevo.setRequerimientoDocumentoDetalleUuid(UUID.randomUUID().toString());
            requerimientoDocumentoDetalleNuevo.setRequerimientoDocumento(clonRequerimientoDocumento);
            requerimientoDocumentoDetalleNuevo.setDescripcionRequisito(requerimientoDocumentoDetalleBD.getDescripcionRequisito());
            requerimientoDocumentoDetalleNuevo.setRequisito(requerimientoDocumentoDetalleBD.getRequisito());
            requerimientoDocumentoDetalleNuevo.setPresentado(Constantes.FLAG.NO_PRESENTADO);
            requerimientoDocumentoDetalleNuevo.setOrigenRequisito(requerimientoDocumentoDetalleBD.getOrigenRequisito());

            if(requerimientoDocumentoDetalleBD.getOrigenRequisito().getCodigo().equals(Constantes.LISTADO.ORIGEN_REQUISITO.EXTERNO) &&
                    !Constantes.LISTADO.ESTADO_REQ_DOCUMENTO_DETALLE.OBSERVADO.equals(requerimientoDocumentoDetalleBD.getEvaluacion().getCodigo())){
                requerimientoDocumentoDetalleNuevo.setEvaluacion(requerimientoDocumentoDetalleBD.getEvaluacion());
                requerimientoDocumentoDetalleNuevo.setUsuario(requerimientoDocumentoDetalleBD.getUsuario());
                requerimientoDocumentoDetalleNuevo.setFechaEvaluacion(requerimientoDocumentoDetalleBD.getFechaEvaluacion());
                requerimientoDocumentoDetalleNuevo.setObservacion(requerimientoDocumentoDetalleBD.getObservacion());
                requerimientoDocumentoDetalleNuevo.setPresentado(requerimientoDocumentoDetalleBD.getPresentado());
            }

            AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumentoDetalleNuevo, contexto);
            requerimientoDocumentoDetalleDao.save(requerimientoDocumentoDetalleNuevo);
            // Clonar archivo asociado al detalle (solo externos)
            if (!requerimientoDocumentoDetalleBD.getOrigenRequisito().getCodigo().equals(Constantes.LISTADO.ORIGEN_REQUISITO.EXTERNO)) {
                continue;
            }
            Archivo archivoBD = archivoService.obtenerArchivoPorReqDocumentoDetalle(requerimientoDocumentoDetalleBD.getRequerimientoDocumentoDetalleUuid(), contexto);
            Archivo archivoNuevo = getArchivoNuevo(archivoBD, requerimientoDocumentoNuevo, requerimientoDocumentoDetalleNuevo);
            AuditoriaUtil.setAuditoriaRegistro(archivoNuevo, contexto);
            archivoDao.save(archivoNuevo);
        }
        return clonRequerimientoDocumento;
    }

    private static Archivo getArchivoNuevo(Archivo archivoBD, RequerimientoDocumento requerimientoDocumentoNuevo, RequerimientoDocumentoDetalle requerimientoDocumentoDetalleNuevo) {
        Archivo archivoNuevo = new Archivo();
        archivoNuevo.setEstado(archivoBD.getEstado());
        archivoNuevo.setNombreReal(archivoBD.getNombreReal());
        archivoNuevo.setNombreAlFresco(archivoBD.getNombreAlFresco());
        archivoNuevo.setCodigo(UUID.randomUUID().toString());
        archivoNuevo.setTipoArchivo(archivoBD.getTipoArchivo());
        archivoNuevo.setTipo(archivoBD.getTipo());
        archivoNuevo.setNroFolio(archivoBD.getNroFolio());
        archivoNuevo.setPeso(archivoBD.getPeso());
        archivoNuevo.setIdRequerimiento(requerimientoDocumentoNuevo.getRequerimiento().getIdRequerimiento());
        archivoNuevo.setIdReqDocumentoDetalle(requerimientoDocumentoDetalleNuevo.getIdRequerimientoDocumentoDetalle());
        return archivoNuevo;
    }

    @Override
    @Transactional
    public RequerimientoDocumento revisar(String documentoUuid, List<RequerimientoDocumentoDetalle> detalleDocumento, Contexto contexto) {
        //Validar Req. Documento en estado concluido?
        if(contexto.getUsuario().getRoles().stream().noneMatch(rol -> rol.getCodigo().equals(EVALUADOR_CONTRATOS))) {
            throw new ValidacionException(ACCESO_NO_AUTORIZADO);
        }
        List<RequerimientoDocumentoDetalle> detallesBD = new ArrayList<>();
        for(RequerimientoDocumentoDetalle detalles: detalleDocumento) {
            RequerimientoDocumentoDetalle detalle = requerimientoDocumentoDetalleDao.findById(detalles.getIdRequerimientoDocumentoDetalle())
                    .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_DOCUMENTO_DETALLE_NO_ENCONTRADO));
            detalle.setFlagVistoBueno(detalles.getFlagVistoBueno());
            detallesBD.add(detalle);
        }
        RequerimientoDocumento requerimientoDocumento = requerimientoDocumentoDao.obtenerPorUuid(documentoUuid)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_DOCUMENTO_NO_ENCONTRADO));

        List<RequerimientoDocumentoDetalle> detalleDocumentoSinVistoBueno = detallesBD
                .stream()
                .filter(det -> det.getFlagVistoBueno().equals(Constantes.FLAG.NO_PRESENTADO))
                .collect(Collectors.toList());
        List<RequerimientoDocumentoDetalle> detalleDocumentoExterno = detalleDocumentoSinVistoBueno
                .stream()
                .filter(det -> det.getOrigenRequisito().getCodigo().equals(Constantes.LISTADO.ORIGEN_REQUISITO.EXTERNO))
                .collect(Collectors.toList());
        List<RequerimientoDocumentoDetalle> detalleDocumentoInterno = detalleDocumentoSinVistoBueno
                .stream()
                .filter(det -> det.getOrigenRequisito().getCodigo().equals(Constantes.LISTADO.ORIGEN_REQUISITO.REQUERIMIENTO))
                .collect(Collectors.toList());

        if(!detalleDocumentoExterno.isEmpty()) {//Sin visto bueno: Requisitos Supervisor
            Supervisora supervisora = requerimientoDocumento.getRequerimiento().getSupervisora();

            //update req doc estado solicitud preliminar
            ListadoDetalle estadoEnProceso = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.CODIGO,
                    Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.SOLICITUD_PRELIMINAR
            );
            requerimientoDocumento.setEstado(estadoEnProceso);
            AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumento, contexto);
            requerimientoDocumento = requerimientoDocumentoDao.save(requerimientoDocumento);

            //Enviar Notificacion Supervisor
            notificacionService.enviarMensajeVistoBuenoSupervisor(supervisora, detalleDocumentoExterno, contexto);
        }

        if(!detalleDocumentoInterno.isEmpty()) {//Sin visto bueno: Requisitos Coordinador Gestion
            String correoCoordinador = requerimientoDocumento.getRequerimiento().getDivision().getUsuario().getCorreo();
            //Enviar Notificacion Coordinador
            notificacionService.enviarMensajeVistoBuenoCoordinador(correoCoordinador, contexto);
        }

        //Actualizar Visto Bueno
        for (RequerimientoDocumentoDetalle requerimientoDocumentoDetalle : detallesBD) {
            AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumentoDetalle, contexto);
            requerimientoDocumentoDetalleDao.save(requerimientoDocumentoDetalle);
        }
        return requerimientoDocumento;
    }

    @Override
    public RequerimientoDocumento guardar(RequerimientoDocumento model, Contexto contexto) {
        return null;
    }

    @Override
    public RequerimientoDocumento obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {

    }

}
