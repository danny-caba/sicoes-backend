package pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.SupervisoraRenovacionContratoDTO;
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring"
)
public interface SupervisoraMapper {
    SupervisoraMapper MAPPER =
            Mappers.getMapper(SupervisoraMapper.class);


    SupervisoraRenovacionContratoDTO toDto(Supervisora supervisora);

}
