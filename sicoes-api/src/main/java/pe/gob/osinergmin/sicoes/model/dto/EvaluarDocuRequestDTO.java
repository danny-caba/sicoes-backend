package pe.gob.osinergmin.sicoes.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluarDocuRequestDTO {
    private Long idDocumento;
    private String conformidad;
    private String observacion;
    private Long idRol;
}
