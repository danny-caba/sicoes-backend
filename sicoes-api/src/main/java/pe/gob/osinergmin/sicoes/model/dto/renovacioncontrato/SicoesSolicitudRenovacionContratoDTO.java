package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SicoesSolicitudRenovacionContratoDTO {

    private Long idSolicitud;

    private PropuestaRenovacionContratoDTO propuesta;

    private Long idSolicitudPadre;

    private SupervisoraRenovacionContratoDTO supervisora;

    private String descripcionSolicitud;

    private String tipoSolicitud;

    private String estadoProcesoSolicitud;
}
