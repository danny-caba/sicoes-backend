package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificacionRCDTO {
    private Long idNotificacion;


    private String correo;

    private String asunto;

    private String mensaje;

}
