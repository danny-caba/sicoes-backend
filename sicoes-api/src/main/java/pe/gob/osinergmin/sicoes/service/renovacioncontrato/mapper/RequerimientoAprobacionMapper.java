package pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoAprobacionDTO;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;

@Mapper(uses = {UsuarioMapper.class, SupervisoraMapper.class, SicoesSolicitudMapper.class, RequerimientoRenovacionMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequerimientoAprobacionMapper {

    RequerimientoAprobacionMapper MAPPER = Mappers.getMapper(RequerimientoAprobacionMapper.class);
    
    @Mapping(target = "grupoLd", ignore = true)
    @Mapping(target = "tipoAprobadorLd", ignore = true)
    @Mapping(target = "grupoAprobadorLd", ignore = true)
    RequerimientoAprobacionDTO toDTO(RequerimientoAprobacion informeRenovacionContrato);
}
