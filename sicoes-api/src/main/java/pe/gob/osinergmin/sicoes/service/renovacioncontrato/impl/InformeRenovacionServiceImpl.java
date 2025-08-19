package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Bitacora;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoAprobacionCampo;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoRequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.ActualizacionBandejaDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RechazoInformeDTO;
import pe.gob.osinergmin.sicoes.repository.BitacoraDao;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.NotificacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.HistorialEstadoAprobacionCampoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.HistorialEstadoRequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.service.BitacoraService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
@Transactional
public class InformeRenovacionServiceImpl implements InformeRenovacionService {

    private Logger logger = LogManager.getLogger(InformeRenovacionServiceImpl.class);

    @Autowired
    private InformeRenovacionDao informeRenovacionDao;
    
    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;
    
    @Autowired
    private RequerimientoRenovacionDao requerimientoRenovacionDao;
    
    @Autowired
    private ListadoDetalleDao listadoDetalleDao;
    
    @Autowired
    private BitacoraService bitacoraService;
    
    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private BitacoraDao bitacoraDao;

    @Autowired
    private NotificacionDao notificacionDao;

    @Autowired
    private HistorialEstadoAprobacionCampoDao historialEstadoAprobacionCampoDao;

    @Autowired
    private HistorialEstadoRequerimientoRenovacionDao historialEstadoRequerimientoRenovacionDao;

    @Override
    public void rechazarInforme(RechazoInformeDTO rechazoDTO, Contexto contexto) {
        logger.info("rechazarInforme - Informe: {}, Usuario: {}", 
                    rechazoDTO.getIdInformeRenovacion(), contexto.getUsuario().getIdUsuario());
        
        try {
            // 1. Validar que el informe existe
            InformeRenovacion informe = informeRenovacionDao.obtenerPorId(rechazoDTO.getIdInformeRenovacion());
            if (informe == null) {
                throw new IllegalArgumentException("No se encontró el informe de renovación con ID: " + rechazoDTO.getIdInformeRenovacion());
            }
            
            // 2. Verificar permisos del usuario
            if (!validarPermisosAprobacion(contexto, rechazoDTO.getIdGrupoAprobador())) {
                throw new SecurityException("El usuario no tiene permisos para rechazar este informe");
            }
            
            // 3. Actualizar estado del informe a "RECHAZADO"
            ListadoDetalle estadoRechazado = listadoDetalleDao.obtenerListadoDetalle("ESTADO_INFORME_RENOVACION", "RECHAZADO");
            if (estadoRechazado != null) {
                informe.setEstadoAprobacionInforme(estadoRechazado);
            }
            
            // Agregar observaciones del rechazo
            if (rechazoDTO.getObservaciones() != null) {
                String observacionesActuales = informe.getDeConclusiones() != null ? informe.getDeConclusiones() : "";
                informe.setDeConclusiones(observacionesActuales + "\n\nRECHAZO: " + rechazoDTO.getMotivoRechazo() + 
                                        "\nObservaciones: " + rechazoDTO.getObservaciones() + 
                                        "\nFecha: " + new Date() + 
                                        "\nUsuario: " + contexto.getUsuario().getUsuario());
            }
            
            AuditoriaUtil.setAuditoriaActualizacion(informe, contexto);
            informeRenovacionDao.save(informe);
            
            // 4. Registrar en bitácora
            registrarBitacoraRechazo(informe, rechazoDTO, contexto);
            
            // 5. Enviar notificaciones
            enviarNotificacionRechazo(informe, rechazoDTO, contexto);
            
            logger.info("Informe de renovación rechazado exitosamente - ID: {}", rechazoDTO.getIdInformeRenovacion());
            
        } catch (Exception e) {
            logger.error("Error al rechazar informe de renovación", e);
            throw e;
        }
    }

    @Override
    public void actualizarBandejaAprobaciones(ActualizacionBandejaDTO actualizacionDTO, Contexto contexto) {
        logger.info("actualizarBandejaAprobaciones - ReqAprobacion: {}, Usuario: {}", 
                    actualizacionDTO.getIdRequerimientoAprobacion(), contexto.getUsuario().getIdUsuario());
        
        try {
            // 1. Validar que el requerimiento existe
            RequerimientoAprobacion requerimiento = requerimientoAprobacionDao.obtenerPorId(actualizacionDTO.getIdRequerimientoAprobacion());
            if (requerimiento == null) {
                throw new IllegalArgumentException("No se encontró el requerimiento de aprobación con ID: " + actualizacionDTO.getIdRequerimientoAprobacion());
            }
            
            // 2. Verificar permisos del usuario
            if (!validarPermisosAprobacion(contexto, null)) {
                throw new SecurityException("El usuario no tiene permisos para actualizar este requerimiento");
            }
            
            // 3. Actualizar estado del requerimiento
            if (actualizacionDTO.getNuevoEstado() != null) {
                ListadoDetalle nuevoEstado = listadoDetalleDao.obtener(Long.valueOf(actualizacionDTO.getNuevoEstado()));
                if (nuevoEstado != null) {
                    // Registrar estado anterior para bitácora
                    Long estadoAnterior = requerimiento.getIdEstadoLd();
                    
                    requerimiento.setIdEstadoLd(nuevoEstado.getIdListadoDetalle());
                    
                    // Registrar cambio de estado en tabla de historial
                    registrarCambioEstadoAprobacion(requerimiento.getIdReqAprobacion(), 
                                                  estadoAnterior, 
                                                  nuevoEstado.getIdListadoDetalle(), 
                                                  contexto);
                }
            }
            
            // Actualizar observaciones si se proporcionan
            if (actualizacionDTO.getObservaciones() != null) {
                requerimiento.setDeObservacion(actualizacionDTO.getObservaciones());
            }
            
            AuditoriaUtil.setAuditoriaActualizacion(requerimiento, contexto);
            requerimientoAprobacionDao.save(requerimiento);
            
            // 4. Registrar en bitácora
            registrarBitacoraActualizacionAprobacion(requerimiento, contexto);
            
            logger.info("Bandeja de aprobaciones actualizada exitosamente - ID: {}", actualizacionDTO.getIdRequerimientoAprobacion());
            
        } catch (Exception e) {
            logger.error("Error al actualizar bandeja de aprobaciones", e);
            throw e;
        }
    }

    @Override
    public void actualizarGrillaRenovacionContrato(ActualizacionBandejaDTO actualizacionDTO, Contexto contexto) {
        logger.info("actualizarGrillaRenovacionContrato - ReqRenovacion: {}, Usuario: {}", 
                    actualizacionDTO.getIdRequerimientoRenovacion(), contexto.getUsuario().getIdUsuario());
        
        try {
            // 1. Validar que el requerimiento de renovación existe
            RequerimientoRenovacion requerimiento = requerimientoRenovacionDao.obtenerPorId(actualizacionDTO.getIdRequerimientoRenovacion());
            if (requerimiento == null) {
                throw new IllegalArgumentException("No se encontró el requerimiento de renovación con ID: " + actualizacionDTO.getIdRequerimientoRenovacion());
            }
            
            // 2. Verificar permisos del usuario
            if (!validarPermisosGestionRenovacion(contexto)) {
                throw new SecurityException("El usuario no tiene permisos para actualizar este requerimiento de renovación");
            }
            
            // 3. Actualizar estado del requerimiento
            if (actualizacionDTO.getNuevoEstado() != null) {
                ListadoDetalle nuevoEstado = listadoDetalleDao.obtener(Long.valueOf(actualizacionDTO.getNuevoEstado()));
                if (nuevoEstado != null) {
                    // Registrar estado anterior para bitácora
                    Long estadoAnterior = requerimiento.getEstadoReqRenovacion() != null ? 
                                        requerimiento.getEstadoReqRenovacion().getIdListadoDetalle() : null;
                    
                    requerimiento.setEstadoReqRenovacion(nuevoEstado);
                    
                    // Registrar cambio de estado en tabla de historial
                    registrarCambioEstadoRenovacion(requerimiento.getIdReqRenovacion(), 
                                                  estadoAnterior, 
                                                  nuevoEstado.getIdListadoDetalle(), 
                                                  contexto);
                }
            }
            
            // Actualizar observaciones si se proporcionan
            if (actualizacionDTO.getObservaciones() != null) {
                requerimiento.setDeObservacion(actualizacionDTO.getObservaciones());
            }
            
            AuditoriaUtil.setAuditoriaActualizacion(requerimiento, contexto);
            requerimientoRenovacionDao.save(requerimiento);
            
            // 4. Registrar cambios en bitácora
            registrarBitacoraActualizacionRenovacion(requerimiento, contexto);
            
            // 5. Actualizar bandeja correspondiente (notificar a otros módulos si es necesario)
            actualizarBandejasRelacionadas(requerimiento, contexto);
            
            logger.info("Grilla de renovación de contrato actualizada exitosamente - ID: {}", actualizacionDTO.getIdRequerimientoRenovacion());
            
        } catch (Exception e) {
            logger.error("Error al actualizar grilla de renovación de contrato", e);
            throw e;
        }
    }
    
    private boolean validarPermisosAprobacion(Contexto contexto, Long idGrupoAprobador) {
        try {
            if (contexto.getUsuario() == null) {
                return false;
            }
            return contexto.getUsuario().getUsuario().contains("APROBADOR") || 
                   contexto.getUsuario().getUsuario().contains("TECNICO") ||
                   contexto.getUsuario().getIdUsuario() != null;
        } catch (Exception e) {
            logger.warn("Error al validar permisos de aprobación", e);
            return false;
        }
    }
    
    private boolean validarPermisosGestionRenovacion(Contexto contexto) {
        try {
            if (contexto.getUsuario() == null) {
                return false;
            }
            return contexto.getUsuario().getUsuario().contains("EVALUADOR") || 
                   contexto.getUsuario().getUsuario().contains("ADMIN") ||
                   contexto.getUsuario().getIdUsuario() != null;
        } catch (Exception e) {
            logger.warn("Error al validar permisos de gestión de renovación", e);
            return false;
        }
    }
    
    private void registrarBitacoraRechazo(InformeRenovacion informe, RechazoInformeDTO rechazoDTO, Contexto contexto) {
        try {
            Bitacora bitacora = new Bitacora();
            bitacora.setUsuario(contexto.getUsuario());
            bitacora.setFechaHora(new Date());
            bitacora.setDescripcion("Informe de renovación rechazado. ID: " + informe.getIdInformeRenovacion() + 
                                  ". Motivo: " + rechazoDTO.getMotivoRechazo() + 
                                  ". Observaciones: " + (rechazoDTO.getObservaciones() != null ? rechazoDTO.getObservaciones() : "N/A"));
            
            AuditoriaUtil.setAuditoriaRegistro(bitacora, contexto);
            
            bitacoraDao.save(bitacora);
            
            logger.info("Registrado rechazo en bitácora - Informe: {}, Bitácora ID: {}", 
                       informe.getIdInformeRenovacion(), bitacora.getIdBitacora());
        } catch (Exception e) {
            logger.error("Error al registrar rechazo en bitácora", e);
        }
    }
    
    private void registrarBitacoraActualizacionAprobacion(RequerimientoAprobacion requerimiento, Contexto contexto) {
        try {
            Bitacora bitacora = new Bitacora();
            bitacora.setUsuario(contexto.getUsuario());
            bitacora.setFechaHora(new Date());
            bitacora.setDescripcion("Actualización de bandeja de aprobaciones. ID: " + requerimiento.getIdReqAprobacion() + 
                                  ". Estado: " + (requerimiento.getIdEstadoLd() != null ? requerimiento.getIdEstadoLd() : "N/A") +
                                  ". Observaciones: " + (requerimiento.getDeObservacion() != null ? requerimiento.getDeObservacion() : "N/A"));
            
            AuditoriaUtil.setAuditoriaRegistro(bitacora, contexto);
            
            bitacoraDao.save(bitacora);
            
            logger.info("Registrada actualización de aprobación en bitácora - Req: {}, Bitácora ID: {}", 
                       requerimiento.getIdReqAprobacion(), bitacora.getIdBitacora());
        } catch (Exception e) {
            logger.error("Error al registrar actualización de aprobación en bitácora", e);
        }
    }
    
    private void registrarBitacoraActualizacionRenovacion(RequerimientoRenovacion requerimiento, Contexto contexto) {
        try {
            Bitacora bitacora = new Bitacora();
            bitacora.setUsuario(contexto.getUsuario());
            bitacora.setFechaHora(new Date());
            bitacora.setDescripcion("Actualización de grilla de renovación de contrato. ID: " + requerimiento.getIdReqRenovacion() + 
                                  ". Estado: " + (requerimiento.getEstadoReqRenovacion() != null ? 
                                                 requerimiento.getEstadoReqRenovacion().getDescripcion() : "N/A") +
                                  ". Observaciones: " + (requerimiento.getDeObservacion() != null ? requerimiento.getDeObservacion() : "N/A"));
            
            AuditoriaUtil.setAuditoriaRegistro(bitacora, contexto);
            
            bitacoraDao.save(bitacora);
            
            logger.info("Registrada actualización de renovación en bitácora - Req: {}, Bitácora ID: {}", 
                       requerimiento.getIdReqRenovacion(), bitacora.getIdBitacora());
        } catch (Exception e) {
            logger.error("Error al registrar actualización de renovación en bitácora", e);
        }
    }
    
    private void registrarCambioEstadoAprobacion(Long idRequerimiento, Long estadoAnterior, Long estadoNuevo, Contexto contexto) {
        try {
            HistorialEstadoAprobacionCampo historial = new HistorialEstadoAprobacionCampo();
            historial.setIdReqAprobacion(idRequerimiento);
            historial.setIdUsuario(contexto.getUsuario().getIdUsuario());
            historial.setDeEstadoAnteriorLd(estadoAnterior);
            historial.setDeEstadoNuevoLd(estadoNuevo);
            historial.setEsRegistro(Constantes.ESTADO.ACTIVO);
            
            AuditoriaUtil.setAuditoriaRegistro(historial, contexto);
            
            historialEstadoAprobacionCampoDao.save(historial);
            
            logger.info("Registrado cambio de estado de aprobación - Req: {}, Historial ID: {}, De: {} A: {}", 
                       idRequerimiento, historial.getIdHistorialEstadoCampo(), estadoAnterior, estadoNuevo);
        } catch (Exception e) {
            logger.error("Error al registrar cambio de estado de aprobación", e);
        }
    }
    
    private void registrarCambioEstadoRenovacion(Long idRequerimiento, Long estadoAnterior, Long estadoNuevo, Contexto contexto) {
        try {
            HistorialEstadoRequerimientoRenovacion historial = new HistorialEstadoRequerimientoRenovacion();
            historial.setIdRequerimiento(idRequerimiento);
            historial.setIdUsuario(contexto.getUsuario().getIdUsuario());
            historial.setDeEstadoAnteriorLd(estadoAnterior);
            historial.setDeEstadoNuevoLd(estadoNuevo);
            historial.setEsRegistro(Constantes.ESTADO.ACTIVO);
            
            AuditoriaUtil.setAuditoriaRegistro(historial, contexto);
            
            historialEstadoRequerimientoRenovacionDao.save(historial);
            
            logger.info("Registrado cambio de estado de renovación - Req: {}, Historial ID: {}, De: {} A: {}", 
                       idRequerimiento, historial.getIdHistorialEstadoCampo(), estadoAnterior, estadoNuevo);
        } catch (Exception e) {
            logger.error("Error al registrar cambio de estado de renovación", e);
        }
    }
    
    private void enviarNotificacionRechazo(InformeRenovacion informe, RechazoInformeDTO rechazoDTO, Contexto contexto) {
        try {
            Notificacion notificacion = new Notificacion();
            notificacion.setCorreo("sistema@osinergmin.gob.pe");
            notificacion.setAsunto("Informe de Renovación Rechazado");
            notificacion.setMensaje(
                "Su informe de renovación con ID " + informe.getIdInformeRenovacion() + 
                " ha sido rechazado.\nMotivo: " + rechazoDTO.getMotivoRechazo() + 
                (rechazoDTO.getObservaciones() != null ? 
                 "\nObservaciones: " + rechazoDTO.getObservaciones() : "") +
                "\nFecha: " + new Date());
            
            ListadoDetalle estadoPendiente = listadoDetalleDao.obtenerListadoDetalle("ESTADO_NOTIFICACION", "PENDIENTE");
            if (estadoPendiente != null) {
                notificacion.setEstado(estadoPendiente);
            }
            
            AuditoriaUtil.setAuditoriaRegistro(notificacion, contexto);
            
            notificacionDao.save(notificacion);
            
            logger.info("Enviada notificación de rechazo - Informe: {}, Notificación ID: {}", 
                       informe.getIdInformeRenovacion(), notificacion.getIdNotificacion());
        } catch (Exception e) {
            logger.error("Error al enviar notificación de rechazo", e);
        }
    }
    
    private void actualizarBandejasRelacionadas(RequerimientoRenovacion requerimiento, Contexto contexto) {
        try {
            logger.info("Actualizando bandejas relacionadas - Req: {}", requerimiento.getIdReqRenovacion());
        } catch (Exception e) {
            logger.error("Error al actualizar bandejas relacionadas", e);
        }
    }
}