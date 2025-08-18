package pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.UsuarioRCDTO;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface UsuarioMapper {
        UsuarioMapper MAPPER =
            Mappers.getMapper(UsuarioMapper.class);
       UsuarioRCDTO toDto(Usuario usuario);
}
