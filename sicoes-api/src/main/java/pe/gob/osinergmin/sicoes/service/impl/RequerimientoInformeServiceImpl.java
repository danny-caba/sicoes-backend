package pe.gob.osinergmin.sicoes.service.impl;

import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.repository.RequerimientoInformeDao;
import pe.gob.osinergmin.sicoes.service.RequerimientoInformeService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import pe.gob.osinergmin.sicoes.model.*;

import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;

@Service
public class RequerimientoInformeServiceImpl implements RequerimientoInformeService {

    private static final Logger logger = LogManager.getLogger(RequerimientoInformeServiceImpl.class);

    @Autowired
    private RequerimientoInformeDao requerimientoInformeDao;

    @Override
    public RequerimientoInforme guardar(RequerimientoInforme requerimientoInforme, Contexto contexto) {
        try {
            AuditoriaUtil.setAuditoriaRegistro(requerimientoInforme, contexto);
            return requerimientoInformeDao.save(requerimientoInforme);
        } catch (Exception ex) {
            logger.error("Error al guardar el informe. Contexto: {}, Entidad: {}", contexto, requerimientoInforme, ex);
            throw new RuntimeException("Error al guardar el informe", ex);
        }
    }
}
