package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.EliminarInvitacionDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.util.Contexto;

/**
 * Servicio para manejar las solicitudes de renovación de contrato
 */
public interface RenovacionesService {
    
    /**
     * Busca solicitudes de renovación con filtros
     * @param numeroExpediente Número de expediente
     * @param tipoSector Tipo de sector
     * @param tipoSubSector Tipo de subsector
     * @param nombreItem Nombre del item
     * @param estadoRequerimiento Estado del requerimiento
     * @param fechaDesde Fecha desde
     * @param fechaHasta Fecha hasta
     * @param pageable Configuración de paginación
     * @param contexto Contexto del usuario
     * @return Página de requerimientos de renovación
     */
    Page<RequerimientoRenovacion> buscarSolicitudesRenovacion(
            String numeroExpediente,
            String tipoSector,
            String tipoSubSector,
            String nombreItem,
            Integer estadoRequerimiento,
            String fechaDesde,
            String fechaHasta,
            Pageable pageable,
            Contexto contexto);
    
    /**
     * Lista invitaciones de renovación con filtros
     * @param numeroExpediente Número de expediente
     * @param nombreItem Nombre del item
     * @param estadoInvitacion Estado de la invitación
     * @param fechaDesde Fecha desde
     * @param fechaHasta Fecha hasta
     * @param pageable Configuración de paginación
     * @param contexto Contexto del usuario
     * @return Página de invitaciones
     */
    Page<RequerimientoInvitacion> listarInvitaciones(
            String numeroExpediente,
            String nombreItem,
            Integer estadoInvitacion,
            String fechaDesde,
            String fechaHasta,
            Pageable pageable,
            Contexto contexto);
    
    /**
     * Elimina una invitación de renovación de contrato
     * @param eliminarDTO DTO con los datos de la invitación a eliminar
     * @param contexto Contexto del usuario
     */
    void eliminarInvitacion(EliminarInvitacionDTO eliminarDTO, Contexto contexto);
}