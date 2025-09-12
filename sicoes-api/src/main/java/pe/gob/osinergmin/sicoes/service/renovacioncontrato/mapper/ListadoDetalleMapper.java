package pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper;

import org.springframework.stereotype.Component;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;

@Component
public class ListadoDetalleMapper {

    /**
     * Convierte una entidad ListadoDetalle a ListadoDetalleDTO
     * @param entity La entidad a convertir
     * @return ListadoDetalleDTO o null si la entidad es null
     */
    public ListadoDetalleDTO toDto(ListadoDetalle entity) {
        if (entity == null) {
            return null;
        }
        
        ListadoDetalleDTO dto = new ListadoDetalleDTO();
        dto.setIdListadoDetalle(entity.getIdListadoDetalle());
        dto.setCodigo(entity.getCodigo());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    /**
     * Convierte un Optional<ListadoDetalle> a ListadoDetalleDTO
     * @param optionalEntity El Optional que contiene la entidad a convertir
     * @return ListadoDetalleDTO o null si el Optional está vacío
     */
    public ListadoDetalleDTO toDto(java.util.Optional<ListadoDetalle> optionalEntity) {
        return optionalEntity.map(this::toDto).orElse(null);
    }
}
