package pe.gob.osinergmin.sicoes.controller.renovacioncontrato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.controller.BaseRestController;
import pe.gob.osinergmin.sicoes.model.renovacioncontrato.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.service.renovacioncontrato.InformeRenovacionContratoService;


@RestController
@RequestMapping("/api/informe/renovacion")
public class InformeRenovacionContratoController extends BaseRestController{
    
    private Logger logger = LogManager.getLogger(InformeRenovacionContratoController.class);

    @Autowired
    InformeRenovacionContratoService informeRenovacionContratoService;

    @GetMapping("/informes")
	public Page<InformeRenovacionContrato> listarInformes(
        @RequestParam(required = false) String  numeroExpediente,
        @RequestParam(required = false) Integer  estado,
        @RequestParam(required = false) String  nombreContratista,
        Pageable pageable){
		
		return informeRenovacionContratoService.listaInformes(numeroExpediente,estado,nombreContratista,getContexto(), pageable);		
	}
}
