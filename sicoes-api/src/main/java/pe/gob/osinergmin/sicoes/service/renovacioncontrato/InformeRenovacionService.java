package pe.gob.osinergmin.sicoes.service.renovacioncontrato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RechazoInformeDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.ActualizacionBandejaDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.HistorialAprobacionDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.HistorialAprobacionesResponse;
import pe.gob.osinergmin.sicoes.util.Contexto;

public interface InformeRenovacionService {

    public Page<InformeRenovacionDTO> buscar(String nuExpediente, String contratista, String estadoAprobacion, Pageable pageable, Contexto contexto);

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

    /**
     * Descarga un adjunto desde Alfresco usando el UUID
     * @param uuid UUID del archivo en Alfresco
     * @param contexto Contexto del usuario
     * @return Contenido del archivo como byte array
     */
    byte[] descargarAdjuntoDesdeAlfresco(String uuid, Contexto contexto);

    /**
     * Busca informes de renovación pendientes de aprobación con filtros
     * @param numeroExpediente Número de expediente
     * @param tipoSector Tipo de sector
     * @param tipoSubSector Tipo de subsector
     * @param nombreItem Nombre del item
     * @param razSocialSupervisora Razón social de la supervisora
     * @param estadoInforme Estado del informe
     * @param grupoAprobador Grupo aprobador
     * @param prioridad Prioridad del informe
     * @param fechaDesde Fecha desde
     * @param fechaHasta Fecha hasta
     * @param soloVencidos Solo informes vencidos
     * @param soloAsignados Solo informes asignados al usuario
     * @param pageable Configuración de paginación
     * @param contexto Contexto del usuario
     * @return Página de informes pendientes de aprobación
     */
    Page<InformeAprobacionResponseDTO> buscarInformesParaAprobar(
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
            Contexto contexto);

    /**
     * Lista el historial de aprobaciones de informes de renovación con filtros
     * @param numeroExpediente Número de expediente
     * @param tipoSector Tipo de sector
     * @param tipoSubSector Tipo de subsector
     * @param nombreItem Nombre del item
     * @param razSocialSupervisora Razón social de la supervisora
     * @param estadoFinal Estado final del proceso
     * @param tipoAccion Tipo de acción realizada
     * @param grupoAprobador Grupo aprobador
     * @param idUsuarioAccion ID del usuario que realizó la acción
     * @param fechaDesde Fecha desde
     * @param fechaHasta Fecha hasta
     * @param soloAprobados Solo registros aprobados
     * @param soloRechazados Solo registros rechazados
     * @param soloMisAcciones Solo acciones del usuario actual
     * @param pageable Configuración de paginación
     * @param contexto Contexto del usuario
     * @return Página de historial de aprobaciones
     */
    Page<HistorialAprobacionDTO> listarHistorialAprobaciones(
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
            Contexto contexto);
            
    /**
     * Lista el historial de aprobaciones con formato personalizado
     * @param documentoId ID del documento de contrato
     * @param fechaDesde Fecha desde
     * @param fechaHasta Fecha hasta
     * @param resultado Filtro por resultado
     * @param grupo Filtro por grupo
     * @param pageable Parámetros de paginación
     * @param contexto Contexto del usuario
     * @return Respuesta formateada con historial de aprobaciones
     */
    HistorialAprobacionesResponse listarHistorialAprobacionesFormateado(
            String documentoId,
            String fechaDesde,
            String fechaHasta,
            String resultado,
            String grupo,
            Pageable pageable,
            Contexto contexto);
}