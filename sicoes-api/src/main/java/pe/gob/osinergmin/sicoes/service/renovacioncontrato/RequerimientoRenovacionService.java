package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoRenovacionListDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface RequerimientoRenovacionService  {

	public RequerimientoRenovacion guardar(RequerimientoRenovacion requerimientoRenovacion, Contexto contexto)throws Exception;
	public Page<RequerimientoRenovacionListDTO> buscar(String idSolicitud, String requerimientoRenovacion, String sector, String subSector, Pageable pageable, Contexto contexto);
    public RequerimientoRenovacion obtener(String nuExpediente, Contexto contexto);
}
