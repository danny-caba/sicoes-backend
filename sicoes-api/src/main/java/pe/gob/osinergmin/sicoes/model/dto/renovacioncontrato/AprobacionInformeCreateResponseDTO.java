package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AprobacionInformeCreateResponseDTO {
    private List<RequerimientoRenovacionAprobacionResponseDTO> requerimientosRenovacion;
}
