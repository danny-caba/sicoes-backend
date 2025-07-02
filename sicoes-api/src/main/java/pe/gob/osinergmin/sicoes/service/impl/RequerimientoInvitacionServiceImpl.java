package pe.gob.osinergmin.sicoes.service.impl;

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
import pe.gob.osinergmin.sicoes.service.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import java.util.Date;

@Service
public class RequerimientoInvitacionServiceImpl implements RequerimientoInvitacionService {

    Logger logger = LogManager.getLogger(RequerimientoInvitacionServiceImpl.class);
    @Autowired
    private SupervisoraService supervisoraService;
    @Autowired
    private ListadoDetalleService listadoDetalleService;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private RequerimientoInvitacionDao invitacionDao;
    @Autowired
    private RequerimientoDao requerimientoDao;
    @Autowired
    private RequerimientoAprobacionDao aprobacionDao;

    @Override
    public RequerimientoInvitacion guardar(RequerimientoInvitacion model, Contexto contexto) {
        return null;
    }

    @Override
    public RequerimientoInvitacion obtener(Long supervisoraId, Contexto contexto) {
        return null;
    }

    @Override
    public Page<RequerimientoInvitacion> obtener(Long idEstado, String fechaInicioInvitacion,
                                                 String fechaFinInvitacion, Contexto contexto, Pageable pageable) {
        Date fechaInicio = DateUtil.getInitDay(fechaInicioInvitacion);
        Date fechaFin = DateUtil.getEndDay(fechaFinInvitacion);
        Supervisora supervisora = supervisoraService.obtenerSupervisoraPorRucPostorOrJuridica(contexto.getUsuario().getCodigoRuc());
        Long idSupervisora = supervisora.getIdSupervisora();
        //TODO: formatear saldo en tiempo y fecha de cancelacion (fecha invi +3 dias)
        return invitacionDao.obtenerInvitaciones(idSupervisora, idEstado, fechaInicio, fechaFin, pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Requerimiento evaluar(Long  id, ListadoDetalleDTO estado, Contexto contexto) {

        RequerimientoInvitacion invitacion = invitacionDao.obtener(id);
        if(!invitacion.getRequerimiento().getEstado().getCodigo()
                .equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_PROCESO)) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_EN_PROCESO);
        }
        boolean esAprobacion = false;
        ListadoDetalle estadoInvitacion = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_INVITACION.CODIGO, estado.getCodigo());
        Requerimiento requerimiento = requerimientoDao.obtener(invitacion.getRequerimiento().getIdRequerimiento());

        if(estadoInvitacion.getCodigo().equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQ_INVITACION.ACEPTADO)) {
            //update estado y fechas a Aceptado o Rechazado a la Invitacion
            invitacion.setEstado(estadoInvitacion);
            invitacion.setFechaAceptacion(new Date());
            AuditoriaUtil.setAuditoriaRegistro(invitacion, contexto);
            invitacion = invitacionDao.save(invitacion);

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
            aprobacion = aprobacionDao.save(aprobacion);

            //Flag notificacion aceptado
            esAprobacion = true;

            //Notificacion Asignacion Requerimiento
            notificacionService.enviarMensajeAsignacionRequerimiento(requerimiento, contexto);

        } else if(estadoInvitacion.getCodigo().equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQ_INVITACION.RECHAZADO)) {
            //update estado y fechas a Aceptado o Rechazado a la Invitacion
            invitacion.setEstado(estadoInvitacion);
            invitacion.setFechaRechazo(new Date());
            AuditoriaUtil.setAuditoriaRegistro(invitacion, contexto);
            invitacion = invitacionDao.save(invitacion);
        }

        //select req para el response

        //Enviar notificacion
        //notificacionService.enviarMensajeAprobacionRechazoReqInvitacion(invitacion, esAprobacion, contexto);
        return requerimiento;
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {

    }
}
