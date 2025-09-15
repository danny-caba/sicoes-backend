package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para las respuestas de gestión de informes presupuestales.
 * 
 * Esta clase encapsula los datos de respuesta después de adjuntar o anular documentos
 * asociados a un informe presupuestal.
 */
@Getter
@Setter
public class InformePresupuestalCreateResponse {
    
    private Long idInformeRenovacion;
    private Long idRequerimientoRenovacion;
    private Long idArchivo;
    private Long idDocumentoSiged;
    private String nombreDocumento;
    private String estado;
    private String mensaje;
}
