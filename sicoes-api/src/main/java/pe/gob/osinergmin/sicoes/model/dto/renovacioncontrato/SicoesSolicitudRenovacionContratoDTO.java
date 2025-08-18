package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.dto.PropuestaDTO;


@Getter
@Setter
public class SicoesSolicitudRenovacionContratoDTO {
    private static final long serialVersionUID = 1L;

    private Long idSolicitud;

    private PropuestaRenovacionContratoDTO propuesta;

    private Long idSolicitudPadre;

    private SupervisoraRenovacionContratoDTO supervisora;

    private String descripcionSolicitud;

    private String tipoSolicitud;

    private String estadoProcesoSolicitud;
}
