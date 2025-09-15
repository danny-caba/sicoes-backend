package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import gob.osinergmin.siged.remote.rest.ro.in.ClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.DireccionxClienteInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.ClienteListInRO;
import gob.osinergmin.siged.remote.rest.ro.in.list.DireccionxClienteListInRO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gob.osinergmin.siged.remote.rest.ro.in.DocumentoInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformePresupuestalCreateResponse;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.repository.SolicitudDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformePresupuestalService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Implementación del servicio para la gestión de informes presupuestales.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class InformePresupuestalServiceImpl implements InformePresupuestalService {

    private final Logger log = LogManager.getLogger(InformePresupuestalServiceImpl.class);
    
    private final InformeRenovacionContratoDao informeRenovacionContratoDao;
    private final RequerimientoRenovacionDao requerimientoRenovacionDao;
    private final ArchivoDao archivoDao;
    private final SicoesSolicitudDao sicoesSolicitudDao;
    private final ListadoDetalleService listadoDetalleService;
    private final SolicitudDao solicitudDao;
    private final SigedOldConsumer sigedOldConsumer;
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
            Archivo archivoGuardado = archivoDao.save(archivo);
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
                return null;
    }



}