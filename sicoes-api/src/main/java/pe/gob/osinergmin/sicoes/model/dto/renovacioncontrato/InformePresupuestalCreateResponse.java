package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para las respuestas de gestión de informes presupuestales.
 * 
 * Esta clase encapsula los datos de respuesta después de adjuntar o anular documentos
 * asociados a un informe presupuestal.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InformePresupuestalCreateResponse {
    private Long idArchivo;
    private Long idDocumentoSiged;
    private Long idRequerimientoAprobacion;
    private String estado;
    private String mensaje;
}
