package pe.gob.osinergmin.sicoes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.mapper.RequerimientoMapper;
import pe.gob.osinergmin.sicoes.model.*;
import pe.gob.osinergmin.sicoes.model.dto.FiltroRequerimientoDTO;
import pe.gob.osinergmin.sicoes.model.dto.RequerimientoDTO;
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

    @Autowired
    private RequerimientoMapper requerimientoMapper;

    public RequerimientoDTO guardar(RequerimientoDTO requerimientoDTO, Contexto contexto) {
        try {
            Requerimiento entidad = requerimientoMapper.toEntity(requerimientoDTO);
            AuditoriaUtil.setAuditoriaRegistro(entidad, contexto);
            Requerimiento guardado = requerimientoDao.save(entidad);
            return requerimientoMapper.toDTO(guardado);
        } catch (Exception ex) {
            logger.error("Error al guardar el requerimiento. Contexto: {}, DTO: {}", contexto, requerimientoDTO, ex);
            throw new RuntimeException("Error al guardar el requerimiento", ex);
        }
    }

    @Override
    @Transactional
    public Page<RequerimientoDTO> listar(FiltroRequerimientoDTO filtroRequerimientoDTO, Pageable pageable, Contexto contexto) {
        Date fechaInicio = filtroRequerimientoDTO.getFechaInicio();
        Date fechaFin = filtroRequerimientoDTO.getFechaFin();

        if (fechaInicio != null && fechaInicio.after(new Date())) {
            throw new ValidacionException("E002002");
        }
        if (fechaInicio != null && fechaFin != null && fechaFin.before(fechaInicio)) {
            throw new ValidacionException("E002001");
        }
        if (fechaInicio != null) {
            fechaInicio = DateUtil.getInitDay(fechaInicio);
        }
        if (fechaFin != null) {
            fechaFin = DateUtil.getEndDay(fechaFin);
        }
        Division division = filtroRequerimientoDTO.getDivision() != null
                ? crearDivision(filtroRequerimientoDTO.getDivision())
                : null;
        ListadoDetalle perfil = filtroRequerimientoDTO.getPerfil() != null
                ? crearListadoDetalle(filtroRequerimientoDTO.getPerfil())
                : null;
        Supervisora supervisora = filtroRequerimientoDTO.getSupervisora() != null
                ? crearSupervisora(filtroRequerimientoDTO.getSupervisora())
                : null;
        ListadoDetalle estadoAprobacion = filtroRequerimientoDTO.getEstadoAprobacion() != null
                ? crearListadoDetalle(filtroRequerimientoDTO.getEstadoAprobacion())
                : null;
        Page<Requerimiento> pagina = requerimientoDao.listarRequerimientos(
                division, perfil, fechaInicio, fechaFin, supervisora, estadoAprobacion, pageable
        );
        return pagina.map(requerimientoMapper::toDTO);
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
    public RequerimientoDTO archivar(Long id, String observacion, Contexto contexto) {
        Optional<Requerimiento> optional = requerimientoDao.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("Requerimiento no encontrado");
        }
        Requerimiento entidad = optional.get();
        ListadoDetalle estadoArchivado = listadoDetalleDao.obtenerListadoDetalle("ESTADO_REQUERIMIENTO", "ARCHIVADO");
        if (estadoArchivado == null) {
            throw new IllegalStateException("Estado ARCHIVADO no configurado en ListadoDetalle");
        }
        entidad.setEstado(estadoArchivado);
        entidad.setDeObservacion(observacion);
        entidad.setIpActualizacion(contexto.getIp());
        entidad.setUsuActualizacion(contexto.getUsuario().getUsuario());
        entidad.setFecActualizacion(new Date());
        Requerimiento actualizado = requerimientoDao.save(entidad);
        return requerimientoMapper.toDTO(actualizado);
    }

    @Override
    public Optional<RequerimientoDTO> obtenerPorId(Long id) {
        return requerimientoDao.buscarPorId(id).map(requerimientoMapper::toDTO);
    }

}
