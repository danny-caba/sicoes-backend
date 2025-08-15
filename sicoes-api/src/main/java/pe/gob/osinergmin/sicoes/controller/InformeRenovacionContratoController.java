package pe.gob.osinergmin.sicoes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.osinergmin.sicoes.model.DictamenEvaluacion;
import pe.gob.osinergmin.sicoes.model.InformeRenovacionContrato;
import pe.gob.osinergmin.sicoes.service.InformeRenovacionContratoService;
import pe.gob.osinergmin.sicoes.util.Raml;

@RestController
@RequestMapping("/api/informe/renovacion")
public class InformeRenovacionContratoController extends BaseRestController{
    
    private Logger logger = LogManager.getLogger(InformeRenovacionContratoController.class);

    @Autowired
    InformeRenovacionContratoService informeRenovacionContratoService;

    @GetMapping("/informes")
	public Page<InformeRenovacionContrato> listarInformes(
        @RequestParam String  numeroExpediente,
        @RequestParam String  estado,
        @RequestParam String  nombreContratista,
        Pageable pageable){
		
		return informeRenovacionContratoService.listaInformes(numeroExpediente,estado,nombreContratista,getContexto());		
	}
}
