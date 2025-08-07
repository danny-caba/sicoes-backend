package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.SaldoSupervisora;

import java.util.Optional;

public interface SaldoSupervisoraService {

    Optional<SaldoSupervisora> obtenerPorSupervisoraId(Long id);

}
