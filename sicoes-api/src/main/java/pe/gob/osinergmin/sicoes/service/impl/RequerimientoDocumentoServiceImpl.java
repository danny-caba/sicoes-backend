package pe.gob.osinergmin.sicoes.service.impl;

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
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumento;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumentoDetalle;
import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoCoordinadorDTO;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoDTO;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDocumentoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDocumentoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.RequerimientoDocumentoService;
import pe.gob.osinergmin.sicoes.service.RequerimientoInvitacionService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_FIN_ANTES_INICIO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_INICIO_ANTES_HOY;

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
    private Environment env;

    @Value("${invitacion.supervisor.persona.natural}")
    private String INVITACION_SUPERVISOR_PERSONA_NATURAL;

    @Value("${siged.old.proyecto}")
    private String SIGLA_PROYECTO;

    @Value("${siged.ws.cliente.osinergmin.numero.documento}")
    private String OSI_DOCUMENTO;

    @Autowired
    private UsuarioService usuarioService;

    @Value("${path.jasper}")
    private String pathJasper;

    @Autowired
    private ArchivoUtil archivoUtil;

    @Autowired
    private ArchivoService archivoService;

    @Value("${path.temporal}")
    private String pathTemporal;

    @Autowired
    private SigedApiConsumer sigedApiConsumer;
    @Autowired
    private SupervisoraService supervisoraService;
    @Autowired
    private RequerimientoInvitacionService requerimientoInvitacionService;
    @Autowired
    private RequerimientoInvitacionDao requerimientoInvitacionDao;
    @Autowired
    private RequerimientoService requerimientoService;

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
    public List<RequerimientoDocumentoDetalle> listarRequerimientosDocumentosDetalle(String requerimientoUuid) {
        List<RequerimientoDocumentoDetalle> listaDetalle = requerimientoDocumentoDetalleDao.listarPorUuid(requerimientoUuid);
        return listaDetalle.stream().map(this::cargarArchivo).collect(Collectors.toList());
    }

    private RequerimientoDocumentoDetalle cargarArchivo(RequerimientoDocumentoDetalle requerimientoDocumentoDetalle) {
        List<Archivo> list = archivoService.buscarPorReqDocDetalle(requerimientoDocumentoDetalle.getIdRequerimientoDocumentoDetalle());
        if (!list.isEmpty() && list != null) {
            requerimientoDocumentoDetalle.setArchivo(list.get(0));
        }

        return requerimientoDocumentoDetalle;
    }

    @Override
    @Transactional
    public List<RequerimientoDocumentoDetalle> actualizarRequerimientosDocumentosDetalle(
            List<RequerimientoDocumentoDetalle> lstDetalle, Contexto contexto){

        RequerimientoDocumento requerimientoDocumentoDB = actualizarEstadoReqDocumento(lstDetalle, contexto);

        List<RequerimientoDocumentoDetalle> lstDetalleDB = lstDetalle.stream()
                        .map( rd -> guardarFlagPresentado(rd, requerimientoDocumentoDB, contexto))
                .collect(Collectors.toList());

        // TODO: MODIFICAR DATOS DE CLIENTE CON LOS DATOS DEL SUPERVISOR PERSONA NATURAL
        ExpedienteInRO expedienteInRO = crearExpediente(
                requerimientoDocumentoDB,
                Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear"))
        );

        List<File> archivosAlfresco = new ArrayList<>();
        Archivo archivo = archivoRequerimiento(requerimientoDocumentoDB, lstDetalle, contexto);
        archivoService.guardarXRequerimientoDocumento(archivo, contexto);

        File file = fileRequerimiento(archivo, requerimientoDocumentoDB.getRequerimiento().getIdRequerimiento());
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
        return null;
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

    private Archivo archivoRequerimiento(
            RequerimientoDocumento requerimientoDocumento,
            List<RequerimientoDocumentoDetalle> lstDetalle, Contexto contexto) {

        Archivo archivo = new Archivo();

        Long idRequerimiento = requerimientoDocumento.getRequerimiento().getIdRequerimiento();
        archivo.setIdRequerimiento(idRequerimiento);

        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
            Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
            Constantes.LISTADO.TIPO_ARCHIVO.DOCUMENTO_REQUERIMIENTO);

        archivo.setTipoArchivo(tipoArchivo);

//        archivo.setIdRequimientoDocumento(requerimientoDocumento.getIdRequerimientoDocumento());

        archivo.setNombre("Requerimie_Documento_" + (new Date()).getTime() + ".pdf");
        archivo.setNombreReal("Requerimie_Documento_" + (new Date()).getTime() + ".pdf");
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
        // TODO: HACER LA FUNCION PARA EN NOMBRES ENVIAR NOMBRES Y APELLIDOS O RAZON SOCIAL SEGUN CORRESPONDA
        RequerimientoDocumento requerimientoDocumentoJasper = new RequerimientoDocumento();
        requerimientoDocumentoJasper.setFechaIngreso(requerimientoDocumento.getFechaIngreso());
        requerimientoDocumentoJasper.setFechaInvitacion(invitacion.getFechaInvitacion());
        requerimientoDocumentoJasper.setRequerimiento(requerimientoDocumento.getRequerimiento());
        requerimientoDocumentoJasper.setRequerimientosDocumentosDetalles(lstDetalleFormated);
        try {
            File jrxml = new File(pathJasper + "Formato_04_Requerimiento_Documento.jrxml");
            File jrxml2= new File(pathJasper + "Formato_04_Requerimiento_Documento_Presentados.jrxml");
            JasperReport jasperReport2 =  archivoUtil.getJasperCompilado(jrxml2);
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
    public RequerimientoDocumentoDetalle patchRequerimientoDocumentoDetalle(RequerimientoDocumentoDetalle requerimientoDocumentoDetalle, Contexto contexto) {
        if (requerimientoDocumentoDetalle == null || requerimientoDocumentoDetalle.getIdRequerimientoDocumentoDetalle() == null) {
            throw new ValidacionException("ID de detalle de documento es obligatorio.");
        }
        RequerimientoDocumentoDetalle requerimientoDocumentoDetalleDB = requerimientoDocumentoDetalleDao.findById(requerimientoDocumentoDetalle.getIdRequerimientoDocumentoDetalle())
                .orElseThrow(() -> new ValidacionException("No se encontró el detalle con ID: " + requerimientoDocumentoDetalle.getIdRequerimientoDocumentoDetalle()));
        if (requerimientoDocumentoDetalle.getEvaluacion() == null || requerimientoDocumentoDetalle.getEvaluacion().getIdListadoDetalle() == null) {
            throw new ValidacionException("El campo 'estado' es obligatorio.");
        }
        ListadoDetalle estadoObservado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO_DETALLE.CODIGO,
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO_DETALLE.OBSERVADO
        );
        if (estadoObservado == null) {
            throw new ValidacionException("Estado OBSERVADO no configurado en ListadoDetalle");
        }
        if (!requerimientoDocumentoDetalle.getEvaluacion().equals(estadoObservado)) {
            if (requerimientoDocumentoDetalle.getObservacion() == null || requerimientoDocumentoDetalle.getObservacion().trim().isEmpty()) {
                throw new ValidacionException("Debe ingresar una observación cuando el estado es OBSERVADO.");
            }
        }
        AuditoriaUtil.setAuditoriaActualizacion(requerimientoDocumentoDetalleDB, contexto);
        requerimientoDocumentoDetalleDB.setEvaluacion(requerimientoDocumentoDetalle.getEvaluacion());
        requerimientoDocumentoDetalleDB.setObservacion(requerimientoDocumentoDetalle.getObservacion());
        return requerimientoDocumentoDetalleDao.save(requerimientoDocumentoDetalleDB);
    }

    @Override
    public RequerimientoDocumento evaluarRequerimientosDocumento(RequerimientoDocumento requerimientoDocumento, Contexto contexto) {
        return null;
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

    private RequerimientoDocumento actualizarEstadoReqDocumento(
            List<RequerimientoDocumentoDetalle> detalle, Contexto contexto) {

        String requerimientoDocumentoUuid = detalle.get(0).getRequerimientoDocumento().getRequerimientoDocumentoUuid();
        RequerimientoDocumento requerimientoDocumento = requerimientoDocumentoDao.obtenerPorUuid(requerimientoDocumentoUuid);

        ListadoDetalle estadoEnProceso = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.EN_PROCESO);

        requerimientoDocumento.setEstado(estadoEnProceso);
        requerimientoDocumento.setFechaIngreso(new Date());
        AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumento, contexto);

        return requerimientoDocumentoDao.save(requerimientoDocumento);
    }

    private RequerimientoDocumentoDetalle guardarFlagPresentado(
            RequerimientoDocumentoDetalle detalle, RequerimientoDocumento documento, Contexto contexto) {

        detalle.setRequerimientoDocumento(documento);
        if (detalle.getArchivo() != null) {
            detalle.setPresentado(Constantes.FLAG.PRESENTADO);
        } else {
            detalle.setPresentado(Constantes.FLAG.NO_PRESENTADO);
        }
        AuditoriaUtil.setAuditoriaRegistro(detalle, contexto);
        return requerimientoDocumentoDetalleDao.save(detalle);
    }
}
