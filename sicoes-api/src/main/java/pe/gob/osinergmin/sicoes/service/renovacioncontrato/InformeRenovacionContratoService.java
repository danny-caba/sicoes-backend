package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoAprobacionDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface InformeRenovacionContratoService {

    Page<InformeRenovacionContratoDTO> listaInformes(
            String tipoAprobador,
        String numeroExpediente,
        Long estadoAprobacionInforme,
        Long idContratista,
        Contexto contexto,
        Pageable pageable);

    InformeRenovacionContratoDTO  crearInforme(InformeRenovacionContratoDTO informeRenovacionContratoDTO, Contexto contexto);

    RequerimientoAprobacionDTO rechazarInformePresupuestal(RequerimientoAprobacionDTO requerimientoAprobacionDTO,
            Contexto contexto);

    InformeRenovacion obtenerInformePorNroExpediente(String nroExpediente, Contexto contexto);
}
