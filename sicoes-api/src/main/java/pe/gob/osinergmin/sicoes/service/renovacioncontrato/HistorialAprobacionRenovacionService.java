package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

/**
 * Interfaz del servicio para el registro del historial de aprobación de renovación de contratos.
 * 
 * Este servicio se encarga de registrar automáticamente en la tabla SICOES_TZ_HIST_EST_APROB_CAMPO
 * cuando se inserta un nuevo registro en SICOES_TC_REQ_APROBACION.
 * 
 * @author Sistema SICOES
 * @version 1.0
 * @since 2025-09-18
 */
public interface HistorialAprobacionRenovacionService {

    /**
     * Registra el historial de aprobación después de insertar un nuevo requerimiento de aprobación.
     * 
     * Este método replica la funcionalidad del trigger Oracle que automáticamente
     * registra en la tabla SICOES_TZ_HIST_EST_APROB_CAMPO cuando se detecta
     * que un RequerimientoAprobacion tiene un ID_ESTADO_LD válido.
     * 
     * @param requerimientoAprobacion El requerimiento de aprobación que fue insertado
     * @param contexto Contexto de la aplicación con información del usuario y sesión
     * @throws IllegalArgumentException Si el requerimientoAprobacion es null o no tiene ID_ESTADO_LD
     * @throws RuntimeException Si ocurre un error durante el registro del historial
     */
    void registrarHistorialAprobacionRenovacion(RequerimientoAprobacion requerimientoAprobacion, Contexto contexto);
}