package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.BandejaAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.BandejaAprobacionMapper;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class BandejaAprobacionImplService {
    
    private static final Logger logger = LogManager.getLogger(BandejaAprobacionImplService.class);

    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Autowired
    private BandejaAprobacionMapper bandejaAprobacionMapper;

    @Autowired
    private ListadoDetalleService listadoDetalleService;





    public Page<BandejaAprobacionResponseDTO> listaApobaciones(
            String numeroExpediente,
            Long estadoAprobacionInforme,
            Long idContratista,
            String nombreContratista,
            Contexto contexto,
            Pageable pageable) {
        return listaApobaciones(numeroExpediente, estadoAprobacionInforme, idContratista, nombreContratista, null, contexto, pageable);
    }

    public Page<BandejaAprobacionResponseDTO> listaApobaciones(
            String numeroExpediente,
            Long estadoAprobacionInforme,
            Long idContratista,
            String nombreContratista,
            Integer grupoUsuario,
            Contexto contexto,
            Pageable pageable) {

        Long idUsuario = contexto.getUsuario().getIdUsuario();
        
        // MODIFICADO: Permitir que G2 vea informes rechazados por G1 (ES_VIGENTE = 0)
        // Determinar si el usuario es G2 - usar parámetro del frontend o detección automática
        boolean esUsuarioG2 = (grupoUsuario != null && grupoUsuario == 3) ? true : esUsuarioAprobadorG2(contexto);
        boolean esUsuarioG1 = !esUsuarioG2; // Si no es G2, entonces es G1
        Integer esVigente = esUsuarioG2 ? null : 1; // G2 ve todos, otros solo vigentes
        
        // DEBUG: Logs detallados para debug
        logger.warn("=== DEBUG USUARIO ===");
        logger.warn("Usuario ID: {}", idUsuario);
        logger.warn("grupoUsuario param: {}", grupoUsuario);
        logger.warn("esUsuarioG2: {}", esUsuarioG2);
        logger.warn("esUsuarioG1: {}", esUsuarioG1);
        logger.warn("esVigente: {}", esVigente);
        
        // DEBUG: Información completa del usuario
        if (contexto.getUsuario() != null) {
            logger.warn("Usuario nombre: {}", contexto.getUsuario().getNombre());
            logger.warn("Usuario email: {}", contexto.getUsuario().getEmail());
            
            if (contexto.getUsuario().getRoles() != null) {
                logger.warn("Roles del usuario:");
                contexto.getUsuario().getRoles().forEach(rol -> {
                    logger.warn("  - Rol ID: {}, Codigo: {}, Nombre: {}, Descripcion: {}", 
                        rol.getIdRol(), rol.getCodigo(), rol.getNombre(), rol.getDescripcion());
                });
            } else {
                logger.warn("Usuario no tiene roles asignados");
            }
        }
        logger.warn("===================");
        

        // Si se proporciona cualquier parámetro de búsqueda, usar búsqueda abierta
        Page<RequerimientoAprobacion> listaAprobaciones;
        boolean usarBusquedaAbierta = (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) ||
                                    (nombreContratista != null && !nombreContratista.trim().isEmpty()) ||
                                    (estadoAprobacionInforme != null);
        
        if (usarBusquedaAbierta) {
            // Debug específico para estado de aprobación
            if (estadoAprobacionInforme != null) {
                // Si se busca específicamente por estadoAprobacionInforme, usar consulta especializada según G1/G2
                if (esUsuarioG2) {
                    listaAprobaciones = requerimientoAprobacionDao.buscarPorEstadoAprobacionInformeSinFiltroUsuarioG2(
                            estadoAprobacionInforme,
                            numeroExpediente,
                            idContratista,
                            nombreContratista,
                            esVigente,
                            pageable
                    );
                } else {
                    listaAprobaciones = requerimientoAprobacionDao.buscarPorEstadoAprobacionInformeSinFiltroUsuario(
                            estadoAprobacionInforme,
                            numeroExpediente,
                            idContratista,
                            nombreContratista,
                            esVigente,
                            pageable
                    );
                }
            } else {
                // Búsqueda normal sin filtro de usuario según G1/G2
                if (esUsuarioG2) {
                    listaAprobaciones = requerimientoAprobacionDao.buscarSinFiltroUsuarioG2(
                            numeroExpediente,
                            estadoAprobacionInforme,
                            idContratista,
                            nombreContratista,
                            esVigente,
                            pageable
                    );
                } else {
                    listaAprobaciones = requerimientoAprobacionDao.buscarSinFiltroUsuario(
                            numeroExpediente,
                            estadoAprobacionInforme,
                            idContratista,
                            nombreContratista,
                            esVigente,
                            pageable
                    );
                }
            }
        } else {
            // Búsqueda normal filtrada por usuario (bandeja personal) según G1/G2
            
            if (estadoAprobacionInforme != null) {
                // Si se busca específicamente por estadoAprobacionInforme, usar consulta especializada según G1/G2
                if (esUsuarioG2) {
                    listaAprobaciones = requerimientoAprobacionDao.buscarPorEstadoAprobacionInformeG2(
                            estadoAprobacionInforme,
                            numeroExpediente,
                            idContratista,
                            nombreContratista,
                            esVigente,
                            pageable
                    );
                } else {
                    listaAprobaciones = requerimientoAprobacionDao.buscarPorEstadoAprobacionInforme(
                            estadoAprobacionInforme,
                            numeroExpediente,
                            idContratista,
                            nombreContratista,
                            idUsuario,
                            esVigente,
                            pageable
                    );
                }
            } else {
                // Búsqueda normal con filtro de usuario según G1/G2
                if (esUsuarioG2) {
                    logger.warn("DEBUG - Ejecutando buscarByIdUsuarioG2 para usuario {}", idUsuario);
                    listaAprobaciones = requerimientoAprobacionDao.buscarByIdUsuarioG2(
                            numeroExpediente,
                            estadoAprobacionInforme,
                            idContratista,
                            nombreContratista,
                            esVigente,
                            pageable
                    );
                } else {
                    logger.warn("DEBUG - Ejecutando buscarByIdUsuario para usuario {} (G1)", idUsuario);
                    listaAprobaciones = requerimientoAprobacionDao.buscarByIdUsuario(
                            numeroExpediente,
                            estadoAprobacionInforme,
                            idContratista,
                            nombreContratista,
                            idUsuario,
                            esVigente,
                            pageable
                    );
                }
            }
        }
        

        List<BandejaAprobacionResponseDTO> dtos = listaAprobaciones.getContent().stream()
                .map(entity -> bandejaAprobacionMapper.toDto(entity, contexto, listadoDetalleService))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, listaAprobaciones.getTotalElements());
    }
    
    /**
     * Determina si el usuario actual es un aprobador G2
     */
    private boolean esUsuarioAprobadorG2(Contexto contexto) {
        try {
            if (contexto.getUsuario() == null || contexto.getUsuario().getRoles() == null) {
                return false;
            }
            
            // Detectar G2 por múltiples criterios:
            return contexto.getUsuario().getRoles().stream().anyMatch(rol -> 
                // 1. Código "05" (rol genérico de aprobador)
                "05".equals(rol.getCodigo()) ||
                // 2. Cualquier rol que contenga "G2" en nombre o descripción
                (rol.getNombre() != null && rol.getNombre().toUpperCase().contains("G2")) ||
                (rol.getDescripcion() != null && rol.getDescripcion().toUpperCase().contains("G2")) ||
                // 3. Roles que contengan "GERENTE" en nombre o descripción
                (rol.getNombre() != null && rol.getNombre().toUpperCase().contains("GERENTE")) ||
                (rol.getDescripcion() != null && rol.getDescripcion().toUpperCase().contains("GERENTE")) ||
                // 4. ID de rol específico para G2 (si conoces el ID)
                (rol.getIdRol() != null && rol.getIdRol().equals(543L))
            );
        } catch (Exception e) {
            return false;
        }
    }
}
