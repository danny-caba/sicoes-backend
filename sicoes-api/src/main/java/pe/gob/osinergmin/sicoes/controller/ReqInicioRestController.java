package pe.gob.osinergmin.sicoes.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.model.ReqInicioServicio;
import pe.gob.osinergmin.sicoes.repository.ReqInicioServicioDao;

@RestController
@RequestMapping("/api/req-inicio-servicio")
public class ReqInicioRestController {

    private final ReqInicioServicioDao repo;

    public ReqInicioRestController(ReqInicioServicioDao repo) {
        this.repo = repo;
    }

    /** Upsert al subir/reemplazar archivo (sin tocar evaluación) */
    @PostMapping
    @Transactional
    public ReqInicioServicio upsert(@RequestBody ReqInicioServicio body) {
        Optional<ReqInicioServicio> existente = repo.findBySolicitudPerfilIdAndTipoDocumento(
                body.getSolicitudPerfilId(),
                body.getTipoDocumento()
        );
        ReqInicioServicio e = existente.orElse(new ReqInicioServicio());

        e.setSolicitudPerfilId(body.getSolicitudPerfilId());
        e.setSupervisoraId(body.getSupervisoraId());
        e.setTipoDocumento(body.getTipoDocumento());
        e.setArchivoId(body.getArchivoId());
        // dejamos los campos de evaluación en null
        e.setEstadoEvaluacion(null);
        e.setUsuarioId(null);
        e.setFechaEvaluacion(null);
        e.setObservacion(null);

        return repo.save(e);
    }

    /** Actualizar solo fechas de inicio y fin */
    @PutMapping("/{id}/fechas")
    @Transactional
    public ReqInicioServicio updateFechas(
            @PathVariable Long id,
            @RequestBody ReqInicioServicio body
    ) {
        ReqInicioServicio e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisito no encontrado id=" + id));
        e.setFechaInicio(body.getFechaInicio());
        e.setFechaFin(body.getFechaFin());
        return repo.save(e);
    }
    
    @GetMapping("/contrato/{soliPerfilId}")
    public List<ReqInicioServicio> listarPorContrato(@PathVariable Long soliPerfilId) {
        return repo.findAll().stream()
                   .filter(r -> r.getSolicitudPerfilId().equals(soliPerfilId))
                   .collect(Collectors.toList());
    }
}
