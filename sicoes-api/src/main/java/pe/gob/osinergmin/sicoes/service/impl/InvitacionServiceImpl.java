package pe.gob.osinergmin.sicoes.service.impl;

import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.mapper.InvitacionMapper;
import pe.gob.osinergmin.sicoes.model.dto.InvitacionDTO;
import pe.gob.osinergmin.sicoes.repository.InvitacionDao;
import pe.gob.osinergmin.sicoes.service.InvitacionService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;

@Service
public class InvitacionServiceImpl implements InvitacionService {

    private static final Logger logger = LogManager.getLogger(InvitacionServiceImpl.class);

    @Autowired
    private InvitacionDao invitacionDao;

    @Autowired
    private InvitacionMapper invitacionMapper;

    @Override
    public InvitacionDTO guardar(InvitacionDTO invitacionDTO, Contexto contexto) {
        try {
            RequerimientoInvitacion entidad = invitacionMapper.toEntity(invitacionDTO);
            AuditoriaUtil.setAuditoriaRegistro(entidad, contexto);
            RequerimientoInvitacion guardado = invitacionDao.save(entidad);
            return invitacionMapper.toDTO(guardado);
        } catch (Exception ex) {
            logger.error("Error al guardar la invitación. Contexto: {}, DTO: {}", contexto, invitacionDTO, ex);
            throw new RuntimeException("Error al guardar la invitación", ex);
        }
    }

    @Override
    public InvitacionDTO eliminar(Long id, Contexto contexto) {
        return null;
    }
}
