package pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.SicoesSolicitudRenovacionContratoDTO;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface SicoesSolicitudMapper {
    
    SicoesSolicitudMapper MAPPER =
            Mappers.getMapper(SicoesSolicitudMapper.class);

    SicoesSolicitudRenovacionContratoDTO toDto(SicoesSolicitud sicoesSolicitud);
}
