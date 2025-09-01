package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.util.bean.siged.AccessRequestInFirmaDigital;

@Getter
@Setter
public class FirmaDigitalResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private AccessRequestInFirmaDigital acceso;
    private Long idArchivo;
}
