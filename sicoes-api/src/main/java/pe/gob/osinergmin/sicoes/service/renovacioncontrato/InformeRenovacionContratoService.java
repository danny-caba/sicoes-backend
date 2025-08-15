package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface InformeRenovacionContratoService {

    Page<InformeRenovacionContrato> listaInformes(
        String numeroExpediente,
        Integer estado,
        String nombreContratista,
        Contexto contexto,
        Pageable pageable);
    
}
