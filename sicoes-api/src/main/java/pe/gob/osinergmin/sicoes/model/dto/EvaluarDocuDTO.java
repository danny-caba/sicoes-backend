package pe.gob.osinergmin.sicoes.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
public class EvaluarDocuDTO {

    private Long idEvalDocumento;
    private Long idDocumento;
    private Long idEvaluadoPor;
    private String conforme;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date fechaEvaluacion;
    private String ipActualizacion;
    private String usuActualizacion;
}
