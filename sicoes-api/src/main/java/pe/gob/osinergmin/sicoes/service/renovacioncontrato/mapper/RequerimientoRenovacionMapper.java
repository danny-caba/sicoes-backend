package pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoRenovacionDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface RequerimientoRenovacionMapper {
    
        
     RequerimientoRenovacionMapper MAPPER =
            Mappers.getMapper(RequerimientoRenovacionMapper.class);

    RequerimientoRenovacionDTO toDto(RequerimientoRenovacion requerimientoRenovacion);
}
