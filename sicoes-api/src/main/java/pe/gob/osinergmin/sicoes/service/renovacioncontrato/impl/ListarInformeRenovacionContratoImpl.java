package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.ListadoDetalleDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoAprobacionDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.InformeRenovacionContratoMapper;

import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class ListarInformeRenovacionContratoImpl {

    private final Logger logger = LogManager.getLogger(ListarInformeRenovacionContratoImpl.class);

    private final InformeRenovacionContratoDao informeRenovacionContratoDao;
    private final ListadoDetalleDao listadoDetalleDao;


       public ListarInformeRenovacionContratoImpl(
        InformeRenovacionContratoDao informeRenovacionContratoDao,
        ListadoDetalleDao listadoDetalleDao) {
           this.informeRenovacionContratoDao = informeRenovacionContratoDao;
           this.listadoDetalleDao = listadoDetalleDao;
           
       }

    public Page<InformeRenovacionContratoDTO> ejecutar(
            String tipoAprobador,
            String numeroExpediente, 
            Long estadoAprobacionInforme,
            Long idContratista ,
            Contexto contexto,
            Pageable pageable) {

        Usuario usuarioCtx = contexto.getUsuario();
        if (usuarioCtx.getRoles().stream().noneMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.APROBADOR_TECNICO))){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_SIN_PERMISO_RENOVACION_CONTRATO);
        }
        this.logger.info("Tipo aprobador {}", tipoAprobador);

        Boolean esVigente = Boolean.TRUE;
        Page<InformeRenovacionContrato> listInforme = informeRenovacionContratoDao.findByFiltrosWithJoins2(
            numeroExpediente,
            esVigente,
            estadoAprobacionInforme,
            idContratista,
            usuarioCtx.getIdUsuario(),
            pageable
        );

        // Recopilar todos los IDs de listado detalle de las aprobaciones
        Set<Long> idsListadoDetalle = new HashSet<>();
        
        for (InformeRenovacionContrato informe : listInforme.getContent()) {
            if (informe.getAprobaciones() != null) {
                for (RequerimientoAprobacion aprobacion : informe.getAprobaciones()) {
                    // Agregar IDs de listado detalle a la colección
                    if (aprobacion.getIdGrupoAprobadorLd() != null) {
                        idsListadoDetalle.add(aprobacion.getIdGrupoAprobadorLd());
                    }
                    if (aprobacion.getIdTipoAprobadorLd() != null) {
                        idsListadoDetalle.add(aprobacion.getIdTipoAprobadorLd());
                    }
                    if (aprobacion.getIdGrupoLd() != null) {
                        idsListadoDetalle.add(aprobacion.getIdGrupoLd());
                    }
                }
            }
        }

        // Consultar todos los ListadoDetalle por los IDs recopilados
        List<ListadoDetalle> listadoDetalleList = new ArrayList<>();
        if (!idsListadoDetalle.isEmpty()) {
            listadoDetalleList = listadoDetalleDao.findAllById(idsListadoDetalle);
        }

        // Crear un mapa para acceso rápido por ID
        Map<Long, ListadoDetalle> mapListadoDetalle = new HashMap<>();
        for (ListadoDetalle detalle : listadoDetalleList) {
            mapListadoDetalle.put(detalle.getIdListadoDetalle(), detalle);
        }

        // Convertir a DTO y asignar los datos de listado detalle
        return listInforme.map(informe -> {
            InformeRenovacionContratoDTO dto = InformeRenovacionContratoMapper.MAPPER.toDTO(informe);
            
            // Asignar datos de listado detalle a las aprobaciones
            if (dto.getAprobaciones() != null) {
                for (RequerimientoAprobacionDTO aprobacionDTO : dto.getAprobaciones()) {
                    // Asignar grupoAprobadorLd
                    if (aprobacionDTO.getIdGrupoAprobadorLd() != null) {
                        ListadoDetalle grupoAprobador = mapListadoDetalle.get(aprobacionDTO.getIdGrupoAprobadorLd());
                        if (grupoAprobador != null) {
                            ListadoDetalleDTO grupoAprobadorDto = new ListadoDetalleDTO();
                            grupoAprobadorDto.setIdListadoDetalle(grupoAprobador.getIdListadoDetalle());
                            grupoAprobadorDto.setCodigo(grupoAprobador.getCodigo());
                            grupoAprobadorDto.setNombre(grupoAprobador.getNombre());
                            aprobacionDTO.setGrupoAprobadorLd(grupoAprobadorDto);
                        }
                    }
                    
                    // Asignar tipoAprobadorLd
                    if (aprobacionDTO.getIdTipoAprobadorLd() != null) {
                        ListadoDetalle tipoAprobadorDetalle = mapListadoDetalle.get(aprobacionDTO.getIdTipoAprobadorLd());
                        if (tipoAprobadorDetalle != null) {
                            ListadoDetalleDTO tipoAprobadorDto = new ListadoDetalleDTO();
                            tipoAprobadorDto.setIdListadoDetalle(tipoAprobadorDetalle.getIdListadoDetalle());
                            tipoAprobadorDto.setCodigo(tipoAprobadorDetalle.getCodigo());
                            tipoAprobadorDto.setNombre(tipoAprobadorDetalle.getNombre());
                            aprobacionDTO.setTipoAprobadorLd(tipoAprobadorDto);
                        }
                    }
                    
                    // Asignar grupoLd
                    if (aprobacionDTO.getIdGrupoLd() != null) {
                        ListadoDetalle grupo = mapListadoDetalle.get(aprobacionDTO.getIdGrupoLd());
                        if (grupo != null) {
                            ListadoDetalleDTO grupoDto = new ListadoDetalleDTO();
                            grupoDto.setIdListadoDetalle(grupo.getIdListadoDetalle());
                            grupoDto.setCodigo(grupo.getCodigo());
                            grupoDto.setNombre(grupo.getNombre());
                            aprobacionDTO.setGrupoLd(grupoDto);
                        }
                    }
                }
            }
            
            return dto;
        });
    }
}