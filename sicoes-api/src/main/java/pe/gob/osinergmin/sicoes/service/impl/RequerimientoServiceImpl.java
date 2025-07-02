package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.repository.ListadoDetalleDao;
import pe.gob.osinergmin.sicoes.repository.RequerimientoDao;
import pe.gob.osinergmin.sicoes.service.RequerimientoService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.DateUtil;
import pe.gob.osinergmin.sicoes.util.ValidacionException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class RequerimientoServiceImpl implements RequerimientoService {

    private static final Logger logger = LogManager.getLogger(RequerimientoServiceImpl.class);

    @Autowired
    private RequerimientoDao requerimientoDao;

    @Autowired
    private ListadoDetalleDao listadoDetalleDao;

    @Override
    public Requerimiento guardar(Requerimiento requerimiento, Contexto contexto) {
        AuditoriaUtil.setAuditoriaRegistro(requerimiento, contexto);
        return requerimientoDao.save(requerimiento);
    }

    @Override
    @Transactional
    public Page<Requerimiento> listar(FiltroRequerimientoDTO filtro, Pageable pageable, Contexto contexto) {
        Date fechaInicio = filtro.getFechaInicio();
        Date fechaFin = filtro.getFechaFin();
        if (fechaInicio != null && fechaInicio.after(new Date())) {
            throw new ValidacionException("E002002");
        }
        if (fechaInicio != null && fechaFin != null && fechaFin.before(fechaInicio)) {
            throw new ValidacionException("E002001");
        }
        if (fechaInicio != null) fechaInicio = DateUtil.getInitDay(fechaInicio);
        if (fechaFin != null) fechaFin = DateUtil.getEndDay(fechaFin);
        Division division = filtro.getDivision() != null ? crearDivision(filtro.getDivision()) : null;
        ListadoDetalle perfil = filtro.getPerfil() != null ? crearListadoDetalle(filtro.getPerfil()) : null;
        Supervisora supervisora = filtro.getSupervisora() != null ? crearSupervisora(filtro.getSupervisora()) : null;
        ListadoDetalle estadoAprobacion = filtro.getEstadoAprobacion() != null ? crearListadoDetalle(filtro.getEstadoAprobacion()) : null;
        return requerimientoDao.listarRequerimientos(division, perfil, fechaInicio, fechaFin, supervisora, estadoAprobacion, pageable);
    }

    private Division crearDivision(Long id) {
        Division division = new Division();
        division.setIdDivision(id);
        return division;
    }

    private ListadoDetalle crearListadoDetalle(Long id) {
        ListadoDetalle detalle = new ListadoDetalle();
        detalle.setIdListadoDetalle(id);
        return detalle;
    }

    private Supervisora crearSupervisora(Long id) {
        Supervisora s = new Supervisora();
        s.setIdSupervisora(id);
        return s;
    }

    @Override
    @Transactional
    public Requerimiento archivar(Long id, String observacion, Contexto contexto) {
        Requerimiento entidad = requerimientoDao.findById(id).orElseThrow(() -> new RuntimeException("Requerimiento no encontrado"));
        ListadoDetalle estadoArchivado = listadoDetalleDao.obtenerListadoDetalle("ESTADO_REQUERIMIENTO", "ARCHIVADO");
        if (estadoArchivado == null) {
            throw new IllegalStateException("Estado ARCHIVADO no configurado en ListadoDetalle");
        }
        entidad.setEstado(estadoArchivado);
        entidad.setDeObservacion(observacion);
        entidad.setIpActualizacion(contexto.getIp());
        entidad.setUsuActualizacion(contexto.getUsuario().getUsuario());
        entidad.setFecActualizacion(new Date());
        return requerimientoDao.save(entidad);
    }

    @Override
    public Optional<Requerimiento> obtenerPorId(Long id) {
        return requerimientoDao.buscarPorId(id);
    }

}
