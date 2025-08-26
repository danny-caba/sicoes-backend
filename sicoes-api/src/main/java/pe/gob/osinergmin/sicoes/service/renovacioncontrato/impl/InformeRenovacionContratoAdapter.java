package pe.gob.osinergmin.sicoes.service.renovacioncontrato.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoAprobacionDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionContratoService;
import pe.gob.osinergmin.sicoes.util.Contexto;

@Service
public class InformeRenovacionContratoAdapter implements InformeRenovacionContratoService {

    private final ListarInformeRenovacionContratoImpl listarInformeRenovacionContratoImpl;
    private final CrearInformeRenovacionContratoImpl crearInformeRenovacionContratoImpl;
    private final RechazarInformePresupuestalRenovacionContratoImpl rechazarInformeRenovacionContratoImpl;

    public InformeRenovacionContratoAdapter(
            CrearInformeRenovacionContratoImpl crearInformeRenovacionContratoImpl, 
            ListarInformeRenovacionContratoImpl listarInformeRenovacionContratoImpl, 
            RechazarInformePresupuestalRenovacionContratoImpl rechazarInformeRenovacionContratoImpl) {
        this.listarInformeRenovacionContratoImpl = listarInformeRenovacionContratoImpl;
        this.crearInformeRenovacionContratoImpl = crearInformeRenovacionContratoImpl;
        this.rechazarInformeRenovacionContratoImpl = rechazarInformeRenovacionContratoImpl;
    }


    @Override
    public Page<InformeRenovacionContratoDTO> listaInformes(String tipoAprobador,String numeroExpediente, Long estado, Long idContratista,
            Contexto contexto, Pageable pageable) {
        return listarInformeRenovacionContratoImpl.ejecutar(tipoAprobador,numeroExpediente, estado, idContratista, contexto, pageable);
    }

    @Override
    public InformeRenovacionContratoDTO crearInforme(InformeRenovacionContratoDTO informeRenovacionContratoDTO,
            Contexto contexto) {
        return crearInformeRenovacionContratoImpl.ejecutar(informeRenovacionContratoDTO, contexto);
    }

    @Override
    public RequerimientoAprobacionDTO rechazarInformePresupuestal(RequerimientoAprobacionDTO requerimientoAprobacionDTO,
            Contexto contexto) {
        return rechazarInformeRenovacionContratoImpl.ejecutar(requerimientoAprobacionDTO,contexto);
    }
    
}
