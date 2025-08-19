package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.Contrato;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.SicoesTdSolPerConSec;
import pe.gob.osinergmin.sicoes.model.dto.ContratoDetalleDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;
import java.util.Optional;

public interface ContratoService extends BaseService<Contrato, Long> {
    Contrato registrarNuevoContrato(SicoesSolicitud solicitud, Contexto contexto) throws Exception;
    Page<Contrato> obtenerContratos(String expediente, String contratista, String tipoContrato, String areaSolicitante, Pageable pageable, Contexto contexto);
	Optional<ContratoDetalleDTO> obtenerContratoDetallePorId(Long idContrato);
	public ContratoDetalleDTO actualizarContratoDetalle(ContratoDetalleDTO contratoActualizadoDto, Contexto contexto);
	void procesarAccionesMasivas(Contrato req);
}