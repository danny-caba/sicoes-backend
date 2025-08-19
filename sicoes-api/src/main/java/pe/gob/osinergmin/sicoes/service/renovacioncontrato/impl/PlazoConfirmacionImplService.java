package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.PlazoConfirmacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.PlazoConfirmacionDTO;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.PlazoConfirmacionDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.PlazoConfirmacionService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
@Transactional
public class PlazoConfirmacionImplService implements PlazoConfirmacionService {

    private Logger logger = LogManager.getLogger(PlazoConfirmacionImplService.class);

    @Autowired
    private PlazoConfirmacionDao plazoConfirmacionDao;

    @Override
    @Transactional(readOnly = true)
    public List<PlazoConfirmacion> listar() {
        logger.info("listar - Obteniendo todos los plazos de confirmación activos");
        return plazoConfirmacionDao.listarActivos();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlazoConfirmacion> listarPaginado(Pageable pageable) {
        logger.info("listarPaginado - Página: {}, Tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return plazoConfirmacionDao.listarActivosPaginado(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public PlazoConfirmacion obtenerPorId(Long id) {
        logger.info("obtenerPorId - ID: {}", id);
        
        if (id == null) {
            throw new IllegalArgumentException("El ID del plazo de confirmación es requerido");
        }
        
        PlazoConfirmacion plazo = plazoConfirmacionDao.obtenerPorId(id);
        if (plazo == null) {
            throw new IllegalArgumentException("No se encontró el plazo de confirmación con ID: " + id);
        }
        
        return plazo;
    }

    @Override
    public PlazoConfirmacion crear(PlazoConfirmacionDTO plazoDTO, Contexto contexto) {
        logger.info("crear - Usuario: {}, Datos: {}", contexto.getUsuario().getIdUsuario(), plazoDTO);
        
        validarDatos(plazoDTO);
        
        PlazoConfirmacion plazo = new PlazoConfirmacion();
        plazo.setFeBase(plazoDTO.getFeBase());
        plazo.setInTipoDia(plazoDTO.getInTipoDia());
        plazo.setNuDias(plazoDTO.getNuDias());
        plazo.setEsRegistro(Constantes.ESTADO.ACTIVO);
        
        AuditoriaUtil.setAuditoriaRegistro(plazo, contexto);
        
        return plazoConfirmacionDao.save(plazo);
    }

    @Override
    public PlazoConfirmacion actualizar(Long id, PlazoConfirmacionDTO plazoDTO, Contexto contexto) {
        logger.info("actualizar - ID: {}, Usuario: {}", id, contexto.getUsuario().getIdUsuario());
        
        if (id == null) {
            throw new IllegalArgumentException("El ID del plazo de confirmación es requerido");
        }
        
        validarDatos(plazoDTO);
        
        PlazoConfirmacion plazo = obtenerPorId(id);
        
        plazo.setFeBase(plazoDTO.getFeBase());
        plazo.setInTipoDia(plazoDTO.getInTipoDia());
        plazo.setNuDias(plazoDTO.getNuDias());
        
        AuditoriaUtil.setAuditoriaActualizacion(plazo, contexto);
        
        return plazoConfirmacionDao.save(plazo);
    }

    @Override
    public void eliminar(Long id, Contexto contexto) {
        logger.info("eliminar - ID: {}, Usuario: {}", id, contexto.getUsuario().getIdUsuario());
        
        if (id == null) {
            throw new IllegalArgumentException("El ID del plazo de confirmación es requerido");
        }
        
        PlazoConfirmacion plazo = obtenerPorId(id);
        plazo.setEsRegistro(Constantes.ESTADO.INACTIVO);
        
        AuditoriaUtil.setAuditoriaActualizacion(plazo, contexto);
        
        plazoConfirmacionDao.save(plazo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlazoConfirmacion> listarPorTipoDia(Integer tipoDia) {
        logger.info("listarPorTipoDia - Tipo día: {}", tipoDia);
        
        if (tipoDia == null) {
            throw new IllegalArgumentException("El tipo de día es requerido");
        }
        
        return plazoConfirmacionDao.listarPorTipoDia(tipoDia);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlazoConfirmacion> listarPorDias(Integer dias) {
        logger.info("listarPorDias - Días: {}", dias);
        
        if (dias == null) {
            throw new IllegalArgumentException("El número de días es requerido");
        }
        
        return plazoConfirmacionDao.listarPorDias(dias);
    }

    private void validarDatos(PlazoConfirmacionDTO plazoDTO) {
        if (plazoDTO == null) {
            throw new IllegalArgumentException("Los datos del plazo de confirmación son requeridos");
        }
        
        if (plazoDTO.getFeBase() == null || plazoDTO.getFeBase().trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha base es requerida");
        }
        
        if (plazoDTO.getInTipoDia() == null) {
            throw new IllegalArgumentException("El tipo de día es requerido");
        }
        
        if (plazoDTO.getInTipoDia() != 1 && plazoDTO.getInTipoDia() != 2) {
            throw new IllegalArgumentException("El tipo de día debe ser 1 (Calendario) o 2 (Hábil)");
        }
        
        if (plazoDTO.getNuDias() == null || plazoDTO.getNuDias() <= 0) {
            throw new IllegalArgumentException("El número de días debe ser mayor a 0");
        }
        
        if (plazoDTO.getNuDias() > 99999) {
            throw new IllegalArgumentException("El número de días no puede ser mayor a 99999");
        }
    }
}
