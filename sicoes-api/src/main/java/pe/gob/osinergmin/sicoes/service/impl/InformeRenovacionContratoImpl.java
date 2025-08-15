package pe.gob.osinergmin.sicoes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.repository.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.service.InformeRenovacionContratoService;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class InformeRenovacionContratoImpl implements InformeRenovacionContratoService {

    @Autowired
    InformeRenovacionContratoDao informeRenovacionContratoDao;
    @Override
    public Page<InformeRenovacionContrato> listaInformes(String numeroExpediente, String estado,
            String nombreContratista, Contexto contexto) {

        return informeRenovacionContratoDao.listaInformes(numeroExpediente, estado, nombreContratista);
    }
    
}
