package pe.gob.osinergmin.sicoes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.gob.osinergmin.sicoes.model.InformeRenovacionContrato;


@Repository
public interface InformeRenovacionContratoDao extends JpaRepository<InformeRenovacionContrato, Long> {

    Page<InformeRenovacionContrato> listaInformes(String numeroExpediente, String estado, String nombreContratista);
    
}
