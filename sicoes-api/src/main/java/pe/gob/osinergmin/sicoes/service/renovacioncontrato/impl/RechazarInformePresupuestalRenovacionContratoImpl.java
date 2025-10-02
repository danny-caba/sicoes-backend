package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Usuario;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoAprobacionDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.SolicitudPerfecionamientoContrato;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.SolicitudPerfecionamientoContratoDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.RequerimientoAprobacionMapper;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
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

        ListadoDetalle estadoAprobacionInformeRCAsignado = listadoDetalleService.obtenerListadoDetalle(
                "ESTADO_APROBACION"    ,
                "ASIGNADO"
        );

        informeRenovacionContrato.setEstadoAprobacionInforme(estadoAprobacionInformeRCAsignado);

        actualizarEstadoInformeRenovacionContrato(informeRenovacionContrato, contexto);
        
        SolicitudPerfecionamientoContrato solicitudPerfecionamientoContrato = listaPerfilesAprobadoresBySolicitud.get(0);

        RequerimientoAprobacion requerimientoAprobacion = new RequerimientoAprobacion();
        requerimientoAprobacion.setDeObservacion(requerimientoAprobacionDTO.getDeObservacion());
        requerimientoAprobacion.setIdInformeRenovacion(requerimientoAprobacionDTO.getIdReqInforme());
        
        // NO asignar idRequerimiento - la FK espera SICOES_TC_REQUERIMIENTO que no existe en el modelo actual
        // if (informeRenovacionContrato.getRequerimiento() != null) {
        //     requerimientoAprobacion.setIdRequerimiento(informeRenovacionContrato.getRequerimiento().getIdReqRenovacion());
        // }
        requerimientoAprobacion.setUsuario(contexto.getUsuario());
        requerimientoAprobacion.setIdTipoLd(1L);//revisar si aplica

        ListadoDetalle grupo3 = listadoDetalleService.obtenerListadoDetalle(
           Constantes.LISTADO.GRUPOS.CODIGO, Constantes.LISTADO.GRUPOS.G3);
        requerimientoAprobacion.setGrupo(grupo3);
        
        // CORREGIDO: Usar ID_ESTADO_LD = 960 para rechazo según requerimiento
        ListadoDetalle estadoRechazado = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_APROBACION.CODIGO, Constantes.LISTADO.ESTADO_APROBACION.DESAPROBADO);
        requerimientoAprobacion.setEstado(estadoRechazado);
        
        // AGREGADO: Establecer FE_RECHAZO con la fecha actual
        requerimientoAprobacion.setFeRechazo(new Date());
        
        requerimientoAprobacion.setIdReqInforme(requerimientoAprobacionDTO.getIdReqInforme());

        // AGREGADO: Asignar datos de auditoría completos
        AuditoriaUtil.setAuditoriaRegistro(requerimientoAprobacion, contexto);

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
