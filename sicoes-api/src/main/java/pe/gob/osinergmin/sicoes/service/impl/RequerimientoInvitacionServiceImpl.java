package pe.gob.osinergmin.sicoes.service.impl;

import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_FIN_ANTES_INICIO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_INICIO_ANTES_HOY;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.INVITACION_NO_ENCONTRADA;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.REQUERIMIENTO_NO_ENCONTRADO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import pe.gob.osinergmin.sicoes.repository.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.service.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class RequerimientoInvitacionServiceImpl implements RequerimientoInvitacionService {

    private static final Logger logger = LogManager.getLogger(RequerimientoInvitacionServiceImpl.class);

    @Autowired
    private SupervisoraService supervisoraService;

    @Autowired
    private ListadoDetalleService listadoDetalleService;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private RequerimientoInvitacionDao requerimientoInvitacionDao;

    @Autowired
    private RequerimientoDao requerimientoDao;

    @Autowired
    private RequerimientoAprobacionDao aprobacionDao;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public RequerimientoInvitacion guardar(RequerimientoInvitacion requerimientoInvitacion, Contexto contexto) {
        ListadoDetalle estadoArchivado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_INVITACION.CODIGO,
                Constantes.LISTADO.ESTADO_INVITACION.ARCHIVADO
        );
        if (estadoArchivado == null) {
            throw new ValidacionException("Estado ARCHIVADO no configurado en ListadoDetalle");
        }
        if (estadoArchivado.getCodigo().equals(requerimientoInvitacion.getEstado().getCodigo())) {
            throw new ValidacionException("No se puede archivar una invitación en estado ARCHIVADO");
        }
        ListadoDetalle estadoInvitado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_INVITACION.CODIGO,
                Constantes.LISTADO.ESTADO_INVITACION.INVITADO
        );
        if (estadoInvitado == null) {
            throw new ValidacionException("Estado INVITADO no configurado en ListadoDetalle");
        }
        requerimientoInvitacion.setEstado(estadoInvitado);
        Date fechaInvitacion = new Date();
        requerimientoInvitacion.setFechaInvitacion(fechaInvitacion);
        LocalDate fechaCaducidadLocal = fechaInvitacion.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .plusDays(3);
        Date fechaCaducidad = Date.from(fechaCaducidadLocal
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        requerimientoInvitacion.setFechaCaducidad(fechaCaducidad);
        AuditoriaUtil.setAuditoriaRegistro(requerimientoInvitacion, contexto);
        Usuario usuarioSupervisorPN = usuarioService.obtener(requerimientoInvitacion.getRequerimiento().getSupervisora().getIdSupervisora());
        notificacionService.enviarRequerimientoInvitacion(usuarioSupervisorPN, requerimientoInvitacion, contexto);
        return requerimientoInvitacionDao.save(requerimientoInvitacion);
    }

    @Override
    public RequerimientoInvitacion obtener(Long id, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long id, Contexto contexto) {
        logger.info("Eliminando RequerimientoInvitacion con ID {} - usuario: {}", id, contexto.getUsuario());
        Optional<RequerimientoInvitacion> optional = requerimientoInvitacionDao.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("RequerimientoInvitacion no encontrado con ID: " + id);
        }
        ListadoDetalle estadoEliminado = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REQ_INVITACION.CODIGO, Constantes.LISTADO.ESTADO_REQ_INVITACION.ELIMINADO);
        if (estadoEliminado == null) {
            throw new IllegalStateException("Estado ELIMINADO no configurado en ListadoDetalle");
        }
        RequerimientoInvitacion entidad = optional.get();
        entidad.setEstado(estadoEliminado);
        AuditoriaUtil.setAuditoriaActualizacion(entidad, contexto);
        requerimientoInvitacionDao.save(entidad);
    }

    @Override
    public Page<RequerimientoInvitacion> obtener(Long idEstado, String fechaInicioInvitacion,
                                                 String fechaFinInvitacion, Contexto contexto, Pageable pageable) {
        Long idSupervisora = null;
        Date fechaInicio = DateUtil.getInitDay(fechaInicioInvitacion);
        Date fechaFin = DateUtil.getEndDay(fechaFinInvitacion);
        if (fechaInicio != null && fechaInicio.after(new Date())) {
            throw new ValidacionException(ERROR_FECHA_INICIO_ANTES_HOY);
        }
        if (fechaInicio != null && fechaFin != null && fechaFin.before(fechaInicio)) {
            throw new ValidacionException(ERROR_FECHA_FIN_ANTES_INICIO);
        }

        if (contexto.getUsuario().getCodigoUsuarioInterno() == null) {
            Supervisora supervisora = supervisoraService.obtenerSupervisoraPorRucPostorOrJuridica(contexto.getUsuario().getCodigoRuc());
            idSupervisora = supervisora.getIdSupervisora();
        }
        return requerimientoInvitacionDao.obtenerInvitaciones(idSupervisora, idEstado, fechaInicio, fechaFin, pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Requerimiento evaluar(String  uuid, ListadoDetalleDTO estado, Contexto contexto) {
        RequerimientoInvitacion invitacion = requerimientoInvitacionDao.obtenerPorUuid(uuid)
                .orElseThrow(() -> new ValidacionException(INVITACION_NO_ENCONTRADA));
        if(!invitacion.getRequerimiento().getEstado().getCodigo()
                .equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_PROCESO)) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_EN_PROCESO);
        }
        boolean esAprobacion = false;
        ListadoDetalle estadoInvitacion = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_INVITACION.CODIGO, estado.getCodigo());
        Requerimiento requerimiento = requerimientoDao.obtener(invitacion.getRequerimiento().getIdRequerimiento())
                .orElseThrow(() -> new ValidacionException(REQUERIMIENTO_NO_ENCONTRADO));
        if(estadoInvitacion.getCodigo().equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQ_INVITACION.ACEPTADO)) {
            //update estado y fechas a Aceptado o Rechazado a la Invitacion
            invitacion.setEstado(estadoInvitacion);
            invitacion.setFechaAceptacion(new Date());
            AuditoriaUtil.setAuditoriaRegistro(invitacion, contexto);
            invitacion = requerimientoInvitacionDao.save(invitacion);

            //update estado a En Aprobacion al Requerimiento
            ListadoDetalle estadoRequerimiento = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO, Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_APROBACION);
            requerimiento.setEstado(estadoRequerimiento);
            AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
            requerimiento = requerimientoDao.save(requerimiento);

            //insert en req aprobacion
            ListadoDetalle tipoAprobacion = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.TIPO_APROBACION.CODIGO, Constantes.LISTADO.TIPO_APROBACION.APROBAR);
            ListadoDetalle grupoAprobacion = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.GRUPO_APROBACION.CODIGO, Constantes.LISTADO.GRUPO_APROBACION.GPPM);
            ListadoDetalle estadoAprobacion = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO);
            RequerimientoAprobacion aprobacion = new RequerimientoAprobacion();
            aprobacion.setRequerimiento(requerimiento);
            aprobacion.setTipo(tipoAprobacion);
            aprobacion.setGrupo(grupoAprobacion);
            aprobacion.setUsuario(contexto.getUsuario());
            aprobacion.setEstado(estadoAprobacion);
            AuditoriaUtil.setAuditoriaRegistro(aprobacion, contexto);
            aprobacionDao.save(aprobacion);

            //Flag notificacion aceptado
            esAprobacion = true;

            //Notificacion Asignacion Requerimiento
            notificacionService.enviarMensajeRequerimientoPorAprobar(requerimiento, contexto);

        } else if (estadoInvitacion.getCodigo().equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQ_INVITACION.RECHAZADO)) {
            //update estado y fechas a Aceptado o Rechazado a la Invitacion
            invitacion.setEstado(estadoInvitacion);
            invitacion.setFechaRechazo(new Date());
            AuditoriaUtil.setAuditoriaRegistro(invitacion, contexto);
            requerimientoInvitacionDao.save(invitacion);
        }

        //Enviar notificacion
        notificacionService.enviarMensajeAprobacionRechazoReqInvitacion(invitacion, esAprobacion, contexto);
        return requerimiento;
    }

}
