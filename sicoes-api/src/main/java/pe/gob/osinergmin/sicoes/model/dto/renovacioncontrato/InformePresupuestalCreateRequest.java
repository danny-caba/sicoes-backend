package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO para las solicitudes de gestión de informes presupuestales.
 * 
 * Esta clase encapsula los datos necesarios para adjuntar o anular documentos
 * asociados a un informe presupuestal.
 */
@Getter
@Setter
public class InformePresupuestalCreateRequest {
    
    private Long idInformeRenovacion;
    private Long idRequerimientoRenovacion;
    private Long idArchivo;
    private Long idTipoDocumentoLd;
    private MultipartFile archivo;
    private String nombreDocumento;
    private String observacion;
}
