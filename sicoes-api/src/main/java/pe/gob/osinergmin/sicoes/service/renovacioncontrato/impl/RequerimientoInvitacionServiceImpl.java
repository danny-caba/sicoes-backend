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
import java.util.ArrayList;
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
    public RequerimientoInvitacion aceptar(RequerimientoInvitacion requerimientoInvitacionIn, Contexto contexto) throws Exception {
        System.out.println("===== SERVICIO aceptar INICIO =====");
        logger.error("===== SERVICIO aceptar INICIO =====");
        try {
            System.out.println("RequerimientoInvitacion recibido: " + requerimientoInvitacionIn);
            System.out.println("ID: " + (requerimientoInvitacionIn != null ? requerimientoInvitacionIn.getIdReqInvitacion() : "null"));
            
            logger.error("Iniciando proceso de aceptación de invitación ID: {}", 
                        requerimientoInvitacionIn != null ? requerimientoInvitacionIn.getIdReqInvitacion() : "null");
            
            if (requerimientoInvitacionIn == null || requerimientoInvitacionIn.getIdReqInvitacion() == null) {
                System.out.println("VALIDACION FALLO: Datos de invitación inválidos");
                throw new ValidacionException("Datos de invitación inválidos");
            }
            
            RequerimientoInvitacion requerimientoInvitacion = requerimientoInvitacionDao.findById(requerimientoInvitacionIn.getIdReqInvitacion()).orElse(null);
            
            if (requerimientoInvitacion == null) {
                throw new ValidacionException("No se encontró la invitación especificada con ID: " + requerimientoInvitacionIn.getIdReqInvitacion());
            }
            
            logger.info("Invitación encontrada. Estado actual: {}, RequerimientoRenovacion ID: {}", 
                       requerimientoInvitacion.getEstadoInvitacion() != null ? requerimientoInvitacion.getEstadoInvitacion().getCodigo() : "null", 
                       requerimientoInvitacion.getRequerimientoRenovacion() != null ? requerimientoInvitacion.getRequerimientoRenovacion().getIdReqRenovacion() : "null");
            
            // Obtener estado actual
            ListadoDetalle estadoActual = requerimientoInvitacion.getEstadoInvitacion();
            if (estadoActual == null) {
                throw new ValidacionException("La invitación no tiene un estado válido");
            }
            
            if (!Constantes.LISTADO.ESTADO_INVITACION.INVITADO.equals(estadoActual.getCodigo())) {
                logger.warn("Estado actual no es INVITADO. Estado actual: {} ({})", 
                           estadoActual.getCodigo(), estadoActual.getDescripcion());
                throw new ValidacionException("La invitación debe estar en estado INVITADO para ser aceptada. Estado actual: " + estadoActual.getDescripcion());
            }
        
            // 1. Actualizar estado de la invitación a ACEPTADO
            logger.info("Obteniendo estado ACEPTADO. Código listado: {}, Código detalle: {}", 
                       Constantes.LISTADO.ESTADO_INVITACION.CODIGO, 
                       Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO);
            
            ListadoDetalle estadoNuevo = listadoDetalleDao.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_INVITACION.CODIGO, Constantes.LISTADO.ESTADO_INVITACION.ACEPTADO
            );
            
            if (estadoNuevo == null) {
                throw new ValidacionException("No se encontró el estado ACEPTADO en el catálogo");
            }
            
            logger.info("Estado ACEPTADO encontrado con ID: {}", estadoNuevo.getIdListadoDetalle());
            
            requerimientoInvitacion.setIdEstadoLd(estadoNuevo.getIdListadoDetalle());
            requerimientoInvitacion.setEstadoInvitacion(estadoNuevo);
            requerimientoInvitacion.setFeAceptacion(new Date()); // Fecha de la transacción

        // 2. Crear historial de cambio de estado de la invitación
        // COMENTADO TEMPORALMENTE PARA DEBUGGING
        /*
        HistorialEstadoInvitacion nuevoHistorial = new HistorialEstadoInvitacion();
        nuevoHistorial.setDeEstadoAnterior(estadoActual.getDescripcion());
        nuevoHistorial.setDeEstadoNuevo(estadoNuevo.getDescripcion());
        nuevoHistorial.setEsRegistro(Constantes.ESTADO.ACTIVO);
        nuevoHistorial.setIdReqInvitacion(requerimientoInvitacion.getIdReqInvitacion());
        nuevoHistorial.setIdUsuario(contexto.getUsuario().getIdUsuario());
        nuevoHistorial.setFeFechaCambio(new Timestamp(System.currentTimeMillis()));
        AuditoriaUtil.setAuditoriaRegistro(nuevoHistorial, contexto);
        historialEstadoInvitacionDao.save(nuevoHistorial);
        */

        // 3. Derivar a la bandeja de G3-GPPM (user: 9134)
        // Solo actualizar la tabla de invitaciones, la derivación a GPPM se maneja en el workflow de aprobación
        Long idReqRenovacion = requerimientoInvitacion.getRequerimientoRenovacion() != null ? 
                               requerimientoInvitacion.getRequerimientoRenovacion().getIdReqRenovacion() : null;
        
        if (idReqRenovacion != null) {
            logger.info("Invitación aceptada - se derivará automáticamente a G3-GPPM para requerimiento ID: {}", idReqRenovacion);
            // TODO: Implementar lógica específica de derivación a G3-GPPM si es necesaria
        } else {
            logger.warn("No se puede derivar a GPPM - ID RequerimientoRenovacion es null para invitación ID: {}", 
                       requerimientoInvitacion.getIdReqInvitacion());
        }

        // 4. Guardar cambios en la invitación
        AuditoriaUtil.setAuditoriaActualizacion(requerimientoInvitacion, contexto);
        requerimientoInvitacionDao.save(requerimientoInvitacion);
        
        // TODO: 5. Implementar notificación a G3-GPPM de la aceptación
        // enviarNotificacionAceptacionGPPM(requerimientoInvitacion, contexto);
        
            logger.info("Proceso de aceptación completado exitosamente para invitación ID: {} - Derivado a G3-GPPM (user: 9134)", 
                       requerimientoInvitacion.getIdReqInvitacion());
            
            return requerimientoInvitacion;
            
        } catch (ValidacionException e) {
            logger.error("Error de validación en aceptar invitación: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error inesperado al aceptar invitación ID: {}", 
                        requerimientoInvitacionIn != null ? requerimientoInvitacionIn.getIdReqInvitacion() : "null", e);
            throw new ValidacionException("Error al procesar la aceptación de la invitación: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RequerimientoInvitacion rechazar(RequerimientoInvitacion requerimientoInvitacionIn, Contexto contexto) throws Exception {
        try {
            logger.info("Iniciando proceso de rechazo de invitación ID: {}", requerimientoInvitacionIn.getIdReqInvitacion());
            
            if (requerimientoInvitacionIn == null || requerimientoInvitacionIn.getIdReqInvitacion() == null) {
                throw new ValidacionException("Datos de invitación inválidos");
            }
            
            RequerimientoInvitacion requerimientoInvitacion = requerimientoInvitacionDao.findById(requerimientoInvitacionIn.getIdReqInvitacion()).orElse(null);
            
            if (requerimientoInvitacion == null) {
                throw new ValidacionException("No se encontró la invitación especificada con ID: " + requerimientoInvitacionIn.getIdReqInvitacion());
            }
            
            logger.info("Invitación encontrada. Estado actual: {}, RequerimientoRenovacion ID: {}", 
                       requerimientoInvitacion.getEstadoInvitacion() != null ? requerimientoInvitacion.getEstadoInvitacion().getCodigo() : "null", 
                       requerimientoInvitacion.getRequerimientoRenovacion() != null ? requerimientoInvitacion.getRequerimientoRenovacion().getIdReqRenovacion() : "null");
        
        // Obtener estado actual
        ListadoDetalle estadoActual = requerimientoInvitacion.getEstadoInvitacion();
        if (estadoActual == null) {
            throw new ValidacionException("La invitación no tiene un estado válido");
        }
        
        if (!Constantes.LISTADO.ESTADO_INVITACION.INVITADO.equals(estadoActual.getCodigo())) {
            throw new ValidacionException("La invitación debe estar en estado INVITADO para poder ser evaluada");
        }
        
            // 1. Cambiar estado de la invitación a RECHAZADO
            logger.info("Obteniendo estado RECHAZADO. Código listado: {}, Código detalle: {}", 
                       Constantes.LISTADO.ESTADO_INVITACION.CODIGO, 
                       Constantes.LISTADO.ESTADO_INVITACION.RECHAZADO);
            
            ListadoDetalle estadoNuevo = listadoDetalleDao.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_INVITACION.CODIGO, Constantes.LISTADO.ESTADO_INVITACION.RECHAZADO
            );
            
            if (estadoNuevo == null) {
                throw new ValidacionException("No se encontró el estado RECHAZADO en el catálogo");
            }
            
            logger.info("Estado RECHAZADO encontrado con ID: {}", estadoNuevo.getIdListadoDetalle());
            
            requerimientoInvitacion.setIdEstadoLd(estadoNuevo.getIdListadoDetalle());
            requerimientoInvitacion.setEstadoInvitacion(estadoNuevo);
            requerimientoInvitacion.setFeCancelado(new Date());

        // 2. Crear historial de cambio de estado de la invitación
        HistorialEstadoInvitacion nuevoHistorial = new HistorialEstadoInvitacion();
        nuevoHistorial.setDeEstadoAnterior(estadoActual.getDescripcion());
        nuevoHistorial.setDeEstadoNuevo(estadoNuevo.getDescripcion());
        nuevoHistorial.setEsRegistro(Constantes.ESTADO.ACTIVO);
        nuevoHistorial.setIdReqInvitacion(requerimientoInvitacion.getIdReqInvitacion());
        nuevoHistorial.setIdUsuario(contexto.getUsuario().getIdUsuario());
        nuevoHistorial.setFeFechaCambio(new Timestamp(System.currentTimeMillis()));
        AuditoriaUtil.setAuditoriaRegistro(nuevoHistorial, contexto);
        historialEstadoInvitacionDao.save(nuevoHistorial);

        // 3. Actualizar ES_REQ_RENOVACION a 946 (Archivado) en la tabla SICOES_TC_REQ_RENOVACION
        Long idReqRenovacion = requerimientoInvitacion.getRequerimientoRenovacion() != null ? 
                               requerimientoInvitacion.getRequerimientoRenovacion().getIdReqRenovacion() : null;
        if (idReqRenovacion != null) {
            RequerimientoRenovacion requerimientoRenovacion = requerimientoRenovacionDao
                    .findById(idReqRenovacion).orElse(null);
            
            if (requerimientoRenovacion != null) {
                ListadoDetalle estadoArchivado = listadoDetalleDao.findById(946L).orElseThrow(
                        () -> new ValidacionException("No se encontró el estado Archivado (946)"));
                
                requerimientoRenovacion.setEsReqRenovacion(estadoArchivado.getIdListadoDetalle());
                AuditoriaUtil.setAuditoriaActualizacion(requerimientoRenovacion, contexto);
                requerimientoRenovacionDao.save(requerimientoRenovacion);
                
                logger.info("Requerimiento de renovación actualizado a estado Archivado (946) - ID: {}", 
                           requerimientoRenovacion.getIdReqRenovacion());
            } else {
                logger.warn("No se encontró el RequerimientoRenovacion con ID: {}", 
                           idReqRenovacion);
            }
        } else {
            logger.warn("La invitación no tiene asociado un RequerimientoRenovacion");
        }

        // 4. Guardar cambios en la invitación
        AuditoriaUtil.setAuditoriaActualizacion(requerimientoInvitacion, contexto);
        requerimientoInvitacionDao.save(requerimientoInvitacion);
        
        // TODO: 5. Implementar notificación a G3-GPPM del rechazo
        // enviarNotificacionRechazoGPPM(requerimientoInvitacion, contexto);
        
            logger.info("Proceso de rechazo completado exitosamente para invitación ID: {}", 
                       requerimientoInvitacion.getIdReqInvitacion());
            
            return requerimientoInvitacion;
            
        } catch (ValidacionException e) {
            logger.error("Error de validación en rechazar invitación: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error inesperado al rechazar invitación ID: {}", 
                        requerimientoInvitacionIn != null ? requerimientoInvitacionIn.getIdReqInvitacion() : "null", e);
            throw new ValidacionException("Error al procesar el rechazo de la invitación: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public int cancelarCaducados(Date fecha, Contexto contexto) throws Exception {
        logger.info("Iniciando cancelación de invitaciones caducadas. Fecha: {}", fecha);
        int invitacionesActualizadas = 0;
        try {
            ListadoDetalle estadoCaducado = listadoDetalleDao.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_INVITACION.CODIGO,Constantes.LISTADO.ESTADO_INVITACION.CADUCADO);

            ListadoDetalle estadoArchivado = listadoDetalleDao.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_INVITACION.CODIGO,Constantes.LISTADO.ESTADO_INVITACION.CADUCADO);

            List<RequerimientoInvitacion> invitacionesCaducadas = requerimientoInvitacionDao
                    .findInvitacionesCaducadas(fecha, Constantes.LISTADO.ESTADO_INVITACION.INVITADO);

            if (invitacionesCaducadas.isEmpty()) {
                logger.info("No se encontraron invitaciones caducadas para la fecha: {}", fecha);
                return 0;
            }

            logger.info("Encontradas {} invitaciones caducadas", invitacionesCaducadas.size());


            for (RequerimientoInvitacion invitacion : invitacionesCaducadas) {
                try {
                    ListadoDetalle estadoActual = invitacion.getEstadoInvitacion();

                    invitacion.setIdEstadoLd(estadoCaducado.getIdListadoDetalle());
                    invitacion.setEstadoInvitacion(estadoCaducado);
                    invitacion.setFeCancelado(new Date());
                    AuditoriaUtil.setAuditoriaActualizacion(invitacion, contexto);

                    HistorialEstadoInvitacion historialInvitacion = new HistorialEstadoInvitacion();
                    historialInvitacion.setDeEstadoAnterior(estadoActual.getDescripcion());
                    historialInvitacion.setDeEstadoNuevo(estadoCaducado.getDescripcion());
                    historialInvitacion.setEsRegistro(Constantes.ESTADO.ACTIVO);
                    historialInvitacion.setIdReqInvitacion(invitacion.getIdReqInvitacion());
                    historialInvitacion.setIdUsuario(contexto.getUsuario().getIdUsuario());
                    historialInvitacion.setFeFechaCambio(new Timestamp(System.currentTimeMillis()));
                    AuditoriaUtil.setAuditoriaRegistro(historialInvitacion, contexto);
                    historialEstadoInvitacionDao.save(historialInvitacion);

                    requerimientoInvitacionDao.save(invitacion);
                    invitacionesActualizadas++;
                } catch (Exception e) {
                    logger.error("Error al procesar invitación caducada ID: {}", invitacion.getIdReqInvitacion(), e);
                }
            }

            logger.info("Proceso de cancelación por caducidad completado. Invitaciones procesadas: {}/{}",
                    invitacionesActualizadas,invitacionesCaducadas.size());

            return invitacionesActualizadas;

        } catch (Exception e) {
            logger.error("Error general en el proceso de cancelación por caducidad", e);
            throw new Exception("Error al cancelar invitaciones caducadas: " + e.getMessage(), e);
        }
    }


}
