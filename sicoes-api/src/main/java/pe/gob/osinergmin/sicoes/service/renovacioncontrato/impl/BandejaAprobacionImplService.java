package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.BandejaAprobacionFullDTO;
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

        String idUsuario;

        if(contexto.getUsuario()!=null){
            idUsuario=contexto.getUsuario().getIdUsuario().toString();
        }else {
            idUsuario=contexto.getUsuarioApp();
        }
        // Determinar tipo de usuario usando consulta a SICOES_TX_PERFIL_APROBADOR
        Long idUsuarioLong = Long.valueOf(idUsuario);
        String tipoAprobador = requerimientoAprobacionDao.obtenerTipoAprobador(idUsuarioLong);
        
        // Determinar si es G1 o G2 basado en la consulta
        boolean esUsuarioG1 = tipoAprobador != null && (tipoAprobador.contains("G1") || tipoAprobador.equals("G1,G2"));
        boolean esUsuarioG2 = tipoAprobador != null && (tipoAprobador.contains("G2") || tipoAprobador.equals("G1,G2"));
        
        // DEBUG: Logs detallados para debug
        logger.warn("=== DEBUG USUARIO ===");
        logger.warn("Usuario ID: {}", idUsuario);
        logger.warn("tipoAprobador desde BD: {}", tipoAprobador);
        logger.warn("esUsuarioG1: {}", esUsuarioG1);
        logger.warn("esUsuarioG2: {}", esUsuarioG2);
        
        // DEBUG: Información completa del usuario
        if (contexto.getUsuario() != null) {
            
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
        
        // DEBUG: Log de parámetros de búsqueda
        logger.warn("=== PARÁMETROS DE BÚSQUEDA ===");
        logger.warn("numeroExpediente: {}", numeroExpediente);
        logger.warn("estadoAprobacionInforme: {}", estadoAprobacionInforme);
        logger.warn("idContratista: {}", idContratista);
        logger.warn("nombreContratista: {}", nombreContratista);
        logger.warn("grupoUsuario: {}", grupoUsuario);
        logger.warn("==============================");

        // Si se proporciona cualquier parámetro de búsqueda, usar búsqueda abierta
        Page<BandejaAprobacionFullDTO> listaAprobaciones;

        

                // Búsqueda normal con filtro de usuario según G1/G2
                // Verificar si hay filtros que requieran condiciones NULL problemáticas
                boolean hayFiltros = (numeroExpediente != null && !numeroExpediente.trim().isEmpty()) ||
                                   (idContratista != null) ||
                                   (nombreContratista != null && !nombreContratista.trim().isEmpty()) ||
                                   (estadoAprobacionInforme != null);

                if (hayFiltros) {
                    // Cuando hay filtros, tanto G1 como G2 ven todos los registros
                    logger.warn("DEBUG - Hay filtros aplicados, mostrando todos los registros para usuario {}", idUsuario);
                    // Usar la consulta de G2 que ahora muestra todos los registros
                    listaAprobaciones = requerimientoAprobacionDao.buscarByIdUsuarioG2ConFiltrosDinamicos(
                            numeroExpediente,
                            estadoAprobacionInforme,
                            idContratista,
                            nombreContratista,
                            pageable
                    );
                } else if (esUsuarioG2) {
                    logger.warn("DEBUG - Sin filtros, ejecutando buscarByIdUsuarioG2ConFiltrosDinamicos para usuario G2 {}", idUsuario);
                    // Sin filtros, G2 usa su consulta normal
                    listaAprobaciones = requerimientoAprobacionDao.buscarByIdUsuarioG2ConFiltrosDinamicos(
                            numeroExpediente,
                            estadoAprobacionInforme,
                            idContratista,
                            nombreContratista,
                            pageable
                    );
                } else if(esUsuarioG1) {
                    logger.warn("DEBUG - Sin filtros, ejecutando buscarByIdUsuarioConFiltrosDinamicos para usuario G1 {}", idUsuario);
                    // Sin filtros, G1 usa su consulta normal restringida
                    listaAprobaciones = requerimientoAprobacionDao.buscarByIdUsuarioConFiltrosDinamicos(
                            numeroExpediente,
                            estadoAprobacionInforme,
                            idContratista,
                            nombreContratista,
                            idUsuarioLong.intValue(),
                            pageable
                    );
                } else {
                    // Usuario no es ni G1 ni G2 - retornar página vacía
                    logger.warn("DEBUG - Usuario {} no es ni G1 ni G2", idUsuario);
                    listaAprobaciones = Page.empty(pageable);
                }



        

        List<BandejaAprobacionResponseDTO> dtos = listaAprobaciones.getContent().stream()
                .map(dto -> bandejaAprobacionMapper.toDto(dto, contexto, listadoDetalleService))
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
