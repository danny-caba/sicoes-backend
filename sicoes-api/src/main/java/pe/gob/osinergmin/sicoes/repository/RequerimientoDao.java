package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.Requerimiento;

@Repository
public interface RequerimientoDao extends JpaRepository<Requerimiento, Long> {
    @Query(value="select r from Requerimiento r "
            + "left join fetch s.estado e "
            + "left join fetch s.division d "
            + "left join fetch s.perfil p "
            + "where r.idRequerimiento = :idRequerimiento ")
    Requerimiento obtener(Long idRequerimiento);
}
