package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;

@Getter
@Setter
public class InformeCreateResponseDTO {   
    private Long idInformeRenovacion; 
    private ListadoDetalleDTO estadoAprobacionInforme;
    private RequerimientoRenovacionCreateResponseDTO requerimientoRenovacion;
}
