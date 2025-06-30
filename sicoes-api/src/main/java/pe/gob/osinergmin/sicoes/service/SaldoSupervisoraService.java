package pe.gob.osinergmin.sicoes.service;

import pe.gob.osinergmin.sicoes.model.dto.SaldoSupervisoraDTO;

import java.util.Optional;

public interface SaldoSupervisoraService {

    Optional<SaldoSupervisoraDTO> obtenerPorId(Long id);

}
