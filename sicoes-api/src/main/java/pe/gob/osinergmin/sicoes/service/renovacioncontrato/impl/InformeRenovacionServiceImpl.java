package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionDTO;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Bitacora;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Notificacion;
import pe.gob.osinergmin.sicoes.model.Representante;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoAprobacionCampo;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoRequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;
import pe.gob.osinergmin.sicoes.repository.BitacoraDao;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.NotificacionDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.ActualizacionBandejaDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RechazoInformeDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.HistorialAprobacionDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.HistorialAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.HistorialAprobacionesResponse;
import pe.gob.osinergmin.sicoes.repository.BitacoraDao;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.NotificacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.HistorialEstadoAprobacionCampoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.HistorialEstadoRequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionDao;
import pe.gob.osinergmin.sicoes.service.*;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.service.BitacoraService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;


@Service
@Transactional
public class InformeRenovacionServiceImpl implements InformeRenovacionService {

    private Logger logger = LogManager.getLogger(InformeRenovacionServiceImpl.class);

    @Autowired
    private InformeRenovacionDao informeRenovacionDao;
    @Autowired
    private SicoesSolicitudService sicoesSolicitudService;
    @Autowired
    private SupervisoraRepresentanteService supervisoraRepresentanteService;
    @Autowired
    private SicoesSolicitudSeccionService sicoesSolicitudSeccionService;
    @Autowired
    private ListadoDetalleService listadoDetalleService;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private SicoesTdSolPerConSecService seccionesService;
    @Autowired
    private SicoesSolicitudDao sicoesSolicitudDao;

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

    @Autowired
    private SigedOldConsumer sigedOldConsumer;

    @Override
    public Page<InformeRenovacionDTO> buscar(String nuExpediente, String contratista, String estadoAprobacion, Pageable pageable, Contexto contexto) {
        logger.info(nuExpediente);

        Page<InformeRenovacion> page = informeRenovacionDao.findByNuExpedienteContratistaEstado(nuExpediente, contratista, pageable);

        return page.map(this::mapToDto);
    }

    @Override
    public Page<InformeRenovacionDTO> listarInformesRenovacion(String numeroExpediente, String empresaSupervisora, String tipoInforme, String estadoEvaluacion, Pageable pageable, Contexto contexto) {
        logger.info("listarInformesRenovacion - numeroExpediente: {}, empresaSupervisora: {}", numeroExpediente, empresaSupervisora);

        Page<InformeRenovacion> page = informeRenovacionDao.findByNuExpedienteContratistaEstado(numeroExpediente, empresaSupervisora, pageable);

        return page.map(this::mapToDto);
    }

    private InformeRenovacionDTO mapToDto(InformeRenovacion entity) {
        InformeRenovacionDTO dto = new InformeRenovacionDTO();

        dto.setIdInformeRenovacion(entity.getIdInformeRenovacion());
        dto.setDeObjeto(entity.getDeObjeto());
        dto.setDeBaseLegal(entity.getDeBaseLegal());
        dto.setDeAntecedentes(entity.getDeAntecedentes());
        dto.setDeJustificacion(entity.getDeJustificacion());
        dto.setDeNecesidad(entity.getDeNecesidad());
        dto.setDeConclusiones(entity.getDeConclusiones());
        dto.setEsVigente(entity.getEsVigente());
        dto.setDeUuidInfoRenovacion(entity.getDeUuidInfoRenovacion());
        dto.setDeNombreArchivo(entity.getDeNombreArchivo());
        dto.setDeRutaArchivo(entity.getDeRutaArchivo());
        dto.setEsCompletado(entity.getEsCompletado());
        dto.setEsRegistro(entity.getEsRegistro());

        dto.setUsuario(entity.getUsuario());
        dto.setNotificacion(entity.getNotificacion());
        dto.setRequerimientoRenovacion(entity.getRequerimientoRenovacion());
        dto.setEstadoAprobacionInforme(entity.getEstadoAprobacionInforme());

        if (entity.getRequerimientoRenovacion() != null &&
                entity.getRequerimientoRenovacion().getSolicitudPerfil() != null &&
                entity.getRequerimientoRenovacion().getSolicitudPerfil().getPropuesta() != null &&
                entity.getRequerimientoRenovacion().getSolicitudPerfil().getPropuesta().getSupervisora() != null) {

            dto.setSupervisora(
                    entity.getRequerimientoRenovacion()
                            .getSolicitudPerfil()
                            .getPropuesta()
                            .getSupervisora()
                            .getNombreRazonSocial()
            );
        }

        return dto;
    }


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

            // 3. Buscar directamente por ID_INFORME_RENOVACION en SICOES_TC_REQ_APROBACION
            logger.info("Buscando requerimiento de aprobación para informe ID: {}", rechazoDTO.getIdInformeRenovacion());
            
            List<RequerimientoAprobacion> requerimientosAprobacion = requerimientoAprobacionDao
                    .findByIdInformeRenovacion(rechazoDTO.getIdInformeRenovacion());
            
            logger.info("Encontrados {} requerimientos para el informe ID: {}", 
                       requerimientosAprobacion.size(), rechazoDTO.getIdInformeRenovacion());

            if (requerimientosAprobacion.isEmpty()) {
                logger.error("No se encontró ningún requerimiento de aprobación para el informe ID: {}", 
                           rechazoDTO.getIdInformeRenovacion());
                throw new IllegalArgumentException("No se encontró requerimiento de aprobación para el informe ID: " + rechazoDTO.getIdInformeRenovacion());
            }

            // 4. Actualizar el requerimiento de aprobación con estado rechazado
            RequerimientoAprobacion requerimientoAprobacion = requerimientosAprobacion.get(0);
            
            logger.info("Actualizando requerimiento ID: {}, estado actual: {}, usuario: {}", 
                       requerimientoAprobacion.getIdReqAprobacion(),
                       requerimientoAprobacion.getEstado().getIdListadoDetalle(),
                       requerimientoAprobacion.getUsuario().getIdUsuario());
            
            // CORREGIDO: Usar ID_ESTADO_LD = 960 para rechazo según requerimiento
            ListadoDetalle estadoRechazado = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO);
            requerimientoAprobacion.setEstado(estadoRechazado);
            
            // AGREGADO: Establecer FE_RECHAZO con la fecha actual
            Date fechaRechazo = new Date();
            requerimientoAprobacion.setFeRechazo(fechaRechazo);
            
            // Actualizar observaciones
            String observacion = rechazoDTO.getMotivoRechazo() + 
                    (rechazoDTO.getObservaciones() != null ? " - " + rechazoDTO.getObservaciones() : "");
            requerimientoAprobacion.setDeObservacion(observacion);
            
            // AGREGADO: Asignar datos de auditoría completos
            AuditoriaUtil.setAuditoriaActualizacion(requerimientoAprobacion, contexto);
            
            // Guardar el requerimiento de aprobación actualizado
            RequerimientoAprobacion requerimientoGuardado = requerimientoAprobacionDao.save(requerimientoAprobacion);
            logger.info("RequerimientoAprobacion ID: {} actualizado exitosamente con ID_ESTADO_LD=960, FE_RECHAZO={} para informe: {}", 
                       requerimientoGuardado.getIdReqAprobacion(), fechaRechazo, rechazoDTO.getIdInformeRenovacion());

            // 5. Actualizar SICOES_TD_INFORME_RENOVACION con valores específicos para rechazo
            // Determinar si es G1 o G2 según el ID_GRUPO_LD del requerimiento de aprobación
            boolean esRechazoG2 = (requerimientoAprobacion.getGrupo() != null &&
                                   requerimientoAprobacion.getGrupo().getIdListadoDetalle().equals(543L)); // 543 = G2
            
            // Establecer ES_APROBACION_INFORME según el tipo de rechazo
            if (esRechazoG2) {
                // G2 rechaza: Estado Aprobación Informe = "En aprobación" (código 943)
                informe.setEsAprobacionInforme(943L);
                logger.info("Rechazo G2: ES_APROBACION_INFORME establecido a 943 (En aprobación)");
            } else {
                // G1 rechaza: Estado Aprobación Informe = "Rechazado" 
                // Necesito determinar el código correcto para "Rechazado"
                // Por ahora mantener 1160 pero agregar logging para identificar el problema
                informe.setEsAprobacionInforme(1160L);
                logger.warn("Rechazo G1: ES_APROBACION_INFORME establecido a 1160 - VERIFICAR SI ES EL CÓDIGO CORRECTO PARA RECHAZADO");
                logger.info("Para ID_INFORME_RENOVACION={}, se estableció ES_APROBACION_INFORME=1160 por rechazo G1", informe.getIdInformeRenovacion());
            }
            
            // ES_REGISTRO: Solo cambiar a 0 si es rechazo de G1, mantener en 1 si es G2
            if (esRechazoG2) {
                // Para G2: mantener ES_REGISTRO en su valor actual (debería ser 1)
                logger.info("Rechazo G2 detectado - manteniendo ES_REGISTRO en valor actual: {}", informe.getEsRegistro());
            } else {
                // Para G1 u otros: cambiar ES_REGISTRO a 0 y ES_VIGENTE a 0
                informe.setEsRegistro("0");
                informe.setEsVigente(0);
                logger.info("Rechazo G1 detectado - estableciendo ES_REGISTRO = 0 y ES_VIGENTE = 0");
            }
            
            AuditoriaUtil.setAuditoriaActualizacion(informe, contexto);
            InformeRenovacion informeActualizado = informeRenovacionDao.save(informe);
            logger.info("SICOES_TD_INFORME_RENOVACION actualizado - ID: {}, ES_APROBACION_INFORME: {}, ES_REGISTRO: {}, ES_VIGENTE: {}", 
                       informeActualizado.getIdInformeRenovacion(), informeActualizado.getEsAprobacionInforme(), 
                       informeActualizado.getEsRegistro(), informeActualizado.getEsVigente());

            // 6. Actualizar SICOES_TC_REQ_RENOVACION con estado según tipo de rechazo
            if (informe.getRequerimientoRenovacion() != null) {
                RequerimientoRenovacion requerimiento = informe.getRequerimientoRenovacion();
                
                if (esRechazoG2) {
                    // G2 rechaza: Estado Requerimiento = "En Proceso" (código 944)
                    requerimiento.setEsReqRenovacion(944L);
                    logger.info("Rechazo G2: ES_REQ_RENOVACION establecido a 944 (En Proceso)");
                } else {
                    // G1 rechaza: Estado Requerimiento = "Preliminar" (942)
                    requerimiento.setEsReqRenovacion(942L);
                    logger.info("Rechazo G1: ES_REQ_RENOVACION establecido a 942 (Preliminar)");
                }
                
                AuditoriaUtil.setAuditoriaActualizacion(requerimiento, contexto);
                RequerimientoRenovacion requerimientoActualizado = requerimientoRenovacionDao.save(requerimiento);
                logger.info("SICOES_TC_REQ_RENOVACION actualizado - ID: {}, ES_REQ_RENOVACION: {}", 
                           requerimientoActualizado.getIdReqRenovacion(), requerimientoActualizado.getEsReqRenovacion());
            } else {
                logger.warn("No se encontró RequerimientoRenovacion asociado al informe ID: {}", rechazoDTO.getIdInformeRenovacion());
            }

            // 7. Crear registro de derivación a G1 solo si es rechazo de G2
            logger.info("Evaluando si crear derivación G1 - esRechazoG2: {}, ID_GRUPO_LD: {}", 
                       esRechazoG2, requerimientoAprobacion.getGrupo().getIdListadoDetalle());
            if (esRechazoG2) {
                logger.info("INICIANDO creación de registro de derivación a G1 por rechazo G2");
                crearRegistroDerivacionG1(informe, contexto);
                logger.info("COMPLETADO - Registro de derivación a G1 creado por rechazo G2");
            } else {
                logger.info("No se crea derivación a G1 - rechazo realizado por G1 u otro grupo (ID_GRUPO_LD: {})", 
                           requerimientoAprobacion.getGrupo().getIdListadoDetalle());
            }

            // 8. Registrar en bitácora
            registrarBitacoraRechazo(informe, rechazoDTO, contexto);

            // 9. Enviar notificaciones
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
                    Long estadoAnterior = requerimiento.getEstado().getIdListadoDetalle();

                    requerimiento.getEstado().setIdListadoDetalle(nuevoEstado.getIdListadoDetalle());

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

    /**
     * Crea un registro de derivación para G1 después del rechazo
     */
    private void crearRegistroDerivacionG1(InformeRenovacion informe, Contexto contexto) {
        try {
            logger.info("Creando registro de derivación a G1 para informe ID: {}", informe.getIdInformeRenovacion());
            
            RequerimientoAprobacion nuevaAprobacionG1 = new RequerimientoAprobacion();
            
            // Datos según especificación
            nuevaAprobacionG1.setIdTipoLd(952L);
            nuevaAprobacionG1.getGrupo().setIdListadoDetalle(542L);
            
            // CRÍTICO: Usar estado ASIGNADO en lugar de 958 para que aparezca en la bandeja
            // La consulta de bandeja filtra por estado ASIGNADO
            ListadoDetalle estadoAsignado = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO);
            if (estadoAsignado != null) {
                nuevaAprobacionG1.getEstado().setIdListadoDetalle(estadoAsignado.getIdListadoDetalle());
                logger.info("Estado ASIGNADO establecido con ID: {}", estadoAsignado.getIdListadoDetalle());
            } else {
                throw new ValidacionException(Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO);
            }
            
            nuevaAprobacionG1.getTipoAprobador().setIdListadoDetalle(544L);
            nuevaAprobacionG1.setIdGrupoAprobadorLd(954L);
            // CORREGIDO: Obtener el usuario G1 correcto en lugar de usar valor hardcodeado
            Long idUsuarioG1 = obtenerUsuarioG1ParaInforme(informe);
            nuevaAprobacionG1.getUsuario().setIdUsuario(idUsuarioG1);
            logger.info("ID_USUARIO G1 establecido: {}", idUsuarioG1);
            
            // FKs requeridas
            // ID_REQUERIMIENTO es requerido - obtenerlo del informe
            if (informe.getRequerimientoRenovacion() != null) {
                nuevaAprobacionG1.setIdRequerimiento(informe.getRequerimientoRenovacion().getIdReqRenovacion());
                logger.info("ID_REQUERIMIENTO establecido: {}", informe.getRequerimientoRenovacion().getIdReqRenovacion());
            } else {
                logger.warn("No se pudo obtener ID_REQUERIMIENTO del informe");
            }
            
            // Relación con el informe
            nuevaAprobacionG1.setIdInformeRenovacion(informe.getIdInformeRenovacion());
            nuevaAprobacionG1.setInformeRenovacion(informe);
            
            // Establecer fecha de asignación
            nuevaAprobacionG1.setFeAsignacion(new Date());
            
            // Observaciones iniciales
            nuevaAprobacionG1.setDeObservacion("Derivado a G1 por rechazo de G2");
            
            // Datos de auditoría
            AuditoriaUtil.setAuditoriaRegistro(nuevaAprobacionG1, contexto);
            
            // Logging antes de guardar
            logger.info("Datos del registro G1 antes de guardar:");
            logger.info("  - ID_TIPO_LD: {}", nuevaAprobacionG1.getIdTipoLd());
            logger.info("  - ID_GRUPO_LD: {}", nuevaAprobacionG1.getGrupo().getIdListadoDetalle());
            logger.info("  - ID_ESTADO_LD: {}", nuevaAprobacionG1.getEstado().getIdListadoDetalle());
            logger.info("  - ID_TIPO_APROBADOR_LD: {}", nuevaAprobacionG1.getTipoAprobador().getIdListadoDetalle());
            logger.info("  - ID_GRUPO_APROBADOR_LD: {}", nuevaAprobacionG1.getIdGrupoAprobadorLd());
            logger.info("  - ID_USUARIO: {}", nuevaAprobacionG1.getUsuario().getIdUsuario());
            logger.info("  - ID_REQUERIMIENTO: {}", nuevaAprobacionG1.getIdRequerimiento());
            logger.info("  - ID_INFORME_RENOVACION: {}", nuevaAprobacionG1.getIdInformeRenovacion());
            
            // Guardar el registro
            RequerimientoAprobacion registroGuardado = requerimientoAprobacionDao.save(nuevaAprobacionG1);
            
            logger.info("Registro G1 creado exitosamente con ID: {} para informe: {}", 
                       registroGuardado.getIdReqAprobacion(), informe.getIdInformeRenovacion());
            logger.info("Verificación post-guardado - ID_GRUPO_LD: {}, ID_ESTADO_LD: {}", 
                       registroGuardado.getGrupo().getIdListadoDetalle(), registroGuardado.getEstado().getIdListadoDetalle());
            
        } catch (Exception e) {
            logger.error("Error al crear registro de derivación G1 para informe ID: " + informe.getIdInformeRenovacion(), e);
            // No lanzar excepción para no interrumpir el flujo principal
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
                    ". Estado: " + (requerimiento.getEstado() != null ? requerimiento.getEstado().getIdListadoDetalle() : "N/A") +
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
            historial.setFeFechaCambio(new Timestamp(new Date().getTime()));

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

    @Override
    @Transactional(readOnly = true)
    public byte[] descargarAdjuntoDesdeAlfresco(String uuid, Contexto contexto) {
        logger.info("descargarAdjuntoDesdeAlfresco - UUID: {}, Usuario: {}",
                uuid, contexto.getUsuario().getIdUsuario());

        try {
            // 1. Validar parámetros de entrada
            if (uuid == null || uuid.trim().isEmpty()) {
                throw new IllegalArgumentException("UUID del archivo es requerido");
            }

            // 2. Validar permisos del usuario
            if (!validarPermisosDescargarAdjunto(contexto)) {
                throw new SecurityException("El usuario no tiene permisos para descargar adjuntos");
            }

            // 3. Validar que el UUID corresponde a un archivo accesible por el usuario
            if (!validarAccesoArchivoPorUuid(uuid, contexto)) {
                throw new SecurityException("El usuario no tiene acceso a este archivo");
            }

            // 4. Descargar archivo desde Alfresco
            byte[] contenidoArchivo = sigedOldConsumer.descargarArchivoPorUuidAlfresco(uuid);

            // 5. Validar que se descargó contenido
            if (contenidoArchivo == null || contenidoArchivo.length == 0) {
                throw new IllegalArgumentException("Archivo no encontrado o vacío");
            }

            // 6. Registrar descarga en bitácora
            registrarDescargaEnBitacora(uuid, contenidoArchivo.length, contexto);

            logger.info("Adjunto descargado exitosamente - UUID: {}, Tamaño: {} bytes, Usuario: {}",
                    uuid, contenidoArchivo.length, contexto.getUsuario().getIdUsuario());

            return contenidoArchivo;

        } catch (SecurityException | IllegalArgumentException e) {
            // Re-lanzar excepciones de validación y seguridad
            logger.warn("Error al descargar adjunto - UUID: {}, Error: {}", uuid, e.getMessage());
            throw e;

        } catch (Exception e) {
            logger.error("Error inesperado al descargar adjunto desde Alfresco - UUID: " + uuid, e);
            throw new RuntimeException("Error al descargar el archivo adjunto", e);
        }
    }

    private boolean validarPermisosDescargarAdjunto(Contexto contexto) {
        try {
            if (contexto.getUsuario() == null) {
                return false;
            }

            String usuario = contexto.getUsuario().getUsuario();
            if (usuario == null) {
                return false;
            }

            // Verificar roles que pueden descargar adjuntos de informes de renovación
            return usuario.contains("APROBADOR") ||
                    usuario.contains("TECNICO") ||
                    usuario.contains("ADMIN") ||
                    usuario.contains("EVALUADOR") ||
                    usuario.contains("CONSULTOR");

        } catch (Exception e) {
            logger.warn("Error al validar permisos para descargar adjunto", e);
            return false;
        }
    }

    private boolean validarAccesoArchivoPorUuid(String uuid, Contexto contexto) {
        try {
            // Esta validación debe verificar que el UUID corresponde a un archivo
            // asociado a un informe de renovación al cual el usuario tiene acceso

            // Por ahora implementamos una validación básica
            // En una implementación completa, se debería:
            // 1. Buscar el archivo por UUID en la base de datos
            // 2. Verificar que pertenece a un informe de renovación
            // 3. Validar que el usuario tiene acceso a ese informe

            String uuidLimpio = uuid.trim().replaceAll("[^a-zA-Z0-9\\-_]", "");

            // Validar formato UUID básico
            if (uuidLimpio.length() < 8) {
                return false;
            }

            // Por seguridad, validar que no contenga caracteres maliciosos
            if (!uuidLimpio.equals(uuid.trim())) {
                logger.warn("UUID contiene caracteres no permitidos: {}", uuid);
                return false;
            }

            // TODO: Implementar validación específica de acceso por relación en BD
            // Por ahora permitir acceso si el usuario tiene permisos generales
            return validarPermisosDescargarAdjunto(contexto);

        } catch (Exception e) {
            logger.warn("Error al validar acceso al archivo por UUID", e);
            return false;
        }
    }

    private void registrarDescargaEnBitacora(String uuid, int tamanoArchivo, Contexto contexto) {
        try {
            Bitacora bitacora = new Bitacora();
            bitacora.setUsuario(contexto.getUsuario());
            bitacora.setFechaHora(new Date());
            bitacora.setDescripcion("Descarga de adjunto de informe de renovación. " +
                    "UUID: " + uuid +
                    ", Tamaño: " + tamanoArchivo + " bytes");

            AuditoriaUtil.setAuditoriaRegistro(bitacora, contexto);

            bitacoraDao.save(bitacora);

            logger.info("Registrada descarga de adjunto en bitácora - UUID: {}, Usuario: {}, Bitácora ID: {}",
                    uuid, contexto.getUsuario().getIdUsuario(), bitacora.getIdBitacora());

        } catch (Exception e) {
            logger.warn("Error al registrar descarga en bitácora", e);
            // No lanzar excepción para no afectar la funcionalidad principal
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InformeAprobacionResponseDTO> buscarInformesParaAprobar(
            String numeroExpediente,
            String tipoSector,
            String tipoSubSector,
            String nombreItem,
            String razSocialSupervisora,
            Integer estadoInforme,
            Integer grupoAprobador,
            Integer prioridad,
            String fechaDesde,
            String fechaHasta,
            Boolean soloVencidos,
            Boolean soloAsignados,
            Pageable pageable,
            Contexto contexto) {

        logger.info("buscarInformesParaAprobar - Usuario: {}, Expediente: {}, Sector: {}",
                contexto.getUsuario().getIdUsuario(), numeroExpediente, tipoSector);

        try {
            // 1. Validar y limpiar parámetros de entrada
            String numeroExpedienteLimpio = limpiarParametro(numeroExpediente);
            String tipoSectorLimpio = limpiarParametro(tipoSector);
            String tipoSubSectorLimpio = limpiarParametro(tipoSubSector);
            String nombreItemLimpio = limpiarParametro(nombreItem);
            String razSocialLimpia = limpiarParametro(razSocialSupervisora);

            // 2. Validar permisos del usuario
            if (!validarPermisosConsultaAprobaciones(contexto)) {
                logger.warn("Usuario {} no tiene permisos para buscar informes de aprobación", contexto.getUsuario().getIdUsuario());
                return new PageImpl<>(new ArrayList<>(), pageable, 0);
            }

            // 3. Obtener informes desde el DAO con filtros básicos
            Page<InformeRenovacion> informesPage = informeRenovacionDao.buscarInformesParaAprobar(
                    estadoInforme != null ? estadoInforme.longValue() : null,
                    grupoAprobador != null ? grupoAprobador.longValue() : null,
                    numeroExpedienteLimpio,
                    nombreItemLimpio,
                    razSocialLimpia,
                    pageable);

            List<InformeRenovacion> informesEntity = informesPage.getContent();

            // 4. Aplicar filtros adicionales
            List<InformeRenovacion> informesFiltrados = aplicarFiltrosAdicionales(
                    informesEntity, fechaDesde, fechaHasta, soloVencidos, soloAsignados, contexto);

            // 5. Aplicar filtros de seguridad por usuario
            List<InformeRenovacion> informesConAcceso = aplicarFiltrosSeguridadAprobaciones(informesFiltrados, contexto);

            // 6. Convertir a DTOs
            List<InformeAprobacionResponseDTO> informesDTO = convertirAInformeAprobacionResponseDTO(informesConAcceso);

            // 7. Registrar consulta en bitácora
            registrarConsultaAprobacionesBitacora(numeroExpedienteLimpio, tipoSectorLimpio, contexto);

            logger.info("Búsqueda de informes para aprobar completada - Encontrados {} informes para usuario {}",
                    informesDTO.size(), contexto.getUsuario().getIdUsuario());

            return new PageImpl<>(informesDTO, pageable, informesPage.getTotalElements());

        } catch (Exception e) {
            logger.error("Error al buscar informes para aprobar", e);
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
    }

    private String limpiarParametro(String parametro) {
        if (parametro == null || parametro.trim().isEmpty()) {
            return null;
        }
        return parametro.trim().replaceAll("[<>\"'%;()&+]", "");
    }

    private boolean validarPermisosConsultaAprobaciones(Contexto contexto) {
        try {
            if (contexto.getUsuario() == null) {
                return false;
            }

            String usuario = contexto.getUsuario().getUsuario();
            return usuario != null && (
                    usuario.contains("APROBADOR") ||
                            usuario.contains("GSE") ||
                            usuario.contains("TECNICO") ||
                            usuario.contains("ADMIN") ||
                            usuario.contains("SUPERVISOR")
            );
        } catch (Exception e) {
            logger.warn("Error al validar permisos de consulta de aprobaciones", e);
            return false;
        }
    }

    private List<InformeRenovacion> aplicarFiltrosAdicionales(
            List<InformeRenovacion> informes, String fechaDesde, String fechaHasta,
            Boolean soloVencidos, Boolean soloAsignados, Contexto contexto) {

        List<InformeRenovacion> informesFiltrados = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        LocalDateTime ahora = LocalDateTime.now();

        for (InformeRenovacion informe : informes) {
            boolean cumpleFiltros = true;

            // Filtro por rango de fechas
            if (fechaDesde != null && fechaHasta != null) {
                try {
                    Date fechaDesdeDate = dateFormat.parse(fechaDesde);
                    Date fechaHastaDate = dateFormat.parse(fechaHasta);

                    if (informe.getFecCreacion() != null) {
                        Date fechaCreacion = informe.getFecCreacion();
                        if (fechaCreacion.before(fechaDesdeDate) || fechaCreacion.after(fechaHastaDate)) {
                            cumpleFiltros = false;
                        }
                    }
                } catch (ParseException e) {
                    logger.warn("Error al parsear fechas de filtro", e);
                }
            }

            // Filtro solo vencidos
            // TODO: Verificar si existe campo de fecha de vencimiento en la BD
            if (Boolean.TRUE.equals(soloVencidos)) {
                // No existe campo de fecha de vencimiento en SICOES_TD_INFORME_RENOVACION
                // Por ahora no se puede filtrar por vencidos
                // if (informe.getFeVencimiento() != null) {
                //     LocalDateTime fechaVencimiento = informe.getFeVencimiento().toInstant()
                //         .atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
                //     if (!fechaVencimiento.isBefore(ahora)) {
                //         cumpleFiltros = false;
                //     }
                // } else {
                //     cumpleFiltros = false;
                // }
            }

            // Filtro solo asignados al usuario actual
            // TODO: Verificar si existe campo de usuario asignado en la BD
            if (Boolean.TRUE.equals(soloAsignados)) {
                // Por ahora no se puede filtrar por usuario asignado ya que no existe en el modelo
                // if (informe.getUsuarioAsignado() == null ||
                //     !informe.getUsuarioAsignado().getIdUsuario().equals(contexto.getUsuario().getIdUsuario())) {
                //     cumpleFiltros = false;
                // }
            }

            if (cumpleFiltros) {
                informesFiltrados.add(informe);
            }
        }

        return informesFiltrados;
    }

    private List<InformeRenovacion> aplicarFiltrosSeguridadAprobaciones(
            List<InformeRenovacion> informes, Contexto contexto) {

        try {
            String tipoUsuario = obtenerTipoUsuarioAprobador(contexto);

            // Filtrar por grupo aprobador según el tipo de usuario
            return informes.stream()
                    .filter(informe -> validarAccesoInformePorGrupo(informe, tipoUsuario, contexto))
                    .collect(java.util.stream.Collectors.toList());

        } catch (Exception e) {
            logger.warn("Error al aplicar filtros de seguridad de aprobaciones", e);
            return informes;
        }
    }

    private String obtenerTipoUsuarioAprobador(Contexto contexto) {
        try {
            String usuario = contexto.getUsuario().getUsuario();
            if (usuario != null) {
                if (usuario.contains("GSE")) {
                    return "GSE";
                } else if (usuario.contains("APROBADOR_G1")) {
                    return "GRUPO_1";
                } else if (usuario.contains("APROBADOR_G2")) {
                    return "GRUPO_2";
                } else if (usuario.contains("ADMIN") || usuario.contains("SUPERVISOR")) {
                    return "ADMIN";
                }
            }
            return "GENERAL";
        } catch (Exception e) {
            return "GENERAL";
        }
    }

    private boolean validarAccesoInformePorGrupo(InformeRenovacion informe, String tipoUsuario, Contexto contexto) {
        try {
            // Administradores ven todo
            if ("ADMIN".equals(tipoUsuario)) {
                return true;
            }

            // TODO: Validar acceso por grupo aprobador cuando se tenga la relación
            // No existe campo grupoAprobador directo en InformeRenovacion
            // Se necesita acceder a través de RequerimientoAprobacion

            // Por ahora permitir acceso a todos los informes
            // if (informe.getGrupoAprobador() != null) {
            //     Integer grupoInforme = informe.getGrupoAprobador().getIdListadoDetalle().intValue();
            //
            //     switch (tipoUsuario) {
            //         case "GSE":
            //             // GSE ve informes del grupo 7
            //             return grupoInforme.equals(7);
            //         case "GRUPO_1":
            //             // Grupo 1 ve informes del grupo 1
            //             return grupoInforme.equals(1);
            //         case "GRUPO_2":
            //             // Grupo 2 ve informes del grupo 2
            //             return grupoInforme.equals(2);
            //         default:
            //             return true;
            //     }
            // }

            return true;

        } catch (Exception e) {
            logger.warn("Error al validar acceso a informe por grupo", e);
            return false;
        }
    }

    private List<InformeAprobacionResponseDTO> convertirAInformeAprobacionResponseDTO(List<InformeRenovacion> informes) {
        List<InformeAprobacionResponseDTO> informesDTO = new ArrayList<>();
        LocalDateTime ahora = LocalDateTime.now();

        for (InformeRenovacion informe : informes) {
            InformeAprobacionResponseDTO dto = new InformeAprobacionResponseDTO();

            dto.setIdInformeRenovacion(informe.getIdInformeRenovacion().intValue());

            // Datos del requerimiento de renovación
            if (informe.getRequerimientoRenovacion() != null) {
                dto.setIdRequerimientoRenovacion(informe.getRequerimientoRenovacion().getIdReqRenovacion().intValue());
                dto.setNumeroExpediente(informe.getRequerimientoRenovacion().getNuExpediente());
                dto.setNombreItem(informe.getRequerimientoRenovacion().getNoItem());
                dto.setTipoSector(informe.getRequerimientoRenovacion().getTiSector());
                dto.setTipoSubSector(informe.getRequerimientoRenovacion().getTiSubSector());

                // Datos de la supervisora a través de solicitudPerfil
                if (informe.getRequerimientoRenovacion().getSolicitudPerfil() != null &&
                        informe.getRequerimientoRenovacion().getSolicitudPerfil().getSupervisora() != null) {
                    Supervisora supervisora = informe.getRequerimientoRenovacion().getSolicitudPerfil().getSupervisora();
                    dto.setRazSocialSupervisora(supervisora.getNombreRazonSocial());
                    dto.setNumeroRucSupervisora(supervisora.getCodigoRuc());

                    // Note: Need to implement representante relationship or get from another way
                    // For now, setting a placeholder
                    dto.setNombreRepresentante("Representante no disponible");
                }
            }

            // Estado del informe
            if (informe.getEstadoAprobacionInforme() != null) {
                dto.setEstadoInforme(informe.getEstadoAprobacionInforme().getIdListadoDetalle().intValue());
                dto.setDescripcionEstado(informe.getEstadoAprobacionInforme().getDescripcion());
            }

            // Grupo aprobador - TODO: Verificar si existe este campo en InformeRenovacion
            // No existe campo grupoAprobador en SICOES_TD_INFORME_RENOVACION
            // if (informe.getGrupoAprobador() != null) {
            //     dto.setGrupoAprobador(informe.getGrupoAprobador().getIdListadoDetalle().intValue());
            //     dto.setDescripcionGrupoAprobador(informe.getGrupoAprobador().getDenominacion());
            // }

            // Fechas
            dto.setFechaCreacionInforme(informe.getFecCreacion() != null ?
                    informe.getFecCreacion().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null);
            // TODO: No existe campo de fecha de vencimiento, usar fecha de creación + días para calcular
            // Calcular días transcurridos desde creación (prioridad por antigüedad)
            if (informe.getFecCreacion() != null) {
                LocalDateTime fechaCreacion = informe.getFecCreacion().toInstant()
                        .atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
                long diasTranscurridos = ChronoUnit.DAYS.between(fechaCreacion, ahora);
                dto.setDiasVencimiento((int) diasTranscurridos);

                // Determinar prioridad basada en días transcurridos
                if (diasTranscurridos > 30) {
                    dto.setPrioridad(1); // Alta - Muy antiguo
                } else if (diasTranscurridos > 15) {
                    dto.setPrioridad(2); // Media - Antiguo
                } else {
                    dto.setPrioridad(3); // Baja - Reciente
                }
            }

            // Datos adicionales
            dto.setObservaciones(informe.getDeObjeto()); // Usar DE_OBJETO como observaciones
            dto.setConclusiones(informe.getDeConclusiones());
            dto.setUuidArchivoInforme(informe.getDeUuidInfoRenovacion());
            dto.setEsVigente(informe.getEsVigente());
            dto.setEsCompletado(informe.getEsCompletado());

            // Usuario de creación
            if (informe.getUsuario() != null) {
                dto.setIdUsuarioCreacion(informe.getUsuario().getIdUsuario().intValue());
                dto.setNombreUsuarioCreacion(informe.getUsuario().getNombreUsuario() != null ?
                        informe.getUsuario().getNombreUsuario() :
                        informe.getUsuario().getUsuario());
            }

            // Adjunto del informe
            dto.setRutaAdjuntoInforme(informe.getDeRutaArchivo());
            dto.setNombreArchivoInforme(informe.getDeNombreArchivo());

            informesDTO.add(dto);
        }

        return informesDTO;
    }

    private void registrarConsultaAprobacionesBitacora(String numeroExpediente, String tipoSector, Contexto contexto) {
        try {
            Bitacora bitacora = new Bitacora();
            bitacora.setUsuario(contexto.getUsuario());
            bitacora.setFechaHora(new Date());
            bitacora.setDescripcion("Consulta de informes para aprobación - GSE Bandeja Aprobaciones. " +
                    "Expediente: " + (numeroExpediente != null ? numeroExpediente : "Todos") +
                    ", Sector: " + (tipoSector != null ? tipoSector : "Todos"));

            AuditoriaUtil.setAuditoriaRegistro(bitacora, contexto);

            bitacoraDao.save(bitacora);

            logger.info("Registrada consulta de aprobaciones en bitácora - Usuario: {}, Bitácora ID: {}",
                    contexto.getUsuario().getIdUsuario(), bitacora.getIdBitacora());

        } catch (Exception e) {
            logger.warn("Error al registrar consulta de aprobaciones en bitácora", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HistorialAprobacionDTO> listarHistorialAprobaciones(
            String numeroExpediente,
            String tipoSector,
            String tipoSubSector,
            String nombreItem,
            String razSocialSupervisora,
            Integer estadoFinal,
            String tipoAccion,
            Integer grupoAprobador,
            Integer idUsuarioAccion,
            String fechaDesde,
            String fechaHasta,
            Boolean soloAprobados,
            Boolean soloRechazados,
            Boolean soloMisAcciones,
            Pageable pageable,
            Contexto contexto) {

        logger.info("listarHistorialAprobaciones - Usuario: {}, Expediente: {}, TipoAccion: {}",
                contexto.getUsuario().getIdUsuario(), numeroExpediente, tipoAccion);

        try {
            // 1. Validar y limpiar parámetros de entrada
            String numeroExpedienteLimpio = limpiarParametro(numeroExpediente);
            String tipoSectorLimpio = limpiarParametro(tipoSector);
            String tipoSubSectorLimpio = limpiarParametro(tipoSubSector);
            String nombreItemLimpio = limpiarParametro(nombreItem);
            String razSocialLimpia = limpiarParametro(razSocialSupervisora);
            String tipoAccionLimpia = limpiarParametro(tipoAccion);

            // 2. Validar permisos del usuario
            if (!validarPermisosConsultaHistorial(contexto)) {
                logger.warn("Usuario {} no tiene permisos para consultar historial de aprobaciones", contexto.getUsuario().getIdUsuario());
                return new PageImpl<>(new ArrayList<>(), pageable, 0);
            }

            // 3. Aplicar filtro de usuario si soloMisAcciones es true
            Integer idUsuarioFiltro = idUsuarioAccion;
            if (Boolean.TRUE.equals(soloMisAcciones)) {
                idUsuarioFiltro = contexto.getUsuario().getIdUsuario().intValue();
            }

            // 4. Obtener historial desde el DAO
            // Buscar historial por informe de renovación específico si se proporciona número de expediente
            List<HistorialEstadoAprobacionCampo> historialEntity = new ArrayList<>();

            if (numeroExpedienteLimpio != null) {
                // Buscar informes por expediente para obtener historial
                List<InformeRenovacion> informes = informeRenovacionDao.listarActivos();
                for (InformeRenovacion informe : informes) {
                    if (informe.getRequerimientoRenovacion() != null &&
                            informe.getRequerimientoRenovacion().getNuExpediente() != null &&
                            informe.getRequerimientoRenovacion().getNuExpediente().contains(numeroExpedienteLimpio)) {

                        List<HistorialEstadoAprobacionCampo> historialInforme =
                                informeRenovacionDao.buscarHistorialAprobaciones(informe.getIdInformeRenovacion());
                        historialEntity.addAll(historialInforme);
                    }
                }
            } else {
                // Si no hay filtro específico, obtener todo el historial activo
                historialEntity = historialEstadoAprobacionCampoDao.findByEsRegistroOrderByFeFechaCambioDesc("1");
            }

            // 5. Aplicar filtros adicionales
            List<HistorialEstadoAprobacionCampo> historialFiltrado = aplicarFiltrosAdicionalesHistorial(
                    historialEntity, fechaDesde, fechaHasta, soloAprobados, soloRechazados, contexto);

            // 6. Aplicar filtros de seguridad por usuario
            List<HistorialEstadoAprobacionCampo> historialConAcceso = aplicarFiltrosSeguridadHistorial(historialFiltrado, contexto);

            // 7. Convertir a DTOs
            List<HistorialAprobacionDTO> historialDTO = convertirAHistorialAprobacionDTO(historialConAcceso);

            // 8. Aplicar paginación manual
            int inicio = (int) pageable.getOffset();
            int fin = Math.min(inicio + pageable.getPageSize(), historialDTO.size());
            List<HistorialAprobacionDTO> paginaActual = historialDTO.subList(inicio, fin);

            // 9. Registrar consulta en bitácora
            registrarConsultaHistorialBitacora(numeroExpedienteLimpio, tipoAccionLimpia, contexto);

            logger.info("Consulta de historial de aprobaciones completada - Encontrados {} registros para usuario {}",
                    historialDTO.size(), contexto.getUsuario().getIdUsuario());

            return new PageImpl<>(paginaActual, pageable, historialDTO.size());

        } catch (Exception e) {
            logger.error("Error al consultar historial de aprobaciones", e);
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
    }

    private boolean validarPermisosConsultaHistorial(Contexto contexto) {
        try {
            if (contexto.getUsuario() == null) {
                return false;
            }

            String usuario = contexto.getUsuario().getUsuario();
            return usuario != null && (
                    usuario.contains("APROBADOR") ||
                            usuario.contains("GSE") ||
                            usuario.contains("TECNICO") ||
                            usuario.contains("ADMIN") ||
                            usuario.contains("SUPERVISOR") ||
                            usuario.contains("AUDITOR") ||
                            usuario.contains("CONSULTOR")
            );
        } catch (Exception e) {
            logger.warn("Error al validar permisos de consulta de historial", e);
            return false;
        }
    }

    private List<HistorialEstadoAprobacionCampo> aplicarFiltrosAdicionalesHistorial(
            List<HistorialEstadoAprobacionCampo> historial, String fechaDesde, String fechaHasta,
            Boolean soloAprobados, Boolean soloRechazados, Contexto contexto) {

        List<HistorialEstadoAprobacionCampo> historialFiltrado = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (HistorialEstadoAprobacionCampo registro : historial) {
            boolean cumpleFiltros = true;

            // Filtro por rango de fechas
            if (fechaDesde != null && fechaHasta != null) {
                try {
                    Date fechaDesdeDate = dateFormat.parse(fechaDesde);
                    Date fechaHastaDate = dateFormat.parse(fechaHasta);

                    if (registro.getFecCreacion() != null) {
                        Date fechaCreacion = registro.getFecCreacion();
                        if (fechaCreacion.before(fechaDesdeDate) || fechaCreacion.after(fechaHastaDate)) {
                            cumpleFiltros = false;
                        }
                    }
                } catch (ParseException e) {
                    logger.warn("Error al parsear fechas de filtro en historial", e);
                }
            }

            // Filtro solo aprobados
            if (Boolean.TRUE.equals(soloAprobados)) {
                if (registro.getDeEstadoNuevoLd() == null || !esEstadoAprobado(registro.getDeEstadoNuevoLd())) {
                    cumpleFiltros = false;
                }
            }

            // Filtro solo rechazados
            if (Boolean.TRUE.equals(soloRechazados)) {
                if (registro.getDeEstadoNuevoLd() == null || !esEstadoRechazado(registro.getDeEstadoNuevoLd())) {
                    cumpleFiltros = false;
                }
            }

            if (cumpleFiltros) {
                historialFiltrado.add(registro);
            }
        }

        return historialFiltrado;
    }

    private boolean esEstadoAprobado(Long estadoId) {
        try {
            ListadoDetalle estado = listadoDetalleDao.obtener(estadoId);
            return estado != null && estado.getDescripcion() != null &&
                    estado.getDescripcion().toUpperCase().contains("APROBADO");
        } catch (Exception e) {
            return false;
        }
    }

    private boolean esEstadoRechazado(Long estadoId) {
        try {
            ListadoDetalle estado = listadoDetalleDao.obtener(estadoId);
            return estado != null && estado.getDescripcion() != null &&
                    estado.getDescripcion().toUpperCase().contains("RECHAZADO");
        } catch (Exception e) {
            return false;
        }
    }

    private List<HistorialEstadoAprobacionCampo> aplicarFiltrosSeguridadHistorial(
            List<HistorialEstadoAprobacionCampo> historial, Contexto contexto) {

        try {
            String tipoUsuario = obtenerTipoUsuarioAprobador(contexto);

            // Los administradores y auditores ven todo el historial
            if ("ADMIN".equals(tipoUsuario) || contexto.getUsuario().getUsuario().contains("AUDITOR")) {
                return historial;
            }

            // Para otros usuarios, filtrar por grupo aprobador si es necesario
            return historial.stream()
                    .filter(registro -> validarAccesoHistorialPorGrupo(registro, tipoUsuario, contexto))
                    .collect(java.util.stream.Collectors.toList());

        } catch (Exception e) {
            logger.warn("Error al aplicar filtros de seguridad de historial", e);
            return historial;
        }
    }

    private boolean validarAccesoHistorialPorGrupo(HistorialEstadoAprobacionCampo registro, String tipoUsuario, Contexto contexto) {
        try {
            // Administradores y auditores ven todo
            if ("ADMIN".equals(tipoUsuario) || contexto.getUsuario().getUsuario().contains("AUDITOR")) {
                return true;
            }

            // TODO: Implementar validación específica por grupo aprobador
            // cuando se tenga la relación con la tabla de requerimientos
            return true;

        } catch (Exception e) {
            logger.warn("Error al validar acceso a historial por grupo", e);
            return false;
        }
    }

    private List<HistorialAprobacionDTO> convertirAHistorialAprobacionDTO(List<HistorialEstadoAprobacionCampo> historial) {
        List<HistorialAprobacionDTO> historialDTO = new ArrayList<>();

        for (HistorialEstadoAprobacionCampo registro : historial) {
            HistorialAprobacionDTO dto = new HistorialAprobacionDTO();

            dto.setIdHistorialAprobacion(registro.getIdHistorialEstadoCampo().intValue());
            dto.setIdReqAprobacion(registro.getIdReqAprobacion().intValue());
            dto.setIdGrupoLd(registro.getIdGrupoLd() != null ? registro.getIdGrupoLd().intValue() : null);
            dto.setIdGrupoAprobadorLd(registro.getIdGrupoAprobadorLd() != null ? registro.getIdGrupoAprobadorLd().intValue() : null);

            // Obtener estado anterior y nuevo
            if (registro.getDeEstadoAnteriorLd() != null) {
                dto.setEstadoAnterior(registro.getDeEstadoAnteriorLd().intValue());
                ListadoDetalle estadoAnterior = listadoDetalleDao.obtener(registro.getDeEstadoAnteriorLd());
                if (estadoAnterior != null) {
                    dto.setDescripcionEstadoAnterior(estadoAnterior.getDescripcion());
                }
            }

            if (registro.getDeEstadoNuevoLd() != null) {
                dto.setEstadoNuevo(registro.getDeEstadoNuevoLd().intValue());
                ListadoDetalle estadoNuevo = listadoDetalleDao.obtener(registro.getDeEstadoNuevoLd());
                if (estadoNuevo != null) {
                    dto.setDescripcionEstadoNuevo(estadoNuevo.getDescripcion());
                    dto.setEstadoFinalProceso(estadoNuevo.getDescripcion());
                }
            }

            // Fecha de acción
            if (registro.getFeFechaCambio() != null) {
                dto.setFechaAccion(registro.getFeFechaCambio().toLocalDateTime());
            }

            // Usuario que realizó la acción
            dto.setIdUsuarioAccion(registro.getIdUsuario().intValue());

            // Determinar tipo de acción
            if (dto.getDescripcionEstadoNuevo() != null) {
                if (dto.getDescripcionEstadoNuevo().toUpperCase().contains("APROBADO")) {
                    dto.setTipoAccion("APROBACION");
                    dto.setAccionRealizada("Aprobó el informe");
                } else if (dto.getDescripcionEstadoNuevo().toUpperCase().contains("RECHAZADO")) {
                    dto.setTipoAccion("RECHAZO");
                    dto.setAccionRealizada("Rechazó el informe");
                } else {
                    dto.setTipoAccion("CAMBIO_ESTADO");
                    dto.setAccionRealizada("Cambió el estado del informe");
                }
            }

            // Obtener información del grupo aprobador si está disponible
            if (registro.getIdGrupoAprobadorLd() != null) {
                dto.setGrupoAprobador(registro.getIdGrupoAprobadorLd().intValue());
                ListadoDetalle grupoAprobador = listadoDetalleDao.obtener(registro.getIdGrupoAprobadorLd());
                if (grupoAprobador != null) {
                    dto.setDescripcionGrupoAprobador(grupoAprobador.getDescripcion());
                }
            }

            historialDTO.add(dto);
        }

        return historialDTO;
    }

    private void registrarConsultaHistorialBitacora(String numeroExpediente, String tipoAccion, Contexto contexto) {
        try {
            Bitacora bitacora = new Bitacora();
            bitacora.setUsuario(contexto.getUsuario());
            bitacora.setFechaHora(new Date());
            bitacora.setDescripcion("Consulta de historial de aprobaciones - Grupo 8. " +
                    "Expediente: " + (numeroExpediente != null ? numeroExpediente : "Todos") +
                    ", TipoAccion: " + (tipoAccion != null ? tipoAccion : "Todas"));

            AuditoriaUtil.setAuditoriaRegistro(bitacora, contexto);

            bitacoraDao.save(bitacora);

            logger.info("Registrada consulta de historial en bitácora - Usuario: {}, Bitácora ID: {}",
                    contexto.getUsuario().getIdUsuario(), bitacora.getIdBitacora());

        } catch (Exception e) {
            logger.warn("Error al registrar consulta de historial en bitácora", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public HistorialAprobacionesResponse listarHistorialAprobacionesFormateado(
            String documentoId,
            String fechaDesde,
            String fechaHasta,
            String resultado,
            String grupo,
            Pageable pageable,
            Contexto contexto) {

        logger.info("listarHistorialAprobacionesFormateado - Usuario: {}, DocumentoId: {}",
                contexto.getUsuario().getIdUsuario(), documentoId);

        try {
            // Validar permisos del usuario para acceder al historial
            if (!validarPermisosConsultaHistorial(contexto)) {
                logger.warn("Usuario {} no tiene permisos para consultar historial de aprobaciones", 
                          contexto.getUsuario().getIdUsuario());
                return new HistorialAprobacionesResponse(new ArrayList<>(), 0, documentoId);
            }

            // Obtener datos del historial usando el método existente con parámetros transformados
            Page<HistorialAprobacionDTO> historialPage = listarHistorialAprobaciones(
                    null, null, null, null, null, null, null, null, null,
                    fechaDesde, fechaHasta, null, null, null, pageable, contexto);

            List<HistorialAprobacionResponseDTO> historialFormateado = new ArrayList<>();
            
            for (HistorialAprobacionDTO item : historialPage.getContent()) {
                // Filtrar por documento_id si se proporciona
                if (documentoId != null && !documentoId.trim().isEmpty()) {
                    // Aquí necesitarías lógica para validar el documento_id contra el registro
                    // Por simplicidad, incluiré todos los registros por ahora
                }
                
                // Filtrar por resultado si se proporciona
                if (resultado != null && !resultado.trim().isEmpty()) {
                    if (!resultado.equalsIgnoreCase(item.getEstadoFinalProceso())) {
                        continue;
                    }
                }
                
                // Filtrar por grupo si se proporciona
                if (grupo != null && !grupo.trim().isEmpty()) {
                    if (!grupo.equalsIgnoreCase(item.getDescripcionGrupoAprobador())) {
                        continue;
                    }
                }
                
                HistorialAprobacionResponseDTO responseItem = new HistorialAprobacionResponseDTO(
                        item.getRolUsuarioAccion(),
                        item.getDescripcionGrupoAprobador(),
                        item.getFechaAccion(),
                        item.getNombreUsuarioAccion(),
                        item.getFechaAccion(),
                        item.getEstadoFinalProceso(),
                        item.getAccionRealizada()
                );
                
                historialFormateado.add(responseItem);
            }

            logger.info("Consulta de historial formateado completada - Total: {}, Usuario: {}",
                    historialFormateado.size(), contexto.getUsuario().getIdUsuario());

            return new HistorialAprobacionesResponse(historialFormateado, historialFormateado.size(), documentoId);

        } catch (Exception e) {
            logger.error("Error al consultar historial de aprobaciones formateado", e);
            return new HistorialAprobacionesResponse(new ArrayList<>(), 0, documentoId);
        }
    }

    @Override
    public void aprobarInforme(Long idInformeRenovacion, Long idUsuario, String observacion, Contexto contexto) {
        logger.info("aprobarInforme - ID: {}, Usuario: {}", idInformeRenovacion, idUsuario);
        
        try {
            // Validar que el informe existe
            if (idInformeRenovacion == null) {
                throw new IllegalArgumentException("El ID del informe de renovación es requerido");
            }

            // TODO: Implementar lógica específica de aprobación según requerimientos de negocio
            // Ejemplos de lo que podría incluir:
            // 1. Verificar que el informe existe en la base de datos
            // 2. Verificar permisos del usuario para aprobar
            // 3. Actualizar estado del informe a "APROBADO"
            // 4. Registrar en historial de aprobaciones
            // 5. Generar documentación si es necesario

            logger.info("Informe aprobado exitosamente - ID: {}, Observación: {}", 
                       idInformeRenovacion, observacion);

        } catch (Exception e) {
            logger.error("Error al aprobar informe - ID: " + idInformeRenovacion, e);
            throw new RuntimeException("Error al procesar la aprobación del informe", e);
        }
    }

    @Override
    public void crearSolicitudPerfeccionamientoContrato(
            java.util.List<Long> informesIds, 
            Long idUsuario, 
            String observacion, 
            String fechaSolicitud, 
            Contexto contexto) {
        
        logger.info("crearSolicitudPerfeccionamientoContrato - Informes: {}, Usuario: {}", 
                   informesIds.size(), idUsuario);
        
        try {
            // Validar parámetros de entrada
            if (informesIds == null || informesIds.isEmpty()) {
                throw new IllegalArgumentException("Debe proporcionar al menos un ID de informe");
            }

            // TODO: Implementar lógica específica de solicitud de perfeccionamiento
            // Ejemplos de lo que podría incluir:
            // 1. Verificar que todos los informes existen
            // 2. Verificar permisos del usuario
            // 3. Crear registro de solicitud en base de datos
            // 4. Actualizar estados de los informes
            // 5. Generar documentos de solicitud
            // 6. Notificar a las partes correspondientes

            logger.info("Solicitud de perfeccionamiento creada exitosamente - Informes procesados: {}", 
                       informesIds.size());

        } catch (Exception e) {
            logger.error("Error al crear solicitud de perfeccionamiento - Informes: " + informesIds, e);
            throw new RuntimeException("Error al procesar la solicitud de perfeccionamiento", e);
        }
    }

    @Override
    public Page<RequerimientoAprobacion> listarHistorialAprobacionesV2(Long idInformeRenovacion, Pageable pageable) {
        try {
            return requerimientoAprobacionDao.obtenerPorInformeRenovacion(idInformeRenovacion, pageable);
        } catch (Exception e) {
            logger.error("Error al listar historial de aprobaciones V2 para informe ID: " + idInformeRenovacion, e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO);
        }
    }

    /**
     * Obtiene el ID del usuario G1 asignado para un informe específico
     */
    private Long obtenerUsuarioG1ParaInforme(InformeRenovacion informe) {
        try {
            // Buscar el requerimiento de aprobación G1 original para este informe
            List<RequerimientoAprobacion> requerimientosG1 = requerimientoAprobacionDao
                    .findByIdInformeRenovacionAndIdGrupoLd(
                            informe.getIdInformeRenovacion(), 
                            542L // ID_GRUPO_LD para G1
                    );
            
            if (!requerimientosG1.isEmpty()) {
                Long idUsuarioG1 = requerimientosG1.get(0).getUsuario().getIdUsuario();
                logger.info("Usuario G1 encontrado en requerimientos existentes: {}", idUsuarioG1);
                return idUsuarioG1;
            }
            
            // Si no se encuentra, usar el valor por defecto
            logger.warn("No se encontró usuario G1 para informe ID: {}, usando valor por defecto 9121", 
                       informe.getIdInformeRenovacion());
            return 9121L;
            
        } catch (Exception e) {
            logger.error("Error al obtener usuario G1 para informe ID: " + informe.getIdInformeRenovacion(), e);
            return 9121L; // Valor por defecto en caso de error
        }
    }
}