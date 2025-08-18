package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface RequerimientoRenovacionService  {

	public RequerimientoRenovacion guardar(RequerimientoRenovacion requerimientoRenovacion, Contexto contexto)throws Exception;
	public Page<RequerimientoRenovacion> buscar(String requerimientoRenovacion, Pageable pageable, Contexto contexto);

}
