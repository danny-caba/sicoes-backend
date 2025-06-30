package pe.gob.osinergmin.sicoes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaldoSupervisoraDTO {

    private Long idSaldoSupervisora;

    private Supervisora supervisora;

    private Long cantidad;

    private String usCreacion;
    private String ipCreacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feCreacion;

    private String usActualizacion;
    private String ipActualizacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date feActualizacion;
}
