package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface InformeRenovacionService {

	public Page<InformeRenovacionDTO> buscar(String nuExpediente, String contratista, String estadoAprobacion, Pageable pageable, Contexto contexto);

}
