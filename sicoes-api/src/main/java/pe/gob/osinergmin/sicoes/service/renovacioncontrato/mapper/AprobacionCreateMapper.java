package pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.*;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.dto.UsuarioDTO;

import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.UsuarioDao;


@Component
public class AprobacionCreateMapper {
    
    @Autowired
    private ListadoDetalleDao listadoDetalleDao;
    
    @Autowired
    private UsuarioDao usuarioDao;
    
    @Autowired
    private ListadoDetalleMapper listadoDetalleMapper;

    public AprobacionCreateResponseDTO toDto(RequerimientoAprobacion entity) {
        if (entity == null) {
            return null;
        }

        AprobacionCreateResponseDTO dto = new AprobacionCreateResponseDTO();
        
        // Campos básicos
        dto.setIdRequerimientoAprobacion(entity.getIdReqAprobacion());
        dto.setObservacion(entity.getDeObservacion());
        dto.setFechaAprobacion(entity.getFeAprobacion());
        dto.setFechaRechazo(entity.getFeRechazo());
        dto.setFechaFirma(entity.getFeFirma());
        
        // ListadoDetalle relacionados
        if (entity.getIdEstadoLd() != null) {
            dto.setEstadoLd(listadoDetalleMapper.toDto(listadoDetalleDao.findById(entity.getIdEstadoLd())));
        }
        if (entity.getIdTipoLd() != null) {
            dto.setTipoAprobacionLd(listadoDetalleMapper.toDto(listadoDetalleDao.findById(entity.getIdTipoLd())));
        }
        if (entity.getIdGrupoAprobadorLd() != null) {
            dto.setGrupoAprobadorLd(listadoDetalleMapper.toDto(listadoDetalleDao.findById(entity.getIdGrupoAprobadorLd())));
        }
        
        // Usuario
        if (entity.getIdUsuario() != null) {
            Usuario usuario = usuarioDao.obtener(entity.getIdUsuario());
            if (usuario != null) {
                UsuarioCreateResponseDTO usuarioDto = new UsuarioCreateResponseDTO();
                usuarioDto.setIdUsuario(usuario.getIdUsuario());
                usuarioDto.setNombreUsuario(usuario.getNombreUsuario());
                usuarioDto.setUsuario(usuario.getUsuario());
                dto.setUsuario(usuarioDto);
            }
        }
        
        // Notificación
        if (entity.getNotificacion() != null) {
            NotificacionCreateResponseDTO notificacionDto = new NotificacionCreateResponseDTO();
            notificacionDto.setIdNotificacion(entity.getNotificacion().getIdNotificacion());
            notificacionDto.setCorreo(entity.getNotificacion().getCorreo());
            notificacionDto.setAsunto(entity.getNotificacion().getAsunto());
            if (entity.getNotificacion().getEstado() != null) {
                notificacionDto.setEstado(listadoDetalleMapper.toDto(
                    listadoDetalleDao.findById(entity.getNotificacion().getEstado().getIdListadoDetalle())
                ));
            }
            
            dto.setNotificacion(notificacionDto);
        }
        
        // Informe de Renovación
        if (entity.getInformeRenovacionContrato() != null) {
            InformeCreateResponseDTO informeDto = new InformeCreateResponseDTO();
            informeDto.setIdInformeRenovacion(entity.getInformeRenovacionContrato().getIdInformeRenovacion());
            
            if (entity.getInformeRenovacionContrato().getEstadoAprobacionInforme() != null) {

                informeDto.setEstadoAprobacionInforme(listadoDetalleMapper.toDto(
                    listadoDetalleDao.findById(entity.getInformeRenovacionContrato().getEstadoAprobacionInforme().getIdListadoDetalle())
                ));
            }
            
            // Requerimiento de Renovación
            if (entity.getInformeRenovacionContrato().getRequerimiento() != null) {

                RequerimientoRenovacionCreateResponseDTO requerimientoDto = new RequerimientoRenovacionCreateResponseDTO();
                requerimientoDto.setIdRequerimientoRenovacion(
                    entity.getInformeRenovacionContrato().getRequerimiento().getIdReqRenovacion()
                );
                
                if (entity.getInformeRenovacionContrato().getRequerimiento().getEstadoReqRenovacion() != null) {
                    requerimientoDto.setEstadoReqRenovacion(listadoDetalleMapper.toDto(
                        listadoDetalleDao.findById(
                            entity.getInformeRenovacionContrato().getRequerimiento().getEstadoReqRenovacion().getIdListadoDetalle()
                        )
                    ));
                }
                
                informeDto.setRequerimientoRenovacion(requerimientoDto);
            }
            dto.setInforme(informeDto);
        }
        
        return dto;
    }
}
