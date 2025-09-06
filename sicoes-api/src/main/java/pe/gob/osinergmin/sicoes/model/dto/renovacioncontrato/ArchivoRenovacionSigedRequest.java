package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para las solicitudes de gestión de archivos SIGED en renovación de contrato.
 * 
 * Esta clase encapsula los datos necesarios para adjuntar o anular documentos
 * asociados a un informe de renovación de contrato en el sistema SIGED.
 * 
 * @author Sistema SICOES
 * @version 1.0
 */
@Getter
@Setter
public class ArchivoRenovacionSigedRequest {
    
    private Long idInformeRenovacion;
    
    public ArchivoRenovacionSigedRequest() {
    }
}
