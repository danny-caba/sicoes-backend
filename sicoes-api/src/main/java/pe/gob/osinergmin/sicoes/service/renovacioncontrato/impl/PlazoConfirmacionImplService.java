package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.PlazoConfirmacionMapper;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.PlazoConfirmacionRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.PlazoConfirmacionResponseDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.PlazoConfirmacion;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.PlazoConfirmacionDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.PlazoConfirmacionService;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;

@Service
public class PlazoConfirmacionImplService implements PlazoConfirmacionService {

    @Autowired
    private PlazoConfirmacionDao plazoConfirmacionDao;
    
    @Autowired
    private PlazoConfirmacionMapper plazoConfirmacionMapper;

    @Override
    public List<PlazoConfirmacionResponseDTO> listarPlazoConfirmacion(String estado) {
        List<PlazoConfirmacion> plazoConfirmacionList = plazoConfirmacionDao.buscarPorEstado(estado);
        return plazoConfirmacionMapper.toResponseDTOList(plazoConfirmacionList);
    }

    @Override
    public PlazoConfirmacionResponseDTO registrarPlazoConfirmacion(PlazoConfirmacionRequestDTO requestDTO) {
        if (requestDTO.getTipoDia() == null || (requestDTO.getTipoDia() != 1 && requestDTO.getTipoDia() != 2)) {
            throw new DataNotFoundException("tipoDia debe ser 1 o 2");
        }
        
        if (requestDTO.getFechaBase() == null) {
            throw new DataNotFoundException("fechaBase no puede ser nulo");
        }
        
        if (requestDTO.getNumeroDias() == null || requestDTO.getNumeroDias() <= 1) {
            throw new DataNotFoundException("numeroDias debe ser diferente de null y mayor de 1");
        }
        
        PlazoConfirmacion plazoConfirmacion;
        
        if (requestDTO.getIdPlazoConfirmacion() == null) {
            List<PlazoConfirmacion> plazoConfirmacionActivosList = plazoConfirmacionDao.buscarPorEstado("1");
            for (PlazoConfirmacion plazoActivo : plazoConfirmacionActivosList) {
                plazoActivo.setEsRegistro("0");
                plazoActivo.setUsuActualizacion(requestDTO.getUsuario());
                plazoActivo.setFecActualizacion(new Date());
                plazoActivo.setIpActualizacion(requestDTO.getIp());
                plazoConfirmacionDao.save(plazoActivo);
            }
            
            plazoConfirmacion = plazoConfirmacionMapper.toEntity(requestDTO);
            plazoConfirmacion.setEsRegistro("1");
            plazoConfirmacion.setUsuCreacion(requestDTO.getUsuario());
            plazoConfirmacion.setFecCreacion(new Date());
            plazoConfirmacion.setIpCreacion(requestDTO.getIp());
        } else {
            plazoConfirmacion = plazoConfirmacionDao.obtener(requestDTO.getIdPlazoConfirmacion());
            if (plazoConfirmacion == null) {
                throw new DataNotFoundException("No se encontró el registro con ID: " + requestDTO.getIdPlazoConfirmacion());
            }
            plazoConfirmacion.setFeBase(requestDTO.getFechaBase());
            plazoConfirmacion.setInTipoDia(requestDTO.getTipoDia());
            plazoConfirmacion.setNuDias(requestDTO.getNumeroDias());
            plazoConfirmacion.setEsRegistro(requestDTO.getEstado());
            plazoConfirmacion.setUsuActualizacion(requestDTO.getUsuario());
            plazoConfirmacion.setFecActualizacion(new Date());
            plazoConfirmacion.setIpActualizacion(requestDTO.getIp());
        }
        
        PlazoConfirmacion savedEntity = plazoConfirmacionDao.save(plazoConfirmacion);
        return plazoConfirmacionMapper.toResponseDTO(savedEntity);
    }
}
