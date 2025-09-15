package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import gob.osinergmin.siged.remote.rest.ro.in.DocumentoAnularInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.model.Archivo;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.DocumentoInformePresupuestoRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.DocumentoInformePresupuestoResponseDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.repository.ArchivoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.BorrarService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BorrarServiceImpl implements BorrarService {

    private static final Logger logger = LogManager.getLogger(BorrarServiceImpl.class);

    @Autowired
    private InformeRenovacionContratoDao informeRenovacionContratoDao;

    @Autowired
    private RequerimientoRenovacionDao requerimientoRenovacionDao;

    @Autowired
    private ArchivoDao archivoDao;

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    private SigedApiConsumer sigedApiConsumer;

    @Override
    public DocumentoInformePresupuestoResponseDTO agregarDocumento(DocumentoInformePresupuestoRequestDTO requestDTO, Contexto contexto) throws Exception {
        logger.info("agregarDocumento - idInformeRenovacion: {}", requestDTO.getIdInformeRenovacion());

        Optional<InformeRenovacionContrato> informeOpt = informeRenovacionContratoDao.findById(requestDTO.getIdInformeRenovacion());
        if (!informeOpt.isPresent()) {
            throw new DataNotFoundException("No se encontró el informe de renovación con ID: " + requestDTO.getIdInformeRenovacion());
        }
        InformeRenovacionContrato informeRenovacionContrato = informeOpt.get();

        Optional<RequerimientoRenovacion> requerimientoOpt = requerimientoRenovacionDao.findById(informeRenovacionContrato.getRequerimiento().getIdReqRenovacion());
        if (!requerimientoOpt.isPresent()) {
            throw new DataNotFoundException("No se encontró el requerimiento de renovación con ID: " + informeRenovacionContrato.getRequerimiento().getIdReqRenovacion());
        }
        RequerimientoRenovacion requerimiento = requerimientoOpt.get();

        ExpedienteInRO expedienteInRO = new ExpedienteInRO();
        expedienteInRO.setNroExpediente(requerimiento.getNuExpediente());

        List<File> archivos = Arrays.asList(requestDTO.getDocumento());

        DocumentoOutRO documentoOutRO = sigedApiConsumer.agregarDocumento(expedienteInRO, archivos);

        Archivo archivo = new Archivo();
        archivo.setIdInformeRenovacion(requestDTO.getIdInformeRenovacion());
        archivo.setIdDocumentoSiged(documentoOutRO.getCodigoDocumento().longValue());

        ListadoDetalle estado = listadoDetalleService.obtenerListadoDetalle("ESTADO_ARCHIVO", "ASOCIADO");
        archivo.setEstado(estado);

        ListadoDetalle tipoArchivo = listadoDetalleService.obtenerListadoDetalle("TIPO_ARCHIVO", "TA35");
        archivo.setTipoArchivo(tipoArchivo);

        archivo = archivoDao.save(archivo);

        DocumentoInformePresupuestoResponseDTO response = new DocumentoInformePresupuestoResponseDTO();
        response.setIdInformeRenovacion(requestDTO.getIdInformeRenovacion());
        response.setIdArchivo(archivo.getIdArchivo());
        response.setIdDocumentoSiged(archivo.getIdDocumentoSiged());

        return response;
    }

    @Override
    public DocumentoInformePresupuestoResponseDTO anularDocumento(DocumentoInformePresupuestoRequestDTO requestDTO, Contexto contexto) throws Exception {
        logger.info("anularDocumento - idInformeRenovacion: {}", requestDTO.getIdInformeRenovacion());

        Optional<InformeRenovacionContrato> informeOpt = informeRenovacionContratoDao.findById(requestDTO.getIdInformeRenovacion());
        if (!informeOpt.isPresent()) {
            throw new DataNotFoundException("No se encontró el informe de renovación con ID: " + requestDTO.getIdInformeRenovacion());
        }
        InformeRenovacionContrato informeRenovacionContrato = informeOpt.get();

        Optional<RequerimientoRenovacion> requerimientoOpt = requerimientoRenovacionDao.findById(informeRenovacionContrato.getRequerimiento().getIdReqRenovacion());
        if (!requerimientoOpt.isPresent()) {
            throw new DataNotFoundException("No se encontró el requerimiento de renovación con ID: " + informeRenovacionContrato.getRequerimiento().getIdReqRenovacion());
        }
        RequerimientoRenovacion requerimiento = requerimientoOpt.get();

        DocumentoAnularInRO documentoAnularInRO = new DocumentoAnularInRO();
        documentoAnularInRO.setNroExpediente(requerimiento.getNuExpediente());
        documentoAnularInRO.setMotivo("anular");

        sigedApiConsumer.anularDocumento(documentoAnularInRO);

        List<Archivo> archivos = archivoDao.buscarArchivo("TA35", null);
        Optional<Archivo> archivoOpt = archivos.stream()
                .filter(a -> a.getIdInformeRenovacion() != null && a.getIdInformeRenovacion().equals(requestDTO.getIdInformeRenovacion()))
                .findFirst();

        if (archivoOpt.isPresent()) {
            Archivo archivo = archivoOpt.get();
            archivoDao.delete(archivo);

            DocumentoInformePresupuestoResponseDTO response = new DocumentoInformePresupuestoResponseDTO();
            response.setIdInformeRenovacion(requestDTO.getIdInformeRenovacion());
            response.setIdArchivo(archivo.getIdArchivo());
            response.setIdDocumentoSiged(archivo.getIdDocumentoSiged());

            return response;
        }

        throw new DataNotFoundException("No se encontró el archivo para anular");
    }
}
