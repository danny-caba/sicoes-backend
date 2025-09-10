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
        Long esVigente =new Long(Constantes.ESTADO.ACTIVO);
        Page<InformeRenovacionContrato> listInforme = informeRenovacionContratoDao.findByFiltrosWithJoins(
            numeroExpediente,
            esVigente,
            estadoAprobacionInforme,
            idContratista,
            pageable
        );
         listInforme = filtrarAprobacionesBandeja(listInforme,usuarioCtx.getIdUsuario());

         return listInforme.map(InformeRenovacionContratoMapper.MAPPER::toDTO);

    }
    private Page<InformeRenovacionContrato> filtrarAprobacionesBandeja(Page<InformeRenovacionContrato> informes, Long idUsuario) {
        // Filtra las aprobaciones por usuario y elimina informes sin aprobaciones válidas
        List<InformeRenovacionContrato> filtrados = informes.stream()
            .map(informe -> {
                List<RequerimientoAprobacion> aprobacionesFiltradas = informe.getAprobaciones().stream()
                    .filter(aprobacion -> aprobacion != null
                        && aprobacion.getIdUsuario() != null
                        && aprobacion.getIdUsuario().equals(idUsuario))
                    .collect(Collectors.toList());
                informe.setAprobaciones(aprobacionesFiltradas);
                return informe;
            })
            .filter(informe -> informe.getAprobaciones() != null && !informe.getAprobaciones().isEmpty())
            .collect(Collectors.toList());
        // Retorna una página simulada (puedes ajustar según tu implementación de Page)
        return new org.springframework.data.domain.PageImpl<>(filtrados, informes.getPageable(), filtrados.size());
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
