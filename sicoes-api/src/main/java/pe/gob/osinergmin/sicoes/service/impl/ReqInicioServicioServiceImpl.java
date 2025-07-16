package pe.gob.osinergmin.sicoes.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.ReqInicioServicio;
import pe.gob.osinergmin.sicoes.repository.ReqInicioServicioDao;

@Service
public class ReqInicioServicioServiceImpl {

    private final ReqInicioServicioDao repo;

    public ReqInicioServicioServiceImpl(ReqInicioServicioDao repo) {
        this.repo = repo;
    }

    @Transactional
    public ReqInicioServicio upsert(ReqInicioServicio body) {
        Optional<ReqInicioServicio> existente = repo.findBySolicitudPerfilIdAndTipoDocumento(
                body.getSolicitudPerfilId(),
                body.getTipoDocumento()
        );
        ReqInicioServicio e = existente.orElse(new ReqInicioServicio());

        e.setSolicitudPerfilId(body.getSolicitudPerfilId());
        e.setSupervisoraId(body.getSupervisoraId());
        e.setTipoDocumento(body.getTipoDocumento());
        e.setArchivoId(body.getArchivoId());
        e.setEstadoEvaluacion(null);
        e.setUsuarioId(null);
        e.setFechaEvaluacion(null);
        e.setObservacion(null);

        return repo.save(e);
    }

    @Transactional
    public ReqInicioServicio updateFechas(Long id, ReqInicioServicio body) {
        ReqInicioServicio e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisito no encontrado id=" + id));
        e.setFechaInicio(body.getFechaInicio());
        e.setFechaFin(body.getFechaFin());
        return repo.save(e);
    }
}
