package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.TipoAprobadorResponseDTO;
import pe.gob.osinergmin.sicoes.util.Contexto;

/**
 * Servicio para identificar el tipo de aprobador de un usuario
 */
public interface TipoAprobadorService {
    
    /**
     * Identifica el tipo de aprobador basándose en los registros de RequerimientoAprobacion
     * asignados al usuario actual
     * 
     * @param contexto Contexto del usuario autenticado
     * @return DTO con la información del tipo de aprobador
     */
    TipoAprobadorResponseDTO identificarTipoAprobador(Contexto contexto);
}