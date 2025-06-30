package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.Optional;

public interface RequerimientoService {

    RequerimientoDTO guardar(RequerimientoDTO requerimientoDTO, Contexto contexto);

    Page<RequerimientoDTO> listar(FiltroRequerimientoDTO filtroRequerimientoDTO, Pageable pageable, Contexto contextos);

    RequerimientoDTO archivar(Long id, String observacion, Contexto contexto);

    Optional<RequerimientoDTO> obtenerPorId(Long id);

}
