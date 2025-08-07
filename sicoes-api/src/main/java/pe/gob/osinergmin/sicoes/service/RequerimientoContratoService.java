package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.RequerimientoContrato;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoContratoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoContratoDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface RequerimientoContratoService extends BaseService<RequerimientoContrato, Long> {
    Page<RequerimientoContrato> listar(FiltroRequerimientoContratoDTO filtroRequerimientoContratoDTO, Pageable pageable, Contexto contextos);
    RequerimientoContrato editar(String uuid, RequerimientoContratoDTO contrato, Contexto contexto);
}
