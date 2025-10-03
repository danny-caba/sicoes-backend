package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoAprobacionCampo;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.HistorialEstadoAprobacionCampoDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.HistorialAprobacionRenovacionService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Implementación del servicio para el registro del historial de aprobación de renovación de contratos.
 * 
 * Esta implementación replica la funcionalidad del trigger Oracle que automáticamente
 * registra en la tabla SICOES_TZ_HIST_EST_APROB_CAMPO cuando se inserta un nuevo
 * requerimiento de aprobación en SICOES_TC_REQ_APROBACION.
 * 
 * @author Sistema SICOES
 * @version 1.0
 * @since 2025-09-18
 */
@Service
@Transactional
public class HistorialAprobacionRenovacionImplService implements HistorialAprobacionRenovacionService {

    private static final Logger logger = LogManager.getLogger(HistorialAprobacionRenovacionImplService.class);

    @Autowired
    private HistorialEstadoAprobacionCampoDao historialEstadoAprobacionCampoDao;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void registrarHistorialAprobacionRenovacion(RequerimientoAprobacion requerimientoAprobacion, Contexto contexto) {
        try {
            // Validar parámetros de entrada
            validarParametrosEntrada(requerimientoAprobacion, contexto);

            // Verificar si tiene ID_ESTADO_LD (condición del trigger)
            if (requerimientoAprobacion.getEstado() == null) {
                logger.debug("RequerimientoAprobacion ID {} no tiene ID_ESTADO_LD, no se registra historial", 
                           requerimientoAprobacion.getIdReqAprobacion());
                return;
            }

            logger.info("Iniciando registro de historial para RequerimientoAprobacion ID: {}", 
                       requerimientoAprobacion.getIdReqAprobacion());

            // Crear la entidad del historial
            HistorialEstadoAprobacionCampo historial = crearHistorialEstadoAprobacion(requerimientoAprobacion, contexto);

            // Guardar el historial
            // HistorialEstadoAprobacionCampo historialGuardado = historialEstadoAprobacionCampoDao.save(historial);

            // logger.info("Historial registrado exitosamente con ID: {} para RequerimientoAprobacion ID: {}", 
            //            historialGuardado.getIdHistorialEstadoCampo(), requerimientoAprobacion.getIdReqAprobacion());

        } catch (Exception e) {
            logger.error("Error al registrar historial de aprobación para RequerimientoAprobacion ID: {}", 
                        requerimientoAprobacion != null ? requerimientoAprobacion.getIdReqAprobacion() : "null", e);
            throw new RuntimeException("Error al registrar historial de aprobación: " + e.getMessage(), e);
        }
    }

    @Override
    public HistorialEstadoAprobacionCampo registrarHistorialPreAprobacion(RequerimientoAprobacion aprobacion, Contexto contexto) {
        try {
            HistorialEstadoAprobacionCampo historial = new HistorialEstadoAprobacionCampo();
            historial.setIdReqAprobacion(aprobacion.getIdReqAprobacion());
            historial.setIdUsuario(contexto.getUsuario().getIdUsuario());
            historial.setDeEstadoAnteriorLd(aprobacion.getEstado().getIdListadoDetalle());
            historial.setEsRegistro(Constantes.ESTADO.ACTIVO);
            historial.setIdGrupoLd(aprobacion.getGrupo().getIdListadoDetalle());
            historial.setIdGrupoAprobadorLd(aprobacion.getIdGrupoAprobadorLd());
            historial.setFeFechaCambio(new Timestamp(new Date().getTime()));

            // return historialEstadoAprobacionCampoDao.save(historial);
            return historial;
        } catch (Exception e) {
            logger.error("Error al registrar historial de pre-aprobación para RequerimientoAprobacion ID: {}",
                        aprobacion != null ? aprobacion.getIdReqAprobacion() : "null", e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO);
        }
    }

    @Override
    public void registrarHistorialPostAprobacion(HistorialEstadoAprobacionCampo historial, RequerimientoAprobacion aprobacion, Contexto contexto) {
        try {
            HistorialEstadoAprobacionCampo historialDB = historialEstadoAprobacionCampoDao
                    .findById(historial.getIdHistorialEstadoCampo()).orElseThrow(
                            () -> new ValidacionException(Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO)
                    );
            historialDB.setDeEstadoNuevoLd(aprobacion.getEstado().getIdListadoDetalle());
            historialDB.setFeFechaCambio(new Timestamp(new Date().getTime()));
            AuditoriaUtil.setAuditoriaRegistro(aprobacion, contexto);
            // historialEstadoAprobacionCampoDao.save(historialDB);
        } catch (Exception e) {
            logger.error("Error al registrar historial de pre-aprobación para RequerimientoAprobacion ID: {}",
                    aprobacion != null ? aprobacion.getIdReqAprobacion() : "null", e);
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.ERROR_EN_SERVICIO);
        }
    }

    /**
     * Valida los parámetros de entrada del método.
     * 
     * @param requerimientoAprobacion El requerimiento de aprobación
     * @param contexto El contexto de la aplicación
     * @throws IllegalArgumentException Si algún parámetro es inválido
     */
    private void validarParametrosEntrada(RequerimientoAprobacion requerimientoAprobacion, Contexto contexto) {
        if (requerimientoAprobacion == null) {
            throw new IllegalArgumentException("El RequerimientoAprobacion no puede ser null");
        }

        if (requerimientoAprobacion.getIdReqAprobacion() == null) {
            throw new IllegalArgumentException("El ID del RequerimientoAprobacion no puede ser null");
        }

        if (contexto == null) {
            throw new IllegalArgumentException("El Contexto no puede ser null");
        }

        if (contexto.getUsuario() == null || contexto.getUsuario().getIdUsuario() == null) {
            throw new IllegalArgumentException("El usuario en el contexto no puede ser null");
        }
    }

    /**
     * Crea la entidad HistorialEstadoAprobacionCampo con los datos del RequerimientoAprobacion.
     * Replica la lógica del trigger Oracle.
     * 
     * @param requerimientoAprobacion El requerimiento de aprobación
     * @param contexto El contexto de la aplicación
     * @return La entidad historial creada
     */
    private HistorialEstadoAprobacionCampo crearHistorialEstadoAprobacion(
            RequerimientoAprobacion requerimientoAprobacion, Contexto contexto) {

        HistorialEstadoAprobacionCampo historial = new HistorialEstadoAprobacionCampo();

        // Campos según el trigger Oracle
        historial.setIdReqAprobacion(requerimientoAprobacion.getIdReqAprobacion());
        historial.setIdUsuario(requerimientoAprobacion.getUsuario().getIdUsuario());
        
        // DE_ESTADO_ANTERIOR_LD = NULL (no existía estado anterior en inserción)
        historial.setDeEstadoAnteriorLd(null);
        
        // DE_ESTADO_NUEVO_LD = ID_ESTADO_LD del requerimiento
        historial.setDeEstadoNuevoLd(requerimientoAprobacion.getEstado().getIdListadoDetalle());
        
        // ID_GRUPO_LD del requerimiento
        historial.setIdGrupoLd(requerimientoAprobacion.getGrupo().getIdListadoDetalle());
        
        // ID_GRUPO_APROBADOR_LD del requerimiento
        historial.setIdGrupoAprobadorLd(requerimientoAprobacion.getIdGrupoAprobadorLd());
        
        // FE_FECHA_CAMBIO = SYSDATE (se establece automáticamente en el constructor)
        historial.setFeFechaCambio(new Timestamp(new Date().getTime()));
        
        // ES_REGISTRO = '1' (activo)
        historial.setEsRegistro(Constantes.ESTADO.ACTIVO);

        // Campos de auditoría usando AuditoriaUtil
        AuditoriaUtil.setAuditoriaRegistro(historial, contexto);

        logger.debug("Historial creado - IdReqAprobacion: {}, IdUsuario: {}, EstadoNuevo: {}, GrupoLd: {}, GrupoAprobadorLd: {}",
                    historial.getIdReqAprobacion(), historial.getIdUsuario(), historial.getDeEstadoNuevoLd(),
                    historial.getIdGrupoLd(), historial.getIdGrupoAprobadorLd());

        return historial;
    }
}