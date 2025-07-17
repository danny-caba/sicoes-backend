package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface RequerimientoAprobacionService extends BaseService<RequerimientoAprobacion, Long> {
    Page<RequerimientoAprobacion> obtenerHistorial(String uuid, Contexto contexto, Pageable pageable);
    Page<RequerimientoAprobacionResponseDTO>  buscar(FiltroRequerimientoDTO filtroRequerimientoDTO, Pageable pageable, Contexto contexto);

}
