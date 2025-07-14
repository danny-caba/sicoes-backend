package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoAprobacionDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.Optional;

public interface RequerimientoService extends BaseService<Requerimiento, Long> {

    Requerimiento guardar(Requerimiento requerimiento, Contexto contexto);

    Page<Requerimiento> listar(FiltroRequerimientoDTO filtroRequerimientoDTO, Pageable pageable, Contexto contextos);

    Requerimiento archivar(Requerimiento requerimiento, Contexto contexto);

    Optional<Requerimiento> obtenerPorUuid(String uuid);

    Long obtenerId(String requerimientoUuid);

    Requerimiento aprobar(String uuid, RequerimientoAprobacionDTO aprobacion, Contexto contexto);

    Page<Requerimiento> listarPorAprobar(FiltroRequerimientoDTO filtroRequerimientoDTO, Pageable pageable, Contexto contextos);

    Requerimiento actualizar(Requerimiento requerimiento, Contexto contexto);
}
