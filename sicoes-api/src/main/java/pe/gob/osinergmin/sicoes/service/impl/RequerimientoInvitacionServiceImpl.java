package pe.gob.osinergmin.sicoes.service.impl;

import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ACCESO_NO_AUTORIZADO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_FIN_ANTES_INICIO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_INICIO_ANTES_HOY;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.INVITACION_NO_ENCONTRADA;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.Rol;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.UsuarioRol;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import pe.gob.osinergmin.sicoes.repository.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.RolService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.service.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.service.UsuarioRolService;
import pe.gob.osinergmin.sicoes.service.UsuarioService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioRolService usuarioRolService;

    @Autowired
    private SigedApiConsumer sigedApiConsumer;

    @Override
    @Transactional
    public RequerimientoInvitacion guardar(RequerimientoInvitacion requerimientoInvitacion, Contexto contexto) {
        ListadoDetalle estadoInvitado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_INVITACION.CODIGO,
                Constantes.LISTADO.ESTADO_REQ_INVITACION.INVITADO
        );
        requerimientoInvitacion.setEstado(estadoInvitado);
        Date fechaInvitacion = new Date();
        requerimientoInvitacion.setFechaInvitacion(fechaInvitacion);
        Date fechaCaducidad = sigedApiConsumer.calcularFechaFin(fechaInvitacion, 3L, "H");
        requerimientoInvitacion.setFechaCaducidad(fechaCaducidad);
        requerimientoInvitacion.setFlagActivo(Constantes.FLAG_INVITACION.ACTIVO);
        requerimientoInvitacion.setRequerimientoInvitacionUuid(UUID.randomUUID().toString());
        AuditoriaUtil.setAuditoriaRegistro(requerimientoInvitacion, contexto);
        Supervisora supervisoraPN = supervisoraService.obtener(requerimientoInvitacion.getSupervisora().getIdSupervisora(), contexto);
        notificacionService.enviarRequerimientoInvitacion(supervisoraPN, requerimientoInvitacion, contexto);
        return requerimientoInvitacionDao.save(requerimientoInvitacion);
    }

    @Override
    public RequerimientoInvitacion obtener(Long id, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {

    }

    @Override
    public RequerimientoInvitacion eliminar(String uuid, Contexto contexto) {
        logger.info("Eliminando RequerimientoInvitacion con ID {} - usuario: {}", uuid, contexto.getUsuario());
        Optional<RequerimientoInvitacion> optional = requerimientoInvitacionDao.obtenerPorUuid(uuid);
        if (!optional.isPresent()) {
            throw new ValidacionException(INVITACION_NO_ENCONTRADA);
        }
        ListadoDetalle estadoEliminado = listadoDetalleService.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_REQ_INVITACION.CODIGO, Constantes.LISTADO.ESTADO_REQ_INVITACION.ELIMINADO);
        if (estadoEliminado == null) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_ELIMINADO_NO_CONFIGURADO_EN_LISTADODETALLE);
        }
        RequerimientoInvitacion entidad = optional.get();
        entidad.setEstado(estadoEliminado);
        entidad.setFlagActivo(Constantes.FLAG_INVITACION.INACTIVO);
        AuditoriaUtil.setAuditoriaActualizacion(entidad, contexto);
        return requerimientoInvitacionDao.save(entidad);
    }

    @Override
    public Page<RequerimientoInvitacion> obtener(Long idEstado, String fechaInicioInvitacion,
                                                 String fechaFinInvitacion, String requerimientoUuid, Contexto contexto, Pageable pageable) {
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
            Supervisora supervisora = supervisoraService.obtenerSupervisoraXRUCVigente(contexto.getUsuario().getCodigoRuc());
            idSupervisora = supervisora.getIdSupervisora();
        }
        return requerimientoInvitacionDao.obtenerInvitaciones(idSupervisora, idEstado, fechaInicio, fechaFin, requerimientoUuid, pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Requerimiento evaluar(String  uuid, ListadoDetalleDTO estado, Contexto contexto) {
        //Solo Supervisor
        if (contexto.getUsuario().getCodigoUsuarioInterno() != null) {
            throw new ValidacionException(ACCESO_NO_AUTORIZADO);
        }
        RequerimientoInvitacion invitacion = requerimientoInvitacionDao.obtenerPorUuid(uuid)
                .orElseThrow(() -> new ValidacionException(INVITACION_NO_ENCONTRADA));
        Requerimiento requerimiento = invitacion.getRequerimiento();
        if(!requerimiento.getEstado().getCodigo()
                .equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_PROCESO)) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_EN_PROCESO);
        }
        List<String> correosNotificacion = new ArrayList<>();
        boolean esAprobacion = false;
        ListadoDetalle estadoInvitacion = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_INVITACION.CODIGO, estado.getCodigo());
        if(estadoInvitacion.getCodigo().equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQ_INVITACION.ACEPTADO)) {
            //Obtener Supervisora
            Supervisora supervisora = supervisoraService.obtenerSupervisoraXRUCVigente(contexto.getUsuario().getCodigoRuc());

            //update estado y fechas a Aceptado o Rechazado a la Invitacion
            invitacion.setEstado(estadoInvitacion);
            invitacion.setFechaAceptacion(new Date());
            AuditoriaUtil.setAuditoriaRegistro(invitacion, contexto);
            invitacion = requerimientoInvitacionDao.save(invitacion);

            //update estado a En Aprobacion al Requerimiento
            ListadoDetalle estadoRequerimiento = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO, Constantes.LISTADO.ESTADO_REQUERIMIENTO.EN_APROBACION);
            requerimiento.setEstado(estadoRequerimiento);
            requerimiento.setSupervisora(supervisora);
            AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
            requerimiento = requerimientoDao.save(requerimiento);

            //insert en req aprobacion
            ListadoDetalle tipoAprobacion = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.TIPO_APROBACION.CODIGO, Constantes.LISTADO.TIPO_APROBACION.APROBAR);
            ListadoDetalle grupoAprobador = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.GRUPO_APROBACION.CODIGO, Constantes.LISTADO.GRUPO_APROBACION.GPPM);
            ListadoDetalle estadoAprobacion = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO);
            ListadoDetalle tipoAprobador = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.TIPO_APROBACION.CODIGO, Constantes.LISTADO.TIPO_EVALUADOR.APROBADOR_ADMINISTRATIVO);
            RequerimientoAprobacion aprobacion = new RequerimientoAprobacion();
            aprobacion.setRequerimiento(requerimiento);
            aprobacion.setTipo(tipoAprobacion);
            aprobacion.setGrupoAprobador(grupoAprobador);
            aprobacion.setTipoAprobador(tipoAprobador);
            aprobacion.setUsuario(contexto.getUsuario());
            aprobacion.setEstado(estadoAprobacion);
            AuditoriaUtil.setAuditoriaRegistro(aprobacion, contexto);
            aprobacionDao.save(aprobacion);

            //Flag notificacion aceptado
            esAprobacion = true;

            //Obtener correos
            Rol rol = rolService.obtenerCodigo(Constantes.ROLES.APROBADOR_GPPM);
            List<UsuarioRol> usuarioGPPM = usuarioRolService.obtenerUsuarioRolPorRol(rol);
            if(usuarioGPPM.isEmpty()) {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_ROL_GPPM_NO_ENCONTRADO);
            }

            //Notificacion Asignacion Requerimiento
            notificacionService.enviarMensajeRequerimientoPorAprobar(requerimiento, usuarioGPPM.get(0).getUsuario(), contexto);

        } else if (estadoInvitacion.getCodigo().equalsIgnoreCase(Constantes.LISTADO.ESTADO_REQ_INVITACION.RECHAZADO)) {
            //update estado y fechas a Aceptado o Rechazado a la Invitacion
            invitacion.setEstado(estadoInvitacion);
            invitacion.setFechaRechazo(new Date());
            AuditoriaUtil.setAuditoriaRegistro(invitacion, contexto);
            requerimientoInvitacionDao.save(invitacion);
        }

        //Obtener correos
        correosNotificacion.add(invitacion.getSupervisora().getCorreo());
        correosNotificacion.add(requerimiento.getDivision().getUsuario().getCorreo());

        //Enviar notificacion
        notificacionService.enviarMensajeAprobacionRechazoReqInvitacion(invitacion, correosNotificacion, esAprobacion, contexto);
        return null;
    }

}
