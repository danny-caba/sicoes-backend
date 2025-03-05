package pe.gob.osinergmin.sicoes.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

 
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.osinergmin.sicoes.model.PropuestaProfesional;
import pe.gob.osinergmin.sicoes.model.Seccion;
import pe.gob.osinergmin.sicoes.model.SicoesSolicitud;
import pe.gob.osinergmin.sicoes.model.SicoesTdSoliPersProp;
import pe.gob.osinergmin.sicoes.service.SeccionService;
import pe.gob.osinergmin.sicoes.service.SicoesTdSolPersPropService;
import pe.gob.osinergmin.sicoes.service.SolicitudSicoesService;
import pe.gob.osinergmin.sicoes.util.Raml;


@RestController
@RequestMapping("/api")
public class SolicitudTdSolPersPropController extends BaseRestController{
	
	private Logger logger = LogManager.getLogger(SolicitudTdSolPersPropController.class);
	
	@Autowired
	private SicoesTdSolPersPropService sicoesTdSolPersPropService;

	@Autowired
	private EmpresasSancionadaRestController empresasSancionadaRestController;

	@PutMapping("/sicoes/solicitud/seccion/persona")
	public SicoesTdSoliPersProp getSolicitud(@RequestBody SicoesTdSoliPersProp sicoesTdSoliPersProp){
		return sicoesTdSolPersPropService.update(sicoesTdSoliPersProp,getContexto());
	}

	@GetMapping("/personas/{idSeccionPerConSec}/listar")
	@Raml("persona.listar.properties")
	public Page<SicoesTdSoliPersProp> personasPorSeccion(@PathVariable Long idSeccionPerConSec, Pageable pageable){
		logger.info("personasPorSeccion");
		return sicoesTdSolPersPropService.personasPorSeccion(idSeccionPerConSec, pageable, getContexto());
	}

	@PostMapping("/personas/validar-trabajador")
	public Map<String,String> validarTrabajadorOsi(@RequestBody String codigoRuc) {
		logger.info("validarTrabajadorOsi {} ",codigoRuc);

		return empresasSancionadaRestController.ValidadSancionPerfContr(codigoRuc);
	}
 
}

