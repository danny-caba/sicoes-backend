package pe.gob.osinergmin.sicoes.mapper;

import org.springframework.stereotype.Component;
import pe.gob.osinergmin.sicoes.model.RequerimientoInforme;
import pe.gob.osinergmin.sicoes.model.dto.InformeDTO;

@Component
public class InformeMapper {

    public InformeDTO toDTO(RequerimientoInforme entity) {
        if (entity == null) return null;

        InformeDTO dto = new InformeDTO();
        dto.setIdReqInforme(entity.getIdRequerimientoInforme());
        dto.setRequerimiento(entity.getRequerimiento());
        dto.setUsCreacion(entity.getUsuCreacion());
        dto.setIpCreacion(entity.getIpCreacion());
        dto.setFeCreacion(entity.getFecCreacion());
        dto.setUsActualizacion(entity.getUsuActualizacion());
        dto.setIpActualizacion(entity.getIpActualizacion());
        dto.setFeActualizacion(entity.getFecActualizacion());

        return dto;
    }

    public RequerimientoInforme toEntity(InformeDTO dto) {
        if (dto == null) return null;

        RequerimientoInforme entity = new RequerimientoInforme();
        entity.setIdRequerimientoInforme(dto.getIdReqInforme());
        entity.setRequerimiento(dto.getRequerimiento());
        entity.setUsuCreacion(dto.getUsCreacion());
        entity.setIpCreacion(dto.getIpCreacion());
        entity.setFecCreacion(dto.getFeCreacion());
        entity.setUsuActualizacion(dto.getUsActualizacion());
        entity.setIpActualizacion(dto.getIpActualizacion());
        entity.setFecActualizacion(dto.getFeActualizacion());

        return entity;
    }
}
