package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;


import javax.validation.constraints.NotEmpty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class InformeRenovacionContratoDTO {

    private Long idInformeRenovacion;

    private UsuarioRCDTO usuario;

    private NotificacionRCDTO notificacion;

    private RequerimientoRenovacionDTO requerimiento;

    private List<RequerimientoAprobacionDTO> aprobaciones;

    private String objeto;

    private String baseLegal;

    private String antecedentes;

    private String justificacion;

    private String necesidad;

    private String conclusiones;

    private Long vigente;

    private String registro;

    @NotEmpty
    private String completado;

    private ListadoDetalleDTO estadoAprobacionInforme;

    private String uuiInfoRenovacion;
}
