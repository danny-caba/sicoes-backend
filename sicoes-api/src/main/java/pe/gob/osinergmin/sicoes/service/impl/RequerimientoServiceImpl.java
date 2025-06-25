package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.service.RequerimientoService;

@Service
public class RequerimientoServiceImpl implements RequerimientoService {

    Logger logger = LogManager.getLogger(RequerimientoServiceImpl.class);
    @Autowired
    private RequerimientoDao requerimientoDao;
}
