package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;

@Repository
public interface InformeRenovacionDao extends JpaRepository<InformeRenovacion, Long> {
    @Query(value = "select i from InformeRenovacion i "
            + "join fetch i.requerimientoRenovacion c "
            + "join fetch c.solicitudPerfil s "
            + "join fetch s.propuesta p "
            + "join fetch p.supervisora su "
            + "where (:nuExpediente is null or lower(s.numeroExpediente) like lower(concat('%', :nuExpediente, '%'))) "
            + "and (:contratista is null or lower(su.nombreRazonSocial) like lower(concat('%', :contratista, '%')))",

            countQuery = "select count(i) from InformeRenovacion i "
                    + "inner join i.requerimientoRenovacion c "
                    + "inner join c.solicitudPerfil s "
                    + "inner join s.propuesta p "
                    + "inner join p.supervisora su "
                    + "where (:nuExpediente is null or lower(s.numeroExpediente) like lower(concat('%', :nuExpediente, '%'))) "
                    + "and (:contratista is null or lower(su.nombreRazonSocial) like lower(concat('%', :contratista, '%')))"
    )
    Page<InformeRenovacion> findByNuExpedienteContratistaEstado(String nuExpediente, String contratista, Pageable pageable);
}

