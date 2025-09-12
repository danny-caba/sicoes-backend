package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;

@Getter
@Setter
public class NotificacionCreateResponseDTO { 
    private Long idNotificacion;
    private ListadoDetalleDTO estado;
    private String correo;
    private String asunto;
}
