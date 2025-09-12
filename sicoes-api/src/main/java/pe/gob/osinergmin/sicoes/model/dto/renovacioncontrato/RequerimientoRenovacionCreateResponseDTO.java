package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;

@Getter
@Setter
public class RequerimientoRenovacionCreateResponseDTO {    
    private Long idRequerimientoRenovacion;
    private ListadoDetalleDTO estadoReqRenovacion;
}
