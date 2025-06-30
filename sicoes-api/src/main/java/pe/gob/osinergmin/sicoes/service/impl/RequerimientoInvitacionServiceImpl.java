package pe.gob.osinergmin.sicoes.service.impl;

import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.mapper.RequerimientoInvitacionMapper;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoInvitacionDTO;
import pe.gob.osinergmin.sicoes.repository.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.service.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;

@Service
public class RequerimientoInvitacionServiceImpl implements RequerimientoInvitacionService {

    private static final Logger logger = LogManager.getLogger(RequerimientoInvitacionServiceImpl.class);

    @Autowired
    private RequerimientoInvitacionDao requerimientoInvitacionDao;

    @Autowired
    private RequerimientoInvitacionMapper requerimientoInvitacionMapper;

    @Override
    public RequerimientoInvitacionDTO guardar(RequerimientoInvitacionDTO requerimientoInvitacionDTO, Contexto contexto) {
        try {
            RequerimientoInvitacion requerimientoInvitacion = requerimientoInvitacionMapper.toEntity(requerimientoInvitacionDTO);
            AuditoriaUtil.setAuditoriaRegistro(requerimientoInvitacion, contexto);
            RequerimientoInvitacion guardado = requerimientoInvitacionDao.save(requerimientoInvitacion);
            return requerimientoInvitacionMapper.toDTO(guardado);
        } catch (Exception ex) {
            logger.error("Error al guardar la invitación. Contexto: {}, DTO: {}", contexto, requerimientoInvitacionDTO, ex);
            throw new RuntimeException("Error al guardar la invitación", ex);
        }
    }

    @Override
    public RequerimientoInvitacionDTO eliminar(Long id, Contexto contexto) {
        return null;
    }
}
