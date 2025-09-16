package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.consumer.SigedOldConsumer;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.FirmaInformeRequestDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.FirmaInformeResponseDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoRenovacion;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.InformeRenovacionContratoDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoAprobacionDao;
import pe.gob.osinergmin.sicoes.repository.renovacioncontrato.RequerimientoRenovacionDao;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.FirmaInformeService;
import pe.gob.osinergmin.sicoes.util.Contexto;
import pe.gob.osinergmin.sicoes.util.common.exceptionHandler.DataNotFoundException;

import java.util.Optional;

@Service
public class FirmaInformeImplService implements FirmaInformeService {

    private static final Logger logger = LogManager.getLogger(FirmaInformeImplService.class);

    @Autowired
    private SigedOldConsumer sigedOldConsumer;

    @Autowired
    private RequerimientoRenovacionDao requerimientoRenovacionDao;

    @Autowired
    private InformeRenovacionContratoDao informeRenovacionContratoDao;

    @Autowired
    private RequerimientoAprobacionDao requerimientoAprobacionDao;

    @Override
    public FirmaInformeResponseDTO obtenerIdArchivo(FirmaInformeRequestDTO requestDTO, Contexto contexto) throws Exception {

        RequerimientoAprobacion entity = requerimientoAprobacionDao.obtenerPorId(requestDTO.getIdRequerimientoAprobacion());
        if (entity == null) {
            throw new DataNotFoundException("No se encontró el requerimiento de aprobación con ID: " + requestDTO.getIdRequerimientoAprobacion());
        }
        // 1. Validar que existe el informe de renovación
        Optional<InformeRenovacionContrato> informeOpt = informeRenovacionContratoDao.findById(entity.getInformeRenovacion().getIdInformeRenovacion());
        if (!informeOpt.isPresent()) {
            throw new DataNotFoundException("No se encontró el informe de renovación con ID: " + entity.getInformeRenovacion().getIdInformeRenovacion());
        }
        InformeRenovacionContrato informeRenovacionContrato = informeOpt.get();

        // 2. Validar que existe el requerimiento de renovación asociado
        Optional<RequerimientoRenovacion> requerimientoOpt = requerimientoRenovacionDao.findById(informeRenovacionContrato.getRequerimiento().getIdReqRenovacion());
        if (!requerimientoOpt.isPresent()) {
            throw new DataNotFoundException("No se encontró el requerimiento de renovación con ID: " + informeRenovacionContrato.getRequerimiento().getIdReqRenovacion());
        }

        RequerimientoRenovacion requerimiento = requerimientoOpt.get();

        Long idArchivo = sigedOldConsumer.obtenerIdArchivosRenovacionContrato(requerimiento.getNuExpediente(),informeRenovacionContrato.getIdInformeRenovacion());

        // 4. Crear y devolver la respuesta
    FirmaInformeResponseDTO response = new FirmaInformeResponseDTO();
        response.setIdArchivo(idArchivo);



        return response;
    }


}
