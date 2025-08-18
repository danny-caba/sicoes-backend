package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionContratoService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.InformeRenovacionContratoMapper;
import pe.gob.osinergmin.sicoes.util.Contexto;


@Service
public class InformeRenovacionContratoImpl implements InformeRenovacionContratoService {

    private final InformeRenovacionContratoDao informeRenovacionContratoDao;
    public InformeRenovacionContratoImpl(InformeRenovacionContratoDao informeRenovacionContratoDao) {
        this.informeRenovacionContratoDao = informeRenovacionContratoDao;
    }

    @Override
    public Page<InformeRenovacionContratoDTO> listaInformes(
            String numeroExpediente, 
            Long estado,
            Long idContratista ,
            Contexto contexto,
            Pageable pageable) {

        Boolean esVigente = true;
        contexto.getUsuario().getRoles();
        Page<InformeRenovacionContrato> listInforme =  informeRenovacionContratoDao.findByFiltrosWithJoins(
                                                        numeroExpediente,
                                                        esVigente,
                                                        estado,
                                                        idContratista,
                                                        pageable);
        return listInforme.map(InformeRenovacionContratoMapper.MAPPER::map);
    }

    
}
