package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.SaldoSupervisora;
import pe.gob.osinergmin.sicoes.repository.SaldoSupervisoraDao;
import pe.gob.osinergmin.sicoes.service.SaldoSupervisoraService;
import java.util.Optional;

@Service
public class SaldoSupervisoraServiceImpl implements SaldoSupervisoraService {

    private static final Logger logger = LogManager.getLogger(SaldoSupervisoraServiceImpl.class);

    @Autowired
    private SaldoSupervisoraDao saldoSupervisoraDao;

    @Override
    public Optional<SaldoSupervisora> obtenerPorId(Long id) {
        return saldoSupervisoraDao.buscarPorId(id);
    }

}
