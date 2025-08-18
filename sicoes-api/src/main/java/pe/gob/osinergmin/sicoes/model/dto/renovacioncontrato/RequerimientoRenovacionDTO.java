package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import java.util.Date;

@Getter
@Setter
public class RequerimientoRenovacionDTO {

    private Long idReqRenovacion;

    private SicoesSolicitudRenovacionContratoDTO solicitudPerfil;

    private Long idUsuario;

    private UsuarioRCDTO usuario;

    private String nuExpediente;

    private String tiSector;

    private String tiSubSector;

    private String noItem;

    private Date feRegistro;

    private ListadoDetalleDTO estadoReqRenovacion;
    private String deObservacion;
    private String esRegistro;

}
