package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.Asignacion;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.InformeRenovacionContratoDTO;
import pe.gob.osinergmin.sicoes.model.dto.renovacioncontrato.RequerimientoAprobacionDTO;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionContratoService;


@RestController
@RequestMapping("/api/renovacion")
public class InformeRenovacionContratoController extends BaseRestController{

    private final Logger logger = LogManager.getLogger(InformeRenovacionContratoController.class);

    private final InformeRenovacionContratoService informeRenovacionContratoService;

    public InformeRenovacionContratoController(InformeRenovacionContratoService informeRenovacionContratoService) {
        this.informeRenovacionContratoService = informeRenovacionContratoService;
    }

    @GetMapping("/informes")
    public Page<InformeRenovacionContratoDTO> listarInformes(
            @RequestParam(required = false) String numeroExpediente,
            @RequestParam(required = false) Long estado,
            @RequestParam(required = false) Long idContratista,
            Pageable pageable) {
        
        logger.info("get listarInformes: {} {} {}", numeroExpediente,estado,idContratista);
        String tipoAprobador=null;
        return informeRenovacionContratoService.listaInformes(
                tipoAprobador,
                numeroExpediente, estado, idContratista, getContexto(), pageable);
    }

    @PostMapping("/informes")
	public InformeRenovacionContratoDTO crearInforme(@RequestBody InformeRenovacionContratoDTO informeRenovacionContratoDTO) {
		logger.info("crearInforme objecto: {} ",informeRenovacionContratoDTO.getObjeto());

		return informeRenovacionContratoService.crearInforme(informeRenovacionContratoDTO, getContexto());
	}

    @PostMapping("/informes/presupuestal/rechazar")
	public RequerimientoAprobacionDTO rechazarInforme(@RequestBody RequerimientoAprobacionDTO requerimientoAprobacionDTO) {
		logger.info("rechazarInforme objecto: {} ",requerimientoAprobacionDTO.getDeObservacion());

		return informeRenovacionContratoService.rechazarInformePresupuestal(requerimientoAprobacionDTO, getContexto());
	}
}
