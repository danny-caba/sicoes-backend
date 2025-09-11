package pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.*;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;


@Mapper(uses = {UsuarioMapper.class, SupervisoraMapper.class, SicoesSolicitudMapper.class, RequerimientoRenovacionMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface InformeRenovacionContratoMapper {

    InformeRenovacionContratoMapper MAPPER = Mappers.getMapper(InformeRenovacionContratoMapper.class);

    @Mapping(source = "requerimiento", target = "requerimiento")
    InformeRenovacionContratoDTO toDTO(InformeRenovacionContrato informeRenovacionContrato);

    InformeRenovacionContrato toEntity(InformeRenovacionContratoDTO informeRenovacionContrato);
}
