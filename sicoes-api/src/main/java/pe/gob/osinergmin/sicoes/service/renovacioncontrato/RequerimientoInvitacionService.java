package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.Date;

public interface RequerimientoInvitacionService {

	public RequerimientoInvitacion aceptar(RequerimientoInvitacion requerimientoInvitacion, Contexto contexto)throws Exception;
	public int cancelarCaducados(Date fecha, Contexto contexto)throws Exception;

}
