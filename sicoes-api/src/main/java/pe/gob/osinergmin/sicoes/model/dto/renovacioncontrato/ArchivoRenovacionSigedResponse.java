package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para las respuestas de gestión de archivos SIGED en renovación de contrato.
 * 
 * Esta clase encapsula los datos de respuesta después de adjuntar o anular documentos
 * asociados a un informe de renovación de contrato en el sistema SIGED.
 * 
 * @author Sistema SICOES
 * @version 1.0
 */
@Getter
@Setter
public class ArchivoRenovacionSigedResponse {
    
    private Long idInformeRenovacion;
    
    private Integer codigoDocumento;
    
    public ArchivoRenovacionSigedResponse() {
    }
    
    public ArchivoRenovacionSigedResponse(Long idInformeRenovacion, Integer codigoDocumento) {
        this.idInformeRenovacion = idInformeRenovacion;
        this.codigoDocumento = codigoDocumento;
    }
}
