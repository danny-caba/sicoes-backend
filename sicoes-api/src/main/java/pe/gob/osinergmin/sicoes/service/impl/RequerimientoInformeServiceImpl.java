package pe.gob.osinergmin.sicoes.service.impl;

import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.repository.PerfilAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoInformeDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.RequerimientoInformeService;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.ArchivoUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import pe.gob.osinergmin.sicoes.model.*;

import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RequerimientoInformeServiceImpl implements RequerimientoInformeService {

    private static final Logger logger = LogManager.getLogger(RequerimientoInformeServiceImpl.class);

    @Autowired
    private RequerimientoInformeDao requerimientoInformeDao;

    @Autowired
    private RequerimientoDao requerimientoDao;

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    private Environment env;

    @Value("${informe.requerimiento}")
    private String INFORME_REQUERIMIENTO;

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
    private RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Autowired
    private PerfilAprobadorDao perfilAprobadorDao;

    @Override
    public RequerimientoInforme guardar(RequerimientoInforme requerimientoInforme, Contexto contexto) {
        try {
            AuditoriaUtil.setAuditoriaRegistro(requerimientoInforme, contexto);
            ListadoDetalle estadoEnAprobacion = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO,
                    Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_APROBACION
                );
            Requerimiento requerimiento = requerimientoInforme.getRequerimiento();
            requerimiento.setEstado(estadoEnAprobacion);
            Requerimiento requerimientoDB = requerimientoDao.save(requerimiento);
            ExpedienteInRO expedienteInRO = crearExpediente(
                    requerimientoDB,
                    Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear"))
            );
            List<File> archivosAlfresco = new ArrayList<>();
            RequerimientoAprobacion aprobadorG1 = asignarAprobadorG1(requerimiento, contexto);
            Archivo archivo = archivoRequerimientoInforme(requerimiento, contexto);
            archivoService.guardarXRequerimientoInforme(archivo, contexto);
            File file = fileRequerimiento(archivo, requerimiento.getIdRequerimiento());
            archivosAlfresco.add(file);
            ExpedienteOutRO expedienteOutRO = sigedApiConsumer.crearExpediente(expedienteInRO, archivosAlfresco);
            logger.info("SIGED RESULT: {}", expedienteOutRO.getMessage());
            if (expedienteOutRO.getResultCode() != 1) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE, expedienteOutRO.getMessage());
            }
            if (expedienteOutRO.getResultCode() == 1) {
                requerimiento.setNuExpediente(expedienteOutRO.getCodigoExpediente());
            }
            requerimientoInforme = requerimientoInformeDao.save(requerimientoInforme);
            return requerimientoInforme;
        } catch (Exception ex) {
            logger.error("Error al guardar el informe. Contexto: {}, Entidad: {}", contexto, requerimientoInforme, ex);
            throw new RuntimeException("Error al guardar el informe", ex);
        }
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
        documento.setAsunto(INFORME_REQUERIMIENTO);
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

    private Archivo archivoRequerimientoInforme(Requerimiento requerimiento, Contexto contexto) {
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

    private RequerimientoAprobacion asignarAprobadorG1(Requerimiento requerimiento, Contexto contexto) {
        RequerimientoAprobacion requerimientoAprobacion = new RequerimientoAprobacion();
        PerfilAprobador perfilAprobador = perfilAprobadorDao
                .findFirstByPerfilIdListadoDetalle(requerimiento.getPerfil().getIdListadoDetalle())
                .orElseThrow(() -> new IllegalStateException("No se encontró perfil aprobador para el perfil del requerimiento"));
        Usuario aprobadorG1 = perfilAprobador.getAprobadorG1();
        if (aprobadorG1 == null) {
            throw new IllegalStateException("No se encontró aprobador G1 para el perfil del requerimiento");
        }
        requerimientoAprobacion.setUsuario(aprobadorG1);
        ListadoDetalle asignado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO
        );
        if (asignado == null) {
            throw new IllegalStateException("Estado ASIGNADO no configurado en ListadoDetalle");
        }
        ListadoDetalle tipo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_TIPO_APROBACION.CODIGO,
                Constantes.LISTADO.ESTADO_TIPO_APROBACION.FIRMAR);
        requerimientoAprobacion.setEstado(asignado);
        requerimientoAprobacion.setRequerimiento(requerimiento);
        requerimientoAprobacion.setTipo(tipo);
        AuditoriaUtil.setAuditoriaRegistro(requerimientoAprobacion, contexto);
        return requerimientoAprobacionDao.save(requerimientoAprobacion);
    }
}
