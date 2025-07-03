package pe.gob.osinergmin.sicoes.service.impl;

import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_FIN_ANTES_INICIO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_INICIO_ANTES_HOY;

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
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import pe.gob.osinergmin.sicoes.repository.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.service.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

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

    @Override
    public RequerimientoInvitacion guardar(RequerimientoInvitacion requerimientoInvitacion, Contexto contexto) {
        try {
            if (requerimientoInvitacion.getFlagActivo() == null) {
                requerimientoInvitacion.setFlagActivo(Constantes.ESTADO.ACTIVO); // o el valor por defecto definido
            }

            AuditoriaUtil.setAuditoriaRegistro(requerimientoInvitacion, contexto);
            return requerimientoInvitacionDao.save(requerimientoInvitacion);
        } catch (Exception ex) {
            logger.error("Error al guardar la invitación. Contexto: {}, Entidad: {}", contexto, requerimientoInvitacion, ex);
            throw new RuntimeException("Error al guardar la invitación", ex);
        }
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
        Date fechaInicio = DateUtil.getInitDay(fechaInicioInvitacion);
        Date fechaFin = DateUtil.getEndDay(fechaFinInvitacion);
        if (fechaInicio != null && fechaInicio.after(new Date())) {
            throw new ValidacionException(ERROR_FECHA_INICIO_ANTES_HOY);
        }
        if (fechaInicio != null && fechaFin != null && fechaFin.before(fechaInicio)) {
            throw new ValidacionException(ERROR_FECHA_FIN_ANTES_INICIO);
        }
        Supervisora supervisora = supervisoraService.obtenerSupervisoraPorRucPostorOrJuridica(contexto.getUsuario().getCodigoRuc());
        Long idSupervisora = supervisora.getIdSupervisora();
        return requerimientoInvitacionDao.obtenerInvitaciones(idSupervisora, idEstado, fechaInicio, fechaFin, pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Requerimiento evaluar(Long  id, ListadoDetalleDTO estado, Contexto contexto) {

        RequerimientoInvitacion invitacion = requerimientoInvitacionDao.obtener(id);
        if(!invitacion.getRequerimiento().getEstado().getCodigo()
                .equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_PROCESO)) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_EN_PROCESO);
        }
        boolean esAprobacion = false;
        ListadoDetalle estadoInvitacion = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_INVITACION.CODIGO, estado.getCodigo());
        Requerimiento requerimiento = requerimientoDao.obtener(invitacion.getRequerimiento().getIdRequerimiento())
                .orElseThrow(() -> new RuntimeException("Requerimiento no encontrado con ID: " + id));
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
                    Constantes.LISTADO.ESTADO_TIPO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_TIPO_APROBACION.APROBAR);
            ListadoDetalle grupoAprobacion = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_GRUPO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_GRUPO_APROBACION.GPPM);
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
            //notificacionService.enviarMensajeAsignacionRequerimiento(requerimiento, contexto);

        } else if(estadoInvitacion.getCodigo().equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQ_INVITACION.RECHAZADO)) {
            //update estado y fechas a Aceptado o Rechazado a la Invitacion
            invitacion.setEstado(estadoInvitacion);
            invitacion.setFechaRechazo(new Date());
            AuditoriaUtil.setAuditoriaRegistro(invitacion, contexto);
            requerimientoInvitacionDao.save(invitacion);
        }

        //select req para el response

        //Enviar notificacion
        //notificacionService.enviarMensajeAprobacionRechazoReqInvitacion(invitacion, esAprobacion, contexto);
        return requerimiento;
    }

}
