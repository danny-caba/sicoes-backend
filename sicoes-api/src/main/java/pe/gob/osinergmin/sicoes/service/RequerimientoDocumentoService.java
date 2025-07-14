package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumento;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumentoDetalle;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;

public interface RequerimientoDocumentoService extends BaseService<RequerimientoDocumento, Long> {

    Page<RequerimientoDocumento> listar(FiltroRequerimientoDocumentoDTO filtroRequerimientoDocumentoDTO, Pageable pageable, Contexto contextos);

    RequerimientoDocumentoDetalle obtenerPorRequerimientoDocumentoUuid(String documentoUuid);

    RequerimientoDocumento registrar(List<RequerimientoDocumentoDetalle> listRequerimientoDocumentoDetalle, Long idRequerimiento, Contexto contexto);

}
