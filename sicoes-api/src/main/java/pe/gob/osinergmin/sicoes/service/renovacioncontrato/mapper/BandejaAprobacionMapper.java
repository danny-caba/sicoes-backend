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

@Component
public class BandejaAprobacionMapper {

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
        dto.setNumeroExpediente(entity.getInformeRenovacion().getRequerimientoRenovacion().getNuExpediente());

        // informe
        dto.setInforme(entity.getInformeRenovacion().getDeNombreArchivo());

        // tp
        dto.setTp("PJ");

        // contratista
        dto.setContratista(entity.getInformeRenovacion().getRequerimientoRenovacion().getSolicitudPerfil().getSupervisora().getNombreRazonSocial());

        // tipoContrato
        dto.setTipoContrato("Renovación");

        // fechaIngreso
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaIngreso = entity.getInformeRenovacion().getFecCreacion() != null ?
                sdf.format(entity.getInformeRenovacion().getFecCreacion()) : "";
        dto.setFechaIngresoInforme(fechaIngreso);

        // estadoAprobacion
        String estadoAprobacion = entity.getInformeRenovacion().getEstadoAprobacionInforme() != null ?
                entity.getInformeRenovacion().getEstadoAprobacionInforme().getNombre() : "";
        dto.setEstadoAprobacionInforme(estadoAprobacion);

        // Estado Aprobación Jefe División
        ListadoDetalle jefeDivisionG1GrupoAprobadorLD = listadoDetalleService.obtenerListadoDetalle(
            "GRUPO_APROBACION",
            "JEFE_UNIDAD"
        );
        String jefeDivision = "";
        java.util.List<RequerimientoAprobacion> jefeDivisionList = requerimientoAprobacionDao.findByIdInformeRenovacionAndIdGrupoAprobadorLd(
            entity.getInformeRenovacion().getIdInformeRenovacion(),
            jefeDivisionG1GrupoAprobadorLD.getIdListadoDetalle()
        );
        if (jefeDivisionList != null && !jefeDivisionList.isEmpty()) {
            Long estadoLd = jefeDivisionList.get(0).getIdEstadoLd();
            ListadoDetalle detalle = listadoDetalleService.obtener(estadoLd, contexto);
            jefeDivision = detalle != null ? detalle.getNombre() : "";
        }
        dto.setEstadoAprobacionJefeDivision(jefeDivision);

        // Estado Aprobación Gerente División
        ListadoDetalle gerenteUnidadG2GrupoAprobadorLD = listadoDetalleService.obtenerListadoDetalle(
            "GRUPO_APROBACION",
            "GERENTE"
        );
        String gerenteDivision = "";
        java.util.List<RequerimientoAprobacion> gerenteDivisionList = requerimientoAprobacionDao.findByIdInformeRenovacionAndIdGrupoAprobadorLd(
            entity.getInformeRenovacion().getIdInformeRenovacion(),
            gerenteUnidadG2GrupoAprobadorLD.getIdListadoDetalle()
        );
        if (gerenteDivisionList != null && !gerenteDivisionList.isEmpty()) {
            Long estadoLd = gerenteDivisionList.get(0).getIdEstadoLd();
            ListadoDetalle detalle = listadoDetalleService.obtener(estadoLd, contexto);
            gerenteDivision = detalle != null ? detalle.getNombre() : "";
        }
        dto.setEstadoAprobacionGerenteDivision(gerenteDivision);

        // Estado Aprobación GPPM
        ListadoDetalle gppmG3GrupoAprobadorLD = listadoDetalleService.obtenerListadoDetalle(
            "GRUPO_APROBACION",
            "GPPM"
        );
        String gppm = "";
        java.util.List<RequerimientoAprobacion> gppmList = requerimientoAprobacionDao.findByIdInformeRenovacionAndIdGrupoAprobadorLd(
            entity.getInformeRenovacion().getIdInformeRenovacion(),
            gppmG3GrupoAprobadorLD.getIdListadoDetalle()
        );
        if (gppmList != null && !gppmList.isEmpty()) {
            Long estadoLd = gppmList.get(0).getIdEstadoLd();
            ListadoDetalle detalle = listadoDetalleService.obtener(estadoLd, contexto);
            gppm = detalle != null ? detalle.getNombre() : "";
        }
        dto.setEstadoAprobacionGPPM(gppm);

        // Estado Aprobación GSE
        ListadoDetalle gseG3GrupoAprobadorLD = listadoDetalleService.obtenerListadoDetalle(
            "GRUPO_APROBACION",
            "GSE"
        );
        String gse = "";
        java.util.List<RequerimientoAprobacion> gseList = requerimientoAprobacionDao.findByIdInformeRenovacionAndIdGrupoAprobadorLd(
            entity.getInformeRenovacion().getIdInformeRenovacion(),
            gseG3GrupoAprobadorLD.getIdListadoDetalle()
        );
        if (gseList != null && !gseList.isEmpty()) {
            Long estadoLd = gseList.get(0).getIdEstadoLd();
            ListadoDetalle detalle = listadoDetalleService.obtener(estadoLd, contexto);
            gse = detalle != null ? detalle.getNombre() : "";
        }
        dto.setEstadoAprobacionGSE(gse);

        // Asignar tipoAprobacionLd
        java.util.Optional.ofNullable(entity.getIdTipoAprobadorLd())
            .ifPresent(id -> dto.setTipoAprobacionLd(
            listadoDetalleMapper.toDto(
                listadoDetalleDao.findById(entity.getIdTipoLd())
            )
            ));

        // Asignar estadoLd
        java.util.Optional.ofNullable(entity.getIdEstadoLd())
            .ifPresent(id -> dto.setEstadoLd(
            listadoDetalleMapper.toDto(
                listadoDetalleDao.findById(entity.getIdEstadoLd())
            )
            ));

        // Asignar grupoAprobadorLd
        java.util.Optional.ofNullable(entity.getIdGrupoAprobadorLd())
            .ifPresent(id -> dto.setGrupoAprobadorLd(
            listadoDetalleMapper.toDto(
                listadoDetalleDao.findById(entity.getIdGrupoAprobadorLd())
            )
            ));

        return dto;
    }
}
