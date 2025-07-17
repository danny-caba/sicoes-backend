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
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumento;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumentoDetalle;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoCoordinadorDTO;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoDTO;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDocumentoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDocumentoDetalleDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.RequerimientoDocumentoService;
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
        return requerimientoDocumentoDao.listarRequerimientosDocumentos(contexto.getUsuario().getIdUsuario(), filtro.getEstado(), fechaInicio, fechaFin, pageable);
    }

    @Override
    public List<RequerimientoDocumentoDetalle> listarRequerimientosDocumentosDetalle(String requerimientoUuid) {
        return requerimientoDocumentoDetalleDao.listarPorUuid(requerimientoUuid);
    }

    @Override
    @Transactional
    public List<RequerimientoDocumentoDetalle> actualizarRequerimientosDocumentosDetalle(List<RequerimientoDocumentoDetalle> listRequerimientoDocumentoDetalle, Contexto contexto){
        RequerimientoDocumentoDetalle requerimientoDocumentoDetalleDB = requerimientoDocumentoDetalleDao.findById(
                listRequerimientoDocumentoDetalle.get(0).getIdRequerimientoDocumentoDetalle()
        ).orElseThrow(() -> new ValidacionException("Detalle no encontrado"));
        RequerimientoDocumento requerimientoDocumento = requerimientoDocumentoDetalleDB.getRequerimientoDocumento();
        ListadoDetalle estadoEnProceso = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.EN_PROCESO
        );
        if (estadoEnProceso == null) {
            throw new ValidacionException("Estado EN PROCESO no configurado en ListadoDetalle");
        }
        AuditoriaUtil.setAuditoriaActualizacion(requerimientoDocumento, contexto);
        requerimientoDocumento.setEstado(estadoEnProceso);
        requerimientoDocumentoDao.save(requerimientoDocumento);
        for (RequerimientoDocumentoDetalle requerimientoDocumentoDetalle : listRequerimientoDocumentoDetalle) {
            AuditoriaUtil.setAuditoriaActualizacion(requerimientoDocumentoDetalle, contexto);
            requerimientoDocumentoDetalle.setPresentado("1");
            requerimientoDocumentoDetalleDao.save(requerimientoDocumentoDetalle);
        }
        ExpedienteInRO expedienteInRO = crearExpediente(
                requerimientoDocumento,
                Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear"))
        );
        List<File> archivosAlfresco = new ArrayList<>();
        Archivo archivo = archivoRequerimiento(requerimientoDocumento, listRequerimientoDocumentoDetalle, contexto);
        archivoService.guardarXRequerimientoDocumento(archivo, contexto);
        List<Archivo> archivos = archivoService.obtenerArchivosPorRequerimientoDocumento(requerimientoDocumento.getIdRequerimientoDocumento(), contexto);
        listRequerimientoDocumentoDetalle.forEach(requerimientoDocumentoDetalle -> {
            archivos.forEach(archivoRequisito -> {
                if (requerimientoDocumentoDetalle..getIdSolicitudSeccion().equals(archivoRequisito.getIdSeccionRequisito())) {
                    requerimientoDocumentoDetalle.setNombreArchivo(archivoRequisito.getNombreReal());
                    requerimientoDocumentoDetalle.setPeso((long) (archivoRequisito.getPeso() / 8.0 / 1024.0));
                    requerimientoDocumentoDetalle.setNroFolio(archivoRequisito.getNroFolio());
                }
            });
        });


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
        return listRequerimientoDocumentoDetalle;
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

    private Archivo archivoRequerimiento(RequerimientoDocumento requerimientoDocumento, List<RequerimientoDocumentoDetalle> requerimientosDocumentosDetalles, Contexto contexto) {
        ListadoDetalle perfil = listadoDetalleService.obtener(requerimientoDocumento.getRequerimiento().getPerfil().getIdListadoDetalle(), contexto);
        requerimientoDocumento.getRequerimiento().setPerfil(perfil);
        requerimientoDocumento.getRequerimiento().setUsuarioCreador(usuarioService.obtener(Long.valueOf(requerimientoDocumento.getRequerimiento().getUsuCreacion())));
        Archivo archivo = new Archivo();
        Long idRequerimiento = requerimientoDocumento.getRequerimiento().getIdRequerimiento();
        archivo.setIdRequerimiento(idRequerimiento);
        archivo.setIdRequimientoDocumento(requerimientoDocumento.getIdRequerimientoDocumento());
        archivo.setNombre("Requerimie_Documento_" + idRequerimiento + ".pdf");
        archivo.setNombreReal("Requerimie_Documento_" + idRequerimiento + ".pdf");
        archivo.setTipo("application/pdf");
        ByteArrayOutputStream output;
        JasperPrint print;
        InputStream appLogo = null;
        InputStream osinermingLogo = null;
        RequerimientoDocumento requerimientoDocumentoJasper = new RequerimientoDocumento();
        requerimientoDocumentoJasper.setRequerimiento(requerimientoDocumento.getRequerimiento());
        requerimientoDocumentoJasper.setRequerimientosDocumentosDetalles(requerimientosDocumentosDetalles);
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

    @Override
    public Long obtenerId(String requerimientoDocumentoUuid) {
        if (requerimientoDocumentoUuid == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_REQUERIMIENTO_NO_ENVIADO);
        }
        return requerimientoDocumentoDao.obtenerId(requerimientoDocumentoUuid);
    }
}
