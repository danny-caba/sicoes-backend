package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;

import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.Contrato;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.SolicitudPerfecionamientoContrato;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.ContratoDao;
import pe.gob.osinergmin.sicoes.repository.NotificacionDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.SolicitudPerfecionamientoContratoDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.HistorialAprobacionRenovacionService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.HistorialRequerimientoRenovacionService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.NotificacionRenovacionContratoService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.InformeRenovacionContratoMapper;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;


@Service
public class CrearInformeRenovacionContratoImpl {

    private final Logger logger = LogManager.getLogger(CrearInformeRenovacionContratoImpl.class);

    @Value("${path.jasper}")
    private String pathJasper;

    @Value("${crear.expediente.parametros.tipo.documento.adjuntar}")
    private String crearExpedienteParametrosTipoDocumentoAdjuntar;

    @Value("${crear.expediente.parametros.proceso}")
    private String crearExpedienteParametrosProceso;

    @Value("${siged.old.proyecto}")
    private String SIGLA_PROYECTO;

    @Value("${siged.titulo.informe.renovacion.contrato}")
    private String TITULO_INFORME_RENOVACION_CONTRATO;

    @Value("${siged.bus.server.id.usuario}")
    private String BUS_SERVER_ID_USUARIO;

    @Value("${crear.expediente.parametros.enumerado}")
    private String crearExpedienteParametrosEnumerado;

    @Value("${crear.expediente.parametros.esta.en.flujo}")
    private String crearExpedienteParametrosEstaEnFlujo;

    @Value("${crear.expediente.parametros.firmado}")
    private String crearExpedienteParametrosFirmado;

    @Value("${crear.expediente.parametros.crea.expediente.no}")
    private String crearExpedienteParametrosCreaExpedienteNo;

    @Value("${crear.expediente.parametros.crea.folio}")
    private String crearExpedienteParametrosCreaFolio;

    @Value("${crear.expediente.parametros.crea.publico}")
    private String crearExpedienteParametrosCreaPublico;

    @Value("${crear.expediente.parametros.tipo.cliente}")
    private String crearExpedienteParametrosTipoCliente;

    @Value("${crear.expediente.parametros.direccion.estado}")
    private String crearExpedienteParametrosDireccionEstado;

    @Value("${path.temporal}")
    private String pathTemporal;

    private final InformeRenovacionContratoDao informeRenovacionContratoDao;
    private final SigedOldConsumer sigedOldConsumer;
    private final SigedApiConsumer sigedApiConsumer;
    private final NotificacionRenovacionContratoService notificacionRenovacionContratoService;
    private final ArchivoDao archivoDao;
    private final ListadoDetalleService listadoDetalleService;
    private final SolicitudPerfecionamientoContratoDao solicitudPerfecionamientoContratoDao;
    private final ContratoDao contratoDao;
    private final SicoesSolicitudDao solicitudDao;
    private final UsuarioDao usuarioDao;
    private final NotificacionDao notificacionDao;
    private final RequerimientoRenovacionDao requerimientoRenovacionDao;
    private final RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Autowired
    private HistorialRequerimientoRenovacionService historialRequerimientoRenovacionService;

    @Autowired
    private HistorialAprobacionRenovacionService historialAprobacionRenovacionService;

    public CrearInformeRenovacionContratoImpl(
            InformeRenovacionContratoDao informeRenovacionContratoDao,
            SigedOldConsumer sigedOldConsumer,
            SigedApiConsumer sigedApiConsumer,
            NotificacionRenovacionContratoService notificacionRenovacionContratoService,
            ArchivoDao archivoDao,
            ListadoDetalleService listadoDetalleService,
            SolicitudPerfecionamientoContratoDao solicitudPerfecionamientoContratoDao,
            ContratoDao contratoDao,
            SicoesSolicitudDao solicitudDao,
            UsuarioDao usuarioDao,
            NotificacionDao notificacionDao,
            RequerimientoRenovacionDao requerimientoRenovacionDao,
            RequerimientoAprobacionDao requerimientoAprobacionDao
    ) {

        this.informeRenovacionContratoDao = informeRenovacionContratoDao;
        this.sigedOldConsumer = sigedOldConsumer;
        this.sigedApiConsumer = sigedApiConsumer;
        this.notificacionRenovacionContratoService = notificacionRenovacionContratoService;
        this.archivoDao = archivoDao;
        this.listadoDetalleService = listadoDetalleService;
        this.solicitudPerfecionamientoContratoDao = solicitudPerfecionamientoContratoDao;
        this.contratoDao = contratoDao;
        this.solicitudDao = solicitudDao;
        this.usuarioDao = usuarioDao;
        this.notificacionDao = notificacionDao;
        this.requerimientoRenovacionDao = requerimientoRenovacionDao;
        this.requerimientoAprobacionDao = requerimientoAprobacionDao;
    }


    public InformeRenovacionContratoDTO ejecutar(InformeRenovacionContratoDTO dto, Contexto contexto) {

        Long idSolicitud = dto.getRequerimiento().getSolicitudPerfil().getIdSolicitud();
        List<SolicitudPerfecionamientoContrato> listaPerfilesAprobadoresBySolicitud =
                solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdPerfilListadoDetalle(idSolicitud);

        if (listaPerfilesAprobadoresBySolicitud.isEmpty()) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.LISTADO_DETALLE_NO_ENCONTRADO, "No se encuentra el listado de detalle para IdSolcitud: " + idSolicitud);
        }

        Contrato contrato = contratoDao.findBySolicitudPerfContId(idSolicitud).orElseThrow(() -> new ValidacionException(
                Constantes.CODIGO_MENSAJE.CONTRATO_NO_ENCONTRADO,
                "Contrato no encontrado para idSolicitud: " + idSolicitud
        ));

        InformeRenovacionContrato nuevoInformeRenovacionContrato;
        InformeRenovacionContrato informe;
        if (dto.getIdInformeRenovacion() == null) {
            informe = InformeRenovacionContratoMapper.MAPPER.toEntity(dto);
        } else {
            informe = informeRenovacionContratoDao.findById(dto.getIdInformeRenovacion()).orElseThrow(
                    () -> new ValidacionException(Constantes.CODIGO_MENSAJE.INFORME_PRESUPUESTO_RENOVACION_CONTRATO_NO_ENCONTRADO)
            );
        }


        // IMPORTANTE: Este UUID será usado tanto para el informe como para el archivo en Alfresco
        AuditoriaUtil.setAuditoriaRegistro(informe, contexto);
        Usuario usuario = usuarioDao.obtener(Long.parseLong(informe.getUsuCreacion()));
        informe.setUsuario(usuario);
        if (Constantes.INFORME_RENOVACION.ESTADO_INCOMPLETO.equals(dto.getCompletado())) {

            informe.setVigente(Boolean.FALSE);
            informe.setRegistro(Constantes.ESTADO.ACTIVO);
            informe.setCompletado(Constantes.INFORME_RENOVACION.ESTADO_INCOMPLETO);
            informe.setObjeto(dto.getObjeto());
            informe.setBaseLegal(dto.getBaseLegal());
            informe.setAntecedentes(dto.getAntecedentes());
            informe.setJustificacion(dto.getJustificacion());
            informe.setNecesidad(dto.getNecesidad());
            informe.setConclusiones(dto.getConclusiones());
            informe.setEstadoAprobacionInforme(null);

            nuevoInformeRenovacionContrato = informeRenovacionContratoDao.save(informe);
        } else if (Constantes.INFORME_RENOVACION.ESTADO_COMPLETO.equals(dto.getCompletado())) {

            UUID uuid = UUID.randomUUID();
            String uuidInformeRenovacion = uuid.toString();
            informe.setUuiInfoRenovacion(uuidInformeRenovacion);


            RequerimientoRenovacion requerimientoRenovacion = requerimientoRenovacionDao.findByNuExpediente(
                    dto.getRequerimiento().getNuExpediente()).orElseThrow(() ->
                    new ValidacionException(Constantes.CODIGO_MENSAJE.LISTADO_DETALLE_NO_ENCONTRADO,
                            "No se encuentra el listado de detalle para IDSolcitud: " + idSolicitud));
            String nombreEmpresaSupervisora = requerimientoRenovacion.getSolicitudPerfil().getSupervisora().getNombres();
            String numExpediente = requerimientoRenovacion.getNuExpediente();
            informe.setVigente(Boolean.TRUE);
            informe.setRegistro(Constantes.ESTADO.ACTIVO);
            informe.setCompletado(Constantes.INFORME_RENOVACION.ESTADO_COMPLETO);
            informe.setObjeto(dto.getObjeto());
            informe.setBaseLegal(dto.getBaseLegal());
            informe.setAntecedentes(dto.getAntecedentes());
            informe.setJustificacion(dto.getJustificacion());
            informe.setNecesidad(dto.getNecesidad());
            informe.setConclusiones(dto.getConclusiones());

            // ACTUALIZADO: Establecer ES_APROBACION_INFORME = 944 (En proceso) cuando se completa y deriva a G1
            ListadoDetalle estadoInformeEnProceso = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REQ_RENOVACION.CODIGO, Constantes.LISTADO.ESTADO_SOLICITUD.EN_PROCESO);
            informe.setEstadoAprobacionInforme(estadoInformeEnProceso);

            // Verificar que el estado obtenido corresponda al código 944
            if (estadoInformeEnProceso != null) {
                logger.info("Estado En Proceso obtenido del catálogo para informe - ID: {}", estadoInformeEnProceso.getIdListadoDetalle());
                if (!estadoInformeEnProceso.getIdListadoDetalle().equals(944L)) {
                    logger.warn("ADVERTENCIA: El estado 'En Proceso' tiene ID {} pero se esperaba 944", estadoInformeEnProceso.getIdListadoDetalle());
                }
            } else {
                logger.error("No se pudo obtener estado En Proceso del catálogo para el informe");
            }

            // AGREGADO: Actualizar también el estado del requerimiento de renovación a "En proceso" (944)
            // Esto es requerido cuando se completa el informe y se deriva a G1
            if (requerimientoRenovacion != null) {
                requerimientoRenovacion.setEsReqRenovacion(944L); // En proceso
                requerimientoRenovacionDao.save(requerimientoRenovacion);
                logger.info("Estado del requerimiento de renovación actualizado a En Proceso (944) - ID: {}",
                        requerimientoRenovacion.getIdReqRenovacion());
            }

            File jrxml = new File(pathJasper + "Formato_Informe_RenovacionContrato.jrxml");
            Usuario usuarioG1 = usuarioDao.obtener(listaPerfilesAprobadoresBySolicitud.get(0).getIdAprobadorG1());

            SicoesSolicitud solicitud = solicitudDao.findSolicitudWithSupervisoraNative(informe.getRequerimiento().getSolicitudPerfil().getIdSolicitud())
                    .orElseThrow(
                            () -> new ValidacionException(
                                    Constantes.CODIGO_MENSAJE.SOLICITUD_NO_ENCONTRADA,
                                    "Solicitud para renovación no encontrado para idInformeRenovacion: " + informe.getRequerimiento().getSolicitudPerfil().getIdSolicitud()
                            )
                    );
            nombreEmpresaSupervisora = solicitud.getSupervisora().getNombreRazonSocial();

            ByteArrayOutputStream output = generarPdfOutputStream(informe, contexto.getUsuario().getNombreUsuario(), nombreEmpresaSupervisora, numExpediente, jrxml, contexto.getUsuario().getRoles().get(0).getNombre());
            byte[] bytesSalida = output.toByteArray();

            Archivo archivoInformePdf = asignarDatosArchivo(bytesSalida, numExpediente);
            archivoInformePdf.setIdContrato(contrato.getIdContrato());

            // Usar el UUID del informe para subir a Alfresco y obtener el UUID real del nodo
            String rutaAlfresco = sigedOldConsumer.subirArchivosAlfrescoRenovacionContratoConUuid(
                    requerimientoRenovacion.getIdReqRenovacion(),
                    archivoInformePdf,
                    uuidInformeRenovacion);

            // Actualizar el informe con el UUID real de Alfresco
            informe.setUuiInfoRenovacion(uuidInformeRenovacion);


            adjuntarDocumentoSiged(informe, archivoInformePdf.getNombreReal(), bytesSalida, solicitud);

            ListadoDetalle EnAprobacionEstadoAprobacionInforme = listadoDetalleService.obtenerListadoDetalle(
                    "ESTADO_REQUERIMIENTO",
                    "EN_APROBACION"
            );

            informe.setDeNombreArchivo(archivoInformePdf.getNombre());
            informe.setDeRutaArchivo(rutaAlfresco);
            informe.setEstadoAprobacionInforme(EnAprobacionEstadoAprobacionInforme);
            nuevoInformeRenovacionContrato = informeRenovacionContratoDao.save(informe);

            ListadoDetalle EnProcesoEstadoRequerimientoRenovacion = listadoDetalleService.obtenerListadoDetalle(
                    "ESTADO_REQUERIMIENTO",
                    "EN_PROCESO"
            );
            requerimientoRenovacion.setEstadoReqRenovacion(EnProcesoEstadoRequerimientoRenovacion);

            RequerimientoRenovacion requerimientoRenovacionResult = requerimientoRenovacionDao.save(requerimientoRenovacion);
            try {
                historialRequerimientoRenovacionService.registrarHistorialRequerimientoRenovacion(requerimientoRenovacionResult, contexto);
            } catch (Exception e) {
                throw new DataNotFoundException("Error al registrar historial de aprobación: " + e.getMessage(), e);
            }
            archivoInformePdf.setIdInformeRenovacion(nuevoInformeRenovacionContrato.getIdInformeRenovacion());

            archivoInformePdf.setCodigo(uuidInformeRenovacion);
            archivoInformePdf.setNombreAlFresco(rutaAlfresco);

            AuditoriaUtil.setAuditoriaRegistro(archivoInformePdf, contexto);
            archivoInformePdf = archivoDao.save(archivoInformePdf);

            logger.info("Archivo registrado en DB con ID: {} y Ruta Alfresco: {} ", archivoInformePdf.getIdArchivo(), rutaAlfresco);

            // Enviar notificación y capturar el ID
            logger.info("Iniciando proceso de notificación para usuarioG1: {} y expediente: {}", usuarioG1.getIdUsuario(), numExpediente);
            Long idNotificacion = notificacionRenovacionContratoService.notificacionInformePorAprobar(usuarioG1, numExpediente, contexto);
            logger.info("ID de notificación recibido: {}", idNotificacion);

            // Actualizar el informe con el ID de la notificación
            if (idNotificacion != null && idNotificacion > 0) {
                logger.info("Buscando notificación con ID: {}", idNotificacion);
                // Buscar la notificación por ID y asignarla al informe
                pe.gob.osinergmin.sicoes.model.Notificacion notificacion = notificacionDao.findById(idNotificacion).orElse(null);
                if (notificacion != null) {
                    logger.info("Notificación encontrada con ID: {}, asignando al informe: {}", idNotificacion, nuevoInformeRenovacionContrato.getIdInformeRenovacion());
                    nuevoInformeRenovacionContrato.setNotificacion(notificacion);
                    nuevoInformeRenovacionContrato = informeRenovacionContratoDao.save(nuevoInformeRenovacionContrato);
                    logger.info("Informe actualizado exitosamente con ID de notificación: {}", idNotificacion);
                } else {
                    logger.error("No se encontró la notificación con ID: {}", idNotificacion);
                }
            } else {
                logger.error("ID de notificación es null o inválido: {}", idNotificacion);
            }

            RequerimientoAprobacion requerimientoAprobacionG1 = buildRequerimientoAprobacionG1(
                    nuevoInformeRenovacionContrato.getIdInformeRenovacion(),
                    listaPerfilesAprobadoresBySolicitud.get(0).getIdAprobadorG1(),
                    contexto.getUsuario().getIdUsuario(),
                    contexto.getIp()

            );
            AuditoriaUtil.setAuditoriaRegistro(requerimientoAprobacionG1, contexto);
            RequerimientoAprobacion requerimientoAprobacionResult = requerimientoAprobacionDao.save(requerimientoAprobacionG1);
            historialAprobacionRenovacionService.registrarHistorialAprobacionRenovacion(requerimientoAprobacionResult, contexto);

            // CORREGIDO: Solo crear requerimiento de aprobación G1 (ID_GRUPO_LD = 542) al crear el informe
            // La creación del requerimiento G2 se debe hacer cuando G1 apruebe, no al crear el informe
            logger.info("Requerimiento de aprobación G1 creado exitosamente con ID: {} para informe: {}",
                    requerimientoAprobacionResult.getIdReqAprobacion(),
                    nuevoInformeRenovacionContrato.getIdInformeRenovacion());

        } else {
            throw new ValidacionException(
                    Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO);
        }
        return InformeRenovacionContratoMapper.MAPPER.toDTO(nuevoInformeRenovacionContrato);
    }

    private RequerimientoAprobacion buildRequerimientoAprobacionG1(Long idInformeRenovacion, Long idUsuarioG1, Long idUsuario, String ip) {

        RequerimientoAprobacion requerimientoAprobacionG1 = new RequerimientoAprobacion();
        requerimientoAprobacionG1.setFeAsignacion(new Date());
        requerimientoAprobacionG1.setFecCreacion(new Date());
        requerimientoAprobacionG1.setIpCreacion(ip);
        requerimientoAprobacionG1.setUsuCreacion(idUsuario.toString());
        requerimientoAprobacionG1.getUsuario().setIdUsuario(idUsuarioG1);
        requerimientoAprobacionG1.setIdInformeRenovacion(idInformeRenovacion);

        ListadoDetalle g1GrupoLD = listadoDetalleService.obtenerListadoDetalle(
                "GRUPOS",
                "G1"
        );
        if (g1GrupoLD == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.LISTADO_DETALLE_NO_ENCONTRADO, "No se encontró el grupo G1 en listado detalle");
        }
        requerimientoAprobacionG1.getGrupo().setIdListadoDetalle(g1GrupoLD.getIdListadoDetalle());
        logger.info("RequerimientoAprobacionG1 configurado con ID_GRUPO_LD: {} (debe ser 542 para G1)", g1GrupoLD.getIdListadoDetalle());
        ListadoDetalle asignadoEstadoLD = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_APROBACION",
                "ASIGNADO"
        );
        requerimientoAprobacionG1.getEstado().setIdListadoDetalle(asignadoEstadoLD.getIdListadoDetalle());

        ListadoDetalle tecnicoTipoEvaluadorLD = listadoDetalleService.obtenerListadoDetalle(
                "TIPO_EVALUADOR",
                "APROBADOR_TECNICO"
        );
        requerimientoAprobacionG1.getTipoAprobador().setIdListadoDetalle(tecnicoTipoEvaluadorLD.getIdListadoDetalle());

        ListadoDetalle grupoAprobadorLD = listadoDetalleService.obtenerListadoDetalle(
                "GRUPO_APROBACION",
                "JEFE_UNIDAD"
        );
        requerimientoAprobacionG1.setIdGrupoAprobadorLd(grupoAprobadorLD.getIdListadoDetalle());
        ListadoDetalle tipoAprobacionG1LD = listadoDetalleService.obtenerListadoDetalle(
                "TIPO_APROBACION",
                "APROBAR"
        );
        requerimientoAprobacionG1.setIdTipoLd(tipoAprobacionG1LD.getIdListadoDetalle());

        return requerimientoAprobacionG1;
    }

    private RequerimientoAprobacion buildRequerimientoAprobacionG2(Long idInformeRenovacion, Long idUsuarioG2, Long idUsuario, String ip) {

        RequerimientoAprobacion requerimientoAprobacionG2 = new RequerimientoAprobacion();
        requerimientoAprobacionG2.setFeAsignacion(new Date());
        requerimientoAprobacionG2.setFecCreacion(new Date());
        requerimientoAprobacionG2.setIpCreacion(ip);
        requerimientoAprobacionG2.setUsuCreacion(idUsuario.toString());
        requerimientoAprobacionG2.getUsuario().setIdUsuario(idUsuarioG2);
        requerimientoAprobacionG2.setIdInformeRenovacion(idInformeRenovacion);

        // Configurar como grupo G2
        ListadoDetalle g2GrupoLD = listadoDetalleService.obtenerListadoDetalle(
                "GRUPOS",
                "G2"
        );
        if (g2GrupoLD == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.LISTADO_DETALLE_NO_ENCONTRADO, "No se encontró el grupo G2 en listado detalle");
        }
        requerimientoAprobacionG2.getGrupo().setIdListadoDetalle(g2GrupoLD.getIdListadoDetalle());

        ListadoDetalle asignadoEstadoLD = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_APROBACION",
                "ASIGNADO"
        );
        if (asignadoEstadoLD == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.LISTADO_DETALLE_NO_ENCONTRADO, "No se encontró el estado ASIGNADO en listado detalle");
        }
        requerimientoAprobacionG2.getEstado().setIdListadoDetalle(asignadoEstadoLD.getIdListadoDetalle());

        ListadoDetalle tecnicoTipoEvaluadorLD = listadoDetalleService.obtenerListadoDetalle(
                "TIPO_EVALUADOR",
                "APROBADOR_TECNICO"
        );
        if (tecnicoTipoEvaluadorLD == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.LISTADO_DETALLE_NO_ENCONTRADO, "No se encontró el tipo APROBADOR_TECNICO en listado detalle");
        }
        requerimientoAprobacionG2.getTipoAprobador().setIdListadoDetalle(tecnicoTipoEvaluadorLD.getIdListadoDetalle());

        ListadoDetalle grupoAprobadorLD = listadoDetalleService.obtenerListadoDetalle(
                "GRUPO_APROBACION",
                "GERENTE"
        );
        if (grupoAprobadorLD == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.LISTADO_DETALLE_NO_ENCONTRADO, "No se encontró el grupo aprobador GERENTE en listado detalle");
        }
        requerimientoAprobacionG2.setIdGrupoAprobadorLd(grupoAprobadorLD.getIdListadoDetalle());

        ListadoDetalle tipoAprobacionLD = listadoDetalleService.obtenerListadoDetalle(
                "TIPO_APROBACION",
                "APROBAR"
        );
        if (tipoAprobacionLD == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.LISTADO_DETALLE_NO_ENCONTRADO, "No se encontró el tipo APROBAR en listado detalle");
        }
        requerimientoAprobacionG2.setIdTipoLd(tipoAprobacionLD.getIdListadoDetalle());

        ListadoDetalle g2 = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.GRUPOS.CODIGO,
                Constantes.LISTADO.GRUPOS.G2
        );

        // IMPORTANTE: Asignar el valor 962 para ID_FIRMADO_LD cuando ID_GRUPO_LD es 543 (G2)
        if (requerimientoAprobacionG2.getGrupo() != null && requerimientoAprobacionG2.getGrupo().equals(g2)) {
            ListadoDetalle estadoPendiente = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_FIRMADO.CODIGO,
                    Constantes.LISTADO.ESTADO_FIRMADO.PENDIENTE
            );
            requerimientoAprobacionG2.setIdFirmadoLd(estadoPendiente.getIdListadoDetalle());
        }

        logger.info("RequerimientoAprobacionG2 construido exitosamente para informe: {}, usuario G2: {}, grupo LD: {}",
                idInformeRenovacion, idUsuarioG2, requerimientoAprobacionG2.getGrupo().getIdListadoDetalle());

        return requerimientoAprobacionG2;
    }

//    public static String removerSufijoPdf(String nombreArchivo) {
//        if (nombreArchivo == null) {
//            return null;
//        }
//        // Remover .pdf al final, case insensitive
//        return nombreArchivo.replaceAll("(?i)\\.pdf$", "");
//    }

    private void adjuntarDocumentoSiged(InformeRenovacionContrato nuevoInformeRenovacionContrato, String nombre, byte[] bytesSalida, SicoesSolicitud solicitud) {
        List<File> archivosAlfresco = new ArrayList<>();
        ExpedienteInRO expedienteInRO = crearExpediente(
                nuevoInformeRenovacionContrato,
                Integer.parseInt(crearExpedienteParametrosTipoDocumentoAdjuntar),
                solicitud
        );
        File file = null;
        try {
//            String nombreSinExtension = removerSufijoPdf(nombre);
//            file = crearFileDesdeBytes(bytesSalida, nombreSinExtension, ".pdf");
//            archivosAlfresco.add(file);
            File dir = new File(pathTemporal+File.separator+"temporales" +File.separator+ nuevoInformeRenovacionContrato.getIdInformeRenovacion());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            file = new File(
                    pathTemporal+File.separator+"temporales" +File.separator+  nuevoInformeRenovacionContrato.getIdInformeRenovacion() + File.separator + nombre);
            FileUtils.writeByteArrayToFile(file, bytesSalida);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file == null)
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.FILE_NO_ENCONTRADO);

        archivosAlfresco.add(file);

        try {
            DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);

            logger.info("SIGED RESULT: {}", documentoOutRO.getMessage());
            if (documentoOutRO.getResultCode() != 1) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO, documentoOutRO.getMessage());
            }
        } catch (Exception e) {
            logger.error("Error al agregar documento informe renovacion contrato en SIGED", e);
        }
    }

//    public static File crearFileDesdeBytes(byte[] bytes, String prefix, String suffix) throws IOException {
//        // Crear archivo temporal en el directorio temporal del sistema
//        File tempFile = File.createTempFile(prefix, suffix);
//        // Escribir los bytes al archivo
//        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
//            fos.write(bytes);
//            fos.flush();
//        }
//        return tempFile;
//    }

    private Archivo asignarDatosArchivo(byte[] bytesSalida, String numExpediente) {
        Archivo archivo = new Archivo();
        archivo.setNombre("INFORME_RENOVACION_CONTRATO_" + numExpediente + ".pdf");
        archivo.setNombreReal("INFORME_RENOVACION_CONTRATO_" + numExpediente + ".pdf");
        archivo.setTipo("application/pdf");
        ListadoDetalle tipoArchivoLd = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                Constantes.LISTADO.TIPO_ARCHIVO.INFORME_RENOVACION_CONTRATO
        );
        if (tipoArchivoLd == null) {
            throw new RuntimeException("Estado 'renovacion contrato' no encontrado en listado detalle");
        }
        archivo.setPeso(bytesSalida.length * 1L);
        archivo.setNroFolio(1L);
        archivo.setContenido(bytesSalida);
        archivo.setTipoArchivo(tipoArchivoLd);
        archivo.setDescripcion("Informe del Requerimiento de Renovación de Contrato");

        ListadoDetalle estado = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_ARCHIVO",
                "CARGADO"
        );
        if (estado == null) {
            throw new RuntimeException("Estado 'estado' no encontrado en listado detalle");
        }
        archivo.setEstado(estado);
        return archivo;
    }

    private ByteArrayOutputStream generarPdfOutputStream(InformeRenovacionContrato informe,
                                                         String nombreEvaluador,
                                                         String nombreEmpresaSupervisora,
                                                         String numExpediente,
                                                         File jrxml,
                                                         String nombreRol) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JasperPrint print = null;

        try {
            JasperReport jasperReport = getJasperCompilado(jrxml);

            Map<String, Object> parameters = buildParameters(informe,
                    nombreEvaluador,
                    nombreEmpresaSupervisora,
                    numExpediente,
                    nombreRol);

            print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            output = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, output);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        }
        return output;
    }

    private Map<String, Object> buildParameters(InformeRenovacionContrato informe,
                                                String nombreEvaluador,
                                                String nombreEmpresaSupervisora,
                                                String numExpediente,
                                                String nombreRol
    ) {

        InputStream isLogoSicoes = null;
        InputStream isLogoOsinergmin = null;
        Map<String, Object> parameters = new HashMap<>();
        logger.info("SUBREPORT_DIR: {}", pathJasper);
        parameters.put("SUBREPORT_DIR", pathJasper);

        try {
            isLogoSicoes = Files.newInputStream(Paths.get(pathJasper + "logo-sicoes.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        parameters.put("P_LOGO_SICOES", isLogoSicoes);
        try {
            isLogoOsinergmin = Files.newInputStream(Paths.get(pathJasper + "logo-osinerming.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        parameters.put("P_LOGO_OSINERGMIN", isLogoOsinergmin);

        parameters.put("nombreAreaSolcitud", nombreRol);
        parameters.put("nombreEvaluador", nombreEvaluador);
        parameters.put("nombreEmpresaSupervisora", nombreEmpresaSupervisora);
        parameters.put("numExpediente", numExpediente);

        parameters.put("objecto", informe.getObjeto());
        parameters.put("baseLegal", informe.getBaseLegal());
        parameters.put("antecedentes", informe.getAntecedentes());
        parameters.put("justificacion", informe.getJustificacion());
        parameters.put("necesidad", informe.getNecesidad());
        parameters.put("conclusiones", informe.getConclusiones());

        return parameters;
    }

    public JasperReport getJasperCompilado(File path) throws JRException, FileNotFoundException {
        FileInputStream employeeReportStream = new FileInputStream(path);
        return JasperCompileManager.compileReport(employeeReportStream);
    }

    public ExpedienteInRO crearExpediente(InformeRenovacionContrato nuevoInformeRenovacionContrato, Integer codigoTipoDocumento, SicoesSolicitud solicitud) {
        ExpedienteInRO expediente = new ExpedienteInRO();
        DocumentoInRO documento = new DocumentoInRO();
        ClienteListInRO clientes = new ClienteListInRO();
        ClienteInRO cs = new ClienteInRO();
        List<ClienteInRO> cliente = new ArrayList<>();
        DireccionxClienteListInRO direcciones = new DireccionxClienteListInRO();
        DireccionxClienteInRO d = new DireccionxClienteInRO();
        List<DireccionxClienteInRO> direccion = new ArrayList<>();

        Integer codigoUsuario = 1;

        expediente.setProceso(Integer.parseInt(crearExpedienteParametrosProceso));
        expediente.setDocumento(documento);
        String numExpediente = nuevoInformeRenovacionContrato.getRequerimiento().getNuExpediente();
        expediente.setNroExpediente(numExpediente);

        documento.setAsunto(TITULO_INFORME_RENOVACION_CONTRATO);
        documento.setAppNameInvokes(SIGLA_PROYECTO);

        cs.setCodigoTipoIdentificacion(Integer.parseInt("1"));
        cs.setNombre("Osinergmin");
        cs.setApellidoPaterno("-");
        cs.setApellidoMaterno("-");
        cs.setRepresentanteLegal("-");
        cs.setRazonSocial(solicitud.getSupervisora().getNombreRazonSocial());
        cs.setNroIdentificacion(solicitud.getSupervisora().getNumeroDocumento());
        cs.setTipoCliente(Integer.parseInt(crearExpedienteParametrosTipoCliente));
        cliente.add(cs);

        d.setDireccion("-");
        d.setDireccionPrincipal(true);
        d.setEstado(crearExpedienteParametrosDireccionEstado.charAt(0));
        d.setTelefono("-");
        d.setUbigeo(Integer.parseInt("150101"));
        direccion.add(d);

        direcciones.setDireccion(direccion);
        cs.setDirecciones(direcciones);
        clientes.setCliente(cliente);
        documento.setClientes(clientes);
        documento.setCodTipoDocumento(codigoTipoDocumento);

        documento.setUsuarioCreador(Integer.parseInt(BUS_SERVER_ID_USUARIO));
        documento.setEnumerado(crearExpedienteParametrosEnumerado.charAt(0));
        documento.setEstaEnFlujo(crearExpedienteParametrosEstaEnFlujo.charAt(0));
        documento.setFirmado(crearExpedienteParametrosFirmado.charAt(0));
        documento.setCreaExpediente(crearExpedienteParametrosCreaExpedienteNo.charAt(0));
        documento.setNroFolios(Integer.parseInt(crearExpedienteParametrosCreaFolio));
        documento.setPublico(crearExpedienteParametrosCreaPublico.charAt(0));
        documento.setUsuarioCreador(codigoUsuario);

        logger.info("DOC_EXPEDIENTE {}", documento);
        logger.info("EXPEDIENTE_INFORME_RENOVACION {}", expediente);
        return expediente;
    }
}
