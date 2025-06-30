package pe.gob.osinergmin.sicoes.mapper;

import org.springframework.stereotype.Component;
import pe.gob.osinergmin.sicoes.model.Invitacion;
import pe.gob.osinergmin.sicoes.model.dto.InvitacionDTO;

@Component
public class InvitacionMapper {

    public InvitacionDTO toDTO(Invitacion entity) {
        if (entity == null) return null;

        InvitacionDTO dto = new InvitacionDTO();
        dto.setIdReqInvitacion(entity.getIdReqInvitacion());
        dto.setRequerimiento(entity.getRequerimiento());
        dto.setEstado(entity.getEstado());
        dto.setSupervisora(entity.getSupervisora());
        dto.setFeInvitacion(entity.getFeInvitacion()); // cuidado: en entidad es "feInivitacion"
        dto.setFeCaducidad(entity.getFeCaducidad());
        dto.setFeAceptacion(entity.getFeAceptacion());
        dto.setFeRechazo(entity.getFeRechazo());
        dto.setCaSaldoContrato(entity.getCaSaldoContrato());
        dto.setFlActivo(entity.getFlActivo());
        dto.setUsCreacion(entity.getUsuCreacion());
        dto.setIpCreacion(entity.getIpCreacion());
        dto.setFeCreacion(entity.getFecCreacion());
        dto.setUsActualizacion(entity.getUsuActualizacion());
        dto.setIpActualizacion(entity.getIpActualizacion());
        dto.setFeActualizacion(entity.getFecActualizacion());
        return dto;
    }

    public Invitacion toEntity(InvitacionDTO dto) {
        if (dto == null) return null;

        Invitacion entity = new Invitacion();
        entity.setIdReqInvitacion(dto.getIdReqInvitacion());
        entity.setRequerimiento(dto.getRequerimiento());
        entity.setEstado(dto.getEstado());
        entity.setSupervisora(dto.getSupervisora());
        entity.setFeInvitacion(dto.getFeInvitacion()); // mismo cuidado aquí
        entity.setFeCaducidad(dto.getFeCaducidad());
        entity.setFeAceptacion(dto.getFeAceptacion());
        entity.setFeRechazo(dto.getFeRechazo());
        entity.setCaSaldoContrato(dto.getCaSaldoContrato());
        entity.setFlActivo(dto.getFlActivo());
        entity.setUsuCreacion(dto.getUsCreacion());
        entity.setIpCreacion(dto.getIpCreacion());
        entity.setFecCreacion(dto.getFeCreacion());
        entity.setUsuActualizacion(dto.getUsActualizacion());
        entity.setIpActualizacion(dto.getIpActualizacion());
        entity.setFecActualizacion(dto.getFeActualizacion());
        return entity;
    }
}
