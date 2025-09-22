package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

/**
 * Servicio para el manejo del historial de estados de requerimientos de renovación
 * @author SICOES
 * @version 1.0
 */
public interface HistorialRequerimientoRenovacionService {

    /**
     * Registra el historial de estado de un requerimiento de renovación
     * Implementa la lógica equivalente al trigger IF INSERTING para SICOES_TZ_HIST_EST_REQ_RENOV
     * 
     * @param requerimientoRenovacion El requerimiento de renovación recién insertado
     * @param contexto Contexto de la aplicación con información del usuario
     * @throws Exception si ocurre algún error durante el registro del historial
     */
    void registrarHistorialRequerimientoRenovacion(RequerimientoRenovacion requerimientoRenovacion, Contexto contexto) throws Exception;
}