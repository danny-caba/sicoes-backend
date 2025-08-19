package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import java.util.List;
import java.util.Map;

/**
 * Servicio para manejar los parámetros de renovación de contrato
 */
public interface ParametrosRenovacionService {
    
    /**
     * Lista los tipos de solicitudes disponibles
     * @return Lista de tipos de solicitudes
     */
    List<Map<String, Object>> listarTiposSolicitudes();
    
    /**
     * Lista los subsectores disponibles
     * @return Lista de subsectores
     */
    List<Map<String, Object>> listarSubsectores();
}