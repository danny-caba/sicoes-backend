package pe.gob.osinergmin.sicoes.repository.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.BandejaAprobacionFullDTO;

public interface RequerimientoAprobacionDaoCustom {
    
    Page<BandejaAprobacionFullDTO> buscarByIdUsuarioConFiltrosDinamicos(
            String numeroExpediente,
            Long estadoAprobacionInforme,
            Long idContratista,
            String nombreContratista,
            Integer idUsuario,
            Pageable pageable
    );
    
    Page<BandejaAprobacionFullDTO> buscarByIdUsuarioG2ConFiltrosDinamicos(
            String numeroExpediente,
            Long estadoAprobacionInforme,
            Long idContratista,
            String nombreContratista,
            Pageable pageable
    );
}