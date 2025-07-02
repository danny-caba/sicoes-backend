package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface RequerimientoInvitacionService  extends BaseService<RequerimientoInvitacion, Long>{
    Page<RequerimientoInvitacion> obtener(Long idEstado, String fechaInicioInvitacion,
                                          String fechaFinInvitacion, Contexto contexto, Pageable pageable);

    Requerimiento evaluar(Long  id, ListadoDetalleDTO estado, Contexto contexto);
}
