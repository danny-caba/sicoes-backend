package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RechazoInformeDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.ActualizacionBandejaDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

/**
 * Servicio para manejar informes de renovación de contrato
 */
public interface InformeRenovacionService {
    
    /**
     * Rechaza un informe de renovación
     * @param rechazoDTO Datos del rechazo
     * @param contexto Contexto del usuario
     */
    void rechazarInforme(RechazoInformeDTO rechazoDTO, Contexto contexto);
    
    /**
     * Actualiza la bandeja de aprobaciones
     * @param actualizacionDTO Datos de actualización
     * @param contexto Contexto del usuario
     */
    void actualizarBandejaAprobaciones(ActualizacionBandejaDTO actualizacionDTO, Contexto contexto);
    
    /**
     * Actualiza la grilla de renovación de contrato
     * @param actualizacionDTO Datos de actualización
     * @param contexto Contexto del usuario
     */
    void actualizarGrillaRenovacionContrato(ActualizacionBandejaDTO actualizacionDTO, Contexto contexto);
}