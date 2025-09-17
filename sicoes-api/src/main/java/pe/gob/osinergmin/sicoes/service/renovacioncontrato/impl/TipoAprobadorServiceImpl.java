package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.TipoAprobadorResponseDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.TipoAprobadorService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.model.ListadoDetalle;

import java.util.List;

@Service
public class TipoAprobadorServiceImpl implements TipoAprobadorService {
    
    private final Logger logger = LogManager.getLogger(TipoAprobadorServiceImpl.class);
    
    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;
    
    @Autowired
    private ListadoDetalleService listadoDetalleService;
    
    @Override
    public TipoAprobadorResponseDTO identificarTipoAprobador(Contexto contexto) {
        Long idUsuario = contexto.getUsuario().getIdUsuario();
        
        logger.info("Identificando tipo de aprobador para usuario: {}", idUsuario);
        
        // Buscar requerimientos de aprobación asignados al usuario en estado ASIGNADO
        ListadoDetalle estadoAsignado = listadoDetalleService.obtenerListadoDetalle("ESTADO_APROBACION", "ASIGNADO");
        
        // Obtener todos los requerimientos activos del usuario
        List<RequerimientoAprobacion> requerimientos = requerimientoAprobacionDao.findByIdUsuarioAndIdEstadoLd(
            idUsuario, 
            estadoAsignado.getIdListadoDetalle()
        );
        
        if (requerimientos.isEmpty()) {
            logger.warn("No se encontraron requerimientos asignados para usuario: {}", idUsuario);
            return new TipoAprobadorResponseDTO(idUsuario, "EVALUADOR", "EVALUADOR");
        }
        
        // Tomar el primer requerimiento para determinar el tipo de aprobador
        RequerimientoAprobacion primerRequerimiento = requerimientos.get(0);
        Long idGrupoAprobadorLd = primerRequerimiento.getIdGrupoAprobadorLd();
        
        if (idGrupoAprobadorLd == null) {
            logger.warn("Requerimiento sin grupo aprobador para usuario: {}", idUsuario);
            return new TipoAprobadorResponseDTO(idUsuario, "EVALUADOR", "EVALUADOR");
        }
        
        // Obtener el código del grupo aprobador
        ListadoDetalle grupoAprobador = listadoDetalleService.obtener(idGrupoAprobadorLd, contexto);
        String codigoGrupo = grupoAprobador.getCodigo();
        
        logger.info("Usuario {} tiene grupo aprobador: {}", idUsuario, codigoGrupo);
        
        // Mapear código de grupo a tipo de aprobador
        String tipoAprobador;
        switch (codigoGrupo) {
            case "JEFE_UNIDAD":
                tipoAprobador = "G1";
                break;
            case "GERENTE":
                tipoAprobador = "G2";
                break;
            case "GPPM":
                tipoAprobador = "G3_GPPM";
                break;
            case "GSE":
                tipoAprobador = "G3_GSE";
                break;
            default:
                tipoAprobador = "EVALUADOR";
                codigoGrupo = "EVALUADOR";
        }
        
        logger.info("Usuario {} identificado como: {}", idUsuario, tipoAprobador);
        
        return new TipoAprobadorResponseDTO(idUsuario, tipoAprobador, codigoGrupo);
    }
}