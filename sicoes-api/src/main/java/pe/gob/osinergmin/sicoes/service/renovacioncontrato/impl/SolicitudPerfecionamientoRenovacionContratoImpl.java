package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.consumer.SigedApiConsumer;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Propuesta;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.SolicitudPerfRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.repository.PropuestaDao;
import pe.gob.osinergmin.sicoes.repository.SicoesSolicitudDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;


@Service
@Transactional
public class SolicitudPerfecionamientoRenovacionContratoImpl {

    private static final String DESCRIPCION_SOL_PER_RENOVACION_CONTRATO = "RENOVACION DE CONTRATO";
    private static final String TIPO_SOLICITUD_PERF_CONTRATO = "Inscripción";
    private static final String  ESTADO_PRELIMINAR= "1";
    private static final String  ESTADO_SOLICITUD_PERF_CONTRATO_ACTIVO= "1";
    private static final Long PRESENTACION_DIAS_HABILES = 10L;
    private final Logger logger = LogManager.getLogger(SolicitudPerfecionamientoRenovacionContratoImpl.class);
    private final PropuestaDao propuestaDao;
    private final SicoesSolicitudDao sicoesSolicitudDao;
    private final InformeRenovacionContratoDao informeRenovacionContratoDao;
    private final ListadoDetalleService listadoDetalleService;
    private final SigedApiConsumer sigedApiConsumer;
    
    public  SolicitudPerfecionamientoRenovacionContratoImpl (
        PropuestaDao propuestaDao,
        SicoesSolicitudDao sicoesSolicitudDao,
        InformeRenovacionContratoDao informeRenovacionContratoDao,
        ListadoDetalleService listadoDetalleService,
        SigedApiConsumer sigedApiConsumer) {
        this.propuestaDao = propuestaDao;
        this.sicoesSolicitudDao = sicoesSolicitudDao;
        this.informeRenovacionContratoDao = informeRenovacionContratoDao;
        this.listadoDetalleService = listadoDetalleService;
        this.sigedApiConsumer = sigedApiConsumer;
    }

    public SicoesSolicitud crearSolicitudPerfecionamientoRenovacionContrato(SolicitudPerfRenovacionContratoDTO solicitudPerfRenovacionContratoDTO, Contexto contexto) {
        this.logger.info("crearSolicitudPerfecionamientoRenovacionContrato - numExpediente: {}", solicitudPerfRenovacionContratoDTO.getNuExpediente());
        
        Optional<Propuesta>  propuestaOptional = propuestaDao.findById(solicitudPerfRenovacionContratoDTO.getIdSoliPerfCont());
        if(!propuestaOptional.isPresent()){
            throw new ValidacionException(Constantes.CODIGO_MENSAJE.PROPUESTA_NO_ENCONTRADA, "propuesta no encontrada para idRequerimiento: " + solicitudPerfRenovacionContratoDTO.getIdReqRenovacion());
        }
        Propuesta propuesta = propuestaOptional.get();
        Supervisora supervisora = propuesta.getSupervisora();

        Date  dateUltimaFechaAprobacion = getDateUltimaFechaAprobacionInfome(solicitudPerfRenovacionContratoDTO.getIdInformeRenovacion());
            
        SicoesSolicitud sicoesSolicitud = new SicoesSolicitud();
        sicoesSolicitud.setPropuesta(propuesta);
        sicoesSolicitud.setSupervisora(supervisora);
        sicoesSolicitud.setDescripcionSolicitud(DESCRIPCION_SOL_PER_RENOVACION_CONTRATO);
        sicoesSolicitud.setEstado(ESTADO_PRELIMINAR); 
        sicoesSolicitud.setEstadoProcesoSolicitud(ESTADO_SOLICITUD_PERF_CONTRATO_ACTIVO);
        sicoesSolicitud.setTipoSolicitud(TIPO_SOLICITUD_PERF_CONTRATO);

        Date fechaCaducidad = sigedApiConsumer.calcularFechaFin(dateUltimaFechaAprobacion, PRESENTACION_DIAS_HABILES, Constantes.DIAS_HABILES);
        sicoesSolicitud.setFechaHoraPresentacion(fechaCaducidad);
        sicoesSolicitud.setNumeroExpediente(solicitudPerfRenovacionContratoDTO.getNuExpediente());

        AuditoriaUtil.setAuditoriaRegistro(sicoesSolicitud,contexto);
        return  sicoesSolicitudDao.save(sicoesSolicitud);
    }

    private Date  getDateUltimaFechaAprobacionInfome(Long idInformeRenovacion) {
        
        InformeRenovacionContrato informeRenovacionContratoOptional = informeRenovacionContratoDao.getInformeRenovacion(idInformeRenovacion)
        .orElseThrow(() -> new ValidacionException(
            Constantes.CODIGO_MENSAJE.INFORME_PRESUPUESTO_RENOVACION_CONTRATO_NO_ENCONTRADO,
            "Informe renovación no encontrado para idInformeRenovacion: " + idInformeRenovacion
        ));

        ListadoDetalle grupo = validarListadoDetalle(Constantes.LISTADO.GRUPOS.CODIGO, Constantes.LISTADO.GRUPOS.G3);
        ListadoDetalle grupoAprobador = validarListadoDetalle(
                Constantes.LISTADO.GRUPO_APROBACION.CODIGO, Constantes.LISTADO.GRUPO_APROBACION.GSE);
        
        return informeRenovacionContratoOptional.getAprobaciones().stream()
                .filter(req -> req.getEstado().getIdListadoDetalle().equals(grupo.getIdListadoDetalle())
                        && req.getIdGrupoAprobadorLd().equals(grupoAprobador.getIdListadoDetalle()))
                .map(RequerimientoAprobacion::getFeAprobacion)
                .findFirst()
                .orElseThrow(() -> new ValidacionException(
                        Constantes.CODIGO_MENSAJE.APROBACION_NO_ENCONTRADA,
                        "No se encontró aprobación válida en informe " + idInformeRenovacion
                ));
    }


    private ListadoDetalle validarListadoDetalle(String codigo, String valor) {
        return Optional.ofNullable(listadoDetalleService.obtenerListadoDetalle(codigo, valor))
                .orElseThrow(() -> {
                    logger.warn("ListadoDetalle no encontrado: {} - {}", codigo, valor);
                    return new ValidacionException(Constantes.CODIGO_MENSAJE.LISTADO_DETALLE_NO_ENCONTRADO,
                            "ListadoDetalle no encontrado para código=" + codigo + " y valor=" + valor);
                });
    }
}
