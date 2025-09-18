package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.osinergmin.sicoes.model.renovacioncontrato.HistorialEstadoRequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.HistorialEstadoRequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.HistorialRequerimientoRenovacionService;
import pe.gob.osinergmin.sicoes.util.AuditoriaUtil;
import pe.gob.osinergmin.sicoes.util.Constantes;
import pe.gob.osinergmin.sicoes.util.Contexto;

/**
 * Implementación del servicio para el manejo del historial de estados de requerimientos de renovación
 * 
 * @author SICOES
 * @version 1.0
 */
@Service
@Transactional
public class HistorialRequerimientoRenovacionImplService implements HistorialRequerimientoRenovacionService {

    private static final Logger logger = LogManager.getLogger(HistorialRequerimientoRenovacionImplService.class);

    @Autowired
    private HistorialEstadoRequerimientoRenovacionDao historialEstadoRequerimientoRenovacionDao;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registrarHistorialRequerimientoRenovacion(RequerimientoRenovacion requerimientoRenovacion, Contexto contexto) throws Exception {
        try {
            // Validar que el requerimiento tenga estado
            if (requerimientoRenovacion.getEstadoReqRenovacion() != null && 
                requerimientoRenovacion.getEstadoReqRenovacion().getIdListadoDetalle() != null) {
                
                logger.info("Iniciando registro de historial para requerimiento ID: {} con estado: {}", 
                           requerimientoRenovacion.getIdReqRenovacion(), 
                           requerimientoRenovacion.getEstadoReqRenovacion().getIdListadoDetalle());

                // Crear nueva instancia del historial
                HistorialEstadoRequerimientoRenovacion historial = new HistorialEstadoRequerimientoRenovacion();
                
                // Setear los valores principales
                historial.setIdRequerimiento(requerimientoRenovacion.getIdReqRenovacion());
                historial.setIdUsuario(requerimientoRenovacion.getIdUsuario());
                historial.setDeEstadoAnteriorLd(null); // Estado anterior (no existía en inserción)
                historial.setDeEstadoNuevoLd(requerimientoRenovacion.getEstadoReqRenovacion().getIdListadoDetalle()); // Estado nuevo
                historial.setFeFechaCambio(new Timestamp(System.currentTimeMillis())); // SYSDATE equivalente
                historial.setEsRegistro(Constantes.ESTADO.ACTIVO); // '1'
                
                // Setear campos de auditoría usando AuditoriaUtil
                AuditoriaUtil.setAuditoriaRegistro(historial, contexto);
                
                // Guardar en la base de datos
                historialEstadoRequerimientoRenovacionDao.save(historial);
                
                logger.info("Historial registrado exitosamente con ID: {} para requerimiento: {}", 
                           historial.getIdHistorialEstadoCampo(), 
                           requerimientoRenovacion.getIdReqRenovacion());
                
            } else {
                logger.warn("No se registró historial para requerimiento ID: {} porque no tiene estado definido", 
                           requerimientoRenovacion.getIdReqRenovacion());
            }
            
        } catch (Exception e) {
            logger.error("Error al registrar historial para requerimiento ID: {}", 
                        requerimientoRenovacion.getIdReqRenovacion(), e);
            throw new Exception("Error al registrar historial de requerimiento de renovación: " + e.getMessage(), e);
        }
    }
}