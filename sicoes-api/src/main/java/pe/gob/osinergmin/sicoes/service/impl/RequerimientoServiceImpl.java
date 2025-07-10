package pe.gob.osinergmin.sicoes.service.impl;

import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_FIN_ANTES_INICIO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_INICIO_ANTES_HOY;

import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import net.sf.jasperreports.engine.*;
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
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.DivisionDTO;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.repository.PerfilAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.util.*;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RequerimientoServiceImpl implements RequerimientoService {

    private static final Logger logger = LogManager.getLogger(RequerimientoServiceImpl.class);

    @Autowired
    private RequerimientoDao requerimientoDao;

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    private Environment env;

    @Value("${solicitud.requerimiento.contrato.pn}")
    private String SOLICITUD_REQUERIMIENTO_CONTRATO_PN;

    @Value("${solicitud.requerimiento.contrato.supervisor}")
    private String SOLICITUD_REQUERIMIENTO_CONTRATO_SUPERVISOR;

    @Value("${siged.old.proyecto}")
    private String SIGLA_PROYECTO;

    @Value("${siged.ws.cliente.osinergmin.numero.documento}")
    private String OSI_DOCUMENTO;

    @Autowired
    private ArchivoService archivoService;

    @Autowired
    private SigedApiConsumer sigedApiConsumer;

    @Value("${path.temporal}")
    private String pathTemporal;

    @Value("${path.jasper}")
    private String pathJasper;

    @Autowired
    private ArchivoUtil archivoUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRolService usuarioRolService;

    @Autowired
    private RolService rolService;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private PerfilAprobadorDao perfilAprobadorDao;

    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Override
    @Transactional
    public Requerimiento guardar(Requerimiento requerimiento, Contexto contexto) {
        AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
        if (requerimiento.getEstado() == null || requerimiento.getEstado().getIdListadoDetalle() == null) {
            ListadoDetalle estadoPreliminar = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO,
                    Constantes.LISTADO.ESTADO_REQUERIMIENTO.PRELIMINAR
            );
            requerimiento.setEstado(estadoPreliminar);
        }
        requerimiento.setFeRegistro(new Date());
        requerimiento.setRequerimientoUuid(UUID.randomUUID().toString());
        Requerimiento requerimientoDB = requerimientoDao.save(requerimiento);
        ExpedienteInRO expedienteInRO = crearExpediente(
                requerimientoDB,
                Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear"))
        );
        List<File> archivosAlfresco = new ArrayList<>();
        Archivo archivo = archivoRequerimiento(requerimientoDB, contexto);
        archivoService.guardarXRequerimiento(archivo, contexto);
        File file = fileRequerimiento(archivo, requerimientoDB.getIdRequerimiento());
        archivosAlfresco.add(file);
        ExpedienteOutRO expedienteOutRO = sigedApiConsumer.crearExpediente(expedienteInRO, archivosAlfresco);
        logger.info("SIGED RESULT: {}", expedienteOutRO.getMessage());
        if (expedienteOutRO.getResultCode() != 1) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE, expedienteOutRO.getMessage());
        }
        if (expedienteOutRO.getResultCode() == 1) {
            requerimiento.setNuExpediente(expedienteOutRO.getCodigoExpediente());
        }
        requerimiento = requerimientoDao.save(requerimiento);
        return requerimiento;
    }

    private ExpedienteInRO crearExpediente(Requerimiento requerimiento, Integer codigoTipoDocumento) {
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
        if (requerimiento.getNuExpediente() != null) {
            expediente.setNroExpediente(requerimiento.getNuExpediente());
        }
        documento.setAsunto(SOLICITUD_REQUERIMIENTO_CONTRATO_PN);
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

    private Archivo archivoRequerimiento(Requerimiento requerimiento, Contexto contexto) {
        ListadoDetalle perfil = listadoDetalleService.obtener(requerimiento.getPerfil().getIdListadoDetalle(), contexto);
        requerimiento.setPerfil(perfil);
        requerimiento.setUsuarioCreador(usuarioService.obtener(Long.valueOf(requerimiento.getUsuCreacion())));
        Archivo archivo = new Archivo();
        Long idRequerimiento = requerimiento.getIdRequerimiento();
        archivo.setIdRequerimiento(idRequerimiento);
        archivo.setNombre("Solicitud_Requerimiento_Contrato_PN_" + idRequerimiento + ".pdf");
        archivo.setNombreReal("Solicitud_Requerimiento_Contrato_PN_" + idRequerimiento + ".pdf");
        archivo.setTipo("application/pdf");
        ByteArrayOutputStream output;
        JasperPrint print;
        InputStream appLogo = null;
        InputStream osinermingLogo = null;
        try {
            File jrxml = new File(pathJasper + "Formato_04_Requerimiento.jrxml");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SUBREPORT_DIR", pathJasper);
            appLogo = Files.newInputStream(Paths.get(pathJasper + "logo-sicoes.png"));
            osinermingLogo = Files.newInputStream(Paths.get(pathJasper + "logo-osinerming.png"));
            parameters.put("P_LOGO_APP", appLogo);
            parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
            List<Requerimiento> requerimientos = new ArrayList<>();
            requerimientos.add(requerimiento);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(requerimientos);
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

    @Override
    public Requerimiento obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {

    }

    @Override
    @Transactional
    public Page<Requerimiento> listar(FiltroRequerimientoDTO filtro, Pageable pageable, Contexto contexto) {
        Rol rol = rolService.obtenerCodigo(Constantes.ROLES.COORDINADOR_GESTION);
        List<UsuarioRol> usuariosCoordinador = usuarioRolService.obtenerUsuarioRolPorRol(rol);
        Optional<UsuarioRol> coordinador = usuariosCoordinador.stream()
                .filter(u -> u.getUsuario().getIdUsuario()
                        .equals(contexto.getUsuario().getIdUsuario())).findFirst();
        List<Long> divisionIds;
        List<DivisionDTO> divisiones;
        if (coordinador.isPresent()) {
            divisiones = divisionService.listarDivisionesCoordinador(contexto);
        } else {
            divisiones = divisionService.listarDivisiones();
        }
        if (divisiones == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ERROR_LISTA_DIVISIONES);
        }
        divisionIds = divisiones.stream().map(DivisionDTO::getIdDivision).collect(Collectors.toList());
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
//        Division division = filtro.getDivision() != null ? crearDivision(filtro.getDivision()) : null;
//        ListadoDetalle perfil = filtro.getPerfil() != null ? crearListadoDetalle(filtro.getPerfil()) : null;
//        Supervisora supervisora = filtro.getSupervisora() != null ? crearSupervisora(filtro.getSupervisora()) : null;
//        ListadoDetalle estadoAprobacion = filtro.getEstadoAprobacion() != null ? crearListadoDetalle(filtro.getEstadoAprobacion()) : null;
//        return requerimientoDao.listarRequerimientos(division, perfil, fechaInicio, fechaFin, supervisora, estadoAprobacion, divisionIds, pageable);
        return requerimientoDao.listarRequerimientos(filtro.getDivision(), filtro.getPerfil(), fechaInicio, fechaFin, filtro.getSupervisora(), filtro.getEstadoAprobacion(), divisionIds, pageable);
    }

//    private Division crearDivision(Long id) {
//        Division division = new Division();
//        division.setIdDivision(id);
//        return division;
//    }
//
//    private ListadoDetalle crearListadoDetalle(Long id) {
//        ListadoDetalle detalle = new ListadoDetalle();
//        detalle.setIdListadoDetalle(id);
//        return detalle;
//    }
//
//    private Supervisora crearSupervisora(Long id) {
//        Supervisora s = new Supervisora();
//        s.setIdSupervisora(id);
//        return s;
//    }

    @Override
    @Transactional
    public Requerimiento archivar(Requerimiento requerimiento, Contexto contexto) {
        ListadoDetalle estadoEnProceso = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_PROCESO
        );
        if (estadoEnProceso == null) {
            throw new ValidacionException("Estado EN PROCESO no configurado en ListadoDetalle");
        }
        if (!estadoEnProceso.getCodigo().equals(requerimiento.getEstado().getCodigo())) {
            throw new ValidacionException("Solo se puede archivar un requerimiento en estado EN PROCESO");
        }
        ListadoDetalle enAprobacion = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_APROBACION
        );
        if (enAprobacion == null) {
            throw new IllegalStateException("Estado EN APROBACIÓN no configurado en ListadoDetalle");
        }
        requerimiento.setEstado(enAprobacion);
        AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
        ExpedienteInRO expedienteInRO = crearExpediente(
                requerimiento,
                Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear"))
        );
        List<File> archivosAlfresco = new ArrayList<>();
        RequerimientoAprobacion aprobadorG2 = asignarAprobadorG2(requerimiento, contexto);
        Archivo archivo = archivoRequerimiento(aprobadorG2, contexto);
        archivoService.guardarXRequerimientoAprobacion(archivo, contexto);
        File file = fileRequerimiento(archivo, aprobadorG2.getRequerimiento().getIdRequerimiento());
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
        Requerimiento requerimientoDB = requerimientoDao.save(requerimiento);
        Usuario usuarioAprobador = usuarioService.obtener(aprobadorG2.getUsuario().getIdUsuario());
        notificacionService.enviarMensajeSolicitudFirmaArchivamientoRequerimiento(usuarioAprobador, requerimientoDB, contexto);
        return requerimientoDB;
    }

    private ExpedienteInRO crearExpediente(RequerimientoAprobacion requerimientoAprobacion, Integer codigoTipoDocumento) {
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
        if (requerimientoAprobacion.getRequerimiento().getNuExpediente() != null) {
            expediente.setNroExpediente(requerimientoAprobacion.getRequerimiento().getNuExpediente());
        }
        documento.setAsunto(SOLICITUD_REQUERIMIENTO_CONTRATO_SUPERVISOR);
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

    private Archivo archivoRequerimiento(RequerimientoAprobacion requerimientoAprobacion, Contexto contexto) {
        ListadoDetalle perfil = listadoDetalleService.obtener(requerimientoAprobacion.getRequerimiento().getPerfil().getIdListadoDetalle(), contexto);
        requerimientoAprobacion.getRequerimiento().setPerfil(perfil);
        requerimientoAprobacion.getRequerimiento().setUsuarioCreador(usuarioService.obtener(Long.valueOf(requerimientoAprobacion.getRequerimiento().getUsuCreacion())));
        Archivo archivo = new Archivo();
        Long idRequerimiento = requerimientoAprobacion.getRequerimiento().getIdRequerimiento();
        archivo.setIdRequerimiento(idRequerimiento);
        archivo.setNombre("Solicitud_Requerimiento_Contrato_Sup_" + idRequerimiento + ".pdf");
        archivo.setNombreReal("Solicitud_Requerimiento_Contrato_Sup_" + idRequerimiento + ".pdf");
        archivo.setTipo("application/pdf");
        ByteArrayOutputStream output;
        JasperPrint print;
        InputStream appLogo = null;
        InputStream osinermingLogo = null;
        try {
            File jrxml = new File(pathJasper + "Formato_04_Requerimiento_Aprobacion.jrxml");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SUBREPORT_DIR", pathJasper);
            appLogo = Files.newInputStream(Paths.get(pathJasper + "logo-sicoes.png"));
            osinermingLogo = Files.newInputStream(Paths.get(pathJasper + "logo-osinerming.png"));
            parameters.put("P_LOGO_APP", appLogo);
            parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
            List<RequerimientoAprobacion> requerimientosAprobacion = new ArrayList<>();
            requerimientosAprobacion.add(requerimientoAprobacion);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(requerimientosAprobacion);
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

    @Override
    public Optional<Requerimiento> obtenerPorId(Long id) {
        return requerimientoDao.obtener(id);
    }

    @Override
    public Long obtenerId(String requerimientoUuid) {
        if (requerimientoUuid == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_REQUERIMIENTO_NO_ENVIADO);
        }
        return requerimientoDao.obtenerId(requerimientoUuid);
    }

    @Override
    public Requerimiento obtenerPorUuid(String requerimientoUuid) {
        return requerimientoDao.findByRequerimientoUuid(requerimientoUuid);
    }

    private RequerimientoAprobacion asignarAprobadorG2(Requerimiento requerimiento, Contexto contexto) {
        RequerimientoAprobacion requerimientoAprobacion = new RequerimientoAprobacion();
        PerfilAprobador perfilAprobador = perfilAprobadorDao
                .findFirstByPerfilIdListadoDetalle(requerimiento.getPerfil().getIdListadoDetalle())
                .orElseThrow(() -> new IllegalStateException("No se encontró perfil aprobador para el perfil del requerimiento"));
        Usuario aprobadorG2 = perfilAprobador.getAprobadorG2();
        if (aprobadorG2 == null) {
            throw new IllegalStateException("No se encontró aprobador G2 para el perfil del requerimiento");
        }
        requerimientoAprobacion.setUsuario(aprobadorG2);
        ListadoDetalle asignado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO
        );
        if (asignado == null) {
            throw new IllegalStateException("Estado ASIGNADO no configurado en ListadoDetalle");
        }
        ListadoDetalle firmaPendiente = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_FIRMA.CODIGO,
                Constantes.LISTADO.ESTADO_FIRMA.PENDIENTE);
        ListadoDetalle grupo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_GRUPO_APROBACION.CODIGO,
                Constantes.LISTADO.ESTADO_GRUPO_APROBACION.GERENTE);
        ListadoDetalle tipo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_TIPO_APROBACION.CODIGO,
                Constantes.LISTADO.ESTADO_TIPO_APROBACION.APROBAR);
        requerimientoAprobacion.setEstado(asignado);
        requerimientoAprobacion.setRequerimiento(requerimiento);
        requerimientoAprobacion.setFirmado(firmaPendiente);
        requerimientoAprobacion.setGrupo(grupo);
        requerimientoAprobacion.setTipo(tipo);
        AuditoriaUtil.setAuditoriaRegistro(requerimientoAprobacion, contexto);
        return requerimientoAprobacionDao.save(requerimientoAprobacion);
    }

}
