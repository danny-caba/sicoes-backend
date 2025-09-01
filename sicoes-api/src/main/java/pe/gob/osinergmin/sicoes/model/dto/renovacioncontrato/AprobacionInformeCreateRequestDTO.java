package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AprobacionInformeCreateRequestDTO {
	private Long idUsuario;
	private List<InformeAprobacionCreateRequestDTO> informes;
	private String observacion;
}
