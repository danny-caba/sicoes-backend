package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.SolicitudPerfecionamientoContrato;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.SolicitudPerfecionamientoContratoDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.InformeRenovacionContratoMapper;

import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class ListarInformeRenovacionContratoImpl {

    private final Logger logger = LogManager.getLogger(ListarInformeRenovacionContratoImpl.class);

    private final SolicitudPerfecionamientoContratoDao solicitudPerfecionamientoContratoDao;
    private final ListadoDetalleService listadoDetalleService;
    private final InformeRenovacionContratoDao informeRenovacionContratoDao;


       public ListarInformeRenovacionContratoImpl(
        ListadoDetalleService listadoDetalleService,
        InformeRenovacionContratoDao informeRenovacionContratoDao,
        SolicitudPerfecionamientoContratoDao solicitudPerfecionamientoContratoDao) {
           this.listadoDetalleService = listadoDetalleService;
           this.informeRenovacionContratoDao = informeRenovacionContratoDao;
           this.solicitudPerfecionamientoContratoDao = solicitudPerfecionamientoContratoDao;
           
       }

    public Page<InformeRenovacionContratoDTO> ejecutar(
            String tipoAprobador,
            String numeroExpediente, 
            Long estado,
            Long idContratista ,
            Contexto contexto,
            Pageable pageable) {

        Usuario usuarioCtx = contexto.getUsuario();

        if (usuarioCtx.getRoles().stream().noneMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.APROBADOR_TECNICO))){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_SIN_PERMISO_RENOVACION_CONTRATO);
        }

        this.logger.info("Tipo aprobador {}", tipoAprobador);
/*
        switch (tipoAprobador) {
            case Constantes.LISTADO.GRUPOS.G1:                */
                return flujoG1(usuarioCtx, numeroExpediente, estado, idContratista, pageable);
/*
            case Constantes.LISTADO.GRUPOS.G2:                
                return flujoG2(usuarioCtx, numeroExpediente, estado, idContratista, pageable);

            default:
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_SIN_PERMISO_RENOVACION_CONTRATO);
        }*/
}

private Page<InformeRenovacionContratoDTO> flujoG2(
    Usuario usuarioCtx,
    String numeroExpediente, 
    Long estado,
    Long idContratista ,
    Pageable pageable
    ) {
        Long esVigente = new Long(1);
        Page<InformeRenovacionContrato> listInforme = informeRenovacionContratoDao.findByFiltrosWithJoins(
            numeroExpediente,
            esVigente,
            estado,
            idContratista,
            pageable
        );    

        List<SolicitudPerfecionamientoContrato> perfilesG2   = 
        solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdUsuarioG2(usuarioCtx.getIdUsuario());
        
        
        if (!perfilesG2 .isEmpty()) {
            ListadoDetalle estadoLd = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_APROBACION.CODIGO    ,
                    Constantes.LISTADO.ESTADO_APROBACION.APROBADO
            );
            ListadoDetalle grupoG2 = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.GRUPOS.CODIGO    ,
                    Constantes.LISTADO.GRUPOS.G2
            );


            listInforme = filtrarAprobaciones(listInforme, estadoLd.getIdListadoDetalle(), grupoG2.getIdListadoDetalle());
            return listInforme.map(InformeRenovacionContratoMapper.MAPPER::toDTO);
        }else {
            this.logger.info("Usuario {} sin permiso G2 ",usuarioCtx.getIdUsuario());
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_SIN_PERMISO_RENOVACION_CONTRATO);
        }
    }
    
    private Page<InformeRenovacionContratoDTO> flujoG1(
        Usuario usuarioCtx,
        String numeroExpediente, 
        Long estado,
        Long idContratista ,
        Pageable pageable
        ) {
        Long esVigente =new Long(1);
        Page<InformeRenovacionContrato> listInforme = informeRenovacionContratoDao.findByFiltrosWithJoins(
            numeroExpediente,
            esVigente,
            estado,
            idContratista,
            pageable
        );
        /*List<SolicitudPerfecionamientoContrato> perfilesG1   =
            solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdUsuarioG1(usuarioCtx.getIdUsuario());*/

        //if (!perfilesG1 .isEmpty()) {
            /*ListadoDetalle estadoLd = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_APROBACION.CODIGO    ,
                    Constantes.LISTADO.ESTADO_APROBACION.ASIGNADO
            );*/
            /*ListadoDetalle grupoG1 = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.GRUPOS.CODIGO    ,
                    Constantes.LISTADO.GRUPOS.G1
            );*/
/*
            if (estadoLd == null || grupoG1 == null) {
                throw new ValidacionException("Configuración de listado detalle no encontrada para estado o grupo");
            }*/
            listInforme = filtrarAprobacionesBandeja(listInforme,usuarioCtx.getIdUsuario());
            return listInforme.map(InformeRenovacionContratoMapper.MAPPER::toDTO);
        /*}else{
            this.logger.warn("Usuario {} sin permiso G1 ",usuarioCtx.getIdUsuario());
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_SIN_PERMISO_RENOVACION_CONTRATO);
        }*/
    }
    private Page<InformeRenovacionContrato> filtrarAprobacionesBandeja(Page<InformeRenovacionContrato> informes, Long idUsuario) {
        return informes.map(informe -> {
            List<RequerimientoAprobacion> aprobacionesFiltradas = informe.getAprobaciones().stream()
                    .filter(aprobacion -> aprobacion != null 
                        && aprobacion.getIdUsuario() != null 
                        && aprobacion.getIdUsuario().equals(idUsuario))
                    .collect(Collectors.toList());
            informe.setAprobaciones(aprobacionesFiltradas);
            return informe;
        });
    }

    private Page<InformeRenovacionContrato> filtrarAprobaciones(Page<InformeRenovacionContrato> informes,Long a,Long b) {
        return informes.map(informe -> {
            List<RequerimientoAprobacion> aprobacionesFiltradas = informe.getAprobaciones().stream()
                    .filter(aprobacion -> aprobacion != null)
                    .collect(Collectors.toList());
            informe.setAprobaciones(aprobacionesFiltradas);
            return informe;
        });
    }
}
