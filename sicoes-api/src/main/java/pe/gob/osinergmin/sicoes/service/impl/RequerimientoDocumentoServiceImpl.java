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
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoCoordinadorDTO;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDocumentoDTO;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDocumentoDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDocumentoDetalleDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.RequerimientoDocumentoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public Page<RequerimientoDocumento> listarRequerimientosDocumentos(FiltroRequerimientoDocumentoDTO filtro, Pageable pageable, Contexto contexto) {
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
        return requerimientoDocumentoDao.listarRequerimientosDocumentos(contexto.getUsuario().getIdUsuario(), filtro.getEstado(), fechaInicio, fechaFin, pageable);
    }

    @Override
    public List<RequerimientoDocumentoDetalle> listarRequerimientosDocumentosDetalle(String requerimientoUuid) {
        return requerimientoDocumentoDetalleDao.listarPorUuid(requerimientoUuid);
    }

    @Override
    @Transactional
    public RequerimientoDocumento registrarRequerimientosDocumento(List<RequerimientoDocumentoDetalle> listRequerimientoDocumentoDetalle, Contexto contexto){
        RequerimientoDocumento requerimientoDocumento = new RequerimientoDocumento();
        AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumento, contexto);AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumento, contexto);
        requerimientoDocumento.setRequerimientoDocumentoUuid(UUID.randomUUID().toString());
        requerimientoDocumento.setFlagActivo("1");
        requerimientoDocumento.setFechaIngreso(new Date());
        Requerimiento requerimiento = new Requerimiento();
        requerimiento.setIdRequerimiento(1L);
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
            AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumentoDetalle, contexto);AuditoriaUtil.setAuditoriaRegistro(requerimientoDocumentoDetalle, contexto);
            requerimientoDocumentoDetalle.setRequerimientoDocumento(requerimientoDocumento);
            requerimientoDocumento.getDetalles().add(requerimientoDocumentoDetalle);
        }
        return requerimientoDocumentoDao.save(requerimientoDocumento);
    }

    public Page<RequerimientoDocumento> listarRequerimientosDocumentosCoordinador(FiltroRequerimientoDocumentoCoordinadorDTO filtro, Pageable pageable, Contexto contexto) {
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
        return requerimientoDocumentoDao.listarRequerimientosDocumentosCoordinador(contexto.getUsuario().getIdUsuario(), filtro.getDivision(), filtro.getPerfil(), filtro.getSupervisora(), filtro.getEstado(), fechaInicio, fechaFin, pageable);
    }

    @Override
    public RequerimientoDocumentoDetalle acualizarRequerimientosDocumentoDetalle(RequerimientoDocumentoDetalle requerimientoDocumentoDetalle, Contexto contexto) {
        return null;
    }

    @Override
    public RequerimientoDocumento evaluarRequerimientosDocumento(RequerimientoDocumento requerimientoDocumento, Contexto contexto) {
        return null;
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
