package pe.gob.osinergmin.sicoes.service;

import org.springframework.data.domain.Page;

import pe.gob.osinergmin.sicoes.model.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface InformeRenovacionContratoService {

    Page<InformeRenovacionContrato> listaInformes(String numeroExpediente, String estado, String nombreContratista,
            Contexto contexto);
    
}
