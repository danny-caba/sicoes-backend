package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirmaDigitalRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idReqRenovacion;
    private String token;
    private String ip;
}
