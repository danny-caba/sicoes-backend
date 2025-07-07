package pe.gob.osinergmin.sicoes.service.impl;

import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ACCESO_NO_AUTORIZADO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.APROBACION_NO_ENCONTRADA;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_FIN_ANTES_INICIO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_INICIO_ANTES_HOY;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ESTADO_APROBACION_NO_ENVIADO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.REQUERIMIENTO_NO_ENCONTRADO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.SIAF_NO_ENVIADO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.Division;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoAprobacionDTO;
import pe.gob.osinergmin.sicoes.repository.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.RequerimientoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class RequerimientoServiceImpl implements RequerimientoService {

    private static final Logger logger = LogManager.getLogger(RequerimientoServiceImpl.class);

    @Autowired
    private RequerimientoDao requerimientoDao;
    @Autowired
    private RequerimientoAprobacionDao aprobacionDao;
    @Autowired
    private ListadoDetalleService listadoDetalleService;
    @Autowired
    private NotificacionService notificacionService;

    @Override
    public Requerimiento guardar(Requerimiento requerimiento, Contexto contexto) {
        AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
        return requerimientoDao.save(requerimiento);
    }

    @Override
    public Requerimiento obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {

    }

    @Override
    @Transactional
    public Page<Requerimiento> listar(FiltroRequerimientoDTO filtro, Pageable pageable, Contexto contexto) {
        Date fechaInicio = filtro.getFechaInicio();
        Date fechaFin = filtro.getFechaFin();
        if (fechaInicio != null && fechaInicio.after(new Date())) {
            throw new ValidacionException(ERROR_FECHA_INICIO_ANTES_HOY);
        }
        if (fechaInicio != null && fechaFin != null && fechaFin.before(fechaInicio)) {
            throw new ValidacionException(ERROR_FECHA_FIN_ANTES_INICIO);
        }
        if (fechaInicio != null) fechaInicio = DateUtil.getInitDay(fechaInicio);
        if (fechaFin != null) fechaFin = DateUtil.getEndDay(fechaFin);
        Division division = filtro.getDivision() != null ? crearDivision(filtro.getDivision()) : null;
        ListadoDetalle perfil = filtro.getPerfil() != null ? crearListadoDetalle(filtro.getPerfil()) : null;
        Supervisora supervisora = filtro.getSupervisora() != null ? crearSupervisora(filtro.getSupervisora()) : null;
        ListadoDetalle estadoAprobacion = filtro.getEstadoAprobacion() != null ? crearListadoDetalle(filtro.getEstadoAprobacion()) : null;
        return requerimientoDao.listarRequerimientos(division, perfil, fechaInicio, fechaFin, supervisora, estadoAprobacion, pageable);
    }

    private Division crearDivision(Long id) {
        Division division = new Division();
        division.setIdDivision(id);
        return division;
    }

    private ListadoDetalle crearListadoDetalle(Long id) {
        ListadoDetalle detalle = new ListadoDetalle();
        detalle.setIdListadoDetalle(id);
        return detalle;
    }

    private Supervisora crearSupervisora(Long id) {
        Supervisora s = new Supervisora();
        s.setIdSupervisora(id);
        return s;
    }

    @Override
    @Transactional
    public Requerimiento archivar(Long id, String observacion, Contexto contexto) {
        Requerimiento requerimiento = requerimientoDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Requerimiento no encontrado"));
        ListadoDetalle estadoArchivado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO, Constantes.LISTADO.ESTADO_REQUERIMIENTO.ARCHIVADO);
        if (estadoArchivado == null) {
            throw new IllegalStateException("Estado ARCHIVADO no configurado en ListadoDetalle");
        }
        requerimiento.setEstado(estadoArchivado);
        requerimiento.setDeObservacion(observacion);
        AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
        return requerimientoDao.save(requerimiento);
    }

    @Override
    public Optional<Requerimiento> obtenerPorId(Long id) {
        return requerimientoDao.obtener(id);
    }

    @Override
    @Transactional
    public Requerimiento aprobar(String uuid, RequerimientoAprobacionDTO aprobacion, Contexto contexto) {
        //TODO: buscar el Rol GPPM o GSE
        Requerimiento requerimientoBD = requerimientoDao.obtenerPorUuid(uuid)
                .orElseThrow(() -> new ValidacionException(REQUERIMIENTO_NO_ENCONTRADO));
        boolean esGppm = false;
        boolean esAprobado = false;
        ListadoDetalle estadoAprobacionRequest = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_APROBACION.CODIGO, aprobacion.getEstado().getCodigo());
        //validar si es GPPM o GSE
        if(aprobacion.getRol().equalsIgnoreCase(Constantes.LISTADO.ESTADO_GRUPO_APROBACION.GPPM)) {
            esGppm = true;
            //Buscar Aprobacion GPPM
            ListadoDetalle grupoGppm = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_GRUPO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_GRUPO_APROBACION.GPPM);
            RequerimientoAprobacion aprobacionGppm = aprobacionDao.obtenerPorRequerimientoYGrupo(requerimientoBD.getIdRequerimiento(),
                            grupoGppm.getIdListadoDetalle(), Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO)
                    .orElseThrow(() -> new ValidacionException(APROBACION_NO_ENCONTRADA));
            //Aprobado o Rechazado
            if(aprobacion.getEstado().getCodigo()
                    .equalsIgnoreCase(Constantes.LISTADO.ESTADO_APROBACION.APROBADO)) {
                esAprobado = true;
                //Registrar Asignado GSE
                ListadoDetalle tipoAprobacion = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.ESTADO_TIPO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_TIPO_APROBACION.APROBAR);
                ListadoDetalle grupoAprobacion = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.ESTADO_GRUPO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_GRUPO_APROBACION.GSE);
                ListadoDetalle estadoAprobacion = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.ESTADO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO);

                RequerimientoAprobacion aprobacionGse = new RequerimientoAprobacion();
                aprobacionGse.setRequerimiento(requerimientoBD);
                aprobacionGse.setTipo(tipoAprobacion);
                aprobacionGse.setGrupo(grupoAprobacion);
                aprobacionGse.setUsuario(contexto.getUsuario());
                aprobacionGse.setEstado(estadoAprobacion);
                aprobacionGse.setFlagFirmado(Constantes.FLAG_FIRMADO.NO_FIRMADO);
                AuditoriaUtil.setAuditoriaRegistro(aprobacionGse, contexto);
                aprobacionDao.save(aprobacionGse);

                //Actualizar NuSiaf Requerimiento
                if(Objects.isNull(aprobacion.getNuSiaf()) || aprobacion.getNuSiaf().isEmpty()) {
                    throw new ValidacionException(SIAF_NO_ENVIADO);
                }
                requerimientoBD.setNuSiaf(aprobacion.getNuSiaf());

                //Actualizar Fecha de Aprobacion
                aprobacionGppm.setFechaAprobacion(new Date());
            } else if(aprobacion.getEstado().getCodigo()
                    .equalsIgnoreCase(Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO)) {
                //Actualizar Estado Requerimiento
                ListadoDetalle estadoReqDesaprobado = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO, Constantes.LISTADO.ESTADO_REQUERIMIENTO.DESAPROBADO);
                requerimientoBD.setEstado(estadoReqDesaprobado);

                //Actualizar Fecha de Rechazo
                aprobacionGppm.setFechaRechazo(new Date());
            } else {
                throw new ValidacionException(ESTADO_APROBACION_NO_ENVIADO);
            }
            //Actualizar Requerimiento
            requerimientoBD.setDeObservacion(aprobacion.getDeObservacion());
            AuditoriaUtil.setAuditoriaRegistro(requerimientoBD, contexto);
            requerimientoBD = requerimientoDao.save(requerimientoBD);

            //Actualizar GPPM a Aprobado o Desaprobado
            aprobacionGppm.setEstado(estadoAprobacionRequest);
            AuditoriaUtil.setAuditoriaRegistro(aprobacionGppm, contexto);
            aprobacionDao.save(aprobacionGppm);
        } else if(aprobacion.getRol().equalsIgnoreCase(Constantes.LISTADO.ESTADO_GRUPO_APROBACION.GSE)) {
            //Buscar Aprobacion GSE
            ListadoDetalle grupoGse = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_GRUPO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_GRUPO_APROBACION.GSE);
            RequerimientoAprobacion aprobacionGse = aprobacionDao.obtenerPorRequerimientoYGrupo(requerimientoBD.getIdRequerimiento(),
                            grupoGse.getIdListadoDetalle(), Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO)
                    .orElseThrow(() -> new ValidacionException(APROBACION_NO_ENCONTRADA));
            //Aprobado o Rechazado
            if(aprobacion.getEstado().getCodigo()
                    .equalsIgnoreCase(Constantes.LISTADO.ESTADO_APROBACION.APROBADO)) {
                esAprobado = true;
                //Actualizar Fecha de Aprobacion
                aprobacionGse.setFechaAprobacion(new Date());
            } else if(aprobacion.getEstado().getCodigo()
                .equalsIgnoreCase(Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO)) {
                //Actualizar Fecha de Rechazo
                aprobacionGse.setFechaRechazo(new Date());
                //Actualizar Requerimiento
                ListadoDetalle estadoReqDesaprobado = listadoDetalleService.obtenerListadoDetalle(
                        Constantes.LISTADO.ESTADO_REQUERIMIENTO.CODIGO, Constantes.LISTADO.ESTADO_REQUERIMIENTO.DESAPROBADO);
                requerimientoBD.setEstado(estadoReqDesaprobado);
            } else {
                throw new ValidacionException(ESTADO_APROBACION_NO_ENVIADO);
            }

            //Actualizar GSE a Aprobado o Desaprobado
            aprobacionGse.setEstado(estadoAprobacionRequest);
            AuditoriaUtil.setAuditoriaRegistro(aprobacionGse, contexto);
            aprobacionDao.save(aprobacionGse);

            //Actualizar Requerimiento
            requerimientoBD.setDeObservacion(aprobacion.getDeObservacion());
            AuditoriaUtil.setAuditoriaRegistro(requerimientoBD, contexto);
            requerimientoBD = requerimientoDao.save(requerimientoBD);
        } else {
            throw new ValidacionException(ACCESO_NO_AUTORIZADO);
        }

        //Envio de Notificaciones
        if(esGppm) {
            if(esAprobado) {
                //Enviar por aprobar a GSE
                notificacionService.enviarMensajeRequerimientoPorAprobar(requerimientoBD, contexto);
            } else {
                //Enviar q GPPM rechazo
                notificacionService.enviarMensajeRechazoRequerimiento(requerimientoBD, Constantes.LISTADO.ESTADO_GRUPO_APROBACION.GPPM, contexto);
            }
        } else {
            if(esAprobado) {
                //Enviar para cargar docs
                notificacionService.enviarMensajeCargarDocumentosRequerimiento(requerimientoBD, contexto);
            } else {
                //Enviar q GSE rechazo
                notificacionService.enviarMensajeRechazoRequerimiento(requerimientoBD, Constantes.LISTADO.ESTADO_GRUPO_APROBACION.GSE, contexto);
            }
        }
        return requerimientoBD;
    }

}
