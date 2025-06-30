package pe.gob.osinergmin.sicoes.mapper;

import org.springframework.stereotype.Component;
import pe.gob.osinergmin.sicoes.model.SaldoSupervisora;
import pe.gob.osinergmin.sicoes.model.dto.SaldoSupervisoraDTO;

@Component
public class SaldoSupervisoraMapper {

    public SaldoSupervisoraDTO toDTO(SaldoSupervisora entity) {
        SaldoSupervisoraDTO dto = new SaldoSupervisoraDTO();
        dto.setIdSaldoSupervisora(entity.getIdSaldoSupervisora());
        dto.setSupervisora(entity.getSupervisora());
        dto.setCantidad(entity.getCantidad());

        dto.setUsCreacion(entity.getUsCreacion());
        dto.setIpCreacion(entity.getIpCreacion());
        dto.setFeCreacion(entity.getFeCreacion());

        dto.setUsActualizacion(entity.getUsActualizacion());
        dto.setIpActualizacion(entity.getIpActualizacion());
        dto.setFeActualizacion(entity.getFeActualizacion());

        return dto;
    }

    public SaldoSupervisora toEntity(SaldoSupervisoraDTO dto) {
        SaldoSupervisora entity = new SaldoSupervisora();
        entity.setIdSaldoSupervisora(dto.getIdSaldoSupervisora());
        entity.setSupervisora(dto.getSupervisora());
        entity.setCantidad(dto.getCantidad());

        entity.setUsCreacion(dto.getUsCreacion());
        entity.setIpCreacion(dto.getIpCreacion());
        entity.setFeCreacion(dto.getFeCreacion());

        entity.setUsActualizacion(dto.getUsActualizacion());
        entity.setIpActualizacion(dto.getIpActualizacion());
        entity.setFeActualizacion(dto.getFeActualizacion());

        return entity;
    }
}
