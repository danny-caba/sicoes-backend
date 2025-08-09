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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.PerfilAprobador;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.RequerimientoInformeDetalle;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.repository.PerfilAprobadorDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoInformeDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoInformeDetalleDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.RequerimientoInformeService;
import pe.gob.osinergmin.sicoes.service.RequerimientoService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
public class RequerimientoInformeServiceImpl implements RequerimientoInformeService {

    private static final Logger logger = LogManager.getLogger(RequerimientoInformeServiceImpl.class);

    @Autowired
    private RequerimientoInformeDao requerimientoInformeDao;

    @Autowired
    private RequerimientoInformeDetalleDao requerimientoInformeDetalleDao;

    @Autowired
    private RequerimientoDao requerimientoDao;

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    private Environment env;

    @Autowired
    private ArchivoService archivoService;

    @Autowired
    private SigedApiConsumer sigedApiConsumer;

    @Autowired
    private ArchivoUtil archivoUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RequerimientoService requerimientoService;

    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Autowired
    private PerfilAprobadorDao perfilAprobadorDao;

    @Value("${path.temporal}")
    private String pathTemporal;

    @Value("${path.jasper}")
    private String pathJasper;

    @Value("${informe.requerimiento}")
    private String INFORME_REQUERIMIENTO;

    @Value("${siged.old.proyecto}")
    private String SIGLA_PROYECTO;

    @Value("${siged.ws.cliente.osinergmin.numero.documento}")
    private String OSI_DOCUMENTO;

    @Override
    @Transactional
    public RequerimientoInforme guardar(RequerimientoInformeDetalle requerimientoInformeDetalle, Contexto contexto) {
        validarInformeDetalle(requerimientoInformeDetalle);
        try {
            Requerimiento requerimiento = obtenerRequerimiento(requerimientoInformeDetalle)
                    .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_NO_ENCONTRADO));
            RequerimientoInforme requerimientoInformeDB = guardarRequerimientoInforme(requerimiento, contexto);
            guardarDetalle(requerimientoInformeDetalle, requerimientoInformeDB, contexto);
            cambiarEstadoRequerimiento(requerimiento, contexto);
            ExpedienteInRO expedienteInRO = crearExpediente(
                    requerimiento,
                    Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear"))
            );
            List<File> archivosAlfresco = new ArrayList<>();
            asignarAprobadorG1(requerimiento, requerimientoInformeDB, contexto);
            Archivo archivo = archivoRequerimientoInforme(requerimientoInformeDetalle, contexto);
            archivoService.guardarXRequerimientoInforme(archivo, contexto);
            File file = fileRequerimiento(archivo, requerimiento.getIdRequerimiento());
            archivosAlfresco.add(file);
            DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
            logger.info("SIGED RESULT: {}", documentoOutRO.getMessage());
            if (documentoOutRO.getResultCode() != 1) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_CREAR_EXPEDIENTE, documentoOutRO.getMessage());
            }
//            aprobarG1(requerimientoInformeDB, contexto);
//            aprobarG3(requerimientoInformeDB, contexto);
            return requerimientoInformeDB;
        } catch (Exception ex) {
            logger.error("Error al guardar el informe. Contexto: {}, Entidad: {}", contexto, requerimientoInformeDetalle, ex);
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

    private Archivo archivoRequerimientoInforme(RequerimientoInformeDetalle requerimientoInformeDetalle, Contexto contexto) {
        Archivo archivo = new Archivo();
        Long idRequerimiento = requerimientoInformeDetalle.getRequerimientoInforme().getRequerimiento().getIdRequerimiento();
        archivo.setIdRequerimiento(idRequerimiento);
        archivo.setNombre("Requerimiento_Informe_" + contexto.getUsuario().getUsuario() + ".pdf");
        archivo.setNombreReal("Requerimiento_Informe_" + contexto.getUsuario().getUsuario() + ".pdf");
        archivo.setTipo("application/pdf");
        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                Constantes.LISTADO.TIPO_ARCHIVO.INFORME_REQUERIMIENTO);
        archivo.setTipoArchivo(tipoArchivo);
        ByteArrayOutputStream output;
        JasperPrint print;
        InputStream appLogo = null;
        InputStream osinermingLogo = null;
        try {
            File jrxml = new File(pathJasper + "Formato_04_Requerimiento_Informe.jrxml");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SUBREPORT_DIR", pathJasper);
            appLogo = Files.newInputStream(Paths.get(pathJasper + "logo-sicoes.png"));
            osinermingLogo = Files.newInputStream(Paths.get(pathJasper + "logo-osinerming.png"));
            parameters.put("P_LOGO_APP", appLogo);
            parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);
            List<RequerimientoInformeDetalle> requerimientosInformeDetalle = new ArrayList<>();
            requerimientosInformeDetalle.add(requerimientoInformeDetalle);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(requerimientosInformeDetalle);
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

    private void asignarAprobadorG1(Requerimiento requerimiento, RequerimientoInforme informe, Contexto contexto) {
        RequerimientoAprobacion requerimientoAprobacion = new RequerimientoAprobacion();
        PerfilAprobador perfilAprobador = perfilAprobadorDao
                .findFirstByPerfilIdListadoDetalle(requerimiento.getPerfil().getIdListadoDetalle())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.PERFIL_APROBADOR_NO_ENCONTRADO));
        Usuario aprobadorG1 = perfilAprobador.getAprobadorG1();
        if (aprobadorG1 == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.PERFIL_APROBADOR_G1_NO_ENCONTRADO);
        }
        requerimientoAprobacion.setUsuario(aprobadorG1);
        ListadoDetalle asignado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO
        );
        if (asignado == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_ASIGNADO_NO_CONFIGURADO_EN_LISTADODETALLE);
        }
        ListadoDetalle tipo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_APROBACION.CODIGO,
                Constantes.LISTADO.TIPO_APROBACION.FIRMAR);
        requerimientoAprobacion.setEstado(asignado);
        requerimientoAprobacion.setRequerimiento(requerimiento);
        requerimientoAprobacion.setTipo(tipo);
        ListadoDetalle grupo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.GRUPOS.CODIGO,
                Constantes.LISTADO.GRUPOS.G1);
        requerimientoAprobacion.setGrupo(grupo);
        ListadoDetalle grupoAprobador = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.GRUPO_APROBACION.CODIGO,
                Constantes.LISTADO.GRUPO_APROBACION.JEFE_UNIDAD);
        requerimientoAprobacion.setGrupoAprobador(grupoAprobador);
        ListadoDetalle tipoAprobador = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_EVALUADOR.CODIGO,
                Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO);
        requerimientoAprobacion.setTipoAprobador(tipoAprobador);
        requerimientoAprobacion.setRequerimientoInforme(informe);
        requerimientoAprobacion.setFechaAsignacion(new Date());
        AuditoriaUtil.setAuditoriaRegistro(requerimientoAprobacion, contexto);
        requerimientoAprobacionDao.save(requerimientoAprobacion);
    }

    private void aprobarG1(RequerimientoInforme requerimientoInforme, Contexto contexto) {
        RequerimientoAprobacion requerimientoAprobacion = new RequerimientoAprobacion();
        PerfilAprobador perfilAprobador = perfilAprobadorDao
                .findFirstByPerfilIdListadoDetalle(requerimientoInforme.getRequerimiento().getPerfil().getIdListadoDetalle())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.PERFIL_APROBADOR_NO_ENCONTRADO));
        Usuario aprobadorG1 = perfilAprobador.getAprobadorG1();
        if (aprobadorG1 == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.PERFIL_APROBADOR_G1_NO_ENCONTRADO);
        }
        requerimientoAprobacion.setUsuario(aprobadorG1);
        ListadoDetalle aprobado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                Constantes.LISTADO.ESTADO_APROBACION.APROBADO
        );
        if (aprobado == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_ASIGNADO_NO_CONFIGURADO_EN_LISTADODETALLE);
        }
        ListadoDetalle tipo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_APROBACION.CODIGO,
                Constantes.LISTADO.TIPO_APROBACION.APROBAR);
        requerimientoAprobacion.setEstado(aprobado);
        requerimientoAprobacion.setRequerimiento(requerimientoInforme.getRequerimiento());
        requerimientoAprobacion.setRequerimientoInforme(requerimientoInforme);
        requerimientoAprobacion.setTipo(tipo);
        requerimientoAprobacion.setFechaAprobacion(new Date());
        ListadoDetalle grupo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.GRUPOS.CODIGO,
                Constantes.LISTADO.GRUPOS.G1);
        requerimientoAprobacion.setGrupo(grupo);
        ListadoDetalle grupoAprobador = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.GRUPO_APROBACION.CODIGO,
                Constantes.LISTADO.GRUPO_APROBACION.JEFE_UNIDAD);
        requerimientoAprobacion.setGrupoAprobador(grupoAprobador);
        ListadoDetalle tipoAprobador = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_EVALUADOR.CODIGO,
                Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO);
        requerimientoAprobacion.setTipoAprobador(tipoAprobador);
        AuditoriaUtil.setAuditoriaRegistro(requerimientoAprobacion, contexto);
        requerimientoAprobacionDao.save(requerimientoAprobacion);
    }

    private void aprobarG3(RequerimientoInforme requerimientoInforme, Contexto contexto) {
        RequerimientoAprobacion requerimientoAprobacion = new RequerimientoAprobacion();
        PerfilAprobador perfilAprobador = perfilAprobadorDao
                .findFirstByPerfilIdListadoDetalle(requerimientoInforme.getRequerimiento().getPerfil().getIdListadoDetalle())
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.PERFIL_APROBADOR_NO_ENCONTRADO));
        Usuario aprobadorG3 = perfilAprobador.getAprobadorG3();
        if (aprobadorG3 == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.PERFIL_APROBADOR_G3_NO_ENCONTRADO);
        }
        requerimientoAprobacion.setUsuario(aprobadorG3);
        ListadoDetalle aprobado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_APROBACION.CODIGO,
                Constantes.LISTADO.ESTADO_APROBACION.APROBADO
        );
        if (aprobado == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_ASIGNADO_NO_CONFIGURADO_EN_LISTADODETALLE);
        }
        ListadoDetalle tipo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_APROBACION.CODIGO,
                Constantes.LISTADO.TIPO_APROBACION.APROBAR);
        requerimientoAprobacion.setEstado(aprobado);
        requerimientoAprobacion.setRequerimiento(requerimientoInforme.getRequerimiento());
        requerimientoAprobacion.setRequerimientoInforme(requerimientoInforme);
        requerimientoAprobacion.setTipo(tipo);
        requerimientoAprobacion.setFechaAprobacion(new Date());
        ListadoDetalle grupo = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.GRUPOS.CODIGO,
                Constantes.LISTADO.GRUPOS.G1);
        requerimientoAprobacion.setGrupo(grupo);
        ListadoDetalle grupoAprobador = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.GRUPO_APROBACION.CODIGO,
                Constantes.LISTADO.GRUPO_APROBACION.GERENTE);
        requerimientoAprobacion.setGrupoAprobador(grupoAprobador);
        ListadoDetalle tipoAprobador = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_EVALUADOR.CODIGO,
                Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_TECNICO);
        requerimientoAprobacion.setTipoAprobador(tipoAprobador);
        AuditoriaUtil.setAuditoriaRegistro(requerimientoAprobacion, contexto);
        requerimientoAprobacionDao.save(requerimientoAprobacion);
    }

    private void validarInformeDetalle(RequerimientoInformeDetalle requerimientoInformeDetalle) {
        if (requerimientoInformeDetalle == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_INFORME_DETALLE_NULO);
        }
        if (requerimientoInformeDetalle.getRequerimientoInforme() == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_INFORME_NULO);
        }
    }

    private Optional<Requerimiento> obtenerRequerimiento(RequerimientoInformeDetalle requerimientoInformeDetalle) {
        String requerimientoUuid = requerimientoInformeDetalle.getRequerimientoInforme()
                .getRequerimiento().getRequerimientoUuid();
        return requerimientoService.obtenerPorUuid(requerimientoUuid);
    }

    private RequerimientoInforme guardarRequerimientoInforme(Requerimiento requerimiento, Contexto contexto) {
        RequerimientoInforme requerimientoInforme = new RequerimientoInforme();
        requerimientoInforme.setRequerimiento(requerimiento);
        requerimientoInforme.setRequerimientoInformeUuid(UUID.randomUUID().toString());
        AuditoriaUtil.setAuditoriaRegistro(requerimientoInforme, contexto);
        return requerimientoInformeDao.save(requerimientoInforme);
    }

    private void guardarDetalle(RequerimientoInformeDetalle requerimientoInformeDetalle, RequerimientoInforme requerimientoInforme, Contexto contexto){
        requerimientoInformeDetalle.setRequerimientoInforme(requerimientoInforme);
        AuditoriaUtil.setAuditoriaRegistro(requerimientoInformeDetalle, contexto);
        requerimientoInformeDetalleDao.save(requerimientoInformeDetalle);
    }

    private void cambiarEstadoRequerimiento(Requerimiento requerimiento, Contexto contexto) {
        ListadoDetalle estadoEnAprobacion = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_APROBACION
        );
        if (estadoEnAprobacion == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_EN_APROBACION_NO_CONFIGURADO_EN_LISTADODETALLE);
        }
        requerimiento.setEstado(estadoEnAprobacion);
        requerimientoService.actualizar(requerimiento, contexto);
    }

}
