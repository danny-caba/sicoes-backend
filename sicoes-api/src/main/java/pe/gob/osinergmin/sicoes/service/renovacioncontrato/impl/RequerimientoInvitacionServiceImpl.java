package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.*;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.*;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RequerimientoInvitacionService;
import pe.gob.osinergmin.sicoes.util.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Service
public class RequerimientoInvitacionServiceImpl implements RequerimientoInvitacionService {

    @Autowired
    private RequerimientoInvitacionDao requerimientoInvitacionDao;
    @Autowired
    private HistorialEstadoInvitacionDao historialEstadoInvitacionDao;
    @Autowired
    private HistorialEstadoRequerimientoRenovacionDao historialEstadoRequerimientoRenovacionDao;
    @Autowired
    private HistorialEstadoAprobacionCampoDao historialEstadoAprobacionCampoDao;
    @Autowired
    private ListadoDetalleDao listadoDetalleDao;

    Logger logger = LogManager.getLogger(RequerimientoInvitacionServiceImpl.class);
    @Autowired
    private RequerimientoRenovacionDao requerimientoRenovacionDao;

    @Override
    @Transactional
    public RequerimientoInvitacion aceptar(RequerimientoInvitacion requerimientoInvitacionIn, Contexto contexto) {
        RequerimientoInvitacion requerimientoInvitacion = requerimientoInvitacionDao.findById(requerimientoInvitacionIn.getIdReqInvitacion()).orElse(null);
        ListadoDetalle estadoActual = listadoDetalleDao.findById(requerimientoInvitacion.getIdEstadoLd()).orElseThrow(
                () -> new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_TIPO_INCORRECTO));
        if (!Constantes.LISTADO.ESTADO_INVITACION.INVITADO.equals(estadoActual.getCodigo())) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ESTADO_INVITACION_EDITAR);
        }
        ListadoDetalle estadoNuevo = listadoDetalleDao.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_INVITACION.CODIGO, Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO
        );
        requerimientoInvitacion.setIdEstadoLd(estadoNuevo.getIdListadoDetalle());
        requerimientoInvitacion.setFeAceptacion(new Date());

        HistorialEstadoInvitacion nuevoHistorial = new HistorialEstadoInvitacion();
        nuevoHistorial.setDeEstadoAnterior(estadoActual.getDescripcion());
        nuevoHistorial.setDeEstadoNuevo(estadoNuevo.getDescripcion());
        nuevoHistorial.setEsRegistro(Constantes.ESTADO.ACTIVO);
        nuevoHistorial.setIdReqInvitacion(requerimientoInvitacion.getIdReqInvitacion());
        nuevoHistorial.setIdUsuario(contexto.getUsuario().getIdUsuario());
        nuevoHistorial.setFeFechaCambio(new Timestamp(System.currentTimeMillis()));
        AuditoriaUtil.setAuditoriaRegistro(nuevoHistorial,contexto);
        historialEstadoInvitacionDao.save(nuevoHistorial);

        ListadoDetalle ldGPPM = listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.GRUPO_APROBACION.CODIGO,Constantes.LISTADO.GRUPO_APROBACION.GPPM);
        ListadoDetalle estadoAsignado = listadoDetalleDao.obtenerListadoDetalle(Constantes.LISTADO.ESTADO_APROBACION.CODIGO,Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO);
        List<HistorialEstadoAprobacionCampo> lista = historialEstadoAprobacionCampoDao.findByIdGrupoAprobadorLdAndIdReqAprobacionOrderByFeFechaCambioDesc(
                ldGPPM.getIdListadoDetalle(),requerimientoInvitacion.getRequerimientoRenovacion().getIdReqRenovacion());
        logger.info("Sin historial GPPM: {}",lista.isEmpty());

        HistorialEstadoAprobacionCampo historialEstadoAprobacionCampo= new HistorialEstadoAprobacionCampo();
        historialEstadoAprobacionCampo.setDeEstadoAnteriorLd(null);
        historialEstadoAprobacionCampo.setDeEstadoNuevoLd(estadoAsignado.getIdListadoDetalle());
        historialEstadoAprobacionCampo.setIdReqAprobacion(168L);
        historialEstadoAprobacionCampo.setIdUsuario(contexto.getUsuario().getIdUsuario());
        historialEstadoAprobacionCampo.setFeFechaCambio(new Timestamp(System.currentTimeMillis()));
        historialEstadoAprobacionCampo.setEsRegistro(Constantes.ESTADO.ACTIVO);
        AuditoriaUtil.setAuditoriaRegistro(historialEstadoAprobacionCampo,contexto);
        historialEstadoAprobacionCampoDao.save(historialEstadoAprobacionCampo);

        requerimientoInvitacionDao.save(requerimientoInvitacion);
        return requerimientoInvitacion;
    }


}
