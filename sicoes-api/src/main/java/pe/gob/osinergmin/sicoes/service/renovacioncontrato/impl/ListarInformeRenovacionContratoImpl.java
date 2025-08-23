package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
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
            String numeroExpediente, 
            Long estado,
            Long idContratista ,
            Contexto contexto,
            Pageable pageable) {

        Boolean esVigente = true;
        Usuario usuarioCtx = contexto.getUsuario();

        ListadoDetalle estadoLd = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_APROBACION"    ,
                "ASIGNADO"
        );
        ListadoDetalle grupoG1 = listadoDetalleService.obtenerListadoDetalle(
                "GRUPOS"    ,
                "G1"
        );

        if (estadoLd == null || grupoG1 == null) {
            throw new ValidacionException("Configuración de listado detalle no encontrada para estado o grupo");
        }

        if (usuarioCtx.getRoles().stream().noneMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.APROBADOR_TECNICO))){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_SIN_PERMISO_RENOVACION_CONTRATO);
        }

        Page<InformeRenovacionContrato> listInforme = informeRenovacionContratoDao.findByFiltrosWithJoins(
            numeroExpediente,
            esVigente,
            estado,
            idContratista,
            pageable
        ); 
        
        
        List<SolicitudPerfecionamientoContrato> perfilesG1   = 
         solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdUsuarioG1(usuarioCtx.getIdUsuario());


        if (!perfilesG1 .isEmpty()) {
            listInforme = filtrarAprobaciones(listInforme,
                    estadoLd.getIdListadoDetalle(),
                    grupoG1.getIdListadoDetalle());
        
            return listInforme.map(InformeRenovacionContratoMapper.MAPPER::toDTO);
        }else{
            this.logger.warn("Usuario {} sin permiso G1 ",usuarioCtx.getIdUsuario());
        }
        
        
        List<SolicitudPerfecionamientoContrato> perfilesG2   = 
         solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdUsuarioG2(usuarioCtx.getIdUsuario());
        
        
        if (!perfilesG2 .isEmpty()) {
            listInforme = filtrarAprobaciones(listInforme, 959L, 955L);

            return listInforme.map(InformeRenovacionContratoMapper.MAPPER::toDTO);
        }else {
            this.logger.info("Usuario {} sin permiso G2 ",usuarioCtx.getIdUsuario());
        }
        throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_SIN_PERMISO_RENOVACION_CONTRATO);
}

    
    private Page<InformeRenovacionContrato> filtrarAprobaciones(Page<InformeRenovacionContrato> informes,
                                                            Long estadoFiltro,
                                                            Long grupoFiltro) {
    return informes.map(informe -> {
        List<RequerimientoAprobacion> aprobacionesFiltradas = informe.getAprobaciones().stream()
                .filter(reqAprob -> reqAprob.getIdEstadoLd().equals(estadoFiltro)
                        && reqAprob.getIdGrupoAprobadorLd().equals(grupoFiltro))
                .collect(Collectors.toList());

        informe.setAprobaciones(aprobacionesFiltradas);
        return informe;
    });
    }
}
