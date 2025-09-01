package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class RequerimientoRenovacionListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idReqRenovacion;
    private SicoesSolicitudRenovacionContratoDTO solicitudPerfil;
    private Long idUsuario;
    private String nuExpediente;
    private String tiSector;
    private String tiSubSector;
    private String noItem;
    private Date feRegistro;
    private String estadoReqRenovacion;
    private String deObservacion;
    private String esRegistro;
    private String estadoAprobacionInforme;
    private String estadoAprobacionGPPM;
    private String estadoAprobacionGSE;

}
