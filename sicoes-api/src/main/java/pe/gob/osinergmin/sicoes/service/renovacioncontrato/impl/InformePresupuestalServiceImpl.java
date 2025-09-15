package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import gob.osinergmin.siged.remote.rest.ro.in.DocumentoAnularInRO;

import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformePresupuestalCreateResponse;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformePresupuestalService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
/**
 * Implementación del servicio para la gestión de informes presupuestales.
 */
@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class InformePresupuestalServiceImpl implements InformePresupuestalService {
    
    private final ArchivoDao archivoDao;
    private final SicoesSolicitudDao sicoesSolicitudDao;
    private final SigedOldConsumer sigedOldConsumer;
    private final SigedApiConsumer sigedApiConsumer;

    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;
    @Autowired
    private ClienteSigedServiceImpl clienteSigedService;


    @Override
    @Transactional
    public InformePresupuestalCreateResponse uploadDocument(
            Long idRequerimientoAprobacion,
            MultipartFile file,
            Contexto contexto) {
        RequerimientoAprobacion entity = requerimientoAprobacionDao.obtenerPorId(idRequerimientoAprobacion);
        if (entity == null) {
            throw new DataNotFoundException(
                String.format("No se encontró el requerimiento de aprobación con ID: %d", idRequerimientoAprobacion));
        }
        log.info("Iniciando carga de documento para requerimiento ID: {}", idRequerimientoAprobacion);
        InformeRenovacion informe=entity.getInformeRenovacion();
        InformeRenovacionContrato informeRenovacionContrato= entity.getInformeRenovacionContrato();
        RequerimientoRenovacion requerimiento=entity.getInformeRenovacion().getRequerimientoRenovacion();

        try {
            // 3. Crear y procesar archivo
            Archivo archivo = clienteSigedService.buildArchivo(file, informe.getIdInformeRenovacion());
            // 4. Subir a Alfresco
            String alfrescoPath = sigedOldConsumer.subirArchivosAlfrescoRenovacionContrato(
                    requerimiento.getIdReqRenovacion(), archivo);
            archivo.setNombreAlFresco(alfrescoPath);
            // 5. Registrar auditoría
            AuditoriaUtil.setAuditoriaRegistro(archivo, contexto);
            archivo.setIdInformeRenovacion(informe.getIdInformeRenovacion());
            // 6. Obtener solicitud asociada
            SicoesSolicitud solicitud = sicoesSolicitudDao.findById(requerimiento.getIdSoliPerfCont())
                .orElseThrow(() -> new DataNotFoundException(
                    String.format("No se encontró la solicitud asociada al requerimiento ID: %d",
                            requerimiento.getIdSoliPerfCont())));
            // 7. Adjuntar a SIGED y guardar
            clienteSigedService.adjuntarDocumentoSiged(informeRenovacionContrato, archivo.getNombreReal(), file, solicitud);
            archivo = archivoDao.save(archivo);
            log.info("Documento procesado exitosamente para requerimiento ID: {}", idRequerimientoAprobacion);
            // 8. Construir respuesta
            return null;

        } catch (Exception e) {
            log.error("Error al procesar documento para requerimiento ID: {}", idRequerimientoAprobacion, e);
            throw new DataNotFoundException(String.format("Error al procesar el documento: %s", e.getMessage()));
        }
    }


    @Override
    @Transactional
    public InformePresupuestalCreateResponse anularDocumentoSiged(
            Long idRequerimientoAprobacion,
            Contexto contexto) {
        RequerimientoAprobacion entity = requerimientoAprobacionDao.obtenerPorId(idRequerimientoAprobacion);
        if (entity == null) {
            throw new DataNotFoundException(
                    String.format("No se encontró el requerimiento de aprobación con ID: %d", idRequerimientoAprobacion));
        }
        log.info("Iniciando carga de documento para requerimiento ID: {}", idRequerimientoAprobacion);

        RequerimientoRenovacion requerimiento=entity.getInformeRenovacion().getRequerimientoRenovacion();
        DocumentoAnularInRO documentoAnularInRO = new DocumentoAnularInRO();
        documentoAnularInRO.setNroExpediente(requerimiento.getNuExpediente());
        documentoAnularInRO.setMotivo("anular");
        try {
        sigedApiConsumer.anularDocumento(documentoAnularInRO);
        } catch (Exception e) {
            log.error("Error al procesar documento para requerimiento ID: {}", idRequerimientoAprobacion, e);
            throw new DataNotFoundException(String.format("Error al procesar el documento: %s", e.getMessage()));
        }
        return null;
    }

}