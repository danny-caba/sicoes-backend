package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.repository.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.service.RequerimientoInvitacionService;

@Service
public class RequerimientoInvitacionServiceImpl implements RequerimientoInvitacionService {

    Logger logger = LogManager.getLogger(RequerimientoInvitacionServiceImpl.class);
    @Autowired
    private RequerimientoInvitacionDao requerimientoInvitacionDao;
}
