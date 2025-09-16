package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del servicio para la gestión de informes presupuestales.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ClienteSigedServiceImpl   {

    private final Logger log = LogManager.getLogger(ClienteSigedServiceImpl.class);
    

    private final ListadoDetalleService listadoDetalleService;

    private final SigedApiConsumer sigedApiConsumer;

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

    private static final String EXTENSION_PDF = ".pdf";

    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;


    public Archivo buildArchivo(MultipartFile file, Long idInformeRenovacion) {
        try {
            // Crear archivo base
            Archivo archivo = new Archivo();
            // Generar nombre con timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
            String nombreBase = String.format("INFORME_RENOVACION_PRESUPUESTO_%d_%s%s",
                idInformeRenovacion, timestamp, EXTENSION_PDF);
            // Configurar propiedades básicas
            archivo.setNombre(nombreBase);
            archivo.setNombreReal(nombreBase);
            archivo.setTipo("application/pdf");
            archivo.setPeso(file.getSize());
            archivo.setNroFolio(1L);
            archivo.setContenido(file.getBytes());
            archivo.setTipo(crearExpedienteParametrosTipoDocumentoAdjuntar);
            archivo.setIdInformeRenovacion(idInformeRenovacion);
            // Obtener y validar tipo de archivo
            ListadoDetalle archivoRenovacion = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.TIPO_ARCHIVO.CODIGO,
                    Constantes.LISTADO.TIPO_ARCHIVO.INFORME_RENOVACION_CONTRATO);
            archivo.setTipoArchivo(archivoRenovacion);
            return archivo;
            
        } catch (IOException e) {
            log.error("Error al procesar el archivo para el informe ID: {}", idInformeRenovacion, e);
            throw new DataNotFoundException(
                String.format("Error al procesar el archivo: %s", e.getMessage()));
        }
    }

    public void adjuntarDocumentoSiged(InformeRenovacionContrato informe,
            String nombre, MultipartFile archivo, SicoesSolicitud solicitud) {
        List<File> archivosAlfresco = new ArrayList<>();
        ExpedienteInRO expedienteInRO = crearExpediente(
                informe,
                Integer.parseInt(crearExpedienteParametrosTipoDocumentoAdjuntar),
                solicitud
        );
        File file = null;
        try {
            String nombreSinExtension = removerSufijoPdf(nombre);
            file = crearFileDesdeBytes(archivo, nombreSinExtension,".pdf");
            archivosAlfresco.add(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);

            log.info("SIGED RESULT: {}", documentoOutRO.getMessage());
            if (documentoOutRO.getResultCode() != 1) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.SOLICITUD_GUARDAR_FORMATO_RESULTADO, documentoOutRO.getMessage());
            }
        } catch (Exception e) {
            log.error("Error al agregar documento informe renovacion contrato en SIGED", e);
        }

    }

    private static File crearFileDesdeBytes(MultipartFile multipartFile, String prefix, String suffix) throws IOException {
        // Crear archivo temporal en el directorio temporal del sistema
        File tempFile = File.createTempFile(prefix, suffix);
        // Escribir los bytes del MultipartFile al archivo
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.flush();
        }
        return tempFile;
    }

    private static String removerSufijoPdf(String nombreArchivo) {
        if (nombreArchivo == null) {
            return null;
        }
        // Remover .pdf al final, case insensitive
        return nombreArchivo.replaceAll("(?i)\\.pdf$", "");
    }


    private ExpedienteInRO crearExpediente(InformeRenovacionContrato nuevoInformeRenovacionContrato, Integer codigoTipoDocumento,SicoesSolicitud solicitud) {
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

        log.info("DOC_EXPEDIENTE {}",documento);
        log.info("EXPEDIENTE_INFORME_RENOVACION {}",expediente);
        return expediente;
    }

}