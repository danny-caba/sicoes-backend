package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;
import pe.gob.osinergmin.sicoes.model.Requerimiento;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumento;
import pe.gob.osinergmin.sicoes.model.RequerimientoDocumentoDetalle;
import pe.gob.osinergmin.sicoes.model.Supervisora;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoDTO;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDocumentoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDocumentoDetalleDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.NotificacionService;
import pe.gob.osinergmin.sicoes.service.RequerimientoDocumentoService;
import pe.gob.osinergmin.sicoes.service.SupervisoraService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_FIN_ANTES_INICIO;
import static pe.gob.osinergmin.sicoes.util.Constantes.CODIGO_MENSAJE.ERROR_FECHA_INICIO_ANTES_HOY;

@Service
public class RequerimientoDocumentoServiceImpl implements RequerimientoDocumentoService {

    private static final Logger logger = LogManager.getLogger(RequerimientoDocumentoServiceImpl.class);

    @Autowired
    private RequerimientoDocumentoDao requerimientoDocumentoDao;

    @Autowired
    private RequerimientoDocumentoDetalleDao requerimientoDocumentoDetalleDao;

    @Autowired
    private ListadoDetalleService listadoDetalleService;
    @Autowired
    private SupervisoraService supervisoraService;
    @Autowired
    private NotificacionService notificacionService;

    @Transactional
    public Page<RequerimientoDocumento> listar(FiltroRequerimientoDocumentoDTO filtro, Pageable pageable, Contexto contexto) {
        Date fechaInicio = filtro.getFechaInicio();
        Date fechaFin = filtro.getFechaFin();
        if (fechaInicio != null && fechaInicio.after(new Date())) {
            throw new ValidacionException(ERROR_FECHA_INICIO_ANTES_HOY);
        }
        if (fechaInicio != null && fechaFin != null && fechaFin.before(fechaInicio)) {
            throw new ValidacionException(ERROR_FECHA_FIN_ANTES_INICIO);
        }
        if (fechaInicio != null) fechaInicio = DateUtil.getInitDay(fechaInicio);
        if (fechaFin != null) fechaFin = DateUtil.getEndDay(fechaFin);
        return requerimientoDocumentoDao.listarRequerimientosDocumentos(filtro.getEstado(), fechaInicio, fechaFin, pageable);
    }

    @Override
    public RequerimientoDocumentoDetalle obtenerPorRequerimientoDocumentoUuid(String requerimientoUuid) {
        return requerimientoDocumentoDetalleDao.obtenerPorUuid(requerimientoUuid)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_DOCUMENTO_DETALLE_NO_ENCONTRADO));
    }

    @Override
    @Transactional
    public RequerimientoDocumento registrar(List<RequerimientoDocumentoDetalle> listRequerimientoDocumentoDetalle, Long idRequerimiento, Contexto contexto){
        RequerimientoDocumento requerimientoDocumento = new RequerimientoDocumento();
        AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumento, contexto);
        requerimientoDocumento.setRequerimientoDocumentoUuid(UUID.randomUUID().toString());
        requerimientoDocumento.setFlagActivo("1");
        requerimientoDocumento.setFechaIngreso(new Date());
        Requerimiento requerimiento = new Requerimiento();
        requerimiento.setIdRequerimiento(idRequerimiento);
        requerimientoDocumento.setRequerimiento(requerimiento);
        ListadoDetalle estadoEnProceso = listadoDetalleService.obtenerListadoDetalle(
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.CODIGO,
                Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.EN_PROCESO
        );
        if (estadoEnProceso == null) {
            throw new ValidacionException("Estado EN PROCESO no configurado en ListadoDetalle");
        }
        requerimientoDocumento.setEstado(estadoEnProceso);
        for (RequerimientoDocumentoDetalle requerimientoDocumentoDetalle : listRequerimientoDocumentoDetalle) {
            AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumentoDetalle, contexto);
            requerimientoDocumentoDetalle.setRequerimientoDocumento(requerimientoDocumento);
            requerimientoDocumento.getDetalles().add(requerimientoDocumentoDetalle);
        }
        return requerimientoDocumentoDao.save(requerimientoDocumento);
    }

    @Override
    @Transactional
    public RequerimientoDocumento revisar(String documentoUuid, List<RequerimientoDocumentoDetalle> detalleDocumento, Contexto contexto) {
        //Validar Req. Documento en estado concluido?

        List<RequerimientoDocumentoDetalle> detallesBD = new ArrayList<>();
        for(RequerimientoDocumentoDetalle detalles: detalleDocumento) {
            RequerimientoDocumentoDetalle detalle = requerimientoDocumentoDetalleDao.findById(detalles.getIdRequerimientoDocumentoDetalle())
                    .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_DOCUMENTO_DETALLE_NO_ENCONTRADO));
            detalle.setFlagVistoBueno(detalles.getFlagVistoBueno());
            detallesBD.add(detalle);
        }
        RequerimientoDocumento requerimientoDocumento = requerimientoDocumentoDao.obtenerPorUuid(documentoUuid)
                .orElseThrow(() -> new ValidacionException(Constantes.CODIGO_MENSAJE.REQUERIMIENTO_DOCUMENTO_NO_ENCONTRADO));

        List<RequerimientoDocumentoDetalle> detalleDocumentoSinVistoBueno = detallesBD
                .stream()
                .filter(det -> det.getFlagVistoBueno().equals(Constantes.ESTADO.INACTIVO))
                .collect(Collectors.toList());
        List<RequerimientoDocumentoDetalle> detalleDocumentoExterno = detalleDocumentoSinVistoBueno
                .stream()
                .filter(det -> det.getOrigenRequisito().getCodigo().equals(Constantes.LISTADO.ORIGEN_REQUISITO.EXTERNO))
                .collect(Collectors.toList());
        List<RequerimientoDocumentoDetalle> detalleDocumentoInterno = detalleDocumentoSinVistoBueno
                .stream()
                .filter(det -> det.getOrigenRequisito().getCodigo().equals(Constantes.LISTADO.ORIGEN_REQUISITO.REQUERIMIENTO))
                .collect(Collectors.toList());

        if(!detalleDocumentoExterno.isEmpty()) {//Sin visto bueno: Requisitos Supervisor
            Supervisora supervisora = requerimientoDocumento.getRequerimiento().getSupervisora();

            //update req doc estado solicitud preliminar
            ListadoDetalle estadoEnProceso = listadoDetalleService.obtenerListadoDetalle(
                    Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.CODIGO,
                    Constantes.LISTADO.ESTADO_REQ_DOCUMENTO.PRELIMINAR
            );
            requerimientoDocumento.setEstado(estadoEnProceso);
            AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumento, contexto);
            requerimientoDocumento = requerimientoDocumentoDao.save(requerimientoDocumento);

            //Enviar Notificacion Supervisor
            notificacionService.enviarMensajeVistoBuenoSupervisor(supervisora, detalleDocumentoExterno, contexto);
        }

        if(!detalleDocumentoInterno.isEmpty()) {//Sin visto bueno: Requisitos Coordinador Gestion
            //Enviar Notificacion Coordinador
            notificacionService.enviarMensajeVistoBuenoCoordinador(contexto);
        }

        //Actualizar Visto Bueno
        for (RequerimientoDocumentoDetalle requerimientoDocumentoDetalle : detallesBD) {
            AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumentoDetalle, contexto);
            requerimientoDocumentoDetalleDao.save(requerimientoDocumentoDetalle);
        }
        return requerimientoDocumento;
    }

    @Override
    public RequerimientoDocumento guardar(RequerimientoDocumento model, Contexto contexto) {
        return null;
    }

    @Override
    public RequerimientoDocumento obtener(Long aLong, Contexto contexto) {
        return null;
    }

    @Override
    public void eliminar(Long aLong, Contexto contexto) {

    }
}
