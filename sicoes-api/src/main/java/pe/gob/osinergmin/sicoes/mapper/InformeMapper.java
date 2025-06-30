package pe.gob.osinergmin.sicoes.mapper;

import org.springframework.stereotype.Component;
import pe.gob.osinergmin.sicoes.model.Informe;
import pe.gob.osinergmin.sicoes.model.dto.InformeDTO;

@Component
public class InformeMapper {

    public InformeDTO toDTO(Informe entity) {
        if (entity == null) return null;

        InformeDTO dto = new InformeDTO();
        dto.setIdReqInforme(entity.getIdReqInforme());
        dto.setRequerimiento(entity.getRequerimiento());
        dto.setUsCreacion(entity.getUsuCreacion());
        dto.setIpCreacion(entity.getIpCreacion());
        dto.setFeCreacion(entity.getFecCreacion());
        dto.setUsActualizacion(entity.getUsuActualizacion());
        dto.setIpActualizacion(entity.getIpActualizacion());
        dto.setFeActualizacion(entity.getFecActualizacion());

        return dto;
    }

    public Informe toEntity(InformeDTO dto) {
        if (dto == null) return null;

        Informe entity = new Informe();
        entity.setIdReqInforme(dto.getIdReqInforme());
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
