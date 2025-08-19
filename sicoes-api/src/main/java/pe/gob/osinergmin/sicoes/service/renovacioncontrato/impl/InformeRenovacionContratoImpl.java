package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionContratoService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.InformeRenovacionContratoMapper;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
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
        // TODO: Falta implementar flujo segun roles
        Page<InformeRenovacionContrato> listInforme =  informeRenovacionContratoDao.findByFiltrosWithJoins(
                                                        numeroExpediente,
                                                        esVigente,
                                                        estado,
                                                        idContratista,
                                                        pageable);
        return listInforme.map(InformeRenovacionContratoMapper.MAPPER::toDTO);
    }

    @Override
    public InformeRenovacionContratoDTO crearInforme(InformeRenovacionContratoDTO informeRenovacionContratoDTO, Contexto contexto) {
        

    InformeRenovacionContrato informe = InformeRenovacionContratoMapper.MAPPER.toEntity(informeRenovacionContratoDTO);
    UUID uuid = UUID.randomUUID();
    String uuidString = uuid.toString();

    informe.setUuidInformeRenovacion(uuidString);
    informe.setVigente(Boolean.TRUE);
    informe.setRegistro(Constantes.ESTADO.ACTIVO);
    informe.setCompletado(Constantes.ESTADO.INACTIVO);

    AuditoriaUtil.setAuditoriaRegistro(informe,contexto);
    //TODO: Falta implementar crear PDF   
    //TODO: Falta implementar flujo ALFRESCO
    //TODO: Falta implementar flujo SIGED
        
    InformeRenovacionContrato nuevoInforme =  informeRenovacionContratoDao.save(informe);
    return InformeRenovacionContratoMapper.MAPPER.toDTO(nuevoInforme);
    }

    
}
