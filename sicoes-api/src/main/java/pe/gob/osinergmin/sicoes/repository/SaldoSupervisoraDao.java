package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.SaldoSupervisora;

import java.util.Optional;

@Repository
public interface SaldoSupervisoraDao extends JpaRepository<SaldoSupervisora, Long> {

    @Query("SELECT s FROM SaldoSupervisora s WHERE s.id = :id")
    Optional<SaldoSupervisora> buscarPorId(@Param("id") Long id);
}
