package pe.gob.osinergmin.sicoes.service.impl;

import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_FIN_ANTES_INICIO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_INICIO_ANTES_HOY;

import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
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
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.service.ArchivoService;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.RequerimientoService;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.*;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class RequerimientoServiceImpl implements RequerimientoService {

    private static final Logger logger = LogManager.getLogger(RequerimientoServiceImpl.class);

    @Autowired
    @SuppressWarnings("unused")
    private RequerimientoDao requerimientoDao;

    @Autowired
    @SuppressWarnings("unused")
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    @SuppressWarnings("unused")
    private Environment env;

    @Value("${solicitud.requerimiento.contrato.pn}")
    @SuppressWarnings("unused")
    private String SOLICITUD_REQUERIMIENTO_CONTRATO_PN;

    @Value("${siged.old.proyecto}")
    @SuppressWarnings("unused")
    private String SIGLA_PROYECTO;

    @Autowired
    @SuppressWarnings("unused")
    private ArchivoService archivoService;

    @Autowired
    @SuppressWarnings("unused")
    private SigedApiConsumer sigedApiConsumer;

    @Value("${path.temporal}")
    @SuppressWarnings("unused")
    private String pathTemporal;

    @Value("${path.jasper}")
    @SuppressWarnings("unused")
    private String pathJasper;

    @Autowired
    @SuppressWarnings("unused")
    private ArchivoUtil archivoUtil;

    @Autowired
    @SuppressWarnings("unused")
    private UsuarioService usuarioService;

    @Override
    public Requerimiento guardar(Requerimiento requerimiento, Contexto contexto) {
        AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
        if (requerimiento.getEstado() == null || requerimiento.getEstado().getIdListadoDetalle() == null) {
            ListadoDetalle listadoDetalle = new ListadoDetalle();
            ListadoDetalle estadoPreliminar = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO,
                    Constantes.LISTADO.ESTADO_REQUERIMIENTO.PRELIMINAR
            );
            listadoDetalle.setIdListadoDetalle(estadoPreliminar.getIdListadoDetalle());
            requerimiento.setEstado(listadoDetalle);
        }
        requerimiento.setFeRegistro(new Date());
        requerimiento.setRequerimientoUuid(UUID.randomUUID().toString());
        if (requerimiento.getReqInvitaciones() != null) {
            for (RequerimientoInvitacion invitacion : requerimiento.getReqInvitaciones()) {
                invitacion.setRequerimiento(requerimiento);
            }
        }
        requerimiento = requerimientoDao.save(requerimiento);
        requerimiento.setUsuarioCreador(usuarioService.obtener(Long.valueOf(requerimiento.getUsuCreacion())));
        ExpedienteInRO expedienteInRO = crearExpediente(
                requerimiento,
                Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear"))
        );
        List<File> archivosAlfresco = new ArrayList<>();
        Archivo archivo = archivoRequerimiento(requerimiento, contexto);
        archivoService.guardarXRequerimiento(archivo, contexto);
        File file = fileRequerimiento(archivo, requerimiento.getIdRequerimiento());
        archivosAlfresco.add(file);
        ExpedienteOutRO expedienteOutRO = sigedApiConsumer.crearExpediente(expedienteInRO, archivosAlfresco);
        requerimiento.setNuExpediente(String.valueOf(expedienteOutRO));
        requerimiento = requerimientoDao.save(requerimiento);
        return requerimiento;
    }

    private ExpedienteInRO crearExpediente(Requerimiento requerimiento, Integer codigoTipoDocumento) {
        ExpedienteInRO expediente = new ExpedienteInRO();
        DocumentoInRO documento = new DocumentoInRO();
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
        return expediente;
    }

    private Archivo archivoRequerimiento(Requerimiento requerimiento, Contexto contexto) {
        // Se crea una nueva instancia de Archivo (entidad usada para persistir el PDF generado)
        Archivo archivo = new Archivo();

        // Se obtiene el ID del requerimiento
        Long idRequerimiento = requerimiento.getIdRequerimiento();

        // Se asocia el archivo al requerimiento actual
        archivo.setIdRequerimiento(idRequerimiento);

        // Se establece el nombre visible y real del archivo PDF
        archivo.setNombre("Solicitud_Requerimiento_Contrato_PN_" + idRequerimiento + ".pdf");
        archivo.setNombreReal("Solicitud_Requerimiento_Contrato_PN_" + idRequerimiento + ".pdf");

        // Se indica el tipo MIME del archivo generado (PDF)
        archivo.setTipo("application/pdf");

        // Se consulta y asigna el tipo de archivo desde ListadoDetalle (por ejemplo: FORMATO)
        archivo.setTipoArchivo(listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                Constantes.LISTADO.TIPO_ARCHIVO.FORMATO
        ));

        // Busca los archivos ya asociados al requerimiento y agrega este nuevo a la lista (aunque aquí no se guarda)
        List<Archivo> archivos = archivoService.buscarXRequerimiento(idRequerimiento, contexto);
        archivos.add(archivo);

        // Declaración de variables para el JasperReport
        ByteArrayOutputStream output;
        JasperPrint print;
        InputStream appLogo = null;
        InputStream osinermingLogo = null;

        try {
            // Carga el archivo .jrxml (plantilla JasperReport) desde el path configurado
            File jrxml = new File(pathJasper + "Formato_04_Requerimiento.jrxml");

            // Mapa de parámetros para el reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("SUBREPORT_DIR", pathJasper); // ruta para subreportes

            // Carga de logos institucionales como recursos InputStream
            appLogo = Files.newInputStream(Paths.get(pathJasper + "logo-sicoes.png"));
            osinermingLogo = Files.newInputStream(Paths.get(pathJasper + "logo-osinerming.png"));
            parameters.put("P_LOGO_APP", appLogo);
            parameters.put("P_LOGO_OSINERGMIN", osinermingLogo);

            // Fuente de datos: una lista con un solo requerimiento
            List<Requerimiento> requerimientos = new ArrayList<>();
            requerimientos.add(requerimiento);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(requerimientos);

            // Compila la plantilla JRXML a un objeto JasperReport
            JasperReport jasperReport = archivoUtil.getJasperCompilado(jrxml);

            // Rellena el reporte con datos y parámetros
            print = JasperFillManager.fillReport(jasperReport, parameters, ds);

            // Exporta el reporte a PDF y lo guarda en un array de bytes
            output = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, output);

        } catch (Exception e) {
            // En caso de error, se registra en log y lanza excepción de validación
            logger.error(e.getMessage(), e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_GUARDAR_FORMATO_04, e);
        } finally {
            // Cierre seguro de los InputStreams
            archivoUtil.close(appLogo);
            archivoUtil.close(osinermingLogo);
        }

        // Se asigna el contenido binario del archivo generado
        byte[] bytesSalida = output.toByteArray();
        archivo.setPeso((long) bytesSalida.length); // tamaño del archivo en bytes
        archivo.setNroFolio(1L);                     // se asume 1 folio (página)
        archivo.setContenido(bytesSalida);           // se guarda el contenido

        return archivo; // se retorna el objeto Archivo para su persistencia y envío a SIGED
    }

    private File fileRequerimiento(Archivo archivo, Long idRequerimiento) {
        try {
            // Construye la ruta del directorio temporal usando el id del requerimiento
            String dirPath = pathTemporal + File.separator + "temporales" + File.separator + idRequerimiento;

            // Crea un objeto File que representa ese directorio
            File dir = new File(dirPath);

            // Si el directorio no existe aún, intenta crearlo
            if (!dir.exists()) {
                boolean creado = dir.mkdirs(); // mkdirs() puede fallar (retorna false)
                if (!creado) {
                    // Si no se pudo crear el directorio, se emite una advertencia al log
                    logger.warn("No se pudo crear el directorio temporal: {}", dirPath);
                }
            }

            // Se construye la ruta completa del archivo con su nombre dentro del directorio temporal
            File file = new File(dirPath + File.separator + archivo.getNombre());

            // Escribe el contenido binario del archivo (PDF generado) al archivo físico en disco
            FileUtils.writeByteArrayToFile(file, archivo.getContenido());

            // Luego de escribirlo, se vuelve a leer el archivo como bytes y se actualiza en el objeto `archivo`
            // Esto asegura que el contenido corresponde a lo realmente escrito
            archivo.setContenido(Files.readAllBytes(file.toPath()));

            // Retorna el archivo físico, que se usará para enviar a Alfresco (SIGED)
            return file;
        } catch (Exception e) {
            // Si ocurre cualquier error (escritura, lectura, permisos), se registra y lanza excepción de validación
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
        Division division = filtro.getDivision() != null ? crearDivision(filtro.getDivision()) : null;
        ListadoDetalle perfil = filtro.getPerfil() != null ? crearListadoDetalle(filtro.getPerfil()) : null;
        Supervisora supervisora = filtro.getSupervisora() != null ? crearSupervisora(filtro.getSupervisora()) : null;
        ListadoDetalle estadoAprobacion = filtro.getEstadoAprobacion() != null ? crearListadoDetalle(filtro.getEstadoAprobacion()) : null;
        return requerimientoDao.listarRequerimientos(division, perfil, fechaInicio, fechaFin, supervisora, estadoAprobacion, pageable);
    }

    private Division crearDivision(Long id) {
        Division division = new Division();
        division.setIdDivision(id);
        return division;
    }

    private ListadoDetalle crearListadoDetalle(Long id) {
        ListadoDetalle detalle = new ListadoDetalle();
        detalle.setIdListadoDetalle(id);
        return detalle;
    }

    private Supervisora crearSupervisora(Long id) {
        Supervisora s = new Supervisora();
        s.setIdSupervisora(id);
        return s;
    }

    @Override
    @Transactional
    public Requerimiento archivar(Long id, String observacion, Contexto contexto) {
        Requerimiento requerimiento = requerimientoDao.findById(id).orElseThrow(() -> new RuntimeException("Requerimiento no encontrado"));
        ListadoDetalle estadoArchivado = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO, Constantes.LISTADO.ESTADO_REQUERIMIENTO.ARCHIVADO);
        if (estadoArchivado == null) {
            throw new IllegalStateException("Estado ARCHIVADO no configurado en ListadoDetalle");
        }
        requerimiento.setEstado(estadoArchivado);
        requerimiento.setDeObservacion(observacion);
        AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
        return requerimientoDao.save(requerimiento);
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

}
