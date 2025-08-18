package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlazoConfirmacionRequestDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long idPlazoConfirmacion;
    private String fechaBase;
    private Integer tipoDia;
    private Integer numeroDias;
    private String estado;
    private String usuario;
    private String ip;
}
