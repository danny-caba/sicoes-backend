package pe.gob.osinergmin.sicoes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.SaldoSupervisora;
import pe.gob.osinergmin.sicoes.repository.SaldoSupervisoraDao;
import pe.gob.osinergmin.sicoes.service.SaldoSupervisoraService;
import java.util.Optional;

@Service
public class SaldoSupervisoraServiceImpl implements SaldoSupervisoraService {

    @Autowired
    private SaldoSupervisoraDao saldoSupervisoraDao;

    @Override
    public Optional<SaldoSupervisora> obtenerPorSupervisoraId(Long id) {
        return saldoSupervisoraDao.buscarPorId(id);
    }

}
