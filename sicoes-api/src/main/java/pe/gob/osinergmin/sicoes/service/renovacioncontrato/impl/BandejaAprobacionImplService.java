package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.BandejaAprobacionResponseDTO;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.service.ListadoDetalleService;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.mapper.BandejaAprobacionMapper;
import pe.gob.osinergmin.sicoes.util.Contexto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BandejaAprobacionImplService {

    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Autowired
    private BandejaAprobacionMapper bandejaAprobacionMapper;

    @Autowired
    private ListadoDetalleService listadoDetalleService;





    public Page<BandejaAprobacionResponseDTO> listaApobaciones(

            String numeroExpediente,
            Long estadoAprobacionInforme,
            Long idContratista,
            String nombreContratista,
            Contexto contexto,
            Pageable pageable) {

        Long idUsuario = contexto.getUsuario().getIdUsuario();
        Integer esVigente = 1;

        Page<RequerimientoAprobacion> listaAprobaciones = requerimientoAprobacionDao.buscarByIdUsuario(
                numeroExpediente,
                estadoAprobacionInforme,
                idContratista,
                nombreContratista,
                idUsuario,
                esVigente,
                pageable
        );

        List<BandejaAprobacionResponseDTO> dtos = listaAprobaciones.getContent().stream()
                .map(entity -> bandejaAprobacionMapper.toDto(entity, contexto, listadoDetalleService))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, listaAprobaciones.getTotalElements());
    }
}
