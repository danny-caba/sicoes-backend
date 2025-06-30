package pe.gob.osinergmin.sicoes.mapper;

import org.springframework.stereotype.Component;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoDTO;

@Component
public class RequerimientoMapper {

    public RequerimientoDTO toDTO(Requerimiento r) {
        RequerimientoDTO dto = new RequerimientoDTO();
        dto.setIdRequerimiento(r.getIdRequerimiento());
        dto.setNuExpediente(r.getNuExpediente());

        dto.setEstado(r.getEstado());
        dto.setPerfil(r.getPerfil());
        dto.setDivision(r.getDivision());

        dto.setFeRegistro(r.getFeRegistro());
        dto.setFePlazoCargaDoc(r.getFePlazoCargaDoc());
        dto.setDeObservacion(r.getDeObservacion());
        dto.setNuSiaf(r.getNuSiaf());
        dto.setUsCreacion(r.getUsuCreacion());
        dto.setFeCreacion(r.getFecCreacion());
        dto.setUsActualizacion(r.getUsuActualizacion());
        dto.setFeActualizacion(r.getFecActualizacion());

        return dto;
    }

    public Requerimiento toEntity(RequerimientoDTO dto) {
        Requerimiento r = new Requerimiento();
        r.setIdRequerimiento(dto.getIdRequerimiento());
        r.setNuExpediente(dto.getNuExpediente());

        r.setEstado(dto.getEstado());
        r.setPerfil(dto.getPerfil());
        r.setDivision(dto.getDivision());

        r.setFeRegistro(dto.getFeRegistro());
        r.setFePlazoCargaDoc(dto.getFePlazoCargaDoc());
        r.setDeObservacion(dto.getDeObservacion());
        r.setNuSiaf(dto.getNuSiaf());
        r.setUsuCreacion(dto.getUsCreacion());
        r.setFecCreacion(dto.getFeCreacion());
        r.setUsuActualizacion(dto.getUsActualizacion());
        r.setFecActualizacion(dto.getFeActualizacion());

        return r;
    }
}
