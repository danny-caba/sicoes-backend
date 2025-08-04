package pe.gob.osinergmin.sicoes.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EvaluarDocuResponseDTO implements Serializable {
    private Long idEvaluarDocuReemp;
    private Long idDocuReemp;
    private String fecEvaluacion;
    private String evaluador;
    private String conformidad;
    private String observacion;
}
