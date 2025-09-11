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
            Long estadoAprobacionInforme,
            Long idContratista ,
            Contexto contexto,
            Pageable pageable) {

        Usuario usuarioCtx = contexto.getUsuario();
        if (usuarioCtx.getRoles().stream().noneMatch(rol -> rol.getCodigo().equals(Constantes.ROLES.APROBADOR_TECNICO))){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.USUARIO_SIN_PERMISO_RENOVACION_CONTRATO);
        }
        this.logger.info("Tipo aprobador {}", tipoAprobador);
        return flujoG1(usuarioCtx, numeroExpediente, estadoAprobacionInforme, idContratista, pageable);

}

    private Page<InformeRenovacionContratoDTO> flujoG1(
        Usuario usuarioCtx,
        String numeroExpediente, 
        Long estadoAprobacionInforme,
        Long idContratista ,
        Pageable pageable
        ) {
        Boolean esVigente =Boolean.TRUE;
        Page<InformeRenovacionContrato> listInforme = informeRenovacionContratoDao.findByFiltrosWithJoins2(
            numeroExpediente,
            esVigente,
            estadoAprobacionInforme,
            idContratista,
            usuarioCtx.getIdUsuario(),
            pageable
        );


         return listInforme.map(InformeRenovacionContratoMapper.MAPPER::toDTO);

    }



}
