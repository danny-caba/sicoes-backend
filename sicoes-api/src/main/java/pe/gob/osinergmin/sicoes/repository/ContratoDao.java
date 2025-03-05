package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.gob.osinergmin.sicoes.model.Contrato;
import pe.gob.osinergmin.sicoes.model.SicoesTdSolPerConSec;

import java.util.List;

@Repository
public interface ContratoDao extends JpaRepository<Contrato, Long> {

    @Query(value = "select c from Contrato c "
            + "inner join fetch c.solicitudPerfCont s "
            + "inner join fetch s.propuesta p "
            + "inner join fetch p.supervisora su "
            + "inner join fetch p.procesoItem pi "
            + "inner join fetch pi.proceso pr "
            + "where (:expediente is null or pr.numeroExpediente like :expediente) "
            + "and (:contratista is null or su.nombreRazonSocial like :contratista) "
            + "and (:tipoContrato is null or c.estadoContrato = :tipoContrato) "
            + "and (:areaSolicitante is null or pr.nombreArea like :areaSolicitante) "
            + "order by c.idContrato",
            countQuery = "select count(c) from Contrato c "
                    + "inner join c.solicitudPerfCont s "
                    + "inner join s.propuesta p "
                    + "inner join p.supervisora su "
                    + "inner join p.procesoItem pi "
                    + "inner join pi.proceso pr "
                    + "where (:expediente is null or pr.numeroExpediente like :expediente) "
                    + "and (:contratista is null or su.nombreRazonSocial like :contratista) "
                    + "and (:tipoContrato is null or c.estadoContrato = :tipoContrato) "
                    + "and (:areaSolicitante is null or pr.nombreArea like :areaSolicitante) "
                    + "order by c.idContrato")
    Page<Contrato> obtenerContratos(String expediente, String contratista, String tipoContrato, String areaSolicitante, Pageable pageable);
 
}
