package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoAprobacionDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.ListadoDetalleRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.SolicitudPerfecionamientoContrato;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.SolicitudPerfecionamientoContratoDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.RequerimientoAprobacionMapper;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

@Service
public class RechazarInformePresupuestalRenovacionContratoImpl {
    private final Logger logger = LogManager.getLogger(RechazarInformePresupuestalRenovacionContratoImpl.class);

    private final SolicitudPerfecionamientoContratoDao solicitudPerfecionamientoContratoDao;
    private final InformeRenovacionContratoDao informeRenovacionContratoDao;
    private final RequerimientoAprobacionDao requerimientoAprobacionDao;
    private final ListadoDetalleService listadoDetalleService;
     public RechazarInformePresupuestalRenovacionContratoImpl(
        SolicitudPerfecionamientoContratoDao solicitudPerfecionamientoContratoDao,
        InformeRenovacionContratoDao informeRenovacionContratoDao,
        RequerimientoAprobacionDao requerimientoAprobacionDao,
        ListadoDetalleService listadoDetalleService
     ) {
         
         this.solicitudPerfecionamientoContratoDao = solicitudPerfecionamientoContratoDao;
         this.informeRenovacionContratoDao = informeRenovacionContratoDao;
         this.requerimientoAprobacionDao = requerimientoAprobacionDao;
         this.listadoDetalleService = listadoDetalleService; 
     }
    public RequerimientoAprobacionDTO ejecutar(
        RequerimientoAprobacionDTO requerimientoAprobacionDTO,
        Contexto contexto) {
        
        Optional<InformeRenovacionContrato> informe = informeRenovacionContratoDao.findById(requerimientoAprobacionDTO.getIdReqInforme());
        if(!informe.isPresent()){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.INFORME_PRESUPUESTO_RENOVACION_CONTRATO_NO_ENCONTRADO);
        }
        InformeRenovacionContrato informeRenovacionContrato = informe.get();
        Long idSolicitud = informeRenovacionContrato.getRequerimiento().getSolicitudPerfil().getIdSolicitud(); 


        List<SolicitudPerfecionamientoContrato> listaPerfilesAprobadoresBySolicitud  = 
         solicitudPerfecionamientoContratoDao.getPerfilAprobadorByIdPerfilListadoDetalle(idSolicitud);
        
        if (listaPerfilesAprobadoresBySolicitud.isEmpty()) {
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.PERFIL_APROBADOR_RENOVACION_CONTRATO_NO_ENCONTRADO);
        }

        ListadoDetalle estadoAprobacionInformeRC = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_APROBACION"    ,
                "ASIGNADO"
        );

        ListadoDetalleRenovacionContrato listadoDetalleRenovacionContrato= new ListadoDetalleRenovacionContrato();
        listadoDetalleRenovacionContrato.setIdListadoDetalle(estadoAprobacionInformeRC.getIdListadoDetalle());  
        informeRenovacionContrato.setEstadoAprobacionInforme(listadoDetalleRenovacionContrato);

        actualizarEstadoInformeRenovacionContrato(informeRenovacionContrato, contexto);
        
    
        SolicitudPerfecionamientoContrato solicitudPerfecionamientoContrato = listaPerfilesAprobadoresBySolicitud.get(0);

        
        RequerimientoAprobacion requerimientoAprobacion = new RequerimientoAprobacion();        
        requerimientoAprobacion.setDeObservacion(requerimientoAprobacionDTO.getDeObservacion());
        requerimientoAprobacion.setIdInformeRenovacion(requerimientoAprobacionDTO.getIdReqInforme());
        Usuario usuario = contexto.getUsuario();
        requerimientoAprobacion.setIdUsuario(usuario.getIdUsuario());
        requerimientoAprobacion.setIdTipoLd(1L);//revisar si aplica

        ListadoDetalle estadoAprobacionGrupoLD = listadoDetalleService.obtenerListadoDetalle(
            "GRUPOS"    ,
            "G3"
        );
        requerimientoAprobacion.setIdGrupoLd(estadoAprobacionGrupoLD.getIdListadoDetalle());
        
        ListadoDetalle estadoLD = listadoDetalleService.obtenerListadoDetalle(
            "ESTADO_APROBACION"    ,
            "DESAPROBADO"
        );
        requerimientoAprobacion.setIdEstadoLd(estadoLD.getIdListadoDetalle());
        requerimientoAprobacion.setIdReqInforme(requerimientoAprobacionDTO.getIdReqInforme());

        requerimientoAprobacion = requerimientoAprobacionDao.save(requerimientoAprobacion);
            
        return RequerimientoAprobacionMapper.MAPPER.toDTO(requerimientoAprobacion);
    }
    private void actualizarEstadoInformeRenovacionContrato(InformeRenovacionContrato informeRenovacionContrato,Contexto contexto) {
        String usuarioCodigo=contexto.getUsuario().getIdUsuario().toString();
        String ipUpdated = contexto.getIp();

        int afectados =informeRenovacionContratoDao.actualizarEstadoEvaluacionPropuestPerfilPuestoPorId(
            informeRenovacionContrato.getIdInformeRenovacion(), 
            informeRenovacionContrato.getEstadoAprobacionInforme().getIdListadoDetalle(),
             LocalDateTime.now(),
              ipUpdated,
              usuarioCodigo);

        this.logger.info("actualizarEstadoInformeRenovacionContrato afectados {}", afectados);
    }

}
