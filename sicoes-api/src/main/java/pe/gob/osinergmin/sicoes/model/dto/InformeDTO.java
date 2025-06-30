package pe.gob.osinergmin.sicoes.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.osinergmin.sicoes.model.Requerimiento;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InformeDTO {

    private Long idReqInforme;

    private Requerimiento requerimiento;

    private String usCreacion;
    private String ipCreacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feCreacion;

    private String usActualizacion;
    private String ipActualizacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feActualizacion;
}
