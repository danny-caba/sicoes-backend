package pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoInformePresupuestoRequestDTO {
	private Long idInformeRenovacion;
	private File documento;
	private String usuario;
	private String ip;
}
