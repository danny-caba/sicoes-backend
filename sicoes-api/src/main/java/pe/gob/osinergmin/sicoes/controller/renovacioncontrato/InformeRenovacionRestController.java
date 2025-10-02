package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionDTO;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacion;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.RequerimientoAprobacion;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/informe/renovacion")
public class InformeRenovacionRestController extends BaseRestController {

	private Logger logger = LogManager.getLogger(InformeRenovacionRestController.class);
	
	@Autowired
    InformeRenovacionService informeRenovacionService;

    @GetMapping("/aprobar/buscar")
    public Page<InformeRenovacionDTO> buscar(
            @RequestParam(required = false) String numeroExpediente,
            @RequestParam(required = false) String contratista,
            @RequestParam(required = false) String estadoAprobacion, Pageable pageable) {
        logger.info("buscar {} ", numeroExpediente);
        return informeRenovacionService.buscar(numeroExpediente,contratista,estadoAprobacion,pageable,getContexto());
    }

    @GetMapping("/informes")
    public Page<InformeRenovacionDTO> listarInformes(
            @RequestParam(required = false) String numeroExpediente,
            @RequestParam(required = false) String empresaSupervisora,
            @RequestParam(required = false) String tipoInforme,
            @RequestParam(required = false) String estadoEvaluacion,
            Pageable pageable) {
        logger.info("listarInformes - numeroExpediente: {}, empresaSupervisora: {}", numeroExpediente, empresaSupervisora);
        return informeRenovacionService.listarInformesRenovacion(numeroExpediente, empresaSupervisora, tipoInforme, estadoEvaluacion, pageable, getContexto());
    }

    @GetMapping("/historial-aprobaciones")
    @Raml("aprobacionRenovacion.listar.properties")
    public Page<RequerimientoAprobacion> listarHistorialAprobaciones(
            @RequestParam Long idInformeRenovacion,
            Pageable pageable) {
        logger.info("listarHistorialAprobaciones - idInformeRenovacion: {}", idInformeRenovacion);
        return informeRenovacionService.listarHistorialAprobacionesV2(idInformeRenovacion, pageable);
    }

}
