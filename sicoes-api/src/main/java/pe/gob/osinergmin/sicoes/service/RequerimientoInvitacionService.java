package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface RequerimientoInvitacionService extends BaseService<RequerimientoInvitacion, Long> {

    RequerimientoInvitacion guardar(RequerimientoInvitacion requerimientoInvitacionDTO, Contexto contexto);

    RequerimientoInvitacion eliminar(String uuid, Contexto contexto);

    Page<RequerimientoInvitacion> obtener(Long idEstado, String fechaInicioInvitacion, String fechaFinInvitacion, String requerimientoUuid, Contexto contexto, Pageable pageable);

    Requerimiento evaluar(String  uuid, ListadoDetalleDTO estado, Contexto contexto);

    void actualizarEstadoInvitaciones(Contexto contexto);

}
