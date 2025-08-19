package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import java.util.Date;

@Getter
@Setter
public class PropuestaRenovacionContratoDTO {

    private Long idPropuesta;

    private String nombre;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date fechaPresentacion;

    private ListadoDetalleDTO estado;

    private String propuestaUuid;

    private ListadoDetalleDTO ganador;

    private String decripcionDescarga;

    private String rutaDescarga;
}
