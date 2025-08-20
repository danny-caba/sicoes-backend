package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.Bitacora;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.EliminarInvitacionDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InvitacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoInvitacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.repository.BitacoraDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoInvitacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.service.BitacoraService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.RenovacionesService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
@Transactional
public class RenovacionesServiceImpl implements RenovacionesService {

    private Logger logger = LogManager.getLogger(RenovacionesServiceImpl.class);

    @Autowired
    private RequerimientoRenovacionDao requerimientoRenovacionDao;
    
    @Autowired
    private RequerimientoInvitacionDao requerimientoInvitacionDao;
    
    @Autowired
    private BitacoraService bitacoraService;
    
    @Autowired
    private BitacoraDao bitacoraDao;

    @Override
    @Transactional(readOnly = true)
    public Page<RequerimientoRenovacion> buscarSolicitudesRenovacion(
            String numeroExpediente,
            String tipoSector,
            String tipoSubSector,
            String nombreItem,
            Integer estadoRequerimiento,
            String fechaDesde,
            String fechaHasta,
            Pageable pageable,
            Contexto contexto) {
        
        logger.info("buscarSolicitudesRenovacion - Usuario: {}, Expediente: {}, Sector: {}", 
                    contexto.getUsuario().getIdUsuario(), numeroExpediente, tipoSector);
        
        try {
            // 1. Validar y limpiar parámetros de entrada
            String numeroExpedienteLimpio = limpiarParametro(numeroExpediente);
            String tipoSectorLimpio = limpiarParametro(tipoSector);
            String tipoSubSectorLimpio = limpiarParametro(tipoSubSector);
            String nombreItemLimpio = limpiarParametro(nombreItem);
            
            // 2. Aplicar filtros de seguridad por usuario/perfil
            if (!validarPermisosConsulta(contexto)) {
                logger.warn("Usuario {} no tiene permisos para consultar solicitudes de renovación", contexto.getUsuario().getIdUsuario());
                return new PageImpl<>(new ArrayList<>(), pageable, 0);
            }
            
            // 3. Ejecutar consulta paginada
            Page<RequerimientoRenovacion> solicitudes;
            
            if (tieneParametrosFecha(fechaDesde, fechaHasta)) {
                // Búsqueda con filtro de fechas requiere consulta especial
                solicitudes = buscarConFiltroFechas(
                    numeroExpedienteLimpio, tipoSectorLimpio, tipoSubSectorLimpio, 
                    nombreItemLimpio, estadoRequerimiento, fechaDesde, fechaHasta, 
                    pageable, contexto);
            } else {
                // Búsqueda estándar con filtros básicos
                solicitudes = requerimientoRenovacionDao.buscarSolicitudesRenovacion(
                    numeroExpedienteLimpio, tipoSectorLimpio, tipoSubSectorLimpio, 
                    nombreItemLimpio, estadoRequerimiento, pageable);
            }
            
            // 4. Aplicar filtros adicionales de seguridad a nivel de registro
            Page<RequerimientoRenovacion> solicitudesFiltradas = aplicarFiltrosSeguridad(solicitudes, contexto);
            
            // 5. Registrar consulta en bitácora
            registrarConsultaBitacora(numeroExpedienteLimpio, tipoSectorLimpio, contexto);
            
            logger.info("Búsqueda completada - Encontradas {} solicitudes para usuario {}", 
                       solicitudesFiltradas.getTotalElements(), contexto.getUsuario().getIdUsuario());
            
            return solicitudesFiltradas;
            
        } catch (Exception e) {
            logger.error("Error al buscar solicitudes de renovación", e);
            // En caso de error, retornar lista vacía para evitar fallos del sistema
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
    }
    
    private String limpiarParametro(String parametro) {
        if (parametro == null || parametro.trim().isEmpty()) {
            return null;
        }
        // Limpiar caracteres especiales para prevenir inyección SQL
        return parametro.trim().replaceAll("[<>\"'%;()&+]", "");
    }
    
    private boolean validarPermisosConsulta(Contexto contexto) {
        try {
            // Validar que el usuario tiene permisos para consultar solicitudes de renovación
            if (contexto.getUsuario() == null) {
                return false;
            }
            
            // Verificar roles que pueden consultar
            String usuario = contexto.getUsuario().getUsuario();
            return usuario != null && (
                usuario.contains("EVALUADOR") || 
                usuario.contains("TECNICO") || 
                usuario.contains("ADMIN") ||
                usuario.contains("CONSULTOR")
            );
        } catch (Exception e) {
            logger.warn("Error al validar permisos de consulta", e);
            return false;
        }
    }
    
    private boolean tieneParametrosFecha(String fechaDesde, String fechaHasta) {
        return (fechaDesde != null && !fechaDesde.trim().isEmpty()) || 
               (fechaHasta != null && !fechaHasta.trim().isEmpty());
    }
    
    private Page<RequerimientoRenovacion> buscarConFiltroFechas(
            String numeroExpediente, String tipoSector, String tipoSubSector,
            String nombreItem, Integer estadoRequerimiento, String fechaDesde, 
            String fechaHasta, Pageable pageable, Contexto contexto) {
        
        try {
            // Validar y convertir fechas
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaDesdeDate = null;
            Date fechaHastaDate = null;
            
            if (fechaDesde != null && !fechaDesde.trim().isEmpty()) {
                fechaDesdeDate = dateFormat.parse(fechaDesde);
            }
            
            if (fechaHasta != null && !fechaHasta.trim().isEmpty()) {
                fechaHastaDate = dateFormat.parse(fechaHasta);
            }
            
            // Si solo se proporcionan fechas, usar consulta específica de rango
            if (fechaDesdeDate != null && fechaHastaDate != null) {
                List<RequerimientoRenovacion> solicitudesPorFecha = 
                    requerimientoRenovacionDao.buscarPorRangoFechas(fechaDesde, fechaHasta);
                
                // Aplicar filtros adicionales en memoria si son pocos resultados
                List<RequerimientoRenovacion> solicitudesFiltradas = aplicarFiltrosAdicionales(
                    solicitudesPorFecha, numeroExpediente, tipoSector, tipoSubSector, 
                    nombreItem, estadoRequerimiento);
                
                // Crear página manualmente
                int inicio = (int) pageable.getOffset();
                int fin = Math.min(inicio + pageable.getPageSize(), solicitudesFiltradas.size());
                List<RequerimientoRenovacion> paginaActual = solicitudesFiltradas.subList(inicio, fin);
                
                return new PageImpl<>(paginaActual, pageable, solicitudesFiltradas.size());
            } else {
                // Usar consulta estándar si las fechas no están completas
                return requerimientoRenovacionDao.buscarSolicitudesRenovacion(
                    numeroExpediente, tipoSector, tipoSubSector, nombreItem, 
                    estadoRequerimiento, pageable);
            }
            
        } catch (ParseException e) {
            logger.error("Error al parsear fechas - fechaDesde: {}, fechaHasta: {}", fechaDesde, fechaHasta, e);
            // Si hay error en fechas, usar consulta sin filtro de fecha
            return requerimientoRenovacionDao.buscarSolicitudesRenovacion(
                numeroExpediente, tipoSector, tipoSubSector, nombreItem, 
                estadoRequerimiento, pageable);
        }
    }
    
    private List<RequerimientoRenovacion> aplicarFiltrosAdicionales(
            List<RequerimientoRenovacion> solicitudes, String numeroExpediente,
            String tipoSector, String tipoSubSector, String nombreItem, 
            Integer estadoRequerimiento) {
        
        List<RequerimientoRenovacion> filtradas = new ArrayList<>();
        
        for (RequerimientoRenovacion solicitud : solicitudes) {
            boolean cumpleFiltros = true;
            
            if (numeroExpediente != null && !solicitud.getNuExpediente().toLowerCase()
                    .contains(numeroExpediente.toLowerCase())) {
                cumpleFiltros = false;
            }
            
            if (tipoSector != null && !solicitud.getTiSector().toLowerCase()
                    .contains(tipoSector.toLowerCase())) {
                cumpleFiltros = false;
            }
            
            if (tipoSubSector != null && !solicitud.getTiSubSector().toLowerCase()
                    .contains(tipoSubSector.toLowerCase())) {
                cumpleFiltros = false;
            }
            
            if (nombreItem != null && !solicitud.getNoItem().toLowerCase()
                    .contains(nombreItem.toLowerCase())) {
                cumpleFiltros = false;
            }
            
            if (estadoRequerimiento != null && solicitud.getEstadoReqRenovacion() != null &&
                !estadoRequerimiento.equals(solicitud.getEstadoReqRenovacion().getIdListadoDetalle().intValue())) {
                cumpleFiltros = false;
            }
            
            if (cumpleFiltros) {
                filtradas.add(solicitud);
            }
        }
        
        return filtradas;
    }
    
    private Page<RequerimientoRenovacion> aplicarFiltrosSeguridad(
            Page<RequerimientoRenovacion> solicitudes, Contexto contexto) {
        
        // Aplicar filtros de seguridad a nivel de registro
        // Por ejemplo, si el usuario solo puede ver sus propias solicitudes
        try {
            String tipoUsuario = obtenerTipoUsuario(contexto);
            
            if ("EXTERNO".equals(tipoUsuario)) {
                // Usuario externo solo ve sus propias solicitudes
                List<RequerimientoRenovacion> solicitudesFiltradas = solicitudes.getContent().stream()
                    .filter(s -> s.getIdUsuario() != null && s.getIdUsuario().equals(contexto.getUsuario().getIdUsuario()))
                    .collect(java.util.stream.Collectors.toList());
                
                return new PageImpl<>(solicitudesFiltradas, solicitudes.getPageable(), solicitudesFiltradas.size());
            }
            
            // Usuarios internos ven todas las solicitudes que ya pasaron el filtro DAO
            return solicitudes;
            
        } catch (Exception e) {
            logger.warn("Error al aplicar filtros de seguridad", e);
            return solicitudes;
        }
    }
    
    private String obtenerTipoUsuario(Contexto contexto) {
        try {
            String usuario = contexto.getUsuario().getUsuario();
            if (usuario != null) {
                if (usuario.contains("ADMIN") || usuario.contains("TECNICO")) {
                    return "INTERNO";
                } else if (usuario.contains("EXTERNO")) {
                    return "EXTERNO";
                }
            }
            return "DESCONOCIDO";
        } catch (Exception e) {
            return "DESCONOCIDO";
        }
    }
    
    private void registrarConsultaBitacora(String numeroExpediente, String tipoSector, Contexto contexto) {
        try {
            Bitacora bitacora = new Bitacora();
            bitacora.setUsuario(contexto.getUsuario());
            bitacora.setFechaHora(new Date());
            bitacora.setDescripcion("Consulta de solicitudes de renovación. " +
                                "Expediente: " + (numeroExpediente != null ? numeroExpediente : "Todos") + 
                                ", Sector: " + (tipoSector != null ? tipoSector : "Todos"));
            
            AuditoriaUtil.setAuditoriaRegistro(bitacora, contexto);
            
            bitacoraDao.save(bitacora);
            
            logger.info("Registrada consulta en bitácora - Usuario: {}, Bitácora ID: {}", 
                       contexto.getUsuario().getIdUsuario(), bitacora.getIdBitacora());
            
        } catch (Exception e) {
            logger.warn("Error al registrar consulta en bitácora", e);
            // No lanzar excepción para no afectar la funcionalidad principal
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvitacionResponseDTO> listarInvitaciones(
            String numeroExpediente,
            String nombreItem,
            Integer estadoInvitacion,
            String fechaDesde,
            String fechaHasta,
            Pageable pageable,
            Contexto contexto) {
        
        logger.info("listarInvitaciones - Usuario: {}, Expediente: {}, Item: {}", 
                    contexto.getUsuario().getIdUsuario(), numeroExpediente, nombreItem);
        
        try {
            // 1. Validar y limpiar parámetros de entrada
            String numeroExpedienteLimpio = limpiarParametro(numeroExpediente);
            String nombreItemLimpio = limpiarParametro(nombreItem);
            
            // 2. Validar permisos del usuario
            if (!validarPermisosConsultaInvitaciones(contexto)) {
                logger.warn("Usuario {} no tiene permisos para listar invitaciones", contexto.getUsuario().getIdUsuario());
                return new PageImpl<>(new ArrayList<>(), pageable, 0);
            }
            
            // 3. Obtener invitaciones desde el DAO
            Page<RequerimientoInvitacion> invitacionesEntity;
            
            if (tieneParametrosFecha(fechaDesde, fechaHasta)) {
                invitacionesEntity = buscarInvitacionesConFiltroFechas(
                    numeroExpedienteLimpio, nombreItemLimpio, estadoInvitacion, 
                    fechaDesde, fechaHasta, pageable, contexto);
            } else {
                invitacionesEntity = requerimientoInvitacionDao.buscarInvitaciones(
                    numeroExpedienteLimpio, nombreItemLimpio, estadoInvitacion, pageable);
            }
            
            // 4. Convertir entities a DTOs
            List<InvitacionResponseDTO> invitacionesDTO = convertirAInvitacionResponseDTO(invitacionesEntity.getContent());
            
            // 5. Aplicar filtros de seguridad adicionales
            List<InvitacionResponseDTO> invitacionesFiltradas = aplicarFiltrosSeguridadInvitaciones(invitacionesDTO, contexto);
            
            // 6. Registrar consulta en bitácora
            registrarConsultaInvitacionesBitacora(numeroExpedienteLimpio, nombreItemLimpio, contexto);
            
            logger.info("Búsqueda de invitaciones completada - Encontradas {} invitaciones para usuario {}", 
                       invitacionesFiltradas.size(), contexto.getUsuario().getIdUsuario());
            
            return new PageImpl<>(invitacionesFiltradas, pageable, invitacionesEntity.getTotalElements());
            
        } catch (Exception e) {
            logger.error("Error al listar invitaciones", e);
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
    }
    
    private boolean validarPermisosConsultaInvitaciones(Contexto contexto) {
        try {
            if (contexto.getUsuario() == null) {
                return false;
            }
            
            String usuario = contexto.getUsuario().getUsuario();
            return usuario != null && (
                usuario.contains("EVALUADOR") || 
                usuario.contains("TECNICO") || 
                usuario.contains("ADMIN") ||
                usuario.contains("CONSULTOR")
            );
        } catch (Exception e) {
            logger.warn("Error al validar permisos de consulta de invitaciones", e);
            return false;
        }
    }
    
    private Page<RequerimientoInvitacion> buscarInvitacionesConFiltroFechas(
            String numeroExpediente, String nombreItem, Integer estadoInvitacion,
            String fechaDesde, String fechaHasta, Pageable pageable, Contexto contexto) {
        
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaDesdeDate = null;
            Date fechaHastaDate = null;
            
            if (fechaDesde != null && !fechaDesde.trim().isEmpty()) {
                fechaDesdeDate = dateFormat.parse(fechaDesde);
            }
            
            if (fechaHasta != null && !fechaHasta.trim().isEmpty()) {
                fechaHastaDate = dateFormat.parse(fechaHasta);
            }
            
            if (fechaDesdeDate != null && fechaHastaDate != null) {
                List<RequerimientoInvitacion> invitacionesPorFecha = 
                    requerimientoInvitacionDao.buscarPorRangoFechas(fechaDesde, fechaHasta);
                
                List<RequerimientoInvitacion> invitacionesFiltradas = aplicarFiltrosAdicionalesInvitaciones(
                    invitacionesPorFecha, numeroExpediente, nombreItem, estadoInvitacion);
                
                int inicio = (int) pageable.getOffset();
                int fin = Math.min(inicio + pageable.getPageSize(), invitacionesFiltradas.size());
                List<RequerimientoInvitacion> paginaActual = invitacionesFiltradas.subList(inicio, fin);
                
                return new PageImpl<>(paginaActual, pageable, invitacionesFiltradas.size());
            } else {
                return requerimientoInvitacionDao.buscarInvitaciones(
                    numeroExpediente, nombreItem, estadoInvitacion, pageable);
            }
            
        } catch (ParseException e) {
            logger.error("Error al parsear fechas para invitaciones - fechaDesde: {}, fechaHasta: {}", fechaDesde, fechaHasta, e);
            return requerimientoInvitacionDao.buscarInvitaciones(
                numeroExpediente, nombreItem, estadoInvitacion, pageable);
        }
    }
    
    private List<RequerimientoInvitacion> aplicarFiltrosAdicionalesInvitaciones(
            List<RequerimientoInvitacion> invitaciones, String numeroExpediente,
            String nombreItem, Integer estadoInvitacion) {
        
        List<RequerimientoInvitacion> filtradas = new ArrayList<>();
        
        for (RequerimientoInvitacion invitacion : invitaciones) {
            boolean cumpleFiltros = true;
            
            if (numeroExpediente != null && invitacion.getRequerimientoRenovacion() != null &&
                !invitacion.getRequerimientoRenovacion().getNuExpediente().toLowerCase()
                    .contains(numeroExpediente.toLowerCase())) {
                cumpleFiltros = false;
            }
            
            if (nombreItem != null && invitacion.getRequerimientoRenovacion() != null &&
                !invitacion.getRequerimientoRenovacion().getNoItem().toLowerCase()
                    .contains(nombreItem.toLowerCase())) {
                cumpleFiltros = false;
            }
            
            if (estadoInvitacion != null && invitacion.getEstadoInvitacion() != null &&
                !estadoInvitacion.equals(invitacion.getEstadoInvitacion().getIdListadoDetalle().intValue())) {
                cumpleFiltros = false;
            }
            
            if (cumpleFiltros) {
                filtradas.add(invitacion);
            }
        }
        
        return filtradas;
    }
    
    private List<InvitacionResponseDTO> convertirAInvitacionResponseDTO(List<RequerimientoInvitacion> invitaciones) {
        List<InvitacionResponseDTO> invitacionesDTO = new ArrayList<>();
        
        for (RequerimientoInvitacion invitacion : invitaciones) {
            InvitacionResponseDTO dto = new InvitacionResponseDTO();
            
            dto.setIdRequerimientoInvitacion(invitacion.getIdReqInvitacion() != null ? invitacion.getIdReqInvitacion().intValue() : null);
            
            if (invitacion.getRequerimientoRenovacion() != null) {
                dto.setNumeroExpediente(invitacion.getRequerimientoRenovacion().getNuExpediente());
                dto.setNombreItem(invitacion.getRequerimientoRenovacion().getNoItem());
            }
            
            // Información de supervisora se debe obtener por ID
            if (invitacion.getIdSupervisora() != null) {
                // TODO: Implementar carga de supervisora por ID cuando sea necesario
                dto.setIdSupervisora(invitacion.getIdSupervisora());
            }
            
            if (invitacion.getEstadoInvitacion() != null) {
                dto.setEstadoInvitacion(invitacion.getEstadoInvitacion().getIdListadoDetalle().intValue());
                dto.setDescripcionEstado(invitacion.getEstadoInvitacion().getDescripcion());
            }
            
            dto.setFechaCreacion(invitacion.getFecCreacion() != null ? 
                invitacion.getFecCreacion().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null);
            dto.setFechaVencimiento(invitacion.getFeCaducidad() != null ? 
                invitacion.getFeCaducidad().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime() : null);
            
            // Observaciones no están en el modelo base
            // dto.setObservaciones(invitacion.getDeObservaciones());
            
            // Usuario creación se obtiene por ID desde BaseModel
            if (invitacion.getUsuCreacion() != null) {
                dto.setNombreUsuarioCreacion(invitacion.getUsuCreacion());
            }
            
            invitacionesDTO.add(dto);
        }
        
        return invitacionesDTO;
    }
    
    private List<InvitacionResponseDTO> aplicarFiltrosSeguridadInvitaciones(
            List<InvitacionResponseDTO> invitaciones, Contexto contexto) {
        
        try {
            String tipoUsuario = obtenerTipoUsuario(contexto);
            
            if ("EXTERNO".equals(tipoUsuario)) {
                // Usuario externo solo ve invitaciones dirigidas a él
                return invitaciones.stream()
                    .filter(inv -> {
                        // Aquí deberías implementar la lógica para verificar si la invitación
                        // está dirigida al usuario actual (por ejemplo, por RUC o ID de supervisora)
                        return true; // Placeholder - implementar lógica específica
                    })
                    .collect(java.util.stream.Collectors.toList());
            }
            
            return invitaciones;
            
        } catch (Exception e) {
            logger.warn("Error al aplicar filtros de seguridad en invitaciones", e);
            return invitaciones;
        }
    }
    
    private void registrarConsultaInvitacionesBitacora(String numeroExpediente, String nombreItem, Contexto contexto) {
        try {
            Bitacora bitacora = new Bitacora();
            bitacora.setUsuario(contexto.getUsuario());
            bitacora.setFechaHora(new Date());
            bitacora.setDescripcion("Consulta de invitaciones de renovación. " +
                                "Expediente: " + (numeroExpediente != null ? numeroExpediente : "Todos") + 
                                ", Item: " + (nombreItem != null ? nombreItem : "Todos"));
            
            AuditoriaUtil.setAuditoriaRegistro(bitacora, contexto);
            
            bitacoraDao.save(bitacora);
            
            logger.info("Registrada consulta de invitaciones en bitácora - Usuario: {}, Bitácora ID: {}", 
                       contexto.getUsuario().getIdUsuario(), bitacora.getIdBitacora());
            
        } catch (Exception e) {
            logger.warn("Error al registrar consulta de invitaciones en bitácora", e);
        }
    }

    @Override
    @Transactional
    public void eliminarInvitacion(EliminarInvitacionDTO eliminarDTO, Contexto contexto) {
        logger.info("eliminarInvitacion - IdInvitacion: {}, Usuario: {}", 
                    eliminarDTO.getIdReqInvitacion(), contexto.getUsuario().getIdUsuario());
        
        try {
            // 1. Validar que la invitación existe
            RequerimientoInvitacion invitacion = requerimientoInvitacionDao.obtenerPorId(eliminarDTO.getIdReqInvitacion());
            if (invitacion == null) {
                throw new IllegalArgumentException("No se encontró la invitación con ID: " + eliminarDTO.getIdReqInvitacion());
            }
            
            // 2. Verificar que la invitación está activa
            if (!"1".equals(invitacion.getFlActivo())) {
                throw new IllegalArgumentException("La invitación ya se encuentra inactiva");
            }
            
            // 3. Verificar permisos del usuario
            if (!validarPermisosEliminacion(contexto, invitacion)) {
                throw new SecurityException("El usuario no tiene permisos para eliminar esta invitación");
            }
            
            // 4. Verificar que la invitación puede ser eliminada (no ha sido aceptada)
            if (invitacion.getFeAceptacion() != null) {
                throw new IllegalArgumentException("No se puede eliminar una invitación que ya ha sido aceptada");
            }
            
            // 5. Marcar la invitación como inactiva (eliminación lógica)
            invitacion.setFlActivo("0");
            invitacion.setFeCancelado(new Date());
            
            AuditoriaUtil.setAuditoriaActualizacion(invitacion, contexto);
            requerimientoInvitacionDao.save(invitacion);
            
            // 6. Registrar eliminación en bitácora
            registrarEliminacionBitacora(invitacion, eliminarDTO, contexto);
            
            logger.info("Invitación eliminada exitosamente - ID: {}", eliminarDTO.getIdReqInvitacion());
            
        } catch (Exception e) {
            logger.error("Error al eliminar invitación", e);
            throw e;
        }
    }
    
    private boolean validarPermisosEliminacion(Contexto contexto, RequerimientoInvitacion invitacion) {
        try {
            if (contexto.getUsuario() == null) {
                return false;
            }
            
            String usuario = contexto.getUsuario().getUsuario();
            if (usuario == null) {
                return false;
            }
            
            // Verificar roles que pueden eliminar invitaciones
            if (usuario.contains("ADMIN") || usuario.contains("TECNICO") || usuario.contains("EVALUADOR")) {
                return true;
            }
            
            // El usuario puede eliminar sus propias invitaciones si es el propietario del requerimiento
            RequerimientoRenovacion requerimiento = requerimientoRenovacionDao.obtenerPorId(invitacion.getIdRequerimiento());
            if (requerimiento != null && requerimiento.getIdUsuario() != null) {
                return requerimiento.getIdUsuario().equals(contexto.getUsuario().getIdUsuario());
            }
            
            return false;
            
        } catch (Exception e) {
            logger.warn("Error al validar permisos de eliminación", e);
            return false;
        }
    }
    
    private void registrarEliminacionBitacora(RequerimientoInvitacion invitacion, EliminarInvitacionDTO eliminarDTO, Contexto contexto) {
        try {
            Bitacora bitacora = new Bitacora();
            bitacora.setUsuario(contexto.getUsuario());
            bitacora.setFechaHora(new Date());
            bitacora.setDescripcion("Eliminación de invitación. ID: " + invitacion.getIdReqInvitacion() + 
                                  ", Requerimiento: " + invitacion.getIdRequerimiento() +
                                  ", Motivo: " + (eliminarDTO.getMotivo() != null ? eliminarDTO.getMotivo() : "No especificado") +
                                  (eliminarDTO.getObservaciones() != null ? ", Observaciones: " + eliminarDTO.getObservaciones() : ""));
            
            AuditoriaUtil.setAuditoriaRegistro(bitacora, contexto);
            
            bitacoraDao.save(bitacora);
            
            logger.info("Registrada eliminación de invitación en bitácora - Invitación: {}, Bitácora ID: {}", 
                       invitacion.getIdReqInvitacion(), bitacora.getIdBitacora());
                       
        } catch (Exception e) {
            logger.warn("Error al registrar eliminación de invitación en bitácora", e);
        }
    }
}