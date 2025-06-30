package pe.gob.osinergmin.sicoes.mapper;

import org.springframework.stereotype.Component;
import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.dto.InvitacionDTO;

@Component
public class InvitacionMapper {

    public InvitacionDTO toDTO(RequerimientoInvitacion entity) {
        if (entity == null) return null;

        InvitacionDTO dto = new InvitacionDTO();
        dto.setIdReqInvitacion(entity.getIdRequerimientoInvitacion());
        dto.setRequerimiento(entity.getRequerimiento());
        dto.setEstado(entity.getEstado());
        dto.setSupervisora(entity.getSupervisora());
        dto.setFeInvitacion(entity.getFechaInvitacion()); // cuidado: en entidad es "feInivitacion"
        dto.setFeCaducidad(entity.getFechaCaducidad());
        dto.setFeAceptacion(entity.getFechaAceptacion());
        dto.setFeRechazo(entity.getFechaRechazo());
        dto.setCaSaldoContrato(entity.getSaldoContrato());
        dto.setFlActivo(entity.getFlagActivo());
        dto.setUsCreacion(entity.getUsuCreacion());
        dto.setIpCreacion(entity.getIpCreacion());
        dto.setFeCreacion(entity.getFecCreacion());
        dto.setUsActualizacion(entity.getUsuActualizacion());
        dto.setIpActualizacion(entity.getIpActualizacion());
        dto.setFeActualizacion(entity.getFecActualizacion());
        return dto;
    }

    public RequerimientoInvitacion toEntity(InvitacionDTO dto) {
        if (dto == null) return null;

        RequerimientoInvitacion entity = new RequerimientoInvitacion();
        entity.setIdRequerimientoInvitacion(dto.getIdReqInvitacion());
        entity.setRequerimiento(dto.getRequerimiento());
        entity.setEstado(dto.getEstado());
        entity.setSupervisora(dto.getSupervisora());
        entity.setFechaInvitacion(dto.getFeInvitacion()); // mismo cuidado aquí
        entity.setFechaCaducidad(dto.getFeCaducidad());
        entity.setFechaAceptacion(dto.getFeAceptacion());
        entity.setFechaRechazo(dto.getFeRechazo());
        entity.setSaldoContrato(dto.getCaSaldoContrato());
        entity.setFlagActivo(dto.getFlActivo());
        entity.setUsuCreacion(dto.getUsCreacion());
        entity.setIpCreacion(dto.getIpCreacion());
        entity.setFecCreacion(dto.getFeCreacion());
        entity.setUsuActualizacion(dto.getUsActualizacion());
        entity.setIpActualizacion(dto.getIpActualizacion());
        entity.setFecActualizacion(dto.getFeActualizacion());
        return entity;
    }
}
