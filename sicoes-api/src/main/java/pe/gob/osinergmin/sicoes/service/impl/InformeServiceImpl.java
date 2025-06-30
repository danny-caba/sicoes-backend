package pe.gob.osinergmin.sicoes.service.impl;

import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.mapper.InformeMapper;
import pe.gob.osinergmin.sicoes.model.dto.InformeDTO;
import pe.gob.osinergmin.sicoes.repository.InformeDao;
import pe.gob.osinergmin.sicoes.service.InformeService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import pe.gob.osinergmin.sicoes.model.*;

import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;

@Service
public class InformeServiceImpl implements InformeService {

    private static final Logger logger = LogManager.getLogger(InformeServiceImpl.class);

    @Autowired
    private InformeDao informeDao;

    @Autowired
    private InformeMapper informeMapper;

    @Override
    public InformeDTO guardar(InformeDTO informeDTO, Contexto contexto) {
        try {
            Informe entidad = informeMapper.toEntity(informeDTO);
            AuditoriaUtil.setAuditoriaRegistro(entidad, contexto);
            Informe guardado = informeDao.save(entidad);
            return informeMapper.toDTO(guardado);
        } catch (Exception ex) {
            logger.error("Error al guardar el informe. Contexto: {}, DTO: {}", contexto, informeDTO, ex);
            throw new RuntimeException("Error al guardar el informe", ex);
        }
    }
}
