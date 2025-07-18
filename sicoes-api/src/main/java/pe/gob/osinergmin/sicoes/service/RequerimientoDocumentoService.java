package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumento;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumentoDetalle;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoCoordinadorDTO;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;

public interface RequerimientoDocumentoService extends BaseService<RequerimientoDocumento, Long> {

    Page<RequerimientoDocumento> listarRequerimientosDocumentos(FiltroRequerimientoDocumentoDTO filtroRequerimientoDocumentoDTO, Pageable pageable, Contexto contexto);

    List<RequerimientoDocumentoDetalle> listarRequerimientosDocumentosDetalle(String documentoUuid);

    List<RequerimientoDocumentoDetalle> actualizarRequerimientosDocumentosDetalle(List<RequerimientoDocumentoDetalle> listRequerimientoDocumentoDetalle, Contexto contexto);

    Page<RequerimientoDocumento> listarRequerimientosDocumentosCoordinador(FiltroRequerimientoDocumentoCoordinadorDTO filtroRequerimientoDocumentoCoordinadorDTO, Pageable pageable, Contexto contexto);

    RequerimientoDocumentoDetalle patchRequerimientoDocumentoDetalle(RequerimientoDocumentoDetalle requerimientoDocumentoDetalle, Contexto contexto);

    RequerimientoDocumento evaluarRequerimientosDocumento(String uuid, Contexto contextos);

}
