package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface InformeRenovacionContratoService {

    Page<InformeRenovacionContratoDTO> listaInformes(
        String numeroExpediente,
        Long estado,
        Long idContratista,
        Contexto contexto,
        Pageable pageable);

    InformeRenovacionContratoDTO  crearInforme(InformeRenovacionContratoDTO informeRenovacionContratoDTO, Contexto contexto);
    
}
