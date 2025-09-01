package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gob.osinergmin.siged.remote.rest.ro.in.DocumentoAnularInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoAnularOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.ArchivoRenovacionSigedService;

@Service
public class ArchivoRenovacionSigedServiceImpl implements ArchivoRenovacionSigedService {

    private static final Logger logger = LogManager.getLogger(ArchivoRenovacionSigedServiceImpl.class);

    @Autowired
    private SigedApiConsumer sigedApiConsumer;

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
}
