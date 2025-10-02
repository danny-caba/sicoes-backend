package pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.BandejaAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.text.SimpleDateFormat;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class BandejaAprobacionMapper {

    private static final Logger logger = LogManager.getLogger(BandejaAprobacionMapper.class);

    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Autowired
    private ListadoDetalleDao listadoDetalleDao;

    @Autowired
    private ListadoDetalleMapper listadoDetalleMapper;

    public BandejaAprobacionResponseDTO toDto(RequerimientoAprobacion entity, Contexto contexto, ListadoDetalleService listadoDetalleService) {


        BandejaAprobacionResponseDTO dto = new BandejaAprobacionResponseDTO();


        dto.setIdRequermientoAprobacion(entity.getIdReqAprobacion());
        dto.setIdInformeRenovacion(entity.getIdInformeRenovacion());

        // tipoAprobacion
        String tipoAprobacion = listadoDetalleService.obtener(entity.getIdTipoLd(), contexto) != null ?
                listadoDetalleService.obtener(entity.getIdTipoLd(), contexto).getNombre() : "";
        dto.setTipoAprobacion(tipoAprobacion);

        // numeroExpediente
        String numeroExpediente = "";
        try {
            if (entity.getInformeRenovacion() != null &&
                entity.getInformeRenovacion().getRequerimientoRenovacion() != null) {
                numeroExpediente = entity.getInformeRenovacion().getRequerimientoRenovacion().getNuExpediente();
            }
        } catch (Exception e) {
            // Si falla el acceso lazy, dejar vacío
            numeroExpediente = "";
        }
        dto.setNumeroExpediente(numeroExpediente);

        // informe - usar nombre del archivo si está disponible
        String informe = "";
        if (entity.getInformeRenovacion() != null) {
            informe = entity.getInformeRenovacion().getDeNombreArchivo() != null ?
                    entity.getInformeRenovacion().getDeNombreArchivo() : 
                    "INF-" + entity.getInformeRenovacion().getIdInformeRenovacion();
        }
        dto.setInforme(informe);

        // tp - tipo persona
        String tp = "PJ"; // Por defecto PJ
        try {
            if (entity.getInformeRenovacion() != null &&
                entity.getInformeRenovacion().getRequerimientoRenovacion() != null &&
                entity.getInformeRenovacion().getRequerimientoRenovacion().getSolicitudPerfil() != null &&
                entity.getInformeRenovacion().getRequerimientoRenovacion().getSolicitudPerfil().getSupervisora() != null) {
                // Aquí deberíamos obtener el código del tipo de persona del listado detalle
                // Por ahora usar PJ/PN basado en algún criterio
                tp = "PJ";
            }
        } catch (Exception e) {
            // Si falla el acceso lazy, dejar PJ por defecto
        }
        dto.setTp(tp);

        // contratista
        String contratista = "";
        try {
            if (entity.getInformeRenovacion() != null &&
                entity.getInformeRenovacion().getRequerimientoRenovacion() != null &&
                entity.getInformeRenovacion().getRequerimientoRenovacion().getSolicitudPerfil() != null &&
                entity.getInformeRenovacion().getRequerimientoRenovacion().getSolicitudPerfil().getSupervisora() != null) {
                contratista = entity.getInformeRenovacion().getRequerimientoRenovacion()
                        .getSolicitudPerfil().getSupervisora().getNombreRazonSocial();
            }
        } catch (Exception e) {
            // Si falla el acceso lazy, dejar vacío
        }
        dto.setContratista(contratista);

        // tipoContrato - basado en el tipo de solicitud
        String tipoContrato = "Renovación"; // Por defecto
        try {
            if (entity.getInformeRenovacion() != null &&
                entity.getInformeRenovacion().getRequerimientoRenovacion() != null &&
                entity.getInformeRenovacion().getRequerimientoRenovacion().getSolicitudPerfil() != null &&
                entity.getInformeRenovacion().getRequerimientoRenovacion().getSolicitudPerfil().getTipoSolicitud() != null) {
                // Aquí deberíamos mapear el TI_SOLICITUD al tipo de contrato
                // Por ahora mantener "Renovación" como default
                tipoContrato = "Renovación";
            }
        } catch (Exception e) {
            // Si falla el acceso lazy, dejar default
        }
        dto.setTipoContrato(tipoContrato);

        // fechaIngreso - usar fecha de creación del informe si está disponible
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaIngreso = "";
        if (entity.getInformeRenovacion() != null && entity.getInformeRenovacion().getFecCreacion() != null) {
            fechaIngreso = sdf.format(entity.getInformeRenovacion().getFecCreacion());
        } else if (entity.getFecCreacion() != null) {
            fechaIngreso = sdf.format(entity.getFecCreacion());
        }
        dto.setFechaIngresoInforme(fechaIngreso);

        // estadoAprobacionInforme
        String estadoAprobacionInforme = "";
        if (entity.getInformeRenovacion() != null && entity.getInformeRenovacion().getEsAprobacionInforme() != null) {
            Long esAprobacion = entity.getInformeRenovacion().getEsAprobacionInforme();
            java.util.Optional<ListadoDetalle> estadoOpt = listadoDetalleDao.findById(esAprobacion);
            if (estadoOpt.isPresent()) {
                estadoAprobacionInforme = estadoOpt.get().getNombre();
            }
        }
        dto.setEstadoAprobacionInforme(estadoAprobacionInforme);

        // Estados de aprobación por nivel jerárquico
        String estadoAprobacionJefe = "";
        String estadoAprobacionGerente = "";
        String estadoAprobacionGPPM = "";
        String estadoAprobacionGSE = "";

        // Obtener todos los requerimientos de aprobación para este informe
        if (entity.getIdInformeRenovacion() != null) {
            java.util.List<RequerimientoAprobacion> todasAprobaciones = 
                requerimientoAprobacionDao.findByIdInformeRenovacion(entity.getIdInformeRenovacion());

            for (RequerimientoAprobacion aprobacion : todasAprobaciones) {
                if (aprobacion.getIdGrupoAprobadorLd() != null && aprobacion.getEstado() != null) {
                    java.util.Optional<ListadoDetalle> estadoOpt = listadoDetalleDao.findById(aprobacion.getEstado().getIdListadoDetalle());
                    String nombreEstado = estadoOpt.isPresent() ? estadoOpt.get().getNombre() : "";

                    // Mapear por grupo aprobador: 954 = JEFE_UNIDAD (G1), 955 = GERENTE (G2), etc.
                    if (aprobacion.getIdGrupoAprobadorLd().equals(954L)) {
                        estadoAprobacionJefe = nombreEstado;
                    } else if (aprobacion.getIdGrupoAprobadorLd().equals(955L)) {
                        estadoAprobacionGerente = nombreEstado;
                    }
                    // Agregar más grupos si es necesario (GPPM, GSE)
                }
            }
        }

        // Si no se encontró estado específico, usar el estado actual del registro
        if (estadoAprobacionJefe.isEmpty() && entity.getIdGrupoAprobadorLd() != null && entity.getIdGrupoAprobadorLd().equals(954L)) {
            java.util.Optional<ListadoDetalle> estadoOpt = listadoDetalleDao.findById(entity.getEstado().getIdListadoDetalle());
            estadoAprobacionJefe = estadoOpt.isPresent() ? estadoOpt.get().getNombre() : "";
        }
        if (estadoAprobacionGerente.isEmpty() && entity.getIdGrupoAprobadorLd() != null && entity.getIdGrupoAprobadorLd().equals(955L)) {
            java.util.Optional<ListadoDetalle> estadoOpt = listadoDetalleDao.findById(entity.getEstado().getIdListadoDetalle());
            estadoAprobacionGerente = estadoOpt.isPresent() ? estadoOpt.get().getNombre() : "";
        }

        dto.setEstadoAprobacionJefeDivision(estadoAprobacionJefe);
        dto.setEstadoAprobacionGerenteDivision(estadoAprobacionGerente);
        dto.setEstadoAprobacionGPPM(estadoAprobacionGPPM);
        dto.setEstadoAprobacionGSE(estadoAprobacionGSE);

        // DEBUG: Log para ver qué estados se están enviando
        logger.warn("DEBUG MAPPER - Registro ID: {}, InformeID: {}", 
            entity.getIdReqAprobacion(), entity.getIdInformeRenovacion());
        logger.warn("  - estadoAprobacionJefeDivision: '{}'", estadoAprobacionJefe);
        logger.warn("  - estadoAprobacionGerenteDivision: '{}'", estadoAprobacionGerente);
        logger.warn("  - grupoAprobadorLd actual: {}", entity.getIdGrupoAprobadorLd());
        logger.warn("  - estadoLd actual: {}", entity.getEstado().getIdListadoDetalle());

        // tipoAprobacionLd
        if (entity.getIdTipoLd() != null) {
            listadoDetalleDao.findById(entity.getIdTipoLd())
                .ifPresent(tipoLd -> dto.setTipoAprobacionLd(listadoDetalleMapper.toDto(tipoLd)));
        }

        // estadoLd
        if (entity.getEstado() != null) {
            listadoDetalleDao.findById(entity.getEstado().getIdListadoDetalle())
                .ifPresent(estadoLd -> dto.setEstadoLd(listadoDetalleMapper.toDto(estadoLd)));
        }

        // grupoAprobadorLd
        if (entity.getIdGrupoAprobadorLd() != null) {
            listadoDetalleDao.findById(entity.getIdGrupoAprobadorLd())
                .ifPresent(grupoLd -> dto.setGrupoAprobadorLd(listadoDetalleMapper.toDto(grupoLd)));
        }

        // UUID Informe Renovación - usar deUuidInfoRenovacion si existe
        String uuidInforme = "";
        if (entity.getInformeRenovacion() != null) {
            // Usar el UUID del informe si está disponible
            uuidInforme = entity.getInformeRenovacion().getDeUuidInfoRenovacion() != null ?
                    entity.getInformeRenovacion().getDeUuidInfoRenovacion() : "";
        }
        dto.setUuidInformeRenovacion(uuidInforme);

        return dto;

    }
}
