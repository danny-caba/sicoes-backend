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

        dto.setUsCreacion(entity.getUsuCreacion());
        dto.setIpCreacion(entity.getIpCreacion());
        dto.setFeCreacion(entity.getFecCreacion());

        dto.setUsActualizacion(entity.getUsuActualizacion());
        dto.setIpActualizacion(entity.getIpActualizacion());
        dto.setFeActualizacion(entity.getFecActualizacion());

        return dto;
    }

    public SaldoSupervisora toEntity(SaldoSupervisoraDTO dto) {
        SaldoSupervisora entity = new SaldoSupervisora();
        entity.setIdSaldoSupervisora(dto.getIdSaldoSupervisora());
        entity.setSupervisora(dto.getSupervisora());
        entity.setCantidad(dto.getCantidad());

        entity.setUsuCreacion(dto.getUsCreacion());
        entity.setIpCreacion(dto.getIpCreacion());
        entity.setFecCreacion(dto.getFeCreacion());

        entity.setUsuActualizacion(dto.getUsActualizacion());
        entity.setIpActualizacion(dto.getIpActualizacion());
        entity.setFecActualizacion(dto.getFeActualizacion());

        return entity;
    }
}
