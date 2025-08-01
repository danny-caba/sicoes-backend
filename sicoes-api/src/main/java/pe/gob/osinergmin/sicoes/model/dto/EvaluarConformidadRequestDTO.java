package pe.gob.osinergmin.sicoes.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EvaluarConformidadRequestDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date fecConformidad;
    private Long idDocumento;
    private String conformidad;
    private String observacion;
    private Long idRol;
}
