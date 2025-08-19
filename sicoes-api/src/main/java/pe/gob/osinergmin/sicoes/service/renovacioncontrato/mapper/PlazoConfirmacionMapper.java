package pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.PlazoConfirmacionRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.PlazoConfirmacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.PlazoConfirmacion;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface PlazoConfirmacionMapper {
    
    PlazoConfirmacionMapper MAPPER = Mappers.getMapper(PlazoConfirmacionMapper.class);

    @Mapping(source = "feBase", target = "fechaBase")
    @Mapping(source = "inTipoDia", target = "tipoDia")
    @Mapping(source = "nuDias", target = "numeroDias")
    @Mapping(source = "esRegistro", target = "estado")
    @Mapping(source = "fecCreacion", target = "fechaCreacion")
    @Mapping(source = "usuCreacion", target = "usuarioCreacion")
    @Mapping(source = "fecActualizacion", target = "fechaActualizacion")
    @Mapping(source = "usuActualizacion", target = "usuarioActualizacion")
    PlazoConfirmacionResponseDTO toResponseDTO(PlazoConfirmacion plazoConfirmacion);
    
    @Mapping(source = "fechaBase", target = "feBase")
    @Mapping(source = "tipoDia", target = "inTipoDia")
    @Mapping(source = "numeroDias", target = "nuDias")
    PlazoConfirmacion toEntity(PlazoConfirmacionRequestDTO requestDTO);
    
    List<PlazoConfirmacionResponseDTO> toResponseDTOList(List<PlazoConfirmacion> plazoConfirmacionList);
}
