package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitacionCreateRequestDTO {
    private Long idReqRenovacion;
    private String usuario;    
    private String ip;
}
