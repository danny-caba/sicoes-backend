package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import gob.osinergmin.siged.remote.rest.ro.in.DocumentoAnularInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoAnularOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.ArchivoRenovacionSigedRequest;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.ArchivoRenovacionSigedResponse;

/**
 * Servicio para la gestión de archivos SIGED en renovación de contrato.
 * 
 * Esta interfaz define los métodos para adjuntar y anular documentos
 * asociados a informes de renovación de contrato en el sistema SIGED.
 * 
 * @author Sistema SICOES
 * @version 1.0
 */
public interface ArchivoRenovacionSigedService {

    DocumentoOutRO agregarDocumento(ExpedienteInRO expediente, List<File> archivos) throws Exception;
    
    DocumentoAnularOutRO anularDocumento(DocumentoAnularInRO documento) throws Exception;
    
    ArchivoRenovacionSigedResponse adjuntarArchivo(ArchivoRenovacionSigedRequest request, MultipartFile file) throws Exception;
    
    ArchivoRenovacionSigedResponse anularArchivo(ArchivoRenovacionSigedRequest request) throws Exception;
}
