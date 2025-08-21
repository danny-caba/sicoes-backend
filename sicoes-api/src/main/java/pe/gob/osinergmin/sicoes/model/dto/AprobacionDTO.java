package pe.gob.osinergmin.sicoes.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AprobacionDTO {
    private Long idAprobacion;
    private String deObservacion;
    private Long idDocumento;
    private Long estadoAprob;
    private Long idAprobador;
    private String requerimiento;
    private String accion;
    private Boolean conforme;

}