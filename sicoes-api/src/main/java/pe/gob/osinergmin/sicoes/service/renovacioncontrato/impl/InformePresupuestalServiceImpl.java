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
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformePresupuestalCreateRequest;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformePresupuestalCreateResponse;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformePresupuestalService;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;

/**
 * Implementación del servicio para la gestión de informes presupuestales.
 */
@Service
public class InformePresupuestalServiceImpl implements InformePresupuestalService {

    private static final Logger logger = LogManager.getLogger(InformePresupuestalServiceImpl.class);

    @Autowired
    private InformeRenovacionContratoDao informeRenovacionContratoDao;

    @Autowired
    private RequerimientoRenovacionDao requerimientoRenovacionDao;

    @Autowired
    private ArchivoDao archivoDao;

    @Autowired
    private SicoesSolicitudDao sicoesSolicitudDao;

    @Value("${SICOES.URIS.SIGED.FILE.PATH}")
    private String sigedFilePath;

    @Autowired
    private Environment env;

    @Autowired
    private SigedApiConsumer sigedApiConsumer;

    @Override
    public InformePresupuestalCreateResponse uploadDocument(
            Long idRequerimientoAprobacion,
            MultipartFile file,
            Contexto contexto) throws Exception {
        logger.info("uploadDocument - idRequerimientoAprobacion: {}", idRequerimientoAprobacion);

        /*
        Optional<RequerimientoRenovacion> requerimientoOpt = requerimientoRenovacionDao.findById(requestDTO.getIdRequerimientoRenovacion());
        if (!requerimientoOpt.isPresent()) {
            throw new DataNotFoundException("No se encontró el requerimiento de renovación con ID: " + requestDTO.getIdRequerimientoRenovacion());
        }
        RequerimientoRenovacion requerimiento = requerimientoOpt.get();


        Optional<InformeRenovacionContrato> informeOpt = informeRenovacionContratoDao.findByRequerimientoAndEstado(requerimiento, Constantes.ESTADO_ACTIVO);
        if (!informeOpt.isPresent()) {
            throw new DataNotFoundException("No se encontró un informe activo para el requerimiento de renovación con ID: " + requestDTO.getIdRequerimientoRenovacion());
        }
        InformeRenovacionContrato informe = informeOpt.get();


        ExpedienteOutRO expedienteOutRO = sigedApiConsumer.obtenerExpediente(requerimiento.getNuExpediente());
        if (expedienteOutRO == null) {
            throw new ValidacionException("El expediente " + requerimiento.getNuExpediente() + " no existe en SIGED");
        }

        MultipartFile file = requestDTO.getArchivo();
        String originalFilename = file.getOriginalFilename();
        Path targetPath = Files.createTempFile(sigedFilePath, originalFilename);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        File fileToUpload = targetPath.toFile();

        List<File> archivos = new ArrayList<>();
        archivos.add(fileToUpload);

        ExpedienteInRO expedienteInRO = new ExpedienteInRO();
        expedienteInRO.setNroExpediente(requerimiento.getNuExpediente());


        DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivos);


        Archivo archivo = new Archivo();
        archivo.setIdDocumentoSiged(documentoOutRO.getCodigoDocumento().longValue());
        archivo.setIdInformeRenovacion(informe.getIdInformeRenovacion());
        archivo.setNombreArchivo(originalFilename);
        archivo = archivoDao.save(archivo);


        try {
            Files.delete(targetPath);
        } catch (IOException e) {
            logger.error("Error al eliminar archivo temporal", e);
        }

        InformePresupuestalCreateResponse response = new InformePresupuestalCreateResponse();
        response.setIdArchivo(archivo.getIdArchivo());
        response.setIdDocumentoSiged(archivo.getIdDocumentoSiged());
        response.setIdRequerimientoRenovacion(requestDTO.getIdRequerimientoRenovacion());

        return response;
        */
        return null;
    }

    @Override
    public InformePresupuestalCreateResponse anularDocumentoSiged(
            Long idRequerimientoAprobacion,
            Contexto contexto) throws Exception {

        logger.info("anularDocumentoSiged - idRequerimientoAprobacion: {}", idRequerimientoAprobacion);

        /*
        Optional<RequerimientoRenovacion> requerimientoOpt = requerimientoRenovacionDao.findById(requestDTO.getIdRequerimientoRenovacion());
        if (!requerimientoOpt.isPresent()) {
            throw new DataNotFoundException("No se encontró el requerimiento de renovación con ID: " + requestDTO.getIdRequerimientoRenovacion());
        }
        RequerimientoRenovacion requerimiento = requerimientoOpt.get();


        Optional<Archivo> archivoOpt = archivoDao.findById(requestDTO.getIdArchivo());
        if (!archivoOpt.isPresent()) {
            throw new DataNotFoundException("No se encontró el archivo con ID: " + requestDTO.getIdArchivo());
        }
        Archivo archivo = archivoOpt.get();

        DocumentoAnularInRO documentoAnularInRO = new DocumentoAnularInRO();
        documentoAnularInRO.setNroExpediente(requerimiento.getNuExpediente());
        documentoAnularInRO.setMotivo("Anulación solicitada por el usuario");

        DocumentoAnularOutRO documentoAnularOutRO = sigedApiConsumer.anularDocumento(documentoAnularInRO);
        if (documentoAnularOutRO == null) {
            throw new ValidacionException("Error al anular el documento en SIGED");
        }

        archivoDao.delete(archivo);

        InformePresupuestalCreateResponse response = new InformePresupuestalCreateResponse();
        response.setIdArchivo(archivo.getIdArchivo());
        response.setIdDocumentoSiged(archivo.getIdDocumentoSiged());
        response.setIdRequerimientoRenovacion(requestDTO.getIdRequerimientoRenovacion());

        return response;
        */
        return null;
    }
}
