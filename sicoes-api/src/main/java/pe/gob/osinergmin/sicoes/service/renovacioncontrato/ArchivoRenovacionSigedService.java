package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import java.io.File;
import java.util.List;

import gob.osinergmin.siged.remote.rest.ro.in.DocumentoAnularInRO;
import gob.osinergmin.siged.remote.rest.ro.in.ExpedienteInRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoAnularOutRO;
import gob.osinergmin.siged.remote.rest.ro.out.DocumentoOutRO;

public interface ArchivoRenovacionSigedService {

    DocumentoOutRO agregarDocumento(ExpedienteInRO expediente, List<File> archivos) throws Exception;
    
    DocumentoAnularOutRO anularDocumento(DocumentoAnularInRO documento) throws Exception;
}
