package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import gob.osinergmin.siged.remote.rest.ro.in.*;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.out.ExpedienteOutRO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gob.osinergmin.siged.remote.rest.ro.out.DocumentoAnularOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.ArchivoRenovacionSigedRequest;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.ArchivoRenovacionSigedResponse;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.ArchivoRenovacionSigedService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;

/**
 * Implementación del servicio para la gestión de archivos SIGED en renovación de contrato.
 * 
 * Esta clase implementa la lógica de negocio para adjuntar y anular documentos
 * asociados a informes de renovación de contrato en el sistema SIGED.
 * 
 * @author Sistema SICOES
 * @version 1.0
 */
@Service
public class ArchivoRenovacionSigedServiceImpl implements ArchivoRenovacionSigedService {

    private static final Logger logger = LogManager.getLogger(ArchivoRenovacionSigedServiceImpl.class);


    
    @Autowired
    private InformeRenovacionContratoDao informeRenovacionContratoDao;
    
    @Autowired
    private ArchivoDao archivoDao;

    @Autowired
    private RequerimientoRenovacionDao requerimientoRenovacionDao;
    @Autowired
    private SicoesSolicitudService sicoesSolicitudService;
    @Autowired
    private SupervisoraRepresentanteService supervisoraRepresentanteService;
    @Autowired
    private SicoesSolicitudSeccionService sicoesSolicitudSeccionService;
    @Autowired
    private ListadoDetalleService listadoDetalleService;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private SicoesTdSolPerConSecService seccionesService;
    @Autowired
    private SigedApiConsumer sigedApiConsumer;
    @Autowired
    private SicoesSolicitudDao sicoesSolicitudDao;
    @Autowired
    private Environment env;

    @Value("${path.jasper}")
    private String pathJasper;
    @Value("${path.temporal}")
    private String pathTemporal;
    @Value("${siged.titulo.incripcion.registro}")
    private String TITULO_REGISTRO;
    @Value("${siged.old.proyecto}")
    private String SIGLA_PROYECTO;

    @Override
    public DocumentoOutRO agregarDocumento(ExpedienteInRO expediente, List<File> archivos) throws Exception {
        logger.info("Iniciando proceso de agregar documento al expediente");
        
        try {
            DocumentoOutRO resultado = sigedApiConsumer.agregarDocumento(expediente, archivos);
            
            if (resultado.getResultCode() == 0) {
                logger.info("Documento agregado exitosamente");
            } else {
                logger.warn("Error al agregar documento. Código: {}, Mensaje: {}", 
                           resultado.getErrorCode(), resultado.getMessage());
            }
            
            return resultado;
        } catch (Exception e) {
            logger.error("Error inesperado al agregar documento", e);
            throw e;
        }
    }

    @Override
    public DocumentoAnularOutRO anularDocumento(DocumentoAnularInRO documento) throws Exception {
        logger.info("Iniciando proceso de anular documento");
        
        try {
            DocumentoAnularOutRO resultado = sigedApiConsumer.anularDocumento(documento);
            
            if (resultado.getResultCode() == 0) {
                logger.info("Documento anulado exitosamente");
            } else {
                logger.warn("Error al anular documento. Código: {}, Mensaje: {}", 
                           resultado.getErrorCode(), resultado.getMessage());
            }
            
            return resultado;
        } catch (Exception e) {
            logger.error("Error inesperado al anular documento", e);
            throw e;
        }
    }

    @Override
    public ArchivoRenovacionSigedResponse adjuntarArchivo(ArchivoRenovacionSigedRequest request, MultipartFile file,Contexto contexto) throws Exception {
        logger.info("Iniciando proceso de adjuntar archivo para informe: {}", request.getIdInformeRenovacion());
        
        // Validaciones
        validarRequest(request);
        validarArchivo(file);
        try {
            // Verificar que el informe existe
            Optional<InformeRenovacionContrato> informeOpt = informeRenovacionContratoDao.findById(request.getIdInformeRenovacion());
            if (!informeOpt.isPresent()) {
                throw new DataNotFoundException("Informe de renovación no encontrado con ID: " + request.getIdInformeRenovacion());
            }
            InformeRenovacionContrato informe = informeOpt.get();
            // Crear archivo temporal
            File tempFile = convertirMultipartFileAFile(file);
            List<File> archivos = new ArrayList<>();
            archivos.add(tempFile);
            RequerimientoRenovacion requerimientoRenovacion = requerimientoRenovacionDao.obtenerPorId(
                    informe.getRequerimiento().getIdReqRenovacion());
            SicoesSolicitud solicitud = sicoesSolicitudService.obtener(requerimientoRenovacion.getIdSoliPerfCont(), contexto);
            if(solicitud==null ){
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.ID_SOLICITUD_NO_ENVIADO);
            }
            // Crear expediente para SIGED usando el número de expediente del requerimiento
            ExpedienteInRO expedienteInRO = new ExpedienteInRO();
            String codExpediente = null;
            if (solicitud.getIdSolicitudPadre() != null) {
                SicoesSolicitud solicitudPadre = sicoesSolicitudDao.findById(solicitud.getIdSolicitudPadre()).orElse(null);
                codExpediente = solicitudPadre.getNumeroExpediente();
            }
            if(codExpediente==null){
                codExpediente = requerimientoRenovacion.getNuExpediente();
            }
            expedienteInRO = crearExpedientePresentacion(solicitud, codExpediente, contexto);
            ExpedienteOutRO expedienteOutRO = null;
            DocumentoOutRO documentoSubsanacionOutRO = null;
            List<File> archivosAlfresco = new ArrayList<>();
            archivos.add(tempFile);
            archivosAlfresco.add(tempFile);
            DocumentoOutRO resultado = agregarDocumento(expedienteInRO, archivos);
            if (codExpediente != null) {
                documentoSubsanacionOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivosAlfresco);
                resultado=documentoSubsanacionOutRO;
            } else {
                expedienteOutRO = sigedApiConsumer.crearExpediente(expedienteInRO, archivosAlfresco);
                resultado.setCodigoDocumento(expedienteOutRO.getIdDocumento());
            }
            // Limpiar archivo temporal
            if (tempFile.exists()) {
                tempFile.delete();
            }
            // Crear respuesta
            ArchivoRenovacionSigedResponse response = new ArchivoRenovacionSigedResponse();
            response.setIdInformeRenovacion(request.getIdInformeRenovacion());
            
            if (resultado.getResultCode() == 0) {
                response.setCodigoDocumento(resultado.getCodigoDocumento());
                logger.info("Archivo adjuntado exitosamente para informe: {} con código de documento: {}", 
                           request.getIdInformeRenovacion(), resultado.getCodigoDocumento());
            } else {
                logger.warn("Error al adjuntar archivo para informe: {}. Código de error: {}, Mensaje: {}", 
                           request.getIdInformeRenovacion(), resultado.getErrorCode(), resultado.getMessage());
                throw new RuntimeException("Error al adjuntar archivo en SIGED: " + resultado.getMessage());
            }
            
            return response;
            
        } catch (Exception e) {
            logger.error("Error inesperado al adjuntar archivo para informe: {}", request.getIdInformeRenovacion(), e);
            throw e;
        }
    }

    @Override
    public ArchivoRenovacionSigedResponse anularArchivo(ArchivoRenovacionSigedRequest request) throws Exception {
        logger.info("Iniciando proceso de anular archivo para informe: {}", request.getIdInformeRenovacion());
        
        // Validaciones
        validarRequest(request);
        
        try {
            // Verificar que el informe existe
            Optional<InformeRenovacionContrato> informeOpt = informeRenovacionContratoDao.findById(request.getIdInformeRenovacion());
            if (!informeOpt.isPresent()) {
                throw new DataNotFoundException("Informe de renovación no encontrado con ID: " + request.getIdInformeRenovacion());
            }
            
            InformeRenovacionContrato informe = informeOpt.get();
            /*
            // Buscar archivos asociados al informe de renovación que tengan idDocumentoSiged
            List<Archivo> archivos = archivoDao.findByIdInformeRenovacionAndIdDocumentoSigedIsNotNull(request.getIdInformeRenovacion());
            
            if (archivos.isEmpty()) {
                throw new DataNotFoundException("No se encontraron archivos SIGED para anular en el informe: " + request.getIdInformeRenovacion());
            }
            
            Integer ultimoCodigoDocumento = null;
            
            // Anular cada documento en SIGED
            for (Archivo archivo : archivos) {
                DocumentoAnularInRO documentoAnular = new DocumentoAnularInRO();
                documentoAnular.setNroExpediente(informe.getRequerimiento().getNuExpediente());
                documentoAnular.setCodigoDocumento(archivo.getIdDocumentoSiged().intValue());
                documentoAnular.setMotivo("Anulación de documento del informe de renovación de contrato");
                
                DocumentoAnularOutRO resultado = anularDocumento(documentoAnular);
                
                if (resultado.getResultCode() == 0) {
                    // Actualizar el archivo para indicar que ha sido anulado en SIGED
                    archivo.setIdDocumentoSiged(null);
                    archivoDao.save(archivo);
                    ultimoCodigoDocumento = documentoAnular.getCodigoDocumento();
                    
                    logger.info("Documento SIGED anulado exitosamente. Código: {}", documentoAnular.getCodigoDocumento());
                } else {
                    logger.warn("Error al anular documento SIGED. Código: {}, Mensaje: {}", 
                               resultado.getErrorCode(), resultado.getMessage());
                    throw new RuntimeException("Error al anular documento en SIGED: " + resultado.getMessage());
                }
            }*/
            
            // Crear respuesta
            ArchivoRenovacionSigedResponse response = new ArchivoRenovacionSigedResponse();
            response.setIdInformeRenovacion(request.getIdInformeRenovacion());
           // response.setCodigoDocumento(ultimoCodigoDocumento);
            
           /* logger.info("Proceso de anulación completado exitosamente para informe: {}. Total documentos anulados: {}",
                       request.getIdInformeRenovacion(), archivos.size());
            */

            return response;
            
        } catch (Exception e) {
            logger.error("Error inesperado al anular archivo para informe: {}", request.getIdInformeRenovacion(), e);
            throw e;
        }
    }
    
    private void validarRequest(ArchivoRenovacionSigedRequest request) {
        if (request.getIdInformeRenovacion() == null || request.getIdInformeRenovacion() <= 0) {
            throw new DataNotFoundException("El campo idInformeRenovacion es obligatorio y debe ser mayor a 0");
        }
    }
    
    private void validarArchivo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new DataNotFoundException("El archivo es obligatorio y no debe estar vacío");
        }
        if (file.getSize() <= 0) {
            throw new DataNotFoundException("El archivo debe tener un tamaño mayor a 0");
        }
    }
    
    private File convertirMultipartFileAFile(MultipartFile multipartFile) throws IOException {
        Path tempPath = Files.createTempFile("upload_", "_" + multipartFile.getOriginalFilename());
        Files.copy(multipartFile.getInputStream(), tempPath, StandardCopyOption.REPLACE_EXISTING);
        return tempPath.toFile();
    }


    private ExpedienteInRO crearExpedientePresentacion(SicoesSolicitud solicitud, String codExpediente, Contexto contexto) {
        return crearExpediente(solicitud,
                Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.documento.crear")), codExpediente, contexto);
    }

    private ExpedienteInRO crearExpediente(SicoesSolicitud solicitud, Integer codigoTipoDocumento, String codExpediente, Contexto contexto) {
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
        if (solicitud.getNumeroExpediente() != null || codExpediente != null) {
            if (codExpediente != null) {
                expediente.setNroExpediente(codExpediente);
            } else {
                expediente.setNroExpediente(solicitud.getNumeroExpediente());
            }
        }
        documento.setAsunto(String.format("Solicitud de Renovación Requerimiento"));
        documento.setAppNameInvokes(SIGLA_PROYECTO);
        cs.setCodigoTipoIdentificacion(1);
        if (cs.getCodigoTipoIdentificacion() == 1) {
            cs.setNombre(solicitud.getSupervisora().getNombreRazonSocial());
            cs.setApellidoPaterno("-");
            cs.setApellidoMaterno("-");
        }
        cs.setRazonSocial(solicitud.getSupervisora().getNombreRazonSocial());
        cs.setNroIdentificacion(solicitud.getSupervisora().getNumeroDocumento());
        cs.setTipoCliente(Integer.parseInt(env.getProperty("crear.expediente.parametros.tipo.cliente")));
        cliente.add(cs);
        d.setDireccion(solicitud.getSupervisora().getDireccion());
        d.setDireccionPrincipal(true);
        d.setEstado(env.getProperty("crear.expediente.parametros.direccion.estado").charAt(0));
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
        documento.setEnumerado(env.getProperty("crear.expediente.parametros.enumerado").charAt(0));
        documento.setEstaEnFlujo(env.getProperty("crear.expediente.parametros.esta.en.flujo").charAt(0));
        documento.setFirmado(env.getProperty("crear.expediente.parametros.firmado").charAt(0));
        documento.setCreaExpediente(env.getProperty("crear.expediente.parametros.crea.expediente").charAt(0));
        documento.setPublico(env.getProperty("crear.expediente.parametros.crea.publico").charAt(0));
        documento.setNroFolios(Integer.parseInt(env.getProperty("crear.expediente.parametros.crea.folio")));
        documento.setUsuarioCreador(Integer.parseInt(env.getProperty("siged.bus.server.id.usuario")));
        logger.info("DOC_EXPEDIENTE--- :"+documento);
        logger.info("EXPEDIENTE_REGISTRO_PERF :"+expediente);
        return expediente;
    }


}
