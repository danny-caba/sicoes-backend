package pe.gob.osinergmin.sicoes.service.impl;

import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.repository.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.service.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;

import java.util.Optional;

@Service
public class RequerimientoInvitacionServiceImpl implements RequerimientoInvitacionService {

    private static final Logger logger = LogManager.getLogger(RequerimientoInvitacionServiceImpl.class);

    @Autowired
    private RequerimientoInvitacionDao requerimientoInvitacionDao;

    @Override
    public RequerimientoInvitacion guardar(RequerimientoInvitacion requerimientoInvitacion, Contexto contexto) {
        try {
            // Validación rápida para evitar el error ORA-01400
            if (requerimientoInvitacion.getFlagActivo() == null) {
                requerimientoInvitacion.setFlagActivo("1"); // o el valor por defecto definido
            }

            AuditoriaUtil.setAuditoriaRegistro(requerimientoInvitacion, contexto);
            return requerimientoInvitacionDao.save(requerimientoInvitacion);
        } catch (Exception ex) {
            logger.error("Error al guardar la invitación. Contexto: {}, Entidad: {}", contexto, requerimientoInvitacion, ex);
            throw new RuntimeException("Error al guardar la invitación", ex);
        }
    }

    @Override
    public RequerimientoInvitacion eliminar(Long id, Contexto contexto) {
        Optional<RequerimientoInvitacion> optional = requerimientoInvitacionDao.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("RequerimientoInvitacion no encontrado con ID: " + id);
        }
        RequerimientoInvitacion entidad = optional.get();
        entidad.setFlagActivo("0");
        AuditoriaUtil.setAuditoriaActualizacion(entidad, contexto);
        return requerimientoInvitacionDao.save(entidad);
    }
}
