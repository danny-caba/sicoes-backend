package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionContratoService;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class InformeRenovacionContratoImpl implements InformeRenovacionContratoService {

    @Autowired
    InformeRenovacionContratoDao informeRenovacionContratoDao;
    @Override
    public Page<InformeRenovacionContrato> listaInformes(
            String numeroExpediente, 
            Integer estado,
            String nombreContratista, 
            Contexto contexto,
            Pageable pageable) {

        Boolean esVigente = true;        

        return informeRenovacionContratoDao.findByFiltrosWithJoins(numeroExpediente,esVigente,pageable);
    }
    
}
